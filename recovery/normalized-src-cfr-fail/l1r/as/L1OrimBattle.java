/*
 * Decompiled with CFR 0.152.
 */
package l1r.as;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import l1r.ai.IdFactory;
import l1r.ao.NpcTable;
import l1r.ao.TrapSpawnTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1TrapInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.ax.L1Map;
import l1r.bd.L1TeleportTrap;
import l1r.be.S_CastleMaster;
import l1r.be.S_DoActionGFX;
import l1r.be.S_EffectLocation;
import l1r.be.S_PacketBox;
import l1r.be.S_SkillSound;
import l1r.be.S_Sound;
import l1r.be.ServerBasePacket;
import l1r.bi.Point;
import l1r.bi.Random;

public class L1OrimBattle {
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
    private L1Location F;
    private L1Location G;
    private L1NpcInstance H;
    private L1NpcInstance I;
    private L1NpcInstance J;
    private L1NpcInstance K;
    private L1NpcInstance L;
    private final int[] M = new int[12];
    private final int[] N = new int[12];
    private final ArrayList<L1NpcInstance> O = new ArrayList();
    private final CopyOnWriteArrayList<L1PcInstance> P = new CopyOnWriteArrayList();
    private final CopyOnWriteArrayList<L1Object> Q = new CopyOnWriteArrayList();
    private ScheduledExecutorService R;
    private static L1OrimBattle S;
    private final int[] T = new int[]{10638, 10639, 10640, 10641, 10642, 10643, 10644, 10645, 10646, 10647, 10648, 10649, 10666, 10667, 10668, 10669, 10670, 10671, 10672, 10673, 10674, 10675, 10676, 10677, 10678, 10679, 10680, 10681, 10682, 10683, 10684, 10685, 10686};
    private final int[] U = new int[]{91378, 91373, 91516, 91514, 91513, 91515, 91517, 91521, 91518, 91380, 91519, 91520, 91552, 91392, 91522, 91523, 91524, 91525, 91526, 91527};
    private final int[] V = new int[4];

    private void b() {
        this.a = false;
        for (L1Object obj : L1World.a().b(this.B).values()) {
            if (!(obj instanceof L1PcInstance)) continue;
            L1PcInstance pc = (L1PcInstance)obj;
            this.P.add(pc);
        }
        for (L1PcInstance pc : this.P) {
            if (!pc.am()) continue;
            pc.a(new S_CastleMaster(10, pc.fr()));
            pc.b(new S_CastleMaster(10, pc.fr()));
        }
        this.O.addAll(this.a(new L1Location(32803, 32809, this.B), 91507, 1));
        this.O.addAll(this.a(new L1Location(32792, 32809, this.B), 91508, 1));
        this.I = this.a(new L1Location(32799, 32806, this.B), 91506, 1).get(0);
        this.I.cq(32);
        this.F = new L1Location(32799, 32809, this.B);
        this.G = new L1Location(32799, 32803, this.B);
        this.R = Executors.newScheduledThreadPool(2);
    }

    private void c() {
        this.i();
        for (L1Object obj : L1World.a().b(this.B).values()) {
            L1World.a().d(obj);
        }
        this.a = true;
        this.R.shutdownNow();
    }

    public static L1OrimBattle a() {
        if (S == null) {
            S = new L1OrimBattle(9101);
        }
        return S;
    }

    private L1OrimBattle(int mapId) {
        this.B = mapId;
    }

    public void a(int stage) {
        this.b();
        new L1R_a(stage, 0).a();
        new L1R_a(6, 0).a();
    }

