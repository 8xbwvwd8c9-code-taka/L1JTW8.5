/*
 * Decompiled with CFR 0.152.
 */
package l1r.as;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import l1r.ao.DoorTable;
import l1r.ao.FieldSpawnTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_Html;
import l1r.be.S_NpcChatPacket;
import l1r.be.S_PacketBox;
import l1r.be.S_SkillSound;
import l1r.bh.L1DoorGfx;
import l1r.bi.Random;

public class L1HardinBattle {
    private L1Location c;
    public int a;
    public boolean b = true;
    private boolean d;
    private final int e;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private L1PcInstance i;
    private L1NpcInstance j;
    private L1NpcInstance k;
    private L1NpcInstance l;
    private ScheduledExecutorService m;
    private final CopyOnWriteArrayList<L1PcInstance> n = new CopyOnWriteArrayList();
    private final ArrayList<L1DoorInstance> o = new ArrayList();
    private final ArrayList<L1DoorInstance> p = new ArrayList();
    private static L1HardinBattle q;
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
    private final L1DoorGfx aj = L1DoorGfx.a(7536);
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

    public static L1HardinBattle a() {
        if (q == null) {
            q = new L1HardinBattle(9000);
        }
        return q;
    }

    private L1HardinBattle(int mapId) {
        this.e = (short)mapId;
    }

    public void a(int stage) {
        this.c();
        new L1R_a(stage, 0).a();
        new L1R_a(999, 0).a();
    }

    private void a(ArrayList<L1NpcInstance> list, int dir, int range) throws InterruptedException {
        int i = 0;
        while (i < range) {
            for (L1NpcInstance npc : list) {
                if (i == 0 && npc.fb() != dir) {
                    npc.ct(dir);
                }
                npc.g(npc.fb());
            }
            Thread.sleep(800L);
            ++i;
        }
    }

    private void b() {
        for (L1DoorInstance door : this.p) {
            DoorTable.b().a(door.fu());
        }
        for (L1DoorInstance wall : this.o) {
            DoorTable.b().a(wall.fu());
        }
        for (L1Object obj : L1World.a().b(this.e).values()) {
            L1World.a().d(obj);
        }
        this.b = true;
        this.m.shutdownNow();
    }

