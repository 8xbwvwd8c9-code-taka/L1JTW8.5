/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.q;
import ap.t;
import ap.u;
import ap.v;
import be.eu;
import java.util.ArrayList;

public class cs
extends eu {
    public cs(int npcObjId, u pc) {
        this.a(npcObjId, pc);
    }

    private void a(int npcObjId, u pc) {
        ArrayList<q> amuletList = new ArrayList<q>();
        for (q item : pc.j().d()) {
            if (item.N() != 40314 && item.N() != 40316 || this.a(pc, item)) continue;
            amuletList.add(item);
        }
        if (!amuletList.isEmpty()) {
            this.c(162);
            this.a(npcObjId);
            this.b(amuletList.size());
            this.c(12);
            for (q item : amuletList) {
                this.a(item.fr());
                this.c(0);
                this.b(item.e());
                this.c(item.F());
                this.a(item.E());
                this.c(item.C() ? 1 : 0);
                this.a(item.r());
            }
        } else {
            return;
        }
        this.a(115);
    }

    private boolean a(u pc, q item) {
        for (t petNpc : pc.ek().values()) {
            if (!(petNpc instanceof v)) continue;
            v pet = (v)petNpc;
            if (item.fr() != pet.k()) continue;
            return true;
        }
        return false;
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_PetList";
    }
}

