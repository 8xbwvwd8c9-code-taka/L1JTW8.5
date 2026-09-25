import auto.hunt.AutoHuntTargetPolicy;
import auto.hunt.AutoHuntTargetPolicy.Candidate;
import java.util.Arrays;

public final class AutoHuntTargetPolicyTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static Candidate c(int id, int distance) {
        return new Candidate(id, false, false, false, false, 100, false, 1, true, distance, 0);
    }

    public static void main(String[] args) {
        AutoHuntTargetPolicy p = new AutoHuntTargetPolicy(10, 3);

        check(p.select(Arrays.asList(c(1, 5), c(2, 2)), false, 0).objId == 2,
                "nearest selectable monster must win");

        Candidate dead = new Candidate(3, false, false, false, true, 0, false, 1, true, 1, 0);
        Candidate hidden = new Candidate(4, false, false, false, false, 100, true, 1, true, 1, 0);
        Candidate immobile = new Candidate(5, false, false, false, false, 100, false, 0, true, 1, 0);
        Candidate excludedNpc = new Candidate(81257, false, true, false, false, 100, false, 1, true, 1, 0);
        Candidate blockedEffect = new Candidate(6, false, false, true, false, 100, false, 1, true, 1, 0);
        check(p.select(Arrays.asList(dead, hidden, immobile, excludedNpc, blockedEffect, c(7, 6)), false, 0).objId == 7,
                "381 exclusion gates must reject invalid monsters");

        Candidate boss = new Candidate(8, true, false, false, false, 100, false, 1, true, 1, 0);
        check(p.select(Arrays.asList(boss, c(9, 4)), false, 0).objId == 9,
                "boss must be excluded when treatBossAsNormal is false");
        check(p.select(Arrays.asList(boss, c(9, 4)), true, 0).objId == 8,
                "boss may compete normally when enabled");

        Candidate outsideButReachable = new Candidate(10, false, false, false, false, 100, false, 1, true, 2, 12);
        Candidate outsideTooFar = new Candidate(11, false, false, false, false, 100, false, 1, true, 1, 14);
        check(p.select(Arrays.asList(outsideTooFar, outsideButReachable), false, 10).objId == 10,
                "patrol target may be outside radius only within engage overflow");

        Candidate unreachable = new Candidate(12, false, false, false, false, 100, false, 1, false, 1, 0);
        check(p.select(Arrays.asList(unreachable, c(13, 3)), false, 0).objId == 13,
                "unreachable target must be rejected");

        check(p.select(Arrays.asList(c(14, 11), c(15, 10)), false, 0).objId == 15,
                "target range cap must be enforced");

        System.out.println("AUTO_HUNT_TARGET_POLICY_TEST=PASS");
    }
}
