import auto.hunt.AutoHuntBossIndex;
import java.util.Arrays;
import java.util.Collections;

public final class AutoHuntBossIndexTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        AutoHuntBossIndex index = new AutoHuntBossIndex();
        check(!index.isBoss(45684), "empty index must not classify boss");
        index.replaceAll(Arrays.asList(45684, 45685, 45684));
        check(index.isBoss(45684), "loaded npc id must classify boss");
        check(index.isBoss(45685), "second loaded npc id must classify boss");
        check(!index.isBoss(81257), "unloaded npc id must stay normal");
        index.replaceAll(Collections.<Integer>emptyList());
        check(!index.isBoss(45684), "reload must replace old snapshot");
        System.out.println("AUTO_HUNT_BOSS_INDEX_TEST=PASS");
    }
}
