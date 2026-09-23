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

## V2 — completed / raw-file path exhausted

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

## V3 — completed / coarse scorer retired

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

The ±0x600 displacement scorer is retired because its raw member counts cross function/class boundaries.

The V3 nearby-call list is also retired for ranking. Its scanner treated any byte `E8` as a call opcode without instruction-boundary decoding. A proven false positive is:

```text
reported CALL RVA=0x00701E78
actual aligned instruction at 0x00701E76:
C7 05 E8 CE 6B 01 00 00 00 00
```

The `E8` byte at `0x00701E78` belongs to the absolute address immediate `0x016BCEE8`; it is not a CALL opcode.

Full reconstruction notes:

```text
launcher/850Launcher/INVENTORY_V3_BYTE_RECONSTRUCTION_20260924.md
```

## Code-layout evidence

Reconstructed aligned instructions support:

```text
INVWIN_A: vtable write + zero init at +0x178/+0x17C neighborhood
GRID_A:   vtable write + dense zero-init cluster beginning +0x1D8
ROOT_A:   vtable write + ROOT+0x168=0 + embedded init at +0x16C
```

Current candidate GRID triples remain inference only:

```text
VECTOR_A = {+0x1D8,+0x1DC,+0x1E0}
VECTOR_B = {+0x1E4,+0x1E8,+0x1EC}
VECTOR_C = {+0x1F0,+0x1F4,+0x1F8}
```

`+0x228/+0x22C` proximity hits are rejected as GRID model evidence because they belong to another function/class context.

## B-side local shape

Reconstructed code shows:

```text
INVWIN_B: this=[ebp-10] -> INVWIN vtable -> cleanup call
GRID_B:   this=[ebp-10] -> GRID vtable   -> cleanup call
ROOT_B:   this=[ebp-10] -> ROOT vtable   -> clear module-global -> more cleanup
```

INVWIN_A/GRID_A and INVWIN_B/GRID_B are no longer presumed to share one function merely because their xrefs are close. Adjacent independent ctor/dtor wrappers are at least as plausible.

```text
NO_INHERITANCE_INFERENCE_FROM_XREF_PROXIMITY=YES
```

## Prior backing-model evidence adjudicated

Single run:

```text
INVWIN+0x220
USED=1792=28*64
CAPACITY=1856=29*64
ITEM_OFF=+0x04 candidate
CATALOG_MATCH=27/27 nonzero=100%
```

Later full-process run re-established exactly one valid GRID/ROOT/INVWIN graph but returned:

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

Additional bounds adjudication rejected high-score artifacts that started at vector END/CAP pointers or read far beyond logical USED ranges.

Full report:

```text
launcher/850Launcher/INVENTORY_BACKING_MODEL_ADJUDICATION_20260924.md
```

## Highest-priority code anchor

ROOT_B contains:

```text
RVA 0x00701E76
C7 05 E8 CE 6B 01 00 00 00 00
```

Therefore:

```text
GLOBAL_VA =0x016BCEE8
GLOBAL_RVA=0x012BCEE8
TEARDOWN_CLEAR=PROVEN
```

Only ROOT_B among the reconstructed INVWIN/GRID/ROOT B-side snippets clears a module-global after its vtable write. This asymmetry is consistent with ROOT being the higher-level/singleton-owned UI object, matching the live graph, but construction-side assignment is still required before owner promotion.

## V4 — prepared

```text
launcher/850Launcher/tools/run_850_inventory_ctor_owner_trace_v4.ps1
```

Purpose:

```text
six exact vtable xrefs
 -> candidate function boundaries
 -> direct inbound E8 callers whose target exactly equals candidate start
 -> module-global stores in candidate/caller contexts
```

Promotion requires function-context evidence; raw nearby E8 counts are ignored.

## V4b — prepared / hardened

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
classify exact load/store/push forms
read only the module-global dword itself
classify value as ZERO / MODULE_POINTER / NONMODULE_VALUE_OR_POINTER
split refs near ROOT_A vs ROOT_B
never dereference into heap/MEM_PRIVATE
```

Promotion rule:

```text
construction-side assignment of this/returned ROOT object to same global
PLUS teardown clear / repeated ROOT-context loads
=> STABLE_ROOT_OWNER_CANDIDATE

otherwise
=> FLAG_OR_STATE_GLOBAL
```

## V4c — prepared

```text
launcher/850Launcher/tools/run_850_inventory_vtable_rtti_v4c.ps1
```

Purpose:

```text
read fixed GRID/ROOT/INVWIN vtables inside Lin.bin2 module image
read vtable[-1] MSVC CompleteObjectLocator when present
read TypeDescriptor decorated class name
read ClassHierarchyDescriptor/base-class names when internally valid
compare first 32 vtable entries by index/prefix
```

This can prove or reject class/inheritance relationships without touching heap inventory data.

## Hard boundaries

```text
OpenProcess=QUERY_INFORMATION|VM_READ
MEMORY_WRITE=NO
HEAP_SCAN=NO
HEAP_DEREFERENCE=NO for V4b/V4c
MEM_PRIVATE_SCAN=NO
VECTOR_WIDE_SCAN=NO
NO_381_880_RUNTIME_ADDRESS_REUSE=YES
```

## Current gate

```text
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
ITEM_USE_BRIDGE=UNMAPPED
NEXT_RUNTIME_1=run_850_inventory_vtable_rtti_v4c.ps1
NEXT_RUNTIME_2=run_850_inventory_root_global_xref_v4b.ps1
NEXT_RUNTIME_3=run_850_inventory_ctor_owner_trace_v4.ps1
NEXT_AFTER_OWNER_PROOF=fixed-offset read only; no broad scan
```
