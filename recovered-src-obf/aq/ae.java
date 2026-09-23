/*
 * Decompiled with CFR 0.152.
 */
package aq;

import am.c;
import ao.ay;
import ap.u;
import aq.f;
import be.cf;
import be.cm;
import be.ds;
import be.s;
import be.z;
import java.util.HashMap;

public class ae {
    private static final int g = 1;
    private static final int h = 2;
    private static final int i = 4;
    private static final int j = 8;
    private static final int k = 16;
    private static final int l = 32;
    private static final int m = 64;
    private static final int n = 128;
    private static final int o = 256;
    private static final int p = 512;
    private static final int q = 1024;
    private static final int r = 1;
    private static final int s = 2;
    private static final int t = 4;
    private static final int u = 8;
    private static final int v = 16;
    private static final int w = 32;
    private static final int x = 64;
    private static final int y = 128;
    private static final int z = 256;
    private static final int A = 512;
    private static final int B = 1024;
    private static final int C = 2048;
    private static final int D = 4096;
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 4;
    public static final int d = 8;
    public static final int e = 0;
    private static final HashMap<Integer, Integer> E = new HashMap();
    private static final HashMap<Integer, Integer> F;
    private final int G;
    private final int H;
    private final int I;
    private final int J;
    private final boolean K;
    private final int L;
    public static int[] f;

    static {
        E.put(1, 2);
        E.put(7, 1);
        E.put(257, 4);
        E.put(259, 256);
        E.put(261, 16);
        E.put(2, 8);
        E.put(6, 32);
        E.put(9, 256);
        E.put(264, 128);
        E.put(260, 64);
        E.put(3, 256);
        E.put(5, 16);
        E.put(258, 8);
        E.put(262, 32);
        E.put(8, 512);
        E.put(266, 1024);
        E.put(264, 512);
        F = new HashMap();
        F.put(1, 1);
        F.put(2, 16);
        F.put(3, 8);
        F.put(4, 32);
        F.put(7, 256);
        F.put(6, 1024);
        F.put(8, 128);
        F.put(11, 2);
        F.put(9, 512);
        F.put(12, 64);
        F.put(13, 4);
        F.put(10, 2048);
        F.put(5, 4096);
        f = new int[]{29, 945, 947, 979, 1037, 1039, 3860, 3861, 3862, 3863, 3864, 3865, 3904, 3906, 95, 146, 2374, 2376, 2377, 2378, 3866, 3867, 3868, 3869, 3870, 3871, 3872, 3873, 3874, 3875, 3876};
    }

    public ae(int polyId, int minLevel, int weaponEquipFlg, int armorEquipFlg, boolean canUseSkill, int causeFlg) {
        this.G = polyId;
        this.H = minLevel;
        this.I = weaponEquipFlg;
        this.J = armorEquipFlg;
        this.K = canUseSkill;
        this.L = causeFlg;
    }

    public int a() {
        return this.G;
    }

    public int b() {
        return this.H;
    }

    public int c() {
        return this.I;
    }

    public int d() {
        return this.J;
    }

    public boolean e() {
        return this.K;
    }

    public int f() {
        return this.L;
    }

    public static boolean a(u pc, String polyName, int timeSecs) {
        if (polyName.equals("") && pc.fe() != 6034 && pc.fe() != 6035) {
            ae.b(pc);
            return true;
        }
        ae poly = ay.a().a(polyName);
        if (poly == null) {
            pc.a(new ds(181));
            return false;
        }
        if (pc.ev() < poly.b() && !pc.l()) {
            pc.a(new ds(181));
            return false;
        }
        pc.setActivePolyMorphRule(poly);
        return ae.a(pc, poly.a(), timeSecs, 1);
    }

    public static boolean a(f cha, int polyId, int timeSecs, int cause) {
        if (cha == null || cha.eX()) {
            return false;
        }
        if (cha instanceof u) {
            u pc = (u)cha;
            ae activeRule = pc.getActivePolyMorphRule();
            if (activeRule == null || activeRule.a() != polyId) {
                activeRule = ay.a().a(polyId);
                pc.setActivePolyMorphRule(activeRule);
            }
            if (!pc.fq().r()) {
                pc.a(new ds(1170));
                return false;
            }
            if (pc.fe() == 6034 || pc.fe() == 6035 || !ae.c(activeRule, cause)) {
                pc.a(new ds(181));
                return false;
            }
            pc.bz(1006);
            pc.bz(1031);
            pc.bz(1037);
            pc.bA(67);
            pc.j(67, timeSecs * 1000);
            if (pc.fe() != polyId) {
                pc.cw(polyId);
                pc.a(new s(pc.fr(), polyId, pc.k()));
                if (!pc.aA()) {
                    if (pc.ff()) {
                        pc.c(new s(pc.fr(), polyId, pc.k()));
                    } else {
                        pc.b(new s(pc.fr(), polyId, pc.k()));
                    }
                }
            }
            pc.j().l(polyId);
            if (timeSecs > 0) {
                pc.a(new cm(35, timeSecs));
            }
        } else if (cha instanceof ap.s) {
            ap.s mob = (ap.s)cha;
            mob.bA(67);
            mob.j(67, timeSecs * 1000);
            if (mob.fe() != polyId) {
                mob.cw(polyId);
                int npcStatus = 0;
                if (am.c.a().b(polyId, 21)) {
                    npcStatus = 20;
                    mob.x(10);
                    mob.b_(66);
                } else if (am.c.a().b(polyId, 25)) {
                    npcStatus = 24;
                    mob.x(2);
                    mob.b_(0);
                } else if (am.c.a().b(polyId, 17)) {
                    npcStatus = 0;
                    mob.x(2);
                    mob.b_(0);
                } else if (am.c.a().b(polyId, 5)) {
                    npcStatus = 4;
                    mob.x(1);
                    mob.b_(0);
                } else {
                    npcStatus = 0;
                    mob.x(1);
                    mob.b_(0);
                }
                mob.cq(npcStatus);
                mob.m(am.c.a().a(polyId, mob.eY()));
                mob.n(am.c.a().a(polyId, mob.eY() + 1));
                mob.b(new cf(mob.fr(), polyId, mob.fa(), mob.eY()));
            }
        }
        return true;
    }

