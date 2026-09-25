package auto.hunt;

public final class AutoHuntEngageRange {
    private AutoHuntEngageRange() {
    }

    public static int resolve(int weaponRange, boolean autoMagicOn, int singleSkillRange) {
        int range = weaponRange;
        if (range == 0) {
            range = 1;
        }
        if (range < 0) {
            range = 9;
        }
        if (autoMagicOn && singleSkillRange > range) {
            range = singleSkillRange;
        }
        return Math.max(1, range);
    }
}
