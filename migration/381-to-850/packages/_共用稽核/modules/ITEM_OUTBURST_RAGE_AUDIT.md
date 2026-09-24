# 381 -> 850 Item Outburst / Rage Audit

## Migration authority

Target authority:
- 850 character state model
- 850 item-use lifecycle
- 850 buff/stat framework
- 850 persistence/timer infrastructure

381 is donor-only.

## Scope

381 table:
- `w_道具爆氣系統`

Primary donor runtime:
- `ItemOutburst`
- `ItemFire`
- `ItemFireCount`
- custom fields in `L1PcInstance`

## Current content

Current DB rows: 3.

All rows use source item:
```text
92508
```

Level bands:
```text
20..29 -> +5
30..39 -> +10
40..50 -> +15
```

Applied fields per tier:
- melee damage
- ranged damage
- damage reduction
- max HP
- max MP
- SP

Current rows:
```text
effect gfx = 0
consume 1 rage per second = 1
```

Messages:
- 啟動爆氣
- 關閉爆氣

The split SQL is INSERT-only; authoritative CREATE schema is not proven.

## Entry items

Current etcitem data proves two donor item executors:

```text
爆氣之書
  classname=ItemFire

爆氣補充水
  classname=ItemFireCount 1 5000
```

The donor item ID identity for the trigger scroll itself is not safely derivable from INSERT row order alone.

Do not map by apparent row position.

## Toggle flow

`ItemFire.execute(...)`:

1. ensures an item with ID `92508` exists by removing/re-storing it
2. if outburst already active:
   - call `ItemOutburst.falseOutburst(...)`
   - set active false
3. otherwise:
   - require rage count > 0
   - call `ItemOutburst.trueOutburst(...)`
   - set active true

This donor implementation contains unnecessary inventory mutation around item `92508`.

850 should not reproduce that remove/re-add behavior.

## Rage refill

`ItemFireCount` is parameterized by:

```text
add amount = 1
cap = 5000
```

It increments:
```text
pc.Quburcount
```

and consumes one refill item.

## Runtime stat application

`ItemOutburst.trueOutburst(...)` selects a rule by:
- source item ID
- player minimum/maximum level
- active state false

Then adds:
- `Dmgup`
- `BowDmgup`
- `Clan_ReductionDmg`
- max HP/current HP
- max MP/current MP
- SP

If configured:
- optional skin/effect is spawned

It also sets:
```text
checkOutbur=true
```
when the rule says rage should be consumed over time.

## Disable flow

`falseOutburst(...)` attempts to reverse the same stat deltas and remove the optional skin.

### Concrete donor bug

For HP/MP disable logic the donor does:

```java
pc.addMaxHp(-bonus);
pc.setCurrentHp(pc.getCurrentHp() + bonus);

pc.addMaxMp(-bonus);
pc.setCurrentMp(pc.getCurrentMp() + bonus);
```

The current HP/MP is increased while max HP/MP is reduced.

That is not a correct inverse operation and can produce invalid current/max relationships depending on surrounding clamps.

850 must NOT reproduce this bug.

Correct target behavior should:
- remove max bonus
- clamp current HP/MP to the new max
- never add the removed max bonus to current value

## Donor player-state coupling

381 adds custom fields directly into `L1PcInstance`:

```text
_isOutbur
_ischeckOutbur
_Quburcount
```

with accessors:
- `getOutbur/setOutbur`
- `getcheckOutbur/setcheckOutbur`
- `getQuburcount/setQuburcount`

These fields are donor-specific implementation detail.

Do not add equivalent ad-hoc fields to 850 unless they map into an existing 850 state/buff resource model.

## Rage drain lifecycle

The table and runtime prove intent:
```text
每秒扣一怒氣值
```

and `checkOutbur` is used as a flag indicating continuous rage consumption.

However the exact authoritative timer/decrement owner was not proven in the targeted trace completed for this audit.

Therefore:
```text
RAGE_DRAIN_INTENT=PROVEN
RAGE_DRAIN_HOOK=NOT_PROVEN
```

Do not infer timer semantics or persistence from the flag name alone.

## Persistence

No persistence of:
- rage count
- active outburst state

was proven from this module.

Current donor state appears process/player-memory based unless another storage path exists outside the traced files.

Therefore:
```text
RAGE_PERSISTENCE=NOT_PROVEN
ACTIVE_STATE_PERSISTENCE=NOT_PROVEN
```

850 design must explicitly choose whether these survive:
- logout
- restart
- death
- map change

## 850-first target design

Do NOT port:
- `ItemOutburst`
- custom L1PcInstance booleans/counters
- donor inventory remove/re-store hack

Preferred target model:

```text
rage resource
  current
  cap
  persistence policy

outburst buff
  active
  level-tier profile
  stat deltas
  drain rate
  optional visual
```

Use existing 850:
- item executor/action
- buff/effect ownership
- stat add/remove API
- scheduled buff/resource timer
- status packets

If 850 has an existing timed buff/resource component, map this feature into it.

## Client dependency

Current source rows use:
```text
特效=0
```

So current behavior needs no custom visual resource.

Interaction is standard item use + server messages/status updates.

```text
CLIENT_PROTOCOL_DEP=NO
CUSTOM_GFX_DEP_CURRENT_CONTENT=NO
```

## Difficulty

`LEVEL=L3`

Reason:
- persistent/ephemeral player resource decision
- toggleable multi-stat buff
- periodic resource drain
- lifecycle cleanup
- death/logout/restart semantics
- current donor implementation adds custom player state

This should still be implemented 850-native rather than porting donor core.

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_道具爆氣系統
LEVEL=L3
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_ROWS=3
DONOR_RUNTIME=ItemOutburst+ItemFire+ItemFireCount
CUSTOM_PC_FIELDS=3
RAGE_CAP_CURRENT_DONOR=5000
RAGE_DRAIN_RATE_INTENT=1_PER_SECOND
RAGE_DRAIN_HOOK=NOT_PROVEN
RAGE_PERSISTENCE=NOT_PROVEN
ACTIVE_STATE_PERSISTENCE=NOT_PROVEN
CUSTOM_GFX_DEP_CURRENT_CONTENT=NO
CLIENT_PROTOCOL_DEP=NO
DONOR_HP_MP_DISABLE_BUG=YES
WHOLESALE_RUNTIME_PORT=NO
SOURCE_SCHEMA=NOT_PROVEN
BLOCKERS=850 buff/resource mapping,drain lifecycle,persistence policy,item semantic mapping
```
