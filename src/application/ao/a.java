/*
 * Decompiled with CFR 0.152.
 */
package ao;

import be.ds;
import bi.e;
import bi.j;
import bj.d;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class a {
    private static final Logger b = Logger.getLogger(a.class.getName());
    private static a c;
    public final ConcurrentHashMap<String, d> a;

    public static a a() {
        if (c == null) {
            c = new a();
        }
        return c;
    }

    private a() {
        block5: {
            this.a = new ConcurrentHashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE accounts SET online=0, OnlineStatus=0 WHERE online=1 OR OnlineStatus=1");
                    pstm.execute();
                }
                catch (SQLException e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    public bh.a a(String name, String rawPassword, String ip, String host) {
        block5: {
            bh.a a2;
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                bh.a account = new bh.a();
                account.c(name);
                account.e(bh.a.b(rawPassword));
                account.d(ip);
                account.f(host);
                account.g(119);
                con = l1j.server.b.a().b();
                String sqlstr = "INSERT INTO accounts SET login=?,password=?,lastactive=?,access_level=?,ip=?,host=?,online=?,banned=?,character_slot=?,OnlineStatus=?,WorldShopAdena=?,TamPoint=?";
                pstm = con.prepareStatement("INSERT INTO accounts SET login=?,password=?,lastactive=?,access_level=?,ip=?,host=?,online=?,banned=?,character_slot=?,OnlineStatus=?,WorldShopAdena=?,TamPoint=?");
                pstm.setString(1, account.d());
                pstm.setString(2, account.f());
                pstm.setTimestamp(3, account.g());
                pstm.setInt(4, 0);
                pstm.setString(5, account.e());
                pstm.setString(6, account.i());
                pstm.setInt(7, 0);
                pstm.setBoolean(8, account.j());
                pstm.setBoolean(9, account.n());
                pstm.setBoolean(10, account.o());
                pstm.setInt(11, account.p());
                pstm.setInt(12, account.q());
                pstm.execute();
                System.out.println("created new account for " + name);
                a2 = account;
                j.a(pstm);
            }
            catch (SQLException e2) {
                b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                break block5;
            }
            finally {
                j.a(pstm);
                j.a(con);
            }
            j.a(con);
            return a2;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean a(String ip) {
        boolean bl2;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT * FROM accounts WHERE ip=?");
            pstm.setString(1, ip);
            rs = pstm.executeQuery();
            rs.last();
            bl2 = rs.getRow() < 6;
        }
        catch (SQLException e2) {
            try {
                b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
            return false;
        }
        j.a(rs, pstm, con);
        return bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean b(String host) {
        boolean bl2;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT * FROM accounts WHERE host=?");
            pstm.setString(1, host);
            rs = pstm.executeQuery();
            rs.last();
            bl2 = rs.getRow() < 6;
        }
        catch (SQLException e2) {
            try {
                b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
            return false;
        }
        j.a(rs, pstm, con);
        return bl2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public bh.a c(String name) {
        bh.a a2;
        ResultSet rs;
        PreparedStatement pstm;
        Connection con;
        block5: {
            con = null;
            pstm = null;
            rs = null;
            con = l1j.server.b.a().b();
            String sqlstr = "SELECT * FROM accounts WHERE login=? LIMIT 1";
            pstm = con.prepareStatement("SELECT * FROM accounts WHERE login=? LIMIT 1");
            pstm.setString(1, name);
            rs = pstm.executeQuery();
            if (rs.next()) break block5;
            j.a(rs, pstm, con);
            return null;
        }
        try {
            bh.a account = new bh.a();
            account.c(rs.getString("login"));
            account.e(rs.getString("password"));
            account.a(rs.getTimestamp("lastactive"));
            account.d(rs.getInt("access_level"));
            account.d(rs.getString("ip"));
            account.f(rs.getString("host"));
            account.a(rs.getBoolean("banned"));
            account.c(rs.getBoolean("online"));
            account.e(rs.getInt("character_slot"));
            account.f(rs.getInt("warepassword"));
            account.d(rs.getBoolean("OnlineStatus"));
            account.g(rs.getInt("WorldShopAdena"));
            account.h(rs.getInt("TamPoint"));
            a2 = account;
        }
        catch (SQLException e2) {
            try {
                b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
            return null;
        }
        j.a(rs, pstm, con);
        return a2;
    }

    public void a(bh.a account) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE accounts SET WorldShopAdena=? ,access_level=? WHERE login=?");
                    pstm.setInt(1, account.p());
                    pstm.setInt(2, account.h());
                    pstm.setString(3, account.d());
                    pstm.execute();
                }
                catch (Exception e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    public void b(bh.a account) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            try {
                try {
                    con = l1j.server.b.a().b();
                    String sqlstr = "UPDATE accounts SET lastactive=?, ip=? ,online=1 WHERE login = ?";
                    pstm = con.prepareStatement("UPDATE accounts SET lastactive=?, ip=? ,online=1 WHERE login = ?");
                    pstm.setTimestamp(1, ts);
                    pstm.setString(2, account.e());
                    pstm.setString(3, account.d());
                    pstm.execute();
                    account.a(ts);
                }
                catch (Exception e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    public void c(bh.a account) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE accounts SET character_slot=? WHERE login=?");
                    pstm.setInt(1, account.k());
                    pstm.setString(2, account.d());
                    pstm.execute();
                }
                catch (Exception e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    public void d(bh.a account) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE accounts SET TamPoint=? WHERE login=?");
                    pstm.setInt(1, account.q());
                    pstm.setString(2, account.d());
                    pstm.execute();
                }
                catch (Exception e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    public synchronized void a(bh.a account, boolean b2) {
        block6: {
            if (account == null) {
                return;
            }
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE accounts SET online=?,ip=?,host=? WHERE login=?");
                    pstm.setBoolean(1, b2);
                    pstm.setString(2, account.e());
                    pstm.setString(3, account.i());
                    pstm.setString(4, account.d());
                    pstm.execute();
                    account.c(b2);
                }
                catch (SQLException e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block6;
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

    public synchronized void b(bh.a account, boolean b2) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE accounts SET OnlineStatus=? WHERE login=?");
                    pstm.setBoolean(1, b2);
                    pstm.setString(2, account.d());
                    pstm.execute();
                    account.d(b2);
                }
                catch (SQLException e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    public void d(String accName) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE accounts SET banned=1, online=0, OnlineStatus=0 WHERE login=?");
                    pstm.setString(1, accName);
                    pstm.execute();
                }
                catch (SQLException e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    public void a(bh.a account, int newPassword) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE accounts SET warepassword = ? WHERE login = ?");
                    pstm.setInt(1, newPassword);
                    pstm.setString(2, account.d());
                    pstm.execute();
                    account.f(newPassword);
                }
                catch (SQLException e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    private void a(final d client) {
        if (client == null) {
            return;
        }
        e.a().b(new Runnable(){

            @Override
            public void run() {
                if (client.f() != null) {
                    client.f().a(new ds(357));
                }
                try {
                    Thread.sleep(1000L);
                }
                catch (Exception e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                }
                client.c();
            }
        });
    }

    public synchronized void a(bh.a account, d client) {
        if (!account.l()) {
            throw new IllegalArgumentException("\u5e33\u6236\u6c92\u6709\u901a\u904e\u8a8d\u8b49");
        }
        if (this.a.size() >= l1j.server.a.o && !account.a()) {
            throw new IllegalArgumentException("\u7dda\u4e0a\u4eba\u6578\u5df2\u7d93\u98fd\u548c\uff0c\u5207\u65b7 (" + client.g() + ") \u7684\u9023\u7dda\u3002");
        }
        if (this.a.containsKey(account.d())) {
            this.a(this.a.remove(account.d()));
            throw new IllegalArgumentException("\u540c\u500b\u5e33\u865f\u5df2\u7d93\u767b\u5165\uff0c\u5207\u65b7 (" + client.g() + ") \u7684\u9023\u7dda\u3002");
        }
        this.a.put(account.d(), client);
        this.a(client.e(), true);
    }

    public synchronized void e(String accName) {
        if (accName == null) {
            return;
        }
        this.a.remove(accName);
    }
}

