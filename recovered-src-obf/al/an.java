/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.ae;
import aq.aq;
import be.ds;
import be.ei;
import java.util.StringTokenizer;

public class an
implements l {
    private an() {
    }

    public static l a() {
        return new an();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String name = st.nextToken();
            int polyid = Integer.parseInt(st.nextToken());
            u tg = aq.a().a(name);
            if (tg == null) {
                pc.a(new ds(73, name));
            } else {
                try {
                    ae.a(tg, polyid, 7200, 2);
                }
                catch (Exception exception) {
                    pc.a(new ei("\u8acb\u8f38\u5165 .poly \u73a9\u5bb6\u540d\u7a31 \u8b8a\u8eab\u4ee3\u78bc\u3002"));
                }
            }
        }
        catch (Exception e2) {
            pc.a(new ei(String.valueOf(cmdName) + " \u8acb\u8f38\u5165  \u73a9\u5bb6\u540d\u7a31 \u8b8a\u8eab\u4ee3\u78bc\u3002"));
        }
    }
}

