/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.ay;
import al.l;
import ap.u;
import aq.ae;
import aq.aq;
import aq.f;
import be.ds;
import be.ei;
import bf.a;
import bi.g;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class e
implements l {
    private static final Logger a = Logger.getLogger(e.class.getName());

    private e() {
    }

    public static l a() {
        return new e();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            if (!st.hasMoreTokens()) {
                pc.a(new ei("\u8acb\u8f38\u5165 .allBuff \u73a9\u5bb6\u540d\u7a31\u3002"));
                return;
            }
            String name = st.nextToken();
            u target = aq.a().a(name);
            if (target == null) {
                pc.a(new ds(73, name));
                return;
            }
            int skillid = 1;
            while (skillid <= 609) {
                a executor;
                if (skillid != 120 && skillid != 78 && skillid != 31 && (executor = g.a(skillid)) != null) {
                    executor.a((f)target, 0);
                }
                ++skillid;
            }
            new ay().a(pc);
            ae.a(target, 5641, 7200, 2);
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 .allBuff \u73a9\u5bb6\u540d\u7a31\u3002"));
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }
}

