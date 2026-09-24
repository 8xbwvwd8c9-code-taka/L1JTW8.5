# L1JTW8.5 Core Repair Status — 2026-09-24

Branch: `completed/l1jtw85-core-fixes`

## Conversation checkpoint

```text
STATUS=HANDOFF_CHECKPOINT
AUDIT_CURSOR=AUDIT_EXHAUSTED
MODE=PROMOTION_RECONCILIATION_ONLY
NO_REPO_WIDE_SCAN=YES
```

This report records the state at conversation handoff. It does **not** mark any unvalidated BUG as completed.

## Repairs closed by this conversation

- `BUG-850-269` — PASS / PROMOTED
- `BUG-850-266` — PASS / PROMOTED
- `BUG-850-265` — PASS / PROMOTED
- `BUG-850-263` — PASS / PROMOTED
- `BUG-850-262` — PASS / PROMOTED
- `BUG-850-258` — PASS / PROMOTED
- `BUG-850-250` — PASS / PROMOTED
- `BUG-850-251` — PASS / PROMOTED
- `BUG-850-257` — PASS / ALREADY COVERED
- `BUG-850-255` — PASS / PROMOTED

## BUG-850-255 authority

```text
WORK_CI=35723688572
COMPLETED_CI=35900095083
SOURCE_COMMIT=15bf12eda62671cede855ff6931d49f8db1a4d05
HISTORICAL_PATCH_CHAIN=7470ecbe,8e2d571a,1f2cb7b1,a01d93c3,d3c8078b,1693b07c
EXACT_PATCH_APPLIED=PASS
UNRELATED_SOURCE_REPLAY=NO
STATUS=PASS_PROMOTED
```

The first two completed promotion attempts failed closed because of validation-script matching/quoting mistakes. They did not commit core source. Run `35900095083` is the successful completed authority.

## Concurrent lane state discovered before handoff

The completed branch advanced independently after BUG-850-255. The current completed records include at least:

- `BUG-850-254` — PASS / PROMOTED
- `BUG-850-252` — PASS / PROMOTED
- `BUG-850-249` — PASS / PROMOTED
- `BUG-850-246` — PASS / PROMOTED
- `BUG-850-245` — PASS / PROMOTED
- `BUG-850-275` — PASS / PROMOTED

Do not redo these entries. Always re-fetch the latest completed README/evidence before taking new work.

## BUG-850-254 authority

```text
WORK_CI=35722588093
COMPLETED_CI=35938795118
SOURCE_COMMIT=0f3c51e000e89cef0c3bf2f861d1e78fa271fcf4
PATCH_CHAIN=e5320aea,2fb56fbd,c75e085e,8470e775,a5dba0e6
EXACT_HISTORICAL_PATCH_CHAIN=PASS
UNRELATED_SOURCE_REPLAY=NO
BULK_DELETE_RESULT_PROPAGATED=PASS
LIVE_WEEKLY_RESET_GATED_BY_DELETE_SUCCESS=PASS
WEEKLY_RESCHEDULE_FINALLY_PRESERVED=PASS
BUG_850_255_UPSERT_PRESERVED=PASS
STATUS=PASS_PROMOTED
```

Therefore `BUG-850-254` is closed and must not be resumed.

## Audit state

The authoritative work ledger reports:

```text
RECENT_LANE_NEXT=EXHAUSTED
OLD_LANE_NEXT=RETIRED
MEETING=AUDIT_EXHAUSTED
```

Do not restart repo-wide or archive-wide BUG discovery unless the user explicitly starts a new audit.

## Next safe entry

`recovery/BUG-850-244_PROMOTION_READY.tmp` currently contains only `STOP2`. That marker is not PASS evidence.

Treat `BUG-850-244` only as the next **promotion candidate to reconcile**:

1. Re-fetch `completed/l1jtw85-core-fixes` and `work/l1jtw85-core-fixes` heads.
2. Check whether another lane has already completed 244.
3. Locate 244 work repair/evidence/CI and exact source boundary.
4. Compare against latest completed source.
5. Promote only with dedicated validation and a non-force concurrency gate.
6. Record completed status only after PASS.

