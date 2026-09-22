# L381 Transformation Card / Collection → 850 深度遷移審計

## 最終判定

```text
STATUS=PASS
MODULE=TRANSFORMATION_CARD
LEVEL=L4
OWNERSHIP=character_quests.quest_id/quest_step（已證實）
CORE_DEP=YES
DB_DEP=YES
HTML_CLIENT_DEP=YES
TIANM_ADAPTER_READY=YES
TARGET_DOC=migration/381-to-850-db/modules/TRANSFORMATION_CARD_DEEP_AUDIT.md
COMMIT=pending
BLOCKERS=850 無等價 card/collection framework；card HTML/action 未確認存在；381 card poly IDs 在 850 polymorphs 未命中；需明確遷移 381 quest IDs 與 client HTML
```

## 審計來源

- L381 source：`I:\L381\Atu-381伺服器端主\src`
- L381 DB package：`I:\L381\Atu-381伺服器端主\DB\381_DB_AI用`
- 850 DB authority：`I:\L1JTW8.5\db\無使用給AI檢查用資料庫DB`
- 850 core authority：Git ref `origin/completed/l1jtw85-core-fixes`，主要檢查 `recovery/normalized-src-vf`
- 本次未讀取 archive、未執行 full suite、未修改 production core/DB，也未審計 `w_天m合成系統` 內部。

## DB tables 與資料模型

### `w_變身卡片能力登入`

來源：`I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_變身卡片能力登入_202609221205.sql`。

該 package 以 INSERT 提供卡片定義；source `ACardTable.load()` 對同名表執行 `SELECT *`，讀取欄位：

`獲得能力時的訊息`、`出現顯示能力頁面名稱`、`對話檔指令`、`任務編號`、`變身編號`、`變身時效`、`變身消耗道具編號`、`變身消耗道具數量`，以及力量/敏捷/體質/智力/精神/魅力、防禦、HP/MP、回血/回魔、近/遠距離傷害與命中、物理/魔法減免、魔攻/魔法命中、MR、火/水/風/地屬性防禦。

Targeted parse 證實共有 64 張 card row，ownership quest IDs 為連續 `5001–5064`。卡片使用的變身消耗道具 ID 在目前資料都是 `40308`。

### `w_變身卡片能力組合套卡`

來源：`I:\L381\Atu-381伺服器端主\DB\381_DB_AI用\w_變身卡片能力組合套卡_202609221205.sql`。

`CardSetTable.load()` 對同名表執行 `SELECT *`，把：

- `流水號` → set definition ID
- `需求的變身卡編號` → comma-separated card IDs
- `需求的玩家任務編號` → comma-separated ownership quest IDs
- `需求的變身卡名稱` → display names
- `任務紀錄編號` → set completion/bonus quest ID
- 同一組能力欄位 → `CardPolySet` bonus

目前 package 有 10 個 set rows；set quest IDs 為 `5100–5109`。前五組對應階段卡片，後五組對應真/偽變身組合。套卡能力不是由 item ownership 推導，而是由 `character_quests.quest_step` 對 `任務紀錄編號` 與 `需求的玩家任務編號` 判定。

### Ownership persistence：精確證明

`CardBookCmd`、`C_LoginToServer.getCard()` 與 `CardSetTable` 都使用：

```java
pc.getQuest().get_step(card.getQuestId()) != 0
pc.getQuest().get_step(cards.getQuestId()) != 0
pc.getQuest().get_step(cards.getNeedQuest()[j]) != 0
```

`L1PcQuest.set_step(quest_id, step)` 在新增/修改時呼叫 `CharacterQuestReading.storeQuest/updateQuest`；`CharacterQuestTable` 對 `character_quests` 執行：

```sql
INSERT INTO character_quests (char_id, quest_id, quest_step, ...)
UPDATE character_quests SET quest_step=? ...
  WHERE char_id=? AND quest_id=?
```

因此：

