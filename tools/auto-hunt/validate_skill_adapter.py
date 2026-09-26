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
    'nowMs < controller.nextCastAtMs(skillId)',
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
public class s extends aq.f {
 public static boolean dead; public static int hp=100; public static int map=1; public static int hidden;
 @Override public boolean eX(){return dead;} @Override public int ea(){return hp;}
 @Override public int fp(){return map;} public int ac(){return hidden;}
}
''',
    'aq/f.java': '''package aq;
public class f {
 public int fr(){return 1;} public int fp(){return 1;} public int ea(){return 100;} public boolean eX(){return false;}
 public int fs(){return 10;} public int ft(){return 10;} public u fu(){return new u();}
 public boolean i(int x,int y){return true;}
}
''',
    'aq/u.java': '''package aq;
public class u {
 public static boolean screen=true; public static int distance=1;
 public boolean e(u other){return screen;} public int c(u other){return distance;}
}
''',
    'ax/b.java': 'package ax; public class b { public static boolean usable=true; public boolean q(){return usable;} }\n',
    'bh/v.java': '''package bh;
public class v {
 public static int reuse=500, range=3, area=0;
 public int h(){return reuse;} public int p(){return range;} public int q(){return area;}
}
''',
    'ao/be.java': '''package ao;
public class be { private static final be I=new be(); public static be a(){return I;} public bh.v a(int id){return id==999?null:new bh.v();} }
''',
    'bf/a.java': '''package bf;
public class a {
 public static boolean allow=true; public static int preflight, issued;
 public boolean a(aq.f user,int target,int skill){preflight++;return allow;}
 public void a(aq.f user,int target,int skill,int x,int y,String m){issued++;}
}
''',
    'bi/g.java': 'package bi; public class g { public static bf.a a(int id){return id==998?null:new bf.a();} }\n',
    'SkillCasterHarness.java': '''import auto.hunt.*;
public final class SkillCasterHarness {
 public static void main(String[] args) {
  ap.u pc=new ap.u(); ap.s target=new ap.s(); AutoHunt850SkillCaster caster=new AutoHunt850SkillCaster(pc);

  check(caster.cast(target,10,1000L)==AutoHunt850SkillCaster.Result.CAST_ATTEMPTED,"first cast");
  check(bf.a.issued==1,"native executor must receive first cast");
  int preflightAfterFirst=bf.a.preflight;
  check(caster.cast(target,10,1200L)==AutoHunt850SkillCaster.Result.COOLDOWN,"same skill cooldown");
  check(bf.a.preflight==preflightAfterFirst,"own cooldown must gate before native preflight");
  check(bf.a.issued==1,"cooldown must suppress native cast");

  check(caster.cast(target,11,1200L)==AutoHunt850SkillCaster.Result.CAST_ATTEMPTED,"different skill independent cooldown");
  check(bf.a.issued==2,"different skill may cast immediately");

  bh.v.area=1;
  check(caster.cast(target,12,1200L)==AutoHunt850SkillCaster.Result.UNSUPPORTED_SKILL,"aoe excluded from single-target phase");
  bh.v.area=0;

  aq.u.distance=4; bh.v.range=3;
  check(caster.cast(target,13,1200L)==AutoHunt850SkillCaster.Result.OUT_OF_RANGE,"range gate");
  aq.u.distance=1; bh.v.range=3;

  bf.a.allow=false;
  check(caster.cast(target,14,1200L)==AutoHunt850SkillCaster.Result.BLOCKED,"native preflight remains authoritative");
  bf.a.allow=true;

  check(caster.cast(target,999,1200L)==AutoHunt850SkillCaster.Result.INVALID_SKILL,"missing template");
  check(caster.cast(target,998,1200L)==AutoHunt850SkillCaster.Result.INVALID_SKILL,"missing executor");

  caster.reset();
  check(caster.cast(target,10,1201L)==AutoHunt850SkillCaster.Result.CAST_ATTEMPTED,"reset clears per-skill cooldown");
  System.out.println("AUTO_HUNT_850_SKILL_ADAPTER=PASS");
 }
 static void check(boolean v,String m){if(!v)throw new AssertionError(m);}
}
''',
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
    run = subprocess.run(['java', '-cp', str(out), 'SkillCasterHarness'], text=True, capture_output=True)
    if run.returncode != 0:
        raise AssertionError('skill adapter harness failed:\n' + run.stdout + run.stderr)
    print(run.stdout.strip())
