# L1JTW8.5 Recovery

> **狀態：反編譯 / Source Recovery 尚未全部完成。**
>
> 目前 **788 個 application Java source 已可完整 javac 編譯，1109 個 application class 的 class-set / hierarchy / member recovery gate 已通過**；但 donor 內嵌的 **246-class protobuf runtime** 尚未完成 source-only recovery，因此不得標示「反編譯完成」。

Branch: `analysis/l1jtw85-recovery`  
Donor ground truth: `l1jserver2.jar`

## Current status

| Gate | Status |
|---|---|
| Application Java sources | **788** |
| Application javac | **PASS / 0 errors** |
| Generated application classes | **1109** |
| Normalized class set | **PASS / 0 missing / 0 extra** |
| Runtime hierarchy | **PASS** |
| Compile-ref integrity | **PASS** |
| Generic builder audit | **PASS with documented metadata differences** |
| Member ABI recovery | **PASS with documented source/compiler exceptions** |
| Mapping reversibility | **PASS** |
| Embedded protobuf runtime source-only compile | **OPEN / FAIL** |
| Final source-only dependency closure | **OPEN** |
| Full recovery completion | **NOT COMPLETE** |

## Primary remaining blocker

The embedded protobuf runtime currently has:

- runtime class files: **246**
- decompiled Java files: **45**
- source-only javac errors: **3954**
- error files: **32**
- generated classes: **0**
- missing runtime classes: **246**

Largest current error concentrations:

| File | Errors |
|---|---:|
| `l1rpb/j.java` | 2574 |
| `l1rpb/a.java` | 258 |
| `l1rpb/k.java` | 176 |
| `l1rpb/p.java` | 137 |
| `l1rpb/c.java` | 130 |
| `l1rpb/ap.java` | 115 |

Current recovery binary dependency:

`recovery/compile-ref-protobuf-l1rpb.jar`

Classification:

**TEMPORARY_BOOTSTRAP_ONLY + SOURCE_RECOVERY_REQUIRED**

It is not a final runtime artifact.

## Latest omission audit

A quick cross-check against the active recovery transforms found several previously handled source-representation families that were under-documented. They are now recorded in the issue ledger, including hard-tail multi-decompiler overrides, L1Craft static-factory shadowing, Builder caller aliases, residual runtime imports, runtime type shadows, inner visibility metadata, targeted bridge flags, and abstract-obligation handling.

This audit does **not** change the completion state: **full decompilation/source-only recovery is still NOT complete**.

## Encountered decompilation problems

The complete issue ledger is maintained in:

**[`DECOMPILATION_ISSUES_20260921.md`](./DECOMPILATION_ISSUES_20260921.md)**

Major problem families encountered so far:

- obfuscated package/class/member identities;
- Java-keyword class/member names;
- JVM-valid but Java-source-illegal nested same-name classes;
- protobuf runtime namespace/type shadowing;
- generic builder superclass representation;
- parser generic/covariant bridge reconstruction;
- builder typed aliases and return-type-only JVM identities;
- decompiler-materialized synthetic bridge collisions;
- synthetic boolean accessor reconstruction;
- javac `access$NNN` synthetic accessor name/return-shape differences;
- outer-instance/captured/enum synthetic fields;
- generated-only parser/builder/helper methods;
- generic Signature metadata differences;
- non-protobuf local generic / overload / `@Override` / duplicate-local problems;
- SourceFile filename collisions requiring recovery disambiguation;
- embedded protobuf runtime source-only compile failure.

Resolved and rejected approaches are recorded in the issue ledger. Do not repeat broad transforms without donor evidence.

## 核心修復支線：Runtime 控制來源檢查規則

> **任何核心 BUG 檢查、修復、功能還原，都不得只看 Java 核心。**
>
> 每一個修復項目都必須同步檢查：
>
> 1. **控制端 / `config/` 內的設定文件**
> 2. **Java 核心實際 call path / loader / getter / fallback**
> 3. **資料庫 DB table / column / row / loader**
>
> 只有在追完三邊來源後，才決定真正需要修改的範圍。

### 固定檢查順序

```text
FEATURE / BUG
    ↓
Java code entry
    ↓
call path / loader / getter
    ↓
config / properties / txt / xml
    ↓
DB table / column / row
    ↓
default / fallback / hardcoded value
    ↓
startup load / runtime reload
    ↓
ACTIVE / CONDITIONAL / FALLBACK / UNUSED / UNKNOWN
    ↓
最小完整修復
```

### 每次修復至少要回答