    private void c() {
        int[] loc;
        this.b = false;
        this.c = new L1Location(32707, 32846, this.e);
        this.m = Executors.newScheduledThreadPool(10);
        for (L1Object obj : L1World.a().b(this.e).values()) {
            if (!(obj instanceof L1PcInstance)) continue;
            L1PcInstance pc = (L1PcInstance)obj;
            if (pc.am() || pc.l()) {
                this.i = pc;
            }
            this.n.add(pc);
        }
        this.k = this.a(new L1Location(32742, 32930, this.e), 91397, 1).get(0);
        this.j = this.a(new L1Location(32733, 32724, this.e), 91430, 1).get(0);
        Object object = this.at;
        int n = this.at.length;
        int n2 = 0;
        while (n2 < n) {
            loc = object[n2];
            this.a(new L1Location(loc[0], loc[1], this.e), this.am);
            ++n2;
        }
        object = this.as;
        n = this.as.length;
        n2 = 0;
        while (n2 < n) {
            loc = object[n2];
            if (Random.a(100) <= 50) {
                L1Location location = new L1Location(loc[0], loc[1], this.e);
                L1World.a().a(location).a(41704 + Random.a(10), 1);
            }
            ++n2;
        }
        L1DoorInstance[] l1DoorInstanceArray = DoorTable.b().c();
        object = l1DoorInstanceArray;
        n = l1DoorInstanceArray.length;
        n2 = 0;
        while (n2 < n) {
            int[] door = object[n2];
            if (door.fp() == 2 && (door.fs() != 32684 || door.ft() != 32850)) {
                L1Location spwanLoc = new L1Location(door.fs(), door.ft(), this.e);
                L1DoorGfx gfx = L1DoorGfx.a(door.fe());
                L1DoorInstance create = DoorTable.b().a(0, gfx, spwanLoc, 0, 0, false);
                if (create.fs() == 32673 && create.ft() == 32820) {
                    create.f(1);
                } else if (create.fs() == 32741 && create.ft() == 32804 || create.fs() == 32740 && create.ft() == 32788) {
                    create.f(2);
                } else if (create.fs() == 32723 && create.ft() == 32848) {
                    create.f(4);
                }
                this.p.add(create);
            }
            ++n2;
        }
        int i = 0;
        while (i < 10) {
            this.o.add(DoorTable.b().a(0, this.aj, new L1Location(32702 + i, 32866, this.e), 0, 1, false));
            this.o.add(DoorTable.b().a(0, this.aj, new L1Location(32703 + i, 32872, this.e), 0, 1, false));
            ++i;
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
            for (L1PcInstance pc : this.n) {
                int[][] nArray = points;
                int n = points.length;
                int n2 = 0;
                while (n2 < n) {
                    int[] point = nArray[n2];
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
                            for (L1DoorInstance door : this.p) {
                                if (door.p() != stage) continue;
                                door.f();
                            }
                            this.a(new L1Location(point[0], point[1], this.e), 91418, 1);
                        }
                        return;
                    }
                    ++n2;
                }
            }
            Thread.sleep(2000L);
        }
    }

    private void a(int[][] points) {
        int[][] nArray = points;
        int n = points.length;
        int n2 = 0;
        while (n2 < n) {
            int[] point = nArray[n2];
            FieldSpawnTable.a().a(1172, point[0], point[1], this.e);
            FieldSpawnTable.a().a(7480, point[0], point[1], this.e);
            ++n2;
        }
    }

    private ArrayList<L1NpcInstance> a(L1Location loc, int mobid, int amount) {
        int[] mobids = new int[amount];
        int i = 0;
        while (i < amount) {
            mobids[i] = mobid;
            ++i;
        }
        return this.a(loc, mobids);
    }

    private ArrayList<L1NpcInstance> a(L1Location loc, int[] mobsID) {
        ArrayList<L1NpcInstance> mob_list = new ArrayList<L1NpcInstance>();
        int[] nArray = mobsID;
        int n = mobsID.length;
        int n2 = 0;
        while (n2 < n) {
            int npcid = nArray[n2];
            L1NpcInstance npc = mobsID.length > 1 ? SpawnTable.a(npcid, loc.f(), loc.g(), loc.b(), 5, 5, true) : SpawnTable.a(npcid, loc.f(), loc.g(), loc.b(), 5, 0, true);
            mob_list.add(npc);
            ++n2;
        }
        return mob_list;
    }

    private int a(ArrayList<L1NpcInstance> list, int maxTimeSec) throws InterruptedException {
        int count = -1;
        boolean checked = false;
        while (count++ < maxTimeSec) {
            boolean isAllDeath = false;
            for (L1NpcInstance mob : list) {
                if (!mob.eX()) {
                    isAllDeath = false;
                    break;
                }
                if (!(mob.z() != 91395 && mob.z() != 91395 || checked)) {
                    new L1R_a(301, 1000).a();
                    checked = true;
                } else if (!(mob.z() != 91390 && mob.z() != 91394 || checked || mob.z() != 91395 && mob.z() != 91395)) {
                    new L1R_a(302, 1000).a();
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
        for (L1NpcInstance mob : list) {
            if (mob.eX()) continue;
            ++this.r;
        }
        return maxTimeSec;
    }

    private void d() {
        for (L1PcInstance pc : this.n) {
            int locx = 32587 + Random.a(4);
            int locy = 32941 + Random.a(4);
            L1Teleport.a(pc, locx, locy, 0, 5, true);
        }
    }

    private void a(L1NpcInstance npc, String s, int cases) {
        String[] colors = new String[]{"", "\\f=", "\\f3"};
        for (L1PcInstance pc : this.n) {
            pc.a(new S_PacketBox(84, 2, String.valueOf(colors[cases]) + s));
        }
        if (npc != null) {
            npc.b(new S_NpcChatPacket(npc, s, 0));
        }
    }

    private void a(String s, int cases) {
        String[] colors = new String[]{"", "\\f=", "\\f3"};
        for (L1PcInstance pc : this.n) {
            if (pc.fr() == this.i.fr()) continue;
            pc.a(new S_PacketBox(84, 2, String.valueOf(colors[cases]) + s));
        }
    }

    private void b(String s, int cases) {
        this.a = 0;
        String[] colors = new String[]{"", "\\f=", "\\f3"};
        this.i.a(new S_PacketBox(84, 2, String.valueOf(colors[cases]) + s));
    }

    private void e() {
        for (L1PcInstance pc : this.n) {
            pc.a(new S_PacketBox(83, 2));
        }
    }

    private class L1R_a
    implements Runnable {
        int a;
        int b;

        private L1R_a(int order, int time) {
            this.a = time;
            this.b = order;
        }

        private void a() {
            L1HardinBattle.this.m.schedule(this, (long)this.a, TimeUnit.MILLISECONDS);
        }

        @Override
        public void run() {
            int used_time = 0;
            switch (this.b) {
                case 6: {
                    try {
                        FieldSpawnTable.a().a(7572, 32742, 32930, L1HardinBattle.this.e);
                        L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep003"));
                        L1HardinBattle.this.b("$7616", 0);
                        Thread.sleep(6000L);
                        if (L1HardinBattle.this.a == 69) {
                            L1HardinBattle.this.b("$7562", 1);
                        } else if (L1HardinBattle.this.a == 66) {
                            L1HardinBattle.this.b("$7570", 1);
                        } else {
                            L1HardinBattle.this.b("$7617", 1);
                            L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                            l1HardinBattle.h = l1HardinBattle.h + 1;
                        }
                        if (L1HardinBattle.this.a(1, 120) < 120) {
                            L1HardinBattle.this.b("$7622", 2);
                        } else {
                            L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep004"));
                            L1HardinBattle.this.a(L1HardinBattle.this.k, "$7560 : $7618", 2);
                            used_time = L1HardinBattle.this.a(1, 30);
                            if (used_time >= 30) {
                                L1HardinBattle.this.d();
                                return;
                            }
                            if (used_time < 5) {
                                L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                                l1HardinBattle.g = l1HardinBattle.g + 1;
                            }
                            L1HardinBattle.this.b("$7620", 0);
                            Thread.sleep(6000L);
                            if (L1HardinBattle.this.a == 69) {
                                L1HardinBattle.this.b("$7571", 1);
                            } else if (L1HardinBattle.this.a == 66) {
                                L1HardinBattle.this.b("$7563", 1);
                            } else {
                                L1HardinBattle.this.b("$7619", 1);
                                L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                                l1HardinBattle.h = l1HardinBattle.h + 1;
                            }
                        }
                        Thread.sleep(3000L);
                        new L1R_a(304, 0).a();
                        L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep005"));
                        L1HardinBattle.this.b("$7623", 0);
                        Thread.sleep(6000L);
                        if (L1HardinBattle.this.a == 69) {
                            L1HardinBattle.this.b("$7564", 1);
                        } else if (L1HardinBattle.this.a == 66) {
                            L1HardinBattle.this.b("$7572", 1);
                        } else {
                            L1HardinBattle.this.b("$7624", 1);
                            L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                            l1HardinBattle.h = l1HardinBattle.h + 1;
                        }
                        Thread.sleep(3000L);
                        L1HardinBattle.this.b("$7639", 2);
                        L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), 91381, 6);
                        if (L1HardinBattle.this.a(2, 120) >= 120) {
                            L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep004"));
                            L1HardinBattle.this.a(L1HardinBattle.this.k, "$7560 : $7625", 2);
                            used_time = L1HardinBattle.this.a(2, 30);
                            if (used_time >= 30) {
                                L1HardinBattle.this.d();
                                return;
                            }
                            if (used_time < 5) {
                                L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                                l1HardinBattle.g = l1HardinBattle.g + 1;
                            }
                        }
                        L1HardinBattle.this.b("$7627", 0);
                        Thread.sleep(6000L);
                        if (L1HardinBattle.this.a == 69) {
                            L1HardinBattle.this.b("$7571", 1);
                        } else if (L1HardinBattle.this.a == 66) {
                            L1HardinBattle.this.b("$7563", 1);
                        } else {
                            L1HardinBattle.this.b("$7626", 1);
                            L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                            l1HardinBattle.h = l1HardinBattle.h + 1;
                        }
                        Thread.sleep(3000L);
                        L1HardinBattle.this.b("$7629", 2);
                        L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep006"));
                        Thread.sleep(3000L);
                        L1HardinBattle.this.b("$7639", 2);
                        L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), 91381, 6);
                        Thread.sleep(3000L);
                        L1HardinBattle.this.b("$7630", 0);
                        Thread.sleep(6000L);
                        if (L1HardinBattle.this.a == 69) {
                            L1HardinBattle.this.b("$7565", 1);
                        } else if (L1HardinBattle.this.a == 66) {
                            L1HardinBattle.this.b("$7573", 1);
                        } else {
                            L1HardinBattle.this.b("$7631", 1);
                            L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                            l1HardinBattle.h = l1HardinBattle.h + 1;
                        }
                        if (L1HardinBattle.this.a(3, 120) >= 120) {
                            L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep004"));
                            L1HardinBattle.this.a(L1HardinBattle.this.k, "$7560 : $7632", 2);
                            used_time = L1HardinBattle.this.a(3, 30);
                            if (used_time >= 30) {
                                L1HardinBattle.this.d();
                                return;
                            }
                            if (used_time < 5) {
                                L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                                l1HardinBattle.g = l1HardinBattle.g + 1;
                            }
                        }
                        L1HardinBattle.this.b("$7634", 0);
                        Thread.sleep(6000L);
                        if (L1HardinBattle.this.a == 69) {
                            L1HardinBattle.this.b("$7563", 1);
                        } else if (L1HardinBattle.this.a == 66) {
                            L1HardinBattle.this.b("$7571", 1);
                        } else {
                            L1HardinBattle.this.b("$7633", 1);
                            L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                            l1HardinBattle.h = l1HardinBattle.h + 1;
                        }
                        Thread.sleep(6000L);
                        L1HardinBattle.this.b("$7636", 2);
                        Thread.sleep(6000L);
                        L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep007"));
                        L1HardinBattle.this.b("$7637", 0);
                        Thread.sleep(6000L);
                        if (L1HardinBattle.this.a == 69) {
                            L1HardinBattle.this.b("$7566", 1);
                        } else if (L1HardinBattle.this.a == 66) {
                            L1HardinBattle.this.b("$7574", 1);
                        } else {
                            L1HardinBattle.this.b("$7638", 1);
                            L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                            l1HardinBattle.h = l1HardinBattle.h + 1;
                        }
                        Thread.sleep(3000L);
                        L1HardinBattle.this.b("$7639", 2);
                        L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), 91381, 6);
                        if (L1HardinBattle.this.a(4, 120) >= 120) {
                            L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep004"));
                            L1HardinBattle.this.a(L1HardinBattle.this.k, "$7560 : $7640", 2);
                            Thread.sleep(3000L);
                        }
                        L1HardinBattle.this.b("$7642", 0);
                        Thread.sleep(6000L);
                        if (L1HardinBattle.this.a == 69) {
                            L1HardinBattle.this.b("$7563", 1);
                        } else if (L1HardinBattle.this.a == 66) {
                            L1HardinBattle.this.b("$7571", 1);
                        } else {
                            L1HardinBattle.this.b("$7641", 1);
                            L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                            l1HardinBattle.h = l1HardinBattle.h + 1;
                        }
                        L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep008"));
                        L1HardinBattle.this.b("$7643", 0);
                        Thread.sleep(6000L);
                        if (L1HardinBattle.this.a == 69) {
                            L1HardinBattle.this.b("$7567", 1);
                        } else if (L1HardinBattle.this.a == 66) {
                            L1HardinBattle.this.b("$7575", 1);
                        } else {
                            L1HardinBattle.this.b("$7646", 1);
                            L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                            l1HardinBattle.h = l1HardinBattle.h + 1;
                        }
                        if (L1HardinBattle.this.h >= 9) {
                            L1HardinBattle.this.b("$7644", 2);
                            Thread.sleep(6000L);
                            if (L1HardinBattle.this.a == 69) {
                                L1HardinBattle.this.d = true;
                                L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                                l1HardinBattle.g = l1HardinBattle.g + 1;
                                L1HardinBattle.this.b("$7568", 1);
                            } else {
                                L1HardinBattle.this.b("$7645", 1);
                            }
                        }
                        Thread.sleep(2500L);
                        L1Teleport.a(L1HardinBattle.this.i, 32718, 32849, L1HardinBattle.this.e, 5, true);
                    }
                    catch (InterruptedException interruptedException) {}
                    break;
                }
                case 7: {
                    try {
                        L1HardinBattle.this.a(L1HardinBattle.this.ao);
                        L1HardinBattle.this.a(1, L1HardinBattle.this.ao);
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7597 : $7621", 2);
                        L1HardinBattle.this.a(L1HardinBattle.this.ap);
                        L1HardinBattle.this.a(2, L1HardinBattle.this.ap);
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7597 : $7628", 2);
                        L1HardinBattle.this.a(L1HardinBattle.this.aq);
                        L1HardinBattle.this.a(3, L1HardinBattle.this.aq);
                        Thread.sleep(2000L);
                        for (L1PcInstance pc : L1HardinBattle.this.n) {
                            if (pc.fr() == L1HardinBattle.this.i.fr()) continue;
                            L1Teleport.a(pc, 32796, 32848, L1HardinBattle.this.e, 5, true);
                        }
                        Thread.sleep(2000L);
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7597 : $7635", 2);
                        ArrayList list = new ArrayList();
                        list.addAll(L1HardinBattle.this.a(new L1Location(32775, 32846, L1HardinBattle.this.e), 45107, 8));
                        list.addAll(L1HardinBattle.this.a(new L1Location(32775, 32846, L1HardinBattle.this.e), 45130, 8));
                        if (L1HardinBattle.this.a(list, 120) >= 120) {
                            L1HardinBattle.this.d();
                            return;
                        }
                        for (L1DoorInstance door : L1HardinBattle.this.p) {
                            if (door.p() != 4) continue;
                            door.f();
                        }
                        L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                        l1HardinBattle.g = l1HardinBattle.g + 1;
                        L1HardinBattle.this.f = 5;
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7647", 0);
                        Thread.sleep(5000L);
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7648", 0);
                        Thread.sleep(15000L);
                        for (L1DoorInstance door : L1HardinBattle.this.p) {
                            if (door.o() != 28) continue;
                            door.g();
                        }
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7649", 0);
                        new L1R_a(5, 5000).a();
                    }
                    catch (InterruptedException list) {}
                    break;
                }
                case 5: {
                    try {
                        L1HardinBattle.this.e();
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7650", 0);
                        Thread.sleep(4000L);
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7651", 0);
                        Thread.sleep(4000L);
                        int round = 1;
                        while (round <= 11) {
                            ArrayList list = new ArrayList();
                            Thread.sleep(1000L);
                            int[] msgid = new int[]{8708, 8709, 8710, 8704, 8711, 8712, 8713, 8706, 8714, 8715, 8716};
                            L1HardinBattle.this.a(L1HardinBattle.this.j, "$" + msgid[round - 1], 0);
                            Thread.sleep(3000L);
                            int subRound = 0;
                            while (subRound < 4) {
                                L1HardinBattle.this.a(L1HardinBattle.this.j, "$" + (8689 + subRound), 0);
                                Thread.sleep(1000L);
                                int[] mobids = new int[5];
                                int i = 0;
                                while (i < mobids.length) {
                                    mobids[i] = L1HardinBattle.this.ak[Random.a(L1HardinBattle.this.ak.length)];
                                    ++i;
                                }
                                list.addAll(L1HardinBattle.this.a(L1HardinBattle.this.c, mobids));
                                Thread.sleep(4000L);
                                ++subRound;
                            }
                            if (round == 4) {
                                Thread.sleep(1000L);
                                L1HardinBattle.this.a(null, "$8705", 0);
                                Thread.sleep(3000L);
                                list.addAll(L1HardinBattle.this.a(L1HardinBattle.this.c, 91391, 1));
                            } else if (round == 8) {
                                Thread.sleep(1000L);
                                L1HardinBattle.this.a(null, "$8707", 0);
                                Thread.sleep(3000L);
                                list.addAll(L1HardinBattle.this.a(L1HardinBattle.this.c, 91393, 1));
                            }
                            int use_time = L1HardinBattle.this.a(list, 300);
                            if (use_time >= 300) {
                                if (L1HardinBattle.this.r >= 4) {
                                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$7653", 0);
                                    L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                                    l1HardinBattle.g = l1HardinBattle.g + 10;
                                    Thread.sleep(1000L);
                                }
                                L1HardinBattle.this.a(L1HardinBattle.this.j, "$7811", 0);
                                L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7811", 0));
                                if (L1HardinBattle.this.a(list, 60) >= 60) {
                                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$7681", 0);
                                    L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7681", 0));
                                    L1HardinBattle.this.d();
                                } else {
                                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8703", 0);
                                    L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$8703", 0));
                                }
                            } else if (use_time < 120) {
                                L1HardinBattle l1HardinBattle = L1HardinBattle.this;
                                l1HardinBattle.g = l1HardinBattle.g + 100;
                            } else {
                                L1HardinBattle.this.a(L1HardinBattle.this.j, "$7652", 0);
                            }
                            ++round;
                        }
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$7654", 0);
                        Thread.sleep(5000L);
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$8717", 0);
                        L1HardinBattle.this.e();
                        Thread.sleep(2000L);
                        int[] mobids = new int[16];
                        int[] monster = L1HardinBattle.this.ak;
                        if (L1HardinBattle.this.g > 1000) {
                            L1HardinBattle.this.l = (L1NpcInstance)L1HardinBattle.this.a(L1HardinBattle.this.c, 91394, 1).get(0);
                        } else if (L1HardinBattle.this.g > 100 && L1HardinBattle.this.g < 1000) {
                            L1HardinBattle.this.l = (L1NpcInstance)L1HardinBattle.this.a(L1HardinBattle.this.c, 91390, 1).get(0);
                        } else {
                            monster = L1HardinBattle.this.al;
                        }
                        boolean isSpawnKelenis = true;
                        L1HardinBattle.this.l.b(new S_NpcChatPacket(L1HardinBattle.this.l, "$7656", 0));
                        Thread.sleep(1000L);
                        L1HardinBattle.this.l.b(new S_NpcChatPacket(L1HardinBattle.this.l, "$7657", 0));
                        if (!L1HardinBattle.this.d) {
                            L1HardinBattle.this.k = (L1NpcInstance)L1HardinBattle.this.a(new L1Location(32711, 32845, L1HardinBattle.this.e), 91396, 1).get(0);
                        } else if (L1HardinBattle.this.g % 10 == 5) {
                            L1HardinBattle.this.k = (L1NpcInstance)L1HardinBattle.this.a(new L1Location(32711, 32845, L1HardinBattle.this.e), 91395, 1).get(0);
                        } else {
                            isSpawnKelenis = false;
                        }
                        if (isSpawnKelenis) {
                            if (L1HardinBattle.this.k.z() == 91396) {
                                Thread.sleep(1500L);
                                L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7820", 0));
                                Thread.sleep(1500L);
                                L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7821", 0));
                                Thread.sleep(1500L);
                                L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7822", 0));
                                Thread.sleep(1500L);
                                L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7823", 0));
                                Thread.sleep(1500L);
                                L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7824", 0));
                            } else {
                                Thread.sleep(1500L);
                                L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7815", 0));
                                Thread.sleep(1500L);
                                L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7816", 0));
                                Thread.sleep(1500L);
                                L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7817", 0));
                                Thread.sleep(1500L);
                                L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7818", 0));
                                Thread.sleep(1500L);
                                L1HardinBattle.this.l.b(new S_NpcChatPacket(L1HardinBattle.this.l, "$7819", 0));
                                Thread.sleep(1500L);
                            }
                        }
                        int i = 0;
                        while (i < mobids.length) {
                            mobids[i] = monster[Random.a(L1HardinBattle.this.ak.length)];
                            ++i;
                        }
                        ArrayList list = L1HardinBattle.this.a(new L1Location(32706, 32836, L1HardinBattle.this.e), mobids);
                        if (L1HardinBattle.this.l != null) {
                            list.add(L1HardinBattle.this.l);
                        }
                        if (isSpawnKelenis) {
                            list.add(L1HardinBattle.this.k);
                        }
                        if (L1HardinBattle.this.a(list, 900) >= 900) {
                            return;
                        }
                        L1NpcInstance Mob = null;
                        L1HardinBattle.this.e();
                        if (L1HardinBattle.this.g >= 5) {
                            Mob = (L1NpcInstance)L1HardinBattle.this.a(new L1Location(32707, 32858, L1HardinBattle.this.e), 91443, 1).get(0);
                            Mob.b(new S_NpcChatPacket(Mob, "$7663", 0));
                            Thread.sleep(1000L);
                            Mob.b(new S_NpcChatPacket(Mob, "$7664", 0));
                            Thread.sleep(1000L);
                            Mob.b(new S_NpcChatPacket(Mob, "$7665", 0));
                            Thread.sleep(1000L);
                            L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7837", 0));
                        } else {
                            Mob = (L1NpcInstance)L1HardinBattle.this.a(new L1Location(32707, 32858, L1HardinBattle.this.e), 91444, 1).get(0);
                            Mob.b(new S_NpcChatPacket(Mob, "$7694", 0));
                            Thread.sleep(1000L);
                            Mob.b(new S_NpcChatPacket(Mob, "$7695", 0));
                            Thread.sleep(1000L);
                            Mob.b(new S_NpcChatPacket(Mob, "$7709", 0));
                            Thread.sleep(1000L);
                            L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7677", 0));
                        }
                        Thread.sleep(1000L);
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7838", 0));
                        Thread.sleep(1000L);
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7839", 0));
                        ArrayList<L1Location> loc_list = new ArrayList<L1Location>();
                        int cnt = 3;
                        for (L1DoorInstance wall : L1HardinBattle.this.o) {
                            int y;
                            int x;
                            L1Location move_loc;
                            if (cnt % 4 == 0) {
                                L1HardinBattle.this.e();
                            }
                            while (loc_list.contains(move_loc = new L1Location(x = 32702 + Random.a(10), y = 32860 + Random.a(6), L1HardinBattle.this.e))) {
                            }
                            loc_list.add(move_loc);
                            DoorTable.b().a(wall.fu());
                            DoorTable.b().a(0, L1HardinBattle.this.aj, move_loc, 0, 1, false);
                            Thread.sleep(600L);
                            ++cnt;
                        }
                        int i2 = 0;
                        while (i2 < L1HardinBattle.this.o.size()) {
                            DoorTable.b().a((L1Location)loc_list.get(i2));
                            DoorTable.b().a(0, L1HardinBattle.this.aj, ((L1DoorInstance)L1HardinBattle.this.o.get(i2)).fu(), 0, 1, false);
                            Thread.sleep(600L);
                            ++i2;
                        }
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$8718", 0);
                        int[][] point = new int[L1HardinBattle.this.n.size() - 1][2];
                        int i3 = 0;
                        while (i3 < point.length) {
                            point[i3] = L1HardinBattle.this.ar[i3];
                            L1HardinBattle.this.a(new L1Location(point[i3][0], point[i3][1], L1HardinBattle.this.e), 91439, 1);
                            ++i3;
                        }
                        L1HardinBattle.this.a(0, point);
                        L1HardinBattle.this.a(L1HardinBattle.this.j, "$8719", 0);
                        L1HardinBattle.this.a(new L1Location(32802, 32868, L1HardinBattle.this.e), 91438, 1);
                        L1HardinBattle.this.a(0, new int[][]{{32802, 32868}});
                        int[][] nArray = point;
                        int n = point.length;
                        int n2 = 0;
                        while (n2 < n) {
                            int[] p = nArray[n2];
                            L1World.a().a(new L1Location(p[0], p[1], L1HardinBattle.this.e)).a(41757, 1);
                            ++n2;
                        }
                        L1World.a().a(new L1Location(32802, 32868, L1HardinBattle.this.e)).a(41757, 1);
                        Thread.sleep(30000L);
                        L1HardinBattle.this.d();
                    }
                    catch (Exception mobids) {}
                    break;
                }
                case 10: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$7598", 0);
                    new L1R_a(11, 4000).a();
                    break;
                }
                case 11: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8693", 0);
                    new L1R_a(12, 8000).a();
                    break;
                }
                case 12: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8694", 0);
                    if (L1HardinBattle.this.a == 69) {
                        new L1R_a(20, 8000).a();
                        break;
                    }
                    new L1R_a(13, 8000).a();
                    break;
                }
                case 13: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8695", 0);
                    new L1R_a(14, 8000).a();
                    break;
                }
                case 14: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8696", 0);
                    new L1R_a(15, 8000).a();
                    break;
                }
                case 15: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8697", 0);
                    new L1R_a(16, 8000).a();
                    break;
                }
                case 16: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8698", 0);
                    new L1R_a(17, 8000).a();
                    break;
                }
                case 17: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8699", 0);
                    new L1R_a(18, 8000).a();
                    break;
                }
                case 18: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8700", 0);
                    new L1R_a(19, 8000).a();
                    break;
                }
                case 19: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8701", 0);
                    new L1R_a(20, 8000).a();
                    break;
                }
                case 20: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$8702", 0);
                    new L1R_a(21, 6000).a();
                    break;
                }
                case 21: {
                    int time = 4000;
                    if (L1HardinBattle.this.a != 69) {
                        L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep002"));
                        time = 10000;
                    }
                    new L1R_a(22, time).a();
                    break;
                }
                case 22: {
                    L1HardinBattle.this.i.a(new S_Html(L1HardinBattle.this.i.fr(), "j_ep001"));
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$7599", 0);
                    new L1R_a(23, 8000).a();
                    break;
                }
                case 23: {
                    try {
                        if (L1HardinBattle.this.a != 69) {
                            L1HardinBattle.this.a(L1HardinBattle.this.j, "$7601", 0);
                            Thread.sleep(5000L);
                            int i = 0;
                            while (i < 5) {
                                if (L1HardinBattle.this.a == 69) break;
                                L1HardinBattle.this.a(L1HardinBattle.this.j, "$7602", 0);
                                Thread.sleep(5000L);
                                ++i;
                            }
                        }
                        new L1R_a(24, 8000).a();
                    }
                    catch (InterruptedException i) {}
                    break;
                }
                case 24: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$7600", 0);
                    new L1R_a(25, 8000).a();
                    break;
                }
                case 25: {
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$7603", 0);
                    new L1R_a(26, 2000).a();
                    break;
                }
                case 26: {
                    L1Teleport.a(L1HardinBattle.this.i, 32738, 32930, L1HardinBattle.this.e, 5, true);
                    L1HardinBattle.this.a(L1HardinBattle.this.j, "$7604", 0);
                    new L1R_a(101, 1000).a();
                    new L1R_a(201, 1000).a();
                    break;
                }
                case 101: {
                    L1HardinBattle.this.a("$7605", 0);
                    new L1R_a(102, 4000).a();
                    break;
                }
                case 102: {
                    L1HardinBattle.this.a("$7606", 0);
                    new L1R_a(103, 4000).a();
                    break;
                }
                case 103: {
                    L1HardinBattle.this.j.a(32716, 32846, 6);
                    new L1R_a(104, 4000).a();
                    break;
                }
                case 104: {
                    for (L1PcInstance pc : L1HardinBattle.this.n) {
                        if (pc.fr() == L1HardinBattle.this.i.fr()) continue;
                        L1Teleport.a(pc, 32665, 32793, L1HardinBattle.this.e, 5, true);
                    }
                    L1HardinBattle.this.a("$7611", 0);
                    new L1R_a(105, 2000).a();
                    break;
                }
                case 105: {
                    L1HardinBattle.this.a("$7613", 0);
                    new L1R_a(106, 5000).a();
                    break;
                }
                case 106: {
                    L1HardinBattle.this.a("$7615", 0);
                    L1HardinBattle.this.e();
                    break;
                }
                case 201: {
                    L1HardinBattle.this.b("$7607", 0);
                    new L1R_a(202, 4000).a();
                    break;
                }
                case 202: {
                    L1HardinBattle.this.b("$7608", 0);
                    new L1R_a(203, 4000).a();
                    break;
                }
                case 203: {
                    L1HardinBattle.this.b("$7609", 0);
                    new L1R_a(204, 5000).a();
                    break;
                }
                case 204: {
                    L1HardinBattle.this.b("$7610", 0);
                    new L1R_a(205, 5000).a();
                    break;
                }
                case 205: {
                    if (L1HardinBattle.this.a == 69) {
                        L1HardinBattle.this.b("$7561", 1);
                    } else if (L1HardinBattle.this.a == 66) {
                        L1HardinBattle.this.b("$7569", 1);
                    } else {
                        L1HardinBattle.this.b("$7612", 1);
                    }
                    new L1R_a(206, 6000).a();
                    break;
                }
                case 206: {
                    L1HardinBattle.this.b("$7614", 2);
                    new L1R_a(6, 3000).a();
                    new L1R_a(7, 3000).a();
                    new L1R_a(303, 3000).a();
                    break;
                }
                case 303: {
                    try {
                        int count = 0;
                        while (L1HardinBattle.this.k.z() == 91397) {
                            L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$" + (7576 + Random.a(12)), 0));
                            if (L1HardinBattle.this.f <= 3) {
                                if (count % 12 == 0) {
                                    if (Random.a(100) <= 66) {
                                        L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), L1HardinBattle.this.an);
                                    } else {
                                        L1HardinBattle.this.a(L1HardinBattle.this.i.fu(), 45278, 3);
                                        L1HardinBattle.this.b("$7596", 0);
                                    }
                                }
                                if (count % (1 + Random.a(2)) == 0) {
                                    for (L1Object obj : L1World.a().b((L1Object)L1HardinBattle.this.k, 6)) {
                                        if (!(obj instanceof L1MonsterInstance)) continue;
                                        ((L1MonsterInstance)obj).b(new S_SkillSound(obj.fr(), 7398));
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
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7833", 0));
                        Thread.sleep(2000L);
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7834", 0));
                    }
                    catch (InterruptedException count) {}
                    break;
                }
                case 302: {
                    try {
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7827", 0));
                        Thread.sleep(1000L);
                        L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7828", 0));
                        Thread.sleep(1800L);
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7829", 0));
                        Thread.sleep(1800L);
                        L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7830", 0));
                        Thread.sleep(1000L);
                        L1HardinBattle.this.j.b(new S_NpcChatPacket(L1HardinBattle.this.j, "$7831", 0));
                        Thread.sleep(1500L);
                        L1HardinBattle.this.k.b(new S_NpcChatPacket(L1HardinBattle.this.k, "$7832", 0));
                    }
                    catch (InterruptedException count) {}
                    break;
                }
                case 304: {
                    try {
                        L1NpcInstance orc = (L1NpcInstance)L1HardinBattle.this.a(new L1Location(32729, 32917, L1HardinBattle.this.e), 91433, 1).get(0);
                        L1NpcInstance orcFighter = (L1NpcInstance)L1HardinBattle.this.a(new L1Location(32732, 32916, L1HardinBattle.this.e), 91436, 1).get(0);
                        L1NpcInstance orcArcher = (L1NpcInstance)L1HardinBattle.this.a(new L1Location(32730, 32920, L1HardinBattle.this.e), 91434, 1).get(0);
                        L1NpcInstance barusim = (L1NpcInstance)L1HardinBattle.this.a(new L1Location(32732, 32919, L1HardinBattle.this.e), 91435, 1).get(0);
                        ArrayList<L1NpcInstance> list = new ArrayList<L1NpcInstance>();
                        list.add(orc);
                        list.add(orcFighter);
                        list.add(orcArcher);
                        list.add(barusim);
                        L1HardinBattle.this.a(list, 3, 15);
                        Thread.sleep(2000L);
                        barusim.b(new S_NpcChatPacket(barusim, "$7842", 0));
                        Thread.sleep(2000L);
                        orc.b(new S_NpcChatPacket(orc, "$7848", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new S_NpcChatPacket(orcFighter, "$7854", 0));
                        Thread.sleep(2000L);
                        orc.b(new S_NpcChatPacket(orc, "$7849", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new S_NpcChatPacket(orcFighter, "$7855", 0));
                        Thread.sleep(2000L);
                        barusim.b(new S_NpcChatPacket(barusim, "$7843", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new S_NpcChatPacket(orcFighter, "$7856", 0));
                        Thread.sleep(2000L);
                        barusim.b(new S_NpcChatPacket(barusim, "$7844", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new S_NpcChatPacket(orcFighter, "$7857", 0));
                        Thread.sleep(2000L);
                        orcArcher.b(new S_NpcChatPacket(orcArcher, "$7851", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new S_NpcChatPacket(orcFighter, "$7858", 0));
                        Thread.sleep(2000L);
                        orcArcher.b(new S_NpcChatPacket(orcArcher, "$7852", 0));
                        Thread.sleep(2000L);
                        orcFighter.b(new S_NpcChatPacket(orcFighter, "$7859", 0));
                        Thread.sleep(2000L);
                        orcArcher.b(new S_NpcChatPacket(orcArcher, "$7853", 0));
                        Thread.sleep(2000L);
                        orc.b(new S_NpcChatPacket(orc, "$7850", 0));
                        Thread.sleep(2000L);
                        barusim.b(new S_NpcChatPacket(barusim, "$7845", 0));
                        Thread.sleep(4000L);
                        barusim.b(new S_NpcChatPacket(barusim, "$7846", 0));
                        Thread.sleep(4000L);
                        barusim.b(new S_NpcChatPacket(barusim, "$7847", 0));
                        Thread.sleep(4000L);
                        L1HardinBattle.this.a(list, 1, 20);
                    }
                    catch (InterruptedException orc) {}
                    break;
                }
                case 999: {
                    try {
                        try {
                            while (!L1HardinBattle.this.n.isEmpty()) {
                                for (L1PcInstance pc : L1HardinBattle.this.n) {
                                    if (pc.bE() != 0 && pc.fp() == L1HardinBattle.this.e) continue;
                                    L1HardinBattle.this.n.remove(pc);
                                    if (!pc.am()) continue;
                                    L1HardinBattle.this.d();
                                }
                                Thread.sleep(3000L);
                            }
                        }
                        catch (Exception exception) {
                            System.out.println("[\u526f\u672c\u7d50\u675f]:\u54c8\u6c40\u526f\u672c");
                            L1HardinBattle.this.b();
                            break;
                        }
                    }
                    finally {
                        System.out.println("[\u526f\u672c\u7d50\u675f]:\u54c8\u6c40\u526f\u672c");
                        L1HardinBattle.this.b();
                    }
                }
            }
        }
    }
}
