/*
 * Decompiled with CFR 0.152.
 */
package ba;

import ao.bh;
import ap.u;
import aq.aq;
import at.b;
import at.c;
import be.cm;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

public class h {
    private static final Logger a = Logger.getLogger(h.class.getName());
    private static h b;
    private static a c;

    public static h a() {
        if (b == null) {
            b = new h();
        }
        return b;
    }

    private h() {
        this.d();
    }

    private void d() {
        if (c == null) {
            c = new a();
            at.c.a().a(c);
        }
    }

    private void a(at.a time) {
        Calendar cal = time.d();
        int day = cal.get(5);
        if (day == 25) {
            this.c();
        } else {
            this.b();
        }
    }

    public void b() {
        System.out.println("\u57ce\u93ae\u7cfb\u7d71\uff1a\u958b\u59cb\u8655\u7406\u6bcf\u65e5\u4e8b\u9805");
        bh.a().d();
        bh.a().e();
        bh.a().b();
    }

    public void c() {
        System.out.println("\u57ce\u93ae\u7cfb\u7d71\uff1a\u958b\u59cb\u8655\u7406\u6bcf\u6708\u4e8b\u9805");
        aq.a().b(true);
        Collection<u> players = aq.a().c();
        for (u pc : players) {
            pc.I();
        }
        int townId = 1;
        while (townId <= 10) {
            String leaderName = h.b(townId);
            if (leaderName != null) {
                cm packet = new cm(23, leaderName);
                for (u pc : players) {
                    if (pc.bF() != townId) continue;
                    pc.aE(0);
                    pc.a(packet);
                }
            }
            ++townId;
        }
        bh.a().b();
        for (u pc : players) {
            if (pc.bF() == -1) {
                pc.aD(0);
            }
            pc.aE(0);
            pc.I();
        }
        h.e();
        aq.a().b(false);
    }

    private static String b(int townId) {
        String leaderName;
        block9: {
            Connection con = null;
            PreparedStatement pstm1 = null;
            ResultSet rs1 = null;
            PreparedStatement pstm2 = null;
            ResultSet rs2 = null;
            PreparedStatement pstm3 = null;
            ResultSet rs3 = null;
            PreparedStatement pstm4 = null;
            PreparedStatement pstm5 = null;
            int leaderId = 0;
            leaderName = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm1 = con.prepareStatement("SELECT objid, char_name FROM characters WHERE HomeTownID = ? ORDER BY Contribution DESC");
                    pstm1.setInt(1, townId);
                    rs1 = pstm1.executeQuery();
                    if (rs1.next()) {
                        leaderId = rs1.getInt("objid");
                        leaderName = rs1.getString("char_name");
                    }
                    double totalContribution = 0.0;
                    pstm2 = con.prepareStatement("SELECT SUM(Contribution) AS TotalContribution FROM characters WHERE HomeTownID = ?");
                    pstm2.setInt(1, townId);
                    rs2 = pstm2.executeQuery();
                    if (rs2.next()) {
                        totalContribution = rs2.getInt("TotalContribution");
                    }
                    double townFixTax = 0.0;
                    pstm3 = con.prepareStatement("SELECT town_fix_tax FROM town WHERE town_id = ?");
                    pstm3.setInt(1, townId);
                    rs3 = pstm3.executeQuery();
                    if (rs3.next()) {
                        townFixTax = rs3.getInt("town_fix_tax");
                    }
                    double contributionUnit = 0.0;
                    if (totalContribution != 0.0) {
                        contributionUnit = Math.floor(townFixTax / totalContribution * 100.0) / 100.0;
                    }
                    pstm4 = con.prepareStatement("UPDATE characters SET Contribution = 0, Pay = Contribution * ? WHERE HomeTownID = ?");
                    pstm4.setDouble(1, contributionUnit);
                    pstm4.setInt(2, townId);
                    pstm4.execute();
                    pstm5 = con.prepareStatement("UPDATE town SET leader_id = ?, leader_name = ?, tax_rate = 0, tax_rate_reserved = 0, sales_money = 0, sales_money_yesterday = sales_money, town_tax = 0, town_fix_tax = 0 WHERE town_id = ?");
                    pstm5.setInt(1, leaderId);
                    pstm5.setString(2, leaderName);
                    pstm5.setInt(3, townId);
                    pstm5.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs1);
                    j.a(pstm1);
                    j.a(rs2);
                    j.a(pstm2);
                    j.a(rs3);
                    j.a(pstm3);
                    j.a(pstm4);
                    j.a(pstm5);
                    j.a(con);
                    break block9;
                }
            }
            catch (Throwable throwable) {
                j.a(rs1);
                j.a(pstm1);
                j.a(rs2);
                j.a(pstm2);
                j.a(rs3);
                j.a(pstm3);
                j.a(pstm4);
                j.a(pstm5);
                j.a(con);
                throw throwable;
            }
            j.a(rs1);
            j.a(pstm1);
            j.a(rs2);
            j.a(pstm2);
            j.a(rs3);
            j.a(pstm3);
            j.a(pstm4);
            j.a(pstm5);
            j.a(con);
        }
        return leaderName;
    }

    private static void e() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE characters SET HomeTownID = 0 WHERE HomeTownID = -1");
                    pstm.execute();
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

    public static int a(int objid) {
        int pay;
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            PreparedStatement pstm2 = null;
            ResultSet rs = null;
            pay = 0;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT Pay FROM characters WHERE objid = ? FOR UPDATE");
                    pstm.setInt(1, objid);
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        pay = rs.getInt("Pay");
                    }
                    pstm2 = con.prepareStatement("UPDATE characters SET Pay = 0 WHERE objid = ?");
                    pstm2.setInt(1, objid);
                    pstm2.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm2);
                    j.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm2);
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(pstm2);
            j.a(rs, pstm, con);
        }
        return pay;
    }

    private class a
    extends b {
        private a() {
        }

        @Override
        public void a(at.a time) {
            h.this.a(time);
        }
    }
}

