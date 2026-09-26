package auto.hunt;

import ap.q;
import ap.s;
import ap.u;
import bh.v;

/**
 * Maps explicit behavior settings onto 850 runtime data before target selection.
 * No launcher/UI defaults live here.
 */
public final class AutoHunt850TargetProvider implements AutoHunt850Session.TargetProvider {
    private final u pc;
    private final AutoHuntRuntimeSettings settings;
    private final AutoHuntTargetSelector selector;

    public AutoHunt850TargetProvider(u pc,
                                     AutoHuntRuntimeSettings settings,
                                     AutoHuntTargetSelector selector) {
        if (pc == null || settings == null || selector == null) {
            throw new NullPointerException();
        }
        this.pc = pc;
        this.settings = settings;
        this.selector = selector;
    }

    @Override
    public s select() {
        q weapon = pc.v();
        boolean hasWeapon = weapon != null;
        int weaponRange = hasWeapon ? weapon.a().aB() : 0;

        int singleSkillRange = 0;
        if (settings.autoMagicOn()) {
            v skill = ao.be.a().a(settings.singleSkillId());
            if (skill != null) {
                singleSkillRange = skill.p();
            }
        }

        int engageRange = AutoHuntEngageRange.resolve(
                hasWeapon,
                weaponRange,
                settings.autoMagicOn(),
                singleSkillRange);

        int patrolX = settings.patrolEnabled() ? settings.patrolX() : 0;
        int patrolY = settings.patrolEnabled() ? settings.patrolY() : 0;
        int patrolRadius = settings.patrolEnabled() ? settings.patrolRadius() : 0;

        return selector.select(
                pc,
                settings.treatBossAsNormal(),
                settings.avoidOccupied(),
                patrolX,
                patrolY,
                patrolRadius,
                engageRange);
    }
}
