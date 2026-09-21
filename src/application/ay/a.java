/*
 * Decompiled with CFR 0.152.
 */
package ay;

import aj.d;
import ap.u;
import aq.aa;
import aq.ak;
import aq.aq;
import ay.f;

public class a
extends f {
    public a(int oId) {
        super(oId);
    }

    @Override
    public void a(u pc) {
        try {
            while (pc.cK() != 0) {
                aa obj = aq.a().a(pc.cK());
                if (!(obj instanceof aq.f)) {
                    return;
                }
                aq.f target = (aq.f)aq.a().a(pc.cK());
                if (target == null || pc.eX()) {
                    return;
                }
                bi.a os = new bi.a();
                os.c(111);
                os.a(pc.cK());
                os.b(target.fs());
                os.b(target.ft());
                new d(os.b(), pc.aK());
                os.close();
                int interval = pc.ce().b(ak.a.b);
                Thread.sleep(interval);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

