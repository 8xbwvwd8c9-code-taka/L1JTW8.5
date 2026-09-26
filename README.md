# L1JTW8.5 — Fast Development Build

> Branch: `work/l1jtw85-fast-dev-build`  
> Mode: **FAST_DEV / readable-source-first / incremental compile**  
> Updated: 2026-09-26

這條支線是 **850 重新架構核心的日常開發 / 編譯 / 執行入口**。

它不是第三條核心 source authority，也不是再做一次反編譯。850 核心權威仍只有：

- `completed/l1jtw85-decompiled`：原始反編譯 frozen baseline。
- `completed/l1jtw85-core-fixes`：所有已驗證 BUG 修復的唯一 authority。

Fast Dev 的工作是把上述權威 materialize 成可閱讀的 `core/src`，讓日常修改一顆核心後能直接增量編譯與實機驗證，不必每次重跑 recovery、1109 application class rebuild、inverse-remap 與 production JAR 重建。

---

## Fast Dev / 本機編譯故障索引

之後再遇到本機 Fast Dev / Full Compile 錯誤時，**先查本索引，再進行新修補**。每筆必須保留症狀、根因、修法與驗證，不把工具鏈/反編譯 artifact 誤判成 gameplay BUG。

### ERROR-LOCAL-001 — Fast Dev branch checkout 被本機變更阻擋

```text
ERROR_ID=ERROR-LOCAL-001
DATE=2026-09-25
PHASE=BRANCH_SWITCH
SYMPTOM=git switch work/l1jtw85-fast-dev-build 被 tracked/untracked local changes 阻擋
ROOT_CAUSE=原工作目錄仍有 recovery / production rebuild / audit / SQL 等本機工作，Git 正確拒絕覆蓋
FIX=git stash push -u 保存 LOCAL-BACKUP-before-fast-dev-build-20260925，再 fetch/switch/pull；禁止 reset --hard / clean -fd / stash pop 到 Fast Dev
COMMIT=N/A（本機工作目錄保護流程）
VALIDATION=LOCAL_BRANCH_SWITCH=PASS；LOCAL_SYNC=PASS；stash 保留未 pop
STATUS=CLOSED
```

### ERROR-LOCAL-002 — Windows CP950 / Python UnicodeDecodeError

```text
ERROR_ID=ERROR-LOCAL-002
DATE=2026-09-25
PHASE=FAST_DEV_SYNC
SYMPTOM=.\build850.ps1 -Sync 出現 UnicodeDecodeError(cp950)，之後 proc.stdout=None 導致 write_text(None) TypeError
ROOT_CAUSE=Windows 繁中環境 subprocess text=True 預設 CP950，但 Git 輸出含 UTF-8 中文
FIX=build850.ps1 強制 python -X utf8
COMMIT=HISTORICAL（交接紀錄未保存此修補的 exact SHA；不得猜值）
VALIDATION=Run #172 RED；Run #173 GREEN；FAST_DEV_SYNC=PASS；AUTHORITY=f27acba8917a2b261ed38125704bd866b00f9038；COMPLETED_SOURCES=77
STATUS=CLOSED
```

### ERROR-LOCAL-003 — Windows WinError 206 / javac command line 過長

```text
ERROR_ID=ERROR-LOCAL-003
DATE=2026-09-25
PHASE=LOCAL_FULL_COMPILE
SYMPTOM=.\build850.ps1 -Full 在 subprocess/_winapi.CreateProcess 報 FileNotFoundError [WinError 206]
ROOT_CAUSE=Full Compile 把數百個 Java source path 直接塞進 Windows command line；javac 尚未真正啟動
FIX=source list 改寫入 javac argfile，命令改為 javac ... @javac-sources.xxxxx.args；既有 encoding/source/target/classpath/-d 語意不變
COMMIT=a133214461343e033ce4f4299dbc0b5be1d95e02
VALIDATION=Run #174 RED；argfile regression GREEN；incremental compiler contracts PASS；real core materialization PASS；runtime smoke contract PASS
STATUS=CLOSED
```

