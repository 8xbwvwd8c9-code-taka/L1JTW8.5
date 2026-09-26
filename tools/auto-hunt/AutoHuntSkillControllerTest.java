import auto.hunt.AutoHuntSkillController;

public final class AutoHuntSkillControllerTest {
    private static final class FakeAttempt implements AutoHuntSkillController.Attempt {
        boolean accepted = true;
        int calls;

        @Override
        public boolean cast() {
            calls++;
            return accepted;
        }
    }

    public static void main(String[] args) {
        independentCooldowns();
        rejectedAttemptDoesNotConsumeCooldown();
        cooldownOverflowSaturates();
        resetClearsAllCooldowns();
        System.out.println("AUTO_HUNT_SKILL_CONTROLLER_TEST=PASS");
    }

    private static void independentCooldowns() {
        AutoHuntSkillController controller = new AutoHuntSkillController();
        FakeAttempt a = new FakeAttempt();
        FakeAttempt b = new FakeAttempt();

        check(controller.tryCast(10, 500L, a, 1000L) == AutoHuntSkillController.Result.CAST_ATTEMPTED,
                "skill 10 first cast should be attempted");
        check(controller.nextCastAtMs(10) == 1500L, "skill 10 cooldown must be isolated");
        check(controller.tryCast(10, 500L, a, 1200L) == AutoHuntSkillController.Result.COOLDOWN,
                "skill 10 should still be cooling down");
        check(a.calls == 1, "cooldown must suppress duplicate skill 10 attempt");

        check(controller.tryCast(11, 800L, b, 1200L) == AutoHuntSkillController.Result.CAST_ATTEMPTED,
                "skill 11 must not share skill 10 cooldown");
        check(controller.nextCastAtMs(11) == 2000L, "skill 11 must own its own deadline");

        check(controller.tryCast(10, 500L, a, 1500L) == AutoHuntSkillController.Result.CAST_ATTEMPTED,
                "skill 10 should cast again exactly at its own deadline");
        check(a.calls == 2, "skill 10 second accepted attempt expected");
    }

    private static void rejectedAttemptDoesNotConsumeCooldown() {
        AutoHuntSkillController controller = new AutoHuntSkillController();
        FakeAttempt attempt = new FakeAttempt();
        attempt.accepted = false;

        check(controller.tryCast(20, 600L, attempt, 1000L) == AutoHuntSkillController.Result.CAST_REJECTED,
                "adapter may reject after final revalidation");
        check(controller.nextCastAtMs(20) == 0L, "rejected attempt must not start cooldown");
        check(controller.tryCast(20, 600L, attempt, 1001L) == AutoHuntSkillController.Result.CAST_REJECTED,
                "rejected skill should be immediately retryable");
    }

    private static void cooldownOverflowSaturates() {
        AutoHuntSkillController controller = new AutoHuntSkillController();
        FakeAttempt attempt = new FakeAttempt();
        check(controller.tryCast(30, 100L, attempt, Long.MAX_VALUE - 10L)
                        == AutoHuntSkillController.Result.CAST_ATTEMPTED,
                "overflow-bound cast should still be accepted");
        check(controller.nextCastAtMs(30) == Long.MAX_VALUE,
                "cooldown addition must saturate instead of overflow");
    }

    private static void resetClearsAllCooldowns() {
        AutoHuntSkillController controller = new AutoHuntSkillController();
        FakeAttempt attempt = new FakeAttempt();
        controller.tryCast(40, 500L, attempt, 1000L);
        controller.tryCast(41, 700L, attempt, 1000L);
        controller.reset();
        check(controller.nextCastAtMs(40) == 0L && controller.nextCastAtMs(41) == 0L,
                "stop/reset must clear every per-skill deadline");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
