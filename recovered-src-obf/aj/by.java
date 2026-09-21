/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.s;
import ap.u;
import ap.v;
import ap.z;
import aq.aa;
import aq.aq;
import aq.f;
import be.ds;
import bj.d;

public class by
extends cv {
    public by(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int petId = this.b();
        int unknow = this.c();
        int targetId = this.b();
        aa obj = aq.a().a(petId);
        aa targetObj = aq.a().a(targetId);
        if (!(obj instanceof v) || !(targetObj instanceof f)) {
            pc.a(new ds(328));
            return;
        }
        v pet = (v)obj;
        if (pet.M() != pc) {
            pc.a(new ds(328));
            return;
        }
        f target = (f)targetObj;
        if (pet.M() != pc || pet.fp() != pc.fp() || target.fp() != pet.fp()) {
            pc.a(new ds(328));
            return;
        }
        if (target instanceof u) {
            u tpc = (u)target;
            if (tpc.ep() == 1 || pet.ep() == 1 || tpc.a(tpc, pet, false)) {
                pc.a(new ds(328));
                return;
            }
        } else if (target instanceof v) {
            v targetPet = (v)target;
            if (targetPet.ep() == 1 || pet.ep() == 1) {
                pc.a(new ds(328));
                return;
            }
        } else if (target instanceof z) {
            z targetSummon = (z)target;
            if (targetSummon.ep() == 1 || pet.ep() == 1) {
                pc.a(new ds(328));
                return;
            }
        } else if (target instanceof s) {
            s mob = (s)target;
            if (pet.M().d(pet.M(), mob.z())) {
                pc.a(new ds(328));
                return;
            }
        }
        pet.g(target);
    }

    @Override
    public String a() {
        return "C_SelectTarget";
    }
}

