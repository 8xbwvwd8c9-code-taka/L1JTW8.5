import auto.hunt.AutoHunt850Session;
import ap.s;
import ap.u;
import bi.e;

public final class AutoHunt850TargetStateTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        final u pc = new u();
        final s first = new s();
        final s second = new s();
        final s[] next = new s[] { first };

        AutoHunt850Session session = new AutoHunt850Session(
                pc,
                200L,
                new AutoHunt850Session.TargetProvider() {
                    @Override
                    public s select() {
                        return next[0];
                    }
                });

        session.start();
        long beforeAcquire = session.getActionGeneration();
        e.a().fire();
        check(session.currentTarget() == first, "first scan must acquire target");
        check(session.getActionGeneration() > beforeAcquire,
                "target acquire must invalidate action generation");

        long afterAcquire = session.getActionGeneration();
        e.a().fire();
        check(session.currentTarget() == first, "same scan result must retain target");
        check(session.getActionGeneration() == afterAcquire,
                "same target must not invalidate action generation");

        next[0] = second;
        e.a().fire();
        check(session.currentTarget() == second, "new scan result must replace target");
        check(session.getActionGeneration() == afterAcquire + 1,
                "target replacement must invalidate exactly once");

        long beforeClear = session.getActionGeneration();
        next[0] = null;
        e.a().fire();
        check(session.currentTarget() == null, "empty scan must clear target");
        check(session.getActionGeneration() == beforeClear + 1,
                "target clear must invalidate exactly once");

        session.stop();
        check(session.currentTarget() == null, "stop must leave no target");

        System.out.println("AUTO_HUNT_850_TARGET_STATE_TEST=PASS");
    }
}
