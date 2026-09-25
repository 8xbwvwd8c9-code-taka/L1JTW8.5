/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.bl;
import ap.q;
import ap.s;
import ap.t;
import ap.u;
import ap.v;
import ap.z;
import aq.aa;
import aq.ai;
import aq.f;
import aq.w;
import az.a;
import az.b;
import az.d;
import be.ak;
import be.aq;
import be.bs;
import be.cg;
import be.cm;
import be.cn;
import be.dc;
import be.ds;
import be.ee;
import be.ei;
import be.g;
import bf.ad;
import bf.ah;
import bf.dp;
import bi.h;
import bi.i;

public class c {
    private u a = null;
    private f b = null;
    private f c = null;
    private u d = null;
    private t e = null;
    private t f = null;
    private int g = 0;
    private static final int h = 1;
    private static final int i = 2;
    private static final int j = 3;
    private static final int k = 4;
    private boolean l = false;
    private int m = 0;
    private int n = 0;
    private int o = 0;
    private q p = null;
    private q q = null;
    private int r = 0;
    private int s = 0;
    private int t = 0;
    private int u = 0;
    private int v = 0;
    private int w = 1;
    private int x = 1;
    private int y = 0;
    private int z = 0;
    private int A = 0;
    private int B = 0;
    private int C = 0;
    private final int D;
    private static final int[] E = new int[]{78, 50, 157, 15003, 15004, 120};
    private static int[] F = new int[]{3008, 3009, 3010, 3011, 3012, 3013, 3014, 3024, 3025, 3026, 3027, 3028, 3029, 3030, 3040, 3041, 3042, 3043, 3044, 3045, 3046};

    public c(f attacker, f target) {
        this(attacker, target, 0);
    }

    public c(f attacker, f target, int skillId) {
        this.D = skillId;
        if (attacker instanceof u) {
            this.a = (u)attacker;
            if (target instanceof u) {
                this.d = (u)target;
                this.g = 1;
            } else if (target instanceof t) {
                this.f = (t)target;
                this.g = 2;
            }
            this.p = this.a.v();
            if (this.p != null) {
                this.r = this.p.N();
                this.s = this.p.a().aO();
                this.t = this.p.a().ac() + this.p.a().ad() + this.p.P();
                this.u = this.p.a().v();
                this.v = this.p.a().w();
                this.w = this.p.a().aB();
                this.x = this.p.F();
                this.y = this.p.G();
                this.z = this.p.a().k();
                if (this.s == 20 || this.s == 62) {
                    this.q = this.a.j().n(this.s);
                    if (this.q != null) {
                        this.x = this.q.F();
                        this.z = this.q.a().k();
                    }
                } else {
                    this.y = this.p.G() - this.p.H();
                }
                this.A = this.p.a().aC();
                if (this.p.N() == 353) {
                    this.A += this.p.G();
                }
                this.B = this.p.K();
                this.C = this.p.L();
            }
        } else if (attacker instanceof t) {
            this.e = (t)attacker;
            if (target instanceof u) {
                this.d = (u)target;
                this.g = 3;
            } else if (target instanceof t) {
                this.f = (t)target;
                this.g = 4;
            }
        }
        this.c = attacker;
        this.b = target;
    }

    public boolean a() {
        if (this.g == 1 || this.g == 2) {
            if (this.w == -1) {
                if (!this.a.fu().e(this.b.fu())) {
                    this.l = false;
                    return false;
                }
            } else if (this.a.fu().c(this.b.fu()) > this.w + 1) {
                this.l = false;
                return false;
            }
            if ((this.s == 20 || this.s == 62) && this.q == null && this.r != 190 && this.r != 399) {
                this.l = false;
                return false;
            }
            if (this.w != 1 && !this.a.i(this.b.fs(), this.b.ft())) {
                this.l = false;
                return false;
            }
        }
        if (this.g == 1) {
            this.l = this.h();
        } else if (this.g == 2) {
            this.l = this.i();
        } else if (this.g == 3) {
            this.l = this.j();
        } else if (this.g == 4) {
            this.l = this.k();
        }
        if (this.g == 1 && this.l && this.a.bB(92) && this.d.bB(78) && bi.i.a(100) < this.a.ev() - 80 + 1) {
            this.d.bz(78);
            this.d.a(new ee(this.d.fr(), 14539));
            this.d.b(new ee(this.d.fr(), 14539));
        }
        int[] nArray = E;
        int n2 = E.length;
        int n3 = 0;
        while (n3 < n2) {
            int skillId = nArray[n3];
            if (this.b.bB(skillId)) {
                this.l = false;
                return false;
            }
            ++n3;
        }
        return this.l;
    }

    private boolean h() {
        int _hitRate = this.a.ev();
        _hitRate += this.a.aC().h(this.a.ev());
        if (this.s == 20 || this.s == 62) {
            _hitRate += bi.d.e(this.a.bh(), this.a.eB());
            _hitRate += this.a.eU() + this.a.bY();
        } else {
            _hitRate += bi.d.b(this.a.bf(), this.a.ez());
            _hitRate += this.a.eT() + this.a.bW();
        }
        if (this.a.j().h() > 33 && this.a.j().h() <= 50) {
            --_hitRate;
        } else if (this.a.j().h() >= 51 && this.a.j().h() <= 66) {
            _hitRate -= 3;
        } else if (this.a.j().h() >= 67 && this.a.j().h() <= 82) {
            _hitRate -= 5;
        }
        _hitRate += this.y / 2;
        if (this.a.E() && this.s == 58) {
            return true;
        }
        if (this.d.ai()) {
            return false;
        }
        int dice = 20;
        dice += this.d.fk();
        int value = bi.i.a(dice -= this.d.fl()) + 1;
        if (value >= 20 + this.d.fk()) {
            return false;
        }
        _hitRate = this.d.ey() < 0 ? (_hitRate -= bi.i.a((int)((double)(-this.d.ey()) * 1.5))) : (_hitRate += this.d.ey());
        if (this.s == 20 && _hitRate > value) {
            return this.d.u() < bi.i.a(100);
        }
        return _hitRate > value;
    }

