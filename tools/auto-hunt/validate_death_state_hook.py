from pathlib import Path

p = Path('recovered-src-obf/aq/f.java')
s = p.read_text(encoding='utf-8')

assert 'import auto.hunt.AutoHuntService;' in s, 'missing AutoHuntService import'
needle = '''    public void X(boolean flag) {\n        if (flag && this instanceof u) {\n            AutoHuntService.stop((u)this);\n        }\n        this.ap = flag;\n    }'''
assert needle in s, 'death-state setter must synchronously stop player auto-hunt before setting dead flag'
assert s.count('AutoHuntService.stop((u)this);') == 1, 'death hook must be exactly once'
print('AUTO_HUNT_DEATH_STATE_HOOK=PASS')
