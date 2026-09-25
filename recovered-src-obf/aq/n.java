/*
 * Decompiled with CFR 0.152.
 */
package aq;

import java.util.concurrent.CopyOnWriteArrayList;

public class n {
    private final CopyOnWriteArrayList<String> a = new CopyOnWriteArrayList();

    public String[] a() {
        return this.a.toArray(new String[this.a.size()]);
    }

    public void a(String name) {
        this.a.add(name);
    }

    public String b(String name) {
        for (String each : this.a) {
            if (!each.equalsIgnoreCase(name)) continue;
            this.a.remove(each);
            return each;
        }
        return null;
    }

    public boolean c(String name) {
        for (String each : this.a) {
            if (!each.equalsIgnoreCase(name)) continue;
            return true;
        }
        return false;
    }

    public boolean b() {
        return this.a.size() > 50;
    }
}

