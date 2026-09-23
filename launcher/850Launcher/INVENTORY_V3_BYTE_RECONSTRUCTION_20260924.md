# 850 Inventory V3 Byte Reconstruction — 2026-09-24

```text
STATUS=OFFLINE_RECONSTRUCTION
SOURCE=850_inventory_runtime_module_xref_v3.txt
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
MEMORY_WRITE=NO
FORMAL_WP5=NOT_YET
```

## Method

V3 emitted 1,600+ runtime-image hex snippets around literal/member hits. Overlapping snippets were merged by RVA. Where two snippets overlapped, their bytes agreed; no conflicting byte reconstruction was observed in the inventory target windows used below.

This is **partial reconstruction**, not a complete dump. Missing bytes remain unknown and no function boundary is promoted from gaps alone.

## Confirmed instruction shapes

### INVWIN_A — RVA 0x0070135A literal

Reconstructed aligned sequence:

```asm
0x0070134E  mov dword ptr [ebp-04], 0
0x00701355  mov eax, [ebp-10]
0x00701358  mov dword ptr [eax], 0x012DE180
0x0070135E  mov ecx, [ebp-10]
0x00701361  mov dword ptr [ecx+0x178], 0
0x0070136B  mov edx, [ebp-10]
...          next member initialization begins at +0x17C
```

Facts:

```text
THIS_SLOT=[EBP-0x10]
INVWIN_VTABLE_WRITE=YES
INVWIN+0x178_ZERO_INIT=YES
INVWIN+0x17C_INIT_BEGINS=YES
```

### GRID_A — RVA 0x007013DA literal

```asm
0x007013CE  mov dword ptr [ebp-04], 0
0x007013D5  mov eax, [ebp-10]
0x007013D8  mov dword ptr [eax], 0x012DDE38
0x007013DE  mov ecx, [ebp-10]
0x007013E1  mov dword ptr [ecx+0x1D8], 0
0x007013EB  mov edx, [ebp-10]
...          next member initialization begins at +0x1DC
```

This independently supports the previously inferred dense GRID bookkeeping/collection cluster beginning at `+0x1D8`.

### ROOT_A — RVA 0x0070184E literal

```asm
0x00701842  mov dword ptr [ebp-04], 0
0x00701849  mov ecx, [ebp-10]
0x0070184C  mov dword ptr [ecx], 0x012DE2F8
0x00701852  mov edx, [ebp-10]
0x00701855  mov dword ptr [edx+0x168], 0
0x0070185F  mov ecx, [ebp-10]
0x00701862  add ecx, 0x16C
0x00701868  call ...
```

Facts:

```text
ROOT+0x168_POINTER_OR_OWNED_SLOT_ZERO_INIT=YES
ROOT+0x16C_EMBEDDED_SUBOBJECT_INIT=YES
```

This aligns with the previously proven live graph `ROOT+0x168 -> INVWIN`.

## B-side teardown-shaped paths

### INVWIN_B

```asm
0x00701C23  mov [ebp-10], ecx
0x00701C26  mov eax, [ebp-10]
0x00701C29  mov dword ptr [eax], 0x012DE180
0x00701C2F  mov dword ptr [ebp-04], 0
0x00701C36  mov dword ptr [ebp-04], -1
0x00701C3D  mov ecx, [ebp-10]
0x00701C40  call 0x00854340
```

### GRID_B

```asm
0x00701C83  mov [ebp-10], ecx
0x00701C86  mov eax, [ebp-10]
0x00701C89  mov dword ptr [eax], 0x012DDE38
0x00701C8F  mov dword ptr [ebp-04], 0
0x00701C96  mov dword ptr [ebp-04], -1
0x00701C9D  mov ecx, [ebp-10]
0x00701CA0  call 0x008AE440
```

These two paths have nearly identical local shapes and are separated by `0x60` bytes at the vtable literals. This is more consistent with adjacent small destructor/cleanup wrappers than with assuming one shared vtable-transition function.

Classification:

