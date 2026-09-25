/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.ei;
import java.util.StringTokenizer;

public class j
implements l {
    private j() {
    }

    public static l a() {
        return new j();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            if (st.hasMoreTokens()) {
                String msg;
                String flag = st.nextToken();
                if (flag.compareToIgnoreCase("on") == 0) {
                    aq.a().a(true);
                    msg = "\u958b\u555f\u5168\u9ad4\u804a\u5929\u3002";
                } else if (flag.compareToIgnoreCase("off") == 0) {
                    aq.a().a(false);
                    msg = "\u95dc\u9589\u5168\u9ad4\u804a\u5929\u3002";
                } else {
                    throw new Exception();
                }
                pc.a(new ei(msg));
            } else {
                String msg = aq.a().k() ? "\u5168\u9ad4\u804a\u5929\u5df2\u958b\u555f\u3002.chat off \u80fd\u4f7f\u5176\u95dc\u9589\u3002" : "\u5168\u9ad4\u804a\u5929\u5df2\u95dc\u9589\u3002.chat on \u80fd\u4f7f\u5176\u958b\u555f\u3002";
                pc.a(new ei(msg));
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 " + cmdName + " [on|off]"));
        }
    }
}

