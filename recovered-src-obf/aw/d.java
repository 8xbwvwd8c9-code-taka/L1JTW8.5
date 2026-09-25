/*
 * Decompiled with CFR 0.152.
 */
package aw;

import ap.q;
import ap.u;
import be.ad;
import be.bs;
import be.cm;
import be.ds;
import be.dx;
import be.ea;
import be.ec;
import be.ee;
import bi.i;
import java.util.Random;

public class d {
    public static void a(u pc, int skillid, q item, boolean correspond) {
        if (!correspond) {
            pc.j().b(item, 1);
            pc.a(new ds(79));
            return;
        }
        int type = 1;
        if (skillid == 1016) {
            type = 3;
        } else if (skillid == 1017) {
            type = 4;
        }
        int gfxid = skillid == 1017 ? 7110 : 751;
        int time = item.a().V();
        if (item.F() == 0) {
            time = (int)((double)time * 1.5);
        } else if (item.F() == 2) {
            time = (int)((double)time * 0.5);
        }
        pc.j(skillid, time * 1000);
        pc.a(new dx(pc.fr(), type, time));
        pc.b(new dx(pc.fr(), type, time));
        pc.a(new ee(pc.fr(), gfxid));
        pc.b(new ee(pc.fr(), gfxid));
        pc.cv(type);
        pc.j().b(item, 1);
    }

    public static void a(u pc, q item, int time) {
        if (pc.bB(1038)) {
            pc.a(new ds(79));
            return;
        }
        if (item.F() == 0) {
            time = (int)((double)time * 1.5);
        } else if (item.F() == 2) {
            time = (int)((double)time * 0.5);
        }
        pc.j(1027, time * 1000);
        pc.a(new bs(pc.fr(), 8));
        pc.b(new bs(pc.fr(), 8));
        pc.a(new ee(pc.fr(), 7976));
        pc.b(new ee(pc.fr(), 7976));
        pc.a(new ds(1065));
        pc.j().b(item, 1);
    }

    public static void a(u pc, q item) {
        int gfxid = 189;
        if (item.a().aP() == 24) {
            gfxid = 194;
        } else if (item.a().aP() == 25) {
            gfxid = 197;
        }
        int heal = item.a().V();
        if (item.F() == 0) {
            heal = (int)((double)heal * 1.5);
        } else if (item.F() == 2) {
            heal = (int)((double)heal * 0.5);
        }
        heal = (int)((double)heal * (new Random().nextGaussian() / 5.0 + 1.0));
        heal += pc.dz() + bi.d.l(pc.bg(), pc.eA());
        if (pc.bB(173)) {
            heal /= 2;
        }
        pc.a(pc.ea() + heal);
        pc.a(new ee(pc.fr(), gfxid));
        pc.b(new ee(pc.fr(), gfxid));
        pc.a(new ds(77));
        pc.j().b(item, 1);
    }

    public static void b(u pc, q item) {
        int mp = item.a().V();
        if (item.F() == 0) {
            mp = (int)((double)mp * 1.5);
        } else if (item.F() == 2) {
            mp = (int)((double)mp * 0.5);
        }
        mp -= i.a(mp / 3);
        pc.a(new ee(pc.fr(), 190));
        pc.b(new ee(pc.fr(), 190));
        pc.a(new ds(338, "$1084"));
        pc.i_(pc.eb() + (mp += bi.d.n(pc.bk(), pc.eE())));
        pc.j().b(item, 1);
    }

    public static void c(u pc, q item) {
        int time = item.a().V();
        if (item.F() == 0) {
            time = (int)((double)time * 1.5);
        } else if (item.F() == 2) {
            time = (int)((double)time * 0.5);
        }
        pc.a(new ee(pc.fr(), 191));
        pc.b(new ee(pc.fr(), 191));
        if (pc.bU() > 0) {
            return;
        }
        pc.e(false);
        switch (pc.fc()) {
            case 0: 
            case 1: {
                pc.j(1001, time * 1000);
                pc.cu(1);
                pc.a(new ea(pc.fr(), 1, time));
                pc.b(new ea(pc.fr(), 1, time));
                break;
            }
            case 2: {
                int[] status;
                int[] nArray = status = new int[]{29, 76, 152};
                int n2 = status.length;
                int n3 = 0;
                while (n3 < n2) {
                    int id = nArray[n3];
                    if (pc.bB(id)) {
                        pc.bz(id);
                    }
                    ++n3;
                }
                break;
            }
        }
        pc.j().b(item, 1);
    }

    public static void d(u pc, q item) {
        int time = item.a().V();
        if (item.F() == 0) {
            time = (int)((double)time * 1.5);
        } else if (item.F() == 2) {
            time = (int)((double)time * 0.5);
        }
        pc.a(new ee(pc.fr(), 190));
        pc.b(new ee(pc.fr(), 190));
        pc.a(new ds(1007));
        pc.a(new cm(34, time));
        pc.j(1002, time * 1000);
        pc.j().b(item, 1);
    }

    public static void e(u pc, q item) {
        pc.j().b(item, 1);
        if (!pc.B() && !pc.E()) {
            pc.a(new ds(79));
            return;
        }
        int time = item.a().V();
        if (item.F() == 0) {
            time = (int)((double)time * 1.5);
        } else if (item.F() == 2) {
            time = (int)((double)time * 0.5);
        }
        if (!pc.bB(1004)) {
            pc.cp(2);
        }
        pc.a(new cm(57, time));
        pc.a(new ee(pc.fr(), 750));
        pc.b(new ee(pc.fr(), 750));
        pc.j(1004, time * 1000);
    }

    public static void f(u pc, q item) {
        int time = item.a().V();
        if (item.F() == 0) {
            time = (int)((double)time * 1.5);
        } else if (item.F() == 2) {
            time = (int)((double)time * 0.5);
        }
        if (pc.bB(1003)) {
            time += pc.bC(1003);
            time = Math.min(time, 7200);
        }
        pc.a(new ec(pc.fr(), time));
        pc.a(new ee(pc.fr(), 190));
        pc.b(new ee(pc.fr(), 190));
        pc.j(1003, time * 1000);
        pc.j().b(item, 1);
    }

    public static void g(u pc, q item) {
        if (pc.bB(1012)) {
            pc.a(new ad(2));
        } else {
            pc.a(new ad(1));
        }
        pc.j(20, 16000);
        pc.j().b(item, 1);
    }
}

