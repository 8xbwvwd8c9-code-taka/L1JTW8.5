from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
SERVICE = (ROOT / 'recovered-src-obf/auto/hunt/AutoHuntService.java').read_text(encoding='utf-8')
ATTACKER = (ROOT / 'recovered-src-obf/auto/hunt/AutoHunt850Attacker.java').read_text(encoding='utf-8')

required_attacker = [
    'public int attackRange()',
    'AutoHuntBasicAttackRange.resolve',
    'weapon.a().aB()',
]
for needle in required_attacker:
    assert needle in ATTACKER, f'missing physical attack range adapter: {needle}'

required_service = [
    'int basicAttackRange = attacker.attackRange()',
    'mover.moveToward(target, basicAttackRange)',
    'attacker.attack(target, basicAttackRange)',
]
for needle in required_service:
    assert needle in SERVICE, f'missing physical attack range service wiring: {needle}'

assert 'mover.moveToward(target, provider.engageRange())' not in SERVICE, \
    'Move must not use magic-extended target engage range during Basic Attack stage'

print('AUTO_HUNT_SERVICE_BASIC_ATTACK_RANGE=PASS')
