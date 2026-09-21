/*
 * Decompiled with CFR 0.152.
 */
package ba;

import ap.q;
import ap.u;
import aq.aq;
import be.ds;
import bi.e;
import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class j {
    private static final Logger a = Logger.getLogger(j.class.getName());
    private final int[] b = new int[]{310, 640699, 640700, 640319, 640320, 640321, 640322, 640323, 640324, 640325, 640354, 640355, 640701, 640820};
    private static j c;

    public static j a() {
        if (c == null) {
            c = new j();
        }
        return c;
    }

    private j() {
        e.a().a(new a(), 30000L, 30000L);
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                for (u pc : aq.a().c()) {
                    if (pc == null || pc.bE() == 0) continue;
                    for (q item : pc.j().d()) {
                        int[] nArray = j.this.b;
                        int n2 = nArray.length;
                        int n3 = 0;
                        while (n3 < n2) {
                            int itemid = nArray[n3];
                            if (item.N() == itemid && (pc.fp() == 4 || pc.fp() >= 100 && pc.fp() <= 111)) {
                                pc.j().f(item);
                            }
                            ++n3;
                        }
                        if (item.bb() == null) continue;
                        Timestamp current = new Timestamp(System.currentTimeMillis());
                        if (!item.bb().before(current)) continue;
                        pc.a(new ds(2535, item.b(), "0"));
                        if (item.N() >= 21246 && item.N() <= 21251 || item.N() >= 21252 && item.N() <= 21257 || item.N() >= 21261 && item.N() <= 21300) continue;
                        pc.j().f(item);
                    }
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

