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
BUG-850-105 / BUG-850-106
COMMIT=034029c
STATUS=SUBMITTED

VALID_L1_PROGRESS=13/41
NEXT_UNFIXED_L1=BUG-850-114
```

BUG-850-114 下一步驗證範圍：

```text
物品轉換來源驗證
→ 扣除結果
→ 替代品授予
→ DB inventory API
→ partial-success boundary
```

修復原則：不能只交換操作順序；必須確認來源物品驗證、扣除、替代品授予與 DB inventory API 的結果形成完整一致的成功/失敗邊界，避免留下部分成功狀態。

### 支線整理規則

目前採三支線生命週期：

```text
analysis/l1jtw85-recovery
= 反編譯尚未完成的工作支線

completed/l1jtw85-core-fixes
= 已完成且驗證 PASS 的核心修復

work/l1jtw85-core-fixes
= 尚未完成 / 尚未驗證完成的核心修復
```

反編譯 Final Gate 完成後：

```text
analysis/l1jtw85-recovery
→ completed/l1jtw85-decompiled
```

所有核心修復完成後：

```text
work/l1jtw85-core-fixes
→ PASS 成果移入 completed/l1jtw85-core-fixes
→ work branch 刪除
```

最終成果支線：

```text
completed/l1jtw85-decompiled
completed/l1jtw85-core-fixes
```

`main` 永遠保留原始 baseline，不計入上述兩條成果支線。

完整支線規則：

- [Branch Lifecycle](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/analysis/l1jtw85-recovery/recovery/BRANCH_LIFECYCLE.md)

## 2026-09-21 支線整理更新

本次已完成一次遠端支線收斂，重複、已吸收與純 CI probe 支線均已清理。

### 已刪除的重複 / 已吸收支線

```text
analysis/l1jtw85-bug-audit
fix/l1jtw85-audit-remediation
recovery/l1jtw85-processed-checkpoint
recovery/l1jtw85-verified
completed/l1jtw85-decompiled-fixes-20260921
```

其中：

- `analysis/l1jtw85-bug-audit` 的內容已被目前 BUG audit 工作吸收。
- `fix/l1jtw85-audit-remediation` 已被 `completed/l1jtw85-core-fixes` 吸收。
- `recovery/l1jtw85-processed-checkpoint` 已被 `analysis/l1jtw85-recovery` 吸收。
- `recovery/l1jtw85-verified` 的 recovery 資料已併入現行 recovery；唯一缺少的 `VERIFIED_RECOVERY_README.md` 亦已保存至 `analysis/l1jtw85-recovery`。
- `completed/l1jtw85-decompiled-fixes-20260921` 與 `completed/l1jtw85-core-fixes` 原本指向相同 commit，屬重複支線。

### 已刪除的 CI probe 支線

`ci/l1jtw85-runtime-probe` 至 `ci/l1jtw85-runtime-probe26` 共 26 條均已完成驗證並刪除。

每條 CI probe 相對 recovery 主工作支線只保留一個獨立 trigger commit，內容僅為：

```text
tools/recovery/.runtime-probe-trigger*
```

這些 trigger 不包含唯一 recovery/source 成果，因此不需要長期保留。

### 目前遠端必要支線

```text
main
analysis/l1jtw85-recovery
work/l1jtw85-core-fixes
completed/l1jtw85-core-fixes
```

整理時確認的 HEAD：

```text
main
488a7e63f77c2fe5da78018c50c40c97cbdfc843

analysis/l1jtw85-recovery
7829533cae021f01d57a2451ec28426e138cbd3a

work/l1jtw85-core-fixes
8b01f0f2c6cff8b2b1c2ca1c3166f0defc8d435a

completed/l1jtw85-core-fixes
e4ca01a6ed34af1b407da6d53b3b5ddcab955427
```

### 最終支線目標

工作進行期間允許保留 `analysis/*` 與 `work/*`，但最終成果固定收斂為：

```text
completed/l1jtw85-decompiled
= 完整反編譯、未套用 BUG 修復的原始核心

completed/l1jtw85-core-fixes
= 完整反編譯 + 全部已驗證 BUG 修復的核心
```

完成條件：

1. Source Recovery Final Gate = PASS 後，將 `analysis/l1jtw85-recovery` 收成 `completed/l1jtw85-decompiled`。
2. 全部 BUG 修復、編譯與必要 runtime validation = PASS 後，將成果統一收至 `completed/l1jtw85-core-fixes`。
3. 兩個 Final Gate 都完成後，刪除不再需要的 `analysis/*` / `work/*` 暫時支線。
4. `main` 保留原始 baseline，不作為修復成果支線。

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
