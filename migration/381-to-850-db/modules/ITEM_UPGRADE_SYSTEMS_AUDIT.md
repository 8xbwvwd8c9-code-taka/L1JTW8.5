# 381 -> 850 Item Upgrade Systems Audit

## Summary

381 contains multiple unrelated upgrade frameworks.
Do NOT migrate them as one module.

Confirmed systems:

1. `server_item_update`
2. `w_道具升級系統`
3. `w_道具升級`
4. `server_item_power_update`

No equivalent implementation was confirmed in current 850 evidence.

---

## A. server_item_update

DB:
`server_item_update`

Loader:
`ItemUpdateTable`

NPC executor:
`Npc_ItemUpdate`

Semantics:
- NPC-driven
- lists eligible non-stackable, unequipped inventory items
- source item_id -> target item_id
- fixed additional material list
- deterministic conversion
- mutates the existing inventory object to the new item template
- preserves inventory object identity
- writes item_id/name through CharItemsReading
- records shifting history

Example donor row:
- item 1 -> item 2
- cost 40308 x10

UI dependencies:
- server HTML: y_up_i0 / y_up_i2
- S_PowerItemList
- S_ItemCount

Classification:
**L3**

Reason:
- dedicated NPC executor
- custom packet/UI flow
- inventory-object mutation/persistence hook

---

## B. w_道具升級系統

DB:
`w_道具升級系統`

Entry item classname:
`add.Item_up`

Executor:
`com.lineage.data.item_etcitem.add.Item_up`

Runtime:
`com.lineage.william.Itemup`

Semantics:
- player uses an upgrade item on a target item
- target must match configured `ne_item`
- target cannot be equipped
- doll target restrictions are enforced
- configurable level requirement
- configurable materials/counts
- configurable success rate
- success converts target item_id in place
- failure may destroy target depending on save_type
- upgrade stone is consumed

Example donor rows:
- item 92402 + 40308 x10000 + target item 1 -> item 42 at 10%
- item 92402 + 44070 x100 + target item 4 -> item 9 at 100%

Classification:
**L3 confirmed**

Important:
This is NOT the same system as `server_item_update`.

---

## C. w_道具升級

DB:
`w_道具升級`

Runtime:
`com.lineage.william.ItemIntegration`

Semantics:
- item-driven target upgrade/integration
- class restriction
- level restriction
- upgrade-item quantity
- target item ID/count
- optional target enchant requirement
- optional additional materials
- success rate
- multiple possible output items/counts
- optional success gfx
- configurable failure destruction
- optional failure preservation
- world broadcast
- same-type item replacement for weapon/armor/etcitem
- updates inventory object item_id/template in place

Observed persistence:
- CharItemsReading.updateItemId_Name

Classification:
**L3 confirmed**

Important:
This framework is substantially broader than `w_道具升級系統`.
Do not merge them without redesign.

---

## D. server_item_power_update

DB:
`server_item_power_update`

Loader/model:
- `ItemPowerUpdateTable`
- `L1ItemPowerUpdate`

Fields:
- itemid
- nedid
- type_id
- order_id
- mode
- random
- note
- 世界廣播

Observed content families include:
- 橘熾娃娃 progression
- 推廣徽章
- VIP progression
- 守護者的榮耀
- 麥斯特耳環
- 永恆項鍊
- 光之殷海薩腰帶
- 龍印魔石
- 幻象之劍
- sealed -> normal -> 真 equipment chains
- skill-book unseal/upgrade chains
- Hecate attack/defense
- 極．死亡騎士脛甲 progression

This table is a generic staged progression catalog keyed by:
- type_id = progression family
- order_id = stage
- itemid = current stage
- nedid = upgrade catalyst/material
- mode = stage behavior
- random = configured chance

Classification:
**L3 confirmed**, pending exact action/executor audit.

This is the progression mechanism previously seen behind several MasterCraft-produced accessory families.
It must remain a separate optional subsystem.

---

## 850 compatibility

Current 850 evidence did not confirm equivalents for:
- ItemUpdateTable
- Npc_ItemUpdate
- add.Item_up
- Itemup
- ItemIntegration
- ItemPowerUpdateTable
- server_item_power_update

Therefore none of these four systems should be treated as DB-only migration.

850 native craft can replace some deterministic one-step conversions, but not the full runtime behavior of these systems.

---

## Migration boundaries

Recommended independent modules:

