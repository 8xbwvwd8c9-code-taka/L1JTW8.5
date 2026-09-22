# Master Craft Family Audit: Map Badges and VIP Items

## Scope
381 MasterCraft outputs:
- 240104-240114 map badge chain
- 240128-240137 VIP chain

## Critical shared dependency

Both families are not ordinary etcitems.

381 `etcitem` rows use:
- `classname = add.VIP`

Runtime executor:
- `com.lineage.data.item_etcitem.add.VIP`

Definition loader:
- `ItemVIPTable`

Source table:
- `w_指定道具賦予狀態`

Therefore both families depend on the same reusable "equippable status item" subsystem.

## add.VIP behavior

Using the item toggles an equipped state.

On equip:
- finds item definition in `w_指定道具賦予狀態`
- rejects another equipped VIP item of the same `type`
- marks item equipped
- applies configured bonuses
- persists equipped state through inventory item state
- sends slot/status packets

On unequip:
- reverses all applied bonuses
- clears equipped state

Supported effects include:
- weapon magic damage/proc
- STR/DEX/CON/INT/WIS/CHA
- AC
- HP/MP
- HPR/MPR
- melee damage/hit
- bow damage/hit
- physical/magic damage reduction
- MR/SP
- elemental resistances
- stun/stone/sleep/freeze/sustain/blind resistance
- EXP bonus
- adena bonus
- polymorph
- skin object
- effect icon
- displayed VIP title/name

This is broader than ordinary etcitem data and cannot be reproduced by craft rows alone.

## Map badge chain

MasterCraft:
- 240104 Lv1 badge
- 240105 Lv2
- 240106 Lv3
- 240108 Lv4
- 240109 Lv5
- 240110 Lv6
- 240111 Lv7
- 240112 Lv8
- 240113 Lv9
- 240114 Lv10

Upgrade behavior:
- Lv1 is guaranteed
- higher levels use decreasing success:
  - 90 / 80 / 70 / 60 / 50 / 40 / 30 / 20 / 10
- failure returns previous badge
- each level consumes a level-specific material 240207-240217 plus adena

Status definitions in `w_指定道具賦予狀態` scale per level.

Example progression includes increasing:
- HP/MP
- damage/hit
- bow damage/hit
- reductions
- resist/stat effects
- EXP bonus

Classification:
- craft chain itself: **L2**
- item/status runtime: **L3 confirmed**

The badge family is not a pure "map access item" based on current evidence.
No direct row dependency was found in `w_map_limit_item` from the split export.
Its confirmed function is an equippable status item through `add.VIP`.

## VIP1-VIP10 chain

MasterCraft:
- 240128 -> VIP1
- ...
- 240137 -> VIP10

Progression is deterministic and consumes:
- previous VIP item
- adena
- item 44070 in increasing amounts

All VIP1-VIP10 entries also use `add.VIP`.

Their `w_指定道具賦予狀態` rows use a separate `type` value from the map badges, allowing the two families to coexist if their type values differ.

VIP levels add increasingly strong:
- HP/MP
- damage/hit
- physical/magic reduction
- EXP/adena/stat-related bonuses

Classification:
- craft progression: **L2**
- VIP status subsystem: **L3 confirmed**

## Relation to character_vip

Important:
The `add.VIP` item subsystem and the time-based `character_vip` system are separate mechanisms.

Time-based VIP:
- `VIPSet`
- `VIPReading`
- `VIPTable`
- `character_vip`
- `VIPTimer`

Item-based VIP/status:
- `add.VIP`
- `ItemVIPTable`
- `w_指定道具賦予狀態`

Do NOT bundle `character_vip` into the item-based VIP migration unless a direct dependency is later proven.

## 850 migration consequence

These two content families cannot be migrated as:
- etcitem rows + craft rows only

Required shared core module:
```
status-item-vip-core/
  ItemVIP definition model
  loader for w_指定道具賦予狀態
  equip/unequip toggle
  apply/remove stat effects
  equipped-state persistence integration
  packet/UI handling
```

Then independent data/content modules:
```
map-badges/
  etcitem rows
  w_指定道具賦予狀態 rows
  craft rows
  materials
  rollback

vip-item-chain/
  etcitem rows
  w_指定道具賦予狀態 rows
  craft rows
  materials
  rollback
```

Both may depend on the shared status-item-vip-core, but must not depend on each other.

## Difficulty

Shared status-item-vip-core: **L3**
Map badge content: **L2 on top of L3 shared core**
VIP1-VIP10 content: **L2 on top of L3 shared core**

## Recommendation
Do not prioritize these for first migration batch.
They are cleanly modularizable, but they require a reusable runtime subsystem first.
