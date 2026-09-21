/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ap.u;
import be.cm;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class k {
    private static final Logger a = Logger.getLogger(k.class.getName());
    private static k b;
    private final HashMap<Integer, a> c = new HashMap();
    private final HashMap<String, b> d = new HashMap();

    public static k a() {
        if (b == null) {
            b = new k();
        }
        return b;
    }

    public k() {
        this.b();
        this.c();
    }

    private void b() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM gift");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int index = rs.getInt("index");
                        int itemid = rs.getInt("itemid");
                        String key = String.valueOf(index) + "-" + itemid;
                        if (this.d.containsKey(key)) continue;
                        b data = new b();
                        data.a = index;
                        data.b = itemid;
                        data.c = rs.getInt("count");
                        data.d = rs.getInt("enchant");
                        data.e = rs.getString("activate");
                        this.d.put(key, data);
                    }
                }
                catch (Exception e2) {
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

    private void c() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_gift");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int objid = rs.getInt("objid");
                        byte[] data = rs.getBytes("data");
                        if (this.c.containsKey(objid)) continue;
                        a gift = new a(objid);
                        gift.b = data;
                        this.c.put(objid, gift);
                    }
                }
                catch (Exception e2) {
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

    private void b(u pc, int index) {
        String classType = pc.aC().h();
        for (b data : this.d.values()) {
            if (data.a != index || !data.e.contains(classType) && !data.e.contains("A")) continue;
            ah.a(pc, data.b, data.c, data.d);
        }
    }

    public void a(u pc) {
        a gift;
        if (this.c.containsKey(pc.fr())) {
            gift = this.c.get(pc.fr());
        } else {
            gift = new a(pc.fr());
            this.a(gift);
        }
        pc.a(new cm(188, gift.b));
    }

    public void a(u pc, int index) {
        if (!this.c.containsKey(pc.fr())) {
            System.out.println("CharacterGiftTable has some error , ID=" + pc.fr());
            return;
        }
        a gift = this.c.get(pc.fr());
        if (index < gift.b.length && gift.b[index] == 0) {
            gift.b[index] = 1;
            this.b(gift);
            this.b(pc, index);
        }
    }

    private void a(a gift) {
        block6: {
            if (this.c.containsKey(gift.a)) {
                return;
            }
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_gift SET objid=?, data=?");
                    pstm.setInt(1, gift.a);
                    pstm.setBytes(2, gift.b);
                    pstm.execute();
                    this.c.put(gift.a, gift);
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
    }

    private void b(a gift) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE character_gift SET  data=? WHERE objid=" + gift.a);
                    pstm.setBytes(1, gift.b);
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

    private class a {
        public int a = 0;
        public byte[] b = new byte[512];

        private a(int _objid) {
            this.a = _objid;
        }
    }

    private class b {
        public int a;
        public int b;
        public int c;
        public int d;
        public String e;

        private b() {
        }
    }
}

