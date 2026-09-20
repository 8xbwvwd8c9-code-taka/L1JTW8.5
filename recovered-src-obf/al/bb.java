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

public class bb
implements l {
    private static final Logger a = Logger.getLogger(bb.class.getName());

    private bb() {
    }

    public static l a() {
        return new bb();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            int locX = pc.fs();
            int locY = pc.ft();
            int mapId = pc.fp();
            int tile0 = d.b().a(mapId).a(locX, locY - 1);
            int tile1 = d.b().a(mapId).a(locX + 1, locY - 1);
            int tile2 = d.b().a(mapId).a(locX + 1, locY);
            int tile3 = d.b().a(mapId).a(locX + 1, locY + 1);
            int tile4 = d.b().a(mapId).a(locX, locY + 1);
            int tile5 = d.b().a(mapId).a(locX - 1, locY + 1);
            int tile6 = d.b().a(mapId).a(locX - 1, locY);
            int tile7 = d.b().a(mapId).a(locX - 1, locY - 1);
            String msg = String.format("0:%d 1:%d 2:%d 3:%d 4:%d 5:%d 6:%d 7:%d", tile0, tile1, tile2, tile3, tile4, tile5, tile6, tile7);
            pc.a(new ei(msg));
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }
}

