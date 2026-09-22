# 8.5 / 850 變身 UI 資料統整

> 整理日期：2026-09-22  
> 適用目標：L1JTW8.5 / 850 客戶端與伺服器資料追查  
> 來源：45 天堂論壇〈如何在資料庫追加變身卷軸? (8.5版)〉與本倉庫 850 DB 結構交叉核對  
> 論壇：https://lineage45.com/forum.php?mod=viewthread&tid=227653&highlight=8.5

## 1. 結論先行

8.5 的「變身」不是只改一張 DB 表。

至少可拆成三層：

```text
[伺服器變身定義]
polymorphs
  ↓
[道具 / 變身卷軸觸發]
etcitem + 核心使用道具邏輯
  ↓
[客戶端變身 UI]
Tile.idx
  ├─ PolymorphUI.xml
  ├─ Polymorphlist.xml
  └─ Polymorphlist2.xml
        ↓
Text.idx
  └─ desc-c.tbl
```

因此：

- DB 有變身資料，不代表 UI 一定會顯示。
- UI 有項目，不代表伺服器一定允許變身。
- 新增變身卷軸後出現「無法使用」，不應只檢查 `polyid`。
- 要新增完整可用的變身，必須同時確認 **Server DB / 核心觸發 / Client UI / Client 文字資源**。

---

## 2. 論壇提供的 8.5 客戶端 UI 線索

論壇回覆指出，8.5 類型客戶端的變身介面資料可由遊戲主程式資源中抽出。

### 2.1 Tile.idx

論壇指出要由：

```text
Tile.idx
```

抽出 poly / polymorph 相關 XML。

已點名的檔案：

```text
PolymorphUI.xml
Polymorphlist.xml
Polymorphlist2.xml
```

其中論壇使用者的客戶端版本是以：

```text
Polymorphlist2.xml
```

保存實際變身清單。

> 注意：不同 8.x 客戶端 revision 可能使用 `Polymorphlist.xml` 或 `Polymorphlist2.xml`，不能只假設固定其中一個。

---

## 3. PolymorphUI.xml

論壇描述：

```text
PolymorphUI.xml
= 變身介面本體 / UI 版面
```

並提到：

```text
CategoryList
```

是 UI 中的類別清單。

範例線索：

```text
CategoryBtn0
```

被推測對應「近距離特化」類別。

論壇範例還提到數值：

```text
14303
```

可能是按鈕所使用的圖檔 / UI 資源 ID。

### 目前可採用的判斷

```text
PolymorphUI.xml
├─ UI 版面
├─ 類別按鈕
├─ CategoryList
└─ 類別圖示 / UI sprite reference
```

但論壇沒有提供完整 schema，因此後續實作前仍應以實際抽出的 XML 為準。

---

## 4. Polymorphlist.xml / Polymorphlist2.xml

這兩份檔案是目前最重要的客戶端變身項目清單候選。

論壇範例：

```text
lv84 jin death knight
```

對應：

```text
84 級 真死亡騎士
```

論壇指出清單中的：

```text
desc="18692"
```

是顯示文字的描述 ID，而不是直接把中文名稱寫死在 XML。

因此可以整理為：

```text
Polymorphlist*.xml
├─ 變身項目
├─ 類別
├─ 等級 / 條件資訊
├─ 變身識別資訊
└─ desc ID
       ↓
    desc-c.tbl
```

---

## 5. Text.idx / desc-c.tbl

論壇指出：

```text
Text.idx
└─ desc-c.tbl
```

負責變身 UI 顯示文字。

論壇範例：

```text
desc="18692"
```

對應「真死亡騎士」的文字。

同時論壇特別提醒：某些 tbl 編輯工具顯示的「行號」可能與真正 desc ID 有偏移，因此：

```text
工具顯示行號 ≠ 一定等於 desc ID
```

不能直接拿編輯器畫面上的行號當正式 ID。

### 修改 UI 名稱時的正確追查順序

```text
Polymorphlist*.xml
  ↓ 找 desc="xxxxx"
Text.idx
  ↓
desc-c.tbl
  ↓
確認 xxxxx 對應文字
```

---

## 6. 850 倉庫已驗證：polymorphs 才是變身主表

