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
| BUG-850-144 | L2 | house-sale price range validation | PASS / PROMOTED |
| BUG-850-140 | L2 | house-sale authority revalidation | PASS / ALREADY COVERED |
| BUG-850-141 | L2 | house-bid state / eligibility / minimum revalidation | PASS / PROMOTED |
| BUG-850-142 | L2 | house-bid payment / persistence / refund atomicity | PASS / PROMOTED |
| BUG-850-143 | L2 | amount-dialog pending context / proximity binding | PASS / PROMOTED |
| BUG-850-142 | L2 | house bid payment / persistence / refund atomicity | PASS / PROMOTED |
| BUG-850-141 | L2 | house bid state / eligibility / minimum-price revalidation | PASS / PROMOTED |
| BUG-850-137 | L2 | inn rental amount multiplication overflow | PASS / PROMOTED |
| BUG-850-132 | L2 | town salary claim reset / payout atomicity | PASS / PROMOTED |
| BUG-850-136 | L2 | inn payment/key/lease persistence coupling | PASS / PROMOTED |
| BUG-850-140 | L2 | house sale ownership / keeper / leader revalidation | PASS / PROMOTED |
| BUG-850-145 | L2 | auction seller payout / ownership atomicity | PASS / PROMOTED |
| BUG-850-146 | L2 | auction settlement bidder-clan guard | PASS / PROMOTED |
| BUG-850-148 | L2 | auction-board unknown house-id guard | PASS / PROMOTED |
| BUG-850-149 | L2 | house tax-expiry foreclosure atomicity | PASS / PROMOTED |
| BUG-850-152 | L2 | board post fee / DB success coupling | PASS / ALREADY COVERED |
| BUG-850-154 | L2 | duel logout peer-id preservation | PASS / PROMOTED |
| BUG-850-155 | L2 | doll cleanup timer/logout idempotency | PASS / PROMOTED |
| BUG-850-162 | L2 | follower logout stale/destroyed guard | PASS / PROMOTED |
| BUG-850-165 | L2 | summon logout lifecycle cleanup | PASS / PROMOTED |
| BUG-850-166 | L2 | clan creation / Adena / membership atomicity | PASS / PROMOTED |
| BUG-850-167 | L2 | NPC AI exception / running-flag lifecycle | PASS / PROMOTED |
| BUG-850-178 | L2 | board-write local interaction / persistence-fee consistency | PASS / PROMOTED |
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
| BUG-850-269 | L2 | boss fixed-time scheduler minute/ms unit conversion | PASS / PROMOTED |
| BUG-850-266 | L2 | mob skill exact probability boundary | PASS / PROMOTED |
| BUG-850-265 | L2 | monthly town salary pre-reset contribution calculation | PASS / PROMOTED |
| BUG-850-263 | L2 | account register online-state authority binding | PASS / PROMOTED |
| BUG-850-262 | L2 | weapon proc exact probability / overflow-safe threshold | PASS / PROMOTED |
| BUG-850-258 | L2 | furniture DB/world persistence ordering | PASS / PROMOTED |
| BUG-850-257 | L2 | SoulTower empty-board bootstrap admission | PASS / ALREADY COVERED |
| BUG-850-255 | L2 | progression save missing-row self-heal via UPSERT | PASS / PROMOTED |
| BUG-850-251 | L2 | SoulTower durable rewrite / live publication consistency | PASS / PROMOTED |
| BUG-850-250 | L2 | SoulTower top-10 ranking / safe comparator | PASS / PROMOTED |

## BUG-850-144 — unchecked house-sale price flowed into auction settlement

### Problem

The original house-sale amount response accepted the client-supplied sale price without a bounded server-side range, and settlement later trusted the stored house price for payout/accounting.

### Fix

The accepted sale-price domain is now enforced at both boundaries:

```text
100000 <= price <= 2000000000
```

- `C_Amount agsell` rejects prices outside the range before mutating house state.
- `HouseTimer` revalidates the persisted/live price again before settlement.
- No payout-percentage formula was changed.

### Validation

```text
GitHub Actions run = 35714805839
STATUS = PASS

BUG_850_144_CONTRACT=PASS
BUG_850_144_TARGETED_JAVAC=PASS
BUG_850_144_TARGETED_BEHAVIOR_RUNTIME=PASS
PRICE_99999_REJECTED=PASS
PRICE_100000_ALLOWED=PASS
PRICE_2000000000_ALLOWED=PASS
PRICE_2000000001_REJECTED=PASS
```

### Promotion

```text
normalized C_Amount = b1e0702cd79373a9a6b5d6ab8c5b43c24939ea5e
obfuscated C_Amount = d1f9914ebe9b6fdbe70cb575381fd623032a27bf
normalized HouseTimer = ed020fc740d27b820087ab8cffec9992db18acd9
obfuscated HouseTimer = 702e8a44065f6f342c43465201d16998dba37643
```

### Result

```text
BUG-850-144=L2
STATUS=PASS
PROMOTED=YES
CALC_RULE_CHANGE=NO
```


## BUG-850-140 — house-sale amount response lacked mutation-time ownership authority

### Existing repair coverage

The completed normalized and obfuscated C_Amount paths already revalidate the sale authority before mutating house state:

- the player must still belong to the owning clan;
- the clan must still own the requested house;
- the player must still be the clan leader;
- the interacted NPC must still be that house's keeper;
- an already-on-sale house is rejected.

### Validation

```text
GitHub Actions run = 35716361051
STATUS = PASS

BUG_850_140_CONTRACT=PASS
BUG_850_140_TARGETED_BEHAVIOR_RUNTIME=PASS
CROSS_HOUSE_REJECTED=PASS
NON_LEADER_REJECTED=PASS
WRONG_KEEPER_REJECTED=PASS
```

### Result

```text
BUG-850-140=L2
STATUS=PASS_ALREADY_COVERED
NEW_CORE_PATCH=NO
```


## BUG-850-141 — house-bid amount response trusted stale auction eligibility

### Problem

The bid response originally relied on checks performed when the amount dialog was opened.

At mutation time it did not re-prove auction state, bidder clan eligibility, deadline or the current minimum bid.

### Fix

The normalized C_Amount path now mirrors the existing hardened authority:

- bidder clan must still exist;
- player must still be leader and level >= 15;
- clan must still own no house;
- house must still be on sale;
- deadline must still be in the future;
- NPC distance must be <= 11;
- first bid must meet current price;
- rebid must be at least current price + 1;
- amount must remain within the accepted upper bound.

### Validation

```text
GitHub Actions run = 35716252593
STATUS = PASS

BUG_850_141_CONTRACT=PASS
BUG_850_141_TARGETED_JAVAC=PASS
BUG_850_141_TARGETED_BEHAVIOR_RUNTIME=PASS
DEADLINE_EQUALITY_REJECTED=PASS
REMOTE_BID_REJECTED=PASS
MINIMUM_INCREMENT=PASS
```

### Promotion

```text
normalized = f264d9cb6322e1a021b521db349856f781992ac0
obfuscated authority = 7615d0eedb211c61b880782d9fdbaf30403ad52f
```

### Result

