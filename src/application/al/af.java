/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import aq.f;
import be.ei;

public class af
implements l {
    private af() {
    }

    public static l a() {
        return new af();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            u target = aq.a().a(arg);
            if (target != null) {
                target.a(0);
                target.b((f)null);
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 : " + cmdName + " \u73a9\u5bb6\u540d\u7a31\u3002"));
        }
    }
}

