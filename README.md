# L1JTW8.5 — Completed Core Fixes

Branch: `completed/l1jtw85-core-fixes`

This branch contains **validated core repairs only**.  
Active/unvalidated repair work stays on `work/l1jtw85-core-fixes`.

## Promotion rule

A repair may enter this branch only after the project chain has been checked:

```text
BUG
-> Java CORE entry / call path
-> config control
-> DB table / loader
-> default / fallback
-> ACTIVE source
-> minimal complete repair
-> targeted validation
-> promotion
```

380 / 880 cores may be used as donor/reference evidence, but fixes are not copied blindly.

## Completed repairs

| BUG | Level | Area | Status |
|---|---|---|---|
| BUG-850-294 | L2 | NPC sell-to-shop inventory / payout consistency | PASS / PROMOTED |
| BUG-850-293 | L2 | NPC purchase war-tax treasury accounting | PASS / PROMOTED |
| BUG-850-292 | L2 | c3p0 connection acquisition / checkout liveness | PASS / PROMOTED |
| BUG-850-291 | L2 | clan-mail sender/target clan authorization binding | PASS / PROMOTED |
| BUG-850-284 | L2 | ShopWorld clan-announcement governance authorization | PASS / PROMOTED |
| BUG-850-283 | L2 | ShopWorld account debit / pending-item durable atomicity | PASS / PROMOTED |
| BUG-850-282 | L2 | ShopWorld claim capacity/count authority | PASS / PROMOTED |
| BUG-850-281 | L2 | ShopWorld resolvent local-NPC interaction authorization | PASS / PROMOTED |
| BUG-850-280 | L2 | LuckyDraw claim capacity/reward-count authority | PASS / PROMOTED |
| BUG-850-277 | L2 | new quest existing-inventory item progress initialization | PASS / PROMOTED |
| BUG-850-276 | L2 | new quest level-objective completion evaluation | PASS / PROMOTED |

## BUG-850-294 — NPC sell validation set differed from inventory mutation set

### Problem

The normal NPC sell-to-shop path priced only entries that passed server-side inventory and NPC purchasing-list validation.

After pricing, the completion phase iterated the original request list again and removed every requested entry. Therefore the set used to calculate payout could differ from the set used to mutate player inventory.

### Root cause

`C_Result` delegates the normal NPC sell flow to `ShopTable`.

Inside `ShopTable`:

1. the pricing pass resolved the current player inventory object;
2. the pricing pass resolved the NPC's authoritative purchasing entry from the loaded `shop` table;
3. invalid entries were skipped from payout;
4. the later deletion pass did not reuse that validated set.

The transaction therefore had two different authorities for the same sale.

### Runtime source map

- CORE: `C_Result case 1 -> ShopTable.a(pc, orderList, npc, coinID)`
- DB: `shop`
  - `item_id`
  - `selling_price`
  - `purchasing_price`
  - `pack_count`
- Loader: `ShopTable` loads `shop` by NPC id.
- Config: `RateShopPurchasingPrice` -> `Config.M`
- Config default: `1.0`
- Loader fallback: `pack_count=0 -> 1`
- Protocol / DB schema changes: **none**

### 380 / 880 cross-check

- 380 builds an `L1Shop` sell-order list before calling the shop purchase operation.
- 880 performs inventory mutation from the accepted `L1ShopSellOrderList`.

Both support the same invariant used by this repair:

```text
validated pricing set == inventory mutation set
```

### Fix

The 8.5 `ShopTable` now builds a `validatedOrderList` only after:

- the player inventory object exists;
- the NPC actually purchases that item;
- the authoritative sale count has been clamped to the player's current count.

The completion phase removes items only from that validated list and reuses the same clamped count that was used for pricing.

Existing completed-branch positive-count, long-arithmetic, and overflow guards were preserved.

### Modified core source

- `recovered-src-obf/ao/bc.java`
- `recovery/normalized-src-vf/l1r/ao/ShopTable.java`

Promotion commits:

- obfuscated source: `c015503058b7a44f8150e4eb99fc041e23818659`
- normalized source: `cd01d2244ca29a35a7a72bcb1040f9367762b4f3`

### Validation

Final targeted validation:

```text
GitHub Actions run = 35671684341
STATUS = PASS

BUG_850_294_CONTRACT=PASS
BUG_850_294_TARGETED_JAVAC_REGRESSION=PASS
BUG_850_294_TARGETED_BEHAVIOR_RUNTIME=PASS
BUG_850_294_PROMOTION_CONTRACT=PASS
BUG_850_294_PROMOTION_JAVAC_REGRESSION=PASS
COMPLETED_EXISTING_GUARDS=PRESERVED
PROMOTION_EXACT_BLOB_MATCH=PASS
```

