/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.be;
import ap.s;
import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.ak;
import be.ci;
import be.dh;
import be.ee;
import bf.bg;
import bh.l;
import bi.e;

public class h
extends t {
    private static final int y = 1000;
    private static final int z = 500;
    private static final int A = 8000;
    private static final int B = 1000;
    private u C;
    private int D;

    public h(l template) {
        super(template);
    }

    public void f() {
        if (this.fe() == 168) {
            bi.e.a().a(new b(this), 0L);
        } else if (this.fe() == 6706 || this.fe() == 6712 || this.fe() == 6718 || this.fe() == 6724) {
            bi.e.a().a(new a(this), 0L);
        } else if (this.fe() == 1263) {
            bi.e.a().a(new c(this), 0L);
        }
    }

    @Override
    public void aa_() {
        this.k(true);
        if (this.y() != null) {
            this.y().g();
        }
        this.t();
        this.k = null;
        aq.a().d(this);
        aq.a().b(this);
        for (u pc : aq.a().f(this)) {
            pc.d(this);
            pc.a(new dh(this));
        }
        this.es();
    }

    private void a(f cha, f effect) {
        int castGfx = be.a().a(this.h()).t();
        u pc = null;
        if (this.fe() == 6706) {
            if (!cha.bB(1018)) {
                cha.ca(30);
                if (cha instanceof u) {
                    pc = (u)cha;
                    pc.a(new ci(pc));
                    pc.a(new ee(pc.fr(), castGfx));
                }
                cha.b(new ee(cha.fr(), castGfx));
                cha.j(1018, 8000);
            }
        } else if (this.fe() == 6712) {
            if (!cha.bB(1020)) {
                cha.cb(30);
                if (cha instanceof u) {
                    pc = (u)cha;
                    pc.a(new ci(pc));
                    pc.a(new ee(pc.fr(), castGfx));
                }
                cha.b(new ee(cha.fr(), castGfx));
                cha.j(1020, 8000);
            }
        } else if (this.fe() == 6718) {
            if (!cha.bB(1022)) {
                cha.bY(30);
                if (cha instanceof u) {
                    pc = (u)cha;
                    pc.a(new ci(pc));
                    pc.a(new ee(pc.fr(), castGfx));
                }
                cha.b(new ee(cha.fr(), castGfx));
                cha.j(1022, 8000);
            }
        } else if (this.fe() == 6724 && !cha.bB(1025)) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new ee(pc.fr(), castGfx));
            }
            cha.b(new ee(cha.fr(), castGfx));
            cha.j(1025, 8000);
            bb.a cube = new bb.a(effect, cha, 1025);
            cube.a();
        }
    }

    private void b(f cha, f effect) {
        int castGfx2 = be.a().a(this.h()).u();
        u pc = null;
        if (this.fe() == 6706) {
            if (!cha.bB(1019)) {
                if (cha instanceof u) {
                    pc = (u)cha;
                    pc.a(new ee(pc.fr(), castGfx2));
                }
                cha.b(new ee(cha.fr(), castGfx2));
                cha.j(1019, 8000);
                bb.a cube = new bb.a(effect, cha, 1019);
                cube.a();
            }
        } else if (this.fe() == 6712) {
            if (!cha.bB(1021)) {
                if (cha instanceof u) {
                    pc = (u)cha;
                    pc.a(new ee(pc.fr(), castGfx2));
                }
                cha.b(new ee(cha.fr(), castGfx2));
                cha.j(1021, 8000);
                bb.a cube = new bb.a(effect, cha, 1021);
                cube.a();
            }
        } else if (this.fe() == 6718) {
            if (!cha.bB(1023)) {
                if (cha instanceof u) {
                    pc = (u)cha;
                    pc.a(new ee(pc.fr(), castGfx2));
                }
                cha.b(new ee(cha.fr(), castGfx2));
                cha.j(1023, 8000);
                bb.a cube = new bb.a(effect, cha, 1023);
                cube.a();
            }
        } else if (this.fe() == 6724 && !cha.bB(1025)) {
            if (cha instanceof u) {
                pc = (u)cha;
                pc.a(new ee(pc.fr(), castGfx2));
            }
            cha.b(new ee(cha.fr(), castGfx2));
            cha.j(1025, 8000);
            bb.a cube = new bb.a(effect, cha, 1025);
            cube.a();
        }
    }

    public void d(u pc) {
        this.C = pc;
    }

    public u g() {
        return this.C;
    }

    public void h_(int i2) {
        this.D = i2;
    }

    public int h() {
        return this.D;
    }

    private class a
    implements Runnable {
        private final h b;

        private a(h effect) {
            this.b = effect;
        }

        @Override
        public void run() {
            while (!h.this.ah()) {
                try {
                    for (aa objects : aq.a().b((aa)this.b, 3)) {
                        s mob;
                        if (objects instanceof u) {
                            u pc = (u)objects;
                            if (pc.eX()) continue;
                            u user = h.this.g();
                            if (pc.fr() == user.fr()) {
                                h.this.a(pc, this.b);
                                continue;
                            }
                            if (pc.aF() != 0 && user.aF() == pc.aF()) {
                                h.this.a(pc, this.b);
                                continue;
                            }
                            if (pc.q() && pc.aL().d(user)) {
                                h.this.a(pc, this.b);
                                continue;
                            }
                            if (pc.ep() == 1) {
                                if (!as.b.a().a((f)pc)) continue;
                                h.this.b(pc, this.b);
                                continue;
                            }
                            h.this.b(pc, this.b);
                            continue;
                        }
                        if (!(objects instanceof s) || (mob = (s)objects).eX()) continue;
                        h.this.b(mob, this.b);
                    }
                    Thread.sleep(500L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
        }
    }

    private class b
    implements Runnable {
        private final h b;
        private final bf.a c;

        private b(h effect) {
            this.b = effect;
            this.c = new bg();
        }

        @Override
        public void run() {
            while (!h.this.ah()) {
                try {
                    if (this.b.g() == null || this.b == null) continue;
                    for (f target : this.c.a((f)this.b.g(), this.b, 1)) {
                        w magic = new w(this.b.g(), target);
                        int damage = magic.a();
                        magic.a(damage, 0);
                        if (damage <= 0) continue;
                        if (target instanceof u) {
                            u pc = (u)target;
                            pc.a(new ak(pc.fr(), 2));
                        }
                        target.b(new ak(target.fr(), 2));
                    }
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
        }
    }

    private class c
    implements Runnable {
        private final h b;

        private c(h effect) {
            this.b = effect;
        }

        @Override
        public void run() {
            while (!h.this.ah()) {
                try {
                    for (aa objects : aq.a().b((aa)this.b, 0)) {
                        f cha;
                        if (!(objects instanceof f) || (cha = (f)objects) instanceof s) continue;
                        az.a.a(this.b, cha, 3000, 20, 30);
                    }
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
        }
    }
}

