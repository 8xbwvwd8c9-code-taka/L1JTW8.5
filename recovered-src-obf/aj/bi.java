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
        if (clan == null || clan.m() != castaleID || l1castle.h() != pc.fr()) {
            return;
        }
        if (size <= 0 || size > l1castle.k().size()) {
            return;
        }
        int i2 = 0;
        while (i2 < size) {
            int index = this.d();
            int count = this.d();
            int price = this.d();
            if (index < 0 || index >= l1castle.k().size() || count <= 0 || price != 4000) {
                return;
            }
            long total = 4000L * (long)count;
            if (total <= 0L || total > 2000000000L || (long)l1castle.f() < total) {
                return;
            }
            bh.d.a mercenary = l1castle.k().get(index);
            if (mercenary == null || mercenary.c > 2000000000 - count) {
                return;
            }
            mercenary.c += count;
            l1castle.b((int)((long)l1castle.f() - total));
            ++i2;
        }
        g.a().a(l1castle);
    }

    @Override
    public String a() {
        return "C_MercenaryEmpoly";
    }
}

