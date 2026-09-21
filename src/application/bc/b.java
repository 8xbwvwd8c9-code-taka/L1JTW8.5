/*
 * Decompiled with CFR 0.152.
 */
package bc;

import ap.h;
import ap.u;
import aq.aa;
import aq.f;
import aq.r;
import be.ee;
import bi.d;
import bi.i;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class b
extends TimerTask {
    private static final Logger a = Logger.getLogger(b.class.getName());
    private final u b;
    private int c = 0;
    private int d = 0;
    private int e = 4;
    private int f = 0;

    public b(u pc) {
        this.b = pc;
        this.a();
    }

    public void a(int state) {
        if (this.e < state) {
            return;
        }
        this.e = state;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        try {
            if (this.b.eX()) {
                return;
            }
            this.d += this.e;
            this.e = 4;
            b b2 = this;
            synchronized (b2) {
                if (this.c <= this.d) {
                    this.d = 0;
                    this.c();
                }
            }
            if (this.b.cF() > 0 && this.f++ >= 64) {
                this.f = 0;
                this.b();
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    private void b() {
        int newHp = this.b.ea() + this.b.cF();
        if (newHp < 0) {
            newHp = 0;
        }
        this.b.a(new ee(this.b.fr(), 744));
        this.b.b(new ee(this.b.fr(), 744));
        this.b.a(newHp);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void a() {
        int[] lvlTable = new int[]{30, 25, 20, 16, 14, 12, 11, 10, 9, 3, 2};
        int regenLvl = Math.min(10, this.b.ev());
        if (30 <= this.b.ev() && this.b.z()) {
            regenLvl = 11;
        }
        b b2 = this;
        synchronized (b2) {
            this.c = lvlTable[regenLvl - 1] * 4;
        }
    }

    private void c() {
        int newHp;
        if (this.b.eX()) {
            return;
        }
        int calcHpr = bi.d.k(this.b.bg(), this.b.eA());
        int value = i.a(calcHpr) + 1;
        int bonus = 0;
        if (this.b.bB(158)) {
            value += 15;
        }
        if (r.a(this.b.fs(), this.b.ft(), this.b.fp())) {
            bonus += 5;
        }
        if (this.b.fp() >= 16384 && this.b.fp() <= 25599) {
            bonus += 5;
        }
        if (this.b.fu().e(new bi.h(33055, 32336)) && this.b.fp() == 4 && this.b.A()) {
            bonus += 5;
        }
        boolean inLifeStream = false;
        if (bc.b.c(this.b)) {
            inLifeStream = true;
            bonus += 3;
        }
        value += this.b.j().l() + this.b.ar();
        if (this.b.bB(55)) {
            value = 0;
        }
        if (this.b.bB(5017)) {
            value = -100;
        }
        if (this.b.fj() < 3 || this.b(this.b)) {
            value = this.b.bg() >= 45 ? (value /= 2) : 0;
        }
        if ((newHp = this.b.ea() + value + bonus) < 1) {
            newHp = 1;
        }
        if (this.a(this.b) && (newHp -= 20) < 1) {
            if (this.b.l()) {
                newHp = 1;
            } else {
                this.b.b((f)null);
            }
        }
        if (this.b.fp() == 410 && !inLifeStream && (newHp -= 10) < 1) {
            if (this.b.l()) {
                newHp = 1;
            } else {
                this.b.b((f)null);
            }
        }
        if (!this.b.eX()) {
            this.b.a(Math.min(newHp, this.b.ew()));
        }
    }

    private boolean a(u pc) {
        if (pc.j().h(20207)) {
            return false;
        }
        if (pc.bB(1003) || pc.cH()) {
            return false;
        }
        if (pc.j().h(21048) && pc.j().h(21049) && pc.j().h(21050)) {
            return false;
        }
        return pc.fq().g();
    }

    private boolean b(u pc) {
        if (pc.bB(169) || pc.bB(176)) {
            return false;
        }
        if (pc.j().h(20049)) {
            return false;
        }
        return pc.j().h() > 49;
    }

    private static boolean c(u pc) {
        for (aa object : pc.eq()) {
            h effect;
            if (!(object instanceof h) || (effect = (h)object).z() != 81169 || effect.fu().c(pc.fu()) >= 4) continue;
            return true;
        }
        return false;
    }
}

