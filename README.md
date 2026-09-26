# L1JTW8.5 — Fast Development Build

> Branch: `work/l1jtw85-fast-dev-build`  
> Mode: **FAST_DEV / readable-source-first / incremental compile**  
> Updated: 2026-09-26

這條支線的目的不是再做一次反編譯，也不是把 850 強行改成 880 的核心格式。

本支線的目標是：**保留 850 已完成的反編譯與 mapping 成果，但把日常開發流程簡化成接近 880 的使用方式。**

未來修改一顆核心後，應該可以直接重新編譯該核心與必要相依類別，不需要每次重新執行完整 recovery、1109 application class rebuild、promotion 掃描、inverse-remap 與 production JAR 重建。

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

---

## 1. 核心目標

850 已完成主要反編譯工作，因此現在進入 **快速開發模式**。

日常開發的目標操作介面：

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

舊 production rebuild 管線已經證明 850 可以：

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

這條流程適合驗證正式 runtime，但不適合日常反覆修改核心。

舊流程包含：

- recovery source hierarchy
- normalized namespace
- production obfuscated namespace
- deployability selection
- promotion candidate scanning
- inverse remap
- production JAR patch
- structural validation

如果每改一顆核心都重新跑完整流程，開發速度會非常慢。

因此 Fast Dev 將「開發」與「未來發布/混淆」拆開：

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

目前階段只做 FAST DEV。

---

## 3. 權威來源

### 3.1 反編譯基準

反編譯工作已完成，歷史 recovery/decompile 內容是 bootstrap authority，不再是日常工作目錄。

主要 authority：

```text
completed/l1jtw85-decompiled
```

已接受的 recovery 基準：

```text
APPLICATION_SOURCES=788
APPLICATION_CLASSES=1109
JAVAC_ERRORS=0
MAPPING_UNION=1765
MISSING=0
```

Fast Dev 不重新做反編譯。

### 3.2 BUG 修復中的核心

核心 BUG 修復有兩條權威支線：

```text
work/l1jtw85-core-fixes
completed/l1jtw85-core-fixes
```

規則：

```text
work/l1jtw85-core-fixes
= 待修 / 修復中 / 尚未完成驗證
= QUARANTINE
= 絕對不能直接進 Fast Dev active runtime

completed/l1jtw85-core-fixes
= 已修復 / 已驗證 / 已 promotion
= 唯一可同步進 Fast Dev 的 BUG repair authority
```

**不要因為 work 分支某個 Java 檔比較新，就直接覆蓋 Fast Dev source。**

### 3.3 原始 production JAR

```text
l1jserver2.jar
```

原始正式 JAR 必須保持不變。

已知 SHA-256：

```text
8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814
```

Fast Dev、bootstrap、測試、release 工具都不得覆蓋它。

---

## 4. 最終目標目錄

Fast Dev 完成後，日常只需要理解下面幾個目錄：

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
│  ├─ repair-registry.json
│  └─ repair-registry.md
│
├─ tools\850\
│  ├─ bootstrap\
│  ├─ compiler\
│  ├─ repair-sync\
│  └─ release\
│
├─ .build850\
│  ├─ cache\
│  ├─ classes\
│  ├─ abi\
│  ├─ logs\
│  ├─ state.json
│  └─ dependency-index.json
│
├─ dist\
│  ├─ l1jserver2-dev.jar
│  └─ release\
│
├─ recovery\
│
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

### 歷史/底層目錄

```text
recovery/
recovered-src-obf/
tools/production-rebuild/
```

這些資料保留作 recovery、mapping、正式 runtime 與驗證證據，但未來不應作為一般核心修改入口。

---

## 5. 新核心目錄與 namespace

Fast Dev 不會把：

```text
l1r/aj
l1r/ao
l1r/ap
...
```

繼續當作新的核心工作目錄。

目標是直接建立正常、可閱讀、package/path 對齊的 Java source tree，例如：

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

這樣 VS Code / Java Language Server / 搜尋 / refactor 才能正常使用。

完整映射保存於：

```text
core/package-map.csv
```

邏輯欄位：

```text
OriginalInternal
RecoveredInternal
DevInternal
SourceFile
Category
```

例如：

```text
aj/bk
→ l1r/aj/C_NpcAction
→ l1j/server/clientpackets/C_NpcAction
```

任何無法確定的 package/class mapping 必須 **fail closed**，不能猜測後繼續搬移。

---

## 6. Fast Dev Runtime

Fast Dev 會建立一次性的 readable runtime baseline：

```text
.build850/cache/850-dev-base.jar
```

它不是每次編譯重建。

Cache identity 至少由以下內容決定：

