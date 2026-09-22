# 381 -> 850 DB / Module Migration

Base: `completed/l1jtw85-core-fixes`

Purpose: analysis-only branch for 381 -> 850 DB/module migration. Keep BUG repair branch clean.

## Rules
- ANALYSIS / RECORDS / REQUIRED DATA only.
- No production merge from this branch.
- No direct bulk SQL import into 850.
- Judge each module as a full chain:
  `control/config -> Java core -> DB schema/data -> NPC/action -> XML/HTML/menu -> client/protocol dependency`.
- Prefer adapting 381 content to 850 native high-version systems over porting old 381 frameworks unchanged.
- 850 authority for core behavior: `completed/l1jtw85-core-fixes`.
- Every migrated module MUST be DB-isolated and independently importable. Do not require a monolithic all-modules SQL import.
- Each module package must contain its own schema/data changes plus dependency notes. Where applicable, provide separate `install.sql` and `rollback.sql` (or clearly documented irreversible/data-loss cases).
- Cross-module DB dependencies must be explicit. Shared base tables may be referenced, but one optional module must not silently require another optional module's SQL.
- A module is not considered migration-ready until its DB can be applied independently to a clean 850 baseline and its required core/config/NPC/menu dependencies are identified.

## Current baseline
### 381
- DB split source: `L381/main/DB/381_DB_AI用`
- 320 SQL files total.
- 210 non-empty SQL files.
- 381 contains dedicated module tables and Java implementations.
- NPC action resources include:
  - `data/xml/NpcActions/ItemMaking.xml`
  - `data/xml/NpcActions/SingleItemMaking.xml`
  - `data/xml/NpcActions/Teleporter.xml`
- Config also includes multiple `config/其他控制端/*.properties` files.

### 850
- DB baseline: `db/8.5.sql`
- Split inspection DB: `db/無使用給AI檢查用資料庫DB`
- 100 split DB files observed.
- 45 table names directly overlap with 381.
- Completed core contains two crafting paths:

1. Legacy NPC/HTML crafting:
   `C_NpcAction -> HtmlCraftTable -> html_craft`

2. High-version crafting:
   `CraftListTable -> craft + craft_exchange -> L1Craft -> protobuf crafting UI`

Key recovered paths:
- `recovery/normalized-src-vf/l1r/aj/C_NpcAction.java`
- `recovery/normalized-src-vf/l1r/ao/HtmlCraftTable.java`
- `recovery/normalized-src-vf/l1r/ao/CraftListTable.java`
- `recovery/normalized-src-vf/l1r/aq/L1Craft.java`
- `recovery/normalized-src-vf/l1r/be/S_HowManyMake.java`

## Important finding
381 XML crafting should NOT be assumed to require direct XML-engine porting.

Preferred direction:
- Convert suitable 381 `ItemMaking.xml / SingleItemMaking.xml` recipes into 850 native `craft / craft_exchange`.
- Use 850 high-version crafting UI where possible.
- Use `html_craft` only where NPC action / custom HTML behavior is still required.
- Port 381 Java module code only when its behavior cannot be represented by 850 native crafting/data systems.

This may reduce many crafting migrations from L3 to L2.

## Migration difficulty
- L1: 850 already has compatible core path; mostly DB/data mapping.
- L2: 850 has compatible framework; schema/action/menu conversion required.
- L3: independent 381 Java module + DB + NPC/action integration required.
- L4: packet/client/opcode/resource dependency or major protocol mismatch.

## Priority analysis set
1. `x_大師製作系統`
2. 381 XML ItemMaking / SingleItemMaking
3. `w_天m合成系統`
4. `w_變身卡片能力登入` / combination collection
5. `w_自動學習技能`
6. `w_血盟技能` / `w_血盟等級`
7. `w_威望*`
8. `w_道具附魔系統` / `w_道具升級*`
9. `w_全服怪物提升`

## Required evidence per module
Record:
- control switch / config source
- Java loader/table/event/item/NPC classes
- startup registration
- DB table DDL and populated rows
- NPC IDs and class/action handlers
- XML actions if any
- HTML/menu IDs/files if any
- output/material item IDs
- 850 equivalent framework
- client/protobuf/opcode/resource dependency
- migration mapping
- difficulty L1-L4
- blockers / validation plan

## First crafting assessment
`x_大師製作系統` is feature-rich and contains fields beyond basic recipes:
- NPC/action/category hierarchy
- display ordering
- chance / bonus item
- inheritance of material/enchant/bless/additional state
- fail return
- level/class limits
- HP/MP cost
- quantity input / batch settlement
- success/fail HTML
- global announcement

Therefore it must be split into:
A. fields directly representable by 850 `craft/craft_exchange`
B. fields requiring small 850 framework extension
C. fields requiring dedicated behavior

Do not port its entire 381 UI/core blindly.


