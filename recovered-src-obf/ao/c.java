/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ap.q;
import ap.u;
import aq.ae;
import be.bl;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class c {
    private static final Logger a = Logger.getLogger(c.class.getName());
    private static c b;
    private final ArrayList<a> c = new ArrayList();

    public static c a() {
        if (b == null) {
            b = new c();
        }
        return b;
    }

    private c() {
        this.c();
    }

    private void c() {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM armor_set");
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
            a as2 = new a();
            as2.b(rs.getInt("id"));
            as2.a(rs.getString("note"));
            as2.a(ao.c.a(rs.getString("sets"), ","));
            as2.c(rs.getInt("polyid"));
            as2.d(rs.getInt("poly_desc"));
            as2.e(rs.getInt("ac"));
            as2.f(rs.getInt("hp"));
            as2.g(rs.getInt("mp"));
            as2.h(rs.getInt("hpr"));
            as2.i(rs.getInt("mpr"));
            as2.j(rs.getInt("mr"));
            as2.k(rs.getInt("str"));
            as2.l(rs.getInt("dex"));
            as2.m(rs.getInt("con"));
            as2.n(rs.getInt("wis"));
            as2.o(rs.getInt("cha"));
            as2.p(rs.getInt("intl"));
            as2.u(rs.getInt("hit_modifier"));
            as2.v(rs.getInt("dmg_modifier"));
            as2.w(rs.getInt("bow_hit_modifier"));
            as2.x(rs.getInt("bow_dmg_modifier"));
            as2.y(rs.getInt("sp"));
            as2.q(rs.getInt("defense_water"));
            as2.r(rs.getInt("defense_wind"));
            as2.s(rs.getInt("defense_fire"));
            as2.t(rs.getInt("defense_earth"));
            as2.z(rs.getInt("damage_reduction"));
            this.c.add(as2);
        }
    }

    public ArrayList<a> b() {
        return this.c;
    }

    private static int[] a(String s2, String sToken) {
        StringTokenizer st = new StringTokenizer(s2, sToken);
        int size = st.countTokens();
        String temp = null;
        int[] array = new int[size];
        int i2 = 0;
        while (i2 < size) {
            temp = st.nextToken();
            array[i2] = Integer.parseInt(temp);
            ++i2;
        }
        return array;
    }

    public ArrayList<a> a(int itemid) {
        ArrayList<a> result = new ArrayList<a>();
        for (a as2 : this.c) {
            if (!as2.a(itemid)) continue;
            result.add(as2);
        }
        return result;
    }

    public class a {
        private int b;
        private int[] c;
        private int d;
        private int e;
        private int f;
        private int g;
        private int h;
        private int i;
        private int j;
        private int k;
        private int l;
        private int m;
        private int n;
        private int o;
        private int p;
        private int q;
        private int r;
        private int s;
        private int t;
        private int u;
        private int v;
        private int w;
        private int x;
        private int y;
        private int z;
        private int A;
        private String B;

        public boolean a(int itemid) {
            int[] nArray = this.c;
            int n2 = this.c.length;
            int n3 = 0;
            while (n3 < n2) {
                int i2 = nArray[n3];
                if (itemid == i2) {
                    return true;
                }
                ++n3;
            }
            return false;
        }

        public boolean a(u pc) {
            return pc.j().b(this.c);
        }

        public void a(u pc, q item, boolean isTrue) {
            item.e(false);
            pc.a(new bl(item));
            int[] nArray = this.c;
            int n2 = this.c.length;
            int n3 = 0;
            while (n3 < n2) {
                int i2 = nArray[n3];
                q set = pc.j().b(i2);
                if (set.D()) {
                    set.e(isTrue);
                    pc.a(new bl(set));
                }
                ++n3;
            }
        }

        public void b(u pc) {
            pc.bL(this.f);
            pc.bH(this.g);
            pc.bJ(this.h);
            pc.c(this.i);
            pc.d(this.j);
            pc.co(this.k);
            pc.cp(this.z);
            pc.bN((byte)this.l);
            pc.bR((byte)this.m);
            pc.bP((byte)this.n);
            pc.bX((byte)this.o);
            pc.bT((byte)this.p);
            pc.bV((byte)this.q);
            pc.bZ(this.r);
            pc.bY(this.s);
            pc.ca(this.t);
            pc.cb(this.u);
            pc.G(this.v);
            pc.H(this.w);
            pc.I(this.x);
            pc.J(this.y);
            pc.F(this.A);
            if (this.d > -1) {
                if (this.d == 6080 || this.d == 6094) {
                    int n2 = this.d = pc.aJ() == 0 ? 6094 : 6080;
                    if (!this.e(pc)) {
                        return;
                    }
                }
                ae.a(pc, this.d, 0, 1);
            }
        }

        public void c(u pc) {
            pc.bL(-this.f);
            pc.bH(-this.g);
            pc.bJ(-this.h);
            pc.c(-this.i);
            pc.d(-this.j);
            pc.co(-this.k);
            pc.cp(-this.z);
            pc.bN(-((byte)this.l));
            pc.bR(-((byte)this.m));
            pc.bP(-((byte)this.n));
            pc.bX(-((byte)this.o));
            pc.bT(-((byte)this.p));
            pc.bV(-((byte)this.q));
            pc.bZ(-this.r);
            pc.bY(-this.s);
            pc.ca(-this.t);
            pc.cb(-this.u);
            pc.G(-this.v);
            pc.H(-this.w);
            pc.I(-this.x);
            pc.J(-this.y);
            pc.F(-this.A);
            if (this.d > -1) {
                if (this.d == 6080 || this.d == 6094) {
                    int n2 = this.d = pc.aJ() == 0 ? 6094 : 6080;
                }
                if (pc.fe() != this.d) {
                    return;
                }
                ae.b(pc);
            }
        }

        private boolean e(u pc) {
            q item;
            boolean isRemainderOfCharge = false;
            if (pc.j().g(20383, 1) && (item = pc.j().b(20383)) != null && item.I() != 0) {
                isRemainderOfCharge = true;
            }
            return isRemainderOfCharge;
        }

        public boolean d(u pc) {
            int equip_count = 0;
            int[] nArray = this.c;
            int n2 = this.c.length;
            int n3 = 0;
            while (n3 < n2) {
                int itemid = nArray[n3];
                for (q ring : pc.j().i()) {
                    if (ring.N() == itemid) {
                        ++equip_count;
                    }
                    if (equip_count < 2) continue;
                    return true;
                }
                ++n3;
            }
            return false;
        }

        public int a() {
            return this.b;
        }

        public void b(int i2) {
            this.b = i2;
        }

        public int[] b() {
            return this.c;
        }

        public void a(int[] i2) {
            this.c = i2;
        }

        public int c() {
            return this.d;
        }

        public void c(int i2) {
            this.d = i2;
        }

        public int d() {
            return this.e;
        }

        public void d(int i2) {
            this.e = i2;
        }

        public int e() {
            return this.f;
        }

        public void e(int i2) {
            this.f = i2;
        }

        public int f() {
            return this.g;
        }

        public void f(int i2) {
            this.g = i2;
        }

        public int g() {
            return this.h;
        }

        public void g(int i2) {
            this.h = i2;
        }

        public int h() {
            return this.i;
        }

        public void h(int i2) {
            this.i = i2;
        }

        public int i() {
            return this.j;
        }

        public void i(int i2) {
            this.j = i2;
        }

        public int j() {
            return this.k;
        }

        public void j(int i2) {
            this.k = i2;
        }

        public int k() {
            return this.l;
        }

        public void k(int i2) {
            this.l = i2;
        }

        public int l() {
            return this.m;
        }

        public void l(int i2) {
            this.m = i2;
        }

        public int m() {
            return this.n;
        }

        public void m(int i2) {
            this.n = i2;
        }

        public int n() {
            return this.o;
        }

        public void n(int i2) {
            this.o = i2;
        }

        public int o() {
            return this.p;
        }

        public void o(int i2) {
            this.p = i2;
        }

        public int p() {
            return this.q;
        }

        public void p(int i2) {
            this.q = i2;
        }

        public int q() {
            return this.r;
        }

        public void q(int i2) {
            this.r = i2;
        }

        public int r() {
            return this.s;
        }

        public void r(int i2) {
            this.s = i2;
        }

        public int s() {
            return this.t;
        }

        public void s(int i2) {
            this.t = i2;
        }

        public int t() {
            return this.u;
        }

        public void t(int i2) {
            this.u = i2;
        }

        public int u() {
            return this.v;
        }

        public void u(int i2) {
            this.v = i2;
        }

        public int v() {
            return this.w;
        }

        public void v(int i2) {
            this.w = i2;
        }

        public int w() {
            return this.x;
        }

        public void w(int i2) {
            this.x = i2;
        }

        public int x() {
            return this.y;
        }

        public void x(int i2) {
            this.y = i2;
        }

        public int y() {
            return this.z;
        }

        public void y(int i2) {
            this.z = i2;
        }

        public int z() {
            return this.A;
        }

        public void z(int i2) {
            this.A = i2;
        }

        public String A() {
            return this.B;
        }

        public void a(String s2) {
            this.B = s2;
        }
    }
}

