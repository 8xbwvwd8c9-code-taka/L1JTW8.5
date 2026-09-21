/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.am;
import aq.aq;
import be.ei;
import bj.d;

public class q
extends cv {
    public q(byte[] decrypt, d client) {
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
        am.a(target, pc, 2);
        pc.a(new ei(String.valueOf(name) + "\u6210\u529f\u88ab\u60a8\u53ec\u559a\u56de\u4f86\u3002"));
        target.a(new ei("\u60a8\u88ab\u53ec\u559a\u5230GM\u8eab\u908a\u3002"));
    }

    @Override
    public String a() {
        return "C_CallUser";
    }
}

