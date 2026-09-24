# 381 -> 850 Custom Polymorph Scroll Audit

## Scope
Table: `w_自訂變形卷軸`

Current donor rows:
- 6 rows
- 5 unique actions
- 5 unique poly IDs
- duplicate exact row for `tw wizard plus -> 13219`
- all current rows require level 62
- all current rows configure consume item 40308 x1
- CREATE schema not proven

## Donor runtime
`com.lineage.william.ItemActionPoly`

Loader behavior:
- lazy load on first `forNpcQuest()`
- one static ArrayList cache
- no reload path
- DB exceptions swallowed
- source order preserved
- all matching rows are iterated; there is no break/return after successful polymorph

## Duplicate runtime proof

Current duplicate:
```text
tw wizard plus,13219,62,40308,1
tw wizard plus,13219,62,40308,1
```

For a level-eligible player with enough items, both rows match because the loop continues after the first success.

Each match executes:
```text
checkItem(itemId)
consumeItem(itemId)
L1PolyMorph.doPoly(...)
close dialog
```

Therefore:
```text
DUPLICATE_CONSUME_RISK=YES
DUPLICATE_POLY_APPLY_RISK=YES
DUPLICATE_ROW_IMPACT=double execution while resources remain
```

The second polymorph may refresh/reapply the same transform lifecycle even though the visible gfx is unchanged.

## Configured count bug

The DB has `扣除道具數量`, but donor runtime checks and consumes using overloads without the configured count:

```text
checkItem(itemId)
consumeItem(itemId)
```

The count value is only used in the insufficient-item message.

Current rows all use count=1, so current content happens to match runtime.
Future rows with count>1 would be semantically incorrect.

## Consume ordering

Donor:
```text
validate level
-> validate item existence
-> consume item
-> doPoly
```

381 `L1PolyMorph.doPoly` may return early for:
- dead player
- blocked maps
- protected/temp gfx states
- cause mismatch
- item-poly restrictions / missing secondary eligibility item
- other transformation restrictions

Because `ItemActionPoly` consumes first and donor `doPoly` is void, it cannot know success.

```text
CONSUME_BEFORE_EFFECT=YES
PARTIAL_FAILURE_RISK=PROVEN
REFUND_PATH=NONE
```

## Runtime reachability

Targeted inspection of donor `C_NPCAction` does not import or invoke `ItemActionPoly`.
Repository-targeted searches did not prove another direct callsite.

Therefore:
```text
CALLSITE=PROVEN
RUNTIME_REACHABILITY=PROVEN_DONOR;NOT_PROVEN_850
ACTION_ORIGIN=BLOCKED
```

Do not treat the current DB rows as guaranteed reachable production content until an action owner is proven.

## Donor/native polymorph comparison

381 native polymorph already owns:
- transformation timer effect 67
- tempCharGfx
- map restrictions
- cause restrictions
- equipment compatibility
- replacement cleanup
- transform-stat family add/remove
- arrow effect hook

Thus `ItemActionPoly` adds only:
- action-string routing
- custom minimum-level gate
- item cost
- hardcoded 1800-second duration

It does not need to own transformation lifecycle.

## 850 native comparison

850 completed authority has:
- `PolyTable`
- `L1PolyMorph`
- native lookup by name and poly ID
- minimum level data
- weapon/armor compatibility
- cause flags
- map restriction check
- effect 67 ownership
- native transformation duration packet

Important difference:
850 native polymorph application returns boolean success/failure.

Therefore the preferred target can safely be:

```text
validate action mapping
-> validate semantic item cost
-> call 850 native polymorph
-> only on success consume required item
```

This removes donor consume-before-effect failure.

```text
850_NATIVE_POLYMORPH=YES
DONOR_RUNTIME_PORT_REQUIRED=NO
MINIMAL_EXTENSION=small action/item-cost adapter only if an action entry point is still required
```

## Semantic ID mapping

Donor polymorph IDs:
- 13216
- 13217
- 13218
- 13219
- 13220

Donor cost item:
- 40308

Current audit does NOT prove semantic equivalence of these numeric IDs in 850.

