/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.g;
import ap.t;
import aq.an;
import aq.f;
import aq.u;
import at.b;
import ax.c;
import bh.d;
import bi.i;
import java.util.HashMap;
import java.util.Map;

public class e {
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 3;
    public static final int d = 4;
    public static final int e = 5;
    public static final int f = 6;
    public static final int g = 7;
    public static final int h = 8;
    private static final int i = 33169;
    private static final int j = 32778;
    private static final short k = 4;
    private static final int l = 33089;
    private static final int m = 33219;
    private static final int n = 32717;
    private static final int o = 32827;
    private static final int p = 4;
    private static final int q = 15;
    private static final int r = 32799;
    private static final int s = 32284;
    private static final int t = 4;
    private static final int u = 32750;
    private static final int v = 32850;
    private static final int w = 32250;
    private static final int x = 32350;
    private static final int y = 4;
    private static final int z = 32623;
    private static final int A = 33379;
    private static final int B = 4;
    private static final int C = 32571;
    private static final int D = 32721;
    private static final int E = 33350;
    private static final int F = 33460;
    private static final int G = 4;
    private static final int H = 29;
    private static final int I = 33631;
    private static final int J = 32678;
    private static final int K = 4;
    private static final int L = 33559;
    private static final int M = 33686;
    private static final int N = 32615;
    private static final int O = 32755;
    private static final int P = 4;
    private static final int Q = 52;
    private static final int R = 33524;
    private static final int S = 33396;
    private static final int T = 4;
    private static final int U = 33458;
    private static final int V = 33583;
    private static final int W = 33315;
    private static final int X = 33490;
    private static final int Y = 4;
    private static final int Z = 64;
    private static final int aa = 32828;
    private static final int ab = 32818;
    private static final int ac = 66;
    private static final int ad = 32755;
    private static final int ae = 32870;
    private static final int af = 32790;
    private static final int ag = 32920;
    private static final int ah = 66;
    private static final int ai = 34090;
    private static final int aj = 33260;
    private static final int ak = 4;
    private static final int al = 34007;
    private static final int am = 34162;
    private static final int an = 33172;
    private static final int ao = 33332;
    private static final int ap = 4;
    private static final int aq = 300;
    private static final int ar = 34057;
    private static final int as = 33291;
    private static final int at = 34123;
    private static final int au = 33291;
    private static final int av = 34057;
    private static final int aw = 33230;
    private static final int ax = 34123;
    private static final int ay = 33230;
    private static final int az = 33033;
    private static final int aA = 32895;
    private static final int aB = 320;
    private static final int aC = 32888;
    private static final int aD = 33070;
    private static final int aE = 32839;
    private static final int aF = 32953;
    private static final int aG = 320;
    private static final int aH = 330;
    private static final HashMap<Integer, u> aI = new HashMap();
    private static final HashMap<Integer, c> aJ;
    private static final HashMap<Integer, Integer> aK;
    private static final HashMap<Integer, u> aL;
    private static HashMap<Integer, Integer> aM;
    private static a aN;

    static {
        aI.put(1, new u(33169, 32778, 4));
        aI.put(2, new u(32799, 32284, 4));
        aI.put(3, new u(32623, 33379, 4));
        aI.put(4, new u(33631, 32678, 4));
        aI.put(5, new u(33524, 33396, 4));
        aI.put(6, new u(32828, 32818, 66));
        aI.put(7, new u(34090, 33260, 4));
        aI.put(8, new u(33033, 32895, 320));
        aJ = new HashMap();
        aJ.put(1, new c(33089, 32717, 33219, 32827, 4));
        aJ.put(2, new c(32750, 32250, 32850, 32350, 4));
        aJ.put(3, new c(32571, 33350, 32721, 33460, 4));
        aJ.put(4, new c(33559, 32615, 33686, 32755, 4));
        aJ.put(5, new c(33458, 33315, 33583, 33490, 4));
        aJ.put(6, new c(32755, 32790, 32870, 32920, 66));
        aJ.put(7, new c(34007, 33172, 34162, 33332, 4));
        aJ.put(8, new c(32888, 32839, 33070, 32953, 320));
        aK = new HashMap();
        aK.put(1, 15);
        aK.put(3, 29);
        aK.put(4, 52);
        aK.put(5, 64);
        aK.put(7, 300);
        aK.put(8, 330);
        aL = new HashMap();
        aL.put(1, new u(34057, 33291, 4));
        aL.put(2, new u(34123, 33291, 4));
        aL.put(3, new u(34057, 33230, 4));
        aL.put(4, new u(34123, 33230, 4));
        aM = new HashMap();
    }

