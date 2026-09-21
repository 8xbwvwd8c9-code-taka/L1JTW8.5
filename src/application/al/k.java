/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.cm;
import be.ds;
import be.ei;
import java.util.StringTokenizer;

public class k
implements l {
    private k() {
    }

    public static l a() {
        return new k();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String name = st.nextToken();
            int time = Integer.parseInt(st.nextToken());
            u tg = aq.a().a(name);
            if (tg != null) {
                tg.j(1005, time * 60 * 1000);
                tg.a(new cm(36, time * 60));
                tg.a(new ds(286, String.valueOf(time)));
                pc.a(new ds(287, name));
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 " + cmdName + " \u73a9\u5bb6\u540d\u7a31 \u6642\u9593(\u5206)\u3002"));
        }
    }
}

