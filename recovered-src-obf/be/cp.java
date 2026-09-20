/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.t;
import ap.u;
import ap.z;
import be.eu;

public class cp
extends eu {
    public cp(u pc, t npc, boolean open) {
        this.c(42);
        this.c(12);
        if (open) {
            this.b(pc.ek().size() * 3);
            this.a(0);
            this.a(npc.fr());
            this.a(npc.fp());
            this.b(npc.fs());
            this.b(npc.ft());
            this.c(npc instanceof z ? 0 : 1);
            this.a(npc.T());
        } else {
            this.b(pc.ek().size() * 3 - 3);
            this.a(1);
            this.a(npc.fr());
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_PetCtrlMenu";
    }
}

