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

## 2026-09-22 Reverse checkpoint

```text
WP1=PASS_ANALYSIS
WP2=DESIGN_READY
WP3=READY_FOR_LOCAL_SCAN
WP4=READY_FOR_RUNTIME_CORRELATION
WP5=READY_FOR_RUNTIME_CORRELATION
WP6=BLOCKED_BY_WP3-WP5
```

新增：

- `docs/850-launcher/reverse/WP3-WP5_STATIC_PLAN.md`
- `tools/scan_850_client.ps1`

目前不接受任何 381/880 address 作 850 authority。850 `Lin.bin2` 必須輸出自己的 hash / PE / strings / runtime correlation evidence 後，才可建立 Player、HP/MP、Inventory mapping。

## 2026-09-22 850 Client static scan result

User-local authoritative client path:

```text
I:\8.50c客服端
```

Four primary binaries were scanned successfully:

| File | Size | Arch | PE | Sections | SHA256 |
|---|---:|---|---|---:|---|
| Lin.bin2 | 7,488,696 | x86 | PE32 | 6 | FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4 |
| Lin.bin | 8,219,320 | x86 | PE32 | 6 | 6CC9F57862926FDFDC983BA1B027BE3267FBDBF1A0E8A98D966EB0DF0D7E1B78 |
| Lineage.exe | 1,532,600 | x86 | PE32 | 4 | 219E09156A57C84ED3EA87C74AFBE1BBCAB84EF7E2EF2531A898C39588D962A4 |
| LoginWithoutUI.exe | 67,584 | x86 | PE32 | 3 | 378CC782B2D7143A07DCA9F5453A37C693D4F30E9AAD2DB65B2CA516E77FA93A |

Conclusions:

```text
STATIC_SCAN=PASS
850_CLIENT_ARCH=x86/PE32
LIN_BIN_EQ_LIN_BIN2=NO
LIN_BIN2_LOGIN_BASELINE=CONFIRMED_BY_LOGINWITHOUTUI_SOURCE
PLAYER_POINTER=NOT_PROVEN
HP_MP=NOT_PROVEN
INVENTORY=NOT_PROVEN
```

The first string scan produced many false positives because random packed/compressed bytes matched short ASCII patterns such as `hp` / `mp`. Those results are not accepted as Player/HP/MP evidence.

Next action is binary structural diff between `Lin.bin` and `Lin.bin2`, then runtime correlation.


## 2026-09-22 Lin.bin vs Lin.bin2 structural diff

Authoritative local comparison:

```text
A=Lin.bin
B=Lin.bin2
A_SIZE=8219320
B_SIZE=7488696
A_IMAGEBASE=0x400000
B_IMAGEBASE=0x400000
A_ENTRY_RVA=0x01C93000
B_ENTRY_RVA=0x0198F000
A_SECTIONS=6
B_SECTIONS=6
COMMON_BYTES=7488696
DIFF_BYTES_COMMON=7454397
TAIL_BYTES=730624
TOTAL_DIFF_BYTES=8185021
DIFF_RUN_COUNT=30647
```

Observed PE section layouts differ materially:

```text
Lin.bin
first code/data span VSize=24469504
.rsrc RVA=0x01757000
.idata RVA=0x017C1000
entry section RVA=0x01C93000

Lin.bin2
first code/data span VSize=21184512
.rsrc RVA=0x01435000
.idata RVA=0x0149F000
entry section RVA=0x0198F000
```

Conclusion:

```text
LIN_BIN2_IS_SIMPLE_PATCH_OF_LIN_BIN=NO
SAME_BUILD_LAYOUT=NO
BINARY_NEAR_DIFF_STRATEGY=REJECTED
850_LOGIN_RUNTIME_AUTHORITY=Lin.bin2
LIN_BIN_ROLE=SEPARATE_BUILD_OR_UPDATE_VARIANT
```

