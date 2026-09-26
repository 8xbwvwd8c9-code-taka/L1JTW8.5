import auto.hunt.AutoHuntRuntimeSettings;

public final class AutoHuntRuntimeSettingsTest {
    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    public static void main(String[] args) {
        AutoHuntRuntimeSettings settings = new AutoHuntRuntimeSettings(
                true,  // treatBossAsNormal
                true,  // avoidOccupied
                true,  // patrolEnabled
                32681, 32866, 12,
                true,  // autoMagicOn
                123);

        check(settings.treatBossAsNormal(), "boss flag must be explicit");
        check(settings.avoidOccupied(), "avoid-occupied flag must be explicit");
        check(settings.patrolEnabled(), "patrol flag must be explicit");
        check(settings.patrolX() == 32681 && settings.patrolY() == 32866,
                "patrol origin must be preserved");
        check(settings.patrolRadius() == 12, "patrol radius must be preserved");
        check(settings.autoMagicOn(), "auto magic flag must be explicit");
        check(settings.singleSkillId() == 123, "single skill id must be preserved");

        boolean badRadius = false;
        try {
            new AutoHuntRuntimeSettings(false, false, true, 1, 1, 0, false, 0);
        } catch (IllegalArgumentException expected) {
            badRadius = true;
        }
        check(badRadius, "enabled patrol requires positive radius");

        boolean badSkill = false;
        try {
            new AutoHuntRuntimeSettings(false, false, false, 0, 0, 0, true, 0);
        } catch (IllegalArgumentException expected) {
            badSkill = true;
        }
        check(badSkill, "enabled auto magic requires positive single skill id");

        System.out.println("AUTO_HUNT_RUNTIME_SETTINGS_TEST=PASS");
    }
}
