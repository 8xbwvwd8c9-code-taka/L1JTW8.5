# First-Pass Module Isolation Audit

Branch: `analysis/381-to-850-db-migration`
850 authority: `completed/l1jtw85-core-fixes`

## Isolation rule
Every optional migrated module must be installable independently.
Each module package should eventually contain:
- `install.sql`
- `rollback.sql` where safe/possible
- `DEPENDENCIES.md`
- core/config/NPC/menu requirements
- validation notes

Shared 850 base tables may be referenced, but one optional module must not silently depend on another optional module.

## Initial findings

### 1. Master Craft
381 core:
- `src/com/add/system/MasterCraft.java`
- `src/com/add/system/MasterCraftTable.java`

DB:
- `x_大師製作系統`

Loader evidence:
- `MasterCraftTable.TABLE_NAME = "x_大師製作系統"`
- explicit `load()`
- `SELECT * FROM x_大師製作系統 WHERE 是否顯示 <> 0 ...`
- keyed by `npcid + action`

Isolation:
- Strong candidate for independent module package.
- DB is already centralized in one dedicated table.
- NPC IDs/actions are dependencies and must be mapped explicitly.

850 adaptation:
- Do NOT blindly port 381 UI/core.
- First map recipe semantics into 850 native:
  `craft / craft_exchange / L1Craft`.
- Use `html_craft` only where NPC action/custom HTML remains necessary.

Difficulty:
- Basic recipes: provisional L2.
- Advanced inheritance/bonus/fail/announcement behavior: provisional L2-L3.

### 2. Transformation Card / Collection
381 core:
- `src/com/add/system/ACardTable.java`
- `src/com/add/system/CardSetTable.java`
- related: `ACard.java`, `CardBookCmd.java`, `CardPolySet.java`, event `CardSet.java`

DB:
- `w_變身卡片能力登入`
- `w_變身卡片能力組合套卡`

Loader evidence:
- `ACardTable.load() -> SELECT * FROM w_變身卡片能力登入`
- `CardSetTable.load() -> SELECT * FROM w_變身卡片能力組合套卡`

Isolation:
- Treat as ONE optional module with two primary DB tables.
- Do not split these tables into separate optional modules unless later call-graph proof shows independence.

Difficulty:
- Provisional L3 until 850 equivalents/client dependency are checked.

### 3. Clan Skill
381 core:
- `src/com/lineage/server/datatables/RewardClanSkillsTable.java`
- related event/core classes:
  `ClanSkillDBSet.java`, `ClanSkillSet.java`, `ClanSkillTimer.java`, `L1ClanSkills.java`

DB:
- `w_血盟技能`

Loader evidence:
- constructor loads table
- `SELECT * FROM w_血盟技能`

Isolation:
- Dedicated table and loader: good independent-module candidate.
- Must also inspect clan-level dependency before packaging.

Difficulty:
- Provisional L2-L3.

### 4. Prestige
381 core:
- `NpcPrestigeTable.java`
- `RewardPrestigeTable.java`
- `Prestige.java`
- `L1PcRewardPrestigeGfxTimer.java`
- `L1RewardPrestige.java`

DB:
- `w_威望怪物`
- `w_威望設置`

Loader evidence:
- `NpcPrestigeTable.load() -> SELECT * FROM w_威望怪物`
- `RewardPrestigeTable -> SELECT * FROM w_威望設置`

Isolation:
- Treat both tables as one Prestige module.
- Monster score input and reward/level output are coupled parts of the same feature.

Difficulty:
- Provisional L3 until player persistence columns and 850 hooks are checked.

### 5. Auto Learn Skill
381 core:
- `AutoAddSkillTable.java`
- `L1AutoLearnSkill.java`
- related `Addskill.java` / `SkillTeacherSet.java`

DB:
- `w_自動學習技能`

Loader evidence:
- constructor invokes `load()`
- `SELECT * FROM w_自動學習技能`

Isolation:
- Excellent independent-module candidate.
- Single dedicated config table plus skill/class dependencies.

Difficulty:
- Provisional L2 if 850 skill-learning hook exists.
- L3 only if login/level-up integration must be newly added.

### 6. Item Upgrade / Enchant family
381 has multiple similarly named systems:
- `w_道具升級`
- `w_道具升級系統`
- `server_item_update`
- `w_道具附魔系統`
- multiple William/enchant/item-update classes

Confirmed loader:
- `ItemUpdateTable.load() -> SELECT * FROM server_item_update ORDER BY id`

Important:
- Do NOT assume `w_道具升級*` is the active loader used by this core path.
- Must resolve old/new/parallel implementations before migration.

Isolation:
- Pending.

Difficulty:
- Pending call-graph audit.

## 850 crafting capability already confirmed
Completed 850 core contains:
- `C_NpcAction -> HtmlCraftTable -> html_craft`
- `CraftListTable -> craft + craft_exchange -> L1Craft`
- protobuf high-version crafting UI
- `S_HowManyMake` for quantity input

This is the preferred target for adapting 381 XML/master-craft recipes.
