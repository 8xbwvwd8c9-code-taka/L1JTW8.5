# L381 `w_道具輔助系統` → L1JTW8.5 / 850 審計

## 判定摘要

- `STATUS=BLOCKED`
- `LEVEL=L4`（本模組包含永久角色狀態、變身/GFX、EXP/lawful/karma/skill 擴展欄位；各 capability 的可遷移等級仍分開判定）
- `PRODUCTION_PORT=NO`
- 850 是 authority；L381 僅為 donor。本文沒有匯入 SQL、修改 live DB 或修改 production/core。

## Source / schema

來源：`L381/DB/381_DB_AI用/w_道具輔助系統_202609221205.sql`。

| 驗證項 | 結果 | 證據 |
|---|---|---|
| `SOURCE_ROWS` | `50` | 五個 INSERT block，共 50 個 value tuples。item IDs：92006–92019、92030–92051、92062、95331、240224–240225、240254–240263。 |
| `SOURCE_SCHEMA` | `NOT_PROVEN` | 檔案只有 INSERT；沒有 CREATE TABLE/ALTER TABLE。不能由此創造 850 DDL。 |
| `ACTIVE_FIELDS` | `PROVEN` | 由實際 rows 判定：`removeItem` 42 rows；`polyId/polyTime/讀取變身能力` 14 rows；`permanenceHp` 4；`permanenceMp` 4；永久 STR/DEX/CON/WIS/INT/CHA 共 18；`Exp` 3；`Gfx` 6；其餘 active 值見下表。 |
| donor table loader | `PROVEN` | `com.lineage.william.ItemUse.getData15b()` 執行 `SELECT * FROM w_道具輔助系統`，以欄名填入 runtime array。 |

沒有 donor CREATE schema，因此欄位型別、主鍵、索引、NULL/default、唯一性均 `NOT_PROVEN`。

## Donor entrypoint and direct call sites

### Entry → validation → consume → effects

1. Client item-use path：`C_ItemUSe.useItemRequest(...)` 驗證玩家/物品並依 item use type dispatch；donor direct caller `com.lineage.data.item_etcitem.add.Itemuse.execute(...)` 呼叫 `ItemUse.forItemUSe(pc, item)`。
2. `ItemUse.forItemUSe` 以 `itemInstance.getItemId()` 找到 first matching loaded row；未命中時沒有成功效果。
3. Validation 順序：class (`checkClass`) → level (`level`) → required item presence (`checkItem`)。
4. Consume：若 `removeItem != 0`，直接 `findItemId(item_id)`，再 `removeItem(item.getId(), 1L)`；這發生在所有效果前，且不是 transaction。required `checkItem` 只被檢查，donor code 沒有消耗它。
5. Effects 按固定順序執行：polymorph → permanent HP/MP → permanent stats → instant HP/MP → EXP → lawful → GFX → skill execution → karma。
6. Direct runtime call sites（donor）：`com.lineage.data.item_etcitem.add.Itemuse`；其餘 `ItemUse` 文字命中是 import/definition，不是第二個 effect entrypoint。`GameServer` 只初始化其他 `ItemUseEXTable`，不能視為本模組 call site。

### Consume-order / failure risks

- `removeItem` 在 polymorph、stat、EXP、GFX、skill 執行前；後續 `return` 可造成已消耗但未完成，例如 `hasSkillEffect(71)` 時 instant HP/MP 分支會送失敗訊息後 return，後續效果不再執行。
- `L1PolyMorph.doPoly`、`L1SkillUse.handleCommands` 可能拒絕/失敗，但 donor 沒有 rollback。
- 同一 item ID 若存在多列，loader 保留全部但使用時命中第一筆；schema 沒有證明 unique constraint。重複使用沒有 donor-side idempotency/locking。
- 所有 active rows 的 `removeItem=1` 是消耗使用中的 item；dice rows、92062、95331 的 `removeItem=0` 不消耗。這是資料事實，不推論 item stack semantics。

## Active capability matrix

