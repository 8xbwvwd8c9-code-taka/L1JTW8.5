# L381 transformation-status family → 850 audit

## Final classification

`BASE_TABLE_LEVEL=L1 (CURRENT_MIGRATION=SKIP)`; `ITEM_TABLE_LEVEL=L4`; `SHARED_MODIFIER_LEVEL=L3/L4`; `STATUS=BLOCKED`.

The empty base split is not evidence that its engine is absent. Its loader and lifecycle are proven. The nonempty `_道具` table is not owned by `GfxIdOrginalpoly`; it has a separate loader/object pair, although both are selected by the same polymorph lifecycle through two branches.

## A. Source family identity

| TABLE | SQL_SIZE | ROW_COUNT | INSERT_COLUMN_COUNT | CREATE_SCHEMA | ACTIVE_CONTENT | LOADER | RUNTIME |
|---|---:|---:|---:|---|---|---|---|
| `w_變身賦予狀態` | `0 bytes` | `0` | `NOT_APPLICABLE` | `NOT_PROVEN` | `NONE in current split` | `GfxIdOrginalpoly` | `L1WilliamGfxIdOrginalpoly` |
| `w_變身賦予狀態_道具` | `2774 bytes` | `19` | `38` | `NOT_PROVEN` | `gfxId` keyed rows with stats, resistances and flags | `GfxIdOrginal` | `L1WilliamGfxIdOrginal` |

The base loader still initializes at startup: `GameServer` calls `GfxIdOrginalpoly.getInstance()`, whose constructor executes `SELECT * FROM w_變身賦予狀態`. No unsplit schema/data source was proven in the targeted donor search. The current base migration decision is therefore `ENGINE_CAPABILITY=PROVEN`, `CURRENT_CONTENT=NONE`, `CURRENT_MIGRATION=SKIP`.

The item SQL has exactly 19 rows and each row has 38 values aligned to the 38 INSERT columns. The first row is `gfxId=13450`; the remaining identities are 13715, 13717, 13719, 13721, 13723, 13725, 13727, 13729, 13731, 13733, 23370, 23375, 24014, 24024, 23797, 24004, 24009 and 23648. Alignment is proven from the explicit column list and row tuples; numeric identity is not accepted as an 850 match.

## B. Runtime ownership and call graph

Base:

```text
w_變身賦予狀態
  -> GfxIdOrginalpoly.loadGfxIdOrginalpoly()
  -> HashMap<Integer,L1WilliamGfxIdOrginalpoly> keyed by gfxId
  -> L1WilliamGfxIdOrginalpoly.getAddGfxIdOrginalpoly(pc,gfxId)
     / getReductionGfxIdOrginalpoly(pc,gfxId)
  -> direct mutable L1PcInstance stat APIs
```

Item:

```text
w_變身賦予狀態_道具
  -> GfxIdOrginal.loadGfxIdOrginal()
  -> HashMap<Integer,L1WilliamGfxIdOrginal> keyed by gfxId
  -> L1WilliamGfxIdOrginal.getAddGfxIdOrginal(pc,gfxId)
     / getReductionGfxIdOrginal(pc,gfxId)
  -> direct mutable L1PcInstance stat APIs
```

`L1PolyMorph.doPoly` is the activation/removal owner for players. On replacement it removes the old profile using `pc.getTempCharGfx()`, changes shape, then adds the new profile. Branch selection is controlled by `pc.getloginpoly()`: `1` selects `GfxIdOrginal` (the `_道具` table); `0` selects `GfxIdOrginalpoly` (the base table). Startup registration is proven in `GameServer`; direct lifecycle call sites are `L1PolyMorph.doPoly`, `undoPoly`, and `undoPolyPrivateShop` paths. `L1ActionPc`, item/skill/NPC callers call `L1PolyMorph.undoPoly`, not either table loader directly.

Additional behavior call sites use the same selected runtime: `L1AttackPc` checks `Cancellation(...)`; `L1PcInstance` checks `DeadExp(...)` during death/EXP handling; `CANCELLATION` skill mode checks the selected flag before deciding whether to undo the polymorph. These flags are behavior gates, not stat-vector fields.

### Ownership result

