package auto.hunt;

import ap.s;
import ap.u;
import bh.v;

public final class AutoHunt850SkillCaster {
    public enum Result {
        INVALID_SKILL,
        UNSUPPORTED_SKILL,
        INVALID_TARGET,
        BLOCKED,
        OUT_OF_RANGE,
        COOLDOWN,
        CAST_ATTEMPTED,
        CAST_REJECTED
    }

    private final u pc;
    private final AutoHuntSkillController controller = new AutoHuntSkillController();

    public AutoHunt850SkillCaster(u pc) {
        if (pc == null) {
            throw new NullPointerException("pc");
        }
        this.pc = pc;
    }

    public Result cast(s target, int skillId) {
        return cast(target, skillId, System.currentTimeMillis());
    }

    public Result cast(final s target, final int skillId, long nowMs) {
        if (skillId <= 0) {
            return Result.INVALID_SKILL;
        }

        final v skill = ao.be.a().a(skillId);
        final bf.a executor = bi.g.a(skillId);
        if (skill == null || executor == null) {
            return Result.INVALID_SKILL;
        }
        if (isSpecialPayloadSkill(skillId) || skill.q() != 0) {
            return Result.UNSUPPORTED_SKILL;
        }
        if (!isValidTarget(target)) {
            return Result.INVALID_TARGET;
        }
        if (!pc.fq().q() || !pc.h(skillId) || pc.eX() || pc.aR()) {
            return Result.BLOCKED;
        }
        if (!isInRange(target, skill.p())) {
            return Result.OUT_OF_RANGE;
        }
        if (!executor.a(pc, target.fr(), skillId)) {
            return Result.BLOCKED;
        }

        AutoHuntSkillController.Result result = controller.tryCast(
                skillId,
                Math.max(0L, (long)skill.h()),
                new AutoHuntSkillController.Attempt() {
                    @Override
                    public boolean cast() {
                        if (!isValidTarget(target)
                                || !pc.fq().q()
                                || !pc.h(skillId)
                                || pc.eX()
                                || pc.aR()
                                || !isInRange(target, skill.p())) {
                            return false;
                        }
                        executor.a(pc, target.fr(), skillId, target.fs(), target.ft(), null);
                        return true;
                    }
                },
                nowMs);

        if (result == AutoHuntSkillController.Result.COOLDOWN) {
            return Result.COOLDOWN;
        }
        if (result == AutoHuntSkillController.Result.CAST_REJECTED) {
            return Result.CAST_REJECTED;
        }
        return Result.CAST_ATTEMPTED;
    }

    public long nextCastAtMs(int skillId) {
        return controller.nextCastAtMs(skillId);
    }

    public void reset() {
        controller.reset();
    }

    private boolean isValidTarget(s target) {
        return target != null
                && !target.eX()
                && target.ea() > 0
                && target.fp() == pc.fp()
                && target.ac() == 0;
    }

    private boolean isInRange(s target, int range) {
        if (range < 0) {
            return pc.fu().e(target.fu());
        }
        return pc.fu().c(target.fu()) <= range
                && pc.i(target.fs(), target.ft());
    }

    private static boolean isSpecialPayloadSkill(int skillId) {
        return skillId == 5
                || skillId == 58
                || skillId == 63
                || skillId == 69
                || skillId == 116
                || skillId == 118;
    }
}
