/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ai.d;
import ao.ah;
import ao.au;
import ap.t;
import ap.u;
import ap.w;
import aq.aa;
import aq.aq;
import aq.f;
import aq.z;
import be.ax;
import bh.l;
import bi.g;
import java.util.logging.Level;
import java.util.logging.Logger;

public class k
extends t {
    private static final Logger y = Logger.getLogger(k.class.getName());

    @Override
    public boolean a() {
        for (aa object : aq.a().e(this)) {
            u pc;
            if (!(object instanceof t)) continue;
            t npc = (t)object;
            if (npc.U_().b() == 70740 && this.U_().b() == 71093) {
                this.V(true);
                pc = (u)this.k;
                if (!pc.j().f(40593)) {
                    ah.a(pc, 40593, 1);
                }
                this.aa_();
                return true;
            }
            if (npc.U_().b() == 70811 && this.U_().b() == 71094) {
                this.V(true);
                pc = (u)this.k;
                if (!pc.j().f(40582) && !pc.j().f(40583)) {
                    ah.a(pc, 40582, 1);
                }
                this.aa_();
                return true;
            }
            if (npc.U_().b() == 70964 && this.U_().b() == 70957) {
                if (this.fu().c(this.k.fu()) >= 3 || (pc = (u)this.k).fs() < 32917 || pc.fs() > 32921 || pc.ft() < 32974 || pc.ft() > 32978 || pc.fp() != 410) continue;
                this.V(true);
                ah.a(pc, 41003, 1);
                pc.bb().a(38, 0);
                this.aa_();
                return true;
            }
            if (npc.U_().b() != 71114 || this.U_().b() != 81350 || this.fu().c(this.k.fu()) >= 15 || (pc = (u)this.k).fs() < 32542 || pc.fs() > 32585 || pc.ft() < 32656 || pc.ft() > 32698 || pc.fp() != 400) continue;
            this.V(true);
            ah.a(pc, 49163, 1);
            pc.bb().a(4, 4);
            this.aa_();
            return true;
        }
        if (this.k.eX() || this.fu().c(this.k.fu()) > 10) {
            this.V(true);
            this.a(this.U_().b(), this.fs(), this.ft(), this.fb(), this.fp());
            this.aa_();
            return true;
        }
        if (this.k != null && this.k.fp() == this.fp() && this.fu().c(this.k.fu()) > 2) {
            this.g(this.a(this.k.fs(), this.k.ft()));
            this.v(this.f(this.N(), 0));
        }
        return false;
    }

    public k(l template, t target, f _master) {
        super(template);
        this.cF(ai.d.a().c());
        this.e(_master);
        this.cG(target.fs());
        this.cH(target.ft());
        this.cE(target.fp());
        this.ct(target.fb());
        this.s(target.aa());
        target.V(true);
        target.X(true);
        target.aa_();
        aq.a().a(this);
        aq.a().c(this);
        for (u pc : aq.a().f(this)) {
            this.b(pc);
        }
        this.q();
        this.k.a(this);
    }

    @Override
    public synchronized void aa_() {
        this.k.em().remove(this.fr());
        super.aa_();
    }

    @Override
    public void a(u pc) {
        if (this.eX()) {
            return;
        }
        z.a(this, pc);
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new ax(this));
    }

    public void a(int npcId, int X, int Y, int H, int Map2) {
        l l1npc = au.a().a(npcId);
        if (l1npc != null) {
            t mob = null;
            try {
                mob = bi.g.a(l1npc);
                mob.cF(ai.d.a().c());
                mob.cG(X);
                mob.cH(Y);
                mob.q(X);
                mob.r(Y);
                mob.cE(Map2);
                mob.ct(H);
                aq.a().a(mob);
                aq.a().c(mob);
                aa object = aq.a().a(mob.fr());
                w newnpc = (w)object;
                newnpc.Z_();
                newnpc.fg();
                newnpc.a_(0);
            }
            catch (Exception e2) {
                y.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

