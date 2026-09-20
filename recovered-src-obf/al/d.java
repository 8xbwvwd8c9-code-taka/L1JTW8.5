/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.ah;
import ap.u;
import be.ei;
import java.util.StringTokenizer;

public class d
implements l {
    private d() {
    }

    public static l a() {
        return new d();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer stringtokenizer = new StringTokenizer(arg);
            int count = Integer.parseInt(stringtokenizer.nextToken());
            ah.a(pc, 40308, count);
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 .adena \u6578\u91cf||.\u91d1\u5e63  \u6578\u91cf\u3002"));
        }
    }
}

