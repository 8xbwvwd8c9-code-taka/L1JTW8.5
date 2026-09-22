# 381 -> 850 Outburst / Rage Item System Audit

## Migration authority

Target authority:
- 850 player state/effect lifecycle
- 850 item-use framework
- 850 stat aggregation
- 850 persistence model
- 850 visual/effect handling

381 is donor-only.

Do NOT port `ItemOutburst`, `ItemFire`, or `ItemFireCount` wholesale.

## Scope

381 table:
- `w_道具爆氣系統`

Primary donor runtime:
- `com.lineage.william.ItemOutburst`
- `com.lineage.data.item_etcitem.ItemFire`
- `com.lineage.data.item_etcitem.ItemFireCount`
- custom fields on `L1PcInstance`

## Current DB content

Current split SQL contains 3 level-band rows for the same trigger item.

```text
item 92508
level 20..29 -> +5
level 30..39 -> +10
level 40..50 -> +15
```

Each row currently applies the same value to:
- melee damage
- ranged damage
- damage reduction
- max HP
- max MP
- SP

Current rows:
- gfx = 0
- 每秒扣一怒氣值 = 1
- activation/deactivation messages enabled

Current split artifact is INSERT-only; CREATE schema is not proven.

## Donor entry items

Observed etcitem definitions include:

```text
爆氣之書
classname = ItemFire

爆氣補充水
classname = ItemFireCount 1 5000
```

Thus:
- `ItemFire` toggles the mode
- `ItemFireCount` adds rage/outburst resource
- recharge amount = 1
- recharge cap = 5000

The numeric source item identities must be semantically mapped to 850.

## Donor player state

381 adds custom fields:

```text
_isOutbur
_ischeckOutbur
_Quburcount
```

with getters/setters:
- `getOutbur/setOutbur`
- `getcheckOutbur/setcheckOutbur`
- `getQuburcount/setQuburcount`

These are donor-specific player fields.

Do NOT add the same raw fields to 850 unless they are the best fit for 850's native state model.

## Toggle behavior

`ItemFire.execute(...)`:

1. finds item `92508`
2. removes it
3. sleeps 1 ms
4. stores item `92508` back
5. if already active:
   - calls `ItemOutburst.falseOutburst`
   - sets active false
6. otherwise:
   - requires `Quburcount > 0`
   - calls `ItemOutburst.trueOutburst`
   - sets active true

The remove/re-store cycle appears to be donor refresh/state glue.

It should NOT be copied into 850.

## Activation stat application

For the matching item + level band, `trueOutburst` applies:

```text
addDmgup
addBowDmgup
addClan_ReductionDmg
addMaxHp
addMaxMp
addSp
```

It can also spawn a skin effect through:
```text
L1SpawnUtil.spawnSkin
```

Current data has `特效=0`, so no current skin resource is required.

If:
```text
每秒扣一怒氣值 == 1
```
the donor sets:
```text
checkOutbur = true
```

## Deactivation

`falseOutburst` subtracts the configured stat values and removes the optional skin.

### Donor HP/MP restoration bug

On deactivation donor performs:

```text
max HP -= bonus
current HP += bonus

max MP -= bonus
current MP += bonus
```

This is internally inconsistent.

Reducing max HP/MP while increasing current HP/MP can leave:
- current > max
- unexpected healing
- state requiring later clamp

Do NOT preserve this bug.

850 should:
- remove max-stat bonus
- clamp current HP/MP to new maximum
- never add the removed bonus to current resource merely because the buff ended

## Rage recharge

`ItemFireCount`:
1. checks current rage against configured cap
2. adds configured amount
3. consumes one recharge item

Current etcitem configuration:
```text
ItemFireCount 1 5000
```

### Donor cap bug

The implementation checks:

```text
if current >= cap -> reject
else current += amount
```

It does NOT clamp the result.

With an amount greater than 1, values can exceed the cap.

Current amount is 1, so present data avoids the bug.

850 should use:
```text
newValue = min(cap, current + amount)
```

## Critical missing runtime proof: per-second drain

The DB field explicitly says:
```text
每秒扣一怒氣值
```

and activation sets:
```text
checkOutbur = true
```

However targeted source inspection has NOT proven:
- the scheduler/timer that decrements `Quburcount`
- exact tick frequency
- exact decrement amount
- behavior at zero
- whether zero automatically calls deactivation
- logout/login handling
- persistence of rage value

Therefore:

```text
RAGE_DRAIN_RUNTIME=NOT_PROVEN
RAGE_PERSISTENCE=NOT_PROVEN
AUTO_DISABLE_AT_ZERO=NOT_PROVEN
```

This is a hard migration blocker.

Do not infer the missing behavior merely from the DB column name.

## 850-first design

The feature should be remodeled as a native 850 toggle/resource buff:

```text
outburst definition
  level band
  stat modifier profile
  visual profile
  resource cost policy

player outburst state
  active
  rage_current
  rage_cap
  tick ownership/persistence
```

Recommended lifecycle:

```text
use toggle item
-> validate level/resource
-> activate 850-native modifier
-> register periodic resource drain
-> when rage reaches zero:
     remove modifier
     remove visual
     persist state
-> manual toggle:
     remove modifier
     persist state
```

Use a single 850 scheduler/effect owner.

Do not implement periodic logic inside the item executor itself.

## 850 reusable primitives

850 already has generic concepts for:
- timed skill/effect state
- stat modifiers
- item use
- player status updates
- scheduler/thread pool

A direct outburst framework is not proven.

Therefore:

```text
850_NATIVE_OUTBURST=NOT_PROVEN
850_REUSABLE_PRIMITIVES=YES
```

## Persistence policy

381 source evidence does not prove `Quburcount` persistence.

For 850 this must be explicitly chosen:

```text
rage persists across logout/restart
OR
rage is session-only
```

Because recharge items have consumable value, session-only loss on restart can be economically significant.

Recommended 850 design is persistent rage if this feature is retained.

## Client dependency

Current active rows use:
- normal item usage
- normal server messages
- ordinary stat/status packets
- no active custom skin GFX

Therefore:

```text
CLIENT_PROTOCOL_DEP=NO
CUSTOM_GFX_CURRENT_CONTENT=NO
```

850 UI remains authoritative.

## Classification

`LEVEL=L3`

Reason:
- persistent/resource player state likely required
- timed decrement lifecycle
- reversible stat modifier
- item toggle/recharge integration
- logout/death/restart cleanup semantics
- missing donor timer means target behavior must be explicitly designed
- no custom client dependency

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_道具爆氣系統
LEVEL=L3
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_ROWS=3
TOGGLE_ITEM=ItemFire
RECHARGE_ITEM=ItemFireCount
RAGE_CAP_CURRENT_CONFIG=5000
RAGE_RECHARGE_CURRENT_CONFIG=1
PLAYER_STATE=_isOutbur+_ischeckOutbur+_Quburcount
RAGE_DRAIN_RUNTIME=NOT_PROVEN
RAGE_PERSISTENCE=NOT_PROVEN
AUTO_DISABLE_AT_ZERO=NOT_PROVEN
CURRENT_CUSTOM_GFX=NO
DONOR_HP_MP_DEACTIVATION_BUG=YES
DONOR_RECHARGE_CAP_CLAMP=NO
850_REUSABLE_PRIMITIVES=YES
WHOLESALE_RUNTIME_PORT=NO
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=drain scheduler semantics,persistence policy,zero-state behavior,item semantic mapping,850 stat-modifier adapter
```
