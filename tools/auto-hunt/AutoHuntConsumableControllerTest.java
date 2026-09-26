import auto.hunt.AutoHuntConsumableController;

public final class AutoHuntConsumableControllerTest {
    private static final class FakeHost implements AutoHuntConsumableController.Host {
        int hp = 50;
        int maxHp = 100;
        boolean blocked;
        boolean cooldown;
        boolean available = true;
        boolean useSucceeds = true;
        int uses;

        @Override public int currentHp() { return hp; }
        @Override public int maxHp() { return maxHp; }
        @Override public boolean isBlocked() { return blocked; }
        @Override public boolean isOnCooldown() { return cooldown; }
        @Override public boolean hasConsumable(int itemId) { return available; }
        @Override public boolean useConsumable(int itemId) { uses++; return useSucceeds; }
    }

    public static void main(String[] args) {
        disabledDoesNothing();
        percentThresholdConsumesAtBoundary();
        percentThresholdDoesNotConsumeAboveBoundary();
        absoluteThresholdConsumesAtBoundary();
        blockedDoesNotConsume();
        cooldownDoesNotConsume();
        missingItemDoesNotConsume();
        rejectedUseDoesNotReportConsumed();
        System.out.println("AUTO_HUNT_CONSUMABLE_CONTROLLER_TEST=PASS");
    }

    private static void disabledDoesNothing() {
        FakeHost host = new FakeHost();
        check(AutoHuntConsumableController.tryConsume(host, false,
                AutoHuntConsumableController.Mode.PERCENT, 50, 40010)
                == AutoHuntConsumableController.Result.DISABLED, "disabled must not consume");
        check(host.uses == 0, "disabled must issue zero uses");
    }

    private static void percentThresholdConsumesAtBoundary() {
        FakeHost host = new FakeHost();
        host.hp = 50;
        host.maxHp = 100;
        check(AutoHuntConsumableController.tryConsume(host, true,
                AutoHuntConsumableController.Mode.PERCENT, 50, 40010)
                == AutoHuntConsumableController.Result.CONSUMED, "50/100 at 50% must consume");
        check(host.uses == 1, "percent threshold must consume exactly once");
    }

    private static void percentThresholdDoesNotConsumeAboveBoundary() {
        FakeHost host = new FakeHost();
        host.hp = 51;
        host.maxHp = 100;
        check(AutoHuntConsumableController.tryConsume(host, true,
                AutoHuntConsumableController.Mode.PERCENT, 50, 40010)
                == AutoHuntConsumableController.Result.ABOVE_THRESHOLD, "51/100 above 50% must wait");
        check(host.uses == 0, "above threshold must issue zero uses");
    }

    private static void absoluteThresholdConsumesAtBoundary() {
        FakeHost host = new FakeHost();
        host.hp = 250;
        host.maxHp = 1000;
        check(AutoHuntConsumableController.tryConsume(host, true,
                AutoHuntConsumableController.Mode.ABSOLUTE, 250, 40010)
                == AutoHuntConsumableController.Result.CONSUMED, "absolute boundary must consume");
        check(host.uses == 1, "absolute threshold must consume exactly once");
    }

    private static void blockedDoesNotConsume() {
        FakeHost host = new FakeHost();
        host.blocked = true;
        check(AutoHuntConsumableController.tryConsume(host, true,
                AutoHuntConsumableController.Mode.PERCENT, 80, 40010)
                == AutoHuntConsumableController.Result.BLOCKED, "blocked state must win");
        check(host.uses == 0, "blocked state must issue zero uses");
    }

    private static void cooldownDoesNotConsume() {
        FakeHost host = new FakeHost();
        host.cooldown = true;
        check(AutoHuntConsumableController.tryConsume(host, true,
                AutoHuntConsumableController.Mode.PERCENT, 80, 40010)
                == AutoHuntConsumableController.Result.COOLDOWN, "potion cooldown must be independent");
        check(host.uses == 0, "cooldown must issue zero uses");
    }

    private static void missingItemDoesNotConsume() {
        FakeHost host = new FakeHost();
        host.available = false;
        check(AutoHuntConsumableController.tryConsume(host, true,
                AutoHuntConsumableController.Mode.PERCENT, 80, 40010)
                == AutoHuntConsumableController.Result.NO_ITEM, "missing item must be reported");
        check(host.uses == 0, "missing item must issue zero uses");
    }

    private static void rejectedUseDoesNotReportConsumed() {
        FakeHost host = new FakeHost();
        host.useSucceeds = false;
        check(AutoHuntConsumableController.tryConsume(host, true,
                AutoHuntConsumableController.Mode.PERCENT, 80, 40010)
                == AutoHuntConsumableController.Result.REJECTED, "native rejection must be preserved");
        check(host.uses == 1, "native use should be attempted exactly once");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