    private e() {
    }

    private static int b(u loc) {
        for (Map.Entry<Integer, u> entry : aI.entrySet()) {
            if (!entry.getValue().equals(loc)) continue;
            return entry.getKey();
        }
        return 0;
    }

    public static int a(int locx, int locy, int i2) {
        return aq.e.b(new u(locx, locy, i2));
    }

    private static int c(u loc) {
        for (Map.Entry<Integer, c> entry : aJ.entrySet()) {
            if (!entry.getValue().a(loc)) continue;
            return entry.getKey();
        }
        for (Map.Entry<Integer, Object> entry : aK.entrySet()) {
            if (((Integer)entry.getValue()).intValue() != loc.b()) continue;
            return entry.getKey();
        }
        return 0;
    }

    public static int a(f cha) {
        return aq.e.c(cha.fu());
    }

    private static boolean a(int castleId, u loc) {
        return castleId == aq.e.c(loc);
    }

    public static boolean a(int castleId, f cha) {
        return aq.e.a(castleId, cha.fu());
    }

    public static boolean a(u loc) {
        return aq.e.c(loc) != 0;
    }

    public static boolean b(int locx, int locy, int mapid) {
        return aq.e.a(new u(locx, locy, mapid));
    }

    public static int[] a(int castleId) {
        int[] result = new int[3];
        u loc = aI.get(castleId);
        if (loc != null) {
            result[0] = loc.f();
            result[1] = loc.g();
            result[2] = loc.b();
        }
        return result;
    }

    public static int[] b(int castleId) {
        int[] gfxid = new int[]{1284, 1284};
        if (castleId == 1) {
            gfxid[0] = 10457;
            gfxid[1] = 10459;
        } else if (castleId == 2) {
            gfxid[0] = 10453;
            gfxid[1] = 10455;
        } else if (castleId == 3) {
            gfxid[0] = 10461;
            gfxid[1] = 10463;
        } else if (castleId == 4) {
            gfxid[0] = 10477;
            gfxid[1] = 10479;
        } else if (castleId == 5) {
            gfxid[0] = 10449;
            gfxid[1] = 10451;
        } else if (castleId == 6) {
            gfxid[0] = 10473;
            gfxid[1] = 10475;
        } else if (castleId == 7) {
            gfxid[0] = 10469;
            gfxid[1] = 10471;
        } else if (castleId == 8) {
            gfxid[0] = 10465;
            gfxid[1] = 10467;
        }
        return gfxid;
    }

    public static int[] c(int castleId) {
        int[] loc = new int[5];
        if (castleId == 1) {
            loc[0] = 33089;
            loc[1] = 33219;
            loc[2] = 32717;
            loc[3] = 32827;
            loc[4] = 4;
        } else if (castleId == 2) {
            loc[0] = 32750;
            loc[1] = 32850;
            loc[2] = 32250;
            loc[3] = 32350;
            loc[4] = 4;
        } else if (castleId == 3) {
            loc[0] = 32571;
            loc[1] = 32721;
            loc[2] = 33350;
            loc[3] = 33460;
            loc[4] = 4;
        } else if (castleId == 4) {
            loc[0] = 33559;
            loc[1] = 33686;
            loc[2] = 32615;
            loc[3] = 32755;
            loc[4] = 4;
        } else if (castleId == 5) {
            loc[0] = 33458;
            loc[1] = 33583;
            loc[2] = 33315;
            loc[3] = 33490;
            loc[4] = 4;
        } else if (castleId == 6) {
            loc[0] = 32755;
            loc[1] = 32870;
            loc[2] = 32790;
            loc[3] = 32920;
            loc[4] = 66;
        } else if (castleId == 7) {
            loc[0] = 34007;
            loc[1] = 34162;
            loc[2] = 33172;
            loc[3] = 33332;
            loc[4] = 4;
        } else if (castleId == 8) {
            loc[0] = 32888;
            loc[1] = 33070;
            loc[2] = 32839;
            loc[3] = 32953;
            loc[4] = 320;
        }
        return loc;
    }

