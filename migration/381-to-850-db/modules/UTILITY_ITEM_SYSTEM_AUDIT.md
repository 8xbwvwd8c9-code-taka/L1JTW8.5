# 381 -> 850 Utility Item System Audit

## Migration authority

Target authority:
- 850 item-use lifecycle
- 850 character persistence
- 850 polymorph/skill/effect systems
- 850 packets/UI

381 is donor-only.

Do NOT port the donor catch-all `ItemUse` class as a parallel item framework.

## Scope

381 table:
- `w_道具輔助系統`

Primary donor runtime:
- `com.lineage.william.ItemUse`

Current split content:
```text
ROWS=50
```

## Current content families

The table mixes unrelated item behaviors into one generic rule engine.

### 1. Visual-only dice items

```text
92006..92011
```

Behavior:
- no consume
- no stat change
- only GFX:
  - 3209
  - 3208
  - 3207
  - 3206
  - 3205
  - 3204

These are simple 850-native item-effect candidates.

### 2. EXP scrolls

```text
92012 = +50000 EXP
92013 = +20000 EXP
92014 = +10000 EXP
92015 = +5000 EXP
```

All are consumed on use.

These are simple 850-native item reward actions.

### 3. Permanent MP items

```text
92016 = +30 base MP
92017 = +15 base MP
92018 = +10 base MP
92019 = +5 base MP
```

### 4. Permanent HP items

```text
92030 = +20 base HP
92031 = +15 base HP
92032 = +10 base HP
92033 = +5 base HP
```

### 5. Permanent base-stat items

```text
92034..92051
```

Current rows cover permanent:
- WIS
- CHA
- CON
- INT
- DEX
- STR

with +1 / +2 / +3 variants.

Donor also increments:
```text
ElixirStats
```
for permanent six-stat increases.

### 6. Polymorph items

Visible rows include:
- 92062 巴風特雕像
- 95331 神話變形怪首領金
- 240224 死亡騎士
- 240225 黑暗妖精
- 240254..240263 class/gender morph scrolls

Some rows set:
```text
讀取變身能力=1
```
which causes donor runtime to also store:
```text
loginpoly
```

Target behavior must use 850 polymorph persistence semantics rather than copying this custom flag.

## Donor runtime capability

`ItemUse` supports much more than current content:
- class restriction
- required medium item
- consume source item
- polymorph
- login morph flag
- permanent HP/MP
- permanent six stats
- minimum level
- immediate HP/MP
- EXP
- lawful
- GFX
- skill casting
- karma

Current active content does NOT require all of these capabilities.

Do not port unused donor surface.

## Critical donor issues

### 1. Permanent stat items have no module-level one-time guard

For permanent HP/MP/stat rows, `ItemUse` directly modifies base values and consumes the source item.

There is no rule-level:
- account-once
- character-once
- cap
- quest guard

inside this module.

If the item can be repeatedly acquired, the permanent bonus is repeatable.

This may be intentional, but 850 migration must preserve or explicitly cap it through target item policy.

### 2. Permanent six-stat items modify ElixirStats

For STR/DEX/CON/WIS/INT/CHA the donor also executes:
```text
setElixirStats(current + amount)
```

This means the item participates in donor elixir/stat accounting semantics.

850 must decide whether these should count against / integrate with its native stat/elixir system.

Do not blindly increment an unrelated target counter.

### 3. HP/MP permanent values use base-stat mutation

Donor calls:
```text
addBaseMaxHp
addBaseMaxMp
```

Target must use 850 authoritative character persistence fields and recalc flow.

### 4. Generic loader swallows all exceptions

`getData15b()` catches generic exceptions without logging.

Target should validate rows explicitly.

### 5. One table mixes unrelated behavior types

This makes rollback/testing difficult.

850 should split by native item action type instead of reproducing the donor mega-table.

### 6. Donor class decoder is old

The donor supports only:
- crown
- knight
- wizard
- elf
- dark elf
- dragon knight
- illusionist

No Warrior path exists.

Current visible rows use:
```text
checkClass=0
```
so this does not affect current data.

Do not port the old decoder.

### 7. Skill/GFX/poly numeric IDs are donor identities

All IDs must be semantically validated against 850 resources.

Especially:
- GFX IDs
- poly IDs
- any future skill IDs

## 850 native capability

Targeted 850 inspection proves native components for:
- `C_ItemUSe`
- `L1PolyMorph`
- `CharacterTable`
- `CharBuffTable`
- `S_OwnCharStatus`
- `S_OwnCharStatus2`

Therefore most current behaviors can be expressed through existing 850 lifecycle primitives.

```text
850_NATIVE_ITEM_USE=YES
850_NATIVE_POLYMORPH=YES
850_NATIVE_CHARACTER_PERSISTENCE=YES
```

A donor `ItemUse` clone is unnecessary.

## 850-first migration model

Split source rows by behavior.

### Visual item
```text
item -> existing 850 effect packet
```

### EXP item
```text
item -> consume -> add EXP -> native status refresh
```

### Permanent HP/MP item
```text
item -> validate target cap/policy -> mutate 850 authoritative base HP/MP -> save character -> refresh
```

### Permanent stat item
```text
item -> validate stat/elixir policy -> mutate 850 native base stat -> save -> refresh
```

### Morph item
```text
item -> 850 native polymorph action
```

No donor-specific UI should be carried forward.

## Classification

Family-level:
```text
LEVEL=L3
```

Sub-classification:
```text
visual-only rows = L2
EXP rows         = L2
polymorph rows   = L2
permanent HP/MP  = L2/L3
permanent stats  = L3 until 850 elixir/stat-accounting mapping is proven
```

Family is L3 because permanent character mutation requires authoritative persistence/accounting integration.

## Client dependency

No custom HTML or custom protocol is proven.

Current behavior uses:
- standard status packets
- standard skill/GFX packets
- native polymorph visuals

```text
CLIENT_PROTOCOL_DEP=NO
CLIENT_RESOURCE_DEP=ID_MAPPING_ONLY
```

## Source schema

Current split artifact is INSERT-only.

```text
SOURCE_SCHEMA=NOT_PROVEN
```

Do not clone donor schema into 850; represent rows in target-native item configuration/action structures.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_道具輔助系統
LEVEL=L3
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_ROWS=50
WHOLESALE_ITEMUSE_PORT=NO
VISUAL_ROWS=L2
EXP_ROWS=L2
POLY_ROWS=L2
PERMANENT_HP_MP=L2_L3
PERMANENT_STAT=L3
850_NATIVE_ITEM_USE=YES
850_NATIVE_POLYMORPH=YES
850_NATIVE_CHARACTER_PERSISTENCE=YES
CLIENT_PROTOCOL_DEP=NO
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=item semantic mapping,gfx/poly mapping,permanent-stat cap policy,ElixirStats semantic mapping,850 save/recalc mapping
```
