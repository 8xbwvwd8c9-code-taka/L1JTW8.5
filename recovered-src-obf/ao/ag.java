/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ag {
    private static final Logger a = Logger.getLogger(ag.class.getName());
    private static CopyOnWriteArrayList<String> b;
    private static boolean c;
    private static ag d;

    public static ag a() {
        if (d == null) {
            d = new ag();
        }
        return d;
    }

    private ag() {
        if (!c) {
            b = new CopyOnWriteArrayList();
            this.b();
        }
    }

    public void a(String ip) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO ban_ip SET ip=?");
                    pstm.setString(1, ip);
                    pstm.execute();
                    b.add(ip);
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm);
                j.a(con);
                throw throwable;
            }
            j.a(pstm);
            j.a(con);
        }
    }

    public boolean b(String s2) {
        for (String BanIpAddress : b) {
            if (BanIpAddress.endsWith("*")) {
                int fStarindex = BanIpAddress.indexOf("*");
                String reip = BanIpAddress.substring(0, fStarindex);
                String newaddress = s2.substring(0, fStarindex);
                if (!newaddress.equalsIgnoreCase(reip)) continue;
                return true;
            }
            if (!s2.equalsIgnoreCase(BanIpAddress)) continue;
            return true;
        }
        return false;
    }

    private void b() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM ban_ip");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        b.add(rs.getString(1));
                    }
                    c = true;
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

    public boolean c(String ip) {
        boolean ret;
        block5: {
            ret = false;
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM ban_ip WHERE ip=?");
                    pstm.setString(1, ip);
                    pstm.execute();
                    ret = b.remove(ip);
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm);
                j.a(con);
                throw throwable;
            }
            j.a(pstm);
            j.a(con);
        }
        return ret;
    }
}

