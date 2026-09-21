/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.ei;
import bj.d;

public class au
implements l {
    private au() {
    }

    public static l a() {
        return new au();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            u target = aq.a().a(arg);
            if (target != null) {
                pc.a(new ei(target.et() + "\u5df2\u88ab\u60a8\u5f37\u5236\u8e22\u9664\u904a\u6232\u3002"));
                target.cG(33080);
                target.cH(33392);
                target.cE(4);
                d targetClient = target.aK();
                target.aK().c();
                System.out.println("GM\u7684\u8e22\u9664\u6307\u4ee4\u4f7f\u5f97(" + targetClient.a() + ":" + targetClient.g() + ")\u7684\u9023\u7dda\u88ab\u5f37\u5236\u4e2d\u65b7\u3002");
            } else {
                pc.a(new ei("\u6307\u5b9a\u7684ID\u4e0d\u5b58\u5728\u3002"));
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165: " + cmdName + " \u73a9\u5bb6\u540d\u7a31\u3002"));
        }
    }
}

