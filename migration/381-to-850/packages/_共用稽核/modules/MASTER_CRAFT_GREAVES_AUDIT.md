# Master Craft Family Audit: Greaves / Death Knight Greaves

## Scope
381 MasterCraft output/material chain:
- 330000-330005 ability greaves
- 1310162 / 1310172 / 1310182 death-knight greaves
- 120480 / 120481 / 120482 extreme death-knight greaves

## 381 item definitions

### Base ability greaves
Found in 381 armor data by name:
- 鋼鐵脛甲
- 力量脛甲
- 敏捷脛甲
- 智力脛甲
- 精神脛甲

These are ordinary `pants` armor rows with `classname='0'`.
Their effects are expressed through normal armor columns:
- AC
- STR/DEX/INT/WIS
- MR
- other standard armor stats

### Death-knight greaves
Base death-knight greaves are also primarily normal armor rows.

However higher variants include rows whose classname is:
`Hades_Cloak <rate> <damage_min> <damage_max>`

Examples:
- `Hades_Cloak 50 80 80`
- `Hades_Cloak 50 100 100`
- `Hades_Cloak 100 120 120`
- `Hades_Cloak 150 150 150`

## Hades_Cloak runtime behavior

381 class:
`com.lineage.data.item_armor.Hades_Cloak`

It is a custom `ItemExecutor`.

On equip/use it sets player runtime state:
- proc rate
- minimum reflected/counter damage
- maximum reflected/counter damage

`L1PcInstance.receiveDamage()` contains the actual hook:
- when `_hades_cloak > 0`
- roll against a 0..999 range
- on success deal configured damage back to the attacker
- works against PC and NPC attackers
- halves this counter damage when target has skill effect 68

This behavior cannot be represented by ordinary armor stat columns.

## 850 compatibility

850 already supports:
- armor type `armor_Greaves`
- standard armor stats
- STR/DEX/CON/INT/WIS/CHA
- HP/MP
- MR
- PVP damage/reduction
- magic hit
- anti damage reduction
- EXP
- standard equip application

Therefore base 330000-330005 style greaves are structurally compatible with 850 armor and are likely **L2 data migration** once exact IDs/rows are recovered.

850 current armor schema does not expose the 381 `classname` ItemExecutor model, and no Hades-equivalent proc state/hook has been confirmed.

Therefore Hades variants require a dedicated core extension.

## Difficulty split

### Ability greaves 330000-330005
**L2**
- armor rows
- craft rows
- client gfx/name/resource verification
- no custom executor proven

### Base death-knight greaves
**L2-L3**
- standard armor stats mostly fit 850
- must map exact 381 item IDs because the current split armor export omitted `item_id`
- verify each row has no custom classname/effect

### Extreme/+9+ death-knight greaves using Hades_Cloak
**L3 confirmed**
Requires:
- reusable Hades proc state on player
- equip/unequip application
- receiveDamage counter-damage hook
- configuration per item
- tests for PC/NPC attackers and effect-68 interaction

Do NOT fake Hades behavior using static damage/PVP/DR armor columns; it is a reactive damage proc.

## Data-export blocker

Current 381 split `armor_202609221205.sql` omits `item_id` from INSERT columns.
Names and stats are visible, but exact row-to-ID identity cannot be safely reconstructed from row order.

Before migration:
- use authoritative full 381 dump or live DB
- query exact armor rows with `item_id`
- bind names -> IDs explicitly

## Recommended package split

```
craft-greaves-basic/
  armor rows: 330000-330005
  craft rows
  dependencies
  rollback

craft-dk-greaves-basic/
  exact non-Hades DK armor rows
  craft rows
  dependencies
  rollback

craft-dk-greaves-hades/
  armor rows
  Hades core extension
  craft rows
  validation
  rollback
```

Do not make the Hades extension a dependency of the basic greaves package.
