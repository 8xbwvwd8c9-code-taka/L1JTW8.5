# 381 -> 850 DB / Module Migration

Base core authority: `completed/l1jtw85-core-fixes`  
Audit/document branch: `analysis/381-to-850-db-migration`

Purpose: analysis-only branch for 381 -> 850 DB/core/client migration difficulty and dependency mapping. Keep production BUG/core repair isolated from migration analysis.

---

## Canonical authority — 2026-09-24

### Target / donor relationship
- **850 is the only target/core authority.**
- **381 is donor/reference only.** It is used to identify desired behavior, DB semantics, hooks, lifecycle, NPC/menu/client requirements.
- Do **not** port 381 Java frameworks wholesale.
- For later implementation, rewrite/fuse the required behavior into the repaired 850 architecture.
- Any later production core implementation belongs only in `completed/l1jtw85-core-fixes`.
- This branch remains analysis/docs unless the user explicitly changes phase.

### Mandatory per-module audit order
Every 381 module must be traced in this order before final difficulty/decision:

1. **381 DB** — CREATE / INSERT / fields / active rows / data semantics
2. **381 core** — loader/Table/handler and real hook: login/equip/attack/death/timer/NPC/etc.
3. **381 control source** — DB / Config / XML / properties / hard-coded control
4. **850 core** — native equivalent, lifecycle owner, hook, scheduler, transaction/persistence path
5. **850 DB** — equivalent table/schema, native conversion path, or smallest required extension
6. **Client** — only if feature actually requires HTML/UI/Sprite/GFX/poly/icon/Text/opcode/protobuf/launcher resources
7. **Difficulty** — measure what 850 is missing, not donor framework size
8. **Decision** — `MIGRATE / ADAPT / MERGE / HOLD / SKIP`

### Empty SQL / zero rows policy
**Empty SQL / zero active rows is evidence of absent migration data, NOT evidence of an absent feature.**

No module may be marked `SKIP` solely because:
- the split SQL file is 0 bytes
- there are zero active rows
- CREATE schema is missing
- the exact table name has no direct Java source hit
- a simple English/Chinese alias search has no hit

For unresolved empty modules use:
```text
STATUS=HOLD
DATA_STATE=NO_ACTIVE_DATA
SOURCE_SCHEMA=NOT_PROVEN
RUNTIME_OWNER=NOT_PROVEN
CONTROL_SOURCE=NOT_PROVEN
850_EQUIVALENT=REQUIRED
DIFFICULTY=NOT_FINAL
CURRENT_MIGRATION=HOLD
```

Search non-matches mean **NOT_PROVEN**, never `FEATURE_ABSENT`.

### Valid SKIP reasons
`SKIP` must state an explicit authority reason:

```text
SKIP_USER_DECISION
```
User explicitly chose not to migrate the feature. Current example: `w_自動學習技能`.

```text
SKIP_DUPLICATE_NATIVE
```
Allowed only after the audit proves the intended 381 behavior is completely represented by an 850-native path and no donor-specific behavior remains to migrate.

### Client classification policy
Do not raise an entire module to L4 merely because some rows may require client resources.

Prefer separate fields:
```text
SERVER_LEVEL=L2/L3
CLIENT_DEP=...
CLIENT_GATE=L4_BLOCKED   # only when client work is actually confirmed/required
```

Examples:
- custom polymorph scroll: server behavior can be L2 while missing GFX/resource mapping remains a client gate
- transform arrow effect: server poly->GFX resolver can be L2 while actual GFX compatibility remains a client gate
- designated item status: server lifecycle can be L3 while only specific poly/title/icon/skin rows carry client work

---

## Migration difficulty — canonical definition
- **L1**: almost entirely DB/data/config mapping; no meaningful new server behavior.
- **L2**: 850 already has the native framework/lifecycle; conversion plus a small adapter/hook/lookup is required.
- **L3**: 850 needs a new/reworked persistent state owner, cross-event recompute, shared concurrency settlement, session/timer lifecycle, or deep runtime/combat behavior.
- **L4**: server work plus **confirmed** client UI/Sprite/protocol/special-resource work. When possible keep server level and client gate separate.

Important:
- 381 has a large Java class/framework != L3 by itself.
- missing SQL schema != L3 by itself.
- a loader/hook on an existing 850 lifecycle is normally L2 unless it adds new state/lifecycle semantics.

---

## Current baseline

### 381
- DB split source: `L381/main/DB/381_DB_AI用`
- 320 SQL files total observed in the working audit.
- 210 non-empty SQL files observed in the working audit.
- NPC action resources include:
  - `data/xml/NpcActions/ItemMaking.xml`
  - `data/xml/NpcActions/SingleItemMaking.xml`
  - `data/xml/NpcActions/Teleporter.xml`
