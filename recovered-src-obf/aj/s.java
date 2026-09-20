/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.o;
import ao.w;
import ap.q;
import ap.u;
import aq.am;
import be.ck;
import be.cl;
import be.x;
import bi.c;
import bi.d;

public class s
extends cv {
    private static final String a = "[C] C_CharReset";

    public s(byte[] abyte0, bj.d clientthread) {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int stage = this.c();
        if (stage == 1) {
            int str = this.c();
            int intel = this.c();
            int wis = this.c();
            int dex = this.c();
            int con = this.c();
            int cha = this.c();
            int hp = c.a(pc);
            int mp = c.c(pc);
            pc.a(new cl(pc));
            pc.a(new x(pc, 1, hp, mp, 10, str, intel, wis, dex, con, cha));
            this.a(pc, hp, mp, str, intel, wis, dex, con, cha);
            o.a().d(pc);
        } else if (stage == 2) {
            int type2 = this.c();
            if (type2 == 0) {
                this.a(pc, 1);
            } else if (type2 == 7) {
                if (pc.cx() - pc.cw() < 10) {
                    return;
                }
                if (pc.cw() >= 40) {
                    return;
                }
                this.a(pc, 10);
            } else if (type2 == 1) {
                pc.o(1);
                this.a(pc, 1);
            } else if (type2 == 2) {
                pc.s(1);
                this.a(pc, 1);
            } else if (type2 == 3) {
                pc.t(1);
                this.a(pc, 1);
            } else if (type2 == 4) {
                pc.q(1);
                this.a(pc, 1);
            } else if (type2 == 5) {
                pc.p(1);
                this.a(pc, 1);
            } else if (type2 == 6) {
                pc.r(1);
                this.a(pc, 1);
            } else if (type2 == 8) {
                switch (this.c()) {
                    case 1: {
                        pc.o(1);
                        break;
                    }
                    case 2: {
                        pc.s(1);
                        break;
                    }
                    case 3: {
                        pc.t(1);
                        break;
                    }
                    case 4: {
                        pc.q(1);
                        break;
                    }
                    case 5: {
                        pc.p(1);
                        break;
                    }
                    case 6: {
                        pc.r(1);
                    }
                }
                if (pc.bB() > 0) {
                    pc.a(new x(pc.bB()));
                    return;
                }
                this.a(pc);
            }
        } else if (stage == 3) {
            pc.o(this.c() - pc.bf());
            pc.s(this.c() - pc.bj());
            pc.t(this.c() - pc.bk());
            pc.q(this.c() - pc.bh());
            pc.p(this.c() - pc.bg());
            pc.r(this.c() - pc.bi());
            this.a(pc);
        }
    }

    private void a(u pc) {
        pc.s(false);
        pc.aa();
        pc.a(pc.ew());
        pc.i_(pc.ex());
        if (pc.cx() != pc.ev()) {
            pc.b(pc.cx());
            pc.k(w.a(pc.cx()));
        }
        if (pc.ev() > 50) {
            pc.ay(pc.ev() - 50);
        } else {
            pc.ay(0);
        }
        pc.a(new ck(pc));
        q item = pc.j().b(49142);
        if (item != null) {
            pc.j().b(item, 1);
            pc.I();
        }
        am.a(pc, 32628, 32772, 4, 4, true);
    }

    private void a(u pc, int hp, int mp, int str, int intel, int wis, int dex, int con, int cha) {
        pc.m(hp - pc.bd());
        pc.n(mp - pc.be());
        pc.o(str - pc.bf());
        pc.s(intel - pc.bj());
        pc.t(wis - pc.bk());
        pc.q(dex - pc.bh());
        pc.p(con - pc.bg());
        pc.r(cha - pc.bi());
    }

    private void a(u pc, int addLv) {
        pc.aS(pc.cw() + addLv);
        int i2 = 0;
        while (i2 < addLv) {
            int randomHp = d.a(pc);
            int randomMp = d.b(pc);
            pc.m(randomHp);
            pc.n(randomMp);
            ++i2;
        }
        int newAc = 10 + d.a(pc.bh()) + pc.aC().d(pc.cw());
        pc.a(new x(pc, pc.cw(), pc.bd(), pc.be(), newAc, pc.bf(), pc.bj(), pc.bk(), pc.bh(), pc.bg(), pc.bi()));
    }

    @Override
    public String a() {
        return a;
    }
}