### ERROR-LOCAL-004 — PBMessageALL* protobuf / decompiler symbol rewrite

```text
ERROR_ID=ERROR-LOCAL-004
DATE=2026-09-26
PHASE=LOCAL_FULL_COMPILE
SYMPTOM=WinError 206 關閉後 javac 真正啟動；錯誤先集中 PBMessageALL* protobuf/generated source，之後依序暴露 compile-view alias/runtime-g、raw Comparator 與 L1Craft/S_ProtoBuffers Java name-shadow；javac error family 100→88→22→0
ROOT_CAUSE=Fast Dev 將 non-round-trippable PBMessageALL* generated source 與歷史 compile-view ABI/normalizer 表示混入一般 javac；其餘錯誤同屬 recovery/source-representation 相容層，不是 gameplay BUG
FIX=PBMessageALL、PBMessageALL2..9 設為 baseline-only 並由 850-dev-base.jar 提供；其餘 application source 正常 full javac；移除 compile-view-only external-builder/runtime-g normalizer；保留 typed Comparator；shadowed runtime g 呼叫改為 ((g)null).a(...)；shallow clone 缺 pinned normalizer e83c26c3 時只抓 exact SHA，fetch_if_missing policy 端到端傳遞
COMMIT=47d1f1f5984cd0bdefead21ddff10b76d01bbcac（final fetch-policy guard）；主要修復序列含 63095b16、0c7e85d5、f048222d、18c0cdba
VALIDATION=Fast Dev Main Run #205 PASS；Fast Dev Full Compile Run #32 PASS；Baseline Contract Run #17 PASS；real automatic bootstrap PASS；MySQL 5.7 import PASS；DB-backed runtime smoke PASS；Real Fast Dev full compile PASS；production l1jserver2.jar SHA256 8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814 保持不變
STATUS=CLOSED
```

### ERROR-LOCAL-005 — Windows stale dev-base WinError 5 / 本機啟動鏈路

```text
ERROR_ID=ERROR-LOCAL-005
DATE=2026-09-26
PHASE=WINDOWS_LOCAL_STARTUP
SYMPTOM=.build850/cache/850-dev-base.jar 已失效，但 Windows 對 unlink 持續回傳 PermissionError [WinError 5]；原 retry-only cleanup 無法前進
ROOT_CAUSE=generated dev-base 可被本機 Windows handle 暫時或持續鎖定；舊 bootstrap cleanup 假設 stale JAR 一定能直接刪除
RED_COMMIT=d5d93ff811b69d945339544e146c19ee620f04ee
GREEN_COMMIT=1acda3229a2715e5c750d9eab579ad5892834b0a
WINDOWS_CI_COMMIT=5031890000d7fae455062be3c9bc707031d6db72
FIX=stale dev-base 先做 5 次 unlink retry；持續 PermissionError 時使用 quarantine path 將 stale generated artifact 移出 active path，再由 bootstrap 建立 usable baseline；加入 persistent-delete-denial regression 與獨立 windows-latest CI gate
LINUX_MAIN_RUN=36218863978 (#224) PASS
FULL_COMPILE_RUN=36218863958 (#47) PASS
WINDOWS_RUN=36220565710 (#1) PASS
LOCAL_DB_FOLLOWUP=本機 DB credentials 修正；JDBC database target 由 8.5 校正為 850；本機 credential 不進 Git
LOCAL_VALIDATION=BUILD PASS；DATABASE_CONNECT PASS；SERVER_INIT PASS；CLIENT_CONNECT PASS；CHARACTER_LOGIN PASS
STATUS=CLOSED
```

最終實機結果：

