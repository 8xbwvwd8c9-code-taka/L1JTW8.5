/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.ah;
import ao.bf;
import ao.k;
import ap.q;
import ap.u;
import aq.am;
import aq.aq;
import aq.i;
import be.cm;
import be.dr;
import be.ds;
import bh.c;
import bj.d;

public class bz
extends cv {
    private static final String a = "[C] C_SendLocation";

    public bz(byte[] abyte0, d client) {
        super(abyte0);
        int type = this.c();
        if (type == 13) {
            return;
        }
        if (type == 11) {
            String name = this.g();
            int mapId = this.d();
            int x2 = this.d();
            int y2 = this.d();
            int msgId = this.c();
            if (name.isEmpty()) {
                return;
            }
            u target = aq.a().a(name);
            if (target != null) {
                u pc = client.f();
                String sender = pc.et();
                target.a(new dr(sender, mapId, x2, y2, msgId));
                pc.a(new ds(1783, name));
            }
        } else if (type != 6 && type != 32) {
            if (type == 34) {
                u pc = client.f();
                if (pc == null) {
                    return;
                }
                int uk = this.c();
                int size = pc.ba().size();
                int[] orderValues = new int[size];
                int orderCount = 0;
                while (orderCount < size) {
                    int order = this.c();
                    if (order == 255) {
                        break;
                    }
                    if (order < 0 || order >= size) {
                        return;
                    }
                    orderValues[orderCount++] = order;
                }
                int[] fastIndex = new int[]{-1, -1, -1, -1, -1};
                int fastCount = 0;
                while (fastCount < 5) {
                    int index = this.c();
                    if (index == 255) {
                        break;
                    }
                    if (index < 0 || index >= size) {
                        return;
                    }
                    fastIndex[fastCount++] = index;
                }
                for (int n = 0; n < orderCount; ++n) {
                    pc.ba().get(n).e(orderValues[n]);
                }
                for (c bookmark2 : pc.ba()) {
                    bookmark2.f(-1);
                }
                for (int n = 0; n < fastCount; ++n) {
                    pc.ba().get(fastIndex[n]).f(n);
                }
                c.a(pc.ba());
            } else if (type == 39) {
                u pc = client.f();
                int size = this.b();
                int i3 = 0;
                while (i3 < size) {
                    int bookmarkID = this.b();
                    String new_name = this.g();
                    c bookmark = pc.l(bookmarkID);
                    if (bookmark != null) {
                        bookmark.a(new_name);
                    }
                    ++i3;
                }
                c.a(pc.ba());
            } else if (type == 40) {
                u pc = client.f();
                if (pc == null) {
                    return;
                }
                int item_objid = this.b();
                int size = this.b();
                if (size < 0 || size > 60) {
                    pc.a(new ds(2930));
                    return;
                }
                q source = pc.j().e(item_objid);
                if (source == null || source.N() != 41761) {
                    return;
                }
                if (pc.j().b(source, 1) != 1) {
                    return;
                }
                q new_item = ah.a(pc, 41762, 1);
                if (new_item == null) {
                    ah.a(pc, 41761, 1);
                    return;
                }
                if (!c.a(pc, new_item)) {
                    pc.j().f(new_item);
                    ah.a(pc, 41761, 1);
                    return;
                }
            } else if (type == 46) {
                u pc = client.f();
                if (pc.aH() != 4 && pc.aH() != 10) {
                    return;
                }
                int emblemStatus = this.c();
                i clan = ao.q.a().a(pc.aF());
                if (clan == null) {
                    return;
                }
                clan.e(emblemStatus);
                ao.q.a().b(clan);
                for (u member : clan.b()) {
                    member.a(new cm(173, emblemStatus));
                }
            } else if (type == 48) {
                int mapIndex = this.d();
                int point = this.d();
                int locx;
                int locy;
                u pc = client.f();
                if (pc == null) {
                    return;
                }
                if (mapIndex == 1) {
                    if (point == 0) {
                        locx = 34079 + (int)(Math.random() * 12.0);
                        locy = 33136 + (int)(Math.random() * 15.0);
                    } else if (point == 1) {
                        locx = 33970 + (int)(Math.random() * 10.0);
                        locy = 33243 + (int)(Math.random() * 14.0);
                    } else if (point == 2) {
                        locx = 33925 + (int)(Math.random() * 14.0);
                        locy = 33351 + (int)(Math.random() * 9.0);
                    } else {
                        return;
                    }
                } else if (mapIndex == 2) {
                    if (point == 0) {
                        locx = 32615 + (int)(Math.random() * 11.0);
                        locy = 32719 + (int)(Math.random() * 7.0);
                    } else if (point == 1) {
                        locx = 32621 + (int)(Math.random() * 9.0);
                        locy = 32788 + (int)(Math.random() * 13.0);
                    } else {
                        return;
                    }
                } else if (mapIndex == 3) {
                    if (point == 0) {
                        locx = 33501 + (int)(Math.random() * 11.0);
                        locy = 32765 + (int)(Math.random() * 9.0);
                    } else if (point == 1) {
                        locx = 33440 + (int)(Math.random() * 11.0);
                        locy = 32784 + (int)(Math.random() * 11.0);
                    } else {
                        return;
                    }
                } else if (mapIndex == 4) {
                    int[][] loc = new int[][]{{32838, 32886}, {32800, 32874}, {32755, 32899}, {32741, 32938}, {32740, 32964}, {32801, 32982}, {32845, 32986}, {32852, 32932}, {32799, 32927}};
                    if (point < 0 || point >= loc.length) {
                        return;
                    }
                    locx = loc[point][0];
                    locy = loc[point][1];
                } else {
                    return;
                }
                am.a(pc, locx, locy, pc.fp(), pc.fb(), true);
                pc.a(new cm(176, pc));
            } else if (type == 54) {
                int itemobjid = this.b();
                u pc = client.f();
                pc.a(new cm(185, itemobjid));
            } else if (type == 55) {
                int index = this.c();
                u pc = client.f();
                k.a().a(pc, index);
            } else if (type == 58) {
                u pc = client.f();
                bf.a().a(pc);
            }
        }
    }

    @Override
    public String a() {
        return a;
    }
}

