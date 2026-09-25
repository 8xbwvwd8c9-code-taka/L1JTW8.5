/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import auto.hunt.AutoHuntService;
import bj.d;

public class ah
extends cv {
    public ah(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc != null) {
            AutoHuntService.get().stop(pc);
        }
        client.c();
    }

    @Override
    public String a() {
        return "C_Disconnect";
    }
}
