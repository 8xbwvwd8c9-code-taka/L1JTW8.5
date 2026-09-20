/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ap.t;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.cc;
import be.ee;
import bh.l;
import bi.e;
import java.util.logging.Level;
import java.util.logging.Logger;

public class m
extends t {
    private static final Logger y = Logger.getLogger(m.class.getName());
    private boolean z = false;
    private int A = 0;

    public m(l template) {
        super(template);
    }

    @Override
    public void c(u pc) {
        pc.a(new ee(pc.fr(), this.A));
        f cha = new f();
        cha.cG(pc.fs() + 5);
        cha.cH(pc.ft() + 5);
        pc.ct(pc.a((aa)cha));
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new cc(this));
        perceivedFrom.a(new ee(this.fr(), this.A));
        if (!this.z) {
            this.z = true;
            new a().a();
        }
    }

    private void h() {
        for (u pc : aq.a().f(this)) {
            pc.a(new ee(this.fr(), this.A));
        }
    }

    public int f() {
        return this.A;
    }

    public void b(int i2) {
        this.A = i2;
    }

    private class a
    implements Runnable {
        private a() {
        }

        public void a() {
            bi.e.a().a(this);
        }

        @Override
        public void run() {
            try {
                while (m.this.z) {
                    m.this.h();
                    Thread.sleep(2000L);
                }
            }
            catch (Exception e2) {
                y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

