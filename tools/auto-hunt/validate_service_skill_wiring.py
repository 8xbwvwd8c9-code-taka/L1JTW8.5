from pathlib import Path
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[2]
SERVICE = ROOT / 'recovered-src-obf/auto/hunt/AutoHuntService.java'
source = SERVICE.read_text(encoding='utf-8')

required = [
    'new AutoHunt850SkillCaster',
    'settings.autoMagicOn()',
    'settings.singleSkillId()',
    'AutoHunt850SkillCaster.Result.CAST_ATTEMPTED',
    'AutoHunt850SkillCaster.Result.INVALID_TARGET',
    'skillCaster.reset()',
]
for needle in required:
    assert needle in source, f'missing service skill wiring: {needle}'

files = {
    'ap/u.java': 'package ap; public class u { public int fr(){return 1;} }\n',
    'ap/s.java': 'package ap; public class s { }\n',
    'auto/hunt/AutoHuntRuntimeSettings.java': '''package auto.hunt;
public final class AutoHuntRuntimeSettings {
 public static boolean magic = true; public static int skill = 10;
 public boolean autoMagicOn(){return magic;} public int singleSkillId(){return skill;}
}
''',
    'auto/hunt/AutoHuntBossIndex.java': 'package auto.hunt; public final class AutoHuntBossIndex { }\n',
    'auto/hunt/AutoHuntBossIndexLoader.java': 'package auto.hunt; public final class AutoHuntBossIndexLoader { public static void load(AutoHuntBossIndex i) throws java.sql.SQLException { } }\n',
    'auto/hunt/AutoHuntTargetSelector.java': 'package auto.hunt; public interface AutoHuntTargetSelector { }\n',
    'auto/hunt/AutoHunt850TargetSelector.java': 'package auto.hunt; public final class AutoHunt850TargetSelector implements AutoHuntTargetSelector { public AutoHunt850TargetSelector(AutoHuntBossIndex i){} }\n',
    'auto/hunt/AutoHunt850TargetProvider.java': '''package auto.hunt;
public final class AutoHunt850TargetProvider implements AutoHunt850Session.TargetProvider {
 public AutoHunt850TargetProvider(ap.u pc, AutoHuntRuntimeSettings s, AutoHuntTargetSelector t) { }
 public ap.s select(){return new ap.s();}
}
''',
    'auto/hunt/AutoHuntMoveController.java': 'package auto.hunt; public final class AutoHuntMoveController { public enum Result { INVALID_TARGET, IN_RANGE, COOLDOWN, MOVED, UNREACHABLE, STEP_REJECTED } }\n',
    'auto/hunt/AutoHuntAttackController.java': 'package auto.hunt; public final class AutoHuntAttackController { public enum Result { INVALID_TARGET, BLOCKED, OUT_OF_RANGE, COOLDOWN, ATTACKED, ATTACK_REJECTED } }\n',
    'auto/hunt/AutoHunt850Mover.java': '''package auto.hunt;
public final class AutoHunt850Mover {
 public static AutoHuntMoveController.Result next = AutoHuntMoveController.Result.IN_RANGE;
 public static int calls, resets;
 public AutoHunt850Mover(ap.u pc) { }
 public AutoHuntMoveController.Result moveToward(ap.s target, int range){calls++;return next;}
 public void reset(){resets++;}
}
''',
    'auto/hunt/AutoHunt850Attacker.java': '''package auto.hunt;
public final class AutoHunt850Attacker {
 public static AutoHuntAttackController.Result next = AutoHuntAttackController.Result.ATTACKED;
 public static int calls, resets;
 public AutoHunt850Attacker(ap.u pc) { }
 public int attackRange(){return 1;}
 public AutoHuntAttackController.Result attack(ap.s target, int range){calls++;return next;}
 public void reset(){resets++;}
}
''',
    'auto/hunt/AutoHunt850SkillCaster.java': '''package auto.hunt;
public final class AutoHunt850SkillCaster {
 public enum Result { INVALID_SKILL, UNSUPPORTED_SKILL, INVALID_TARGET, BLOCKED, OUT_OF_RANGE, COOLDOWN, CAST_ATTEMPTED, CAST_REJECTED }
 public static Result next = Result.COOLDOWN; public static int calls, resets;
 public AutoHunt850SkillCaster(ap.u pc) { }
 public Result cast(ap.s target,int skillId){calls++;return next;}
 public void reset(){resets++;}
}
''',
    'auto/hunt/AutoHunt850Session.java': '''package auto.hunt;
public final class AutoHunt850Session {
 public interface TargetProvider { ap.s select(); }
 public interface TargetAction { boolean onTarget(ap.s t); void reset(); }
 public static TargetAction lastAction;
 private boolean running;
 public AutoHunt850Session(ap.u pc,long p){}
 public AutoHunt850Session(ap.u pc,long p,TargetProvider provider,TargetAction action){lastAction=action;}
 public long start(){running=true;return 1;}
 public void stop(){if(running&&lastAction!=null)lastAction.reset();running=false;}
 public boolean isRunning(){return running;}
}
''',
    'ServiceSkillHarness.java': '''import auto.hunt.*;
public final class ServiceSkillHarness {
 public static void main(String[] args) {
  ap.u pc=new ap.u(); AutoHuntRuntimeSettings settings=new AutoHuntRuntimeSettings();
  AutoHuntService.start(pc,settings); ap.s target=new ap.s();

  AutoHunt850SkillCaster.next=AutoHunt850SkillCaster.Result.CAST_ATTEMPTED;
  check(AutoHunt850Session.lastAction.onTarget(target),"cast keeps target");
  check(AutoHunt850SkillCaster.calls==1,"skill must be attempted first");
  check(AutoHunt850Mover.calls==0 && AutoHunt850Attacker.calls==0,"successful skill consumes tick");

  AutoHunt850SkillCaster.next=AutoHunt850SkillCaster.Result.COOLDOWN;
  AutoHunt850Mover.next=AutoHuntMoveController.Result.IN_RANGE;
  check(AutoHunt850Session.lastAction.onTarget(target),"cooldown falls through to basic action");
  check(AutoHunt850Mover.calls==1 && AutoHunt850Attacker.calls==1,"basic move/attack fallback expected");

  AutoHunt850SkillCaster.next=AutoHunt850SkillCaster.Result.INVALID_TARGET;
  check(!AutoHunt850Session.lastAction.onTarget(target),"invalid skill target must clear session target");

  AutoHuntService.stop(pc);
  check(AutoHunt850SkillCaster.resets==1 && AutoHunt850Mover.resets==1 && AutoHunt850Attacker.resets==1,
        "stop must reset skill/move/attack clocks");
  System.out.println("AUTO_HUNT_SERVICE_SKILL_WIRING=PASS");
 }
 static void check(boolean v,String m){if(!v)throw new AssertionError(m);}
}
''',
}

with tempfile.TemporaryDirectory(prefix='auto-hunt-service-skill-') as temp:
    temp=Path(temp)
    for rel,content in files.items():
        path=temp/rel; path.parent.mkdir(parents=True,exist_ok=True); path.write_text(content,encoding='utf-8')
    shutil.copyfile(SERVICE,temp/'auto/hunt/AutoHuntService.java')
    out=temp/'out';out.mkdir()
    compile_run=subprocess.run(['javac','-d',str(out)]+[str(p) for p in temp.rglob('*.java')],text=True,capture_output=True)
    if compile_run.returncode!=0: raise AssertionError('service skill compile failed:\n'+compile_run.stderr)
    run=subprocess.run(['java','-cp',str(out),'ServiceSkillHarness'],text=True,capture_output=True)
    if run.returncode!=0: raise AssertionError('service skill harness failed:\n'+run.stdout+run.stderr)
    print(run.stdout.strip())
