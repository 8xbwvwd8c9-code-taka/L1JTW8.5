/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.u;
import aq.aq;
import java.util.LinkedHashMap;
import java.util.Map;

public class d {
    private final int a;
    private final LinkedHashMap<Integer, String> b = new LinkedHashMap();

    public d(int charId) {
        this.a = charId;
    }

    public int a() {
        return this.a;
    }

    public boolean a(int objId, String name) {
        if (this.b.containsKey(objId)) {
            return false;
        }
        this.b.put(objId, name);
        return true;
    }

    public boolean a(String name) {
        int id = 0;
        for (Map.Entry<Integer, String> buddy : this.b.entrySet()) {
            if (!name.equalsIgnoreCase(buddy.getValue())) continue;
            id = buddy.getKey();
            break;
        }
        if (id == 0) {
            return false;
        }
        this.b.remove(id);
        return true;
    }

    public String b() {
        String result = new String("");
        for (u pc : aq.a().c()) {
            if (!this.b.containsKey(pc.fr())) continue;
            result = String.valueOf(result) + pc.et() + " ";
        }
        return result;
    }

    public String[] c() {
        return this.b.values().toArray(new String[0]);
    }

    public boolean b(String name) {
        for (String buddyName : this.b.values()) {
            if (!name.equalsIgnoreCase(buddyName)) continue;
            return true;
        }
        return false;
    }
}

