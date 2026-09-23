# 850 Inventory XREF Status — 2026-09-24

## Authority

```text
CLIENT=I:\8.50c客服端\Lin.bin2
SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
BRANCH=work/850-inventory-helper
```

## Proven UI graph

```text
InventoryItemGrid +0xEC -> Root
Root +0x15C -> InventoryItemGrid
Root +0x168 -> InvWin
```

These relations are runtime UI ownership evidence. They do not by themselves prove the backing item collection or item-record layout.

## Static XREF V2 — completed

```text
STATUS=PASS_STATIC_ONLY_V2
VTABLE_LITERAL_XREF_TOTAL=0
GLOBAL_MEMBER_REF_TOTAL=0
RUNTIME_ATTACH=NO
MEMORY_WRITE=NO
```

Interpretation:

```text
V2_RESULT=VALID_NEGATIVE_EVIDENCE
DISK_STATIC_RAW_XREF_PATH=EXHAUSTED
REASON=packed/virtual Lin.bin2 layout hides the relevant runtime code from the raw-file executable view
```

Do not repeat or widen raw-file literal/displacement scans.

## Runtime module XREF V3 — completed

Tool:

```text
launcher/850Launcher/tools/run_850_inventory_runtime_module_xref_v3.ps1
```

Result:

```text
STATUS=PASS_RUNTIME_MODULE_ONLY
VTABLE_RUNTIME_XREF_TOTAL=6
GLOBAL_RUNTIME_MEMBER_REF_TOTAL=7176
STRONG_OWNER_WINDOW_COUNT=0
BEST_OWNER=NONE
```

Recovered vtable xrefs:

```text
INVWIN_A=0x0070135A
GRID_A  =0x007013DA
ROOT_A  =0x0070184E
INVWIN_B=0x00701C2B
GRID_B  =0x00701C8B
ROOT_B  =0x00701E6B
```

The broad ±0x600 displacement scorer is now retired. Its raw member counts contain cross-function contamination and are not a promotion source.

## Code-layout inference

Current constructor-supported evidence:

```text
ROOT +0x168 = pointer/owned-reference slot candidate
ROOT +0x16C = embedded-state initialization
GRID +0x1D8..+0x204 = dense collection/bookkeeping cluster
```

Candidate triples only:

```text
GRID VECTOR_A = {+0x1D8,+0x1DC,+0x1E0}
GRID VECTOR_B = {+0x1E4,+0x1E8,+0x1EC}
GRID VECTOR_C = {+0x1F0,+0x1F4,+0x1F8}
```

Exact vector semantics remain unproven.

`+0x228/+0x22C` proximity hits were rejected as GRID model evidence because they belong to another function/class context around `0x007014CC..0x007014F7`.

## Prior backing-model evidence adjudicated

A prior run (`PID=35568`) found:

```text
INVWIN+0x220
BEGIN=0x0F0FF488
END  =0x0F0FFB88
CAP  =0x0F0FFBC8
USED =1792 = 28*64
CAPACITY=1856 = 29*64
ITEM_OFF=+0x04 candidate
CATALOG_MATCH=27/27 nonzero = 100%
```

`INVWIN+0x210` pointed exactly `0x80` bytes (`2*64`) into that same backing region and is classified as an alias/interior-pointer candidate, not a second model.

A later full-process run (`PID=36132`) re-established exactly one valid GRID/ROOT/INVWIN graph, then found:

```text
SCAN_RANGE=INVWIN+0x1E0..0x230
CANDIDATE_COUNT=0
STATUS=NO_STRONG_VECTOR64_CURRENT_PROCESS
```

Therefore:

```text
INVWIN+0x220=SINGLE_RUN_STRONG_BUT_RESTART_UNSTABLE
PROMOTE_TO_LAYOUT=NO
```

`GRID+0x0F4` is a real small vector-like state (`USED=48`) but its high catalog scores came from fixed record probing beyond that logical used range, so it is not accepted as the player inventory backing model.

Full adjudication:

```text
launcher/850Launcher/INVENTORY_BACKING_MODEL_ADJUDICATION_20260924.md
```

## Highest-priority stable-anchor candidate

V3 ROOT_B bytes contain:

```text
ROOT_B_XREF_RVA=0x00701E6B
C7 05 E8 CE 6B 01 00 00 00 00
```

This writes zero to:

```text
GLOBAL_VA =0x016BCEE8
GLOBAL_RVA=0x012BCEE8
```

If ROOT_B is a teardown/destructor context, this may be a singleton/global owner slot being cleared. It may also be a flag/state dword; construction-side evidence is required before promotion.

## V4 — prepared

Tool:

```text
launcher/850Launcher/tools/run_850_inventory_ctor_owner_trace_v4.ps1
```

Purpose:

```text
six exact vtable xrefs
 -> candidate x86 function boundaries
 -> group xrefs by function start
 -> enumerate direct E8 callers
 -> inspect only those callers/functions for module-global stores
```

Key gate:

```text
INVWIN_A + GRID_A same START_RVA
=> same function context
=> stop calling them independent constructors
=> vtable transition/inheritance remains inference until control-flow proof
```

## V4b — prepared

Tool:

```text
launcher/850Launcher/tools/run_850_inventory_root_global_xref_v4b.ps1
```

Seed:

```text
GLOBAL_RVA=0x012BCEE8
```

Purpose:

```text
scan loaded Lin.bin2 executable MEM_IMAGE only
find every absolute reference to candidate global
classify load/store/push references
read only the module-global dword itself
never dereference it into heap/MEM_PRIVATE
```

Promotion rule:

```text
construction-side assignment of this/returned ROOT object to RVA 0x012BCEE8
OR repeated ROOT-context pointer loads from same global
=> STABLE_ROOT_OWNER_CANDIDATE

otherwise
=> FLAG_OR_STATE_GLOBAL
```

## Hard boundaries

```text
OpenProcess=QUERY_INFORMATION|VM_READ
MEMORY_WRITE=NO
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
NO_381_880_RUNTIME_ADDRESS_REUSE=YES
```

## Current gate

```text
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
ITEM_USE_BRIDGE=UNMAPPED
NEXT_RUNTIME_1=run_850_inventory_ctor_owner_trace_v4.ps1
NEXT_RUNTIME_2=run_850_inventory_root_global_xref_v4b.ps1
NEXT_AFTER_OWNER_PROOF=fixed-offset read only; no broad scan
```
