/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class eq
extends eu {
    public eq(int type, String clan_name1, String clan_name2) {
        this.a(type, clan_name1, clan_name2);
    }

    private void a(int type, String clan_name1, String clan_name2) {
        this.c(114);
        this.c(type);
        this.a(clan_name1);
        this.a(clan_name2);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_War";
    }
}