The normalized recovered `ShopTable` already has five unrelated decompiler/generic javac errors before this repair. The validation gate therefore compares baseline vs patched javac diagnostics and requires **no new compile error signatures**. The targeted Java behavior test also executes the validation/mutation-set invariant and count-clamp behavior.

This is a targeted runtime regression gate, not a live game-server session.

### Result

```text
BUG-850-294=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-293 — positive war tax was charged but not credited to Aden/Diad

### Problem

Normal NPC purchases include the fixed war-tax component in the amount charged to the player, but the treasury distribution block was guarded by `warTax <= 0`.

For ordinary positive-price purchases the calculated war tax is positive, so the distribution block was skipped and the corresponding persistent castle treasury revenue was not credited.

### Root cause

8.5 `TaxCalculator` defines the war-tax component as a fixed 15% and the normal NPC purchase path includes it in the charged total.

After payment, both relevant `ShopTable` purchase paths calculate the same positive war-tax value, resolve Aden/Diad treasury destinations, and then used the inverted sign condition before updating them.

### Runtime source map

- CORE: `ShopTable` normal NPC purchase paths
- Tax calculator: fixed war-tax rate `15`
- DB: `castle.public_money`
- Loader/persistence: `CastleTable`
- Destination castle ids: Aden `7`, Diad `8`
- Config: no external war-tax-rate control in this 8.5 path
- Protocol / DB schema changes: **none**

### 380 / 880 cross-check

Both donor lines treat treasury tax credit as a **positive-tax** operation. Their exact tax formulas/rates differ from 8.5, so only the accounting invariant was reused:

```text
positive charged tax -> positive treasury credit
```

No donor tax percentage was copied into 8.5.

### Fix

Both 8.5 war-tax distribution gates were changed from:

```text
warTax <= 0
```

to:

```text
warTax > 0
```

The fixed 15% calculation, destination castle ids, payment path and DB schema were left unchanged.

### Modified core source

- `recovered-src-obf/ao/bc.java`
- `recovery/normalized-src-vf/l1r/ao/ShopTable.java`

Promotion commits:

- obfuscated source: `32e73abc70b48e5689fb87b906df1b56a7d6c9ca`
- normalized source: `8ef5fd37a3c427c74092c1f833eb6144c88066ff`

### Validation

Final promotion validation:

```text
GitHub Actions run = 35672076110
STATUS = PASS

BUG_850_293_CONTRACT=PASS
BUG_850_293_TARGETED_JAVAC_REGRESSION=PASS
BUG_850_293_TARGETED_BEHAVIOR_RUNTIME=PASS
POSITIVE_WAR_TAX_DISTRIBUTION=PASS
BUG_850_293_PROMOTION_CONTRACT=PASS
BUG_850_293_PROMOTION_JAVAC_REGRESSION=PASS
BUG_850_294_PRESERVED=PASS
COMPLETED_EXISTING_GUARDS=PRESERVED
PROMOTION_EXACT_BLOB_MATCH=PASS
```

The targeted Java runtime regression executes the positive/zero tax accounting behavior. The normalized recovered source still carries pre-existing recovery/decompiler compile errors, so the javac gate compares baseline and patched error signatures and requires no new errors.

This is a targeted runtime regression gate, not a live game-server session.

### Result

```text
BUG-850-293=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-292 — database connection acquisition could wait indefinitely

### Problem

The active 8.5 `DatabaseFactory` explicitly configured bundled c3p0 0.9.5.2 with:

```text
acquireRetryAttempts=0
acquireRetryDelay=500
checkoutTimeout=0
breakAfterAcquireFailure=false
```

For the bundled c3p0 generation, non-positive acquisition retry attempts mean unlimited acquisition retries, while `checkoutTimeout=0` means callers of `getConnection()` can wait indefinitely.

`DatabaseFactory.b()` adds no higher-level deadline, and startup validation also calls `getConnection()`, so the same unbounded policy affected both startup and runtime DB callers.

### Runtime source map

- CORE: `DatabaseFactory`
- Runtime dependency: `lib/c3p0-0.9.5.2.jar`
- DB endpoint config: `config/server.properties`
  - `URL`
  - `Login`
  - `Password`
- External pool timeout/retry config: **none**
- DB schema changes: **none**
- Protocol changes: **none**

### 380 / 880 cross-check

The 380 / 880 repositories were searched for an equivalent c3p0 timeout/retry repair, but no matching pool policy was found.

Therefore this repair does **not** copy donor numbers. The policy is based on the bundled 8.5 c3p0 version and its documented semantics, then validated against the bundled runtime JAR.

### Fix

The pool now uses:

