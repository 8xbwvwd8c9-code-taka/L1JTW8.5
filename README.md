# L1JTW8.5

## 專案入口

`main` 只作為專案導覽首頁與治理規則入口，不作為核心 source authority。

> 2026-09-25 核心支線重新整理：從現在開始，**850 核心只承認兩條遠端權威支線**。既有其他 core / repair / promote / integrate / tmp / recovery 工作支線全部視為歷史證據或退休支線，不得再作為目前核心來源；**禁止再建立新的核心相關支線**。

## 850 核心唯一兩條權威支線

| 用途 | 唯一權威支線 | 規則 |
|---|---|---|
| 反編譯原始核心 | [`completed/l1jtw85-decompiled`](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/completed/l1jtw85-decompiled) | frozen / read-only；只保存完成反編譯的乾淨 baseline，禁止寫入 BUG fix |
| 反編譯核心修復完成 | [`completed/l1jtw85-core-fixes`](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/completed/l1jtw85-core-fixes) | 所有已驗證核心修復的唯一 authority；後續完成修復只能進這條支線 |

### 禁止再建立核心支線

```text
CORE_BRANCH_POLICY=LOCKED_TWO_BRANCH_MODEL

ALLOWED_CORE_BRANCH_1=completed/l1jtw85-decompiled
ALLOWED_CORE_BRANCH_2=completed/l1jtw85-core-fixes

NEW_CORE_BRANCH=FORBIDDEN
repair/bug-*=RETIRED
promote/*core*=RETIRED
integrate/*core*=RETIRED
tmp/*core*=RETIRED
work/l1jtw85-core-fixes=RETIRED_AS_AUTHORITY
analysis/l1jtw85-recovery=HISTORICAL_EVIDENCE_ONLY
```

未來若還有核心修補：

```text
1. 來源只從 completed/l1jtw85-core-fixes 取得
2. 本地完成分析、修補、Java 8 / runtime / failure-model 驗證
3. 驗證未 PASS 前不得推到遠端核心 authority
4. PASS 後直接更新 completed/l1jtw85-core-fixes
5. 不得為單顆 BUG、批次 promotion、暫存驗證再建立遠端 branch
6. completed/l1jtw85-decompiled 永遠維持 frozen source baseline
```

既有歷史核心支線即使仍存在 Git refs，也只保留追溯用途；任何文件、代理人或對話都不得把它們重新升格為 authority。

## 核心來源優先規則

```text
1. 已修復核心唯一來源：completed/l1jtw85-core-fixes
2. 原始反編譯唯一來源：completed/l1jtw85-decompiled
3. main：只放入口、治理規則、統計與非核心導覽
4. 所有其他歷史核心支線：只讀 / 不再續作 / 不再 promotion
```

## 已修復核心 BUG 統計（L1-L3）

統計口徑：只計入已完成驗證並已有 completed authority 的 BUG；同一 BUG 在 README 重複紀錄只計一次；`PASS_ALREADY_COVERED` 仍視為該 BUG 已完成關閉。

| 等級 | 已修復完成 | 狀態 / 來源 |
|---|---:|---|
| L1 | **45** | `CONFIRMED_L1=45`、`VALIDATED_DONE_PASS=45/45` |
| L2 | **74** | 最新 `completed/l1jtw85-core-fixes` 的 `Completed repairs` 表依 BUG ID 去重 |
| L3 | **54** | `L3_REPAIR_SCOPE=54`、`REPAIRED_AND_VALIDATED=54`，後續已整合進 completed |
| **合計** | **173** | `45 + 74 + 54 = 173` |

```text
L1_FIXED=45
L2_FIXED=74
L3_FIXED=54
CORE_BUG_FIXED_TOTAL=173
COUNT_DATE=2026-09-25
```

### L1

```text
CONFIRMED_L1=45
VALIDATED_DONE_PASS=45/45
PATCHED_PENDING_VALIDATION=0
BLOCKED=0
UNPATCHED_L1=0
STATE=L1_REPAIR_COMPLETE
```

### L2

L2 統計直接以 `completed/l1jtw85-core-fixes` 首頁最上方的 `Completed repairs` 表為完成權威，按 `BUG-850-xxx` 去重；目前為 **74** 顆。不要再沿用舊首頁的 `L2_PENDING=13` snapshot，該數字已被後續 promotion 淘汰。

### L3

```text
L3_REPAIR_SCOPE=54
REPAIRED_AND_VALIDATED=54
BATCH1_7=PASS
FINAL_PROMOTION_VALIDATION=PASS
INTEGRATED_INTO_COMPLETED=YES
STATE=L3_REPAIR_COMPLETE
```

## Source Recovery 基線

```text
BRANCH=completed/l1jtw85-decompiled
STATE=FROZEN / FINAL GATE PASS
AUTHORITATIVE_SOURCE_MAPPINGS=1765
APPLICATION_SOURCE_MAPPINGS=788
PROTOBUF_SOURCE_MAPPINGS=45
THIRD_PARTY_SOURCE_MAPPINGS=932
UNKNOWN=0
AMBIGUOUS=0
SOURCE_ONLY_APPLICATION_COMPILE=PASS
EXACT_PROTOBUF_RUNTIME_LINKAGE=PASS
```

這條支線只代表原始反編譯完成基線，不接受任何 BUG fix。

## 核心修復固定規則

```text
BUG / FEATURE
→ Java CORE entry / call path
→ config/ 控制文件
→ DB table / column / loader
→ default / fallback
→ ACTIVE source
→ fresh RED / regression evidence
→ 最小完整修復
→ Java 8 / runtime / failure model validation
→ completed/l1jtw85-core-fixes
→ completion record / recount
```

不得只看 Java 核心就直接修。每次都要同時檢查：

- `config/` 控制文件
- Java call path / loader / getter
- DB table / column / row / loader
- hardcoded default / fallback
- startup load / runtime reload
- normalized / obfuscated ACTIVE forms（若兩者仍是 runtime authority）

舊 historical patch 若無法乾淨套到 latest completed，只移植 invariant，不覆蓋整個舊檔案。

## 850 其他工作入口

以下不是「核心修復支線」，不受兩條 core authority 命名限制，但不得存放服務端核心修補成果：

- [850 登入器 / 內掛開發支線](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/work/850-launcher-helper)
- [850 登入器 / 內掛開發報告](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/work/850-launcher-helper/docs/850-launcher/REPORT.md)
- [381 → 850 DB 移植分析](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/analysis/381-to-850-db-migration)
- [8.5 / 850 變身 UI 資料統整](docs/850/變身UI資料統整.md)

## 歷史首頁

2026-09-25 以前的大量 checkpoint、算術驗證、repair/promote/tmp 支線紀錄已歸檔：

- [`docs/archive/README_20260925_before_core_authority_cleanup.md`](docs/archive/README_20260925_before_core_authority_cleanup.md)

需要追舊 repair commit、舊 CI 編號或 arithmetic proof 時才進歷史紀錄；目前核心工作一律只認上方兩條權威支線。