/*
 * Decompiled with CFR 0.152.
 */
package ak;

import bh.e;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class a {
    private static final Logger a = Logger.getLogger(a.class.getName());

    private static e a(ResultSet rs) throws SQLException {
        return new e(rs.getString("name"), rs.getInt("access_level"), rs.getString("class_name"));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static e a(String name) {
        e e2;
        ResultSet rs;
        PreparedStatement pstm;
        Connection con;
        block5: {
            con = null;
            pstm = null;
            rs = null;
            con = b.a().b();
            pstm = con.prepareStatement("SELECT * FROM commands WHERE name=?");
            pstm.setString(1, name);
            rs = pstm.executeQuery();
            if (rs.next()) break block5;
            j.a(rs, pstm, con);
            return null;
        }
        try {
            e2 = ak.a.a(rs);
        }
        catch (SQLException e3) {
            try {
                a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
            return null;
        }
        j.a(rs, pstm, con);
        return e2;
    }

    public static List<e> a(int accessLevel) {
        ArrayList<e> result;
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            result = new ArrayList<e>();
            try {
                try {
                    con = b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM commands WHERE access_level <= ?");
                    pstm.setInt(1, accessLevel);
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        result.add(ak.a.a(rs));
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
        return result;
    }
}

