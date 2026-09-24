# L1JTW8.5 Core Repair Handoff — 2026-09-24

```text
STATUS=HANDOFF_READY
PROJECT=L1JTW8.5
REPO=8xbwvwd8c9-code-taka/L1JTW8.5
COMPLETED_BRANCH=completed/l1jtw85-core-fixes
WORK_BRANCH=work/l1jtw85-core-fixes
AUDIT_CURSOR=AUDIT_EXHAUSTED
SUBAGENTS=0
NO_REPO_WIDE_SCAN=YES
```

## GOAL

Continue only the remaining promotion/reconciliation work for already-known core repairs. Do not restart repo-wide BUG discovery unless explicitly requested.

## MUST

- Re-fetch both `completed/l1jtw85-core-fixes` and `work/l1jtw85-core-fixes` before every new unit.
- Treat the latest completed README/evidence as authority; another lane may advance the branch at any time.
- Use exact/minimal historical patches when shared files later accumulated unrelated BUG fixes.
- Require dedicated source/compile/runtime validation before writing `PASS / PROMOTED`.
- Use non-force updates and fail closed on concurrency.
- Preserve 850 architecture as authority; 380/880 are donor/reference only.

## DO NOT

- Do not redo `BUG-850-254` or `BUG-850-255`.
- Do not redo completed SoulTower `250/251/257` work.
- Do not replay an entire later work blob when it would smuggle 252/254/275 or other later fixes.
- Do not treat README text, a marker file, or a temporary flag as validation evidence by itself.
- Do not force-push `completed/l1jtw85-core-fixes`.

## COMPLETED BY THIS CONVERSATION

| BUG | Result | Notes |
|---|---|---|
| BUG-850-269 | PASS / PROMOTED | boss fixed-time same-hour minute delta converted to ms |
| BUG-850-266 | PASS / PROMOTED | exact 0..99 probability boundary |
| BUG-850-265 | PASS / PROMOTED | salary computed before Contribution reset |
| BUG-850-263 | PASS / PROMOTED | account online-state authority bound to supplied account |
| BUG-850-262 | PASS / PROMOTED | overflow-safe long threshold + clamp + exact probability |
| BUG-850-258 | PASS / PROMOTED | furniture DB-first affected-row gates before live publish/remove |
| BUG-850-250 | PASS / PROMOTED | SoulTower top-10 + safe comparator |
| BUG-850-251 | PASS / PROMOTED | SoulTower durable transaction/rollback + post-commit RAM publish |
| BUG-850-257 | PASS / ALREADY COVERED | empty-board bootstrap covered by 250 logic |
| BUG-850-255 | PASS / PROMOTED | three progression saves use UPSERT; exact historical patch only |

## BUG-850-255 AUTHORITY

```text
WORK_CI=35723688572
COMPLETED_CI=35900095083
SOURCE_COMMIT=15bf12eda62671cede855ff6931d49f8db1a4d05
HISTORICAL_PATCH_CHAIN=7470ecbe,8e2d571a,1f2cb7b1,a01d93c3,d3c8078b,1693b07c
EXACT_PATCH_APPLIED=PASS
UNRELATED_SOURCE_REPLAY=NO
SOURCE_CONTRACT=PASS
MOBS_TABLES_JAVAC=PASS
QUESTNEW_NO_NEW_JAVAC_REGRESSION=PASS
TARGETED_BEHAVIOR_RUNTIME=PASS
CONCURRENCY_GATE=PASS
STATUS=PASS_PROMOTED
```

The first two completed promotion attempts failed closed because the validation workflow itself had a matching/quoting defect. No core source was committed by those failed attempts. Run `35900095083` is the successful completed authority.

## CONCURRENT COMPLETED WORK DISCOVERED AT HANDOFF

The completed branch advanced independently after the 255 close. Current completed records include at least:

