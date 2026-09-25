/*
 * Decompiled with CFR 0.152.
 */
package bg;

import ao.a;
import ap.s;
import ap.t;
import ap.u;
import ap.z;
import aq.f;
import aq.j;
import be.ak;
import be.ba;
import be.bs;
import be.bv;
import be.ci;
import be.ck;
import be.cm;
import be.cn;
import be.cz;
import be.dc;
import be.do;
import be.ds;
import be.dx;
import be.ea;
import be.eb;
import be.ec;
import be.v;
import bi.g;

class c {
    c() {
    }

    public static void a(f cha, int skillId) {
        t npc;
        u pc;
        if (skillId < 600) {
            bf.a executor = g.a(skillId);
            executor.a(cha);
            return;
        }
        if (skillId == 15003 || skillId == 15004) {
            cha.V(false);
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new cz(pc.fr(), 0));
                pc.b(new cz(pc.fr(), 0));
                pc.a(new cn(4, false));
            } else if (cha instanceof s || cha instanceof z || cha instanceof ap.v) {
                npc = (t)cha;
                npc.b(new cz(npc.fr(), 0));
            }
        } else if (skillId == 1028) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new cn(6, false));
            } else if (cha instanceof t) {
                npc = (t)cha;
                npc.n(false);
            }
        } else if (skillId == 1018) {
            cha.ca(-30);
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new ci(pc));
            }
        } else if (skillId == 1020) {
            cha.cb(-30);
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new ci(pc));
            }
        } else if (skillId == 1022) {
            cha.bY(-30);
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new ci(pc));
            }
        } else if (skillId == 1000 || skillId == 1016 || skillId == 1026) {
            cha.cv(0);
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new dx(pc.fr(), 0, 0));
                pc.b(new dx(pc.fr(), 0, 0));
            }
        } else if (skillId == 1027) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new bs(pc.fr(), 0));
                pc.b(new bs(pc.fr(), 0));
            }
        } else if (skillId == 1017) {
            cha.cv(0);
        } else if (skillId == 1001) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new ea(pc.fr(), 0, 0));
                pc.b(new ea(pc.fr(), 0, 0));
            }
            cha.cu(0);
        } else if (skillId == 1003) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new ec(pc.fr(), 0));
            }
        } else if (skillId == 1004) {
            if (cha instanceof u) {
                pc = (u)cha;
                cha.cp(-2);
                pc.a(new cm(57, 0));
            }
        } else if (skillId == 1005) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new ds(288));
            }
        } else if (skillId >= 3000 && skillId <= 3056) {
            if (cha instanceof u) {
                pc = (u)cha;
                j.a(pc, skillId);
            }
        } else if (skillId == 4006) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.cm(-3);
                pc.ck(-3);
                pc.d(-2);
            }
        } else if (skillId == 4008) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-50);
                pc.c(-4);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4009) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-40);
                pc.d(-4);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4010) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.cm(-3);
                pc.ck(-3);
                pc.cn(-3);
                pc.cl(-3);
                pc.cp(-3);
            }
        } else if (skillId == 4011) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bL(2);
                pc.bZ(-50);
                pc.a(new cm(100, 82, 0));
                pc.a(new ds(3419));
            }
        } else if (skillId == 4012) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.c(-3);
                pc.d(-1);
                pc.bY(-50);
                pc.a(new cm(100, 85, 0));
                pc.a(new ds(3419));
            }
        } else if (skillId == 4077) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.cp(-1);
                pc.co(-1);
                pc.ca(-50);
                pc.a(new cm(100, 88, 0));
                pc.a(new ds(3419));
            }
        } else if (skillId == 4013) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-10);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4014) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-20);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4015) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-30);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4016) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-40);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4017) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-50);
                pc.c(-1);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4018) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-60);
                pc.c(-2);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4019) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-70);
                pc.c(-3);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4020) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-80);
                pc.c(-4);
                pc.cm(-1);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4021) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-100);
                pc.c(-5);
                pc.cm(-2);
                pc.ck(-2);
                pc.bN(-1);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4022) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-5);
                pc.bJ(-3);
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4023) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-10);
                pc.bJ(-6);
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4024) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-15);
                pc.bJ(-10);
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4025) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-20);
                pc.bJ(-15);
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4026) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-25);
                pc.bJ(-20);
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4027) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-30);
                pc.bJ(-20);
                pc.c(-1);
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4028) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-35);
                pc.bJ(-20);
                pc.c(-1);
                pc.d(-1);
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4029) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-40);
                pc.bJ(-25);
                pc.c(-2);
                pc.d(-1);
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4030) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-50);
                pc.bJ(-30);
                pc.c(-2);
                pc.d(-2);
                pc.cl(-2);
                pc.cn(-2);
                pc.bR(-1);
                pc.a(new ba(pc.ea(), pc.ew()));
                pc.a(new bv(pc.eb(), pc.ex()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4031) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-5);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4032) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-10);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4033) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-15);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4034) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-20);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4035) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-25);
                pc.d(-1);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4036) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-30);
                pc.d(-2);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4037) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-35);
                pc.d(-3);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4038) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-40);
                pc.d(-4);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4039) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-50);
                pc.d(-5);
                pc.bV(-1);
                pc.cp(-1);
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4040) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.co(-2);
            }
        } else if (skillId == 4041) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.co(-4);
            }
        } else if (skillId == 4042) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.co(-6);
            }
        } else if (skillId == 4043) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.co(-8);
            }
        } else if (skillId == 4044) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.co(-10);
                pc.bL(1);
            }
        } else if (skillId == 4045) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.co(-10);
                pc.bL(2);
            }
        } else if (skillId == 4046) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.co(-10);
                pc.bL(3);
            }
        } else if (skillId == 4047) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.co(-15);
                pc.bL(4);
                pc.F(-1);
            }
        } else if (skillId == 4048) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.co(-20);
                pc.bL(5);
                pc.bP(-1);
                pc.F(-3);
            }
        } else if (skillId == 4049) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.ce(-3);
                pc.cA(-1);
                pc.a(new cm(88, pc.fk()));
            }
        } else if (skillId == 4050) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.cg(-3);
            }
        } else if (skillId == 4051) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.cf(-3);
            }
        } else if (skillId == 4052) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.cd(-3);
                pc.ck(-2);
            }
        } else if (skillId == 4053) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.ci(-3);
                pc.cA(-1);
                pc.a(new cm(88, pc.fk()));
            }
        } else if (skillId == 4054) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.ch(-3);
                pc.cA(-1);
                pc.a(new cm(88, pc.fk()));
            }
        } else if (skillId == 4055) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.ck(2);
                pc.cA(-1);
                pc.a(new cm(88, pc.fk()));
            }
        } else if (skillId == 4056) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-100);
                pc.bJ(-50);
                pc.c(-3);
                pc.d(-3);
                pc.cb(-30);
                pc.ck(-1);
                pc.cm(-5);
                pc.C(-40);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4057) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-80);
                pc.bJ(-10);
                pc.bZ(-30);
                pc.bL(8);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4079) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-100);
                pc.bJ(-50);
                pc.c(-3);
                pc.d(-3);
                pc.bY(-30);
                pc.ck(-1);
                pc.cm(-5);
                pc.C(-40);
                pc.a(new ba(pc.ea(), pc.ew()));
                if (pc.q()) {
                    pc.aL().f(pc);
                }
                pc.a(new bv(pc.eb(), pc.ex()));
            }
        } else if (skillId == 4067) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.cm(-30);
                pc.ck(-30);
                pc.cn(-30);
                pc.cl(-30);
                pc.cp(-30);
                pc.a(new dc(110, skillId));
            }
        } else if (skillId == 4068) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bL(50);
                pc.a(new ci(pc));
                pc.a(new dc(110, skillId));
            }
        } else if (skillId == 4086 || skillId == 4087) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bR(-1);
                pc.cn(-5);
                pc.cl(-3);
            }
        } else if (skillId == 4088 || skillId == 4089) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bN(-1);
                pc.cm(-5);
                pc.ck(-3);
            }
        } else if (skillId == 4090 || skillId == 4091) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bV(-1);
                pc.bJ(-50);
                pc.d(-5);
            }
        } else if (skillId == 4092) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.F(-5);
                pc.a(new dc(110, skillId));
            }
        } else if (skillId == 5018) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.ck(-10);
                pc.cl(-10);
                pc.cm(-10);
                pc.cn(-10);
                pc.F(-5);
                pc.cp(-5);
                pc.a(new dc(110, skillId));
            }
        } else if (skillId == 5014) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new eb(221, 0, 1));
            }
        } else if (skillId == 5015) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new eb(221, 0, 2));
            }
        } else if (skillId == 4069) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-10);
                pc.bJ(-10);
                pc.c(-1);
                pc.d(-1);
                pc.bL(3);
                pc.a(new dc(4069, 0, 8, 0, 0, 0, 0, 0, 3));
            }
        } else if (skillId == 4085) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-100);
                pc.bJ(-100);
                pc.c(-5);
                pc.d(-5);
                pc.bN(-1);
                pc.bR(-1);
                pc.bP(-1);
                pc.bX(-1);
                pc.bV(-1);
                pc.bT(-1);
                pc.cA(-5);
                pc.ck(-5);
                pc.cl(-5);
                pc.cp(-3);
                pc.F(-5);
                pc.bL(5);
                pc.a(new cm(132, pc.u()));
                pc.a(new dc(4085, 0, 8, 0, 0, 0, 0, 0, 3));
            }
        } else if (skillId == 1032) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-15);
                pc.bJ(-15);
                pc.F(-1);
                pc.a(new dc(1032, 0, 8, 0, 0, 0, 0, 0, 3));
            }
        } else if (skillId == 1033) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-30);
                pc.bJ(-30);
                pc.F(-2);
                pc.a(new dc(1033, 0, 8, 0, 0, 0, 0, 0, 3));
            }
        } else if (skillId == 1034) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-45);
                pc.bJ(-45);
                pc.F(-3);
                pc.a(new dc(1034, 0, 8, 0, 0, 0, 0, 0, 3));
            }
        } else if (skillId == 1035) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-65);
                pc.bJ(-65);
                pc.F(-4);
                pc.a(new dc(1035, 0, 8, 0, 0, 0, 0, 0, 3));
            }
        } else if (skillId == 1036) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-100);
                pc.bJ(-75);
                pc.F(-5);
                pc.a(new dc(1036, 0, 8, 0, 0, 0, 0, 0, 3));
            }
        } else if (skillId == 1007) {
            cha.y(0);
            cha.a((az.c)null);
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new cm(161, 0, 0));
                pc.a(new ds(311));
            }
        } else if (skillId == 25009 || skillId == 25010 || skillId == 25011) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bl(skillId);
                pc.a(new cm(151, skillId - 25008));
            }
        } else if (skillId == 4076) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new cm(86, 173, 0, 0));
            }
        } else if (skillId == 25012) {
            if (cha instanceof u && (pc = (u)cha).aK() != null) {
                bh.a account = pc.aK().e();
                int point = pc.ep() == 0 ? 4 : 2;
                account.a(point);
                int total = account.p() + pc.cQ();
                if (account.h() == 0 && total >= 1000) {
                    account.d(1);
                    pc.a(new v(72, account.h()));
                } else if (account.h() == 1 && total >= 2000) {
                    account.d(2);
                    pc.a(new v(72, account.h()));
                } else if (account.h() == 2 && total >= 4000) {
                    account.d(3);
                    pc.a(new v(72, account.h()));
                } else if (account.h() == 3 && total >= 10000) {
                    account.d(4);
                    pc.a(new v(72, account.h()));
                } else if (account.h() == 4 && total >= 30000) {
                    account.d(5);
                    pc.a(new v(72, account.h()));
                }
                a.a().a(account);
                pc.a(new v(37, account.p()));
                pc.j(25012, 1800000);
            }
        } else if (skillId == 4072) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-50);
                pc.ck(-2);
                pc.c(-3);
                pc.bN(-1);
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4073) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-25);
                pc.cl(-2);
                pc.bJ(-25);
                pc.c(-1);
                pc.d(-1);
                pc.bR(-1);
                if (pc.q()) {
                    pc.aL().f(pc);
                }
            }
        } else if (skillId == 4074) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bJ(-50);
                pc.d(-3);
                pc.bV(-1);
                pc.cp(-2);
            }
        } else if (skillId == 4075) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-30);
                pc.bJ(-30);
                pc.bL(5);
                pc.co(-10);
                pc.F(-1);
                pc.a(new ci(pc));
            }
        } else if (skillId == 1006) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-100);
                pc.bJ(-100);
                pc.cm(-10);
                pc.ck(-5);
                pc.cn(-10);
                pc.cl(-5);
                pc.cp(-5);
                pc.bN(-1);
                pc.bR(-1);
                pc.bV(-1);
                pc.a(new ak(pc.fr(), 3));
                pc.b(new ak(pc.fr(), 3));
            }
        } else if (skillId == 1037) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-120);
                pc.bJ(-100);
                pc.F(-15);
                pc.co(-30);
                pc.cm(-10);
                pc.cn(-10);
                pc.U(-3);
                pc.bN(-3);
                pc.bR(-3);
                pc.bV(-3);
                pc.a(new ak(pc.fr(), 3));
                pc.b(new ak(pc.fr(), 3));
                pc.bz(67);
            }
        } else if (skillId == 1031) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-120);
                pc.bJ(-100);
                pc.cm(-10);
                pc.ck(-7);
                pc.cn(-10);
                pc.cl(-7);
                pc.cp(-5);
                pc.bN(-1);
                pc.bR(-1);
                pc.bV(-1);
                pc.a(new ak(pc.fr(), 3));
                pc.b(new ak(pc.fr(), 3));
            }
        } else if (skillId == 1038) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-100);
                pc.bJ(-100);
                pc.ck(-5);
                pc.cm(-10);
                pc.cl(-5);
                pc.cn(-10);
                pc.cp(-5);
                pc.bL(10);
                pc.co(-10);
                pc.a(new dc(110, skillId));
            }
        } else if (skillId == 5006) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bv(0);
                pc.a(new cm(204, pc));
            }
        } else if (skillId == 4080) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bH(-25);
                pc.bJ(-20);
            }
        } else if (skillId == 4081) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bL(2);
                pc.ck(-4);
                pc.F(-1);
            }
        } else if (skillId == 4082) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.bL(2);
                pc.cl(-4);
                pc.F(-1);
            }
        } else if (skillId == 4083 && cha instanceof u) {
            pc = (u)cha;
            pc.bL(2);
            pc.cp(-3);
            pc.F(-1);
        }
        if (cha instanceof u) {
            pc = (u)cha;
            pc.a(new ck(pc));
            pc.a(new dc(485, pc));
            pc.a(new do(pc));
        }
    }
}

