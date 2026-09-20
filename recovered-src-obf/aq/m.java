/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.be;
import ao.c;
import ap.q;
import ap.u;
import aq.f;
import be.a;
import be.ae;
import be.cm;
import be.d;
import be.dc;
import be.do;
import be.dx;
import be.ea;
import bf.bi;
import bh.j;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class m {
    private final u a;
    private final CopyOnWriteArrayList<c.a> b;
    private final CopyOnWriteArrayList<q> c;
    private final ArrayList<q> d;

    public m(u owner) {
        this.a = owner;
        this.c = new CopyOnWriteArrayList();
        this.d = new ArrayList();
        this.b = new CopyOnWriteArrayList();
    }

    private void d(q weapon) {
        weapon.e(this.a);
        this.c.add(weapon);
        this.a.a(this.c);
        int type = weapon.a().aP();
        int range = weapon.a().aB();
        range = range < 0 ? 15 : range;
        this.a.a(new cm(160, range, type));
        if (weapon.N() == 145 || weapon.N() == 149) {
            this.a.C(true);
        }
        for (c.a armorSet : ao.c.a().b()) {
            if (!armorSet.a(weapon.N()) || !armorSet.a(this.a)) continue;
            armorSet.a(this.a, weapon, true);
            armorSet.b(this.a);
            this.b.add(armorSet);
        }
    }

    private void e(q weapon) {
        weapon.u();
        this.c.remove(weapon);
        if (this.a.bB(91)) {
            this.a.bz(91);
        }
        this.a.a(this.c);
        this.a.a(new cm(160, 1, 0));
        if (weapon.N() == 145 || weapon.N() == 149) {
            this.a.C(false);
        }
        for (c.a armorSet : ao.c.a().b()) {
            if (!armorSet.a(weapon.N()) || !this.b.contains(armorSet) || armorSet.a(this.a)) continue;
            armorSet.a(this.a, weapon, false);
            armorSet.c(this.a);
            this.b.remove(armorSet);
        }
    }

    private void f(q armor) {
        j item = armor.a();
        int itemId = armor.N();
        if (armor.i()) {
            this.a.bL(item.X() - armor.O() - armor.ad());
        } else {
            this.a.bL(item.X() - armor.G() - armor.O() - armor.ad());
        }
        this.a.F(item.Y() + armor.ap());
        this.a.C(armor.aT());
        this.a.D(item.Z());
        this.a.G(item.aa() + armor.as());
        this.a.H(item.ac() + armor.au() + armor.bl());
        this.a.I(item.ab() + armor.at());
        this.a.J(item.ad() + armor.av() + armor.bm());
        this.a.cd(item.ai() + armor.aF() + armor.bk());
        this.a.ce(item.aj() + armor.aA());
        this.a.cf(item.ak() + armor.aB());
        this.a.cg(item.al() + armor.aC());
        this.a.ch(item.am() + armor.aE() + armor.bj());
        this.a.ci(item.an() + armor.aD());
        this.a.cj(item.ao() + armor.aG());
        this.a.cb(item.ah() + armor.az() + armor.bi());
        this.a.bY(item.af() + armor.ax() + armor.bg());
        this.a.bZ(item.ae() + armor.aw() + armor.bf());
        this.a.ca(item.ag() + armor.ay() + armor.bh());
        this.a.P(armor.aL() + armor.bo());
        this.a.cA(armor.aK() + armor.bn());
        this.a.Q(item.au() + armor.ao());
        this.a.U(item.as() + armor.ar());
        this.a.V(armor.aP());
        this.a.W(armor.aQ());
        this.a.X(item.at() + armor.aR());
        this.a.ac(item.aw() + armor.aS());
        this.a.R(item.aq() + armor.aM());
        this.a.S(item.ar() + armor.aN());
        this.a.T(armor.aO());
        this.a.Y(armor.aY());
        this.a.Z(armor.ba());
        if (armor.B()) {
            this.a.T(true);
        }
        if (armor.aK() + armor.bn() != 0) {
            this.a.a(new cm(88, this.a.fk()));
        }
        this.d.add(armor);
        for (c.a armorSet : ao.c.a().b()) {
            if (!armorSet.a(itemId) || !armorSet.a(this.a)) continue;
            armorSet.a(this.a, armor, true);
            if (armor.h() && armor.a().aP() == 9) {
                if (armorSet.d(this.a)) continue;
                armorSet.b(this.a);
                this.b.add(armorSet);
                continue;
            }
            armorSet.b(this.a);
            this.b.add(armorSet);
        }
        if (itemId == 20077 || itemId == 20062 || itemId == 120077) {
            new bi().a((f)this.a, -1);
        } else if (itemId == 20281) {
            this.a.a(new a(2, true));
        } else if (itemId == 20288 || itemId == 21418) {
            this.a.a(new a(1, true));
        } else if (itemId == 20284) {
            this.a.a(new a(5, true));
        } else if (itemId == 20383) {
            if (armor.I() != 0) {
                armor.g(armor.I() - 1);
                this.a.j().b(armor);
            }
        } else if (itemId == 21397) {
            this.a.a(new dc(item.g(), -1, 0, 5934, 5934, 4647, 4647, 0, 1));
        } else if (itemId >= 21123 && itemId <= 21126) {
            this.a.E(true);
        } else if (itemId >= 21119 && itemId <= 21122) {
            this.a.D(true);
        } else if (itemId == 21446) {
            this.a.D(true);
        } else if (itemId == 21484) {
            this.a.P(true);
        } else if (itemId == 21485) {
            this.a.Q(true);
        } else if (itemId == 21486) {
            this.a.R(true);
        } else if (itemId == 21205 || itemId == 21517) {
            this.a.bm(armor.G() * 2);
        } else if (itemId == 21509) {
            this.a.bn(armor.G());
            this.a.M(true);
        } else if (itemId == 21510 || itemId == 21511) {
            this.a.bo(armor.G());
            this.a.bp(armor.G());
        } else if (itemId == 21473) {
            this.a.H(true);
            this.a.G(true);
        } else if (itemId == 21206) {
            this.a.F(true);
            this.a.G(true);
        } else if (itemId == 21207 || itemId == 21208) {
            this.a.G(true);
        } else if (itemId >= 21527 && itemId <= 21530) {
            this.a.bq(armor.aZ());
        } else if (itemId >= 21213 && itemId <= 21221) {
            this.a.I(true);
        } else if (itemId == 21474) {
            this.a.J(true);
        } else if (itemId == 21500) {
            this.a.S(true);
        } else if (itemId >= 21437 && itemId <= 21440) {
            this.a.K(true);
        } else if (itemId >= 21420 && itemId <= 21423) {
            this.a.L(true);
        } else if (itemId >= 21369 && itemId <= 21371) {
            this.a.O(true);
        } else if (itemId >= 21363 && itemId <= 21365) {
            if (armor.G() >= 10) {
                this.a.M(true);
            } else {
                this.a.N(true);
            }
        }
        armor.e(this.a);
    }

    public List<q> a() {
        return this.d;
    }

    private void g(q armor) {
        j item = armor.a();
        int itemId = armor.N();
        if (armor.i()) {
            this.a.bL(-(item.X() - armor.O() - armor.ad()));
        } else {
            this.a.bL(-(item.X() - armor.G() - armor.O() - armor.ad()));
        }
        this.a.F(-item.Y() - armor.ap());
        this.a.C(-armor.aT());
        this.a.D(-item.Z());
        this.a.G(-item.aa() - armor.as());
        this.a.H(-item.ac() - armor.au() - armor.bl());
        this.a.I(-item.ab() - armor.at());
        this.a.J(-item.ad() - armor.av() - armor.bm());
        this.a.cd(-item.ai() - armor.aF() - armor.bk());
        this.a.ce(-item.aj() - armor.aA());
        this.a.cf(-item.ak() - armor.aB());
        this.a.cg(-item.al() - armor.aC());
        this.a.ch(-item.am() - armor.aE() - armor.bj());
        this.a.ci(-item.an() - armor.aD());
        this.a.cj(-item.ao() - armor.aG());
        this.a.cb(-item.ah() - armor.az() - armor.bi());
        this.a.bY(-item.af() - armor.ax() - armor.bg());
        this.a.bZ(-item.ae() - armor.aw() - armor.bf());
        this.a.ca(-item.ag() - armor.ay() - armor.bh());
        this.a.P(-armor.aL() - armor.bo());
        this.a.cA(-armor.aK() - armor.bn());
        this.a.Q(-item.au() - armor.ao());
        this.a.U(-item.as() - armor.ar());
        this.a.V(-armor.aP());
        this.a.W(-armor.aQ());
        this.a.X(-item.at() - armor.aR());
        this.a.ac(-item.aw() - armor.aS());
        this.a.R(-item.aq() - armor.aM());
        this.a.S(-item.ar() - armor.aN());
        this.a.T(-armor.aO());
        this.a.Y(-armor.aY());
        this.a.Z(-armor.ba());
        if (armor.B()) {
            boolean isAnother = false;
            for (q other : this.d) {
                if (other.fr() == armor.fr() || !other.B()) continue;
                isAnother = true;
                break;
            }
            this.a.T(isAnother);
        }
        if (armor.aK() + armor.bn() != 0) {
            this.a.a(new cm(88, this.a.fk()));
        }
        for (c.a armorSet : ao.c.a().b()) {
            if (!armorSet.a(itemId) || !this.b.contains(armorSet) || armorSet.a(this.a)) continue;
            armorSet.a(this.a, armor, false);
            armorSet.c(this.a);
            this.b.remove(armorSet);
        }
        if (itemId == 20077 || itemId == 20062 || itemId == 120077) {
            this.a.s();
        } else if (itemId == 20281) {
            this.a.a(new a(2, false));
        } else if (itemId == 20288 || itemId == 21418) {
            this.a.a(new a(1, false));
        } else if (itemId == 20284) {
            this.a.a(new a(5, false));
        } else if (itemId == 21397) {
            this.a.a(new dc(item.g(), 0, 0, 5934, 5934, 0, 0, 0, 1));
        } else if (itemId >= 21123 && itemId <= 21126) {
            this.a.E(false);
        } else if (itemId >= 21119 && itemId <= 21122) {
            this.a.D(false);
        } else if (itemId == 21446) {
            this.a.D(false);
        } else if (itemId == 21484) {
            this.a.P(false);
        } else if (itemId == 21485) {
            this.a.Q(false);
        } else if (itemId == 21486) {
            this.a.R(false);
        } else if (itemId == 21205 || itemId == 21517) {
            this.a.bm(0);
        } else if (itemId == 21509) {
            this.a.bn(0);
            this.a.M(false);
        } else if (itemId == 21510 || itemId == 21511) {
            this.a.bo(0);
            this.a.bp(0);
        } else if (itemId >= 21527 && itemId <= 21530) {
            this.a.bq(0);
        } else if (itemId == 21473) {
            this.a.H(false);
            this.a.G(false);
        } else if (itemId == 21206) {
            this.a.F(false);
            this.a.G(false);
        } else if (itemId == 21207 || itemId == 21208) {
            this.a.G(false);
        } else if (itemId >= 21213 && itemId <= 21221) {
            this.a.I(false);
        } else if (itemId == 21474) {
            this.a.J(false);
        } else if (itemId == 21500) {
            this.a.S(false);
        } else if (itemId >= 21437 && itemId <= 21440) {
            this.a.K(false);
        } else if (itemId >= 21420 && itemId <= 21423) {
            this.a.L(false);
        } else if (itemId >= 21369 && itemId <= 21371) {
            this.a.O(false);
        } else if (itemId >= 21363 && itemId <= 21365) {
            if (armor.G() >= 10) {
                this.a.M(false);
            } else {
                this.a.N(false);
            }
        }
        armor.u();
        this.d.remove(armor);
    }

    public void a(q equipment) {
        j item = equipment.a();
        if (equipment.f()) {
            return;
        }
        if (item.M() != 0) {
            this.a.bH(item.M());
        }
        if (item.N() != 0) {
            this.a.bJ(item.N());
        }
        if (equipment.ab() != 0) {
            this.a.bH(equipment.ab());
        }
        if (equipment.ac() != 0) {
            this.a.bJ(equipment.ac());
        }
        this.a.bN(item.G() + equipment.ai());
        this.a.bP(item.I() + equipment.ak());
        this.a.bR(item.H() + equipment.aj());
        this.a.bV(item.J() + equipment.al());
        this.a.bX(item.K() + equipment.am());
        if (item.K() + equipment.am() != 0) {
            this.a.Y();
        }
        this.a.bT(item.L() + equipment.an());
        int addMr = 0;
        addMr += equipment.o() + equipment.af() + equipment.be();
        if (equipment.N() == 20236 && this.a.A()) {
            addMr += 5;
        }
        if (addMr != 0) {
            this.a.co(addMr);
        }
        if (item.Q() + equipment.ae() + equipment.bp() != 0) {
            this.a.cp(item.Q() + equipment.ae() + equipment.bp());
        }
        this.a.ab(item.ay() + equipment.aH());
        this.a.aa(item.av() + equipment.aq());
        if (item.S()) {
            this.a.E(1);
            this.a.V();
            if (this.a.fc() != 1) {
                this.a.cu(1);
                this.a.a(new ea(this.a.fr(), 1, -1));
                this.a.b(new ea(this.a.fr(), 1, -1));
            }
        }
        if (equipment.aU()) {
            this.a.z(true);
        }
        if (equipment.aV()) {
            this.a.A(true);
        }
        if (equipment.aW()) {
            this.a.B(true);
        }
        if (equipment.N() == 20383 && this.a.bB(1000)) {
            this.a.bA(1000);
            this.a.a(new dx(this.a.fr(), 0, 0));
            this.a.b(new dx(this.a.fr(), 0, 0));
            this.a.cv(0);
        }
        this.a.bK().c(equipment);
        if (equipment.g()) {
            this.d(equipment);
        } else if (equipment.h()) {
            this.f(equipment);
        }
        this.a.a(new do(this.a));
    }

    public void b(q equipment) {
        j item = equipment.a();
        if (equipment.f()) {
            return;
        }
        if (item.M() != 0) {
            this.a.bH(-item.M());
        }
        if (item.N() != 0) {
            this.a.bJ(-item.N());
        }
        if (equipment.ab() != 0) {
            this.a.bH(-equipment.ab());
        }
        if (equipment.ac() != 0) {
            this.a.bJ(-equipment.ac());
        }
        this.a.bN(-item.G() - equipment.ai());
        this.a.bP(-item.I() - equipment.ak());
        this.a.bR(-item.H() - equipment.aj());
        this.a.bV(-item.J() - equipment.al());
        this.a.bX(-item.K() - equipment.am());
        if (item.K() + equipment.am() != 0) {
            this.a.Y();
        }
        this.a.bT(-item.L() - equipment.an());
        int addMr = 0;
        addMr -= equipment.o() + equipment.af() + equipment.be();
        if (equipment.N() == 20236 && this.a.A()) {
            addMr -= 5;
        }
        if (addMr != 0) {
            this.a.co(addMr);
        }
        if (item.Q() + equipment.ae() + equipment.bp() != 0) {
            this.a.cp(-item.Q() - equipment.ae() - equipment.bp());
        }
        this.a.ab(-item.ay() - equipment.aH());
        this.a.aa(-item.av() - equipment.aq());
        if (item.S()) {
            this.a.E(-1);
            if (this.a.bU() == 0) {
                this.a.cu(0);
                this.a.a(new ea(this.a.fr(), 0, 0));
                this.a.b(new ea(this.a.fr(), 0, 0));
            }
        }
        if (equipment.aU()) {
            this.a.z(false);
        }
        if (equipment.aV()) {
            this.a.A(false);
        }
        if (equipment.aW()) {
            this.a.B(false);
        }
        this.a.bK().a(this.a.fr(), equipment);
        if (equipment.g()) {
            this.e(equipment);
        } else if (equipment.h()) {
            this.g(equipment);
        }
        this.a.a(new do(this.a));
    }

    public void c(q item) {
        switch (item.N()) {
            case 20013: {
                this.a.f(26);
                this.a.f(43);
                this.a.a(new d(this.a, 26, 43));
                break;
            }
            case 20014: {
                this.a.f(1);
                this.a.f(19);
                this.a.a(new d(this.a, 1, 19));
                break;
            }
            case 20015: {
                this.a.f(12);
                this.a.f(13);
                this.a.f(42);
                this.a.a(new d(this.a, 12, 13, 42));
                break;
            }
            case 20008: {
                this.a.f(43);
                this.a.a(new d(this.a, 43));
                break;
            }
            case 20023: {
                this.a.f(43);
                this.a.f(54);
                this.a.a(new d(this.a, 43, 54));
            }
        }
    }

    private void a(int objectId, q item) {
        switch (item.N()) {
            case 20013: {
                if (!be.a().a(objectId, 26)) {
                    this.a.g(26);
                    this.a.a(new ae(26));
                }
                if (be.a().a(objectId, 43)) break;
                this.a.g(43);
                this.a.a(new ae(43));
                break;
            }
            case 20014: {
                if (!be.a().a(objectId, 1)) {
                    this.a.g(1);
                    this.a.a(new ae(1));
                }
                if (be.a().a(objectId, 19)) break;
                this.a.g(19);
                this.a.a(new ae(19));
                break;
            }
            case 20015: {
                if (!be.a().a(objectId, 12)) {
                    this.a.g(12);
                    this.a.a(new ae(12));
                }
                if (!be.a().a(objectId, 13)) {
                    this.a.g(13);
                    this.a.a(new ae(13));
                }
                if (be.a().a(objectId, 42)) break;
                this.a.g(42);
                this.a.a(new ae(42));
                break;
            }
            case 20008: {
                if (be.a().a(objectId, 43)) break;
                this.a.g(43);
                this.a.a(new ae(43));
                break;
            }
            case 20023: {
                if (!be.a().a(objectId, 43)) {
                    this.a.g(43);
                    this.a.a(new ae(43));
                }
                if (be.a().a(objectId, 54)) break;
                this.a.g(54);
                this.a.a(new ae(54));
            }
        }
    }
}