```text
acquireRetryAttempts=30
acquireRetryDelay=500
checkoutTimeout=30000 ms
breakAfterAcquireFailure=false
```

Rationale:

- `30` restores a finite retry count instead of unlimited acquisition attempts;
- the existing 500 ms retry delay is preserved;
- `30000 ms` gives callers a finite checkout deadline;
- `breakAfterAcquireFailure=false` is preserved so the pool can remain available for future requests after a transient DB outage.

### Modified core source

- `recovered-src-obf/l1j/server/b.java`
- `recovery/normalized-src-vf/l1r/l1j/server/DatabaseFactory.java`

Promotion commits:

- obfuscated source: `7c357efb9051b914e393338e239d7a6b596dcbd6`
- normalized source: `44b99388e0bbed1a9a30b0cce18a78e2de1f427b`

### Validation

Final targeted validation:

```text
GitHub Actions run = 35672679021
STATUS = PASS

BUG_850_292_CONTRACT=PASS
BUG_850_292_TARGETED_JAVAC_REGRESSION=PASS
BUG_850_292_BUNDLED_C3P0_RUNTIME=PASS
PRODUCTION_POLICY_GETTERS=PASS
BUG_850_292_PROMOTION_CONTRACT=PASS
POOL_RECOVERY_POLICY_PRESERVED=PASS
BUG_850_292_PROMOTION_JAVAC_REGRESSION=PASS
PROMOTION_EXACT_BLOB_MATCH=PASS
```

The runtime gate compiles and executes against the repository's actual c3p0 0.9.5.2 / mchange / MySQL Connector/J dependencies. A deliberately unreachable local DB endpoint is required to fail with `SQLException` inside a bounded test envelope; the final successful run failed in 467 ms under the short test policy.

The production policy itself is also asserted through c3p0 getters.

This is a targeted dependency/runtime regression gate, not a live production DB outage test.

### Result

```text
BUG-850-292=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-291 — clan mail target was selected by client clan name instead of sender ClanID

### Problem

The clan-mail send branch first checked only that the sender belonged to some clan.

It then parsed a clan-name string from the request and resolved the recipient clan from that client-provided name. The resolved clan's member list was used to create durable clan-mail rows.

The sender's authoritative `ClanID` therefore proved only "the sender belongs to a clan"; it did not bind the persistent mail mutation to that same clan.

### Root cause

The active path used two separate authorities:

```text
authorization gate = sender ClanID != 0
target selection    = client clanName -> ClanTable lookup
```

Those values were never compared or rebound.

### Runtime source map

- CORE: `C_Mail`, clan-mail send type
- Sender authority: `L1PcInstance.ClanID`
- Clan lookup authority: `ClanTable.a(int clan_id)`
- Recipient set: authoritative clan member list
- Persistence: `MailTable` clan-mail rows
- Config/default layer: none
- Protocol change: **none**
- DB schema change: **none**

### 380 / 880 cross-check

The inspected 380 / 880 clan-mail implementations also accept a client clan-name field and use that field to resolve the target clan.

Therefore they were useful as protocol/history references but **not** as a safe repair donor for this authorization defect.

The 8.5 repair instead follows the server-authority invariant:

```text
sender ClanID -> authoritative clan -> recipient member list
```

### Fix

The request's clan-name field is still parsed so packet compatibility is unchanged, but it no longer selects the target clan.

The target clan is now resolved only from the sender's current authoritative `ClanID`:

```text
client clanName = protocol data only
sender ClanID   = target-clan authority
```

If the authoritative ClanID does not resolve to a live clan object, the path fails closed before persistent clan mail is created.

### Modified core source

- `recovered-src-obf/aj/bg.java`
- `recovery/normalized-src-vf/l1r/aj/C_Mail.java`

Promotion commits:

- obfuscated source: `e397bf6b531e02e2fbb01b067b664ad2f06810c3`
- normalized source: `ef0351a37c6095d960a09b47d164dd0b675c892e`

### Validation

Isolated validation run:

```text
GitHub Actions run = 35674676538
STATUS = PASS

BUG_850_291_CONTRACT=PASS
TARGET_CLAN_AUTHORITY=sender.ClanID
BUG_850_291_TARGET_BINDING_RUNTIME=PASS
CLIENT_NAME_CANNOT_SELECT_OTHER_CLAN=PASS
BUG_850_291_EXACT_BASE_TRANSFORM=PASS
BUG_850_291_TARGETED_JAVAC_REGRESSION=PASS
PROMOTION_EXACT_BLOB_MATCH=PASS
```

The isolated workflow proves that the promoted staging files equal the then-current completed files with only the intended clan-authority lookup changed. This prevents unrelated work-branch `C_Mail` repairs from being promoted accidentally.

The Java behavior regression verifies that a mismatching client clan name cannot change the authoritative target selected by the sender ClanID.

This is a targeted authorization/runtime regression gate, not a live multi-client game-server session.

### Result

```text
BUG-850-291=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-284 — ShopWorld clan announcement lacked clan-governance authorization

