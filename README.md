# L1JTW8.5

## 專案入口

目前 `main` 保留原始 baseline。反編譯、核心修復與支線整理請從以下入口進入。


### 850 資料整理 / 客戶端研究

- [850 登入器 / 內掛開發支線](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/work/850-launcher-helper) — 僅收納 850 LoginWithoutUI、外部 IP/Port/ServerName、850 Helper、Inventory/Item bridge、LinHelperZ donor 研究與 UI；不收錄主線服務端核心/DB/修復文件。
- [850 登入器 / 內掛開發報告](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/work/850-launcher-helper/docs/850-launcher/REPORT.md) — AUTHORITY=850；381/880 僅作功能與 UI donor；第一個主要 PASS gate 為 850 背包道具列舉（WP6）。

- [8.5 / 850 變身 UI 資料統整](docs/850/變身UI資料統整.md) — 整理 `Tile.idx → PolymorphUI.xml / Polymorphlist*.xml`、`Text.idx → desc-c.tbl`、`polymorphs` / `etcitem` DB 對應，以及尚待核心驗證的 item-use / UI packet 路徑。

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

- [最新核心修復對話交接（2026-09-22）](recovery/L1JTW85_CORE_REPAIR_HANDOFF_20260922.md) — 短版 checkpoint；先看此檔再接續 `work/l1jtw85-core-fixes`。

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

### 最新 L1 修復進度（2026-09-22 checkpoint）

```text
WORK_BRANCH=work/l1jtw85-core-fixes
CONFIRMED_L1=45
REPAIR_LANE_COVERAGE=43/45
PATCHED_PENDING_VALIDATION=40
VALIDATED_DONE_PASS=1
PARTIAL_BLOCKER=BUG-850-208
UNPATCHED_L1=BUG-850-105,BUG-850-106
FINISHED_FAMILY_ACTIVE_CLAIMS=0
GITHUB_WORKFLOW_RUNS_FOR_LATEST_PATCH=0
STATE=PAUSED_CLEAN_CHECKPOINT
```

本次工作已告一段落並停在乾淨 checkpoint。完整 L1 authority 以 `DUAL_LANE_CORE_WORK_LEDGER.md` 的 `CONFIRMED L1` 分類為準，共 45 顆；現行 audit 內直接標成 `Tier: L1` 的 27 段只是較晚加入的明示 tier，不代表完整 L1 universe。

目前 45 顆中已有 43 顆建立 repair lane：40 顆為 `PATCHED_PENDING_VALIDATION`、`BUG-850-114` 為既有 `DONE/PASS`、`BUG-850-208` 為 `PARTIAL_BLOCKED_ROUTE_AUTHORITY`。尚未進 repair lane 的 L1 只剩 `BUG-850-105` 與 `BUG-850-106`；本次依使用者要求不再開新修補。

本輪最後收斂的 castle withdrawal 家族 `BUG-850-012 / 040 / 045` 已完成：要求 self objid、正數提款、crown + current clan leader + matching castle ownership，使用 shared castle synchronization，並以專用 `public_money` DB-first persistence 成功後才發 Adena。這同時關閉不足餘額發錢、負數灌大 treasury、缺少 leader authorization，以及 withdrawal read-modify-write concurrency 風險。

`BUG-850-014 / 015` ShopWorld 亦已由並行工作完成，現為 `PATCHED_PENDING_VALIDATION`：正數 count、overflow-safe long total、client/server price exact match，再進 transactional account/item persistence。

`BUG-850-208` 仍維持 partial：六個 ship map 的 ticket mapping 與 consume-success fail-closed 已修，但 380/381/880 與公開 L1J-TW 3.80c 都沒有 server-authoritative route table，destination map/x/y 仍由 client 提供；因此沒有虛構 route whitelist。

目前 GitHub 最新 patch 沒有 workflow run，work branch 也沒有可直接使用的 `.github/workflows`，所以除既有 PASS 證據外，修補不得 promotion 到 `completed/l1jtw85-core-fixes`。下一次恢復工作時，優先順序：`BUG-850-105` → `BUG-850-106` → targeted compile/runtime validation → 再處理 `BUG-850-208` route-authority blocker。

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

### 最新核心修復即時紀錄（2026-09-22 23:38 +08:00）

