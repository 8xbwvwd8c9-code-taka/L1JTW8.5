/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class r {
    private static final Logger a = Logger.getLogger(r.class.getName());
    private final ConcurrentHashMap<String, String> b = new ConcurrentHashMap();
    private static r c;

    public String a(String parameter, String defaultValue) {
        if (this.b.containsKey(parameter)) {
            return this.b.get(parameter);
        }
        System.out.println(String.valueOf(parameter) + " has no setting in DB !! Use defaultValue= " + defaultValue);
        return defaultValue;
    }

    public static r a() {
        if (c == null) {
            c = new r();
        }
        return c;
    }

    private r() {
        this.a("_config");
        this.a("_config_other");
        this.a("_config_world");
    }

    private void a(String tableName) {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM " + tableName);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        String parameter = rs.getString("parameter");
                        String value = rs.getString("value");
                        if (this.b.containsKey(parameter)) {
                            System.out.println("[Errer]" + tableName + " has repeated parameter= " + parameter);
                            continue;
                        }
                        this.b.put(parameter, value);
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

    public ConcurrentHashMap<String, String> b() {
        return this.b;
    }
}