```text
FAST_DEV_BUILD=PASS
WINDOWS_CI=PASS
WINDOWS_LOCAL=PASS
DATABASE_CONNECT=PASS
SERVER_INIT=PASS
CLIENT_CONNECT=PASS
CHARACTER_LOGIN=PASS
ERROR-LOCAL-005=CLOSED
```

> `config/server.properties` 是本機環境設定。DB credential 不得寫入 README、commit 或任何遠端 branch。

---

## 1. 核心目標

850 已完成主要反編譯工作，因此現在進入 **快速開發模式**。

日常開發介面：

```powershell
.\build850.ps1
.\build850.ps1 -Run
.\build850.ps1 -Watch
```

預期行為：

```text
修改 1 顆 Java 核心
        ↓
偵測 source hash 變更
        ↓
只編譯該 top-level class + inner classes
        ↓
若 public/protected ABI 未改變：直接完成
        ↓
若 ABI 改變：只補編 reverse dependents
        ↓
無法證明安全時才升級成 Full Compile
```

原則：

```text
INCREMENTAL FIRST
FULL COMPILE ONLY WHEN NECESSARY
NO DAILY OBFUSCATION
NO DAILY INVERSE-REMAP
```

---

## 2. 為什麼要建立這條支線

舊 production rebuild 已證明：

```text
repaired source
→ Java 8 compile
→ inverse remap
→ patch original runtime namespace
→ repaired test JAR
→ MySQL 5.7
→ server startup
→ port 2000 listening
```

這條流程適合 production/runtime 驗證，但不適合日常反覆修改核心。

Fast Dev 將開發與未來發布拆開：

```text
FAST DEV
= readable source
+ semantic package
+ incremental compile
+ class overlay

FUTURE RELEASE
= validation
+ optional remap
+ optional obfuscation/encryption
```

---

## 3. 權威來源與支線角色

### 3.1 原始反編譯 baseline

```text
BRANCH=completed/l1jtw85-decompiled
ROLE=FROZEN_DECOMPILE_BASELINE
WRITE=NO
```

已接受基準：

```text
APPLICATION_SOURCES=788
APPLICATION_CLASSES=1109
JAVAC_ERRORS=0
MAPPING_UNION=1765
MISSING=0
```

Fast Dev 不重新反編譯。

### 3.2 已修復 BUG authority

```text
BRANCH=completed/l1jtw85-core-fixes
ROLE=VALIDATED_REPAIR_AUTHORITY
WRITE_BY_FAST_DEV=NO
SYNC_INTO_FAST_DEV=YES
```

規則：

```text
completed/l1jtw85-core-fixes
= 所有已修復 / 已驗證 / 已 promotion 核心的唯一 authority
= Fast Dev -Sync 唯一可接受的 BUG repair 來源

舊 work/l1jtw85-core-fixes
= RETIRED / HISTORICAL ONLY
= 不再是 authority
= 不得直接進 Fast Dev active runtime
```

禁止因為任何 work / repair / tmp / historical branch 的 Java 檔較新，就覆蓋 Fast Dev source。

### 3.3 重新架構 / Fast Dev branch

```text
BRANCH=work/l1jtw85-fast-dev-build
ROLE=DEVELOPMENT_MATERIALIZATION + BUILD + RUNTIME
CORE_AUTHORITY=NO
```

這條支線保存工具鏈、mapping 規則、bootstrap、compiler、runbook 與故障紀錄；實際 editable core 由 bootstrap 在本機 materialize。

### 3.4 原始 production JAR

```text
FILE=l1jserver2.jar
ROLE=IMMUTABLE_RUNTIME_BASELINE
```

已知 SHA-256：

```text
8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814
```

Fast Dev、bootstrap、測試、release 工具都不得覆蓋它。

---

## 4. 核心架構 ↔ completed BUG 核心存放位置映射

### 4.1 固定資料流

