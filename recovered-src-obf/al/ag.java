/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.w;
import ap.u;
import be.ei;
import bi.f;
import java.util.StringTokenizer;

public class ag
implements l {
    private ag() {
    }

    public static l a() {
        return new ag();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            int level = Integer.parseInt(tok.nextToken());
            if (level == pc.ev()) {
                return;
            }
            if (!f.a(level, 1, 99)) {
                pc.a(new ei("\u8acb\u57281-99\u7bc4\u570d\u5167\u6307\u5b9a"));
                return;
            }
            pc.k(w.a(level - 1) + 1);
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 : " + cmdName + " lv "));
        }
    }
}

