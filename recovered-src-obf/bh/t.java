/*
 * Decompiled with CFR 0.152.
 */
package bh;

import bh.u;
import java.util.List;

public class t {
    private final int a;
    private final List<u> b;
    private final List<u> c;

    public t(int npcId, List<u> sellingItems, List<u> purchasingItems) {
        if (sellingItems == null || purchasingItems == null) {
            throw new NullPointerException();
        }
        this.a = npcId;
        this.b = sellingItems;
        this.c = purchasingItems;
    }

    public int a() {
        return this.a;
    }

    public List<u> b() {
        return this.b;
    }

    public List<u> c() {
        return this.c;
    }
}

