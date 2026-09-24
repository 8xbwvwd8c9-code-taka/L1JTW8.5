# 850 Inventory INVWIN <0x180 agent semantic trace — 2026-09-24

## Evidence status

```text
SOURCE=AGENT_REPORTED
MAINLINE_INDEPENDENT_VERIFICATION=NOT_YET
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

This file preserves the independent agent result supplied back to the mainline. Treat the detailed class/semantic labels below as high-confidence supporting evidence until independently reproduced by the mainline decoder lane.

## Agent result

```text
STATUS=NO_PROMOTABLE_MODEL_OWNER_IN_V8_INVWIN_LT_0x180
CANDIDATE_OFFSETS=NONE
RETIRED_OFFSETS=0x0AC,0x0B0,0x0B8,0x0E8,0x0EC,0x0F0,0x118,0x160,0x178,0x17C
ITEM_RECORD_TRAVERSAL_PROVEN=NO
OBJECT_ID_SEMANTICS_PROVEN=NO
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
```

## Best observed lifecycle field: 0x0F0

Agent evidence:

- `METHOD_RVA=0x0086E710 / INSN_RVA=0x0086E7B4`: field receives an element originating from the already-retired `INVWIN+0x0F4` control collection.
- `METHOD_RVA=0x0086E890 / INSN_RVA=0x0086E8F0`: calls element virtual slot 1 while forwarding four UI-event parameters.
- `METHOD_RVA=0x00873C70 / INSN_RVA=0x00873DCF`: clears the field to zero.

Agent classification: current UI-control cache, not item/data owner.

## Other retired fields

- `0x0E8`: `METHOD_RVA=0x00869700 / INSN_RVA=0x00869733` calls virtual slot 56 for coordinate state, then uses `+0x94/+0x98`; classified as UI position/control semantics.
- `0x0EC` and `0x118`: both participate in helper `RVA=0x008BD120` from `METHOD_RVA=0x00873C70 / INSN_RVA=0x00873DC7`, proving cleanup/association only.
- `0x118`: additionally calls helper `RVA=0x0085E7B0` from `METHOD_RVA=0x0085E950 / INSN_RVA=0x0085E96C`; no find/count/index/insert/remove or item traversal semantics were observed.
- `0x0AC/0x0B0/0x0B8`: event callback function pointers.
- `0x160`: numeric value returned by a UI child method.
- `0x178/0x17C`: controls instantiated from resource names.

## Mainline adjudication

```text
INVWIN_LT_0x180_FIXED_FIELD_MODEL_OWNER=NOT_PROVEN
REOPEN_RETIRED_OFFSETS=NO
NEXT=Follow only direct helper calls in V8 methods that receive INVWIN or return object-like values. Capture helper bodies at depth 1 from the authoritative client image. Promote only find/lookup/count/index/insert/remove/item-record traversal semantics.
HEAP_SCAN=NO
MEM_PRIVATE_SCAN=NO
MEMORY_WRITE=NO
```