    private void b(int round) throws InterruptedException {
        int d_count = this.P.size() <= 1 ? 1 : this.P.size() - 1;
        ArrayList<L1NpcInstance> list_D = this.a(this.G, 91510, d_count);
        L1Location loc = this.b(this.G, 10);
        ArrayList<L1NpcInstance> list_A = this.a(loc, 91511, 1);
        int defense_time = 0;
        int attack_time = 0;
        int count = -1;
        boolean onAttackPoint = false;
        boolean onDefensePoint = false;
        while (count++ < 10) {
            Thread.sleep(1000L);
            if (!onAttackPoint) {
                for (L1NpcInstance pointA : list_A) {
                    onAttackPoint = this.a(pointA.fu(), 2);
                }
            } else if (this.b == 66) {
                this.b = 0;
                for (L1NpcInstance cannon : this.O) {
                    cannon.b(new S_DoActionGFX(cannon.fr(), 2));
                }
                if (attack_time == 0) {
                    attack_time = count;
                }
            }
            if (!onDefensePoint) {
                for (L1NpcInstance pointD : list_D) {
                    onDefensePoint = this.a(pointD.fu(), 2);
                }
            } else if (this.b == 69) {
                this.b = 0;
                for (L1PcInstance pc : this.P) {
                    if (!pc.am()) continue;
                    pc.a(new S_SkillSound(pc.fr(), 10165));
                    pc.b(new S_SkillSound(pc.fr(), 10165));
                }
                if (defense_time == 0) {
                    defense_time = count;
                }
            }
            if (attack_time * defense_time > 0) break;
        }
        list_A.addAll(list_D);
        for (L1NpcInstance point : list_A) {
            point.aa_();
        }
        if (attack_time != 0 && Random.a(4) > 0) {
            this.a("Critical HIT!", this.M[round]);
            int n = round;
            this.M[n] = this.M[n] + 1;
        }
        if (defense_time == 0) {
            int i = 0;
            while (i < 5) {
                int x = 32790 + Random.a(25);
                int y = Random.a(2) == 0 ? 32818 : 32788;
                this.a(new S_EffectLocation(x, y, 8233));
                this.a(new S_PacketBox(83, 2));
                Thread.sleep(1000L);
                ++i;
            }
        }
    }

    private boolean a(L1Location point, int radius) {
        for (L1PcInstance pc : this.P) {
            if (radius == -1 && pc.fu().e(point)) {
                L1Teleport.a(pc, 32799, 32809, this.B, 1, false);
                return true;
            }
            if (pc.fu().d(point) >= radius) continue;
            return true;
        }
        return false;
    }

    private void d() throws InterruptedException {
        this.a("$9603", 0);
        this.a(new S_PacketBox(83, 2));
        this.a(new S_Sound(82));
        int[] mobids = new int[7];
        int i = 0;
        while (i < 7) {
            mobids[i] = this.U[Random.a(5)];
            ++i;
        }
        L1Location loc = new L1Location(32797, 32803, this.B);
        ArrayList<L1NpcInstance> list = this.a(loc, mobids);
        int max_time = 35;
        int use_time = this.a(list, 35000);
        this.C = (35 - use_time) / 5;
        Thread.sleep(3000L);
    }

    private void e() throws InterruptedException {
        int rnd;
        int i;
        this.a("$9548", 0);
        int n = i = Random.a(4);
        this.V[n] = this.V[n] + 1;
        Thread.sleep(5000L);
        ArrayList<L1NpcInstance> gfxNpcList = new ArrayList();
        if (i == 0) {
            this.a(new S_EffectLocation(new L1Location(32803, 32788, this.B), 8142));
            this.a("$9541", 0);
        } else if (i == 1) {
            rnd = Random.a(4) + 6;
            gfxNpcList = this.a(this.G, 91540, rnd);
            this.a(gfxNpcList, -1, 40);
            this.a(gfxNpcList, 0);
            this.a("$9542", 0);
        } else if (i == 2) {
            rnd = Random.a(4) + 6;
            gfxNpcList = this.a(this.G, 91539, rnd);
            this.a(gfxNpcList, -1, 40);
            this.a(gfxNpcList, 0);
            this.a("$9543", 0);
        } else if (i == 3) {
            this.a(new S_EffectLocation(new L1Location(32800, 32794, this.B), 8241));
            this.a("$9544", 0);
        }
        Thread.sleep(5000L);
        if (this.V[i] == 3) {
            if (i == 1) {
                ArrayList<L1NpcInstance> list = this.a(this.G, 91540, 3);
                this.a("$9549", 1);
                this.a(list, 15000);
            } else if (i == 2) {
                ArrayList<L1NpcInstance> list = this.a(this.G, 91539, 3);
                this.a("$9549", 1);
                this.a(list, 15000);
            } else if (i == 3) {
                ArrayList<L1NpcInstance> list = this.a(new L1Location(32800, 32794, this.B), 91538, 1);
                list.addAll(this.a(new L1Location(32800, 32795, this.B), 91548, 1));
                list.addAll(this.a(new L1Location(32800, 32796, this.B), 91549, 1));
                this.a("$9558", 1);
                Thread.sleep(3000L);
                this.a("$10720", 1);
                int time = this.a(list, 60000);
                if (time < 60) {
                    this.a(new L1Location(32798, 32807, this.B), 91497, 1);
                }
            }
        }
        Thread.sleep(3000L);
        this.a("$" + this.T[Math.min(this.D / 100, 33)], 1);
        Thread.sleep(3000L);
    }

