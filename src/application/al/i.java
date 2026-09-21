/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.ei;
import be.es;
import java.util.StringTokenizer;

public class i
implements l {
    private i() {
    }

    public static l a() {
        return new i();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            int weather = Integer.parseInt(tok.nextToken());
            aq.a().c(weather);
            aq.a().a(new es(weather));
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 " + cmdName + " 0\uff5e3\u300116\uff5e19\u3002"));
        }
    }
}

