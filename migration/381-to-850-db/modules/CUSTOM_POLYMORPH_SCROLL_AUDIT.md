# `w_自訂變形卷軸` 深度稽核

## 結論

`DONOR_RUNTIME_PORT_REQUIRED=NO`。381 的功能可由 850 原生 polymorph 加 action/item-cost mapping 表達；不可直接啟用，因 action owner、40308 語義與 client resource 尚未全部證明。

## 來源與重複列

證據：`I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_自訂變形卷軸_202609221205.sql`，以及 `DB\atu381_0906.sql`。

* `SOURCE_ROWS=6`
* `UNIQUE_ACTION_COUNT=5`
* `UNIQUE_POLY_COUNT=5`：13216, 13217, 13218, 13219, 13220
* `DUPLICATE_ROWS=1`
* `DUPLICATE_ACTIONS=tw wizard plus`
* `DUPLICATE_POLY_IDS=13219`
* `INSERT_COLUMN_COUNT=6`; `COLUMN_ALIGNMENT=PROVEN`; `CREATE_SCHEMA=NOT_PROVEN`

兩筆 `tw wizard plus` 均為 `13219,62,Sosc_Japan 防LH變身,40308,1`，屬 byte/semantic exact duplicate，分類 `REDUNDANT_DATA`，不得靜默 dedupe。

## `ItemActionPoly` loader/runtime

證據：`I:\L381\Atu-381伺服器端主\src\com\lineage\william\ItemActionPoly.java`。

`LOAD_MODE=lazy first-use`；`CACHE_MODE=static ArrayList + one-shot flag`；`RELOAD_SUPPORT=NO`；`THREAD_SAFETY=UNSAFE/NOT_PROVEN`；`ERROR_HANDLING=swallow all Exception`；`ORDER_PRESERVATION=ResultSet append order`。

精確控制流：符合 action 與 level 後，`checkItem(itemId)` → `consumeItem(itemId)` → `L1PolyMorph.doPoly(polyId,1800,1)` → `S_CloseList`；沒有 `return/break`，迴圈繼續，方法最後固定 `return false`。因此 duplicate action 在有兩個 40308 時會消耗兩次並套用兩次；`DUPLICATE_CONSUME_RISK=YES`、`DUPLICATE_POLY_APPLY_RISK=YES`、`DUPLICATE_ROW_IMPACT=double execution`。設定的 count 未傳給 check/consume，故 count 欄位實際未生效。

## Callsite/action origin

唯一直接 callsite 已證明：381 `L1ActionPc.action(String cmd,long amount)` → `ItemActionPoly.forNpcQuest(cmd,pc)`。`CALLSITE=PROVEN`、`CALLER=L1ActionPc.action`、`RUNTIME_REACHABILITY=PROVEN in donor; NOT_PROVEN in 850`。五個 action 的來源只在 DB row 中出現，未找到 HTML/NPC owner：`ACTION_ORIGIN=BLOCKED`；`NPC_REQUIRED=not required by method signature`、`HTML_REQUIRED=not proven`、`ITEM_REQUIRED=40308 x1`。

## 850 polymorph mapping

850 authority：`origin/completed/l1jtw85-core-fixes:db/8.5.sql`。

| donor action | donor ID | 850 native name | 850 ID | status |
|---|---:|---|---:|---|
| `tw shogun plus` | 13216 | `大名` / `branch shogun seven` | 13216 | `EXACT_NATIVE_MATCH`, client pending |
| `tw samurai plus` | 13217 | `武士` / `branch samurai seven` | 13217 | `EXACT_NATIVE_MATCH`, client pending |
| `tw archer plus` | 13218 | `姬武者` / `branch archer seven` | 13218 | `EXACT_NATIVE_MATCH`, client pending |
| `tw wizard plus` | 13219 | `陰陽師` / `branch wizard seven` | 13219 | `EXACT_NATIVE_MATCH`, client pending |
| `tw ninja plus` | 13220 | `忍者` / `branch ninja three` | 13220 | `EXACT_NATIVE_MATCH`, client pending |

850 另有 item 640307--640311 指向上述 IDs。數字相等只證明 server identity，不證明 client resource。850 native `L1PolyMorph.doPoly/undoPoly` 是 replacement/lifecycle owner；donor 的 1800 秒與 mode 1 可由既有 native contract 表達。`DURATION_COMPAT=PROVEN`、`MODE_COMPAT=PROVEN`、`REPLACEMENT_COMPAT=PROVEN`。

## level/item/order

`DONOR_MIN_LEVEL=62`；850 五筆 native min level 均為 1；`RULE_RELATIONSHIP=donor custom rule stricter`，不能由 native min-level 取代。Donor order 是 `check -> consume -> doPoly`，無 refund：`CONSUME_BEFORE_EFFECT=YES`、`PARTIAL_FAILURE_RISK=YES`、`REFUND_PATH=NONE`。

Donor item `40308 x1`。850 `etcitem` 定義 `40308=金幣`；donor item 名稱/DDL 未由本次證據證明，故 `SEMANTIC_MATCH=NOT_PROVEN`、`DIRECT_ID_SAFE=NO`。不得因數字相等直接遷移。

## native/status/arrow/client

`UNIQUE_DONOR_BEHAVIOR=action string routing + level 62 + item cost + fixed 1800s + close-list`；沒有獨立 stat/persistence lifecycle。`w_變身賦予狀態_FAMILY` 已證明 `L1PolyMorph` 負責 replacement remove/apply；`TRANSFORM_STATUS_DEP=CHECKED`、`SHARED_LIFECYCLE_OWNER=L1PolyMorph`。`w_變身箭矢特效` targeted data 只有 polyid 6611，故 `ARROW_EFFECT_DEP=NO_CURRENT_MAPPING`。

五個 ID：`SERVER_POLY_DEFINITION=PROVEN`、`CLIENT_GFX_REQUIRED=YES`、`CLIENT_RESOURCE_MAPPING=NOT_PROVEN`、`CUSTOM_HTML_REQUIRED=NOT_PROVEN`；`CLIENT_DEP=BLOCKED`。

## proven bugs/classification

已證明：duplicate row；無 return/break 導致重複消耗/套用；count 欄位未傳入 check/consume；consume 在 doPoly 前且無 refund；DB exception swallowed；lazy cache 無 reload/同步。遷移不得重現上述控制流。未證明 action 與其他 command collision。

`DATA_LEVEL=L1`；`SERVER_RUNTIME_LEVEL=L2`；`CLIENT_LEVEL=L4`；`FINAL_LEVEL=L4/BLOCKED`。建議 `Option C`：850 native polymorph + 小型 generic action/item-cost adapter；不移植 `ItemActionPoly`，不讓 scroll module 擁有 transform stat。未修改 production core/DB/client。
