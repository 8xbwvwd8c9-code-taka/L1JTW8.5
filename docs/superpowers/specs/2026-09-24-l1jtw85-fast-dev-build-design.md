# L1JTW8.5 Fast Development Build — Design

Date: 2026-09-24
Branch: `work/l1jtw85-fast-dev-build`
Mode: `FAST_DEV`

## 1. Goal

Turn L1JTW8.5 into a readable-source-first development project where normal core edits can be recompiled immediately without rerunning the full recovery / inverse-remap / production-JAR rebuild pipeline.

The daily development experience should be close to the 880 workflow:

```powershell
.\build850.ps1
.\build850.ps1 -Run
.\build850.ps1 -Watch
```

The user should not need to work directly in `recovery/normalized-src-vf/...`, inspect obfuscated runtime paths, rebuild all 1109 recovered application classes for every change, or run production remapping during ordinary development.

Obfuscation / release remapping is not part of the default development loop. It remains an optional future release step.

## 2. Non-goals

- Do not overwrite the original `l1jserver2.jar`.
- Do not delete historical recovery evidence.
- Do not make `work/l1jtw85-core-fixes` an accepted runtime source.
- Do not promote an in-progress BUG core into the active dev runtime before completed/PASS authority exists.
- Do not require the 880 compiler implementation or assume 880 ABI/package layout equals 850.
- Do not add obfuscation to the daily development path.

## 3. Authority model

### 3.1 Development source authority

The new readable development source tree is:

```text
core/
```

`core/` becomes the only normal human-editing location after migration.

Its initial contents are constructed from:

1. accepted recovered/decompiled 850 source baseline;
2. latest validated source from `completed/l1jtw85-core-fixes` where a repair has been promoted;
3. original/unrepaired recovered source for BUGs still pending or in repair.

`work/l1jtw85-core-fixes` is never directly merged into `core/` simply because a file exists there. Work-branch changes remain quarantined until promotion.

### 3.2 Recovery authority

Existing recovery trees remain preserved under:

```text
recovery/
```

They become historical / bootstrap / evidence material rather than daily source roots.

### 3.3 Original production authority

```text
l1jserver2.jar
```

remains immutable. Its known SHA-256 stays a safety authority for bootstrap and release verification.

## 4. Simplified repository layout

Target layout:

```text
I:\L1JTW8.5
│
├─ build850.ps1
├─ build850.cmd
│
├─ core\
│  ├─ client\
│  ├─ server\
│  ├─ table\
│  ├─ model\
│  ├─ instance\
│  ├─ item\
│  ├─ npc\
│  ├─ timer\
│  ├─ world\
│  ├─ system\
│  ├─ util\
│  └─ misc\
│
├─ tools\850\
│  ├─ compiler\
│  ├─ bootstrap\
│  ├─ repair-sync\
│  └─ release\
│
├─ .build850\
│  ├─ cache\
│  ├─ classes\
│  ├─ abi\
│  ├─ logs\
│  ├─ state.json
│  └─ dependency-index.json
│
├─ dist\
│  ├─ l1jserver2-dev.jar
│  └─ release\
│
├─ recovery\
└─ l1jserver2.jar
```

### 4.1 Physical source layout vs Java identity

Fast Dev must not require the historical obfuscated package directories to remain the user-facing file layout.

The migration tool maintains a source registry mapping physical readable paths to Java/internal identities. This permits semantic folders such as `core/table/AccountTable.java` while preserving compile-correct package declarations during the first migration stage.

The compiler always receives an explicit source manifest, so Java source files do not need to be discovered by the old recovery directory hierarchy.

A later package-cleanup phase may replace temporary internal package names with semantic package names after the Fast Dev runtime is stable. That namespace cleanup is not allowed to block incremental-build delivery.

## 5. Fast Dev runtime

### 5.1 One-time readable dev baseline

The original production JAR is transformed once into a development baseline:

```text
.build850/cache/850-dev-base.jar
```

This bootstrap operation relocates application class identities into the readable development namespace and validates class closure before marking the cache usable.

Cache identity is keyed at minimum by:

```text
original l1jserver2.jar SHA-256
namespace mapping SHA-256
Java major version
Fast Dev compiler schema version
```

If these values do not change, the dev baseline is reused and must not be rebuilt for ordinary source edits.

### 5.2 Classpath overlay

Normal `-Run` does not need to repack a JAR after every edit.

Runtime classpath order:

```text
.build850/classes
.build850/cache/850-dev-base.jar
lib/*
```

