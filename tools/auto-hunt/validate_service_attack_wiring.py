from pathlib import Path
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[2]
SERVICE = ROOT / 'recovered-src-obf/auto/hunt/AutoHuntService.java'
source = SERVICE.read_text(encoding='utf-8')

required = [
    'new AutoHunt850Attacker',
    'int basicAttackRange = attacker.attackRange()',
    'mover.moveToward(target, basicAttackRange)',
    'moveResult == AutoHuntMoveController.Result.IN_RANGE',
    'attacker.attack(target, basicAttackRange)',
    'AutoHuntAttackController.Result.INVALID_TARGET',
    'attacker.reset()',
]
for needle in required:
    assert needle in source, f'missing service attack wiring: {needle}'

assert 'attacker.attack(target, provider.engageRange())' not in source, \
    'Basic Attack must not use magic-extended engage range'

files = {
    'ap/u.java': 'package ap; public class u { public int fr(){return 1;} }\n',
    'ap/s.java': 'package ap; public class s { }\n',
    'auto/hunt/AutoHuntRuntimeSettings.java': 'package auto.hunt; public final class AutoHuntRuntimeSettings { }\n',
    'auto/hunt/AutoHuntBossIndex.java': 'package auto.hunt; public final class AutoHuntBossIndex { }\n',
    'auto/hunt/AutoHuntBossIndexLoader.java': 'package auto.hunt; public final class AutoHuntBossIndexLoader { public static void load(AutoHuntBossIndex i) throws java.sql.SQLException { } }\n',
    'auto/hunt/AutoHuntTargetSelector.java': 'package auto.hunt; public interface AutoHuntTargetSelector { }\n',
    'auto/hunt/AutoHunt850TargetSelector.java': 'package auto.hunt; public final class AutoHunt850TargetSelector implements AutoHuntTargetSelector { public AutoHunt850TargetSelector(AutoHuntBossIndex i){} }\n',
    'auto/hunt/AutoHunt850TargetProvider.java': '''package auto.hunt;
public final class AutoHunt850TargetProvider implements AutoHunt850Session.TargetProvider {
 public AutoHunt850TargetProvider(ap.u pc, AutoHuntRuntimeSettings s, AutoHuntTargetSelector t) { }
 public int engageRange(){return 9;}
 public ap.s select(){return new ap.s();}
}
''',
    'auto/hunt/AutoHuntMoveController.java': 'package auto.hunt; public final class AutoHuntMoveController { public enum Result { INVALID_TARGET, IN_RANGE, COOLDOWN, MOVED, UNREACHABLE, STEP_REJECTED } }\n',
    'auto/hunt/AutoHuntAttackController.java': 'package auto.hunt; public final class AutoHuntAttackController { public enum Result { INVALID_TARGET, BLOCKED, OUT_OF_RANGE, COOLDOWN, ATTACKED, ATTACK_REJECTED } }\n',
    'auto/hunt/AutoHunt850Mover.java': '''package auto.hunt;
public final class AutoHunt850Mover {
 public static AutoHuntMoveController.Result next = AutoHuntMoveController.Result.IN_RANGE;
 public static int calls, resets, lastRange;
 public AutoHunt850Mover(ap.u pc) { }
 public AutoHuntMoveController.Result moveToward(ap.s target, int range){calls++;lastRange=range;return next;}
 public void reset(){resets++;}
}
''',
    'auto/hunt/AutoHunt850Attacker.java': '''package auto.hunt;
public final class AutoHunt850Attacker {
 public static AutoHuntAttackController.Result next = AutoHuntAttackController.Result.ATTACKED;
 public static int calls, resets, lastRange;
 public AutoHunt850Attacker(ap.u pc) { }
 public int attackRange(){return 3;}
 public AutoHuntAttackController.Result attack(ap.s target, int range){calls++;lastRange=range;return next;}
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
    'ServiceAttackHarness.java': '''import auto.hunt.*;
public final class ServiceAttackHarness {
 public static void main(String[] args) {
  ap.u pc=new ap.u();
  AutoHuntService.start(pc,new AutoHuntRuntimeSettings());
  ap.s target=new ap.s();

  AutoHunt850Mover.next=AutoHuntMoveController.Result.MOVED;
  check(AutoHunt850Session.lastAction.onTarget(target),"moved keeps target");
  check(AutoHunt850Attacker.calls==0,"must not attack in same tick as move");
  check(AutoHunt850Mover.lastRange==3,"move must use physical attack range");

  AutoHunt850Mover.next=AutoHuntMoveController.Result.IN_RANGE;
  AutoHunt850Attacker.next=AutoHuntAttackController.Result.ATTACKED;
  check(AutoHunt850Session.lastAction.onTarget(target),"attacked keeps target");
  check(AutoHunt850Attacker.calls==1,"in-range must invoke attacker");
  check(AutoHunt850Attacker.lastRange==3,"attack must use physical attack range");

  AutoHunt850Attacker.next=AutoHuntAttackController.Result.BLOCKED;
  check(AutoHunt850Session.lastAction.onTarget(target),"temporary blocked state keeps target");

  AutoHunt850Attacker.next=AutoHuntAttackController.Result.INVALID_TARGET;
  check(!AutoHunt850Session.lastAction.onTarget(target),"invalid attack target must clear target");

  AutoHuntService.stop(pc);
  check(AutoHunt850Mover.resets==1 && AutoHunt850Attacker.resets==1,"stop resets both action clocks");
  System.out.println("AUTO_HUNT_SERVICE_ATTACK_WIRING=PASS");
 }
 static void check(boolean v,String m){if(!v)throw new AssertionError(m);}
}
''',
}

with tempfile.TemporaryDirectory(prefix='auto-hunt-service-attack-') as temp:
    temp=Path(temp)
    for rel,content in files.items():
        path=temp/rel; path.parent.mkdir(parents=True,exist_ok=True); path.write_text(content,encoding='utf-8')
    shutil.copyfile(SERVICE,temp/'auto/hunt/AutoHuntService.java')
    out=temp/'out';out.mkdir()
    compile_run=subprocess.run(['javac','-d',str(out)]+[str(p) for p in temp.rglob('*.java')],text=True,capture_output=True)
    if compile_run.returncode!=0: raise AssertionError('service attack compile failed:\n'+compile_run.stderr)
    run=subprocess.run(['java','-cp',str(out),'ServiceAttackHarness'],text=True,capture_output=True)
    if run.returncode!=0: raise AssertionError('service attack harness failed:\n'+run.stdout+run.stderr)
    print(run.stdout.strip())
