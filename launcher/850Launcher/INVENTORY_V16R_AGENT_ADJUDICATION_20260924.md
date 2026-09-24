# INVENTORY V16R Agent Adjudication — 2026-09-24

## Accepted agent result

The V16R semantic agent validated all 16 exact runtime targets and returned:

- `STATUS=PARTIAL_RUNTIME_HELPER_SEMANTICS`
- 13 helpers retired as UI, lifecycle, generic-container, or accessor semantics.
- No promotable inventory/item-model helper.
- `0x0085E7B0` proves fixed-stride record traversal only; no ObjectId, ItemId, stack Count, lookup, insert/remove, or item-specific consumer.
- Three external thunk targets remain unresolved: `0x00D86014`, `0x00D86092`, `0x00D860B6`.
- `FORMAL_WP5=NOT_YET`, `FORMAL_WP6=NOT_YET`.

## Mainline correction

The V16R target `0x00869AF0` is one function and is correctly retired as a bounds-checked accessor over the already-retired `this+0xF4` UI/control container.

However, the same 384-byte capture also contains a **separate adjacent function starting at `0x00869B30`**. It must not be conflated with `0x00869AF0`.

`0x00869B30` independently operates on `this+0x16C` and uses:

- `0x0084EFF0`
- `0x00880E80`
- `0x00854E30`
- `0x008816E0`
- `0x008547A0`
- `0x00854AA0`

The control flow includes an iterator-style loop and compares the caller argument with a loop index before dereferencing the selected element. This lane remains unresolved and is the only retained follow-up from this capture.

## Thunk-lane decision

The three unresolved external/import thunk destinations are not the next mainline target. They are lower-value for inventory-owner discovery because their captured local RVAs are import/runtime stubs and no item/model affinity has been established.

They remain recorded as unresolved but are not promoted.

## V17 decision

Proceed only with bounded runtime `MEM_IMAGE` capture of:

- sanity anchors: `0x004CC180`, `0x004CE990`
- `0x00869B30`
- `0x0084EFF0`
- `0x00880E80`
- `0x008816E0`
- `0x00854E30`
- `0x008547A0`
- `0x00854AA0`
- `0x00854A50` (required direct landing for the dereference wrapper)

Safety remains:

```text
EXACT_TARGET_ONLY=YES
HELPER_DEPTH=1
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
```

No retired ROOT, GRID B, `INVWIN+0xF4`, or retired `<0x180` owner lane may be reopened.

## Current gates

```text
V16R_AGENT_CLASSIFICATION=ACCEPTED
V16R_PROMOTABLE_HELPERS=NONE
V16R_EXTERNAL_THUNKS=UNRESOLVED_NOT_PROMOTED
INVWIN_0x16C_LANE=OPEN
ITEM_RECORD_TRAVERSAL_PROVEN=NO
OBJECT_ID_SEMANTICS_PROVEN=NO
ITEM_ID_SEMANTICS_PROVEN=NO
COUNT_SEMANTICS_PROVEN=NO
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```
