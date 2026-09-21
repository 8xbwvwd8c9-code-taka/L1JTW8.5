/*
 * Decompiled with CFR 0.152.
 */
package bc;

import ap.u;
import aq.r;
import be.ee;
import bi.d;
import bi.h;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class c
extends TimerTask {
    private static final Logger a = Logger.getLogger(c.class.getName());
    private final u b;
    private int c = 0;
    private int d = 4;
    private int e = 0;

    public c(u pc) {
        this.b = pc;
    }

    public void a(int state) {
        if (this.d < state) {
            return;
        }
        this.d = state;
    }

    @Override
    public void run() {
        try {
            if (this.b.eX()) {
                return;
            }
            this.c += this.d;
            this.d = 4;
            if (this.c >= 64) {
                this.c = 0;
                this.b();
            }
            if (this.b.cG() > 0 && this.e++ >= 64) {
                this.e = 0;
                this.a();
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    private void a() {
        int newMp = this.b.eb() + this.b.cG();
        if (newMp < 0) {
            newMp = 0;
        }
        this.b.a(new ee(this.b.fr(), 6321));
        this.b.b(new ee(this.b.fr(), 6321));
        this.b.i_(newMp);
    }

    private void b() {
        int newMp;
        int baseMpr = bi.d.m(this.b.bk(), this.b.eE());
        int bonus = 0;
        if (this.b.bB(1002)) {
            int val = this.b.eE() - 10;
            baseMpr += val <= 0 ? 1 : val;
            if (this.b.bB(32)) {
                int continueTime = 640 - this.b.bC(32);
                int n2 = Math.min(continueTime / 16, 8);
                baseMpr += 2 * n2;
            }
        }
        if (r.a(this.b.fs(), this.b.ft(), this.b.fp())) {
            bonus += 3;
        }
        if (this.b.fp() >= 16384 && this.b.fp() <= 25599) {
            bonus += 3;
        }
        if (this.b.fu().e(new h(33055, 32336)) && this.b.fp() == 4 && this.b.A()) {
            bonus += 3;
        }
        baseMpr += this.b.j().m() + this.b.as();
        if (this.b.fj() < 3 || this.a(this.b)) {
            baseMpr = this.b.bk() >= 45 ? (baseMpr /= 2) : 0;
        }
        if ((newMp = this.b.eb() + baseMpr + bonus) < 0) {
            newMp = 0;
        }
        this.b.i_(newMp);
    }

    private boolean a(u pc) {
        if (pc.bB(169) || pc.bB(176)) {
            return false;
        }
        return pc.j().h() > 49;
    }
}

