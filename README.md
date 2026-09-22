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
