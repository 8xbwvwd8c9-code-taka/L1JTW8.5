# L1JTW8.5

## 專案入口

目前 `main` 保留原始 baseline。反編譯、核心修復與支線整理請從以下入口進入。

### 反編譯 / Source Recovery

目前狀態：**尚未全部反編譯完成**。

- [Recovery 首頁](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/analysis/l1jtw85-recovery/recovery/README.md)
- [反編譯問題總報告](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/analysis/l1jtw85-recovery/recovery/DECOMPILATION_ISSUES_20260921.md)
- [WP5 Source-Only / Runtime Dependency Closure](https://github.com/8xbwvwd8c9-code-taka/L1JTW8.5/blob/analysis/l1jtw85-recovery/recovery/WP5_SOURCE_ONLY_RUNTIME_DEPENDENCY_CLOSURE.md)

目前主要剩餘 blocker：

```text
embedded protobuf runtime
246 classes
45 decompiled Java files
source-only javac still OPEN
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
NOT COMPLETE
```

不要因 application javac 已經是 0，就標示整體反編譯完成。
