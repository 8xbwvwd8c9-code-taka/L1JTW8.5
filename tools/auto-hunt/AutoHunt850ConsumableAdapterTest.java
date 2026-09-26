package auto.hunt;

import java.util.function.LongSupplier;

public final class AutoHunt850ConsumableAdapterTest {
    private static final class Clock implements LongSupplier {
        long now;
        @Override public long getAsLong() { return now; }
    }

    private static final class FakeNative implements AutoHunt850ConsumableAdapter.NativeAccess {
        int hp = 40;
        int maxHp = 100;
        boolean blocked;
        boolean available = true;
        boolean useSucceeds = true;
        int uses;

        @Override public int currentHp() { return hp; }
        @Override public int maxHp() { return maxHp; }
        @Override public boolean blocked() { return blocked; }
        @Override public boolean hasConsumable(int itemId) { return available; }
        @Override public boolean useHpPotion(int itemId) { uses++; return useSucceeds; }
    }

    public static void main(String[] args) {
        successfulUseStartsIndependentCooldown();
        rejectedUseDoesNotStartCooldown();
        resetClearsCooldown();
        System.out.println("AUTO_HUNT_850_CONSUMABLE_ADAPTER_TEST=PASS");
    }

    private static void successfulUseStartsIndependentCooldown() {
        Clock clock = new Clock();
        FakeNative nativeAccess = new FakeNative();
        AutoHunt850ConsumableAdapter adapter = new AutoHunt850ConsumableAdapter(nativeAccess, 1000L, clock);

        check(!adapter.isOnCooldown(), "new adapter must not start on cooldown");
        check(adapter.useConsumable(40010), "native success must be preserved");
        check(adapter.isOnCooldown(), "successful potion must start cooldown");
        clock.now = 999L;
        check(adapter.isOnCooldown(), "cooldown must remain active before deadline");
        clock.now = 1000L;
        check(!adapter.isOnCooldown(), "cooldown boundary must reopen at deadline");
        check(nativeAccess.uses == 1, "successful use must invoke native path once");
    }

    private static void rejectedUseDoesNotStartCooldown() {
        Clock clock = new Clock();
        FakeNative nativeAccess = new FakeNative();
        nativeAccess.useSucceeds = false;
        AutoHunt850ConsumableAdapter adapter = new AutoHunt850ConsumableAdapter(nativeAccess, 1000L, clock);

        check(!adapter.useConsumable(40010), "native rejection must be preserved");
        check(!adapter.isOnCooldown(), "rejected use must not consume cooldown");
    }

    private static void resetClearsCooldown() {
        Clock clock = new Clock();
        FakeNative nativeAccess = new FakeNative();
        AutoHunt850ConsumableAdapter adapter = new AutoHunt850ConsumableAdapter(nativeAccess, 1000L, clock);

        check(adapter.useConsumable(40010), "setup use must succeed");
        check(adapter.isOnCooldown(), "setup must enter cooldown");
        adapter.reset();
        check(!adapter.isOnCooldown(), "reset must clear potion timing state");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
