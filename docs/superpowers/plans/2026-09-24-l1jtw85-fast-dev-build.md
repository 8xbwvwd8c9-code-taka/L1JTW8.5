# L1JTW8.5 Fast Development Build Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build a readable-source-first, incremental L1JTW8.5 development toolchain where ordinary core edits compile immediately and completed BUG repairs sync into the dev tree without rerunning the recovery/remap production pipeline.

**Architecture:** Preserve the original production JAR and recovery trees as immutable authorities, generate a semantic `core/src` tree plus complete package map, bootstrap one cached readable dev runtime, then overlay incrementally compiled classes at runtime. BUG repair state is dynamically reconciled from `work/l1jtw85-core-fixes` and `completed/l1jtw85-core-fixes`; work-only repairs never enter the active dev runtime.

**Tech Stack:** PowerShell 7, Python 3, Java 8 `javac`, ZIP/JAR classfile rewriting already proven by `tools/production-rebuild`, Git/GitHub Actions.

**Spec:** `docs/superpowers/specs/2026-09-24-l1jtw85-fast-dev-build-design.md`

## Global Constraints

- `l1jserver2.jar` is immutable and must never be overwritten.
- Default mode is readable Fast Dev; remap/obfuscation is not part of normal development.
- `core/src` becomes the only normal human-editing source root after migration.
- `work/l1jtw85-core-fixes` is quarantine only; only completed/PASS promotions can replace active dev source.
- Incremental compilation is used when dependency safety is proven; otherwise escalate to full readable-source compile.
- Compile failure must preserve the last-known-good class overlay.
- No repo-wide BUG re-audit; repair state comes from existing authorities/evidence.
- Java runtime/compiler compatibility remains Java 8 for server classes.

## Review Focus

1. A changed source whose public/protected ABI changes must rebuild reverse dependents or fail closed to a full compile.
2. A work-branch-only BUG repair must never enter `core/src` or `.build850/classes`.
3. A completed promotion that touches multiple source files must sync atomically as one promotion scope.
4. A compile failure must not delete/replace the previously runnable class overlay.
5. Package-map generation must reject duplicate or unmapped application identities rather than invent a silent fallback package.

---

### Task 1: Semantic Package Map Generator

**Files:**
- Create: `tools/850/bootstrap/package_map.py`
- Create: `tools/850/bootstrap/package_rules.json`
- Create: `tools/850/tests/test_package_map.py`
- Generate later: `core/package-map.csv`

**Interfaces:**
- Consumes: `class_source_mapping.csv`, accepted normalized/recovery source identities.
- Produces: `build_package_map(mapping_rows, rules) -> list[PackageMapEntry]`, `validate_package_map(entries) -> None`, CSV columns `OriginalInternal,RecoveredInternal,DevInternal,SourceFile,Category`.

- [ ] **Step 1: Write failing tests** for known identities (`aj/bk -> l1j/server/clientpackets/C_NpcAction`, `ao/a -> l1j/server/datatables/AccountTable`), duplicate rejection, unknown-category rejection, and complete uniqueness.
- [ ] **Step 2: Run tests and verify RED** because `package_map.py` does not exist.
- [ ] **Step 3: Implement minimal rule-driven mapper**. Rules are explicit by original package family plus named overrides; there is no default `misc` fallback for unknown application packages.
- [ ] **Step 4: Run tests and verify GREEN**.
- [ ] **Step 5: Commit** `feat(fast-dev): add semantic package map generator`.

### Task 2: Core Source Bootstrap

**Files:**
- Create: `tools/850/bootstrap/bootstrap_core.py`
- Create: `tools/850/tests/test_bootstrap_core.py`
- Generate: `core/src/**`, `core/source-index.json`, `core/package-map.csv`

**Interfaces:**
- Consumes: Task 1 package map; accepted recovered source baseline; completed repair authority.
- Produces: deterministic semantic source tree with rewritten package/import/type references and `source-index.json` recording source authority per top-level class.

- [ ] **Step 1: Write failing tests** proving package declaration rewriting, cross-source reference rewriting, completed-over-baseline precedence, work-only exclusion, duplicate identity rejection, and source-count equality.
- [ ] **Step 2: Run tests and verify RED**.
- [ ] **Step 3: Implement bootstrap staging** into a temporary directory; publish `core/src` only after all mapped sources validate.
- [ ] **Step 4: Validate all accepted application top-level sources are represented exactly once** and original JAR hash is unchanged.
- [ ] **Step 5: Run tests GREEN and commit** `feat(fast-dev): bootstrap readable core source tree`.

### Task 3: Dynamic BUG Repair Registry and Sync

**Files:**
- Create: `tools/850/repair-sync/repair_registry.py`
- Create: `tools/850/repair-sync/sync_repairs.py`
- Create: `tools/850/tests/test_repair_registry.py`
- Generate: `core/repair-registry.json`, `core/repair-registry.md`

**Interfaces:**
- Consumes: work/completed branch heads and existing repair evidence; Task 2 source index.
- Produces: states `PENDING`, `IN_REPAIR`, `PROMOTED_NOT_SYNCED`, `SYNCED_DEV`, `DEFERRED_COMPILE`, `VALIDATED_DEV`; promotion-scoped source candidate sets.

