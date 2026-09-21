/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.am;
import bj.d;

public class ax
extends cv {
    public ax(byte[] abyte0, d client) {
        super(abyte0);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int shipMapId = this.d();
        if (shipMapId == 65535) {
            am.a(pc);
            return;
        }
        int locX = this.d();
        int locY = this.d();
        int mapId = pc.fp();
        int ticketId = 0;
        if (mapId == 5) {
            ticketId = 40299;
        } else if (mapId == 6) {
            ticketId = 40298;
        } else if (mapId == 83) {
            ticketId = 40300;
        } else if (mapId == 84) {
            ticketId = 40301;
        } else if (mapId == 446) {
            ticketId = 40303;
        } else if (mapId == 447) {
            ticketId = 40302;
        } else {
            return;
        }
        if (!pc.j().b(ticketId, 1)) {
            return;
        }
        am.a(pc, locX, locY, shipMapId, 0, true);
    }

    @Override
    public String a() {
        return "C_Ship";
    }
}

