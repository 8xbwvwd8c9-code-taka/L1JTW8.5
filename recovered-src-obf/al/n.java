/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.ah;
import ap.q;
import ap.u;
import be.ds;
import be.ei;
import bh.j;
import java.util.StringTokenizer;

public class n
implements l {
    private n() {
    }

    public static l a() {
        return new n();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            int itemid;
            int attLevel;
            String attr;
            int bless;
            int enchant;
            int count;
            block27: {
                StringTokenizer st = new StringTokenizer(arg);
                String nameid = st.nextToken();
                count = 1;
                if (st.hasMoreTokens()) {
                    count = Integer.parseInt(st.nextToken());
                }
                enchant = 0;
                if (st.hasMoreTokens()) {
                    enchant = Integer.parseInt(st.nextToken());
                }
                bless = 1;
                if (st.hasMoreTokens()) {
                    bless = Integer.parseInt(st.nextToken());
                }
                attr = "";
                if (st.hasMoreTokens()) {
                    attr = st.nextToken();
                }
                attLevel = 0;
                if (st.hasMoreTokens()) {
                    attLevel = Integer.parseInt(st.nextToken());
                }
                itemid = 0;
                try {
                    itemid = Integer.parseInt(nameid);
                }
                catch (NumberFormatException e2) {
                    itemid = ah.a().b(nameid);
                    if (itemid != 0) break block27;
                    pc.a(new ei("\u627e\u4e0d\u5230\u7b26\u5408\u689d\u4ef6\u9805\u76ee\u3002"));
                    return;
                }
            }
            j temp = ah.a().a(itemid);
            if (temp != null) {
                if (temp.aF()) {
                    q item = ah.a().b(itemid);
                    item.a(0);
                    item.e(count);
                    item.f(bless);
                    item.a(true);
                    if (pc.j().a(item, count) == 0) {
                        pc.j().d(item);
                        pc.a(new ds(403, String.valueOf(item.s()) + "(ID:" + itemid + ")"));
                    }
                } else {
                    q item = null;
                    int createCount = 0;
                    while (createCount < count) {
                        item = ah.a().b(itemid);
                        item.a(enchant);
                        item.f(bless);
                        item.n();
                        if (item.g()) {
                            if (attr.equalsIgnoreCase("\u5730") || attr.equalsIgnoreCase("1")) {
                                if (attLevel > 0 && attLevel <= 3) {
                                    item.h(1);
                                    item.i(attLevel);
                                }
                            } else if (attr.equalsIgnoreCase("\u706b") || attr.equalsIgnoreCase("2")) {
                                if (attLevel > 0 && attLevel <= 3) {
                                    item.h(2);
                                    item.i(attLevel);
                                }
                            } else if (attr.equalsIgnoreCase("\u6c34") || attr.equalsIgnoreCase("4")) {
                                if (attLevel > 0 && attLevel <= 3) {
                                    item.h(4);
                                    item.i(attLevel);
                                }
                            } else if ((attr.equalsIgnoreCase("\u98a8") || attr.equalsIgnoreCase("8")) && attLevel > 0 && attLevel <= 3) {
                                item.h(8);
                                item.i(attLevel);
                            }
                        }
                        item.a(true);
                        if (pc.j().a(item, 1) != 0) break;
                        pc.j().d(item);
                        ++createCount;
                    }
                    if (createCount > 0 && item != null) {
                        pc.a(new ds(403, String.valueOf(item.s()) + "(ID:" + itemid + ")"));
                    }
                }
            } else {
                pc.a(new ei("\u6307\u5b9a\u7684\u9053\u5177\u7de8\u865f\u4e0d\u5b58\u5728"));
            }
        }
        catch (Exception e3) {
            pc.a(new ei("\u8acb\u8f38\u5165 .item itemid|name [\u6578\u91cf] [\u5f37\u5316\u7b49\u7d1a] [\u9451\u5b9a\u72c0\u614b] [\u6b66\u5668\u5c6c\u6027] [\u5c6c\u6027\u7b49\u7d1a]\u3002"));
        }
    }
}