- `BUG-850-254` — PASS / PROMOTED
- `BUG-850-252` — PASS / PROMOTED
- `BUG-850-249` — PASS / PROMOTED
- `BUG-850-246` — PASS / PROMOTED
- `BUG-850-245` — PASS / PROMOTED
- `BUG-850-275` — PASS / PROMOTED

The latest branch also contains newer files/records for additional repairs such as 247/248/288. Re-fetch before assuming this static list is current.

## BUG-850-254 AUTHORITY

```text
WORK_CI=35722588093
COMPLETED_CI=35938795118
SOURCE_COMMIT=0f3c51e000e89cef0c3bf2f861d1e78fa271fcf4
PATCH_CHAIN=e5320aea,2fb56fbd,c75e085e,8470e775,a5dba0e6
EXACT_HISTORICAL_PATCH_CHAIN=PASS
UNRELATED_SOURCE_REPLAY=NO
SOURCE_CONTRACT=PASS
BULK_DELETE_RESULT_PROPAGATED=PASS
LIVE_WEEKLY_RESET_GATED_BY_DELETE_SUCCESS=PASS
WEEKLY_RESCHEDULE_FINALLY_PRESERVED=PASS
BUG_850_255_UPSERT_PRESERVED=PASS
TARGETED_JAVAC=PASS
TARGETED_BEHAVIOR_RUNTIME=PASS
FAILED_DELETE_LIVE_STATE_UNCHANGED=PASS
NEXT_WEEK_SCHEDULE_PRESERVED=PASS
CONCURRENCY_GATE=PASS
STATUS=PASS_PROMOTED
```

Therefore: **do not resume BUG-850-254**.

## AUDIT STATE

`recovery/DUAL_LANE_CORE_WORK_LEDGER.md` reports:

```text
RECENT_LANE_NEXT=EXHAUSTED
OLD_LANE_NEXT=RETIRED
MEETING=AUDIT_EXHAUSTED
```

The next conversation should not perform a new repo-wide or archive-wide BUG scan. Continue only known repair promotion/reconciliation unless the user explicitly starts a new audit.

## NEXT SAFE CANDIDATE

`recovery/BUG-850-244_PROMOTION_READY.tmp` exists on completed and currently contains only:

```text
STOP2
```

This is a marker only. It is **not** a PASS gate and must not be used to declare `BUG-850-244` complete.

Next conversation should:

1. Re-fetch latest completed/work heads.
2. Check whether another lane already completed `BUG-850-244` after this handoff.
3. Locate the 244 work repair, dedicated CI/evidence, exact historical source boundary, and ownership state.
4. Compare exact repaired behavior against latest completed source.
5. If already covered, prove and record `PASS_ALREADY_COVERED`.
6. Otherwise promote only the exact 244 delta, with compile/runtime/source contract gates and a concurrency gate.
7. Update completed documentation only after PASS.

## READ FIRST

```text
CORE_REPAIR_STATUS_20260924.md
README.md
recovery/DUAL_LANE_CORE_WORK_LEDGER.md
recovery/BUG-850-254_VALIDATION_20260924.md
recovery/BUG-850-255_VALIDATION_20260924.md
recovery/BUG-850-244_PROMOTION_READY.tmp
```

## VALIDATE

For every next promotion:

```text
SOURCE_CONTRACT=PASS
TARGETED_JAVAC_OR_NO_NEW_REGRESSION=PASS
TARGETED_BEHAVIOR_RUNTIME=PASS
CONCURRENCY_GATE=PASS
UNRELATED_SOURCE_REPLAY=NO
```

Shared-file repairs require an exact-patch proof if later work contains unrelated BUGs.

## FINAL / CONTINUATION PROMPT

```text
GO
Read CORE_REPAIR_STATUS_20260924.md and recovery/L1JTW85_CORE_REPAIR_HANDOFF_20260924.md.
Refetch completed/work heads first.
Do not redo completed BUGs.
Reconcile BUG-850-244 promotion readiness next; STOP2 is a marker, not PASS evidence.
Use exact patch + dedicated validation + non-force concurrency gate.
```