- card ownership = `character_quests` 中 `quest_id=5001..5064` 且 `quest_step != 0`
- set ownership/activation = `quest_id=5100..5109` 且 `quest_step != 0`
- `quest_step` 的非零性是本模組實際使用的 membership predicate；source 沒有要求特定 step 值
- `L1PcInstance._CardId` 只保存目前 UI 選取的 card，初始化為 0，不是持久化 ownership
- item inventory 不是 card ownership store；`40308` 只在變身使用時被消耗

850 AI-check DB 確認存在一般 `character_quests` 與 `character_quests_new` 表，但沒有 card-specific rows/framework。850 `polymorphs.sql` 也只是一般 polymorph table。

## Runtime trace（分離功能責任）

### 1. Card ownership

`CardBookCmd.PolyCmd()` 以對話 command 找到 `ACard`，寫入 `pc.setCarId(i)`；UI 顯示 `[已存入]` 或 `未偵測[存入]`，條件是 `get_step(card.getQuestId()) != 0`。本段只讀 quest state，不寫入 ownership。

本次在指定六個類別與相關 source 中，沒有找到由 card UI 直接寫入 `5001..5064` 的通用新增方法；寫入 ownership 的共用邊界是 `L1PcQuest.set_step`/`CharacterQuestReading`。因此 card reward/grant producer 必須另行接到這個 persistence API，不能靠 card SQL 自動產生 ownership。

### 2. Card stat bonuses

`GameServer.java:501-502` 啟動 `CardSetTable.get().load()`、`ACardTable.get().load()`。

`C_LoginToServer.java:615-644` 逐張卡檢查 `quest_step != 0`，將 ACard 的所有 stat fields 加到 player：attributes、AC/HP/MP、regen、melee/ranged damage/hit、damage reduction、magic stats、MR、element resistances。

### 3. Combo/set bonuses

`C_LoginToServer.java:647-673` 對 `CardPolySet` 使用同樣的 `quest_step(cards.getQuestId()) != 0` predicate 並累加 set fields。

`CardBookCmd.CardAllSet()` 同時聚合已登錄單卡與已完成套卡；`CardBookCmd.CardSet()` 逐組檢查 `getNeedQuest()` 全部非零後才向 UI 輸出套卡能力。因此「登入時套卡能力」與「HTML 顯示套卡完成」是兩條都依賴 quest IDs 的路徑。

### 4. Card activation/use

`CardBook.java` 是 item executor：使用 card book 時清除選取 card (`setCarId(-1)`) 並發送 `S_NPCTalkReturn(..., "card_01")`。

`L1ActionPc` 將一般 client command 依序交給 `CardBookCmd.get().Cmd()` 與 `CardBookCmd.get().PolyCmd()`。`Cmd("polycard")` 會：

1. 取目前 `pc.getCardId()`。
2. 要求該卡 quest step 非零。
3. 若 `polyid != 0`，呼叫 `L1PolyMorph.doPoly(pc, polyid, polytime, 1)`。
4. 若 `polyitemid != 0`，先檢查並消耗 `polyitemcount`。

故 activation 不改 ownership；它只使用已持有卡片並可能消耗變身道具。

### 5. Polymorph path

`ACard` 保存 `變身編號`、`變身時效`、消耗 item ID/count；`CardBookCmd` 將這些值直接傳入 `L1PolyMorph.doPoly`。本模組不是另建 polymorph engine，而是依賴 850 的既有 server polymorph path，但必須補齊每個 poly ID 在 target `polymorphs`/runtime 的存在性與 client GFX 支援。

## HTML / action / client dependency

已證實的 HTML/action names：

- `card_01`：`CardBook.execute()` 開啟 card book 主頁
- `card_0`：`CardBookCmd.PolyCmd()` 顯示單卡詳情、能力與存入狀態
- `card_10`：`CardBookCmd.CardSet()` 顯示套卡需求與能力
- `card_11`：`CardBookCmd.CardAllSet()` 顯示已聚合總能力
- `a1`–`a64`：DB 的 `對話檔指令`，由 `PolyCmd()` 選取對應 card
- `cardset`、`cardset2`、`polycard`：由 `L1ActionPc` 分派給 `CardBookCmd`

