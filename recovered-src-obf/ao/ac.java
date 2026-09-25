/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ap.t;
import ap.u;
import aq.aq;
import be.bd;
import be.be;
import be.ds;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ac {
    private static final Logger a = Logger.getLogger(ac.class.getName());
    private static ac b;
    private final HashMap<String, a> c;

    public static ac a() {
        if (b == null) {
            b = new ac();
        }
        return b;
    }

    private ac() {
        block6: {
            this.c = new HashMap();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM html_craft");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        a data = new a();
                        data.a = rs.getString("action");
                        data.b = rs.getInt("npcid");
                        data.c = this.a(rs.getString("craft_itemid"));
                        data.d = this.a(rs.getString("craft_count"));
                        data.e = this.a(rs.getString("material"));
                        data.f = this.a(rs.getString("material_count"));
                        data.g = rs.getString("success_html");
                        data.h = rs.getString("fail_html");
                        data.i = rs.getInt("isInputable") == 1;
                        String key = String.valueOf(data.a) + "-" + data.b;
                        this.c.put(key, data);
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

    private int[] a(String s2) {
        if (s2.endsWith(",")) {
            s2 = s2.substring(0, s2.length() - 1);
        }
        String[] splite = s2.split(",");
        int[] result = new int[splite.length];
        try {
            int i2 = 0;
            while (i2 < splite.length) {
                result[i2] = Integer.parseInt(splite[i2]);
                ++i2;
            }
        }
        catch (NumberFormatException e2) {
            System.out.println("--------DB:html_craft Error:[" + s2 + "]--------");
        }
        return result;
    }

    public boolean a(String action, u pc, t npc, int amount) {
        a data = this.c.get(String.valueOf(action) + "-" + npc.z());
        if (data == null) {
            data = this.c.get(String.valueOf(action) + "-0");
        }
        if (data == null) {
            return false;
        }
        boolean isChecked = true;
        int i2 = 0;
        while (i2 < data.e.length) {
            if (!pc.j().h(data.e[i2], data.f[i2] * amount)) {
                bh.j l1item = ah.a().a(data.e[i2]);
                pc.a(new ds(337, String.valueOf(l1item.j()) + "(" + data.f[i2] * amount + ")"));
                isChecked = false;
            }
            ++i2;
        }
        if (!isChecked) {
            pc.a(new be(npc.fr(), data.h));
            return true;
        }
        i2 = 0;
        while (i2 < data.e.length) {
            pc.j().b(data.e[i2], data.f[i2] * amount);
            ++i2;
        }
        i2 = 0;
        while (i2 < data.c.length) {
            ah.a(pc, data.c[i2], data.d[i2] * amount);
            if (data.c[i2] >= 640626 && data.c[i2] <= 640637) {
                aq.a().a(new ds(2922));
            }
            ++i2;
        }
        pc.a(new be(npc.fr(), data.g));
        return true;
    }

    public boolean a(String action, u pc, t npc) {
        int numOfMaterials;
        a data = this.c.get(String.valueOf(action) + "-" + npc.z());
        if (data == null) {
            data = this.c.get(String.valueOf(action) + "-0");
        }
        if (data == null) {
            return false;
        }
        if (data.i && (numOfMaterials = this.a(pc, data)) > 1) {
            pc.a(new bd(npc.fr(), numOfMaterials, action));
            return true;
        }
        return this.a(action, pc, npc, 1);
    }

    private int a(u pc, a data) {
        int count = Integer.MAX_VALUE;
        int i2 = 0;
        while (i2 < data.e.length) {
            int numOfSet = pc.j().g(data.e[i2]) / data.f[i2];
            count = Math.min(count, numOfSet);
            ++i2;
        }
        return count;
    }

    private class a {
        public String a;
        public int b;
        public int[] c;
        public int[] d;
        public int[] e;
        public int[] f;
        public String g;
        public String h;
        public boolean i;

        private a() {
        }
    }
}