    private boolean i() {
        int _hitRate = this.a.ev();
        _hitRate += this.a.aC().h(this.a.ev());
        if (this.s == 20 || this.s == 62) {
            _hitRate += bi.d.e(this.a.bh(), this.a.eB());
            _hitRate += this.a.eU() + this.a.bY();
        } else {
            _hitRate += bi.d.b(this.a.bf(), this.a.ez());
            _hitRate += this.a.eT() + this.a.bW();
        }
        if (this.a.j().h() > 33 && this.a.j().h() <= 50) {
            --_hitRate;
        } else if (this.a.j().h() >= 51 && this.a.j().h() <= 66) {
            _hitRate -= 3;
        } else if (this.a.j().h() >= 67 && this.a.j().h() <= 82) {
            _hitRate -= 5;
        }
        _hitRate += this.y / 2;
        if (this.a.E() && this.s == 58) {
            return true;
        }
        if (this.a.d(this.a, this.f.z())) {
            return false;
        }
        int dice = 20;
        dice += this.f.fk();
        int value = bi.i.a(dice -= this.f.fl()) + 1;
        if (value >= 20 + this.f.fk()) {
            return false;
        }
        _hitRate = this.f.ey() < 0 ? (_hitRate -= bi.i.a((int)((double)(-this.f.ey()) * 1.5))) : (_hitRate += this.f.ey());
        return _hitRate > value;
    }

    private boolean j() {
        int _hitRate = this.e.ev();
        if (this.e instanceof v) {
            _hitRate += ((v)this.e).p();
        }
        _hitRate += this.e.eT() + this.e.J() + this.e.ez();
        if ((this.e instanceof v || this.e instanceof z) && (this.d.ep() == 1 || this.e.ep() == 1 || this.d.a(this.d, this.e, false))) {
            return false;
        }
        if (this.d.ai()) {
            return false;
        }
        int dice = 20;
        dice += this.d.fk();
        int value = bi.i.a(dice -= this.d.fl()) + 1;
        if (value >= 20 + this.d.fk()) {
            return false;
        }
        _hitRate = this.d.ey() < 0 ? (_hitRate -= bi.i.a(-this.d.ey())) : (_hitRate += this.d.ey());
        if (this.e.C() >= 10 && _hitRate > value && this.e.fu().c(new h(this.b.fs(), this.b.ft())) >= 2) {
            return this.d.u() < bi.i.a(100);
        }
        return _hitRate > value;
    }

    private boolean k() {
        int _hitRate = this.e.ev();
        if (this.e instanceof v) {
            _hitRate += ((v)this.e).p();
        }
        _hitRate += this.e.eT() + this.e.J() + this.e.ez();
        if ((this.e instanceof v || this.e instanceof z) && (this.f instanceof v || this.f instanceof z) && (this.f.ep() == 1 || this.e.ep() == 1)) {
            return false;
        }
        int dice = 20;
        dice += this.f.fk();
        int value = bi.i.a(dice -= this.f.fl()) + 1;
        if (value >= 20 + this.f.fk()) {
            return false;
        }
        _hitRate = this.f.ey() < 0 ? (_hitRate -= bi.i.a(-this.f.ey())) : (_hitRate += this.f.ey());
        return _hitRate > value;
    }

    public int b() {
        if (!this.c.i(this.b.fs(), this.b.ft())) {
            this.m = 0;
            return 0;
        }
        if (this.g == 1) {
            this.m = this.l();
        } else if (this.g == 2) {
            this.m = this.m();
        } else if (this.g == 3) {
            this.m = this.o();
        } else if (this.g == 4) {
            this.m = this.p();
        }
        return this.m;
    }

    private int a(int weaponMaxDamage) {
        int weaponDamage = bi.i.a(weaponMaxDamage) + 1;
        if (this.a.bB(175)) {
            weaponDamage = weaponMaxDamage;
        }
        weaponDamage += this.t + this.y;
        if (this.y >= 10) {
            weaponDamage += this.y - 9;
        }
        if (this.g == 2) {
            weaponDamage += this.q();
        }
        weaponDamage += this.r();
        int calcCritical = 1 + bi.d.c(this.a.bf(), this.a.ez()) + this.a.dE();
        if (this.a.C() && (this.s == 58 || this.s == 54)) {
            if (bi.i.a(100) < this.A || this.a.bB(609) || bi.i.a(100) < calcCritical) {
                weaponDamage *= 3;
                int n2 = this.n = this.s == 58 ? 2 : 4;
                if (this.a.bB(105) && bi.i.a(100) <= 33) {
                    weaponDamage *= 2;
                }
                if (this.a.bB(97) && this.a.bB(233) && this.a.h(609) && !this.a.bB(609)) {
                    int advence = 5;
                    if (this.a.ev() >= 85 && this.a.ev() < 90) {
                        ++advence;
                    } else if (this.a.ev() >= 90) {
                        advence += 2;
                    }
                    this.a.j(609, advence * 1000);
                    this.a.bz(97);
                    this.a.bz(233);
                    this.a.a(new ee(this.a.fr(), 14547));
                    this.a.b(new ee(this.a.fr(), 14547));
                    this.a.a(new dc(609, advence, 6, 7447, 7448, 4750, 4750, 4751, 1));
                }
            }
        } else {
            if (this.s == 20 || this.s == 62) {
                return this.r();
            }
            if (bi.i.a(100) < calcCritical) {
                weaponDamage = (int)((double)weaponMaxDamage * 2.5);
                this.n = 2;
            }
        }
        return weaponDamage;
    }