1. 這個功能從哪個 Java 入口進入？
2. 實際呼叫哪些 loader / table / parser / getter？
3. `config/` 有沒有控制開關、倍率、ID、路徑、預設值？
4. DB 是否有對應 table / column / row？
5. Java 是否還有 hardcoded default / fallback？
6. config / DB / core 三邊是否存在互相覆蓋或優先順序？
7. 資料是在 server startup 載入，還是可以 runtime reload？
8. 找到的 config 或 DB 欄位是否真的有被 runtime 使用，而不是只存在但未生效？
9. 修復後是否要同步更新 config / DB / core，還是只需要改其中一個 ACTIVE source？
10. 驗證時是否同時驗證三邊來源沒有留下衝突值？

### 固定 Runtime Source Map

每個 BUG / 功能修復都建立：

```text
FEATURE
→ CORE ENTRY
→ CALL PATH
→ CONFIG SOURCE
→ DB SOURCE
→ DEFAULT / FALLBACK
→ CONDITION / SWITCH
→ LOAD TIME / RELOAD
→ ACTIVE SOURCE
→ REQUIRED CHANGE
→ VALIDATION
```

### 禁止事項

- 不得看到 Java 常數就直接改，未確認 config / DB 是否覆蓋。
- 不得看到 DB 欄位就直接改，未確認 loader 是否真的讀取。
- 不得看到 config 檔存在就假設 runtime 正在使用。
- 不得只修 core 後就宣告完成。
- 不得把 `FILE EXISTS` 當作 `RUNTIME ACTIVE`。
- 找不到來源時標記 `UNKNOWN`，不得猜測。

### 核心修復支線輸出格式

```text
ISSUE=<name>
CORE_ENTRY=<path:symbol>
CONFIG_SOURCE=<path/key or NONE>
DB_SOURCE=<table.column / query or NONE>
FALLBACK=<value/path or NONE>
ACTIVE_SOURCE=<CORE|CONFIG|DB|MIXED|UNKNOWN>
ROOT_CAUSE=<exact>
FIX_SCOPE=<CORE|CONFIG|DB|MIXED>
MODIFIED=<files/tables>
RESTART_REQUIRED=YES|NO|UNKNOWN
VALIDATION=<exact test>
STATUS=PASS|FAIL|BLOCKED
```

這條規則適用於後續所有核心修復支線，包括登入、封包、角色、職業、技能、NPC、地圖、掉落、道具、倉庫、交易、強化、活動、VIP、傳送、持久化與其他 server runtime 功能。

## Recovery rules

1. `main` remains untouched.
2. All recovery work stays on `analysis/l1jtw85-recovery`.
3. Donor `l1jserver2.jar` is the ABI/behavior ground truth.
4. Do not use the full donor game JAR as final application compile fallback.
5. Recovery-only namespace/name/metadata transforms must be deterministic and reversible.
6. Do not globally strip `Signature`.
7. Do not globally delete or toggle synthetic/bridge members.
8. Do not change gameplay, DB, protocol, or balance behavior during recovery.
9. Closed work stays closed unless new production/compiler evidence reopens it.
10. Do not claim full completion while WP5 source-only/runtime dependency closure is open.

## Closed major recovery gates

- WP1 — Compile-Ref Method Integrity: **PASS**
- WP2 — Generic Builder Signature Audit: **PASS**
- WP3 — Full Method/Field ABI Recovery Audit: **PASS_WITH_SOURCE_REPRESENTATION_EXCEPTIONS**
- WP4 — Mapping Reversibility: **PASS**

Current:

- WP5 — Source-Only / Runtime Dependency Closure: **OPEN**
- Final Gate: **OPEN**

## Authoritative evidence

Key recovery evidence:

- `normalized_stage_compile.json`
- `post_javac0_class_hierarchy.json`
- `generic_builder_signature_audit.json`
- `protobuf_compile_ref_method_integrity.json`
- `wp3_final_member_abi_exception_ledger.json`
- `mapping_reversibility.json` / external verified WP4 evidence
- `wp5_source_only_runtime_dependency_closure.json`
- `DECOMPILATION_ISSUES_20260921.md`

## Accurate completion wording

Allowed now:

- **APPLICATION SOURCE RECOVERY COMPILES COMPLETELY.**
- **APPLICATION CLASS SET MATCHES COMPLETELY.**
- **RUNTIME CLASS HIERARCHY MATCHES COMPLETELY.**
- **APPLICATION MEMBER RECOVERY GATE PASSES WITH DOCUMENTED SOURCE-REPRESENTATION EXCEPTIONS.**

Not allowed yet:

- **FULL DECOMPILATION COMPLETE**
- **FULL SOURCE-ONLY RECOVERY COMPLETE**
- **NO BINARY RECOVERY DEPENDENCY REMAINS**

Those statements become valid only after WP5 and the Final Gate pass.

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

完整規則：[`recovery/BRANCH_LIFECYCLE.md`](./BRANCH_LIFECYCLE.md)
