/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class aq {
    private static final Logger a = Logger.getLogger(aq.class.getName());
    private static aq b;
    private final ArrayList<a> c;

    public static aq a() {
        if (b == null) {
            b = new aq();
        }
        return b;
    }

    private aq() {
        block6: {
            this.c = new ArrayList();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM mob_quest_week");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a data = new a();
                        data.a = rs.getInt("mob_number");
                        data.b = rs.getInt("count");
                        this.c.add(data);
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

    public int[][] b() {
        int[][] result = new int[9][4];
        ArrayList<a> temp = new ArrayList<a>();
        while (temp.size() < 9) {
            a data = this.c.get(i.a(this.c.size()));
            if (temp.contains(data)) continue;
            temp.add(data);
        }
        int i2 = 0;
        while (i2 < result.length) {
            a data = (a)temp.get(i2);
            result[i2][0] = data.a;
            result[i2][1] = data.b;
            result[i2][2] = 0;
            result[i2][3] = 1;
            ++i2;
        }
        return result;
    }

    public class a {
        public int a;
        public int b;
    }
}

