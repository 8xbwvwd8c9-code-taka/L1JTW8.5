/*
 * Decompiled with CFR 0.152.
 */
package ai;

import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class d {
    private static final Logger a = Logger.getLogger(d.class.getName());
    private int b;
    private int c = 16;
    private final Object d = new Object();
    private static final int e = 0x10000000;
    private static d f = new d();
    private final AtomicInteger g = new AtomicInteger(0xF000000);

    private d() {
        this.e();
    }

    public static d a() {
        return f;
    }

    public int b() {
        this.g.compareAndSet(0xFFFFFFF, 0xF000000);
        return this.g.getAndIncrement();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int c() {
        Object object = this.d;
        synchronized (object) {
            int result = this.c++;
            if (result == 0xFFFFFFF) {
                System.out.println("IdFactory for Npc out of range");
            }
            int n2 = result++;
            return n2;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int d() {
        Object object = this.d;
        synchronized (object) {
            return this.b++;
        }
    }

    private void e() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("select max(id)+1 as nextid from (select id from character_items union all select id from character_teleport union all select id from character_warehouse union all select id from character_elf_warehouse union all select id from clan_warehouse union all select id from mail union all select objid as id from characters union all select clan_id as id from clan_data union all select objid as id from pets ) t");
                    rs = pstm.executeQuery();
                    int id = 0;
                    if (rs.next()) {
                        id = rs.getInt("nextid");
                    }
                    if (id < 0x10000000) {
                        id = 0x10000000;
                    }
                    this.b = id;
                    System.out.println("\u76ee\u524d\u7684\u7269\u4ef6ID: " + this.b);
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }
}

