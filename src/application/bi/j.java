/*
 * Decompiled with CFR 0.152.
 */
package bi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class j {
    public static SQLException a(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        }
        catch (SQLException e2) {
            return e2;
        }
        return null;
    }

    public static SQLException a(Statement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        }
        catch (SQLException e2) {
            return e2;
        }
        return null;
    }

    public static SQLException a(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        }
        catch (SQLException e2) {
            return e2;
        }
        return null;
    }

    public static void a(ResultSet rs, Statement pstm, Connection con) {
        j.a(rs);
        j.a(pstm);
        j.a(con);
    }
}

