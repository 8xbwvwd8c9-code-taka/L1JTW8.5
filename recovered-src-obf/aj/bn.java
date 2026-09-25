/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import ap.v;
import aq.aa;
import aq.aq;
import be.cr;
import bj.d;

public class bn
extends cv {
    private static final String a = "[C] C_PetMenu";

    public bn(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int petId = this.b();
        aa obj = aq.a().a(petId);
        if (obj instanceof v) {
            v pet = (v)obj;
            if (pet.M() != pc) {
                return;
            }
            pc.a(new cr(pet));
        }
    }

    @Override
    public String a() {
        return a;
    }
}

