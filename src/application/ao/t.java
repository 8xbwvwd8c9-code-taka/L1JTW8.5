/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ao.au;
import ap.f;
import aq.aq;
import aq.u;
import bh.g;
import bh.l;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class t {
    private static t a;
    private final ConcurrentHashMap<u, f> b = new ConcurrentHashMap();
    private final ConcurrentHashMap<u, f> c = new ConcurrentHashMap();

    public static void a() {
        a = new t();
    }

    public static t b() {
        return a;
    }

    private t() {
        this.d();
    }

    private void d() {
        for (g spawn : g.i()) {
            u loc = new u(spawn.c(), spawn.d(), spawn.e());
            if (this.b.containsKey(loc)) {
                System.out.println(String.format("Duplicate door location: id = %d", spawn.a()));
                continue;
            }
            this.a(spawn.a(), spawn.b(), loc, spawn.f(), spawn.g(), spawn.h());
        }
    }

    private void a(f door) {
        for (u key : this.c(door)) {
            this.c.put(key, door);
        }
    }

    private void b(f door) {
        for (u key : this.c(door)) {
            this.c.remove(key);
        }
    }

    private List<u> c(f door) {
        ArrayList<u> keys = new ArrayList<u>();
        int left = door.ac_();
        int right = door.n();
        if (door.j() == 0) {
            int x2 = left;
            while (x2 <= right) {
                keys.add(new u(x2, door.ft(), door.fp()));
                ++x2;
            }
        } else {
            int y2 = left;
            while (y2 <= right) {
                keys.add(new u(door.fs(), y2, door.fp()));
                ++y2;
            }
        }
        return keys;
    }

    public f a(int doorId, bh.f gfx, u loc, int hp, int keeper, boolean isOpening) {
        if (this.b.containsKey(loc)) {
            return null;
        }
        l template = au.a().a(189997);
        template.k(gfx.a());
        f door = new f(template, doorId, gfx, loc, hp, keeper, isOpening);
        door.cF(d.a().c());
        aq.a().a(door);
        aq.a().c(door);
        this.b.put(door.fu(), door);
        this.a(door);
        return door;
    }

    public void a(u loc) {
        f door = this.b.remove(loc);
        if (door != null) {
            this.b(door);
            door.aa_();
        }
    }

    public f a(int x2, int y2, int mapid) {
        for (f door : this.b.values()) {
            if (door.fs() != x2 || door.ft() != y2 || door.fp() != mapid) continue;
            return door;
        }
        return null;
    }

    public f[] c() {
        return this.b.values().toArray(new f[this.b.size()]);
    }
}