本倉庫：

```text
db/無使用給AI檢查用資料庫DB/polymorphs.sql
```

850 schema：

```sql
CREATE TABLE polymorphs (
  name         varchar(45) PRIMARY KEY,
  note         varchar(45),
  polyid       int,
  minlevel     int,
  weaponequip  int,
  armorequip   int,
  isSkillUse   int,
  cause        int
);
```

欄位用途可先整理為：

| 欄位 | 用途 |
|---|---|
| `name` | 伺服器端變身名稱 / key |
| `note` | 中文備註或用途說明 |
| `polyid` | 變身外觀 / sprite 對應 ID |
| `minlevel` | 最低等級 |
| `weaponequip` | 可使用武器限制 bitmask |
| `armorequip` | 可使用防具限制 bitmask |
| `isSkillUse` | 變身後是否允許技能 |
| `cause` | 變身來源 / 使用條件相關值；需再由核心 loader 驗證語意 |

850 DB 已存在例如：

```text
lv84 jin death knight
polyid=13152
minlevel=84
```

所以論壇提到的 `lv84 jin death knight` 在 850 DB 中確實可找到對應伺服器資料。

---

## 7. 850 倉庫已驗證：pettypes 不是變身主表

論壇樓主曾表示自己在 `pettypes` 新增變身項目，但本倉庫 850 的：

```text
db/無使用給AI檢查用資料庫DB/pettypes.sql
```

schema 是：

```text
BaseNpcId
Name
ItemIdForTaming
HpUpMin / HpUpMax
MpUpMin / MpUpMax
EvolvItemId
NpcIdForEvolving
MessageId...
canUseEquipment
```

這是一張**寵物 / 馴服 / 進化資料表**，不是 850 的主要變身清單。

因此對本倉庫 850：

```text
變身資料優先查 polymorphs
不要把 pettypes 當 polymorph table
```

論壇最後提到「似乎要對應 pettypes name」只能視為該發文者當時的觀察，不能直接套用到本 850 DB。

---

## 8. 850 倉庫已驗證：etcitem 沒有 classname

本倉庫：

```text
db/無使用給AI檢查用資料庫DB/etcitem.sql
```

850 schema 主要有：

```text
item_id
name
unidentified_name_id
identified_name_id
item_type
use_type
material
weight
invgfx
grdgfx
itemdesc_id
...
value
save_at_once
```

**沒有：**

```text
classname
```

所以論壇回覆中「8.15 可以透過 classname 指定自訂 item handler」的做法，不適用於目前這套 850 DB schema。

論壇提到的核心路徑：

```text
src/com/lineage/data/item_etcitem/shop/PolyUserSet
```

屬於另一套有 `classname` loader 架構的核心範例，不能直接假設 850 有相同 dispatch 機制。

---

## 9. etcitem 與變身卷軸

論壇發文中的重要測試結果：

1. 新增一筆變身卷軸後會出現「無法使用」。
2. 把**原本能用的舊變身卷軸** `value` 改成新變身代碼，則可以變身。
3. 把新增卷軸的 `use_type` 改成 `sosc` 後，可以叫出變身 UI，但仍不保證能完成目標變身。

這個現象代表：

```text
value 本身不是唯一 gate
```

很可能還存在：

```text
item_type / use_type
→ item-use dispatch
→ 核心 handler / opcode path
→ polymorph lookup
→ client UI selection result
```

因此新增卷軸時，不應只複製 `value`。

---

## 10. 目前可建立的完整資料流

### A. UI 顯示

```text
Tile.idx
  ↓
PolymorphUI.xml
  ↓
CategoryList / CategoryBtn*
  ↓
Polymorphlist.xml / Polymorphlist2.xml
  ↓
desc ID
  ↓
Text.idx / desc-c.tbl
  ↓
遊戲內顯示名稱
```

### B. Server 變身合法性

```text
polymorphs.name
polymorphs.polyid
polymorphs.minlevel
polymorphs.weaponequip
polymorphs.armorequip
polymorphs.isSkillUse
polymorphs.cause
  ↓
核心 polymorph loader / lookup
  ↓
實際變身
```

### C. 變身卷軸

