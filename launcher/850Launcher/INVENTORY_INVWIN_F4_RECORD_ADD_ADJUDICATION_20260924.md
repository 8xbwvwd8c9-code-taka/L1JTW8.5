# 850 Inventory INVWIN+0xF4 record-add adjudication — 2026-09-24

## Controlled evidence

Authority process:

```text
PID=31632
PROCESS_START_UTC=2026-09-24T05:19:05.5641076Z
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
```

Fresh baseline:

```text
BEGIN=0x2C7AEB70
END=0x2C7AEB8C
USED_BYTES=28
ELEMENT_COUNT=7
PAIR_CLASS=MONOTONIC_STRIDE4_PAIR
```

After one controlled distinct inventory-record add:

```text
BEGIN=0x2C7AEB70
END=0x2C7AEB8C
USED_BYTES=28
ELEMENT_COUNT=7
DELTA_BEGIN=0
DELTA_END=0
DELTA_USED_BYTES=0
DELTA_ELEMENT_COUNT=0
ALLOCATION_MOVED=NO
STATUS=CONTROLLED_DELTA_NOT_MATCHED
```

## Decision

The `INVWIN+0xF4` subobject remains a real static 4-byte-stride collection shape, but the controlled distinct-record add did not change its cardinality.

Therefore it is not promoted as the backing inventory-record collection.

```text
F4_STATIC_COLLECTION_SHAPE=PASS
F4_STACK_COUNT_INVARIANCE=PASS
F4_DISTINCT_RECORD_ADD_GATE=FAIL
F4_AS_INVENTORY_RECORD_COLLECTION=RETIRED
RUN_RECORD_REMOVE_FOR_F4=NO
COLLECTION_BUFFER_DEREFERENCE=NO
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

Do not reopen this lane unless a new independently verified controlled action proves that this collection tracks inventory-record cardinality.

## Mainline pivot

Return to code-proven INVWIN child/model pointer fields below `+0x180`, prioritizing:

```text
INVWIN+0x148
INVWIN+0x14C
INVWIN+0x150
INVWIN+0x154
```

These fields had the strongest V8 direct read density and lifecycle/virtual-call evidence. Next work should classify only these bounded child objects and their immediate vtable/helper semantics; no heap scan, no MEM_PRIVATE scan, no broad vector scan.
