/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import be.ca;
import be.ee;
import be.ei;

public class at
implements l {
    private at() {
    }

    public static l a() {
        return new at();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            int objid = pc.fr();
            pc.a(new ee(objid, 759));
            pc.b(new ee(objid, 759));
            pc.a(pc.ew());
            pc.i_(pc.ex());
            for (u tg : aq.a().f(pc)) {
                if (tg.ea() == 0 && tg.eX()) {
                    tg.a(new ei("GM\u7d66\u4e88\u4e86\u91cd\u751f\u3002"));
                    tg.b(new ee(tg.fr(), 3944));
                    tg.a(new ee(tg.fr(), 3944));
                    tg.am(objid);
                    tg.a(new ca(322, new String[0]));
                    continue;
                }
                tg.a(new ei("GM\u7d66\u4e88\u4e86\u6cbb\u7642\u3002"));
                tg.b(new ee(tg.fr(), 832));
                tg.a(new ee(tg.fr(), 832));
                tg.a(tg.ew());
                tg.i_(tg.ex());
            }
        }
        catch (Exception e2) {
            pc.a(new ei(String.valueOf(cmdName) + " \u6307\u4ee4\u932f\u8aa4"));
        }
    }
}

