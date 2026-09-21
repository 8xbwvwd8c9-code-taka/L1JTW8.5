# WP5 Source-Only / Runtime Dependency Closure

Status: **OPEN**

## Closed prerequisites

- Application source compile: **PASS**
- Java sources: **788**
- javac: **0 / 0**
- Generated application classes: **1109**
- Normalized class set: **0 / 0**
- Runtime hierarchy: **PASS**
- WP1 compile-ref integrity: **PASS**
- WP2 generic signature audit: **PASS**
- WP3 member ABI: **PASS_WITH_SOURCE_REPRESENTATION_EXCEPTIONS**
- WP4 mapping reversibility: **PASS**

## Current dependency boundary

The 788 recovered application Java sources currently compile against:

`recovery/compile-ref-protobuf-l1rpb.jar`

The full donor game JAR is **not** on the javac classpath.

The compile-ref is a recovery-only relocated copy of the embedded protobuf runtime:
- donor namespace: `a/**`
- recovery namespace: `l1rpb/**`
- runtime classes: **246**
- purpose: **RECOVERY_SOURCE_REPRESENTATION_ONLY**
- final runtime artifact: **NO**

WP1 proved the compile-ref mutations are structurally local and valid, including 40 typed aliases with no unexpected existing-method or class-metadata changes.

## Runtime source-only experiment

A dedicated source-only recovery job decompiles the relocated protobuf runtime with Vineflower and compiles those sources with **no binary protobuf runtime on the classpath**.

Current result:

- Runtime Java sources: **45**
- Expected runtime class files: **246**
- javac exit: **1**
- javac errors: **3954**
- error files: **32**
- generated classes: **0**
- missing runtime classes: **246**
- extra runtime classes: **0**
- source-only class-set gate: **FAIL**

Largest error files:
- `l1rpb/j.java`: **2574**
- `l1rpb/a.java`: **258**
- `l1rpb/k.java`: **176**
- `l1rpb/p.java`: **137**
- `l1rpb/c.java`: **130**
- `l1rpb/ap.java`: **115**

Dominant error families:
- cannot find symbol: **1143**
- invalid/missing override: **751**
- same-erasure name clash: **271**
- ambiguous references: **200**
- nested-type/package identity recovery failures: large repeated family

## Classification

`compile-ref-protobuf-l1rpb.jar` is currently:

**TEMPORARY_BOOTSTRAP_ONLY + SOURCE_RECOVERY_REQUIRED**

It is not classified as a legitimate permanent external runtime dependency because it is reconstructed from classes embedded in the donor core itself.

The remaining WP5 problem is therefore not application-source recovery. It is the source reconstruction of the **246-class embedded protobuf runtime**.

## Gate

WP5 remains OPEN until one of these is proven:

1. the 246 runtime classes compile from recovered source and reproduce the normalized runtime class set; or
2. exact evidence proves those runtime classes are a standard external dependency that should remain external, with a reproducible dependency/version identity and no donor-private runtime modifications.

At present, condition 2 is **not proven**.

## Next

Retain the complete protobuf-runtime source-only experiment as a CI artifact, then classify the 3954 javac errors by exact source/decompiler pattern. Fix only high-confidence representation families; do not alter application Java sources or reopen WP1-WP4.
