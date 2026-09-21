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
