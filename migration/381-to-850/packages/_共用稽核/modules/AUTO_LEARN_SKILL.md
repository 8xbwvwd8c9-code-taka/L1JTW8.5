# Module Spec: Auto Learn Skill (381 -> 850)

Status: READY FOR IMPLEMENTATION DESIGN
Difficulty: L2
Branch: `analysis/381-to-850-db-migration`

## 1. 381 source

DB:
- `w_自動學習技能`

Known data columns from current split SQL:
- `註解`
- `Level`
- `Skill`
- `Royal`
- `Knight`
- `Mage`
- `Elf`
- `Darkelf`
- `DragonKnight`
- `Illusionist`

Important:
Current split file contains INSERT data only. Exact original DDL must be extracted from the authoritative 381 DB/full dump before generating install.sql. Do not infer column types as final migration schema.

Core:
- `AutoAddSkillTable`

381 class mapping:
- Royal -> type 0
- Knight -> type 1
- Elf -> type 2
- Mage -> type 3
- Darkelf -> type 4
- DragonKnight -> type 5
- Illusionist -> type 6

Loader model:
- `Map<classType, Map<level, int[] skillIds>>`

Runtime semantics:
1. On level-up, get current class type.
2. Iterate configured thresholds where configured level <= current level.
3. For each configured skill:
   - skip if already mastered
   - send add-skill packet
   - persist skill to character skill storage
   - send learn effect/message

The <= comparison is important: if a character skips levels, all missing eligible configured skills are learned.

## 2. 850 existing reusable path

850 already contains:
- `SkillsTable`
- `S_AddSkill`
- `character_skills`
- existing skill persistence method in `SkillsTable`
- central level-up flow in `L1PcInstance.g() -> cJ(levelGap)`

Confirmed 850 persistence schema:
```
character_skills
  id
  char_obj_id
  skill_id
  skill_name
  is_active
  activetimeleft
PRIMARY KEY (char_obj_id, skill_id)
```

Confirmed 850 skill grant pattern already used by character creation:
```
L1Skills skill = SkillsTable...get(skillId)
SkillsTable...insert(charObjId, skillId, skillName, 0, 0)
send S_AddSkill
```

Therefore no new character-skill persistence schema is required.

## 3. Migration architecture

### DB package
Target:
```
modules/auto-learn-skill/
  install.sql
  rollback.sql
  DEPENDENCIES.md
  VALIDATION.md
```

`install.sql`:
- CREATE only the dedicated auto-learn configuration table.
- INSERT migrated rules.
- MUST NOT ALTER `characters`.
- MUST NOT ALTER `character_skills`.

`rollback.sql`:
- DROP only this module's configuration table.
- MUST NOT delete already learned player skills.
  Learned skills become normal character skills once granted.
- Document this intentionally non-reverting runtime side effect.

### Core
Add one isolated loader/service:
- load config at GameServer startup
- validate configured skill IDs through 850 SkillsTable
- expose `applyEligibleSkills(pc)`

Hook:
- call once from existing 850 level-up path after level has been finalized.

Do not duplicate:
- skill packet serialization
- character skill INSERT logic
- mastery state logic

Reuse 850 native implementations.

## 4. Validation and safety

At load time:
- reject/log nonexistent skill IDs
- reject rows with no enabled class
- reject ambiguous rows with multiple class flags unless semantics are explicitly defined
- trim and validate CSV skill list

At runtime:
- never insert duplicate `character_skills`
- one failed/invalid configured skill must not block later valid skills
- repeated level recalculation must be idempotent

## 5. Initial migrated 381 rules observed

Examples:
- Royal Lv10 -> skills 1-8
- Royal Lv15 -> 113,116
- Royal Lv20 -> skills 9-16
- Knight Lv50 -> skills 1-8
- Mage Lv4 -> skills 1-8
- Mage Lv8 -> skills 9-16
- Mage Lv12 -> 17-23
- Mage Lv16 -> 25,26,28,30,32
- Mage Lv20 -> 34,35,38
- Mage Lv24 -> 42,43,48
- Elf Lv8 -> skills 1-8
- Elf Lv10 -> 129,130,131
- Elf Lv16 -> skills 9-16
- Elf Lv20 -> 137,138,147
- Elf Lv24 -> 17-23
- Elf Lv32 -> 25,26,28,30,32,154,162
- Elf Lv40 -> 34,35,38
- Elf Lv48 -> 42,43,48
- Darkelf Lv12 -> skills 1-8
- Darkelf Lv15 -> 100,101,106
- Darkelf Lv24 -> skills 9-16
- DragonKnight Lv15 -> 181,186 and 182,183,188
- Illusionist Lv15 -> 205,215 and 201,202,203,204,206,208,209,211,213

Note:
Multiple rows can share the same class + level. The 381 implementation stores by `Map.put(level, skills)`, so later rows overwrite earlier rows for the same class/level. This is a real migration hazard.

Observed collision:
- DragonKnight Lv15 appears in two rows.
- Illusionist Lv15 appears in two rows.

Do NOT preserve overwrite behavior blindly.

Recommended normalized 850 behavior:
- merge all rows for the same class + level into a unique union of skill IDs.
- retain deterministic order.
- log duplicate/collision normalization.

This is a correctness improvement required for faithful content migration.

## 6. PASS gate before implementation

1. Extract authoritative 381 `SHOW CREATE TABLE w_自動學習技能` or equivalent CREATE TABLE from the full dump.
2. Verify every migrated skill ID exists in 850 `skills`.
3. Verify 850 class-type IDs 0-6 match the intended classes.
4. Resolve same-class/same-level duplicate rows by union, not overwrite.
5. Confirm clean 850 DB + this module install boots.
6. Test:
   - exact threshold level
   - skipped multiple levels
   - already-known skill
   - repeated invocation
   - invalid configured skill ID
   - class isolation
7. Rollback removes configuration only and leaves player-earned skills intact.



## 7. Skill-ID compatibility check

Compared all distinct skill IDs referenced by current 381 `w_自動學習技能` data against 850 `skills`.

Result:
- checked: 63
- found in 850: 63
- missing: 0

Status: **PASS**

No skill-ID remapping blocker exists for the currently observed 381 rules.
