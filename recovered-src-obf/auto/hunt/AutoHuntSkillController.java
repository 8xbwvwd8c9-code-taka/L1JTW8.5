package auto.hunt;

import java.util.HashMap;
import java.util.Map;

public final class AutoHuntSkillController {
    public enum Result {
        COOLDOWN,
        CAST_ATTEMPTED,
        CAST_REJECTED
    }

    public interface Attempt {
        boolean cast();
    }

    private final Map<Integer, Long> nextCastAtMs = new HashMap<Integer, Long>();

    public synchronized Result tryCast(int skillId, long cooldownMs, Attempt attempt, long nowMs) {
        if (skillId <= 0) {
            throw new IllegalArgumentException("skillId must be positive");
        }
        if (cooldownMs < 0L) {
            throw new IllegalArgumentException("cooldownMs must be non-negative");
        }
        if (attempt == null) {
            throw new NullPointerException("attempt");
        }

        long next = nextCastAtMs(skillId);
        if (nowMs < next) {
            return Result.COOLDOWN;
        }
        if (!attempt.cast()) {
            return Result.CAST_REJECTED;
        }

        long interval = Math.max(1L, cooldownMs);
        long deadline = nowMs > Long.MAX_VALUE - interval
                ? Long.MAX_VALUE
                : nowMs + interval;
        nextCastAtMs.put(skillId, deadline);
        return Result.CAST_ATTEMPTED;
    }

    public synchronized long nextCastAtMs(int skillId) {
        Long value = nextCastAtMs.get(skillId);
        return value == null ? 0L : value.longValue();
    }

    public synchronized void reset() {
        nextCastAtMs.clear();
    }
}
