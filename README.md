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
