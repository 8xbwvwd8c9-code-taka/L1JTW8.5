# L1JTW8.5 Production JAR Rebuild Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a deterministic inverse-remap and test-JAR pipeline that deploys only validated completed repairs into the original obfuscated L1JTW8.5 runtime namespace.

**Architecture:** Reuse the accepted normalized-source compile path and `recovery/source_namespace_map.csv`. Implement the missing reverse classfile relocation by adapting the already-used forward constant-pool rewrite strategy, then patch a copy of the authoritative original JAR and validate namespace/linkage/manifest preservation. Final runtime/login gates remain local to `I:\L1JTW8.5`.

**Tech Stack:** Python 3, Java 8-compatible `javac`/`javap`, ZIP/JAR, GitHub Actions.

**Spec:** `recovery/PRODUCTION_JAR_REBUILD_DESIGN_20260924.md`

## Global Constraints

- `SUBAGENTS=0`.
- `NO_REPO_WIDE_SCAN=YES`.
- Source authority: `completed/l1jtw85-core-fixes@fc473aef65485d1524283fa34d01ab7fad9a7b93`.
- Recovery authority: `completed/l1jtw85-decompiled@c0c00f9ad36b45dbca75f5c79b7f02ae20b0038c` / accepted recovery head `999f2f6571e6984217c33cf0449cc8c7f1679dbd`.
- Original `l1jserver2.jar` SHA-256 must remain `8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814`.
- Never consume unvalidated source from `work/l1jtw85-core-fixes`.
- Never overwrite production `l1jserver2.jar`.
- Do not change client, DB schema, launcher, or port as part of this task.

## Review Focus

- String constants that happen to contain class-like text must not be rewritten unless structurally referenced.
- Named inner-class `InnerClasses.inner_name_index` must restore the original obfuscated simple name.
- Anonymous/local `$<number>` classes must retain numeric identity.
- Descriptors/signatures/annotation descriptors containing normalized object names must return to original internal names.
- JAR patching must preserve non-class resources and manifest bytes while replacing only validated classes.

---

### Task 1: Inverse remapper contract tests

**Files:**
- Create: `tools/production-rebuild/tests/test_inverse_remap.py`
- Create: `.github/workflows/l1jtw85-production-rebuild.yml`

**Interfaces:**
- Consumes: `recovery/source_namespace_map.csv` format `OldInternal,NewInternal,Kind`.
- Produces: executable tests defining `inverse_remap.py` API and classfile behavior.

- [ ] **Step 1: Write failing tests** for map loading, top-level class remap, descriptors, string-constant preservation, named/anonymous inner classes, and unmapped class rejection.
- [ ] **Step 2: Add CI workflow** that runs only the production-rebuild test suite on pushes to `work/l1jtw85-production-jar-rebuild`.
- [ ] **Step 3: Observe RED** because `tools/production-rebuild/inverse_remap.py` does not exist.

### Task 2: Implement inverse classfile remapper

**Files:**
- Create: `tools/production-rebuild/inverse_remap.py`

**Interfaces:**
- Consumes: compiled normalized class bytes plus authoritative namespace CSV.
- Produces: remapped original-runtime-name class bytes and output path.

- [ ] **Step 1: Reuse the forward remapper constant-pool parser model** from `tools/normalized-recovery/build-normalized-jar.py` rather than inventing a second class parser.
- [ ] **Step 2: Reverse `NewInternal -> OldInternal`** for all mapped top/inner classes.
- [ ] **Step 3: Rewrite structural UTF8 entries** while preserving constant-string-only entries.
- [ ] **Step 4: Patch named-inner simple names** in `InnerClasses`; preserve anonymous/local numeric names.
- [ ] **Step 5: Fail closed** for duplicate reverse mappings, unmapped normalized application classes, malformed classfiles, or output collisions.
- [ ] **Step 6: Run Task 1 tests GREEN**.

### Task 3: Build and patch repaired test JAR

**Files:**
- Create: `tools/production-rebuild/build_repaired_test_jar.py`
- Create: `tools/production-rebuild/tests/test_build_repaired_test_jar.py`

**Interfaces:**
- Consumes: original `l1jserver2.jar`, normalized compiled classes, namespace CSV.
- Produces: `recovery/production-build/l1jserver2.repaired-test.jar`, `REPLACED_CLASSES.md`, machine-readable manifest/state JSON.

- [ ] **Step 1: Write failing tests** that create a small fixture JAR and prove original resources/manifest are byte-preserved, only mapped class entries are replaced, and production input is unchanged.
- [ ] **Step 2: Implement build script** with original SHA-256 gate, isolated output directory, inverse remap invocation, duplicate/collision checks, and atomic output replacement.
- [ ] **Step 3: Emit replacement audit** containing original class name, normalized class name, compiled path, remapped runtime path, and source authority.
- [ ] **Step 4: Run unit tests GREEN**.

### Task 4: Structural validation command

**Files:**
- Create: `tools/production-rebuild/validate_repaired_test_jar.py`
- Create: `tools/production-rebuild/tests/test_validate_repaired_test_jar.py`
- Modify: `.github/workflows/l1jtw85-production-rebuild.yml`

**Interfaces:**
- Consumes: repaired test JAR and namespace CSV.
- Produces: `VALIDATION_RESULT.md/json` and nonzero exit on leakage/linkage/manifest failure.

- [ ] **Step 1: Write failing tests** for `l1r/` namespace leakage, missing remapped entry, wrong internal class name, and changed manifest/resource.
- [ ] **Step 2: Implement structural checks** including `javap`/classfile internal-name identity, mapped class closure, no unexpected normalized namespace leakage, and original non-class preservation.
- [ ] **Step 3: Extend CI** to run all production-rebuild tests.
- [ ] **Step 4: Run GREEN**.

### Task 5: Local production-build driver and runbook

**Files:**
- Create: `tools/production-rebuild/build-production-test.ps1`
- Create: `recovery/BUILD_REPRODUCTION.md`

**Interfaces:**
- Consumes: existing normalized recovery scripts and completed repaired source tree.
- Produces: one local command sequence for compile -> inverse remap -> test JAR -> structural validation.

- [ ] **Step 1: Pin source/JAR hashes and expected paths** under `I:\L1JTW8.5`.
- [ ] **Step 2: Reuse existing normalized compile stage**; do not redo decompilation.
- [ ] **Step 3: Build only `l1jserver2.repaired-test.jar`** and never replace production automatically.
- [ ] **Step 4: Document final manual/runtime gates** for C3P0, DB, map/spr/mob initialization, port 2000, unchanged 8.50c account login, character select, and enter-game.

### Task 6: Final verification

**Files:**
- Modify as needed only to fix Critical/Important findings.

**Interfaces:**
- Consumes: all above outputs.
- Produces: final branch evidence and exact remaining blocker if local runtime/login has not yet been exercised.

- [ ] **Step 1: Run complete CI/unit suite**.
- [ ] **Step 2: Verify branch diff contains no generated JAR binary commit**.
- [ ] **Step 3: Verify production JAR blob/hash authority unchanged**.
- [ ] **Step 4: Report `PASS` only if compile/remap/JAR/server/login all pass; otherwise report `PARTIAL` with one concrete next action.**
