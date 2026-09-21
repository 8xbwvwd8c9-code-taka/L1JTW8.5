/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ao.au;
import ap.s;
import ap.t;
import aq.aq;
import aq.y;
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

public class ap {
    private static final Logger a = Logger.getLogger(ap.class.getName());
    private static ap b;
    private final HashMap<Integer, b> c = new HashMap();
    private boolean d;
    private boolean e;

    public static ap a() {
        if (b == null) {
            b = new ap();
        }
        return b;
    }

    private ap() {
        this.b();
    }

    private void b() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM mobgroup");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int mobGroupId = rs.getInt("id");
                        b mobGroup = new b();
                        mobGroup.c = rs.getBoolean("remove_group_if_leader_die");
                        int i2 = 1;
                        while (i2 <= 7) {
                            int npcid = rs.getInt("minion" + i2 + "_id");
                            int count = rs.getInt("minion" + i2 + "_count");
                            mobGroup.b.add(new a(npcid, count));
                            ++i2;
                        }
                        this.c.put(mobGroupId, mobGroup);
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

    public void a(t leader, int groupId, boolean isRespawnScreen, boolean isInitSpawn) {
        b mobGroup = this.c.get(groupId);
        if (mobGroup == null) {
            return;
        }
        this.d = isRespawnScreen;
        this.e = isInitSpawn;
        y mobGroupInfo = new y();
        mobGroupInfo.a(mobGroup.c);
        mobGroupInfo.c(leader);
        for (a minion : mobGroup.b) {
            if (minion.a()) continue;
            int i2 = 0;
            while (i2 < minion.c) {
                t mob = this.a(leader, minion.b);
                if (mob != null) {
                    mobGroupInfo.c(mob);
                }
                ++i2;
            }
        }
    }

    private t a(t leader, int npcId) {
        t mob = null;
        try {
            mob = au.a().b(npcId);
            mob.cF(ai.d.a().c());
            mob.ct(leader.fb());
            mob.cE(leader.fp());
            mob.u(leader.ad());
            mob.l(leader.ai());
            mob.cG(leader.fs() + i.a(5) - 2);
            mob.cH(leader.ft() + i.a(5) - 2);
            if (!this.a(mob)) {
                mob.cG(leader.fs());
                mob.cH(leader.ft());
            }
            mob.q(mob.fs());
            mob.r(mob.ft());
            if (mob instanceof s) {
                ((s)mob).a(leader);
            }
            mob.a(leader.R());
            mob.g(leader.Z());
            mob.p(leader.S());
            if (mob instanceof s && mob.fp() == 666) {
                ((s)mob).c(true);
            }
            aq.a().a(mob);
            aq.a().c(mob);
            if (mob instanceof s && !this.e && mob.ac() == 0) {
                mob.Z_();
            }
            mob.fg();
            mob.a_(0);
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        return mob;
    }

    private boolean a(t mob) {
        if (mob.fq().a(mob.fu()) && mob.fq().c(mob.fs(), mob.ft())) {
            if (this.d) {
                return true;
            }
            if (aq.a().f(mob).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private class a {
        private final int b;
        private final int c;

        private a(int _npcid, int _count) {
            this.b = _npcid;
            this.c = _count;
        }

        private boolean a() {
            return this.b == 0 && this.c == 0;
        }
    }

    private class b {
        private final ArrayList<a> b = new ArrayList();
        private boolean c;

        private b() {
        }
    }
}

