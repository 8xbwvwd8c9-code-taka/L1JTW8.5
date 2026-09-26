package auto.hunt;

public final class AutoHuntAttackController {
    public enum Result {
        INVALID_TARGET,
        BLOCKED,
        OUT_OF_RANGE,
        COOLDOWN,
        ATTACKED,
        ATTACK_REJECTED
    }

    public interface Host {
        boolean isValidTarget();
        boolean isAttackBlocked();
        boolean isInRange(int engageRange);
        long attackIntervalMs();
        boolean attack();
    }

    private long nextAttackAtMs;

    public synchronized Result tryAttack(Host host, int engageRange, long nowMs) {
        if (host == null) {
            throw new NullPointerException("host");
        }
        if (engageRange < 0) {
            throw new IllegalArgumentException("engageRange must be non-negative");
        }
        if (!host.isValidTarget()) {
            return Result.INVALID_TARGET;
        }
        if (host.isAttackBlocked()) {
            return Result.BLOCKED;
        }
        if (!host.isInRange(engageRange)) {
            return Result.OUT_OF_RANGE;
        }
        if (nowMs < nextAttackAtMs) {
            return Result.COOLDOWN;
        }
        if (!host.attack()) {
            return Result.ATTACK_REJECTED;
        }

        long interval = Math.max(1L, host.attackIntervalMs());
        nextAttackAtMs = nowMs > Long.MAX_VALUE - interval
                ? Long.MAX_VALUE
                : nowMs + interval;
        return Result.ATTACKED;
    }

    public synchronized long nextAttackAtMs() {
        return nextAttackAtMs;
    }

    public synchronized void reset() {
        nextAttackAtMs = 0L;
    }
}
