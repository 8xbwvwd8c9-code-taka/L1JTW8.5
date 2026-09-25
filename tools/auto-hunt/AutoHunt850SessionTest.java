import auto.hunt.AutoHunt850Session;
import ap.u;
import bi.e;

public final class AutoHunt850SessionTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        u pc = new u();
        AutoHunt850Session session = new AutoHunt850Session(pc, 200L);

        long g1 = session.start();
        check(session.start() == g1, "850 duplicate start must be idempotent");
        check(e.a().schedules == 1, "850 session must own one player scheduled task");

        long a1 = session.getActionGeneration();
        e.a().fire();
        check(session.getActionGeneration() > a1, "first 850 tick must observe current map");
        long a2 = session.getActionGeneration();
        pc.mapId = 69;
        e.a().fire();
        check(session.getActionGeneration() > a2, "850 map change must invalidate delayed actions");

        pc.dead = true;
        e.a().fire();
        check(!session.isRunning(), "850 dead flag must stop session");
        check(e.a().future.cancelled, "850 dead flag must cancel future");

        pc.dead = false;
        pc.hp = 0;
        session.start();
        e.a().fire();
        check(!session.isRunning(), "850 HP<=0 must stop session");

        pc.hp = 100;
        pc.connection = null;
        session.start();
        e.a().fire();
        check(!session.isRunning(), "850 missing ClientThread must stop session");

        pc.connection = new Object();
        pc.teleport = true;
        session.start();
        e.a().fire();
        check(!session.isRunning(), "850 teleport state must stop session");

        System.out.println("AUTO_HUNT_850_SESSION_TEST=PASS");
    }
}
