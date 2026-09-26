import auto.hunt.AutoHuntMoveController;

public final class AutoHuntMoveControllerTest {
    private static final class FakeHost implements AutoHuntMoveController.Host {
        int x = 10;
        int y = 10;
        int targetX = 14;
        int targetY = 10;
        boolean inRange;
        boolean blocked;
        boolean stepSucceeds = true;
        int steps;
        int lastHeading = -1;
        int unreachableMarks;
        long interval = 500L;

        @Override public boolean isInRange(int engageRange) { return inRange; }
        @Override public int x() { return x; }
        @Override public int y() { return y; }
        @Override public int targetX() { return targetX; }
        @Override public int targetY() { return targetY; }
        @Override public boolean canStep(int fromX, int fromY, int heading) { return !blocked; }
        @Override public long moveIntervalMs() { return interval; }
        @Override public boolean step(int heading) {
            steps++;
            lastHeading = heading;
            return stepSucceeds;
        }
        @Override public void markUnreachable() { unreachableMarks++; }
    }

    public static void main(String[] args) {
        alreadyInRangeDoesNothing();
        successfulStepStartsCooldown();
        cooldownSkipsMovement();
        rejectedStepDoesNotConsumeCooldown();
        unreachableMarksTarget();
        System.out.println("AUTO_HUNT_MOVE_CONTROLLER_TEST=PASS");
    }

    private static void alreadyInRangeDoesNothing() {
        AutoHuntMoveController controller = new AutoHuntMoveController();
        FakeHost host = new FakeHost();
        host.inRange = true;
        check(controller.tryMove(host, 1, 1000L) == AutoHuntMoveController.Result.IN_RANGE,
                "in-range target should not move");
        check(host.steps == 0, "in-range target must not step");
    }

    private static void successfulStepStartsCooldown() {
        AutoHuntMoveController controller = new AutoHuntMoveController();
        FakeHost host = new FakeHost();
        check(controller.tryMove(host, 1, 1000L) == AutoHuntMoveController.Result.MOVED,
                "open route should move");
        check(host.steps == 1 && host.lastHeading == 2, "move should use planned east heading");
        check(controller.nextMoveAtMs() == 1500L, "successful move should use native interval");
    }

    private static void cooldownSkipsMovement() {
        AutoHuntMoveController controller = new AutoHuntMoveController();
        FakeHost host = new FakeHost();
        check(controller.tryMove(host, 1, 1000L) == AutoHuntMoveController.Result.MOVED,
                "first move should succeed");
        check(controller.tryMove(host, 1, 1200L) == AutoHuntMoveController.Result.COOLDOWN,
                "move before native interval expires must wait");
        check(host.steps == 1, "cooldown must not perform a second step");
    }

    private static void rejectedStepDoesNotConsumeCooldown() {
        AutoHuntMoveController controller = new AutoHuntMoveController();
        FakeHost host = new FakeHost();
        host.stepSucceeds = false;
        check(controller.tryMove(host, 1, 1000L) == AutoHuntMoveController.Result.STEP_REJECTED,
                "host revalidation may reject a planned step");
        check(controller.nextMoveAtMs() == 0L, "rejected step must not consume move cooldown");
    }

    private static void unreachableMarksTarget() {
        AutoHuntMoveController controller = new AutoHuntMoveController();
        FakeHost host = new FakeHost();
        host.blocked = true;
        check(controller.tryMove(host, 1, 1000L) == AutoHuntMoveController.Result.UNREACHABLE,
                "no route should be reported unreachable");
        check(host.unreachableMarks == 1, "unreachable target must receive donor marker");
        check(host.steps == 0, "unreachable target must not move");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
