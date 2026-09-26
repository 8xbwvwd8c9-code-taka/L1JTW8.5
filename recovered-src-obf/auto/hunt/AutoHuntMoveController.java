package auto.hunt;

public final class AutoHuntMoveController {
    public enum Result {
        INVALID_TARGET,
        IN_RANGE,
        COOLDOWN,
        MOVED,
        UNREACHABLE,
        STEP_REJECTED
    }

    public interface Host {
        boolean isInRange(int engageRange);
        int x();
        int y();
        int targetX();
        int targetY();
        boolean canStep(int fromX, int fromY, int heading);
        long moveIntervalMs();
        boolean step(int heading);
        void markUnreachable();
    }

    private long nextMoveAtMs;

    public synchronized Result tryMove(final Host host, int engageRange, long nowMs) {
        if (host == null) {
            throw new NullPointerException("host");
        }
        if (engageRange < 0) {
            throw new IllegalArgumentException("engageRange must be non-negative");
        }
        if (host.isInRange(engageRange)) {
            return Result.IN_RANGE;
        }
        if (nowMs < nextMoveAtMs) {
            return Result.COOLDOWN;
        }

        int heading = AutoHuntMovePlanner.nextHeading(
                host.x(),
                host.y(),
                host.targetX(),
                host.targetY(),
                engageRange,
                new AutoHuntMovePlanner.Passability() {
                    @Override
                    public boolean canStep(int x, int y, int heading) {
                        return host.canStep(x, y, heading);
                    }
                });
        if (heading < 0) {
            host.markUnreachable();
            return Result.UNREACHABLE;
        }
        if (!host.step(heading)) {
            return Result.STEP_REJECTED;
        }

        long interval = Math.max(1L, host.moveIntervalMs());
        nextMoveAtMs = nowMs > Long.MAX_VALUE - interval
                ? Long.MAX_VALUE
                : nowMs + interval;
        return Result.MOVED;
    }

    public synchronized long nextMoveAtMs() {
        return nextMoveAtMs;
    }

    public synchronized void reset() {
        nextMoveAtMs = 0L;
    }
}
