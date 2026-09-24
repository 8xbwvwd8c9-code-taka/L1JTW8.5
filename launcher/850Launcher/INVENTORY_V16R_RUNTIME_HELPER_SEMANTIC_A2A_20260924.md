GOAL
Classify the validated V16R runtime helper bodies and determine whether any helper proves an inventory/model/data-owner path suitable for FORMAL_WP5 promotion.

MUST
- Use the validated runtime capture `850_inventory_invwin_runtime_helpers_v16r.txt` as authority.
- Preserve the V16R gates:
  - `RUNTIME_ANCHOR_004CC180=INDEX_ACCESSOR`
  - `RUNTIME_ANCHOR_004CE990=SIZE_ACCESSOR`
  - `RUNTIME_CODE_SANITY=PASS`
  - `STATUS=PASS_RUNTIME_HELPER_CAPTURE_VALIDATED`
- Analyze only the 16 exact-target runtime helpers already captured by V16R. Do not discover new runtime addresses by scanning.
- Prioritize these helpers first:
  1. `0x00869AF0`
  2. `0x00874850`
  3. `0x00854820`
  4. `0x00880E20`
  5. `0x00881680`
  6. `0x008550E0`
- For every promoted candidate, provide exact evidence: helper RVA, instruction RVA, register/object provenance, compared field/argument, loop/traversal structure, and called helper/vtable slot.
- Distinguish generic UI-control/container semantics from game inventory/item semantics.
- Treat `0x004CC180` as the proven stride-4 index accessor and `0x004CE990` as the proven size accessor; these alone are NOT inventory proof.
- For `0x00869AF0`, determine the semantic meaning of its index/count loop and whether returned pointers are UI child controls or game-data/item records.
- For `0x00874850`, determine what collection is traversed via SIZE+INDEX and classify all important indirect calls/field offsets used on each element.
- For `0x00854820`, classify the object family reached through BEGIN_CONST and whether it is a generic collection accessor only.
- For `0x00880E20` and `0x00881680`, classify the begin/end-mutator family and determine whether it is UI/container plumbing or a game-data model.
- For `0x008550E0`, classify the five indirect calls and identify the object types/field offsets they operate on.
- Explicitly test for these promotion semantics:
  - item-record traversal
  - ObjectId comparison or lookup
  - ItemId/template-id access
  - item count/stack quantity access
  - insert/erase/remove of item records
  - stable item object pointer/container ownership
- If a helper only manipulates UI controls, surfaces, widgets, events, coordinates, visibility/enabled flags, or generic CControl child vectors, retire it.
- Keep evidence conservative: a loop + SIZE/INDEX is only container evidence until element semantics are proven.

DO NOT
- Do not reopen ROOT discovery.
- Do not reopen GRID B.
- Do not reopen `INVWIN+0x0F4`.
- Do not reopen retired owner fields: `0x0AC,0x0B0,0x0B8,0x0E8,0x0EC,0x0F0,0x118,0x148,0x14C,0x150,0x154,0x160,0x178,0x17C` as inventory candidates.
- No heap scan.
- No MEM_PRIVATE scan.
- No broad pointer sweep.
- No memory write.
- No packet sending.
- No direct client action invocation.
- Do not analyze on-disk Lin.bin2 code bytes as semantic authority; V16 proved the on-disk representation differs from runtime MEM_IMAGE.
- Do not follow helpers beyond depth 1 unless the exact callee is already present inside the V16R capture and is required only to classify the current helper.
- Do not promote a candidate based only on STL/vector-like shape.

VALIDATE
- Runtime authority must remain client SHA256 `FAB9DB971F22BF91D06BB36485AAAABFFAEA795BB0DCC22D2EB4039227F54AD4`.
- Confirm V16R source is `RUNTIME_MEM_IMAGE_EXACT_TARGETS` and `EXACT_TARGET_ONLY=YES`.
- The known anchors must remain correctly classified before accepting any other semantic conclusion.
- A helper is promotable only if exact instructions prove game item/data semantics, not generic UI/container traversal.
- If no helper meets that threshold, return `NO_PROMOTABLE_INVENTORY_HELPER` rather than guessing.

FINAL
Create `launcher/850Launcher/INVENTORY_V16R_RUNTIME_HELPER_SEMANTIC_TRACE_20260924.md` and report exactly:

STATUS=
VALIDATED_TARGET_COUNT=16
RETIRED_HELPERS=
PROMOTABLE_HELPERS=
BEST_CANDIDATE=
BEST_CANDIDATE_EVIDENCE=
ITEM_RECORD_TRAVERSAL_PROVEN=
OBJECT_ID_SEMANTICS_PROVEN=
ITEM_ID_SEMANTICS_PROVEN=
COUNT_SEMANTICS_PROVEN=
FORMAL_WP5=NOT_YET
FORMAL_WP6=NOT_YET
NEXT=

For each helper include a compact table with:
`HELPER_RVA | CLASS | CONTAINER_SEMANTICS | ELEMENT_SEMANTICS | ITEM_AFFINITY | EVIDENCE | VERDICT`.
