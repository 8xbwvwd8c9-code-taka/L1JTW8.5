/*
 * Decompiled with CFR 0.152.
 */
package bh;

import ai.d;
import ap.q;
import ap.u;
import be.cm;
import be.ds;
import be.n;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class c {
    private static final Logger c = Logger.getLogger(c.class.getName());
    private int d;
    private int e;
    private String f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    public static HashMap<String, aq.u> a = new HashMap();
    public static HashMap<String, aq.u> b;

    static {
        a.put("[\u795e\u79d8]\u51b0\u6e56\u6c34\u4e2d\u592e", new aq.u(34002, 32329, 4));
        a.put("[\u795e\u79d8]\u51b0\u4e4b\u5916\u570d", new aq.u(33994, 32332, 4));
        a.put("[\u795e\u7955]\u51b0\u4e4b\u7d55\u58c1", new aq.u(34212, 32343, 4));
        a.put("[\u795e\u79d8]\u4e9e\u4e01\u5c0f\u8239", new aq.u(34194, 33135, 4));
        a.put("[\u795e\u7955]\u6d77\u97f3\u8b66\u5099\u5854", new aq.u(33503, 33505, 4));
        a.put("[\u795e\u79d8]\u9f8d\u4e4b\u8c37\u7d55\u58c1", new aq.u(33466, 32312, 4));
        a.put("[\u795e\u79d8]\u96b1\u85cf\u4e4b\u9f9c", new aq.u(33343, 33163, 4));
        a.put("[\u795e\u7955]\u7cbe\u795e\u8207\u6642\u9593", new aq.u(32555, 32979, 4));
        b = new HashMap();
        b.put("\u6c99\u6f20\u7da0\u6d32", new aq.u(32864, 33252, 4));
        b.put("\u90aa\u60e1\u795e\u6bbf", new aq.u(32885, 32652, 4));
        b.put("\u6b63\u7fa9\u795e\u6bbf", new aq.u(33118, 32936, 4));
        b.put("\u8aaa\u8a71\u4e4b\u5cf6\u5bc4\u990a\u8655", new aq.u(32445, 32991, 0));
        b.put("\u8aaa\u8a71\u4e4b\u5cf6\u2500\u5409\u502b", new aq.u(32559, 33082, 0));
        b.put("\u5947\u5ca9", new aq.u(33430, 32815, 4));
        b.put("\u5947\u5ca9\u2500\u5730\u9593", new aq.u(33305, 33063, 4));
        b.put("\u5947\u5ca9\u2500\u9f8d\u4e00\u6d1e", new aq.u(33413, 32411, 4));
        b.put("\u5947\u5ca9\u2500\u9f8d\u4e00\u89c0\u6230\u5340", new aq.u(33400, 32420, 4));
        b.put("\u5947\u5ca9\u2500\u9f8d\u4e8c\u6d1e", new aq.u(33365, 32386, 4));
        b.put("\u5947\u5ca9\u2500\u9f8d\u4e09\u6d1e", new aq.u(33396, 32326, 4));
        b.put("\u5947\u5ca9\u2500\u9f8d\u4e09\u9ed1\u8001\u5340", new aq.u(33387, 32347, 4));
        b.put("\u5947\u5ca9\u2500\u9f8d\u56db", new aq.u(33348, 32349, 4));
        b.put("\u5947\u5ca9\u2500\u9f8d\u56db\u5de6\u4e0b", new aq.u(33312, 32357, 4));
        b.put("\u5947\u5ca9\u2500\u9f8d\u9aa8\u982d", new aq.u(33281, 32400, 4));
        b.put("\u4e9e\u4e01\u2500\u4e0b\u6c34\u9053", new aq.u(34149, 33382, 4));
        b.put("\u4e9e\u4e01\u2500\u50b2\u6162\u6a4b", new aq.u(34273, 33093, 4));
        b.put("\u4e9e\u4e01\u2500\u9053\u5177\u5e97", new aq.u(34124, 33146, 4));
        b.put("\u4e9e\u4e01\u2500\u5922\u5e7b\u5cf6", new aq.u(33977, 32925, 4));
        b.put("\u4e9e\u4e01\u2500\u6559\u5802", new aq.u(33958, 33365, 4));
        b.put("\u6d77\u97f3", new aq.u(33604, 33234, 4));
        b.put("\u6d77\u97f3\u9053\u5177\u5e97", new aq.u(33063, 32736, 4));
        b.put("\u6d77\u97f3\u2500\u5730\u9593", new aq.u(33621, 33507, 4));
        b.put("\u6d77\u97f3\u2500\u907a\u5fd8\u50b3\u5e2b", new aq.u(33445, 33476, 4));
        b.put("\u6d77\u97f3\u2500\u8b8a\u602a\u5340", new aq.u(33781, 33275, 4));
        b.put("\u71c3\u67f3", new aq.u(32746, 32441, 4));
        b.put("\u71c3\u67f3\u2500\u5996\u9b54\u57ce\u5821", new aq.u(32940, 32281, 4));
        b.put("\u71c3\u67f3\u2500\u90aa\u60e1\u795e\u6bbf", new aq.u(32662, 32303, 4));
        b.put("\u71c3\u67f3\u2500\u7720\u6d1e", new aq.u(32938, 32284, 4));
        b.put("\u8a71\u5cf6", new aq.u(32576, 32945, 0));
        b.put("\u8a71\u5cf6\u2500\u5192\u6d1e", new aq.u(32509, 32864, 0));
        b.put("\u80af\u7279", new aq.u(33072, 32799, 4));
        b.put("\u80af\u7279\u2500\u8461\u8404\u5712", new aq.u(32876, 32797, 4));
        b.put("\u80af\u7279\u2500\u6b63\u7fa9\u795e\u6bbf", new aq.u(33118, 32936, 4));
        b.put("\u98a8\u6728", new aq.u(32611, 33185, 4));
        b.put("\u98a8\u6728\u2500\u87fb\u540e\u6d1e", new aq.u(32795, 33192, 4));
        b.put("\u98a8\u6728\u2500\u62c9\u6d1e", new aq.u(32564, 33460, 4));
        b.put("\u98a8\u6728\u2500\u617e\u671b\u6d1e\u7a74", new aq.u(32760, 33456, 4));
        b.put("\u9a0e\u6751", new aq.u(33072, 33385, 4));
        b.put("\u9a0e\u6751\u2500\u9a0e\u4e00\u6d1e", new aq.u(32970, 33511, 4));
        b.put("\u9a0e\u6751\u2500\u9a0e\u4e09\u6d1e", new aq.u(32883, 33511, 4));
        b.put("\u5a01\u9813", new aq.u(33724, 32488, 4));
        b.put("\u6b50\u745e", new aq.u(34057, 32285, 4));
        b.put("\u6b50\u745e\u2500\u8c61\u7259\u5854", new aq.u(34041, 32155, 4));
        b.put("\u6b50\u745e\u2500\u6c34\u6676\u6d1e\u4e8c\u6a13\u53e3", new aq.u(33969, 32337, 4));
        b.put("\u6b50\u745e\u2500\u5f71\u5b50\u795e\u6bbf\u50b3\u9001\u9ede", new aq.u(34268, 32193, 4));
        b.put("\u53e4\u4e01", new aq.u(32610, 32766, 4));
        b.put("\u53e4\u4e01\u2500\u5730\u9593", new aq.u(32741, 32928, 4));
        b.put("\u53e4\u4e01\u2500\u80af\u7279\u6a4b", new aq.u(32000, 32780, 4));
        b.put("\u53e4\u4e01\u2500\u71c3\u67f3\u6a4b", new aq.u(32654, 32524, 4));
    }

    public static boolean a(int x2, int y2, int mapid) {
        if (mapid == 4) {
            if (x2 >= 33506 && x2 <= 33714 && y2 >= 32217 && y2 <= 32478) {
                return false;
            }
            if (x2 >= 33714 && x2 <= 33819 && y2 >= 32202 && y2 <= 32464) {
                return false;
            }
            if (x2 >= 33446 && x2 <= 33475 && y2 >= 32315 && y2 <= 32354) {
                return false;
            }
            if (x2 >= 33389 && x2 <= 33396 && y2 >= 32340 && y2 <= 32348) {
                return false;
            }
            if (x2 >= 33259 && x2 <= 33266 && y2 >= 32399 && y2 <= 32406) {
                return false;
            }
            if (x2 >= 33332 && x2 <= 33340 && y2 >= 32433 && y2 <= 32440) {
                return false;
            }
            if (x2 >= 34171 && x2 <= 34303 && y2 >= 33086 && y2 <= 33530) {
                return false;
            }
            if (x2 >= 32704 && x2 <= 32837 && y2 >= 33102 && y2 <= 33240) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(CopyOnWriteArrayList<c> bookMarkList) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            con.setAutoCommit(false);
            pstm = con.prepareStatement("UPDATE character_teleport SET order_id=?, order_id_fast=?, name=? WHERE id=?");
            for (c bookmark : bookMarkList) {
                pstm.setInt(1, bookmark.i);
                pstm.setInt(2, bookmark.j);
                pstm.setString(3, bookmark.f);
                pstm.setInt(4, bookmark.e);
                pstm.addBatch();
            }
            pstm.executeBatch();
            con.commit();
            return true;
        }
        catch (SQLException e2) {
            c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException ignored) {
                }
            }
            return false;
        }
        finally {
            bi.j.a(pstm);
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                }
                catch (SQLException ignored) {
                }
            }
            bi.j.a(con);
        }
    }

    public static void a(u pc, String s2) {
        c book = pc.a(s2);
        if (book == null) {
            return;
        }
        int order = book.f();
        Connection con = null;
        PreparedStatement del = null;
        PreparedStatement reorder = null;
        try {
            con = l1j.server.b.a().b();
            con.setAutoCommit(false);
            del = con.prepareStatement("DELETE FROM character_teleport WHERE id=? AND char_id=?");
            del.setInt(1, book.a());
            del.setInt(2, pc.fr());
            if (del.executeUpdate() != 1) {
                con.rollback();
                return;
            }
            reorder = con.prepareStatement("UPDATE character_teleport SET order_id=order_id-1 WHERE char_id=? AND order_id>?");
            reorder.setInt(1, pc.fr());
            reorder.setInt(2, order);
            reorder.executeUpdate();
            con.commit();
        }
        catch (SQLException e2) {
            c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException ignored) {
                }
            }
            return;
        }
        finally {
            bi.j.a(reorder);
            bi.j.a(del);
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                }
                catch (SQLException ignored) {
                }
            }
            bi.j.a(con);
        }
        pc.ba().remove(book);
        for (c bookmark : pc.ba()) {
            if (bookmark.f() > order) {
                bookmark.e(bookmark.f() - 1);
            }
        }
    }

    public static void b(u pc, String s2) {
        if (!pc.fq().h()) {
            pc.a(new ds(214));
            return;
        }
        if (pc.ba().size() >= pc.cI()) {
            return;
        }
        if (pc.a(s2) != null) {
            pc.a(new ds(327));
            return;
        }
        c bookmark = new c();
        bookmark.a(ai.d.a().d());
        bookmark.b(pc.fr());
        bookmark.a(s2);
        bookmark.c(pc.fs());
        bookmark.d(pc.ft());
        bookmark.g(pc.fp());
        bookmark.e(pc.ba().size());

        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("INSERT INTO character_teleport SET id=?, char_id=?, name=?, locx=?, locy=?, mapid=?, order_id=?");
            pstm.setInt(1, bookmark.a());
            pstm.setInt(2, bookmark.b());
            pstm.setString(3, bookmark.c());
            pstm.setInt(4, bookmark.d());
            pstm.setInt(5, bookmark.e());
            pstm.setInt(6, bookmark.h());
            pstm.setInt(7, bookmark.f());
            pstm.executeUpdate();
        }
        catch (SQLException e2) {
            c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return;
        }
        finally {
            bi.j.a(pstm);
            bi.j.a(con);
        }
        pc.ba().add(bookmark);
        pc.a(new n(s2, bookmark.h(), bookmark.a(), bookmark.d(), bookmark.e()));
    }

    public static boolean a(u pc, HashMap<String, aq.u> bookmark_list) {
        if (bookmark_list == null || bookmark_list.isEmpty()) {
            return false;
        }
        ArrayList<c> pending = new ArrayList<c>();
        int order = pc.ba().size();
        for (String s2 : bookmark_list.keySet()) {
            if (pc.a(s2) != null) {
                continue;
            }
            if (order >= pc.cI()) {
                return false;
            }
            aq.u loc = bookmark_list.get(s2);
            c bookmark = new c();
            bookmark.a(ai.d.a().d());
            bookmark.b(pc.fr());
            bookmark.a(s2);
            bookmark.c(loc.f());
            bookmark.d(loc.g());
            bookmark.g((short)loc.b());
            bookmark.e(order++);
            pending.add(bookmark);
        }
        if (pending.isEmpty()) {
            return false;
        }

        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            con.setAutoCommit(false);
            pstm = con.prepareStatement("INSERT INTO character_teleport SET id=?, char_id=?, name=?, locx=?, locy=?, mapid=?, order_id=?");
            for (c bookmark : pending) {
                pstm.setInt(1, bookmark.a());
                pstm.setInt(2, bookmark.b());
                pstm.setString(3, bookmark.c());
                pstm.setInt(4, bookmark.d());
                pstm.setInt(5, bookmark.e());
                pstm.setInt(6, bookmark.h());
                pstm.setInt(7, bookmark.f());
                pstm.addBatch();
            }
            pstm.executeBatch();
            con.commit();
        }
        catch (SQLException e2) {
            c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException ignored) {
                }
            }
            return false;
        }
        finally {
            bi.j.a(pstm);
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                }
                catch (SQLException ignored) {
                }
            }
            bi.j.a(con);
        }

        for (c bookmark : pending) {
            pc.ba().add(bookmark);
            pc.a(new n(bookmark.c(), (short)bookmark.h(), bookmark.a(), bookmark.d(), bookmark.e()));
        }
        return true;
    }

    public static void a(u pc, q item) {
        if (pc == null || item == null) {
            return;
        }
        Connection con = null;
        PreparedStatement delete = null;
        PreparedStatement insert = null;
        try {
            con = l1j.server.b.a().b();
            con.setAutoCommit(false);
            delete = con.prepareStatement("DELETE FROM character_teleport WHERE char_id=?");
            delete.setInt(1, item.fr());
            delete.executeUpdate();
            insert = con.prepareStatement("INSERT INTO character_teleport SET id=?, char_id=?, name=?, locx=?, locy=?, mapid=?, order_id=?");
            for (c bookmark : pc.ba()) {
                insert.setInt(1, ai.d.a().d());
                insert.setInt(2, item.fr());
                insert.setString(3, bookmark.c());
                insert.setInt(4, bookmark.d());
                insert.setInt(5, bookmark.e());
                insert.setInt(6, bookmark.h());
                insert.setInt(7, bookmark.f());
                insert.addBatch();
            }
            insert.executeBatch();
            con.commit();
        }
        catch (SQLException e2) {
            c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException ignored) {
                }
            }
        }
        finally {
            bi.j.a(insert);
            bi.j.a(delete);
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                }
                catch (SQLException ignored) {
                }
            }
            bi.j.a(con);
        }
    }

    public static void a(q item, u pc) {
        if (item == null || pc == null) {
            return;
        }
        ArrayList<c> pending = new ArrayList<c>();
        Connection con = null;
        PreparedStatement select = null;
        PreparedStatement insert = null;
        PreparedStatement deleteBookmarks = null;
        PreparedStatement deleteItem = null;
        ResultSet rs = null;
        try {
            con = l1j.server.b.a().b();
            con.setAutoCommit(false);

            select = con.prepareStatement("SELECT name,locx,locy,mapid FROM character_teleport WHERE char_id=? ORDER BY order_id");
            select.setInt(1, item.fr());
            rs = select.executeQuery();
            int order = pc.ba().size();
            while (rs.next()) {
                String name = rs.getString("name");
                if (pc.a(name) != null) {
                    continue;
                }
                if (order >= pc.cI()) {
                    con.rollback();
                    pc.a(new ds(2961, "1"));
                    return;
                }
                c bookmark = new c();
                bookmark.a(ai.d.a().d());
                bookmark.b(pc.fr());
                bookmark.a(name);
                bookmark.c(rs.getInt("locx"));
                bookmark.d(rs.getInt("locy"));
                bookmark.g(rs.getShort("mapid"));
                bookmark.e(order++);
                pending.add(bookmark);
            }
            if (pending.isEmpty()) {
                con.rollback();
                pc.a(new ds(2963));
                return;
            }

            insert = con.prepareStatement("INSERT INTO character_teleport SET id=?, char_id=?, name=?, locx=?, locy=?, mapid=?, order_id=?");
            for (c bookmark : pending) {
                insert.setInt(1, bookmark.a());
                insert.setInt(2, bookmark.b());
                insert.setString(3, bookmark.c());
                insert.setInt(4, bookmark.d());
                insert.setInt(5, bookmark.e());
                insert.setInt(6, bookmark.h());
                insert.setInt(7, bookmark.f());
                insert.addBatch();
            }
            insert.executeBatch();

            deleteBookmarks = con.prepareStatement("DELETE FROM character_teleport WHERE char_id=?");
            deleteBookmarks.setInt(1, item.fr());
            deleteBookmarks.executeUpdate();

            deleteItem = con.prepareStatement("DELETE FROM character_items WHERE id=? AND char_id=?");
            deleteItem.setInt(1, item.fr());
            deleteItem.setInt(2, pc.fr());
            if (deleteItem.executeUpdate() != 1) {
                con.rollback();
                return;
            }
            con.commit();
        }
        catch (SQLException e2) {
            c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException ignored) {
                }
            }
            return;
        }
        finally {
            bi.j.a(rs, select);
            bi.j.a(insert);
            bi.j.a(deleteBookmarks);
            bi.j.a(deleteItem);
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                }
                catch (SQLException ignored) {
                }
            }
            bi.j.a(con);
        }

        for (c bookmark : pending) {
            pc.ba().add(bookmark);
            pc.a(new n(bookmark.c(), (short)bookmark.h(), bookmark.a(), bookmark.d(), bookmark.e()));
        }
        pc.j().f(item);
    }

    public static void a(u pc) {
        block5: {
            pc.an();
            pc.a(new cm(141, pc.cI()));
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE  characters  SET BookMarkSpace =? WHERE objid=?");
                    pstm.setInt(1, pc.cI());
                    pstm.setInt(2, pc.fr());
                    pstm.execute();
                }
                catch (SQLException e2) {
                    c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(pstm);
                    bi.j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(pstm);
                bi.j.a(con);
                throw throwable;
            }
            bi.j.a(pstm);
            bi.j.a(con);
        }
    }

    public int a() {
        return this.e;
    }

    public void a(int i2) {
        this.e = i2;
    }

    public int b() {
        return this.d;
    }

    public void b(int i2) {
        this.d = i2;
    }

    public String c() {
        return this.f;
    }

    public void a(String s2) {
        this.f = s2;
    }

    public int d() {
        return this.g;
    }

    public void c(int i2) {
        this.g = i2;
    }

    public int e() {
        return this.h;
    }

    public void d(int i2) {
        this.h = i2;
    }

    public int f() {
        return this.i;
    }

    public void e(int i2) {
        this.i = i2;
    }

    public int g() {
        return this.j;
    }

    public void f(int i2) {
        this.j = i2;
    }

    public int h() {
        return this.k;
    }

    public void g(int i2) {
        this.k = i2;
    }
}

