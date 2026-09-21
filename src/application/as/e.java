/*
 * Decompiled with CFR 0.152.
 */
package as;

import ao.bg;
import ao.x;
import ap.f;
import ap.s;
import ap.t;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import be.be;
import be.cg;
import be.cm;
import be.ee;
import bi.i;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class e {
    private aq.u c;
    public int a;
    public boolean b = true;
    private boolean d;
    private final int e;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private u i;
    private t j;
    private t k;
    private t l;
    private ScheduledExecutorService m;
    private final CopyOnWriteArrayList<u> n = new CopyOnWriteArrayList();
    private final ArrayList<f> o = new ArrayList();
    private final ArrayList<f> p = new ArrayList();
    private static e q;
    private int r = 0;
    private static final int s = 69;
    private static final int t = 66;
    private static final int u = 1;
    private static final int v = 2;
    private static final int w = 3;
    private static final int x = 4;
    private static final int y = 5;
    private static final int z = 6;
    private static final int A = 7;
    private static final int B = 10;
    private static final int C = 11;
    private static final int D = 12;
    private static final int E = 13;
    private static final int F = 14;
    private static final int G = 15;
    private static final int H = 16;
    private static final int I = 17;
    private static final int J = 18;
    private static final int K = 19;
    private static final int L = 20;
    private static final int M = 21;
    private static final int N = 22;
    private static final int O = 23;
    private static final int P = 24;
    private static final int Q = 25;
    private static final int R = 26;
    private static final int S = 101;
    private static final int T = 102;
    private static final int U = 103;
    private static final int V = 104;
    private static final int W = 105;
    private static final int X = 106;
    private static final int Y = 201;
    private static final int Z = 202;
    private static final int aa = 203;
    private static final int ab = 204;
    private static final int ac = 205;
    private static final int ad = 206;
    private static final int ae = 301;
    private static final int af = 302;
    private static final int ag = 303;
    private static final int ah = 304;
    private static final int ai = 999;
    private final bh.f aj = bh.f.a(7536);
    private final int[] ak = new int[]{91374, 91375, 91376, 91377, 91378, 91379, 91380, 91381, 91382, 91383, 91384, 91385};
    private final int[] al = new int[]{91384, 91385, 91386, 91387, 91388, 91389, 91392};
    private final int[] am = new int[]{91370, 91371, 91373, 91554, 91555, 91370, 91371, 91373, 91554, 91555};
    private final int[] an = new int[]{91440, 91441, 91442, 91440, 91441, 91442};
    private final int[][] ao = new int[][]{{32666, 32817}, {32668, 32817}, {32668, 32819}, {32666, 32819}};
    private final int[][] ap = new int[][]{{32712, 32793}, {32703, 32791}, {32710, 32803}, {32703, 32800}};
    private final int[][] aq = new int[][]{{32807, 32839}, {32809, 32837}, {32807, 32837}, {32809, 32839}};
    private final int[][] ar = new int[][]{{32806, 32863}, {32808, 32864}, {32800, 32864}, {32799, 32866}, {32806, 32872}, {32798, 32872}, {32800, 32873}};
    private final int[][] as = new int[][]{{32763, 32800}, {32758, 32801}, {32663, 32876}, {32667, 32867}, {32722, 32866}};
    private final int[][] at = new int[][]{{32785, 32871}, {32725, 32789}, {32745, 32813}, {32686, 32790}, {32664, 32813}, {32669, 32850}, {32683, 32813}, {32715, 32810}, {32745, 32789}, {32784, 32795}, {32805, 32797}, {32792, 32828}};

    public static e a() {
        if (q == null) {
            q = new e(9000);
        }
        return q;
    }

    private e(int mapId) {
        this.e = (short)mapId;
    }

    public void a(int stage) {
        this.c();
        new a(stage, 0).a();
        new a(999, 0).a();
    }

    private void a(ArrayList<t> list, int dir, int range) throws InterruptedException {
        int i2 = 0;
        while (i2 < range) {
            for (t npc : list) {
                if (i2 == 0 && npc.fb() != dir) {
                    npc.ct(dir);
                }
                npc.g(npc.fb());
            }
            Thread.sleep(800L);
            ++i2;
        }
    }

    private void b() {
        for (f door : this.p) {
            ao.t.b().a(door.fu());
        }
        for (f wall : this.o) {
            ao.t.b().a(wall.fu());
        }
        for (aa obj : aq.aq.a().b(this.e).values()) {
            aq.aq.a().d(obj);
        }
        this.b = true;
        this.m.shutdownNow();
    }

    private void c() {
        int[] loc;
        this.b = false;
        this.c = new aq.u(32707, 32846, this.e);
        this.m = Executors.newScheduledThreadPool(10);
        for (aa obj : aq.aq.a().b(this.e).values()) {
            if (!(obj instanceof u)) continue;
            u pc = (u)obj;
            if (pc.am() || pc.l()) {
                this.i = pc;
            }
            this.n.add(pc);
        }
        this.k = this.a(new aq.u(32742, 32930, this.e), 91397, 1).get(0);
        this.j = this.a(new aq.u(32733, 32724, this.e), 91430, 1).get(0);
        Object object = this.at;
        int n2 = this.at.length;
        int n3 = 0;
        while (n3 < n2) {
            loc = object[n3];
            this.a(new aq.u(loc[0], loc[1], this.e), this.am);
            ++n3;
        }
        object = this.as;
        n2 = this.as.length;
        n3 = 0;
        while (n3 < n2) {
            loc = object[n3];
            if (bi.i.a(100) <= 50) {
                aq.u location = new aq.u(loc[0], loc[1], this.e);
                aq.aq.a().a(location).a(41704 + bi.i.a(10), 1);
            }
            ++n3;
        }
        f[] fArray = ao.t.b().c();
        object = fArray;
        n2 = fArray.length;
        n3 = 0;
        while (n3 < n2) {
            int[] door = object[n3];
            if (door.fp() == 2 && (door.fs() != 32684 || door.ft() != 32850)) {
                aq.u spwanLoc = new aq.u(door.fs(), door.ft(), this.e);
                bh.f gfx = bh.f.a(door.fe());
                f create = ao.t.b().a(0, gfx, spwanLoc, 0, 0, false);
                if (create.fs() == 32673 && create.ft() == 32820) {
                    create.f(1);
                } else if (create.fs() == 32741 && create.ft() == 32804 || create.fs() == 32740 && create.ft() == 32788) {
                    create.f(2);
                } else if (create.fs() == 32723 && create.ft() == 32848) {
                    create.f(4);
                }
                this.p.add(create);
            }
            ++n3;
        }
        int i2 = 0;
        while (i2 < 10) {
            this.o.add(ao.t.b().a(0, this.aj, new aq.u(32702 + i2, 32866, this.e), 0, 1, false));
            this.o.add(ao.t.b().a(0, this.aj, new aq.u(32703 + i2, 32872, this.e), 0, 1, false));
            ++i2;
        }
    }

    private int a(int stage, int maxTimeSec) throws InterruptedException {
        int count = -1;
        while (count++ < maxTimeSec) {
            if (this.f < 0 && maxTimeSec >= 60) {
                return maxTimeSec;
            }
            if (this.f > stage) {
                return count;
            }
            Thread.sleep(1000L);
        }
        return maxTimeSec;
    }

    private void a(int stage, int[][] points) throws InterruptedException {
        while (true) {
            int count = 0;
            for (u pc : this.n) {
                int[][] nArray = points;
                int n2 = points.length;
                int n3 = 0;
                while (n3 < n2) {
                    int[] point = nArray[n3];
                    if (pc.fs() == point[0] && pc.ft() == point[1]) {
                        ++count;
                        if (point.equals(this.ap[0])) {
                            this.a(this.j, "$8720", 0);
                        }
                    }
                    if (count == 3) {
                        this.f = -1;
                    } else if (count == points.length) {
                        this.f = stage + 1;
                        this.e();
                        if (stage > 0) {
                            for (f door : this.p) {
                                if (door.p() != stage) continue;
                                door.f();
                            }
                            this.a(new aq.u(point[0], point[1], this.e), 91418, 1);
                        }
                        return;
                    }
                    ++n3;
                }
            }
            Thread.sleep(2000L);
        }
    }

    private void a(int[][] points) {
        int[][] nArray = points;
        int n2 = points.length;
        int n3 = 0;
        while (n3 < n2) {
            int[] point = nArray[n3];
            ao.x.a().a(1172, point[0], point[1], this.e);
            ao.x.a().a(7480, point[0], point[1], this.e);
            ++n3;
        }
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
            int npcid = nArray[n3];
            t npc = mobsID.length > 1 ? bg.a(npcid, loc.f(), loc.g(), loc.b(), 5, 5, true) : bg.a(npcid, loc.f(), loc.g(), loc.b(), 5, 0, true);
            mob_list.add(npc);
            ++n3;
        }
        return mob_list;
    }

    private int a(ArrayList<t> list, int maxTimeSec) throws InterruptedException {
        int count = -1;
        boolean checked = false;
        while (count++ < maxTimeSec) {
            boolean isAllDeath = false;
            for (t mob : list) {
                if (!mob.eX()) {
                    isAllDeath = false;
                    break;
                }
                if (!(mob.z() != 91395 && mob.z() != 91395 || checked)) {
                    new a(301, 1000).a();
                    checked = true;
                } else if (!(mob.z() != 91390 && mob.z() != 91394 || checked || mob.z() != 91395 && mob.z() != 91395)) {
                    new a(302, 1000).a();
                    checked = true;
                }
                isAllDeath = mob.eX();
            }
            if (isAllDeath) {
                return count;
            }
            Thread.sleep(1000L);
        }
        this.r = 0;
        for (t mob : list) {
            if (mob.eX()) continue;
            ++this.r;
        }
        return maxTimeSec;
    }

    private void d() {
        for (u pc : this.n) {
            int locx = 32587 + bi.i.a(4);
            int locy = 32941 + bi.i.a(4);
            aq.am.a(pc, locx, locy, 0, 5, true);
        }
    }

    private void a(t npc, String s2, int cases) {
        String[] colors = new String[]{"", "\\f=", "\\f3"};
        for (u pc : this.n) {
            pc.a(new cm(84, 2, String.valueOf(colors[cases]) + s2));
        }
        if (npc != null) {
            npc.b(new cg(npc, s2, 0));
        }
    }

    private void a(String s2, int cases) {
        String[] colors = new String[]{"", "\\f=", "\\f3"};
        for (u pc : this.n) {
            if (pc.fr() == this.i.fr()) continue;
            pc.a(new cm(84, 2, String.valueOf(colors[cases]) + s2));
        }
    }

    private void b(String s2, int cases) {
        this.a = 0;
        String[] colors = new String[]{"", "\\f=", "\\f3"};
        this.i.a(new cm(84, 2, String.valueOf(colors[cases]) + s2));
    }

    private void e() {
        for (u pc : this.n) {
            pc.a(new cm(83, 2));
        }
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
            e.this.m.schedule(this, (long)this.a, TimeUnit.MILLISECONDS);
        }

        @Override
        public void run() {
            int used_time = 0;
            switch (this.b) {
                case 6: {
                    try {
                        ao.x.a().a(7572, 32742, 32930, e.this.e);
                        e.this.i.a(new be(e.this.i.fr(), "j_ep003"));
                        e.this.b("$7616", 0);
                        Thread.sleep(6000L);
                        if (e.this.a == 69) {
                            e.this.b("$7562", 1);
                        } else if (e.this.a == 66) {
                            e.this.b("$7570", 1);
                        } else {
                            e.this.b("$7617", 1);
                            e e2 = e.this;
                            e2.h = e2.h + 1;
                        }
                        if (e.this.a(1, 120) < 120) {
                            e.this.b("$7622", 2);
                        } else {
                            e.this.i.a(new be(e.this.i.fr(), "j_ep004"));
                            e.this.a(e.this.k, "$7560 : $7618", 2);
                            used_time = e.this.a(1, 30);
                            if (used_time >= 30) {
                                e.this.d();
                                return;
                            }
                            if (used_time < 5) {
                                e e3 = e.this;
                                e3.g = e3.g + 1;
                            }
                            e.this.b("$7620", 0);
                            Thread.sleep(6000L);
                            if (e.this.a == 69) {
                                e.this.b("$7571", 1);
                            } else if (e.this.a == 66) {
                                e.this.b("$7563", 1);
                            } else {
                                e.this.b("$7619", 1);
                                e e4 = e.this;
                                e4.h = e4.h + 1;
                            }
                        }
                        Thread.sleep(3000L);
                        new a(304, 0).a();
                        e.this.i.a(new be(e.this.i.fr(), "j_ep005"));
                        e.this.b("$7623", 0);
                        Thread.sleep(6000L);
                        if (e.this.a == 69) {
                            e.this.b("$7564", 1);
                        } else if (e.this.a == 66) {
                            e.this.b("$7572", 1);
                        } else {
                            e.this.b("$7624", 1);
                            e e5 = e.this;
                            e5.h = e5.h + 1;
                        }
                        Thread.sleep(3000L);
                        e.this.b("$7639", 2);
                        e.this.a(e.this.i.fu(), 91381, 6);
                        if (e.this.a(2, 120) >= 120) {
                            e.this.i.a(new be(e.this.i.fr(), "j_ep004"));
                            e.this.a(e.this.k, "$7560 : $7625", 2);
                            used_time = e.this.a(2, 30);
                            if (used_time >= 30) {
                                e.this.d();
                                return;
                            }
                            if (used_time < 5) {
                                e e6 = e.this;
                                e6.g = e6.g + 1;
                            }
                        }
                        e.this.b("$7627", 0);
                        Thread.sleep(6000L);
                        if (e.this.a == 69) {
                            e.this.b("$7571", 1);
                        } else if (e.this.a == 66) {
                            e.this.b("$7563", 1);
                        } else {
                            e.this.b("$7626", 1);
                            e e7 = e.this;
                            e7.h = e7.h + 1;
                        }
                        Thread.sleep(3000L);
                        e.this.b("$7629", 2);
                        e.this.i.a(new be(e.this.i.fr(), "j_ep006"));
                        Thread.sleep(3000L);
                        e.this.b("$7639", 2);
                        e.this.a(e.this.i.fu(), 91381, 6);
                        Thread.sleep(3000L);
                        e.this.b("$7630", 0);
                        Thread.sleep(6000L);
                        if (e.this.a == 69) {
                            e.this.b("$7565", 1);
                        } else if (e.this.a == 66) {
                            e.this.b("$7573", 1);
                        } else {
                            e.this.b("$7631", 1);
                            e e8 = e.this;
                            e8.h = e8.h + 1;
                        }
                        if (e.this.a(3, 120) >= 120) {
                            e.this.i.a(new be(e.this.i.fr(), "j_ep004"));
                            e.this.a(e.this.k, "$7560 : $7632", 2);
                            used_time = e.this.a(3, 30);
                            if (used_time >= 30) {
                                e.this.d();
                                return;
                            }
                            if (used_time < 5) {
                                e e9 = e.this;
                                e9.g = e9.g + 1;
                            }
                        }
                        e.this.b("$7634", 0);
                        Thread.sleep(6000L);
                        if (e.this.a == 69) {
                            e.this.b("$7563", 1);
                        } else if (e.this.a == 66) {
                            e.this.b("$7571", 1);
                        } else {
                            e.this.b("$7633", 1);
                            e e10 = e.this;
                            e10.h = e10.h + 1;
                        }
                        Thread.sleep(6000L);
                        e.this.b("$7636", 2);
                        Thread.sleep(6000L);
                        e.this.i.a(new be(e.this.i.fr(), "j_ep007"));
                        e.this.b("$7637", 0);
                        Thread.sleep(6000L);
                        if (e.this.a == 69) {
                            e.this.b("$7566", 1);
                        } else if (e.this.a == 66) {
                            e.this.b("$7574", 1);
                        } else {
                            e.this.b("$7638", 1);
                            e e11 = e.this;
                            e11.h = e11.h + 1;
                        }
                        Thread.sleep(3000L);
                        e.this.b("$7639", 2);
                        e.this.a(e.this.i.fu(), 91381, 6);
                        if (e.this.a(4, 120) >= 120) {
                            e.this.i.a(new be(e.this.i.fr(), "j_ep004"));
                            e.this.a(e.this.k, "$7560 : $7640", 2);
                            Thread.sleep(3000L);
                        }
                        e.this.b("$7642", 0);
                        Thread.sleep(6000L);
                        if (e.this.a == 69) {
                            e.this.b("$7563", 1);
                        } else if (e.this.a == 66) {
                            e.this.b("$7571", 1);
                        } else {
                            e.this.b("$7641", 1);
                            e e12 = e.this;
                            e12.h = e12.h + 1;
                        }
                        e.this.i.a(new be(e.this.i.fr(), "j_ep008"));
                        e.this.b("$7643", 0);
                        Thread.sleep(6000L);
                        if (e.this.a == 69) {
                            e.this.b("$7567", 1);
                        } else if (e.this.a == 66) {
                            e.this.b("$7575", 1);
                        } else {
                            e.this.b("$7646", 1);
                            e e13 = e.this;
                            e13.h = e13.h + 1;
                        }
                        if (e.this.h >= 9) {
                            e.this.b("$7644", 2);
                            Thread.sleep(6000L);
                            if (e.this.a == 69) {
                                e.this.d = true;
                                e e14 = e.this;
                                e14.g = e14.g + 1;
                                e.this.b("$7568", 1);
                            } else {
                                e.this.b("$7645", 1);
                            }
                        }
                        Thread.sleep(2500L);
                        aq.am.a(e.this.i, 32718, 32849, e.this.e, 5, true);
                    }
                    catch (InterruptedException interruptedException) {}
                    break;
                }
                case 7: {
                    try {
                        e.this.a(e.this.ao);
                        e.this.a(1, e.this.ao);
                        e.this.a(e.this.j, "$7597 : $7621", 2);
                        e.this.a(e.this.ap);
                        e.this.a(2, e.this.ap);
                        e.this.a(e.this.j, "$7597 : $7628", 2);
                        e.this.a(e.this.aq);
                        e.this.a(3, e.this.aq);
                        Thread.sleep(2000L);
                        for (u pc : e.this.n) {
                            if (pc.fr() == e.this.i.fr()) continue;
                            aq.am.a(pc, 32796, 32848, e.this.e, 5, true);
                        }
                        Thread.sleep(2000L);
                        e.this.a(e.this.j, "$7597 : $7635", 2);
                        ArrayList list = new ArrayList();
                        list.addAll(e.this.a(new aq.u(32775, 32846, e.this.e), 45107, 8));
                        list.addAll(e.this.a(new aq.u(32775, 32846, e.this.e), 45130, 8));
                        if (e.this.a(list, 120) >= 120) {
                            e.this.d();
                            return;
                        }
                        for (f door : e.this.p) {
                            if (door.p() != 4) continue;
                            door.f();
                        }
                        e e15 = e.this;
                        e15.g = e15.g + 1;
                        e.this.f = 5;
                        e.this.a(e.this.j, "$7647", 0);
                        Thread.sleep(5000L);
                        e.this.a(e.this.j, "$7648", 0);
                        Thread.sleep(15000L);
                        for (f door : e.this.p) {
                            if (door.o() != 28) continue;
                            door.g();
                        }
                        e.this.a(e.this.j, "$7649", 0);
                        new a(5, 5000).a();
                    }
                    catch (InterruptedException list) {}
                    break;
                }
                case 5: {
                    try {
                        e.this.e();
                        e.this.a(e.this.j, "$7650", 0);
                        Thread.sleep(4000L);
                        e.this.a(e.this.j, "$7651", 0);
                        Thread.sleep(4000L);
                        int round = 1;
                        while (round <= 11) {
                            ArrayList list = new ArrayList();
                            Thread.sleep(1000L);
                            int[] msgid = new int[]{8708, 8709, 8710, 8704, 8711, 8712, 8713, 8706, 8714, 8715, 8716};
                            e.this.a(e.this.j, "$" + msgid[round - 1], 0);
                            Thread.sleep(3000L);
                            int subRound = 0;
                            while (subRound < 4) {
                                e.this.a(e.this.j, "$" + (8689 + subRound), 0);
                                Thread.sleep(1000L);
                                int[] mobids = new int[5];
                                int i2 = 0;
                                while (i2 < mobids.length) {
                                    mobids[i2] = e.this.ak[bi.i.a(e.this.ak.length)];
                                    ++i2;
                                }
                                list.addAll(e.this.a(e.this.c, mobids));
                                Thread.sleep(4000L);
                                ++subRound;
                            }
                            if (round == 4) {
                                Thread.sleep(1000L);
                                e.this.a(null, "$8705", 0);
                                Thread.sleep(3000L);
                                list.addAll(e.this.a(e.this.c, 91391, 1));
                            } else if (round == 8) {
                                Thread.sleep(1000L);
                                e.this.a(null, "$8707", 0);
                                Thread.sleep(3000L);
                                list.addAll(e.this.a(e.this.c, 91393, 1));
                            }
                            int use_time = e.this.a(list, 300);
                            if (use_time >= 300) {
                                if (e.this.r >= 4) {
                                    e.this.a(e.this.j, "$7653", 0);
                                    e e16 = e.this;
                                    e16.g = e16.g + 10;
                                    Thread.sleep(1000L);
                                }
                                e.this.a(e.this.j, "$7811", 0);
                                e.this.j.b(new cg(e.this.j, "$7811", 0));
                                if (e.this.a(list, 60) >= 60) {
                                    e.this.a(e.this.j, "$7681", 0);
                                    e.this.j.b(new cg(e.this.j, "$7681", 0));
                                    e.this.d();
                                } else {
                                    e.this.a(e.this.j, "$8703", 0);
                                    e.this.j.b(new cg(e.this.j, "$8703", 0));
                                }
                            } else if (use_time < 120) {
                                e e17 = e.this;
                                e17.g = e17.g + 100;
                            } else {
                                e.this.a(e.this.j, "$7652", 0);
                            }
                            ++round;
                        }
                        e.this.a(e.this.j, "$7654", 0);
                        Thread.sleep(5000L);
                        e.this.a(e.this.j, "$8717", 0);
                        e.this.e();
                        Thread.sleep(2000L);
                        int[] mobids = new int[16];
                        int[] monster = e.this.ak;
                        if (e.this.g > 1000) {
                            e.this.l = (t)e.this.a(e.this.c, 91394, 1).get(0);
                        } else if (e.this.g > 100 && e.this.g < 1000) {
                            e.this.l = (t)e.this.a(e.this.c, 91390, 1).get(0);
                        } else {
                            monster = e.this.al;
                        }
                        boolean isSpawnKelenis = true;
                        e.this.l.b(new cg(e.this.l, "$7656", 0));
                        Thread.sleep(1000L);
                        e.this.l.b(new cg(e.this.l, "$7657", 0));
                        if (!e.this.d) {
                            e.this.k = (t)e.this.a(new aq.u(32711, 32845, e.this.e), 91396, 1).get(0);
                        } else if (e.this.g % 10 == 5) {
                            e.this.k = (t)e.this.a(new aq.u(32711, 32845, e.this.e), 91395, 1).get(0);
                        } else {
                            isSpawnKelenis = false;
                        }
                        if (isSpawnKelenis) {
                            if (e.this.k.z() == 91396) {
                                Thread.sleep(1500L);
                                e.this.k.b(new cg(e.this.k, "$7820", 0));
                                Thread.sleep(1500L);
                                e.this.j.b(new cg(e.this.j, "$7821", 0));
                                Thread.sleep(1500L);
                                e.this.k.b(new cg(e.this.k, "$7822", 0));
                                Thread.sleep(1500L);
                                e.this.k.b(new cg(e.this.k, "$7823", 0));
                                Thread.sleep(1500L);
                                e.this.j.b(new cg(e.this.j, "$7824", 0));
                            } else {
                                Thread.sleep(1500L);
                                e.this.k.b(new cg(e.this.k, "$7815", 0));
                                Thread.sleep(1500L);
                                e.this.k.b(new cg(e.this.k, "$7816", 0));
                                Thread.sleep(1500L);
                                e.this.j.b(new cg(e.this.j, "$7817", 0));
                                Thread.sleep(1500L);
                                e.this.k.b(new cg(e.this.k, "$7818", 0));
                                Thread.sleep(1500L);
                                e.this.l.b(new cg(e.this.l, "$7819", 0));
                                Thread.sleep(1500L);
                            }
                        }
                        int i3 = 0;
                        while (i3 < mobids.length) {
                            mobids[i3] = monster[bi.i.a(e.this.ak.length)];
                            ++i3;
                        }
                        ArrayList list = e.this.a(new aq.u(32706, 32836, e.this.e), mobids);
                        if (e.this.l != null) {
                            list.add(e.this.l);
                        }
                        if (isSpawnKelenis) {
                            list.add(e.this.k);
                        }
                        if (e.this.a(list, 900) >= 900) {
                            return;
                        }
                        t Mob = null;
                        e.this.e();
                        if (e.this.g >= 5) {
                            Mob = (t)e.this.a(new aq.u(32707, 32858, e.this.e), 91443, 1).get(0);
                            Mob.b(new cg(Mob, "$7663", 0));
                            Thread.sleep(1000L);
                            Mob.b(new cg(Mob, "$7664", 0));
                            Thread.sleep(1000L);
                            Mob.b(new cg(Mob, "$7665", 0));
                            Thread.sleep(1000L);
                            e.this.j.b(new cg(e.this.j, "$7837", 0));
                        } else {
                            Mob = (t)e.this.a(new aq.u(32707, 32858, e.this.e), 91444, 1).get(0);
                            Mob.b(new cg(Mob, "$7694", 0));
                            Thread.sleep(1000L);
                            Mob.b(new cg(Mob, "$7695", 0));
                            Thread.sleep(1000L);
                            Mob.b(new cg(Mob, "$7709", 0));
                            Thread.sleep(1000L);
                            e.this.j.b(new cg(e.this.j, "$7677", 0));
                        }
                        Thread.sleep(1000L);
                        e.this.j.b(new cg(e.this.j, "$7838", 0));
                        Thread.sleep(1000L);
                        e.this.j.b(new cg(e.this.j, "$7839", 0));
                        ArrayList<aq.u> loc_list = new ArrayList<aq.u>();
                        int cnt = 3;
                        for (f wall : e.this.o) {
                            int y2;
                            int x2;
                            aq.u move_loc;
                            if (cnt % 4 == 0) {
                                e.this.e();
                            }
                            while (loc_list.contains(move_loc = new aq.u(x2 = 32702 + bi.i.a(10), y2 = 32860 + bi.i.a(6), e.this.e))) {
                            }
                            loc_list.add(move_loc);
                            ao.t.b().a(wall.fu());
                            ao.t.b().a(0, e.this.aj, move_loc, 0, 1, false);
                            Thread.sleep(600L);
                            ++cnt;
                        }
                        int i4 = 0;
                        while (i4 < e.this.o.size()) {
                            ao.t.b().a((aq.u)loc_list.get(i4));
                            ao.t.b().a(0, e.this.aj, ((f)e.this.o.get(i4)).fu(), 0, 1, false);
                            Thread.sleep(600L);
                            ++i4;
                        }
                        e.this.a(e.this.j, "$8718", 0);
                        int[][] point = new int[e.this.n.size() - 1][2];
                        int i5 = 0;
                        while (i5 < point.length) {
                            point[i5] = e.this.ar[i5];
                            e.this.a(new aq.u(point[i5][0], point[i5][1], e.this.e), 91439, 1);
                            ++i5;
                        }
                        e.this.a(0, point);
                        e.this.a(e.this.j, "$8719", 0);
                        e.this.a(new aq.u(32802, 32868, e.this.e), 91438, 1);
                        e.this.a(0, new int[][]{{32802, 32868}});
                        int[][] nArray = point;
                        int n2 = point.length;
                        int n3 = 0;
                        while (n3 < n2) {
                            int[] p2 = nArray[n3];
                            aq.aq.a().a(new aq.u(p2[0], p2[1], e.this.e)).a(41757, 1);
                            ++n3;
                        }
                        aq.aq.a().a(new aq.u(32802, 32868, e.this.e)).a(41757, 1);
                        Thread.sleep(30000L);
                        e.this.d();
                    }
                    catch (Exception mobids) {}
                    break;
                }
                case 10: {
                    e.this.a(e.this.j, "$7598", 0);
                    new a(11, 4000).a();
                    break;
                }
                case 11: {
                    e.this.a(e.this.j, "$8693", 0);
                    new a(12, 8000).a();
                    break;
                }
                case 12: {
                    e.this.a(e.this.j, "$8694", 0);
                    if (e.this.a == 69) {
                        new a(20, 8000).a();
                        break;
                    }
                    new a(13, 8000).a();
                    break;
                }
                case 13: {
                    e.this.a(e.this.j, "$8695", 0);
                    new a(14, 8000).a();
                    break;
                }
                case 14: {
                    e.this.a(e.this.j, "$8696", 0);
                    new a(15, 8000).a();
                    break;
                }
                case 15: {
                    e.this.a(e.this.j, "$8697", 0);
                    new a(16, 8000).a();
                    break;
                }
                case 16: {
                    e.this.a(e.this.j, "$8698", 0);
                    new a(17, 8000).a();
                    break;
                }
                case 17: {
                    e.this.a(e.this.j, "$8699", 0);
                    new a(18, 8000).a();
                    break;
                }
                case 18: {
                    e.this.a(e.this.j, "$8700", 0);
                    new a(19, 8000).a();
                    break;
                }
                case 19: {
                    e.this.a(e.this.j, "$8701", 0);
                    new a(20, 8000).a();
                    break;
                }
                case 20: {
                    e.this.a(e.this.j, "$8702", 0);
                    new a(21, 6000).a();
                    break;
                }
                case 21: {
                    int time = 4000;
                    if (e.this.a != 69) {
                        e.this.i.a(new be(e.this.i.fr(), "j_ep002"));
                        time = 10000;
                    }
                    new a(22, time).a();
                    break;
                }
                case 22: {
                    e.this.i.a(new be(e.this.i.fr(), "j_ep001"));
                    e.this.a(e.this.j, "$7599", 0);
                    new a(23, 8000).a();
                    break;
                }
                case 23: {
                    try {
                        if (e.this.a != 69) {
                            e.this.a(e.this.j, "$7601", 0);
                            Thread.sleep(5000L);
                            int i6 = 0;
                            while (i6 < 5) {
                                if (e.this.a == 69) break;
                                e.this.a(e.this.j, "$7602", 0);
                                Thread.sleep(5000L);
                                ++i6;
                            }
                        }
                        new a(24, 8000).a();
                    }
                    catch (InterruptedException i6) {}
                    break;
                }
                case 24: {
                    e.this.a(e.this.j, "$7600", 0);
                    new a(25, 8000).a();
                    break;
                }
                case 25: {
                    e.this.a(e.this.j, "$7603", 0);
                    new a(26, 2000).a();
                    break;
                }
                case 26: {
                    aq.am.a(e.this.i, 32738, 32930, e.this.e, 5, true);
                    e.this.a(e.this.j, "$7604", 0);
                    new a(101, 1000).a();
                    new a(201, 1000).a();
                    break;
                }
                case 101: {
                    e.this.a("$7605", 0);
                    new a(102, 4000).a();
                    break;
                }
                case 102: {
                    e.this.a("$7606", 0);
                    new a(103, 4000).a();
                    break;
                }
                case 103: {
                    e.this.j.a(32716, 32846, 6);
                    new a(104, 4000).a();
                    break;
                }
                case 104: {
                    for (u pc : e.this.n) {
                        if (pc.fr() == e.this.i.fr()) continue;
                        aq.am.a(pc, 32665, 32793, e.this.e, 5, true);
                    }
                    e.this.a("$7611", 0);
                    new a(105, 2000).a();
                    break;
                }
                case 105: {
                    e.this.a("$7613", 0);
                    new a(106, 5000).a();
                    break;
                }
                case 106: {
                    e.this.a("$7615", 0);
                    e.this.e();
                    break;
                }
                case 201: {
                    e.this.b("$7607", 0);
                    new a(202, 4000).a();
                    break;
                }
                case 202: {
                    e.this.b("$7608", 0);
                    new a(203, 4000).a();
                    break;
                }
                case 203: {
                    e.this.b("$7609", 0);
                    new a(204, 5000).a();
                    break;
                }
                case 204: {
                    e.this.b("$7610", 0);
                    new a(205, 5000).a();
                    break;
                }
                case 205: {
                    if (e.this.a == 69) {
                        e.this.b("$7561", 1);
                    } else if (e.this.a == 66) {
                        e.this.b("$7569", 1);
                    } else {
                        e.this.b("$7612", 1);
                    }
                    new a(206, 6000).a();
                    break;
                }
                case 206: {
                    e.this.b("$7614", 2);
                    new a(6, 3000).a();
                    new a(7, 3000).a();
                    new a(303, 3000).a();
                    break;
                }
                case 303: {
                    try {
                        int count = 0;
                        while (e.this.k.z() == 91397) {
                            e.this.k.b(new cg(e.this.k, "$" + (7576 + bi.i.a(12)), 0));
                            if (e.this.f <= 3) {
                                if (count % 12 == 0) {
                                    if (bi.i.a(100) <= 66) {
                                        e.this.a(e.this.i.fu(), e.this.an);
                                    } else {
                                        e.this.a(e.this.i.fu(), 45278, 3);
                                        e.this.b("$7596", 0);
                                    }
                                }
                                if (count % (1 + bi.i.a(2)) == 0) {
                                    for (aa obj : aq.aq.a().b((aa)e.this.k, 6)) {
                                        if (!(obj instanceof s)) continue;
                                        ((s)obj).b(new ee(obj.fr(), 7398));
                                    }
                                }
                            }
                            count += 10;
                            Thread.sleep(10000L);
                        }
                    }
                    catch (Exception count) {}
                    break;
                }
                case 301: {
                    try {
                        e.this.j.b(new cg(e.this.j, "$7833", 0));
                        Thread.sleep(2000L);
                        e.this.j.b(new cg(e.this.j, "$7834", 0));
                    }
                    catch (InterruptedException count) {}
                    break;
                }
                case 302: {
                    try {
                        e.this.j.b(new cg(e.this.j, "$7827", 0));
                        Thread.sleep(1000L);
                        e.this.k.b(new cg(e.this.k, "$7828", 0));
                        Thread.sleep(1800L);
                        e.this.j.b(new cg(e.this.j, "$7829", 0));
                        Thread.sleep(1800L);
                        e.this.k.b(new cg(e.this.k, "$7830", 0));
                        Thread.sleep(1000L);
                        e.this.j.b(new cg(e.this.j, "$7831", 0));
                        Thread.sleep(1500L);
                        e.this.k.b(new cg(e.this.k, "$7832", 0));
                    }
                    catch (InterruptedException count) {}
                    break;
                }
                case 304: {
                    try {
                        t orc = (t)e.this.a(new aq.u(32729, 32917, e.this.e), 91433, 1).get(0);
                        t orcFighter = (t)e.this.a(new aq.u(32732, 32916, e.this.e), 91436, 1).get(0);
                        t orcArcher = (t)e.this.a(new aq.u(32730, 32920, e.this.e), 91434, 1).get(0);
                        t barusim = (t)e.this.a(new aq.u(32732, 32919, e.this.e), 91435, 1).get(0);
                        ArrayList<t> list = new ArrayList<t>();
                        list.add(orc);
                        list.add(orcFighter);
                        list.add(orcArcher);
                        list.add(barusim);
                        e.this.a(list, 3, 15);
                        Thread.sleep(2000L);
                        barusim.b(new cg(barusim, "$7842", 0));
                        Thread.sleep(2000L);
                        orc.b(new cg(orc, "$7848", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new cg(orcFighter, "$7854", 0));
                        Thread.sleep(2000L);
                        orc.b(new cg(orc, "$7849", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new cg(orcFighter, "$7855", 0));
                        Thread.sleep(2000L);
                        barusim.b(new cg(barusim, "$7843", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new cg(orcFighter, "$7856", 0));
                        Thread.sleep(2000L);
                        barusim.b(new cg(barusim, "$7844", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new cg(orcFighter, "$7857", 0));
                        Thread.sleep(2000L);
                        orcArcher.b(new cg(orcArcher, "$7851", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new cg(orcFighter, "$7858", 0));
                        Thread.sleep(2000L);
                        orcArcher.b(new cg(orcArcher, "$7852", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new cg(orcFighter, "$7859", 0));
                        Thread.sleep(2000L);
                        orcArcher.b(new cg(orcArcher, "$7853", 0));
                        Thread.sleep(2000L);
                        orc.b(new cg(orc, "$7850", 0));
                        Thread.sleep(2000L);
                        barusim.b(new cg(barusim, "$7845", 0));
                        Thread.sleep(4000L);
                        barusim.b(new cg(barusim, "$7846", 0));
                        Thread.sleep(4000L);
                        barusim.b(new cg(barusim, "$7847", 0));
                        Thread.sleep(4000L);
                        e.this.a(list, 1, 20);
                    }
                    catch (InterruptedException orc) {}
                    break;
                }
                case 999: {
                    try {
                        try {
                            while (!e.this.n.isEmpty()) {
                                for (u pc : e.this.n) {
                                    if (pc.bE() != 0 && pc.fp() == e.this.e) continue;
                                    e.this.n.remove(pc);
                                    if (!pc.am()) continue;
                                    e.this.d();
                                }
                                Thread.sleep(3000L);
                            }
                        }
                        catch (Exception exception) {
                            System.out.println("[\u526f\u672c\u7d50\u675f]:\u54c8\u6c40\u526f\u672c");
                            e.this.b();
                            break;
                        }
                    }
                    finally {
                        System.out.println("[\u526f\u672c\u7d50\u675f]:\u54c8\u6c40\u526f\u672c");
                        e.this.b();
                    }
                }
            }
        }
    }
}

