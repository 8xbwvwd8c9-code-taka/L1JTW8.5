# 381 -> 850 Item Status / Timed Buff Audit

## Migration authority

Target authority:
- 850 item-use lifecycle
- 850 timed effect ownership
- 850 character stat APIs
- 850 character_buff persistence
- 850 protocol/UI

381 is donor-only.

Do NOT port `ItemUseEXTable` or `item_buffTime` wholesale as a parallel framework.

## Scope

381 table:
- `w_道具狀態`

Current source:
- 9 populated rows
- split artifact is INSERT-only
- CREATE schema is not proven

Primary donor runtime:
- `com.lineage.data.item_etcitem.add.item_buffTime`
- `com.lineage.server.datatables.ItemUseEXTable`
- `com.lineage.server.templates.L1ItemUseEX`

## Proven donor execution path

```text
use item
-> item_buffTime.execute()
-> ItemUseEXTable.add(pc,itemId,...)
-> itemId becomes timed skill-effect identity
-> apply stat/vector delta
-> optional polymorph/GFX
-> optional buff persistence
-> remove source item only when add() succeeds
```

Expiry/removal is represented by `ItemUseEXTable.remove(pc,itemId)`, which subtracts the same configured stat deltas.

## Type / replacement model

Rows are indexed by:
- item ID
- logical type

On activation, if another item of the same non-zero type is already active:
- `type_mod=0`: reject
- `type_mod!=0`: remove old skill effect, then activate new one

Current rows use `type_mod=1`.

Current type grouping:
- type 1: 92183, 92187, 92188, 92199, 92200, 92201
- type 2: 92184
- type 3: 92185
- type 4: 92186

Therefore six current rows mutually replace one another under type 1.

## Current source values

All 9 rows have exactly 42 values matching the 42 INSERT columns.

Important source/data observations:

```text
92183 烤玉米力量
description: 力量+1
configured STR: +10

92184 烤玉米敏捷
description: 敏捷+1
configured DEX: +1

92185 烤玉米智力
description: 智力+1
configured INT: +1

92186 烤玉米攻擊
description: 攻擊+5
configured:
  HPR +500
  MPR +300
  melee damage +50
  melee hit +15
  ranged damage +70
  ranged hit +15
  physical reduction +12
  magic reduction +10
  stun resistance +15
  sleep resistance +5

92187 烤玉米命中
description: 命中+3
configured melee hit +3
configured ranged hit +3

92188 烤玉米魔攻
description: 魔攻+2
configured SP +2

92199 烤玉米抗魔
description: 抗魔+10
configured MR +10

92200 烤玉米血量
description: 血量+400
configured HP bonus: 0

92201 烤玉米魔量
description: 魔量+200
configured MP bonus: 0
```

Do not silently correct these values during migration.
The source description and configured behavior disagree for at least 92183, 92200 and 92201; 92186 is a multi-stat profile whose description cannot describe the full configured vector.

## Buff duration / persistence

All current rows:
```text
duration = 1200 sec
buff_save = 1 except 92201 where current row has buff_save = 0
remove item = 1
poly = -1
gfx = 0
cancellation = 0
```

Donor persistence path:
`ItemUseEXTable.getSaveSkillid(pc)`

For every configured row with `buff_save=1`, donor checks remaining skill-effect time using the item ID and writes it through `CharBuffTable.storeBuff`.

This proves persistence intent for current timed item effects in 381.

However the exact login reconstruction/apply-once path must be validated before production migration because the stat vector itself is applied separately from the timer identity.

## Donor add/remove behavior

Activation directly mutates live player stats:
- STR/DEX/CON/INT/WIS/CHA
- AC
- max HP/max MP
- HPR/MPR
- melee/ranged damage
- melee/ranged hit
- physical/magic reduction
- MR/SP
- elemental resistance
- abnormal-status resistances
- optional score multiplier

Removal directly subtracts the configured deltas.

This is algebraically reversible only if activation/removal occur exactly once and replacement/restore does not double-apply.

