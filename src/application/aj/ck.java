/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.am;
import aq.aq;
import be.ee;
import be.ei;
import bj.d;

public class ck
extends cv {
    public ck(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null || !pc.l()) {
            return;
        }
        String name = this.g();
        if (name.isEmpty()) {
            return;
        }
        u target = aq.a().a(name);
        if (target == null) {
            pc.a(new ei(String.valueOf(name) + "\u5df2\u4e0d\u5728\u7dda\u4e0a\u3002"));
            return;
        }
        aq.u loc = aq.u.a(target.fu(), 1, 2, false);
        am.a(pc, loc.f(), loc.g(), target.fp(), pc.fb(), true);
        pc.a(new ee(pc.fr(), 12446));
        pc.b(new ee(pc.fr(), 12446));
        pc.a(new ei("\u79fb\u52d5\u5230\u73a9\u5bb6[" + name + "]\u8eab\u908a\u3002"));
    }

    @Override
    public String a() {
        return "C_TeleportUser";
    }
}

