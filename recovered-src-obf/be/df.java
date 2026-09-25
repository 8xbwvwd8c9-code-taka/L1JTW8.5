/*
 * Decompiled with CFR 0.152.
 */
package be;

import ai.c;
import an.i;
import ao.ba;
import be.eu;
import bi.g;
import java.util.Date;

public class df
extends eu {
    public df(int lv, int clan, int weapon, int rich, int rich2, int kill) {
        this.c(121);
        this.c(166);
        this.c(0);
        this.a((int)(new Date().getTime() / 1000L));
        this.a(lv);
        this.a(clan);
        this.a(weapon);
        this.a(rich);
        this.a(rich2);
        this.a(kill);
    }

    public df(int type, ba.a ... datas) {
        this.c(1);
        this.b(136);
        i.e.a builder = i.e.G();
        builder.b(0);
        builder.c(c.a().a);
        builder.d(type);
        builder.e(1);
        builder.f(1);
        int i2 = 0;
        while (i2 < datas.length) {
            ba.a data = datas[i2];
            i.a.a builder1 = i.a.G();
            int effect = 1;
            if (i2 + 1 >= 31 && i2 + 1 <= 60) {
                ++effect;
            } else if (i2 + 1 >= 11 && i2 + 1 <= 30) {
                effect += 2;
            } else if (i2 + 1 >= 1 && i2 + 1 <= 10) {
                effect += 3;
            }
            builder1.b(effect);
            builder1.c(i2 + 1);
            builder1.d(data.f);
            builder1.e(data.e);
            builder1.e(g.a(data.b));
            builder.e(builder1.H().f());
            ++i2;
        }
        this.a(builder.H().g());
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Rank";
    }
}

