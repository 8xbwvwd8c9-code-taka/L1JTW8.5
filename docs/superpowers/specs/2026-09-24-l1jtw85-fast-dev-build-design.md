# L1JTW8.5 Fast Development Build — Design

Date: 2026-09-24
Branch: `work/l1jtw85-fast-dev-build`
Mode: `FAST_DEV`

## 1. Goal

Turn L1JTW8.5 into a readable-source-first development project where normal core edits can be recompiled immediately without rerunning the full recovery / inverse-remap / production-JAR rebuild pipeline.

Daily use should be close to 880:

```powershell
.\build850.ps1
.\build850.ps1 -Run
.\build850.ps1 -Watch
```

The developer should not need to work inside `recovery/normalized-src-vf/...`, inspect obfuscated runtime paths, rebuild all recovered application classes for every edit, or run production remapping during ordinary development.

Obfuscation/remap is a future release concern, not a development concern.

## 2. Non-goals

- Never overwrite the original `l1jserver2.jar`.
- Never delete historical recovery evidence.
- Never make `work/l1jtw85-core-fixes` an accepted runtime source.
- Never deploy an in-progress BUG core before completed/PASS authority exists.
- Do not assume the 880 compiler implementation or ABI equals 850.
- Do not add obfuscation to the default development loop.

## 3. Authority model

### 3.1 Development source authority

After migration the only normal human-editing source root is:

```text
core/src/
```

It is constructed from:

1. accepted recovered/decompiled 850 source baseline;
2. latest validated source from `completed/l1jtw85-core-fixes` where repairs are promoted;
3. original/unrepaired recovered source for BUGs still pending/in repair.

`work/l1jtw85-core-fixes` is quarantine only. A work-only change never becomes active dev source merely because it exists.

### 3.2 Recovery authority

Existing `recovery/` content remains preserved as historical/bootstrap/evidence material. It is no longer a daily source root.

### 3.3 Original production authority

`l1jserver2.jar` remains immutable and is used only as the original runtime/bootstrap/release authority.

## 4. Semantic readable namespace

Fast Dev removes the historical recovery namespace from the new working tree.

The new source tree must use normal Java package-aligned paths so VS Code/Java Language Server works without package/path mismatch warnings.

Target examples:

```text
core/src/l1j/server/clientpackets/C_NpcAction.java
core/src/l1j/server/datatables/AccountTable.java
core/src/l1j/server/datatables/CharacterTable.java
core/src/l1j/server/model/L1Master.java
core/src/l1j/server/model/instance/L1PetInstance.java
core/src/l1j/server/model/item/FurnitureItem.java
core/src/l1j/server/model/timer/CurrentTimeReseter.java
core/src/l1j/server/model/timer/HomeTownTimer.java
core/src/l1j/server/templates/L1BookMark.java
```

No `l1r/aj`, `l1r/ao`, or other recovery package path should remain as the canonical location under `core/src/` after migration.

### 4.1 Package mapping

Migration creates an explicit complete map:

```text
core/package-map.csv
```

Logical columns:

```text
OriginalInternal
RecoveredInternal
DevInternal
SourceFile
Category
```

Example concept:

```text
aj/bk,l1r/aj/C_NpcAction,l1j/server/clientpackets/C_NpcAction,C_NpcAction.java,clientpackets
ao/a,l1r/ao/AccountTable,l1j/server/datatables/AccountTable,AccountTable.java,datatables
```

Every accepted application class must map exactly once. No guessed silent fallback is allowed. Unknown classifications block migration until assigned a stable semantic package.

The original<->recovered map plus recovered<->dev map preserves a deterministic route back to the original runtime namespace if a future release needs remap/obfuscation.

## 5. Simplified repository layout

