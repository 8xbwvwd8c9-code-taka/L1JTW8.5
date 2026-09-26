from pathlib import Path
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[2]
ADAPTER = ROOT / 'recovered-src-obf/auto/hunt/AutoHunt850Attacker.java'
CONTROLLER = ROOT / 'recovered-src-obf/auto/hunt/AutoHuntAttackController.java'

if not ADAPTER.exists():
    raise AssertionError('missing AutoHunt850Attacker.java')

source = ADAPTER.read_text(encoding='utf-8')
required = [
    'pc.j().h() > 82',
    'pc.bN() || pc.eX() || pc.aR() || pc.ed() || pc.ec()',
    'pc.bB(33)', 'pc.bB(50)', 'pc.bB(66)', 'pc.bB(87)',
    'pc.bB(208)', 'pc.bB(212)', 'pc.bB(1009)', 'pc.bB(1011)',
    'pc.bB(60) || pc.N()',
    'target.fp() != pc.fp()',
    'target.ac() != 0',
    'pc.c(target.fs(), target.ft(), range)',
    'pc.ce().b(ak.a.b)',
    'pc.bz(78)', 'pc.bz(32)', 'pc.bz(97)',
    'pc.e(1)',
    'target.c(pc)',
]
for needle in required:
    assert needle in source, f'missing verified 850 attack contract: {needle}'
assert 'pc.ce().a(ak.a.b)' not in source, 'server auto-hunt must not call client anti-speed attack checker'