    private double a(double dmg) {
        int add_dmg = 1;
        if (this.s == 20 || this.s == 62) {
            if (this.q != null) {
                add_dmg = this.q.a().v();
                if (this.g == 2) {
                    if (this.f.U_().q().equalsIgnoreCase("large")) {
                        add_dmg = this.q.a().w();
                    }
                    if (this.f.U_().W()) {
                        add_dmg /= 2;
                    }
                }
            } else if (this.r == 190) {
                add_dmg = 15;
            } else if (this.r == 399) {
                add_dmg = 2;
            }
        }
        int calcBowCritical = 1 + bi.d.f(this.a.bh(), this.a.eB()) + this.a.dF();
        double longdmg = dmg + (double)this.a.eS();
        if (bi.i.a(100) < calcBowCritical) {
            longdmg += (double)(add_dmg * 2 + 1);
            this.n = 2;
        } else {
            longdmg += (double)(bi.i.a(add_dmg) + 1);
        }
        longdmg += (double)this.a.bZ();
        if (this.a.bB(114)) {
            longdmg += 5.0;
        }
        if (this.a.bB(4058)) {
            longdmg += 100.0;
        }
        if (this.a.aj()) {
            az.a.a(this.a, this.b, 3000, 5, 30);
        }
        if (this.a.ak()) {
            new ad().a(this.b, -1);
        }
        if (this.a.al() && this.b instanceof t) {
            new ah().a(this.b, -1);
        }
        return longdmg;
    }

    private double b(double dmg) {
        double shortdmg = dmg + (double)this.a.eR() + (double)this.a.ag();
        if (this.a.D()) {
            this.B();
        }
        shortdmg = this.c(shortdmg);
        shortdmg += (double)this.a.bX();
        if (this.s == 0) {
            shortdmg = (bi.i.a(5) + 4) / 4;
        } else if (this.a.E() && this.s == 58) {
            shortdmg = this.a(this.a, this.b);
        }
        int power = 0;
        if (this.a.bB(601) && bi.i.a(100) < 15) {
            ++power;
        }
        if (this.a.bB(601) && this.a.bB(602) && bi.i.a(100) < 5) {
            power += 2;
        }
        if (power > 0) {
            shortdmg += (double)((2 + Math.max(this.a.ev() - 45, 0)) * power);
            if (this.d != null) {
                this.d.a(new ee(this.d.fr(), 12486 + power));
            }
            this.b.b(new ee(this.b.fr(), 12486 + power));
        }
        if (this.d != null && this.d.bB(112)) {
            int prob = 38 + (this.a.ev() - this.d.ev()) * (bi.i.a(3) + 2);
            if (this.a.bB(222)) {
                prob += 7;
            }
            if (bi.i.a(100) < prob) {
                shortdmg *= 1.58;
            }
        }
        if (this.a.aj()) {
            az.a.a(this.a, this.b, 3000, 5, 30);
        }
        if (this.a.ak()) {
            new ad().a(this.b, -1);
        }
        if (this.a.al() && this.b instanceof t) {
            new ah().a(this.b, -1);
        }
        return shortdmg;
    }

