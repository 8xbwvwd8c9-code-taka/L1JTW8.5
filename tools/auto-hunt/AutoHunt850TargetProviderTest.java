import auto.hunt.AutoHunt850TargetProvider;
import auto.hunt.AutoHuntRuntimeSettings;
import auto.hunt.AutoHuntTargetSelector;
import ap.s;
import ap.u;

public final class AutoHunt850TargetProviderTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static final class CaptureSelector implements AutoHuntTargetSelector {
        boolean boss;
        boolean avoid;
        int patrolX;
        int patrolY;
        int patrolRadius;
        int engageRange;
        final s result = new s();

        @Override
        public s select(u pc, boolean treatBossAsNormal, boolean avoidOccupied,
                        int patrolX, int patrolY, int patrolRadius, int engageRange) {
            this.boss = treatBossAsNormal;
            this.avoid = avoidOccupied;
            this.patrolX = patrolX;
            this.patrolY = patrolY;
            this.patrolRadius = patrolRadius;
            this.engageRange = engageRange;
            return result;
        }
    }

    public static void main(String[] args) {
        u pc = new u();
        pc.weaponRange = 2;
        pc.hasWeapon = true;
        ao.be.a().skillRange = 7;

        AutoHuntRuntimeSettings settings = new AutoHuntRuntimeSettings(
                true, true, true, 32681, 32866, 12, true, 123);
        CaptureSelector capture = new CaptureSelector();
        AutoHunt850TargetProvider provider = new AutoHunt850TargetProvider(pc, settings, capture);

        check(provider.engageRange() == 7,
                "provider must expose the same dynamic engage range used for selection");
        check(provider.select() == capture.result, "provider must return selector result");
        check(capture.boss, "boss setting must reach selector");
        check(capture.avoid, "avoid-occupied setting must reach selector");
        check(capture.patrolX == 32681 && capture.patrolY == 32866 && capture.patrolRadius == 12,
                "patrol settings must reach selector");
        check(capture.engageRange == 7,
                "engage range must use max of weapon and configured single-skill range");

        pc.hasWeapon = false;
        AutoHuntRuntimeSettings noOptionalFeatures = new AutoHuntRuntimeSettings(
                false, false, false, 0, 0, 0, false, 0);
        CaptureSelector capture2 = new CaptureSelector();
        AutoHunt850TargetProvider provider2 = new AutoHunt850TargetProvider(pc, noOptionalFeatures, capture2);
        check(provider2.engageRange() == 1, "no weapon and no single magic must expose range one");
        provider2.select();
        check(capture2.patrolRadius == 0, "disabled patrol must map to radius zero");
        check(capture2.engageRange == 1, "selector must receive exposed engage range");

        System.out.println("AUTO_HUNT_850_TARGET_PROVIDER_TEST=PASS");
    }
}
