# 850 Inventory INVWIN +0xF4 Collection Candidate

Date: 2026-09-24
Branch: `work/850-inventory-helper`

## Frozen authority

- Client: `I:\8.50c客服端\Lin.bin2`
- SHA256: `FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4`
- `ROOT_GLOBAL_RVA=0x012BCEE8` is restart-stable across three distinct processes.
- `ROOT+0x168 -> INVWIN` and INVWIN vtable `RVA=0x00EDE180` are frozen owner-graph evidence.

## V8/V8b decisions

`INVWIN+0x220` is retired as a direct backing hypothesis:

```text
OFFSET_0x220_ACCESS_COUNT=0
OFFSET_0x220_THISCALL_COUNT=0
OFFSET_0x220_CODE_RELEVANT=NO
```

The only V8b direct focus write in `0x180..0x280` was `INVWIN+0x180`, slot 17, instruction RVA `0x007023A8`.

Slot 17 proves `+0x180` belongs to a parallel initialization family with `+0x178/+0x17C/+0x180`. The method stores `ECX(this)` in a stack local, resolves three resource/config-like values through helper calls, stores the returned values into those three fields, then calls another initializer. Therefore `+0x180` is not promoted as inventory storage.

## New high-priority candidate: embedded subobject at INVWIN+0xF4

V8 raw caller evidence repeatedly contains:

```asm
mov ecx, <saved this>
add ecx, 0xF4
call <helper>
```

This pattern occurs across at least 23 INVWIN vtable slots.

Two helpers have especially collection-like caller semantics:

```text
runtime VA 0x008CE990 / RVA 0x004CE990
- return value is repeatedly compared against an integer index/loop counter
- treated as a size/count candidate

runtime VA 0x008CC180 / RVA 0x004CC180
- called with an index argument
- returned address/value is dereferenced by callers
- treated as an indexed-element accessor candidate
```

Additional helpers called with `ECX=this+0xF4` include runtime VAs:

```text
0x00C80E20
0x00C81680
0x00C828F0
0x00C82800
0x00C54820
0x00C550E0
```

These are candidates for find/insert/remove/iterator or related operations. Semantics are not yet proven.

## Safety / promotion rules

```text
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
BROAD_VECTOR_SCAN=NO
MEMORY_WRITE=NO
COLLECTION_LAYOUT_PROVEN=NO
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

Do not infer STL/vector layout from call shape alone.

## Next

Run V9 bounded helper capture/decoder. V9 may read only the exact eight known helper code windows from executable `MEM_IMAGE`, decode their owner-relative accesses, and then define fixed-offset validation inside the `INVWIN+0xF4` subobject. No heap scan is allowed.
