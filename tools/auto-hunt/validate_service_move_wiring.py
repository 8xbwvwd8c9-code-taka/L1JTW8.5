from pathlib import Path
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[2]
SERVICE = ROOT / 'recovered-src-obf/auto/hunt/AutoHuntService.java'
source = SERVICE.read_text(encoding='utf-8')

required = [
    'start(u pc, AutoHuntRuntimeSettings settings)',
    'AutoHuntBossIndexLoader.load(BOSS_INDEX)',
    'new AutoHunt850TargetProvider',
    'new AutoHunt850Mover',
    'provider.engageRange()',
    'Result.INVALID_TARGET',
    'Result.UNREACHABLE',
    'mover.reset()',
]
for needle in required:
    assert needle in source, f'missing service move wiring: {needle}'

files = {
    'ap/u.java': '''package ap;
public class u {
    public int id;
    public u(int id) { this.id = id; }
    public int fr() { return id; }
}
''',
    'ap/s.java': 'package ap; public class s { }\n',
    'auto/hunt/AutoHuntRuntimeSettings.java': 'package auto.hunt; public final class AutoHuntRuntimeSettings { }\n',
    'auto/hunt/AutoHuntBossIndex.java': 'package auto.hunt; public final class AutoHuntBossIndex { }\n',
    'auto/hunt/AutoHuntBossIndexLoader.java': '''package auto.hunt;
public final class AutoHuntBossIndexLoader {
    public static int loads;
    public static void load(AutoHuntBossIndex index) throws java.sql.SQLException { loads++; }
}
''',
    'auto/hunt/AutoHuntTargetSelector.java': 'package auto.hunt; public interface AutoHuntTargetSelector { }\n',
    'auto/hunt/AutoHunt850TargetSelector.java': '''package auto.hunt;
public final class AutoHunt850TargetSelector implements AutoHuntTargetSelector {
    public AutoHunt850TargetSelector(AutoHuntBossIndex index) { }
}
''',
    'auto/hunt/AutoHunt850TargetProvider.java': '''package auto.hunt;
public final class AutoHunt850TargetProvider implements AutoHunt850Session.TargetProvider {
    public AutoHunt850TargetProvider(ap.u pc, AutoHuntRuntimeSettings settings, AutoHuntTargetSelector selector) { }
    public int engageRange() { return 3; }
    public ap.s select() { return new ap.s(); }
}
''',
    'auto/hunt/AutoHuntMoveController.java': '''package auto.hunt;
public final class AutoHuntMoveController {
    public enum Result { INVALID_TARGET, IN_RANGE, COOLDOWN, MOVED, UNREACHABLE, STEP_REJECTED }
}
''',
    'auto/hunt/AutoHunt850Mover.java': '''package auto.hunt;
public final class AutoHunt850Mover {
    public static AutoHuntMoveController.Result next = AutoHuntMoveController.Result.IN_RANGE;
    public static int resets;
    public AutoHunt850Mover(ap.u pc) { }
    public AutoHuntMoveController.Result moveToward(ap.s target, int engageRange) { return next; }
    public void reset() { resets++; }
}
''',
    'auto/hunt/AutoHunt850Session.java': '''package auto.hunt;
public final class AutoHunt850Session {
    public interface TargetProvider { ap.s select(); }
    public interface TargetAction { boolean onTarget(ap.s target); void reset(); }
    public static TargetProvider lastProvider;
    public static TargetAction lastAction;
    private boolean running;
    private long generation = 1L;
    public AutoHunt850Session(ap.u pc, long period) { }
    public AutoHunt850Session(ap.u pc, long period, TargetProvider provider, TargetAction action) {
        lastProvider = provider;
        lastAction = action;
    }
    public long start() { running = true; return generation; }
    public void stop() { if (running && lastAction != null) lastAction.reset(); running = false; }
    public boolean isRunning() { return running; }
}
''',
    'AutoHuntServiceHarness.java': '''import auto.hunt.*;
public final class AutoHuntServiceHarness {
    public static void main(String[] args) {
        AutoHuntRuntimeSettings settings = new AutoHuntRuntimeSettings();
        ap.u first = new ap.u(1);
        AutoHuntService.start(first, settings);
        check(AutoHuntBossIndexLoader.loads == 1, "boss index must load once");
        check(AutoHunt850Session.lastProvider != null, "settings start must install target provider");
        check(AutoHunt850Session.lastAction != null, "settings start must install target action");

        AutoHunt850Mover.next = AutoHuntMoveController.Result.IN_RANGE;
        check(AutoHunt850Session.lastAction.onTarget(new ap.s()), "in-range target must be retained");
        AutoHunt850Mover.next = AutoHuntMoveController.Result.MOVED;
        check(AutoHunt850Session.lastAction.onTarget(new ap.s()), "moved target must be retained");
        AutoHunt850Mover.next = AutoHuntMoveController.Result.UNREACHABLE;
        check(!AutoHunt850Session.lastAction.onTarget(new ap.s()), "unreachable target must be cleared");
        AutoHunt850Mover.next = AutoHuntMoveController.Result.INVALID_TARGET;
        check(!AutoHunt850Session.lastAction.onTarget(new ap.s()), "invalid target must be cleared");

        AutoHuntService.stop(first);
        check(AutoHunt850Mover.resets == 1, "stop must reset mover state");

        ap.u second = new ap.u(2);
        AutoHuntService.start(second, settings);
        check(AutoHuntBossIndexLoader.loads == 1, "boss index must not reload for every session");
        AutoHuntService.stop(second);

        ap.u legacy = new ap.u(3);
        AutoHuntService.start(legacy);
        check(AutoHuntService.isRunning(legacy), "legacy lifecycle start must remain available");
        AutoHuntService.stop(legacy);
        System.out.println("AUTO_HUNT_SERVICE_MOVE_WIRING=PASS");
    }
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
''',
}

with tempfile.TemporaryDirectory(prefix='auto-hunt-service-') as temp:
    temp = Path(temp)
    for rel, content in files.items():
        path = temp / rel
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content, encoding='utf-8')
    service_dst = temp / 'auto/hunt/AutoHuntService.java'
    shutil.copyfile(SERVICE, service_dst)
    out = temp / 'out'
    out.mkdir()
    java_files = [str(p) for p in temp.rglob('*.java')]
    compile_run = subprocess.run(['javac', '-d', str(out)] + java_files, text=True, capture_output=True)
    if compile_run.returncode != 0:
        raise AssertionError('service stub compile failed:\n' + compile_run.stderr)
    run = subprocess.run(['java', '-cp', str(out), 'AutoHuntServiceHarness'], text=True, capture_output=True)
    if run.returncode != 0:
        raise AssertionError('service harness failed:\n' + run.stdout + run.stderr)
    print(run.stdout.strip())
