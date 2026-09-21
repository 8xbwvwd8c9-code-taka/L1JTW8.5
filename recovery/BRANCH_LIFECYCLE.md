# L1JTW8.5 Branch Lifecycle

## Branch model

Recovery / repair work uses exactly three logical branches while work is active.

### 1. Decompilation completed branch

Role:

`completed/l1jtw85-decompiled`

Purpose:

- stores only the fully recovered/decompiled source baseline;
- no unfinished bug fixes;
- no experimental repair work;
- no temporary investigation artifacts that change runtime behavior.

Entry gate:

- application source compile PASS;
- embedded runtime source-only closure PASS;
- normalized class set PASS;
- hierarchy PASS;
- ABI/documented reversible exceptions PASS;
- mapping reversibility PASS;
- no temporary donor-derived binary bootstrap dependency required.

This branch is immutable baseline material after acceptance except for documentation-only corrections.

### 2. Core fixes completed branch

Role:

`completed/l1jtw85-core-fixes`

Purpose:

- stores only completed, validated core repairs;
- every fix must already have passed Runtime Source Map checks;
- every fix must include core + config + DB source verification;
- incomplete or speculative fixes are prohibited.

Completed fixes move here only after their validation gate passes.

### 3. Core fixes work-in-progress branch

Role:

`work/l1jtw85-core-fixes`

Purpose:

- contains BUG audit work and repairs that are not yet fully validated;
- may contain temporary probes, debug logging, isolated experiments and incomplete fixes;
- this is the only branch allowed to contain unfinished core repair work.

A repair remains here until its complete validation gate passes.

## Fix promotion flow

```text
BUG FOUND
   ↓
work/l1jtw85-core-fixes
   ↓
CORE + CONFIG + DB runtime-source verification
   ↓
FIX
   ↓
VALIDATE
   ↓
PASS
   ↓
completed/l1jtw85-core-fixes
```

A fix is not considered completed merely because Java compiles.

Required before promotion:

1. Java/core call path identified.
2. `config/` control files checked.
3. DB table/column/loader checked.
4. active/default/fallback sources classified.
5. runtime behavior validated.
6. restart/reload requirement documented.
7. no unrelated regression.
8. fix report updated.

## Final branch convergence

During active recovery, three logical branches exist:

```text
completed/l1jtw85-decompiled
completed/l1jtw85-core-fixes
work/l1jtw85-core-fixes
```

After all core repairs are completed:

1. every PASS fix is transferred/promoted into `completed/l1jtw85-core-fixes`;
2. verify the work branch contains no unique unfinished work that must be preserved;
3. archive/document any rejected experiment that still matters;
4. delete `work/l1jtw85-core-fixes`;
5. final project state keeps only the two completed project branches:

```text
completed/l1jtw85-decompiled
completed/l1jtw85-core-fixes
```

The original repository `main` remains untouched as donor/original baseline and is not counted as one of these two project-result branches.

## Hard rules

- One branch = one lifecycle role.
- Never mix unfinished repairs into a completed branch.
- Never mark decompilation branch completed before source-only recovery Final Gate passes.
- Never copy an unfinished fix into completed just because javac succeeds.
- Completed repair promotion requires core/config/DB verification.
- Work branch may be deleted only after all accepted fixes are transferred and all remaining work is either rejected/documented or confirmed unnecessary.
- Do not delete evidence before its conclusion is documented.
- `main` remains original baseline.


## Current branch cleanup map — 2026-09-21

Canonical branches now created:

```text
analysis/l1jtw85-recovery
  = active decompilation/source-recovery work until Final Gate passes

completed/l1jtw85-core-fixes
  = canonical completed core-fix branch
  = seeded from old completed/l1jtw85-decompiled-fixes-20260921

work/l1jtw85-core-fixes
  = canonical unfinished bug-audit/core-fix branch
  = seeded from analysis/l1jtw85-bug-audit
```

The completed decompilation branch is intentionally **NOT created yet**, because WP5 source-only protobuf runtime recovery is still OPEN.

After Final Gate:

```text
analysis/l1jtw85-recovery
→ completed/l1jtw85-decompiled
```

### Legacy branches classified for retirement

```text
completed/l1jtw85-decompiled-fixes-20260921
  -> superseded by completed/l1jtw85-core-fixes

fix/l1jtw85-audit-remediation
  -> fully contained by completed/l1jtw85-core-fixes
     (completed branch is exactly one snapshot/docs commit ahead)

analysis/l1jtw85-bug-audit
  -> superseded by work/l1jtw85-core-fixes

recovery/l1jtw85-processed-checkpoint
  -> ancestor of active analysis/l1jtw85-recovery

recovery/l1jtw85-verified
  -> legacy verified shelf; unique README context preserved at:
     recovery/archive/VERIFIED_RECOVERY_README_legacy.md
     remaining evidence files already exist in active recovery
```

These legacy refs are no longer authoritative.

### Desired branch state while decompilation is unfinished

Ignoring original `main`, active project branches should be:

```text
analysis/l1jtw85-recovery
completed/l1jtw85-core-fixes
work/l1jtw85-core-fixes
```

### Desired final branch state

After source recovery completes and all WIP fixes are promoted:

```text
completed/l1jtw85-decompiled
completed/l1jtw85-core-fixes
```

Then:

- delete `analysis/l1jtw85-recovery`;
- delete `work/l1jtw85-core-fixes`;
- all legacy refs listed above should already be retired;
- retain `main` as original baseline.

## Completed decompilation branch content policy

The final branch:

`completed/l1jtw85-decompiled`

must be a **clean recovered-source result branch**.

### Keep

Only the recovered/decompiled artifacts that represent the full donor mapping target:

```text
TOTAL_MAPPED_CLASSES=1765
```

The branch may additionally retain only the minimum identity manifest required to prove what the 1765 recovered artifacts correspond to, for example:

- class/source mapping manifest;
- namespace / reversible identity map;
- minimal README stating source provenance and final PASS gate.

### Delete / do not promote into this branch

Do not carry recovery-workspace material into the completed decompilation branch:

- `tools/recovery/**`
- experimental scripts
- CI-only recovery workflow artifacts
- TEMP fixtures
- javac logs
- intermediate JSON/CSV diagnostics not required for final identity proof
- failed experiment reports
- bug-audit reports
- core-fix reports
- core-fix patches
- donor `l1jserver2.jar`
- compile-ref donor-derived JARs
- temporary relocated protobuf JARs
- build output directories
- generated class output used only for validation
- old checkpoints / WIP queues
- archived conversation handoffs
- unrelated DB/config/runtime assets
- launcher/client research material

### Final branch construction rule

Do **not** clean the active recovery branch in place.

When the source-recovery Final Gate passes:

1. verify the authoritative 1765-class mapping target;
2. build a fresh clean tree from the accepted recovered/decompiled source artifacts;
3. include only the minimum mapping/identity manifest;
4. create/update `completed/l1jtw85-decompiled` from that clean tree;
5. verify the completed branch contains no recovery workspace residue;
6. only then retire/delete the active recovery branch.

The active branch `analysis/l1jtw85-recovery` keeps all evidence until Final Gate, because those files are still needed to finish and audit the recovery.

### Final acceptance

```text
COMPLETED_DECOMPILED_BRANCH_CLASS_TARGET=1765
RECOVERY_WORKSPACE_FILES_PRESENT=NO
CORE_FIX_FILES_PRESENT=NO
DONOR_BINARY_PRESENT=NO
TEMP_ARTIFACTS_PRESENT=NO
MINIMAL_IDENTITY_MANIFEST_PRESENT=YES
```
