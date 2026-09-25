/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.ah;
import ap.q;
import ap.t;
import ap.u;
import ap.v;
import ap.z;
import aq.aq;
import aq.c;
import au.f;
import be.ak;
import be.cg;
import be.ds;
import bh.l;
import bi.e;
import bi.i;
import java.util.logging.Level;
import java.util.logging.Logger;

public class o
extends t {
    private static final Logger y = Logger.getLogger(o.class.getName());
    private final o z = this;
    private boolean A = false;
    private aq.f B;

    public o(l template) {
        super(template);
    }

    @Override
    public void c() {
        u targetPlayer = null;
        for (u pc : aq.a().f(this)) {
            if (pc.ea() <= 0 || pc.eX() || pc.l() || pc.bN() || pc.ff() && !this.V()) continue;
            if (!pc.A()) {
                targetPlayer = pc;
                this.d(new cg(this, "$804", 2));
                break;
            }
            if (!pc.A() || !pc.T()) continue;
            targetPlayer = pc;
            this.d(new cg(this, "$815", 1));
            break;
        }
        if (targetPlayer != null) {
            this.n.a(targetPlayer, 0);
            this.m = targetPlayer;
        }
    }

    @Override
    public void a(aq.f cha) {
        if (cha != null && this.n.b()) {
            this.n.a(cha, 0);
            this.d();
        }
    }

    @Override
    public void Z_() {
        if (this.ae()) {
            return;
        }
        if (!this.A && this.o.c() == 0) {
            if (this.z() == 70848) {
                this.o.a(40506, 6);
                this.o.a(40507, 36);
            } else if (this.z() == 70850) {
                this.o.a(40519, 18);
            }
        }
        this.w = false;
        this.q();
    }

    @Override
    public void a(u pc, int skillId) {
        if (this.ea() <= 0 || this.eX()) {
            return;
        }
        c attack = new c(pc, this, skillId);
        if (attack.a()) {
            int dmg = attack.b();
            if (pc.A() && (pc.k() == 0 || dmg <= 3 || pc.v() != null && (pc.v().N() == 5 || pc.v().N() == 100005))) {
                if (this.o.c() > 0) {
                    for (q item : this.o.d()) {
                        if (this.z() == 70848 && item.N() == 40499) {
                            this.o.f(item);
                            ah.a(pc, 40505, item.E(), this.T());
                        } else if (this.z() == 70846 && item.N() == 40507) {
                            this.o.f(item);
                            ah.a(pc, 40503, item.E(), this.T());
                        } else {
                            if (bi.i.a(100) >= 30) continue;
                            this.o.a(item, 1, (f)pc.j());
                            pc.a(new ds(143, this.T(), item.b()));
                        }
                        break;
                    }
                } else {
                    if (this.z() == 70848) {
                        this.b(new cg(this.z, "$822", 0));
                    } else if (this.z() == 70846) {
                        this.b(new cg(this.z, "$823", 0));
                    } else if (this.z() == 70850) {
                        this.b(new cg(this.z, "$824", 0));
                    }
                    if (!this.A) {
                        new a().a();
                    }
                }
            }
            attack.a((aq.f)pc, (aq.f)this);
        }
        attack.c();
        attack.d();
    }

    @Override
    public void b(aq.f attacker, int damage) {
        if (attacker instanceof u && damage > 0) {
            u pc = (u)attacker;
            if (pc.A() && (pc.k() == 0 || damage <= 3 || pc.v() != null && (pc.v().N() == 5 || pc.v().N() == 100005))) {
                return;
            }
            if (this.ea() > 0 && !this.eX()) {
                int newHp;
                if (damage >= 0) {
                    this.c(attacker, damage);
                }
                if (damage > 0) {
                    this.bz(66);
                    this.bz(153);
                }
                this.Z_();
                this.c(pc, this.U_().D());
                if (damage > 0) {
                    pc.a(this);
                }
                if ((newHp = this.ea() - damage) <= 0 && !this.eX()) {
                    this.bx(0);
                    this.X(true);
                    this.cq(8);
                    this.B = attacker;
                    b death = new b();
                    bi.e.a().a(death);
                }
                if (newHp > 0) {
                    this.a(newHp);
                }
            } else if (!this.eX()) {
                this.X(true);
                this.cq(8);
                this.B = attacker;
                b death = new b();
                bi.e.a().a(death);
            }
        }
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

    private class a
    implements Runnable {
        private a() {
        }

        public void a() {
            o.this.A = true;
            bi.e.a().a(this, l1j.server.a.av * 1000);
        }

        @Override
        public void run() {
            try {
                if (o.this.z() == 70848) {
                    o.this.o.a(bi.i.a(100) < 10 ? 40506 : 40507, 1);
                } else if (o.this.z() == 70850) {
                    o.this.o.a(40519, 1);
                } else {
                    return;
                }
                int allitemcount = 0;
                for (q item : o.this.o.d()) {
                    allitemcount += item.E();
                }
                if (allitemcount < 30) {
                    this.a();
                    return;
                }
                o.this.A = false;
            }
            catch (Exception e2) {
                y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class b
    implements Runnable {
        aq.f a;

        private b() {
            this.a = o.this.B;
        }

        @Override
        public void run() {
            o.this.j(true);
            o.this.bx(0);
            o.this.X(true);
            o.this.cq(8);
            int targetobjid = o.this.fr();
            o.this.fq().a(o.this.fu(), true);
            o.this.b(new ak(targetobjid, 8));
            u player = null;
            if (this.a instanceof u) {
                player = (u)this.a;
            } else if (this.a instanceof v) {
                player = (u)((v)this.a).M();
            } else if (this.a instanceof z) {
                player = (u)((z)this.a).M();
            }
            if (player != null) {
                bi.b.a(player, o.this, o.this.n);
                try {
                    ao.v.a().a((t)o.this.z, o.this.l);
                }
                catch (Exception e2) {
                    y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                }
                player.B((int)((double)o.this.P() * l1j.server.a.D));
            }
            o.this.j(false);
            o.this.A(0);
            o.this.k(0);
            o.this.t();
            o.this.A();
        }
    }
}

