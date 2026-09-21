# L1JTW8.5

## 專案入口

目前 `main` 保留原始 baseline。反編譯、核心修復與支線整理請從以下入口進入。

### 反編譯 / Source Recovery

目前狀態：**完成 / Final Gate PASS**。

```text
completed/l1jtw85-decompiled
HEAD=008e4c6e62c5d6b87aec1a367a72fb1a70cf33f5

AUTHORITATIVE_SOURCE_MAPPINGS=1765
APPLICATION_SOURCE_MAPPINGS=788
PROTOBUF_SOURCE_MAPPINGS=45
THIRD_PARTY_SOURCE_MAPPINGS=932

APPLICATION_JAVA_SOURCES=788
PROTOBUF_JAVA_SOURCES=45
UNKNOWN=0
AMBIGUOUS=0
DUPLICATE_CLASS_ASSIGNMENTS=0

SOURCE_ONLY_APPLICATION_COMPILE=PASS
EXACT_PROTOBUF_RUNTIME_LINKAGE=PASS
FINAL_SOURCE_REPRESENTATION=PASS
```

- [完成的反編譯 baseline](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/completed/l1jtw85-decompiled)

`completed/l1jtw85-decompiled` 是已接受的乾淨反編譯基線。後續 BUG audit / core fix 不再寫回此分支。

#### 反編譯過程遇到的問題與解法

##### 1. 1765 統計曾混用 classfile 與 source mapping

早期曾把 `1109 application generated classes`、`246 protobuf runtime classes` 與 remaining 數量直接相加。這是錯誤粒度：`1765` 的 authoritative universe 是 **source mapping rows**，而 1109/246 包含 inner / anonymous classes。

最後改成：

```text
1765 = 788 application source mappings
     + 45 protobuf source mappings
     + 932 remaining third-party source mappings
```

`1109 application classes` 與 `246 protobuf runtime classes` 僅保留作 runtime / ABI 指標，不再與 source mapping universe 混算。

##### 2. Application javac=0 不代表整體反編譯完成

Application core 已先達成：

```text
JAVA_SOURCES=788
JAVAC_ERRORS=0
GENERATED_APPLICATION_CLASSES=1109
MISSING=0
EXTRA=0
RUNTIME_HIERARCHY=PASS
MEMBER_ABI_GATE=PASS
MAPPING_REVERSIBILITY=PASS
```

但 embedded protobuf runtime 尚未完成 source-only closure 時，仍不能宣告整體完成。

解法：把 Application recovery、protobuf recovery、exact-runtime linkage、final source representation 拆成獨立 gate，最後全部 PASS 才建立 completed branch。

##### 3. Embedded protobuf 2.5.0 是 45 個 source 對 246 個 runtime class

最終確認：

```text
protobuf source mappings=45
official protobuf-java 2.5.0 Java sources=45
runtime classes=246
class map=246/246
member map=5999/5999
```

解法：以官方 protobuf-java 2.5.0 source 重建，再建立 donor ↔ canonical class/member mapping，而不是把 246 個 classfile 誤當成 246 個 source。

##### 4. Source-built protobuf 與 donor ABI 有細部差異

主要差異：

```text
ACC_BRIDGE-only mismatches=827
interface order-only mismatches=4
InnerClasses name patches=1379
```

解法：不使用 donor binary 當 runtime output，而是對 source-built artifact 做 deterministic ABI normalization：

- 827 個 member flag 修正；
- 4 個 interface order 修正；
- 1379 個 InnerClasses name 修正。

最後 exact ABI：

```text
donor_classes=246
built_classes=246
missing=0
extra=0
major_mismatch=0
access_mismatch=0
super_mismatch=0
interface_mismatch=0
field_table_mismatch=0
method_table_mismatch=0
PASS
```

##### 5. javac 看不到部分 builder bridge

protobuf compile-view 中部分 builder bridge 帶 `ACC_SYNTHETIC`，導致 javac 不把它們當正常 abstract satisfaction candidate。

解法：

- exact runtime artifact 保持不變；
- 額外建立 compile-view；
- 只對 9 個已證明 builder bridge 清除 synthetic flag；
- 不改 descriptor、bytecode name 或 exact runtime ABI。

