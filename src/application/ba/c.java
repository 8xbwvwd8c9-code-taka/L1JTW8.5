/*
 * Decompiled with CFR 0.152.
 */
package ba;

import bi.e;
import bj.d;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class c {
    private static final Logger a = Logger.getLogger(c.class.getName());
    private static c b;

    public static c a() {
        if (b == null) {
            b = new c();
        }
        return b;
    }

    private c() {
        int period = 1000;
        e.a().a(new a(), 1000L, 1000L);
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                long now = System.currentTimeMillis();
                for (d client : ai.c.a().c()) {
                    if (client.b == 0L || now - client.b <= 180000L) continue;
                    client.a(230);
                    ai.c.a().b(client);
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

