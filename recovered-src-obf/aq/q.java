/*
 * Decompiled with CFR 0.152.
 */
package aq;

import aq.f;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class q {
    private final ConcurrentHashMap<Integer, a> a = new ConcurrentHashMap();

    public synchronized void a(f cha, int hate) {
        if (cha == null) {
            return;
        }
        if (this.a.containsKey(cha.fr())) {
            this.a.get((Object)Integer.valueOf((int)cha.fr())).b += hate;
        } else {
            a data = new a();
            data.a = cha;
            data.b = hate;
            this.a.put(cha.fr(), data);
        }
    }

    public synchronized boolean a(f cha) {
        return this.a.containsKey(cha.fr());
    }

    public synchronized void b(f cha) {
        this.a.remove(cha.fr());
    }

    public synchronized void a() {
        this.a.clear();
    }

    public synchronized boolean b() {
        return this.a.isEmpty();
    }

    public synchronized f c() {
        f cha = null;
        int hate = Integer.MIN_VALUE;
        for (a data : this.a.values()) {
            if (data.b <= hate) continue;
            cha = data.a;
            hate = data.b;
        }
        return cha;
    }

    public synchronized CopyOnWriteArrayList<a> d() {
        return new CopyOnWriteArrayList<a>(this.a.values());
    }

    public class a {
        public f a;
        public int b = 0;
    }
}