```text
BUG-850-287=DONE/PASS (CI 35743735957)
BUG-850-12=PATCHED_PENDING_VALIDATION
BUG-850-12_ARITHMETIC=PASS
ADENA_CAP=2,000,000,000
BOUNDARY_2B=VALID
BOUNDARY_2B_PLUS_1=REJECT
COUNT_GT_TREASURY=REJECT
COUNT_ZERO=REJECT
INT32_INTERMEDIATE_SAFE=NO
LONG_ARITHMETIC_REQUIRED=YES
```

BUG-850-12 算術權威：`newAdena=(long)adena+count`、`newTreasury=(long)treasury-count` 必須先用 long 計算再驗證；不得以 int32 先相加後才檢查上限。此項僅完成 arithmetic proof，source contract / targeted runtime / CI 尚未全數 PASS 前不得標記 DONE。

### BUG-850-14 / BUG-850-15 算術驗證（2026-09-22）

```text
ARITHMETIC=PASS
MAX_TOTAL=2,000,000,000
COUNT_POSITIVE_REQUIRED=YES
CLIENT_PRICE_EXACT_MATCH=YES
INT32_MULTIPLICATION_SAFE=NO
LONG_REQUIRED=YES
BOUNDARY=PASS
OVERFLOW_WITH_LONG=PASS
```

權威公式：`total=(long)unitPrice*count`。先用 long 計算，再驗證 `0<=total<=2,000,000,000` 且 `clientPrice==total`；不得先用 int32 乘法後再做上限檢查。等於 2,000,000,000 合法，超過即拒絕；count=0 與 client/server price mismatch 皆拒絕。此項完成 arithmetic proof，source contract / targeted runtime / CI 尚未全部 PASS 前維持 `PATCHED_PENDING_VALIDATION`。

### BUG-850-20 算術驗證（2026-09-22）

```text
ARITHMETIC=PASS
MAX_VALUE=2,000,000,000
PRICE_INT32_SAFE=NO
WEIGHT_INT32_SAFE=NO
LONG_REQUIRED=YES
BOUNDARY=PASS
OVERFLOW_WITH_LONG=PASS
```

權威公式：`totalPrice=(long)unitPrice*count`、`totalWeight=(long)unitWeight*count`。兩者都必須先用 long 計算，再各自驗證 `0..2,000,000,000`；`count<=0` 直接拒絕。等於上限合法，任一價格或重量超上限即拒絕。此項僅完成 arithmetic proof，source contract / targeted runtime / CI 未全 PASS 前仍維持 `PATCHED_PENDING_VALIDATION`。

### BUG-850-44 算術驗證（2026-09-22）

```text
ARITHMETIC=PASS
MAX_TOTAL=2,000,000,000
COUNT_POSITIVE_REQUIRED=YES
PRICE_NONNEGATIVE_REQUIRED=YES
INT32_MULTIPLICATION_SAFE=NO
LONG_REQUIRED=YES
BOUNDARY=PASS
NEGATIVE_INPUT_GATE=PASS
OVERFLOW_WITH_LONG=PASS
```

權威公式：`total=(long)count*price`。先用 long 計算，再驗證 `0<=total<=2,000,000,000`；`count<=0` 或 `price<0` 直接拒絕。等於上限合法，超過即拒絕。此項僅完成 arithmetic proof，source contract / targeted runtime / CI 未全 PASS 前仍維持 `PATCHED_PENDING_VALIDATION`。

### BUG-850-93 能力值預算驗證（2026-09-22）

```text
STAT_BUDGET_ARITHMETIC=PASS
ALLOWED_L49=75
ALLOWED_L50=75
ALLOWED_L51=76
ALLOWED_L52=77
ALLOWED_L80=105
ALLOWED_L81=106
ALLOWED_L99=124
LEVEL_BOUNDARY=PASS
STAT_BUDGET=PASS
FINAL_EQUALITY_GATE=PASS
SINGLE_STAT_LT45=NOT_COVERED_BY_THIS_CASESET
```