```text
POLY_MAPPING=EXACT_NATIVE_MATCH_SERVER_SIDE
ITEM_MAPPING=NOT_PROVEN
DIRECT_ID_SAFE=NO
```

Do not migrate numeric IDs blindly.

## Interaction with transform-status family

381 `L1PolyMorph.doPoly` already invokes transform-status add/remove around tempCharGfx replacement.

Therefore custom scroll/action migration must call the 850 polymorph lifecycle and must NOT independently apply transform-status modifiers.

```text
TRANSFORM_STATUS_DEP=YES_LIFECYCLE
SHARED_LIFECYCLE_OWNER=POLYMORPH
```

## Arrow effect interaction

No current `w_變身箭矢特效` row has been proven for poly IDs 13216..13220.

```text
ARROW_EFFECT_DEP=NO_CURRENT_MAPPING_PROVEN
```

## Donor bugs / do-not-reproduce

1. Exact duplicate source action
   - impact: double execution / double consumption possible
   - do not reproduce: enforce unique action identity or explicit multi-action semantics

2. No break after successful match
   - impact: every duplicate matching row executes
   - do not reproduce: one authoritative action mapping

3. Configured consume count ignored by validation/consumption
   - impact: future count>1 rows are incorrect
   - do not reproduce: count-aware check/consume

4. Consume before polymorph success
   - impact: item loss on failed transformation
   - do not reproduce: consume only after native success or use atomic transaction semantics

5. DB load exceptions swallowed
   - impact: silent feature disappearance
   - do not reproduce: explicit loader diagnostics

6. No loader reload
   - impact: runtime DB changes not reflected
   - not necessarily required in 850; document ownership explicitly

## Classification

```text
DATA_LEVEL=L1
SERVER_RUNTIME_LEVEL=L2
CLIENT_LEVEL=L4_IF_POLY_RESOURCE_MAPPING_MISSING
FINAL_LEVEL=L4_BLOCKED_BY_SEMANTIC_CLIENT_MAPPING
```

If all five polymorphs and action origins map cleanly to existing 850 resources, implementation can reduce to L2.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_自訂變形卷軸
SOURCE_ROWS=6
UNIQUE_ACTIONS=5
DUPLICATE_ROWS=1
DONOR_RUNTIME=ItemActionPoly
CALLSITE=NOT_PROVEN
RUNTIME_REACHABILITY=NOT_PROVEN
CONSUME_ORDER=BEFORE_EFFECT
DUPLICATE_RUNTIME_EFFECT=DOUBLE_EXECUTION_POSSIBLE
POLY_MAPPING=BLOCKED
ITEM_MAPPING=BLOCKED
850_NATIVE=YES_POLYMORPH
MINIMAL_EXTENSION=ACTION_AND_ITEM_COST_ADAPTER_IF_REQUIRED
DONOR_RUNTIME_PORT_REQUIRED=NO
TRANSFORM_STATUS_DEP=YES_POLYMORPH_LIFECYCLE
CLIENT_DEP=BLOCKED
LEVEL=L4
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=action owner; semantic item mapping 40308; client GFX/resource mapping; duplicate source row; consume-before-effect donor bug
```


## Agent reconciliation addendum

Latest targeted donor/850 verification supersedes earlier unresolved callsite/poly mapping state:

```text
CALLSITE=PROVEN
RUNTIME_REACHABILITY=PROVEN donor / NOT_PROVEN 850
POLY_MAPPING=EXACT_NATIVE_MATCH server-side
ITEM_MAPPING=NOT_PROVEN
850_NATIVE=PROVEN
MINIMAL_EXTENSION=generic action/item-cost adapter
TRANSFORM_STATUS_DEP=CHECKED; L1PolyMorph owns lifecycle
CLIENT_DEP=BLOCKED
LEVEL=L4
```

Interpretation:
- donor action path is reachable
- 13216..13220 have server-side native polymorph equivalents
- 850 should still not port ItemActionPoly
- remaining migration blockers are action ownership mapping, semantic identity of item 40308, and client GFX/resource compatibility