Newly compiled classes in `.build850/classes` override the same class identities inside the cached baseline.

This is the default fast path.

`dist/l1jserver2-dev.jar` is generated only when packaging is explicitly requested or required by a validation gate.

## 6. Incremental compiler

### 6.1 Default command

```powershell
.\build850.ps1
```

Default behavior:

1. load `.build850/state.json`;
2. hash readable core source files;
3. detect changed/new/deleted source files;
4. identify affected top-level classes;
5. compile only changed classes plus required dependents;
6. update `.build850/classes` atomically;
7. update ABI/dependency state only after compile PASS;
8. leave last known-good class output intact on compile failure.

### 6.2 ABI-aware dependency rebuild

Each successfully compiled top-level class gets a public/protected ABI fingerprint.

If source content changes but ABI fingerprint is unchanged:

```text
compile changed top-level class + generated inner classes only
```

If ABI fingerprint changes:

```text
compile changed class
+ reverse dependents from dependency-index.json
```

If dependency closure cannot be proven safe:

```text
automatically escalate to safe full application compile
```

The default policy is therefore:

```text
incremental when proven safe
full compile only when necessary
```

### 6.3 Watch mode

```powershell
.\build850.ps1 -Watch
```

Watch mode:

- monitors `core/` only;
- debounces editor save bursts;
- compiles changed source sets immediately;
- prints concise PASS/FAIL and affected-class count;
- never starts a full recovery pipeline merely because one Java file changed.

Automatic server restart is not enabled by default because live DB/world state may be active. A later explicit `-RestartOnSuccess` option may be added after runtime lifecycle testing.

## 7. Commands

Required front-end contract:

```powershell
# Fast incremental compile
.\build850.ps1

# Fast compile then start readable dev runtime
.\build850.ps1 -Run

# Continuous compile-on-save
.\build850.ps1 -Watch

# Force application-wide readable source compile
.\build850.ps1 -Full

# Clear Fast Dev state/cache and bootstrap again
.\build850.ps1 -Clean

# Refresh validated BUG repairs into core/
.\build850.ps1 -Sync

# Package readable dev runtime when a single JAR is wanted
.\build850.ps1 -Pack
```

Future only:

```powershell
.\build850.ps1 -Release
```

`-Release` may later perform remap/obfuscation/encryption. It is intentionally outside the initial Fast Dev implementation.

## 8. BUG repair registry

Fast Dev must explicitly track repaired, pending and in-progress BUG cores so future promotions are not forgotten.

Registry files:

```text
core/repair-registry.json
core/repair-registry.md
```

Each entry stores at least:

```text
bug_id
level
state
work_commit
completed_commit
source_files
last_completed_seen
dev_sync_status
dev_compile_status
last_sync_time
```

Allowed states:

```text
PENDING
IN_REPAIR
PROMOTED_NOT_SYNCED
SYNCED_DEV
DEFERRED_COMPILE
VALIDATED_DEV
```

### 8.1 Source rules

- `work/l1jtw85-core-fixes` describes pending/in-progress work only.
- `completed/l1jtw85-core-fixes` is the only repair promotion authority.
- `-Sync` compares current work/completed authorities and registry state.
- A work-only core is never copied into active `core/`.
- When a BUG first appears on completed with PASS evidence, its validated source files become eligible for `PROMOTED_NOT_SYNCED`.
- Sync copies/reconciles the promoted source into `core/`, runs the Fast Dev compile gate, then marks `SYNCED_DEV` or `DEFERRED_COMPILE`.
- A failed compile never removes the current last-known-good dev class.

### 8.2 Current pending seed

The latest authoritative L2 recount recorded 20 pending IDs:

```text
010,027,032,033,034,039,043,046,049,057,058,059,082,083,087,089,095,100,102,103
```

After that recount, completed commit `d9448ca6253efd17ae8a47254815c2336f939d5f` promoted `039`, `043`, and `046` together.

Therefore the Fast Dev registry seed currently treats the remaining effective pending set as:

```text
010,027,032,033,034,049,057,058,059,082,083,087,089,095,100,102,103
```

Count:

```text
CURRENT_EFFECTIVE_PENDING=17
```

This number is a bootstrap snapshot only. The implementation must compute state from branch/evidence changes rather than hard-code `17`.

## 9. Completed repair synchronization

A completed promotion can touch one or many source files. Fast Dev sync operates at BUG promotion scope, not just single filenames.

Sync flow:

