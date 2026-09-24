# 850 Inventory INVWIN+0xF4 Collection Status — 2026-09-24

## Authority

- Client: `I:\8.50c客服端\Lin.bin2`
- SHA256: `FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4`
- ROOT owner anchor: restart-stable and frozen.
- Runtime work remains read-only.

## Retired hypotheses

- `INVWIN+0x220`: retired. V8/V8b found no direct code relevance.
- `INVWIN+0x180`: one initializer write in slot 17; classified as UI/resource/config initialization, not inventory backing storage.
- GRID B triple `+0x1E4/+0x1E8/+0x1EC`: structural candidate only; controlled `stack_count` and `record_add` produced zero deltas, therefore not promoted.

## Current highest-priority candidate

`INVWIN+0xF4` is an embedded subobject used by many INVWIN methods. Callers repeatedly pass `ECX = this + 0xF4` into helper functions with collection-like semantics.

### V9 primary helper evidence

`SIZE_CANDIDATE RVA 0x004CE990`:

```asm
call 0x004CD8D0
mov  esi,eax
call 0x004CD890
mov  ecx,[esi]
sub  ecx,[eax]
sar  ecx,2
mov  eax,ecx
ret
```

This computes `(*A - *B) / 4`.

`INDEX_CANDIDATE RVA 0x004CC180`:

```asm
call 0x004CD870
mov  eax,[eax]
mov  ecx,[ebp+8]
lea  eax,[eax+ecx*4]
ret  4
```

This computes `*A + index*4`.

The four tightly spaced helper RVAs are therefore the next exact targets:

- `0x004CD870` — BEGIN non-const candidate
- `0x004CD890` — BEGIN const candidate
- `0x004CD8B0` — END non-const candidate
- `0x004CD8D0` — END const candidate

The names are hypotheses until V10 proves the returned owner-relative field address.

## V10 gate

Files:

- `tools/run_850_inventory_invwin_f4_accessors_v10.ps1`
- `tools/decoder/decode_850_inventory_invwin_f4_accessors_v10.py`

V10 reads only the four known accessors, maximum 96 bytes each, committed executable `MEM_IMAGE` only.

Promotion criteria:

1. `BEGIN_MUT` and `BEGIN_CONST` both return the address of the same owner-relative field.
2. `END_MUT` and `END_CONST` both return the address of the same owner-relative field.
3. begin and end fields are distinct.
4. No heap scan, no MEM_PRIVATE scan, no memory write.

If all pass:

```text
STATUS=PASS_F4_BEGIN_END_ACCESSORS_PROVEN
ELEMENT_STRIDE_FROM_INDEX_HELPER=4
SIZE_FORMULA=(END-BEGIN)/4
```

This is still not FORMAL_WP5. Runtime controlled add/remove validation is required before promotion to a stable inventory collection layout.

## Current state

```text
INVWIN_F4_CODE_RELEVANT=YES
V9=PARTIAL_F4_HELPER_SEMANTICS
F4_COLLECTION_SHAPE=STRONGLY_SUPPORTED
BEGIN_END_FIELD_LAYOUT=NOT_YET
COLLECTION_LAYOUT_PROVEN=NO
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
```
