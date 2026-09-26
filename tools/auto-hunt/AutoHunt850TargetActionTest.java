import auto.hunt.AutoHunt850Session;
import ap.s;
import ap.u;
import bi.e;

public final class AutoHunt850TargetActionTest {
    public static void main(String[] args) {
        actionKeepsTargetWhenAccepted();
        actionClearsTargetWhenRejected();
        stopResetsActionState();
        System.out.println("AUTO_HUNT_850_TARGET_ACTION_TEST=PASS");
    }

    private static void actionKeepsTargetWhenAccepted() {
        final s target = new s();
        final int[] calls = {0};
        AutoHunt850Session session = new AutoHunt850Session(
                new u(), 200L,
                new AutoHunt850Session.TargetProvider() {
                    @Override public s select() { return target; }
                },
                new AutoHunt850Session.TargetAction() {
                    @Override public boolean onTarget(s current) {
                        calls[0]++;
                        return current == target;
                    }
                    @Override public void reset() { }
                });
        session.start();
        e.a().fire();
        check(calls[0] == 1, "target action must run after selection");
        check(session.currentTarget() == target, "accepted action keeps target");
        session.stop();
    }

    private static void actionClearsTargetWhenRejected() {
        final s target = new s();
        final s[] next = { null };
        AutoHunt850Session session = new AutoHunt850Session(
                new u(), 200L,
                new AutoHunt850Session.TargetProvider() {
                    @Override public s select() { return next[0]; }
                },
                new AutoHunt850Session.TargetAction() {
                    @Override public boolean onTarget(s current) { return false; }
                    @Override public void reset() { }
                });
        session.start();
        e.a().fire();
        long before = session.getActionGeneration();
        next[0] = target;
        e.a().fire();
        check(session.currentTarget() == null, "rejected action must clear target");
        check(session.getActionGeneration() == before + 2,
                "acquire plus action rejection must invalidate twice");
        session.stop();
    }

    private static void stopResetsActionState() {
        final int[] resets = {0};
        AutoHunt850Session session = new AutoHunt850Session(
                new u(), 200L,
                new AutoHunt850Session.TargetProvider() {
                    @Override public s select() { return null; }
                },
                new AutoHunt850Session.TargetAction() {
                    @Override public boolean onTarget(s current) { return true; }
                    @Override public void reset() { resets[0]++; }
                });
        session.start();
        session.stop();
        check(resets[0] == 1, "stop must reset target action state exactly once");
        session.stop();
        check(resets[0] == 1, "duplicate stop must stay idempotent");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
