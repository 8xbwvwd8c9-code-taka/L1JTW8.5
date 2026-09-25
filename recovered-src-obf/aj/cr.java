/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.be;
import ap.u;
import aq.ak;
import be.ds;
import bf.a;
import bh.c;
import bi.g;
import bj.d;

public class cr
extends cv {
    public cr(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        u pc = client.f();
        if (pc == null || pc.aR() || pc.eX()) {
            return;
        }
        int row = this.c();
        int column = this.c();
        int skillId = row * 8 + column + 1;
        String message = null;
        int targetId = 0;
        int targetX = 0;
        int targetY = 0;
        if (!pc.fq().q()) {
            pc.a(new ds(563));
            return;
        }
        if (!pc.h(skillId)) {
            return;
        }
        if (be.a().a(skillId) == null) {
            return;
        }
        int result = be.a().a(skillId).s() == 18 ? pc.ce().a(ak.a.c) : pc.ce().a(ak.a.d);
        if (result == 2) {
            return;
        }
        if (pc.bB(78)) {
            pc.bz(78);
        }
        if (pc.bB(32)) {
            pc.bz(32);
        }
        try {
            if (skillId == 116 || skillId == 118) {
                message = this.g();
            } else if (skillId == 5 || skillId == 69) {
                int y2;
                int mapid = this.d();
                int x2 = this.d();
                c book = pc.a(x2, y2 = this.d());
                if (book != null) {
                    targetId = book.a();
                }
            } else if (skillId == 58 || skillId == 63) {
                targetX = this.d();
                targetY = this.d();
            } else {
                targetId = this.b();
                targetX = this.d();
                targetY = this.d();
            }
        }
        catch (ArrayIndexOutOfBoundsException mapid) {
            // empty catch block
        }
        a executor = g.a(skillId);
        if (executor == null) {
            return;
        }
        executor.a(pc, targetId, skillId, targetX, targetY, message);
    }
}

