/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import auto.hunt.AutoHuntService;
import be.cm;
import bj.d;

public class bv
extends cv {
    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public bv(byte[] decrypt, d client) {
        super(decrypt);
        client.a(new cm(42));
        u pc = client.f();
        if (pc != null) {
            u u2 = pc;
            synchronized (u2) {
                AutoHuntService.get().stop(pc);
                pc.p();
                client.a((u)null);
            }
        }
    }

    @Override
    public String a() {
        return "C_Restart";
    }
}