    private int l() {
        double dmg = this.a(this.u);
        dmg = this.s == 20 || this.s == 62 ? this.a(dmg + (double)bi.d.d(this.a.bh(), this.a.eB())) : this.b(dmg + (double)bi.d.a(this.a.bf(), this.a.ez()));
        dmg = this.r == 2 ? (dmg += this.a(this.a, (f)this.d, this.p)) : (dmg += bl.a().a(this.a, this.b, this.r));
        double dmgTemp = dmg;
        dmg -= (double)this.d.bV();
        if (this.d.dC() > 0 && bi.i.a(100) < 5) {
            dmg -= (double)this.d.dC();
        }
        dmg -= (double)this.d.ah();
        if (this.n()) {
            dmg -= 5.0;
        }
        if (this.d.bB(3015) || this.d.bB(3031) || this.d.bB(3047)) {
            dmg -= 5.0;
        }
        if (this.d.bB(88)) {
            int targetPcLvl = this.d.ev();
            if (targetPcLvl < 50) {
                targetPcLvl = 50;
            }
            dmg -= (double)((targetPcLvl - 50) / 5 + 1);
        }
        if (this.d.bB(181)) {
            dmg -= 2.0;
        }
        if (this.d.bB(211)) {
            dmg -= 2.0;
        }
        if (this.d.bB(159)) {
            dmg -= 2.0;
        }
        if (this.d.bB(4058)) {
            dmg -= 60.0;
        }
        if (this.d.bB(68)) {
            dmg /= 2.0;
        } else if (this.d.dg() && bi.i.a(100) < 5) {
            this.d.a(new ee(this.d.fr(), 11101));
            this.d.b(new ee(this.d.fr(), 11101));
            this.d.j(68, 3000);
            this.d.a(new ds(314));
            this.d.a(new cm(40, 3));
            dmg /= 2.0;
        } else if (this.d.di() && bi.i.a(100) < 5) {
            this.d.a(new ee(this.d.fr(), 11101));
            this.d.b(new ee(this.d.fr(), 11101));
            this.d.j(68, 10000);
            this.d.a(new cm(40, 10));
            this.d.a(new ds(314));
            dmg /= 2.0;
        }
        dmg += (double)this.a.dJ();
        double d2 = dmg = dmg > dmgTemp ? dmgTemp : dmg;
        if (this.d.dj() && bi.i.a(100) < 5) {
            this.d.a(new ee(this.d.fr(), 14646));
            this.d.b(new ee(this.d.fr(), 14646));
            this.d.a(this.d.ea() + 20 + bi.i.a(20));
        }
        if (this.d.dk() && bi.i.a(100) < 7) {
            this.d.a(new ee(this.d.fr(), 14649));
            this.d.b(new ee(this.d.fr(), 14649));
            this.d.a(this.d.ea() + 130 + bi.i.a(40));
        }
        if (this.d.do() > 0 && bi.i.a(100) < this.d.do()) {
            this.d.a(new ee(this.d.fr(), 8909));
            this.d.b(new ee(this.d.fr(), 8909));
            this.d.a(this.d.ea() + 130 + bi.i.a(40));
        }
        if (this.d.dh() && bi.i.a(100) < 7) {
            this.d.a(new ee(this.d.fr(), 12357));
            this.d.b(new ee(this.d.fr(), 12357));
            this.d.i_(this.d.eb() + 10);
        }
        if (this.d.dp() > 0 && bi.i.a(100) < this.d.dp()) {
            new dp().a((f)this.d, 5);
        }
        dmg -= (double)this.d.dB();
        dmg += (double)this.a.dA();
        if (this.D == 203) {
            dmg += 15.0;
            if (this.s == 58) {
                dmg = 15.0;
            }
        } else if (this.D == 208) {
            dmg += 10.0;
            if (this.s == 58) {
                dmg = 10.0;
            }
        }
        if (this.a.du() && bi.i.a(100) < 7) {
            dmg += 100.0;
            this.a.a(new ee(this.a.fr(), 10243));
            this.a.b(new ee(this.a.fr(), 10243));
        }
        if (this.a.dv() && bi.i.a(100) < 8) {
            dmg += 150.0;
            this.a.a(new ee(this.a.fr(), 10243));
            this.a.b(new ee(this.a.fr(), 10243));
        }
        if (this.a.dw() && bi.i.a(100) < 9) {
            dmg += 200.0;
            this.a.a(new ee(this.a.fr(), 10243));
            this.a.b(new ee(this.a.fr(), 10243));
        }
        if (this.a.dm() && bi.i.a(100) < 7) {
            dmg += 80.0;
            this.d.a(new ee(this.d.fr(), 13542));
            this.d.b(new ee(this.d.fr(), 13542));
        }
        if (this.a.dt() && bi.i.a(100) < 3) {
            dmg += 220.0;
            this.d.a(new ee(this.d.fr(), 12157));
            this.d.b(new ee(this.d.fr(), 12157));
        }
        if (this.a.dq() > 0 && bi.i.a(100) < this.a.dq()) {
            dmg += 180.0;
            this.d.a(new ee(this.d.fr(), 13338));
            this.d.b(new ee(this.d.fr(), 13338));
        }
        if (this.a.dx() && bi.i.a(100) < 10) {
            dmg += bl.a().a(this.a, this.d, 14449, 4, 50.0);
        }
        if ((this.a.dr() || this.a.ds()) && bi.i.a(100) < 7) {
            int gfxid;
            int leech = this.a.dr() ? 160 : 60;
            int n2 = gfxid = this.a.dr() ? 11677 : 11673;
            if (!(this.a.A() || this.a.B() || this.a.E())) {
                leech += 80;
            }
            w _magic = new w(this.a, this.d);
            _magic.a(leech, 0);
            this.a.a(leech + this.a.ea());
            this.d.a(new ee(this.d.fr(), gfxid));
            this.d.b(new ee(this.d.fr(), gfxid));
        }
        if (this.a.dn() > 0 && bi.i.a(100) < this.a.dn()) {
            ai.a().a(4184, 1000, this.d.fs(), this.d.ft(), this.d.fp());
            this.d.j(1028, 1000);
            this.d.a(new cn(6, true));
        }
        if (this.a.da() && bi.i.a(100) < 5) {
            this.a.a(new ee(this.a.fr(), 13749));
            this.a.b(new ee(this.a.fr(), 13749));
            if (!this.a.bB(1027) && !this.a.bB(1038)) {
                this.a.j(1027, 5000);
                this.a.a(new bs(this.a.fr(), 8));
                this.a.b(new bs(this.a.fr(), 8));
            }
        }
        if (this.d.db() && bi.i.a(100) < 10) {
            this.d.a(new ee(this.d.fr(), 13702));
            this.d.b(new ee(this.d.fr(), 13702));
            dmg -= 30.0;
        }
        if (this.d.df() > bi.i.a(100)) {
            this.d.a(new ee(this.d.fr(), 13702));
            this.d.b(new ee(this.d.fr(), 13702));
            dmg -= 50.0;
        }
        if (this.d.dc() && dmg > 0.0 && bi.i.a(100) < 7) {
            this.d.a(new ee(this.a.fr(), 9801));
            this.d.b(new ee(this.a.fr(), 9801));
            w _magic = new w(this.d, this.a);
            _magic.a(80, 0);
            this.a.a(new ak(this.a.fr(), 2));
            this.a.b(new ak(this.a.fr(), 2));
        }
        if (this.d.dl() && dmg > 0.0 && bi.i.a(100) < 7) {
            this.d.a(new ee(this.d.fr(), 14453));
            this.d.b(new ee(this.d.fr(), 14453));
            w _magic = new w(this.d, this.a);
            _magic.a(120, 0);
            this.a.a(new ak(this.a.fr(), 2));
            this.a.b(new ak(this.a.fr(), 2));
        }
        if (dmg < 0.0) {
            dmg = 1.0;
        }
        return (int)dmg;
    }