```text
OWNER=player polymorph lifecycle, selected by persistent loginpoly branch
ACTIVE_IDENTITY=current player tempCharGfx plus loginpoly selector
START_EVENT=L1PolyMorph.doPoly player branch
END_EVENT=L1PolyMorph.undoPoly / replacement removal / private-shop cleanup
REPLACEMENT_EVENT=remove old gfx profile, set new gfx, add new profile
PERSISTENCE=loginpoly is loaded from character storage; profile stats themselves are not separately persisted
LOGIN_RESTORE=PROVEN for loginpoly field; full profile reapply path NOT_PROVEN in targeted evidence
DEATH_BEHAVIOR=DeadExp flag is queried; exact EXP-loss policy is delegated to death path
CANCELLATION_BEHAVIOR=Cancellation flag gates cancellation; exact skill-path result is proven in CANCELLATION.java
```

The item table is therefore not a separate item-use owner in this family. Its `note` values and gfx identities are definitions selected by the polymorph branch. The table name does not prove source-item ownership.

## C. Table relationship

`RELATIONSHIP=SERVER_POLY_VS_ITEM_POLY / SAME_ENGINE_DIFFERENT_DATA`, not `BASE_AND_ITEM_OVERRIDE` and not proven legacy/new generation. They have the same 38-field vector and separate loader/template classes. The runtime explicitly chooses one branch using `loginpoly`; both are not added simultaneously for one player branch. A transform replacement can switch branch only if the selector changes before the next add/remove call; that selector transition is not an independent database row.

The item table contains no field absent from the base runtime vector, and the base table has no field absent from the item loader. Field meanings are structurally identical. This shared representation must not be confused with shared lifecycle ownership.

## D. Stat vector comparison

`A=w_道具狀態`, `B=w_指定道具賦予狀態`, `C=w_變身賦予狀態`, `D=w_變身賦予狀態_道具`.

| STAT | A/B | C | D | 850 primitive |
|---|---|---|---|---|
| STR/DEX/CON/INT/WIS/CHA | item definition/runtime vocabulary | present | present | player stat modifier primitives |
| AC, MAX_HP, MAX_MP, HPR, MPR | present in targeted item runtime vocabulary | present | present | character combat/stat primitives |
| MELEE_DMG/RANGED_DMG/MELEE_HIT/RANGED_HIT | present | present | present | attack/stat modifier primitives |
| PHYSICAL_REDUCTION/MAGIC_REDUCTION | physical present; magic field requires targeted proof | physical present; magic field stored but not applied | same | reduction primitives; magic reduction mapping required |
| MR/SP | present | present | present | MR/SP primitives |
| FIRE/WIND/EARTH/WATER | present | present | present | elemental resistance primitives |
| EXP | present as item/effect vocabulary | `addExp` present | `addExp` present | EXP modifier path |
| POTION_HEAL | present | present | present | potion-heal modifier path |
| PVP_DMG/PVP_REDUCTION | present | present | present | PVP modifier path |
| MAGIC_HIT | present | present | present | magic-hit primitive |
| STUN/STONE/SLEEP/FREEZE/SUSTAIN/BLIND_RESIST | present in item status family | present | present | abnormal-resistance primitives |

The common numeric vector can be represented by one future `StatModifierDefinition`; its lifecycle must remain external and source-specific. A/B item ownership, C/D polymorph ownership, equipment/passive ownership and timed skill ownership must not be merged into the definition object.

## E. Add/remove symmetry

`ADD_FIELD_COUNT=35 numeric fields`; `REMOVE_FIELD_COUNT=34 numeric fields`; `SYMMETRIC_FIELDS=34`; `ASYMMETRIC_FIELDS=reduction_magic_dmg`.

`reduction_magic_dmg` is read, stored in the constructor and has a getter, but neither `getAddGfxIdOrginalpoly` nor `getReductionGfxIdOrginalpoly` applies/removes it. This is a proven dormant/unused field, not a guessed bug in the SQL. The same structural omission exists in the sibling item runtime class. It must not be reproduced in an 850 modifier implementation unless the 850 contract intentionally declares the field unsupported.

`deadExp` and `cancellation` are boolean behavior flags queried by death and cancellation paths; they are not add/remove numeric modifiers. Numeric add/remove is otherwise sign-symmetric, including AC's inverse API convention, max HP/MP, elemental fields, EXP, potion, PVP and abnormal resistances.

## F. HP/MP invariant and replacement race