結果：

```text
SOURCE_ONLY_APPLICATION_WITH_SOURCE_BUILT_PROTOBUF=PASS
APPLICATION_JAVA_SOURCES=788
GENERATED_APPLICATION_CLASSES=1109
DONOR_PROTOBUF_BINARY_ON_CLASSPATH=false
FULL_DONOR_GAME_JAR_ON_CLASSPATH=false
```

##### 6. compile-view 不能變成 runtime dependency

因此另外建立 exact-runtime linkage gate，確認 application classfiles 在 runtime 不依賴 compile-view 特有內容。

```text
compile_view_on_runtime_classpath=false
compile_view_runtime_dependency_count=0
method_context_unresolved_exact_runtime_refs=0
PASS
```

##### 7. 932 個 third-party mappings 不能靠名稱硬猜

大量 library class 經過 obfuscation，單靠 package/name heuristic 會留下數百 UNKNOWN。

最後只接受 deterministic evidence：

- exact class identity；
- relocation map；
- structural fingerprint；
- 已證明 library source identity。

最終：

```text
REMAINING_TOTAL=932
CLASSIFIED=932
UNKNOWN=0
AMBIGUOUS=0
DUPLICATE_CLASS_ASSIGNMENTS=0

c3p0=151
mchange_commons=467
mysql_connector_java=267
lombok=47
```

第三方 source 不 vendored 進 application tree，只保留 deterministic identity ledger。

##### 8. Final Gate 要把 accounting 與 identity closure 分開

早期只要 remaining 還有 UNKNOWN，整個 final gate 就 FAIL，連已正確證明的 source accounting 都無法穩定保存。

最後拆成：

```text
FINAL_1765_SOURCE_MAPPING_ACCOUNTING
FINAL_SOURCE_IDENTITY_CLOSURE
FINAL_SOURCE_REPRESENTATION
```

因此 accounting、identity、source representation 各自有明確 PASS/FAIL，不再混淆失敗原因。

##### 9. completed branch 不從 recovery branch 原地清檔

`analysis/l1jtw85-recovery` 含 recovery tools、logs、CI evidence、暫存 artifacts，不能原地刪一刪就當完成成果。

最後以 fresh clean tree / orphan branch 組裝：

```text
src/application/ = 788 Java
src/protobuf/    = 45 Java
identity/        = minimum final manifests
README.md
```

遠端最終驗證：

```text
TOTAL_JAVA_SOURCES=833
RECOVERY_WORKSPACE_FILES_PRESENT=NO
TOOLS_PRESENT=NO
GITHUB_RECOVERY_WORKFLOW_PRESENT=NO
DONOR_BINARY_PRESENT=NO
JAR_FILES_PRESENT=NO
FORBIDDEN_FILES=0
```

#### 完成後固定規則

```text
completed/l1jtw85-decompiled
= frozen decompilation/source baseline
= 不放 BUG fix
= 不放 recovery experiment
= 不放 donor JAR

work/l1jtw85-core-fixes
= BUG audit / 尚未完成修補

completed/l1jtw85-core-fixes
= 已完成並驗證的 core fixes
```

### 核心修復

核心修復固定規則：

```text
BUG / FEATURE
→ Java CORE entry / call path
→ config/ 控制文件
→ DB table / column / loader
→ default / fallback
→ ACTIVE source
→ 最小完整修復
→ runtime validation
```

不得只看 Java 核心就直接修。

每次都要同時檢查：

- `config/` 內的控制文件
- Java 核心實際 call path / loader / getter
- DB table / column / row / loader
- hardcoded default / fallback
- startup load / runtime reload

完整規則請見：

- [Recovery / 核心修復首頁](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/analysis/l1jtw85-recovery/recovery/README.md)

### 最新 L1 修復進度（2026-09-21）

```text
BUG-850-114
STATUS=PASS
WORK_BRANCH=work/l1jtw85-core-fixes

VALID_L1_PROGRESS=14/45
NEXT_L1=BUG-850-137
BUG-850-137=PATCHED_PENDING_VALIDATION
BUG-850-140=PATCHED_PENDING_VALIDATION
BUG-850-141=PATCHED_PENDING_VALIDATION
```

