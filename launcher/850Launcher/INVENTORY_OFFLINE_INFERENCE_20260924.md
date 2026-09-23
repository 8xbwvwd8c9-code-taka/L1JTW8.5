# 850 Inventory Offline Inference — 2026-09-24

```text
STATUS=INFERENCE_ONLY
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
SOURCE=850_inventory_runtime_module_xref_v3.txt
WP5=NOT_YET
WP6=NOT_YET
MEMORY_WRITE=NO
NO_HEAP_SCAN=YES
NO_MEM_PRIVATE_SCAN=YES
NO_VECTOR_SCAN=YES
```

## Runtime-image result

V3 proved that the packed client exposes the inventory-related code only after the image is loaded. Six runtime vtable literal xrefs were recovered:

```text
INVWIN  ctor-like=0x0070135A  teardown-like=0x00701C2B
GRID    ctor-like=0x007013DA  teardown-like=0x00701C8B
ROOT    ctor-like=0x0070184E  teardown-like=0x00701E6B
```

The pairs are inference labels, not yet formal function identities. The first INVWIN/GRID pair spacing is especially consistent with paired construction/destruction code.

## Structural inference

Existing dynamic UI graph evidence remains:

```text
GRID +0x0EC -> ROOT
ROOT +0x15C -> GRID
ROOT +0x168 -> INVWIN
```

The ROOT constructor-like xref at `0x0070184E` adds independent code evidence for the `+0x168` slot:

```text
write ROOT vtable
this+0x168 = 0
initialize embedded object at this+0x16C
```

Therefore `ROOT+0x168` is now constructor-supported as a pointer/owned-reference slot. The prior live graph says this slot resolves to INVWIN. This is strong structural evidence, but it is still not promoted to formal WP5 because restart-stable owner acquisition is not yet proven.

## GRID collection-state cluster

Immediately after the GRID vtable write at `0x007013DA`, the same constructor-like region zero-initializes a dense field block:

```text
+0x1D8 = 0
+0x1DC = 0
+0x1E0 = 0
+0x1E4 = 0
+0x1E8 = 0
+0x1EC = 0
+0x1F0 = 0
+0x1F4 = 0
+0x1F8 = 0
+0x200 = 0
+0x204 = 0
```

The closest collection hypotheses are three vector-like triples:

```text
VECTOR_A = GRID + {0x1D8,0x1DC,0x1E0}
VECTOR_B = GRID + {0x1E4,0x1E8,0x1EC}
VECTOR_C = GRID + {0x1F0,0x1F4,0x1F8}
```

This layout is consistent with empty begin/end/capacity-style triples, but exact STL/vector semantics are **not proven**. `+0x1FC` is not established by current evidence. `+0x200/+0x204` are zero-initialized but their roles remain unknown.

## Important rejection: broad-window +0x228 contamination

V3's `MODEL_REFS` around `+0x228/+0x22C` must not be attributed to GRID merely because they fall inside the ±0x600 correlation window.

At approximately `0x007014CC..0x007014F7`, a different constructor-like sequence is visible:

```text
initialize this+0x228 subobject
[this]       = vtable 0x012DDC98
[this+0x228] = vtable 0x012DDE28
[this+0x22C] = constructor argument
[this+0x234] = 0
[this+0x238] = 0
```

This is a separate class/function context. Consequently:

```text
DO_NOT_USE_GRID_MODEL=+0x228/+0x22C based only on V3 proximity score
```

## Why V3 BEST_OWNER=NONE is not a blocker

V3 searches raw little-endian displacement bytes and correlates them inside a ±0x600 window. This intentionally favors safety over semantic precision and creates many false-positive member hits. The `STRONG_OWNER_WINDOW_COUNT=0` result means the broad heuristic did not satisfy its promotion rule; it does not erase the exact constructor-local evidence above.

## Current best model

```text
ROOT
  +0x15C -> GRID             [dynamic graph evidence]
  +0x168 -> INVWIN           [dynamic graph + constructor-supported pointer slot]
  +0x16C -> embedded state   [constructor-supported]

GRID
  +0x0EC -> ROOT             [dynamic graph evidence]
  +0x1D8..+0x1F8             [constructor-supported collection/bookkeeping cluster]
  VECTOR_A candidate          [1D8,1DC,1E0]
  VECTOR_B candidate          [1E4,1E8,1EC]
  VECTOR_C candidate          [1F0,1F4,1F8]
```

## Next code-only task

Do not return to heap/vector scans. The next reverse-engineering task is:

```text
1. infer exact function boundaries around the six vtable xrefs;
2. find inbound direct callers of the ctor-like functions;
3. inspect those callers for module-global pointer stores/loads;
4. derive a stable ROOT/GRID owner acquisition path from module code;
5. only after that, perform one narrow read of GRID+0x1D8..0x204.
```

## Next live validation — narrow only

When runtime is available again, do not scan for vectors. After a stable GRID owner pointer is obtained, read only the fixed field window:

```text
GRID+0x1D8 .. GRID+0x204
```

For A/B/C, validate only:

```text
begin <= end <= capacity
all nonzero pointers readable
used bytes = end-begin is sane
capacity bytes = capacity-begin is sane
controlled inventory changes affect exactly one candidate consistently
restart/relogin stability
```

Only the surviving candidate may be followed to item records. ObjectId/ItemId/Count/Enchant/Equipped remain unmapped until that gate passes.

```text
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
ITEM_USE_BRIDGE=UNMAPPED
MEMORY_WRITE=NO
```
