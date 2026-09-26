import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class AutoHuntServicePotionWiringTest {
    public static void main(String[] args) throws Exception {
        String source = new String(Files.readAllBytes(Paths.get(
                "recovered-src-obf/auto/hunt/AutoHuntService.java")), StandardCharsets.UTF_8);

        int tickAction = source.indexOf("new AutoHunt850Session.TickAction()");
        int potion = source.indexOf("AutoHuntConsumableController.tryConsume");
        int targetAction = source.indexOf("new AutoHunt850Session.TargetAction()");
        int skill = source.indexOf("skillCaster.cast");

        check(tickAction >= 0, "service must wire a pre-target tick action");
        check(potion >= 0, "service must invoke potion controller");
        check(targetAction >= 0, "service must keep combat target action");
        check(skill >= 0, "service must keep skill caster");
        check(tickAction < potion && potion < targetAction,
                "HP potion must run in pre-target tick phase and not require a target");
        check(potion < skill, "HP potion must have priority over skill");
        check(source.contains("potionAdapter.reset();"), "stop/reset path must clear potion timing");
        check(source.contains("return potionResult != AutoHuntConsumableController.Result.CONSUMED;"),
                "successful potion use must consume the rest of the tick");
        System.out.println("AUTO_HUNT_SERVICE_POTION_WIRING_TEST=PASS");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
