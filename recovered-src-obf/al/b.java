/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import be.ak;
import be.ei;
import java.util.StringTokenizer;

public class b
implements l {
    private b() {
    }

    public static l a() {
        return new b();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int actId = Integer.parseInt(st.nextToken(), 10);
            pc.a(new ak(pc.fr(), actId));
        }
        catch (Exception exception) {
            pc.a(new ei("\u8acb\u8f38\u5165 " + cmdName + " actid\u3002"));
        }
    }
}

