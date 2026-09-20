/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.aj;
import be.ei;

public class a
implements l {
    private a() {
    }

    public static l a() {
        return new a();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            u target = aq.a().a(arg);
            if (target != null) {
                ao.a.a().d(target.bc());
                pc.a(new ei(String.valueOf(target.et()) + "\u88ab\u60a8\u5f37\u5236\u8e22\u9664\u904a\u6232\u4e26\u5c01\u9396IP"));
                target.a(new aj(0));
            } else {
                pc.a(new ei(String.valueOf(arg) + "\u4e0d\u5728\u7dda\u4e0a\u3002"));
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 " + cmdName + " \u73a9\u5bb6\u540d\u7a31\u3002"));
        }
    }
}

