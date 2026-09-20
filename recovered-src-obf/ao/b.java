/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bi.j;
import bj.d;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class b {
    private static final Logger a = Logger.getLogger(b.class.getName());
    private static b b;
    private static ArrayList<String> c;

    static {
        c = new ArrayList();
    }

    public static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    public void a(d client) {
        client.a(new CopyOnWriteArrayList<String>(c));
    }

    private b() {
        this.b();
    }

    private void b() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM _announce_login ORDER BY id ASC");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        String text = rs.getString("text");
                        c.add(text);
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

    public void a(CopyOnWriteArrayList<String> list) {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM _announce_cycle ORDER BY id ASC");
                    rs = pstm.executeQuery();
                    list.clear();
                    while (rs.next()) {
                        String text = rs.getString("text");
                        list.add(text);
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
}

