/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.cm;
import be.ds;
import be.ei;
import bi.e;

public class bf
implements l {
    private bf() {
    }

    public static l a() {
        return new bf();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            pc.a(new cm(45));
            for (u each : aq.a().c()) {
                if (each.aK() == null || each.aK().e() == null) {
                    pc.a(new ei(String.valueOf(each.et()) + "\t<-"));
                    continue;
                }
                pc.a(new ei(String.valueOf(each.et()) + "\t-" + each.aK().e().i()));
            }
            pc.a(new ei(e.a().b()));
            pc.a(new ds(81, "" + aq.a().c().size()));
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165: .who \u3002"));
        }
    }
}

