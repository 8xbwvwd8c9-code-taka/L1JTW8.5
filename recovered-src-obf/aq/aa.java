/*
 * Decompiled with CFR 0.152.
 */
package aq;

import aq.u;
import ax.b;
import ax.d;

public class aa {
    private final u a = new u();
    private int b = 0;

    public int fp() {
        return this.a.a().b();
    }

    public void cE(int mapId) {
        this.a.a(d.b().a(mapId));
    }

    public b fq() {
        return this.a.a();
    }

    public void a(b map) {
        if (map == null) {
            throw new NullPointerException();
        }
        this.a.a(map);
    }

    public int fr() {
        return this.b;
    }

    public void cF(int id) {
        this.b = id;
    }

    public int fs() {
        return this.a.f();
    }

    public void cG(int x2) {
        this.a.b(x2);
    }

    public int ft() {
        return this.a.g();
    }

    public void cH(int y2) {
        this.a.c(y2);
    }

    public u fu() {
        return this.a;
    }

    public void a(u loc) {
        this.a.b(loc.f());
        this.a.c(loc.g());
        this.a.a(loc.b());
    }

    public void d(int x2, int y2, int mapid) {
        this.a.b(x2);
        this.a.c(y2);
        this.a.a(mapid);
    }

    public double e(aa obj) {
        return this.fu().b(obj.fu());
    }

    public int f(aa obj) {
        return this.fu().c(obj.fu());
    }

    public int g(aa obj) {
        return this.fu().d(obj.fu());
    }

    public void b(ap.u perceivedFrom) {
    }

    public void c(ap.u actionFrom) {
    }

    public void a(ap.u talkFrom) {
    }

    public void a(ap.u attacker, int skillId) {
    }
}

