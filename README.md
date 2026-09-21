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
