/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.dh;
import be.ei;

public class t
implements l {
    private t() {
    }

    public static l a() {
        return new t();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        if (arg.equalsIgnoreCase("on")) {
            pc.j(26003, 0);
            pc.es();
            pc.h();
        } else if (arg.equalsIgnoreCase("off")) {
            pc.bz(26003);
            for (u visible : aq.a().f(pc)) {
                if (!visible.ff()) continue;
                pc.a(new dh(visible));
            }
        } else {
            pc.a(new ei(String.valueOf(cmdName) + "\u8acb\u8f38\u5165  on|off \u3002"));
        }
    }
}

