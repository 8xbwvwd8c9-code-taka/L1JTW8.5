/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class a
extends eu {
    public a(int type, boolean equipped) {
        this.a(type, equipped);
    }

    private void a(int type, boolean equipped) {
        this.c(11);
        this.c(type);
        if (equipped) {
            this.c(1);
        } else {
            this.c(0);
        }
    }

    public a(int type, int id) {
        this.c(11);
        this.c(type);
        this.c(id);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Ability";
    }
}

