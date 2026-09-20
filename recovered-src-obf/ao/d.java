/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class d {
    private static final Logger a = Logger.getLogger(d.class.getName());
    private static d b;
    private final ArrayList<a> c;

    public static d a() {
        if (b == null) {
            b = new d();
        }
        return b;
    }

    private d() {
        block6: {
            this.c = new ArrayList();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM beginner");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a data = new a();
                        data.a = rs.getInt("item_id");
                        data.b = rs.getInt("count");
                        data.c = rs.getInt("charge_count");
                        data.d = rs.getInt("enchantlvl");
                        data.e = rs.getString("item_name");
                        data.f = rs.getString("activate");
                        data.g = rs.getInt("bless");
                        this.c.add(data);
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

    public void a(u pc) {
        Connection con = null;
        PreparedStatement pstm2 = null;
        try {
            try {
                con = l1j.server.b.a().b();
                for (a data : this.c) {
                    if (!data.f.contains(pc.aC().h()) && !data.f.contains("A")) continue;
                    pstm2 = con.prepareStatement("INSERT INTO character_items SET id=?, item_id=?, char_id=?, item_name=?, count=?, is_equipped=?, enchantlvl=?, is_id=?, durability=?, charge_count=?, temp_value=?, last_used=?, bless=?");
                    pstm2.setInt(1, ai.d.a().d());
                    pstm2.setInt(2, data.a);
                    pstm2.setInt(3, pc.fr());
                    pstm2.setString(4, data.e);
                    pstm2.setInt(5, data.b);
                    pstm2.setInt(6, 0);
                    pstm2.setInt(7, data.d);
                    pstm2.setInt(8, 0);
                    pstm2.setInt(9, 0);
                    pstm2.setInt(10, data.c);
                    pstm2.setInt(11, 0);
                    pstm2.setTimestamp(12, null);
                    pstm2.setInt(13, data.g);
                    pstm2.execute();
                }
            }
            catch (SQLException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                j.a(pstm2);
            }
        }
        finally {
            j.a(pstm2);
        }
    }

    private class a {
        public int a;
        public int b = 1;
        public int c = 0;
        public int d = 0;
        public String e = "";
        public String f = "";
        public int g = 1;

        private a() {
        }
    }
}