### Problem

ShopWorld action 15 resolves the active player's clan and persists a shared clan announcement to `clan_data.announcement`.

Before this repair, any session belonging to a clan could reach the mutation path without proving that the requester was the current clan leader / crown authority.

### Root cause

The handler used the player's `ClanID` to find the clan object, but treated clan membership itself as sufficient authorization for a clan-wide persistent governance mutation.

The same 8.5 core already uses a stronger boundary in `C_BanClan`:

```text
requester is crown/royal
AND
requester object id == clan leader id
```

### Runtime source map

- CORE: `C_ShopWorld`, action 15
- Clan source: active player's `ClanID`
- Leader authority: `L1Clan.leaderId`
- Persistence: `ClanTable.update(clan)`
- DB: `clan_data.announcement`
- Config/default layer: none
- Protocol change: **none**
- DB schema change: **none**

### 380 / 880 cross-check

A direct matching `C_ShopWorld` action-15 implementation was not available at the expected donor paths during this repair, so no donor authorization logic was guessed or copied.

The repair instead reuses the already-established 8.5 clan-governance boundary from `C_BanClan`.

### Fix

Before changing or persisting the announcement, action 15 now requires:

```text
clan exists
AND requester is crown/royal
AND requester object id == current clan leader id
```

Unauthorized requests fail closed and use the existing clan-governance denial message `518`.

The clan-name/announcement payload format and persistence schema are unchanged.

### Required null guard

The completed baseline did not yet contain the work branch's action-15 clan-null guard.

Because leader authorization cannot safely evaluate `clan.leaderId` on a missing clan, this promotion includes the minimal:

```text
if clan == null -> return
```

as a prerequisite to the L2 authorization boundary.

No other ShopWorld action-8/action-10 work-branch changes were promoted with this repair.

### Modified core source

- `recovered-src-obf/aj/cd.java`
- `recovery/normalized-src-vf/l1r/aj/C_ShopWorld.java`

Promotion commits:

- obfuscated source: `1e080de4b8544618c065b71cc02e3f62416e244d`
- normalized source: `fc246c4c47464a07fd1ae4e8a55963007b6ffc15`

### Validation

Isolated validation run:

```text
GitHub Actions run = 35675169378
STATUS = PASS

BUG_850_284_CONTRACT=PASS
ANNOUNCEMENT_AUTHORITY=crown+current_clan_leader
BUG_850_284_TARGETED_BEHAVIOR_RUNTIME=PASS
CLAN_GOVERNANCE_AUTHORITY=PASS
BUG_850_284_EXACT_BASE_TRANSFORM=PASS
BUG_850_284_TARGETED_JAVAC_REGRESSION=PASS
PROMOTION_EXACT_BLOB_MATCH=PASS
```

An earlier run failed only because YAML block indentation altered a multiline string inside the exact-transform harness. The exact comparison was moved to a standalone Python validator and the corrected run passed.

This is a targeted governance/runtime regression gate, not a live multi-client game-server session.

### Result