## Latest module audit
- `w_道具狀態`: STATUS=BLOCKED, LEVEL=L3, audit=`modules/ITEM_STATUS_TIMED_BUFF_AUDIT.md`, commit=`637693a336b45d50068a0efd06b277b8e6d568b2`. 850-native timed/stat primitives exist; persistence and apply-once ownership are not closed.

- `w_指定道具賦予狀態`: STATUS=BLOCKED, LEVEL=L4, audit=`modules/DESIGNATED_ITEM_STATUS_AUDIT.md`, commit=`4ea2d90d9cb80433870080ad47393a731f1b12b3`. Shares stat-vector logic with timed item status but must keep equipped-item lifecycle separate.

- `w_變身箭矢特效`: STATUS=BLOCKED, LEVEL=L4, audit=`modules/TRANSFORM_ARROW_EFFECT_AUDIT.md`, commit=`f02fac7129cecb7dfbd84842a674dfe992935b29`. One poly->arrow-GFX mapping; downstream consumer/client mapping not closed.

- `w_變身卡片能力登入`: STATUS=BLOCKED, LEVEL=L4, audit=`modules/TRANSFORM_CARD_LOGIN_ABILITY_AUDIT.md`, commit=`50199cd6bb05a84dffcbdd294848cb09b0721003`. Quest-owned card unlocks and stat display are proven; login stat application/recompute remains unclosed.

- `w_變身卡片能力組合套卡`: STATUS=BLOCKED, LEVEL=L3/L4, audit=`modules/TRANSFORM_CARD_COLLECTION_SET_AUDIT.md`, commit=`3a2701266546e57f989c0de756295311dff67734`. Quest-owned card/set collection; full max vector proven and 850 requires idempotent collection recompute.

- `w_自訂變形卷軸`: STATUS=BLOCKED, LEVEL=L4, audit=`modules/CUSTOM_POLYMORPH_SCROLL_AUDIT.md`, commit=`e8d7db6274990589c8d8cfc9f5a6f9c0d6cbb898`. Donor callsite and server-side native poly mapping are proven; remaining blockers are action ownership, item 40308 semantics, and client resources.

- `w_城堡狀態_FAMILY`: STATUS=BLOCKED, LEVEL=L3, audit=`modules/CASTLE_STATUS_FAMILY_AUDIT.md`, commit=`e54c0e0c2ca219f3d7b9801dc5e63c5ef17bfd3b`. Login-time castle reward add and NPC access/buff path are proven; 850 needs authoritative castle-state recompute and safer cost/action ordering.

- `w_血盟能量怪物`: STATUS=PASS/SKIP, LEVEL=N/A, audit=`modules/CLAN_ENERGY_MONSTER_AUDIT.md`, commit=`1983a76942043a51ebbb9f3b8b82317bd8ea2ff4`. Current split SQL is empty; no migration work until source/runtime appears.
- `w_血盟等級_血盟技能_FAMILY`: STATUS=BLOCKED, LEVEL=L3, audit=`modules/CLAN_LEVEL_SKILL_FAMILY_AUDIT.md`, commit=`3a9b222f123706d1dd74f0f2fe997c181a97ad8a`. ClanState recompute is required; clan level requirement/persistence models exist but the actual live level-up executor remains unproven.

- `w_城戰獲勝獎勵`: STATUS=BLOCKED, LEVEL=L3, audit=`modules/CASTLE_WAR_VICTORY_REWARD_AUDIT.md`, commit=`513ce8ab735e9a2258a85d0e30385701bf4d6eb2`. Castle-war end callsite is proven; donor double-distributes rewards and table load callsite remains unproven, so 850 needs idempotent native war-end settlement.

- `w_負面魔法機率`: STATUS=BLOCKED, LEVEL=L3, audit=`modules/NEGATIVE_MAGIC_PROBABILITY_AUDIT.md`, commit=`41138a459226aa8011e8b761b3fb3b20e839fd87`. Runtime arithmetic is proven, including type24-28 low-MR sign reversal and Elf 70→35 / 71→55 discontinuity; skill_id and 850 formula mapping remain blocked.

- `w_物品掉落限制三`: STATUS=BLOCKED, LEVEL=L3, audit=`modules/DROP_LIMIT_THREE_AUDIT.md`, commit=`77737d6b509556427cd027814f51d7ddec6f240a`. Timer support is 720..899, strict-after gate, reset can leave stale cache, and quota arithmetic is safe only under valid nonnegative state.
- `MAP_HPR_MPR_FAMILY`: STATUS=BLOCKED, LEVEL=L3, audit=`modules/MAP_HPR_MPR_FAMILY_AUDIT.md`, commit=`c89fd24232a1f0bfd9457f0eeed184265b6c284e`. Multiple range rows per map are destroyed by last-write-wins HashMap; map4 loses 5/6 rows and map800 candidate rectangle is invalid.
