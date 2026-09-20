/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.a;
import ap.r;
import ap.s;
import ap.u;
import ap.v;
import ap.z;
import be.az;
import be.ei;

public class aa
implements l {
    private aa() {
    }

    public static l a() {
        return new aa();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        if (arg.equalsIgnoreCase("on")) {
            pc.j(26001, 0);
        } else if (arg.equalsIgnoreCase("off")) {
            pc.bz(26001);
            for (aq.aa obj : pc.eq()) {
                if (!aa.a(obj)) continue;
                pc.a(new az(obj.fr(), 255, 255));
            }
        } else {
            pc.a(new ei("\u8acb\u8f38\u5165 : " + cmdName + " on|off \u3002"));
        }
    }

    public static boolean a(aq.aa obj) {
        if (obj instanceof s) {
            return true;
        }
        if (obj instanceof u) {
            return true;
        }
        if (obj instanceof z) {
            return true;
        }
        if (obj instanceof v) {
            return true;
        }
        if (obj instanceof ap.aa) {
            return true;
        }
        if (obj instanceof a) {
            return true;
        }
        return obj instanceof r;
    }
}

