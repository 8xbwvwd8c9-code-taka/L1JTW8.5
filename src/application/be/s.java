/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class s
extends eu {
    public s(int objId, int polyId, int currentWeapon) {
        this.c(144);
        this.a(objId);
        this.b(polyId);
        this.c(currentWeapon);
        this.b(65535);
        this.c(0);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ChangeShape";
    }
}

