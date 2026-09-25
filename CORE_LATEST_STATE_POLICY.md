# L1JTW8.5 Latest Completed Core Policy

Branch: `completed/l1jtw85-core-fixes`

## Authority rule

`completed/l1jtw85-core-fixes` is the single authoritative source for the **latest fully repaired and validated 850 core state**.

## Mandatory write-back rule

When core source is taken from this branch for a BUG repair:

1. Start from the latest HEAD of `completed/l1jtw85-core-fixes`.
2. Apply the minimal BUG repair without dropping any previously completed fixes.
3. Complete the required Java 8 / targeted / runtime / failure-path validation for that BUG.
4. Only after validation is PASS, overwrite/update the corresponding repaired core source in `completed/l1jtw85-core-fixes`.
5. If the repair requires DB migration, config, rebuild metadata, validation evidence, or other core-related support data, update those related files together.
6. Re-fetch the latest completed HEAD before write-back and reconcile any concurrent completed fixes. Never overwrite a newer completed repair with an older base.
7. After write-back, this branch must represent the newest cumulative completed repair state.

## Forbidden states

The following must never become authoritative content of this branch:

- unvalidated or partially validated core changes;
- WIP experiments;
- rollback to an older repaired-core snapshot;
- a BUG fix that removes or regresses an already completed fix;
- feature/client/launcher work unrelated to server-core repair;
- temporary promotion branches or promotion-only workflow state.

## Source priority

```text
1. completed/l1jtw85-core-fixes  = latest repaired core authority
2. completed/l1jtw85-decompiled  = frozen original decompiled baseline
```

`completed/l1jtw85-decompiled` is read-only and must not receive repairs.

## Completion invariant

```text
BUG_REPAIR_COMPLETE
AND VALIDATION=PASS
=> WRITE_BACK_TO_COMPLETED_CORE_FIXES=REQUIRED

completed/l1jtw85-core-fixes
= LATEST_CUMULATIVE_COMPLETED_REPAIRED_CORE
```

If validation is not PASS, do not write the repaired core into the authority branch.
