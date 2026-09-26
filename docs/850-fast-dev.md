# L1JTW8.5 Fast Dev Runbook

Branch: `work/l1jtw85-fast-dev-build`  
Status date: 2026-09-26

## Purpose

Fast Dev is the readable-source development path for L1JTW8.5. It reuses the accepted decompile/mapping authority and validated completed repairs, then compiles only the changed Java scope whenever that is safe.

It is intentionally separate from the production rebuild/remap path. Daily development must not overwrite or rebuild the original production JAR.

## Authority rules

```text
accepted decompile baseline:
completed/l1jtw85-decompiled

pending / in-progress repairs:
work/l1jtw85-core-fixes
= QUARANTINE
= never wins active Fast Dev source

validated repair authority:
completed/l1jtw85-core-fixes
= only completed/promoted repair source may sync into Fast Dev
```

Production JAR:

```text
l1jserver2.jar
SHA256=8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814
RULE=IMMUTABLE
```

## Working layout

```text
core/src/                              readable Java working source
core/package-map.csv                  semantic top-level mapping
core/source-index.json                source identity index
core/runtime-class-map.json           complete application runtime mapping
core/repair-registry.json             repair state
core/repair-registry.md               repair state summary

.build850/cache/850-dev-base.jar      readable runtime baseline
.build850/classes/                     compiled overlay
.build850/state.json                  source / ABI state
.build850/dependency-index.json       reverse dependency state

dist/l1jserver2-dev.jar              optional packed readable dev JAR
```

Runtime classpath order:

```text
.build850/classes
.build850/cache/850-dev-base.jar
lib/*
```

The overlay is first, so newly compiled classes replace the same semantic identity from the cached baseline without modifying `l1jserver2.jar`.

## Daily commands

Run from `I:\L1JTW8.5`.

```powershell
# Default: incremental compile
.\build850.ps1

# Compile then start Fast Dev runtime
.\build850.ps1 -Run

# Monitor core/src; compile after source changes
.\build850.ps1 -Watch

# Force a complete readable application compile
.\build850.ps1 -Full

# Remove generated Fast Dev state/cache, then bootstrap a fresh usable baseline/state
.\build850.ps1 -Clean

# Fetch/reconcile new completed repair authority and safely sync it
.\build850.ps1 -Sync

# Build deterministic readable dev JAR
.\build850.ps1 -Pack
```

`-Watch` performs compile-on-save only. It does not automatically restart a running server.

## Incremental compiler behavior

```text
source hash changed
        ↓
compile changed top-level family
        ↓
public/protected ABI unchanged
        → publish changed family only

ABI changed
        → expand to reverse dependents

unknown/unsafe dependency closure
        → escalate to full compile

compile failure
        → preserve last-known-good classes/state
```

PBMessage generated protocol families that are not safely source-round-trippable remain baseline-only:

```text
PBMessageALL
PBMessageALL2
PBMessageALL3
PBMessageALL4
PBMessageALL5
PBMessageALL6
PBMessageALL7
PBMessageALL8
PBMessageALL9
```

All other accepted application source participates in normal readable compilation.

## Migration / mapping invariants

Validated Fast Dev migration scope:

```text
TOP_LEVEL_APPLICATION_SOURCES=788
TOP_LEVEL_MAPPING_IDENTITIES=788
APPLICATION_RUNTIME_CLASS_MAPPINGS=1109
DUPLICATE_DEV_IDENTITY=0
RECOVERY_NAMESPACE_IN_CORE=0
```

Unknown semantic package mapping must fail closed. Do not invent a package and continue.

## Repair sync invariants

```text
completed repaired source wins
work-only / in-progress source never wins
promotion scope is atomic
failed staging compile does not replace last-known-good runtime
-Sync must not compile arbitrary work-branch source
```

Do not hard-code a permanent pending BUG count. The registry must be derived from current completed/work evidence.

## Current automated validation

