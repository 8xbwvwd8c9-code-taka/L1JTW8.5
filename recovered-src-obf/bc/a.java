/*
 * Decompiled with CFR 0.152.
 */
package bc;

import ao.ah;
import ap.q;
import ap.u;
import aq.aq;
import be.ck;
import be.cm;
import be.dc;
import be.ds;
import be.z;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class a
implements Runnable {
    private static final Logger a = Logger.getLogger(a.class.getName());
    private final int b = 120;
    private final u c;
    private int d = 120;
    private boolean e = false;
    private final q f;
    private final aq.u g;
    private static ArrayList<a> h = new ArrayList();
    private static int i = 0;

    public a(u _pc, q _item) {
        this.c = _pc;
        this.g = _pc.fu();
        this.c.a(this);
        this.f = _item;
        if (_item.N() == 640282) {
            this.d = 40;
            this.e = true;
        }
    }

    @Override
    public void run() {
        try {
            int count = 1;
            this.c.a(new dc(63, this.d, this.e ? 2 : 1));
            while (this.c != null && this.c.cq()) {
                if (!this.c.fu().equals(this.g)) {
                    this.a();
                    break;
                }
                if (count >= this.d) {
                    count = 1;
                    this.a(this.c);
                }
                ++count;
                Thread.sleep(1000L);
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    private void a(u pc) {
        boolean success = false;
        int rndValue = bi.i.a(i + i / 20);
        for (a data : h) {
            if (data.c > rndValue || data.d < rndValue) continue;
            q rndItem = ah.a().b(data.a);
            rndItem.e(data.b);
            this.a(rndItem);
            success = true;
            break;
        }
        if (!success) {
            pc.a(new ds(1517));
        }
        if (!pc.j().b(640270, 1)) {
            pc.a(new ds(1137));
            this.a();
            return;
        }
        this.c();
        pc.a(new dc(63, this.d, this.e ? 2 : 1));
    }

    private void a(q item) {
        this.c.a(new ds(403, item.a().h()));
        this.c.x((int)(2.0 * l1j.server.a.B));
        this.c.a(new ck(this.c));
        if (this.c.j().a(item, 1) == 0) {
            this.c.j().d(item);
            this.c.a(new cm(55, item.m()));
        } else {
            item.d(this.c);
            aq.a().a(this.c.fs(), this.c.ft(), this.c.fp()).d(item);
            this.a();
        }
    }

    public void a() {
        this.c.r(false);
        this.c.a((a)null);
        this.c.a(new z(this.c));
        this.c.b(new z(this.c));
    }

    private void c() {
        if (this.f.N() != 640282) {
            return;
        }
        if (this.f.I() > 1) {
            this.f.g(this.f.I() - 1);
            this.c.j().b(this.f);
        } else {
            this.c.j().b(this.f, 1);
            ah.a(this.c, 640269, 1);
            this.c.a(new ds(1163));
            this.a();
        }
    }

    public static void b() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT *FROM fishing");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int itemid = rs.getInt("itemid");
                        int count = rs.getInt("count");
                        int value = rs.getInt("prab_value");
                        a data = new a(itemid, count);
                        data.c = i + 1;
                        data.d = i += value;
                        h.add(data);
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

    private static class a {
        public int a;
        public int b;
        public int c = 0;
        public int d = 0;

        public a(int _itemid, int _count) {
            this.a = _itemid;
            this.b = _count;
        }
    }
}