```text
fetch/inspect completed authority
  -> identify newly promoted BUG IDs since last_completed_seen
  -> identify exact validated source files
  -> translate recovery source locations to core/ canonical locations
  -> stage candidate readable sources
  -> compile candidate + dependency closure
  -> PASS: atomically replace core/ source + class outputs
  -> FAIL: keep old core/class runtime and mark DEFERRED_COMPILE
```

This preserves the rule:

```text
completed repaired source wins
in-progress repaired source never wins
unrepaired source stays active until promotion
```

## 10. Migration from recovery tree

Migration is one-time and auditable.

Input authorities:

```text
accepted recovered/decompiled source set
latest completed repair branch
source namespace mapping
original production JAR
```

Migration output:

```text
core/
core/source-index.json
core/repair-registry.json
.build850/cache/850-dev-base.jar
.build850/dependency-index.json
```

The migration must validate:

```text
all accepted application top-level sources represented exactly once
no source silently omitted
no duplicate canonical source identity
no in-progress work source promoted into core
completed repair precedence preserved
original JAR remains unchanged
```

## 11. Relationship to existing production-rebuild pipeline

The existing `tools/production-rebuild/` pipeline remains intact as a safety/reference implementation.

Fast Dev does not delete or rewrite it during initial rollout.

Responsibilities become:

```text
FAST DEV
  readable source editing
  cached readable ABI/runtime
  incremental javac
  classpath overlay
  fast local server tests

PRODUCTION REBUILD / FUTURE RELEASE
  strict structural verification
  optional inverse remap
  optional obfuscation/encryption
  production artifact packaging
```

Once Fast Dev is fully validated, the older pipeline may be moved under `tools/850/release/` without losing history.

## 12. Failure safety

Fast Dev is fail-closed:

- compilation failure keeps previous good `.class` output;
- sync failure keeps previous good `core/` version active;
- registry state records the failure;
- production JAR is never modified;
- `-Clean` never deletes `core/` or `recovery/`;
- build cache may always be regenerated from source + original JAR authorities.

## 13. Initial validation gates

The Fast Dev architecture is accepted only after all of the following pass:

```text
BOOTSTRAP_DEV_BASE=PASS
CORE_MIGRATION_COVERAGE=100%
COMPLETED_REPAIR_PRECEDENCE=PASS
PENDING_REPAIR_QUARANTINE=PASS
FAST_SINGLE_CLASS_COMPILE=PASS
INNER_CLASS_UPDATE=PASS
ABI_UNCHANGED_INCREMENTAL=PASS
ABI_CHANGED_DEPENDENT_REBUILD=PASS
FAILED_COMPILE_LAST_GOOD_PRESERVED=PASS
WATCH_MODE_COMPILE=PASS
DEV_RUNTIME_START=PASS
DB_CONNECT=PASS
PORT_2000=PASS
ORIGINAL_PRODUCTION_JAR_MODIFIED=NO
```

After runtime startup, unchanged 8.50c account login -> character select -> enter-game remains the highest-confidence integration gate.

## 14. Performance target

After the one-time bootstrap, ordinary single-core edits should not rebuild the entire 788-source / 1109-class recovered application.

Target behavior:

```text
METHOD_BODY_ONLY_CHANGE
  -> 1 top-level source + its generated inner classes

PUBLIC_ABI_CHANGE
  -> changed source + proven reverse dependents

UNKNOWN_DEPENDENCY_CHANGE
  -> safe full application compile

ORIGINAL_JAR_OR_NAMESPACE_MAP_CHANGE
  -> rebuild dev baseline cache
```

The primary optimization goal is avoiding repeated global recovery/remap work during normal core development.

## 15. Rollout order

1. Build Fast Dev source/repair registries.
2. Migrate readable sources into `core/` without deleting recovery sources.
3. Build and validate reusable readable dev baseline.
4. Implement incremental compiler and atomic class output.
5. Implement dependency/ABI tracking.
6. Implement `build850.ps1` front end.
7. Implement `-Run` classpath-overlay runtime.
8. Implement `-Watch`.
9. Implement repair `-Sync` and current pending registry.
10. Run DB/runtime/client smoke tests.
11. Keep release/obfuscation mode deferred until requested.

## 16. Success definition

The architecture succeeds when normal development looks like:

```text
edit core/<readable source>.java
save
run build850.ps1 (or leave -Watch running)
only affected classes compile
run/test server
```

and the developer no longer needs to understand or manually operate the recovery/remap directory structure for everyday core work.
