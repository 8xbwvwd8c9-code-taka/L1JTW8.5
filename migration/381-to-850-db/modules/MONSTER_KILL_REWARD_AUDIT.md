# 381 -> 850 Monster Kill Reward System Audit

## Scope
Module:
- `w_怪物擊殺系統`

Core:
- `L1MonTable`
- `L1Mon`
- `L1AttackPc`
- `L1MonsterInstance`
- `L1PcInstance._npcdmg/_npciddmg`

## DB semantics

Fields used by runtime:
- Npc編號
- 共同攻擊獎勵
- 尾刀攻擊者獎勵
- 共同攻擊者傳送 X/Y/map
- 尾刀攻擊者傳送 X/Y/map
- 打擊怪物總傷害最低值

Current donor content includes ordinary and world-boss style monsters.

## Source DB authority

Current split SQL artifact is INSERT-only and does not include a CREATE schema.

More importantly, the current INSERT column list does **not** include `Npc編號`, while `L1MonTable.load()` requires `rs.getInt("Npc編號")` as the rule key.

Therefore:
- framework semantics are sufficiently proven for difficulty classification
- an installable migration schema/data set is **not** proven
- do not infer `Npc編號` values from row order or NPC names
- authoritative CREATE schema and keyed source rows are required before generating install.sql

## Loader

`L1MonTable.load()` loads `w_怪物擊殺系統` into `L1Mon` templates keyed by NPC ID.

## Damage accumulation

`L1AttackPc` checks whether the target NPC ID exists in `L1MonTable`.

If yes:
- add current physical attack damage to `pc._npcdmg`
- if `_npciddmg == 0`, store target NPC ID
- if stored NPC ID differs from current target NPC ID, reset accumulated damage to zero

This is player-local ephemeral state.
No DB persistence is used for damage accumulation.

## Death settlement

`L1MonsterInstance.distributeExpDropKarma()` handles two reward channels.

### Last-hit attacker
For the resolved killer `pc`:
- optional last-hit teleport
- if last-hit reward item > 0:
  - require `pc.getnpcdmg() > threshold`
  - grant one configured item
  - broadcast reward
  - append local text log

### Shared attackers
For every visible player within radius 15 at death:
- optional shared teleport
- if shared reward item > 0:
  - require `tgpc.getnpcdmg() > threshold`
  - grant one configured item
  - reset tgpc damage state
- otherwise report insufficient damage

This is not based on hate-list contributor identity.
It is based on:
1. player being visible within radius 15 at settlement time
2. player's own ephemeral accumulated damage state satisfying the threshold

## Important donor defects / semantic hazards

### 1. Tracks NPC template ID, not monster object ID

`_npciddmg` stores:
`targetNpc.getNpcTemplate().get_npcId()`

It does NOT store the spawned monster object ID.

Therefore two simultaneously alive monsters with the same NPC template are indistinguishable to the accumulator.

A player can accumulate damage against one instance and potentially carry that value to another instance of the same NPC ID.

Migration should key damage by target object ID/spawn instance, not NPC template ID.

### 2. Shared reward scans nearby players, not proven contributors

Death settlement iterates:
`World.getVisibleObjects(this, 15)`

It does not use the monster hate/damage contributor list as the authority.

Therefore a player with stale matching accumulated state can qualify merely by being nearby.

Migration should settle against an explicit per-monster contribution map.

### 3. Threshold uses strict greater-than

Donor condition:
`damage > configured minimum`

Thus exactly meeting the configured threshold does NOT qualify.

If field semantics are "最低值", target implementation should normally use `>=` unless donor-exact behavior is explicitly desired.

### 4. Damage accumulation coverage is incomplete

The proven accumulator is in `L1AttackPc` physical player attack path.

Equivalent accumulation was not proven for:
- magic damage
- pet damage
- summon damage
- DOT/effect damage
- reflected damage

Therefore "total damage" is not truly total combat contribution in the current donor implementation.

### 5. Reset behavior is fragile

Damage state is stored on the player rather than the monster.
It is reset on:
- settlement paths
- mismatch with a different NPC template
- some player lifecycle paths

This can leave stale state if the configured monster despawns, teleports, resets, or dies through an untracked path.

## 850 comparison

Current 850 search did not confirm:
- L1MonTable
- L1Mon
- getnpcdmg/setnpcdmg
- equivalent per-player configured boss contribution subsystem

Therefore no native equivalent is currently proven.

## Client dependency

Current features use ordinary server behavior:
- item grant
- server messages
- teleport
- broadcast

No dedicated custom client packet/UI is required by the proven runtime.

Classification:
- client protocol dependency = NO based on current evidence
- client resource dependency = only whatever reward items/maps already require

## Recommended redesign for 850

Do NOT port the player-global `_npcdmg/_npciddmg` model verbatim.

Use a per-monster contribution tracker:

```
monster-kill-reward-core/
  rule by npc_id
  per spawned monster object:
    contributor_obj_id -> accumulated damage
  damage source normalization
  threshold evaluation
  final-hit reward
  contributor reward
  optional teleport
  cleanup on death/despawn/reset
```

Adapters should normalize:
- direct melee/ranged
- magic
- pet/summon
- DOT/effect
according to desired ownership rules.

## Package split

```
monster-kill-reward-core/
monster-kill-reward-rules/
monster-kill-reward-items/
```

Rule DB should remain independently removable.

## Difficulty

**L3 confirmed**

Reason:
- combat damage tracking hook
- monster death settlement hook
- per-combat transient state
- rewards and teleport

No evidence justifies L4 because no custom client dependency is required.

## Status

```
STATUS=BLOCKED
AUDIT=PASS
MODULE=w_怪物擊殺系統
LEVEL=L3
CORE_DEP=YES
DB_DEP=YES
DAMAGE_HOOK=L1AttackPc
DEATH_HOOK=L1MonsterInstance.distributeExpDropKarma
PERSISTENCE=NONE_FOR_DAMAGE
CLIENT_DEP=NO
NATIVE_850_EQUIVALENT=NOT_FOUND
SOURCE_SCHEMA=NOT_PROVEN
SOURCE_KEY_DATA=NOT_PROVEN
BLOCKER=Npc編號 required by loader but absent from current split INSERT column list
```
