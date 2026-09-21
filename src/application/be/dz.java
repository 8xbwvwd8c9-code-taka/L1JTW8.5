/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.dy;
import be.eu;
import java.util.ArrayList;

public class dz
extends eu {
    public dz(int skillid) {
        this.c(65);
        this.a(skillid - 1);
    }

    public dz(u pc) {
        ArrayList<Integer> buyList = dy.a(pc);
        this.c(98);
        this.b(buyList.size());
        for (int i2 : buyList) {
            this.a(i2);
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_SkillBuyItem";
    }
}

