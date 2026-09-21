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
