# 850 Inventory V7b Controlled-Change Result — 2026-09-24

## Authority

```text
CLIENT=I:\8.50c客服端\Lin.bin2
SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
ROOT_GLOBAL_RVA=0x012BCEE8
OWNER_ANCHOR=PASS_RESTART_STABLE
```

## Candidate under test

V7 found only one collection-shaped GRID triple:

```text
B:
GRID+0x1E4 = BEGIN
GRID+0x1E8 = END
GRID+0x1EC = CAP

baseline used bytes = 2016
baseline capacity bytes = 3024
shape = MONOTONIC_ALIGNED_TRIPLE
```

A and C were `NONVECTOR_SHAPE` and are not promoted.

## Controlled-change evidence

Same process identity was used for baseline and controlled snapshots.

### Existing stack quantity change

Observed B values remained unchanged:

```text
DELTA_BEGIN=0
DELTA_END=0
DELTA_CAP=0
DELTA_USED_BYTES=0
DELTA_CAPACITY_BYTES=0
```

Interpretation:

```text
B is not directly encoding the changed stack quantity.
```

### Distinct record add

A fresh baseline was captured, then one distinct inventory record was added.
Observed B values again remained unchanged:

```text
DELTA_BEGIN=0
DELTA_END=0
DELTA_CAP=0
DELTA_USED_BYTES=0
DELTA_CAPACITY_BYTES=0
STATUS=CONTROLLED_DELTA_NOT_MATCHED
```

Interpretation:

```text
B is not proven to be the live inventory-record cardinality container.
```

## Decision

```text
B_TRIPLE_SHAPE=STRUCTURAL_CANDIDATE_ONLY
B_INVENTORY_COLLECTION=NOT_PROVEN
B_PROMOTION=NO
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

Do not infer inventory semantics from `BEGIN < END < CAP` alone.
Do not resume broad heap/vector scans.

## Next mainline

Use code semantics instead of shape guessing:

```text
INVWIN_VTABLE_RVA=0x00EDE180
→ bounded first-64 vtable method capture
→ Capstone x86/32 this-alias analysis
→ enumerate actual INVWIN owner-relative field reads/writes
→ specifically adjudicate historical INVWIN+0x220 evidence
```

Tool:

```text
launcher/850Launcher/tools/run_850_inventory_invwin_methods_v8.ps1
```

The GRID semantic lane is delegated independently to:

```text
launcher/850Launcher/INVENTORY_GRID_VTABLE_COLLECTION_SEMANTIC_A2A_20260924.md
```

## Safety

```text
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
MEMORY_WRITE=NO
ROOT_DISCOVERY_REOPENED=NO
```