```text
completed/l1jtw85-decompiled
        │ frozen baseline
        ▼
recovery normalized authority
        │
        ├── formal completed promotion source only
        │       ▲
        │       │ completed/l1jtw85-core-fixes
        │       │
        ▼       │
.build850/cache/completed-authority-core/
        │
        ├── deployable all PASS ───────────────┐
        │                                      │
        └── 有 deferred source                 │
                ▼                              │
.build850/cache/runtime-active-authority-core/ │
                │                              │
                └──────────────┬───────────────┘
                               ▼
                         core/src/
                               │
                               ▼
                     .build850/classes/
                               │
                               + .build850/cache/850-dev-base.jar
                               ▼
                         Fast Dev Runtime
```

### 4.2 每一顆核心的固定位置

| 層級 | 存放位置 | 用途 |
|---|---|---|
| completed 修復權威 | `completed/l1jtw85-core-fixes:recovery/normalized-src-vf/<RecoveredInternal>.java` | 已驗證 promotion 的 normalized source；Fast Dev 只讀 |
| 原始 recovered identity | `recovery/source_namespace_map.csv` | `OldInternal ↔ RecoveredInternal` 權威映射 |
| Fast Dev package 規則 | `tools/850/bootstrap/package_rules.json` | 決定 semantic `DevInternal` package |
| materialized completed authority | `.build850/cache/completed-authority-core/src/<DevInternal>.java` | baseline + 正式 completed promotion 的完整 semantic authority cache |
| runtime-active fallback | `.build850/cache/runtime-active-authority-core/src/<DevInternal>.java` | 只有存在 deferred completed source 時建立；由 baseline + deployable repairs 合成 |
| 日常 editable core | `core/src/<DevInternal>.java` | **日常修改核心的唯一工作入口** |
| 逐核心完整索引 | `core/source-index.json` | 每顆 top-level core 的 original / recovered / dev / source / authority 對照 |
| package map | `core/package-map.csv` | 788 top-level source 的 `OriginalInternal / RecoveredInternal / DevInternal / SourceFile / Category` |
| runtime class map | `core/runtime-class-map.json` | 1109 application runtime classes → semantic Dev identity |
| pinned authority | `core/PINNED_AUTHORITY.json` | 記錄此 working core 由哪個 completed commit / baseline materialize |
| compiled override | `.build850/classes/<DevInternal>.class` | 增量/完整編譯輸出；classpath 優先於 dev-base |
| readable bytecode baseline | `.build850/cache/850-dev-base.jar!/<DevInternal>.class` | 未被 `.build850/classes` 覆蓋的 runtime baseline |
| build state | `.build850/state.json` | source hash / ABI / compile state |
| dependency index | `.build850/dependency-index.json` | reverse dependency / rebuild closure |

### 4.3 映射規則

對每一顆 top-level application core：

```text
completed normalized path
= recovery/normalized-src-vf/<RecoveredInternal>.java

Fast Dev authority cache path
= .build850/cache/completed-authority-core/src/<DevInternal>.java

Fast Dev active editable path
= core/src/<DevInternal>.java

compiled override path
= .build850/classes/<DevInternal>.class
```

例如 `C_NpcAction`：

```text
OriginalInternal = aj/bk
RecoveredInternal = l1r/aj/C_NpcAction
completed path = recovery/normalized-src-vf/l1r/aj/C_NpcAction.java
DevInternal = l1j/server/clientpackets/C_NpcAction
Fast Dev path = core/src/l1j/server/clientpackets/C_NpcAction.java
```

**不要人工猜 package。** `core/source-index.json` 與 `core/package-map.csv` 是逐核心查找入口；任何無法建立唯一 identity 的 mapping 必須 fail closed。

本機要找某一顆核心可直接：

```powershell
Select-String -Path .\core\source-index.json -Pattern 'C_NpcAction' -Context 0,8
```

### 4.4 completed BUG promotion-only 規則

Fast Dev 不把 completed branch 整棵 worktree 當作 source，也不信任 branch 上任意較新的 Java 檔。

