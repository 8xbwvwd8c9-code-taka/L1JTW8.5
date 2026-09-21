/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.ag;
import ap.u;
import aq.aq;
import be.ei;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class f
implements l {
    private static final Logger a = Logger.getLogger(f.class.getName());

    private f() {
    }

    public static l a() {
        return new f();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            String msg;
            StringTokenizer stringtokenizer = new StringTokenizer(arg);
            String s1 = stringtokenizer.nextToken();
            String s2 = null;
            try {
                s2 = stringtokenizer.nextToken();
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            ag iptable = ag.a();
            boolean isBanned = iptable.b(s1);
            for (u tg : aq.a().c()) {
                if (!s1.equals(tg.aK().g())) continue;
                String msg2 = "IP:" + s1 + " \u9023\u7dda\u4e2d\u7684\u89d2\u8272\u540d\u7a31:" + tg.et();
                pc.a(new ei(msg2));
            }
            if ("add".equalsIgnoreCase(s2) && !isBanned) {
                iptable.a(s1);
                msg = "IP:" + s1 + " \u88ab\u65b0\u589e\u5230\u5c01\u9396\u540d\u55ae\u3002";
                pc.a(new ei(msg));
            } else if ("del".equalsIgnoreCase(s2) && isBanned) {
                if (iptable.c(s1)) {
                    msg = "IP:" + s1 + " \u5df2\u5f9e\u5c01\u9396\u540d\u55ae\u4e2d\u522a\u9664\u3002";
                    pc.a(new ei(msg));
                }
            } else if (isBanned) {
                msg = "IP:" + s1 + " \u5df2\u88ab\u767b\u8a18\u5728\u5c01\u9396\u540d\u55ae\u4e2d\u3002";
                pc.a(new ei(msg));
            } else {
                msg = "IP:" + s1 + " \u5c1a\u672a\u88ab\u767b\u8a18\u5728\u5c01\u9396\u540d\u55ae\u4e2d\u3002";
                pc.a(new ei(msg));
            }
        }
        catch (Exception e3) {
            pc.a(new ei("\u8acb\u8f38\u5165 " + cmdName + " IP [ add | del ]\u3002"));
        }
    }
}

