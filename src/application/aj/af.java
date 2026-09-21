/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.q;
import ap.u;
import be.ds;
import bj.d;

public class af
extends cv {
    public af(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int size = this.b();
        int i2 = 0;
        while (i2 < size) {
            int itemObjectId = this.b();
            int deleteCount = this.b();
            q item = pc.j().e(itemObjectId);
            if (item != null) {
                if (item.a().t()) {
                    pc.a(new ds(125));
                } else if (pc.N(item.fr())) {
                    pc.a(new ds(1187));
                } else if (pc.O(item.fr())) {
                    pc.a(new ds(1181));
                } else if (item.D()) {
                    pc.a(new ds(125));
                } else if (item.F() >= 128) {
                    pc.a(new ds(210, item.a().h()));
                } else {
                    pc.j().b(item, deleteCount > 0 ? deleteCount : item.E());
                }
            }
            ++i2;
        }
        pc.fg();
    }

    @Override
    public String a() {
        return "C_DeleteInventoryItem";
    }
}

