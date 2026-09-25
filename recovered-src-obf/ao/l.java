/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ap.q;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class l {
    private static final Logger a = Logger.getLogger(l.class.getName());
    private static l b;

    public static l a() {
        if (b == null) {
            b = new l();
        }
        return b;
    }

    public l() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE character_items SET item_id = ? WHERE item_id >= ? AND item_id<=?");
                    pstm.setInt(1, 640638);
                    pstm.setInt(2, 640626);
                    pstm.setInt(3, 640637);
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

    public List<q> a(int objId) throws Exception {
        ArrayList<q> items = new ArrayList<q>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT * FROM character_items WHERE char_id = ?");
            pstm.setInt(1, objId);
            rs = pstm.executeQuery();
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                int count = rs.getInt("count");
                bh.j itemTemplate = ah.a().a(itemId);
                if (itemTemplate == null) {
                    System.out.println(String.format("item id:%d not found", itemId));
                    continue;
                }
                q item = new q(itemTemplate, count);
                item.cF(rs.getInt("id"));
                item.l(rs.getInt("super_enchant_field_1"));
                item.m(rs.getInt("super_enchant_field_2"));
                item.n(rs.getInt("super_enchant_field_3"));
                item.o(rs.getInt("super_enchant_field_4"));
                item.a(rs.getInt("enchantlvl"));
                item.n();
                item.b(rs.getBoolean("is_equipped"));
                item.a(rs.getBoolean("is_id"));
                item.b(rs.getInt("durability"));
                item.g(rs.getInt("charge_count"));
                item.j(rs.getInt("temp_value"));
                item.a(rs.getTimestamp("last_used"));
                item.f(rs.getInt("bless"));
                item.h(rs.getInt("attr_enchant_kind"));
                item.i(rs.getInt("attr_enchant_level"));
                item.b(rs.getTimestamp("limit_time"));
                item.q();
                if (item.N() == 310 || item.N() == 640354 || item.N() == 640355) {
                    this.a(item);
                    continue;
                }
                items.add(item);
            }
        }
        catch (Throwable throwable) {
            j.a(rs, pstm, con);
            throw throwable;
        }
        j.a(rs, pstm, con);
        return items;
    }

    public void a(int objId, q item) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = l1j.server.b.a().b();
                pstm = con.prepareStatement("INSERT INTO character_items SET id = ?, item_id = ?, char_id = ?, item_name = ?, count = ?, enchantlvl = ?, is_id = ?, durability = ?, charge_count = ?, temp_value = ?, last_used = ?, bless = ?, attr_enchant_kind = ?, attr_enchant_level = ?,super_enchant_field_1 = ? ,super_enchant_field_2 = ?,super_enchant_field_3 = ? ,super_enchant_field_4=? ,limit_time=?,is_equipped=?");
                pstm.setInt(1, item.fr());
                pstm.setInt(2, item.N());
                pstm.setInt(3, objId);
                pstm.setString(4, item.a().h());
                pstm.setInt(5, item.E());
                pstm.setInt(6, item.G());
                pstm.setInt(7, item.C() ? 1 : 0);
                pstm.setInt(8, item.H());
                pstm.setInt(9, item.I());
                pstm.setInt(10, item.M());
                pstm.setTimestamp(11, item.J());
                pstm.setInt(12, item.F());
                pstm.setInt(13, item.K());
                pstm.setInt(14, item.L());
                pstm.setInt(15, item.X());
                pstm.setInt(16, item.Y());
                pstm.setInt(17, item.Z());
                pstm.setInt(18, item.aa());
                pstm.setTimestamp(19, item.bb());
                pstm.setBoolean(20, item.D());
                pstm.execute();
            }
            catch (SQLException e2) {
                a.log(Level.SEVERE, "[\u91cd\u8907\u7684item_objid] itemid=" + item.N() + " count=" + item.E() + " enchant=" + item.G() + " bless=" + item.F());
                throw e2;
            }
        }
        catch (Throwable throwable) {
            j.a(pstm);
            j.a(con);
            throw throwable;
        }
        j.a(pstm);
        j.a(con);
        item.q();
    }

    public void p(q item) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("UPDATE character_items SET item_id=?, item_name=?, count=?, enchantlvl=?, is_id=?, durability=?, charge_count=?, temp_value=?, last_used=?, bless=?, attr_enchant_kind=?, attr_enchant_level=?, super_enchant_field_1=?, super_enchant_field_2=?, super_enchant_field_3=?, super_enchant_field_4=?, limit_time=?, is_equipped=? WHERE id=?");
            pstm.setInt(1, item.N());
            pstm.setString(2, item.a().h());
            pstm.setInt(3, item.E());
            pstm.setInt(4, item.G());
            pstm.setInt(5, item.C() ? 1 : 0);
            pstm.setInt(6, item.H());
            pstm.setInt(7, item.I());
            pstm.setInt(8, item.M());
            pstm.setTimestamp(9, item.J());
            pstm.setInt(10, item.F());
            pstm.setInt(11, item.K());
            pstm.setInt(12, item.L());
            pstm.setInt(13, item.X());
            pstm.setInt(14, item.Y());
            pstm.setInt(15, item.Z());
            pstm.setInt(16, item.aa());
            pstm.setTimestamp(17, item.bb());
            pstm.setBoolean(18, item.D());
            pstm.setInt(19, item.fr());
            if (pstm.executeUpdate() != 1) {
                throw new SQLException("character_items update affected unexpected row count: " + item.fr());
            }
        }
        finally {
            j.a(pstm);
            j.a(con);
        }
    }

    public void a(q item) throws Exception {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM character_items WHERE id = ?");
                    pstm.setInt(1, item.fr());
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

    public void b(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET item_id = ? WHERE id = ?", item.N());
        this.a(item.fr(), "UPDATE character_items SET item_name = ? WHERE id = ?", item.a().h());
    }

    public void c(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET count = ? WHERE id = ?", item.E());
    }

    public void d(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET durability = ? WHERE id = ?", item.H());
    }

    public void e(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET charge_count = ? WHERE id = ?", item.I());
    }

    public void f(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET temp_value = ? WHERE id = ?", item.M());
    }

    public void g(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET limit_time = ? WHERE id = ?", item.bb());
    }

    public void h(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET enchantlvl = ? WHERE id = ?", item.G());
    }

    public void i(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET is_equipped = ? WHERE id = ?", item.D() ? 1 : 0);
    }

    public void j(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET is_id = ? WHERE id = ?", item.C() ? 1 : 0);
    }

    public void k(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET last_used = ? WHERE id = ?", item.J());
    }

    public void l(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET bless = ? WHERE id = ?", item.F());
    }

    public void m(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET attr_enchant_kind = ? WHERE id = ?", item.K());
    }

    public void n(q item) throws Exception {
        this.a(item.fr(), "UPDATE character_items SET attr_enchant_level = ? WHERE id = ?", item.L());
    }

    public void o(q item) throws Exception {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("UPDATE character_items SET super_enchant_field_1 =?,super_enchant_field_2 = ?,super_enchant_field_3 = ?,super_enchant_field_4 = ?  WHERE id = ?");
            pstm.setInt(1, item.X());
            pstm.setInt(2, item.Y());
            pstm.setInt(3, item.Z());
            pstm.setInt(4, item.aa());
            pstm.setInt(5, item.fr());
            pstm.execute();
        }
        catch (Throwable throwable) {
            j.a(pstm);
            j.a(con);
            throw throwable;
        }
        j.a(pstm);
        j.a(con);
    }

    public void persistInventoryState(int charId, q item, int expectedCount) throws SQLException {
        try (Connection con = l1j.server.b.a().b();
             PreparedStatement pstm = con.prepareStatement(
                     "UPDATE character_items SET item_id=?, item_name=?, count=?, enchantlvl=?, is_id=?, durability=?, charge_count=?, temp_value=?, last_used=?, bless=?, attr_enchant_kind=?, attr_enchant_level=?, super_enchant_field_1=?, super_enchant_field_2=?, super_enchant_field_3=?, super_enchant_field_4=?, limit_time=?, is_equipped=? WHERE id=? AND char_id=? AND count=?")) {
            pstm.setInt(1, item.N());
            pstm.setString(2, item.a().h());
            pstm.setInt(3, item.E());
            pstm.setInt(4, item.G());
            pstm.setInt(5, item.C() ? 1 : 0);
            pstm.setInt(6, item.H());
            pstm.setInt(7, item.I());
            pstm.setInt(8, item.M());
            pstm.setTimestamp(9, item.J());
            pstm.setInt(10, item.F());
            pstm.setInt(11, item.K());
            pstm.setInt(12, item.L());
            pstm.setInt(13, item.X());
            pstm.setInt(14, item.Y());
            pstm.setInt(15, item.Z());
            pstm.setInt(16, item.aa());
            pstm.setTimestamp(17, item.bb());
            pstm.setBoolean(18, item.D());
            pstm.setInt(19, item.fr());
            pstm.setInt(20, charId);
            pstm.setInt(21, expectedCount);
            if (pstm.executeUpdate() != 1) {
                throw new SQLException("BUG-850-059 inventory state CAS failed");
            }
        }
    }

    public void insertShopWorldClaim(Connection con, int charId, q item) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO character_items SET id = ?, item_id = ?, char_id = ?, item_name = ?, count = ?, enchantlvl = ?, is_id = ?, durability = ?, charge_count = ?, temp_value = ?, last_used = ?, bless = ?, attr_enchant_kind = ?, attr_enchant_level = ?,super_enchant_field_1 = ? ,super_enchant_field_2 = ?,super_enchant_field_3 = ? ,super_enchant_field_4=? ,limit_time=?,is_equipped=?")) {
            pstm.setInt(1, item.fr());
            pstm.setInt(2, item.N());
            pstm.setInt(3, charId);
            pstm.setString(4, item.a().h());
            pstm.setInt(5, item.E());
            pstm.setInt(6, item.G());
            pstm.setInt(7, item.C() ? 1 : 0);
            pstm.setInt(8, item.H());
            pstm.setInt(9, item.I());
            pstm.setInt(10, item.M());
            pstm.setTimestamp(11, item.J());
            pstm.setInt(12, item.F());
            pstm.setInt(13, item.K());
            pstm.setInt(14, item.L());
            pstm.setInt(15, item.X());
            pstm.setInt(16, item.Y());
            pstm.setInt(17, item.Z());
            pstm.setInt(18, item.aa());
            pstm.setTimestamp(19, item.bb());
            pstm.setBoolean(20, item.D());
            if (pstm.executeUpdate() != 1) {
                throw new SQLException("BUG-850-032 inventory insert affected unexpected row count");
            }
        }
    }

    public void updateShopWorldClaimCount(Connection con, int charId, q item, int expectedCount, int newCount) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement("UPDATE character_items SET count=? WHERE id=? AND char_id=? AND count=?")) {
            pstm.setInt(1, newCount);
            pstm.setInt(2, item.fr());
            pstm.setInt(3, charId);
            pstm.setInt(4, expectedCount);
            if (pstm.executeUpdate() != 1) {
                throw new SQLException("BUG-850-032 inventory stack CAS failed");
            }
        }
    }

    public Integer readShopWorldItemCount(int charId, int itemObjId) throws SQLException {
        try (Connection con = l1j.server.b.a().b();
             PreparedStatement pstm = con.prepareStatement("SELECT count FROM character_items WHERE id=? AND char_id=?")) {
            pstm.setInt(1, itemObjId);
            pstm.setInt(2, charId);
            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next() ? Integer.valueOf(rs.getInt("count")) : null;
            }
        }
    }

    public void insertQuestReward(Connection con, int charId, q item) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement(
                "INSERT INTO character_items SET id = ?, item_id = ?, char_id = ?, item_name = ?, count = ?, enchantlvl = ?, is_id = ?, durability = ?, charge_count = ?, temp_value = ?, last_used = ?, bless = ?, attr_enchant_kind = ?, attr_enchant_level = ?,super_enchant_field_1 = ? ,super_enchant_field_2 = ?,super_enchant_field_3 = ? ,super_enchant_field_4=? ,limit_time=?,is_equipped=?")) {
            pstm.setInt(1, item.fr());
            pstm.setInt(2, item.N());
            pstm.setInt(3, charId);
            pstm.setString(4, item.a().h());
            pstm.setInt(5, item.E());
            pstm.setInt(6, item.G());
            pstm.setInt(7, item.C() ? 1 : 0);
            pstm.setInt(8, item.H());
            pstm.setInt(9, item.I());
            pstm.setInt(10, item.M());
            pstm.setTimestamp(11, item.J());
            pstm.setInt(12, item.F());
            pstm.setInt(13, item.K());
            pstm.setInt(14, item.L());
            pstm.setInt(15, item.X());
            pstm.setInt(16, item.Y());
            pstm.setInt(17, item.Z());
            pstm.setInt(18, item.aa());
            pstm.setTimestamp(19, item.bb());
            pstm.setBoolean(20, item.D());
            if (pstm.executeUpdate() != 1) {
                throw new SQLException("BUG-850-275 reward insert affected unexpected row count");
            }
        }
    }

    public void updateQuestRewardCount(Connection con, int charId, q item, int expectedCount, int newCount) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement(
                "UPDATE character_items SET count=? WHERE id=? AND char_id=? AND count=?")) {
            pstm.setInt(1, newCount);
            pstm.setInt(2, item.fr());
            pstm.setInt(3, charId);
            pstm.setInt(4, expectedCount);
            if (pstm.executeUpdate() != 1) {
                throw new SQLException("BUG-850-275 reward stack CAS failed");
            }
        }
    }

    public void deleteQuestRewardItem(Connection con, int charId, q item, int expectedCount) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement(
                "DELETE FROM character_items WHERE id=? AND char_id=? AND count=?")) {
            pstm.setInt(1, item.fr());
            pstm.setInt(2, charId);
            pstm.setInt(3, expectedCount);
            if (pstm.executeUpdate() != 1) {
                throw new SQLException("BUG-850-275 reward delete CAS failed");
            }
        }
    }

    private void a(int objId, String sql, int updateNum) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement(sql.toString());
            pstm.setInt(1, updateNum);
            pstm.setInt(2, objId);
            pstm.execute();
        }
        catch (Throwable throwable) {
            j.a(pstm);
            j.a(con);
            throw throwable;
        }
        j.a(pstm);
        j.a(con);
    }

    private void a(int objId, String sql, String s2) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement(sql.toString());
            pstm.setString(1, s2);
            pstm.setInt(2, objId);
            pstm.execute();
        }
        catch (Throwable throwable) {
            j.a(pstm);
            j.a(con);
            throw throwable;
        }
        j.a(pstm);
        j.a(con);
    }

    private void a(int objId, String sql, Timestamp ts) throws SQLException {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement(sql.toString());
            pstm.setTimestamp(1, ts);
            pstm.setInt(2, objId);
            pstm.execute();
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

