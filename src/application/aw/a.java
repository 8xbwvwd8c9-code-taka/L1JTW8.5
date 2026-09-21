/*
 * Decompiled with CFR 0.152.
 */
package aw;

import ao.aa;
import ao.ah;
import ap.e;
import ap.q;
import ap.u;
import aq.aq;
import be.ds;
import bi.i;

public class a {
    public static void a(u pc, q l1iteminstance, q select) {
        int i2;
        int n2;
        int n3;
        int[] nArray;
        if (select == null) {
            pc.a(new ds(156));
            return;
        }
        if (select.a().aP() != 17 && select.a().aP() != 22) {
            pc.a(new ds(2477));
            return;
        }
        if (select.a().aP() != 22) {
            pc.a(new ds(79));
            return;
        }
        if (pc.O(select.fr())) {
            pc.a(new ds(1181));
            return;
        }
        int rnd = i.a(100) + 1;
        int success_rate = 55;
        int failure_rate = 10;
        int createID = 0;
        if (rnd >= 45) {
            int[] highest;
            nArray = highest = new int[]{47142, 47148, 47154, 47160, 47166, 47172, 47178, 47184, 47190, 640430, 640436};
            n3 = highest.length;
            n2 = 0;
            while (n2 < n3) {
                i2 = nArray[n2];
                if (select.N() == i2) {
                    pc.a(new ds(79));
                    return;
                }
                ++n2;
            }
            createID = select.N() + 1;
            pc.a(new ds(403, select.b()));
        } else if (rnd <= 10) {
            int[] lowest = new int[]{47137, 47143, 47149, 47155, 47161, 47167, 47173, 47179, 47185, 640425, 640431};
            nArray = lowest;
            n3 = lowest.length;
            n2 = 0;
            while (n2 < n3) {
                i2 = nArray[n2];
                if (select.N() == i2) {
                    pc.a(new ds(79));
                    return;
                }
                ++n2;
            }
            createID = select.N() - 1;
            pc.a(new ds(403, select.b()));
        } else {
            pc.a(new ds(79));
        }
        if (createID > 0) {
            ah.a(pc, createID, 1);
            for (e doll : pc.el().values()) {
                if (doll.f() != select.fr()) continue;
                doll.e();
            }
            pc.j().f(select);
        }
        pc.j().b(l1iteminstance, 1);
    }

    private static boolean a(int scrollID, int selectID) {
        int[] scrollid = new int[]{49311, 40660, 40128, 49312, 40127};
        int[][] specialArmorWeaponList = new int[][]{{311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321}, {246, 247, 248, 249}, {36, 183, 250, 251, 252, 253, 254, 255}, {20028, 20082, 20126, 20173, 20206, 20232, 21138, 21184, 21185, 21186, 21187, 21188, 21189, 21190, 21191, 21192, 21193, 21194, 21195}, {20161, 21035, 21038}};
        int i2 = 0;
        while (i2 < scrollid.length) {
            int[] nArray = specialArmorWeaponList[i2];
            int n2 = nArray.length;
            int n3 = 0;
            while (n3 < n2) {
                int id = nArray[n3];
                if (selectID == id) {
                    return scrollID == scrollid[i2];
                }
                ++n3;
            }
            if (scrollID == scrollid[i2]) {
                return false;
            }
            ++i2;
        }
        return true;
    }

    public static void b(u pc, q item, q select) {
        if (select == null) {
            pc.a(new ds(156));
            return;
        }
        int itemId = item.N();
        int safe_enchant = select.a().x();
        if (!select.g() || safe_enchant < 0 || select.F() >= 128 || !a.a(itemId, select.N())) {
            pc.a(new ds(79));
            return;
        }
        if (itemId == 49311 && select.G() >= safe_enchant) {
            pc.a(new ds(1453));
            return;
        }
        int level = select.G();
        if (item.F() == 2) {
            if (level < -6) {
                a.a(pc, select);
            } else {
                a.a(pc, select, -1);
            }
        } else if (level < safe_enchant) {
            a.a(pc, select, a.a(item.F() == 0, select));
        } else {
            int rnd = i.a(100) + 1;
            int chance = (100 + 3 * l1j.server.a.G) / (level >= 10 ? 6 : 3);
            if (itemId == 640142) {
                chance = (int)((double)chance * 2.5);
            }
            if (rnd < chance) {
                a.a(pc, select, a.a(item.F() == 0, select));
            } else if (level >= 9 && rnd < chance * 2 || itemId >= 640370 && itemId <= 640372) {
                pc.a(new ds(160, select.s(), "$245", "$248"));
            } else {
                a.a(pc, select);
            }
        }
        pc.j().b(item, 1);
    }

    public static void c(u pc, q item, q select) {
        if (select == null) {
            pc.a(new ds(156));
            return;
        }
        int itemId = item.N();
        int safe_enchant = select.a().x();
        if (!select.h() || safe_enchant < 0 || select.F() >= 128 || !a.a(itemId, select.N())) {
            pc.a(new ds(79));
            return;
        }
        if (select.i() || select.j()) {
            pc.a(new ds(79));
            return;
        }
        if (itemId == 49312 && select.G() >= safe_enchant) {
            pc.a(new ds(1453));
            return;
        }
        int level = select.G();
        if (item.F() == 2) {
            if (level < -4) {
                a.a(pc, select);
            } else {
                a.a(pc, select, -1);
            }
        } else if (level < safe_enchant) {
            a.a(pc, select, a.a(item.F() == 0, select));
        } else {
            int rnd = i.a(100) + 1;
            int rank = level + (safe_enchant == 0 ? 2 : 0);
            int chance = (100 + rank * l1j.server.a.H) / rank * (level >= 9 ? 2 : 1);
            if (itemId == 640141) {
                chance = (int)((double)chance * 2.5);
            }
            if (rnd < chance) {
                a.a(pc, select, a.a(item.F() == 0, select));
            } else if (level >= 9 && rnd < chance * 2) {
                pc.a(new ds(160, select.s(), "$252", "$248"));
            } else {
                a.a(pc, select);
            }
        }
        pc.j().b(item, 1);
    }

