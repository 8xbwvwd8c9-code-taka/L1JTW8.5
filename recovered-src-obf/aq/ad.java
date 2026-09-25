/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.n;
import ap.u;
import aq.aa;
import aq.ap;
import aq.aq;
import aq.f;
import as.b;
import be.cv;
import bi.e;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ad {
    private static final Logger a = Logger.getLogger(ad.class.getName());

    private ad() {
    }

    public static void a(u pc, f cha) {
        ap war;
        if (pc == null || cha == null) {
            return;
        }
        u attacker = (u)cha;
        if (pc.fr() == attacker.fr()) {
            return;
        }
        if (attacker.cp() == pc.fr()) {
            return;
        }
        boolean isNowWar = b.a().a((f)pc);
        if (pc.aF() > 0 && attacker.aF() > 0 && (war = aq.a().c(pc.aG())) != null && war.c(pc.aG(), attacker.aG())) {
            isNowWar = true;
        }
        if (!(pc.fa() < 0 || pc.aT() || attacker.fa() < 0 || attacker.aT() || pc.ep() != 0 || attacker.ep() != 0 || isNowWar)) {
            attacker.f(true);
            attacker.a(new cv(attacker.fr(), 180));
            if (!attacker.aA()) {
                attacker.b(new cv(attacker.fr(), 180));
            }
            e.a().b(new a(attacker));
        }
        if (attacker.aT()) {
            for (aa object : aq.a().e(attacker)) {
                if (!(object instanceof n)) continue;
                n guard = (n)object;
                guard.d(attacker);
            }
        }
    }

    private static class a
    implements Runnable {
        private u a = null;

        private a(u attacker) {
            this.a = attacker;
        }

        @Override
        public void run() {
            int i2 = 0;
            while (i2 < 180) {
                block4: {
                    try {
                        Thread.sleep(1000L);
                        if (this.a.eX()) break;
                        if (this.a.fa() < 0) {
                            this.a.f(false);
                        }
                        break block4;
                    }
                    catch (Exception e2) {
                        a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    }
                    break;
                }
                ++i2;
            }
            this.a(this.a);
        }

        private void a(u attacker) {
            attacker.a(new cv(attacker.fr(), 0));
            attacker.b(new cv(attacker.fr(), 0));
            attacker.f(false);
        }
    }
}

