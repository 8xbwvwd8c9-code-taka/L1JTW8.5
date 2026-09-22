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
