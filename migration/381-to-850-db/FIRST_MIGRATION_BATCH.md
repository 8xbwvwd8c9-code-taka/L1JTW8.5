# First Migration Batch Candidates

Branch: `analysis/381-to-850-db-migration`
Authority: `completed/l1jtw85-core-fixes`

Goal: identify migration units that are already proven suitable for isolated implementation.

## PASS: Batch 1 L2 candidates

### A. Auto Learn Skill
381:
- DB: `w_自動學習技能`
- loader: `AutoAddSkillTable`
- runtime: `forAutoAddSkill(pc)`
- hook: `L1PcInstance.levelUp()`

850:
- existing centralized level-up hook:
  `L1PcInstance.g() -> cJ(levelGap)`
- existing skill table and character-skill persistence can be reused.

Package target:
```
modules/auto-learn-skill/
  install.sql
  rollback.sql
  DEPENDENCIES.md
  core.patch/docs
  validation.md
```

Expected DB isolation:
- one dedicated new table
- no characters/clan/item schema alteration required

Difficulty: **L2**

### B. Native Craft Conversion - Tier A
Sources:
- 381 `ItemMaking.xml`
- 381 `SingleItemMaking.xml`
- Tier-A rows from `x_大師製作系統`

850 target:
- `craft`
- `craft_exchange`
- `html_craft` only for legacy NPC/action flows that actually need HTML

Direct/native-supported recipe semantics include:
- output item/count
- fixed output enchant
- material IDs/counts
- material enchant/bless requirements
- level bounds
- success chance
- fail-return item/count
- some alternate material/output mappings
- quantity behavior where supported by native max_count/html_craft

Package rule:
- split recipe groups by feature/content family.
- every recipe group gets its own isolated SQL.
- never require importing one giant 381 crafting SQL package.
- reserve a non-conflicting craft-id range per package.
- check all item IDs/NPC IDs before enabling.

Difficulty: **L2**

## HOLD: not Batch 1

### MasterCraft Tier B/C
Reason:
- class restriction / HP-MP cost / categories / announcement / inheritance need generic core extensions.
Status: L2-L3 / L3.

### Item-use Upgrade: w_道具升級系統
Reason:
- 381 depends on ItemExecutor/classname framework absent from confirmed 850 core.
Status: L3 provisional.

### NPC Item Update: server_item_update
Reason:
- NPC executor + HTML + list packet dependency.
Status: L3 provisional.

### Integration Upgrade: w_道具升級
Reason:
- instance mutation, state preservation, failure semantics, broadcast/gfx.
Status: L3.

### Item Enchant Card: w_道具附魔系統
Reason:
- requires dedicated `character_item_power` persistence + item instance hole state + equip hooks.
Status: L3.

### Transformation Card / Collection
Reason:
- quest persistence + HTML command UI + stat aggregation + morph path.
Status: L3.

### Clan Skill
Reason:
- additive clan_data persistence + L1Clan/storage/event/stat hooks.
Status: L3.

### Clan Level
Reason:
- clan_data persistence + monster contribution + passive stat hooks.
Status: L3.

### Prestige
Reason:
- character persistence + kill/death/stat/title/gfx hooks.
Status: L3.

## Recommended implementation order inside Batch 1

1. Auto Learn Skill
2. One small native-craft recipe package as proof-of-conversion
3. Bulk Tier-A recipe conversion only after the proof package validates

PASS gate before expanding:
- clean 850 baseline DB imports module alone
- clean rollback where safe
- server boots with module installed
- module behavior works without any other optional 381 module
- no existing 850 craft IDs/items/NPC bindings are overwritten
