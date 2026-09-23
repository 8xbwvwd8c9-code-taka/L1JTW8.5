# L1JTW8.5 Core Repair Branch Policy

Date: 2026-09-23
Status: ACTIVE

## Authoritative branches

Only these two branches are valid working entry points for core BUG repair:

- `work/l1jtw85-core-fixes` — pending / claimed / in-progress / not-yet-promoted core repairs.
- `completed/l1jtw85-core-fixes` — validated and completed core repairs.

## Do not create new repair branches

Do **not** create any additional core-repair branch, including patterns such as:

- `repair/bug-*`
- `integrate/l3-*`
- `promote/l3-*`
- per-BUG repair branches
- per-level repair branches

Existing historical branches are retained only as historical mapping / provenance. They are not valid work entry points and must not receive new repair work.

## Repair flow

```text
BUG discovered / audited
        |
        v
work/l1jtw85-core-fixes
  - claim / ownership
  - root-cause analysis
  - core patch
  - targeted validation
        |
        | PASS only
        v
completed/l1jtw85-core-fixes
  - completed core state
  - BUG cause record
  - solution record
  - validation evidence
  - historical branch / commit mapping when applicable
```

## Completed-record authority

All completed BUG repair documentation is authoritative only on:

`completed/l1jtw85-core-fixes`

Every completed repair record must contain at least:

```text
BUG=<BUG-ID>
LEVEL=<L1|L2|L3>
STATUS=<PASS|ALREADY_COVERED|...>
CAUSE=<root cause>
SOLUTION=<implemented fix>
VALIDATION=<targeted evidence / test / action run>
FILES=<affected core/config/db files when applicable>
HISTORY=<old repair/integrate/promote branch or commit mapping, if any>
```

Detailed Markdown sections should use this minimum structure:

```markdown
## BUG-850-XXX — short title

### Cause
What was wrong, where the authority/logic failed, and the affected call path.

### Solution
What was changed and why the fix closes the failure mode.

### Validation
Targeted compile/runtime/contract/DB checks and PASS evidence.

### History / Mapping
Historical branch or commit references only when useful.
```

## Ownership / concurrency

Before changing a BUG on `work/l1jtw85-core-fixes`, verify that it is not already claimed or completed by another worker.

If duplicate ownership or a newer authoritative repair is found:

```text
STOP
DO NOT PATCH DUPLICATE
USE EXISTING AUTHORITATIVE RESULT
```

## Promotion gate

A repair enters `completed/l1jtw85-core-fixes` only after the relevant dependency chain is checked:

```text
BUG
-> Java core entry / call path
-> config control
-> DB table / loader
-> default / fallback
-> ACTIVE source
-> minimal complete repair
-> targeted validation
-> PASS
-> completed branch
```

380 / 381 / 880 cores may be used as donor/reference evidence, but code is not copied blindly; L1JTW8.5 behavior and interfaces remain authoritative.

## Historical branches

Old branches may remain for traceability. Their role is read-only provenance:

```text
HISTORICAL_MAPPING_ONLY=YES
WORK_ENTRY=NO
NEW_COMMITS_FOR_CORE_REPAIR=NO
```

## Current rule

```text
CORE_BRANCH_COUNT=2
PENDING_BRANCH=work/l1jtw85-core-fixes
COMPLETED_BRANCH=completed/l1jtw85-core-fixes
NEW_REPAIR_BRANCHES=FORBIDDEN
COMPLETED_BUG_CAUSE_SOLUTION_RECORDS=completed/l1jtw85-core-fixes
```
