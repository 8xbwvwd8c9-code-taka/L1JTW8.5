/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.au;
import ao.g;
import ap.u;
import ap.z;
import bh.d;
import bh.l;
import bj.d;

public class bh
extends cv {
    public bh(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int objid = this.b();
        int size = this.d();
        int castaleID = this.d();
        int targetid = this.b();
        int count = this.d();
        bh.d l1castle = g.a().a(castaleID);
        if (l1castle == null) {
            return;
        }
        int i2 = 0;
        while (i2 < count) {
            int summonid = 0;
            for (d.a m2 : l1castle.k()) {
                if (m2.c <= 0) continue;
                summonid = m2.a;
                --m2.c;
                break;
            }
            if (summonid == 0) {
                return;
            }
            l npcTemp = au.a().a(summonid);
            z summon = new z(npcTemp, pc);
            summon.o(6);
            ++i2;
        }
        g.a().a(l1castle);
    }

    @Override
    public String a() {
        return "C_MercenaryArrange";
    }
}

