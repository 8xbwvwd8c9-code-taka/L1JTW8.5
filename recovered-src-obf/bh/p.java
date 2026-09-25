/*
 * Decompiled with CFR 0.152.
 */
package bh;

import ao.au;
import bh.l;
import bi.f;

public class p {
    private final int a;
    private final l b;
    private final String c;
    private final int d;
    private final f e;
    private final f f;
    private final int g;
    private final int[] h;
    private final int i;
    private final int j;
    private final boolean k;

    public p(int baseNpcId, String name, int itemIdForTaming, f hpUpRange, f mpUpRange, int evolvItemId, int npcIdForEvolving, int[] msgIds, int defyMsgId, boolean canUseEquipment) {
        this.a = baseNpcId;
        this.b = au.a().a(baseNpcId);
        this.c = name;
        this.d = itemIdForTaming;
        this.e = hpUpRange;
        this.f = mpUpRange;
        this.j = evolvItemId;
        this.g = npcIdForEvolving;
        this.h = msgIds;
        this.i = defyMsgId;
        this.k = canUseEquipment;
    }

    public int a() {
        return this.a;
    }

    public l b() {
        return this.b;
    }

    public String c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public f e() {
        return this.e;
    }

    public f f() {
        return this.f;
    }

    public int g() {
        return this.g;
    }

    public int a(int num) {
        if (num == 0) {
            return 0;
        }
        return this.h[num - 1];
    }

    public static int b(int level) {
        if (50 <= level) {
            return 5;
        }
        if (48 <= level) {
            return 4;
        }
        if (36 <= level) {
            return 3;
        }
        if (24 <= level) {
            return 2;
        }
        if (12 <= level) {
            return 1;
        }
        return 0;
    }

    public int h() {
        return this.i;
    }

    public int i() {
        return this.j;
    }

    public boolean j() {
        return this.k;
    }
}

