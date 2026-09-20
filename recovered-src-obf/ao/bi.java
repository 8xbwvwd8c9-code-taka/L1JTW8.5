/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ao.bj;
import ap.ab;
import ap.u;
import aq.aq;
import bd.i;
import bi.e;
import bi.h;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class bi {
    private static final Logger a = Logger.getLogger(bi.class.getName());
    private final CopyOnWriteArrayList<ab> b = new CopyOnWriteArrayList();
    private final ArrayList<ab> c = new ArrayList();
    private Timer d = new Timer();
    private static bi e;

    private bi() {
        this.d();
    }

    public static bi a() {
        if (e == null) {
            e = new bi();
        }
        return e;
    }

    private void d() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM spawnlist_trap");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int trapId = rs.getInt("trapId");
                        i trapTemp = bj.a().a(trapId);
                        aq.u loc = new aq.u();
                        loc.a(rs.getInt("mapId"));
                        loc.b(rs.getInt("locX"));
                        loc.c(rs.getInt("locY"));
                        h rndPt = new h();
                        rndPt.b(rs.getInt("locRndX"));
                        rndPt.c(rs.getInt("locRndY"));
                        int count = rs.getInt("count");
                        int span = rs.getInt("span");
                        int i2 = 0;
                        while (i2 < count) {
                            ab trap = new ab(ai.d.a().c(), trapTemp, loc, rndPt, span);
                            aq.a().c(trap);
                            this.b.add(trap);
                            ++i2;
                        }
                        ab base = new ab(ai.d.a().c(), loc);
                        aq.a().c(base);
                        this.c.add(base);
                    }
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

    public void a(ab trap) {
        aq.a().c(trap);
        this.b.add(trap);
    }

    public void b(ab trap) {
        aq.a().d(trap);
        this.b.remove(trap);
    }

    public static void b() {
        bj.b();
        bi oldInstance = e;
        e = new bi();
        oldInstance.e();
        bi.a(oldInstance.b);
        bi.a(oldInstance.c);
    }

    private static void a(List<ab> traps) {
        for (ab trap : traps) {
            trap.c();
            aq.a().d(trap);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void e() {
        bi bi2 = this;
        synchronized (bi2) {
            this.d.cancel();
            this.d = new Timer();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void c(ab trap) {
        trap.c();
        bi bi2 = this;
        synchronized (bi2) {
            bi.e.a().a(new a(trap), trap.e());
        }
    }

    public void c() {
        for (ab trap : this.b) {
            trap.a();
            trap.b();
        }
    }

    public void a(u player) {
        aq.u loc = player.fu();
        for (ab trap : this.b) {
            if (!trap.d() || !loc.equals(trap.fu())) continue;
            trap.d(player);
            this.c(trap);
        }
    }

    public void b(u caster) {
        aq.u loc = caster.fu();
        for (ab trap : this.b) {
            if (!trap.d() || !loc.e(trap.fu())) continue;
            trap.f();
            this.c(trap);
        }
    }

    private class a
    extends TimerTask {
        private final ab b;

        public a(ab trap) {
            this.b = trap;
        }

        @Override
        public void run() {
            this.b.a();
            this.b.b();
        }
    }
}