- Config also includes multiple `config/其他控制端/*.properties` files.

### 850
- DB baseline: `db/8.5.sql`
- Split inspection DB: `db/無使用給AI檢查用資料庫DB`
- 850 repaired-core authority: `completed/l1jtw85-core-fixes`
- Native crafting paths include:
  1. legacy NPC/HTML: `C_NpcAction -> HtmlCraftTable -> html_craft`
  2. high-version: `CraftListTable -> craft + craft_exchange -> L1Craft -> protobuf crafting UI`

Key recovered paths include:
- `recovery/normalized-src-vf/l1r/aj/C_NpcAction.java`
- `recovery/normalized-src-vf/l1r/ao/HtmlCraftTable.java`
- `recovery/normalized-src-vf/l1r/ao/CraftListTable.java`
- `recovery/normalized-src-vf/l1r/aq/L1Craft.java`
- `recovery/normalized-src-vf/l1r/be/S_HowManyMake.java`

---

## Native-first crafting rule
381 XML crafting must not be assumed to require 381 XML-engine/core porting.

Preferred direction:
- convert suitable 381 `ItemMaking.xml / SingleItemMaking.xml` recipes to 850 native `craft / craft_exchange`
- use 850 high-version crafting UI where possible
- use `html_craft` where custom NPC/HTML interaction is actually required
- add only the smallest generic 850 extension for missing semantics
- port no donor framework whose behavior is already expressible by 850

For `x_大師製作系統`, split fields into:
A. directly representable by 850 `craft/craft_exchange`
B. small reusable 850 extensions
C. dedicated advanced behavior such as state inheritance/output mutation only where required

---

## Canonical reclassification ledger
The entries below supersede older per-module summary labels when they conflict. Individual specialized audits remain the detailed evidence source.

| Module/family | Canonical audit state | 850-first conclusion |
|---|---|---|
| `w_自動學習技能` | `SKIP_USER_DECISION` | user explicitly chose to skip; do not reopen unless requested |
| `w_火神裝備製作` | L2 | use 850 native craft + small extensions; do not port `L1Blend` wholesale |
| `w_升級獎勵*` | L2 | 850 already has level-up hook, quest persistence, item grant; add rule adapter/safe completion |
| `w_即時獎勵系統` | L3 | global quota claim + reward + personal completion require concurrency/exactly-once settlement |
| `w_掉落廣播系統` | L2 | drop-recipient event + filter/gate + native broadcast |
| `w_怪物死亡召喚` | L2 | monster-death event adapter to native spawn/broadcast/teleport; missing schema is a blocker, not L3 reason |
| `w_拉霸系統` | L2 | NPC action + cost + RNG + reward + short cooldown; no new persistent owner proven |
| `MAP_HPR_MPR_FAMILY` | **L2** | 850 native HP/MP regen timers exist; add DB rule loader + map/range lookup only |
| `w_怪物掉落隨機強化` | **HOLD** | empty data cannot skip; fixed enchant can be L2 native, random/weighted list may require L3 extension |
| `w_裝備持續特效` | **HOLD** | no active rows; runtime/control owner still NOT_PROVEN; difficulty NOT_FINAL |
| `w_裝備總加成能力` | **HOLD** | no active rows; runtime/control owner still NOT_PROVEN; difficulty NOT_FINAL |
| `w_物品融合db化` | **HOLD** | no active rows; runtime/control semantics still NOT_PROVEN; difficulty NOT_FINAL |
| `w_血盟能量怪物` | **HOLD** | no active rows; exact-name/alias search has no hit but runtime absence is NOT proven |
| `w_道具技能` | L2 | item-use adapter into 850 native skill engine |
| `w_道具輔助系統` | split by capability | EXP/GFX/native skill etc. can be L2; persistent permanent stat semantics can be L3 |
| item-upgrade family | split by semantics | ordinary material->new-item conversion may be L2 craft; in-place objid/template/state/failure/protection semantics are L3 |
| `w_自訂變形卷軸` | SERVER=L2 + client gate | use 850 native polymorph; client resource mapping tracked separately |
| `w_變身箭矢特效` | SERVER=L2 + client gate | server poly->arrow-GFX resolver is small; confirm client GFX separately |
| `w_指定道具賦予狀態` | SERVER=L3 + row-level client gate | equipped-item lifecycle/owner is server-side L3; only client-dependent effects receive client gate |
| `w_血盟等級` + `w_血盟技能` | L3 | one `ClanStateOwner`/authoritative recompute family; do not reproduce login-only additive drift |
| `w_鐘點怪物施放` family | L3 overall | use 850 scheduler/spawn infrastructure; no donor 10-second polling loop |
| `w_物品祝福系統` | L3 | permanent per-item state/stat behavior requires item-instance lifecycle integration |
| `w_物品分解系統` | L2 candidate | native-first item conversion/disassembly path; old runtime contract still needs authoritative mapping |
| `w_道具爆氣系統` | L3 | active toggle/session/resource-drain/stat lifecycle |
| prestige family | L3 | persistent prestige state/rank recompute/death adjustment/stat owner missing from 850 native baseline |
| `w_屬性強化系統` | L3 | per-weapon instance state + combat proc pipeline |
| `w_天M合成系統` | L3 | N-of-tier synthesis/pity/failure-return/card ownership semantics exceed normal craft |
| random-color/affix families | L3 | persistent item affix + equip/reroll lifecycle |
| `w_道具狀態` | L3 | timed modifier persistence/replacement/apply-once lifecycle |
| `w_物品掉落限制三` | L3 | persistent quota/next-drop state and concurrency/reset semantics |
| `w_物品時間限制` | L3 | expiry tied to item objid plus login/transfer/stack/cleanup lifecycle |
| castle status NPC/action | L2 | castle-owner gate + native skill/teleport/action path |
| castle status reward | L3 | castle ownership transitions require authoritative recompute/add/remove |
| castle-war victory reward | L3 | durable idempotent war-end settlement required |
| mining/excavation | L3 | cast session/cancel/movement/death/logout/owned-poly lifecycle |

