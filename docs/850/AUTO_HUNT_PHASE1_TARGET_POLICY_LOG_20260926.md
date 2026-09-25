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
- NPC template ID is available from the 850 template object

Not yet proven / do not guess:
- exact obfuscated accessor for boss flag
- exact obfuscated accessor for hidden status
- exact obfuscated accessor for attack speed
- exact 850 reachability/line-of-attack hook to use for the 381 `isAttackPosition` gate

NEXT=Resolve those accessors, then implement the 850 target adapter. Do not start Move/Basic Attack before adapter validation.
