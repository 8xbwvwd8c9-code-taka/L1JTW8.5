/*
 * Decompiled with CFR 0.152.
 */
package as;

import ai.d;
import ao.au;
import ao.bi;
import ap.ab;
import ap.s;
import ap.t;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import ax.b;
import be.ak;
import be.cm;
import be.ee;
import be.ef;
import be.eu;
import be.o;
import bi.h;
import bi.i;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class g {
    private static final int c = 5;
    private static final int d = 6;
    private static final int e = 10;
    private static final int f = 11;
    private static final int g = 12;
    private static final int h = 13;
    private static final int i = 14;
    private static final int j = 15;
    private static final int k = 16;
    private static final int l = 17;
    private static final int m = 18;
    private static final int n = 19;
    private static final int o = 20;
    private static final int p = 21;
    private static final int q = 22;
    private static final int r = 23;
    private static final int s = 24;
    private static final int t = 25;
    private static final int u = 100;
    private static final int v = 101;
    private static final int w = 102;
    private static final int x = 103;
    private static final int y = 104;
    private static final int z = 105;
    private static final int A = 106;
    public boolean a = true;
    private final int B;
    public int b;
    private int C;
    private int D;
    private int E;
    private aq.u F;
    private aq.u G;
    private t H;
    private t I;
    private t J;
    private t K;
    private t L;
    private final int[] M = new int[12];
    private final int[] N = new int[12];
    private final ArrayList<t> O = new ArrayList();
    private final CopyOnWriteArrayList<u> P = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<aa> Q = new CopyOnWriteArrayList();
    private ScheduledExecutorService R;
    private static g S;
    private final int[] T = new int[]{10638, 10639, 10640, 10641, 10642, 10643, 10644, 10645, 10646, 10647, 10648, 10649, 10666, 10667, 10668, 10669, 10670, 10671, 10672, 10673, 10674, 10675, 10676, 10677, 10678, 10679, 10680, 10681, 10682, 10683, 10684, 10685, 10686};
    private final int[] U = new int[]{91378, 91373, 91516, 91514, 91513, 91515, 91517, 91521, 91518, 91380, 91519, 91520, 91552, 91392, 91522, 91523, 91524, 91525, 91526, 91527};
    private final int[] V = new int[4];

    private void b() {
        this.a = false;
        for (aa obj : aq.a().b(this.B).values()) {
            if (!(obj instanceof u)) continue;
            u pc = (u)obj;
            this.P.add(pc);
        }
        for (u pc : this.P) {
            if (!pc.am()) continue;
            pc.a(new o(10, pc.fr()));
            pc.b(new o(10, pc.fr()));
        }
        this.O.addAll(this.a(new aq.u(32803, 32809, this.B), 91507, 1));
        this.O.addAll(this.a(new aq.u(32792, 32809, this.B), 91508, 1));
        this.I = this.a(new aq.u(32799, 32806, this.B), 91506, 1).get(0);
        this.I.cq(32);
        this.F = new aq.u(32799, 32809, this.B);
        this.G = new aq.u(32799, 32803, this.B);
        this.R = Executors.newScheduledThreadPool(2);
    }

    private void c() {
        this.i();
        for (aa obj : aq.a().b(this.B).values()) {
            aq.a().d(obj);
        }
        this.a = true;
        this.R.shutdownNow();
    }

    public static g a() {
        if (S == null) {
            S = new g(9101);
        }
        return S;
    }

    private g(int mapId) {
        this.B = mapId;
    }

    public void a(int stage) {
        this.b();
        new a(stage, 0).a();
        new a(6, 0).a();
    }

    private void b(int round) throws InterruptedException {
        int d_count = this.P.size() <= 1 ? 1 : this.P.size() - 1;
        ArrayList<t> list_D = this.a(this.G, 91510, d_count);
        aq.u loc = this.b(this.G, 10);
        ArrayList<t> list_A = this.a(loc, 91511, 1);
        int defense_time = 0;
        int attack_time = 0;
        int count = -1;
        boolean onAttackPoint = false;
        boolean onDefensePoint = false;
        while (count++ < 10) {
            Thread.sleep(1000L);
            if (!onAttackPoint) {
                for (t pointA : list_A) {
                    onAttackPoint = this.a(pointA.fu(), 2);
                }
            } else if (this.b == 66) {
                this.b = 0;
                for (t cannon : this.O) {
                    cannon.b(new ak(cannon.fr(), 2));
                }
                if (attack_time == 0) {
                    attack_time = count;
                }
            }
            if (!onDefensePoint) {
                for (t pointD : list_D) {
                    onDefensePoint = this.a(pointD.fu(), 2);
                }
            } else if (this.b == 69) {
                this.b = 0;
                for (u pc : this.P) {
                    if (!pc.am()) continue;
                    pc.a(new ee(pc.fr(), 10165));
                    pc.b(new ee(pc.fr(), 10165));
                }
                if (defense_time == 0) {
                    defense_time = count;
                }
            }
            if (attack_time * defense_time > 0) break;
        }
        list_A.addAll(list_D);
        for (t point : list_A) {
            point.aa_();
        }
        if (attack_time != 0 && bi.i.a(4) > 0) {
            this.a("Critical HIT!", this.M[round]);
            int n2 = round;
            this.M[n2] = this.M[n2] + 1;
        }
        if (defense_time == 0) {
            int i2 = 0;
            while (i2 < 5) {
                int x2 = 32790 + bi.i.a(25);
                int y2 = bi.i.a(2) == 0 ? 32818 : 32788;
                this.a(new be.aq(x2, y2, 8233));
                this.a(new cm(83, 2));
                Thread.sleep(1000L);
                ++i2;
            }
        }
    }

    private boolean a(aq.u point, int radius) {
        for (u pc : this.P) {
            if (radius == -1 && pc.fu().e(point)) {
                am.a(pc, 32799, 32809, this.B, 1, false);
                return true;
            }
            if (pc.fu().d(point) >= radius) continue;
            return true;
        }
        return false;
    }

    private void d() throws InterruptedException {
        this.a("$9603", 0);
        this.a(new cm(83, 2));
        this.a(new ef(82));
        int[] mobids = new int[7];
        int i2 = 0;
        while (i2 < 7) {
            mobids[i2] = this.U[bi.i.a(5)];
            ++i2;
        }
        aq.u loc = new aq.u(32797, 32803, this.B);
        ArrayList<t> list = this.a(loc, mobids);
        int max_time = 35;
        int use_time = this.a(list, 35000);
        this.C = (35 - use_time) / 5;
        Thread.sleep(3000L);
    }

    private void e() throws InterruptedException {
        int rnd;
        int i2;
        this.a("$9548", 0);
        int n2 = i2 = bi.i.a(4);
        this.V[n2] = this.V[n2] + 1;
        Thread.sleep(5000L);
        ArrayList<t> gfxNpcList = new ArrayList();
        if (i2 == 0) {
            this.a(new be.aq(new aq.u(32803, 32788, this.B), 8142));
            this.a("$9541", 0);
        } else if (i2 == 1) {
            rnd = bi.i.a(4) + 6;
            gfxNpcList = this.a(this.G, 91540, rnd);
            this.a(gfxNpcList, -1, 40);
            this.a(gfxNpcList, 0);
            this.a("$9542", 0);
        } else if (i2 == 2) {
            rnd = bi.i.a(4) + 6;
            gfxNpcList = this.a(this.G, 91539, rnd);
            this.a(gfxNpcList, -1, 40);
            this.a(gfxNpcList, 0);
            this.a("$9543", 0);
        } else if (i2 == 3) {
            this.a(new be.aq(new aq.u(32800, 32794, this.B), 8241));
            this.a("$9544", 0);
        }
        Thread.sleep(5000L);
        if (this.V[i2] == 3) {
            if (i2 == 1) {
                ArrayList<t> list = this.a(this.G, 91540, 3);
                this.a("$9549", 1);
                this.a(list, 15000);
            } else if (i2 == 2) {
                ArrayList<t> list = this.a(this.G, 91539, 3);
                this.a("$9549", 1);
                this.a(list, 15000);
            } else if (i2 == 3) {
                ArrayList<t> list = this.a(new aq.u(32800, 32794, this.B), 91538, 1);
                list.addAll(this.a(new aq.u(32800, 32795, this.B), 91548, 1));
                list.addAll(this.a(new aq.u(32800, 32796, this.B), 91549, 1));
                this.a("$9558", 1);
                Thread.sleep(3000L);
                this.a("$10720", 1);
                int time = this.a(list, 60000);
                if (time < 60) {
                    this.a(new aq.u(32798, 32807, this.B), 91497, 1);
                }
            }
        }
        Thread.sleep(3000L);
        this.a("$" + this.T[Math.min(this.D / 100, 33)], 1);
        Thread.sleep(3000L);
    }

    private void c(int round) throws InterruptedException {
        aq.u loc = new aq.u(32794, 32825, this.B);
        int typeA = 0;
        int typeB = 0;
        int typeC = 0;
        int i2 = 0;
        while (i2 < 12) {
            if (i2 < 4) {
                typeA += this.M[i2];
            } else if (i2 >= 4 && 7 >= i2) {
                typeB += this.M[i2];
            } else if (i2 >= 8 && 10 >= i2) {
                typeC += this.M[i2];
            }
            ++i2;
        }
        int shipid = 0;
        if (round >= 0 && round <= 3) {
            shipid = (new int[]{91503, 91504, 91505, 91498})[(typeA - 1) / 3];
        } else if (round >= 4 && round <= 7) {
            shipid = (new int[]{91503, 91504, 91505, 91498})[(typeB - 1) / 3];
        } else if (round >= 8 && round <= 11) {
            shipid = (new int[]{91503, 91504, 91505, 91499})[(typeC - 1) / 3];
        }
        this.H = this.a(loc, shipid, 1).get(0);
        this.b(round);
        this.b(round);
        this.b(round);
        Thread.sleep(5000L);
        if (this.H != null) {
            this.a(this.H, 0, 10);
            this.f();
            if (shipid == 91498) {
                this.a("$9553", 0);
                Thread.sleep(2000L);
                this.a("$9559", 1);
                this.a(91509, this.F, new aq.u(32741, 32855, this.B));
                this.a(91509, new aq.u(32741, 32861, this.B), this.F);
            } else if (shipid == 91499) {
                this.a("$9552", 0);
                Thread.sleep(2000L);
                this.a("$9559", 1);
                this.a(91509, this.F, new aq.u(32741, 32855, this.B));
                this.a(91509, new aq.u(32741, 32861, this.B), this.F);
            } else {
                this.a("$9555", 0);
                Thread.sleep(2000L);
                if (round == 3 && typeA >= 12) {
                    int npcId = 0;
                    if (this.D <= 300) {
                        npcId = 91537;
                    } else if (this.D <= 600) {
                        npcId = 91533;
                    } else if (this.D > 600) {
                        npcId = 91535;
                    }
                    this.a(new aq.u(32671, 32802, this.B), npcId, 1);
                    this.a(91509, this.F, new aq.u(32677, 32795, this.B));
                    this.a(91509, new aq.u(32677, 32800, this.B), this.F);
                } else if (round == 7 && typeB >= 12) {
                    int npcId = 0;
                    if (this.D <= 300) {
                        npcId = 91537;
                    } else if (this.D <= 600) {
                        npcId = 91533;
                    } else if (this.D > 600) {
                        npcId = 91535;
                    }
                    this.a(new aq.u(32671, 32866, this.B), npcId, 1);
                    this.a(91509, this.F, new aq.u(32677, 32859, this.B));
                    this.a(91509, new aq.u(32677, 32864, this.B), this.F);
                }
            }
        }
        Thread.sleep(6000L);
    }

    private void d(int round) throws InterruptedException {
        int subRound = 0;
        while (subRound < 3) {
            ArrayList<t> list = this.f(round);
            if (subRound == 0 && (round == 3 || round == 7) && this.Q.isEmpty()) {
                int npcId = 0;
                if (this.D <= 300) {
                    npcId = 91537;
                } else if (this.D <= 600) {
                    npcId = 91533;
                } else if (this.D > 600) {
                    npcId = 91535;
                }
                list.addAll(this.a(this.G, npcId, 1));
            }
            int max_time = 45;
            int time = this.a(list, 45000);
            int n2 = this.N[round] = time == 45 ? -1 : 25 / time;
            if (time == 45) {
                this.a("$" + (9563 + subRound), 1);
            } else {
                this.a("$9560", 2);
            }
            Thread.sleep(3000L);
            ++subRound;
        }
        this.a("$9608", 2);
        this.a(new cm(83, 4));
        ArrayList<t> list = this.a(new aq.u(32735, 32802, this.B), 91512, 3);
        Collections.shuffle(this.P);
        Iterator<aa> iterator = this.P.iterator();
        if (iterator.hasNext()) {
            u pc = iterator.next();
            aq.u temploc = new aq.u(pc.fs(), pc.ft(), pc.fp());
            Thread.sleep(3000L);
            am.a(pc, 32737, 32800, this.B, 1, true);
            this.a(list, 6000);
            this.a("$9606", 1);
            Thread.sleep(3000L);
            am.a(pc, temploc.f(), temploc.g(), temploc.b(), 4, true);
        }
        for (aa obj : this.Q) {
            if (!(obj instanceof t) || obj.fu().f(this.F)) continue;
            this.a(obj.fu(), -1);
        }
        this.i();
        if (this.H != null) {
            this.a(this.H, 4, 10);
            this.H.aa_();
        }
    }

    private void e(int maxTimeMill) throws InterruptedException {
        ArrayList<t> list = this.f(11);
        int[] boss = this.D > 3000 ? new int[]{91550, 91530, 91531, 91532} : (this.D < 1200 ? new int[]{91528} : new int[]{91529, 91530, 91531, 91532});
        list.addAll(this.a(this.G, boss));
        int count = -1;
        while (count++ < maxTimeMill / 1000 && this.E < 10) {
            for (t mob : list) {
                if (!mob.eX()) break;
            }
            if (count % 30 == 0) {
                this.g();
            }
            Thread.sleep(1000L);
        }
        ArrayList<t> mimi = this.a(this.G, 91553, this.P.size() * 2);
        this.a(mimi, 15000);
    }

    private void f() {
        int[] ship_status = new int[]{32, 32, 33, 33, 34, 34, 35, 35, 36, 36};
        this.I.cq(ship_status[this.E]);
        this.I.b(new ak(this.I.fr(), this.I.eY()));
        this.a(new cm(83, 2));
    }

    private void g() throws InterruptedException {
        ++this.E;
        this.a(new cm(83, 2));
        t fire = this.a(this.G, 91544, 3).get(0);
        fire.b(new ee(fire.fr(), 762));
        if (this.E == 10) {
            this.a("$9562", 1);
            Thread.sleep(10000L);
            this.h();
        } else if (this.E == 9) {
            this.a("$9587", 1);
        } else if (this.E < 9) {
            this.a("$9561", 1);
        }
    }

    private void a(t npc, int dir, int range) throws InterruptedException {
        int i2 = 0;
        while (i2 < range) {
            npc.g(dir);
            Thread.sleep(1000L);
            ++i2;
        }
    }

    private void a(ArrayList<t> list, int dir, int range) throws InterruptedException {
        int i2 = 0;
        while (i2 < range) {
            for (t npc : list) {
                if (i2 == 0 && npc.fb() != dir) {
                    npc.ct(bi.i.a(8));
                }
                npc.g(npc.fb());
            }
            Thread.sleep(100L);
            ++i2;
        }
    }

    private ab a(int npcid, aq.u spawnLoc, aq.u teleLoc) {
        int id = ai.d.a().c();
        bd.h trap = new bd.h(id, 0, teleLoc);
        ab tp = new ab(trap.a(), trap, spawnLoc, new h(), 2);
        bi.a().a(tp);
        this.Q.addAll(this.a(spawnLoc, npcid, 1));
        this.Q.add(tp);
        return tp;
    }

    private ArrayList<t> f(int round) {
        int i2 = 0;
        while (i2 < round) {
            if (this.C > this.U.length * 2 / 3) break;
            this.C += this.N[i2];
            ++i2;
        }
        this.C = Math.max(0, this.C);
        this.C = Math.min(this.C, this.U.length - 1);
        int[] mobids = new int[7];
        int i3 = 0;
        while (i3 < 7) {
            mobids[i3] = this.U[this.C + bi.i.a(this.U.length - this.C)];
            ++i3;
        }
        return this.a(this.G, mobids);
    }

    private ArrayList<t> a(aq.u loc, int mobid, int amount) {
        int[] mobids = new int[amount];
        int i2 = 0;
        while (i2 < amount) {
            mobids[i2] = mobid;
            ++i2;
        }
        return this.a(loc, mobids);
    }

    private ArrayList<t> a(aq.u loc, int[] mobsID) {
        ArrayList<t> mob_list = new ArrayList<t>();
        int[] nArray = mobsID;
        int n2 = mobsID.length;
        int n3 = 0;
        while (n3 < n2) {
            t npc;
            int npcid = nArray[n3];
            if (mobsID.length > 1 && npcid != 91512) {
                loc = this.b(loc, 10);
            }
            if ((npc = au.a().b(npcid)) instanceof s) {
                this.a(new be.aq(loc.f(), loc.g(), 11509));
            }
            npc.cF(ai.d.a().c());
            npc.cE((short)loc.b());
            npc.ct(5);
            npc.cG(loc.f());
            npc.cH(loc.g());
            aq.a().a(npc);
            aq.a().c(npc);
            mob_list.add(npc);
            ++n3;
        }
        return mob_list;
    }

    private int a(ArrayList<t> list, int maxTimeMill) throws InterruptedException {
        int count = -1;
        while (count++ < maxTimeMill / 1000) {
            boolean isAllDeath = false;
            for (t mob : list) {
                if (!mob.eX()) {
                    isAllDeath = false;
                    break;
                }
                isAllDeath = mob.eX();
            }
            if (isAllDeath) {
                this.a(list);
                return count;
            }
            Thread.sleep(1000L);
        }
        this.a(list);
        if (maxTimeMill != 0) {
            this.g();
        }
        return maxTimeMill / 1000;
    }

    private void a(ArrayList<t> list) {
        for (t npc : list) {
            if (npc.eX()) {
                this.D += npc.ev() / 5;
            }
            npc.aa_();
        }
        for (u pc : this.P) {
            pc.a(new cm(84, this.D > 0 ? 4 : 3, "" + this.D));
        }
    }

    private void a(String s2, int cases) {
        String[] colors = new String[]{"", "\\f=", "\\f3"};
        for (u pc : this.P) {
            pc.a(new cm(84, 2, String.valueOf(colors[cases]) + s2));
        }
    }

    private void a(eu serverbasepacket) {
        for (u pc : this.P) {
            pc.a(serverbasepacket);
        }
    }

    private void h() {
        for (u pc : this.P) {
            if (pc.am()) {
                pc.a(new o(10, 0));
                pc.b(new o(10, 0));
            }
            int locx = 32587 + bi.i.a(4);
            int locy = 32941 + bi.i.a(4);
            am.a(pc, locx, locy, 0, 5, true);
        }
    }

    private void i() {
        for (aa obj : this.Q) {
            if (obj instanceof ab) {
                bi.a().b((ab)obj);
            } else if (obj instanceof t) {
                ((t)obj).aa_();
            }
            this.Q.remove(obj);
        }
    }

    private aq.u b(aq.u baseLocation, int max) {
        aq.u newLocation = new aq.u();
        int newX = 0;
        int newY = 0;
        int baseX = baseLocation.f();
        int baseY = baseLocation.g();
        b map = baseLocation.a();
        newLocation.a(map);
        int locX1 = baseX - max;
        int locX2 = baseX + max;
        int locY1 = baseY - max;
        int locY2 = baseY + max;
        locX1 = Math.max(32789, locX1);
        locX2 = Math.min(locX2, 32808);
        locY1 = Math.max(32796, locY1);
        locY2 = Math.min(locY2, 32809);
        int diffX = locX2 - locX1;
        int diffY = locY2 - locY1;
        int trial = 0;
        int amax = (int)Math.pow(1 + max * 2, 2.0);
        int trialLimit = 40 * amax / amax;
        do {
            if (trial >= trialLimit) {
                newLocation.a(baseX, baseY);
                break;
            }
            ++trial;
            newX = locX1 + bi.i.a(diffX + 1);
            newY = locY1 + bi.i.a(diffY + 1);
            newLocation.a(newX, newY);
        } while (!map.b(newX, newY) || !map.c(newX, newY));
        return newLocation;
    }

    private class a
    implements Runnable {
        int a;
        int b;

        private a(int order, int time) {
            this.a = time;
            this.b = order;
        }

        private void a() {
            g.this.R.schedule(this, (long)this.a, TimeUnit.MILLISECONDS);
        }

        @Override
        public void run() {
            switch (this.b) {
                case 5: {
                    try {
                        g.this.a("$9540", 0);
                        int round = 0;
                        while (round < 12) {
                            Thread.sleep(3000L);
                            if (round == 0) {
                                g.this.d();
                            }
                            g.this.e();
                            if (round == 11) {
                                g.this.a("$9556", 0);
                            } else if ((round + 1) % 4 == 0) {
                                g.this.a("$9554", 0);
                            } else {
                                g.this.a("$9550", 0);
                            }
                            Thread.sleep(3000L);
                            if (round != 11) {
                                g.this.a("$9551", 0);
                            }
                            g.this.c(round);
                            g.this.a("$" + (9609 + round), 1);
                            Thread.sleep(3000L);
                            g.this.a(new cm(83, 2));
                            g.this.a(new ef(82));
                            Thread.sleep(3000L);
                            if (round != 11) {
                                g.this.d(round);
                            } else {
                                g.this.e(300000);
                                new a(100, 0).a();
                            }
                            ++round;
                        }
                    }
                    catch (InterruptedException round) {}
                    break;
                }
                case 10: {
                    new a(11, 5000).a();
                    break;
                }
                case 11: {
                    g.this.a("$9529", 0);
                    new a(12, 5000).a();
                    break;
                }
                case 12: {
                    g.this.a("$9530", 0);
                    new a(13, 5000).a();
                    break;
                }
                case 13: {
                    if (g.this.b == 68) {
                        g.this.b = 0;
                        new a(5, 5000).a();
                        break;
                    }
                    g.this.a("$9531", 0);
                    new a(14, 5000).a();
                    break;
                }
                case 14: {
                    g.this.a("$9532", 0);
                    new a(15, 5000).a();
                    break;
                }
                case 15: {
                    g.this.a("$9533", 0);
                    new a(16, 5000).a();
                    break;
                }
                case 16: {
                    g.this.a("$9534", 0);
                    aq.u loc = g.this.b(g.this.G, 10);
                    g.this.J = (t)g.this.a(loc, 91511, 1).get(0);
                    new a(17, 5000).a();
                    break;
                }
                case 17: {
                    g.this.a("$9535", 0);
                    new a(18, 5000).a();
                    break;
                }
                case 18: {
                    g.this.a("$9536", 0);
                    new a(19, 5000).a();
                    break;
                }
                case 19: {
                    if (g.this.b == 66 && g.this.a(g.this.J.fu(), 2)) {
                        g.this.b = 0;
                        g.this.J.aa_();
                        new a(21, 5000).a();
                        break;
                    }
                    g.this.a("$9545", 0);
                    new a(20, 5000).a();
                    break;
                }
                case 20: {
                    if (g.this.b == 66 && g.this.a(g.this.J.fu(), 2)) {
                        g.this.b = 0;
                    } else {
                        g.this.a("$9546", 0);
                    }
                    g.this.J.aa_();
                    new a(21, 5000).a();
                    break;
                }
                case 21: {
                    g.this.a("$9537", 0);
                    new a(22, 8000).a();
                    break;
                }
                case 22: {
                    g.this.a("$9538", 0);
                    g.this.K = (t)g.this.a(g.this.G, 91510, 2).get(0);
                    g.this.L = (t)g.this.a(g.this.G, 91510, 2).get(1);
                    new a(23, 5000).a();
                    break;
                }
                case 23: {
                    g.this.a("$9539", 0);
                    new a(24, 5000).a();
                    break;
                }
                case 24: {
                    if (g.this.b == 69 && g.this.a(g.this.K.fu(), 2) && g.this.a(g.this.L.fu(), 2)) {
                        g.this.b = 0;
                        g.this.K.aa_();
                        g.this.L.aa_();
                        new a(5, 5000).a();
                        break;
                    }
                    g.this.a("$9545", 0);
                    new a(25, 5000).a();
                    break;
                }
                case 25: {
                    if (g.this.b == 69 && g.this.a(g.this.K.fu(), 2) && g.this.a(g.this.L.fu(), 2)) {
                        g.this.b = 0;
                    } else {
                        g.this.a("$9546", 0);
                    }
                    g.this.K.aa_();
                    g.this.L.aa_();
                    new a(5, 8000).a();
                    break;
                }
                case 100: {
                    g.this.a("$9579", 0);
                    new a(101, 5000).a();
                    break;
                }
                case 101: {
                    g.this.a("$9580", 0);
                    new a(102, 5000).a();
                    break;
                }
                case 102: {
                    g.this.a("$9581", 0);
                    new a(103, 5000).a();
                    break;
                }
                case 103: {
                    g.this.a("$9582", 0);
                    new a(104, 5000).a();
                    break;
                }
                case 104: {
                    g.this.a("$9583", 0);
                    new a(105, 5000).a();
                    break;
                }
                case 105: {
                    g.this.a("$9584", 0);
                    new a(106, 5000).a();
                    break;
                }
                case 106: {
                    g.this.h();
                    break;
                }
                case 6: {
                    try {
                        try {
                            while (!g.this.P.isEmpty()) {
                                for (u pc : g.this.P) {
                                    if (pc.bE() != 0 && pc.fp() == g.this.B) continue;
                                    g.this.P.remove(pc);
                                }
                                Thread.sleep(3000L);
                            }
                        }
                        catch (Exception exception) {
                            System.out.println("[\u526f\u672c\u7d50\u675f]:\u6d77\u6230\u526f\u672c");
                            g.this.c();
                            break;
                        }
                    }
                    finally {
                        System.out.println("[\u526f\u672c\u7d50\u675f]:\u6d77\u6230\u526f\u672c");
                        g.this.c();
                    }
                }
            }
        }
    }
}