bootstrap 的固定規則：

```text
RECOVERY_BASELINE_COMMIT=f49015ff55120eb414f1feba0b268bbb51484e16
COMPLETED_BRANCH=completed/l1jtw85-core-fixes
NORMALIZED_SOURCE_ROOT=recovery/normalized-src-vf

baseline
+ formal promotion commit touched normalized Java sources
= completed-authority-core
```

`authority_cache.py` 只接受正式 promotion commit pattern，並將重疊 source / 同 BUG split commits 合併成 atomic promotion scope。Fast Dev `-Sync` 以 previous-active / new-active / working-core 三方比較，只在 working file 仍等於舊 authority 時更新；本機已有不同修改則 conflict / fail closed，禁止部分覆寫。

### 4.5 一方向同步規則

```text
completed/l1jtw85-core-fixes
        ↓  -Sync / bootstrap
Fast Dev authority cache
        ↓
core/src
        ↓
classes/runtime

Fast Dev → completed = FORBIDDEN AUTOMATIC WRITEBACK
```

Fast Dev 內的新修改若未完成 BUG 修復流程與驗證，不會自動升格成 completed authority。

---

## 5. 最終目標目錄

```text
I:\L1JTW8.5
│
├─ build850.ps1
├─ build850.cmd
│
├─ core\
│  ├─ src\l1j\server\...
│  ├─ package-map.csv
│  ├─ source-index.json
│  ├─ runtime-class-map.json
│  └─ PINNED_AUTHORITY.json
│
├─ tools\850\
│  ├─ bootstrap\
│  ├─ compiler\
│  └─ release\
│
├─ .build850\
│  ├─ cache\
│  │  ├─ completed-authority-core\
│  │  ├─ recovery-baseline-core\
│  │  ├─ runtime-active-authority-core\   # only when deferred exists
│  │  ├─ completed-repair-overlay\
│  │  └─ 850-dev-base.jar
│  ├─ classes\
│  ├─ state.json
│  └─ dependency-index.json
│
├─ dist\
├─ recovery\
└─ l1jserver2.jar
```

### 日常目錄

```text
core/src/
build850.ps1
.build850/
dist/
tools/850/
```

### 歷史 / 底層目錄

```text
recovery/
recovered-src-obf/
tools/production-rebuild/
```

歷史/底層資料只作 recovery、mapping、正式 runtime 與驗證證據，不作一般核心修改入口。

---

## 6. 新核心目錄與 namespace

Fast Dev 不再把 `l1r/aj`、`l1r/ao`、`l1r/ap` 等 recovery namespace 當新核心工作目錄。

日常直接使用 package/path 對齊的 source，例如：

```text
core/src/l1j/server/clientpackets/C_NpcAction.java
core/src/l1j/server/datatables/AccountTable.java
core/src/l1j/server/datatables/CharacterTable.java
core/src/l1j/server/model/L1Master.java
core/src/l1j/server/model/instance/L1PetInstance.java
core/src/l1j/server/model/item/FurnitureItem.java
core/src/l1j/server/model/timer/CurrentTimeReseter.java
core/src/l1j/server/templates/L1BookMark.java
```

完整逐核心映射由：

```text
core/package-map.csv
core/source-index.json
core/runtime-class-map.json
```

自動生成並保存於 working core。

---

## 7. Fast Dev Runtime

Fast Dev 建立 readable runtime baseline：

```text
.build850/cache/850-dev-base.jar
```

Baseline readiness 同時要求：

```text
.build850/cache/850-dev-base.jar
.build850/state.json
.build850/dependency-index.json
850-dev-base.jar 內存在 l1j/server/Server.class
```

日常 classpath：

```text
.build850/classes
.build850/cache/850-dev-base.jar
lib/*
```

`.build850/classes` 優先，因此新編譯 class 直接覆蓋 baseline 同 identity class；日常修改不需要每次重包 JAR。

