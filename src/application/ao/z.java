/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import aq.am;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class z {
    private static final Logger a = Logger.getLogger(z.class.getName());
    private static z b;
    private final HashMap<Integer, a> c;

    public static z a() {
        if (b == null) {
            b = new z();
        }
        return b;
    }

    public z() {
        block6: {
            this.c = new HashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM getback_restart");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a gbr = new a();
                        int area = rs.getInt("area");
                        gbr.a(rs.getInt("locx"));
                        gbr.b(rs.getInt("locy"));
                        gbr.c(rs.getShort("mapid"));
                        this.c.put(area, gbr);
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

    public void a(int src_area, int new_area) {
        if (!this.c.containsKey(src_area)) {
            System.out.println("[CopyGetBackRestart]: " + src_area + " error");
            return;
        }
        a src_gbr = this.c.get(src_area);
        a gbr = new a();
        gbr.a(src_gbr.a());
        gbr.b(src_gbr.b());
        gbr.c(src_gbr.c());
        if (!this.c.containsKey(new_area)) {
            this.c.put(new_area, gbr);
        }
    }

    public void a(u pc) {
        if (this.c.containsKey(pc.fp())) {
            a gbr = this.c.get(pc.fp());
            pc.cG(gbr.a());
            pc.cH(gbr.b());
            pc.cE(gbr.c());
        }
    }

    public void b(u pc) {
        if (this.c.containsKey(pc.fp())) {
            a gbr = this.c.get(pc.fp());
            am.a(pc, gbr.a(), gbr.b(), gbr.c(), 5, true);
        }
    }

    private class a {
        private int b;
        private int c;
        private int d;

        private a() {
        }

        public int a() {
            return this.b;
        }

        public void a(int i2) {
            this.b = i2;
        }

        public int b() {
            return this.c;
        }

        public void b(int i2) {
            this.c = i2;
        }

        public int c() {
            return this.d;
        }

        public void c(int i2) {
            this.d = i2;
        }
    }
}

