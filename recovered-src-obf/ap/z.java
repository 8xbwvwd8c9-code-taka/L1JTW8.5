/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ai.d;
import ao.au;
import ao.ax;
import ap.q;
import ap.s;
import ap.t;
import ap.u;
import ap.v;
import aq.aa;
import aq.aq;
import aq.c;
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
import bh.p;
import bi.e;
import bi.i;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class z
extends t {
    private ScheduledFuture<?> y;
    private static final long z = 3600000L;
    private int A;
    private final boolean B;
    private boolean C = false;
    private int D;

    @Override
    public boolean a() {
        switch (this.A) {
            case 3: {
                return true;
            }
            case 4: {
                if (this.k == null || this.k.fp() != this.fp() || this.fu().c(this.k.fu()) >= 5) {
                    this.A = 3;
                    return true;
                }
                this.D = this.c(this.k.fs(), this.k.ft());
                this.D = this.a(this.fs(), this.ft(), this.fp(), this.D);
                this.g(this.D);
                this.v(this.f(this.N(), 0));
                return false;
            }
            case 5: {
                if (Math.abs(this.X() - this.fs()) > 1 || Math.abs(this.Y() - this.ft()) > 1) {
                    this.D = this.a(this.X(), this.Y());
                    if (this.D == -1) {
                        this.q(this.fs());
                        this.r(this.ft());
                    } else {
                        this.g(this.D);
                        this.v(this.f(this.N(), 0));
                    }
                }
                return false;
            }
        }
        if (this.k != null && this.k.fp() == this.fp()) {
            if (this.fu().c(this.k.fu()) > 2) {
                this.D = this.a(this.k.fs(), this.k.ft());
                this.g(this.D);
                this.v(this.f(this.N(), 0));
            }
        } else {
            this.A = 3;
            return true;
        }
        return false;
    }

    public z(l template, aq.f master) {
        super(template);
        this.cF(ai.d.a().c());
        this.y = bi.e.a().a(new a(), 3600000L);
        this.e(master);
        this.cG(master.fs() + bi.i.a(5) - 2);
        this.cH(master.ft() + bi.i.a(5) - 2);
        this.cE(master.fp());
        this.ct(5);
        this.s(template.ae());
        this.A = 3;
        this.B = false;
        aq.a().a(this);
        aq.a().c(this);
        for (u pc : aq.a().f(this)) {
            this.b(pc);
        }
        master.e(this);
    }

    public z(s target, aq.f master, boolean isCreateZombie) {
        super(null);
        this.cF(ai.d.a().c());
        if (isCreateZombie) {
            int npcId = 45065;
            u pc = (u)master;
            int level = pc.ev();
            if (pc.B()) {
                if (level >= 24 && level <= 31) {
                    npcId = 81183;
                } else if (level >= 32 && level <= 39) {
                    npcId = 81184;
                } else if (level >= 40 && level <= 43) {
                    npcId = 81185;
                } else if (level >= 44 && level <= 47) {
                    npcId = 81186;
                } else if (level >= 48 && level <= 51) {
                    npcId = 81187;
                } else if (level >= 52) {
                    npcId = 81188;
                }
            } else if (pc.A() && level >= 48) {
                npcId = 81183;
            }
            l template = au.a().a(npcId).a();
            this.a(template);
        } else {
            this.a(target.U_());
            this.bx(target.ea());
            this.by(target.eb());
        }
        this.y = bi.e.a().a(new a(), 3600000L);
        this.e(master);
        this.cG(target.fs());
        this.cH(target.ft());
        this.cE(target.fp());
        this.ct(target.fb());
        this.s(target.aa());
        this.o(6);
        if (!target.i()) {
            ao.v.a().a((t)target, target.y());
        }
        this.a(target.y());
        target.a((f)null);
        this.A = 3;
        this.B = true;
        for (t each : master.ek().values()) {
            each.c(target);
        }
        target.aa_();
        aq.a().a(this);
        aq.a().c(this);
        for (u pc : aq.a().f(this)) {
            this.b(pc);
        }
        master.e(this);
    }

    @Override
    public void b(aq.f attacker, int damage) {
        if (this.ea() > 0) {
            int newHp;
            if (damage > 0) {
                this.c(attacker, 0);
                this.bz(66);
                this.bz(153);
                if (!this.L()) {
                    this.A = 1;
                    this.f(attacker);
                }
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
                this.j();
            } else {
                this.a(newHp);
            }
        } else if (!this.eX()) {
            System.out.println("\u8b66\u544a\uff1a\u30b5\u30e2\u30f3\u306e\uff28\uff30\u6e1b\u5c11\u51e6\u7406\u304c\u6b63\u3057\u304f\u884c\u308f\u308c\u3066\u3044\u306a\u3044\u7b87\u6240\u304c\u3042\u308a\u307e\u3059\u3002\u203b\u3082\u3057\u304f\u306f\u6700\u521d\u304b\u3089\uff28\uff30\uff10");
            this.j();
        }
    }

    private synchronized void j() {
        if (!this.eX()) {
            this.X(true);
            this.a(0);
            this.cq(8);
            this.fq().a(this.fu(), true);
            f targetInventory = this.k.y();
            List<q> items = this.o.d();
            for (q item : items) {
                if (this.k.y().a(item, item.E()) == 0) {
                    this.o.a(item, item.E(), targetInventory);
                    ((u)this.k).a(new ds(143, this.et(), item.s()));
                    continue;
                }
                targetInventory = aq.a().a(this.fs(), this.ft(), this.fp());
                this.o.a(item, item.E(), targetInventory);
            }
            if (this.B) {
                this.b(new ak(this.fr(), 8));
                this.A();
            } else {
                this.aa_();
            }
        }
    }

    public synchronized void h() {
        this.C = true;
        if (!this.B) {
            this.fq().a(this.fu(), true);
            f targetInventory = this.k.y();
            List<q> items = this.o.d();
            for (q item : items) {
                if (this.k.y().a(item, item.E()) == 0) {
                    this.o.a(item, item.E(), targetInventory);
                    ((u)this.k).a(new ds(143, this.et(), item.s()));
                    continue;
                }
                targetInventory = aq.a().a(this.fs(), this.ft(), this.fp());
                this.o.a(item, item.E(), targetInventory);
            }
            this.aa_();
        } else {
            this.k();
        }
    }

    private void k() {
        s monster = new s(this.U_());
        monster.cF(ai.d.a().c());
        monster.cG(this.fs());
        monster.cH(this.ft());
        monster.cE(this.fp());
        monster.ct(this.fb());
        monster.c(true);
        f inv = new f();
        for (q item : this.y().d()) {
            inv.d(item);
        }
        monster.a(inv);
        monster.bx(this.ea());
        monster.by(this.eb());
        monster.k(0);
        if (this.k instanceof u) {
            u pc = (u)this.k;
            pc.a(new ds(666, this.T()));
        }
        if (!this.eX()) {
            this.X(true);
            this.a(0);
            this.fq().a(this.fu(), true);
        }
        this.aa_();
        aq.a().a(monster);
        aq.a().c(monster);
    }

    @Override
    public synchronized void aa_() {
        if (this.ah()) {
            return;
        }
        if (!this.B && !this.C) {
            this.b(new ee(this.fr(), 169));
        }
        if (this.k instanceof u) {
            u pc = (u)this.k;
            pc.a(new cp(pc, this, false));
        }
        this.k.ek().remove(this.fr());
        super.aa_();
        if (this.y != null) {
            this.y.cancel(true);
            this.y = null;
        }
    }

    public void f(aq.f target) {
        if (target != null && (this.A == 1 || this.A == 2 || this.A == 5)) {
            this.c(target, 0);
            if (!this.ae()) {
                this.q();
            }
        }
    }

    public void g(aq.f target) {
        if (target != null && (this.A == 1 || this.A == 5)) {
            this.c(target, 0);
            if (!this.ae()) {
                this.q();
            }
        }
    }

    @Override
    public void a(u pc, int skillId) {
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        if (pc == null) {
            return;
        }
        aq.f cha = this.M();
        if (cha == null) {
            return;
        }
        u master = (u)cha;
        if (master.aR()) {
            return;
        }
        if ((this.ep() == 1 || pc.ep() == 1) && this.L()) {
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
        if (this.k.equals(player)) {
            player.a(new ct(this, 0));
        }
    }

    @Override
    public void a(u player, String action) {
        if (player == null || this.k != player) {
            return;
        }
        int status = this.c(action);
        if (status == 0) {
            return;
        }
        if (status == 6) {
            Object[] petList;
            u petMaster = (u)this.k;
            if (this.B) {
                this.k();
            } else {
                this.j();
            }
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
            Object[] objectArray = petList = this.k.ek().values().toArray();
            int n4 = petList.length;
            int n5 = 0;
            while (n5 < n4) {
                Object petObject = objectArray[n5];
                if (petObject instanceof z) {
                    z summon = (z)petObject;
                    summon.d(status);
                } else if (petObject instanceof v) {
                    p type;
                    int id;
                    v pet = (v)petObject;
                    if (player != null && player.ev() >= pet.ev() && pet.fj() > 0) {
                        pet.e(status);
                    } else if (!pet.eX() && (id = (type = ax.b().a(pet.U_().b())).h()) != 0) {
                        pet.b(new cg(pet, "$" + id, 0));
                    }
                }
                ++n5;
            }
        }
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new eh(this, perceivedFrom));
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
        if (this.k instanceof u) {
            u Master = (u)this.k;
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

    public void d(int i2) {
        this.A = i2;
        if (this.A == 5) {
            this.q(this.fs());
            this.r(this.ft());
        }
        if (this.A == 3) {
            this.t();
        } else if (!this.ae()) {
            this.q();
        }
    }

    public int i() {
        return this.A;
    }

    private class a
    implements Runnable {
        private a() {
        }

        @Override
        public void run() {
            if (z.this.ah()) {
                return;
            }
            if (z.this.B) {
                z.this.k();
            } else {
                z.this.j();
            }
        }
    }
}

