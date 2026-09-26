import auto.hunt.AutoHuntLifecycle;
import auto.hunt.AutoHuntTargetState;

public final class AutoHuntTargetStateTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        AutoHuntLifecycle lifecycle = new AutoHuntLifecycle();
        lifecycle.start();
        lifecycle.observeMap(4);
        AutoHuntTargetState<Object> state = new AutoHuntTargetState<Object>(lifecycle);

        Object a = new Object();
        Object b = new Object();

        long beforeAcquire = lifecycle.getActionGeneration();
        check(state.replace(a), "first target must count as a state change");
        check(state.current() == a, "acquired target must be retained by identity");
        check(lifecycle.getActionGeneration() == beforeAcquire + 1,
                "target acquire must invalidate stale action work");

        long afterAcquire = lifecycle.getActionGeneration();
        check(!state.replace(a), "same target identity must not count as a change");
        check(lifecycle.getActionGeneration() == afterAcquire,
                "same target must not invalidate action generation");

        check(state.replace(b), "replacement target must count as a change");
        check(state.current() == b, "replacement target must become current");
        check(lifecycle.getActionGeneration() == afterAcquire + 1,
                "replacement target must invalidate stale action work");

        long beforeClear = lifecycle.getActionGeneration();
        check(state.clear(), "clearing a live target must count as a change");
        check(state.current() == null, "clear must remove current target");
        check(lifecycle.getActionGeneration() == beforeClear + 1,
                "target clear must invalidate stale action work");

        long afterClear = lifecycle.getActionGeneration();
        check(!state.clear(), "clear/clear must be idempotent");
        check(lifecycle.getActionGeneration() == afterClear,
                "idempotent clear must not invalidate again");

        System.out.println("AUTO_HUNT_TARGET_STATE_TEST=PASS");
    }
}