### MAP_HPR_MPR correction details
850 repaired core proves existing regeneration lifecycle:
- `recovery/normalized-src-vf/l1r/bc/HpRegenerationTimer.java`
- `recovery/normalized-src-vf/l1r/bc/MpRegenerationTimer.java`
- `recovery/normalized-src-vf/l1r/ap/L1PcInstance.java`

Target structure for 381 multi-range data:
```text
mapId -> List<RectangleRule>
```
Do not reproduce donor last-write-wins `Map<Integer, one record>` behavior, which destroys multiple rules on the same map.

---

## Shared modifier architecture direction
Where multiple modules add character stats, share the modifier representation but not lifecycle ownership.

Current design direction:
```text
Shared StatModifierDefinition
  TimedBuffOwner
  EquippedItemOwner
  TransformationOwner
  CollectionOwner
  CastleStateOwner
  ClanStateOwner
```

Each owner remains authoritative for apply/remove/recompute of its own lifecycle.

---

## Required evidence per module
Record at minimum:
- DB DDL/data/row count
- control switch/config source
- Java loader/table/event/item/NPC classes
- startup registration
- real runtime hook/callsite
- NPC IDs and class/action handlers
- XML/HTML/menu dependencies
- output/material/item/NPC/skill identity mapping
- 850 equivalent core/framework
- 850 DB mapping/conversion
- client/protobuf/opcode/resource dependency
- server difficulty and client gate separately where useful
- blockers
- validation plan
- final migration decision and its authority reason

---

## Provisional summary warning
Any older count/table that grouped modules by the previous donor-centric L1/L2/L3/L4 labels is **PROVISIONAL / STALE** until every module has been reconciled against the canonical rules above.

Do not use old summary counts for implementation planning until the per-module reclassification pass is complete.

When conflicts exist, precedence is:
```text
850 repaired-core reality
  -> latest specialized module audit
  -> latest explicit user decision
  -> older summary/history
```

---

## Current audit queue
Complete the 320-item SQL census before selecting migration candidates:
1. use [`inventory/SQL_FULL_INVENTORY.csv`](inventory/SQL_FULL_INVENTORY.csv) as the canonical per-SQL ledger
2. use [`inventory/SQL_MODULE_FRAMEWORK.md`](inventory/SQL_MODULE_FRAMEWORK.md) for the readable 320-item framework
3. use [`inventory/SQL_SCAN_PROGRESS.md`](inventory/SQL_SCAN_PROGRESS.md) for completion counts
4. trace each item's 381 runtime/control owner and compare its 850 DB/core equivalent
5. classify exact duplicate, semantic duplicate, partial overlap, missing target capability, client gate and difficulty
6. select migration candidates only after all 320 items have final comparison records

Current mechanical baseline:
```text
381_SQL_TOTAL=320
381_SQL_NON_EMPTY=210
381_SQL_EMPTY=110
850_CREATE_TABLES=99
850_EXACT_NAME_MATCH=46
850_EXACT_COLUMN_SET=6
850_381_COLUMNS_SUBSET=6
850_COLUMN_DIFFERENCE=24
850_SOURCE_COLUMNS_NOT_PROVEN=10
SEMANTIC_COMPARISON_PENDING=320
MIGRATION_SELECTION=NOT_STARTED
```

Production core modification remains out of scope for this branch.
