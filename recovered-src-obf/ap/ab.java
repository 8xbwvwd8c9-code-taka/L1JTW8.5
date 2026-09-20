/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ap.u;
import aq.aa;
import ax.b;
import be.dh;
import be.eo;
import bi.h;
import bi.i;
import java.util.concurrent.CopyOnWriteArrayList;

public class ab
extends aa {
    private final bd.i a;
    private final h b = new h();
    private final h c = new h();
    private final int d;
    private boolean e = true;
    private final String f;
    private final CopyOnWriteArrayList<u> g = new CopyOnWriteArrayList();

    public ab(int id, bd.i trap, aq.u loc, h rndPt, int span) {
        this.cF(id);
        this.a = trap;
        this.fu().a(loc);
        this.b.a(loc);
        this.c.a(rndPt);
        this.d = span;
        this.f = "trap";
        this.a();
    }

    public ab(int id, aq.u loc) {
        this.cF(id);
        this.a = bd.i.c();
        this.fu().a(loc);
        this.d = 0;
        this.f = "trap base";
    }

    public void a() {
        if (this.c.f() == 0 && this.c.g() == 0) {
            return;
        }
        int i2 = 0;
        while (i2 < 50) {
            int rndX = i.a(this.c.f() + 1) * (i.a(2) == 1 ? 1 : -1);
            int rndY = i.a(this.c.g() + 1) * (i.a(2) == 1 ? 1 : -1);
            b map = this.fu().a();
            if (map.b(rndX += this.b.f(), rndY += this.b.g()) && map.c(rndX, rndY)) {
                this.fu().a(rndX, rndY);
                break;
            }
            ++i2;
        }
    }

    public void b() {
        this.e = true;
    }

    public void c() {
        this.e = false;
        for (u pc : this.g) {
            pc.d(this);
            pc.a(new dh(this));
        }
        this.g.clear();
    }

    public boolean d() {
        return this.e;
    }

    public int e() {
        return this.d;
    }

    public void d(u trodFrom) {
        this.a.a(trodFrom, this);
    }

    public void f() {
        this.a.b(this);
    }

    @Override
    public void b(u perceivedFrom) {
        if (perceivedFrom.bB(26002)) {
            perceivedFrom.c(this);
            perceivedFrom.a(new eo(this, this.f));
            this.g.add(perceivedFrom);
        } else if (this.a.b() == 12876) {
            perceivedFrom.c(this);
            perceivedFrom.a(new eo(this, ""));
            this.g.add(perceivedFrom);
        }
    }

    public boolean g() {
        return this.a.b() == 12876;
    }

    public int h() {
        return this.a.b();
    }
}