```text
BUG-850-141=L2
STATUS=PASS
PROMOTED=YES
```


## BUG-850-142 — house bid payment, persistence and previous-bidder refund were not atomic

### Problem

The old flow charged the new bidder, changed live house state, persisted the house, then refunded the previous bidder as separate operations.

A failure in the middle could split the durable auction state from both players' Adena state.

### Fix

The bid replacement now uses one JDBC transaction across `house` and `character_items`:

1. house row updates through an expected-state CAS over house id, sale flag, price, bidder id and deadline;
2. the new bidder's Adena stack updates/deletes through expected-count CAS;
3. the previous bidder's refund is applied in the same transaction, including offline characters;
4. transaction commits only if every durable mutation succeeds;
5. house RAM and inventory RAM/packets publish only after commit;
6. any SQL/CAS failure rolls back without publishing the new bid.

Both tables are required to use InnoDB.

### Calculation authority

The repair follows CALC-005 refund/CAS conservation rules:

```text
new bidder delta = -newBid
old bidder delta = +oldBid
escrow delta     = newBid-oldBid
sum delta        = 0

CAS success authority = affectedRows == 1
```

### Migration

```text
db/migrations/BUG-850-142_house_bid_innodb.sql
```

### Validation

```text
GitHub Actions run = 35718028574
STATUS = PASS

BUG_850_142_CONTRACT=PASS
BUG_850_142_TARGETED_JAVAC=PASS
BUG_850_142_TARGETED_BEHAVIOR_RUNTIME=PASS
ALL_FAILURE_STAGES_ROLLBACK=PASS
STALE_CAS_REJECTED=PASS
POST_COMMIT_PUBLICATION=PASS
```

### Promotion

```text
normalized C_Amount = 3360764b0cd65ed610499dd916ed4867c84c1916
obfuscated C_Amount = 14471b5738505c8f3e8eef44eef885c0a45d3dd0

normalized CharacterItemTable = c26e4fe4b271581385234da9d1decb4f58194812
normalized L1PcInventory = aa219293110d63634310edd91990619eb1d483d1
obfuscated CharacterItemTable = 4dad35f1e7b0b81a56ed2d78033f0bea12dda989
obfuscated L1PcInventory = fd8dfa2a662dc44628f2a7cda4aa6ab27295631b

migration = ea844c3759609e26535d8f7b11bdcba3dcc64267
```

### Result

```text
BUG-850-142=L2
STATUS=PASS
PROMOTED=YES
DB_MIGRATION_REQUIRED=YES
```


## BUG-850-143 — amount responses were not bound to a live server-side dialog context

### Fix

House bid/sale amount dialogs now bind a short-lived one-shot context on the player:

```text
{ npcObjId, mode, expiry=15s }
```

`C_NpcAction` sets the context when the authorized dialog is opened.

`C_Amount` consumes it before mutation and additionally requires the NPC to remain within the allowed interaction range.

Modes:

```text
1 = auction bid
2 = house sale
```

The context is cleared on successful consume, mismatch, or expiry.

### Validation

```text
RUN=35717948815
STATUS=PASS
HOUSE_CONTEXT_SOURCE_CONTRACT=PASS
HOUSE_CONTEXT_RUNTIME=PASS
```

### Promotion

```text
normalized C_Amount = 41d40bf66f1996291506e926ee3cbf198e4f6411
obfuscated C_Amount = 66d5f1bc689e4c50c80ee1705a945e1c143de333
```

### Result

```text
BUG-850-143=L2
STATUS=PASS
PROMOTED=YES
```


## BUG-850-142 — house bid payment, house persistence and previous-bid refund were non-atomic

### Fix

The bid path now uses one transaction over:

- `house`
- `character_items`

The transaction:

1. validates the new bidder's Adena stack;
2. CAS-updates the house row against the expected current price, bidder id and deadline;
3. CAS-updates/deletes the new bidder's Adena stack;
4. refunds the previous bidder in the same transaction, online or offline;
5. commits all durable mutations together;
6. publishes RAM/inventory changes only after commit.

Both required tables must be InnoDB.

### Migration

```text
db/migrations/BUG-850-142_house_bid_innodb.sql
```

### Validation

```text
RUN=35718028574
STATUS=PASS
SOURCE_CONTRACT=PASS
DEPENDENCY_CLOSURE_JAVAC=PASS
TARGETED_BEHAVIOR_RUNTIME=PASS
```

### Result

```text
BUG-850-142=L2
STATUS=PASS
PROMOTED=YES
```


## BUG-850-141 — house bid response trusted stale auction state and price assumptions

### Fix

Before charging or committing a bid, the amount-response handler now revalidates:

- bidder clan exists;
- player is clan leader;
- level requirement;
- clan owns no house;
- target house exists and is on sale;
- deadline is still active;
- NPC remains in interaction range;
- submitted amount is positive and within configured bounds;
- bid meets the current minimum/current-price requirement.

### Validation

```text
RUN=35716252593
STATUS=PASS
SOURCE_CONTRACT=PASS
TARGETED_JAVAC=PASS
TARGETED_BEHAVIOR_RUNTIME=PASS
```

### Result

```text
BUG-850-141=L2
STATUS=PASS
PROMOTED=YES
```


## BUG-850-137 — inn rental amount multiplication could overflow before payment validation

### Fix

The inn rental charge now uses exact widened arithmetic:

```text
chargeLong = 300L * amount
```

The request is rejected when:

```text
amount <= 0
chargeLong > INT_MAX
```

Only after the bound is proven is the value narrowed to `int`.

### Calculation authority

```text
MAX_SAFE_AMOUNT = 7_158_278
FIRST_REJECTED_AMOUNT = 7_158_279
```

### Validation

```text
RUN=35720707260
STATUS=PASS
BUG_850_137_CONTRACT=PASS
BUG_850_137_TARGETED_BEHAVIOR_RUNTIME=PASS
```

### Promotion

```text
normalized C_Amount = 0ad36cd8b8a566a25772a79457bcf3791e03a5bc
obfuscated existing economic guard = 7615d0eedb211c61b880782d9fdbaf30403ad52f
```

### Result

```text
BUG-850-137=L2
STATUS=PASS
PROMOTED=YES
```


## BUG-850-132 — town salary claim returned Pay even when durable reset failed

### Problem

The original claim path read `Pay`, attempted a separate reset to zero, swallowed SQL failures, and returned the previously read value regardless of whether the reset succeeded.

Concurrent or failed claims could therefore duplicate salary.

### Fix

The normalized claim path now mirrors the completed transactional authority:

- open an explicit transaction;
- `SELECT Pay ... FOR UPDATE`;
- reject missing/zero salary;
- CAS reset with `WHERE objid=? AND Pay=?`;
- require `affectedRows == 1`;
- commit before returning the salary;
- rollback and return `0` on every failure.

### Storage prerequisite

```text
db/migrations/BUG-850-132_characters_innodb.sql
```

The `characters` table is explicitly required to use InnoDB so row locking and rollback are authoritative.

### Validation

