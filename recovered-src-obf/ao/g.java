/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bh.d;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class g {
    private static final Logger a = Logger.getLogger(g.class.getName());
    private static g b;
    private final ConcurrentHashMap<Integer, d> c = new ConcurrentHashMap();

    public static g a() {
        if (b == null) {
            b = new g();
        }
        return b;
    }

    private g() {
        this.d();
    }

    private Calendar a(Timestamp ts) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(ts.getTime());
        return cal;
    }

    private void d() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM castle ORDER BY castle_id ASC");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        d castle = new d(rs.getInt("castle_id"), rs.getString("name"));
                        castle.a(this.a((Timestamp)rs.getObject("war_time")));
                        castle.a(rs.getInt("tax_rate"));
                        castle.b(rs.getInt("public_money"));
                        if (!castle.k().isEmpty()) {
                            castle.k().get((int)0).c = rs.getInt("mercenary_count_0");
                            castle.k().get((int)1).c = rs.getInt("mercenary_count_1");
                            castle.k().get((int)2).c = rs.getInt("mercenary_count_2");
                            castle.k().get((int)3).c = rs.getInt("mercenary_count_3");
                        }
                        this.b(castle);
                        this.c.put(castle.a(), castle);
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

    private void b(d castle) {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT clan_id FROM clan_data WHERE hascastle = ?");
                    pstm.setInt(1, castle.a());
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        castle.c(rs.getInt("clan_id"));
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

    public d[] b() {
        return this.c.values().toArray(new d[this.c.size()]);
    }

    public d a(int id) {
        return this.c.get(id);
    }

    public boolean a(d castle) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("UPDATE castle SET name=?, war_time=?, tax_rate=?, public_money=? ,mercenary_count_0=? ,mercenary_count_1=? ,mercenary_count_2=?,mercenary_count_3=? WHERE castle_id=?");
            pstm.setString(1, castle.b());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String fm2 = sdf.format(castle.c().getTime());
            pstm.setString(2, fm2);
            pstm.setInt(3, castle.e());
            pstm.setInt(4, castle.f());
            pstm.setInt(5, castle.k().isEmpty() ? 0 : castle.k().get((int)0).c);
            pstm.setInt(6, castle.k().isEmpty() ? 0 : castle.k().get((int)1).c);
            pstm.setInt(7, castle.k().isEmpty() ? 0 : castle.k().get((int)2).c);
            pstm.setInt(8, castle.k().isEmpty() ? 0 : castle.k().get((int)3).c);
            pstm.setInt(9, castle.a());
            if (pstm.executeUpdate() != 1) {
                return false;
            }
            this.c.put(castle.a(), castle);
            return true;
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        finally {
            j.a(pstm);
            j.a(con);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a(int castleId, int conut) {
        d castle = g.a().a(castleId);
        int castleTax = conut * castle.e() / 100;
        d d2 = castle;
        synchronized (d2) {
            if (castle.f() < 2000000000) {
                castle.b(castle.f() + castleTax);
                g.a().a(castle);
            }
        }
    }

    public ConcurrentHashMap<Integer, d> c() {
        return this.c;
    }
}

