package auto.hunt;

import ap.s;
import ap.u;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class AutoHuntService {
    private static final long DEFAULT_PERIOD_MS = 200L;
    private static final Object LOCK = new Object();
    private static final Object BOSS_LOCK = new Object();
    private static final Map<Integer, Entry> SESSIONS = new HashMap<Integer, Entry>();
    private static final AutoHuntBossIndex BOSS_INDEX = new AutoHuntBossIndex();
    private static volatile boolean bossIndexLoaded;

    private AutoHuntService() {
    }

    public static long start(u pc) {
        if (pc == null) {
            throw new NullPointerException("pc");
        }
        return startSession(pc, new AutoHunt850Session(pc, DEFAULT_PERIOD_MS));
    }

    public static long start(u pc, final AutoHuntRuntimeSettings settings) {
        if (pc == null) {
            throw new NullPointerException("pc");
        }
        if (settings == null) {
            throw new NullPointerException("settings");
        }
        ensureBossIndexLoaded();

        AutoHuntTargetSelector selector = new AutoHunt850TargetSelector(BOSS_INDEX);
        final AutoHunt850TargetProvider provider = new AutoHunt850TargetProvider(pc, settings, selector);
        final AutoHunt850Mover mover = new AutoHunt850Mover(pc);
        final AutoHunt850Attacker attacker = new AutoHunt850Attacker(pc);
        final AutoHunt850SkillCaster skillCaster = settings.autoMagicOn()
                ? new AutoHunt850SkillCaster(pc)
                : null;
        final AutoHunt850ConsumableAdapter potionAdapter = settings.autoHpPotionOn()
                ? new AutoHunt850ConsumableAdapter(pc, settings.hpPotionCooldownMs())
                : null;

        AutoHunt850Session session = new AutoHunt850Session(
                pc,
                DEFAULT_PERIOD_MS,
                provider,
                new AutoHunt850Session.TargetAction() {
                    @Override
                    public boolean onTarget(s target) {
                        if (potionAdapter != null) {
                            AutoHuntConsumableController.Result potionResult = AutoHuntConsumableController.tryConsume(
                                    potionAdapter,
                                    true,
                                    settings.hpPotionMode(),
                                    settings.hpPotionThreshold(),
                                    settings.hpPotionItemId());
                            if (potionResult == AutoHuntConsumableController.Result.CONSUMED) {
                                return true;
                            }
                        }

                        if (skillCaster != null) {
                            AutoHunt850SkillCaster.Result skillResult = skillCaster.cast(
                                    target,
                                    settings.singleSkillId());
                            if (skillResult == AutoHunt850SkillCaster.Result.INVALID_TARGET) {
                                return false;
                            }
                            if (skillResult == AutoHunt850SkillCaster.Result.CAST_ATTEMPTED) {
                                return true;
                            }
                        }

                        int basicAttackRange = attacker.attackRange();
                        AutoHuntMoveController.Result moveResult = mover.moveToward(target, basicAttackRange);
                        if (moveResult == AutoHuntMoveController.Result.INVALID_TARGET
                                || moveResult == AutoHuntMoveController.Result.UNREACHABLE) {
                            return false;
                        }
                        if (moveResult != AutoHuntMoveController.Result.IN_RANGE) {
                            return true;
                        }

                        AutoHuntAttackController.Result attackResult = attacker.attack(target, basicAttackRange);
                        return attackResult != AutoHuntAttackController.Result.INVALID_TARGET;
                    }

                    @Override
                    public void reset() {
                        if (potionAdapter != null) {
                            potionAdapter.reset();
                        }
                        if (skillCaster != null) {
                            skillCaster.reset();
                        }
                        mover.reset();
                        attacker.reset();
                    }
                });
        return startSession(pc, session);
    }

    private static long startSession(u pc, AutoHunt850Session created) {
        synchronized (LOCK) {
            int objId = pc.fr();
            Entry current = SESSIONS.get(objId);
            if (current != null) {
                if (current.pc == pc && current.session.isRunning()) {
                    return current.session.start();
                }
                current.session.stop();
                SESSIONS.remove(objId);
            }
            Entry entry = new Entry(pc, created);
            SESSIONS.put(objId, entry);
            try {
                return created.start();
            }
            catch (RuntimeException ex) {
                SESSIONS.remove(objId);
                throw ex;
            }
        }
    }

    private static void ensureBossIndexLoaded() {
        if (bossIndexLoaded) {
            return;
        }
        synchronized (BOSS_LOCK) {
            if (bossIndexLoaded) {
                return;
            }
            try {
                AutoHuntBossIndexLoader.load(BOSS_INDEX);
            }
            catch (SQLException ex) {
                throw new IllegalStateException("failed to load auto-hunt boss index", ex);
            }
            bossIndexLoaded = true;
        }
    }

    public static void stop(u pc) {
        if (pc == null) {
            return;
        }
        synchronized (LOCK) {
            int objId = pc.fr();
            Entry current = SESSIONS.get(objId);
            if (current == null || current.pc != pc) {
                return;
            }
            SESSIONS.remove(objId);
            current.session.stop();
        }
    }

    public static boolean isRunning(u pc) {
        if (pc == null) {
            return false;
        }
        synchronized (LOCK) {
            Entry current = SESSIONS.get(pc.fr());
            return current != null && current.pc == pc && current.session.isRunning();
        }
    }

    private static final class Entry {
        private final u pc;
        private final AutoHunt850Session session;

        private Entry(u pc, AutoHunt850Session session) {
            this.pc = pc;
            this.session = session;
        }
    }
}