The donor removal calls `addMaxHp(-value)` / `addMaxMp(-value)` and sends `S_HPUpdate` / `S_MPUpdate`. The targeted code does not show a clamp of current HP/MP to the new maxima in the transformation removal method; packet emission is not proof of clamping. Therefore `currentHP > newMaxHP` and `currentMP > newMaxMP` are `NOT_PROVEN_SAFE`. The same invariant must be checked against `w_道具狀態` and `w_指定道具賦予狀態` before reuse; no single donor helper is proven to enforce it.

Replacement order is proven as: remove A → change shape → add B. This algebraically gives `Baseline + V_A → Baseline → Baseline + V_B`. Same-gfx reapply is guarded by `if (tempCharGfx != polyId)` for the shape transition, but the add calls are outside that guard, so repeated `doPoly` can re-add a profile. A timer/late cancellation using only current gfx can remove B after A replacement if events are delayed; no generation token is proven. Rapid transform, late expiry and cancellation therefore remain drift risks for a future 850 lifecycle owner.

## G. 850-native comparison

`850_NATIVE_POLYMORPH=PROVEN primitive`; `850_NATIVE_TIMED_EFFECT=PROVEN primitive`; `850_NATIVE_STAT_PRIMITIVES=PROVEN`; `850_NATIVE_TRANSFORM_STAT_PROFILE=NOT_PROVEN`; `850_NATIVE_PERSISTENCE=PARTIAL` (`loginpoly`-like character persistence exists in donor evidence, but full transform-stat restore is not proven); `850_NATIVE_MAPPING=PARTIAL/BLOCKED`.

850 should use its native polymorph, timed effect, stat and packet lifecycle. The minimum architecture is a lifecycle-external `StatModifierDefinition` plus `ModifierInstance` owned by a `TransformationLifecycleOwner`, with `apply/remove/replace/recompute`, exactly-once activation/removal, idempotent replay, source namespace, atomic replacement and HP/MP clamp. No implementation is made here.

## H. Client dependency

All 19 `_道具` rows use gfx identities that require semantic 850 polymorph/resource verification. Direct donor numeric reuse is unsafe. Current classification for every listed gfx row is `CLIENT_ID_MAPPING_REQUIRED` or `NOT_PROVEN`; none is proven `SERVER_ONLY`. No client resource or client code was modified.

## I. Proven donor issues / non-reproduction rules

1. `BUG=stored reduction_magic_dmg is never applied or removed`; `EVIDENCE=constructor/getter plus both methods omit it`; `IMPACT=declared magic reduction silently has no runtime effect`; `DO_NOT_REPRODUCE=omit from active 850 vector or implement explicitly after 850 semantic approval`.
2. `BUG=HP/MP post-removal clamp is not proven`; `EVIDENCE=removal decrements max and sends update, with no local current-value clamp`; `IMPACT=possible current value above maximum`; `DO_NOT_REPRODUCE=make clamp an explicit 850 invariant`.
3. `BUG=profile application is outside the shape-change guard`; `EVIDENCE=add branch runs after `if (tempCharGfx != polyId)``; `IMPACT=repeat transform can drift stats`; `DO_NOT_REPRODUCE=use idempotent owner/recompute semantics`.

No proof was found for null-template crashes (both methods null-return), byte overflow beyond the donor byte fields, or a wrong sign in the symmetric fields. Numeric GFX compatibility is not a donor bug; it is an unresolved migration mapping.

## J. Migration decision

- Base table: current split has no content; retain no migration rows. Preserve engine capability only as historical evidence. `BASE_LEVEL=L1`.
- Item table: do not import the 19 rows directly. Map each semantic polymorph/resource identity to 850 definitions after client/resource verification. `ITEM_LEVEL=L4`.
- Shared modifier: a common vector is architecturally reasonable, but lifecycle integration, replacement tokens, HP/MP clamp and client mapping make implementation `L3/L4`. `SHARED_MODIFIER_LEVEL=L3/L4`.

Blockers: base CREATE schema/data source absent; item CREATE schema absent; 850 transform-stat profile not proven; all 19 GFX identities need semantic/client mapping; `loginpoly` restore and death/cancellation cleanup need an 850 lifecycle contract; missing `reduction_magic_dmg` behavior must be decided; HP/MP clamp and replacement generation/idempotency are unresolved.

No production/core/DB/client files were modified.
