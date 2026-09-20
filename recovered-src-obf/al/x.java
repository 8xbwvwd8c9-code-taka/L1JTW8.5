/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.ah;
import ap.q;
import ap.u;
import be.dj;
import be.ei;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class x
implements l {
    private x() {
    }

    public static l a() {
        return new x();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int gfxid = Integer.parseInt(st.nextToken(), 10);
            int count = 250;
            ArrayList<q> itemList = new ArrayList<q>();
            int i2 = 0;
            while (i2 < count) {
                q item = ah.a().b(40005);
                item.e(gfxid + i2);
                item.f(1);
                itemList.add(item);
                ++i2;
            }
            pc.a(new dj(itemList));
        }
        catch (Exception exception) {
            pc.a(new ei(String.valueOf(cmdName) + " \u8acb\u8f38\u5165 id \u51fa\u73fe\u7684\u6578\u91cf\u3002"));
        }
    }
}