```text
BUG-850-284=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-283 — ShopWorld debit and pending-item persistence were not atomic

### Problem

ShopWorld action 8 previously performed one purchase through two unrelated durable operations:

1. mutate the live account shop-currency balance;
2. persist `accounts.WorldShopAdena`;
3. independently insert purchased pending items into `character_shop`.

Both helpers swallowed DB failures and returned `void`, so the caller could not know whether one durable side succeeded while the other failed.

The original schema made the split stronger:

```text
accounts        = MyISAM
character_shop  = InnoDB
```

Therefore merely sharing a JDBC transaction would still not make the original two-table purchase atomic.

### Root cause

The purchase had three independent publication boundaries:

```text
live L1Account balance
accounts.WorldShopAdena
character_shop pending rows / pending RAM map
```

There was no single success result spanning them, no shared transactional engine, and no rollback/compensation path.

### Runtime source map

- CORE entry: `C_ShopWorld` action 8
- Durable helper: `ShopWorldTable`
- Account durable field: `accounts.WorldShopAdena`
- Purchased pending rows: `character_shop`
- Original DB engines:
  - `accounts = MyISAM`
  - `character_shop = InnoDB`
- Config/default layer: none
- Protocol change: **none**

### DB migration requirement

Existing databases must apply:

```text
db/migrations/BUG-850-283_accounts_innodb.sql
```

The migration changes:

```sql
ALTER TABLE accounts ENGINE=InnoDB;
```

and includes an engine verification query for both `accounts` and `character_shop`.

The repaired core also checks the live database engines before every transactional ShopWorld purchase. If either table is not InnoDB, the purchase fails closed instead of performing a non-atomic debit/delivery.

### Fix

A new ShopWorld purchase helper now owns the complete durable transaction.

The repaired order is:

```text
validate request
-> stage pending indexes/items in memory only
-> verify accounts + character_shop are InnoDB
-> disable auto-commit
-> conditional UPDATE accounts balance
-> INSERT all character_shop rows
-> commit
-> publish pending items to live RAM
-> update live L1Account balance
-> send success/history
```

The account UPDATE is conditional on the durable balance still matching the balance used by the request:

```text
WHERE login=? AND WorldShopAdena=expectedBalance
```

If that update affects anything other than exactly one row, the transaction rolls back.

If any pending-item INSERT fails, the same transaction rolls back.

The old independent `AccountTable.update(account)` + separate 3-argument pending-item insert sequence is no longer used by ShopWorld action 8.

### Failure behavior

The following cases now fail without publishing a partial purchase:

- required tables are not both InnoDB;
- durable account balance changed before commit;
- a pending-item row cannot be inserted;
- connection/SQL failure occurs before commit.

Live account balance and pending RAM publication occur only after durable commit succeeds.

### 380 / 880 reference

This defect is driven by the 8.5 schema engines and its own ShopWorld persistence helpers. No donor implementation was copied as a transaction authority.

The repair is based on the active 8.5 DB/storage boundary and preserves the existing ShopWorld packet shape.

### Modified core / DB

- `recovered-src-obf/aj/cd.java`
- `recovery/normalized-src-vf/l1r/aj/C_ShopWorld.java`
- `recovered-src-obf/ao/bd.java`
- `recovery/normalized-src-vf/l1r/ao/ShopWorldTable.java`
- `db/migrations/BUG-850-283_accounts_innodb.sql`

Promotion commits:

- normalized `C_ShopWorld`: `903f1b2798dfbbc6fdc46bd0806e55e16d6b88f5`
- obfuscated `C_ShopWorld`: `4e33e861ad5f7739a325b7e30bc56e089dda7caf`
- normalized `ShopWorldTable`: `d4a81baa91a0601cf88a327ae6fe42d864bf30af`
- obfuscated `ShopWorldTable`: `5f64a8dd40baa8e16cb588cbcedf6e10bfb02127`
- DB migration: `2c3f7447919ad746ded01252b79e3c7af3f33349`

### Validation

Isolated validation run:

```text
GitHub Actions run = 35676841423
STATUS = PASS

BUG_850_283_CONTRACT=PASS
DURABLE_ORDER=engine_gate->tx->conditional_debit->pending_insert->commit->RAM
DB_MIGRATION=accounts_to_InnoDB
BUG_850_283_TRANSACTION_MODEL_RUNTIME=PASS
FAILURE_ROLLBACK=PASS
COMMIT_BEFORE_RAM_PUBLICATION=PASS
BUG_850_283_EXACT_BASE_TRANSFORM=PASS
BUG_850_283_TARGETED_JAVAC_REGRESSION=PASS
PROMOTION_EXACT_BLOB_MATCH=PASS
```

The normalized recovery pair has pre-existing compile errors, so the compile gate compares baseline and staged javac error signatures and requires no new error signature.

The transaction behavior regression explicitly verifies engine-mismatch fail-closed behavior, conditional-balance rejection, rollback on an injected pending-item failure, and commit-before-RAM publication.

This is a targeted transaction/runtime model plus source-contract gate; it is not a live production MySQL failure-injection session.

### Deployment

```text
1. Apply db/migrations/BUG-850-283_accounts_innodb.sql
2. Verify accounts and character_shop both report ENGINE=InnoDB
3. Build/deploy the repaired core
4. Restart the server
```

### Result

```text
BUG-850-283=L2
STATUS=PASS
PROMOTED=YES
DB_MIGRATION_REQUIRED=YES
RESTART_REQUIRED=YES
```


## BUG-850-282 — ShopWorld claim capacity trusted the request count instead of the pending item

### Problem

ShopWorld action 10 receives a pending-item index plus a client count.

The original handler resolved the server-side pending `L1ItemInstance`, but then passed the client count into inventory capacity/weight validation and history logging before inserting the authoritative pending object.

The count used to decide whether the claim could fit therefore did not have the same authority as the object actually delivered.

### Runtime source map

- CORE: `C_ShopWorld`, action 10
- Pending source: `ShopWorldTable`
- Durable pending source: `character_shop`
- Pending item factory: `ItemTable.b(itemId)`
- Factory default count: `new L1ItemInstance(item, 1)`
- Authoritative runtime count: `L1ItemInstance.E()`
- Config/default layer: none
- Protocol change: **none**
- DB schema change: **none**

### Root cause

The path mixed two sources:

```text
delivered object = server pending item
capacity count   = client request count
```

The request count was parsed as protocol data but incorrectly retained authority over capacity and history.

### Fix

Action 10 now:

1. resolves the pending item by server-side pending index;
2. fails closed if the pending item is missing;
3. reads `authoritativeCount = pendingItem.E()`;
4. fails closed if that count is non-positive;
5. uses the authoritative count for inventory capacity/weight validation;
6. inserts the same authoritative pending object;
7. records history using the same authoritative count.

The request count is still parsed for packet compatibility but no longer controls claim capacity or history.

### Modified core source

- `recovery/normalized-src-vf/l1r/aj/C_ShopWorld.java`
- `recovered-src-obf/aj/cd.java`

Promotion commits:

- normalized source: `c46cdd93857096cd36dd06032e232af62734c436`
- obfuscated source: `4b49066ed5679c3fc3633e29ab6ebc746d108c21`

### Validation

Final isolated validation:

```text
GitHub Actions run = 35677827084
STATUS = PASS

