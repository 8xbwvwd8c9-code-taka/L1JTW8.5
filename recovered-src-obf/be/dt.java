/*
 * Decompiled with CFR 0.152.
 */
package be;

import ai.c;
import an.i;
import be.eu;
import l1j.server.a;

public class dt
extends eu {
    public dt() {
        this.c(1);
        this.b(821);
        i.g.a builder = i.g.ak();
        builder.a(0);
        builder.b(a.a);
        builder.c(1710162001);
        builder.d(1710162001);
        builder.e(2015090301);
        builder.f(1710162001);
        builder.g(c.a().a);
        builder.h(0);
        builder.i(3);
        builder.j(this.e());
        builder.k((int)(System.currentTimeMillis() / 1000L));
        builder.l(150316700);
        builder.m(150204901);
        builder.n(151118701);
        builder.o(1710161002);
        builder.p(201523276);
        builder.q(3);
        this.a(builder.M().g());
        this.b(0);
    }

    public dt(int i2) {
        this.c(181);
        this.c(0);
        this.c(a.a);
        this.a(1701172002);
        this.a(1701172002);
        this.a(2015090301);
        this.a(1701172002);
        this.a(c.a().a);
        this.c(0);
        this.c(0);
        this.c(3);
        this.a(this.e());
        this.a((int)(System.currentTimeMillis() / 1000L));
        this.a(150316700);
        this.a(150204901);
        this.a(151118701);
        this.a(495);
        this.a(160922701);
    }

    private int e() {
        boolean[] blArray = new boolean[31];
        blArray[1] = true;
        blArray[7] = true;
        blArray[8] = true;
        blArray[11] = true;
        blArray[12] = true;
        blArray[13] = true;
        blArray[14] = true;
        blArray[16] = true;
        blArray[17] = true;
        blArray[18] = true;
        blArray[19] = true;
        blArray[20] = true;
        blArray[21] = true;
        blArray[22] = true;
        blArray[23] = true;
        blArray[26] = true;
        blArray[27] = true;
        blArray[28] = true;
        blArray[29] = true;
        blArray[30] = true;
        boolean[] setting = blArray;
        int val = 0;
        int i2 = 0;
        while (i2 < setting.length) {
            if (setting[i2]) {
                val |= 1 << i2;
            }
            ++i2;
        }
        return val;
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ServerVersion";
    }
}

