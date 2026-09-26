package auto.hunt;

public final class AutoHuntConsumableController {
    public enum Mode {
        PERCENT,
        ABSOLUTE
    }

    public enum Result {
        DISABLED,
        BLOCKED,
        ABOVE_THRESHOLD,
        NO_ITEM,
        CONSUMED,
        REJECTED
    }

    public interface Host {
        int currentHp();
        int maxHp();
        boolean isBlocked();
        boolean hasConsumable(int itemId);
        boolean useConsumable(int itemId);
    }

    private AutoHuntConsumableController() {
    }

    public static Result tryConsume(Host host,
                                    boolean enabled,
                                    Mode mode,
                                    int threshold,
                                    int itemId) {
        if (host == null) {
            throw new NullPointerException("host");
        }
        if (!enabled) {
            return Result.DISABLED;
        }
        if (mode == null) {
            throw new NullPointerException("mode");
        }
        if (itemId <= 0) {
            throw new IllegalArgumentException("itemId");
        }
        if (mode == Mode.PERCENT) {
            if (threshold < 1 || threshold > 100) {
                throw new IllegalArgumentException("percent threshold");
            }
        } else if (threshold < 1) {
            throw new IllegalArgumentException("absolute threshold");
        }

        if (host.isBlocked()) {
            return Result.BLOCKED;
        }
        int hp = host.currentHp();
        int maxHp = host.maxHp();
        if (hp <= 0 || maxHp <= 0) {
            return Result.BLOCKED;
        }

        boolean shouldConsume;
        if (mode == Mode.PERCENT) {
            shouldConsume = (long) hp * 100L <= (long) maxHp * (long) threshold;
        } else {
            shouldConsume = hp <= threshold;
        }
        if (!shouldConsume) {
            return Result.ABOVE_THRESHOLD;
        }
        if (!host.hasConsumable(itemId)) {
            return Result.NO_ITEM;
        }
        return host.useConsumable(itemId) ? Result.CONSUMED : Result.REJECTED;
    }
}
