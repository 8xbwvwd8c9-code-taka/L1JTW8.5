# 381 -> 850 Item Fusion DB Audit

## Migration authority
- 850-native first.
- 381 donor/reference only.
- Analysis/docs only in this branch.
- Empty SQL / zero active rows is not evidence that a feature can be skipped.

## Scope
381 table:
- `w_物品融合db化`

## Source evidence
Split SQL:
`DB/381_DB_AI用/w_物品融合db化_202609221205.sql`

Observed content:
```text
SIZE=0
ROWS=0
DATA_STATE=NO_ACTIVE_DATA
```

Targeted donor searches for the exact table name and simple fusion aliases have not proven a runtime owner.

```text
DONOR_RUNTIME=NOT_PROVEN
SOURCE_SCHEMA=NOT_PROVEN
CONTROL_SOURCE=NOT_PROVEN
```

This is an unresolved audit state, not a skip decision.

## Required audit before classification
Still required:
1. inspect 381 startup registration / generic item conversion and synthesis handlers
2. inspect Config/XML/properties/hard-coded control paths
3. identify actual consume/success/failure/persistence semantics if a runtime owner is found
4. compare against 850 `craft`, `craft_exchange`, `html_craft`, item conversion, and inventory transaction paths
5. identify any client menu/UI/protocol/resource dependency
6. only then assign L1-L4 and MIGRATE/ADAPT/MERGE/HOLD/SKIP

Do not invent:
- schema
- item fusion semantics
- consume rules
- success/failure behavior
- persistence
- UI/protocol dependencies

## Difficulty policy
If authoritative semantics are simply material consumption -> output item using 850 native crafting/conversion, L2 is the expected candidate. If the feature carries persistent per-item state, protected failure state, or a new lifecycle owner, L3 may be required. Confirmed client work is recorded separately.

Current difficulty cannot be finalized without the runtime contract.

## Status
```text
STATUS=HOLD
AUDIT=IN_PROGRESS
MODULE=w_物品融合db化
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_ROWS=0
SOURCE_SQL=EMPTY_0_BYTES
DATA_STATE=NO_ACTIVE_DATA
SOURCE_SCHEMA=NOT_PROVEN
DONOR_RUNTIME=NOT_PROVEN
CONTROL_SOURCE=NOT_PROVEN
850_EQUIVALENT=REQUIRED
CLIENT_PROTOCOL_DEP=NOT_PROVEN
DIFFICULTY=NOT_FINAL
CURRENT_MIGRATION=HOLD
PRODUCTION_PORT=NO
BLOCKERS=381 runtime/control ownership; authoritative schema/data; 850 native-equivalent mapping
```
