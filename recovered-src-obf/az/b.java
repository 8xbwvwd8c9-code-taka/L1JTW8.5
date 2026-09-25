/*
 * Decompiled with CFR 0.152.
 */
package az;

import ap.u;
import aq.f;
import az.c;
import be.cn;
import bi.e;
import java.util.concurrent.ScheduledFuture;

public class b
extends c {
    private final f a;
    private final int b;
    private final int c;
    private ScheduledFuture<?> d;

    private b(f cha, int delay, int time) {
        this.a = cha;
        this.b = delay;
        this.c = time;
        this.c();
    }

    public static boolean a(f cha, int delay, int time) {
        if (!az.c.a(cha)) {
            return false;
        }
        cha.a(new b(cha, delay, time));
        return true;
    }

    private void c() {
        az.b.a(this.a, 212);
        this.a.y(1);
        if (this.a instanceof u) {
            this.a.j(1008, 0);
            this.d = e.a().a(new a(), this.b);
        }
    }

    @Override
    public void b() {
        if (this.d != null) {
            this.d.cancel(true);
            this.d = null;
        }
        this.a.bA(1009);
        if (this.a.eX()) {
            return;
        }
        if (this.a instanceof u) {
            u pc = (u)this.a;
            pc.a(new cn(1, false));
        }
        this.a.y(0);
        this.a.a((c)null);
    }

    @Override
    public int a() {
        return 1;
    }

    private class a
    implements Runnable {
        private a() {
        }

        @Override
        public void run() {
            b.this.a.y(2);
            b.this.a.bA(1008);
            if (b.this.a.eX()) {
                return;
            }
            b.this.a.j(1009, 0);
            if (b.this.a instanceof u) {
                u pc = (u)b.this.a;
                pc.a(new cn(1, true));
                b.this.d = e.a().a(new b(), b.this.c);
            }
        }
    }

    private class b
    implements Runnable {
        private b() {
        }

        @Override
        public void run() {
            b.this.b();
        }
    }
}

