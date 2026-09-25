/*
 * Decompiled with CFR 0.152.
 */
package bh;

import ao.ah;
import bh.j;

public class u {
    private final int a;
    private final j b;
    private final int c;
    private final int d;

    public u(int itemId, int price, int packCount) {
        this.a = itemId;
        this.b = ah.a().a(itemId);
        this.c = price;
        this.d = packCount;
    }

    public int a() {
        return this.a;
    }

    public j b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public u(j l1item, int price) {
        this.a = l1item.g();
        this.b = l1item;
        this.c = price;
        this.d = 1;
    }
}

