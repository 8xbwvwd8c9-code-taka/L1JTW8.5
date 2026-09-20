/*
 * Decompiled with CFR 0.152.
 */
package bg;

import bg.c;
import bg.d;
import bi.e;
import java.sql.Timestamp;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

class f
implements d,
Runnable {
    private static final Logger a = Logger.getLogger(f.class.getName());
    private ScheduledFuture<?> b = null;
    private final aq.f c;
    private final int d;
    private final int e;
    private int f;
    private boolean g = false;
    private Timestamp h = null;

    public f(aq.f cha, int skillId, int timeMillis, Timestamp limitTime) {
        this.c = cha;
        this.e = skillId;
        this.d = timeMillis;
        this.f = this.d / 1000;
        this.h = limitTime;
    }

    @Override
    public void run() {
        if (this.g) {
            return;
        }
        --this.f;
        if (this.f <= 0) {
            this.c.bz(this.e);
        }
    }

    @Override
    public void a(boolean b2) {
        this.g = b2;
    }

    @Override
    public void c() {
        this.b = bi.e.a().a(this, 1000L, 1000L);
    }

    @Override
    public void d() {
        this.e();
        try {
            bg.c.a(this.c, this.e);
        }
        catch (Throwable e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    @Override
    public void e() {
        if (this.b != null) {
            this.b.cancel(true);
        }
    }

    @Override
    public int a() {
        return this.f;
    }

    @Override
    public Timestamp b() {
        return this.h;
    }
}

