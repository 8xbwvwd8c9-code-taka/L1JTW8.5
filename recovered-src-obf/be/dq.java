/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class dq
extends eu {
    public dq(int objectId, String houseNumber) {
        this.c(193);
        this.a(objectId);
        this.a(0);
        this.a(100000);
        this.a(100000);
        this.a(2000000000);
        this.b(2);
        this.a("agsell");
        this.a("agsell " + houseNumber);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_SellHouse";
    }
}

