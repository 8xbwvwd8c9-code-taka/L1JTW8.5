# 381 -> 850 Special Color Attribute Audit

## Scope

381 module:
- `w_炫色_素質設定`

Required persistence:
- `character_炫色_記錄資料`

Primary runtime:
- `ItemSpecialAttributeTable`
- `ItemSpecialAttributeCharTable`
- `L1ItemSpecialAttribute`
- `L1ItemSpecialAttributeChar`
- `Attribute_Weapon_Get`
- `Attribute_Armor_Get`
- `Attribute_Weapon_Reset`
- `Attribute_Armor_Reset`
- `L1EquipmentSlot`
- `L1AttackPc`
- `L1ItemInstance`

This is separate from the already-audited:
- `w_隨機能力炫色名稱`
- `w_隨機能力炫色武器`
- `w_隨機能力炫色防具`

## Current source content

Current split SQL contains 161 visible attribute rows across 23 textual item types.

Weapon types:
```text
sword
dagger
tohandsword
bow
spear
blunt
staff
claw
edoryu
singlebow
singlespear
tohandblunt
tohandstaff
kiringku
chainsword
```

Armor types currently visible:
```text
helm
armor
T
cloak
glove
boots
shield
guarder
```

Typical tier structure per type:
```text
gold    chance 1
purple  chance 5
red     chance 10
blue    chance 20
green   chance 30/40
white   chance 50
reset   chance 0
```

## Supported attribute model

The DB/runtime supports:
- min/max attack
- melee hit
- extra attack
- STR/CON/DEX/INT/WIS/CHA
- HP/MP
- physical damage reduction
- HPR/MPR
- SP
- MR
- HP drain min/max/chance
- MP drain min/max/chance
- proc chance
- proc gfx
- proc damage
- physical block
- magic block
- PvP damage
- PvP reduction
- potion recovery increase
- broadcast text
- local result text

Current visible data usage is narrower than the engine.

Observed current-row usage:
```text
rows=161
skill_gfx_nonzero=0
hp_drain_rows=0
mp_drain_rows=0
physical_block_rows=0
magic_block_rows=0
pvp_damage_rows=24
pvp_reduction_rows=24
potion_recovery_rows=24
physical_reduction_rows=40
```

Therefore advanced drain/proc/block features exist in runtime but are not active in the current split content.

Do not implement unused features merely because donor code supports them unless another authoritative source proves active data.

## Critical source-schema blocker

`ItemSpecialAttributeTable.load()` requires:

```text
流水號
```

as the primary attribute identity:

```java
int id = rs.getInt("流水號");
_atrrList.put(id, attr);
```

However the current split INSERT column list does NOT contain `流水號`.

Therefore current attribute IDs depend on an unprovided schema/auto-increment state and insertion order.

```text
ATTRIBUTE_ID_AUTHORITY=NOT_PROVEN
```

This is a hard blocker because runtime behavior directly hardcodes those IDs.

## Hardcoded ID layout

Weapon refinement assumes fixed seven-row blocks and reset IDs:

```text
type 1  -> active 1..6, reset 7
type 2  -> active 8..13, reset 14
...
type 18 -> active 99..104, reset 105
```

Armor refinement assumes:

```text
active groups starting at 106
reset IDs:
112,119,126,133,140,147,154,161,168,175,182,189
```

Thus the donor runtime assumes an exact database identity layout.

The current split data only contains 161 visible rows.

That means:
- the weapon range through 105 is represented
- armor content is currently visible only through reset ID 161 if inserted from a clean sequence
- runtime nevertheless contains mappings through 189

This is a source/runtime version mismatch.

Do NOT synthesize missing rows 162..189 from Java switch cases.

## Per-item persistence

Unlike the separate random-color system, this subsystem stores only the selected attribute identity and audit metadata in:

```text
character_炫色_記錄資料
```

Runtime columns include:
- 玩家流水號 (item object ID)
- 炫色武防名稱
- 炫色代碼
- 角色名稱
- 時間
- 使用方式
- 地圖方式
- 洗白方式

The current split artifact for:

```text
character_炫色_記錄資料_202609221205.sql
```

is 0 bytes.

Therefore:

```text
PERSISTENCE_SCHEMA=NOT_PROVEN
```

The system relies on `炫色代碼` to resolve the current rule from `w_炫色_素質設定`.

## Persistence cleanup hazard

`ItemSpecialAttributeCharTable.load()` checks whether the item object ID exists in:
- character inventory
- personal warehouse
- clan warehouse

If none are found, it automatically executes:

```sql
DELETE FROM character_炫色_記錄資料
WHERE 玩家流水號=?
```

This is destructive loader-side cleanup.

Target migration should not silently delete records during configuration/load validation unless item ownership semantics are comprehensively proven.

## Acquisition flow

### Weapon

`Attribute_Weapon_Get`:
1. target must be a weapon
2. target must be unequipped
3. initializes a reset/white identity when needed
4. maps weapon base type to one seven-ID block
5. randomly chooses one of the six active IDs
6. rolls against `獲取的機率`
7. writes selected attr ID to per-item record
8. refreshes item name/status
9. optionally broadcasts success
10. consumes the refinement item

### Armor

`Attribute_Armor_Get` follows the same pattern with armor type -> hardcoded ID block mapping.

For unsupported/missing attr rows, the armor path contains explicit null handling in some places and reports that the equipment cannot be color-refined.

## Reset flow

Weapon/armor reset executors map the base item type to a hardcoded reset attribute ID.