Rationale: more than 99% of the shared byte range differs, entry RVA differs, and section RVAs/sizes are materially shifted. Therefore raw byte-diff translation from Lin.bin to Lin.bin2 is not a safe mapping method.

Next: runtime correlation must target Lin.bin2 directly.


## 2026-09-22 850Launcher v0.1 implementation

```text
IMPLEMENTATION=PASS_SOURCE
PROJECT=launcher/850Launcher
FRAMEWORK=.NET Framework 4.0
PLATFORM=x86
RUNTIME_BRIDGE=UNMAPPED_BY_DESIGN
DONOR_ADDRESSES=NONE
```

Implemented:

- external Server Name / IP / Port;
- persistent `launcher.ini`;
- persistent `helper.ini`;
- writes legacy two-line `ip.ini` for the accepted LoginWithoutUI flow;
- launches `LoginWithoutUI.exe` with optional UAC elevation;
- 850 helper UI shell with Extend / Potion / State / Special / Items / Hotkeys / Timer tabs;
- inventory list UI contract;
- HP/MP display contract;
- runtime bridge interface with explicit UNMAPPED implementation;
- x86 build project and PowerShell build script with .NET Framework v4 MSBuild/csc fallback.

Safety/correctness gate:

```text
NO_FAKE_PLAYER_POINTER
NO_FAKE_HPMP_ADDRESS
NO_FAKE_INVENTORY_ADDRESS
NO_381_880_RUNTIME_ADDRESS
NO_USEITEM_BEFORE_WP5_WP6
```

The v0.1 executable shell can be built now. Helper actions that require runtime mappings remain disabled until WP3-WP6 evidence passes.


## 2026-09-22 local build validation

User-local build result:

```text
BUILD=PASS
ERRORS=0
WARNINGS=10
OUTPUT=launcher/850Launcher/bin/Release/850Launcher.exe
ELAPSED=00:00:00.50
```

Observed warnings:

- MSB3644: .NET Framework 4.0 targeting/reference assemblies pack is not installed.
- CS0649: unmapped runtime bridge fields remained at defaults.

Interpretation:

```text
BUILD_GATE=PASS
MSB3644=NON_FATAL_ON_THIS_HOST
RUNTIME_MAPPING=STILL_UNMAPPED
```

The compiler resolved framework assemblies from the GAC and produced the executable successfully. Runtime bridge field warnings were subsequently eliminated by explicit initialization.


## 2026-09-22 850Launcher v0.2 runtime attach scaffold

```text
STATUS=PASS_SOURCE
RUNTIME_PROCESS_DETECT=IMPLEMENTED
TARGET_PATH=<launcher dir>\Lin.bin2
SHA256_AUTHORITY=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
MEMORY_READ=NOT_YET
MEMORY_WRITE=NO
```

Implemented:

- scans running processes for the exact deployed `Lin.bin2` path;
- reads PID and main module base when accessible;
- verifies the live executable SHA256 against the authoritative 850 binary;
- reports connected/unconnected state in the helper UI;
- keeps HP/MP/inventory values unmapped until WP3-WP5 proof exists.

This advances the launcher from UI-only shell to process-aware runtime scaffold without importing donor addresses.


## 2026-09-22 850Launcher v0.3 read-only runtime probe

```text
STATUS=PASS_SOURCE
PROCESS_ATTACH=IMPLEMENTED
READ_PROCESS_MEMORY=IMPLEMENTED
WRITE_PROCESS_MEMORY=NO
SCAN_WIDTH=32-bit aligned
FIRST_SCAN=exact HP/MaxHP/MP/MaxMP
REFINE_SCAN=exact-value candidate filtering
NEAR_CLUSTER_WINDOW=0x100
EVIDENCE=runtime_probe_evidence.txt
```

Implementation details:

- launcher now requests administrator elevation so it can inspect the elevated 850 client it launches;
- **偵測** tab accepts exact current/max HP/MP values;
- one read-only memory pass collects candidates for all four values;
- subsequent scans only re-read previous candidate addresses, avoiding repeated full scans;
- candidates within 0x100 bytes are correlated/ranked as possible player-structure neighborhoods;
- module-relative RVA is emitted when a candidate lies inside the main `Lin.bin2` image;
- probe evidence records PID, module base, values, candidate counts and top nearby clusters.

