import auto.hunt.AutoHuntAttackController;

public final class AutoHuntAttackControllerTest {
    private static final class FakeHost implements AutoHuntAttackController.Host {
        boolean valid = true;
        boolean blocked;
        boolean inRange = true;
        boolean attackSucceeds = true;
        int attacks;
        long interval = 600L;

        @Override public boolean isValidTarget() { return valid; }
        @Override public boolean isAttackBlocked() { return blocked; }
        @Override public boolean isInRange(int engageRange) { return inRange; }
        @Override public long attackIntervalMs() { return interval; }
        @Override public boolean attack() { attacks++; return attackSucceeds; }
    }

    public static void main(String[] args) {
        invalidTargetDoesNotAttack();
        blockedStateDoesNotAttack();
        outOfRangeDoesNotAttack();
        successfulAttackStartsCooldown();
        cooldownSkipsSecondAttack();
        rejectedAttackDoesNotConsumeCooldown();
        System.out.println("AUTO_HUNT_ATTACK_CONTROLLER_TEST=PASS");
    }

    private static void invalidTargetDoesNotAttack() {
        AutoHuntAttackController controller = new AutoHuntAttackController();
        FakeHost host = new FakeHost();
        host.valid = false;
        check(controller.tryAttack(host, 1, 1000L) == AutoHuntAttackController.Result.INVALID_TARGET,
                "invalid target must be rejected");
        check(host.attacks == 0, "invalid target must not attack");
    }

    private static void blockedStateDoesNotAttack() {
        AutoHuntAttackController controller = new AutoHuntAttackController();
        FakeHost host = new FakeHost();
        host.blocked = true;
        check(controller.tryAttack(host, 1, 1000L) == AutoHuntAttackController.Result.BLOCKED,
                "hard-stop player state must block attack");
        check(host.attacks == 0, "blocked player must not attack");
    }

    private static void outOfRangeDoesNotAttack() {
        AutoHuntAttackController controller = new AutoHuntAttackController();
        FakeHost host = new FakeHost();
        host.inRange = false;
        check(controller.tryAttack(host, 3, 1000L) == AutoHuntAttackController.Result.OUT_OF_RANGE,
                "out-of-range target must return to move stage");
        check(host.attacks == 0, "out-of-range target must not attack");
    }

    private static void successfulAttackStartsCooldown() {
        AutoHuntAttackController controller = new AutoHuntAttackController();
        FakeHost host = new FakeHost();
        check(controller.tryAttack(host, 1, 1000L) == AutoHuntAttackController.Result.ATTACKED,
                "valid in-range target should attack");
        check(host.attacks == 1, "exactly one attack should be issued");
        check(controller.nextAttackAtMs() == 1600L, "native attack interval must become cooldown");
    }

    private static void cooldownSkipsSecondAttack() {
        AutoHuntAttackController controller = new AutoHuntAttackController();
        FakeHost host = new FakeHost();
        check(controller.tryAttack(host, 1, 1000L) == AutoHuntAttackController.Result.ATTACKED,
                "first attack should succeed");
        check(controller.tryAttack(host, 1, 1200L) == AutoHuntAttackController.Result.COOLDOWN,
                "second attack before native interval must wait");
        check(host.attacks == 1, "cooldown must suppress duplicate attack");
    }

    private static void rejectedAttackDoesNotConsumeCooldown() {
        AutoHuntAttackController controller = new AutoHuntAttackController();
        FakeHost host = new FakeHost();
        host.attackSucceeds = false;
        check(controller.tryAttack(host, 1, 1000L) == AutoHuntAttackController.Result.ATTACK_REJECTED,
                "host revalidation may reject attack");
        check(controller.nextAttackAtMs() == 0L, "rejected attack must not consume cooldown");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
