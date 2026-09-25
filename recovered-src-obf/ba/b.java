/*
 * Decompiled with CFR 0.152.
 */
package ba;

import ap.q;
import ap.u;
import aq.aa;
import aq.aq;
import aq.r;
import au.e;
import be.ei;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class b {
    private static final Logger a = Logger.getLogger(b.class.getName());
    private static b b;

    public static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    private b() {
        int period = l1j.server.a.aa * 60 * 1000 - 10000;
        bi.e.a().a(new a(), period, period);
    }

    private void c() {
        int numOfDeleted = 0;
        for (aa obj : aq.a().b()) {
            List<u> players;
            q item;
            if (!(obj instanceof q) || (item = (q)obj).fs() == 0 && item.ft() == 0 || item.N() == 40515 || r.a(item.fs(), item.ft(), item.fp()) || item.fp() >= 1400 && item.fp() <= 1498 || item.fp() >= 16384 || !(players = aq.a().c(item, l1j.server.a.ab)).isEmpty()) continue;
            e groundInventory = aq.a().a(item.fs(), item.ft(), item.fp());
            groundInventory.f(item);
            ++numOfDeleted;
        }
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                aq.a().a(new ei("\u5730\u4e0a\u7684\u7269\u54c1\uff0c10\u79d2\u5f8c\u5c07\u88ab\u6e05\u9664\u3002"));
                Thread.sleep(10000L);
                b.this.c();
                aq.a().a(new ei("\u5730\u4e0a\u7684\u7269\u54c1\uff0c\u5df2\u7d93\u88ab\u6e05\u9664\u4e86\u3002"));
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