    public static int[] d(int castle_id) {
        int[] loc = new int[3];
        if (castle_id == 1) {
            loc[0] = 32731;
            loc[1] = 32810;
            loc[2] = 15;
        } else if (castle_id == 2) {
            loc[0] = 32800;
            loc[1] = 32277;
            loc[2] = 4;
        } else if (castle_id == 3) {
            loc[0] = 32730;
            loc[1] = 32814;
            loc[2] = 29;
        } else if (castle_id == 4) {
            loc[0] = 32724;
            loc[1] = 32827;
            loc[2] = 52;
        } else if (castle_id == 5) {
            loc[0] = 32568;
            loc[1] = 32855;
            loc[2] = 64;
        } else if (castle_id == 6) {
            loc[0] = 32853;
            loc[1] = 32810;
            loc[2] = 66;
        } else if (castle_id == 7) {
            loc[0] = 32892;
            loc[1] = 32572;
            loc[2] = 300;
        } else if (castle_id == 8) {
            loc[0] = 32733;
            loc[1] = 32985;
            loc[2] = 330;
        }
        return loc;
    }

    public static int[] e(int castle_id) {
        int[] loc;
        if (castle_id == 1) {
            loc = aq.an.a(6);
        } else if (castle_id == 2) {
            loc = aq.an.a(4);
        } else if (castle_id == 3) {
            loc = aq.an.a(5);
        } else if (castle_id == 4) {
            loc = aq.an.a(7);
        } else if (castle_id == 5) {
            loc = aq.an.a(8);
        } else if (castle_id == 6) {
            loc = aq.an.a(9);
        } else if (castle_id == 7) {
            loc = aq.an.a(12);
        } else if (castle_id == 8) {
            int rnd = bi.i.a(3);
            loc = new int[3];
            if (rnd == 0) {
                loc[0] = 32792;
                loc[1] = 32807;
                loc[2] = 310;
            } else if (rnd == 1) {
                loc[0] = 32816;
                loc[1] = 32820;
                loc[2] = 310;
            } else if (rnd == 2) {
                loc[0] = 32823;
                loc[1] = 32797;
                loc[2] = 310;
            }
        } else {
            loc = aq.an.a(2);
        }
        return loc;
    }

    public static int a(t npc) {
        int castle_id = 0;
        int town_id = aq.an.a((f)npc);
        switch (town_id) {
            case 3: 
            case 6: {
                castle_id = 1;
                break;
            }
            case 4: {
                castle_id = 2;
                break;
            }
            case 2: 
            case 5: {
                castle_id = 3;
                break;
            }
            case 1: 
            case 7: {
                castle_id = 4;
                break;
            }
            case 8: {
                castle_id = 5;
                break;
            }
            case 9: 
            case 10: {
                castle_id = 6;
                break;
            }
            case 12: {
                castle_id = 7;
                break;
            }
            case 14: {
                castle_id = 8;
                break;
            }
            default: {
                castle_id = 1;
            }
        }
        return castle_id;
    }

    public static int b(t npc) {
        int castleId = aq.e.a(npc);
        if (castleId != 0) {
            return aM.get(castleId);
        }
        return 0;
    }

    public static void a() {
        d[] dArray = ao.g.a().b();
        int n2 = dArray.length;
        int n3 = 0;
        while (n3 < n2) {
            d castle = dArray[n3];
            aM.put(castle.a(), castle.e());
            ++n3;
        }
        if (aN == null) {
            aN = new a();
            at.c.a().a(aN);
        }
    }

    public static int[] f(int no) {
        int[] result = new int[3];
        u loc = aL.get(no);
        if (loc != null) {
            result[0] = loc.f();
            result[1] = loc.g();
            result[2] = loc.b();
        }
        return result;
    }

    private static class a
    extends b {
        private a() {
        }

        @Override
        public void a(at.a time) {
            aq.e.a();
        }
    }
}

