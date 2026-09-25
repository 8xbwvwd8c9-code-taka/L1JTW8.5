/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class bb
extends eu {
    public bb(int objectId, String house_number) {
        this.a(objectId, house_number);
    }

    private void a(int objectId, String house_number) {
        int number = Integer.valueOf(house_number);
        this.c(78);
        this.a(objectId);
        this.a(number);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_HouseMap";
    }
}

