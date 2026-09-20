/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.ac;
import aq.am;
import aq.aq;
import be.ei;
import java.util.logging.Level;
import java.util.logging.Logger;

public class al
implements l {
    private static final Logger a = Logger.getLogger(al.class.getName());

    private al() {
    }

    public static l a() {
        return new al();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        u target = aq.a().a(arg);
        if (target != null) {
            ac party = target.aL();
            if (party != null) {
                int x2 = pc.fs();
                int y2 = pc.ft() + 2;
                int map = pc.fp();
                for (u pc2 : party.c()) {
                    try {
                        am.a(pc2, x2, y2, map, 5, true);
                        pc2.a(new ei("\u60a8\u88ab\u50b3\u559a\u5230GM\u8eab\u908a\u3002"));
                    }
                    catch (Exception e2) {
                        a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    }
                }
            } else {
                pc.a(new ei("\u8acb\u8f38\u5165\u8981\u53ec\u559a\u7684\u89d2\u8272\u540d\u7a31\u3002"));
            }
        } else {
            pc.a(new ei("\u4e0d\u518d\u7dda\u4e0a\u3002"));
        }
    }
}