files = {
    'aq/ak.java': '''package aq;
public final class ak {
    public enum a { a, b }
    public int interval = 600;
    public int b(a type) { return interval; }
}
''',
    'au/g.java': '''package au;
public final class g {
    public int weight;
    public int h() { return weight; }
}
''',
    'ap/u.java': '''package ap;
import java.util.HashSet;
import java.util.Set;
public class u {
    public int mapId = 4;
    public int x = 10;
    public int y = 10;
    public boolean ghost;
    public boolean dead;
    public boolean teleport;
    public boolean paralyzed;
    public boolean sleep;
    public boolean invisDelay;
    public int regenState;
    public final au.g inventory = new au.g();
    public final aq.ak speed = new aq.ak();
    public final Set<Integer> effects = new HashSet<Integer>();
    public boolean bN() { return ghost; }
    public boolean eX() { return dead; }
    public boolean aR() { return teleport; }
    public boolean ed() { return paralyzed; }
    public boolean ec() { return sleep; }
    public au.g j() { return inventory; }
    public boolean bB(int id) { return effects.contains(id); }
    public boolean N() { return invisDelay; }
    public void bz(int id) { effects.remove(id); }
    public aq.ak ce() { return speed; }
    public int fp() { return mapId; }
    public int fs() { return x; }
    public int ft() { return y; }
    public boolean c(int tx, int ty, int range) {
        return Math.max(Math.abs(x - tx), Math.abs(y - ty)) <= range;
    }
    public void e(int state) { regenState = state; }
}
''',
    'ap/s.java': '''package ap;
public class s {
    public int mapId = 4;
    public int x = 11;
    public int y = 10;
    public int hp = 100;
    public boolean dead;
    public int hidden;
    public int attackSpeed = 1;
    public int attacks;
    public int fp() { return mapId; }
    public int fs() { return x; }
    public int ft() { return y; }
    public int ea() { return hp; }
    public boolean eX() { return dead; }
    public int ac() { return hidden; }
    public int O() { return attackSpeed; }
    public void c(u pc) { attacks++; }
}
''',
    'AutoHunt850AttackerHarness.java': '''import auto.hunt.*;
import ap.s;
import ap.u;

public final class AutoHunt850AttackerHarness {
    public static void main(String[] args) {
        normalAttackUsesNativeEntryAndCooldown();
        hardStopBlocksAttack();
        overweightBlocksAttack();
        invisibleBlocksAttack();
        invalidTargetsClear();
        outOfRangeDoesNotAttack();
        resetClearsCooldown();
        System.out.println("AUTO_HUNT_850_ATTACK_ADAPTER=PASS");
    }

    private static void normalAttackUsesNativeEntryAndCooldown() {
        u pc = new u();
        s target = new s();
        pc.effects.add(78);
        pc.effects.add(32);
        pc.effects.add(97);
        AutoHunt850Attacker attacker = new AutoHunt850Attacker(pc);
        check(attacker.attack(target, 1, 1000L) == AutoHuntAttackController.Result.ATTACKED, "attack result");
        check(target.attacks == 1, "must delegate to target.c(pc)");
        check(pc.regenState == 1, "native attack regen state");
        check(!pc.effects.contains(78) && !pc.effects.contains(32) && !pc.effects.contains(97), "native attack effect cleanup");
        check(attacker.attack(target, 1, 1200L) == AutoHuntAttackController.Result.COOLDOWN, "native cooldown");
        check(target.attacks == 1, "cooldown must suppress duplicate attack");
    }

    private static void hardStopBlocksAttack() {
        u pc = new u();
        s target = new s();
        pc.effects.add(1011);
        AutoHunt850Attacker attacker = new AutoHunt850Attacker(pc);
        check(attacker.attack(target, 1, 1000L) == AutoHuntAttackController.Result.BLOCKED, "donor hard stop");
        check(target.attacks == 0, "hard stop must not attack");
    }

    private static void overweightBlocksAttack() {
        u pc = new u();
        s target = new s();
        pc.inventory.weight = 83;
        AutoHunt850Attacker attacker = new AutoHunt850Attacker(pc);
        check(attacker.attack(target, 1, 1000L) == AutoHuntAttackController.Result.BLOCKED, "850 weight gate");
    }

    private static void invisibleBlocksAttack() {
        u pc = new u();
        s target = new s();
        pc.effects.add(60);
        AutoHunt850Attacker attacker = new AutoHunt850Attacker(pc);
        check(attacker.attack(target, 1, 1000L) == AutoHuntAttackController.Result.BLOCKED, "850 invis gate");
    }

    private static void invalidTargetsClear() {
        u pc = new u();
        AutoHunt850Attacker attacker = new AutoHunt850Attacker(pc);
        s dead = new s(); dead.dead = true;
        check(attacker.attack(dead, 1, 1000L) == AutoHuntAttackController.Result.INVALID_TARGET, "dead target");
        s hidden = new s(); hidden.hidden = 1;
        check(attacker.attack(hidden, 1, 1000L) == AutoHuntAttackController.Result.INVALID_TARGET, "hidden target");
        s crossMap = new s(); crossMap.mapId = 69;
        check(attacker.attack(crossMap, 1, 1000L) == AutoHuntAttackController.Result.INVALID_TARGET, "cross-map target");
    }

    private static void outOfRangeDoesNotAttack() {
        u pc = new u();
        s target = new s(); target.x = 14;
        AutoHunt850Attacker attacker = new AutoHunt850Attacker(pc);
        check(attacker.attack(target, 1, 1000L) == AutoHuntAttackController.Result.OUT_OF_RANGE, "out of range");
        check(target.attacks == 0, "out of range must not attack");
    }

    private static void resetClearsCooldown() {
        u pc = new u();
        s target = new s();
        AutoHunt850Attacker attacker = new AutoHunt850Attacker(pc);
        check(attacker.attack(target, 1, 1000L) == AutoHuntAttackController.Result.ATTACKED, "first attack");
        attacker.reset();
        check(attacker.attack(target, 1, 1001L) == AutoHuntAttackController.Result.ATTACKED, "reset should clear cooldown");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
''',
}

with tempfile.TemporaryDirectory(prefix='auto-hunt-attack-') as temp:
    temp = Path(temp)
    for rel, content in files.items():
        path = temp / rel
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content, encoding='utf-8')
    for src in (CONTROLLER, ADAPTER):
        dst = temp / 'auto/hunt' / src.name
        dst.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(src, dst)
    out = temp / 'out'
    out.mkdir()
    java_files = [str(p) for p in temp.rglob('*.java')]
    compile_run = subprocess.run(['javac', '-d', str(out)] + java_files, text=True, capture_output=True)
    if compile_run.returncode != 0:
        raise AssertionError('attack adapter stub compile failed:\n' + compile_run.stderr)
    run = subprocess.run(['java', '-cp', str(out), 'AutoHunt850AttackerHarness'], text=True, capture_output=True)
    if run.returncode != 0:
        raise AssertionError('attack adapter harness failed:\n' + run.stdout + run.stderr)
    print(run.stdout.strip())
