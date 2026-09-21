/*
 * Decompiled with CFR 0.152.
 */
package be;

import ao.ab;
import be.eu;
import bh.i;

public class e
extends eu {
    public e(int objectId, String houseNumber) {
        int houseId = Integer.valueOf(houseNumber);
        i house = ab.a().a(houseId);
        this.c(193);
        this.a(objectId);
        this.a(0);
        if (house.o() == 0) {
            this.a(house.k());
            this.a(house.k());
        } else {
            this.a(house.k() + 1);
            this.a(house.k() + 1);
        }
        this.a(2000000000);
        this.b(2);
        this.a("agapply");
        this.a("agapply " + houseNumber);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ApplyAuction";
    }
}

