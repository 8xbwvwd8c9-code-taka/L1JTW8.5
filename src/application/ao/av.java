/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bh.o;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class av {
    private static final Logger a = Logger.getLogger(av.class.getName());
    private static av b;
    private final HashMap<Integer, o> c = new HashMap();
    private static final HashMap<String, Integer> d;

    static {
        d = new HashMap();
        d.put("armor", new Integer(0));
        d.put("tooth", new Integer(1));
    }

    public static av a() {
        if (b == null) {
            b = new av();
        }
        return b;
    }

    private av() {
        this.b();
    }

    private void b() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM petitem");
                    rs = pstm.executeQuery();
                    this.a(rs);
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    private void a(ResultSet rs) throws SQLException {
        while (rs.next()) {
            o petItem = new o();
            petItem.a(rs.getInt("item_id"));
            petItem.n(d.get(rs.getString("use_type")));
            petItem.b(rs.getInt("hitmodifier"));
            petItem.c(rs.getInt("dmgmodifier"));
            petItem.d(rs.getInt("ac"));
            petItem.e(rs.getInt("add_str"));
            petItem.f(rs.getInt("add_con"));
            petItem.g(rs.getInt("add_dex"));
            petItem.h(rs.getInt("add_int"));
            petItem.i(rs.getInt("add_wis"));
            petItem.j(rs.getInt("add_hp"));
            petItem.k(rs.getInt("add_mp"));
            petItem.l(rs.getInt("add_sp"));
            petItem.m(rs.getInt("m_def"));
            this.c.put(petItem.a(), petItem);
        }
    }

    public o a(int itemId) {
        return this.c.get(itemId);
    }
}

