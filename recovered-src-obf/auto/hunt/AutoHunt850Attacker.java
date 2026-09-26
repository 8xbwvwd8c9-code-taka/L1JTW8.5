package auto.hunt;

import ap.s;
import ap.u;
import aq.ak;

public final class AutoHunt850Attacker {
    private final u pc;
    private final AutoHuntAttackController controller = new AutoHuntAttackController();

    public AutoHunt850Attacker(u pc) {
        if (pc == null) {
            throw new NullPointerException("pc");
        }
        this.pc = pc;
    }

    public AutoHuntAttackController.Result attack(s target, int engageRange) {
        return attack(target, engageRange, System.currentTimeMillis());
    }

    public AutoHuntAttackController.Result attack(final s target, final int engageRange, long nowMs) {
        return controller.tryAttack(new AutoHuntAttackController.Host() {
            @Override
            public boolean isValidTarget() {
                return AutoHunt850Attacker.this.isValidTarget(target);
            }

            @Override
            public boolean isAttackBlocked() {
                return AutoHunt850Attacker.this.isAttackBlocked();
            }

            @Override
            public boolean isInRange(int range) {
                return pc.c(target.fs(), target.ft(), range);
            }

            @Override
            public long attackIntervalMs() {
                return pc.ce().b(ak.a.b);
            }

            @Override
            public boolean attack() {
                return AutoHunt850Attacker.this.issueAttack(target, engageRange);
            }
        }, engageRange, nowMs);
    }

    public void reset() {
        controller.reset();
    }

    private boolean isValidTarget(s target) {
        return target != null
                && !target.eX()
                && target.ea() > 0
                && target.fp() == pc.fp()
                && target.ac() == 0
                && target.O() > 0;
    }

    private boolean isAttackBlocked() {
        if (pc.bN() || pc.eX() || pc.aR() || pc.ed() || pc.ec()) {
            return true;
        }
        if (pc.j().h() > 82) {
            return true;
        }
        if (pc.bB(60) || pc.N()) {
            return true;
        }
        return pc.bB(33)
                || pc.bB(50)
                || pc.bB(66)
                || pc.bB(87)
                || pc.bB(208)
                || pc.bB(212)
                || pc.bB(1009)
                || pc.bB(1011);
    }

    private boolean issueAttack(s target, int engageRange) {
        if (!isValidTarget(target) || isAttackBlocked()) {
            return false;
        }
        if (!pc.c(target.fs(), target.ft(), engageRange)) {
            return false;
        }
        if (pc.bB(78)) {
            pc.bz(78);
        }
        if (pc.bB(32)) {
            pc.bz(32);
        }
        if (pc.bB(97) && !pc.bB(233)) {
            pc.bz(97);
        }
        pc.e(1);
        target.c(pc);
        return true;
    }
}