```text
original l1jserver2.jar SHA-256
package-map.csv SHA-256
Java major version
Fast Dev compiler schema version
```

只要以上沒有改變，就直接重用。

日常執行 classpath：

```text
.build850/classes
.build850/cache/850-dev-base.jar
lib/*
```

`.build850/classes` 優先，所以新編譯 class 可以直接覆蓋 baseline 中同 identity 的 class。

**日常修改不需要每次重新封裝 JAR。**

需要單一 JAR 時才執行 `-Pack`。

---

## 7. 操作指令

以下 CLI 已實作並有 contract / CI 驗證：

```powershell
# 預設：增量編譯
.\build850.ps1

# 編譯成功後啟動 Dev Runtime
.\build850.ps1 -Run

# 監看 core/src，存檔後自動增量編譯
.\build850.ps1 -Watch

# 強制完整 readable application compile
.\build850.ps1 -Full

# 清理 Fast Dev cache/state，再 bootstrap
.\build850.ps1 -Clean

# 同步新的 completed BUG 修復
.\build850.ps1 -Sync

# 產生單一 readable dev JAR
.\build850.ps1 -Pack
```

未來才考慮：

```powershell
.\build850.ps1 -Release
```

`-Release` 才可能執行：

```text
semantic namespace
→ production mapping
→ optional obfuscation
→ optional encryption
→ release JAR
```

目前不要把 release/加密需求混進 Fast Dev compile loop。

---

## 8. BUG Repair Registry

Fast Dev 必須持續知道：

- 哪些 BUG 尚未修
- 哪些正在 work 分支修復
- 哪些已 promotion
- 哪些已同步進 Fast Dev
- 哪些 promotion source 尚未能在 Dev namespace 編譯

Registry 檔：

```text
core/repair-registry.json
core/repair-registry.md
```

狀態：

```text
PENDING
IN_REPAIR
PROMOTED_NOT_SYNCED
SYNCED_DEV
DEFERRED_COMPILE
VALIDATED_DEV
```

同步原則：

```text
completed repaired source wins
work/in-progress source never wins
unrepaired source stays active until promotion
```

`-Sync` 流程：

```text
refresh work + completed authority
        ↓
找出新 promotion BUG
        ↓
取得 exact validated source scope
        ↓
轉成 Dev semantic namespace
        ↓
staging compile
        ↓
PASS → atomic replace core/src + classes
FAIL → 保留 last-known-good runtime
       registry = DEFERRED_COMPILE
```

### Pending snapshot

2026-09-24 的舊 recount 曾有 20 顆 L2 pending。

之後 `BUG-850-039 / 043 / 046` 已 promotion，因此 Fast Dev bootstrap snapshot 曾縮成：

```text
010
027
032
033
034
049
057
058
059
082
083
087
089
095
100
102
103
```

共 17 顆。

**這只是 bootstrap snapshot，不是永久權威。**

BUG 修復支線仍持續前進，因此未來接手者不要人工相信「17」這個數字，必須由 registry / completed evidence 動態重算。

---

## 9. 目前已實作 / 已驗證

Fast Dev 第一階段工具鏈已完成，不再是「只有基礎元件」的施工狀態。

### Source / mapping

```text
TOP_LEVEL_APPLICATION_SOURCES=788
TOP_LEVEL_MAPPING_IDENTITIES=788
APPLICATION_RUNTIME_CLASS_MAPPINGS=1109
DUPLICATE_DEV_IDENTITY=0
RECOVERY_NAMESPACE_IN_CORE=0
```

已完成完整 semantic package rules、`core/package-map.csv`、`core/source-index.json`、`core/runtime-class-map.json` 與 788 top-level source materialization。

### Dev baseline / incremental compiler

已完成：

- `.build850/cache/850-dev-base.jar`
- source hash / state
- ABI fingerprint
- reverse dependency index
- changed top-level family compile
- ABI 變更時 reverse-dependent rebuild
- unknown dependency closure → full compile
- class staging + atomic publish
- compile failure preserves last-known-good classes/state
- PBMessageALL、PBMessageALL2..9 baseline-only generated protocol policy

### Frontend

已完成並可使用：

```text
build850.ps1
build850.cmd
-Run
-Watch
-Full
-Clean
-Sync
-Pack
```

`-Watch` 已有 compile-on-save contract；`-Clean` 已修正為清除 `.build850` 後重新 bootstrap usable baseline/state。

### Repair authority

已完成：

```text
completed repair precedence = PASS
work/in-progress quarantine = PASS
promotion scope atomicity = PASS
-Sync authority atomicity = PASS
```

### Pack / runtime

已完成：

