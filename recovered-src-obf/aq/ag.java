/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ai.d;
import ao.ap;
import ao.au;
import ap.f;
import ap.s;
import ap.t;
import aq.aa;
import aq.am;
import aq.aq;
import aq.u;
import at.c;
import bh.l;
import bi.e;
import bi.g;
import bi.h;
import bi.i;
import java.util.HashMap;

public class ag {
    private final l b;
    private boolean c = false;
    private HashMap<Integer, h> d = null;
    public static final int a = 2;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private boolean u;
    private int v;
    private boolean w;
    private int x;
    private boolean y;

    public ag(l mobTemplate) {
        this.b = mobTemplate;
    }

    public void a() {
        this.c = true;
        if (this.f >= 2 && this.r <= 100 && this.t()) {
            this.d = new HashMap();
        }
        int spawnNum = 0;
        while (spawnNum < this.f) {
            this.s(++spawnNum);
        }
        this.c = false;
    }

    private boolean t() {
        return this.m != 0 && this.n != 0 && this.o != 0 && this.p != 0;
    }

    private boolean u() {
        return this.k != 0 || this.l != 0;
    }

    private void s(int spawnNumber) {
        if (this.y && !at.c.a().b().e()) {
            this.a(spawnNumber, 0);
            return;
        }
        this.b(spawnNumber, 0);
    }

    public void a(int spawnNumber, int objectId) {
        bi.e.a().a(new a(spawnNumber, objectId), this.v() * 1000);
    }

    private int v() {
        int respawnDelay = this.r;
        int delayInterval = this.s - this.r;
        if (delayInterval > 0) {
            respawnDelay += bi.i.a(delayInterval);
        }
        at.a currentTime = at.c.a().b();
        if (this.y && !currentTime.e()) {
            int currentHour = currentTime.a(11);
            int currentMinute = currentTime.a(12);
            respawnDelay = ((17 - currentHour) * 60 - currentMinute) * 60 / 6;
        }
        return Math.max(respawnDelay, 1);
    }

    protected void b(int spawnNumber, int objectId) {
        s mob;
        int npcId;
        t npc = bi.g.a(this.b);
        if (npc.z() == 97370) {
            l template1 = au.a().a(97370 + bi.i.a(8));
            npc = bi.g.a(template1);
        }
        if (objectId == 0) {
            npc.cF(ai.d.a().c());
        } else {
            npc.cF(objectId);
        }
        if (this.q >= 0 && this.q <= 7) {
            npc.ct(this.q);
        }
        if ((npcId = npc.z()) == 45488 && this.t == 809) {
            npc.cE(this.t + bi.i.a(2));
        } else if (npcId == 45601 && this.t == 811) {
            npc.cE(this.t + bi.i.a(3));
        } else if (npcId == 81322 && this.t == 25) {
            npc.cE(this.t + bi.i.a(2));
        } else {
            npc.cE(this.t);
        }
        npc.u(this.v);
        npc.l(this.w);
        int newlocx = this.i;
        int newlocy = this.j;
        int tryCount = 0;
        while (tryCount++ <= 50) {
            if (tryCount > 49) {
                newlocx = this.i;
                newlocy = this.j;
            } else if (this.t()) {
                if (this.d != null && this.d.containsKey(spawnNumber)) {
                    h pt = this.d.get(spawnNumber);
                    u loc = new u(pt, this.t).a(15, false);
                    newlocx = loc.f();
                    newlocy = loc.g();
                } else {
                    int rangeX = this.o - this.m;
                    int rangeY = this.p - this.n;
                    newlocx = this.m + bi.i.a(rangeX);
                    newlocy = this.n + bi.i.a(rangeY);
                }
            } else if (this.u()) {
                newlocx = this.i + (bi.i.a(this.k) - bi.i.a(this.k));
                newlocy = this.j + (bi.i.a(this.l) - bi.i.a(this.l));
            }
            npc.cG(newlocx);
            npc.cH(newlocy);
            npc.q(newlocx);
            npc.r(newlocy);
            if (!npc.fq().a(npc.fu()) || !npc.fq().c(npc.fs(), npc.ft())) continue;
            if (!(npc instanceof s) || this.u) break;
            mob = (s)npc;
            if (aq.a().f(mob).isEmpty()) break;
            bi.e.a().a(new a(spawnNumber, npc.fr()), 3000L);
            return;
        }
        npc.a(this);
        npc.g(true);
        npc.p(spawnNumber);
        if (this.c && this.d != null) {
            this.d.put(spawnNumber, new h(npc.fs(), npc.ft()));
        }
        if (npc instanceof s) {
            mob = (s)npc;
            mob.h();
            mob.b(false);
            if (mob.fp() == 666) {
                mob.c(true);
            } else if (mob.fp() >= 72 && mob.fp() <= 74) {
                ag.t(npcId);
            }
        }
        if (npcId == 45573 && npc.fp() == 2) {
            for (ap.u pc : aq.a().c()) {
                if (pc.fp() != 2) continue;
                am.a(pc, 32664, 32797, 2, 0, true);
            }
        } else if (npcId == 46142 && npc.fp() == 73 || npcId == 46141 && npc.fp() == 74) {
            for (ap.u pc : aq.a().c()) {
                if (pc.fp() < 72 || pc.fp() > 74) continue;
                am.a(pc, 32840, 32833, 72, pc.fb(), true);
            }
        }
        aq.a().a(npc);
        aq.a().c(npc);
        if (npc instanceof s) {
            mob = (s)npc;
            if (!this.c && mob.ac() == 0) {
                mob.Z_();
            }
        }
        if (this.h != 0) {
            ap.a().a(npc, this.h, this.o(), this.c);
        }
        npc.fg();
        npc.a_(0);
    }

