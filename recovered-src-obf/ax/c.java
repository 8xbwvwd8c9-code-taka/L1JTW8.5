/*
 * Decompiled with CFR 0.152.
 */
package ax;

import aq.u;
import ax.b;
import ax.d;

public class c {
    private b a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    public b a() {
        return this.a;
    }

    public void a(b map) {
        this.a = map;
    }

    public int b() {
        return this.a.b();
    }

    public c(int left, int top, int right, int bottom, int mapId) {
        this.b = left;
        this.c = top;
        this.d = right;
        this.e = bottom;
        this.a = ax.d.b().a((short)mapId);
    }

    public boolean a(u loc) {
        return this.a.b() == loc.a().b() && this.a(loc.f(), loc.g());
    }

    private boolean a(int x2, int y2) {
        return this.b <= x2 && x2 <= this.d && this.c <= y2 && y2 <= this.e;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.c;
    }

    public int e() {
        return this.d;
    }

    public int f() {
        return this.e;
    }

    public int g() {
        return this.d - this.b;
    }

    public int h() {
        return this.e - this.c;
    }
}

