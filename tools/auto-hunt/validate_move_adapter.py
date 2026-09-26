from pathlib import Path
import shutil
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[2]
ADAPTER = ROOT / 'recovered-src-obf/auto/hunt/AutoHunt850Mover.java'
PLANNER = ROOT / 'recovered-src-obf/auto/hunt/AutoHuntMovePlanner.java'
CONTROLLER = ROOT / 'recovered-src-obf/auto/hunt/AutoHuntMoveController.java'

if not ADAPTER.exists():
    raise AssertionError('missing AutoHunt850Mover.java')

source = ADAPTER.read_text(encoding='utf-8')
required = [
    'pc.fq().c(x, y, heading)',
    'aq.l.a().a(nextX, nextY, pc.fq().b(), pc)',
    'pc.fq().a(pc.fu(), true)',
    'pc.fu().a(nextX, nextY)',
    'pc.ct(heading)',
    'pc.fq().a(pc.fu(), false)',
    'ao.bi.a().a(pc)',
    'pc.ce().b(ak.a.a)',
    'target.j(pc.fr() + 100000, 20000)',
    'pc.a(new cb(pc))',
]
for needle in required:
    assert needle in source, f'missing verified 850 move contract: {needle}'

files = {
    'aq/u.java': '''package aq;
public final class u {
    public int x;
    public int y;
    public u(int x, int y) { this.x = x; this.y = y; }
    public void a(int x, int y) { this.x = x; this.y = y; }
}
''',
    'ax/b.java': '''package ax;
public final class b {
    public boolean open = true;
    public int mapId = 4;
    public int released;
    public int occupied;
    public boolean c(int x, int y, int heading) { return open; }
    public int b() { return mapId; }
    public void a(aq.u loc, boolean passable) {
        if (passable) released++; else occupied++;
    }
}
''',
    'aq/ak.java': '''package aq;
public final class ak {
    public enum a { a }
    public int interval = 400;
    public int b(a type) { return interval; }
}
''',
    'aq/l.java': '''package aq;
public final class l {
    private static final l INSTANCE = new l();
    public static boolean teleport;
    public static l a() { return INSTANCE; }
    public boolean a(int x, int y, int mapId, ap.u pc) {
        if (!teleport) return false;
        pc.teleported = true;
        return true;
    }
}
''',
    'ao/bi.java': '''package ao;
public final class bi {
    private static final bi INSTANCE = new bi();
    public static int trapChecks;
    public static bi a() { return INSTANCE; }
    public void a(ap.u pc) { trapChecks++; }
}
''',
    'be/cb.java': '''package be;
public final class cb {
    public cb(aq.f pc) { }
}
''',
    'aq/f.java': '''package aq;
public class f { }
''',
    'ap/u.java': '''package ap;
public class u extends aq.f {
    public final aq.u location = new aq.u(10, 10);
    public final ax.b map = new ax.b();
    public final aq.ak speed = new aq.ak();
    public int mapId = 4;
    public int heading;
    public int selfPackets;
    public int normalBroadcasts;
    public int ghostBroadcasts;
    public int id = 77;
    public boolean ghost;
    public boolean teleporting;
    public boolean teleported;
    public int fs() { return location.x; }
    public int ft() { return location.y; }
    public int fp() { return mapId; }
    public ax.b fq() { return map; }
    public aq.u fu() { return location; }
    public void ct(int heading) { this.heading = heading; }
    public aq.ak ce() { return speed; }
    public boolean aR() { return teleporting; }
    public boolean ff() { return ghost; }
    public void a(be.cb packet) { selfPackets++; }
    public void b(be.cb packet) { normalBroadcasts++; }
    public void c(be.cb packet) { ghostBroadcasts++; }
    public int fr() { return id; }
    public boolean c(int x, int y, int range) {
        return Math.max(Math.abs(fs() - x), Math.abs(ft() - y)) <= range;
    }
}
''',
    'ap/s.java': '''package ap;
public class s extends aq.f {
    public int x = 14;
    public int y = 10;
    public int mapId = 4;
    public int hp = 100;
    public boolean dead;
    public int effectId;
    public int effectMs;
    public int fs() { return x; }
    public int ft() { return y; }
    public int fp() { return mapId; }
    public int ea() { return hp; }
    public boolean eX() { return dead; }
    public void j(int id, int ms) { effectId = id; effectMs = ms; }
}
''',
    'AutoHunt850MoverHarness.java': '''import auto.hunt.AutoHunt850Mover;
import auto.hunt.AutoHuntMoveController;
import ap.s;
import ap.u;

public final class AutoHunt850MoverHarness {
    public static void main(String[] args) {
        normalMoveAndCooldown();
        unreachableMarksTarget();
        invalidTargetIsRejected();
        ghostMoveBroadcastsAndEchoes();
        System.out.println("AUTO_HUNT_850_MOVE_ADAPTER=PASS");
    }

    private static void normalMoveAndCooldown() {
        u pc = new u();
        s target = new s();
        AutoHunt850Mover mover = new AutoHunt850Mover(pc);
        check(mover.moveToward(target, 1, 1000L) == AutoHuntMoveController.Result.MOVED, "first step");
        check(pc.fs() == 11 && pc.ft() == 10 && pc.heading == 2, "coordinate and heading");
        check(pc.map.released == 1 && pc.map.occupied == 1, "map occupancy");
        check(ao.bi.trapChecks == 1, "trap check");
        check(pc.normalBroadcasts == 1 && pc.selfPackets == 1, "broadcast plus self echo");
        check(mover.moveToward(target, 1, 1200L) == AutoHuntMoveController.Result.COOLDOWN, "native interval cooldown");
        check(pc.fs() == 11, "cooldown must not move again");
    }

    private static void unreachableMarksTarget() {
        u pc = new u();
        pc.map.open = false;
        s target = new s();
        AutoHunt850Mover mover = new AutoHunt850Mover(pc);
        check(mover.moveToward(target, 1, 1000L) == AutoHuntMoveController.Result.UNREACHABLE, "blocked route");
        check(target.effectId == pc.id + 100000 && target.effectMs == 20000, "381 unreachable marker");
    }

    private static void invalidTargetIsRejected() {
        u pc = new u();
        s target = new s();
        target.mapId = 69;
        AutoHunt850Mover mover = new AutoHunt850Mover(pc);
        check(mover.moveToward(target, 1, 1000L) == AutoHuntMoveController.Result.INVALID_TARGET, "cross-map target");
        check(pc.selfPackets == 0, "invalid target must not emit move packet");
    }

    private static void ghostMoveBroadcastsAndEchoes() {
        u pc = new u();
        pc.ghost = true;
        s target = new s();
        AutoHunt850Mover mover = new AutoHunt850Mover(pc);
        check(mover.moveToward(target, 1, 1000L) == AutoHuntMoveController.Result.MOVED, "ghost move");
        check(pc.ghostBroadcasts == 1 && pc.normalBroadcasts == 0, "ghost broadcast path");
        check(pc.selfPackets == 1, "server-driven ghost move still needs self echo");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
''',
}

with tempfile.TemporaryDirectory(prefix='auto-hunt-move-') as temp:
    temp = Path(temp)
    for rel, content in files.items():
        path = temp / rel
        path.parent.mkdir(parents=True, exist_ok=True)
        path.write_text(content, encoding='utf-8')

    for src in (PLANNER, CONTROLLER, ADAPTER):
        dst = temp / 'auto/hunt' / src.name
        dst.parent.mkdir(parents=True, exist_ok=True)
        shutil.copyfile(src, dst)

    java_files = [str(p) for p in temp.rglob('*.java')]
    out = temp / 'out'
    out.mkdir()
    compile_run = subprocess.run(['javac', '-d', str(out)] + java_files, text=True, capture_output=True)
    if compile_run.returncode != 0:
        raise AssertionError('adapter stub compile failed:\n' + compile_run.stderr)
    run = subprocess.run(['java', '-cp', str(out), 'AutoHunt850MoverHarness'], text=True, capture_output=True)
    if run.returncode != 0:
        raise AssertionError('adapter harness failed:\n' + run.stdout + run.stderr)
    print(run.stdout.strip())
