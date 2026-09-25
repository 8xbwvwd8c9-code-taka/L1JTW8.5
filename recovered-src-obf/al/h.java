/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import be.ee;
import be.ei;
import java.util.StringTokenizer;

public class h
implements l {
    private h() {
    }

    public static l a() {
        return new h();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer stringtokenizer = new StringTokenizer(arg);
            int sprid = Integer.parseInt(stringtokenizer.nextToken());
            pc.a(new ee(pc.fr(), sprid));
            pc.b(new ee(pc.fr(), sprid));
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 " + cmdName + " castgfxid\u3002"));
        }
    }
}