---

## 8. 操作指令

```powershell
# 預設：增量編譯
.\build850.ps1

# 編譯後啟動 Dev Runtime
.\build850.ps1 -Run

# 監看 core/src，存檔後自動增量編譯
.\build850.ps1 -Watch

# 強制完整 readable application compile
.\build850.ps1 -Full

# 清理 .build850 再 bootstrap
.\build850.ps1 -Clean

# 只從 completed authority 同步新修復
.\build850.ps1 -Sync

# 產生 readable dev JAR
.\build850.ps1 -Pack
```

Release remap / obfuscation / encryption 屬未來 release 階段，不放回日常 Fast Dev loop。

---

## 9. 已實作 / 已驗證

### Source / mapping

```text
TOP_LEVEL_APPLICATION_SOURCES=788
TOP_LEVEL_MAPPING_IDENTITIES=788
APPLICATION_RUNTIME_CLASS_MAPPINGS=1109
DUPLICATE_DEV_IDENTITY=0
RECOVERY_NAMESPACE_IN_CORE=0
```

### Authority / sync

```text
DECOMPILE_AUTHORITY=completed/l1jtw85-decompiled
REPAIR_AUTHORITY=completed/l1jtw85-core-fixes
FAST_DEV_BRANCH=work/l1jtw85-fast-dev-build
COMPLETED_AUTHORITY_HEAD=f27acba8917a2b261ed38125704bd866b00f9038
COMPLETED_PROMOTED_SOURCE_COUNT=77
PROMOTION_ONLY_MATERIALIZATION=PASS
COMPLETED_OVERLAY=PASS
SYNC_AUTHORITY_ATOMICITY=PASS
WORK_IN_PROGRESS_SOURCE_LEAK=0
```

### Compiler / runtime

已完成：

- readable semantic source materialization
- `.build850/cache/850-dev-base.jar`
- source hash / ABI fingerprint / reverse dependency index
- changed top-level family incremental compile
- ABI 變更 reverse-dependent rebuild
- unknown dependency closure → full compile
- class staging + atomic publish
- compile failure preserves last-known-good state
- PBMessageALL、PBMessageALL2..9 baseline-only policy
- deterministic readable dev JAR packing
- Java 8 Fast Dev runtime
- MySQL 5.7 DB-backed startup
- client connect / character login
- production `l1jserver2.jar` SHA preservation

### 最新 CI / Windows 實機 gate

```text
FAST_DEV_MAIN_RUN=224
MAIN_RUN_ID=36218863978
MAIN_STATUS=PASS

FAST_DEV_FULL_COMPILE_RUN=47
FULL_COMPILE_RUN_ID=36218863958
FULL_COMPILE_STATUS=PASS

FAST_DEV_WINDOWS_RUN=1
WINDOWS_RUN_ID=36220565710
WINDOWS_STATUS=PASS

WINDOWS_LOCAL=PASS
DATABASE_CONNECT=PASS
SERVER_INIT=PASS
CLIENT_CONNECT=PASS
CHARACTER_LOGIN=PASS
```

Windows workflow 專門跑 `windows-latest + Python 3.13` 的 local startup contracts 與 UTF-8 driver contract；Linux Main / Full Compile 保持原有完整 gate。

---

## 10. 目前狀態 / 下一個 Gate

Fast Dev 第一階段 build/runtime gate與 Windows 本機啟動 gate已完成，8.50c 實機 client 也已成功連線並登入角色。

```text
FAST_DEV_BUILD=PASS
LINUX_MAIN_CI=PASS
FULL_COMPILE_CI=PASS
WINDOWS_CI=PASS
WINDOWS_LOCAL=PASS
MYSQL_DB=PASS
SERVER_INIT=PASS
CLIENT_CONNECT=PASS
CHARACTER_LOGIN=PASS
```

