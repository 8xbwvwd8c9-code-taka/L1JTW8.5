/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ao.ao;
import ap.q;
import ap.t;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class u {
    private static final Logger a = Logger.getLogger(u.class.getName());
    private static u b;
    private final HashMap<Integer, b> c;

    public static u a() {
        if (b == null) {
            b = new u();
        }
        return b;
    }

    private u() {
        block10: {
            this.c = new HashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                con = l1j.server.b.a().b();
                pstm = con.prepareStatement("SELECT * FROM droplist_map");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    b dropmap;
                    int mapid = rs.getInt("mapid");
                    if (this.c.containsKey(mapid)) {
                        dropmap = this.c.get(mapid);
                    } else {
                        dropmap = new b();
                        this.c.put(mapid, dropmap);
                    }
                    int itemid = rs.getInt("itemid");
                    if (dropmap.b.containsKey(itemid)) {
                        System.out.println("[DropMapTable]: mapid=" + mapid + " itemid=" + itemid + " is repeate");
                        continue;
                    }
                    a data = new a();
                    data.b = itemid;
                    data.c = rs.getInt("min");
                    data.d = rs.getInt("max");
                    data.e = rs.getInt("chance");
                    data.f = rs.getInt("enchantlvl");
                    data.g = rs.getInt("bless_change");
                    dropmap.b.put(itemid, data);
                }
            }
            catch (SQLException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                j.a(rs, pstm, con);
                break block10;
            }
            catch (Exception e3) {
                try {
                    a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                }
                catch (Throwable throwable) {
                    j.a(rs, pstm, con);
                    throw throwable;
                }
                j.a(rs, pstm, con);
                break block10;
            }
            j.a(rs, pstm, con);
        }
    }

    public void a(t npc) {
        double adenarate;
        if (!this.c.containsKey(npc.fp())) {
            return;
        }
        if (npc.y() == null) {
            return;
        }
        double droprate = l1j.server.a.F;
        if (droprate <= 0.0) {
            droprate = 0.0;
        }
        if ((adenarate = l1j.server.a.E) <= 0.0) {
            adenarate = 0.0;
        }
        if (droprate <= 0.0 && adenarate <= 0.0) {
            return;
        }
        for (a data : this.c.get(npc.fp()).b.values()) {
            q item = ah.a().b(data.b);
            if (adenarate == 0.0 && data.b == 40308) continue;
            int rnd = i.a(1000000) + 1;
            double rateOfMapId = ao.a().b(npc.fp());
            if (droprate == 0.0 || (double)data.e * droprate * rateOfMapId < (double)rnd) continue;
            int itemCount = data.c;
            int addCount = data.d - data.c + 1;
            if (addCount > 1) {
                itemCount += i.a(addCount);
            }
            if (data.b == 40308) {
                itemCount = (int)((double)itemCount * adenarate);
            }
            if (itemCount < 0) {
                itemCount = 0;
            }
            if (itemCount > 2000000000) {
                itemCount = 2000000000;
            }
            if (item.d()) {
                item.e(itemCount);
                item.a(data.f);
                if (data.g > 0 && i.a(100) < data.g) {
                    item.f(0);
                }
                npc.y().d(item);
                continue;
            }
            int i2 = 0;
            while (i2 < itemCount) {
                q each = ah.a().b(data.b);
                each.a(data.f);
                if (data.g > 0 && i.a(100) < data.g) {
                    each.f(0);
                }
                npc.y().d(each);
                ++i2;
            }
        }
    }

    private class a {
        private int b;
        private int c;
        private int d;
        private int e;
        private int f;
        private int g;

        private a() {
        }
    }

    private class b {
        private final HashMap<Integer, a> b = new HashMap();

        private b() {
        }
    }
}