權威公式：`allowedLevelStatTotal(level)=75+max(0,level-50)`。50級以前總量上限固定75；51級起每級增加1。升級加點必須同時滿足 `nextLevel<=targetLevel` 與 `baseStatTotal+1<=allowed(nextLevel)`；最終完成要求 `currentLevel==targetLevel` 且 `baseStatTotal==allowed(currentLevel)`。本組案例未提供單一能力值，因此 `stat<45` gate 尚未由此案例覆蓋。

### BUG-850-93 單一能力值上限驗證（2026-09-22）

```text
SINGLE_STAT_BOUNDARY=PASS
TOTAL_BUDGET_GATE=PASS
COMBINED_GATE=PASS
STAT_44_PLUS_1=VALID
STAT_45_PLUS_1=REJECT
FINAL_STAT_MAX=45
```

單一能力值與總量預算必須同時成立：`currentStat<45`、`newStat<=45`、`baseStatTotal+1<=allowed(nextLevel)`。總量通過但單一能力值已為45仍必須拒絕；單一能力值可加但總量超預算亦必須拒絕。

### BUG-850-93 靈藥能力值驗證（2026-09-22）

```text
NO_STAT_DECREASE_GATE=PASS
STAT_MAX_45_GATE=PASS
ELIXIR_DELTA_EXACT_GATE=PASS
COMBINED_GATE=PASS
```

Stage 3 權威規則：每個新能力值必須 `newStat>=oldStat` 且 `newStat<=45`；六圍增量總和 `delta` 必須精確等於 `elixirCount`。不得用一項下降抵銷另一項上升來偽造相同 delta；任一條件失敗即拒絕。

### BUG-850-93 十級跳升邊界驗證（2026-09-22）

```text
CURRENT_LEVEL_LOWER_BOUND=PASS
CURRENT_LEVEL_LT40_GATE=PASS
TARGET_LEVEL_BOUNDARY=PASS
TEN_LEVEL_JUMP_GATE=PASS
```

10級跳升權威規則：`newLevel=currentLevel+10`，且必須同時滿足 `currentLevel>=1`、`currentLevel<40`、`newLevel<=targetLevel`。39→49 可接受；40→50 必須由 `currentLevel<40` 拒絕；targetLevel 僅差9級時必須拒絕，差10級可接受。

### BUG-850-48 LuckyDraw key 驗證（2026-09-22）

```text
NON_EMPTY_GATE=PASS
DUPLICATE_GATE=PASS
AUTHORITATIVE_EXISTENCE_GATE=PASS
ALL_OR_NOTHING_GATE=PASS
REWARD_COUNT_RULE=validated unique key count only
```

權威規則：request key 清單不可為空、不可有重複 key、每一個 key 都必須存在 authoritative pendingMap。任一 key 不存在時整批拒絕，不得部分發獎；成功時 rewardCount 等於完整驗證後的 unique key 數量。

### BUG-850-75 Mail owner gate 驗證（2026-09-22）

```text
OWNER_GATE=PASS
EXISTENCE_GATE=PASS
BATCH_INDIVIDUAL_GATE=PASS
CROSS_PLAYER_ACCESS=REJECT
```

權威規則：每個 client-selected mail id 都必須先 resolve 存在，再要求 `mail.inbox_id==pc.id`。單封 read/status/delete 與 batch delete 都不得只靠 global mail id 存在性授權。Batch 採 per-entry gate：合法 mail 可處理，非法或不存在 mail 必須各自拒絕，不得被同批其他合法 mail 繞過。

### BUG-850-19 Achievement reward gate 驗證（2026-09-22）

```text
PROGRESS_GATE=PASS
CLAIMED_GATE=PASS
IDEMPOTENCY_GATE=PASS
OVERFLOW_GATE=PASS
ZERO_REQUIRED=ALLOWED_BY_CURRENT_RULE
ZERO_REQUIRED_POLICY=NEEDS_DATA_AUTHORITY
```

權威規則目前為 `progress>=required && claimed==0`；達標或超額達標皆可領，成功後 claimed 必須轉為 1，重放請求必須拒絕。當 `required=0` 時，依現行規則會直接視為達標；是否應另加 `required>0` 不由算術決定，必須查 achievement/character_mobs 的資料定義與 loader，確認 0 是否為合法配置後再決定。

### BUG-850-12/40/45 城堡提款守恆驗證（2026-09-22）