BUG_850_282_CONTRACT=PASS
PENDING_COUNT_AUTHORITY=L1ItemInstance.E
PENDING_FACTORY_COUNT=1
CLIENT_COUNT_AUTHORITY=NONE
BUG_850_282_TARGETED_BEHAVIOR_RUNTIME=PASS
CLIENT_COUNT_CANNOT_CHANGE_CAPACITY=PASS
BUG_850_282_EXACT_BASE_TRANSFORM=PASS
BUG_850_282_TARGETED_JAVAC_REGRESSION=PASS
PROMOTION_EXACT_BLOB_MATCH=PASS
```

An earlier run failed because a concurrent work-branch update restored the old action-10 block after the repair had first been applied. The L2 lane already owned BUG-850-282, so the same minimal action-10 fix was reapplied to the latest work file without touching other blocks; the subsequent isolated run passed.

This is a targeted authority/capacity regression gate, not a live inventory load test.

### Result

```text
BUG-850-282=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-281 — ShopWorld resolvent action was authorized by global NPC id without local interaction proof

### Problem

ShopWorld action 13 resolves an NPC from the global world registry and then performs item-conversion inventory mutations.

Before this repair, a valid NPC object id was sufficient to reach the conversion path; the handler did not enforce the same local interaction boundary used by the normal NPC-talk path.

### Runtime source map

- CORE: `C_ShopWorld`, action 13
- NPC lookup: `L1World`
- Conversion authority: resolved `L1NpcInstance`
- Item conversion: `ResolventTable` + player inventory mutation
- Existing local interaction reference: `C_NpcTalk`
- Protocol change: **none**
- DB schema change: **none**

### Existing 8.5 interaction boundary

`C_NpcTalk` already defines normal NPC interaction locality as:

```text
same map
AND
tile-line distance <= 11
```

This repair reuses that exact server-side boundary rather than introducing a new distance constant.

### Fix

After the NPC object has resolved, action 13 now rejects the request before any item lookup or mutation when:

```text
npc.map != player.map
OR
npc tile-line distance from player > 11
```

The packet shape, conversion table and item formulas remain unchanged.

### Scope note

The completed baseline still carried the separate action-13 direct-cast robustness behavior covered by lower-tier BUG-850-279.

This L2 promotion does not attempt to fold that unrelated L3 repair into BUG-850-281; the promotion changes only the local-interaction authorization boundary.

### Modified core source

- `recovery/normalized-src-vf/l1r/aj/C_ShopWorld.java`
- `recovered-src-obf/aj/cd.java`

Promotion commits:

- normalized source: `56ba35e9f8e5343be1010fbbdbcdeac49a1bcedc`
- obfuscated source: `71a5d2970b6b8fa5a773d4f3b0a72cdd8d86d31a`

### Validation

Isolated validation run:

```text
GitHub Actions run = 35678757701
STATUS = PASS

BUG_850_281_CONTRACT=PASS
LOCALITY_AUTHORITY=same_map+tile_line_distance<=11
DISTANCE_SOURCE=C_NpcTalk
BUG_850_281_TARGETED_BEHAVIOR_RUNTIME=PASS
NPC_LOCALITY_BOUNDARY=PASS
BUG_850_281_EXACT_BASE_TRANSFORM=PASS
BUG_850_281_TARGETED_JAVAC_REGRESSION=PASS
PROMOTION_EXACT_BLOB_MATCH=PASS
```

