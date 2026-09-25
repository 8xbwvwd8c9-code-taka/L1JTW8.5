/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.bi;
import ap.u;
import aq.ak;
import aq.ao;
import aq.l;
import be.cb;
import be.ei;
import be.k;
import bj.d;
import l1j.server.a;

public class bj
extends cv {
    private final int[][] a;

    public bj(byte[] decrypt, d client) throws Exception {
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
        if (pc == null || pc.aR()) {
            return;
        }
        int locx = this.d();
        int locy = this.d();
        int heading = this.c();
        int result = pc.ce().a(ak.a.a);
        if (result == 2) {
            return;
        }
        if (pc.aO() != 0) {
            ao.b(pc);
        }
        if (pc.cO() != null) {
            pc.cO().a();
        }
        if (pc.bB(32)) {
            pc.bz(32);
        }
        pc.aQ(0);
        if (!pc.bB(78)) {
            pc.e(2);
        }
        if (l1j.server.a.m == 3) {
            boolean check;
            boolean bl2 = check = (locx | locy << 16) == ((pc.fs() | pc.ft() << 16) ^ 0x43F8A19C) * (pc.fr() | 1);
            if (!check) {
                pc.a(new ei("char move has some error"));
                pc.a(new k());
                return;
            }
            heading ^= 0x49;
            locx = pc.fs();
            locy = pc.ft();
        }
        if (heading < 0 || heading >= this.a.length) {
            pc.a(new k());
            return;
        }
        locx += this.a[heading][0];
        locy += this.a[heading][1];
        if (!pc.fq().b(pc.fs(), pc.ft(), heading)) {
            pc.a(new ei("Lineage map has some error"));
            pc.a(new k());
            return;
        }
        if (l.a().a(locx, locy, pc.fq().b(), pc)) {
            return;
        }
        pc.fq().a(pc.fu(), true);
        pc.fu().a(locx, locy);
        pc.ct(heading);
        pc.fq().a(pc.fu(), false);
        bi.a().a(pc);
        if (pc.aA() || pc.bN()) {
            return;
        }
        if (pc.ff()) {
            pc.c(new cb(pc));
        } else {
            pc.b(new cb(pc));
        }
    }

    public void a(u pc) {
        pc.a(new ei(pc.fq().e(pc.fu())));
    }
}

