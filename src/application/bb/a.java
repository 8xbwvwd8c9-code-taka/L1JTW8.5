/*
 * Decompiled with CFR 0.152.
 */
package bb;

import ap.s;
import ap.u;
import aq.f;
import be.ak;
import be.cn;
import bi.e;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

public class a
extends TimerTask {
    private static final Logger a = Logger.getLogger(a.class.getName());
    private ScheduledFuture<?> b = null;
    private int c = 0;
    private final f d;
    private final f e;
    private final int f;

    public a(f effect, f cha, int skillId) {
        this.d = effect;
        this.e = cha;
        this.f = skillId;
    }

    @Override
    public void run() {
        try {
            if (this.e.eX()) {
                this.b();
                return;
            }
            if (!this.e.bB(this.f)) {
                this.b();
                return;
            }
            ++this.c;
            this.c();
        }
        catch (Throwable e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public void a() {
        this.b = bi.e.a().a(this, 900L, 1000L);
    }

    private void b() {
        if (this.b != null) {
            this.b.cancel(true);
        }
    }

    private void c() {
        if (this.f == 1019) {
            if (this.c % 4 != 0) {
                return;
            }
            if (this.e.bB(1028)) {
                return;
            }
            if (this.e.bB(78)) {
                return;
            }
            if (this.e.bB(50)) {
                return;
            }
            if (this.e.bB(157)) {
                return;
            }
            if (this.e instanceof u) {
                u pc = (u)this.e;
                pc.a(new ak(pc.fr(), 2));
                pc.b(new ak(pc.fr(), 2));
                pc.a(this.d, 10.0, false);
            } else if (this.e instanceof s) {
                s mob = (s)this.e;
                mob.b(new ak(mob.fr(), 2));
                mob.b(this.d, 10);
            }
        } else if (this.f == 1021) {
            if (this.c % 4 != 0) {
                return;
            }
            if (this.e.bB(1028)) {
                return;
            }
            if (this.e.bB(78)) {
                return;
            }
            if (this.e.bB(50)) {
                return;
            }
            if (this.e.bB(157)) {
                return;
            }
            if (this.e instanceof u) {
                u pc = (u)this.e;
                pc.j(1028, 1000);
                pc.a(new cn(6, true));
            } else if (this.e instanceof s) {
                s mob = (s)this.e;
                mob.j(1028, 1000);
                mob.V(true);
            }
        } else if (this.f == 1023) {
            this.e.j(1024, 4000);
        } else if (this.f == 1025) {
            if (this.c % 4 == 0) {
                int newMp = this.e.eb() + 5;
                if (newMp < 0) {
                    newMp = 0;
                }
                this.e.i_(newMp);
            }
            if (this.c % 5 == 0) {
                if (this.e instanceof u) {
                    u pc = (u)this.e;
                    pc.a(this.d, 25.0, false);
                } else if (this.e instanceof s) {
                    s mob = (s)this.e;
                    mob.b(this.d, 25);
                }
            }
        }
    }
}