| Capability | Active donor rows / result | 850-native comparison | Migration level / decision |
|---|---|---|---|
| class restriction | `0` in all 50 rows；未啟用 | 850 item-use validation 可作 native boundary | `L1 / no data mapping`; 不引入無資料的 restriction |
| level restriction | `0` in all rows；未啟用 | 850 player level API 可作 validation boundary | `L1 / no data mapping` |
| required item | `checkItem=0` in all rows；未啟用，且 donor 未消耗媒介 | no migration required | `L1 / no data mapping` |
| item consumption | 42 rows `removeItem=1`；逐次移除一個 source item | 850 inventory/item-instance removal is a native primitive, but atomic ordering contract must be defined | `L2`; generic adapter only if native executor lacks atomic effect transaction |
| polymorph | 14 rows；`polyId/polyTime` active，且 `讀取變身能力=1` | 850 has generic polymorph model/runtime (`polymorphs`, `L1PolyMorph` equivalent in recovered authority), not proven to contain these semantic forms | `L3`; map by name/GFX/time, never numeric ID |
| permanent HP/MP | 4 HP rows (+5/+10/+15/+20), 4 MP rows (+5/+10/+15/+30) | 850 character base/stat APIs exist as primitives; target persistence/login reapply contract not proven for these donor bonuses | `L3`; requires explicit permanent-bonus persistence, not direct base mutation |
| permanent stats | 18 rows: STR/DEX/CON/WIS/INT/CHA +1/+2/+3 as applicable | 850 base stat/character persistence primitives exist, but no proven donor-elixir ledger or login reapply mapping | `L3`; separate permanent bonus ledger required |
| instant HP/MP | no active rows (`hp=mp=0` in all rows) | native consumable effects exist, but no rows to migrate | `L1 / unused` |
| EXP | 3 rows: 92012=50000, 92013=20000, 92014=10000 | 850 native EXP gain/player status path is the preferred target | `L2`; semantic item mapping still required |
| lawful | no active rows (`Lawful=0`) | native lawful/player path exists | `L1 / unused` |
| GFX | 6 dice rows: 92006–92011, Gfx 3209..3204 | 850 packet/effect primitive exists, but GFX resource meaning must be verified | `L2`; semantic resource validation required |
| skill execution | no active rows (`Skills=''`, `SkillsTime=0`) | 850 skill executor/timer exists as primitive | `L1 / unused` |
| karma | no active rows (`Karma=0`) | no migration data | `L1 / unused` |
| `讀取變身能力` | active only on 14 polymorph rows | Semantics are donor-specific; field appears to set `user.setloginpoly(polyId)` before polymorph. 850 login/reapply semantics are not proven equivalent | `L3`; must define whether it is persistent login state or only a runtime marker |
| `polyTime` | 1200s for 92062; 1800s for remaining polymorph rows | 850 timed polymorph primitive exists, but duration and cancellation semantics need proof | `L2/L3` depending on semantic mapping |
| name | all rows | display metadata may map to 850 item definition; not an effect contract | `L1`; do not treat donor names as proof of item identity |

### Row groups

- GFX-only dice: 92006–92011; no consumption, no other effect.
- EXP scrolls: 92012–92014; consume one item, add EXP.
- Permanent MP: 92016–92019; consume one item, add base MP.
- Permanent HP: 92030–92033; consume one item, add base HP.
- Permanent stats: 92034–92051; consume one item, add one base stat and increment donor `elixirStats`.
- Timed polymorph: 92062, 95331, 240224–240225, 240254–240263; 92062/95331 do not consume, scroll rows consume.

## Persistence / login implications

Donor calls `addBaseMaxHp`, `addBaseMaxMp`, `addBaseStr/Dex/Con/Wis/Int/Cha`, and increments `getElixirStats()` in memory. This proves mutation at use time only. This targeted evidence does **not** prove that these methods persist a durable bonus ledger or that login reconstructs the mutation. Therefore permanent HP/MP/stat migration is blocked until 850's character persistence and login stat rebuild are inspected and a source-of-truth (existing elixir ledger or a new semantic bonus table) is selected. Do not write directly to base columns without that proof.

## 850-native comparison and minimum delta

850 authority has generic item use/consume, player stat/EXP/lawful, polymorph, packet/GFX and skill primitives, but no proven equivalent `w_道具輔助系統` loader or unified effect row contract. The minimum missing boundary is not a wholesale `ItemUse` port:

1. A typed definition loader/adapter with only active semantic fields, backed by a separately specified schema (currently blocked because donor CREATE is absent).
2. An atomic use transaction contract: validate → reserve/consume → execute; rollback or explicit failure handling for rejected polymorph/skill/effect paths.
3. A permanent-bonus persistence contract and login reapply hook for HP/MP/stats.
4. Semantic mappings for each item, polymorph form, and GFX effect; numeric IDs are not accepted as matches.
5. Reuse 850-native EXP, inventory, polymorph, stat and packet systems; only add adapters where the exact native contract is absent.

The single donor table should be split into semantic 850 targets: (a) consumable EXP, (b) permanent character bonus ledger, (c) timed polymorph definitions, and (d) GFX consumables. Empty capabilities (instant HP/MP, lawful, skill, karma, class/level/required-item) should not receive target rows.

## Blockers

- `SOURCE_SCHEMA=NOT_PROVEN`: no CREATE/ALTER SQL.
- 850 equivalent definitions for all donor item names, polymorph forms, and GFX 3204–3209 are not semantically proven; numeric IDs must not be copied.
- Permanent HP/MP/stat persistence and login reapply are not proven.
- Donor consume-before-effect ordering has partial-failure and duplicate-use risk; 850 transaction/idempotency contract is required.
- `讀取變身能力` / `setloginpoly` semantics are not proven as durable 850 state.
- Active rows reference donor item identities 92006…240263; 850 item existence/name/function must be mapped independently.

## Evidence paths

- Donor SQL: `L381/DB/381_DB_AI用/w_道具輔助系統_202609221205.sql`
- Donor runtime: `L381/src/com/lineage/william/ItemUse.java`
- Donor entry executor: `L381/src/com/lineage/data/item_etcitem/add/Itemuse.java`
- Donor packet entry: `L381/src/com/lineage/server/clientpackets/C_ItemUSe.java`
- 850 authority DB: `db/無使用給AI檢查用資料庫DB/`
- 850 recovered primitives: `recovery/normalized-src-vf/` (`GeneralThreadPool`, spawn/skill/stat/item-related classes)