They do not remove the per-item relation; they replace it with a reset/white attr identity.

This makes the DB ID layout part of functional semantics.

## Equip-time effects

`L1EquipmentSlot.set/remove` resolves:

```text
item.get_ItemAttrName()
-> ItemSpecialAttributeTable.getAttrId(attr_id)
```

and applies/removes active attribute bonuses.

Proven equip-time fields include:
- STR/CON/DEX/INT/WIS/CHA
- HP/MP
- SP
- MR
- HPR/MPR
- physical reduction
- PvP damage
- PvP reduction
- potion recovery
- physical block
- magic block

Thus this module is deeply integrated with the equipment lifecycle.

## Attack-time effects

`L1AttackPc` resolves the equipped weapon's special attribute and supports:
- HP drain
- MP drain
- extra proc damage
- effect-location gfx on proc

Current split content has these fields zero, but the runtime path is proven.

If migration is scoped strictly to current content, these can remain disabled initially.

## Item-name/display dependency

`L1ItemInstance.getNumberedName(...)` prepends:

```text
attr colour code + attr title
```

to the item name.

Example source values use standard Lineage text color control codes such as:
- `\f=`
- `\f4`
- `\f3`
- `\f1`
- `\f2`

This is a client-visible naming dependency, but no custom packet protocol or external HTML resource is proven.

Current active source rows have:

```text
施展魔法gfxid = 0
```

so current content does not prove a required custom effect resource.

Therefore:

```text
CLIENT_DISPLAY_DEP=YES
CLIENT_PROTOCOL_DEP=NO_PROVEN_CUSTOM_PROTOCOL
CUSTOM_GFX_DEP_CURRENT_CONTENT=NO
```

## 850 comparison

Targeted 850 branch inspection found no path matching:
- `ItemSpecialAttribute`
- `Attribute_Weapon_Get`
- `Attribute_Armor_Get`
- this `炫色` persistence model

850 has generic equipment/combat/item persistence primitives, but no equivalent special-color attribute framework is proven.

```text
NATIVE_850_EQUIVALENT=NOT_PROVEN
```

## Architecture risks

### 1. Attribute identity is insertion-order fragile

Because IDs are omitted from split INSERT and runtime hardcodes numeric ranges, restoring data into a database whose AUTO_INCREMENT does not begin at the expected value changes all semantics.

This is the primary migration blocker.

### 2. Rule edits mutate existing item behavior retroactively

Per-item state stores only `attr_id`.

Existing items resolve current rule data at runtime.

Changing the DB row for an ID therefore changes previously-created equipment bonuses immediately.

Target should use immutable/versioned definitions or persist a snapshot of effective affix values.

### 3. Missing rule can break equip/runtime paths

`L1EquipmentSlot` assumes the resolved attr exists once an item has a relation.

A persisted attr ID with no corresponding rule can cause runtime failure.

Target must validate referential integrity before player load/equip.

### 4. Runtime/data coverage mismatch

Runtime armor mappings extend through 189, while current split data exposes only 161 rows.

The extra runtime mappings are NOT authoritative data.

### 5. Persistence table source is empty

The persistence schema is completely absent from the split artifact, so install/rollback cannot yet be authored safely.

### 6. Loader performs destructive orphan cleanup

Target should separate audit/reporting from destructive cleanup.

## Recommended migration model

Do not preserve hardcoded sequential IDs.

Use explicit stable keys such as:

```text
affix_definition
  id
  equipment_category
  tier
  color_code
  title
  chance
  stats...
  version

item_affix
  item_obj_id
  affix_definition_id
  definition_version
  acquisition metadata
```

Alternatively snapshot all effective affix stats on the item record.

Migration should:
1. explicitly assign IDs/semantic keys
2. map only source rows actually present
3. reject unsupported equipment categories
4. validate item-affix FK integrity
5. integrate equip/remove bonuses
6. enable only current active stat fields first
7. add drain/proc/block extensions only if future active data requires them

## Difficulty

`LEVEL=L3`

Reason:
- per-item persistent identity
- equipment lifecycle integration
- combat integration
- custom item-name rendering
- DB rule/persistence loaders
- no proven custom client protocol/resource requirement for current content

The module is invasive, but current evidence does not justify L4 because the visible client behavior uses normal item-name/status packets and standard color codes.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_炫色_素質設定
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
RULE_ROWS=161
SOURCE_TYPES=23
RULE_ID_FIELD=流水號
RULE_ID_IN_SPLIT_INSERT=NO
ATTRIBUTE_ID_AUTHORITY=NOT_PROVEN
PERSISTENCE_TABLE=character_炫色_記錄資料
PERSISTENCE_SQL=EMPTY_0_BYTES
PERSISTENCE_SCHEMA=NOT_PROVEN
EQUIP_HOOK=L1EquipmentSlot
ATTACK_HOOK=L1AttackPc
ITEM_NAME_HOOK=L1ItemInstance
CLIENT_DISPLAY_DEP=YES
CLIENT_PROTOCOL_DEP=NOT_PROVEN
CUSTOM_GFX_DEP_CURRENT_CONTENT=NO
NATIVE_850_EQUIVALENT=NOT_PROVEN
RUNTIME_MAX_HARDCODED_ATTR_ID=189
CURRENT_VISIBLE_MAX_SEQUENCE_IF_CLEAN=161
BLOCKERS=authoritative schemas,explicit attr IDs,runtime/data version mismatch,target persistence,equip/combat adapter
```
