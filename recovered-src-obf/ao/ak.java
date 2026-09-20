/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ap.q;
import au.f;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ak {
    private static final Logger a = Logger.getLogger(ak.class.getName());
    private final HashMap<Integer, Integer> b;
    private final HashMap<Integer, Integer> c;
    private static ak d;

    public static ak a() {
        if (d == null) {
            d = new ak();
        }
        return d;
    }

    public ak() {
        block7: {
            this.b = new HashMap();
            this.c = new HashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM lost_power_item");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int itemid = rs.getInt("itemid");
                        int chande_itemid = rs.getInt("change_itemid");
                        if (itemid <= 0 || chande_itemid <= 0) continue;
                        if (!this.b.containsKey(itemid)) {
                            this.b.put(itemid, chande_itemid);
                        }
                        if (this.c.containsKey(chande_itemid)) continue;
                        this.c.put(chande_itemid, itemid);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public int a(int itemid) {
        if (this.b.containsKey(itemid)) {
            return this.b.get(itemid);
        }
        return 0;
    }

    public void a(int dropitemid, double droprate, f inventory) {
        if (!this.c.containsKey(dropitemid)) {
            return;
        }
        int itemid = this.c.get(dropitemid);
        if (droprate * 5.0 > (double)(i.a(1000000) + 1)) {
            q item = ah.a().b(itemid);
            inventory.d(item);
        }
    }

    public HashMap<Integer, Integer> b() {
        return this.b;
    }
}

