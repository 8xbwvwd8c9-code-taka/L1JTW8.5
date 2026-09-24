# 850 Inventory GRID collection semantic trace — 2026-09-24

```text
STATUS=PASS_NONINVENTORY_SEMANTICS
VTABLE_SLOTS_ANALYZED=64
UNIQUE_FUNCTIONS_ANALYZED=64
B_TRIPLE_ACCESS_FUNCTIONS=2
B_TRIPLE_MUTATORS=0x007023D0
BEST_SEMANTIC_CLASS=ui_bookkeeping
ROOT_DISCOVERY_REOPENED=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
```

## Decision

The prior GRID B triple at `GRID+0x1E4/+0x1E8/+0x1EC` is not promoted as the backing inventory record collection.

This agrees with V7b controlled-change evidence: stack-count change and distinct-record add did not change the B triple.

The GRID B lane is closed. Do not reopen it unless new independent code evidence contradicts this result.

## Mainline

Proceed only through the INVWIN lane. Current mainline candidate is the embedded subobject at `INVWIN+0xF4`, whose helper chain exposes size/index semantics and a 4-byte element stride.

```text
NEXT=INVWIN+0xF4 static begin/end layout proof -> fixed two-dword controlled runtime delta gate
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```
