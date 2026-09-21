/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.q;
import ap.e;
import ap.t;
import ap.v;
import ap.z;
import aq.aa;
import aq.ao;
import aq.aq;
import aq.f;
import aq.i;
import aq.u;
import as.j;
import ax.b;
import ax.d;
import be.bx;
import be.cj;
import be.cm;
import be.cu;
import be.da;
import be.dc;
import be.ds;
import be.ee;
import be.eh;
import be.ek;
import java.util.HashSet;

public class am {
    private static final int a = 169;

    public static void a(ap.u pc, int x2, int y2, int mapid, int head, boolean effectable) {
        am.a(pc, x2, y2, mapid, head, effectable, true);
    }

    public static void a(ap.u pc, int x2, int y2, int mapId, int head, boolean effectable, boolean sendPBPacket) {
        if (pc.aO() != 0) {
            ao.b(pc);
        }
        pc.aI(x2);
        pc.aJ(y2);
        pc.aK(mapId);
        pc.aL(head);
        if (effectable) {
            if (sendPBPacket) {
                pc.a(new dc(145));
            } else {
                pc.a(new ek());
            }
            pc.a(new ee(pc.fr(), 169));
            pc.b(new ee(pc.fr(), 169));
            if (sendPBPacket) {
                am.a(pc);
            }
        } else {
            pc.a(new da(x2, y2, mapId));
        }
    }

    public static void a(ap.u pc) {
        i clan;
        if (pc.eX() || pc.aR()) {
            return;
        }
        if (pc.bB(230)) {
            pc.a(new ds(276));
            return;
        }
        int x2 = pc.cf();
        int y2 = pc.cg();
        int mapId = pc.ch();
        int head = pc.ci();
        b map = d.b().a(mapId);
        if (!map.b(x2, y2) && !pc.l()) {
            x2 = pc.fs();
            y2 = pc.ft();
            mapId = pc.fp();
        }
        pc.d(true);
        if (pc.fp() != mapId) {
            pc.y(false);
        }
        if ((clan = q.a().a(pc.aF())) != null && clan.o() == pc.fr()) {
            clan.i(0);
        }
        aq.a().a((aa)pc, mapId);
        pc.d(x2, y2, mapId);
        pc.ct(head);
        pc.a(new bx(pc.fp(), pc.fq().g()));
        pc.a(new cj(pc));
        pc.es();
        pc.i();
        pc.bA(32);
        pc.aQ(0);
        if (mapId != 1700 && mapId != 1703 && pc.j().m(21397)) {
            pc.a(new ds(123, "\\aG$22171"));
        }
        if (mapId != 6311 && !pc.l() && pc.j().m(413)) {
            pc.a(new ds(123, "\\aG$26512"));
        }
        HashSet<ap.u> subjects = new HashSet<ap.u>();
        subjects.add(pc);
        if (!pc.bN() && !pc.aA()) {
            int ny;
            int nx;
            u loc;
            for (ap.u visiblePc : aq.a().f(pc)) {
                visiblePc.d(pc);
                subjects.add(visiblePc);
            }
            if (pc.fq().n()) {
                for (t petNpc : pc.ek().values()) {
                    loc = pc.fu().a(3, false);
                    nx = loc.f();
                    ny = loc.g();
                    if (pc.fp() >= 5125 && pc.fp() <= 5134) {
                        nx = 32799 + bi.i.a(5) - 3;
                        ny = 32864 + bi.i.a(5) - 3;
                    }
                    am.a(petNpc, nx, ny, mapId, head);
                    if (petNpc instanceof z) {
                        pc.a(new eh((z)petNpc, pc));
                    } else if (petNpc instanceof v) {
                        pc.a(new cu((v)petNpc, pc));
                    }
                    for (ap.u visiblePc : aq.a().f(petNpc)) {
                        visiblePc.d(petNpc);
                        subjects.add(visiblePc);
                    }
                }
            }
            for (e doll : pc.el().values()) {
                loc = pc.fu().a(3, false);
                nx = loc.f();
                ny = loc.g();
                am.a(doll, nx, ny, mapId, head);
                pc.a(new be.am(doll));
                for (ap.u visiblePc : aq.a().f(doll)) {
                    visiblePc.d(doll);
                    subjects.add(visiblePc);
                }
            }
        }
        for (ap.u updatePc : subjects) {
            updatePc.h();
        }
        pc.d(false);
        if (pc.bB(167)) {
            int time = pc.bC(167);
            pc.a(new cm(44, pc.fr(), time));
            pc.b(new cm(44, pc.fr(), time));
        }
        j.a().b(pc);
    }

    public static void a(f cha, f target, int distance) {
        int locX = target.fs();
        int locY = target.ft();
        int heading = target.fb();
        b map = target.fq();
        int mapId = target.fp();
        int[][] nArrayArray = new int[8][];
        int[] nArray = new int[2];
        nArray[1] = -1;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{1, -1};
        int[] nArray2 = new int[2];
        nArray2[0] = 1;
        nArrayArray[2] = nArray2;
        nArrayArray[3] = new int[]{1, 1};
        int[] nArray3 = new int[2];
        nArray3[1] = 1;
        nArrayArray[4] = nArray3;
        nArrayArray[5] = new int[]{-1, 1};
        int[] nArray4 = new int[2];
        nArray4[0] = -1;
        nArrayArray[6] = nArray4;
        nArrayArray[7] = new int[]{-1, -1};
        int[][] DIR_TABLE = nArrayArray;
        if (map.c(locX += DIR_TABLE[heading][0] * bi.i.a(distance), locY += DIR_TABLE[heading][1] * bi.i.a(distance))) {
            if (cha instanceof ap.u) {
                am.a((ap.u)cha, locX, locY, mapId, cha.fb(), true);
            } else if (cha instanceof t) {
                ((t)cha).a(locX, locY, cha.fb());
            }
        }
    }

    public static void a(ap.u pc, int range) {
        u newLocation = pc.fu().a(range, true);
        int newX = newLocation.f();
        int newY = newLocation.g();
        int mapId = newLocation.b();
        am.a(pc, newX, newY, mapId, 5, true, false);
    }

    private static void a(t npc, int x2, int y2, int map, int head) {
        aq.a().a((aa)npc, map);
        d.b().a(npc.fp()).a(npc.fs(), npc.ft(), true);
        npc.cG(x2);
        npc.cH(y2);
        npc.cE(map);
        npc.ct(head);
        d.b().a(npc.fp()).a(npc.fs(), npc.ft(), false);
    }
}

