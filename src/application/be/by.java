/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.t;
import ap.u;
import be.eu;
import bh.d;

public class by
extends eu {
    public by(u pc, d l1castle) {
        this.c(72);
        this.a(pc.fr());
        this.b(l1castle.a());
        int count = l1castle.i();
        this.b(count);
        this.b(0);
        this.a(pc.et());
        this.a(pc.fr());
        int petcost = 0;
        for (t petNpc : pc.ek().values()) {
            petcost += petNpc.Q();
        }
        int charisma = pc.eC() + 6 - petcost;
        int summoncount = Math.min(charisma / 6, 5);
        this.b(Math.min(summoncount, count));
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_MercenaryArrange";
    }
}

