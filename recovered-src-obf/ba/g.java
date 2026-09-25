/*
 * Decompiled with CFR 0.152.
 */
package ba;

import ap.u;
import aq.am;
import aq.an;
import aq.aq;
import be.dg;
import bi.e;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class g {
    private static final Logger a = Logger.getLogger(g.class.getName());
    private static g b;

    public static g a() {
        if (b == null) {
            b = new g();
        }
        return b;
    }

    private g() {
        e.a().a(new a(), 60000L, 60000L);
    }

    public void a(u pc, boolean isFirst) {
        if (pc.fp() != 666) {
            int locx = 32701;
            int locy = 32777;
            int mapid = 666;
            am.a(pc, 32701, 32777, 666, 5, true);
        }
        if (isFirst) {
            if (pc.aD() <= 10) {
                pc.aG(300);
            } else {
                pc.aG(300 * (pc.aD() - 10) + 300);
            }
            pc.a(new dg(552, String.valueOf(pc.aD()), String.valueOf(pc.bI() / 60)));
        } else {
            pc.a(new dg(637, String.valueOf(pc.bI())));
        }
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                for (u pc : aq.a().c()) {
                    if (pc == null || pc.bE() == 0 || pc.eX() || pc.bI() <= 0) continue;
                    pc.aG(pc.bI() - 60);
                    if (pc.bI() > 0) continue;
                    int[] loc = an.a(4);
                    am.a(pc, loc[0], loc[1], loc[2], 5, true);
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

