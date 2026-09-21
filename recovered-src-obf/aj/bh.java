/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.au;
import ao.g;
import ao.q;
import ap.t;
import ap.u;
import ap.z;
import bh.d;
import aq.i;
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
        i clan = q.a().a(pc.aF());
        if (clan == null || clan.m() != castaleID) {
            return;
        }
        int petcost = 0;
        for (t petNpc : pc.ek().values()) {
            petcost += petNpc.Q();
        }
        int charisma = pc.eC() + 6 - petcost;
        int maxCount = Math.min(5, Math.min(l1castle.i(), Math.max(charisma / 6, 0)));
        if (count <= 0 || count > maxCount) {
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

