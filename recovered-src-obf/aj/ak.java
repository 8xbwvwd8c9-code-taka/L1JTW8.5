/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.q;
import ap.u;
import aq.aq;
import au.f;
import be.ds;
import bi.h;
import bj.d;

public class ak
extends cv {
    private final int[][] a;

    public ak(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        int[][] nArrayArray = new int[8][];
        int[] nArray = new int[2];
        nArray[1] = -1;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{1, -1};
        int[] nArray2 = new int[2];
        nArray2[0] = 1;
        nArrayArray[2] = nArray2;
        nArrayArray[3] = new int[]{1, 1};
        int[] nArray3 = new int[2];
        nArray3[1] = 1;
        nArrayArray[4] = nArray3;
        nArrayArray[5] = new int[]{-1, 1};
        int[] nArray4 = new int[2];
        nArray4[0] = -1;
        nArrayArray[6] = nArray4;
        nArrayArray[7] = new int[]{-1, -1};
        this.a = nArrayArray;
        u pc = client.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int size = this.b();
        int i2 = 0;
        while (i2 < size) {
            int x2 = this.d();
            int y2 = this.d();
            int objectId = this.b();
            int count = this.b();
            q item = pc.j().e(objectId);
            if (item != null) {
                if (!item.a().s() || item.F() >= 128) {
                    pc.a(new ds(210, item.a().h()));
                } else if (pc.N(item.fr())) {
                    pc.a(new ds(1187));
                } else if (pc.O(item.fr())) {
                    pc.a(new ds(1181));
                } else if (item.D()) {
                    pc.a(new ds(125));
                } else {
                    h h2 = new h(x2, y2);
                    if (pc.fu().b(h2) > 1.0) {
                        int dir = pc.h(x2, y2);
                        x2 = pc.fs() + this.a[dir][0];
                        y2 = pc.ft() + this.a[dir][1];
                    }
                    pc.j().a(item, count, (f)aq.a().a(x2, y2, pc.fp()));
                }
            }
            ++i2;
        }
        pc.fg();
    }

    @Override
    public String a() {
        return "C_DropItem";
    }
}