```text
RUN=35722329957
STATUS=PASS
BUG_850_132_CONTRACT=PASS
BUG_850_132_TARGETED_JAVAC_REGRESSION=PASS
BUG_850_132_TARGETED_BEHAVIOR_RUNTIME=PASS
CHARACTERS_INNODB_MIGRATION=PASS
```

### Promotion

```text
normalized HomeTownTimer = b9d5553cbd65f92ba0348982b13038a44ece4e36
migration = 90c03e09822d94d390f7b5b279408987edb189e1
obfuscated existing authority = e3e9684c92a57cfc4433e6a1994ef977f30cbfef
```

### Result

```text
BUG-850-132=L2
STATUS=PASS
PROMOTED=YES
DB_MIGRATION_REQUIRED=YES
```


## BUG-850-136 — inn payment, key delivery and lease persistence were not one success contract

### Problem

The original flow could charge Adena and publish a key before confirming that the lease row had been durably inserted.

### Fix

The normalized path now mirrors the completed obfuscated authority:

1. validate amount/cost and available Adena;
2. validate inn availability;
3. create a key template and verify inventory capacity;
4. remove Adena;
5. publish the key;
6. persist the lease;
7. if lease persistence fails, remove the key and refund the full Adena charge;
8. send the success message only after durable lease creation.

`InnTable.a(keyid,count,roomid)` now returns a boolean persistence result and publishes its RAM cache only after the DB INSERT succeeds.

### Validation

```text
RUN=35722261060
STATUS=PASS
BUG_850_136_CONTRACT=PASS
BUG_850_136_TARGETED_JAVAC_REGRESSION=PASS
BUG_850_136_TARGETED_BEHAVIOR_RUNTIME=PASS
LEASE_FAILURE_COMPENSATED=PASS
SUCCESS_AFTER_DURABLE_LEASE=PASS
```

### Promotion

```text
normalized InnTable = 819f5ee1773a178db295f71129b475154776041e
normalized C_Amount = 0ad36cd8b8a566a25772a79457bcf3791e03a5bc
obfuscated existing lease authority = a0ffdc3b0c9ae06157479a494395020488323bde
```

### Result

```text
BUG-850-136=L2
STATUS=PASS
PROMOTED=YES
```


## BUG-850-140 — house sale response trusted first-stage ownership authority

### Fix

At the state-changing amount response, the server now revalidates:

- player clan;
- clan ownership of the target house;
- clan-leader identity;
- house keeper/NPC identity;
- house not already on sale;
- server-side amount-dialog context and proximity through BUG-850-143.

### Validation

```text
RUN=35719307753
STATUS=PASS
SOURCE_CONTRACT=PASS
TARGETED_RUNTIME_CONTRACT=PASS
TARGETED_BEHAVIOR_RUNTIME=PASS
```

### Result

```text
BUG-850-140=L2
STATUS=PASS
PROMOTED=YES
```


## BUG-850-145 — seller payout was not bound to durable ownership transfer

### Problem

The original settlement path paid the old owner before the old-clan, bidder-clan and house ownership mutations were durably committed.

A later persistence failure could therefore leave the seller paid while durable ownership remained partially old/partially new.

### Existing completed obfuscated authority

The completed obfuscated repair already contains:

- `06b3bd47dea027f5f0acc331faed7cf533625658` — surface house persistence failures.
- `08b1760c5bbef690dadd602a1fbf7752ef0d7879` — rollback-safe house settlement and foreclosure.

### Fix

The normalized settlement path now mirrors that authority:

- seller payout must succeed before ownership mutation proceeds;
- old clan, bidder clan and house state are staged in RAM;
- old-clan `hashouse`, bidder-clan `hashouse` and the house row commit through one JDBC transaction;
- if persistence fails, RAM ownership/sale state is restored;
- seller payout is compensated/reclaimed on rollback;
- settlement-success packets are sent only after durable commit.

`HouseTable.a(L1House)` now returns a boolean persistence result so callers can fail closed instead of swallowing SQL failure.

### Validation

```text
GitHub Actions run = 35714152035
STATUS = PASS

BUG_850_145_CONTRACT=PASS
BUG_850_145_TARGETED_JAVAC=PASS
BUG_850_145_TARGETED_BEHAVIOR_RUNTIME=PASS
PAYOUT_FAILURE_ABORTS=PASS
PERSIST_FAILURE_RESTORES_OWNERSHIP=PASS
PERSIST_FAILURE_COMPENSATES_PAYOUT=PASS
SUCCESS_MESSAGE_POST_COMMIT=PASS
```

### Promotion

```text
normalized HouseTimer = 6bbf97194fe6dd80a0b7f18527f8f0b9fd51e7f0
normalized HouseTable = 26dcd1df3896f890b8a0a907f89f6afbc02cbf75
obfuscated HouseTimer authority = 08b1760c5bbef690dadd602a1fbf7752ef0d7879
obfuscated HouseTable authority = 06b3bd47dea027f5f0acc331faed7cf533625658
```

### CALC-005 exact payout authority

Calculator proof:

```text
STATUS=PASS
FLOAT_0_9_SAFE_OVER_DOMAIN=YES
EXACT_SELLER_FORM=floor(((long)price)*90L/100L)
MAX_PRICE=2000000000
MAX_SELLER_PAYOUT=1800000000
ConfigDays*24*60*60*1000L max safe ConfigDays=24855
```

Production was still changed to explicit integer arithmetic so intent and overflow behavior are visible:

```text
seller=(int)(((long)price*90L)/100L)
deadline=((long)Config.an)*24L*60L*60L*1000L
```

Validation:

```text
GitHub Actions run = 35719258360
CALC_005_EXACT_PAYOUT=PASS
CALC_005_SAFE_DEADLINE_MULTIPLY=PASS
```

Promotion:

```text
normalized = 94e49a7ec89be7ad7f67166f6e3126ca1b059c8a
obfuscated = cfb770220f6de1866c6fa9857ed7754764e2c3a9
```


### Result

```text
BUG-850-145=L2
STATUS=PASS
PROMOTED=YES
OBF_EXISTING_REPAIR=PRESERVED
```


## BUG-850-146 — auction settlement could close without assigning the house to any bidder clan

### Problem

The original settlement path treated bidder-clan assignment as a best-effort loop. If no eligible clan matched the stored bidder leader id, assignment silently did nothing while the remaining settlement path could still clear the seller and close the auction.

### Existing completed obfuscated authority

The completed obfuscated source already contains:

- `08b1760c5bbef690dadd602a1fbf7752ef0d7879` — bidder-clan validation plus rollback-safe settlement/foreclosure.

### Fix

The normalized settlement path now mirrors the bidder-clan guard:

- resolve the bidder clan before normal settlement;
- require the clan leader id to match and the clan to currently own no house;
- when the bidder clan is unavailable, refund the stored bid and clear the stale bidder fields instead of completing the sale;
- if house persistence fails, restore bidder state and reclaim the refund;
- never enter the normal close/ownership-transfer path without a valid bidder clan.

### Validation

