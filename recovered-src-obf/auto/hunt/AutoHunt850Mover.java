package auto.hunt;

import ap.s;
import ap.u;
import aq.ak;
import be.cb;

public final class AutoHunt850Mover {
    private static final int[] DX = {0, 1, 1, 1, 0, -1, -1, -1};
    private static final int[] DY = {-1, -1, 0, 1, 1, 1, 0, -1};

    private final u pc;
    private final AutoHuntMoveController controller = new AutoHuntMoveController();

    public AutoHunt850Mover(u pc) {
        if (pc == null) {
            throw new NullPointerException("pc");
        }
        this.pc = pc;
    }

    public AutoHuntMoveController.Result moveToward(s target, int engageRange) {
        return moveToward(target, engageRange, System.currentTimeMillis());
    }

    public AutoHuntMoveController.Result moveToward(final s target, final int engageRange, long nowMs) {
        if (target == null || target.eX() || target.ea() <= 0 || target.fp() != pc.fp()) {
            return AutoHuntMoveController.Result.INVALID_TARGET;
        }
        return controller.tryMove(new AutoHuntMoveController.Host() {
            @Override
            public boolean isInRange(int range) {
                return pc.c(target.fs(), target.ft(), range);
            }

            @Override
            public int x() {
                return pc.fs();
            }

            @Override
            public int y() {
                return pc.ft();
            }

            @Override
            public int targetX() {
                return target.fs();
            }

            @Override
            public int targetY() {
                return target.ft();
            }

            @Override
            public boolean canStep(int x, int y, int heading) {
                return pc.fq().c(x, y, heading);
            }

            @Override
            public long moveIntervalMs() {
                return pc.ce().b(ak.a.a);
            }

            @Override
            public boolean step(int heading) {
                return AutoHunt850Mover.this.step(heading);
            }

            @Override
            public void markUnreachable() {
                target.j(pc.fr() + 100000, 20000);
            }
        }, engageRange, nowMs);
    }

    public void reset() {
        controller.reset();
    }

    private boolean step(int heading) {
        if (heading < 0 || heading >= 8 || pc.aR()) {
            return false;
        }
        int x = pc.fs();
        int y = pc.ft();
        if (!pc.fq().c(x, y, heading)) {
            return false;
        }

        int nextX = x + DX[heading];
        int nextY = y + DY[heading];
        if (aq.l.a().a(nextX, nextY, pc.fq().b(), pc)) {
            return true;
        }

        pc.fq().a(pc.fu(), true);
        pc.fu().a(nextX, nextY);
        pc.ct(heading);
        pc.fq().a(pc.fu(), false);
        ao.bi.a().a(pc);

        if (pc.ff()) {
            pc.c(new cb(pc));
        } else {
            pc.b(new cb(pc));
        }
        pc.a(new cb(pc));
        return true;
    }
}