```text
COUNT_POSITIVE_GATE=PASS
COUNT_LE_TREASURY_GATE=PASS
DB_BEFORE_GRANT_ORDER_GATE=PARTIAL
LOCK_SERIALIZATION_GATE=PASS
ADENA_CAP=2,000,000,000
ADENA_CAP_GATE=REQUIRED
CONSERVATION_CASE3=FAIL
CONSERVATION_CASE8=FAIL_WITHOUT_LOCK_OR_CAS
CONSERVATION_CASE10=FAIL_WITHOUT_COMPENSATION
POST_DB_GRANT_FAILURE_CONSERVATION=FAIL
```

既有專案權威沿用玩家 Adena 上限 `2,000,000,000`，所以提款在 DB UPDATE 前還必須 preflight `(long)adena+count<=2,000,000,000`；CASE6 應拒絕，CASE7 剛好到上限可接受。Castle lock 可防兩筆提款同時以舊 treasury 通過，但 DB-first 仍不足以單獨保證金流守恆：若 DB 已扣款而 grant 失敗，必須有 transaction/compensation/可證明恢復機制，否則 CASE10 仍為 loss-of-funds。守恆條件：`treasury_before-treasury_after == adena_after-adena_before == count`。

### 最新核心修復停止點（2026-09-22）

本輪依要求停止工作。以下為恢復時的 authoritative checkpoint：

```text
STATE=PAUSED_CLEAN_CHECKPOINT
WORK_BRANCH=work/l1jtw85-core-fixes
STOP_AFTER=BUG-850-126 transaction analysis
NEXT_REPAIR=BUG-850-124
DO_NOT_ADVANCE_UNTIL_RESUME=YES
```

#### Calculation authority

```text
CALC-009=PASS_WITH_CORRECTIONS
COMMIT=bc79acad47536c8bfb3ace30da175882cfcbe3cf

CALC-010=PASS_WITH_CLARIFICATIONS
COMMIT=d53abb39b1460efb0859031e435ad6639684cf0b

CALC-011=PASS_WITH_CORRECTIONS
COMMIT=58e1fcafac8395f79481e196906f08ccaab8b451
```

CALC-011 重要修正：

```text
ceil(LONG_MAX / INT_MAX)=4_294_967_299
```

不是 4_294_967_298。Inventory stack / ceilDiv / CAS / object-id allocation authority 已寫入 `recovery/CALC_AUTHORITY_LEDGER_20260922.md`。

#### L2 repair checkpoint

```text
BUG-850-132=PASS_PROMOTED
BUG-850-136=PASS_PROMOTED
BUG-850-137=PASS_PROMOTED

BUG-850-130=PARTIAL_BLOCKED_TRANSACTION_CLOSURE
BUG-850-129=PARTIAL_BLOCKED_TRANSACTION_CLOSURE
BUG-850-126=PARTIAL_BLOCKED_TRANSACTION_CLOSURE

NEXT=BUG-850-124
```

`BUG-850-130`：NPC material exchange 的 input/output inventory persistence 與 `Contribution` RAM mutation 不在同一 durable boundary。不得以單獨 character save / Contribution UPDATE 假裝完成；需 inventory + Contribution 的 transaction closure。

`BUG-850-129`：inn refund 目前分散在 key item removal、Adena refund、`inns` count/delete、InnTable RAM state。`character_items` 已知為 MyISAM，現階段無法證明跨資源 rollback，因此保留 blocker。

`BUG-850-126`：karma-for-item exchange 的 inventory persistence 與 `characters.Karma` persistence 分離。單獨補 character save 不構成 atomic repair；需 item mutation + Karma expected-state update 共用 durable transaction，或有可證明的 idempotent compensation。

Repair log checkpoint：

```text
BUG-850-129_LOG_COMMIT=ab3f52473b3c937ffbdbe4d2625b5c977ea6537f
BUG-850-126_LOG_COMMIT=47d2e6356ffc0ee54ca65ccb24ad6d8d8cbc2b21
```

恢復工作時先讀：

- `recovery/L2_CORE_REPAIR_LOG_20260922.md`
- `recovery/CALC_AUTHORITY_LEDGER_20260922.md`
- `recovery/DUAL_LANE_CORE_WORK_LEDGER.md`

然後從 `BUG-850-124` 繼續。L2 backlog 未清空前，不進 L3。
