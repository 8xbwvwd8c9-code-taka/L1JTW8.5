/*
 * Decompiled with CFR 0.152.
 */
package az;

import ap.s;
import ap.u;
import aq.f;
import az.c;
import be.cm;
import bi.e;

public class a
extends c {
    private Thread a;
    private final f b;
    private final f c;
    private final int d;
    private final int e;

    private a(f attacker, f cha, int damageSpan, int damage, int maxTime) {
        this.b = attacker;
        this.c = cha;
        this.d = damageSpan;
        this.e = damage;
        this.a(maxTime);
    }

    private boolean b(f cha) {
        return cha instanceof u || cha instanceof s;
    }

    private void a(int maxTime) {
        this.c.j(11, maxTime * 1000);
        this.c.y(1);
        if (this.c instanceof u) {
            u pc = (u)this.c;
            pc.a(new cm(161, 1, maxTime));
        }
        if (this.b(this.c)) {
            this.a = new a();
            bi.e.a().a(this.a);
        }
    }

    public static boolean a(f attacker, f cha, int damageSpan, int damage, int maxTime) {
        if (!az.a.a(cha)) {
            return false;
        }
        cha.a(new a(attacker, cha, damageSpan, damage, maxTime));
        return true;
    }

    @Override
    public int a() {
        return 1;
    }

    @Override
    public void b() {
        if (this.a != null) {
            this.a.interrupt();
        }
        this.c.y(0);
        this.c.bA(11);
        this.c.a((c)null);
        if (this.c instanceof u) {
            u pc = (u)this.c;
            pc.a(new cm(161, 1, 0));
        }
    }

    private class a
    extends Thread {
        private a() {
        }

        @Override
        public void run() {
            block4: {
                while (true) {
                    try {
                        Thread.sleep(a.this.d);
                    }
                    catch (InterruptedException e2) {
                        break block4;
                    }
                    if (!a.this.c.bB(11)) break block4;
                    if (a.this.c instanceof u) {
                        u player = (u)a.this.c;
                        player.a(a.this.b, (double)a.this.e, false);
                        if (!player.eX()) continue;
                        break block4;
                    }
                    if (!(a.this.c instanceof s)) continue;
                    s mob = (s)a.this.c;
                    mob.b(a.this.b, a.this.e);
                    if (mob.eX()) break;
                }
                return;
            }
            a.this.b();
        }
    }
}

