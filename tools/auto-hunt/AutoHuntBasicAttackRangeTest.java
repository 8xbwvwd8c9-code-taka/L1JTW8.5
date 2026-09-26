import auto.hunt.AutoHuntBasicAttackRange;

public final class AutoHuntBasicAttackRangeTest {
    public static void main(String[] args) {
        check(AutoHuntBasicAttackRange.resolve(false, 0) == 1, "unarmed range");
        check(AutoHuntBasicAttackRange.resolve(true, 2) == 2, "weapon range");
        check(AutoHuntBasicAttackRange.resolve(true, -1) == 15, "850 C_Attack negative range");
        System.out.println("AUTO_HUNT_BASIC_ATTACK_RANGE_TEST=PASS");
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
}
