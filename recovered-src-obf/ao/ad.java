/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ap.t;
import ap.u;
import be.be;
import be.ds;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ad {
    private static final Logger a = Logger.getLogger(ad.class.getName());
    private static ad b;
    private final HashMap<String, a> c;

    public static ad a() {
        if (b == null) {
            b = new ad();
        }
        return b;
    }

    private ad() {
        block7: {
            this.c = new HashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM html");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a data = new a();
                        data.a = rs.getInt("npcid");
                        data.b = rs.getString("action");
                        data.c = rs.getInt("itemid");
                        data.d = rs.getInt("count");
                        data.e = rs.getString("html");
                        data.f = rs.getString("var");
                        if (data.f.endsWith(",")) {
                            data.f = data.f.substring(0, data.f.length() - 1);
                        }
                        data.g = rs.getString("fail_html");
                        String key = String.valueOf(data.a) + "-" + data.b;
                        this.c.put(key, data);
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

    public boolean a(String action, u pc, t npc) {
        if (!this.c.containsKey(String.valueOf(npc.z()) + "-" + action)) {
            return false;
        }
        a data = this.c.get(String.valueOf(npc.z()) + "-" + action);
        String[] args = data.f.split(",");
        if (pc.j().g(data.c, data.d)) {
            pc.a(new be(npc.fr(), data.e, args));
        } else {
            bh.j l1item = ah.a().a(data.c);
            pc.a(new ds(337, l1item.i()));
            pc.a(new be(npc.fr(), data.g));
        }
        return true;
    }

    private class a {
        public int a;
        public String b;
        public int c;
        public int d;
        public String e;
        public String f;
        public String g;

        private a() {
        }
    }
}

