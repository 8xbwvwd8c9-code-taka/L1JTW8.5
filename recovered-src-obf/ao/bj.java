/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bd.i;
import bi.g;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class bj {
    private static final Logger a = Logger.getLogger(bj.class.getName());
    private static bj b;
    private final HashMap<Integer, i> c = new HashMap();

    private bj() {
        this.d();
    }

    private void d() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                con = l1j.server.b.a().b();
                pstm = con.prepareStatement("SELECT * FROM trap");
                rs = pstm.executeQuery();
                while (rs.next()) {
                    String typeName = rs.getString("type");
                    i trap = g.a(typeName, new a(rs));
                    this.c.put(trap.a(), trap);
                }
            }
            catch (SQLException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                j.a(rs, pstm, con);
                break block7;
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
                break block7;
            }
            j.a(rs, pstm, con);
        }
    }

    public static bj a() {
        if (b == null) {
            b = new bj();
        }
        return b;
    }

    public static void b() {
        bj oldInstance = b;
        b = new bj();
        oldInstance.c.clear();
    }

    public i a(int id) {
        return this.c.get(id);
    }

    private class a
    implements bd.j {
        private final ResultSet b;

        public a(ResultSet rs) {
            this.b = rs;
        }

        @Override
        public String a(String name) {
            try {
                return this.b.getString(name);
            }
            catch (SQLException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                return "";
            }
        }

        @Override
        public int b(String name) {
            try {
                return this.b.getInt(name);
            }
            catch (SQLException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                return 0;
            }
        }

        @Override
        public boolean c(String name) {
            try {
                return this.b.getBoolean(name);
            }
            catch (SQLException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                return false;
            }
        }
    }
}

