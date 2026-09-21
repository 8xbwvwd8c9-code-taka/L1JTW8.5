/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.l;
import ax.d;
import be.ei;

public class an
extends cv {
    public an(byte[] abyte0, bj.d client) throws Exception {
        super(abyte0);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int locx = this.d();
        int locy = this.d();
        if (pc.aR()) {
            return;
        }
        l.a().a(locx, locy, pc.fq().b(), pc);
        if (pc.l()) {
            int gab = d.b().a(pc.fp()).a(locx, locy);
            String msg = String.format("\u5ea7\u6a19 (%d, %d, %d) %d", locx, locy, pc.fp(), gab);
            pc.a(new ei(msg));
        }
    }

    @Override
    public String a() {
        return "C_EnterPortal";
    }
}