    public static void a(u pc, q item, q select, boolean isOnly) {
        if (select == null) {
            pc.a(new ds(156));
            return;
        }
        int level = select.G();
        if (!select.i() || select.F() >= 128 || level >= 10) {
            pc.a(new ds(79));
            return;
        }
        if (!isOnly && select.a().x() <= -1) {
            pc.a(new ds(1453));
            return;
        }
        int rnd = i.a(100) + 1;
        int chance = 45 + (50 + level) / (level + 1);
        if (item.F() == 0) {
            chance += 5;
        } else if (item.F() == 2) {
            chance -= 5;
        }
        pc.j().b(item, 1);
        if (rnd >= chance) {
            if (item.N() == 640614 || item.N() == 640727) {
                a.f(pc, item, select);
            } else {
                a.a(pc, select);
            }
            return;
        }
        a.a(pc, select, 1);
    }

    private static void f(u pc, q l1iteminstance, q select) {
        int newEnchantLvl = select.G() - 1;
        if (l1iteminstance.F() == 0 || newEnchantLvl < 0) {
            pc.a(new ds(160, select.s(), "$246"));
            return;
        }
        boolean isEquipped = select.D();
        pc.j().a(select, false);
        select.a(newEnchantLvl);
        pc.j().j(select);
        pc.j().a(select, isEquipped);
        pc.a(new ds(161, select.s(), "$246", "$247"));
    }

    public static void d(u pc, q l1iteminstance, q select) {
        int scroll_attr = l1iteminstance.a().V();
        if (!select.g()) {
            pc.a(new ds(79));
            return;
        }
        if (select.F() >= 128 || select.a().x() < 0) {
            pc.a(new ds(3298));
            return;
        }
        select.h(scroll_attr);
        pc.j().j(select);
        pc.a(new ds(3296, select.s()));
        pc.j().b(l1iteminstance, 1);
    }

    public static void e(u pc, q item, q select) {
        int item_attr = select.K();
        int level = select.L();
        int scroll_attr = item.a().V();
        if (!select.g()) {
            pc.a(new ds(79));
            return;
        }
        if (select.F() >= 128 || select.a().x() < 0) {
            pc.a(new ds(1453));
            return;
        }
        if (item_attr == scroll_attr && (select.G() <= 8 && level >= 3 || select.G() <= 9 && level >= 4 || select.G() <= 10 && level >= 5 || select.G() > 10 && level >= 5)) {
            pc.a(new ds(3319));
            return;
        }
        int rnd = i.a(100) + 1;
        int chance = l1j.server.a.I;
        if (item.F() == 0) {
            chance += 5;
        } else if (item.F() == 2) {
            chance -= 5;
        }
        if (rnd <= chance) {
            level = (item_attr == scroll_attr ? level : 0) + 1;
            select.h(scroll_attr);
            select.i(level);
            pc.j().j(select);
            pc.a(new ds(1410, select.s()));
        } else {
            pc.a(new ds(1411, select.s()));
        }
        pc.j().b(item, 1);
    }

    public static void a(u pc, q item, int addLevel) {
        int itemType2 = item.a().f();
        String[][] sa = new String[][]{{"", "", "", "", ""}, {"$246", "", "$245", "$245", "$245"}, {"$246", "", "$252", "$252", "$252"}};
        String[][] sb = new String[][]{{"", "", "", "", ""}, {"$247", "", "$247", "$248", "$248"}, {"$247", "", "$247", "$248", "$248"}};
        String sa_temp = sa[itemType2][addLevel + 1];
        String sb_temp = sb[itemType2][addLevel + 1];
        pc.a(new ds(161, item.s(), sa_temp, sb_temp));
        boolean isEquipped = item.D();
        pc.j().a(item, false);
        int newEnchantLvl = item.G() + addLevel;
        if (newEnchantLvl >= 9) {
            aq.a().a(new ds(item.g() ? 4444 : 4445, item.s()));
        }
        item.a(newEnchantLvl);
        pc.j().j(item);
        pc.j().a(item, isEquipped);
        if (item.G() - item.a().x() >= 3) {
            aa.a().a(pc, "\u5f37\u5316\u6210\u529f\uff0c\u7372\u5f97", item);
        }
    }

    private static void a(u pc, q item) {
        String[] sa = new String[]{"", "$245", "$252"};
        int itemType2 = item.a().f();
        if (item.G() < 0) {
            sa[itemType2] = "$246";
        }
        pc.a(new ds(164, item.s(), sa[itemType2]));
        pc.j().b(item, item.E());
        if (item.G() - item.a().x() >= 2) {
            aa.a().a(pc, "\u5f37\u5316\u5931\u6557\uff0c\u5931\u53bb", item);
        }
    }

    private static int a(boolean isBless, q select) {
        if (isBless) {
            int rnd = i.a(100) + 1;
            if (select.G() <= 2) {
                if (rnd >= 33 && rnd <= 76) {
                    return 2;
                }
                if (rnd >= 77 && rnd <= 100) {
                    return 3;
                }
            } else if (select.G() >= 3 && select.G() <= 5 && rnd < 50) {
                return 2;
            }
        }
        return 1;
    }
}

