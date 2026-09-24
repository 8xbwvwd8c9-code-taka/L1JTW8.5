# 850 Inventory INVWIN V16 Static Capture Adjudication — 2026-09-24

## Decision

`V16` helper discovery remains useful, but the **on-disk helper-body capture lane is retired**.

Reason: helper RVAs discovered from the authoritative runtime V8 method blobs are valid, but mapping those RVAs back into the raw `Lin.bin2` file does not yield the same executable bytes that are present in the loaded `MEM_IMAGE`.

## Evidence

Known runtime helpers already proven earlier:

- `RVA 0x004CC180` = stride-4 index accessor
- `RVA 0x004CE990` = vector size accessor

V16 raw-file capture at those exact RVAs produced implausible instruction streams containing patterns such as `lcall`, `int1`, `retf`, `out`, and unrelated FPU/control-flow instructions rather than the previously proven runtime helper bodies.

In addition, many helper RVAs in the loaded main module (`0x0085xxxx..0x0088xxxx`) were not mappable to usable raw-file bytes despite being executable runtime targets.

Therefore:

```text
V16_HELPER_DISCOVERY=KEPT
V16_ON_DISK_BODY_CAPTURE=RETIRED
ON_DISK_BYTES_EQ_RUNTIME_MEM_IMAGE=NOT_PROVEN
STATIC_HELPER_SEMANTIC_RESULTS=INVALID_FOR_PROMOTION
STRONG_DATA_HELPERS=NOT_ESTABLISHED
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

## Replacement lane: V16R

V16R may read only the exact helper RVAs already enumerated by V16 from the authoritative running `Lin.bin2` main module.

Safety gates:

```text
SOURCE=RUNTIME_MEM_IMAGE_EXACT_TARGETS
EXACT_TARGET_ONLY=YES
HELPER_DEPTH=1
CAPTURE_BYTES_PER_TARGET=384
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
```

Each target must be:

1. inside the authoritative main module,
2. inside committed executable `MEM_IMAGE`,
3. read with `PROCESS_QUERY_INFORMATION | PROCESS_VM_READ` only.

Runtime sanity anchors before accepting any semantic analysis:

- `0x004CC180` must classify as `INDEX_ACCESSOR` by calling `0x004CD870` and showing scale-4 index arithmetic.
- `0x004CE990` must classify as `SIZE_ACCESSOR` by calling `0x004CD8D0` and `0x004CD890` and dividing the begin/end byte distance by four.

If either anchor fails, V16R is blocked and no helper semantic promotion is allowed.

## Next

Run `run_850_inventory_invwin_runtime_helpers_v16r.ps1` and classify only the exact captured helper bodies. Do not reopen retired INVWIN offsets, ROOT discovery, GRID B, or `INVWIN+0xF4`.