```text
GitHub Actions run = 35713481799
STATUS = PASS

BUG_850_146_CONTRACT=PASS
BUG_850_146_TARGETED_JAVAC=PASS
BUG_850_146_TARGETED_BEHAVIOR_RUNTIME=PASS
MISSING_BIDDER_CLAN_BLOCKED=PASS
PERSIST_FAILURE_RESTORED=PASS
VALID_SETTLEMENT_ALLOWED=PASS
```

### Promotion

```text
normalized full settlement = 6bbf97194fe6dd80a0b7f18527f8f0b9fd51e7f0
obfuscated existing repair = 08b1760c5bbef690dadd602a1fbf7752ef0d7879
```

### Result

```text
BUG-850-146=L2
STATUS=PASS
PROMOTED=YES
OBF_EXISTING_REPAIR=PRESERVED
```


## BUG-850-148 — auction-board house selection dereferenced unknown house ids

### Problem

The auction-board read packet parsed a house id and immediately dereferenced the result of `HouseTable.get(houseId)`.

A stale or invalid id could therefore abort packet construction with a null dereference.

### Fix

Both normalized and obfuscated packet constructors now:

- resolve the house id;
- return a zero-argument `agsel` response if the house is absent;
- also fail closed when the deadline is null;
- serialize the normal 9-argument response only for a valid house.

### Validation

```text
GitHub Actions run = 35713093198
STATUS = PASS

BUG_850_148_CONTRACT=PASS
BUG_850_148_TARGETED_JAVAC=PASS
BUG_850_148_TARGETED_BEHAVIOR_RUNTIME=PASS
UNKNOWN_HOUSE_SAFE_RESPONSE=PASS
NULL_DEADLINE_SAFE_RESPONSE=PASS
VALID_HOUSE_SERIALIZED=PASS
```

### Promotion

```text
normalized = fcd71e084a3a49f0fabf129d1d1eb5fdc612a069
obfuscated = 9c9d6f52c22238fb3f912fcbf7c476c4d0e95db1
```

### Result

```text
BUG-850-148=L2
STATUS=PASS
PROMOTED=YES
```


## BUG-850-149 — tax-expiry foreclosure mutated clan ownership and house state independently

### Problem

The original foreclosure path cleared the owning clan's `hashouse` state and independently rewrote the `house` row into a fresh auction state.

A failure in only one persistence operation could leave contradictory durable ownership.

### Existing completed obfuscated authority

The completed obfuscated source already contains:

- `08b1760c5bbef690dadd602a1fbf7752ef0d7879` — rollback-safe house settlement and foreclosure.

### Fix

The normalized foreclosure path now mirrors the same transaction boundary for BUG-850-149:

- snapshot clan/house RAM state;
- clear the clan's house ownership;
- rewrite the house into fresh sale state;
- persist `clan_data` and `house` through one JDBC transaction;
- require one-row updates where applicable;
- commit both durable mutations together;
- on SQL failure rollback and restore the RAM snapshot.

Existing work-branch auction price guards are preserved and were not overwritten.

### Migration

```text
db/migrations/BUG-850-149_house_foreclosure_innodb.sql
```

Both `clan_data` and `house` must use InnoDB for transaction rollback to be authoritative.

### Validation

```text
GitHub Actions run = 35712675633
STATUS = PASS

BUG_850_149_CONTRACT=PASS
BUG_850_149_TARGETED_JAVAC_REGRESSION=PASS
BUG_850_149_TARGETED_BEHAVIOR_RUNTIME=PASS
CLAN_FAILURE_ROLLBACK=PASS
HOUSE_FAILURE_ROLLBACK=PASS
```

### Promotion

```text
normalized foreclosure = 9bfc4bb8ee99d365e8bd8c905d6f6632997b61a9
migration = 5cad39351b76e571e760a5a7c3f731f79ae448b5
obfuscated existing repair = 08b1760c5bbef690dadd602a1fbf7752ef0d7879
```

### Result

```text
BUG-850-149=L2
STATUS=PASS
PROMOTED=YES
DB_MIGRATION_REQUIRED=YES
OBF_EXISTING_REPAIR=PRESERVED
```


## BUG-850-152 — board post fee and DB creation were not one success contract

### Existing repair coverage

This finding is already covered by the completed board-write repair chain that was preserved during BUG-850-178 promotion.

The completed handler now:

1. requires successful removal of item `40308 x300` before persistence;
2. aborts if the fee cannot be removed;
3. calls `L1BoardTopic.a(...)`;
4. if persistence returns `null`, refunds `40308 x300`.

Therefore the original two split outcomes are closed:

- DB success with unpaid fee;
- DB failure with permanently consumed fee.

### Existing authority

```text
0cb0fa4b89755fc699bcf7545a1061bfa91f06b9
b824c6b84d354751da32cf00adb64c03b4915615
8bae99e0f7aa18c86dd9166ce1b0a8ab9f94ff53
```

### Validation

The isolated BUG-850-178 validation already exercised the same economic contract:

```text
GitHub Actions run = 35703276792
BOARD_FEE_GATE=PASS
BOARD_DB_FAILURE_REFUND=PASS
```

### Result

```text
BUG-850-152=L2
STATUS=PASS
PROMOTED=ALREADY_COVERED
NEW_CORE_PATCH=NO
```


## BUG-850-154 — logout cleared duel target id before using it to clear the peer

### Problem

The recovered logout path cleared its own duel target id and then performed the world lookup through the now-zero id.

The disconnecting player left duel state, but the peer could keep a stale duel target and miss the duel-end packet.

### Existing completed obfuscated authority

The completed obfuscated source already contains:

- `8af1d2a985a4eacf007db495efee87861171f602` — preserve duel peer id during logout cleanup.

### Fix

The normalized `L1PcInstance.java` now mirrors that behavior:

- save the current duel peer id;
- clear the disconnecting player's duel state;
- resolve the peer using the saved id;
- clear the peer duel state and send the duel-end packet.

### Validation

```text
GitHub Actions run = 35711861758
STATUS = PASS

BUG_850_154_CONTRACT=PASS
BUG_850_154_CANDIDATE_BUILD=PASS
BUG_850_154_SYNTHETIC_PROMOTION_JAVAC_REGRESSION=PASS
BUG_850_154_TARGETED_BEHAVIOR_RUNTIME=PASS
SELF_DUEL_CLEAR=PASS
PEER_DUEL_CLEAR=PASS
PEER_DUEL_END_PACKET=PASS
```

### Promotion

```text
normalized parity = 31a14a5a49d8acdd309d8491ec4a90d115c54377
obfuscated existing repair = 8af1d2a985a4eacf007db495efee87861171f602
```

### Result

```text
BUG-850-154=L2
STATUS=PASS
PROMOTED=YES
OBF_EXISTING_REPAIR=PRESERVED
NORMALIZED_PARITY=RESTORED
```


## BUG-850-155 — doll cleanup side effects were not single-entry across timer/logout races

### Problem

The doll cleanup method removed player bonuses/effects before delegating to inherited NPC deletion.

The method itself was not synchronized and did not check the destroyed state before those doll-specific side effects, so timer expiry and logout cleanup could both enter before inherited deletion marked the doll destroyed.

### Existing completed obfuscated authority

The completed obfuscated source already contains:

