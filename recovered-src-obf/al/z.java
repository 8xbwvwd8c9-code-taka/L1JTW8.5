/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import ba.h;
import be.ei;
import java.util.StringTokenizer;

public class z
implements l {
    private z() {
    }

    public static l a() {
        return new z();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        block4: {
            try {
                StringTokenizer st = new StringTokenizer(arg);
                String para1 = st.nextToken();
                if (para1.equalsIgnoreCase("daily")) {
                    h.a().b();
                    break block4;
                }
                if (para1.equalsIgnoreCase("monthly")) {
                    h.a().c();
                    break block4;
                }
                throw new Exception();
            }
            catch (Exception e2) {
                pc.a(new ei("\u8acb\u8f38\u5165 .hometown daily|monthly \u3002"));
            }
        }
    }
}

