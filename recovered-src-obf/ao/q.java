/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ap.u;
import aq.i;
import be.cm;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class q {
    private static final Logger a = Logger.getLogger(q.class.getName());
    private final ConcurrentHashMap<Integer, i> b;
    private static q c;

    public static q a() {
        if (c == null) {
            c = new q();
        }
        return c;
    }

    private q() {
        block13: {
            this.b = new ConcurrentHashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM clan_data ORDER BY clan_id");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        i clan = new i();
                        int clan_id = rs.getInt("clan_id");
                        clan.c(clan_id);
                        clan.e(rs.getString("clan_name"));
                        clan.f(rs.getInt("leader_id"));
                        clan.g(rs.getString("leader_name"));
                        clan.g(rs.getInt("hascastle"));
                        clan.h(rs.getInt("hashouse"));
                        clan.a(rs.getTimestamp("found_date"));
                        clan.f(rs.getString("announcement"));
                        clan.d(rs.getInt("emblem_id"));
                        clan.e(rs.getInt("emblem_status"));
                        clan.d(rs.getString("watch_clanid"));
                        this.b.put(clan_id, clan);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block13;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
        for (i clan : this.b.values()) {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT char_name FROM characters WHERE ClanID = ?");
                    pstm.setInt(1, clan.e());
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        clan.a(rs.getString(1));
                    }
                }
                catch (SQLException e3) {
                    a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                    j.a(rs, pstm, con);
                    continue;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
        for (i clan : this.b.values()) {
            clan.c().a();
        }
    }

    public void a(i clan) {
        for (i currentClan : this.b.values()) {
            if (!currentClan.f().equalsIgnoreCase(clan.f())) continue;
            return;
        }
        this.b.put(clan.e(), clan);
    }

    public i a(u player, String clan_name) {
        i clan;
        block6: {
            for (i oldClans : this.b.values()) {
                if (!oldClans.f().equalsIgnoreCase(clan_name)) continue;
                return null;
            }
            clan = new i();
            clan.c(d.a().d());
            clan.e(clan_name);
            clan.f(player.fr());
            clan.g(player.et());
            clan.g(0);
            clan.h(0);
            clan.a(new Timestamp(System.currentTimeMillis()));
            clan.f("");
            clan.d(0);
            clan.e(0);
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO clan_data SET clan_id=?, clan_name=?, leader_id=?, leader_name=?, hascastle=?, hashouse=?, found_date=?, announcement=?, emblem_id=?, emblem_status=?");
                    pstm.setInt(1, clan.e());
                    pstm.setString(2, clan.f());
                    pstm.setInt(3, clan.k());
                    pstm.setString(4, clan.l());
                    pstm.setInt(5, clan.m());
                    pstm.setInt(6, clan.n());
                    pstm.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
                    pstm.setString(8, "");
                    pstm.setInt(9, 0);
                    pstm.setInt(10, 0);
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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
        this.b.put(clan.e(), clan);
        player.ah(clan.e());
        player.c(clan.f());
        player.ai(10);
        player.a(new cm(27, 10, player.et()));
        clan.a(player.et());
        player.I();
        return clan;
    }

    public boolean b(i clan) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("UPDATE clan_data SET clan_id=?, leader_id=?, leader_name=?, hascastle=?, hashouse=?, found_date=?, announcement=?, emblem_id=?, emblem_status=?, watch_clanid=? WHERE clan_name=?");
            pstm.setInt(1, clan.e());
            pstm.setInt(2, clan.k());
            pstm.setString(3, clan.l());
            pstm.setInt(4, clan.m());
            pstm.setInt(5, clan.n());
            pstm.setTimestamp(6, clan.g());
            pstm.setString(7, clan.h());
            pstm.setInt(8, clan.i());
            pstm.setInt(9, clan.j());
            pstm.setString(10, clan.d());
            pstm.setString(11, clan.f());
            pstm.execute();
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

    public void a(String clan_name) {
        i clan = this.c(clan_name);
        if (clan == null) {
            return;
        }
        this.b.remove(clan.e());
    }

    public void b(String clan_name) {
        i clan = this.c(clan_name);
        if (clan == null) {
            return;
        }
        Connection con = null;
        try {
            con = l1j.server.b.a().b();
            con.setAutoCommit(false);
            try (PreparedStatement pstm = con.prepareStatement("DELETE FROM clan_warehouse_history WHERE clan_id=?")) {
                pstm.setInt(1, clan.e());
                pstm.executeUpdate();
            }
            try (PreparedStatement pstm = con.prepareStatement("DELETE FROM clan_data WHERE clan_name=?")) {
                pstm.setString(1, clan_name);
                pstm.executeUpdate();
            }
            con.commit();
        }
        catch (SQLException e2) {
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException ignored) {
                }
            }
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return;
        }
        finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                }
                catch (SQLException ignored) {
                }
            }
            j.a(con);
        }
        clan.c().g();
        clan.c().b();
        this.b.remove(clan.e());
    }


    public i a(int clan_id) {
        return this.b.get(clan_id);
    }

    public i c(String clanName) {
        for (i clan : this.b.values()) {
            if (!clan.f().equals(clanName)) continue;
            return clan;
        }
        return null;
    }

    public ConcurrentHashMap<Integer, i> b() {
        return this.b;
    }
}

