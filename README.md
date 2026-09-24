# L1JTW8.5

## 專案入口

`main` 只作為原始 baseline 與專案導覽首頁。核心修復、待修 BUG 與完成成果，請依下列權威入口進入。

> 2026-09-25 更新：核心修復支線已整理。後續不要再以歷史 `repair/bug-*`、`tmp/*` 或舊 handoff 當作目前核心權威；真正的核心工作只認「待修復 BUG 支線」與「修復完畢支線」。

## 核心修復權威入口

| 用途 | 權威支線 / 文件 |
|---|---|
| 待修 BUG、audit、修補中、驗證中 | [`work/l1jtw85-core-fixes`](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/work/l1jtw85-core-fixes) |
| 待修 BUG 權威 ledger | [`recovery/DUAL_LANE_CORE_WORK_LEDGER.md`](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/work/l1jtw85-core-fixes/recovery/DUAL_LANE_CORE_WORK_LEDGER.md) |
| L2 當前協調 / pending authority | [`recovery/L2_REPAIR_COORDINATION_20260924.md`](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/work/l1jtw85-core-fixes/recovery/L2_REPAIR_COORDINATION_20260924.md) |
| 已完成並驗證的核心 | [`completed/l1jtw85-core-fixes`](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/completed/l1jtw85-core-fixes) |
| 已完成乾淨反編譯 baseline | [`completed/l1jtw85-decompiled`](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/completed/l1jtw85-decompiled) |

### 核心來源優先規則

```text
1. 已修復核心：completed/l1jtw85-core-fixes
2. 尚未完成 / 正在修：work/l1jtw85-core-fixes
3. completed/l1jtw85-decompiled 只作 frozen source baseline，不寫入 BUG fix
4. main 只放 baseline + 導覽 / 紀錄，不作修補成果來源
5. 歷史 repair/bug-*、tmp/* 只視為歷史證據，不作目前 authority
```

每處理一顆 BUG 前都要重新 refresh `work` / `completed` HEAD，避免平行對話重複修補或以舊 source 覆蓋較新的 hardened source。

## 最新 L2 修復狀態（2026-09-25）

最近一次已驗證 recount：

```text
L1_PENDING=0
L2_PENDING=13
L3_PENDING=0
ACTIVE_CLAIMS=NONE
PENDING_IDS=010,027,032,033,034,082,083,087,089,095,100,102,103
```

最近已完成 promotion：

```text
BUG-850-057=PASS_PROMOTED
BUG-850-058=PASS_PROMOTED
BUG-850-059=PASS_PROMOTED
```

最近 snapshot（每次開工前仍必須重新 refresh）：

```text
WORK_BRANCH=work/l1jtw85-core-fixes
WORK_HEAD=7252848b341e18cb43bb82eecffb18311e886d14

COMPLETED_BRANCH=completed/l1jtw85-core-fixes
COMPLETED_HEAD=12c7727b516a9e5cc4f8cc9fdcf52c57fc060359
```

### 下一批：BUG-850-082 → BUG-850-083

`BUG-850-082`：**CONFIRMED L2**。`ClanTable` 建盟流程在 `clan_data INSERT` 失敗後仍可能繼續發布 clan live state、character/member state，caller 也可能繼續扣除建盟費，造成 durable / live / economy 三個邊界分裂。

`BUG-850-083`：**CONFIRMED L2**。刪盟流程在 `clan_data DELETE` 失敗後仍可能繼續進行 warehouse 與 RAM destructive cleanup，造成 clan 與 warehouse durable state 分裂。

處理順序固定：

```text
082
→ fresh RED on latest completed
→ 找 historical invariant / current active source
→ minimal semantic repair 或 PASS_ALREADY_COVERED
→ Java 8 no-new-regression
→ runtime / failure-model validation
→ completed head race check
→ promotion + completion record

083
→ 同一套獨立驗證流程

完成 082/083 後 fresh recount
EXPECTED_L2_PENDING=11
```

## 核心修復固定規則

```text
BUG / FEATURE
→ Java CORE entry / call path
→ config/ 控制文件
→ DB table / column / loader
→ default / fallback
→ ACTIVE source
→ fresh RED
→ 最小完整修復
→ Java 8 / runtime / failure model validation
→ race check
→ promotion 到 completed
→ ledger / report / recount
```

不得只看 Java 核心就直接修。每次都要同時檢查：

- `config/` 控制文件
- Java call path / loader / getter
- DB table / column / row / loader
- hardcoded default / fallback
- startup load / runtime reload
- normalized / obfuscated active forms（若兩者都仍是 runtime authority）

舊 historical patch 若無法乾淨套到 latest completed，**只移植 invariant，不覆蓋整個舊檔案**。

## 已完成的重要基線

### Source Recovery

```text
completed/l1jtw85-decompiled
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

### L1 修復

```text
CONFIRMED_L1=45
VALIDATED_DONE_PASS=45/45
PATCHED_PENDING_VALIDATION=0
BLOCKED=0
UNPATCHED_L1=0
STATE=L1_REPAIR_COMPLETE
```

L1 無需重新掃描，除非有新的 regression evidence。

## 850 其他工作入口

- [850 登入器 / 內掛開發支線](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/work/850-launcher-helper)
- [850 登入器 / 內掛開發報告](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/work/850-launcher-helper/docs/850-launcher/REPORT.md)
- [381 → 850 DB 移植分析](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/tree/analysis/381-to-850-db-migration)
- [8.5 / 850 變身 UI 資料統整](docs/850/變身UI資料統整.md)

## 歷史首頁

2026-09-25 以前 main README 內的大量歷史 checkpoint、算術驗證與支線整理紀錄已原樣歸檔，避免首頁持續混入已失效狀態：

- [`docs/archive/README_20260925_before_core_authority_cleanup.md`](docs/archive/README_20260925_before_core_authority_cleanup.md)

需要追舊 repair commit、舊 CI 編號或先前 arithmetic proof 時，再進歷史首頁；目前工作一律以本頁上方的兩條核心權威支線與 ledger 為準。
