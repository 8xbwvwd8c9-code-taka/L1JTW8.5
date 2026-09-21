/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.am;
import aq.aq;
import be.ei;

public class bc
implements l {
    private bc() {
    }

    public static l a() {
        return new bc();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            u target = aq.a().a(arg);
            if (target != null) {
                am.a(pc, target.fs(), target.ft(), target.fp(), 5, true);
                pc.a(new ei(arg + "\u79fb\u52d5\u5230\u73a9\u5bb6\u8eab\u908a\u3002"));
            } else {
                pc.a(new ei(arg + "\u4e0d\u5728\u7dda\u4e0a\u3002"));
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165: " + cmdName + " \u73a9\u5bb6\u540d\u7a31 \u3002"));
        }
    }
}

