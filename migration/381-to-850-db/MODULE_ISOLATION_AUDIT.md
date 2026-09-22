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


## Second-pass call-path findings

### Auto Learn Skill -> L2 confirmed
381:
- Startup: `GameServer.initialize() -> AutoAddSkillTable.get()`
- Runtime hook: `L1PcInstance.levelUp() -> AutoAddSkillTable.get().forAutoAddSkill(this)`
- Dedicated DB: `w_自動學習技能`

850:
- Completed core already has a central level-up path:
  `L1PcInstance.g() -> cJ(levelGap)`
- `cJ()` performs HP/MP/stat updates and already invokes `QuestNewTable.a().a(this)`.

Migration mapping:
- add isolated `w_自動學習技能` table
- add/adapt one AutoLearnSkill loader/table
- initialize it in 850 GameServer
- invoke it from existing 850 `cJ()` level-up hook
- reuse 850 SkillsTable / character_skills persistence

Difficulty: **L2**
Reason: no new packet/UI/NPC system is required; only data loader + existing level-up integration.

### Prestige -> L3 confirmed
381:
- `w_威望怪物` -> `NpcPrestigeTable`
- `w_威望設置` -> `RewardPrestigeTable`
- player persistence is NOT isolated to those tables:
  `characters.PrestigeLv`
- MySqlCharacterStorage reads/inserts/updates `PrestigeLv`
- L1PcInstance owns `_prestige`, `_prestigeLv`
- runtime hooks include score changes, death loss, title/stats and gfx timer application.

Migration requirement:
- two module tables
- additive player persistence migration
- character load/create/update storage patch
- L1PcInstance fields/methods
- monster kill award hook
- death-loss hook
- stat/title/gfx application

Difficulty: **L3**
Reason: independent feature DB can still be packaged separately, but core/player-persistence changes are mandatory.

### 381 startup registration evidence
`GameServer.initialize()` explicitly initializes:
- `MasterCraftTable.getInstance().load()`
- `CardSetTable.get().load()`
- `ACardTable.get().load()`
- `AutoAddSkillTable.get()`

This confirms these are active server modules, not dead DB tables.

### 850 native crafting startup evidence
850 completed core `GameServer` initializes:
- `HtmlCraftTable.a()`
- `CraftListTable.a()`

Therefore the high-version crafting framework is a first-class boot-time subsystem and is the preferred target for 381 recipe conversion.


### Clan Skill -> L3 confirmed
381 DB/core coupling:
- dedicated table: `w_血盟技能`
- clan persistence also extends `clan_data` with:
  - `clan_level`
  - `clan_contribution`
  - `clanskill`
  - `skilltime`
  - `clan_adena`
  - `clanskill_id`
  - `clanskill_lv`
- `ClanTable` reads/writes these fields.
- `L1Clan` owns matching state.
- `ClanSkillDBSet` is event-driven and mutually exclusive with another clan-skill implementation.

850 baseline:
- current `clan_data` only contains base clan identity/leader/castle/house/emblem/watch-clan fields.
- required 381 clan-skill persistence fields are absent.

Isolation package requirement:
- `w_血盟技能` table
- additive ALTERs for `clan_data`
- matching L1Clan/storage changes
- event/startup registration
- skill application/removal hooks
- explicit conflict guard with any other clan-skill implementation

Difficulty: **L3**

Note:
`w_血盟等級` is a related but separate subsystem until further call-graph proof. Do not force it into the clan-skill package unless required.



## Third-pass findings

### Transformation Card / Collection -> L3 provisional confirmed
381 active path:
- startup:
  - `CardSetTable.get().load()`
  - `ACardTable.get().load()`
- item entry:
  - `CardBook.execute() -> S_NPCTalkReturn(..., "card_01")`
- command/menu:
  - `CardBookCmd`
  - HTML IDs include `card_01`, `card_0`, `card_10`, `card_11`
- persistence model:
  - card ownership/completion is stored through quest state:
    `pc.getQuest().get_step(card.getQuestId())`
- combo completion also checks required quest IDs.
- transformation can consume configured item and call `L1PolyMorph.doPoly()`.

DB:
- `w_變身卡片能力登入`
- `w_變身卡片能力組合套卡`

Isolation package:
- two card definition tables
- quest-state dependency (no separate card ownership table is required by this implementation)
- CardBook item executor
- CardBookCmd command/action integration
- HTML resources/menu IDs
- stat aggregation hooks
- optional transformation command path

