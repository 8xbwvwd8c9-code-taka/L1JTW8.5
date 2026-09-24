# 381 -> 850 Item Utility System Audit

## Migration authority

Target authority:
- 850 item-use lifecycle
- 850 polymorph/skill/stat APIs
- 850 persistence
- 850 packet/UI behavior

381 is donor-only.

## Scope

381 table:
- `w_道具輔助系統`

Primary donor runtime:
- `com.lineage.william.ItemUse`

This is separate from:
- `w_道具技能`
- `w_道具狀態`
- `skills_item`

## Current content

Current split SQL contains 50 visible rows.

Observed content families include:

```text
dice/GFX-only items
EXP scrolls
permanent HP/MP increase
permanent STR/DEX/CON/WIS/INT/CHA increase
polymorph/statue items
polymorph scrolls
```

Current source also exposes generic fields for:
- class restriction
- prerequisite item
- consume item
- polymorph ID/time
- permanent HP/MP/stats
- level gate
- current HP/MP change
- EXP
- lawful
- GFX
- skill IDs/time
- karma

The split artifact is INSERT-only.

## Donor runtime

`ItemUse.forItemUSe(...)` performs a broad switch-by-data model:

1. optional class check
2. optional level check
3. optional prerequisite item check
4. optionally consume one source item
5. optional polymorph
6. optional permanent HP/MP
7. optional permanent six-stat changes
8. optional current HP/MP changes
9. optional EXP
10. optional lawful
11. optional skill effect GFX
12. optional skill execution through `L1SkillUse`
13. optional karma

This is a generic donor utility engine, not a single gameplay feature.

## 850 comparison

850 already has native primitives for:
- item-use routing
- polymorph
- current HP/MP
- EXP
- lawful
- karma
- skill execution
- system/status packets
- character base stats
- character persistence

Therefore:

```text
850_NATIVE_PRIMITIVES=YES
WHOLESALE_381_ITEMUSE_PORT=NO
```

## 850-first migration strategy

Do not port the generic `ItemUse` class.

Split source rows into 850-native action families:

```text
GFX_ONLY
EXP_GRANT
PERMANENT_HP_MP
PERMANENT_BASE_STAT
POLYMORPH
SKILL_CAST
LAWFUL_KARMA
HEAL_MP
```

Each family should use an existing 850 action path or the smallest reusable adapter.

## Permanent stat semantics

The donor permanent-stat path calls:
- `addBaseStr/Dex/Con/Wis/Int/Cha`
- `addBaseMaxHp/Mp`

and increments:
```text
ElixirStats
```
for permanent six-stat increases.

This is significant.

A naive target conversion that only changes base stat values can break:
- stat-point accounting
- elixir/stat cap logic
- reset/rebirth behavior
- character save/load consistency

Therefore permanent six-stat items require explicit validation against 850's native permanent-stat accounting.

Do not blindly reuse donor `ElixirStats` semantics.

## Polymorph semantics

Some source rows set:
```text
讀取變身能力=1
```

Donor behavior then also calls:
```text
user.setloginpoly(polyId)
```

before applying the polymorph.

This means the donor intends those morphs to participate in login/restoration behavior.

850 polymorph persistence is authoritative.

Do not port donor `loginpoly` behavior if 850 already has its own restoration model.

## Current content observations

### EXP items

Examples:
- 50,000 EXP
- 20,000 EXP
- 10,000 EXP
- 5,000 EXP

These are straightforward 850-native item actions.

### Permanent HP/MP

Examples:
- permanent MP +30/+15/+10/+5
- permanent HP +20/+15/+10/+5

These need 850 character persistence validation but no custom client protocol.

### Permanent attributes

Rows exist for permanent:
- WIS +1/+2/+3
- CHA +1/+2/+3
- CON +1/+2/+3
- INT +1/+2/+3
- DEX +1/+2/+3
- STR +1/+2/+3

These are the highest-risk rows because they alter character progression accounting.

### Polymorph items

Examples include:
- Baphomet statue
- mythic/gold transformation
- death knight
- dark elf
- class-specific male/female morph scrolls

These must map to 850 polymorph IDs/resources semantically.

## Donor hazards

### 1. Silent loader failure

The donor catches generic exceptions with an empty handler.

Target startup must report invalid rows.

### 2. One giant action surface

The source engine mixes unrelated effects in one row model.

Porting this design would duplicate 850 systems and enlarge risk.

### 3. Permanent stat accounting is donor-specific

Six-stat items also change `ElixirStats`.

Target must use 850-native progression accounting instead.

### 4. Polymorph persistence differs by row

`讀取變身能力` changes whether donor stores a login morph.

Target must normalize this into 850's own morph persistence policy.

### 5. Numeric identities are not portable

Required semantic mapping:
- item IDs
- polymorph IDs
- GFX IDs
- skill IDs

### 6. Source-item consumption happens early

The donor may consume the source item before all later effect operations finish.

Target should use effect-specific validation and only consume when the target action is committed.

## Client dependency

No custom protocol or HTML is proven.

Some rows depend on:
- normal morph resources
- normal skill/GFX resources

```text
CLIENT_PROTOCOL_DEP=NO
CLIENT_RESOURCE_DEP=CONTENT_SPECIFIC
```

Individual morph/GFX rows can be blocked if the referenced 850 resource is absent, without blocking the whole utility family.

## Difficulty

Family level:
```text
LEVEL=L3
```

Subclassification:

```text
GFX/EXP/current HP-MP/lawful/karma = L2
polymorph                          = L2 if target resource exists
skill cast                         = L2
permanent HP/MP                    = L2/L3
permanent six-stat                 = L3
```

Why family-level L3:
- permanent character progression mutation
- persistence/accounting semantics
- multiple action families

But there is no reason to create a parallel 381 core.

## Recommended migration shape

Do not create one replacement mega-table unless needed.

Prefer:
- native 850 item definitions for simple rows
- one small reusable permanent-stat action
- one small reusable item-to-skill action if not already available
- existing 850 polymorph handling

Each migrated row should declare exactly one or a small number of target-native effects.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_道具輔助系統
LEVEL=L3
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_ROWS=50
DONOR_RUNTIME=ItemUse
850_NATIVE_PRIMITIVES=YES
WHOLESALE_RUNTIME_PORT=NO
PERMANENT_STAT_ROWS=YES
POLYMORPH_ROWS=YES
EXP_ROWS=YES
SKILL_FIELDS_SUPPORTED_BY_DONOR=YES
CLIENT_PROTOCOL_DEP=NO
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=permanent-stat accounting,item mapping,poly/GFX/skill semantic mapping,850 persistence validation
```
