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
    private final boolean autoHpPotionOn;
    private final AutoHuntConsumableController.Mode hpPotionMode;
    private final int hpPotionThreshold;
    private final int hpPotionItemId;
    private final long hpPotionCooldownMs;

    public AutoHuntRuntimeSettings(boolean treatBossAsNormal,
                                   boolean avoidOccupied,
                                   boolean patrolEnabled,
                                   int patrolX,
                                   int patrolY,
                                   int patrolRadius,
                                   boolean autoMagicOn,
                                   int singleSkillId) {
        this(treatBossAsNormal, avoidOccupied, patrolEnabled, patrolX, patrolY, patrolRadius,
                autoMagicOn, singleSkillId,
                false, null, 0, 0, 0L);
    }

    public AutoHuntRuntimeSettings(boolean treatBossAsNormal,
                                   boolean avoidOccupied,
                                   boolean patrolEnabled,
                                   int patrolX,
                                   int patrolY,
                                   int patrolRadius,
                                   boolean autoMagicOn,
                                   int singleSkillId,
                                   boolean autoHpPotionOn,
                                   AutoHuntConsumableController.Mode hpPotionMode,
                                   int hpPotionThreshold,
                                   int hpPotionItemId,
                                   long hpPotionCooldownMs) {
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
        if (autoHpPotionOn) {
            if (hpPotionMode == null) {
                throw new NullPointerException("hpPotionMode");
            }
            if (hpPotionItemId <= 0) {
                throw new IllegalArgumentException("hpPotionItemId");
            }
            if (hpPotionCooldownMs <= 0L) {
                throw new IllegalArgumentException("hpPotionCooldownMs");
            }
            if (hpPotionMode == AutoHuntConsumableController.Mode.PERCENT) {
                if (hpPotionThreshold < 1 || hpPotionThreshold > 100) {
                    throw new IllegalArgumentException("hpPotionThreshold");
                }
            } else if (hpPotionThreshold < 1) {
                throw new IllegalArgumentException("hpPotionThreshold");
            }
        } else {
            if (hpPotionMode != null || hpPotionThreshold != 0 || hpPotionItemId != 0 || hpPotionCooldownMs != 0L) {
                throw new IllegalArgumentException("disabled HP potion requires empty HP potion fields");
            }
        }

        this.treatBossAsNormal = treatBossAsNormal;
        this.avoidOccupied = avoidOccupied;
        this.patrolEnabled = patrolEnabled;
        this.patrolX = patrolX;
        this.patrolY = patrolY;
        this.patrolRadius = patrolEnabled ? patrolRadius : 0;
        this.autoMagicOn = autoMagicOn;
        this.singleSkillId = autoMagicOn ? singleSkillId : 0;
        this.autoHpPotionOn = autoHpPotionOn;
        this.hpPotionMode = hpPotionMode;
        this.hpPotionThreshold = hpPotionThreshold;
        this.hpPotionItemId = hpPotionItemId;
        this.hpPotionCooldownMs = hpPotionCooldownMs;
    }

    public boolean treatBossAsNormal() { return treatBossAsNormal; }
    public boolean avoidOccupied() { return avoidOccupied; }
    public boolean patrolEnabled() { return patrolEnabled; }
    public int patrolX() { return patrolX; }
    public int patrolY() { return patrolY; }
    public int patrolRadius() { return patrolRadius; }
    public boolean autoMagicOn() { return autoMagicOn; }
    public int singleSkillId() { return singleSkillId; }
    public boolean autoHpPotionOn() { return autoHpPotionOn; }
    public AutoHuntConsumableController.Mode hpPotionMode() { return hpPotionMode; }
    public int hpPotionThreshold() { return hpPotionThreshold; }
    public int hpPotionItemId() { return hpPotionItemId; }
    public long hpPotionCooldownMs() { return hpPotionCooldownMs; }
}
