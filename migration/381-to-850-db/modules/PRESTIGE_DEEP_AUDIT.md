# 381 -> 850 Prestige System Audit

## Scope
381 prestige family:
- `w_威望名稱自訂`
- `w_威望怪物`
- `w_威望設置`

Core:
- `NpcPrestigeTable`
- `RewardPrestigeTable`
- `L1RewardPrestige`
- `L1PcInstance`
- `L1MonsterInstance`
- `add.Prestige`
- `L1PcRewardPrestigeGfxTimer`
- character persistence
- `ConfigOther.dead_score`

## DB model

### w_威望名稱自訂
Defines display terminology only.
Current donor value:
- 威望積分

Runtime display name is exposed through `pr_type_name`.

### w_威望怪物
Fields:
- npcid
- name
- min_威望
- max_威望

Loaded by `NpcPrestigeTable`.

### w_威望設置
Defines prestige score bands and bonuses:
- score min/max
- title
- MaxHP / MaxMP
- melee/ranged damage
- melee/ranged hit
- MR / SP
- STR / DEX / CON / WIS / INT / CHA
- physical damage reduction
- magic damage reduction
- optional repeated gfx + interval

Current donor ranks span:
新兵 -> 二兵 -> 一兵 -> 上兵 -> 下士 -> 中士 -> 上士 -> 士官長 -> 少尉 -> 中尉 -> 上尉 -> 少校 -> 中校 -> 上校 -> 少將 -> 中將 -> 上將 -> 總長 -> 統帥 -> 元帥.

## Persistence

381 stores the player's prestige score in:
`characters.PrestigeLv`

Despite the column name, the value persisted is:
`pc.getPrestige()`

Load:
`pc.setPrestige(rs.getInt("PrestigeLv"))`

Insert/update:
`PrestigeLv = pc.getPrestige()`

The actual rank/level is derived at runtime:
`RewardPrestigeTable.getLv(prestige)`

Therefore:
- persisted value = score
- derived runtime value = rank ID

Do not rename/migrate this column as if it contains the rank number.

## Monster kill hook

`L1MonsterInstance`:
```
prestige = NpcPrestigeTable.get().get_score(npcId)
if prestige != 0:
    pc.addPrestige(prestige)
```

Then a server message reports the prestige gain.

This is a direct monster-death reward hook.

## Item gain path

`add.Prestige` ItemExecutor:
- consumes one configured item
- adds configured prestige count
- optional gfx
- reports current prestige score

Therefore monster kills are not the only producer.
Any etcitem using classname `add.Prestige` is an independent prestige producer.

## Rank recalculation

`L1PcInstance.addPrestige(delta)`:
1. modify score
2. clamp score >= 0
3. calculate new rank from score ranges
4. if rank changed:
   - remove old rank bonuses
   - assign new rank
   - apply new rank bonuses

This is a replacement model, not cumulative rank stacking.

## Rank bonuses

`RewardPrestigeTable.addPrestige(pc)` applies:
- MaxHP
- MaxMP
- melee damage
- ranged damage
- melee hit
- ranged hit
- MR
- SP
- STR
- DEX
- INT
- CHA
- CON
- WIS
- physical damage reduction
- magic damage reduction
- optional repeating gfx timer

`removePrestige(pc)` reverses the same values.

## Death penalty

`L1PcInstance` death path:
- only when current prestige rank > 0
- subtracts `ConfigOther.dead_score`
- donor default is 100
- if resulting rank <= 0, prestige is reset to 0

This config is part of the module dependency and must not be omitted from an equivalent migration.

## Important donor bug: monster prestige range calculation

`NpcPrestigeTable.get_score()` uses:
```
min + random.nextInt(max)
```

This does NOT generate the intended inclusive [min,max] range.

Example:
- min=5, max=10
- donor result = 5..14

Correct range should be:
```
min + random.nextInt(max - min + 1)
```

Current donor DB appears to use many rows with min=1,max=1, for which the bug is masked.

Migration must implement correct min/max semantics instead of copying this bug.

## Login/restart lifecycle

Persistence loading through `setPrestige()` proves the score/rank is reconstructed.

However, in the currently inspected call paths, `setPrestige()` derives `_prestigeLv` but does not itself call `RewardPrestigeTable.addPrestige()`.

A separate login/recalc call that reapplies prestige stat bonuses was NOT yet proven.

Therefore:
**LOGIN_REAPPLY = NOT_PROVEN**

This is an important migration requirement:
850 implementation should have one explicit idempotent login/recalc hook that reapplies the derived prestige bonus exactly once.

Do not rely on incidental stat reconstruction.

## 850 comparison

No equivalent 850 implementation was found for:
- PrestigeLv
- RewardPrestigeTable
- NpcPrestigeTable
- w_威望*
- dead_score
- pr_type_name prestige display layer

Therefore this is not a DB-only migration.

## Client dependency

Current proven behavior uses ordinary server-side:
- title prefix text
- server messages
- S_SkillSound

No dedicated custom client protocol was proven.

If custom gfx IDs referenced by `gfxid` are non-native target assets, resource validation is still required.

Classification:
- server core dependency = YES
- custom client protocol dependency = NOT_PROVEN
- client resource dependency = POSSIBLE when gfxid != 0

## Recommended package

```
prestige-core/
  prestige score state
  rank derivation
  add/remove/recalc
  character persistence
  death penalty config
  display-name abstraction

prestige-monster-rewards/
  w_威望怪物
  monster-death hook adapter

prestige-ranks/
  w_威望設置
  rank bonus definitions

prestige-item-producers/
  add.Prestige executor
  participating etcitem rows
```

These should remain independently controllable:
- rank core must not require monster rewards
- monster rewards must not require prestige items
- item producers must not require monster reward data

## Difficulty

**L3 confirmed**

Reason:
- character persistent state
- monster death hook
- player stat mutation/reversal
- death hook
- optional timed gfx
- item executor producer

No evidence currently justifies L4.

## Validation gates for implementation

PASS only if:
1. score persists across restart
2. rank derives correctly from DB range
3. login/recalc applies exactly one copy of bonuses
4. rank up removes old bonus then applies new
5. rank down does the same
6. death penalty works
7. monster min/max RNG is corrected
8. module can run without monster-reward package
9. optional item producers can be installed independently
10. gfx IDs are validated against target resources when enabled

## Status

```
STATUS=PASS
MODULE=w_威望*
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
CHARACTER_COLUMN=PrestigeLv(score, not rank)
MONSTER_HOOK=L1MonsterInstance death reward
DEATH_HOOK=ConfigOther.dead_score
LOGIN_REAPPLY=NOT_PROVEN
CLIENT_PROTOCOL_DEP=NOT_PROVEN
CLIENT_RESOURCE_DEP=POSSIBLE
```
