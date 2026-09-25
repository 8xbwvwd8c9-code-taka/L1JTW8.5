/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.ah;
import ao.g;
import ap.q;
import ap.u;
import aq.i;
import be.ei;
import bj.d;

public class aj
extends cv {
    public aj(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int objid = this.b();
        int count = this.b();
        q inventoryItem = pc.j().b(40308);
        long inventoryItemCount = 0L;
        if (inventoryItem != null) {
            inventoryItemCount = inventoryItem.E();
        }
        if (inventoryItemCount + (long)count > 2000000000L) {
            pc.a(new ei("\u4f60\u8eab\u4e0a\u7684\u91d1\u5e63\u5df2\u7d93\u8d85\u904e2000000000\u4e86\uff0c\u6240\u4ee5\u4e0d\u80fd\u9818\u53d6\u91d1\u5e63\u3002"));
            return;
        }
        i clan = ao.q.a().a(pc.aF());
        if (clan == null) {
            return;
        }
        int castle_id = clan.m();
        if (castle_id == 0) {
            return;
        }
        bh.d l1castle = g.a().a(castle_id);
        if (l1castle != null && count > 0 && count <= l1castle.f()) {
            g.a().transferTreasuryAdena(pc, castle_id, count, false);
        }
    }

    @Override
    public String a() {
        return "C_Drawal";
    }
}

