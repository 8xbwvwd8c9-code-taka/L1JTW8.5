/*
 * Decompiled with CFR 0.152.
 */
package ao;

import am.c;
import ap.t;
import aq.f;
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

public class ar {
    private static final Logger a = Logger.getLogger(ar.class.getName());
    private static ar b;
    private final HashMap<Integer, a> c = new HashMap();

    public static ar a() {
        if (b == null) {
            b = new ar();
        }
        return b;
    }

    private ar() {
        this.b();
    }

    private void b() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM mobskills");
                    rs = pstm.executeQuery();
                    this.a(rs);
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    private void a(ResultSet rs) throws SQLException {
        while (rs.next()) {
            a md;
            int npcid = rs.getInt("npcid");
            if (this.c.containsKey(npcid)) {
                md = this.c.get(npcid);
            } else {
                md = new a();
                this.c.put(npcid, md);
            }
            b data = new b();
            data.a = rs.getInt("probability");
            data.b = rs.getInt("base_damage");
            data.c = rs.getInt("random_damage");
            data.d = rs.getInt("range");
            data.e = rs.getInt("area");
            data.f = rs.getInt("effect_id");
            data.g = rs.getBoolean("is_target_effect");
            data.h = rs.getBoolean("is_arrow");
            data.i = rs.getInt("action");
            data.j = rs.getInt("mp_consume");
            data.k = rs.getInt("spell_count");
            data.l = rs.getInt("spell_hp_percent");
            data.m = rs.getInt("attr");
            data.n = rs.getInt("summonid");
            data.o = rs.getInt("polyid");
            data.p = rs.getInt("skill_effect");
            data.q = rs.getBoolean("is_skill_bind");
            data.r = rs.getBoolean("is_skill_recall");
            data.s = rs.getBoolean("is_skill_smoke");
            data.t = rs.getInt("delay");
            md.b.add(data);
        }
    }

    public int a(t mob, f target) {
        if (mob.ej()) {
            return 0;
        }
        if (!this.c.containsKey(mob.z())) {
            return 0;
        }
        if (mob.bB(64)) {
            return 0;
        }
        a md = this.c.get(mob.z());
        b data = null;
        for (b sd : md.b) {
            if (sd.k > 0 && mob.aq() >= sd.k || mob.ea() * 100 / mob.ew() > sd.l || i.a(100) > sd.a) continue;
            data = sd;
            break;
        }
        if (data == null) {
            return 0;
        }
        bf.b executor = new bf.b();
        if (data.d < 0 ? !mob.fu().e(target.fu()) : mob.fu().c(target.fu()) > data.d || !mob.i(target.fs(), target.ft())) {
            return 0;
        }
        if (mob.eb() < data.j) {
            return 0;
        }
        int current_mp = mob.eb() - data.j;
        mob.i_(current_mp);
        executor.a(mob, target, data);
        if (mob.z() == 190280) {
            mob.aa_();
            return 0;
        }
        if (data.t > 0) {
            bg.a.a(mob, data.t);
        }
        if (data.k > 0) {
            mob.I();
        }
        return am.c.a().a(mob.fe(), data.i) + 100;
    }

    private class a {
        private final ArrayList<b> b = new ArrayList();

        private a() {
        }
    }

    public class b {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public boolean g;
        public boolean h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public boolean q;
        public boolean r;
        public boolean s;
        public int t;
    }
}

