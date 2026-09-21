/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.g;
import ao.q;
import ap.u;
import aq.i;
import bj.d;

public class bi
extends cv {
    public bi(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int castaleID = this.b();
        int size = this.d();
        bh.d l1castle = g.a().a(castaleID);
        if (l1castle == null) {
            return;
        }
        i clan = q.a().a(pc.aF());
        if (clan == null || clan.m() != castaleID || clan.k() != pc.fr() || l1castle.h() != pc.fr()) {
            return;
        }
        if (size <= 0 || size > l1castle.k().size()) {
            return;
        }

        long[] additions = new long[l1castle.k().size()];
        long totalCost = 0L;
        int i2 = 0;
        while (i2 < size) {
            int index = this.d();
            int count = this.d();
            int price = this.d();
            if (index < 0 || index >= additions.length || count <= 0 || price != 4000) {
                return;
            }
            additions[index] += (long)count;
            if (additions[index] > 2000000000L) {
                return;
            }
            totalCost += 4000L * (long)count;
            if (totalCost <= 0L || totalCost > 2000000000L || totalCost > (long)l1castle.f()) {
                return;
            }
            ++i2;
        }

        int oldMoney = l1castle.f();
        int[] oldCounts = new int[l1castle.k().size()];
        int idx = 0;
        while (idx < oldCounts.length) {
            oldCounts[idx] = l1castle.k().get(idx).c;
            long next = (long)oldCounts[idx] + additions[idx];
            if (next > 2000000000L) {
                return;
            }
            l1castle.k().get(idx).c = (int)next;
            ++idx;
        }
        l1castle.b((int)((long)oldMoney - totalCost));

        if (!g.a().a(l1castle)) {
            l1castle.b(oldMoney);
            idx = 0;
            while (idx < oldCounts.length) {
                l1castle.k().get(idx).c = oldCounts[idx];
                ++idx;
            }
        }
    }

    @Override
    public String a() {
        return "C_MercenaryEmpoly";
    }
}

