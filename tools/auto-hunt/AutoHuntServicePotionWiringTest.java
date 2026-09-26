import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class AutoHuntServicePotionWiringTest {
    public static void main(String[] args) throws Exception {
        String source = new String(Files.readAllBytes(Paths.get(
                "recovered-src-obf/auto/hunt/AutoHuntService.java")), StandardCharsets.UTF_8);

        int potion = source.indexOf("AutoHuntConsumableController.tryConsume");
        int skill = source.indexOf("skillCaster.cast");
        check(potion >= 0, "service must invoke potion controller");
        check(skill >= 0, "service must keep skill caster");
        check(potion < skill, "HP potion must have priority over skill");
        check(source.contains("potionAdapter.reset();"), "stop/reset path must clear potion timing");
        check(source.contains("if (potionResult == AutoHuntConsumableController.Result.CONSUMED)"),
                "successful potion use must consume the tick");
        System.out.println("AUTO_HUNT_SERVICE_POTION_WIRING_TEST=PASS");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
