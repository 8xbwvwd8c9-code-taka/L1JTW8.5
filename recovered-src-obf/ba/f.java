/*
 * Decompiled with CFR 0.152.
 */
package ba;

import ao.aj;
import ap.i;
import aq.aa;
import aq.aq;
import at.c;
import java.util.logging.Level;
import java.util.logging.Logger;

public class f
implements Runnable {
    private static final Logger a = Logger.getLogger(f.class.getName());
    private static f b;
    private boolean c = false;

    public static f a() {
        if (b == null) {
            b = new f();
        }
        return b;
    }

    @Override
    public void run() {
        try {
            while (true) {
                this.b();
                Thread.sleep(60000L);
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return;
        }
    }

    private void b() {
        int serverTime = at.c.a().b().c();
        int nowTime = serverTime % 86400;
        if (nowTime >= 21300 && nowTime < 64500) {
            if (this.c) {
                this.c = false;
                for (aa object : aq.a().b()) {
                    i npc;
                    if (!(object instanceof i) || (npc = (i)object).U_().b() != 81177 && npc.U_().b() != 81178 && npc.U_().b() != 81179 && npc.U_().b() != 81180 && npc.U_().b() != 81181 || npc.fp() != 0 && npc.fp() != 4) continue;
                    npc.aa_();
                }
            }
        } else if ((nowTime >= 64500 && nowTime <= 86400 || nowTime >= 0 && nowTime < 21300) && !this.c) {
            this.c = true;
            aj.a();
        }
    }
}

