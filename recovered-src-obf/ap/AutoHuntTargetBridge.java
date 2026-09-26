package ap;

public final class AutoHuntTargetBridge {
    private AutoHuntTargetBridge() {
    }

    public static aq.f currentTarget(t npc) {
        return npc == null ? null : npc.m;
    }
}
