/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.aj;
import be.ei;

public class ae
implements l {
    private ae() {
    }

    public static l a() {
        return new ae();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            u target = aq.a().a(arg);
            if (target != null) {
                pc.a(new ei(target.et() + "\u5df2\u88ab\u60a8\u5f37\u5236\u8e22\u9664\u904a\u6232\u3002"));
                target.a(new aj(0));
            } else {
                pc.a(new ei("\u60a8\u6307\u5b9a\u7684\u8a72\u73a9\u5bb6\u540d\u7a31\u4e0d\u5b58\u5728\u3002"));
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 : " + cmdName + " \u73a9\u5bb6\u540d\u7a31\u3002"));
        }
    }
}

