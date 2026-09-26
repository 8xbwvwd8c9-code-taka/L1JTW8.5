# L1JTW8.5 Auto Hunt Phase-1 Basic Attack Log

DATE=2026-09-26
BRANCH=work/850-auto-hunting

## SCOPE
Close Phase-1 Basic Attack only. No skill casting, consumables, loot, supply, stuck teleport or advanced patrol behavior is added here.

## AUTHORITY / CALL PATH
850 repaired runtime is authoritative.

Native client attack path is `aj.d` / `C_Attack`:

```text
C_Attack
-> validate player / target / physical attack range / speed gate
-> clear attack-breaking effects
-> pc.e(1)
-> target.c(pc)
```

For NPC / monster targets, `ap.t.c(u)` is not a hate-only setter. It dispatches into the native combat engine:

```text
ap.t.c(u pc)
-> ap.t.a(pc, 0)
-> new aq.c(pc, this, skillId)
-> attack.a()
-> attack.b()
-> attack.a(pc, target)
-> attack.c()
-> attack.d()
```

Therefore `AutoHunt850Attacker.issueAttack()` using `target.c(pc)` reuses the repaired 850 `L1Attack` pipeline rather than duplicating damage calculation or mutating HP directly.

## PHYSICAL RANGE
850 `C_Attack` physical range behavior:

```text
no weapon -> 1
weapon range >= 0 -> weapon range
weapon range < 0 -> 15
```

`AutoHuntBasicAttackRange.resolve()` matches this contract exactly.

Important separation:
- Target Search may use a magic-extended engage range for candidate eligibility.
- Basic Move + Basic Attack use `attacker.attackRange()` only.
- Magic range must not enlarge physical attack range.

## IMPLEMENTATION
- `recovered-src-obf/auto/hunt/AutoHuntAttackController.java`
- `recovered-src-obf/auto/hunt/AutoHuntBasicAttackRange.java`
- `recovered-src-obf/auto/hunt/AutoHunt850Attacker.java`
- `recovered-src-obf/auto/hunt/AutoHuntService.java`
- `tools/auto-hunt/AutoHuntAttackControllerTest.java`
- `tools/auto-hunt/AutoHuntBasicAttackRangeTest.java`
- `tools/auto-hunt/validate_attack_adapter.py`
- `tools/auto-hunt/validate_service_attack_wiring.py`
- `tools/auto-hunt/validate_service_basic_attack_range.py`

## SESSION ACTION ORDER
Configured runtime target action is:

```text
select/retain target
-> resolve physical attack range
-> Move toward target using physical attack range
-> if MOVED / COOLDOWN / STEP_REJECTED: keep target, no attack this tick
-> if INVALID_TARGET / UNREACHABLE: clear target
-> if IN_RANGE: run Basic Attack
-> temporary attack BLOCKED / COOLDOWN / ATTACK_REJECTED: keep target
-> INVALID_TARGET from attacker: clear target
```

Move and Attack therefore remain separately paced actions; a successful movement does not also attack during the same tick.

## ATTACK BLOCKS / NATIVE STATE
`AutoHunt850Attacker` rejects invalid/dead/hidden/wrong-map targets and respects repaired 850 player states including teleport, dead, sleep/paralysis, overweight and attack-blocking skill effects before issuing the native attack entry.

Attack timing reads the native attack speed interval through:

```text
pc.ce().b(aq.ak.a.b)
```

Server-owned auto hunt does not call the incoming-client anti-speed validator mutation path.

## REGRESSION CORRECTION
An earlier service attack verifier still expected:

```text
attacker.attack(target, provider.engageRange())
```

After physical-range separation this expectation became stale and would create a false failure. The verifier was corrected to require:

```text
int basicAttackRange = attacker.attackRange()
mover.moveToward(target, basicAttackRange)
attacker.attack(target, basicAttackRange)
```

and explicitly rejects use of magic-extended engage range for Basic Attack.

## FRESH VALIDATION
Fresh stub compile/harness after the verifier correction:

```text
AUTO_HUNT_SERVICE_ATTACK_WIRING=PASS
SUITE_EXIT=0
```

Validated behaviors:
- movement retains target
- movement and attack do not execute in the same successful-move tick
- mover receives physical attack range
- IN_RANGE invokes attacker
- attacker receives the same physical attack range
- temporary blocked attack retains target
- INVALID_TARGET clears target
- Stop resets both Move and Attack timing state

Native attack call-path inspection also confirms `target.c(pc)` enters `aq.c` / L1Attack for monster/NPC targets.

## PHASE-1 STATUS
- Lifecycle = CLOSED
- Target Search = CLOSED at core/runtime-contract level
- Basic Move = CLOSED
- Basic Attack = CLOSED
- 880 UI/settings transport = still BLOCKED_BY_DONOR_EVIDENCE and intentionally not guessed
- Full recovered-source project compile = NOT_CLAIMED

NEXT=Skill module planning and regression. Skills must use native 850 skill APIs, explicit cooldowns, and the previously agreed rule that Boss skills must not include healing/self-recovery behavior.
