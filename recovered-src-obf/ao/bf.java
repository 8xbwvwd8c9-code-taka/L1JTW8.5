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
import java.util.List;
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

    public synchronized void a(u pc) {
        pc.a(new dc(this.c.toArray(new a[0])));
    }

    public synchronized void a(u pc, int time) {
        boolean isBetter = this.c.size() < 10;
        for (a rank : this.c) {
            if (time < rank.c) {
                isBetter = true;
                break;
            }
        }
        if (!isBetter) {
            return;
        }

        a rank = new a();
        rank.a = pc.et();
        rank.b = pc.ay();
        rank.c = time;
        Date now = new Date();
        java.sql.Date sqlDate = new java.sql.Date(now.getTime());
        rank.d = sqlDate.getTime();

        ArrayList<a> snapshot = new ArrayList<a>(this.c);
        snapshot.add(rank);
        Collections.sort(snapshot, new Comparator<a>() {
            @Override
            public int compare(a r1, a r2) {
                return Integer.compare(r1.c, r2.c);
            }
        });
        while (snapshot.size() > 10) {
            snapshot.remove(snapshot.size() - 1);
        }

        if (!this.a(snapshot)) {
            return;
        }

        this.c.clear();
        this.c.addAll(snapshot);
    }

    private boolean a(List<a> snapshot) {
        Connection con = null;
        PreparedStatement pstm = null;
        boolean oldAutoCommit = true;
        try {
            con = l1j.server.b.a().b();
            oldAutoCommit = con.getAutoCommit();
            con.setAutoCommit(false);

            pstm = con.prepareStatement("DELETE FROM soul_tower WHERE rank >0");
            pstm.executeUpdate();
            j.a(pstm);
            pstm = con.prepareStatement("INSERT INTO soul_tower SET rank=?,name=?,class=?,time=?,date=?");

            int i2 = 0;
            while (i2 < snapshot.size()) {
                a row = snapshot.get(i2);
                pstm.setInt(1, i2 + 1);
                pstm.setString(2, row.a);
                pstm.setInt(3, row.b);
                pstm.setInt(4, row.c);
                Date utilDate = new Date();
                utilDate.setTime(row.d);
                pstm.setDate(5, new java.sql.Date(utilDate.getTime()));
                if (pstm.executeUpdate() != 1) {
                    throw new SQLException("BUG-850-251 SoulTower insert affected unexpected row count");
                }
                ++i2;
            }

            con.commit();
            return true;
        }
        catch (SQLException e2) {
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException rollbackError) {
                    a.log(Level.SEVERE, rollbackError.getLocalizedMessage(), rollbackError);
                }
            }
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        finally {
            j.a(pstm);
            if (con != null) {
                try {
                    con.setAutoCommit(oldAutoCommit);
                }
                catch (SQLException autoCommitError) {
                    a.log(Level.SEVERE, autoCommitError.getLocalizedMessage(), autoCommitError);
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

