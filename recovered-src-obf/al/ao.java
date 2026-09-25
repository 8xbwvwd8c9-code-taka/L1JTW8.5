/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.ag;
import ap.u;
import aq.aq;
import be.aj;
import be.ei;

public class ao
implements l {
    private ao() {
    }

    public static l a() {
        return new ao();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            u target = aq.a().a(arg);
            ag iptable = ag.a();
            if (target != null) {
                iptable.a(target.aK().g());
                pc.a(new ei(target.et() + "\u88ab\u60a8\u5f37\u5236\u8e22\u9664\u904a\u6232\u4e26\u5c01\u9396IP\u3002"));
                target.a(new aj(0));
            } else {
                pc.a(new ei("\u60a8\u6307\u5b9a\u7684\u8173\u8272\u540d\u7a31\u4e0d\u5b58\u5728\u3002"));
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 : " + cmdName + " \u73a9\u5bb6\u540d\u7a31\u3002"));
        }
    }
}

