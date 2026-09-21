/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import be.ei;

public class r
implements l {
    private r() {
    }

    public static l a() {
        return new r();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        pc.a(new ei(arg));
    }
}