    private static void t(int npcId) {
        int[] npcId2 = new int[]{46143, 46144, 46145, 46146, 46147, 46148, 46149, 46150, 46151, 46152};
        int[] doorId = new int[]{5001, 5002, 5003, 5004, 5005, 5006, 5007, 5008, 5009, 5010};
        int i2 = 0;
        while (i2 < npcId2.length) {
            if (npcId == npcId2[i2]) {
                ag.u(doorId[i2]);
            }
            ++i2;
        }
    }

    private static void u(int doorId) {
        for (aa object : aq.a().b()) {
            f door;
            if (!(object instanceof f) || (door = (f)object).i() != doorId) continue;
            door.g();
        }
    }

    public int b() {
        return this.e;
    }

    public void a(int id) {
        this.e = id;
    }

    public int c() {
        return this.f;
    }

    public void b(int count) {
        this.f = count;
    }

    public int d() {
        return this.g;
    }

    public void c(int npcid) {
        this.g = npcid;
    }

    public int e() {
        return this.h;
    }

    public void d(int groupid) {
        this.h = groupid;
    }

    public int f() {
        return this.i;
    }

    public void e(int baseLocX) {
        this.i = baseLocX;
    }

    public int g() {
        return this.j;
    }

    public void f(int baseLocY) {
        this.j = baseLocY;
    }

    public int h() {
        return this.k;
    }

    public void g(int randomx) {
        this.k = randomx;
    }

    public int i() {
        return this.l;
    }

    public void h(int randomy) {
        this.l = randomy;
    }

    public int j() {
        return this.m;
    }

    public void i(int locX1) {
        this.m = locX1;
    }

    public void j(int locY1) {
        this.n = locY1;
    }

    public void k(int locX2) {
        this.o = locX2;
    }

    public void l(int locY2) {
        this.p = locY2;
    }

    public int k() {
        return this.q;
    }

    public void m(int heading) {
        this.q = heading;
    }

    public int l() {
        return this.r;
    }

    public void n(int minRespawnDelay) {
        this.r = minRespawnDelay;
    }

    public int m() {
        return this.s;
    }

    public void o(int maxRespawnDelay) {
        this.s = maxRespawnDelay;
    }

    public int n() {
        return this.t;
    }

    public void p(int mapid) {
        this.t = mapid;
    }

    public boolean o() {
        return this.u;
    }

    public void a(boolean isRespawnScreen) {
        this.u = isRespawnScreen;
    }

    public int p() {
        return this.v;
    }

    public void q(int movementDistance) {
        this.v = movementDistance;
    }

    public boolean q() {
        return this.w;
    }

    public void b(boolean isRest) {
        this.w = isRest;
    }

    public int r() {
        return this.x;
    }

    public void r(int spawnType) {
        this.x = spawnType;
    }

    public boolean s() {
        return this.y;
    }

    public void c(boolean isNight) {
        this.y = isNight;
    }

    private class a
    implements Runnable {
        private final int b;
        private final int c;

        private a(int spawnNumber, int objectId) {
            this.b = spawnNumber;
            this.c = objectId;
        }

        @Override
        public void run() {
            ag.this.b(this.b, this.c);
        }
    }
}

