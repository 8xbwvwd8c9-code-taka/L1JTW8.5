# 381 -> 850 Transformation Arrow Effect Audit

## Scope

381 table:
- `w_變身箭矢特效`

Current source:
- rows = 1
- mapping = poly 6611 -> arrow gfx 8121
- split artifact is INSERT-only
- CREATE schema not proven

Donor loader/runtime:
- `com.lineage.william.ArrowGfxid`

## Proven donor behavior

`ArrowGfxid.forItemUSe(user, poly)` lazily loads the table and manages a player field named `polyarrow`.

Observed logic:
- if player does not have skill effect 67, set `polyarrow=0`
- if current poly matches configured `polyid`, set configured `arrowgfxid`
- otherwise set fallback `polyarrow=66`

The source therefore proves intent to select an arrow visual by current polymorph state.

## Missing closure

Targeted repository search did not prove the downstream consumer of:
- `getpolyarrow`
- `setpolyarrow`
- `ArrowGfxid.forItemUSe`

Therefore the full chain:

```text
polymorph state
-> ArrowGfxid
-> player.polyarrow
-> ranged attack packet/effect
```

is NOT closed.

Do not migrate this table until the consumer path is proven.

## 850-first assessment

850 authority already has polymorph and ranged-attack packet/effect infrastructure, but no equivalent data-driven poly->arrow-GFX adapter is proven.

Current feature is visual only; no stat modifier should be involved.

If retained, preferred target is:
```text
850 polymorph state
-> small arrow-visual resolver
-> native ranged attack packet/effect
```

Do not add donor `polyarrow` state blindly.

## Client dependency

Both identifiers are client-visible identities:
- polyid = 6611
- arrowgfxid = 8121

Neither numeric identity is proven compatible with 850 client resources.

```text
CLIENT_ID_MAPPING_REQUIRED=YES
CLIENT_RESOURCE_COMPATIBILITY=NOT_PROVEN
```

## Classification

`LEVEL=L4`

Reason:
- server logic is small
- current content is one mapping row
- but functionality depends on client-visible transformation and projectile GFX identities
- downstream packet consumer is not yet proven

If semantic client mapping and consumer path are later proven native-compatible, implementation complexity may reduce to L2.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_變身箭矢特效
LEVEL=L4
SOURCE_ROWS=1
DONOR_RUNTIME=ArrowGfxid
MAPPING=6611->8121
PLAYER_STATE=polyarrow
CONSUMER=NOT_PROVEN
850_NATIVE_POLYMORPH=YES
850_NATIVE_ARROW_MAPPING=NOT_PROVEN
STAT_MODIFIER_DEP=NO
CLIENT_DEP=YES
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=downstream polyarrow consumer; semantic poly mapping; arrow GFX/client resource mapping
```
