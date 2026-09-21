/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class w
extends eu {
    public w(String name, String clanName, int type, int sex, int lawful, int hp, int mp, int ac2, int lv, int str, int dex, int con, int wis, int cha, int intel, int accessLevel, int birthday) {
        this.c(47);
        this.a(name);
        this.a(clanName);
        this.c(type);
        this.c(sex);
        this.b(lawful);
        this.b(hp);
        this.b(mp);
        this.c(ac2);
        this.c(lv);
        this.c(str);
        this.c(dex);
        this.c(con);
        this.c(wis);
        this.c(cha);
        this.c(intel);
        this.c(lv >= 55 ? accessLevel : 0);
        this.a(birthday);
        this.c((lv ^ str ^ dex ^ con ^ wis ^ cha ^ intel) & 0xFF);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_CharPacks";
    }
}

