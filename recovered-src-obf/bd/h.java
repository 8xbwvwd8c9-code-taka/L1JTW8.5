/*
 * Decompiled with CFR 0.152.
 */
package bd;

import ap.u;
import aq.aa;
import aq.am;
import bd.i;
import bd.j;

public class h
extends i {
    private final aq.u a;

    public h(j storage) {
        super(storage);
        int x2 = storage.b("teleportX");
        int y2 = storage.b("teleportY");
        int mapId = storage.b("teleportMapId");
        this.a = new aq.u(x2, y2, mapId);
    }

    public h(int id, int gfxid, aq.u teleLoc) {
        super(id, gfxid, false);
        this.a = teleLoc;
    }

    @Override
    public void a(u trodFrom, aa trapObj) {
        this.a(trapObj);
        if (this.a.f() == 0 || this.a.g() == 0) {
            am.a(trodFrom, 50);
            return;
        }
        am.a(trodFrom, this.a.f(), this.a.g(), this.a.b(), 5, true, false);
    }
}

