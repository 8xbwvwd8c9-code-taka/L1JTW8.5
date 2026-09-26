package auto.hunt;

import ap.q;
import ap.u;
import java.sql.Timestamp;
import java.util.function.LongSupplier;

/** 850-native HP potion bridge for auto-hunt. */
public final class AutoHunt850ConsumableAdapter implements AutoHuntConsumableController.Host {
    interface NativeAccess {
        int currentHp();
        int maxHp();
        boolean blocked();
        boolean hasConsumable(int itemId);
        boolean useHpPotion(int itemId);
    }

    private static final class PlayerNativeAccess implements NativeAccess {
        private final u pc;

        private PlayerNativeAccess(u pc) {
            if (pc == null) {
                throw new NullPointerException("pc");
            }
            this.pc = pc;
        }

        @Override
        public int currentHp() {
            return pc.ea();
        }

        @Override
        public int maxHp() {
            return pc.ew();
        }

        @Override
        public boolean blocked() {
            return pc.bN() || pc.eX() || pc.aR() || pc.ea() <= 0 || !pc.fq().p();
        }

        @Override
        public boolean hasConsumable(int itemId) {
            return pc.j().b(itemId) != null;
        }

        @Override
        public boolean useHpPotion(int itemId) {
            q item = pc.j().b(itemId);
            if (item == null || blocked()) {
                return false;
            }

            int materialType = item.a().aP();
            if (materialType < 23 || materialType > 25) {
                return false;
            }
            if (pc.bB(71)) {
                return false;
            }

            int minLevel = item.a().o();
            int maxLevel = item.a().p();
            if (minLevel != 0 && minLevel > pc.ev()) {
                return false;
            }
            if (maxLevel != 0 && maxLevel < pc.ev()) {
                return false;
            }

            boolean delayEffectUsed = false;
            if (item.f()) {
                int delayId = item.a().aJ();
                if (delayId != 0 && pc.bF(delayId)) {
                    return false;
                }

                int delayEffect = item.a().aL();
                if (delayEffect > 0) {
                    Timestamp lastUsed = item.J();
                    if (lastUsed != null) {
                        long elapsedSeconds = (System.currentTimeMillis() - lastUsed.getTime()) / 1000L;
                        if (elapsedSeconds <= delayEffect) {
                            return false;
                        }
                    }
                    delayEffectUsed = true;
                }
            }

            aw.d.a(pc, item);

            if (delayEffectUsed) {
                item.a(new Timestamp(System.currentTimeMillis()));
                pc.j().j(item);
            }
            av.a.a(pc.aK(), item);
            return true;
        }
    }

    private final NativeAccess nativeAccess;
    private final long cooldownMs;
    private final LongSupplier clock;
    private long nextUseAtMs;

    public AutoHunt850ConsumableAdapter(u pc, long cooldownMs) {
        this(new PlayerNativeAccess(pc), cooldownMs, System::currentTimeMillis);
    }

    AutoHunt850ConsumableAdapter(NativeAccess nativeAccess, long cooldownMs, LongSupplier clock) {
        if (nativeAccess == null) {
            throw new NullPointerException("nativeAccess");
        }
        if (clock == null) {
            throw new NullPointerException("clock");
        }
        if (cooldownMs < 0L) {
            throw new IllegalArgumentException("cooldownMs");
        }
        this.nativeAccess = nativeAccess;
        this.cooldownMs = cooldownMs;
        this.clock = clock;
    }

    @Override
    public int currentHp() {
        return nativeAccess.currentHp();
    }

    @Override
    public int maxHp() {
        return nativeAccess.maxHp();
    }

    @Override
    public boolean isBlocked() {
        return nativeAccess.blocked();
    }

    @Override
    public boolean isOnCooldown() {
        return cooldownMs > 0L && clock.getAsLong() < nextUseAtMs;
    }

    @Override
    public boolean hasConsumable(int itemId) {
        return nativeAccess.hasConsumable(itemId);
    }

    @Override
    public boolean useConsumable(int itemId) {
        if (!nativeAccess.useHpPotion(itemId)) {
            return false;
        }
        if (cooldownMs > 0L) {
            long now = clock.getAsLong();
            nextUseAtMs = now > Long.MAX_VALUE - cooldownMs ? Long.MAX_VALUE : now + cooldownMs;
        }
        return true;
    }

    public void reset() {
        nextUseAtMs = 0L;
    }
}
