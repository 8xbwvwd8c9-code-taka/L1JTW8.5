# L1JTW8.5

> **Recovery status: NOT FULLY DECOMPILED / NOT FULLY SOURCE-ONLY YET**

The active recovery work is on:

`analysis/l1jtw85-recovery`

Current verified application-core state:

- 788 recovered application Java sources
- javac: **0 errors**
- 1109 generated application classes
- normalized class set: **0 missing / 0 extra**
- runtime hierarchy: **PASS**
- member recovery gate: **PASS with documented reversible/source-representation exceptions**
- mapping reversibility: **PASS**

The remaining blocker is the embedded protobuf runtime:

- 246 runtime classes
- 45 decompiled Java files
- source-only javac errors: **3954**
- source-only runtime class-set gate: **OPEN / FAIL**

Therefore this repository must **not** be described as fully decompiled yet.

## Recovery homepage

See:

- [Recovery status / homepage](recovery/README.md)
- [Decompilation issue ledger](recovery/DECOMPILATION_ISSUES_20260921.md)
- [WP5 source-only dependency closure](recovery/WP5_SOURCE_ONLY_RUNTIME_DEPENDENCY_CLOSURE.md)

Donor ground truth: `l1jserver2.jar`

`main` remains the original baseline; recovery changes belong on the recovery branch.

## 支線整理規則

L1JTW8.5 後續固定採三支線生命週期：

```text
completed/l1jtw85-decompiled   = 只存放完整反編譯完成 baseline
completed/l1jtw85-core-fixes  = 只存放已完成且驗證 PASS 的核心修復
work/l1jtw85-core-fixes       = 尚未完成 / 尚未驗證的核心修復工作區
```

修復流程：

```text
未完成修復
→ work/l1jtw85-core-fixes
→ 同時檢查 CORE + config + DB
→ 修復與驗證 PASS
→ 移入 completed/l1jtw85-core-fixes
```

當所有核心修復完成後，`work/l1jtw85-core-fixes` 必須清空、確認無唯一未保存成果後刪除。最終專案成果只保留兩條完成支線：

```text
completed/l1jtw85-decompiled
completed/l1jtw85-core-fixes
```

> `main` 永遠保留原始 baseline，不計入上述兩條成果支線。
>
> 目前反編譯 Final Gate 尚未完成，因此不得提前建立或宣稱 `completed/l1jtw85-decompiled` 已完成。

完整規則：[`recovery/BRANCH_LIFECYCLE.md`](recovery/BRANCH_LIFECYCLE.md)
