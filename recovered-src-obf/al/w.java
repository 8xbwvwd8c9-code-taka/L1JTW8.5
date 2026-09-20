/*
 * Decompiled with CFR 0.152.
 */
package al;

import ai.d;
import al.l;
import ao.au;
import ap.t;
import ap.u;
import aq.aq;
import be.ei;
import bi.g;
import java.util.StringTokenizer;

public class w
implements l {
    private w() {
    }

    public static l a() {
        return new w();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int gfxid = Integer.parseInt(st.nextToken(), 10);
            int count = Integer.parseInt(st.nextToken(), 10);
            int i2 = 0;
            while (i2 < count) {
                bh.l l1npc = au.a().a(45001);
                if (l1npc != null) {
                    t npc = g.a(l1npc);
                    npc.cF(d.a().c());
                    npc.cw(gfxid + i2);
                    npc.a("" + (gfxid + i2));
                    npc.cE(pc.fp());
                    npc.cG(pc.fs() + i2);
                    npc.cH(pc.ft() + i2);
                    npc.q(npc.fs());
                    npc.r(npc.ft());
                    npc.ct(4);
                    aq.a().a(npc);
                    aq.a().c(npc);
                }
                ++i2;
            }
        }
        catch (Exception exception) {
            pc.a(new ei(String.valueOf(cmdName) + " \u8acb\u8f38\u5165  \u52d5\u756b\u7de8\u865f  \u52d5\u756b\u6578\u91cf  \u4eba\u7269ID\u3002"));
        }
    }
}

