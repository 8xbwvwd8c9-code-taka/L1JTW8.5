/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.aa;
import ap.q;
import ap.u;
import aq.aq;
import au.e;
import au.f;
import be.ak;
import be.ds;
import be.ei;
import bj.d;

public class bo
extends cv {
    public bo(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        u pc = client.f();
        if (pc == null || pc.eX() || pc.bN()) {
            return;
        }
        if (pc.ff() || pc.N()) {
            return;
        }
        int x2 = this.d();
        int y2 = this.d();
        int objectId = this.b();
        int pickupCount = this.b();
        if (objectId == pc.fr()) {
            return;
        }
        e groundInventory = aq.a().a(x2, y2, pc.fp());
        q object = groundInventory.e(objectId);
        if (object == null) {
            return;
        }
        q item = object;
        if (item.S() != 0 && pc.fr() != item.S()) {
            pc.a(new ds(623));
            return;
        }
        if (pc.fu().c(item.fu()) > 3) {
            return;
        }
        if (item.N() == 40308) {
            q inventoryItem = pc.j().b(40308);
            int inventoryItemCount = 0;
            if (inventoryItem != null) {
                inventoryItemCount = inventoryItem.E();
            }
            if ((long)inventoryItemCount + (long)pickupCount > 2000000000L) {
                pc.a(new ei("\u4f60\u8eab\u4e0a\u7684\u91d1\u5e63\u5df2\u7d93\u8d85\u904e2000000000\u4e86\uff0c\u6240\u4ee5\u4e0d\u80fd\u64bf\u53d6\u91d1\u5e63\u3002"));
                return;
            }
        }
        if (pc.j().a(item, pickupCount) == 0 && item.fs() != 0 && item.ft() != 0) {
            groundInventory.a(item, pickupCount, (f)pc.j());
            pc.fg();
            pc.a(new ak(pc.fr(), 15));
            if (!pc.aA()) {
                pc.b(new ak(pc.fr(), 15));
            }
            aa.a().c(pc, "\u64bf\u8d77", item, pickupCount);
        }
    }

    @Override
    public String a() {
        return "C_PickUpItem";
    }
}

