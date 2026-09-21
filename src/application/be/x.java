/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;
import bi.c;

public class x
extends eu {
    public x(u pc, int lv, int hp, int mp, int ac2, int str, int intel, int wis, int dex, int con, int cha) {
        this.c(42);
        this.c(2);
        this.c(lv);
        this.c(pc.cx());
        this.b(hp);
        this.b(mp);
        this.b(ac2);
        this.c(str);
        this.c(intel);
        this.c(wis);
        this.c(dex);
        this.c(con);
        this.c(cha);
    }

    public x(int point) {
        this.c(42);
        this.c(3);
        this.c(point);
    }

    public x(u pc) {
        this.c(42);
        this.c(1);
        this.b(c.a(pc));
        this.b(c.c(pc));
        this.c(10);
        this.c(pc.cx());
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_CharReset";
    }
}