```
npc-item-update/
  server_item_update
  ItemUpdateTable
  Npc_ItemUpdate
  required HTML/packet flow

item-use-upgrade/
  w_道具升級系統
  add.Item_up
  Itemup

item-integration-upgrade/
  w_道具升級
  ItemIntegration

staged-item-power-upgrade/
  server_item_power_update
  ItemPowerUpdateTable
  action/executor hook
```

Each must have its own:
- install.sql
- rollback.sql
- DEPENDENCIES.md
- validation
- client/server UI dependency notes

No optional module may silently require another optional upgrade framework.

---

## Cross-links to already audited MasterCraft content

The staged power-upgrade system directly matters to:
- Light Einhasad belts
- Meister earrings
- Eternal necklaces
- extreme Death Knight greaves
- VIP progression

Therefore:
- MasterCraft can create the base item
- staged progression should be supplied by `staged-item-power-upgrade`
- do not duplicate progression logic inside craft packages

---

## Status

```
server_item_update       = L3
w_道具升級系統           = L3
w_道具升級               = L3
server_item_power_update = L3
```

Overall item-upgrade area:
**L3 multi-framework family**

Next:
trace the exact runtime/action hook for `server_item_power_update` and define mode 0/2/4 semantics.


## server_item_power_update runtime resolved

Entry executor:
- `com.lineage.data.item_etcitem.shop.Power_Up_01`

Flow:
```
use upgrade catalyst item on target inventory object
  -> ItemPowerUpdateTable.get(target.item_id)
  -> validate target not equipped
  -> reject terminal row mode=4
  -> require catalyst item_id == nedid
  -> load all stages for same type_id
  -> next stage = order_id + 1
  -> consume one catalyst
  -> roll random.nextInt(1000) < random
  -> success: mutate same target inventory object to next-stage item_id
  -> failure: apply mode policy
```

Probability scale is per-thousand:
- random=1000 => 100%
- 900 => 90%
- 500 => 50%
- 100 => 10%
- 10 => 1%

This is intentionally different from the percent-based TianM synthesis table.

### mode semantics

Confirmed from `Power_Up_01`:

- `mode=0`: failure = keep current item unchanged.
- `mode=1`: failure = downgrade one stage; if `proctect7` is present, consume protection state and keep item.
- `mode=2`: failure = destroy target; `proctect` or `proctect6` can absorb destruction and are consumed.
- `mode=3`: failure = 50/50 downgrade one stage OR destroy target.
- `mode=4`: terminal/max stage; cannot be upgraded further.

Therefore earlier rows using mode 0/2/4 now have exact semantics.

### Persistence / state behavior

On success:
- target object's `item_id` and item template are replaced in-place
- object identity is preserved
- `CharItemsReading.updateItemId_Name`
- `CharItemsReading.updateItemBless`
- add-item refresh packet sent

On downgrade mode 1/3:
- current target object is removed
- previous-stage item is created as a new inventory object
- this does NOT preserve all arbitrary per-instance metadata by construction

On destroy mode 2/3:
- target object is removed unless a supported protection flag absorbs failure.

This difference matters for items carrying custom per-instance state.

### Protection dependencies

Failure handling directly depends on per-item flags:
- `proctect`
- `proctect6`
- `proctect7`

and their persistence via inventory update/save operations.

Migration must either:
1. port those protection-state fields and related protection-scroll subsystem, or
2. define a reduced mode policy that explicitly does not support protection scrolls.

Do not silently omit the protection behavior while claiming semantic equivalence.

### Shared progression-core boundary

`server_item_power_update` can be abstracted as:

```
staged-item-power-upgrade-core/
  stage catalog (type_id/order_id)
  catalyst validation
  per-thousand RNG
  success next-stage transform
  failure policies:
    KEEP
    DOWNGRADE
    DESTROY
    DOWNGRADE_OR_DESTROY
    TERMINAL
  optional protection adapter
  broadcast hook
```

Content packs then contain only their family rows:
- Meister earrings
- Eternal necklaces
- Einhasad belts
- DK greaves
- VIP chain
- doll chains
- etc.

### Revised classification

`server_item_power_update`:
**L3 confirmed and runtime fully traced**

No client binary/protocol dependency was found in this path.
Server packets/HTML are ordinary existing server UI mechanisms.

```
MODE_0=KEEP_ON_FAIL
MODE_1=DOWNGRADE_ON_FAIL
MODE_2=DESTROY_ON_FAIL
MODE_3=50_50_DOWNGRADE_OR_DESTROY
MODE_4=TERMINAL
RNG_SCALE=0..999 / per-thousand
ENTRY=Power_Up_01
```
