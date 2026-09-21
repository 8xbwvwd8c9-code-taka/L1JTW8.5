/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import be.bi;
import be.dh;
import be.ei;

public class ad
implements l {
    private ad() {
    }

    public static l a() {
        return new ad();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            pc.b(true);
            pc.a(new bi(pc.fr(), 1));
            pc.b(new dh(pc));
            pc.a(new ei("\u73fe\u5728\u662f\u96b1\u8eab\u72c0\u614b\u3002"));
        }
        catch (Exception e2) {
            pc.a(new ei(String.valueOf(cmdName) + " \u6307\u4ee4\u932f\u8aa4"));
        }
    }
}

