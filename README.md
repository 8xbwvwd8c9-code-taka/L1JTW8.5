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
