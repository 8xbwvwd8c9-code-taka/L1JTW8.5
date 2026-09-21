/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.g;
import ap.u;
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
        int i2 = 0;
        while (i2 < size) {
            int index = this.d();
            int count = this.d();
            int price = this.d();
            if (l1castle.f() < price * count) break;
            if (l1castle.k().get(i2) != null) {
                l1castle.k().get((int)i2).c += count;
                l1castle.b(l1castle.f() - price * count);
            }
            ++i2;
        }
        g.a().a(l1castle);
    }

    @Override
    public String a() {
        return "C_MercenaryEmpoly";
    }
}