- [ ] **Step 1: Write failing tests** for 20-count snapshot -> completed promotion of 039/043/046 -> 17 effective pending, work-only quarantine, multi-file promotion grouping, and stale registry refresh.
- [ ] **Step 2: Run RED**.
- [ ] **Step 3: Implement dynamic reconciliation**; never hard-code pending count as authority.
- [ ] **Step 4: Implement staged sync candidate generation** without mutating active dev source.
- [ ] **Step 5: Run GREEN and commit** `feat(fast-dev): add repair registry and sync`.

### Task 4: Readable Dev Runtime Bootstrap Cache

**Files:**
- Create: `tools/850/bootstrap/build_dev_base.py`
- Create: `tools/850/tests/test_dev_base.py`
- Reuse internally: proven classfile relocation code from `tools/production-rebuild`.
- Generate: `.build850/cache/850-dev-base.jar`, `.build850/cache/cache-key.json`

**Interfaces:**
- Consumes: original JAR, Task 1 package map.
- Produces: semantic-namespace application runtime baseline and cache key based on original JAR SHA, package map SHA, Java major, compiler schema.

- [ ] **Step 1: Write failing tests** for class/internal-name rewrite, descriptor/signature/inner-class rewrite, resource preservation, string-constant preservation, cache hit/miss, and original JAR immutability.
- [ ] **Step 2: Run RED**.
- [ ] **Step 3: Implement semantic relocation bootstrap** by adapting the existing proven classfile transformer rather than writing a second parser.
- [ ] **Step 4: Run structural closure validation and tests GREEN**.
- [ ] **Step 5: Commit** `feat(fast-dev): build cached readable dev runtime`.

### Task 5: Incremental Compiler and Dependency State

**Files:**
- Create: `tools/850/compiler/incremental.py`
- Create: `tools/850/compiler/dependencies.py`
- Create: `tools/850/compiler/abi.py`
- Create: `tools/850/tests/test_incremental.py`
- Generate: `.build850/state.json`, `.build850/dependency-index.json`, `.build850/abi/**`, `.build850/classes/**`

**Interfaces:**
- Consumes: `core/src`, Task 4 dev-base JAR, `lib/*`.
- Produces: atomic class overlay and state describing hashes, generated class families, ABI fingerprints, and reverse dependencies.

- [ ] **Step 1: Write failing tests** for method-body-only one-class compile, ABI-change dependent expansion, unknown dependency closure -> full compile, deleted source cleanup, failed compile preserving last-known-good classes.
- [ ] **Step 2: Run RED**.
- [ ] **Step 3: Implement source hashing + staging compile** using Java 8 `javac`.
- [ ] **Step 4: Implement ABI fingerprint + reverse-dependency update** only after PASS.
- [ ] **Step 5: Run GREEN and commit** `feat(fast-dev): add incremental Java compiler`.

### Task 6: Fast Dev Frontend

**Files:**
- Create: `build850.ps1`
- Create: `build850.cmd`
- Create: `tools/850/fast_dev.py`
- Create: `tools/850/tests/test_frontend_contract.py`

**Interfaces:**
- Consumes: Tasks 2-5.
- Produces commands `default`, `-Run`, `-Watch`, `-Full`, `-Clean`, `-Sync`, `-Pack`.

- [ ] **Step 1: Write failing contract tests** for command parsing, production-JAR overwrite prohibition, default incremental behavior, `-Run` classpath order, `-Watch` core-only monitoring, and `-Clean` cache reset.
- [ ] **Step 2: Run RED**.
- [ ] **Step 3: Implement PowerShell thin wrapper + Python coordinator**.
- [ ] **Step 4: Implement `-Run` as classpath overlay** `.build850/classes;850-dev-base.jar;lib/*`, not JAR replacement.
- [ ] **Step 5: Implement `-Watch` debounced compile-on-save** without automatic live-server restart.
- [ ] **Step 6: Run GREEN and commit** `feat(fast-dev): add build850 frontend`.

### Task 7: Pack, CI, Migration Verification, and Handoff

**Files:**
- Create: `tools/850/release/pack_dev.py`
- Create: `.github/workflows/850-fast-dev.yml`
- Create: `docs/850-fast-dev.md`
- Update: `CORE_REPAIR_STATUS_20260924.md`

**Interfaces:**
- Consumes: all prior tasks.
- Produces: `dist/l1jserver2-dev.jar`, CI evidence, user runbook.

- [ ] **Step 1: Write failing pack/CI contract tests** proving resources preserved, overlay wins, original JAR unchanged, registry sync cannot consume work-only repairs.
- [ ] **Step 2: Run RED**.
- [ ] **Step 3: Implement `-Pack` deterministic readable dev JAR**.
- [ ] **Step 4: Add CI bootstrap + incremental smoke** including Java 8 compile and startup smoke where environment permits.
- [ ] **Step 5: Run full suite** and verify migration/source counts, package-map uniqueness, pending repair registry, cache reuse, one-class incremental rebuild, full compile fallback, and original JAR SHA preservation.
- [ ] **Step 6: Update status/runbook and commit** `docs(fast-dev): record validated fast development workflow`.

## Plan Self-Review

- Spec coverage: source migration, semantic namespace, cached dev runtime, overlay execution, incremental/ABI rebuild, watch mode, dynamic repair sync, packaging, original-JAR preservation, and future-only release remap all have explicit tasks.
- Placeholder scan: no TBD/TODO implementation placeholders are used.
- Interface consistency: package map flows Task 1 -> 2/4; source index flows Task 2 -> 3; dev base flows Task 4 -> 5/6; compiler/registry flow into Task 6/7.
- Review Focus coverage: each of the five high-risk failure modes has an explicit test in Tasks 1, 3, or 5.