Difficulty: **L3**
Reason: DB is cleanly isolated, but the feature depends on quest-state persistence + HTML command UI + player stat aggregation. It is not a DB-only port.

Potential simplification:
- keep quest-state persistence model to avoid adding a new character-card table
- replace old HTML presentation later only if 850 has a better native collection UI

### Item Upgrade family must be split into separate modules

#### A. NPC Item Update
381:
- table: `server_item_update`
- loader: `ItemUpdateTable`
- event switch: `ItemUpdateSet`
- NPC executor: `Npc_ItemUpdate`
- HTML/menu: `y_up_i0`, `y_up_i2`
- list packet: `S_PowerItemList`

This is a dedicated NPC-driven item-upgrade subsystem.

Isolation: GOOD, but includes event + NPC + packet/html dependencies.
Difficulty: provisional **L3** until 850 packet/UI equivalent is checked.

#### B. Item-use Upgrade
381:
- table: `w_道具升級系統`
- item executor: `com.lineage.data.item_etcitem.add.Item_up`
- runtime engine: `com.lineage.william.Itemup`
- Itemup loads `SELECT * FROM w_道具升級系統`

This is distinct from `server_item_update`.

Isolation: GOOD.
Difficulty: provisional **L2-L3** depending on 850 item-executor compatibility.

#### C. Integration Upgrade
381:
- table: `w_道具升級`
- engine: `com.lineage.william.ItemIntegration`
- loads `SELECT * FROM w_道具升級`
- supports:
  - class/level constraints
  - primary + secondary target item
  - extra material arrays
  - target enchant requirement
  - random success
  - multiple outputs
  - success/fail message
  - gfx
  - broadcast
  - failure destroy/keep behavior
  - weapon/armor item-id transformation while preserving configurable state

Isolation: GOOD as its own optional module.
Difficulty: provisional **L3**.

Rule:
Do NOT merge A/B/C DBs or implementations during migration analysis. They are separate optional modules even though all are “item upgrade” features.

### Enchant level systems are separate again
- `EnchantOrginal -> w_裝武強化lv`
- `EnchantAccessory -> w_飾品等級`
These are passive/stat-by-enchant-level systems, NOT the same as the three item-upgrade systems.

### Clan Level
`w_血盟等級` defines clan-level passive bonuses and contribution thresholds.
It must remain separate from `w_血盟技能` unless a direct runtime dependency is proven.
Shared dependency: both may need additive `clan_data` state fields.

Status: call-path still under audit.



## Fourth-pass findings

### Clan Level -> L3 confirmed
381:
- event: `ClanContribution.execute()`
- startup under event activation:
  - `NpcClanContribution.get().load()`
  - `ClanOriginal.getInstance()`
- monster-energy DB:
  - `w_血盟能量怪物`
- passive level DB:
  - `w_血盟等級`
- runtime engine:
  - `ClanOriginal`
  - reads clan level from `L1Clan.getClanLevel()`
  - applies HP/MP, melee/range dmg/hit, MR/SP/AC, elemental resist,
    STR/DEX/CON/WIS/INT/CHA, physical/magic reduction, EXP rate,
    HPR/MPR and weight reduction.
- persistence dependency:
  - `clan_data.clan_level`
  - `clan_data.clan_contribution`

Isolation package:
- `w_血盟等級`
- `w_血盟能量怪物`
- additive `clan_data` columns
- ClanOriginal runtime/stat application
- monster contribution hook
- event registration

Difficulty: **L3**

This remains separate from `w_血盟技能`.
They may share clan persistence columns, but installation must not silently require the optional clan-skill module.

### Item Enchant Card system -> L3 provisional
381 active loader:
- `PowerItemTable`
- SQL: `SELECT * FROM w_道具附魔系統`

DB:
- `w_道具附魔系統`

Semantics include:
- target type: Weapon / armor / All
- enchant/card item ID
- power count/name
- unequip probability/action
- polymorph probability/id/time
- skill proc probability / skill_id / target
- HP/MP/stat/HPR/MPR/SP bonuses
- melee/ranged hit/dmg
- double-damage chance
- AC/MR/damage reduction
- gfx

Important separation:
`w_道具附魔系統` is NOT the same feature as:
- `w_炫色_素質設定`
- `character_炫色_記錄資料`
which are loaded by `ItemSpecialAttributeTable` / `ItemSpecialAttributeCharTable`.

Therefore create separate optional packages:
1. item-enchant-card module
2. color/special-attribute module

Difficulty for `w_道具附魔系統`: provisional **L3** until its item-use/equip hooks and 850 equivalent item-instance persistence are mapped.