Latest complete Fast Dev Main validation on the code path containing the `-Clean` bootstrap fix and Watch compile-on-save contract:

```text
FAST_DEV_MAIN_RUN=208
RUN_ID=36211693807
HEAD=9b5fc03c0f8892be29c9ea0bcca6073f18f0d638
STATUS=PASS

WINDOWS_UTF8_DRIVER=PASS
PACKAGE_MAP=PASS
CORE_BOOTSTRAP=PASS
REPAIR_REGISTRY=PASS
DEV_BASE=PASS
RUNTIME_MAP=PASS
INCREMENTAL_COMPILER=PASS
AUTHORITY_CACHE=PASS
PROMOTION_SCOPE=PASS
AUTOMATIC_BOOTSTRAP=PASS
STRICT_AUTHORITY_POLICY=PASS
SYNC_ATOMICITY=PASS
FRONTEND=PASS
WATCH_COMPILE_ON_SAVE=PASS
PACK=PASS
REAL_MIGRATION=PASS
REAL_CORE_MATERIALIZATION=PASS
RUNTIME_SMOKE_CONTRACT=PASS
REAL_AUTOMATIC_BOOTSTRAP=PASS
MYSQL_5_7=PASS
DB_8_5_IMPORT=PASS
DB_BACKED_RUNTIME_SMOKE=PASS
PORT_2000=PASS
```

Latest fully completed Full Compile evidence recorded before this runbook was written:

```text
FAST_DEV_FULL_COMPILE_RUN=34
RUN_ID=36211535474
HEAD=8d1b2b4c7d62840fdd54516d0fdcfc45d45cd241
STATUS=PASS
BASELINE_ONLY_GENERATED_SOURCE_CONTRACT=PASS
COMPILE_READY_AUTHORITY_CONTRACT=PASS
REAL_FAST_DEV_FULL_COMPILE=PASS
```

The next Head-only Full Compile run may advance beyond this evidence; use GitHub Actions when deciding whether a newer code commit is green.

## Recent local-build fixes

### ERROR-LOCAL-003 — Windows command-line length

Full compile source paths are passed to `javac` with an argfile instead of placing all source paths directly on the Windows command line.

### ERROR-LOCAL-004 — recovered/generated source representation

The final fix sequence keeps PBMessageALL* baseline-only, removes compile-view-only normalizers from runtime compilation, preserves typed collection/comparator signatures, fixes Java type/value shadow calls, and exact-fetches the pinned compile-ready normalizer when a shallow clone lacks it.

### `-Clean` contract correction

```text
RED_COMMIT=e3dee0571165cff2c7332392d24853bfe8967e05
GREEN_COMMIT=8d1b2b4c7d62840fdd54516d0fdcfc45d45cd241
BEHAVIOR=clear .build850, then rebuild usable baseline/state
```

### Watch proof

```text
COMMIT=9b5fc03c0f8892be29c9ea0bcca6073f18f0d638
BEHAVIOR=core/src source change triggers compile_changed exactly once in the contract scenario
MAIN_RUN_208=PASS
```

## Failure triage

Before creating a new gameplay BUG for Fast Dev compile/startup failures:

1. Check the repository README `Fast Dev / 本機編譯故障索引`.
2. Distinguish recovery/decompiler/source-representation failures from gameplay logic failures.
3. Preserve the original production JAR.
4. Re-run the smallest relevant contract first, then the full Fast Dev workflow before closing the issue.

## Remaining manual gate

Fast Dev first-stage automated build/runtime gates are green. The remaining second-stage client gate must be executed with the unchanged 8.50c client against the Fast Dev runtime:

```text
ACCOUNT_LOGIN=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
CHAR_SELECT=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
ENTER_GAME=NOT_RUN_FOR_CURRENT_FAST_DEV_HEAD
```

Do not promote these to PASS from server startup/port evidence alone.

Release remap, obfuscation and encryption are future release work and are not part of the Fast Dev daily compile loop.
