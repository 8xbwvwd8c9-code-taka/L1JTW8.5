# 850 Inventory INVWIN V8 Adjudication — 2026-09-24

## Authority
- Client: `I:\8.50c客服端\Lin.bin2`
- SHA256: `FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4`
- `INVWIN_VTABLE_RVA=0x00EDE180`
- V8 captured 64 vtable slots / 64 unique methods from read-only `MEM_IMAGE`.
- `HEAP_SCAN=NO`, `MEM_PRIVATE_SCAN=NO`, `MEMORY_WRITE=NO`.

## V8 decision
The prior runtime hypothesis `INVWIN+0x220` is no longer promotable as a direct inventory-backing candidate.

V8 code-level evidence:
- `OFFSET_0x220_ACCESS_COUNT=0`
- `OFFSET_0x220_THISCALL_COUNT=0`
- `OFFSET_0x220_CODE_RELEVANT=NO`
- direct focus range `0x180..0x280`: none in the first-pass reachable linear method bodies.

Therefore:
- `INVWIN+0x220=DIRECT_BACKING_HYPOTHESIS_RETIRED`
- the earlier single-run 28x64-looking buffer remains historical evidence only and must not be restored to the formal layout without new code-level proof.
- `FORMAL_WP5=NOT_YET`
- `FORMAL_WP6=NOT_YET`

## Remaining V8 blocker
V8 identified 29 direct branch/thunk continuations. Those continuations must be resolved before the entire `0x180..0x280` range can be retired for code semantics.

Next authoritative step: V8b bounded CFG over the existing 576-byte method blobs only.

V8b rules:
- no new runtime target attachment,
- no heap or `MEM_PRIVATE`,
- no object scan,
- follow only reachable direct branches inside already captured method blobs,
- preserve `this` / stack-local alias state,
- do not count `LEA` as a memory read,
- unconditional `JMP` has no linear fallthrough,
- cap CFG state explosion conservatively.

Expected output:
`I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_invwin_cfg_v8b.txt`

If V8b still finds no code-relevant offsets in `0x180..0x280`, retire that whole range as direct INVWIN backing and pivot only to code-proven pointer fields below `0x180`.