- `5de0712bde13fed77ac71325bf6577696538ee1b` — make doll cleanup single-entry and idempotent.

### Fix

The normalized `L1DollInstance.e()` now mirrors that completed behavior:

- method is `synchronized`;
- return immediately if the doll is already destroyed;
- doll-specific side effects execute only once before inherited deletion.

### Validation

```text
GitHub Actions run = 35711211164
STATUS = PASS

BUG_850_155_CONTRACT=PASS
BUG_850_155_TARGETED_JAVAC_REGRESSION=PASS
BUG_850_155_TARGETED_BEHAVIOR_RUNTIME=PASS
SINGLE_ENTRY_SIDE_EFFECT=PASS
DESTROYED_REENTRY_SKIPPED=PASS
```

### Promotion

```text
normalized parity = 703e0034e83eedda15b9b3213ced3ff69c66321a
obfuscated existing repair = 5de0712bde13fed77ac71325bf6577696538ee1b
```

### Result

```text
BUG-850-155=L2
STATUS=PASS
PROMOTED=YES
OBF_EXISTING_REPAIR=PRESERVED
NORMALIZED_PARITY=RESTORED
```


## BUG-850-162 — follower logout cleanup could reprocess stale/destroyed followers

### Problem

The recovered logout path iterated follower snapshots and unconditionally executed follower relocation/deletion cleanup.

If a follower entry was null or already destroyed by another lifecycle path, logout could reprocess stale state and abort later cleanup through an exception.

### Existing completed obfuscated authority

The completed obfuscated source already contains the guard in:

- `cc1a753c736ee6c965c90bd659d37b01fc57ee24` — complete summon and follower cleanup on logout.

### Fix

The normalized `L1PcInstance.java` now mirrors that behavior:

- skip null follower entries;
- skip already-destroyed followers;
- run the existing follower relocation/deletion path only for live followers.

### Validation

```text
GitHub Actions run = 35710977451
STATUS = PASS

BUG_850_162_CONTRACT=PASS
BUG_850_162_CANDIDATE_BUILD=PASS
BUG_850_162_SYNTHETIC_PROMOTION_JAVAC_REGRESSION=PASS
BUG_850_162_TARGETED_BEHAVIOR_RUNTIME=PASS
NULL_FOLLOWER_SKIPPED=PASS
DESTROYED_FOLLOWER_SKIPPED=PASS
LIVE_FOLLOWER_CLEANED=PASS
```

### Promotion

```text
normalized parity = eee89a35258f65dd511665618571c9f29ae6c138
obfuscated existing repair = cc1a753c736ee6c965c90bd659d37b01fc57ee24
```

### Result

```text
BUG-850-162=L2
STATUS=PASS
PROMOTED=YES
OBF_EXISTING_REPAIR=PRESERVED
NORMALIZED_PARITY=RESTORED
```


## BUG-850-165 — logout hid summons without releasing their server lifecycle

### Problem

The recovered logout path notified nearby clients that a summon disappeared, but left the summon object alive in server state.

This could retain owner/world references after the player had logged out.

### Existing completed obfuscated authority

The completed obfuscated source already contains:

- `cc1a753c736ee6c965c90bd659d37b01fc57ee24` — guard already-destroyed summons and call the existing summon release path during logout.

### Fix

The normalized `L1PcInstance.java` now mirrors that completed behavior:

- skip already-destroyed summons;
- send removal packets to nearby players;
- call the summon's existing `h()` release/dismiss path.

### Validation

```text
GitHub Actions run = 35710509931
STATUS = PASS

BUG_850_165_CONTRACT=PASS
BUG_850_165_SYNTHETIC_PROMOTION_JAVAC_REGRESSION=PASS
BUG_850_165_TARGETED_BEHAVIOR_RUNTIME=PASS
LIVE_SUMMON_RELEASED=PASS
DESTROYED_SUMMON_SKIPPED=PASS
```

### Promotion

```text
normalized parity = 563ebacec28d023de36d149ab40ea5f80578c217
obfuscated existing repair = cc1a753c736ee6c965c90bd659d37b01fc57ee24
```

### Result

```text
BUG-850-165=L2
STATUS=PASS
PROMOTED=YES
OBF_EXISTING_REPAIR=PRESERVED
NORMALIZED_PARITY=RESTORED
```


## BUG-850-166 — clan creation was not transactionally bound to the 30,000 Adena fee

### Problem

The original create-clan path performed durable and live mutations in separate steps:

- insert `clan_data`;
- publish clan state to the player;
- separately insert/update clan membership;
- separately consume item `40308 x30000` (Adena).

A failure between those operations could leave a clan created without the fee, a fee consumed without complete clan persistence, or partially persisted membership/player state.

### Fix

Clan creation now uses one JDBC transaction over:

- `clan_data`
- `clan_members`
- `characters`
- `character_items`

The transaction:

1. validates the player is clanless and has at least 30,000 Adena;
2. requires all four durable tables to be InnoDB;
3. inserts `clan_data`;
4. inserts the leader into `clan_members`;
5. CAS-updates `characters.ClanID/Clanname/ClanRank` only while `ClanID=0`;
6. CAS-updates or deletes the Adena stack using the previously validated count;
7. commits all durable state together;
8. only after commit publishes clan/RAM state and the committed inventory change.

Any durable failure rolls back the entire operation.

### Migration

Apply once to existing databases:

```text
db/migrations/BUG-850-166_clan_creation_innodb.sql
```

### Validation

```text
GitHub Actions run = 35705902092
STATUS = PASS

BUG_850_166_CONTRACT=PASS
BUG_850_166_TARGETED_JAVAC=PASS
BUG_850_166_TARGETED_BEHAVIOR_RUNTIME=PASS
ROLLBACK_ALL_STAGES=PASS
POST_COMMIT_RAM_PUBLICATION=PASS
```

### Promotion

```text
normalized handler = aeb601dc3b3b2d276bc54f036921c3b8d6dce91f
obfuscated handler = d9c7de484f67ce56fa4ec2e6196ba288fdf9b106
normalized transaction = 86cd593f2b9e310e1d0dbfd34e76baea32540424
obfuscated transaction = 6549adbbbf853df61e07650480f058d7aed1e810
migration = d9113f3b92865aa9654bbd90d95d8239428e0e46
```

### Result

```text
BUG-850-166=L2
STATUS=PASS
PROMOTED=YES
DB_MIGRATION_REQUIRED=YES
RESTART_REQUIRED=YES after building/deploying the repaired core
```


## BUG-850-167 — NPC AI exception could leave the running flag stuck

### Problem

The NPC AI task sets the per-NPC AI-running flag before scheduling. Normal terminal cleanup clears it, but the exception path only logged and returned.

### Existing completed obfuscated authority

The completed obfuscated source already contained the validated repair:

- `25e5bd1f168df36a4a1492784d45ad6eb2a1284a` — clear NPC AI running flag after task exceptions

The normalized source had not received the same repair.

### Fix

The normalized `L1NpcInstance.java` now mirrors the completed obfuscated behavior by clearing the AI-running flag in the AI task's `catch (Exception)` path.

### Validation

