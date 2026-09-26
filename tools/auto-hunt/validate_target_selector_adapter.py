from pathlib import Path

selector = Path('recovered-src-obf/auto/hunt/AutoHunt850TargetSelector.java').read_text(encoding='utf-8')
bridge = Path('recovered-src-obf/ap/AutoHuntTargetBridge.java').read_text(encoding='utf-8')

required = [
    'aq.aq.a().b((aa)pc, TARGET_RANGE)',
    'mob.U_().b()',
    'mob.bB(33)',
    'mob.bB(50)',
    'mob.bB(1011)',
    'mob.bB(1009)',
    'mob.bB(pc.fr() + 100000)',
    'pc.c(mob.fs(), mob.ft(), 1)',
    'mob.ac() > 0',
    'mob.O()',
    'AutoHuntTargetBridge.currentTarget(mob)',
    'mob.c(currentTarget.fs(), currentTarget.ft(), mob.U_().s())',
]
for needle in required:
    assert needle in selector, f'missing verified 850 target contract: {needle}'

assert 'return npc == null ? null : npc.m;' in bridge, 'bridge must expose protected npc current target only'
assert 'TARGET_RANGE = 10' in selector, '381 target scan cap must remain 10'

print('AUTO_HUNT_850_TARGET_ADAPTER=PASS')
