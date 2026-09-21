/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import be.dc;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class bf {
    private static final Logger a = Logger.getLogger(bf.class.getName());
    private static bf b;
    private final ArrayList<a> c;

    public static bf a() {
        if (b == null) {
            b = new bf();
        }
        return b;
    }

    private bf() {
        block6: {
            this.c = new ArrayList();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM soul_tower");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a rank = new a();
                        rank.a = rs.getString("name");
                        rank.b = rs.getInt("class");
                        rank.c = rs.getInt("time");
                        rank.d = rs.getDate("date").getTime();
                        this.c.add(rank);
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

    public void a(u pc) {
        pc.a(new dc(this.c.toArray(new a[0])));
    }

    public synchronized void a(u pc, int time) {
        ArrayList<a> next = new ArrayList<a>(this.c);
        boolean qualifies = next.size() < 10;
        if (!qualifies) {
            for (a rank : next) {
                if (time < rank.c) {
                    qualifies = true;
                    break;
                }
            }
        }
        if (!qualifies) {
            return;
        }

        a rank = new a();
        rank.a = pc.et();
        rank.b = pc.ay();
        rank.c = time;
        Date now = new Date();
        rank.d = new java.sql.Date(now.getTime()).getTime();
        next.add(rank);
        Collections.sort(next, new Comparator<a>() {
            public int a(a r1, a r2) {
                return Integer.compare(r1.c, r2.c);
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((a)object, (a)object2);
            }
        });
        while (next.size() > 10) {
            next.remove(next.size() - 1);
        }

        Connection con = null;
        PreparedStatement delete = null;
        PreparedStatement insert = null;
        try {
            con = l1j.server.b.a().b();
            con.setAutoCommit(false);
            delete = con.prepareStatement("DELETE FROM soul_tower WHERE rank >0");
            delete.executeUpdate();
            insert = con.prepareStatement("INSERT INTO soul_tower SET rank=?,name=?,class=?,time=?,date=?");
            int i2 = 0;
            while (i2 < next.size()) {
                a entry = next.get(i2);
                insert.setInt(1, i2 + 1);
                insert.setString(2, entry.a);
                insert.setInt(3, entry.b);
                insert.setInt(4, entry.c);
                Date utilDate = new Date();
                utilDate.setTime(entry.d);
                insert.setDate(5, new java.sql.Date(utilDate.getTime()));
                insert.addBatch();
                ++i2;
            }
            insert.executeBatch();
            con.commit();
            this.c.clear();
            this.c.addAll(next);
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException ignored) {
                }
            }
        }
        finally {
            j.a(insert);
            j.a(delete);
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                }
                catch (SQLException ignored) {
                }
            }
            j.a(con);
        }
    }

    public class a {
        public String a;
        public int b;
        public int c;
        public long d;
    }
}

