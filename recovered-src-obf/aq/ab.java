/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.s;
import ap.u;
import aq.f;
import be.cm;
import be.cn;
import be.ds;
import bi.e;
import java.util.concurrent.ScheduledFuture;

public class ab {
    private final f a;
    private final int b;
    private final int c;
    private ScheduledFuture<?> d;

    private ab(f cha, int delay, int time) {
        this.a = cha;
        this.b = delay;
        this.c = time;
        this.c();
    }

    private void c() {
        if (this.a instanceof u) {
            u player = (u)this.a;
            player.a(new ds(212));
        }
        this.a.y(2);
        this.a.j(1010, 0);
        this.d = e.a().a(new a(), this.b);
    }

    public void a() {
        if (this.d != null) {
            this.d.cancel(true);
            this.d = null;
        }
        this.a.bA(1010);
        this.a.bA(1011);
        this.a.V(false);
        this.a.y(0);
        this.a.a((ab)null);
        if (this.a instanceof u) {
            u pc = (u)this.a;
            pc.a(new cm(161, 2, 0));
            pc.a(new cn(1, false));
        }
    }

    public static boolean a(f cha, int delay, int time) {
        if (!(cha instanceof u) && !(cha instanceof s)) {
            return false;
        }
        if (cha.bB(1010) || cha.bB(1011)) {
            return false;
        }
        cha.a(new ab(cha, delay, time));
        return true;
    }

    public int b() {
        return 2;
    }

    private class a
    implements Runnable {
        private a() {
        }

        @Override
        public void run() {
            u player;
            if (ab.this.a instanceof u && !(player = (u)ab.this.a).eX()) {
                player.a(new cn(1, true));
                player.a(new cm(161, 2, ab.this.c / 1000));
            }
            ab.this.a.V(true);
            ab.this.a.bA(1010);
            ab.this.a.j(1011, 0);
            ab.this.d = e.a().a(new b(), ab.this.c);
        }
    }

    private class b
    implements Runnable {
        private b() {
        }

        @Override
        public void run() {
            ab.this.a();
        }
    }
}

