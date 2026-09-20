/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ap.q;
import aq.aq;
import be.ds;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class al {
    private static final Logger a = Logger.getLogger(al.class.getName());
    private static al b;
    private final ArrayList<b> c = new ArrayList();
    private int d = 0;
    private final HashMap<String, a> e = new HashMap();

    public static al a() {
        if (b == null) {
            b = new al();
        }
        return b;
    }

    private al() {
        this.b();
        this.c();
        this.d();
    }

    private void b() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM character_luckydraw");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        String accName = rs.getString("acc_name");
                        if (this.e(accName)) continue;
                        this.d(accName);
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

    private void d(String name) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM character_luckydraw WHERE acc_name=? ");
                    pstm.setString(1, name);
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

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean e(String name) {
        boolean bl2;
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = l1j.server.b.a().b();
            String sqlstr = "SELECT * FROM accounts WHERE login=? LIMIT 1";
            pstm = con.prepareStatement("SELECT * FROM accounts WHERE login=? LIMIT 1");
            pstm.setString(1, name);
            rs = pstm.executeQuery();
            bl2 = rs.next();
        }
        catch (SQLException e2) {
            try {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
            return true;
        }
        j.a(rs, pstm, con);
        return bl2;
    }

    public q a(String charName) {
        q rndItem = null;
        int rndValue = i.a(this.d);
        for (b data : this.c) {
            if (data.e > rndValue || data.f < rndValue) continue;
            rndItem = ah.a().b(data.a);
            rndItem.a(data.b);
            rndItem.e(data.c);
            rndItem.a(true);
            if (i.a(100) < data.d) {
                rndItem.f(0);
            }
            if (data.f - data.e <= 5) {
                aq.a().a(new ds(charName, rndItem));
            }
            return rndItem;
        }
        return null;
    }

    private void c() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM luckydraw");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int itemid = rs.getInt("itemid");
                        int enchant = rs.getInt("enchant");
                        int count = rs.getInt("count");
                        int value = rs.getInt("prab_value");
                        int bless_chance = rs.getInt("bless_chance");
                        b data = new b(itemid, enchant, count, bless_chance);
                        data.e = this.d + 1;
                        this.d += value;
                        data.f = this.d;
                        this.c.add(data);
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

    public int b(String acc) {
        if (this.e.containsKey(acc)) {
            return this.e.get(acc).b + 1;
        }
        return 1;
    }

    public HashMap<Integer, q> c(String acc) {
        if (this.e.containsKey(acc)) {
            return this.e.get(acc).c;
        }
        return new HashMap<Integer, q>();
    }

    private void d() {
        block8: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM character_luckydraw");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        String acc = rs.getString("acc_name");
                        int index = rs.getInt("indexid");
                        int itemid = rs.getInt("itemid");
                        int enchant = rs.getInt("enchant");
                        int count = rs.getInt("count");
                        a data = null;
                        if (this.e.containsKey(acc)) {
                            data = this.e.get(acc);
                        } else {
                            data = new a();
                            this.e.put(acc, data);
                        }
                        data.b = Math.max(data.b, index);
                        q item = ah.a().b(itemid);
                        item.e(count);
                        item.a(enchant);
                        data.c.put(index, item);
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

    public void a(String acc, HashMap<Integer, q> list) {
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            try {
                con = l1j.server.b.a().b();
                for (int key : list.keySet()) {
                    a data;
                    q item = list.get(key);
                    if (this.e.containsKey(acc)) {
                        data = this.e.get(acc);
                    } else {
                        data = new a();
                        this.e.put(acc, data);
                    }
                    data.b = Math.max(data.b, key);
                    data.c.put(key, item);
                    pstm = con.prepareStatement("INSERT INTO character_luckydraw SET acc_name=?,itemid=?,indexid=?,enchant=?,count=?,bless=?");
                    pstm.setString(1, acc);
                    pstm.setInt(2, item.N());
                    pstm.setInt(3, key);
                    pstm.setInt(4, item.G());
                    pstm.setInt(5, item.E());
                    pstm.setInt(6, item.F());
                    pstm.execute();
                }
            }
            catch (SQLException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                j.a(pstm);
                j.a(con);
            }
        }
        finally {
            j.a(pstm);
            j.a(con);
        }
    }

    public void a(String acc, int index) {
        block7: {
            if (this.e.containsKey(acc)) {
                a data = this.e.get(acc);
                data.c.remove(index);
            } else {
                System.out.println("LuckyDrawTable has smoe error");
            }
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM character_luckydraw WHERE acc_name=? AND indexid=?");
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
        private int b = 0;
        private final HashMap<Integer, q> c = new HashMap();

        private a() {
        }
    }

    private class b {
        public int a;
        public int b;
        public int c;
        public int d = 0;
        public int e = 0;
        public int f = 0;

        public b(int _itemid, int _enchant, int _count, int _bless) {
            this.a = _itemid;
            this.b = _enchant;
            this.c = _count;
            this.d = _bless;
        }
    }
}

