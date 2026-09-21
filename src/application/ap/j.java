/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ap.t;
import ap.u;
import aq.aa;
import be.ak;
import be.cc;
import be.p;
import bh.l;
import bi.e;
import bi.i;
import java.util.TimerTask;
import java.util.concurrent.ScheduledFuture;

public class j
extends t {
    private final ScheduledFuture<?> y = bi.e.a().a(new a(), 1000L, (30 + bi.i.a(30)) * 1000);

    public j(l template) {
        super(template);
    }

    @Override
    public void b(u perceivedFrom) {
        perceivedFrom.c((aa)this);
        perceivedFrom.a(new cc(this));
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            if (j.this.ah()) {
                j.this.y.cancel(true);
            }
            j.this.ct(bi.i.a(8));
            j.this.b(new p(j.this));
            j.this.b(new ak(j.this.fr(), 0));
        }
    }
}

