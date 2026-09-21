/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.ah;
import ap.e;
import ap.q;
import ap.s;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import be.ba;
import be.bv;
import be.cm;
import be.do;
import be.r;
import be.y;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class p {
    private static final Logger b = Logger.getLogger(p.class.getName());
    private static p c;
    private final long d = 86400000L;
    public boolean a;

    public static p a() {
        if (c == null) {
            c = new p();
        }
        return c;
    }

    private p() {
        block7: {
            this.d = 86400000L;
            this.a = false;
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_items WHERE item_id=?");
                    pstm.setInt(1, 640102);
                    rs = pstm.executeQuery();
                    while (rs.next() && !this.a) {
                        int objid = rs.getInt("char_id");
                        Timestamp ts = rs.getTimestamp("last_used");
                        long period = System.currentTimeMillis() - ts.getTime();
                        if (period > 86400000L) {
                            this.a(640102);
                            continue;
                        }
                        this.a(objid, period);
                    }
                }
                catch (SQLException e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    public void a(int objid, long period) {
        this.a = true;
        long remainTimeMillis = 86400000L - period;
        if (period == 0L) {
            aq.a().a(new cm(84, 2, "\\f=\u6709\u4eba\u7372\u5f97\u4e86\u5b88\u8b77\u8005\u7684\u9748\u9b42\u3002"));
        }
        bi.e.a().a(new a(objid), remainTimeMillis);
        System.out.println("\u4efb\u52d9:\u3010\u5b88\u8b77\u4e4b\u9b42\u3011\u5269\u9918\u6642\u9593 " + remainTimeMillis / 1000L + " \u79d2");
    }

    private void a(int itemid) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM character_items WHERE item_id =" + itemid);
                    pstm.execute();
                    System.out.println("\u9053\u5177:\u3010\u5b88\u8b77\u8005\u7684\u9748\u9b42\u3011\u7684\u6301\u6709\u7d00\u9304\u5f9e\u8cc7\u6599\u5eab\u79fb\u9664\u4e86");
                }
                catch (SQLException e2) {
                    b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
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

    public void a(s mob) {
        if (this.a) {
            return;
        }
        int prob = 1 + mob.ev() / 2;
        int rnd = i.a(100000);
        if (rnd < prob) {
            q item = ah.a().b(640102);
            mob.y().d(item);
        }
    }

    private class a
    extends TimerTask {
        private final int b;

        public a(int id) {
            this.b = id;
        }

        @Override
        public void run() {
            try {
                p.this.a = false;
                aq.a().a(new cm(84, 2, "\\f=\u5b88\u8b77\u8005\u7684\u9748\u9b42\u6d88\u901d\u4e86\u3002"));
                aa obj = aq.a().a(this.b);
                if (obj instanceof u) {
                    u pc = (u)obj;
                    q item = pc.j().b(640102);
                    if (item != null) {
                        pc.j().b(item, item.E());
                    }
                    pc.bz(4058);
                    pc.cp(-30);
                    pc.a(new do(pc));
                    pc.bH(-400);
                    pc.bJ(-200);
                    pc.a(new bv(pc.eb(), pc.ex()));
                    pc.a(new ba(pc.ea(), pc.ew()));
                    if (pc.q()) {
                        pc.aL().f(pc);
                    }
                    pc.a(new cm(144, 0));
                    pc.a(new r(pc.fr(), pc.et()));
                    pc.b(new r(pc.fr(), pc.et()));
                    for (t npc : pc.ek().values()) {
                        npc.b(new y(npc.fr(), String.valueOf(pc.et()) + "\u7684"));
                    }
                    for (e doll : pc.el().values()) {
                        doll.b(new y(doll.fr(), String.valueOf(pc.et()) + "\u7684"));
                    }
                } else {
                    p.this.a(640102);
                }
            }
            catch (Throwable e2) {
                b.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

