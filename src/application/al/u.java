/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import be.ei;

public class u
implements l {
    private u() {
    }

    public static l a() {
        return new u();
    }

    @Override
    public void a(ap.u pc, String cmdName, String arg) {
        pc.ae(pc.l() ? 0 : 200);
        pc.a(new ei("setGm = " + pc.l()));
    }
}