```text
INVWIN_B=TEARDOWN_SHAPED_FUNCTION_CANDIDATE
GRID_B=TEARDOWN_SHAPED_FUNCTION_CANDIDATE
SAME_FUNCTION_ASSUMPTION=REJECTED_UNTIL_V4_BOUNDARY_PROOF
```

### ROOT_B

```asm
0x00701E63  mov [ebp-10], ecx
0x00701E66  mov eax, [ebp-10]
0x00701E69  mov dword ptr [eax], 0x012DE2F8
0x00701E6F  mov dword ptr [ebp-04], 2
0x00701E76  mov dword ptr [0x016BCEE8], 0
0x00701E80  mov byte ptr [ebp-04], 1
0x00701E84  mov ecx, [ebp-10]
...
```

Module base was `0x00400000`, therefore:

```text
GLOBAL_VA =0x016BCEE8
GLOBAL_RVA=0x012BCEE8
```

Among the reconstructed inventory target snippets, this is the only complete `C7 05 <abs32> 00000000` module-global zero-store recovered.

## ROOT-global inference

The existing runtime graph is:

```text
ROOT
  +0x15C -> GRID
  +0x168 -> INVWIN
```

The B-side local code shows:

```text
INVWIN_B: vtable write -> cleanup call
GRID_B:   vtable write -> cleanup call
ROOT_B:   vtable write -> clear module-global -> further cleanup
```

That asymmetry is consistent with ROOT being the higher-level/singleton-owned UI object while GRID and INVWIN are subordinate objects.

Current classification:

```text
GLOBAL_RVA_0x012BCEE8=HIGH_PRIORITY_ROOT_SINGLETON_OR_OWNER_CANDIDATE
TEARDOWN_CLEAR=PROVEN
CONSTRUCTION_ASSIGNMENT=NOT_YET_PROVEN
FORMAL_OWNER_PROMOTION=NO
```

V4b is the authoritative next check because it scans the full loaded Lin.bin2 image for all absolute references to this one global without reading MEM_PRIVATE/heap.

## Important V3 CALL-list correction

V3's nearby-call finder treated any byte `E8` as a call opcode without instruction-boundary decoding.

Concrete false positive:

```text
V3 reported:
CALL RVA=0x00701E78 -> 0x00718A4B
```

But reconstructed aligned code is:

```asm
0x00701E76  C7 05 E8 CE 6B 01 00 00 00 00
            ^     ^^^^^^^^^^^
            opcode absolute address = 0x016BCEE8
```

The byte at `0x00701E78` is the first byte (`E8`) of the absolute address immediate, **not a CALL instruction**.

Therefore:

```text
V3_NEARBY_CALL_COUNTS=RETIRED_FOR_RANKING
V3_CALL_TARGETS=DO_NOT_PROMOTE_WITHOUT_ALIGNED_DECODING
```

V4 exact inbound-call matching is still useful because its target must resolve exactly to a candidate function start, but function-boundary and instruction-context evidence must remain part of the gate.

## A-side relationship correction

A-side literal spacing:

```text
INVWIN_A=0x0070135A
GRID_A  =0x007013DA   delta=0x80
ROOT_A  =0x0070184E
```

B-side literal spacing:

```text
INVWIN_B=0x00701C2B
GRID_B  =0x00701C8B   delta=0x60
ROOT_B  =0x00701E6B
```

Both INVWIN and GRID paths independently use `[ebp-0x10]` as `this` and independently perform their own vtable write/state setup. This makes adjacent independent ctor/dtor functions at least as plausible as a vtable-transition/inheritance interpretation.

Rule:

```text
NO_INHERITANCE_INFERENCE_FROM_XREF_PROXIMITY=YES
WAIT_FOR=V4 function boundary + V4c RTTI/vtable evidence
```

## Next prepared tools

```text
V4  = run_850_inventory_ctor_owner_trace_v4.ps1
V4b = run_850_inventory_root_global_xref_v4b.ps1
V4c = run_850_inventory_vtable_rtti_v4c.ps1
```

All remain read-only and module-image scoped.

```text
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
```