    private void c(int round) throws InterruptedException {
        L1Location loc = new L1Location(32794, 32825, this.B);
        int typeA = 0;
        int typeB = 0;
        int typeC = 0;
        int i = 0;
        while (i < 12) {
            if (i < 4) {
                typeA += this.M[i];
            } else if (i >= 4 && 7 >= i) {
                typeB += this.M[i];
            } else if (i >= 8 && 10 >= i) {
                typeC += this.M[i];
            }
            ++i;
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
                this.a(91509, this.F, new L1Location(32741, 32855, this.B));
                this.a(91509, new L1Location(32741, 32861, this.B), this.F);
            } else if (shipid == 91499) {
                this.a("$9552", 0);
                Thread.sleep(2000L);
                this.a("$9559", 1);
                this.a(91509, this.F, new L1Location(32741, 32855, this.B));
                this.a(91509, new L1Location(32741, 32861, this.B), this.F);
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
                    this.a(new L1Location(32671, 32802, this.B), npcId, 1);
                    this.a(91509, this.F, new L1Location(32677, 32795, this.B));
                    this.a(91509, new L1Location(32677, 32800, this.B), this.F);
                } else if (round == 7 && typeB >= 12) {
                    int npcId = 0;
                    if (this.D <= 300) {
                        npcId = 91537;
                    } else if (this.D <= 600) {
                        npcId = 91533;
                    } else if (this.D > 600) {
                        npcId = 91535;
                    }
                    this.a(new L1Location(32671, 32866, this.B), npcId, 1);
                    this.a(91509, this.F, new L1Location(32677, 32859, this.B));
                    this.a(91509, new L1Location(32677, 32864, this.B), this.F);
                }
            }
        }
        Thread.sleep(6000L);
    }

    private void d(int round) throws InterruptedException {
        int subRound = 0;
        while (subRound < 3) {
            ArrayList<L1NpcInstance> list = this.f(round);
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
            int n = this.N[round] = time == 45 ? -1 : 25 / time;
            if (time == 45) {
                this.a("$" + (9563 + subRound), 1);
            } else {
                this.a("$9560", 2);
            }
            Thread.sleep(3000L);
            ++subRound;
        }
        this.a("$9608", 2);
        this.a(new S_PacketBox(83, 4));
        ArrayList<L1NpcInstance> list = this.a(new L1Location(32735, 32802, this.B), 91512, 3);
        Collections.shuffle(this.P);
        Iterator<L1Object> iterator = this.P.iterator();
        if (iterator.hasNext()) {
            L1PcInstance pc = iterator.next();
            L1Location temploc = new L1Location(pc.fs(), pc.ft(), pc.fp());
            Thread.sleep(3000L);
            L1Teleport.a(pc, 32737, 32800, this.B, 1, true);
            this.a(list, 6000);
            this.a("$9606", 1);
            Thread.sleep(3000L);
            L1Teleport.a(pc, temploc.f(), temploc.g(), temploc.b(), 4, true);
        }
        for (L1Object obj : this.Q) {
            if (!(obj instanceof L1NpcInstance) || obj.fu().f(this.F)) continue;
            this.a(obj.fu(), -1);
        }
        this.i();
        if (this.H != null) {
            this.a(this.H, 4, 10);
            this.H.aa_();
        }
    }

    private void e(int maxTimeMill) throws InterruptedException {
        ArrayList<L1NpcInstance> list = this.f(11);
        int[] boss = this.D > 3000 ? new int[]{91550, 91530, 91531, 91532} : (this.D < 1200 ? new int[]{91528} : new int[]{91529, 91530, 91531, 91532});
        list.addAll(this.a(this.G, boss));
        int count = -1;
        while (count++ < maxTimeMill / 1000 && this.E < 10) {
            for (L1NpcInstance mob : list) {
                if (!mob.eX()) break;
            }
            if (count % 30 == 0) {
                this.g();
            }
            Thread.sleep(1000L);
        }
        ArrayList<L1NpcInstance> mimi = this.a(this.G, 91553, this.P.size() * 2);
        this.a(mimi, 15000);
    }

    private void f() {
        int[] ship_status = new int[]{32, 32, 33, 33, 34, 34, 35, 35, 36, 36};
        this.I.cq(ship_status[this.E]);
        this.I.b(new S_DoActionGFX(this.I.fr(), this.I.eY()));
        this.a(new S_PacketBox(83, 2));
    }

    private void g() throws InterruptedException {
        ++this.E;
        this.a(new S_PacketBox(83, 2));
        L1NpcInstance fire = this.a(this.G, 91544, 3).get(0);
        fire.b(new S_SkillSound(fire.fr(), 762));
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

    private void a(L1NpcInstance npc, int dir, int range) throws InterruptedException {
        int i = 0;
        while (i < range) {
            npc.g(dir);
            Thread.sleep(1000L);
            ++i;
        }
    }

    private void a(ArrayList<L1NpcInstance> list, int dir, int range) throws InterruptedException {
        int i = 0;
        while (i < range) {
            for (L1NpcInstance npc : list) {
                if (i == 0 && npc.fb() != dir) {
                    npc.ct(Random.a(8));
                }
                npc.g(npc.fb());
            }
            Thread.sleep(100L);
            ++i;
        }
    }

    private L1TrapInstance a(int npcid, L1Location spawnLoc, L1Location teleLoc) {
        int id = IdFactory.a().c();
        L1TeleportTrap trap = new L1TeleportTrap(id, 0, teleLoc);
        L1TrapInstance tp = new L1TrapInstance(trap.a(), trap, spawnLoc, new Point(), 2);
        TrapSpawnTable.a().a(tp);
        this.Q.addAll(this.a(spawnLoc, npcid, 1));
        this.Q.add(tp);
        return tp;
    }

    private ArrayList<L1NpcInstance> f(int round) {
        int i = 0;
        while (i < round) {
            if (this.C > this.U.length * 2 / 3) break;
            this.C += this.N[i];
            ++i;
        }
        this.C = Math.max(0, this.C);
        this.C = Math.min(this.C, this.U.length - 1);
        int[] mobids = new int[7];
        int i2 = 0;
        while (i2 < 7) {
            mobids[i2] = this.U[this.C + Random.a(this.U.length - this.C)];
            ++i2;
        }
        return this.a(this.G, mobids);
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
            L1NpcInstance npc;
            int npcid = nArray[n2];
            if (mobsID.length > 1 && npcid != 91512) {
                loc = this.b(loc, 10);
            }
            if ((npc = NpcTable.a().b(npcid)) instanceof L1MonsterInstance) {
                this.a(new S_EffectLocation(loc.f(), loc.g(), 11509));
            }
            npc.cF(IdFactory.a().c());
            npc.cE((short)loc.b());
            npc.ct(5);
            npc.cG(loc.f());
            npc.cH(loc.g());
            L1World.a().a(npc);
            L1World.a().c(npc);
            mob_list.add(npc);
            ++n2;
        }
        return mob_list;
    }

    private int a(ArrayList<L1NpcInstance> list, int maxTimeMill) throws InterruptedException {
        int count = -1;
        while (count++ < maxTimeMill / 1000) {
            boolean isAllDeath = false;
            for (L1NpcInstance mob : list) {
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

    private void a(ArrayList<L1NpcInstance> list) {
        for (L1NpcInstance npc : list) {
            if (npc.eX()) {
                this.D += npc.ev() / 5;
            }
            npc.aa_();
        }
        for (L1PcInstance pc : this.P) {
            pc.a(new S_PacketBox(84, this.D > 0 ? 4 : 3, "" + this.D));
        }
    }

    private void a(String s, int cases) {
        String[] colors = new String[]{"", "\\f=", "\\f3"};
        for (L1PcInstance pc : this.P) {
            pc.a(new S_PacketBox(84, 2, String.valueOf(colors[cases]) + s));
        }
    }

    private void a(ServerBasePacket serverbasepacket) {
        for (L1PcInstance pc : this.P) {
            pc.a(serverbasepacket);
        }
    }

    private void h() {
        for (L1PcInstance pc : this.P) {
            if (pc.am()) {
                pc.a(new S_CastleMaster(10, 0));
                pc.b(new S_CastleMaster(10, 0));
            }
            int locx = 32587 + Random.a(4);
            int locy = 32941 + Random.a(4);
            L1Teleport.a(pc, locx, locy, 0, 5, true);
        }
    }

    private void i() {
        for (L1Object obj : this.Q) {
            if (obj instanceof L1TrapInstance) {
                TrapSpawnTable.a().b((L1TrapInstance)obj);
            } else if (obj instanceof L1NpcInstance) {
                ((L1NpcInstance)obj).aa_();
            }
            this.Q.remove(obj);
        }
    }

    private L1Location b(L1Location baseLocation, int max) {
        L1Location newLocation = new L1Location();
        int newX = 0;
        int newY = 0;
        int baseX = baseLocation.f();
        int baseY = baseLocation.g();
        L1Map map = baseLocation.a();
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
            newX = locX1 + Random.a(diffX + 1);
            newY = locY1 + Random.a(diffY + 1);
            newLocation.a(newX, newY);
        } while (!map.b(newX, newY) || !map.c(newX, newY));
        return newLocation;
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
            L1OrimBattle.this.R.schedule(this, (long)this.a, TimeUnit.MILLISECONDS);
        }

        @Override
        public void run() {
            switch (this.b) {
                case 5: {
                    try {
                        L1OrimBattle.this.a("$9540", 0);
                        int round = 0;
                        while (round < 12) {
                            Thread.sleep(3000L);
                            if (round == 0) {
                                L1OrimBattle.this.d();
                            }
                            L1OrimBattle.this.e();
                            if (round == 11) {
                                L1OrimBattle.this.a("$9556", 0);
                            } else if ((round + 1) % 4 == 0) {
                                L1OrimBattle.this.a("$9554", 0);
                            } else {
                                L1OrimBattle.this.a("$9550", 0);
                            }
                            Thread.sleep(3000L);
                            if (round != 11) {
                                L1OrimBattle.this.a("$9551", 0);
                            }
                            L1OrimBattle.this.c(round);
                            L1OrimBattle.this.a("$" + (9609 + round), 1);
                            Thread.sleep(3000L);
                            L1OrimBattle.this.a(new S_PacketBox(83, 2));
                            L1OrimBattle.this.a(new S_Sound(82));
                            Thread.sleep(3000L);
                            if (round != 11) {
                                L1OrimBattle.this.d(round);
                            } else {
                                L1OrimBattle.this.e(300000);
                                new L1R_a(100, 0).a();
                            }
                            ++round;
                        }
                    }
                    catch (InterruptedException round) {}
                    break;
                }
                case 10: {
                    new L1R_a(11, 5000).a();
                    break;
                }
                case 11: {
                    L1OrimBattle.this.a("$9529", 0);
                    new L1R_a(12, 5000).a();
                    break;
                }
                case 12: {
                    L1OrimBattle.this.a("$9530", 0);
                    new L1R_a(13, 5000).a();
                    break;
                }
                case 13: {
                    if (L1OrimBattle.this.b == 68) {
                        L1OrimBattle.this.b = 0;
                        new L1R_a(5, 5000).a();
                        break;
                    }
                    L1OrimBattle.this.a("$9531", 0);
                    new L1R_a(14, 5000).a();
                    break;
                }
                case 14: {
                    L1OrimBattle.this.a("$9532", 0);
                    new L1R_a(15, 5000).a();
                    break;
                }
                case 15: {
                    L1OrimBattle.this.a("$9533", 0);
                    new L1R_a(16, 5000).a();
                    break;
                }
                case 16: {
                    L1OrimBattle.this.a("$9534", 0);
                    L1Location loc = L1OrimBattle.this.b(L1OrimBattle.this.G, 10);
                    L1OrimBattle.this.J = (L1NpcInstance)L1OrimBattle.this.a(loc, 91511, 1).get(0);
                    new L1R_a(17, 5000).a();
                    break;
                }
                case 17: {
                    L1OrimBattle.this.a("$9535", 0);
                    new L1R_a(18, 5000).a();
                    break;
                }
                case 18: {
                    L1OrimBattle.this.a("$9536", 0);
                    new L1R_a(19, 5000).a();
                    break;
                }
                case 19: {
                    if (L1OrimBattle.this.b == 66 && L1OrimBattle.this.a(L1OrimBattle.this.J.fu(), 2)) {
                        L1OrimBattle.this.b = 0;
                        L1OrimBattle.this.J.aa_();
                        new L1R_a(21, 5000).a();
                        break;
                    }
                    L1OrimBattle.this.a("$9545", 0);
                    new L1R_a(20, 5000).a();
                    break;
                }
                case 20: {
                    if (L1OrimBattle.this.b == 66 && L1OrimBattle.this.a(L1OrimBattle.this.J.fu(), 2)) {
                        L1OrimBattle.this.b = 0;
                    } else {
                        L1OrimBattle.this.a("$9546", 0);
                    }
                    L1OrimBattle.this.J.aa_();
                    new L1R_a(21, 5000).a();
                    break;
                }
                case 21: {
                    L1OrimBattle.this.a("$9537", 0);
                    new L1R_a(22, 8000).a();
                    break;
                }
                case 22: {
                    L1OrimBattle.this.a("$9538", 0);
                    L1OrimBattle.this.K = (L1NpcInstance)L1OrimBattle.this.a(L1OrimBattle.this.G, 91510, 2).get(0);
                    L1OrimBattle.this.L = (L1NpcInstance)L1OrimBattle.this.a(L1OrimBattle.this.G, 91510, 2).get(1);
                    new L1R_a(23, 5000).a();
                    break;
                }
                case 23: {
                    L1OrimBattle.this.a("$9539", 0);
                    new L1R_a(24, 5000).a();
                    break;
                }
                case 24: {
                    if (L1OrimBattle.this.b == 69 && L1OrimBattle.this.a(L1OrimBattle.this.K.fu(), 2) && L1OrimBattle.this.a(L1OrimBattle.this.L.fu(), 2)) {
                        L1OrimBattle.this.b = 0;
                        L1OrimBattle.this.K.aa_();
                        L1OrimBattle.this.L.aa_();
                        new L1R_a(5, 5000).a();
                        break;
                    }
                    L1OrimBattle.this.a("$9545", 0);
                    new L1R_a(25, 5000).a();
                    break;
                }
                case 25: {
                    if (L1OrimBattle.this.b == 69 && L1OrimBattle.this.a(L1OrimBattle.this.K.fu(), 2) && L1OrimBattle.this.a(L1OrimBattle.this.L.fu(), 2)) {
                        L1OrimBattle.this.b = 0;
                    } else {
                        L1OrimBattle.this.a("$9546", 0);
                    }
                    L1OrimBattle.this.K.aa_();
                    L1OrimBattle.this.L.aa_();
                    new L1R_a(5, 8000).a();
                    break;
                }
                case 100: {
                    L1OrimBattle.this.a("$9579", 0);
                    new L1R_a(101, 5000).a();
                    break;
                }
                case 101: {
                    L1OrimBattle.this.a("$9580", 0);
                    new L1R_a(102, 5000).a();
                    break;
                }
                case 102: {
                    L1OrimBattle.this.a("$9581", 0);
                    new L1R_a(103, 5000).a();
                    break;
                }
                case 103: {
                    L1OrimBattle.this.a("$9582", 0);
                    new L1R_a(104, 5000).a();
                    break;
                }
                case 104: {
                    L1OrimBattle.this.a("$9583", 0);
                    new L1R_a(105, 5000).a();
                    break;
                }
                case 105: {
                    L1OrimBattle.this.a("$9584", 0);
                    new L1R_a(106, 5000).a();
                    break;
                }
                case 106: {
                    L1OrimBattle.this.h();
                    break;
                }
                case 6: {
                    try {
                        try {
                            while (!L1OrimBattle.this.P.isEmpty()) {
                                for (L1PcInstance pc : L1OrimBattle.this.P) {
                                    if (pc.bE() != 0 && pc.fp() == L1OrimBattle.this.B) continue;
                                    L1OrimBattle.this.P.remove(pc);
                                }
                                Thread.sleep(3000L);
                            }
                        }
                        catch (Exception exception) {
                            System.out.println("[\u526f\u672c\u7d50\u675f]:\u6d77\u6230\u526f\u672c");
                            L1OrimBattle.this.c();
                            break;
                        }
                    }
                    finally {
                        System.out.println("[\u526f\u672c\u7d50\u675f]:\u6d77\u6230\u526f\u672c");
                        L1OrimBattle.this.c();
                    }
                }
            }
        }
    }
}
