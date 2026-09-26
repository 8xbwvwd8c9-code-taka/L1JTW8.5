import auto.hunt.AutoHuntConsumableController;
import auto.hunt.AutoHuntRuntimeSettings;

public final class AutoHuntRuntimeSettingsPotionTest {
    public static void main(String[] args) {
        enabledPotionFieldsAreExplicit();
        enabledPotionRejectsInvalidThreshold();
        legacyConstructorKeepsPotionDisabled();
        System.out.println("AUTO_HUNT_RUNTIME_SETTINGS_POTION_TEST=PASS");
    }

    private static void enabledPotionFieldsAreExplicit() {
        AutoHuntRuntimeSettings settings = new AutoHuntRuntimeSettings(
                false, false, false, 0, 0, 0, false, 0,
                true, AutoHuntConsumableController.Mode.PERCENT, 45, 40010, 750L);
        check(settings.autoHpPotionOn(), "HP potion must be enabled");
        check(settings.hpPotionMode() == AutoHuntConsumableController.Mode.PERCENT, "mode must round-trip");
        check(settings.hpPotionThreshold() == 45, "threshold must round-trip");
        check(settings.hpPotionItemId() == 40010, "item id must round-trip");
        check(settings.hpPotionCooldownMs() == 750L, "cooldown must round-trip");
    }

    private static void enabledPotionRejectsInvalidThreshold() {
        boolean thrown = false;
        try {
            new AutoHuntRuntimeSettings(false, false, false, 0, 0, 0, false, 0,
                    true, AutoHuntConsumableController.Mode.PERCENT, 101, 40010, 750L);
        } catch (IllegalArgumentException expected) {
            thrown = true;
        }
        check(thrown, "invalid percent threshold must be rejected");
    }

    private static void legacyConstructorKeepsPotionDisabled() {
        AutoHuntRuntimeSettings settings = new AutoHuntRuntimeSettings(
                false, false, false, 0, 0, 0, false, 0);
        check(!settings.autoHpPotionOn(), "legacy constructor must explicitly mean potion disabled");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