Target must protect against:
- duplicate activation
- duplicate expiry/removal
- login reapply twice
- old-type removal racing with new-type activation
- persisted timer restored without its stat vector
- stat vector restored twice for one timer

## HP/MP behavior

Donor activation:
- changes max HP / max MP only
- does not add the same delta to current HP / current MP

Donor removal:
- subtracts max HP / max MP
- does not explicitly clamp current HP/MP in this module

850 migration must use native max/current invariant handling:
```text
0 <= currentHP <= maxHP
0 <= currentMP <= maxMP
```

Do not assume donor surrounding code will clamp correctly.

## Consume order

`item_buffTime.execute()` consumes the source item only when `ItemUseEXTable.add(...)` returns true.

Therefore:
```text
effect validation/application -> consume item
```

This is safer than consume-before-effect, but target still needs atomic ownership for:
- replacing an old buff
- applying the new stat vector
- registering timer/persistence
- consuming the source item

## 850 native comparison

850 already has:
- native skill/timed-effect infrastructure
- native stat mutation primitives
- `character_buff`
- `CharBuffTable`
- native status packets

But 850 `CharBuffTable` persists a fixed whitelist of recognized effect IDs.

Current donor item-effect identities are item IDs:
```text
92183..92201
```

Those donor item IDs are not proven members of the 850 persistent buff whitelist.

Therefore:
```text
850_NATIVE_TIMED_EFFECT=YES
850_NATIVE_STAT_PRIMITIVES=YES
850_NATIVE_BUFF_PERSISTENCE=PARTIAL
850_NATIVE_ITEM_STATUS_FRAMEWORK=NO
```

A small generic 850 adapter may be needed for custom data-driven timed modifiers, but a donor framework transplant is not justified.

## Preferred 850 model

Represent the feature as an 850-owned timed modifier definition:

```text
definition:
  semantic item
  duration
  exclusivity group
  replacement policy
  stat vector
  persistence policy
  optional visual/poly

runtime state:
  modifier identity
  expires_at / remaining time
  exactly-once applied flag or authoritative recomputation
```

Prefer authoritative modifier ownership / recomputation over repeatedly mutating already-mutated stats when practical.

Do not use raw donor item IDs as 850 skill IDs unless collision and persistence semantics are explicitly proven safe.

## Client dependency

Current rows:
- GFX = 0
- poly = -1
- standard status packets only

Therefore current active content has no proven custom client resource or protocol dependency.

```text
CLIENT_PROTOCOL_DEP=NO
CLIENT_RESOURCE_DEP=NO_CURRENT_CONTENT
```

## Classification

`LEVEL=L3`

Reason:
- server-side timed state
- mutual-exclusion/replacement lifecycle
- reversible multi-stat modifier
- persistence/logout/login semantics
- existing 850 primitives but no proven direct data-driven equivalent
- no current custom client dependency

## Status

```text
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_道具狀態
LEVEL=L3
TARGET_POLICY=850_NATIVE_FIRST
SOURCE_ROWS=9
COLUMN_ALIGNMENT=42/42_ALL_ROWS
DONOR_EXECUTOR=item_buffTime
DONOR_RUNTIME=ItemUseEXTable
DURATION_SEC=1200
TYPE_REPLACEMENT=PROVEN
REMOVE_AFTER_ADD_SUCCESS=PROVEN
BUFF_SAVE_INTENT=PROVEN
850_NATIVE_TIMED_EFFECT=YES
850_NATIVE_STAT_PRIMITIVES=YES
850_NATIVE_BUFF_PERSISTENCE=PARTIAL
850_NATIVE_ITEM_STATUS_FRAMEWORK=NO
CLIENT_PROTOCOL_DEP=NO
SOURCE_SCHEMA=NOT_PROVEN
DATA_ANOMALIES=92183_STR_10_VS_DESC_1;92200_HP_0_VS_DESC_400;92201_MP_0_VS_DESC_200;92186_MULTI_STAT_VECTOR
BLOCKERS=CREATE schema absent; semantic item mapping; exact login restore/apply-once path; custom modifier persistence ownership; HP/MP clamp semantics; source data anomalies require policy decision
```
