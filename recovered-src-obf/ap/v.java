/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ai.d;
import ao.av;
import ao.aw;
import ao.ax;
import ao.w;
import ap.q;
import ap.s;
import ap.t;
import ap.u;
import ap.z;
import aq.aa;
import aq.aq;
import aq.c;
import au.e;
import au.f;
import be.ak;
import be.az;
import be.cg;
import be.cp;
import be.ct;
import be.cu;
import be.ds;
import be.ee;
import be.eh;
import bh.l;
import bh.n;
import bh.o;
import bh.p;
import bi.i;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

public class v
extends t {
    private int y;
    private q z;
    private q A;
    private int B;
    private int C;
    private int D;
    private final u E;
    private int F;
    private p G;
    private int H;
    private ScheduledFuture<?> I;

    @Override
    public boolean a() {
        switch (this.D) {
            case 3: {
                return true;
            }
            case 4: {
                if (this.E == null || this.E.fp() != this.fp() || this.fu().c(this.E.fu()) >= 5) {
                    this.D = 3;
                    return true;
                }
                this.y = this.c(this.E.fs(), this.E.ft());
                this.y = this.a(this.fs(), this.ft(), this.fp(), this.y);
                this.g(this.y);
                this.v(this.f(this.N(), 0));
                return false;
            }
            case 5: {
                if (Math.abs(this.X() - this.fs()) > 1 || Math.abs(this.Y() - this.ft()) > 1) {
                    int dir = this.a(this.X(), this.Y());
                    if (dir == -1) {
                        this.q(this.fs());
                        this.r(this.ft());
                    } else {
                        this.g(dir);
                        this.v(this.f(this.N(), 0));
                    }
                }
                return false;
            }
            case 7: {
                if (this.E != null && this.E.fp() == this.fp() && this.fu().c(this.E.fu()) <= 1) {
                    this.D = 3;
                    return true;
                }
                int locx = this.E.fs() + bi.i.a(1);
                int locy = this.E.ft() + bi.i.a(1);
                this.y = this.a(locx, locy);
                if (this.y == -1) {
                    this.D = 3;
                    return true;
                }
                this.g(this.y);
                this.v(this.f(this.N(), 0));
                return false;
            }
        }
        if (this.E != null && this.E.fp() == this.fp()) {
            if (this.fu().c(this.E.fu()) > 2) {
                this.y = this.a(this.E.fs(), this.E.ft());
                this.g(this.y);
                this.v(this.f(this.N(), 0));
            }
        } else {
            this.D = 3;
            return true;
        }
        return false;
    }

    public v(l template, u master, n l1pet) {
        super(template);
        this.E = master;
        this.F = l1pet.a();
        this.G = ax.b().a(template.b());
        this.cF(l1pet.b());
        this.e(l1pet.d());
        this.b(l1pet.e());
        this.bG(l1pet.f());
        this.bx(l1pet.f());
        this.bI(l1pet.g());
        this.by(l1pet.g());
        this.k(l1pet.h());
        this.f(ao.w.a(l1pet.e(), l1pet.h()));
        this.cr(l1pet.i());
        this.c_(l1pet.j());
        this.aw();
        this.e(master);
        this.cG(master.fs() + bi.i.a(5) - 2);
        this.cH(master.ft() + bi.i.a(5) - 2);
        this.cE(master.fp());
        this.ct(5);
        this.s(template.ae());
        this.D = 3;
        aq.a().a(this);
        aq.a().c(this);
        for (u pc : aq.a().f(this)) {
            this.b(pc);
        }
        master.e(this);
    }

    public v(t target, u master, int itemid) {
        super(target.U_());
        this.E = master;
        this.F = itemid;
        this.G = ax.b().a(this.z());
        this.cF(ai.d.a().c());
        this.bx(target.ea());
        this.by(target.eb());
        this.k(750);
        this.f(0);
        this.cr(0);
        this.c_(50);
        this.aw();
        this.e(master);
        this.cG(target.fs());
        this.cH(target.ft());
        this.cE(target.fp());
        this.ct(target.fb());
        this.s(target.aa());
        this.o(6);
        this.a(target.y());
        target.a((f)null);
        this.D = 3;
        this.v();
        if (this.ew() > this.ea()) {
            this.u();
        }
        this.x();
        if (this.ex() > this.eb()) {
            this.w();
        }
        target.aa_();
        aq.a().a(this);
        aq.a().c(this);
        for (u pc : aq.a().f(this)) {
            this.b(pc);
        }
        master.e(this);
        aw.a().a(target, this.fr(), itemid);
    }

    @Override
    public void b(aq.f attacker, int damage) {
        if (this.ea() > 0) {
            int newHp;
            if (damage > 0) {
                this.c(attacker, 0);
                this.bz(66);
                this.bz(153);
            }
            if (attacker instanceof u && damage > 0) {
                u player = (u)attacker;
                player.a(this);
            }
            if (attacker instanceof v) {
                v pet = (v)attacker;
                if (this.ep() == 1 || pet.ep() == 1) {
                    damage = 0;
                }
            } else if (attacker instanceof z) {
                z summon = (z)attacker;
                if (this.ep() == 1 || summon.ep() == 1) {
                    damage = 0;
                }
            }
            if ((newHp = this.ea() - damage) <= 0) {
                this.ay();
            } else {
                this.a(newHp);
            }
        } else if (!this.eX()) {
            this.ay();
        }
    }

    private synchronized void ay() {
        if (!this.eX()) {
            this.X(true);
            this.ax();
            this.cq(8);
            this.a(0);
            this.fq().a(this.fu(), true);
            this.b(new ak(this.fr(), 8));
        }
    }

    public void d(int new_itemobjid) {
        v new_pet;
        n l1pet = aw.a().b(this.F);
        if (l1pet == null) {
            return;
        }
        int newNpcId = this.G.g();
        int evolvItem = this.G.i();
        short tmpMaxHp = this.ew();
        short tmpMaxMp = this.ex();
        this.g_(newNpcId);
        this.G = ax.b().a(newNpcId);
        this.b(1L);
        this.bG(tmpMaxHp / 2);
        this.bI(tmpMaxMp / 2);
        this.bx(this.ew());
        this.by(this.ex());
        this.k(0);
        this.f(0);
        this.y().b(evolvItem, 1);
        aa obj = aq.a().a(l1pet.b());
        if (obj != null && obj instanceof t) {
            new_pet = (v)obj;
            f new_petInventory = new_pet.y();
            List<q> itemList = this.y().d();
            for (q itemObject : itemList) {
                q item = itemObject;
                if (item == null) continue;
                if (item.D()) {
                    item.b(false);
                    o petItem = av.a().a(item.N());
                    if (petItem.n() == 1) {
                        this.b((q)null);
                        new_pet.a(this, item);
                    } else if (petItem.n() == 0) {
                        this.c((q)null);
                        new_pet.b(this, item);
                    }
                }
                if (new_pet.y().a(item, item.E()) == 0) {
                    this.y().a(item, item.E(), new_petInventory);
                    continue;
                }
                new_petInventory = aq.a().a(this.fs(), this.ft(), this.fp());
                this.y().a(item, item.E(), new_petInventory);
            }
            new_pet.b(new ee(new_pet.fr(), 2127));
        }
        n replacement = new n();
        replacement.a(new_itemobjid);
        replacement.b(this.fr());
        replacement.c(newNpcId);
        replacement.a(this.et());
        replacement.d(this.ev());
        replacement.e(this.ew());
        replacement.f(this.ex());
        replacement.g(this.m());
        replacement.h(l1pet.i());
        replacement.i(this.fj());
        if (!aw.a().replaceDurable(this.F, replacement)) {
            return;
        }
        this.F = new_itemobjid;
        if (obj != null && obj instanceof t) {
            new_pet = (v)obj;
            this.aw();
        }
    }

    private void az() {
        s monster = new s(this.U_());
        monster.cF(ai.d.a().c());
        monster.cG(this.fs());
        monster.cH(this.ft());
        monster.cE(this.fp());
        monster.ct(this.fb());
        monster.c(true);
        monster.a(this.y());
        this.a((f)null);
        monster.b(this.ev());
        monster.bG(this.ew());
        monster.bx(this.ea());
        monster.bI(this.ex());
        monster.by(this.eb());
        this.E.ek().remove(this.fr());
        if (this.E.ek().isEmpty()) {
            this.E.a(new cp(this.E, monster, false));
        }
        this.aa_();
        this.E.j().c(this.F, 1);
        aw.a().a(this.F);
        aq.a().a(monster);
        aq.a().c(monster);
        for (u pc : aq.a().f(monster)) {
            this.b(pc);
        }
    }

    public void b(boolean isDepositnpc) {
        f targetInventory = this.E.j();
        List<q> itemList = this.y().d();
        for (q itemObject : itemList) {
            q item = itemObject;
            if (item == null) continue;
            if (item.D()) {
                if (!isDepositnpc) continue;
                o petItem = av.a().a(item.N());
                if (petItem.n() == 1) {
                    this.b((q)null);
                } else if (petItem.n() == 0) {
                    this.c((q)null);
                }
                item.b(false);
            }
            if (this.E.j().a(item, item.E()) == 0) {
                this.y().a(item, item.E(), targetInventory);
                this.E.a(new ds(143, this.et(), item.s()));
                continue;
            }
            targetInventory = aq.a().a(this.fs(), this.ft(), this.fp());
            this.y().a(item, item.E(), targetInventory);
        }
    }

    public void h() {
        e targetInventory = aq.a().a(this.fs(), this.ft(), this.fp());
        for (q item : this.o.d()) {
            if (item.D()) {
                o petItem = av.a().a(item.N());
                if (petItem.n() == 1) {
                    this.b((q)null);
                } else if (petItem.n() == 0) {
                    this.c((q)null);
                }
                item.b(false);
            }
            this.o.a(item, item.E(), (f)targetInventory);
        }
    }

    public void i() {
        int id = this.G.a(bh.p.b(this.ev()));
        if (id != 0 && !this.eX()) {
            if (this.fj() == 0) {
                id = this.G.h();
            }
            this.b(new cg(this, "$" + id, 0));
        }
        if (this.fj() > 0) {
            this.e(7);
        } else {
            this.e(3);
        }
    }

    public void f(aq.f target) {
        if (target != null && (this.D == 1 || this.D == 2 || this.D == 5) && this.fj() > 0) {
            this.c(target, 0);
            if (!this.ae()) {
                this.q();
            }
        }
    }

    public void g(aq.f target) {
        if (target != null && (this.D == 1 || this.D == 5) && this.fj() > 0) {
            this.c(target, 0);
            if (!this.ae()) {
                this.q();
            }
        }
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new cu(this, perceivedFrom));
        if (this.eX()) {
            perceivedFrom.a(new ak(this.fr(), 8));
        }
    }

    @Override
    public void a(u pc, int skillId) {
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        aq.f cha = this.M();
        u master = (u)cha;
        if (master.aR()) {
            return;
        }
        if (this.ep() == 1) {
            c attack_mortion = new c(pc, this, skillId);
            attack_mortion.c();
            return;
        }
        if (pc.a(pc, this, false)) {
            return;
        }
        super.a(pc, skillId);
    }

    @Override
    public void a(u player) {
        if (this.eX()) {
            return;
        }
        if (this.E.equals(player)) {
            player.a(new ct(this, this.l()));
            n l1pet = aw.a().b(this.F);
            if (l1pet != null) {
                l1pet.g(this.m());
                l1pet.d(this.ev());
                l1pet.e(this.ew());
                l1pet.f(this.ex());
                l1pet.i(this.fj());
                aw.a().a(l1pet);
            }
        }
    }

    @Override
    public void a(u player, String action) {
        if (player == null || this.E != player) {
            return;
        }
        int status = this.c(action);
        if (status == 0) {
            return;
        }
        if (status == 6) {
            Object[] petList;
            u petMaster = (u)this.k;
            this.az();
            Object[] objectArray = petList = petMaster.ek().values().toArray();
            int n2 = petList.length;
            int n3 = 0;
            while (n3 < n2) {
                Object petObject = objectArray[n3];
                if (petObject instanceof z) {
                    z summon = (z)petObject;
                    petMaster.a(new eh(summon, petMaster));
                    return;
                }
                if (petObject instanceof v) {
                    v pet = (v)petObject;
                    petMaster.a(new cu(pet, petMaster));
                    return;
                }
                ++n3;
            }
        } else {
            Object[] petList;
            Object[] objectArray = petList = this.E.ek().values().toArray();
            int n4 = petList.length;
            int n5 = 0;
            while (n5 < n4) {
                Object petObject = objectArray[n5];
                if (petObject instanceof v) {
                    p type;
                    int id;
                    v pet = (v)petObject;
                    if (this.E != null && this.E.ev() >= pet.ev() && pet.fj() > 0) {
                        pet.e(status);
                    } else if (!pet.eX() && (id = (type = ax.b().a(pet.U_().b())).h()) != 0) {
                        pet.b(new cg(pet, "$" + id, 0));
                    }
                } else if (petObject instanceof z) {
                    z summon = (z)petObject;
                    summon.d(status);
                }
                ++n5;
            }
        }
    }

    @Override
    public void Y_() {
        if (!this.w) {
            this.e(1, 100);
        }
        if (this.ea() * 100 / this.ew() < 40) {
            this.e(0, 100);
        }
    }

    @Override
    public void a(q item) {
        Arrays.sort(r);
        Arrays.sort(s);
        if (Arrays.binarySearch(r, item.N()) >= 0) {
            if (this.ea() != this.ew()) {
                this.e(0, 100);
            }
        } else if (Arrays.binarySearch(s, item.N()) >= 0) {
            this.e(1, 100);
        }
    }

    private int c(String action) {
        int status = 0;
        if (action.equalsIgnoreCase("aggressive")) {
            status = 1;
        } else if (action.equalsIgnoreCase("defensive")) {
            status = 2;
        } else if (action.equalsIgnoreCase("stay")) {
            status = 3;
        } else if (action.equalsIgnoreCase("extend")) {
            status = 4;
        } else if (action.equalsIgnoreCase("alert")) {
            status = 5;
        } else if (action.equalsIgnoreCase("dismiss")) {
            status = 6;
        } else if (action.equalsIgnoreCase("getitem")) {
            this.b(false);
        }
        return status;
    }

    @Override
    public void a(int i2) {
        int currentHp = i2;
        if (currentHp >= this.ew()) {
            currentHp = this.ew();
        }
        this.bx(currentHp);
        if (this.ew() > this.ea()) {
            this.u();
        }
        if (this.E != null) {
            u Master = this.E;
            Master.a(new az(this));
        }
    }

    @Override
    public void i_(int i2) {
        int currentMp = i2;
        if (currentMp >= this.ex()) {
            currentMp = this.ex();
        }
        this.by(currentMp);
        if (this.ex() > this.eb()) {
            this.w();
        }
    }

    public void e(int i2) {
        this.D = i2;
        if (this.D == 5) {
            this.q(this.fs());
            this.r(this.ft());
        }
        if (this.D == 7) {
            this.t();
        }
        if (this.D == 3) {
            this.t();
        } else if (!this.ae()) {
            this.q();
        }
    }

    public int j() {
        return this.D;
    }

    public int k() {
        return this.F;
    }

    public void f(int expPercent) {
        this.H = expPercent;
    }

    public int l() {
        return this.H;
    }

    public void b(q weapon) {
        this.z = weapon;
    }

    public q n() {
        return this.z;
    }

    public void c(q armor) {
        this.A = armor;
    }

    public q o() {
        return this.A;
    }

    public void B(int i2) {
        this.B = i2;
    }

    public int p() {
        return this.B;
    }

    public void C(int i2) {
        this.C = i2;
    }

    public int au() {
        return this.C;
    }

    public p av() {
        return this.G;
    }

    public void aw() {
        this.I = bi.e.a().a(new a(), 1000L, 200000L);
    }

    public void ax() {
        if (this.I != null) {
            this.I.cancel(true);
        }
    }

    public void a(v pet, q weapon) {
        if (pet.n() == null) {
            this.c(pet, weapon);
        } else if (pet.n().equals(weapon)) {
            this.d(pet, pet.n());
        } else {
            this.d(pet, pet.n());
            this.c(pet, weapon);
        }
    }

    public void b(v pet, q armor) {
        if (pet.o() == null) {
            this.e(pet, armor);
        } else if (pet.o().equals(armor)) {
            this.f(pet, pet.o());
        } else {
            this.f(pet, pet.o());
            this.e(pet, armor);
        }
    }

    private void c(v pet, q weapon) {
        int itemId = weapon.N();
        o petItem = av.a().a(itemId);
        if (petItem == null) {
            return;
        }
        pet.B(petItem.b());
        pet.C(petItem.c());
        pet.bN(petItem.e());
        pet.bP(petItem.f());
        pet.bR(petItem.g());
        pet.bV(petItem.h());
        pet.bX(petItem.i());
        pet.bH(petItem.j());
        pet.bJ(petItem.k());
        pet.cp(petItem.l());
        pet.co(petItem.m());
        pet.b(weapon);
        weapon.b(true);
    }

    private void d(v pet, q weapon) {
        int itemId = weapon.N();
        o petItem = av.a().a(itemId);
        if (petItem == null) {
            return;
        }
        pet.B(0);
        pet.C(0);
        pet.bN(-petItem.e());
        pet.bP(-petItem.f());
        pet.bR(-petItem.g());
        pet.bV(-petItem.h());
        pet.bX(-petItem.i());
        pet.bH(-petItem.j());
        pet.bJ(-petItem.k());
        pet.cp(-petItem.l());
        pet.co(-petItem.m());
        pet.b((q)null);
        weapon.b(false);
    }

    private void e(v pet, q armor) {
        int itemId = armor.N();
        o petItem = av.a().a(itemId);
        if (petItem == null) {
            return;
        }
        pet.bL(petItem.d());
        pet.bN(petItem.e());
        pet.bP(petItem.f());
        pet.bR(petItem.g());
        pet.bV(petItem.h());
        pet.bX(petItem.i());
        pet.bH(petItem.j());
        pet.bJ(petItem.k());
        pet.cp(petItem.l());
        pet.co(petItem.m());
        pet.c(armor);
        armor.b(true);
    }

    private void f(v pet, q armor) {
        int itemId = armor.N();
        o petItem = av.a().a(itemId);
        if (petItem == null) {
            return;
        }
        pet.bL(-petItem.d());
        pet.bN(-petItem.e());
        pet.bP(-petItem.f());
        pet.bR(-petItem.g());
        pet.bV(-petItem.h());
        pet.bX(-petItem.i());
        pet.bH(-petItem.j());
        pet.bJ(-petItem.k());
        pet.cp(-petItem.l());
        pet.co(-petItem.m());
        pet.c((q)null);
        armor.b(false);
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            if (v.this.ah() || v.this.eX()) {
                v.this.I.cancel(true);
                return;
            }
            int _food = v.this.fj() - 2;
            if (_food <= 0) {
                v.this.c_(0);
                v.this.e(3);
                p type = ax.b().a(v.this.z());
                int id = type.h();
                if (id != 0) {
                    v.this.b(new cg(v.this, "$" + id, 0));
                }
            } else {
                v.this.c_(_food);
            }
            aw.a().a(v.this);
        }
    }
}

