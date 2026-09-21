/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.ab;
import ap.u;
import aq.aa;
import be.dh;
import be.ei;

public class av
implements l {
    private av() {
    }

    public static l a() {
        return new av();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        if (arg.equalsIgnoreCase("on")) {
            pc.j(26002, 0);
        } else if (arg.equalsIgnoreCase("off")) {
            pc.bz(26002);
            for (aa obj : pc.eq()) {
                if (!(obj instanceof ab)) continue;
                pc.d(obj);
                pc.a(new dh(obj));
            }
        } else {
            pc.a(new ei("\u8acb\u8f38\u5165: " + cmdName + " on|off \u3002"));
        }
    }
}

