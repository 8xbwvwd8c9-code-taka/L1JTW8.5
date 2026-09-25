/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.q;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public final class bb {
    private static final Logger a = Logger.getLogger(bb.class.getName());
    private static bb b;
    private final HashMap<Integer, Integer> c = new HashMap();
    private final HashMap<Integer, a> d = new HashMap();

    public static bb a() {
        if (b == null) {
            b = new bb();
        }
        return b;
    }

    private bb() {
        this.d();
        this.c();
    }

    public HashMap<Integer, a> b() {
        return this.d;
    }

    public int a(q item) {
        if (!this.d.containsKey(item.N())) {
            return 1;
        }
        a data = this.d.get(item.N());
        int enchantLV = item.G() - item.a().x();
        enchantLV = Math.max(0, Math.min(enchantLV, 8));
        return data.b[enchantLV];
    }

    private void c() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM resolvent_refinery");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int itemId = rs.getInt("itemid");
                        a data = new a();
                        ((a)data).b[0] = rs.getInt("base");
                        ((a)data).b[1] = rs.getInt("base+1");
                        ((a)data).b[2] = rs.getInt("base+2");
                        ((a)data).b[3] = rs.getInt("base+3");
                        ((a)data).b[4] = rs.getInt("base+4");
                        ((a)data).b[5] = rs.getInt("base+5");
                        ((a)data).b[6] = rs.getInt("base+6");
                        ((a)data).b[7] = rs.getInt("base+7");
                        ((a)data).b[8] = rs.getInt("base+8");
                        this.d.put(itemId, data);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    private void d() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM resolvent");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int itemId = rs.getInt("item_id");
                        int crystalCount = rs.getInt("crystal_count");
                        this.c.put(new Integer(itemId), crystalCount);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public int a(int itemId) {
        int crystalCount = 0;
        if (this.c.containsKey(itemId)) {
            crystalCount = this.c.get(itemId);
        }
        return crystalCount;
    }

    private class a {
        private final int[] b = new int[9];

        private a() {
        }
    }
}

