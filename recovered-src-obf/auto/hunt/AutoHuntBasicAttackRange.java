package auto.hunt;

public final class AutoHuntBasicAttackRange {
    private AutoHuntBasicAttackRange() {
    }

    public static int resolve(boolean hasWeapon, int weaponRange) {
        if (!hasWeapon) {
            return 1;
        }
        return weaponRange < 0 ? 15 : weaponRange;
    }
}
