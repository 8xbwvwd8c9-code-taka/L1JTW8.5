/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import be.ei;
import java.util.StringTokenizer;

public class o
implements l {
    private o() {
    }

    public static l a() {
        return new o();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            String name = new StringTokenizer(arg).nextToken();
            pc.a(new ei(String.valueOf(name) + " \u662f\u672a\u5b9a\u7fa9\u7684\u5957\u88dd\u3002"));
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 .itemset \u5957\u88dd\u540d\u7a31\u3002"));
        }
    }
}

