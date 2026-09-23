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

These relations are runtime evidence already established before this stage. They are UI ownership evidence only; they do not prove the backing inventory collection or item record layout.

## Static XREF V2 — completed

Tool:

```text
launcher/850Launcher/tools/run_850_inventory_static_xref_v2.ps1
```

Authoritative result:

```text
STATUS=PASS_STATIC_ONLY_V2
VTABLE_LITERAL_XREF_TOTAL=0
GLOBAL_MEMBER_REF_TOTAL=0
RUNTIME_ATTACH=NO
MEMORY_WRITE=NO
```

All three known inventory UI vtable literals returned zero executable-file hits:

```text
GRID   RVA=0x00EDDE38 -> 0 hits
ROOT   RVA=0x00EDE2F8 -> 0 hits
INVWIN RVA=0x00EDE180 -> 0 hits
```

All targeted raw displacement searches, including the proven graph offsets `+0xEC`, `+0x15C`, and `+0x168`, also returned zero hits.

Interpretation:

```text
V2_RESULT=VALID_NEGATIVE_EVIDENCE
DISK_STATIC_RAW_XREF_PATH=EXHAUSTED
REASON=Lin.bin2 packed/virtual layout does not expose the relevant runtime code through the mapped raw-file executable bytes used by V2
```

Do not repeat or widen raw-file literal/displacement scans expecting the inventory owner to appear.

## Runtime module XREF V3 — next gate

Tool:

```text
launcher/850Launcher/tools/run_850_inventory_runtime_module_xref_v3.ps1
```

V3 is deliberately narrower than the rejected runtime inventory scans. It reads only executable `MEM_IMAGE` pages inside the authoritative `Lin.bin2` module after the client has loaded.

Hard boundaries:

```text
OpenProcess=QUERY_INFORMATION|VM_READ
RUNTIME_ATTACH=READ_ONLY_MODULE_IMAGE
SCAN_SCOPE=LIN.BIN2_EXECUTABLE_MEM_IMAGE_ONLY
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
VECTOR_SCAN=NO
MEMORY_WRITE=NO
```

Purpose:

1. Search the runtime/unpacked executable image for references to the three proven inventory UI vtables.
2. Search the same executable image for the proven member offsets `+0xEC`, `+0x15C`, `+0x168` and the candidate `+0x1E0..+0x230` neighborhood.
3. Correlate vtable references, member references, and nearby direct `E8` calls.
4. Rank only owner windows that have real runtime code correlation.

Promotion rule:

```text
NO_WP5_PROMOTION_FROM_SINGLE_HIT
NO_OWNER_PROMOTION_WITHOUT_CORRELATION
NO_MODEL_POINTER_PROMOTION_WITHOUT_NARROW_RESTART_STABLE_READ
```

If V3 yields a strong owner window, the next step is one narrowly targeted read-only validation derived from that exact runtime code path. If V3 yields no useful correlation, do not fall back to broad heap/vector scans; refine the runtime module call/xref path instead.

## Current gate

```text
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
ITEM_USE_BRIDGE=UNMAPPED
MEMORY_WRITE=NO
NEXT=RUN_RUNTIME_MODULE_XREF_V3
```