```text
GitHub Actions run = 35703525324
STATUS = PASS
BUG_850_167_CONTRACT=PASS
BUG_850_167_TARGETED_JAVAC_REGRESSION=PASS
BUG_850_167_TARGETED_BEHAVIOR_RUNTIME=PASS
```

Promotion commit:

- normalized parity: `f067e0d0aa6000c58ce44f47a6985763b97fe6c2`

### Result

```text
BUG-850-167=L2
STATUS=PASS
PROMOTED=YES
OBF_EXISTING_REPAIR=PRESERVED
NORMALIZED_PARITY=RESTORED
```


## BUG-850-178 — board writes were not bound to a nearby board instance

### Problem

The recovered board-write handler accepted any non-null world object id as sufficient interaction context, then persisted a board post and consumed 300 paper.

A separate earlier repair on the completed obfuscated source had already fixed two related contracts:

- title/content length validation plus fee/persistence rollback;
- binding the write to a nearby `L1BoardInstance`.

The normalized source had not received those completed fixes, so normalized/obfuscated parity was broken.

### Root cause

The original recovered path treated global object existence as authorization for a state-changing board write.

The completed obfuscated repair chain established the stronger contract:

```text
player exists
AND target is L1BoardInstance
AND target location distance <= 11
AND title/content limits pass
AND 300 paper is successfully consumed
AND DB insert succeeds
```

If the DB insert fails after paper consumption, the 300 paper is refunded.

### Existing completed obfuscated authority

The obfuscated repair was already present in:

- `0cb0fa4b89755fc699bcf7545a1061bfa91f06b9` — bind board post limits and fee to persistence success
- `b824c6b84d354751da32cf00adb64c03b4915615` — bind board writes to a nearby board instance

This promotion does not replace or weaken those repairs.

### Fix

The normalized `C_BoardWrite.java` now mirrors the existing completed obfuscated behavior:

- require non-null player;
- require `L1BoardInstance`;
- reject location distance greater than 11;
- enforce title <= 16 and content <= 1000;
- require successful removal of item `40308 x300`;
- if `L1BoardTopic.a(...)` returns null, refund `40308 x300`.

### Modified core source

- normalized: `recovery/normalized-src-vf/l1r/aj/C_BoardWrite.java`
- obfuscated: existing validated `recovered-src-obf/aj/o.java` preserved unchanged

Promotion commit:

- normalized parity: `8bae99e0f7aa18c86dd9166ce1b0a8ab9f94ff53`

### Validation

Isolated validation:

```text
GitHub Actions run = 35703276792
STATUS = PASS

BUG_850_178_CONTRACT=PASS
BUG_850_178_TARGETED_JAVAC=PASS
BUG_850_178_TARGETED_BEHAVIOR_RUNTIME=PASS
BOARD_TYPE_AUTHORITY=PASS
BOARD_NEARBY_BOUNDARY=PASS
BOARD_INPUT_LIMITS=PASS
BOARD_FEE_GATE=PASS
BOARD_DB_FAILURE_REFUND=PASS
```

The promoted normalized blob matches the validated work-branch normalized blob.

### Result

```text
BUG-850-178=L2
STATUS=PASS
PROMOTED=YES
OBF_EXISTING_REPAIR=PRESERVED
NORMALIZED_PARITY=RESTORED
RESTART_REQUIRED=YES after building/deploying the repaired core
```


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


## L3 修復收尾狀態 — 2026-09-22

### 狀態

```text
L3_REPAIR_SCOPE=54
REPAIRED_AND_VALIDATED=54
BATCH1_7=PASS
FINAL_PROMOTION_VALIDATION=PASS
FINAL_RUN=35684550488
PR=#29
PROMOTION_TO_COMPLETED=BLOCKED_BY_MERGE_CONFLICT
SOURCE_OVERWRITE=NO
```

### 問題與解法摘要

本輪 L3 共 54 顆，主要涵蓋：

- 封包/指令長度、索引與 EOF 防呆：在任何陣列、清單、固定 buffer 與 token 使用前 fail closed。
- Shop / PrivateShop / Quest / Achievement：以 server-side authoritative object/list 為準，補 null/type/range guard。
- JDBC / Character delete / Mail / Buddy：縮小 SQL resource lifetime、交易化需要原子性的流程，DB 成功後才修改 RAM。
- Bookmark / Clan：重名直接終止；刪除/重排與 clan 歷史清理使用 transaction，rollback 保護。
- Board / Pet / Party / Heading：補物件型別、同地圖/距離、packet index 與狀態檢查。
- Ranking / Spawn：修正 class routing / top-50 截斷；動態 spawn INSERT 取得 generated key 後同步 live index。
- Polymorph：保留實際選中的 morph rule identity，後續武防具驗證不再只靠可能歧義的 polyid。
- Ghost exit：補保存 x/y/map/heading、ExitGhost 回傳與 teleport finalize 後才清 ghost 狀態。

### 驗證

- Batch1 PASS — run `35671637284`
- Batch2 PASS — run `35671890739`
- Batch3 PASS — run `35673217261`
- Batch5 PASS — run `35674825429`
- Batch6 PASS — run `35678192602`
- Batch7 PASS — run `35678060134`
- completed-base promotion contract PASS — run `35684550488`

### 為什麼尚未寫入 completed 核心

驗證期間 `completed/l1jtw85-core-fixes` 又前進了 12 個 commit；PR #29 與最新 completed 存在 merge conflict。為避免覆蓋其他 lane 已完成修補，本輪停止在已驗證 promotion branch：

`promote/l3-validated-20260922`

PR：

`https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/pull/29`

詳細報告目前在該 promotion branch：

`recovery/L3_CORE_REPAIR_COMPLETION_20260922.md`

下一次接手應從 **最新 completed HEAD** 解 PR #29 衝突；不得用 force update 或整檔覆蓋已完成修補。


## 2026-09-23 — validated L3 promotion integrated into completed

### Scope

```text
SOURCE=promote/l3-validated-20260922
TARGET=completed/l1jtw85-core-fixes
PROMOTION_BASE=6824ebc9
FINAL_CORE=74dcec10dfbf79fd0d3033d977cc3bc456522f7d
CI_READ_ONLY_HEAD=63be6861078565807fbcf08368d2808cd0feaa9d
STATUS=PASS
```

The historical validated L3 repair set was merged into the completed branch without replacing later L2/transaction hardening. The promotion contained 85 changed files; 35 completed-side files had newer changes and 14 paths required overlap reconciliation.

### Reconciled overlap repairs

- `C_ShopWorld`: preserved L3 null/type guards together with the newer local-distance authority.
- `L1PcInventory`: restored BUG-850-017 empty-list guard, BUG-850-278 quest-count clamp, and BUG-850-271 polymorph identity checks while preserving fail-closed persistence/publication hardening.
- `C_Result`: restored BUG-850-038 private-shop sell/buy index bounds guards.
- `ClanTable`: restored BUG-850-096 transactional clan deletion including `clan_warehouse_history` cleanup and rollback.
- `C_Amount`: restored BUG-850-138 NPC type guard without disturbing later auction/context transaction fixes.
- `C_BoardWrite`: core was already stronger than the old literal contract; Batch5 validator was corrected to accept equivalent split title/content guards.
- `L1PcInstance`: restored BUG-850-271 active polymorph-rule identity state and BUG-850-285 ghost return state/API in normalized and obfuscated sources.