```text
I:\L1JTW8.5
│
├─ build850.ps1
├─ build850.cmd
│
├─ core\
│  ├─ src\l1j\server\...
│  ├─ package-map.csv
│  ├─ source-index.json
│  ├─ repair-registry.json
│  └─ repair-registry.md
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

The top-level development surface is therefore only:

```text
core/
build850.ps1
.build850/
dist/
tools/850/
```

Recovery internals stay out of ordinary work.

## 6. Fast Dev runtime

### 6.1 One-time readable dev baseline

Bootstrap transforms the original production application classes directly into the semantic Dev namespace and writes:

```text
.build850/cache/850-dev-base.jar
```

This is a runtime baseline, not merely a source-recovery artifact.

It must pass class-closure and startup validation before being cached as usable.

Cache identity is keyed by at least:

```text
original l1jserver2.jar SHA-256
package-map.csv SHA-256
Java major version
Fast Dev compiler schema version
```

If these do not change, ordinary source edits never rebuild the baseline.

### 6.2 Classpath overlay instead of repacking

Normal development runs with:

```text
.build850/classes
.build850/cache/850-dev-base.jar
lib/*
```

Fresh compiled classes override the cached baseline through classpath order.

Therefore a one-class edit does not need to repack a JAR.

`dist/l1jserver2-dev.jar` is created only for `-Pack` or explicit packaging/validation.

## 7. Incremental compiler

### 7.1 Default flow

```powershell
.\build850.ps1
```

1. load `.build850/state.json`;
2. hash `core/src/` sources;
3. detect changed/new/deleted sources;
4. identify affected top-level classes;
5. compile only changed classes plus required dependents;
6. write candidate classes to a staging directory;
7. atomically publish classes only after javac PASS;
8. update ABI/dependency state only after PASS;
9. preserve the last-known-good class output on failure.

### 7.2 ABI-aware dependency rebuild

Each compiled top-level class receives a public/protected ABI fingerprint.

If source changes but ABI is unchanged:

```text
changed top-level + generated inner classes only
```

If ABI changes:

```text
changed class + reverse dependents from dependency-index.json
```

If dependency closure cannot be proven safe:

```text
escalate automatically to safe full application compile
```

Policy:

```text
incremental when proven safe
full compile only when necessary
```

### 7.3 Watch mode

```powershell
.\build850.ps1 -Watch
```

Watch mode monitors `core/src/`, debounces editor saves, and recompiles affected classes immediately.

It does not automatically restart a live game server by default. A later explicit restart option can be added after lifecycle safety testing.

## 8. Commands

```powershell
# Fast incremental compile
.\build850.ps1

# Compile and start readable dev runtime
.\build850.ps1 -Run

# Compile-on-save
.\build850.ps1 -Watch

# Full readable application compile
.\build850.ps1 -Full

# Clear generated state/cache only, then bootstrap again
.\build850.ps1 -Clean

# Refresh validated BUG promotions into core/src
.\build850.ps1 -Sync

# Produce one readable dev JAR
.\build850.ps1 -Pack
```

Future only:

```powershell
.\build850.ps1 -Release
```

`-Release` may later perform production namespace remap, obfuscation or encryption. It is intentionally excluded from initial Fast Dev delivery.

## 9. BUG repair registry

Fast Dev explicitly tracks repaired, pending and in-progress BUG cores so future completed repairs are never forgotten.

Registry files:

```text
core/repair-registry.json
core/repair-registry.md
```

Minimum fields:

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

States:

```text
PENDING
IN_REPAIR
PROMOTED_NOT_SYNCED
SYNCED_DEV
DEFERRED_COMPILE
VALIDATED_DEV
```

### 9.1 Repair source rules

- `work/l1jtw85-core-fixes` = pending/in-progress evidence only.
- `completed/l1jtw85-core-fixes` = only promotion authority.
- A work-only repaired source never replaces active `core/src`.
- Pending/in-progress BUGs continue using the unrepaired baseline source in Fast Dev.
- When completed gains PASS promotion evidence, the repair becomes `PROMOTED_NOT_SYNCED`.
- `-Sync` transforms the completed recovered source into the semantic Dev namespace, compiles it, then publishes atomically.
- Compile PASS -> `SYNCED_DEV`/`VALIDATED_DEV`.
- Compile FAIL -> `DEFERRED_COMPILE`, while the previous last-known-good dev class remains active.

### 9.2 Dynamic registry, not a hard-coded count

The latest authoritative L2 recount recorded these 20 pending IDs:

```text
010,027,032,033,034,039,043,046,049,057,058,059,082,083,087,089,095,100,102,103
```

Completed commit `d9448ca6253efd17ae8a47254815c2336f939d5f` subsequently promoted `039`, `043`, and `046` together.

Current effective seed:

```text
010,027,032,033,034,049,057,058,059,082,083,087,089,095,100,102,103
CURRENT_EFFECTIVE_PENDING=17
```

This is only the bootstrap snapshot. The implementation derives current state from work/completed/evidence changes and never hard-codes `17` as permanent truth.

## 10. Repair synchronization

`-Sync` is separate from ordinary incremental build so normal compile does not pay network/branch-inspection cost.

Flow:

```text
refresh/inspect work + completed
  -> compare against registry last_completed_seen
  -> identify newly promoted BUG scope
  -> identify exact validated normalized/recovered source files
  -> transform source package/import/type identities to semantic Dev namespace
  -> stage source + affected dependency set
  -> compile
  -> PASS: atomically publish core/src + classes + registry state
  -> FAIL: keep old source/classes active; mark DEFERRED_COMPILE
```

Invariant:

```text
completed repaired source wins
in-progress repaired source never wins
unrepaired baseline stays active until promotion
```

A promotion touching multiple source files is synchronized as one atomic BUG scope, not file-by-file partial publication.

## 11. One-time migration

Inputs:

```text
accepted recovered/decompiled source set
latest completed repair branch
original<->recovered namespace mapping
original production JAR
```

Outputs:

```text
core/src/
core/package-map.csv
core/source-index.json
core/repair-registry.json
.build850/cache/850-dev-base.jar
.build850/dependency-index.json
```

Migration gates:

```text
ALL_ACCEPTED_TOP_LEVEL_SOURCES_REPRESENTED=100%
ALL_APPLICATION_CLASSES_HAVE_DEV_MAPPING=100%
NO_CANONICAL_SOURCE_DUPLICATES=YES
NO_L1R_NAMESPACE_IN_CORE=YES
NO_IN_PROGRESS_WORK_PROMOTED=YES
COMPLETED_REPAIR_PRECEDENCE=PASS
ORIGINAL_JAR_UNCHANGED=YES
```

## 12. Existing production-rebuild relationship

Existing `tools/production-rebuild/` remains intact during rollout as the verified safety/reference bridge.

Responsibilities become:

```text
FAST DEV
  semantic readable sources
  cached readable runtime
  incremental javac
  classpath overlay
  fast local testing
  BUG repair synchronization

PRODUCTION/RELEASE (future)
  strict packaging verification
  semantic->production namespace transform if needed
  optional obfuscation/encryption
  release artifact generation
```

After Fast Dev is validated, old production rebuild helpers may be moved under `tools/850/release/` without deleting history.

## 13. Failure safety

- compile failure preserves previous good classes;
- repair-sync failure preserves previous active source/classes;
- registry records failure state;
- `-Clean` removes generated `.build850` state only, never `core/` or `recovery/`;
- production JAR is never modified;
- every cache can be regenerated from source + original authorities.

## 14. Validation gates

Fast Dev is accepted only when:

```text
PACKAGE_MAP_COVERAGE=100%
BOOTSTRAP_DEV_BASE=PASS
CORE_MIGRATION_COVERAGE=100%
NO_L1R_NAMESPACE_IN_CORE=PASS
COMPLETED_REPAIR_PRECEDENCE=PASS
PENDING_REPAIR_QUARANTINE=PASS
FAST_SINGLE_CLASS_COMPILE=PASS
INNER_CLASS_UPDATE=PASS
ABI_UNCHANGED_INCREMENTAL=PASS
ABI_CHANGED_DEPENDENT_REBUILD=PASS
FAILED_COMPILE_LAST_GOOD_PRESERVED=PASS
WATCH_MODE_COMPILE=PASS
REPAIR_SYNC_ATOMICITY=PASS
DEV_RUNTIME_START=PASS
DB_CONNECT=PASS
PORT_2000=PASS
ORIGINAL_PRODUCTION_JAR_MODIFIED=NO
```

Then run unchanged 8.50c:

```text
account login -> character select -> enter-game
```

## 15. Performance target

After one-time bootstrap, ordinary single-core edits must not rebuild the entire 788-source / 1109-class recovered application.

```text
METHOD_BODY_ONLY_CHANGE
  -> 1 top-level source + generated inner classes

PUBLIC_ABI_CHANGE
  -> changed source + reverse dependents

UNKNOWN_DEPENDENCY_CHANGE
  -> safe full application compile

ORIGINAL_JAR_OR_PACKAGE_MAP_CHANGE
  -> rebuild dev baseline cache
```

The performance goal is to remove repeated global recovery/remap work from normal development.

## 16. Rollout order

1. Inventory accepted recovered classes and create 100% semantic `package-map.csv`.
2. Build source/repair registries.
3. Migrate readable sources into `core/src/` without deleting recovery sources.
4. Build runtime-capable semantic `850-dev-base.jar`.
5. Validate full readable runtime startup.
6. Implement atomic incremental compiler.
7. Implement ABI/dependency tracking.
8. Implement root `build850.ps1` + `build850.cmd`.
9. Implement `-Run` classpath-overlay runtime.
10. Implement `-Watch`.
11. Implement `-Sync` and seed/refresh pending repair registry.
12. Run DB/runtime/client smoke tests.
13. Leave release/obfuscation mode deferred until requested.

## 17. Success definition

Normal work becomes:

```text
edit core/src/l1j/server/.../ReadableClass.java
save
build850.ps1 (or leave -Watch running)
only affected classes compile
run/test server
```

The developer no longer needs to understand or manually operate recovery/remap paths for everyday core changes, while completed BUG repairs continue to flow safely into the readable Dev source tree after PASS promotion.