Detailed handoff: `recovery/L1JTW85_CORE_REPAIR_HANDOFF_20260924.md`.

## Production repaired-JAR rebuild verification — 2026-09-24

Branch: `work/l1jtw85-production-jar-rebuild`

Pinned completed repair authority:

```text
COMPLETED_REF=3d4a392593d61c9203e3f22a154e752c2c51fa97
POLICY=PROMOTION_COMMIT_PLUS_CURRENT_JAVA8_RC0_ONLY
CANDIDATES=25
DEPLOYABLE_TOP_LEVEL=13
DEFERRED_TOP_LEVEL=12
REPLACED_RUNTIME_CLASSES=23
```

Contract and structural gates:

```text
CONTRACT_TESTS=22/22 PASS
STRUCTURAL_VALIDATION=PASS
MISSING_ENTRIES=0
UNEXPECTED_ENTRIES=0
INTERNAL_NAME_MISMATCHES=0
NAMESPACE_LEAKS=0
CHANGED_PRESERVED_ENTRIES=0
```

Production JAR preservation:

```text
ORIGINAL_JAR=l1jserver2.jar
ORIGINAL_SHA256=8E91712FC9EB4AD07E064723CF0FC02AC9A01063231EFD150B90927F04660814
ORIGINAL_JAR_UNCHANGED=YES
TEST_JAR=recovery/production-build/l1jserver2.repaired-test.jar
TEST_JAR_SHA256=32B9405850A29650EFFA7528225863806F27E70893ACBD267FDB15FFD14DFD59
```

DB-backed runtime startup gate, GitHub Actions run `36012858606`:

```text
MYSQL_VERSION=5.7
MYSQL_AUTH_READY=YES
DB_TABLES=99
RUNTIME_JDBC_SSL_DISABLED=YES (CI runner only)
RUNTIME_SMOKE=PASS
JAVA_PROCESS=ALIVE
PORT_2000=LISTENING
SERVER_INITIALIZATION=PASS
WAITING_FOR_CLIENT=YES
```

The runtime smoke used two environment-compatibility adjustments only in CI:

1. MySQL 5.7 was started with `--sql-mode=NO_ENGINE_SUBSTITUTION` because the legacy dump fails under the modern 5.7 default strict mode with `ERROR 1406 Data too long`.
2. The checked-out runner copy of `config/server.properties` was temporarily changed to append `&useSSL=false` because the bundled legacy MySQL JDBC driver otherwise attempts SSL and fails against current Java 8 TLS policy.

Neither adjustment modifies the original `l1jserver2.jar`. The repository's formal production JAR was not overwritten.

Verified Actions artifacts from run `36012858606`:

```text
REPAIRED_JAR_ARTIFACT_ID=10812844448
REPAIRED_JAR_ARTIFACT_NAME=l1jtw85-repaired-test-3d4a3925
REPAIRED_JAR_ARTIFACT_EXPIRED=NO
RUNTIME_SMOKE_ARTIFACT_ID=10812589680
RUNTIME_SMOKE_ARTIFACT_NAME=l1jtw85-runtime-smoke-3d4a3925
RUNTIME_SMOKE_ARTIFACT_EXPIRED=NO
ARTIFACT_RETENTION_UNTIL=2026-10-24
```

Current gate boundary:

```text
BUILD_GATE=PASS
STRUCTURAL_GATE=PASS
DB_IMPORT_GATE=PASS
RUNTIME_STARTUP_GATE=PASS
REAL_CLIENT_LOGIN_GATE=NOT_RUN
FUNCTIONAL_GAMEPLAY_GATE=NOT_RUN
```

Do **not** label this JAR fully production-ready solely from the startup smoke. The next higher-confidence validation is a real 8.50c client login/session test against this repaired JAR, followed by targeted functional checks for the 13 promoted deployable repairs.