### Validation

```text
FINAL_WORKFLOW_RUN=35877469923
L3_BATCH1_CONTRACT=PASS
L3_BATCH2_CONTRACT=PASS
L3_BATCH3_CONTRACT=PASS
L3_BATCH4_CONTRACT=PASS
L3_BATCH5_CONTRACT=PASS
L3_BATCH6_CONTRACT=PASS
L3_BATCH7_CONTRACT=PASS
CI_PERMISSION=contents:read
NEW_WORK_BRANCH=NO
```

Result: the completed branch now contains the validated Batch1–7 L3 repairs plus the newer completed L2/transaction fixes, with the validation workflow returned to read-only mode.

## BUG-850-269 — fixed-time boss scheduler mixed minutes with milliseconds

### Problem

`L1SpawnBoss.c(schedule)` accumulates fixed-time delays in milliseconds, but the same-hour future branch added the raw minute delta directly. For example, `21:00 -> 21:05` produced `5` instead of `300000` milliseconds.

### Fix

The normalized and obfuscated runtime paths now convert the same-hour minute delta with widened millisecond arithmetic:

```text
minute_delta * 60L * 1000L
```

No config, DB schema, spawn probability, weekly scheduling, or other boss logic changed.

### Modified core

- `recovery/normalized-src-vf/l1r/aq/L1SpawnBoss.java`
- `recovered-src-obf/aq/ah.java`

### Validation

```text
GitHub Actions run = 35888235267
STATUS = PASS
BUG_850_269_CONTRACT=PASS
SAME_HOUR_MINUTE_TO_MS=PASS
BUG_850_269_NO_NEW_JAVAC_REGRESSION=PASS
MINUTE_TO_MS_GATE=PASS
SAME_HOUR_GATE=PASS
MIDNIGHT_WRAP_GATE=PASS
EQUALITY_NEXT_DAY_GATE=PASS
BUG_850_269_TARGETED_BEHAVIOR_RUNTIME=PASS
FIXED_TIME_DELAY_UNITS=PASS
```

Validation evidence: `recovery/BUG-850-269_VALIDATION_20260923.md`.

### Result

```text
BUG-850-269=L2
STATUS=PASS
PROMOTED=YES
CALC_RULE_CHANGE=NO
```

## BUG-850-266 — mob skill probability boundary used an inclusive 0..99 threshold

### Problem

`Random.a(100)` delegates to `ThreadLocalRandom.nextInt(100)`, so the roll domain is exactly `0..99`. The old inclusive comparison accepted one extra roll value: normalized used `roll <= probability`, while the equivalent obfuscated continue gate used `roll > probability`.

### Fix

- normalized `MobSkillsTable`: `Random.a(100) < probability`
- obfuscated `ao/ar`: reject when `i.a(100) >= probability`

This makes P accept exactly P values out of 100 for P in `0..100`.

### Validation

```text
WORK_CI=35724321782
COMPLETED_CI=35894483612
BUG_850_266_CONTRACT=PASS
BUG_850_266_TARGETED_JAVAC=PASS
BUG_850_266_TARGETED_BEHAVIOR_RUNTIME=PASS
EXACT_PERCENT_BOUNDARY=PASS
```

### Promotion

```text
OBFUSCATED_COMMIT=3e320a862305c514bb80740ea0e1af530d140b31
NORMALIZED_COMMIT=11f09424ace39b562e454a91b0978deacdd2faa1
```

### Result

```text
BUG-850-266=L2
STATUS=PASS
PROMOTED=YES
NEW_CORE_BRANCH=NO
```

## BUG-850-265 — monthly town salary cleared Contribution before calculating Pay

### Problem

The monthly town settlement used one MySQL `UPDATE` whose assignment order cleared `Contribution` before evaluating `Pay = Contribution * ?`. That could calculate salary from the already-cleared contribution instead of the player's pre-reset contribution.

### Fix

Both normalized and obfuscated `HomeTownTimer` now assign:

```text
Pay = Contribution * ?, Contribution = 0
```

so `Pay` is computed first from the pre-reset contribution and the contribution is then cleared.

### Validation

```text
WORK_CI=35724229720
COMPLETED_CI=35894884374
BUG_850_265_CONTRACT=PASS
BUG_850_265_NO_NEW_JAVAC_REGRESSION=PASS
BUG_850_265_TARGETED_BEHAVIOR_RUNTIME=PASS
MONTHLY_SALARY_USES_PRE_RESET_CONTRIBUTION=PASS
```

### Promotion

```text
NORMALIZED_COMMIT=b4980d15f9f5160ef56517524c71e9cfa5b38969
OBFUSCATED_COMMIT=0b9bb323df75c52599911073897f7b547d342c4f
```

### Result

```text
BUG-850-265=L2
STATUS=PASS
PROMOTED=YES
NEW_CORE_BRANCH=NO
```

## BUG-850-263 — account register persisted online state from an unbound client account

### Problem

The register path receives an already authenticated `L1Account` plus a `ClientThread`, but persisted online state by rereading the account from the client before the outer bind order guaranteed that client account reference existed. This could fail to persist the supplied authenticated account as online.

### Fix

Both normalized and obfuscated register paths now pass the authenticated account parameter directly to the existing online-state persistence method. No login policy, account schema, capacity rule, or duplicate-login rule changed.

### Validation

```text
WORK_CI=35724086529
COMPLETED_CI=35896878279
SOURCE_COMMIT=c40635be088d40a9d75f08be265141a5ddfb9832
BUG_850_263_CONTRACT=PASS
REGISTER_ACCOUNT_AUTHORITY=PASS
BUG_850_263_TARGETED_JAVAC=PASS
BUG_850_263_TARGETED_BEHAVIOR_RUNTIME=PASS
UNBOUND_CLIENT_ONLINE_STATE_FIXED=PASS
BUG_850_263_CONCURRENCY_GATE=PASS
```

Validation evidence: `recovery/BUG-850-263_VALIDATION_20260924.md`.

### Result

```text
BUG-850-263=L2
STATUS=PASS
PROMOTED=YES
```

## BUG-850-262 — weapon proc probability used an inclusive threshold and overflow-prone int arithmetic

### Problem

The weapon proc gate used a `0..99` random roll against `probability + prob_every_enchant * enchant` with an inclusive boundary and `int` multiplication/addition. That made 0% capable of passing at roll 0 and allowed extreme configured/enchant values to overflow before comparison.

### Fix

Both normalized and obfuscated paths now compute the threshold in `long`, clamp it to `[0,100]`, then accept only `roll < probability` (implemented as an early return on `roll >= probability`).

### Validation

