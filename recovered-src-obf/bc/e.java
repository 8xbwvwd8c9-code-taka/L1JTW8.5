/*
 * Decompiled with CFR 0.152.
 */
package bc;

import ap.t;
import ap.u;
import aq.f;
import be.ak;
import be.aq;
import bi.i;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class e
extends TimerTask {
    private static final Logger a = Logger.getLogger(e.class.getName());
    private ScheduledFuture<?> b = null;
    private int c = 0;
    private final int d;
    private final u e;
    private final f f;

    public e(u pc, f cha, int gfxid) {
        this.f = cha;
        this.e = pc;
        this.d = gfxid;
    }

    @Override
    public void run() {
        try {
            if (this.f == null || this.f.eX()) {
                this.b();
                return;
            }
            this.c();
            ++this.c;
            if (this.c >= 3) {
                this.b();
                return;
            }
        }
        catch (Throwable e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public void a() {
        this.b = bi.e.a().a(this, 0L, 1000L);
    }

    private void b() {
        if (this.b != null) {
            this.b.cancel(true);
        }
    }

    private void c() {
        double damage = this.a(this.e, this.f);
        if (this.f.ea() - (int)damage <= 0 && this.f.ea() != 1) {
            damage = this.f.ea() - 1;
        } else if (this.f.ea() == 1) {
            damage = 0.0;
        }
        if (damage >= 400.0) {
            damage = 400.0;
        }
        this.e.a(new aq(this.f.fs(), this.f.ft(), this.d));
        this.e.b(new aq(this.f.fs(), this.f.ft(), this.d));
        if (this.f instanceof u) {
            u pc = (u)this.f;
            pc.a(new ak(pc.fr(), 2));
            pc.b(new ak(pc.fr(), 2));
            pc.a((f)this.e, damage, false);
        } else if (this.f instanceof t) {
            t npc = (t)this.f;
            npc.b(new ak(npc.fr(), 2));
            npc.b(this.e, (int)damage);
        }
    }

    private double a(u pc, f cha) {
        double dmg = 0.0;
        int spByItem = pc.eV() - pc.eW();
        byte intel = pc.eD();
        double charaIntelligence = (double)(pc.eD() - 15) + (double)spByItem * 0.46;
        double coefficientA = 1.0 + 3.0 * charaIntelligence / 32.0;
        if (coefficientA < 1.0) {
            coefficientA = 1.0;
        }
        double coefficientB = 0.0;
        coefficientB = intel > 18 ? ((double)intel + 2.0) / (double)intel : (intel <= 12 ? 0.78 : (double)intel * 0.065);
        double coefficientC = 0.0;
        coefficientC = intel <= 12 ? 12.0 : (double)intel;
        dmg = (double)(i.a(6) + 1 + 7) * coefficientA * coefficientB / 10.5 * coefficientC * 2.0;
        if (cha.bB(68)) {
            dmg /= 2.0;
        }
        return dmg;
    }
}

