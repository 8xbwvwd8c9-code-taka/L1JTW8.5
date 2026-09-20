/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import be.bi;
import be.ch;
import be.ei;

public class be
implements l {
    private be() {
    }

    public static l a() {
        return new be();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            pc.b(false);
            pc.a(new bi(pc.fr(), 0));
            pc.b(new ch(pc));
            pc.a(new ei("\u96b1\u5f62\u72c0\u614b\u89e3\u9664\u3002"));
        }
        catch (Exception e2) {
            pc.a(new ei(String.valueOf(cmdName) + " \u73a9\u5bb6\u540d\u7a31"));
        }
    }
}

