/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.t;
import aq.an;
import aq.e;

public class al {
    private static final int a = 15;
    private final int b;
    private final int c;

    public al(t npc) {
        this.b = e.b(npc);
        this.c = an.a(npc);
    }

    public int a(int price) {
        return price + this.e(price);
    }

    private int e(int price) {
        int taxCastle = price * this.b;
        int taxTown = price * this.c;
        int taxWar = price * 15;
        return (taxCastle + taxTown + taxWar) / 100;
    }

    public int b(int price) {
        return price * this.b / 100;
    }

    public int c(int price) {
        return price * this.c / 100;
    }

    public int d(int price) {
        return price * 15 / 100;
    }
}

