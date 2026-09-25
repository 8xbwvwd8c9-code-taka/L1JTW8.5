import auto.hunt.AutoHuntEngageRange;

public final class AutoHuntEngageRangeTest {
    private static void check(int actual, int expected, String message) {
        if (actual != expected) throw new AssertionError(message + ": " + actual + " != " + expected);
    }

    public static void main(String[] args) {
        check(AutoHuntEngageRange.resolve(false, 0, false, 0), 1, "no weapon defaults to one");
        check(AutoHuntEngageRange.resolve(true, 2, false, 0), 2, "weapon range is authoritative");
        check(AutoHuntEngageRange.resolve(true, -1, false, 0), 9, "negative weapon range maps to nine");
        check(AutoHuntEngageRange.resolve(true, 0, false, 0), 0, "zero weapon range must not be silently rewritten");
        check(AutoHuntEngageRange.resolve(true, 2, true, 6), 6, "single magic may extend engage range");
        check(AutoHuntEngageRange.resolve(true, 9, true, 6), 9, "shorter magic must not shrink weapon range");
        check(AutoHuntEngageRange.resolve(true, 2, false, 9), 2, "disabled auto magic must not affect range");
        System.out.println("AUTO_HUNT_ENGAGE_RANGE_TEST=PASS");
    }
}