    private int m() {
        int weaponMaxDamage = 0;
        if (this.f.U_().q().equalsIgnoreCase("small") && this.u > 0) {
            weaponMaxDamage = this.u;
        } else if (this.f.U_().q().equalsIgnoreCase("large") && this.v > 0) {
            weaponMaxDamage = this.v;
        }
        double dmg = this.a(weaponMaxDamage);
        dmg = this.s == 20 || this.s == 62 ? this.a(dmg + (double)bi.d.d(this.a.bh(), this.a.eB())) : this.b(dmg + (double)bi.d.a(this.a.bf(), this.a.ez()));
        dmg = this.r == 2 ? (dmg += this.a(this.a, (f)this.f, this.p)) : (dmg += bl.a().a(this.a, this.b, this.r));
        dmg -= (double)this.f.U_().V();
        if (this.D == 203) {
            dmg += 15.0;
            if (this.a.E() && this.s == 58) {
                dmg = 15.0;
            }
        } else if (this.D == 208) {
            dmg += 10.0;
            if (this.a.E() && this.s == 58) {
                dmg = 10.0;
            }
        }
        if (this.a.du() && bi.i.a(100) < 7) {
            dmg += 100.0;
            this.a.a(new ee(this.a.fr(), 10243));
            this.a.b(new ee(this.a.fr(), 10243));
        }
        if (this.a.dv() && bi.i.a(100) < 8) {
            dmg += 150.0;
            this.a.a(new ee(this.a.fr(), 10243));
            this.a.b(new ee(this.a.fr(), 10243));
        }
        if (this.a.dw() && bi.i.a(100) < 9) {
            dmg += 200.0;
            this.a.a(new ee(this.a.fr(), 10243));
            this.a.b(new ee(this.a.fr(), 10243));
        }
        if (this.a.dm() && bi.i.a(100) < 7) {
            dmg += 80.0;
            this.f.b(new ee(this.f.fr(), 13542));
        }
        if (this.a.dt() && bi.i.a(100) < 3) {
            dmg += 220.0;
            this.f.b(new ee(this.f.fr(), 12157));
        }
        if (this.a.dx() && bi.i.a(100) < 10) {
            dmg += bl.a().a(this.a, this.f, 14449, 4, 50.0);
        }
        if ((this.a.dr() || this.a.ds()) && bi.i.a(100) < 7) {
            int leech = this.a.dr() ? 160 : 60;
            int gfxid = this.a.dr() ? 11677 : 11673;
            w _magic = new w(this.a, this.f);
            _magic.a(leech, 0);
            this.a.a(leech + this.a.ea());
            this.f.b(new ee(this.f.fr(), gfxid));
        }
        if (this.a.dn() > 0 && bi.i.a(100) < this.a.dn()) {
            ai.a().a(4184, 1000, this.f.fs(), this.f.ft(), this.f.fp());
            this.f.j(1028, 2000);
            this.f.n(true);
        }
        if (this.a.dq() > 0 && bi.i.a(100) < this.a.dq()) {
            dmg += 180.0;
            this.f.b(new ee(this.f.fr(), 13338));
        }
        if (this.a.da() && bi.i.a(100) < 5) {
            this.a.a(new ee(this.a.fr(), 13749));
            this.a.b(new ee(this.a.fr(), 13749));
            if (!this.a.bB(1027) && !this.a.bB(1038)) {
                this.a.j(1027, 5000);
                this.a.a(new bs(this.a.fr(), 8));
                this.a.b(new bs(this.a.fr(), 8));
            }
        }
        if ((this.f instanceof v || this.f instanceof z) && this.f.L() && !as.b.a().a(this.f)) {
            dmg /= 8.0;
        }
        if (dmg <= 0.0) {
            this.l = false;
        }
        return (int)dmg;
    }

