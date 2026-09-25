/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ap.t;
import ap.u;
import aq.am;
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

public class ae {
    private static final Logger a = Logger.getLogger(ae.class.getName());
    private static ae b;
    private final HashMap<String, a> c;

    public static ae a() {
        if (b == null) {
            b = new ae();
        }
        return b;
    }

    private ae() {
        block6: {
            this.c = new HashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM html_teleport");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a data = new a();
                        data.a = rs.getInt("npcid");
                        data.b = rs.getString("action");
                        data.c = rs.getInt("x");
                        data.d = rs.getInt("y");
                        data.e = rs.getInt("mapid");
                        data.f = rs.getInt("heading");
                        data.g = rs.getInt("itemid");
                        data.h = rs.getInt("count");
                        data.i = rs.getInt("min_level");
                        data.j = rs.getInt("max_level");
                        data.k = rs.getString("fail_html");
                        String key = String.valueOf(data.a) + "-" + data.b;
                        this.c.put(key, data);
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

    public boolean a(String action, u pc, t npc) {
        if (!this.c.containsKey(String.valueOf(npc.z()) + "-" + action)) {
            return false;
        }
        a data = this.c.get(String.valueOf(npc.z()) + "-" + action);
        if (!pc.j().g(data.g, data.h)) {
            bh.j l1item = ah.a().a(data.g);
            pc.a(new ds(337, l1item.i()));
            pc.a(new be(npc.fr(), data.k));
            return true;
        }
        if (pc.ev() < data.i) {
            pc.a(new ds(2738));
            pc.a(new be(npc.fr(), data.k));
            return true;
        }
        if (pc.ev() > data.j) {
            pc.a(new ds(2797));
            pc.a(new be(npc.fr(), data.k));
            return true;
        }
        pc.j().b(data.g, data.h);
        am.a(pc, data.c, data.d, data.e, data.f, true, npc.D().equals(""));
        return true;
    }

    private class a {
        public int a;
        public String b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public int h;
        public int i;
        public int j;
        public String k;

        private a() {
        }
    }
}