- deterministic readable dev JAR packing
- Java 8 Fast Dev runtime
- MySQL 5.7 startup
- `db/8.5.sql` import
- DB-backed server startup smoke
- port 2000 listening gate
- production `l1jserver2.jar` SHA preservation

### 最新完整 CI

```text
FAST_DEV_MAIN_RUN=208
MAIN_RUN_ID=36211693807
FAST_DEV_FULL_COMPILE_RUN=35
FULL_COMPILE_RUN_ID=36211693810
HEAD=9b5fc03c0f8892be29c9ea0bcca6073f18f0d638
STATUS=PASS
```

Main #208 已包含：frontend、Watch compile-on-save、pack、real migration、real core materialization、automatic bootstrap、MySQL 5.7、8.5 DB import、DB-backed runtime smoke 與 port 2000 gate。

Full Compile #35 已包含：baseline-only generated-source contract、compile-ready authority contract 與 real Fast Dev full compile，全部 PASS。

完整日常操作與驗證紀錄：

```text
docs/850-fast-dev.md
```

---

## 10. 尚未完成 / 下一個 Gate

Fast Dev **第一階段自動化 build/runtime gate 已完成**。舊版首頁列出的 1–16 項不再是待辦，不得重新做一次。

目前真正尚未完成的是目前 Fast Dev runtime 的第二階段 8.50c client gate：

```text
ACCOUNT_LOGIN=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
CHAR_SELECT=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
ENTER_GAME=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
```

這三項必須用未修改的 8.50c client 實際連 Fast Dev runtime 驗證；不能因為 server startup / DB / port 2000 PASS 就自行改成 PASS。

未來 release remap / obfuscation / encryption 仍屬另一階段，**不是 Fast Dev 第一階段未完成項目**。

---

## 11. 舊 Production Rebuild 的定位

現有：

```text
tools/production-rebuild/
```

不要刪。

它已驗證 production/obfuscated runtime 的安全重建方式，是：

- 正式 runtime 參考
- inverse-remap 參考
- future Release Mode 基礎
- Fast Dev 發生 namespace/runtime 問題時的 fallback comparison

但它 **不是日常快速編譯器**。

Fast Dev 不應每次修改 Java 後重新跑 production rebuild。

---

## 12. 未來接手者必讀

接手這條支線後，先遵守以下順序：

```text
1. 讀本 README
2. 讀 docs/850-fast-dev.md
3. 讀 Fast Dev design spec
4. 讀 implementation plan（注意：原始 checklist 是歷史執行計畫，首頁第 9/10 節才是目前狀態）
5. 檢查最新 work/l1jtw85-core-fixes HEAD
6. 檢查最新 completed/l1jtw85-core-fixes HEAD
7. 檢查 Fast Dev CI
8. 不重做已完成 Fast Dev 基礎設施；目前下一個人工 gate 是 8.50c client login → character select → enter-game
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
DO NOT treat recovery/ as the new normal source tree
DO NOT copy work-branch BUG core into active runtime
DO NOT overwrite l1jserver2.jar
DO NOT force 880 ABI/package assumptions onto 850
DO NOT add daily obfuscation/remap back into the fast loop
DO NOT hard-code pending BUG count
DO NOT silently guess unknown semantic package mappings
```

### 正確工作入口

```text
Fast Dev architecture:
work/l1jtw85-fast-dev-build

Pending/in-progress BUG repair:
work/l1jtw85-core-fixes

Validated repair authority:
completed/l1jtw85-core-fixes

Accepted decompile baseline:
completed/l1jtw85-decompiled
```

---

## 13. 完成定義

Fast Dev 第一階段目前狀態：

```text
READABLE_CORE_TREE=PASS
PACKAGE_MAP_COMPLETE=PASS
APPLICATION_SOURCE_COVERAGE=788/788
DUPLICATE_IDENTITY=0
WORK_BRANCH_SOURCE_LEAK=0
COMPLETED_REPAIR_PRECEDENCE=PASS
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
MYSQL_DB=PASS
PORT_2000=PASS
ORIGINAL_JAR_MODIFIED=NO
```

第二階段目前尚待實機 client 驗證：

```text
ACCOUNT_LOGIN=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
CHAR_SELECT=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
ENTER_GAME=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
```

Release remap / obfuscation / encryption 不屬於 Fast Dev 第一階段完成條件。

---

## 14. 一句話原則

```text
850 現在以「快速修改、快速編譯、快速驗證」為優先。

反編譯已完成；開發時保持可讀。
BUG 修復只吃 completed。
日常只編變更核心。
需要正式發布時，再處理 remap / 混淆 / 加密。
```