    private boolean n() {
        int[] nArray = F;
        int n2 = F.length;
        int n3 = 0;
        while (n3 < n2) {
            int skillid = nArray[n3];
            if (this.d.bB(skillid)) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    private int o() {
        double dmg = this.e.J() + bi.i.a(this.e.K());
        if (this.e instanceof v) {
            dmg += (double)(this.e.ev() / 16);
            dmg += (double)((v)this.e).au();
        }
        dmg += (double)this.e.eR();
        if (this.s()) {
            dmg *= 1.1;
        }
        int ac2 = Math.max(0, 10 - this.d.ey());
        int acDefMax = this.d.aC().b(ac2);
        dmg -= (double)bi.i.a(acDefMax + 1);
        if (this.e.ab()) {
            dmg /= 2.0;
        }
        dmg -= (double)this.d.bV();
        dmg -= (double)this.d.ah();
        if (this.n()) {
            dmg -= 5.0;
        }
        if (this.d.bB(3015) || this.d.bB(3031) || this.d.bB(3047)) {
            dmg -= 5.0;
        }
        if (this.d.bB(88)) {
            int targetPcLvl = this.d.ev();
            if (targetPcLvl < 50) {
                targetPcLvl = 50;
            }
            dmg -= (double)((targetPcLvl - 50) / 5 + 1);
        }
        if (this.d.bB(181)) {
            dmg -= 2.0;
        }
        if (this.d.bB(211)) {
            dmg -= 2.0;
        }
        if (this.d.bB(159)) {
            dmg -= 2.0;
        }
        if (this.d.dC() > 0 && bi.i.a(100) < 5) {
            dmg -= (double)this.d.dC();
        }
        if (this.d.bB(605) && bi.i.a(100) < 5) {
            dmg -= (double)(Math.abs(this.d.ey()) / 10);
            this.d.a(new ee(this.d.fr(), 12536));
            this.d.b(new ee(this.d.fr(), 12536));
        }
        if (this.d.db() && bi.i.a(100) < 10) {
            this.d.a(new ee(this.d.fr(), 13702));
            this.d.b(new ee(this.d.fr(), 13702));
            dmg -= 30.0;
        }
        if (this.d.df() > bi.i.a(100)) {
            this.d.a(new ee(this.d.fr(), 13702));
            this.d.b(new ee(this.d.fr(), 13702));
            dmg -= 50.0;
        }
        if (this.d.bB(4058)) {
            dmg -= 60.0;
        }
        if (this.d.bB(68)) {
            dmg /= 2.0;
        } else if (this.d.dg() && bi.i.a(100) < 5) {
            this.d.a(new ee(this.d.fr(), 11101));
            this.d.b(new ee(this.d.fr(), 11101));
            this.d.j(68, 3000);
            this.d.a(new ds(314));
            this.d.a(new cm(40, 3));
            dmg /= 2.0;
        } else if (this.d.di() && bi.i.a(100) < 5) {
            this.d.a(new ee(this.d.fr(), 11101));
            this.d.b(new ee(this.d.fr(), 11101));
            this.d.j(68, 10000);
            this.d.a(new ds(314));
            this.d.a(new cm(40, 10));
            dmg /= 2.0;
        }
        if (this.d.dj() && bi.i.a(100) < 5) {
            this.d.a(new ee(this.d.fr(), 14646));
            this.d.b(new ee(this.d.fr(), 14646));
            this.d.a(this.d.ea() + 20 + bi.i.a(20));
        }
        if (this.d.dk() && bi.i.a(100) < 7) {
            this.d.a(new ee(this.d.fr(), 14649));
            this.d.b(new ee(this.d.fr(), 14649));
            this.d.a(this.d.ea() + 130 + bi.i.a(40));
        }
        if (this.d.do() > 0 && bi.i.a(100) < this.d.do()) {
            this.d.a(new ee(this.d.fr(), 8909));
            this.d.b(new ee(this.d.fr(), 8909));
            this.d.a(this.d.ea() + 130 + bi.i.a(40));
        }
        if (this.d.dp() > 0 && bi.i.a(100) < this.d.dp()) {
            new dp().a((f)this.d, 5);
        }
        if (this.d.dh() && bi.i.a(100) < 7) {
            this.d.a(new ee(this.d.fr(), 12357));
            this.d.b(new ee(this.d.fr(), 12357));
            this.d.i_(this.d.eb() + 10);
        }
        if (this.d.dc() && dmg > 0.0 && bi.i.a(100) < 7) {
            this.d.a(new ee(this.e.fr(), 9801));
            this.d.b(new ee(this.e.fr(), 9801));
            w _magic = new w(this.d, this.e);
            _magic.a(80, 0);
            this.e.b(new ak(this.e.fr(), 2));
        }
        if (this.d.dl() && dmg > 0.0 && bi.i.a(100) < 7) {
            this.d.a(new ee(this.d.fr(), 14453));
            this.d.b(new ee(this.d.fr(), 14453));
            w _magic = new w(this.d, this.e);
            _magic.a(120, 0);
            this.e.b(new ak(this.e.fr(), 2));
        }
        if (dmg <= 0.0) {
            this.l = false;
        }
        this.b(this.e, this.d);
        return (int)dmg;
    }

    private int p() {
        double dmg = this.e.J() + bi.i.a(this.e.K());
        if (this.e instanceof v) {
            dmg += (double)(this.e.ev() / 16);
            dmg += (double)((v)this.e).au();
        }
        if (this.s()) {
            dmg *= 1.1;
        }
        dmg -= (double)this.f.U_().V();
        if (this.e.ab()) {
            dmg /= 2.0;
        }
        this.b(this.e, this.f);
        if (dmg <= 0.0) {
            this.l = false;
        }
        return (int)dmg;
    }

    private double c(double dmg) {
        if ((this.a.bB(102) || this.a.bB(171) || this.a.bB(117)) && bi.i.a(100) < 33) {
            dmg *= 1.5;
        }
        if (this.a.D() && this.s == 24) {
            dmg += (double)this.a.dI();
            if (this.a.bB(5003)) {
                dmg += 9.0;
            } else if (this.a.bB(5002)) {
                dmg += 6.0;
            } else if (this.a.bB(5001)) {
                dmg += 3.0;
            }
        }
        if (this.a.cM() && this.a.bB(5003)) {
            dmg *= 1.3;
        }
        if (this.a.bB(182)) {
            dmg += 10.0;
            this.a.a(new aq(this.b.fs(), this.b.ft(), 6591));
            this.a.b(new aq(this.b.fs(), this.b.ft(), 6591));
            this.a.bA(182);
        }
        if (this.a.bB(114)) {
            dmg += 5.0;
        }
        if (this.a.bB(4058)) {
            dmg += 100.0;
        }
        return dmg;
    }

    private int q() {
        int damage = 0;
        int undead = this.f.U_().B();
        if (!(this.z != 14 && this.z != 17 && this.z != 22 || undead != 1 && undead != 3 && undead != 5)) {
            damage += bi.i.a(20) + 1;
        } else if ((this.z == 17 || this.z == 22) && undead == 2) {
            damage += bi.i.a(3) + 1;
        }
        if (this.x == 0 && (undead == 1 || undead == 2 || undead == 3)) {
            damage += bi.i.a(4) + 1;
        }
        if (this.a.v() != null && this.s != 20 && this.s != 62 && this.p.Q() != 0 && (undead == 1 || undead == 3)) {
            damage += this.p.Q();
        }
        return damage;
    }

    private int r() {
        if (this.C <= 0) {
            return 0;
        }
        int damage = this.C * 2 - 1;
        int resist = 0;
        if (this.g == 1) {
            if (this.B == 1) {
                resist = this.d.eI();
            } else if (this.B == 2) {
                resist = this.d.eH();
            } else if (this.B == 4) {
                resist = this.d.eG();
            } else if (this.B == 8) {
                resist = this.d.eF();
            }
        } else if (this.g == 2 && this.B == this.f.U_().r()) {
            resist = -50;
        }
        double resistFloor = 0.32 * (double)Math.abs(resist);
        resistFloor = resist >= 0 ? (resistFloor *= 1.0) : (resistFloor *= -1.0);
        double attrCoefficient = 1.0 - resistFloor / 32.0;
        damage = (int)((double)damage * attrCoefficient);
        return damage;
    }

    private boolean s() {
        int undead = this.e.U_().B();
        return (undead == 1 || undead == 3 || undead == 4) && at.c.a().b().e();
    }

    private void b(f attacker, f target) {
        if (this.e.U_().C() == 0) {
            return;
        }
        if (bi.i.a(100) < 15) {
            if (this.e.U_().C() == 1) {
                az.a.a(attacker, target, 3000, 5, 30);
            } else if (this.e.U_().C() == 2) {
                az.d.b(target, 120);
            } else if (this.e.U_().C() == 4) {
                az.b.a(target, 20000, 15000);
            }
        }
    }

    public void a(f attacker, f target) {
        if ((this.r == 13 || this.r == 44 || this.r != 0 && this.a.bB(98)) && bi.i.a(100) < 10) {
            az.a.a(attacker, target, 3000, 5, 30);
        }
    }

    public void c() {
        if (this.g == 0 || this.g == 1 || this.g == 2) {
            this.t();
        } else if (this.g == 3 || this.g == 4) {
            this.u();
        }
    }

    private void t() {
        this.a.ct(this.a.h(this.b.fs(), this.b.ft()));
        if (this.s == 20 && (this.q != null || this.r == 190 || this.r == 399)) {
            if (this.q != null) {
                this.a.j().b(this.q, 1);
                this.o = 66;
                if (this.a.fe() == 8719) {
                    this.o = 8721;
                } else if (this.a.fe() == 8900) {
                    this.o = 8904;
                } else if (this.a.fe() == 8913) {
                    this.o = 8916;
                } else if (this.a.fe() == 11402) {
                    this.o = 8904;
                } else if (this.a.fe() == 11406) {
                    this.o = 8916;
                } else if (this.a.fe() == 13635) {
                    this.o = 13656;
                } else if (this.a.fe() == 13635) {
                    this.o = 13658;
                } else if (this.a.fe() == 12314) {
                    this.o = 8916;
                } else if (this.a.fe() == 15814) {
                    this.o = 8916;
                }
                if (this.a.cL()) {
                    this.o = 11762;
                    if (this.a.fe() == 13635) {
                        this.o = 13657;
                    } else if (this.a.fe() == 13635) {
                        this.o = 13659;
                    } else if (this.a.fe() == 11402) {
                        this.o = 8904;
                    } else if (this.a.fe() == 11406) {
                        this.o = 8916;
                    } else if (this.a.fe() == 12314) {
                        this.o = 8916;
                    } else if (this.a.fe() == 15814) {
                        this.o = 8916;
                    }
                }
            } else if (this.r == 190 || this.r == 399) {
                this.o = 2349;
            }
        } else if (this.s == 62 && this.q != null) {
            this.a.j().b(this.q, 1);
            this.o = 2989;
        }
        if (!this.l) {
            this.m = 0;
        }
        if (this.o > 0) {
            this.a.a(new g(this.a, this.b, 1, this.o, this.m, 0, this.n));
            this.a.b(new g(this.a, this.b, 1, this.o, this.m, 0, this.n));
        } else {
            this.a.a(new g(this.a, this.b.fr(), 1, this.m, this.n));
            this.a.b(new g(this.a, this.b.fr(), 1, this.m, this.n));
        }
        if (this.l) {
            this.b.a(new ak(this.b.fr(), 2), this.a);
        }
    }

    private void u() {
        int currentBowGfxid;
        int actId = am.c.a().a(this.e.fe(), this.e.U_().Z() > 0);
        this.e.ct(this.e.h(this.b.fs(), this.b.ft()));
        boolean isLongRange = false;
        if (this.e.C() > 1) {
            boolean bl2 = isLongRange = this.e.fu().c(new h(this.b.fs(), this.b.ft())) > 1;
        }
        if ((currentBowGfxid = this.e.am()) == 0) {
            currentBowGfxid = this.e.U_().Z();
        }
        if (bi.i.a(100) < 40) {
            this.m = (int)((double)this.m * 1.2);
        } else if (!isLongRange || currentBowGfxid == 0) {
            this.m = (int)((double)this.m * (currentBowGfxid > 0 ? 1.2 : 1.0));
        }
        if (!this.l) {
            this.m = 0;
        }
        if (currentBowGfxid > 0 && isLongRange) {
            this.e.b(new g(this.e, this.b, actId, currentBowGfxid, this.m, 0, 0));
        } else {
            this.e.b(new g(this.e, this.b.fr(), actId, this.m, 0));
        }
        if (this.l) {
            this.b.a(new ak(this.b.fr(), 2), this.e);
        }
    }

    public void d() {
        if (this.l) {
            if (this.g == 1 || this.g == 3) {
                this.v();
            } else if (this.g == 2 || this.g == 4) {
                this.w();
            }
        }
        if (!(this.g != 1 && this.g != 2 || this.m <= this.a.bz() || this.a.fp() == 413 || this.a.fp() >= 2600 && this.a.fp() <= 2698)) {
            this.a.ax(this.m);
        }
        if (l1j.server.a.S) {
            if ((this.g == 1 || this.g == 2) && this.a.l()) {
                this.a.a(new cg(this.b, "\\\\fRfU\u2193 \u666e\u653b\u50b7\u5bb3\\\\fRfM (" + (this.l ? Integer.valueOf(this.m) : "miss") + ")"));
                this.a.a(new ei("\u5c0d" + this.b.et() + "\u9020\u6210\u666e\u653b\u50b7\u5bb3= " + (this.l ? Integer.valueOf(this.m) : "miss")));
            } else if (this.g == 1 || this.g == 3) {
                this.d.l();
            }
        }
    }

    private void v() {
        if (this.g == 1) {
            this.x();
            if (this.a.bB(196)) {
                this.y();
            }
            this.a(this.a, (f)this.d, this.m);
            this.d.a((f)this.a, (double)this.m, false);
        } else if (this.g == 3) {
            this.d.a(this.e, (double)this.m, false);
        }
    }

    private void w() {
        if (this.g == 2) {
            if (this.f.U_().W()) {
                this.z();
            }
            this.a(this.a, (f)this.f, this.m);
            this.f.b(this.a, this.m);
        } else if (this.g == 4) {
            this.f.b(this.e, this.m);
        }
    }

    private void x() {
        if (this.s == 0 || this.s == 20 || this.s == 62) {
            return;
        }
        if (!this.d.bB(89) || this.a.bB(175)) {
            return;
        }
        if (bi.i.a(100) < 10) {
            this.a.a(new ds(268, this.p.s()));
            this.a.j().g(this.p);
        }
    }

    private void y() {
        q armor = this.d.j().j(2);
        if (armor == null) {
            return;
        }
        if (bi.i.a(100) < 5) {
            this.d.a(new ds(268, armor.s()));
            this.d.j().g(armor);
        }
    }

    private void z() {
        if (this.s == 0 || this.s == 20 || this.s == 62 || this.s == 40 || this.s == 58 || this.s == 58) {
            return;
        }
        if (!this.p.a().aE() || this.a.bB(175)) {
            return;
        }
        if ((this.x == 1 || this.x == 2) && bi.i.a(100) < 10 || this.x == 0 && bi.i.a(100) < 3) {
            this.a.a(new ds(268, this.p.s()));
            this.a.j().g(this.p);
        }
    }

    public void e() {
        int gfxid = 0;
        if (this.g == 1) {
            this.a.ct(this.a.h(this.b.fs(), this.b.ft()));
            if (this.d.z()) {
                gfxid = 5846;
            } else if (this.d.D()) {
                gfxid = 9802;
            } else if (this.d.F()) {
                gfxid = this.f() ? 12555 : 12557;
            }
            this.a.a(new g(this.a, this.b.fr(), 1, 0, 0));
            this.a.b(new g(this.a, this.b.fr(), 1, 0, 0));
            this.a.a(new ee(this.a.fr(), gfxid));
            this.a.b(new ee(this.a.fr(), gfxid));
            this.a.a(new ak(this.a.fr(), 2));
            this.a.b(new ak(this.a.fr(), 2));
        } else if (this.g == 3) {
            this.e.ct(this.e.h(this.b.fs(), this.b.ft()));
            if (this.d.z()) {
                gfxid = 5846;
            } else if (this.d.D()) {
                gfxid = 9802;
            } else if (this.d.F()) {
                gfxid = this.f() ? 12555 : 12557;
            }
            this.e.b(new g(this.e, this.b.fr(), 1, 0, 0));
            this.e.b(new ee(this.e.fr(), gfxid));
            this.e.b(new ak(this.e.fr(), 2));
        }
    }

    public boolean f() {
        if (this.g == 1) {
            if (this.s == 20 || this.s == 62 || this.s == 58) {
                return false;
            }
        } else if (this.g == 3) {
            boolean isLongRange = this.e.fu().c(new h(this.b.fs(), this.b.ft())) > 1;
            int bowActId = this.e.am();
            if (bowActId == 0) {
                bowActId = this.e.U_().Z();
            }
            if (isLongRange && bowActId > 0) {
                return false;
            }
        }
        return true;
    }

    public void g() {
        int damage = this.m + this.A();
        if (damage == 0) {
            return;
        }
        if (this.g == 1) {
            this.a.a((f)this.d, (double)damage, false);
        } else if (this.g == 3) {
            this.e.b(this.d, damage);
        }
    }

    private int A() {
        int damage = 0;
        if (this.s == 257) {
            damage = (this.p.a().w() + this.p.G() + this.p.a().ac()) * 2;
        }
        return damage;
    }

    private void B() {
        if (this.s != 24) {
            return;
        }
        int random = bi.i.a(100) + 1;
        if (this.a.bB(5003)) {
            if (random > 30 && random <= 60) {
                this.a.j(5003, 16000);
                this.a.a(new cm(75, 3));
            }
        } else if (this.a.bB(5002)) {
            if (random <= 30) {
                this.a.j(5002, 16000);
                this.a.a(new cm(75, 2));
            } else if (random >= 70) {
                this.a.j(5003, 16000);
                this.a.a(new cm(75, 3));
            }
        } else if (this.a.bB(5001)) {
            if (random <= 40) {
                this.a.j(5001, 16000);
                this.a.a(new cm(75, 1));
            } else if (random >= 70) {
                this.a.j(5002, 16000);
                this.a.a(new cm(75, 2));
            }
        } else {
            int baseProbability;
            int n2 = baseProbability = this.p.N() == 354 ? 18 : 12;
            if (random <= baseProbability) {
                this.a.j(5001, 16000);
                this.a.a(new cm(75, 1));
            }
        }
    }

    private double a(u pc, f cha, q weapon) {
        double dmg = 0.0;
        if (bi.i.a(100) < 3) {
            dmg = cha.ea() * 2 / 3;
            pc.a(new ds(158, weapon.s()));
            pc.j().f(weapon);
        }
        return dmg;
    }

    private double a(u pc, f cha) {
        int value = 0;
        int diceCount = 2 + pc.v().a().J() > 0 ? 1 : 0;
        int i2 = 0;
        while (i2 < diceCount) {
            value += bi.i.a(5) + 1;
            ++i2;
        }
        int kiringkuDamage = pc.v().a().v() / 3 + value + bi.d.g(pc.bj(), pc.eD());
        double dmg = kiringkuDamage + pc.v().G();
        dmg = aq.w.a(pc, cha, dmg, 0);
        if (pc.bB(219)) {
            dmg += 10.0;
        }
        if (pc.v().N() == 270) {
            pc.a(new ee(pc.fr(), 6983));
            pc.b(new ee(pc.fr(), 6983));
        } else {
            pc.a(new ee(pc.fr(), 7049));
            pc.b(new ee(pc.fr(), 7049));
        }
        return dmg;
    }

    private void a(u attacker, f target, int damage) {
        q weapon = attacker.v();
        if (weapon == null) {
            return;
        }
        int baseEnchantLavel = 100;
        int enchant_level = weapon.G();
        int[] effects = new int[]{10966, 11760, 11758, 8531, 5295, 5377, 6703, 7066, 10779, 5627, 5550, 7974, 10698, 11525, 11541, 11557, 11449, 8110};
        if (enchant_level >= 100) {
            int radius = enchant_level - 100 + 1;
            int offset = enchant_level - 100;
            offset = offset < effects.length ? offset : effects.length - 1;
            int gfxid = effects[offset];
            if (target instanceof u) {
                ((u)target).a(new ee(target.fr(), gfxid));
            }
            target.b(new ee(target.fr(), gfxid));
            for (aa obj : attacker.eq()) {
                if (!(obj instanceof s) || obj.fu().c(target.fu()) > radius) continue;
                ((s)obj).b(attacker, damage);
            }
        }
    }
}