    public static void a(f cha, int polyIndex) {
        if (cha == null || cha.eX()) {
            return;
        }
        if (cha instanceof u) {
            u pc = (u)cha;
            int[] PolyList = new int[]{11479, 11427, 10047, 9688, 11322, 10069, 10034, 10032};
            pc.setActivePolyMorphRule(ay.a().a(PolyList[polyIndex - 1]));
            if (pc.fe() != PolyList[polyIndex - 1]) {
                pc.cw(PolyList[polyIndex - 1]);
                pc.a(new s(pc.fr(), PolyList[polyIndex - 1], 70));
                if (!pc.aA()) {
                    if (pc.ff()) {
                        pc.c(new s(pc.fr(), PolyList[polyIndex - 1], 70));
                    } else {
                        pc.b(new s(pc.fr(), PolyList[polyIndex - 1], 70));
                    }
                }
                pc.j().l(PolyList[polyIndex - 1]);
            }
            pc.a(new z(pc, 70));
            pc.b(new z(pc, 70));
        }
    }

    public static void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.setActivePolyMorphRule(null);
            int classId = pc.aB();
            pc.cw(classId);
            if (!pc.eX()) {
                pc.a(new s(pc.fr(), classId, pc.k()));
                pc.b(new s(pc.fr(), classId, pc.k()));
                pc.a(new z(pc, pc.k()));
                pc.b(new z(pc, pc.k()));
            }
        }
    }

    public static void b(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.setActivePolyMorphRule(null);
            int classId = pc.aB();
            int oldGfxid = pc.fe();
            pc.cw(classId);
            if (!pc.eX()) {
                pc.a(new s(pc.fr(), classId, pc.k()));
                pc.b(new s(pc.fr(), classId, pc.k()));
                pc.a(new z(pc));
                pc.b(new z(pc));
            }
        } else if (cha instanceof ap.s) {
            ap.s mob = (ap.s)cha;
            int gfxId = mob.G();
            mob.cw(gfxId);
            mob.cq(am.c.a().a(mob));
            mob.x(-1);
            mob.b_(0);
            mob.m(am.c.a().a(gfxId, mob.eY()));
            mob.n(am.c.a().a(gfxId, mob.eY() + 1));
            mob.b(new cf(mob.fr(), gfxId, mob.fa(), mob.eY()));
        }
    }

    public static boolean a(u pc, int weaponType) {
        ae poly = pc.getActivePolyMorphRule();
        if (poly == null || poly.a() != pc.fe()) {
            poly = ay.a().a(pc.fe());
        }
        Integer flg = E.get(weaponType);
        return poly == null || flg == null || (poly.c() & flg) != 0;
    }

    public static boolean a(int polyId, int weaponType) {
        ae poly = ay.a().a(polyId);
        if (poly == null) {
            return true;
        }
        Integer flg = E.get(weaponType);
        if (flg != null) {
            return (poly.c() & flg) != 0;
        }
        return true;
    }

    public static boolean b(u pc, int armorType) {
        ae poly = pc.getActivePolyMorphRule();
        if (poly == null || poly.a() != pc.fe()) {
            poly = ay.a().a(pc.fe());
        }
        Integer flg = F.get(armorType);
        return poly == null || flg == null || (poly.d() & flg) != 0;
    }

    public static boolean b(int polyId, int armorType) {
        ae poly = ay.a().a(polyId);
        if (poly == null) {
            return true;
        }
        Integer flg = F.get(armorType);
        if (flg != null) {
            return (poly.d() & flg) != 0;
        }
        return true;
    }

    private static boolean c(ae poly, int cause) {
        if (poly == null || cause == 0) {
            return true;
        }
        return (poly.f() & cause) != 0;
    }
}

