# 850 登入器 / 內掛開發報告

STATUS=ACTIVE  
BRANCH=`work/850-launcher-helper`  
TARGET=850 client launcher/helper only  
AUTHORITY=850  
DONORS=381,880  
SERVER_CORE_DOCS=EXCLUDED

## 目的

本支線只收納 850 登入器、850 客戶端內掛、外部設定檔、登入流程、客戶端記憶體/道具取得與 UI 相關研究。

不收納主線服務端核心修復、DB migration、server config、server Java core 或其他服務端文件。

## 權威與 donor 規則

```text
850 = Target / Authority
381 = 功能與 UI donor
880 = LinHelperZ 功能 / 設定 / 道具選擇 donor
```

禁止直接把 381/880 的記憶體位址、玩家基址、背包 offset、UseItem function、Skill function、HP/MP address 或 Buff address 套到 850。

381/880 只能用來回答：

- 有哪些功能
- UI 如何分組
- 設定如何保存
- 自動補血 / Buff / 變身 / 定時等邏輯如何表達
- 道具選擇器需要哪些欄位

850 的實際資料取得與操作入口必須重新驗證。

## 已確認登入器 baseline

### LoginWithoutUI

來源：

```text
analysis/l1jtw85-recovery
└─ 歐皇8.55c可連線登入器/
   └─ 歐皇8.55c可連線登入器/
      └─ 原始碼/LoginWithoutUI/
```

已確認：

- C# 原始碼完整存在。
- `Class1.cs` 啟動 `Lin.bin2`。
- 外部 `ip.ini`：
  - 第一行 = Server IP
  - 第二行 = Server Port
- `Class4.cs` 以程序記憶體寫入 IP / Port。
- 8.55 的固定地址只能作 baseline，不可直接套 850。

另有：

```text
登入器/8.5豋入用/
├─ LoginWithoutUI.exe
└─ Lin.bin2
```

此組優先作 850 login compatibility baseline。

## 已確認 880 donor

檔案庫：

```text
880C 乾淨登入端.zip
```

包含：

```text
LinLauncher.exe
LauncherDll.dll
LinLogin.bin
Encoder.exe
Encoder.ini
LinHelperZ.ini
list.txt
```

用途：

- 研究 LinHelperZ 設定模型
- 研究 UI 功能對照
- 研究物品選擇與自動補血需求
- 研究 launcher / helper 的模組分層

不得直接把 880 的 runtime address 當 850 address。

## 850 目標架構

```text
850 Launcher
│
├─ 850 Login Core
│  ├─ LoginWithoutUI
│  ├─ ServerName / IP / PORT
│  └─ 啟動 Lin.bin2
│
├─ 850 Helper Core
│  ├─ Player
│  ├─ HP / MP
│  ├─ Inventory / Item
│  ├─ Skill / Buff
│  ├─ Transform
│  ├─ Equipment
│  ├─ Monster / Player info
│  └─ UseItem / command bridge
│
└─ Helper UI
   ├─ 擴充
   ├─ 藥水
   ├─ 狀態
   ├─ 特殊
   ├─ 物品
   ├─ 喊話
   ├─ 熱鍵
   └─ 定時
```

## UI / 功能基線

381 與 880 畫面顯示功能高度重疊，850 第一版功能池：

### 擴充

- 全白天
- 海底抽水
- 降低 CPU
- 自動吃肉
- 自動修刀
- 顯示經驗值
- 顯示攻擊累積傷害
- 顯示掉落圖示
- 顯示玩家名稱
- 顯示怪物名稱
- 顯示遊戲時鐘

### 藥水

- 多段 HP 門檻
- 多個補血道具
- 保命吶喊
- 瞬移
- 洗魔
- 補血行數
- HP/MP 百分比判斷

### 狀態

- 取得目前 Buff
- 選擇要維持的 Buff
- 自動補狀態

### 特殊

- 自動變身
- 娃娃
- 自動解毒
- 提煉魔石

### 物品

- 讀取背包
- 列出背包道具
- 指定刪除
- 指定溶解

### 熱鍵

- F1-F4 / 指令群組
- 可編輯 command set

### 定時

- 指定物品或技能
- 秒數間隔
- 多組 timer

## 關鍵技術問題：850 如何抓遊戲內道具

850 必須建立自己的 inventory bridge。

最小資料鏈：

```text
Player pointer
  ↓
Inventory container
  ↓
Item object/list
  ↓
objectId
itemId
name
count
enchant
equipped
```

操作鏈：

```text
UI 選中道具
  ↓
取得 objectId
  ↓
850 原生 UseItem / 封包入口
  ↓
遊戲正常使用該物品
```

禁止只用 itemId 模擬使用；需要確認 850 真正操作所需的 object identity 與原生 call path。

## 開發工作包

```text
WP1  850 LoginWithoutUI 整理
WP2  外部 ServerName / IP / PORT
WP3  找 850 Player pointer
WP4  找 850 HP / MP
WP5  找 850 Inventory / Item list
WP6  列出目前背包道具
WP7  用 objectId 使用指定道具
WP8  自動喝水
WP9  Buff / 技能
WP10 其他 helper 功能
WP11 新版 UI
```

### 第一個主要 PASS gate

`WP6`：

登入 850 後，helper 必須能正確列出目前角色背包中的實際道具與數量。

範例：

```text
治癒藥水 × 155
強力治癒藥水 × 87
瞬間移動卷軸 × 23
勇敢藥水 × 9
```

只有 WP6 PASS 後，才把自動喝水、變身、娃娃、刪物、溶解、定時使用等功能往上接。

## 外部設定方向

預定統一為：

```ini
[Server]
Name=850測試服
IP=127.0.0.1
Port=2000

[Client]
Binary=Lin.bin2

[Helper]
Enabled=1
Config=LinHelperZ.ini
```

實際 key 名稱可在完成 850 runtime 驗證後調整，但必須維持：

- IP 外部可改
- Port 外部可改
- Server name 外部可改
- Helper 設定外部可改
- 不因換伺服器位址而重新編譯 EXE

## 支線收納規則

本支線允許：

```text
README.md
docs/launcher/**
docs/helper/**
docs/reverse/**
launcher config examples
850 launcher/helper source
850 launcher/helper test notes
```

本支線禁止：

```text
server Java core docs
server BUG repair ledger
DB migration docs
server SQL schema notes
server config authority docs
core-fix handoff
380/381/880 server-side migration reports
```

若研究過程需要參照服務端資料，只在報告內留下最小必要結論，不複製服務端文件進本支線。

## 下一步

```text
NEXT=850 Lin.bin2 / LoginWithoutUI runtime mapping
GOAL=定位 Player / HPMP / Inventory
FIRST_GATE=WP6 inventory listing
```
