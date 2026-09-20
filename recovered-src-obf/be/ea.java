/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class ea
extends eu {
    public static final int a = 0;
    public static final int b = 1;
    public static final int c = 2;

    public ea(int objid, int type, int times) {
        this.c(88);
        this.a(objid);
        this.c(type);
        this.b(times);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_SkillHaste";
    }
}

