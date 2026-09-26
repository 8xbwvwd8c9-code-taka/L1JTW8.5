package auto.hunt;

public final class AutoHuntEngageRange {
    private AutoHuntEngageRange() {
    }

    public static int resolve(boolean hasWeapon, int weaponRange,
                              boolean autoMagicOn, int singleSkillRange) {
        int range = hasWeapon ? weaponRange : 1;
        if (range < 0) {
            range = 9;
        }
        if (autoMagicOn && singleSkillRange > range) {
            range = singleSkillRange;
        }
        return range;
    }
}