```text
WORK_CI=35723960434
COMPLETED_CI=35897401239
SOURCE_COMMIT=d6d0cb2948713cb7018c2f19c8640e99c909a718
BUG_850_262_CONTRACT=PASS
WEAPON_PROC_EXACT_THRESHOLD=PASS
WEAPON_PROC_LONG_ARITHMETIC=PASS
WEAPON_PROC_CLAMP_0_100=PASS
BUG_850_262_TARGETED_JAVAC=PASS
BUG_850_262_TARGETED_BEHAVIOR_RUNTIME=PASS
ZERO_PERCENT_NO_PROC=PASS
ENCHANT_THRESHOLD_EXACT=PASS
OVERFLOW_SAFE_CLAMP=PASS
BUG_850_262_CONCURRENCY_GATE=PASS
```

Validation evidence: `recovery/BUG-850-262_VALIDATION_20260924.md`.

### Result

```text
BUG-850-262=L2
STATUS=PASS
PROMOTED=YES
```

## BUG-850-258 — furniture world state could diverge from durable spawn state

### Problem

Furniture placement/removal mutated live world state independently from `spawnlist_furniture` persistence. SQL failure could leave furniture visible without a durable row, or remove live furniture/consume a removal charge while the durable row remained.

### Fix

Both normalized and obfuscated paths now expose affected-row checked `insertDurable`/`deleteDurable` operations. Placement publishes to the world only after a successful insert. Both removal paths remove live state, and consume the removal charge where applicable, only after a successful delete.

### Validation

```text
WORK_CI=35723780763
COMPLETED_CI=35898166735
SOURCE_COMMIT=6f45f8012b19a106f3d7f7e09f483f04994044d6
BUG_850_258_CONTRACT=PASS
PLACEMENT_DB_BEFORE_WORLD=PASS
REMOVAL_DB_BEFORE_WORLD=PASS
AFFECTED_ROW_GATE=PASS
BUG_850_258_TARGETED_JAVAC=PASS
BUG_850_258_TARGETED_BEHAVIOR_RUNTIME=PASS
FURNITURE_DB_WORLD_DIVERGENCE_BLOCKED=PASS
BUG_850_258_CONCURRENCY_GATE=PASS
```

Validation evidence: `recovery/BUG-850-258_VALIDATION_20260924.md`.

### Result

```text
BUG-850-258=L2
STATUS=PASS
PROMOTED=YES
```

## BUG-850-250 — SoulTower ranking could grow beyond authoritative top ten

### Problem

The live SoulTower ranking could retain trailing history beyond the authoritative top ten, and the old comparator used raw subtraction that could overflow. Old trailing entries could distort admission/ranking decisions.

### Fix

Candidate snapshots are safely sorted with `Integer.compare`, trimmed to at most 10 entries, and only the bounded snapshot is published to live RAM.

### Validation

```text
WORK_CI=35722373430
COMPLETED_CI=35898694976
SOURCE_COMMIT=ab2c37c6eab15109a17d33636029cbefcde651b0
BUG_850_250_CONTRACT=PASS
RAM_TOP10_TRIM=PASS
SAFE_COMPARATOR=PASS
BUG_850_250_251_TARGETED_JAVAC=PASS
BUG_850_250_TARGETED_BEHAVIOR_RUNTIME=PASS
RAM_SIZE_ALWAYS_LE_10=PASS
TRAILING_HISTORY_CANNOT_AFFECT_ADMISSION=PASS
```

### Result

```text
BUG-850-250=L2
STATUS=PASS
PROMOTED=YES
```

## BUG-850-251 — SoulTower rewrite was not durable/live atomic

### Problem

Rewriting the ranking and publishing the live snapshot were not closed as one durable success path. Partial persistence failure could diverge durable rows from live rankings.

### Fix

The rewrite now runs in an explicit transaction, requires every insert to affect exactly one row, rolls back on failure, reports success only after commit, and publishes the live bounded snapshot only after durable success. Snapshot reads/writes are serialized.

### Validation

```text
WORK_CI=35722373430
COMPLETED_CI=35898694976
SOURCE_COMMIT=ab2c37c6eab15109a17d33636029cbefcde651b0
BUG_850_251_CONTRACT=PASS
SOULTOWER_TRANSACTION=PASS
ROLLBACK_PRESENT=PASS
LIVE_PUBLISH_GATED_BY_COMMIT_SUCCESS=PASS
SNAPSHOT_READ_WRITE_SERIALIZED=PASS
BUG_850_251_TARGETED_BEHAVIOR_RUNTIME=PASS
FAILED_REWRITE_LIVE_STATE_UNCHANGED=PASS
COMMIT_THEN_PUBLISH=PASS
```

### Result

```text
BUG-850-251=L2
STATUS=PASS
PROMOTED=YES
```

## BUG-850-257 — empty SoulTower leaderboard bootstrap

### Coverage

No additional core patch is required. The bounded admission logic promoted for BUG-850-250 explicitly accepts a result whenever the current board has fewer than 10 entries, including an empty board. The dedicated follow-up runtime added the empty-board first-result case and passed.

### Validation

```text
WORK_CI=35753171855
COMPLETED_CI=35898694976
COVERED_BY=BUG-850-250
BUG_850_257_TARGETED_BEHAVIOR_RUNTIME=PASS
EMPTY_BOARD_BOOTSTRAP_PASS=PASS
```

### Result

```text
BUG-850-257=L2
STATUS=PASS_ALREADY_COVERED
NEW_CORE_PATCH=NO
```

## BUG-850-255 — progression saves silently failed when initialization rows were missing

### Problem

Save paths for `character_mobs`, `character_mobs_week`, and `character_quests_new` used update-only SQL. Missing initialization rows therefore could never self-heal on later saves.

### Fix

Normalized and obfuscated save paths now use `INSERT ... ON DUPLICATE KEY UPDATE`. Promotion replays only the six historical BUG-850-255 SQL/parameter-order patches and excludes later BUG-850-252/254/275 logic.

### Validation

```text
WORK_CI=35723688572
COMPLETED_CI=35900095083
SOURCE_COMMIT=15bf12eda62671cede855ff6931d49f8db1a4d05
HISTORICAL_PATCH_CHAIN=7470ecbe,8e2d571a,1f2cb7b1,a01d93c3,d3c8078b,1693b07c
BUG_850_255_EXACT_PATCH_APPLIED=PASS
UNRELATED_SOURCE_REPLAY=NO
BUG_850_255_CONTRACT=PASS
CHARACTER_MOBS_SAVE_UPSERT=PASS
CHARACTER_MOBS_WEEK_SAVE_UPSERT=PASS
CHARACTER_QUESTS_NEW_SAVE_UPSERT=PASS
BUG_850_255_MOBS_TABLES_JAVAC=PASS
BUG_850_255_QUESTNEW_NO_NEW_JAVAC_REGRESSION=PASS
BUG_850_255_TARGETED_BEHAVIOR_RUNTIME=PASS
MISSING_FIRST_ROW_RECOVERED_ON_SAVE=PASS
EXISTING_ROW_UPDATE_PRESERVED=PASS
BUG_850_255_CONCURRENCY_GATE=PASS
```

Validation evidence: `recovery/BUG-850-255_VALIDATION_20260924.md`.

### Result

```text
BUG-850-255=L2
STATUS=PASS
PROMOTED=YES
```
