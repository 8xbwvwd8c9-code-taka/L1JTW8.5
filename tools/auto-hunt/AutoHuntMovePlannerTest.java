import auto.hunt.AutoHuntMovePlanner;

public final class AutoHuntMovePlannerTest {
    public static void main(String[] args) {
        straightLineUsesDirectHeading();
        blockedDirectStepDetours();
        alreadyInRangeDoesNotMove();
        blockedAllDirectionsReturnsNoStep();
        beyondDonorRangeReturnsNoStep();
        rangedEngageRangeStopsEarly();
        System.out.println("AUTO_HUNT_MOVE_PLANNER_TEST=PASS");
    }

    private static void straightLineUsesDirectHeading() {
        AutoHuntMovePlanner.Passability open = new AutoHuntMovePlanner.Passability() {
            @Override
            public boolean canStep(int x, int y, int heading) {
                return true;
            }
        };
        int heading = AutoHuntMovePlanner.nextHeading(10, 10, 14, 10, 1, open);
        check(heading == 2, "east target should move east");
    }

    private static void blockedDirectStepDetours() {
        AutoHuntMovePlanner.Passability grid = new AutoHuntMovePlanner.Passability() {
            @Override
            public boolean canStep(int x, int y, int heading) {
                return !(x == 10 && y == 10 && heading == 2);
            }
        };
        int heading = AutoHuntMovePlanner.nextHeading(10, 10, 14, 10, 1, grid);
        check(heading == 3 || heading == 1, "blocked direct step should choose a diagonal detour");
    }

    private static void alreadyInRangeDoesNotMove() {
        final int[] calls = {0};
        AutoHuntMovePlanner.Passability grid = new AutoHuntMovePlanner.Passability() {
            @Override
            public boolean canStep(int x, int y, int heading) {
                calls[0]++;
                return true;
            }
        };
        int heading = AutoHuntMovePlanner.nextHeading(10, 10, 11, 10, 1, grid);
        check(heading == -1, "already in engage range should not move");
        check(calls[0] == 0, "already in range should not probe pathing");
    }

    private static void blockedAllDirectionsReturnsNoStep() {
        AutoHuntMovePlanner.Passability closed = new AutoHuntMovePlanner.Passability() {
            @Override
            public boolean canStep(int x, int y, int heading) {
                return false;
            }
        };
        int heading = AutoHuntMovePlanner.nextHeading(10, 10, 14, 10, 1, closed);
        check(heading == -1, "fully blocked map should return no step");
    }

    private static void beyondDonorRangeReturnsNoStep() {
        AutoHuntMovePlanner.Passability open = new AutoHuntMovePlanner.Passability() {
            @Override
            public boolean canStep(int x, int y, int heading) {
                return true;
            }
        };
        int heading = AutoHuntMovePlanner.nextHeading(10, 10, 31, 10, 1, open);
        check(heading == -1, "381 moveDirection cap is 20 tiles");
    }

    private static void rangedEngageRangeStopsEarly() {
        AutoHuntMovePlanner.Passability open = new AutoHuntMovePlanner.Passability() {
            @Override
            public boolean canStep(int x, int y, int heading) {
                return true;
            }
        };
        check(AutoHuntMovePlanner.nextHeading(10, 10, 13, 10, 3, open) == -1,
                "ranged target already in range should not move");
        check(AutoHuntMovePlanner.nextHeading(10, 10, 14, 10, 3, open) == 2,
                "ranged target just outside range should approach");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
