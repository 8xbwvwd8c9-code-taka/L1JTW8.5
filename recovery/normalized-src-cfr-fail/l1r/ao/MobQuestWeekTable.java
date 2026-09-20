/*
 * Decompiled with CFR 0.152.
 */
package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class MobQuestWeekTable {
    private static final Logger a = Logger.getLogger(MobQuestWeekTable.class.getName());
    private static MobQuestWeekTable b;
    private final ArrayList<L1R_a> c;

    public static MobQuestWeekTable a() {
        if (b == null) {
            b = new MobQuestWeekTable();
        }
        return b;
    }

    private MobQuestWeekTable() {
        block6: {
            this.c = new ArrayList();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = DatabaseFactory.a().b();
                    pstm = con.prepareStatement("SELECT * FROM mob_quest_week");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        L1R_a data = new L1R_a();
                        data.a = rs.getInt("mob_number");
                        data.b = rs.getInt("count");
                        this.c.add(data);
                    }
                }
                catch (SQLException e) {
                    a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                    SQLUtil.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                SQLUtil.a(rs, pstm, con);
                throw throwable;
            }
            SQLUtil.a(rs, pstm, con);
        }
    }

    public int[][] b() {
        int[][] result = new int[9][4];
        ArrayList<L1R_a> temp = new ArrayList<L1R_a>();
        while (temp.size() < 9) {
            L1R_a data = this.c.get(Random.a(this.c.size()));
            if (temp.contains(data)) continue;
            temp.add(data);
        }
        int i = 0;
        while (i < result.length) {
            L1R_a data = (L1R_a)temp.get(i);
            result[i][0] = data.a;
            result[i][1] = data.b;
            result[i][2] = 0;
            result[i][3] = 1;
            ++i;
        }
        return result;
    }

    public class L1R_a {
        public int a;
        public int b;
    }
}
