# L1JTW8.5 Auto Hunt Phase-2 Single-Target Skill Log

DATE=2026-09-26
BRANCH=work/850-auto-hunting

## SCOPE
- Player auto-hunt single-target active skill execution only.
- Does NOT modify or reference elite/Boss monster skill pools.
- Does NOT add auto-buff, auto-heal, AOE, consumables, loot, or direct HP/MP mutation.

## 850 NATIVE AUTHORITY
`aj.cr` / C_UseSkill dispatches through `bi.g.a(skillId)` to a `bf.a` executor.
The native executor remains authoritative for skill legality and effects, including target/range rules, MP/HP/item consumption, lawful changes, packets, effects, and native reuse behavior.

Confirmed `skills` mapping through `ao.be` + `bh.v`:
- `skill.h()` = reuseDelay
- `skill.p()` = ranged
- `skill.q()` = area

Native reuse-delay unit is milliseconds: `bf.a` passes `skill.h()` to `bg.a`, which schedules through `bi.e` using `TimeUnit.MILLISECONDS`.

Auto-hunt intentionally does NOT call client anti-speed mutation `pc.ce().a(...)` and does NOT duplicate MP/HP/item deduction.

## IMPLEMENTATION
### AutoHuntSkillController
`recovered-src-obf/auto/hunt/AutoHuntSkillController.java`
- one cooldown deadline per skillId
- independent skill cooldowns
- overflow-safe deadline saturation
- rejected final attempt does not consume auto-hunt cooldown
- reset clears every skill deadline

### AutoHunt850SkillCaster
`recovered-src-obf/auto/hunt/AutoHunt850SkillCaster.java`
- requires valid skill template and native executor
- single-target only: `area == 0`
- special payload skills 5/58/63/69/116/118 are excluded in this phase
- validates live monster/map/learned-skill/range/LOS
- own cooldown gate executes before native executor preflight
- native `bf.a` preflight remains authoritative
- successful attempt dispatches through native executor
- auto-hunt cooldown uses `skills.reuseDelay` directly in milliseconds

### AutoHuntService
Action order is now:
1. configured single-target skill
2. Basic Move
3. Basic Attack

`CAST_ATTEMPTED` consumes the current tick, so Move/Basic Attack do not run in the same tick.
Cooldown/blocked/unsupported/out-of-range skill outcomes fall back to the existing basic path.
`INVALID_TARGET` clears the session target.
Stop/reset clears Skill + Move + Attack clocks.

## TDD / VERIFICATION ASSETS
- `tools/auto-hunt/AutoHuntSkillControllerTest.java`
- `tools/auto-hunt/validate_skill_adapter.py`
- `tools/auto-hunt/validate_service_skill_wiring.py`

RED/GREEN history:
- controller RED: class missing
- controller GREEN: independent cooldown / reject / overflow / reset
- adapter RED: adapter missing
- adapter GREEN: native contract stub compile
- strengthened adapter RED: own cooldown did not precede native preflight
- strengthened adapter GREEN: duplicate preflight suppressed while other skillId remains independent
- service RED: no skill caster wiring
- service GREEN: skill-first/fallback/invalid-target/reset behavior

Fresh closure after final code:
- `AUTO_HUNT_SKILL_CONTROLLER_TEST=PASS`, compile=0, run=0
- `AUTO_HUNT_850_SKILL_ADAPTER=PASS`, compile=0, run=0
- `AUTO_HUNT_SERVICE_SKILL_WIRING=PASS`, compile=0, run=0

## EXPLICIT NON-SCOPE
- Boss/elite monster skill-pool rules are unrelated to this module.
- no Boss heal prohibition exists here
- no auto-heal/buff/AOE policy yet
- 880 UI/settings transport remains separate and unverified
- full recovered-source project compile is not claimed

STATUS=SINGLE_TARGET_ACTIVE_SKILL_CLOSED
NEXT=choose the next player auto-hunt module without mixing monster/Boss affix rules into player auto-hunt.
