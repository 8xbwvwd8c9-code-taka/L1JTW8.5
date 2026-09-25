# L1JTW8.5 Auto Hunt Phase-1 Target Policy Log

DATE=2026-09-26
BRANCH=work/850-auto-hunting

## DONOR / AUTHORITY
- Behavior donor: L381 `L1PcInstance.findAtuTarget()` / `isAtuSelectableTarget()`.
- Runtime authority: 850 repaired core on this branch.
- 850 class mapping confirmed: `ap.s=L1MonsterInstance`, `ap.u=L1PcInstance`, `aq.aq=L1World`.

## 381 RULES PORTED
- Candidate set is monsters only.
- Search cap is 10 tiles.
- Nearest selectable monster wins.
- Boss excluded unless configured to be treated as normal.
- Exclude NPC 81257.
- Exclude blocked-effect targets.
- Exclude dead / HP<=0 / hidden / attackSpeed==0.
- Exclude unreachable targets.
- Patrol mode allows a target outside the patrol radius only when boundary overflow is within engage range.

## IMPLEMENTATION
- `recovered-src-obf/auto/hunt/AutoHuntTargetPolicy.java`
- `tools/auto-hunt/AutoHuntTargetPolicyTest.java`

## VALIDATION
RED: missing `AutoHuntTargetPolicy` -> javac failed as expected.
GREEN: `javac auto/hunt/AutoHuntTargetPolicy.java AutoHuntTargetPolicyTest.java` -> exit 0.
RUN: `AUTO_HUNT_TARGET_POLICY_TEST=PASS`.

## 850 ADAPTER STATUS
Confirmed:
- monster class `ap.s`
- player class `ap.u`
- world class `aq.aq`
- HP/dead state are available through repaired character APIs
- NPC template ID is `mob.U_().b()`
- hidden status is `mob.ac()`; recovered setter parameter is explicitly named `hiddenStatus`
- attack speed is `mob.O()`; recovered setter parameter is explicitly named `atkspeed`
- 381 `isAttackPosition(x,y,1)` can map to 850 character `pc.c(x,y,1)` range + line-of-sight gate

Boss classification finding:
- 850 `npc` template loader (`ao.au` / NpcTable) does not load an `is_boss` field.
- 850 boss spawns are loaded separately from `spawnlist_boss` by `ao.e` / BossSpawnTable using `npc_id`.
- Therefore do NOT invent a boss getter on `bh.l`; adapter needs an explicit boss-NPC index sourced from `spawnlist_boss` (or a future 850-owned boss registry).

Still pending:
- implement boss-NPC index / resolver using 850 authority
- implement 850 target adapter over `ap.s` candidates and the verified accessors
- validate blocked-effect IDs 33/50/1011/1009 plus the per-player unreachable marker against 850 skill-effect APIs

NEXT=Boss index/resolver -> 850 target adapter -> adapter regression. Do not start Move/Basic Attack before adapter validation.