The exact-transform gate used the then-current completed `C_ShopWorld` as the base, preserving the previously completed BUG-850-284 / BUG-850-283 / BUG-850-282 changes.

This is a targeted interaction-authorization regression gate, not a live multi-client game-server session.

### Result

```text
BUG-850-281=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-280 — LuckyDraw claim capacity used the request count instead of the pending reward count

### Problem

The LuckyDraw claim branch resolves a server-side pending `L1ItemInstance`, but the original inventory-capacity check used a client-provided count.

The actual grant path inserts the entire pending item object, whose count comes from the LuckyDraw reward definition / persisted pending row.

That created a mismatch between the quantity that passed capacity/weight validation and the quantity actually delivered.

### Runtime source map

- CORE: `C_Result`, LuckyDraw result type 29
- Pending source: `LuckyDrawTable`
- Reward configuration count: `luckydraw.count`
- Durable pending count: `character_luckydraw.count`
- Authoritative runtime count: `L1ItemInstance.E()`
- Protocol change: **none**
- DB schema change: **none**

### Authoritative count path

The active 8.5 path is:

```text
luckydraw.count
-> LuckyDrawTable creates pending L1ItemInstance and sets count
-> character_luckydraw.count persists pending count
-> reload restores that count into L1ItemInstance
-> C_Result grants that pending object
```

Therefore the authoritative quantity for capacity validation is the pending item's `E()`, not the request count.

### Fix

The client count is still parsed for packet compatibility / existing sanity checks, but it no longer controls reward capacity.

The claim path now:

1. resolves the pending reward;
2. fails closed if the pending item is missing;
3. reads `authoritativeCount = pendingItem.E()`;
4. fails closed if that count is non-positive;
5. runs inventory capacity/weight validation with that authoritative count;
6. inserts the same pending item object.

### Scope note

The completed obfuscated baseline already contained an earlier packet-sanity guard for LuckyDraw index/count values. That guard is preserved.

This repair changes only the capacity authority from client count to pending reward count.

### Modified core source

- `recovery/normalized-src-vf/l1r/aj/C_Result.java`
- `recovered-src-obf/aj/bx.java`

Promotion commits:

- normalized source: `7a463b2d175715d3b7bf665b217e976ed9a2649d`
- obfuscated source: `3c0af015c7bf9fc0ee63ce92d6a0135f7ca67468`

### Validation

Isolated validation run:

```text
GitHub Actions run = 35679277832
STATUS = PASS

BUG_850_280_CONTRACT=PASS
CAPACITY_COUNT_AUTHORITY=pending_item.E
REWARD_COUNT_SOURCE=luckydraw.count/character_luckydraw.count
BUG_850_280_TARGETED_BEHAVIOR_RUNTIME=PASS
CLIENT_COUNT_CANNOT_CHANGE_REWARD_CAPACITY=PASS
BUG_850_280_EXACT_BASE_TRANSFORM=PASS
BUG_850_280_TARGETED_JAVAC_REGRESSION=PASS
PROMOTION_EXACT_BLOB_MATCH=PASS
```

The exact-transform gate used the then-current completed C_Result files as the base and preserved the pre-existing packet-sanity guard.

This is a targeted reward-capacity authority regression gate, not a live inventory/load test.

### Result

```text
BUG-850-280=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-277 — newly attached item-collection quests ignored already-loaded inventory

### Problem

The login path loads the player's inventory before `QuestNewTable` restores and attaches newly eligible quests.

Item-collection progress is normally updated by `L1PcInventory` callbacks when inventory items are added/changed/removed. A newly attached quest therefore started with zero `B[]` item progress even when matching items were already present before the quest existed.

### Existing 8.5 objective rule

The active inventory callback matches an item objective using:

```text
quest item id == inventory item id
AND
quest minimum enchant <= inventory item enchant
```

and then updates quest progress through:

```text
L1QuestNew.a(objectiveIndex, count)
```

That setter caps progress to the quest requirement and runs the normal completion evaluator.

### Fix

Immediately after a new quest is attached, `QuestNewTable` now scans the player's already-loaded inventory for each item objective.

For every objective it:

1. aggregates counts from all matching stacks;
2. applies the same item-id and minimum-enchant rule used by `L1PcInventory`;
3. ignores non-positive item counts;
4. stops once the objective requirement is reached;
5. sends the total through the existing quest progress setter.

A `long` accumulator is used while summing inventory stacks; the value is capped to the quest requirement before converting back to the quest's `int` progress field.

### Runtime source map

