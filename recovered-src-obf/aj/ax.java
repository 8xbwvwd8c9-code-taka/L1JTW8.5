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
        if (mapId == 5) {
            pc.j().b(40299, 1);
        } else if (mapId == 6) {
            pc.j().b(40298, 1);
        } else if (mapId == 83) {
            pc.j().b(40300, 1);
        } else if (mapId == 84) {
            pc.j().b(40301, 1);
        } else if (mapId == 446) {
            pc.j().b(40303, 1);
        } else if (mapId == 447) {
            pc.j().b(40302, 1);
        }
        am.a(pc, locX, locY, shipMapId, 0, true);
    }

    @Override
    public String a() {
        return "C_Ship";
    }
}

