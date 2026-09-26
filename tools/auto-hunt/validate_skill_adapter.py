from pathlib import Path
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[2]
CASTER = ROOT / 'recovered-src-obf/auto/hunt/AutoHunt850SkillCaster.java'
if not CASTER.exists():
    raise AssertionError('missing AutoHunt850SkillCaster.java')
source = CASTER.read_text(encoding='utf-8')

required = [
    'ao.be.a().a(skillId)',
    'bi.g.a(skillId)',
    'pc.fq().q()',
    'pc.h(skillId)',
    'skill.h()',
    'skill.p()',
    'skill.q()',
    'pc.fu().e(target.fu())',
    'pc.fu().c(target.fu())',
    'pc.i(target.fs(), target.ft())',
    'executor.a(pc, target.fr(), skillId)',
    'executor.a(pc, target.fr(), skillId, target.fs(), target.ft(), null)',
]
for needle in required:
    assert needle in source, f'missing 850 skill adapter contract: {needle}'

for forbidden in [
    'pc.ce().a(',
    'pc.i_(pc.eb()',
    'pc.a(pc.ea()',
    'target.b(',
]:
    assert forbidden not in source, f'auto-hunt skill adapter must not bypass native executor: {forbidden}'

files = {
    'ap/u.java': '''package ap;
public class u extends aq.f {
 public boolean aR(){return false;} public boolean eX(){return false;}
 public boolean h(int id){return true;} public ax.b fq(){return new ax.b();}
}
''',
    'ap/s.java': '''package ap;
public class s extends aq.f { public int ac(){return 0;} }
''',
    'aq/f.java': '''package aq;
public class f {
 public int fr(){return 1;} public int fp(){return 1;} public int ea(){return 100;} public boolean eX(){return false;}
 public int fs(){return 10;} public int ft(){return 10;} public u fu(){return new u();}
 public boolean i(int x,int y){return true;}
}
''',
    'aq/u.java': '''package aq;
public class u { public boolean e(u other){return true;} public int c(u other){return 1;} }
''',
    'ax/b.java': 'package ax; public class b { public boolean q(){return true;} }\n',
    'bh/v.java': '''package bh;
public class v { public int h(){return 500;} public int p(){return 3;} public int q(){return 0;} }
''',
    'ao/be.java': '''package ao;
public class be { private static final be I=new be(); public static be a(){return I;} public bh.v a(int id){return new bh.v();} }
''',
    'bf/a.java': '''package bf;
public class a {
 public boolean a(aq.f user,int target,int skill){return true;}
 public void a(aq.f user,int target,int skill,int x,int y,String m){}
}
''',
    'bi/g.java': 'package bi; public class g { public static bf.a a(int id){return new bf.a();} }\n',
}

with tempfile.TemporaryDirectory(prefix='auto-hunt-skill-adapter-') as temp:
    temp = Path(temp)
    for rel, content in files.items():
        path = temp / rel
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content, encoding='utf-8')
    (temp / 'auto/hunt').mkdir(parents=True, exist_ok=True)
    shutil.copyfile(CASTER, temp / 'auto/hunt/AutoHunt850SkillCaster.java')
    shutil.copyfile(ROOT / 'recovered-src-obf/auto/hunt/AutoHuntSkillController.java',
                    temp / 'auto/hunt/AutoHuntSkillController.java')
    out = temp / 'out'; out.mkdir()
    compile_run = subprocess.run(
        ['javac', '-d', str(out)] + [str(p) for p in temp.rglob('*.java')],
        text=True, capture_output=True)
    if compile_run.returncode != 0:
        raise AssertionError('skill adapter stub compile failed:\n' + compile_run.stderr)

print('AUTO_HUNT_850_SKILL_ADAPTER=PASS')
