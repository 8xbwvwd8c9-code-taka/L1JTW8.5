/*
 * Decompiled with CFR 0.152.
 */
package at;

import at.d;
import bi.e;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class c {
    private static final Logger a = Logger.getLogger(c.class.getName());
    private static c b;
    private volatile at.a c = at.a.a();
    private at.a d = null;
    private final CopyOnWriteArrayList<d> e = new CopyOnWriteArrayList();

    public static c a() {
        if (b == null) {
            b = new c();
        }
        return b;
    }

    private c() {
        bi.e.a().a(new a());
    }

    private boolean a(int field) {
        return this.d.a(field) != this.c.a(field);
    }

    private void d() {
        if (this.a(2)) {
            for (d listener : this.e) {
                listener.b(this.c);
            }
        }
        if (this.a(5)) {
            for (d listener : this.e) {
                listener.a(this.c);
            }
        }
        if (this.a(11)) {
            for (d listener : this.e) {
                listener.c(this.c);
            }
        }
        if (this.a(12)) {
            for (d listener : this.e) {
                listener.d(this.c);
            }
        }
    }

    public at.a b() {
        return this.c;
    }

    public void a(d listener) {
        this.e.add(listener);
    }

    private class a
    implements Runnable {
        private a() {
        }

        @Override
        public void run() {
            while (true) {
                c.this.d = c.this.c;
                c.this.c = at.a.a();
                c.this.d();
                try {
                    Thread.sleep(500L);
                    continue;
                }
                catch (InterruptedException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    continue;
                }
                break;
            }
        }
    }
}

