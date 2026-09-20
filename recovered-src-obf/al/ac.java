/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.ah;
import ap.q;
import ap.u;
import be.ei;
import java.util.StringTokenizer;

public class ac
implements l {
    private ac() {
    }

    public static l a() {
        return new ac();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int gfxid = Integer.parseInt(st.nextToken(), 10);
            int count = Integer.parseInt(st.nextToken(), 10);
            int i2 = 0;
            while (i2 < count) {
                q item = ah.a().b(40005);
                item.a().e(gfxid + i2);
                item.a().a(String.valueOf(gfxid + i2));
                pc.j().d(item);
                ++i2;
            }
        }
        catch (Exception exception) {
            pc.a(new ei(String.valueOf(cmdName) + " \u8acb\u8f38\u5165 id \u51fa\u73fe\u7684\u6578\u91cf\u3002"));
        }
    }
}

