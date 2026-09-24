# 850 Inventory ROOT Owner Anchor Proof — 2026-09-24

## Authority

```text
CLIENT=I:\8.50c客服端\Lin.bin2
SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
ROOT_GLOBAL_RVA=0x012BCEE8
```

## Status

```text
STATIC_ROOT_OWNER_PROVENANCE=PASS
STATIC_ROOT_LIFECYCLE=PASS
ROOT_OWNER_ANCHOR=PROVEN_STATIC
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

`FORMAL_WP5` is intentionally not promoted. WP5 still requires real inventory item-record semantics (`ObjectId`, `ItemId`, `Count`, etc.) plus controlled inventory-change/relog proof.

## Decoder-proven lifecycle writers

### Teardown clear

```asm
RVA 0x00701E69  mov dword ptr [eax], 0x012DE2F8 ; ROOT vtable
RVA 0x00701E76  mov dword ptr [0x016BCEE8], 0
```

```text
ZERO_WRITER_RVA=0x00701E76
ZERO_POST_WRITE_EIP_RVA=0x00701E80
CLASS=ZERO_CLEAR
```

### Construction/assignment side

Decoder-proven chain at the global assignment:

```asm
RVA 0x00C9A1D0  mov [ebp-0x3B8], eax
RVA 0x00C9A1DD  mov ecx, [ebp-0x3B8]
RVA 0x00C9A1E3  mov [0x016BCEE8], ecx
RVA 0x00C9A1EC  mov eax, [0x016BCEE8]
RVA 0x00C9A1F1  mov [edx+0x2C], eax
```

V5 proved exactly one write to `[EBP-0x3B8]` before the target load:

```text
STACK_SLOT=[EBP-0x3B8]
UNIQUE_WRITER_RVA=0x00C9A1D0
SOURCE=EAX
```

The immediate source is:

```asm
RVA 0x00C9A1CA  mov eax, [ebp-0x1EC]
RVA 0x00C9A1D0  mov [ebp-0x3B8], eax
```

## Constructor provenance

Inspection of the authoritative V5 module-image window proves the value in `[EBP-0x1EC]` is the return value of the known ROOT constructor:

```asm
RVA 0x00C9A186  push 0x18C
RVA 0x00C9A18B  call 0x00D8436B       ; allocation helper
RVA 0x00C9A193  mov [ebp-0x1E8], eax
RVA 0x00C9A1A0  cmp [ebp-0x1E8], 0
RVA 0x00C9A1A7  je  0x00C9A1C0
RVA 0x00C9A1A9  mov edx, [ebp+0x08]
RVA 0x00C9A1AC  push edx
RVA 0x00C9A1AD  mov ecx, [ebp-0x1E8]
RVA 0x00C9A1B3  call 0x00701810       ; ROOT constructor
RVA 0x00C9A1B8  mov [ebp-0x1EC], eax
RVA 0x00C9A1BE  jmp 0x00C9A1CA
RVA 0x00C9A1C0  mov [ebp-0x1EC], 0
RVA 0x00C9A1CA  mov eax, [ebp-0x1EC]
RVA 0x00C9A1D0  mov [ebp-0x3B8], eax
RVA 0x00C9A1DD  mov ecx, [ebp-0x3B8]
RVA 0x00C9A1E3  mov [ROOT_GLOBAL], ecx
```

Therefore:

```text
allocation(0x18C)
 -> object pointer in ECX
 -> ROOT constructor RVA 0x00701810
 -> constructor return EAX
 -> [EBP-0x1EC]
 -> [EBP-0x3B8]
 -> ECX
 -> module+0x012BCEE8
```

Known aligned ROOT constructor evidence at RVA `0x00701810` includes:

```asm
RVA 0x0070184C  mov [ecx], 0x012DE2F8
RVA 0x00701855  mov [edx+0x168], 0
RVA 0x00701862  add ecx, 0x16C
```

This closes the static provenance gap: the global receives the return value of the same constructor that installs the known ROOT vtable.

## Existing live UI graph

Previously proven live relations:

```text
GRID +0xEC  -> ROOT
ROOT +0x15C -> GRID
ROOT +0x168 -> INVWIN
```

Known vtables:

```text
ROOT_VTABLE_RVA=0x00EDE2F8
GRID_VTABLE_RVA=0x00EDDE38
INVWIN_VTABLE_RVA=0x00EDE180
```

## V6 runtime confirmation

The next gate is intentionally narrow:

```text
module+0x012BCEE8 -> ROOT
ROOT[0]           -> ROOT vtable
ROOT+0x15C        -> GRID
GRID[0]           -> GRID vtable
GRID+0xEC         -> ROOT
ROOT+0x168        -> INVWIN
INVWIN[0]         -> INVWIN vtable
```

Tool:

```text
launcher/850Launcher/tools/run_850_inventory_root_graph_v6.ps1
```

It reads only exact dwords derived from the proven anchor/offsets.

```text
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_SCAN=NO
EXACT_TARGET_DEREFERENCE=YES
MEMORY_WRITE=NO
```

If V6 passes in the current process, repeat after one full `Lin.bin2` restart. After the fresh-process repeat, freeze `ROOT_GLOBAL_RVA=0x012BCEE8` as the runtime ROOT owner anchor and proceed only to bounded collection-offset investigation.
