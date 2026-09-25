/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.u;
import bh.w;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class bh {
    private static final Logger a = Logger.getLogger(bh.class.getName());
    private static bh b;
    private final ConcurrentHashMap<Integer, w> c = new ConcurrentHashMap();

    public static bh a() {
        if (b == null) {
            b = new bh();
        }
        return b;
    }

    private bh() {
        this.b();
    }

    public void b() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            this.c.clear();
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM town");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        w town = new w();
                        int townid = rs.getInt("town_id");
                        town.a(townid);
                        town.a(rs.getString("name"));
                        town.b(rs.getInt("leader_id"));
                        town.b(rs.getString("leader_name"));
                        town.c(rs.getInt("tax_rate"));
                        town.d(rs.getInt("tax_rate_reserved"));
                        town.e(rs.getInt("sales_money"));
                        town.f(rs.getInt("sales_money_yesterday"));
                        town.g(rs.getInt("town_tax"));
                        town.h(rs.getInt("town_fix_tax"));
                        this.c.put(new Integer(townid), town);
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

    public w[] c() {
        return this.c.values().toArray(new w[this.c.size()]);
    }

    public w a(int id) {
        return this.c.get(id);
    }

    public boolean a(u pc, int town_id) {
        w town = this.a(town_id);
        return town.c() == pc.fr();
    }

    public synchronized void a(int town_id, int salesMoney) {
        block10: {
            Connection con = null;
            PreparedStatement pstm = null;
            w town = bh.a().a(town_id);
            int townTaxRate = town.e();
            int townTax = salesMoney * townTaxRate / 100;
            int townFixTax = salesMoney * 2 / 100;
            if (townTax <= 0 && townTaxRate > 0) {
                townTax = 1;
            }
            if (townFixTax <= 0 && townTaxRate > 0) {
                townFixTax = 1;
            }
            if ((long)town.g() + (long)salesMoney >= 2000000000L) {
                salesMoney = 2000000000 - town.g();
            }
            if ((long)town.i() + (long)townTax >= 2000000000L) {
                townTax = 2000000000 - town.i();
            }
            if ((long)town.j() + (long)townFixTax >= 2000000000L) {
                townFixTax = 2000000000 - town.j();
            }
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE town SET sales_money = sales_money + ?, town_tax = town_tax + ?, town_fix_tax = town_fix_tax + ? WHERE town_id = ?");
                    pstm.setInt(1, salesMoney);
                    pstm.setInt(2, townTax);
                    pstm.setInt(3, townFixTax);
                    pstm.setInt(4, town_id);
                    pstm.execute();
                    town.e(town.g() + salesMoney);
                    town.g(town.i() + townTax);
                    town.h(town.j() + townFixTax);
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block10;
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

    public void d() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE town SET tax_rate = tax_rate_reserved");
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

    public void e() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE town SET sales_money_yesterday = sales_money, sales_money = 0");
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
}

