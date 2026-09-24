# INVWIN child vtable semantic A2A — 2026-09-24

## GOAL
Classify the bounded INVWIN child objects captured by V13 and determine which child class is most likely to own or expose the true inventory item/model collection.

## MUST
- English only.
- SUBAGENTS=0.
- NO_REPO_WIDE_SCAN.
- Authority is only current `I:\8.50c客服端\Lin.bin2` SHA256 `FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4`.
- Use V13 as the starting authority:
  - `INVWIN+0x148` object vtable `0x012F61F8`.
  - `INVWIN+0x14C` object vtable `0x012F5510`.
  - `INVWIN+0x150` object vtable `0x012F5510`.
  - `INVWIN+0x154` is NULL in the captured process.
- Treat `+0x14C` and `+0x150` as two instances of the same runtime class unless code proves otherwise.
- Enumerate only the first 16 V13-captured vtable slots for vtables `0x012F61F8` and `0x012F5510`.
- Disassemble each unique method with Capstone x86/32.
- Follow direct local helper calls to depth <= 1 only.
- Record per method:
  - vtable / slot / method RVA
  - owner-relative reads/writes
  - immediate helper calls
  - cardinality/index/find/insert/remove/clear/update/render/input/destructor semantics when code supports them
  - any evidence of item-record pointer traversal or stable object identity
- Prioritize semantic separation between:
  - inventory/model/data ownership
  - UI widget/render/input behavior
  - selection/bookkeeping
  - destructor/lifecycle only
- Reuse existing static evidence where relevant, but do not reopen ROOT discovery.

## DO NOT
- Do not perform heap scans, MEM_PRIVATE scans, broad vector scans, pointer sweeps, or memory writes.
- Do not use donor-client addresses as authority.
- Do not infer inventory semantics from vtable shape alone.
- Do not reopen retired candidates:
  - GRID `+0x1E4/+0x1E8/+0x1EC` is closed as `ui_bookkeeping`.
  - INVWIN `+0xF4` is retired as the inventory-record collection after controlled `record_add` produced zero cardinality delta.
- Do not dereference arbitrary runtime pointers beyond exact child objects or exact code-proven fields.

## VALIDATE
Report a PASS only if at least one child class has code-level semantics materially closer to inventory/model ownership than generic UI behavior.
If neither class qualifies, report a negative result and the strongest code-proven next pointer/helper field to inspect.
No claim may exceed the evidence level.

## FINAL
Write exactly one report:
`launcher/850Launcher/INVENTORY_INVWIN_CHILD_VTABLE_SEMANTIC_TRACE_20260924.md`

The report must end with:
```text
STATUS=<PASS_CHILD_MODEL_CANDIDATE | PASS_UI_ONLY | BLOCKED>
CHILD_148_CLASS=<summary>
CHILD_14C_150_CLASS=<summary>
BEST_CANDIDATE=<0x148 | 0x14C/0x150 | NONE>
ITEM_RECORD_TRAVERSAL_PROVEN=<YES|NO>
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
NEXT=<one bounded next action>
```
