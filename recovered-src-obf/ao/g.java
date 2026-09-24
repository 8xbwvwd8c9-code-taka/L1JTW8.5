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

    public boolean transferTreasuryAdena(ap.u pc, int castleId, int amount, boolean deposit) {
        if (pc == null || amount <= 0) {
            return false;
        }
        d castle = this.c.get(castleId);
        if (castle == null) {
            return false;
        }

        synchronized (castle) {
            ap.q adena = pc.j().b(40308);
            int oldCount = adena == null ? 0 : adena.E();
            int oldMoney = castle.f();
            long newMoney = deposit ? (long)oldMoney + (long)amount : (long)oldMoney - (long)amount;
            long newCount = deposit ? (long)oldCount - (long)amount : (long)oldCount + (long)amount;
            if (newMoney < 0L || newMoney > 2000000000L || newCount < 0L || newCount > 2000000000L) {
                return false;
            }
            if (deposit && (adena == null || oldCount < amount)) {
                return false;
            }
            if (!deposit && adena == null && pc.j().c() >= 180) {
                return false;
            }

            ap.q newAdena = null;
            if (!deposit && adena == null) {
                if (ah.a().a(40308) == null) {
                    return false;
                }
                newAdena = new ap.q(ah.a().a(40308), amount);
                newAdena.cF(ai.d.a().d());
            }

            Connection con = null;
            PreparedStatement pstm = null;
            boolean oldAutoCommit = true;
            boolean committed = false;
            try {
                con = l1j.server.b.a().b();
                this.requireTreasuryInnoDb(con);
                oldAutoCommit = con.getAutoCommit();
                con.setAutoCommit(false);

                pstm = con.prepareStatement("UPDATE castle SET public_money=? WHERE castle_id=? AND public_money=?");
                pstm.setInt(1, (int)newMoney);
                pstm.setInt(2, castleId);
                pstm.setInt(3, oldMoney);
                if (pstm.executeUpdate() != 1) {
                    throw new SQLException("BUG-850-245 castle treasury CAS failed");
                }
                j.a(pstm);
                pstm = null;

                l itemTable = l.a();
                if (deposit) {
                    if (newCount == 0L) {
                        itemTable.deleteQuestRewardItem(con, pc.fr(), adena, oldCount);
                    } else {
                        itemTable.updateQuestRewardCount(con, pc.fr(), adena, oldCount, (int)newCount);
                    }
                } else if (adena != null) {
                    itemTable.updateQuestRewardCount(con, pc.fr(), adena, oldCount, (int)newCount);
                } else {
                    itemTable.insertQuestReward(con, pc.fr(), newAdena);
                }

                con.commit();
                committed = true;
            }
            catch (Exception e2) {
                if (con != null) {
                    try {
                        con.rollback();
                    }
                    catch (SQLException rollbackError) {
                        a.log(Level.SEVERE, rollbackError.getLocalizedMessage(), rollbackError);
                    }
                }
                a.log(Level.SEVERE, "BUG-850-245 castle treasury transfer failed", e2);
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

            if (!committed) {
                return false;
            }

            castle.b((int)newMoney);
            this.c.put(castleId, castle);
            if (deposit) {
                if (newCount == 0L) {
                    pc.j().publishCommittedQuestDelete(adena);
                } else {
                    pc.j().publishCommittedQuestUpdate(adena, (int)newCount);
                }
            } else if (adena != null) {
                pc.j().publishCommittedQuestUpdate(adena, (int)newCount);
            } else {
                pc.j().publishCommittedQuestInsert(newAdena);
            }
            return true;
        }
    }

    private void requireTreasuryInnoDb(Connection con) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            pstm = con.prepareStatement("SELECT TABLE_NAME, ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('castle','character_items')");
            rs = pstm.executeQuery();
            int count = 0;
            while (rs.next()) {
                if (!"InnoDB".equalsIgnoreCase(rs.getString("ENGINE"))) {
                    throw new SQLException("BUG-850-245 requires castle and character_items InnoDB migration");
                }
                ++count;
            }
            if (count != 2) {
                throw new SQLException("BUG-850-245 missing treasury transaction table");
            }
        }
        finally {
            j.a(rs);
            j.a(pstm);
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