PASS boundary remains unchanged:

```text
WP3=NOT_YET_PROVEN
WP4=NOT_YET_PROVEN
NO_ADDRESS_ACCEPTED_FROM_SINGLE_SESSION
RELOG_AND_RESTART_VALIDATION_REQUIRED
```


## 2026-09-22 850Launcher v0.4 inventory probe scaffold

```text
STATUS=PASS_SOURCE
WP5_PROBE=IMPLEMENTED
INPUT=stack item exact count
FIRST_SCAN=aligned 32-bit exact count
REFINE=previous candidates only
NEARBY_DWORD_DUMP=+-0x40
WRITE_PROCESS_MEMORY=NO
EVIDENCE=inventory_probe_evidence.txt
```

The new **物品偵測** tab can isolate candidate count fields by controlled stack changes. Selecting a candidate displays nearby 32-bit values, allowing later correlation of stable objectId/itemId fields around the changing count field.

PASS boundary:

```text
WP5=NOT_YET_PROVEN
OBJECT_ID=UNKNOWN
ITEM_ID=UNKNOWN
COUNT=UNKNOWN_UNTIL_RUNTIME_VALIDATION
ENCHANT=UNKNOWN
EQUIPPED=UNKNOWN
```

No donor address or guessed item layout is embedded.


## 2026-09-22 850Launcher v0.5 stable mapping pipeline

```text
STATUS=PASS_SOURCE
POINTER_CHAIN_SCAN=READ_ONLY
POINTER_DEPTH=1|2
STATIC_ROOT=Lin.bin2 module RVA
RUNTIME_MAP_FILE=runtime-map.ini
RUNTIME_MAP_UI=IMPLEMENTED
WRITE_PROCESS_MEMORY=NO
```

Added:

- read-only pointer-chain search from a validated absolute HP/MP candidate back to a static `Lin.bin2` root;
- one-level and two-level pointer expressions;
- configurable max offset;
- evidence export to `pointer_probe_evidence.txt`;
- **映射** UI for CurrentHP / MaxHP / CurrentMP / MaxMP;
- mapping syntax validation;
- `runtime-map.ini` hot reload through the normal runtime bridge;
- deploy creates `runtime-map.ini` only when missing and never overwrites an existing mapping.

Supported syntax:

```text
RVA:0x<RVA>
PTR:0x<baseRVA>|0x<finalOffset>
PTR:0x<baseRVA>|0x<offset1>|0x<finalOffset>
```

Acceptance boundary remains:

```text
FORMAT_PASS != WP4_PASS
SINGLE_SESSION != WP4_PASS
RELOG_REQUIRED
FULL_CLIENT_RESTART_REQUIRED
```


## 2026-09-22 WP5 inventory field proof scaffold

```text
STATUS=PASS_SOURCE
INVENTORY_BRIDGE_CONTRACT=IMPLEMENTED
INVENTORY_BRIDGE_RUNTIME=UNMAPPED
COUNT_PROBE=IMPLEMENTED
RECORD_AB_COMPARE=IMPLEMENTED
FIELD_VALIDATOR=IMPLEMENTED
WRITE_PROCESS_MEMORY=NO
```

Added staged proof tooling:

1. **物品偵測** isolates a stack-count candidate through controlled count changes.
2. **物品結構** compares two read-only snapshots around that candidate and flags changing/stable fields plus optional known ItemId matches.
3. **物品欄位** validates hypothesized record offsets for ObjectId, ItemId, Count, Enchant and Equipped with configurable field widths.
4. Evidence is appended to:
   - `inventory_probe_evidence.txt`
   - `inventory_record_evidence.txt`
   - `inventory_field_validation.txt`
