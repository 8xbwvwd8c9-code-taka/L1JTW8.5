/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.u;
import aq.f;
import aq.x;
import be.az;
import be.cm;
import be.co;
import be.dc;
import be.ds;
import bf.dh;
import bi.e;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ac {
    private static final Logger a = Logger.getLogger(ac.class.getName());
    private final CopyOnWriteArrayList<u> b = new CopyOnWriteArrayList();
    private u c = null;

    public ac(u _leader) {
        this.c = _leader;
        this.b.add(_leader);
        this.h(_leader);
        e.a().b(new a(), 3000L);
    }

    public void a(u pc) {
        this.b.add(pc);
        this.h(pc);
    }

    private void h(u pc) {
        block3: {
            block2: {
                pc.a(this);
                this.k(pc);
                if (!pc.x() || !pc.h(121) || this.i(pc)) break block2;
                for (u member : this.b) {
                    new dh().a((f)member, 0);
                }
                break block3;
            }
            if (!this.i(pc)) break block3;
            for (u member : this.b) {
                new dh().a((f)member, 0);
            }
        }
    }

    private boolean i(u pc) {
        for (u member : this.b) {
            if (member.fr() == pc.fr() || !member.x() || !member.h(121)) continue;
            return true;
        }
        return false;
    }

    public void b(u pc) {
        if (this.e(pc) || this.b.size() <= 2) {
            this.e();
            return;
        }
        this.j(pc);
        for (u member : this.c()) {
            member.a(new ds(420, pc.et()));
        }
        pc.a(new ds(420, pc.et()));
    }

    public void c(u pc) {
        if (this.b.size() <= 2) {
            this.e();
            return;
        }
        this.j(pc);
        for (u member : this.c()) {
            member.a(new ds(420, pc.et()));
        }
        pc.a(new ds(419));
    }

    private void e() {
        for (u member : this.b) {
            if (!this.e(member)) {
                this.c.a(new ds(420, member.et()));
                this.j(member);
                member.a(new ds(418));
                continue;
            }
            member.a(new ds(418));
            this.j(member);
        }
    }

    private void j(u pc) {
        if (!this.b.contains(pc)) {
            return;
        }
        x.a().c(pc);
        this.b.remove(pc);
        pc.a((ac)null);
        for (u member : this.b) {
            member.a(new az(pc.fr(), 255, 255));
            pc.a(new az(member.fr(), 255, 255));
        }
        pc.a(new az(pc.fr(), 255, 255));
        if (pc.bB(121)) {
            pc.bz(121);
            if (this.b.size() <= 1) {
                return;
            }
        }
        if (pc.x() && pc.h(121) && !this.i(pc)) {
            for (u member : this.b) {
                member.bz(121);
            }
            return;
        }
        if (this.i(pc)) {
            for (u member : this.b) {
                new dh().a((f)member, 0);
            }
        }
    }

    private void k(u pc) {
        for (u member : this.b) {
            if (pc.fr() == this.c.fr() && this.b.size() == 1) continue;
            if (member.fr() == pc.fr()) {
                for (u m2 : this.c()) {
                    pc.a(new az(m2));
                }
                pc.a(new co(104, pc));
            } else {
                member.a(new co(105, pc));
                member.a(new dc(539, pc));
                member.a(new az(pc));
            }
            member.a(new cm(178));
        }
    }

    public boolean d(u pc) {
        return this.b.contains(pc);
    }

    public u a() {
        return this.c;
    }

    public boolean e(u pc) {
        return pc.fr() == this.c.fr();
    }

    public void f(u pc) {
        for (u member : this.b) {
            member.a(new az(pc));
        }
    }

    public void g(u pc) {
        this.c = pc;
        for (u member : this.c()) {
            member.a(new co(106, pc));
        }
    }

    public int b() {
        return this.b.size();
    }

    public CopyOnWriteArrayList<u> c() {
        return this.b;
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                if (ac.this.b.isEmpty()) {
                    return;
                }
                for (u pc : ac.this.b) {
                    pc.a(new co(110, pc));
                }
                this.a(3000);
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }

        private void a(int delay) {
            e.a().b(this, delay);
        }
    }
}

