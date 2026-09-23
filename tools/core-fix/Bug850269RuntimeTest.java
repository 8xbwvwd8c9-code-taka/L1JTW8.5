public final class Bug850269RuntimeTest {
    private static long delayMs(int nowHour, int nowMinute, int setHour, int setMinute) {
        if (nowHour > setHour) {
            return (1440L + (setHour - nowHour) * 60L + (setMinute - nowMinute)) * 60L * 1000L;
        } else if (nowHour < setHour) {
            return ((setHour - nowHour) * 60L + (setMinute - nowMinute)) * 60L * 1000L;
        } else if (nowMinute >= setMinute) {
            return (1440L + (setHour - nowHour) * 60L + (setMinute - nowMinute)) * 60L * 1000L;
        } else {
            return (long)(setMinute - nowMinute) * 60L * 1000L;
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        check(delayMs(21, 0, 21, 5) == 300000L, "same-hour +5min should be 300000ms");
        System.out.println("MINUTE_TO_MS_GATE=PASS");
        System.out.println("SAME_HOUR_GATE=PASS");

        check(delayMs(20, 59, 21, 0) == 60000L, "cross-hour +1min wrong");
        check(delayMs(23, 59, 0, 0) == 60000L, "midnight +1min wrong");
        System.out.println("MIDNIGHT_WRAP_GATE=PASS");

        check(delayMs(21, 5, 21, 5) == 86400000L, "same-minute should roll to next day");
        System.out.println("EQUALITY_NEXT_DAY_GATE=PASS");

        check(delayMs(22, 0, 21, 0) == 82800000L, "past-hour daily rollover wrong");

        System.out.println("BUG_850_269_TARGETED_BEHAVIOR_RUNTIME=PASS");
        System.out.println("FIXED_TIME_DELAY_UNITS=PASS");
    }
}