5. A formal `IInventoryBridge` contract now exists, but the runtime implementation remains intentionally unmapped until the record layout and collection traversal are proven.

UI separation:

```text
NORMAL_PLAYER_UI=Chinese helper tabs only
Developer.Enabled=1 => reverse/probe tabs visible
```

This keeps reverse-engineering controls out of the normal helper interface.


## 2026-09-22 WP7/WP8 protocol and native-send discovery

```text
STATUS=PASS_SOURCE
850_C_ITEMUSE_CLASS=aj.az
850_PACKET_HANDLER=ai.e
C_ITEMUSE_OPCODE=94=0x5E
FIRST_FIELD=objectId LE32
NORMAL_HEAL_POTION_EXTRA_FIELDS=NONE
RAW_SOCKET_SEND=NO
ITEM_USE_BRIDGE=UNMAPPED
AUTO_POTION_CONTROLLER=IMPLEMENTED_GATED
NATIVE_SEND_XREF_SCAN=IMPLEMENTED_READ_ONLY
ITEM_NAME_ROWS=4388
```

Recovered 850 server evidence:

- `identity/class_source_mapping.csv`: `aj.az -> C_ItemUSe.java`, `ai.e -> PacketHandler.java`, `bj.d -> ClientThread.java`.
- `PacketHandler`: case `94` constructs `aj.az` / `C_ItemUSe`.
- `C_ItemUSe`: first field is `itemObjid = readD()`, then inventory lookup is by objectId.
- base packet reader `cv.b()` is a 4-byte little-endian integer.
- 850 DB rows such as item 40010/40011/40012 use `use_type='normal'`; the use-type switch consumes no additional fields for these healing potions.

Thus the logical decrypted normal-potion payload is:

```text
0x5E + objectId(LE32)
```

This does **not** prove the external launcher can send that payload safely. The active Lin.bin2 connection applies its own framing/encryption state.

Added client-side tooling:

- `ItemUseProtocol`: builds the logical payload for evidence only.
- hidden `UseItem協定` page: objectId -> logical payload preview, SEND=NO.
- `PeImportParser`: PE32 import parser.
- `NativeSendXrefScanner`: finds runtime `CALL/JMP [IAT]` references to `send/sendto/WSASend/WSASendTo` in executable Lin.bin2 sections.
- hidden `Send掃描` page and `native_send_xref_evidence.txt`.
- `AutoPotionController`: percent/exact HP policy -> configured potion itemId priority -> mapped inventory objectId -> cooldown -> gated ItemUse bridge.
- `item-names.csv`: 4388 names generated from the authoritative 850 SQL tables; mapped inventory can display names without DB access.

PASS boundary:

```text
WP7=NOT_YET_PASS
SERVER_PROTOCOL_PROOF=PASS
CLIENT_NATIVE_SEND_PATH=NOT_YET_PROVEN
RAW_PACKET_INJECTION=REJECTED
WP8_ACTION=GATED_BY_WP7
```


## 2026-09-23 WP7 native send caller graph

```text
STATUS=PASS_SOURCE
ROOT=send|sendto|WSASend|WSASendTo IAT xref
CALL_GRAPH=relative E8 callers
MAX_DEPTH=5
FUNCTION_START=heuristic x86 prologue / RET-padding recovery
OPCODE_5E_MARKER=HEURISTIC_ONLY
FUNCTION_FINGERPRINT=SHA256(first 64 runtime bytes)
MEMORY_WRITE=NO
```

Added:

- `NativeCallGraphScanner`: starts from proven network-send IAT xrefs and walks direct relative-call callers upward.
- candidate functions are module-relative RVAs, not process absolute addresses.
- strong immediate `0x5E` encodings are flagged only as weak ranking evidence:
  - `push 0x5E`
  - `push 0x0000005E`
  - `mov reg,0x5E`
  - `mov al,0x5E`
- `NativeCodeWindow`: SHA-256 fingerprint of the first 64 runtime bytes for restart identity checks.
- hidden **Send追蹤** page.
- evidence: `native_call_graph_evidence.txt`.

