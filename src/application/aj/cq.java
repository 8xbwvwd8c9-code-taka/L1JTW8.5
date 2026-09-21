/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.av;
import ao.ax;
import ap.q;
import ap.u;
import ap.v;
import aq.aq;
import be.ds;
import bh.o;
import bh.p;
import bj.d;

public class cq
extends cv {
    private static final String a = "[C] C_UsePetItem";

    public cq(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int data = this.c();
        int petId = this.b();
        int listNo = this.c();
        v pet = (v)aq.a().a(petId);
        if (pet == null) {
            return;
        }
        q item = pet.y().d().get(listNo);
        if (item == null) {
            return;
        }
        if (item.f() && item.a().aP() == 11) {
            p petType = ax.b().a(pet.U_().b());
            if (!petType.j()) {
                pc.a(new ds(74, item.s()));
                return;
            }
            int itemId = item.N();
            o petItem = av.a().a(itemId);
            if (petItem.n() == 1) {
                pet.a(pet, item);
                pc.a(new be.cq(data, pet, listNo));
            } else if (petItem.n() == 0) {
                pet.b(pet, item);
                pc.a(new be.cq(data, pet, listNo));
            } else {
                pc.a(new ds(74, item.s()));
            }
        } else {
            pc.a(new ds(74, item.s()));
        }
    }

    @Override
    public String a() {
        return a;
    }
}

