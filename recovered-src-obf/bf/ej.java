/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.au;
import ao.be;
import ap.t;
import ap.u;
import ap.z;
import aq.f;
import bf.a;
import bh.l;
import bh.v;

public class ej
extends a {
    private final int a = 162;
    private final v b = be.a().a(162);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        if (_user instanceof u) {
            u pc = (u)_user;
            int attr = pc.bC();
            if (attr == 0 || !pc.fq().o()) {
                this.b(_user, 79);
                return;
            }
            int petcost = 0;
            for (t pet : pc.ek().values()) {
                petcost += pet.Q();
            }
            if (petcost == 0) {
                int[] nArray = new int[5];
                nArray[0] = 81053;
                nArray[1] = 81050;
                nArray[2] = 81051;
                nArray[4] = 81052;
                int[] summons = nArray;
                int summonid = summons[attr / 2];
                l npcTemp = au.a().a(summonid);
                z summon = new z(npcTemp, pc);
                summon.o(pc.eC() + 7);
            }
        }
    }

    @Override
    public void a(f cha) {
    }
}

