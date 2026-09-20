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
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class bd {
    private static final Logger a = Logger.getLogger(bd.class.getName());
    private static bd b;
    private final HashMap<Integer, b> c = new HashMap();
    private final ConcurrentHashMap<String, a> d = new ConcurrentHashMap();

    public static bd a() {
        if (b == null) {
            b = new bd();
        }
        return b;
    }

    private bd() {
        this.d();
        this.c();
    }

    private void c() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM shop_world");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int itemid = rs.getInt("itemid");
                        q item = ah.a().b(itemid);
                        b data = new b(item);
                        data.b = rs.getInt("price");
                        data.c = rs.getInt("type");
                        data.d = rs.getInt("vip");
                        data.e = rs.getBoolean("isHot");
                        data.f = rs.getBoolean("isNew");
                        this.c.put(item.N(), data);
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

    public HashMap<Integer, b> b() {
        return this.c;
    }

    public ConcurrentHashMap<Integer, q> a(String acc) {
        if (this.d.containsKey(acc)) {
            return this.d.get(acc).b;
        }
        return new ConcurrentHashMap<Integer, q>();
    }

    private void d() {
        block8: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM character_shop");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        String acc = rs.getString("acc_name");
                        a data = null;
                        if (this.d.containsKey(acc)) {
                            data = this.d.get(acc);
                        } else {
                            data = new a();
                            this.d.put(acc, data);
                        }
                        int index = rs.getInt("indexid");
                        int itemid = rs.getInt("itemid");
                        q item = ah.a().b(itemid);
                        data.b.put(index, item);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block8;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public void a(String acc, int itemid, int count) {
        block11: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_shop SET acc_name=?,itemid=?,indexid=?");
                    int c2 = 0;
                    while (c2 < count) {
                        a data;
                        q item = ah.a().b(itemid);
                        int index = 1;
                        if (this.d.containsKey(acc)) {
                            data = this.d.get(acc);
                            int i2 = 1;
                            while (i2 < Integer.MAX_VALUE) {
                                if (!data.b.containsKey(i2)) {
                                    index = i2;
                                    break;
                                }
                                ++i2;
                            }
                        } else {
                            data = new a();
                            this.d.put(acc, data);
                        }
                        data.b.put(index, item);
                        pstm.setString(1, acc);
                        pstm.setInt(2, itemid);
                        pstm.setInt(3, index);
                        pstm.execute();
                        ++c2;
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block11;
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

    public void a(String acc, int index) {
        block7: {
            if (this.d.containsKey(acc)) {
                a data = this.d.get(acc);
                data.b.remove(index);
            } else {
                System.out.println("ShopWorldTable has smoe error");
            }
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM character_shop WHERE acc_name=? AND indexid=?");
                    pstm.setString(1, acc);
                    pstm.setInt(2, index);
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block7;
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

    private class a {
        private final ConcurrentHashMap<Integer, q> b = new ConcurrentHashMap();

        private a() {
        }
    }

    public class b {
        public q a;
        public int b;
        public int c;
        public int d;
        public boolean e;
        public boolean f;

        private b(q _item) {
            this.a = _item;
        }
    }
}

