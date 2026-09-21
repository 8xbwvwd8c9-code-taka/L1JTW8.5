/*
 * Decompiled with CFR 0.152.
 */
package al;

import ai.c;
import al.l;
import ap.u;
import be.ei;

public class aw
implements l {
    private aw() {
    }

    public static l a() {
        return new aw();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            if (arg.equalsIgnoreCase("now")) {
                c.a().a(false);
                return;
            }
            if (arg.equalsIgnoreCase("abort")) {
                c.a().d();
                return;
            }
            int sec = Math.max(5, Integer.parseInt(arg));
            c.a().a(sec, false);
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165: .shutdown sec|now|abort \u3002"));
        }
    }
}