L381 source 沒有在伺服器端 source 內提供 HTML 檔內容；搜尋到的 `card_01/card_0/card_10/card_11` 只證明 server packet action name。指定 850 workspace 也沒有找到這些 card HTML resource。故 `HTML_CLIENT_DEP=YES`，且不能假設 850 client/HTML 已存在。

不要把 `VIP_Card_01` 等 `add.VIP` item executors 視為 card book framework；它們是獨立商城/VIP 類別，本審計不合併。

## Required item IDs / polymorph IDs against 850

### Item ID

所有 `w_變身卡片能力登入` rows 的 `變身消耗道具編號` 都是 `40308`。850 `etcitem.sql` 有 `40308`，但該 row 是「金幣」，不是 card-specific token；因此 numeric ID 存在，但 semantic match 未證明。遷移前必須決定是否保留以金幣作變身消耗，或新增/映射專用 item。

### Polymorph IDs

381 card SQL 的 distinct poly IDs 為：

```text
13600, 13604, 16421, 16422, 18601, 18605, 18606, 18610, 18611,
19000-19015, 20000-20010, 20014-20015, 20019-20020, 20024-20025,
20029-20030, 20034, 20040, 20044-20045, 20049, 20058, 20062,
20100, 20104, 20120, 20124, 20864, 20868-20869, 20873,
21635, 21639, 21646, 21650
```

Targeted exact-ID checks against 850 `polymorphs.sql` did not find these card poly IDs. This is a hard migration blocker for activation; a generic `L1PolyMorph` hook alone cannot make missing polymorph definitions/client visuals work.

## 850 equivalent framework

850 target source exact search found no `ACardTable`, `CardSetTable`, `CardPolySet`, `CardBook`, `CardBookCmd` or `w_變身卡片` framework. 850 does have generic item type entries such as `ccard`, `vcard`, `wcard` in `ItemTable`, plus general `character_quests` and `polymorphs` tables. Those are primitives, not an equivalent card collection system.

因此這不是 L1 DB-only migration：需要 card tables/adapter、quest ownership mapping、login stat aggregation、command routing、HTML resources，以及 poly/item validation。由於 client HTML/action 未確認存在，分類為 L4，而非僅 server-side L3。

## TianM adapter boundary（不審計 TianM 內部）

可提供 adapter，讓 TianM 只呼叫下列穩定介面：

- `isOwned(characterId, cardQuestId)`
- `grantCard(characterId, cardQuestId, step)` → 透過 `L1PcQuest.set_step`
- `isSetActive(characterId, setQuestId)`
- `recalculateCardStats(player)`

此設計讓 TianM 只消費 card ownership/activation contract，不直接依賴 `CardBookCmd`、HTML command 或 SQL table name。故 `TIANM_ADAPTER_READY=YES` 表示邊界可定義，不表示 TianM 已整合或本次已審計其內部。

## Independent package design

1. `transformation-card-db`：兩張 card SQL、明確 DDL、seed、ID validation；可獨立 import/remove。
2. `transformation-card-domain`：`ACard`/`CardPolySet` immutable definitions、quest-ID ownership mapping、set requirement evaluation。
3. `transformation-card-runtime-adapter`：登入 stat recomputation、ownership grant/query、poly activation；依賴 850 的 quest/polymorph/player stat API。
4. `transformation-card-ui`：`card_01/card_0/card_10/card_11` HTML 與 action command；沒有 UI package 時，server card data 不應宣稱功能完整。
5. Optional external adapter：TianM 只依賴 domain/runtime adapter，不依賴 UI 或 SQL。

## Blockers

- 850 沒有等價 card/collection framework。
- 850 未發現 `card_01/card_0/card_10/card_11` HTML；client resource/protocol completion 未證明。
- 381 的 64 card ownership IDs 與 10 set IDs 必須遷移到 850 `character_quests`，且需避免與既有 quest ID 衝突。
- 381 card poly ID 集合在 850 `polymorphs.sql` 未命中；需補定義、映射或明確淘汰卡片。
- `40308` 在 850 存在但語意是金幣，不能直接視為專用卡片消耗道具相容。
- source 沒有在本模組內證明哪些外部流程負責第一次 `set_step(5001..5064)`；需在實作前指定 grant producer/adapter。
