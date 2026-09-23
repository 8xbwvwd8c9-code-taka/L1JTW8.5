# 850 Inventory Static XREF v2 — 2026-09-24

```text
BRANCH=work/850-inventory-helper
BASE_HEAD=bbadbe901dba555c78ad915468e586a61659a46b
V2_COMMIT=61bff35e2603a01563a8fae75a7632b920881660
CLIENT=I:\8.50c客服端\Lin.bin2
CLIENT_SHA256=FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4
WP5=NOT_YET
WP6=NOT_YET
RUNTIME_ATTACH=NO
MEMORY_WRITE=NO
```

## Why v2 exists

The previous `run_850_inventory_static_model_xref.ps1` pass was intentionally conservative but had two important limits:

1. the proven `InventoryItemGrid +0xEC -> Root` edge was not included in `InterestingOffsets`;
2. it searched raw 4-byte displacement literals rather than decoding common x86 ModRM memory operands, so it could not reliably correlate real member accesses with inventory owner code.

No previous runtime vector candidate is promoted by this change.

## New tool

```text
launcher/850Launcher/tools/run_850_inventory_static_xref_v2.ps1
```

The v2 pass is file-only and performs no process attach. It adds:

- authoritative SHA-256 gate for `Lin.bin2`;
- PE32/x86 section parsing;
- executable-section literal XREF search for the known Grid / Root / InvWin vtable VAs;
- probable containing-function hints around each vtable literal XREF;
- decoding of common x86 ModRM member accesses for the proven/candidate offsets;
- explicit `+0xEC`, `+0x15C`, `+0x168` correlation;
- nearby relative `CALL` target extraction;
- global member-reference counts grouped by displacement;
- ranking hints without auto-promoting any mapping.

## Run

```powershell
cd I:\L1JTW8.5
pwsh -File .\launcher\850Launcher\tools\run_850_inventory_static_xref_v2.ps1
```

Expected output:

```text
I:\L共通工具\LineageAIResourceToolkit\outputs\850_inventory_static_xref_v2.txt
```

Expected terminal gate:

```text
STATUS=PASS_STATIC_ONLY_V2
RUNTIME_ATTACH=NO
MEMORY_WRITE=NO
```

## Interpretation gate

Known UI graph:

```text
GRID +0xEC  -> ROOT
ROOT +0x15C -> GRID
ROOT +0x168 -> INVWIN
```

Rank a static owner/function window high only when it combines a Grid/Root/InvWin vtable literal XREF with two or more of the proven graph offsets. Rank it medium when it combines one proven graph offset with repeated candidate model/vector offsets in `0x1E0..0x230`.

Do **not** promote a model pointer, vector offset, record stride, ObjectId, ItemId, Count, Enchant, Equipped, WP5, or WP6 from a single static hit.

## Next step after report

Use the report to select the strongest owner/model pointer candidate first. Only after that candidate is identified may one narrowly targeted read-only live check be used. Do not resume broad private-memory scans or the earlier crashing vector scan path.
