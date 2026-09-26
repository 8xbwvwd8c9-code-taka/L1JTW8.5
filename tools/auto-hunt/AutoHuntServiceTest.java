import auto.hunt.AutoHuntService;
import ap.u;
import bi.e;

public final class AutoHuntServiceTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        u pc1 = new u();
        pc1.objId = 1001;
        long g1 = AutoHuntService.start(pc1);
        long g1b = AutoHuntService.start(pc1);
        check(g1b == g1, "same PC start must be idempotent");
        check(e.a().schedules == 1, "same PC must own one scheduled session");
        check(AutoHuntService.isRunning(pc1), "service must report running PC");

        bi.e.FakeFuture pc1Future = e.a().future;
        AutoHuntService.stop(pc1);
        check(pc1Future.cancelled, "service stop must cancel owned future");
        check(!AutoHuntService.isRunning(pc1), "service stop must remove running state");

        AutoHuntService.start(pc1);
        bi.e.FakeFuture replacedFuture = e.a().future;
        u pc2 = new u();
        pc2.objId = 1001;
        AutoHuntService.start(pc2);
        check(replacedFuture.cancelled, "new PC object with same objid must cancel stale session");
        check(!AutoHuntService.isRunning(pc1), "stale PC object must no longer own session");
        check(AutoHuntService.isRunning(pc2), "replacement PC must own session");

        AutoHuntService.stop(pc2);
        System.out.println("AUTO_HUNT_SERVICE_TEST=PASS");
    }
}
