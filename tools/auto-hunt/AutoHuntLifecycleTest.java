import auto.hunt.AutoHuntLifecycle;

public final class AutoHuntLifecycleTest {
    private static void check(boolean v, String msg) {
        if (!v) throw new AssertionError(msg);
    }

    public static void main(String[] args) {
        AutoHuntLifecycle s = new AutoHuntLifecycle();

        long g1 = s.start();
        check(g1 > 0, "first start must produce positive generation");
        check(s.isActive(), "start must activate");
        check(s.start() == g1, "start/start must be idempotent");
        check(s.isCurrentSession(g1), "fresh token must be current");

        long a1 = s.getActionGeneration();
        s.observeMap(4);
        long a2 = s.getActionGeneration();
        check(a2 > a1, "first observed map establishes a new action generation");
        s.observeMap(4);
        check(s.getActionGeneration() == a2, "same map must not invalidate action generation");
        s.observeMap(69);
        check(s.getActionGeneration() > a2, "map change must invalidate delayed action generation");

        s.stop();
        check(!s.isActive(), "stop must deactivate");
        check(!s.isCurrentSession(g1), "stop must invalidate stale session token");
        long stoppedGeneration = s.getSessionGeneration();
        s.stop();
        check(s.getSessionGeneration() == stoppedGeneration, "stop/stop must be idempotent");

        long g2 = s.start();
        check(g2 > g1, "restart must use a newer generation");
        check(!s.isCurrentSession(g1), "old generation must remain stale after restart");
        check(s.isCurrentSession(g2), "new generation must be current");

        System.out.println("AUTO_HUNT_LIFECYCLE_TEST=PASS");
    }
}