WP7 native identity PASS gate:

```text
1. target binary SHA256 = authoritative 850 Lin.bin2
2. function represented by module RVA
3. first-64-byte SHA256 stable across relog and full restart
4. caller relation to network-send layer stable
5. function/action correlation established during a normal potion use
6. no 381/880 runtime address accepted
7. no raw socket injection accepted
```

Current state:

```text
SERVER_C_ITEMUSE_PROTOCOL=PROVEN
NATIVE_SEND_ROOT_DISCOVERY=IMPLEMENTED
CALLER_GRAPH_DISCOVERY=IMPLEMENTED
NATIVE_ITEMUSE_FUNCTION=NOT_YET_PROVEN
WP7=BLOCKED_RUNTIME_EVIDENCE
```


## 2026-09-23 WP7 cross-session evidence comparator

```text
STATUS=PASS_SOURCE
EVIDENCE_FILE=native_call_graph_evidence.txt
SESSION_COMPARE=IMPLEMENTED
FUNCTION_ID=RVA+SHA256(first64)
EDGE_ID=caller_func+target_func+call_rva
PROCESS_INSTANCE=PROCESS_START_UTC
FULL_RESTART_GATE=distinct_process_instances>=2
WP7_AUTO_PASS=NO
```

Added:

- `RuntimeSnapshot.ProcessStartTimeUtc`.
- every native call graph session records `PROCESS_START_UTC`.
- `NativeCallGraphEvidenceComparer` parses appended sessions and intersects:
  - function RVA + 64-byte SHA-256;
  - caller/target/call-site edges.
- hidden **Send比對** page ranks stable functions by stable edge count, marker presence, and depth.
- repeated scans from the same process do not count as full-client restart evidence.

Current remaining native gate:

```text
RESTART_STABLE_CANDIDATE != WP7_PASS
MANUAL_NORMAL_POTION_ACTION_CORRELATION_REQUIRED
```


## 2026-09-23 WP9 skill/buff scaffold

```text
STATUS=PASS_SOURCE
C_USESKILL_CLASS=aj.cr
C_USESKILL_OPCODE=128=0x80
SKILL_ID_ENCODING=row*8+column+1
ROW_WIDTH=C
COLUMN_WIDTH=C
GENERAL_ARGS=targetId(D)+targetX(H)+targetY(H)
XY_ONLY_SKILLS=58,63
BOOKMARK_SKILLS=5,69
MESSAGE_SKILLS=116,118
SKILL_CATALOG_ROWS=242
AUTO_BUFF_UI=IMPLEMENTED
BUFF_STATE_BRIDGE=UNMAPPED
SKILL_USE_BRIDGE=UNMAPPED
SEND=NO
```

Added:

- `SkillUseProtocol` with recovered 850 row/column encoding and proven argument branches.
- `SkillUseProtocolControl` hidden developer proof page; emits logical payload evidence only.
- `skill-names.csv` generated from the current 850 `skills.sql`.
- `SkillCatalog` offline Chinese lookup with buff duration / HP / MP / reuse metadata.
- normal **狀態** page now lists skills with `buffDuration > 0` and persists checked SkillIds.
- `IBuffStateBridge` and `ISkillUseBridge` gates.
- `AutoBuffController` requires both gates before any cast request.

WP9 boundary:

```text
SERVER_PROTOCOL_PROOF=PASS
PLAYER_SKILL_SELECTION=PASS_SOURCE
BUFF_RUNTIME_STATE=NOT_YET_PROVEN
CLIENT_NATIVE_SKILLUSE_PATH=NOT_YET_PROVEN
WP9=BLOCKED_RUNTIME_EVIDENCE
```


## 下一步

```text
NEXT=850 Lin.bin2 / LoginWithoutUI runtime mapping
GOAL=定位 Player / HPMP / Inventory
FIRST_GATE=WP6 inventory listing
```
