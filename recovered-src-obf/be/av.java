/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class av
extends eu {
    public av(int objectId, int motionNum, int x2, int y2) {
        this.c(75);
        this.a(objectId);
        this.c(motionNum);
        this.b(x2);
        this.b(y2);
        this.a(0);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Fishing";
    }
}

