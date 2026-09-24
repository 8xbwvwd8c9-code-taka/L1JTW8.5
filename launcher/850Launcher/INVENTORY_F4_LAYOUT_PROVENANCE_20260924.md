# 850 Inventory INVWIN+0xF4 Layout Provenance — 2026-09-24

## Authority

- Client SHA256: `FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4`
- ROOT owner anchor: restart-stable, discovery closed.
- Runtime policy: exact fixed/module reads only; no heap scan, no MEM_PRIVATE scan, no memory write.

## Retired hypotheses

- `INVWIN+0x220`: retired. V8/V8b found no code-level access or thiscall relevance.
- `INVWIN+0x180`: one write in slot 17, classified as initialization/resource state rather than backing collection.
- GRID B triple `+0x1E4/+0x1E8/+0x1EC`: shape candidate only; controlled stack-count and record-add deltas were zero.

## Current strongest candidate

`INVWIN+0xF4` is a heavily used embedded subobject. Many INVWIN vtable methods pass `ECX=this+0xF4` into helper families that perform size/index/iteration-like operations.

### V9

`SIZE` wrapper RVA `0x004CE990`:

- calls `0x004CD8D0`
- calls `0x004CD890`
- computes pointer difference and arithmetic shift right by 2

This is consistent with `(END-BEGIN)/4`.

`INDEX` wrapper RVA `0x004CC180`:

- calls `0x004CD870`
- dereferences returned pointer to obtain base
- computes `base + index*4`

This establishes a 4-byte element stride at the wrapper level but does not yet prove inventory semantics.

### V10

Accessor wrappers:

- `0x004CD870` BEGIN_MUT -> calls `0x004CD670`
- `0x004CD890` BEGIN_CONST -> calls `0x004CD690`
- `0x004CD8B0` END_MUT -> calls `0x004CD670`, then `EAX += 4`
- `0x004CD8D0` END_CONST -> calls `0x004CD690`, then `EAX += 4`

Therefore BEGIN and END are proven to be adjacent 4-byte field addresses relative to whatever base address is returned by `0x004CD670/0x004CD690`.

## V11 gate

Only decode:

- `BASE_MUT RVA=0x004CD670`
- `BASE_CONST RVA=0x004CD690`

Success condition:

```text
BASE_ACCESSORS_AGREE=YES
BASE_FIELD_ADDRESS_OFFSET=X
BEGIN_FIELD_OFFSET=X
END_FIELD_OFFSET=X+4
STATUS=PASS_F4_BEGIN_END_LAYOUT_PROVEN_STATIC
```

If V11 passes, the next runtime proof is restricted to fixed reads of:

```text
INVWIN + 0xF4 + BEGIN_FIELD_OFFSET
INVWIN + 0xF4 + END_FIELD_OFFSET
```

Controlled semantic requirement before collection promotion:

- distinct record add -> `(END-BEGIN)` changes by `+4`
- matching record remove -> `(END-BEGIN)` changes by `-4`
- same process / same inventory session for paired comparison

## Current status

```text
F4_SUBOBJECT_CODE_RELEVANT=YES
ELEMENT_STRIDE_FROM_WRAPPERS=4
BEGIN_END_ADJACENCY=PROVEN_STATIC
BASE_FIELD_OFFSET=NOT_YET
COLLECTION_RUNTIME_DELTA_PROVEN=NO
COLLECTION_LAYOUT_PROVEN=NO
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
```
