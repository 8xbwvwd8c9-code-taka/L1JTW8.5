/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.ah;
import ap.u;
import au.a;
import be.ei;
import bh.j;
import java.util.StringTokenizer;

public class ap
implements l {
    private ap() {
    }

    public static l a() {
        return new ap();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            String account = st.nextToken();
            int itemid = Integer.parseInt(st.nextToken(), 10);
            int enchant = Integer.parseInt(st.nextToken(), 10);
            int count = Integer.parseInt(st.nextToken(), 10);
            j temp = ah.a().a(itemid);
            if (temp == null) {
                pc.a(new ei("\u4e0d\u5b58\u5728\u7684\u9053\u5177\u7de8\u865f\u3002"));
                return;
            }
            a.a(account, itemid, enchant, count);
            pc.a(new ei(String.valueOf(temp.j()) + "\u6578\u91cf" + count + "\u500b\u767c\u9001\u51fa\u53bb\u4e86\u3002", true));
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 : .present \u5e33\u865f \u9053\u5177\u7de8\u865f  \u5f37\u5316\u7b49\u7d1a \u6578\u91cf\u3002\uff08* \u7b49\u65bc\u6240\u6709\u5e33\u865f\uff09"));
        }
    }
}

