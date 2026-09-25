/*
 * Decompiled with CFR 0.152.
 */
package ba;

import ao.ah;
import ap.q;
import aq.aa;
import aq.aq;
import aq.u;
import ax.b;
import ax.d;
import bi.h;
import bi.i;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;

public class e
implements Runnable {
    private static final Logger a = Logger.getLogger(e.class.getName());
    private static final int b = 4;
    private static final int c = l1j.server.a.am;
    private static final int d = 3;
    private static final int e = 300;
    private static final int f = 32911;
    private static final int g = 32210;
    private static final int h = 33141;
    private static final int i = 32500;
    private static final int j = 40515;
    private final ArrayList<au.e> k = new ArrayList(c);
    private static e l = null;
    private final aa m = new aa();

    private e() {
    }

    public static e a() {
        if (l == null) {
            l = new e();
        }
        return l;
    }

    private boolean a(u loc) {
        this.m.a(loc.a());
        this.m.cG(loc.f());
        this.m.cH(loc.g());
        return aq.a().f(this.m).isEmpty();
    }

    private h b() {
        int newX = bi.i.a(230) + 32911;
        int newY = bi.i.a(290) + 32210;
        return new h(newX, newY);
    }

    private void c() {
        int i2 = 0;
        while (i2 < this.k.size()) {
            au.e gInventory = this.k.get(i2);
            if (!gInventory.f(40515)) {
                this.k.remove(i2);
                --i2;
            }
            ++i2;
        }
    }

    private void b(u loc) {
        au.e gInventory = aq.a().a(loc);
        q item = ah.a().b(40515);
        item.a(0);
        item.e(1);
        gInventory.d(item);
        this.k.add(gInventory);
    }

    @Override
    public void run() {
        try {
            b map = ax.d.b().a(4);
            while (true) {
                this.c();
                while (this.k.size() < c) {
                    u loc = new u(this.b(), map);
                    if (!this.a(loc)) continue;
                    this.b(loc);
                    Thread.sleep(3000L);
                }
                Thread.sleep(300000L);
            }
        }
        catch (Throwable e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return;
        }
    }
}