```text
etcitem.item_id
etcitem.item_type
etcitem.use_type
etcitem.value
  ↓
核心 item-use dispatch
  ↓
變身 UI / 變身 handler
  ↓
polymorph lookup
```

---

## 11. 若要新增「完整新變身」，建議檢查清單

### Client

- [ ] `Tile.idx` 是否含目標 `PolymorphUI.xml`
- [ ] 確認使用的是 `Polymorphlist.xml` 還是 `Polymorphlist2.xml`
- [ ] 新變身是否加入正確 Category
- [ ] UI 圖示 / button resource ID 是否有效
- [ ] `desc` ID 是否存在
- [ ] `Text.idx → desc-c.tbl` 是否已有對應文字
- [ ] XML 是否需要解密後才能編輯
- [ ] 修改後是否能正確重新封裝 / 加密

### Server DB

- [ ] `polymorphs.name`
- [ ] `polymorphs.polyid`
- [ ] `minlevel`
- [ ] `weaponequip`
- [ ] `armorequip`
- [ ] `isSkillUse`
- [ ] `cause`
- [ ] 新增卷軸的 `etcitem.item_type`
- [ ] 新增卷軸的 `etcitem.use_type`
- [ ] 新增卷軸的 `etcitem.value`

### Core

- [ ] 找到 `polymorphs` loader
- [ ] 找到 `etcitem` loader
- [ ] 找到 `use_type=sosc` 的 dispatch path
- [ ] 找到變身卷軸 item-use handler
- [ ] 確認 UI 選擇結果送回 server 的 packet / opcode
- [ ] 確認 lookup 是依 `name`、`polyid` 或其他 key
- [ ] 確認是否有 hardcoded 可用 item ID / whitelist
- [ ] 確認新增 item ID 不會落入 default / 無法使用分支

---

## 12. 尚未證實、不可直接當成 850 規格的部分

以下都只是在論壇中出現，尚未由本 850 核心完整驗證：

- `PolyUserSet` 是否存在於 850。
- `sosc` 在 850 的實際 dispatch 實作。
- 新增變身卷軸失敗是否有 hardcoded item-ID whitelist。
- UI 回傳 server 的 key 究竟是 `polymorphs.name`、`polyid` 或另一個 client-side ID。
- `cause` 的精確核心語意。
- `CategoryBtn0 = 近距離特化` 與圖檔 ID `14303` 是否完全適用本客戶端。
- 本 850 客戶端實際使用 `Polymorphlist.xml` 或 `Polymorphlist2.xml` 哪一份作 authority。

後續若要真正改 850 變身 UI，應以**實際 850 客戶端抽檔 + completed source/core call path**再做一次 authority closure。

---

## 13. 目前最值得抽出的客戶端檔案

優先級：

```text
P0  Tile.idx → PolymorphUI.xml
P0  Tile.idx → Polymorphlist.xml
P0  Tile.idx → Polymorphlist2.xml
P0  Text.idx → desc-c.tbl

P1  上述 XML 所引用的 icon / sprite / button resource
P1  與 polymorph 選擇封包相關的 UI script / table
```

如果之後要把 850 的變身 UI 做成可自由新增項目，這四份 P0 檔案應先完整抽取、解密、建立欄位對照，再進入核心修改。

---

## 14. 來源可信度標記

```text
[VERIFIED-850-DB]
= 已直接由本倉庫 main DB schema / rows 證實

[FORUM-OBSERVED]
= 論壇 8.5 使用者實機 / 編輯經驗

[UNVERIFIED-CORE]
= 尚未由本倉庫 completed source/core call path 證實
```

目前：

```text
polymorphs schema             = VERIFIED-850-DB
pettypes 是寵物表             = VERIFIED-850-DB
etcitem 無 classname          = VERIFIED-850-DB

PolymorphUI.xml               = FORUM-OBSERVED
Polymorphlist*.xml            = FORUM-OBSERVED
Text.idx → desc-c.tbl         = FORUM-OBSERVED

850 item dispatch / UI packet = UNVERIFIED-CORE
```

這份文件先作為 850「變身 UI / 變身卷軸 / polymorph」追查入口；後續有實際客戶端抽檔或核心驗證結果，再把 UNVERIFIED 項目逐項提升為 VERIFIED。