BUG-850-114 已完成 canonical 吸收與驗證紀錄。來源物品 41761 會先驗證並成功扣除，再建立 41762；bookmark export 使用 transaction / rollback，失敗時回滾替代品並補回來源。

BUG-850-137 已在 work branch 補上正數檢查、`300L * amount` 溢位邊界，以及「扣款成功才建立房卡/租約」；GitHub 此提交沒有 workflow run，因此仍需 targeted Java compile / runtime 驗證後才能標 DONE 或 promotion。

BUG-850-140 / 141 已在 work branch 補上房屋出售與競標第二階段的伺服器端重驗：房屋/血盟 ownership、leader/royal、keeper/interaction context、sale/deadline、以及由目前 DB/runtime house 狀態重新計算最低競標價。兩項同樣等待 targeted Java compile / runtime gate。

### 支線整理規則

目前遠端支線已重新盤點，固定分成「正式保留 / 工作中 / 待退休或待吸收」三類。

#### 正式保留

```text
main
= 原始 baseline + 專案首頁 / 文件
= 不作為反編譯或修補成果分支

completed/l1jtw85-decompiled
= 已完成反編譯的 frozen clean baseline
= HEAD 008e4c6e62c5d6b87aec1a367a72fb1a70cf33f5
= 後續禁止寫入 BUG fix / recovery experiment

completed/l1jtw85-core-fixes
= 已完成且通過驗證的 core-fix 成果
= 只接受已完整驗證的修補
```

#### 工作中

```text
work/l1jtw85-core-fixes
= 唯一主要工作支線
= BUG audit / 未完成修補 / 驗證中的修補
= 目前 audit-only / bug-only 工作持續在此進行
```

#### 待退休 / 待吸收

```text
analysis/l1jtw85-recovery
= RETIRE_READY
= source recovery Final Gate 已 PASS
= completed/l1jtw85-decompiled 已建立並獨立驗證
= 不再新增 recovery 工作

repair/bug-850-114
= HOLD
= 尚有 1 個相對 work branch 的唯一修補 commit
= 不可直接刪除
= 等恢復 repair mode 時驗證並收斂到 canonical core-fix flow
```

目前遠端實際 branch 數：

```text
6

main
completed/l1jtw85-decompiled
completed/l1jtw85-core-fixes
work/l1jtw85-core-fixes
analysis/l1jtw85-recovery        # RETIRE_READY
repair/bug-850-114               # HOLD / unique fix
```

### 最終支線目標

Source recovery 已完成，因此 `analysis/l1jtw85-recovery` 不再是 active authority。

核心修補全部完成後，最終專案只保留：

```text
main
completed/l1jtw85-decompiled
completed/l1jtw85-core-fixes
```

其中：

- `completed/l1jtw85-decompiled`：完整反編譯、未套 BUG 修復的 frozen baseline。
- `completed/l1jtw85-core-fixes`：完整反編譯 + 全部已驗證 BUG 修復。
- `main`：原始 baseline 與專案入口文件。

`work/l1jtw85-core-fixes` 只在仍有 audit / repair 工作時存在；全部成果 promotion 完成後退休。

`repair/bug-850-114` 必須先證明其唯一修補已被 canonical core-fix branch 吸收或明確拒絕，才能刪除。

### 支線硬規則

```text
NO NEW RECOVERY BRANCHES
NO NEW ONE-OFF REPAIR BRANCHES unless explicitly required
ONE ACTIVE WORK BRANCH = work/l1jtw85-core-fixes
DECOMPILED COMPLETED BRANCH = FROZEN
MAIN = BASELINE + DOCS
```

如果需要臨時驗證，優先使用 commit / CI artifact，不再為每個 probe 建永久 branch。

## 目前重要狀態

```text
Application source recovery:
788 Java sources
javac = 0 errors
generated application classes = 1109
class set = 0 missing / 0 extra
runtime hierarchy = PASS
member recovery gate = PASS
mapping reversibility = PASS

Full source-only recovery:
PASS

completed/l1jtw85-decompiled:
008e4c6e62c5d6b87aec1a367a72fb1a70cf33f5
```

反編譯 Final Gate 已完成；後續不再把 BUG 修復混入 completed decompilation baseline。
