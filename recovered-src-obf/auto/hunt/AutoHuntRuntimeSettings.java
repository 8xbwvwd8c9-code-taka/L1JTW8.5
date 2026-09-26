package auto.hunt;

/**
 * Explicit runtime inputs required by the auto-hunt behavior layer.
 *
 * This class is intentionally transport-agnostic: values may later come from an
 * 880 UI/packet adapter, but no 880 field or default is invented here.
 */
public final class AutoHuntRuntimeSettings {
    private final boolean treatBossAsNormal;
    private final boolean avoidOccupied;
    private final boolean patrolEnabled;
    private final int patrolX;
    private final int patrolY;
    private final int patrolRadius;
    private final boolean autoMagicOn;
    private final int singleSkillId;

    public AutoHuntRuntimeSettings(boolean treatBossAsNormal,
                                   boolean avoidOccupied,
                                   boolean patrolEnabled,
                                   int patrolX,
                                   int patrolY,
                                   int patrolRadius,
                                   boolean autoMagicOn,
                                   int singleSkillId) {
        if (patrolEnabled && patrolRadius <= 0) {
            throw new IllegalArgumentException("enabled patrol requires positive radius");
        }
        if (!patrolEnabled && patrolRadius < 0) {
            throw new IllegalArgumentException("patrolRadius");
        }
        if (autoMagicOn && singleSkillId <= 0) {
            throw new IllegalArgumentException("enabled auto magic requires positive single skill id");
        }
        if (!autoMagicOn && singleSkillId < 0) {
            throw new IllegalArgumentException("singleSkillId");
        }
        this.treatBossAsNormal = treatBossAsNormal;
        this.avoidOccupied = avoidOccupied;
        this.patrolEnabled = patrolEnabled;
        this.patrolX = patrolX;
        this.patrolY = patrolY;
        this.patrolRadius = patrolEnabled ? patrolRadius : 0;
        this.autoMagicOn = autoMagicOn;
        this.singleSkillId = autoMagicOn ? singleSkillId : 0;
    }

    public boolean treatBossAsNormal() {
        return treatBossAsNormal;
    }

    public boolean avoidOccupied() {
        return avoidOccupied;
    }

    public boolean patrolEnabled() {
        return patrolEnabled;
    }

    public int patrolX() {
        return patrolX;
    }

    public int patrolY() {
        return patrolY;
    }

    public int patrolRadius() {
        return patrolRadius;
    }

    public boolean autoMagicOn() {
        return autoMagicOn;
    }

    public int singleSkillId() {
        return singleSkillId;
    }
}
