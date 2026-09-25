/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.t;
import aq.ag;
import java.util.concurrent.CopyOnWriteArrayList;

public class y {
    private final CopyOnWriteArrayList<t> a = new CopyOnWriteArrayList();
    private t b;
    private ag c;
    private boolean d;

    public void a(t npc) {
        this.b = npc;
    }

    public t a() {
        return this.b;
    }

    public boolean b(t npc) {
        return npc.fr() == this.b.fr();
    }

    public void a(ag spawn) {
        this.c = spawn;
    }

    public ag b() {
        return this.c;
    }

    public void c(t npc) {
        if (npc == null) {
            throw new NullPointerException();
        }
        if (this.a.isEmpty()) {
            this.a(npc);
            if (npc.Z()) {
                this.a(npc.R());
            }
        }
        if (!this.a.contains(npc)) {
            this.a.add(npc);
        }
        npc.a(this);
        npc.w(this.b.fr());
    }

    public synchronized int d(t npc) {
        if (npc == null) {
            throw new NullPointerException();
        }
        if (this.a.contains(npc)) {
            this.a.remove(npc);
        }
        npc.a((y)null);
        if (this.b(npc)) {
            if (this.d() && !this.a.isEmpty()) {
                for (t minion : this.a) {
                    minion.a((y)null);
                    minion.a((ag)null);
                    minion.g(false);
                }
                return 0;
            }
            if (!this.a.isEmpty()) {
                this.a(this.a.get(0));
            }
        }
        return this.a.size();
    }

    public int c() {
        return this.a.size();
    }

    public boolean d() {
        return this.d;
    }

    public void a(boolean flag) {
        this.d = flag;
    }
}

