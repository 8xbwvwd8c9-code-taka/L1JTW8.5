# 381 -> 850 Equipment Continuous Effect Audit

## Migration authority
- 850 core/runtime/UI/protocol/data model is authoritative.
- 381 is donor/reference only.
- Empty SQL is not sufficient evidence for `SKIP`.

## Scope
381 table:
- `w_裝備持續特效`

Current split artifact:
```text
DB/381_DB_AI用/w_裝備持續特效_202609221205.sql
SIZE=0 bytes
```

## DB evidence
```text
DATA_STATE=NO_ACTIVE_DATA
SOURCE_SCHEMA=NOT_PROVEN
CREATE=NOT_PROVEN
INSERT=NOT_PROVEN
```

This proves only that the current split source contains no active rows. It does not prove that the 381 feature/runtime does not exist.

## Runtime/control trace
Targeted donor searches performed so far did not prove:
- an exact `w_裝備持續特效` table-name consumer
- a dedicated continuous-equipment-effect loader
- a dedicated module entry/action
- a clear English alias owner

Therefore:
```text
DONOR_RUNTIME=NOT_PROVEN
CONTROL_SOURCE=NOT_PROVEN
STARTUP_REGISTRATION=NOT_PROVEN
EQUIP_HOOK=NOT_PROVEN
UNEQUIP_HOOK=NOT_PROVEN
```

No negative conclusion may be inferred from those non-matches. Table aliases, generic equipment handlers, startup registration, Config/XML control, or shared stat/effect owners still require targeted audit.

## Required 850 comparison
Before any final migration decision, compare the intended behavior against 850 native mechanisms such as:
- equipment lifecycle / slot owner
- passive item/stat aggregation
- timed effect/buff lifecycle
- skill/effect scheduler
- visual-effect packet path

If the behavior is representable through existing 850 equipment/effect lifecycle plus data mapping, classify L2. If it requires a new persistent/session owner or new cross-event lifecycle, classify L3. Confirmed client resources/protocol are recorded separately as a client gate.

## Migration decision
```text
CURRENT_MIGRATION=HOLD
```

Do not reconstruct a donor framework from guesses, and do not skip the feature solely because the current SQL file is empty.

## Difficulty
```text
DIFFICULTY=NOT_FINAL
```

## Status
```text
STATUS=HOLD
AUDIT=IN_PROGRESS
MODULE=w_裝備持續特效
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_SQL=EMPTY_0_BYTES
DATA_STATE=NO_ACTIVE_DATA
SOURCE_SCHEMA=NOT_PROVEN
DONOR_RUNTIME=NOT_PROVEN
CONTROL_SOURCE=NOT_PROVEN
850_EQUIVALENT=REQUIRED
CLIENT_DEP=NOT_PROVEN
DIFFICULTY=NOT_FINAL
CURRENT_MIGRATION=HOLD
PRODUCTION_PORT=NO
BLOCKERS=381 runtime/control ownership; 850 native-equivalent mapping; authoritative schema/data
```
