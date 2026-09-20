/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;

public class bg
extends eu {
    public bg(u pc) {
        int[] init = pc.aC().a();
        int diffStr = pc.bq() - init[0];
        int diffDex = pc.bs() - init[1];
        int diffCon = pc.br() - init[2];
        int diffInt = pc.bu() - init[3];
        int diffWis = pc.bv() - init[4];
        int diffCha = pc.bt() - init[5];
        int write1 = diffInt * 16 + diffStr;
        int write2 = diffDex * 16 + diffWis;
        int write3 = diffCha * 16 + diffCon;
        this.c(42);
        this.c(4);
        this.c(write1);
        this.c(write2);
        this.c(write3);
        this.c(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_InitAbility";
    }
}

