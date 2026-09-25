/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import ax.d;
import be.ei;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ai
implements l {
    private static final Logger a = Logger.getLogger(ai.class.getName());

    private ai() {
    }

    public static l a() {
        return new ai();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            int locx = pc.fs();
            int locy = pc.ft();
            int mapid = pc.fp();
            int gab = d.b().a(mapid).a(locx, locy);
            String msg = String.format("\u5ea7\u6a19 (%d, %d, %d) %d", locx, locy, mapid, gab);
            pc.a(new ei(msg));
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }
}

