# 381 -> 850 Equipment Continuous Effect Audit

## Migration authority

Target authority:
- 850 core/runtime/UI/protocol/data model

381 is donor-only.

## Scope

381 table:
- `w_裝備持續特效`

Current split artifact:
```text
DB/381_DB_AI用/w_裝備持續特效_202609221205.sql
SIZE=0 bytes
```

## Runtime trace

Targeted source search did not prove a runtime that reads:
- `w_裝備持續特效`
- a dedicated continuous-equipment-effect loader
- a dedicated module entry/action

Therefore:
```text
DONOR_RUNTIME=NOT_PROVEN
ACTIVE_CONTENT=NONE_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
```

## Migration decision

Do NOT reverse-engineer or recreate a donor framework from class-name guesses.

Because:
- source content is empty
- runtime ownership is not proven
- 850 is authoritative

current migration should be:

```text
CURRENT_MIGRATION=SKIP
```

If future authoritative rows/source appear, first determine whether the behavior can map to existing 850:
- equipment timer/effect
- passive item buff
- skill/effect scheduler
- visual effect packet

before adding any new framework.

## Difficulty

```text
CURRENT_CONTENT=NONE
CURRENT_MIGRATION=SKIP
FRAMEWORK_LEVEL=NOT_CLASSIFIED
```

No L3/L4 should be assigned without active content or runtime evidence.

## Status

```text
STATUS=PASS
MODULE=w_裝備持續特效
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_SQL=EMPTY_0_BYTES
SOURCE_SCHEMA=NOT_PROVEN
DONOR_RUNTIME=NOT_PROVEN
ACTIVE_CONTENT=NONE_PROVEN
CURRENT_MIGRATION=SKIP
PRODUCTION_PORT=NO
```