- Login order: inventory load precedes `QuestNewTable.a().b(pc)`
- Quest attach: `QuestNewTable.a(L1PcInstance)`
- Existing live-update rule: `L1PcInventory`
- Item objective ids: `L1QuestNew.r[]`
- Minimum enchant: `L1QuestNew.t[]`
- Required count: `L1QuestNew.s[]`
- Progress: `L1QuestNew.B[]`
- Progress/completion setter: `L1QuestNew.a(index,count)`
- Protocol change: **none**
- DB schema change: **none**

### Modified core source

- `recovery/normalized-src-vf/l1r/ao/QuestNewTable.java`
- `recovered-src-obf/ao/az.java`

Promotion commits:

- normalized source: `ad90a3c0a1553a5ed5b805234c4a97b3498d085b`
- obfuscated source: `49a876fb0e1984ca692e73707056e8539957d91b`

### Validation

Final isolated validation:

```text
GitHub Actions run = 35682206004
STATUS = PASS

BUG_850_277_CONTRACT=PASS
INITIAL_PROGRESS_SOURCE=loaded_inventory
OBJECTIVE_RULE=item_id+enchant_threshold
PROGRESS_SETTER=L1QuestNew.a(index,count)
BUG_850_277_TARGETED_BEHAVIOR_RUNTIME=PASS
EXISTING_INVENTORY_SYNC=PASS
ENCHANT_THRESHOLD=PASS
REQUIREMENT_CAP=PASS
BUG_850_277_EXACT_BASE_TRANSFORM=PASS
BASE_JAVAC_RC=0
STAGE_JAVAC_RC=0
BUG_850_277_TARGETED_JAVAC_REGRESSION=PASS
PROMOTION_EXACT_BLOB_MATCH=PASS
```

The first validation attempt failed only because the obfuscated staging file disappeared from the latest work HEAD during concurrent branch updates. The missing staging file was rebuilt from the unchanged completed baseline; the subsequent isolated run passed all gates.

### Result

```text
BUG-850-277=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-276 — newly attached level-objective quests skipped completion evaluation

### Problem

When `QuestNewTable` attached a newly eligible quest with a level objective, it initialized level progress through the direct restore-style setter:

```text
quest.d(currentLevel)
```

That setter only assigns the stored level-progress field. It does not run the quest completion evaluator.

The normal level-progress updater instead:

```text
quest.a(currentLevel)
```

caps progress to the target and invokes the standard completion evaluator.

Because new quest attachment occurs after the existing-quest level update pass, a quest created by the level event that made it eligible could start with satisfied level progress while its completion state remained false.

### Runtime source map

- New quest attach: `QuestNewTable.a(L1PcInstance)`
- Level target: `L1QuestNew.n`
- Stored level progress: `L1QuestNew.z`
- Normal evaluating setter: `L1QuestNew.a(int)`
- Direct restore setter: `L1QuestNew.d(int)`
- Completion evaluator: `L1QuestNew.D()`
- Protocol change: **none**
- DB schema change: **none**

### Fix

Only the **new quest attach** path changes:

```text
if quest has level objective:
    evaluatingSet(currentPlayerLevel)
```

The DB restore path intentionally continues using the direct setter for serialized progress restoration.

This preserves the distinction between:

```text
new runtime progress event -> evaluate completion
database state restore      -> restore serialized state directly
```

### Modified core source

- `recovery/normalized-src-vf/l1r/ao/QuestNewTable.java`
- `recovered-src-obf/ao/az.java`

Promotion commits:

- normalized source: `5a66f0a883e0c7ee0b4a33b606e5e3ffe03df924`
- obfuscated source: `c467ebe7a15fc7c80ce83cebe776b5167ce5339a`

### Validation

Isolated validation run:

```text
GitHub Actions run = 35682433910
STATUS = PASS

BUG_850_276_CONTRACT=PASS
NEW_ATTACH_LEVEL_SETTER=evaluating
DB_RESTORE_LEVEL_SETTER=direct
COMPLETION_EVALUATOR=L1QuestNew.D
BUG_850_276_TARGETED_BEHAVIOR_RUNTIME=PASS
ATTACH_COMPLETION_TRANSITION=PASS
LEVEL_PROGRESS_CAP=PASS
BUG_850_276_EXACT_BASE_TRANSFORM=PASS
BASE_JAVAC_RC=0
STAGE_JAVAC_RC=0
BUG_850_276_TARGETED_JAVAC_REGRESSION=PASS
PROMOTION_EXACT_BLOB_MATCH=PASS
```

The staging base already contained the completed BUG-850-277 inventory synchronization repair, so this promotion preserves that prior fix and changes only the level-objective initialization setter.

### Result

```text
BUG-850-276=L2
STATUS=PASS
PROMOTED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```
