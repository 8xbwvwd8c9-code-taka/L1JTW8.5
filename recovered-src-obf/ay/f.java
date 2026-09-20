/*
 * Decompiled with CFR 0.152.
 */
package ay;

import ap.u;
import aq.aq;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class f
implements Runnable {
    private static final Logger a = Logger.getLogger(f.class.getName());
    private final int b;

    public f(int oId) {
        this.b = oId;
    }

    @Override
    public final void run() {
        u pc = (u)aq.a().a(this.b);
        if (pc == null || pc.aK() == null) {
            return;
        }
        try {
            this.a(pc);
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public abstract void a(u var1);
}