接下來的工作不是重做 Fast Dev 基礎設施，而是直接以 `core/src` 進行新的 850 功能 / BUG 開發；新增完成 BUG 必須先進 `completed/l1jtw85-core-fixes`，再由 Fast Dev `-Sync` 消費。

---

## 11. 舊 Production Rebuild 的定位

```text
tools/production-rebuild/
```

保留作：

- production / obfuscated runtime 參考
- inverse-remap 參考
- future Release Mode 基礎
- Fast Dev namespace/runtime 問題的 fallback comparison

它不是日常快速編譯器。

---

## 12. 未來接手者必讀

```text
1. 讀本 README
2. 需要找某顆核心 → 查 core/source-index.json / core/package-map.csv
3. 原始反編譯 authority → completed/l1jtw85-decompiled
4. 已修復 BUG authority → completed/l1jtw85-core-fixes
5. 日常 editable source → core/src
6. 需要新 completed 修復 → 先完成驗證與 promotion，再 .\build850.ps1 -Sync
7. 檢查 Fast Dev Main / Full Compile / Windows CI
8. 不重做已完成的 decompile / Fast Dev bootstrap
```

設計文件：

```text
docs/superpowers/specs/2026-09-24-l1jtw85-fast-dev-build-design.md
```

執行計畫：

```text
docs/superpowers/plans/2026-09-24-l1jtw85-fast-dev-build.md
```

Runbook：

```text
docs/850-fast-dev.md
```

### 不要做

```text
DO NOT redo decompilation
DO NOT treat recovery/ as the normal editable source tree
DO NOT treat work/l1jtw85-fast-dev-build as a third core authority
DO NOT copy work/tmp/historical BUG source into active runtime
DO NOT overwrite l1jserver2.jar
DO NOT force 880 ABI/package assumptions onto 850
DO NOT add daily obfuscation/remap back into the fast loop
DO NOT hard-code pending BUG count
DO NOT silently guess unknown semantic package mappings
DO NOT commit local DB credentials
```

### 正確工作入口

```text
Fast Dev / 重新架構核心開發入口:
work/l1jtw85-fast-dev-build

Validated repair authority:
completed/l1jtw85-core-fixes

Accepted decompile baseline:
completed/l1jtw85-decompiled
```

---

## 13. 完成定義

```text
READABLE_CORE_TREE=PASS
PACKAGE_MAP_COMPLETE=PASS
APPLICATION_SOURCE_COVERAGE=788/788
APPLICATION_RUNTIME_CLASS_MAPPINGS=1109
DUPLICATE_IDENTITY=0
COMPLETED_REPAIR_PRECEDENCE=PASS
PROMOTION_ONLY_MATERIALIZATION=PASS
DEV_BASELINE_BUILD=PASS
INCREMENTAL_COMPILE=PASS
ABI_DEPENDENCY_REBUILD=PASS
LAST_KNOWN_GOOD_PRESERVATION=PASS
WATCH_MODE_COMPILE=PASS
FULL_COMPILE=PASS
CLEAN_REBOOTSTRAP=PASS
SYNC_MODE=PASS
PACK_MODE=PASS
RUN_MODE=PASS
WINDOWS_CI=PASS
WINDOWS_LOCAL=PASS
MYSQL_DB=PASS
SERVER_INIT=PASS
CLIENT_CONNECT=PASS
CHARACTER_LOGIN=PASS
ORIGINAL_JAR_MODIFIED=NO
ERROR-LOCAL-005=CLOSED
```

---

## 14. 一句話原則

```text
850 現在以「快速修改、快速編譯、快速驗證」為優先。

原始反編譯只認 completed/l1jtw85-decompiled。
BUG 修復只認 completed/l1jtw85-core-fixes。
日常核心只改 core/src。
每顆核心的位置由 source-index/package-map 映射。
需要正式發布時，再處理 remap / 混淆 / 加密。
```