/*
 * Decompiled with CFR 0.152.
 */
package ay;

import ap.q;
import ap.u;
import aq.am;
import ay.f;
import be.cm;
import be.dc;
import be.ds;

public class c
extends f {
    private boolean a = false;
    private boolean b = false;
    private final int c = 1;

    public c(int oId) {
        super(oId);
    }

    @Override
    public void a(u pc) {
        if (pc.fu().c()) {
            if (!this.a) {
                this.a = true;
                this.b = true;
            } else {
                this.b = false;
            }
            if (pc.ev() >= 49 && pc.cC() < 1540000) {
                int value = pc.cC() + 11;
                pc.K(value);
            }
        } else if (this.a) {
            this.a = false;
            this.b = true;
        } else {
            this.b = false;
        }
        if (this.b) {
            pc.a(new dc(463, this.a ? 128 : 0));
            if (pc.bB(4078)) {
                pc.a(4078, this.a);
                pc.a(new cm(86, 62, this.a ? 2 : 1, pc.bC(4078)));
            }
        }
        if (pc.fp() >= 807 && pc.fp() <= 813 || pc.fp() >= 53 && pc.fp() <= 56) {
            if (pc.cS() <= 0) {
                am.a(pc, 33703, 32502, 4, 5, true);
            } else {
                pc.be(pc.cS() - 1);
                if (!pc.cR()) {
                    pc.y(true);
                    pc.a(new dc(540, pc.cS(), "\\fZ$20823 "));
                    pc.a(new ds(1527, "" + pc.cS() / 60));
                }
            }
        } else if (pc.fp() >= 280 && pc.fp() <= 284) {
            if (pc.cT() <= 0) {
                am.a(pc, 33703, 32502, 4, 5, true);
            } else {
                pc.bf(pc.cT() - 1);
                if (!pc.cR()) {
                    pc.y(true);
                    pc.a(new dc(540, pc.cT(), "\\fZ$20823 "));
                    pc.a(new ds(1527, "" + pc.cT() / 60));
                }
            }
        } else if (pc.fp() == 814 || pc.fp() >= 30 && pc.fp() <= 37) {
            if (pc.cU() <= 0) {
                am.a(pc, 33703, 32502, 4, 5, true);
            } else {
                pc.bg(pc.cU() - 1);
                if (!pc.cR()) {
                    pc.y(true);
                    pc.a(new dc(540, pc.cU(), "\\fZ$20823 "));
                    pc.a(new ds(1527, "" + pc.cU() / 60));
                }
            }
        } else if (pc.fp() >= 285 && pc.fp() <= 289) {
            if (pc.cV() <= 0) {
                am.a(pc, 33703, 32502, 4, 5, true);
            } else {
                pc.bh(pc.cV() - 1);
                if (!pc.cR()) {
                    pc.y(true);
                    pc.a(new dc(540, pc.cV(), "\\fZ$20823 "));
                    pc.a(new ds(1527, "" + pc.cV() / 60));
                }
            }
        } else if (pc.fp() == 1931) {
            if (pc.cW() <= 0) {
                am.a(pc, 33703, 32502, 4, 5, true);
            } else {
                pc.bi(pc.cW() - 1);
                if (!pc.cR()) {
                    pc.y(true);
                    pc.a(new dc(540, pc.cW(), "\\fZ$20823 "));
                    pc.a(new ds(1527, "" + pc.cW() / 60));
                }
            }
            q item = pc.j().b(640368);
            if (item != null && item.E() >= 5) {
                int need_count = 0;
                for (q needItem : pc.j().d()) {
                    if (needItem.N() != 640357 && needItem.N() != 640358) continue;
                    need_count += needItem.E();
                }
                if (item.E() / 5 >= need_count) {
                    am.a(pc, 33703, 32502, 4, 5, true);
                }
            }
        } else if (pc.fp() >= 121 && pc.fp() <= 130) {
            if (pc.cX() <= 0) {
                am.a(pc, 33440, 32822, 4, 5, true);
            } else {
                pc.bj(pc.cX() - 1);
                if (!pc.cR()) {
                    pc.y(true);
                    pc.a(new dc(540, pc.cX(), "\\fZ$20823 "));
                    pc.a(new ds(1527, "" + pc.cX() / 60));
                }
            }
        } else if (pc.fp() >= 451 && pc.fp() <= 479 || pc.fp() >= 490 && pc.fp() <= 496) {
            if (pc.cY() <= 0) {
                am.a(pc, 33703, 32502, 4, 5, true);
            } else {
                pc.bk(pc.cY() - 1);
                if (!pc.cR()) {
                    pc.y(true);
                    pc.a(new dc(540, pc.cY(), "\\fZ$20823 "));
                    pc.a(new ds(1527, "" + pc.cY() / 60));
                }
            }
        } else if (pc.cR()) {
            pc.y(false);
        }
        if (pc.fp() == 2005 || pc.fp() == 86) {
            if (pc.ev() >= 52 && !pc.l()) {
                am.a(pc, 33084, 33391, 4, 5, true);
            }
        } else if (pc.fp() == 7783 || pc.fp() >= 12146 && pc.fp() <= 12149) {
            if (pc.ev() >= 55 && !pc.l()) {
                am.a(pc, 32583, 32931, 0, 5, true);
            }
        } else if (pc.fp() == 777) {
            if (pc.ev() >= 80) {
                am.a(pc, 34043, 32184, 4, 5, true);
            }
        } else if ((pc.fp() == 778 || pc.fp() == 779) && pc.ev() >= 80) {
            am.a(pc, 32608, 33178, 4, 5, true);
        }
    }
}

