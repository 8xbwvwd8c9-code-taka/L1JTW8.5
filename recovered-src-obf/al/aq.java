/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.am;
import be.ei;
import java.util.ArrayList;
import java.util.Collection;

public class aq
implements l {
    private aq() {
    }

    public static l a() {
        return new aq();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            Collection<u> targets = null;
            if (arg.equalsIgnoreCase("all")) {
                targets = aq.aq.a().c();
            } else {
                targets = new ArrayList<u>();
                u tg = aq.aq.a().a(arg);
                if (tg == null) {
                    pc.a(new ei("ID\u4e0d\u5b58\u5728\u3002"));
                    return;
                }
                targets.add(tg);
            }
            boolean c2 = false;
            for (u target : targets) {
                if (target.fr() == pc.fr()) continue;
                am.a(target, pc, 10);
                pc.a(new ei(target.et() + "\u6210\u529f\u88ab\u60a8\u53ec\u559a\u56de\u4f86\u3002"));
                target.a(new ei("\u60a8\u88ab\u53ec\u559a\u5230GM\u8eab\u908a\u3002"));
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165: " + cmdName + " all|\u73a9\u5bb6\u540d\u7a31\u3002"));
        }
    }
}

