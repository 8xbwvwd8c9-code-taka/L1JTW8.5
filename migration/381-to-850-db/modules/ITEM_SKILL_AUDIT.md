# 381 -> 850 Item Skill Audit

## Migration authority

Target authority:
- 850 item-use lifecycle
- 850 skill system
- 850 packet/action handling
- 850 item definitions

381 is donor-only.

## Scope

381 table:
- `w_道具技能`

Primary donor runtime:
- `com.lineage.william.buffskills`

Do not confuse with:
- `skills_item` / `SkillsItemTable` (skill-purchase material table)
- `w_道具輔助系統` / `ItemUse`
- `w_道具狀態` / `ItemUseEXTable`

These are separate systems.

## Current content

Current split SQL contains 31 item-skill rows.

Item IDs:
```text
90003..90033
```

Fields:
- item_id
- name
- checkClass
- level
- removeItem
- Skills
- SkillsTime
- 施放動作

Current content characteristics:
```text
checkClass = 0 for all visible rows
level      = 90 for all visible rows
removeItem = 1 for all visible rows
施放動作   = 1 for all visible rows
```

Each visible row currently references one skill ID.

Examples:
```text
90003 -> skill 148, time 960
90004 -> skill 48,  time 960
90006 -> skill 55,  time 30
90010 -> skill 68,  time 32
90016 -> skill 91,  time 30
90031 -> skill 175, time 50
```

One visible source row uses:
```text
skill 5158
```

This must be validated semantically against 850; do not assume 381 numeric skill IDs are valid in 850.

## Donor runtime

`buffskills.forItemUSe(...)` lazily loads:
```sql
SELECT * FROM w_道具技能
```

For a matching item:
1. optional class check
2. minimum level check
3. optionally consume one source item
4. parse one or more skill IDs from CSV
5. execute each through existing:
   `L1SkillUse.handleCommands(...)`
6. pass configured `SkillsTime`
7. if `施放動作 == 1`, send action GFX `19`

No module-specific persistence is used.

## Important semantic detail

The donor calls the NORMAL skill engine.

This table does not implement spell logic itself.

Therefore the source feature is conceptually:

```text
item -> invoke existing skill
```

not:

```text
custom item-skill combat framework
```

That strongly favors 850-native mapping.

## 850-first target strategy

Do NOT port:
- `buffskills`
- its static lazy cache
- its 381 class-ID decoder
- donor packet/action glue

Preferred target representation:

```text
850 item definition/action
  trigger item
  minimum level
  optional class restriction
  consume policy
  target 850 skill identity
  optional duration override
  optional cast/action presentation
```

Then execute via 850's existing skill-use engine.

## 850 comparison

850 has:
- `C_ItemUSe`
- native skill execution infrastructure
- existing skill definitions/IDs
- standard action/packet primitives

A dedicated `w_道具技能` table equivalent was not proven, but no new combat framework is required.

Therefore:
```text
850_NATIVE_PRIMITIVES=YES
DEDICATED_850_TABLE_EQUIVALENT=NOT_PROVEN
```

This is a conversion/mapping task, not a core transplant.

## Client dependency

Donor uses:
- normal skill packets/effects
- normal action GFX
- standard item-use flow

No custom client protocol or HTML is proven.

```text
CLIENT_PROTOCOL_DEP=NO
CLIENT_RESOURCE_DEP=SKILL_RESOURCE_ONLY
```

If a mapped 850 skill already exists and renders correctly, no client work is needed.

If a donor-only skill ID/GFX does not exist in 850, that individual row becomes a separate content dependency.

## Donor hazards

### 1. Skill IDs are donor-specific identities

Do not copy numeric values blindly.

Every row requires semantic mapping:
```text
381 skill name/behavior -> 850 skill identity
```

### 2. Duration override semantics need validation

Donor passes `SkillsTime` into:
```text
L1SkillUse.handleCommands(..., time, 4)
```

Some skills normally have their own duration or are instant.

Target must confirm whether 850 supports an item-specific duration override for each mapped skill.

### 3. Action 19 is donor presentation glue

Do not hardcode action 19 globally in 850.

Use the target skill/item animation behavior unless an explicit extra action is needed.

### 4. Current class mapping omits newer classes

Donor class decoder only knows:
- crown
- knight
- wizard
- elf
- dark elf
- dragon knight
- illusionist

It does not include Warrior.

Current rows all use `checkClass=0`, so this does not affect current content.

Do not port the old class decoder.

### 5. Loader error handling is weak

The donor loader catches generic exceptions silently.

Target data should be validated at startup.

## Classification

```text
LEVEL=L2
```

Reason:
- no independent combat logic
- no custom persistence
- no custom UI/protocol
- existing skill engine does the work
- mainly item/skill identity and parameter conversion

A small generic item-to-skill adapter may be needed in 850, but that remains compatible with L2 as long as it reuses native item/skill lifecycle rather than creating a parallel runtime.

## Required validation

Before production migration:
1. map each item ID semantically
2. map each skill semantically
3. verify level requirement
4. verify duration override behavior
5. verify source-item consume timing
6. validate action/effect presentation in 850
7. reject rows whose target skill does not exist

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_道具技能
LEVEL=L2
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_ROWS=31
DONOR_RUNTIME=buffskills
CORE_DEP=SMALL_ADAPTER_ONLY
DB_DEP=OPTIONAL
PERSISTENCE=NONE
850_NATIVE_ITEM_USE=YES
850_NATIVE_SKILL_ENGINE=YES
WHOLESALE_RUNTIME_PORT=NO
CLIENT_PROTOCOL_DEP=NO
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=item semantic mapping,skill semantic mapping,duration override validation
```
