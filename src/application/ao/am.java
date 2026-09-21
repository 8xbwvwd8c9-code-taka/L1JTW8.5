/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.q;
import aq.b;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class am {
    private static final Logger a = Logger.getLogger(am.class.getName());
    private static am b;
    private final HashMap<Integer, a> c = new HashMap();

    public HashMap<Integer, a> a() {
        return this.c;
    }

    public static am b() {
        if (b == null) {
            b = new am();
        }
        return b;
    }

    private am() {
        this.c();
        aq.b.a().a(this.c);
    }

    private void c() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM magic_doll");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int itemId;
                        a template = new a();
                        template.a = itemId = rs.getInt("item_id");
                        template.b = rs.getString("nameid");
                        template.c = rs.getInt("gfxid");
                        template.d = rs.getInt("level");
                        template.e = rs.getInt("ac");
                        template.f = rs.getInt("mr");
                        template.g = rs.getInt("hp");
                        template.h = rs.getInt("mp");
                        template.i = rs.getInt("hpr");
                        template.j = rs.getInt("mpr");
                        template.n = rs.getInt("sp");
                        template.o = rs.getInt("hit");
                        template.k = rs.getInt("dmg");
                        template.m = rs.getInt("dmg_chance");
                        template.p = rs.getInt("bow_hit");
                        template.l = rs.getInt("bow_dmg");
                        template.q = rs.getInt("dmg_reduction");
                        template.r = rs.getInt("dmg_reduction_chance");
                        template.s = rs.getInt("dmg_evasion_chance");
                        template.t = rs.getInt("weight_reduction");
                        template.u = rs.getInt("str");
                        template.v = rs.getInt("con");
                        template.w = rs.getInt("dex");
                        template.x = rs.getInt("cha");
                        template.y = rs.getInt("intel");
                        template.z = rs.getInt("wis");
                        template.A = rs.getInt("regist_stun");
                        template.B = rs.getInt("regist_stone");
                        template.C = rs.getInt("regist_sleep");
                        template.D = rs.getInt("regist_freeze");
                        template.E = rs.getInt("regist_sustain");
                        template.F = rs.getInt("regist_blind");
                        template.G = rs.getInt("make_itemid");
                        template.H = rs.getInt("poison_chance");
                        template.I = rs.getInt("slow_chance");
                        template.J = rs.getInt("speed_up");
                        template.K = rs.getInt("breath_water");
                        template.L = rs.getInt("curse_chance");
                        template.M = rs.getInt("exp");
                        this.c.put(itemId, template);
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

    public void a(q item) {
        if (this.c.containsKey(item.N())) {
            a template = this.c.get(item.N());
            item.a(template.b);
            item.az(template.c);
            item.r(template.e);
            item.t(template.f);
            item.p(template.g);
            item.q(template.h);
            item.aJ(template.i);
            item.aI(template.j);
            item.s(template.n);
            item.G(template.o);
            item.I(template.k);
            item.aA(template.m);
            item.H(template.p);
            item.J(template.l);
            item.D(template.q);
            item.aF(template.r);
            item.aB(template.s);
            item.aG(template.t);
            item.w(template.u);
            item.y(template.v);
            item.x(template.w);
            item.B(template.x);
            item.z(template.y);
            item.A(template.z);
            item.T(template.A);
            item.O(template.B);
            item.P(template.C);
            item.Q(template.D);
            item.S(template.E);
            item.R(template.F);
            item.aH(template.G);
            item.aC(template.H);
            item.aD(template.I);
            item.g(template.J > 0);
            item.h(template.K > 0);
            item.aE(template.L);
            item.aK(template.M);
            if (item.N() == 640771) {
                item.ai(5);
            } else if (item.N() == 640770) {
                item.ak(10);
            }
        }
    }

    public class a {
        public int a;
        public String b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public int m;
        public int n;
        public int o;
        public int p;
        public int q;
        public int r;
        public int s;
        public int t;
        public int u;
        public int v;
        public int w;
        public int x;
        public int y;
        public int z;
        public int A;
        public int B;
        public int C;
        public int D;
        public int E;
        public int F;
        public int G;
        public int H;
        public int I;
        public int J;
        public int K;
        public int L;
        public int M;
    }
}

