/*
 * Decompiled with CFR 0.152.
 */
package aq;

import aq.e;
import aq.r;
import ax.b;
import ax.d;
import bi.h;
import bi.i;

public class u
extends h {
    private b c = new b();

    public u() {
    }

    private u(u loc) {
        this(loc.a, loc.b, loc.c);
    }

    public u(int x2, int y2, int mapId) {
        super(x2, y2);
        this.a(mapId);
    }

    private u(int x2, int y2, b map) {
        super(x2, y2);
        this.c = map;
    }

    public u(h pt, int mapId) {
        super(pt);
        this.a(mapId);
    }

    public u(h pt, b map) {
        super(pt);
        this.c = map;
    }

    public void a(u loc) {
        this.c = loc.c;
        this.a = loc.a;
        this.b = loc.b;
    }

    public void a(int x2, int y2, int mapId) {
        this.a(x2, y2);
        this.a(mapId);
    }

    public b a() {
        return this.c;
    }

    public int b() {
        return this.c.b();
    }

    public void a(b map) {
        this.c = map;
    }

    public void a(int mapId) {
        this.c = d.b().a(mapId);
    }

    public boolean c() {
        return this.c.d(this.a, this.b);
    }

    public boolean d() {
        return this.c.e(this.a, this.b);
    }

    public boolean e() {
        return this.c.f(this.a, this.b);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof u)) {
            return false;
        }
        u loc = (u)obj;
        return this.a() == loc.a() && this.f() == loc.f() && this.g() == loc.g();
    }

    @Override
    public int hashCode() {
        return 7 * this.c.b() + super.hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%d, %d) on %d", this.a, this.b, this.c.b());
    }

    public u a(int max, boolean isRandomTeleport) {
        return this.a(0, max, isRandomTeleport);
    }

    private u a(int min, int max, boolean isRandomTeleport) {
        return u.a(this, min, max, isRandomTeleport);
    }

    public static u a(u baseLocation, int min, int max, boolean isRandomTeleport) {
        if (min > max) {
            throw new IllegalArgumentException("min > max\u3068\u306a\u308b\u5f15\u6570\u306f\u7121\u52b9");
        }
        if (max <= 0) {
            return new u(baseLocation);
        }
        if (min < 0) {
            min = 0;
        }
        u newLocation = new u();
        int newX = 0;
        int newY = 0;
        int locX = baseLocation.f();
        int locY = baseLocation.g();
        short mapId = (short)baseLocation.b();
        b map = baseLocation.a();
        newLocation.a(map);
        int locX1 = locX - max;
        int locX2 = locX + max;
        int locY1 = locY - max;
        int locY2 = locY + max;
        int mapX1 = map.c();
        int mapX2 = mapX1 + map.e();
        int mapY1 = map.d();
        int mapY2 = mapY1 + map.f();
        if (locX1 < mapX1) {
            locX1 = mapX1;
        }
        if (locX2 > mapX2) {
            locX2 = mapX2;
        }
        if (locY1 < mapY1) {
            locY1 = mapY1;
        }
        if (locY2 > mapY2) {
            locY2 = mapY2;
        }
        int diffX = locX2 - locX1;
        int diffY = locY2 - locY1;
        int trial = 0;
        int amax = (int)Math.pow(1 + max * 2, 2.0);
        int amin = min == 0 ? 0 : (int)Math.pow(1 + (min - 1) * 2, 2.0);
        int trialLimit = 40 * amax / (amax - amin);
        do {
            if (trial >= trialLimit) {
                newLocation.a(locX, locY);
                break;
            }
            ++trial;
            newX = locX1 + i.a(diffX + 1);
            newY = locY1 + i.a(diffY + 1);
            newLocation.a(newX, newY);
        } while (baseLocation.c(newLocation) < min || isRandomTeleport && (e.b(newX, newY, mapId) || r.a(newX, newY, mapId)) || !map.b(newX, newY) || !map.c(newX, newY));
        return newLocation;
    }
}

