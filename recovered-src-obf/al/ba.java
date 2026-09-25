/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.au;
import ap.u;
import ap.z;
import be.ei;
import java.util.StringTokenizer;

public class ba
implements l {
    private ba() {
    }

    public static ba a() {
        return new ba();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            int npcid;
            String nameid;
            StringTokenizer tok;
            block6: {
                tok = new StringTokenizer(arg);
                nameid = tok.nextToken();
                npcid = 0;
                try {
                    npcid = Integer.parseInt(nameid);
                }
                catch (NumberFormatException e2) {
                    npcid = au.a().a(nameid);
                    if (npcid != 0) break block6;
                    pc.a(new ei("\u627e\u4e0d\u5230\u7b26\u5408\u689d\u4ef6\u7684NPC\u3002"));
                    return;
                }
            }
            int count = 1;
            if (tok.hasMoreTokens()) {
                count = Integer.parseInt(tok.nextToken());
            }
            bh.l npc = au.a().a(npcid);
            int i2 = 0;
            while (i2 < count) {
                z summonInst = new z(npc, pc);
                summonInst.o(0);
                ++i2;
            }
            nameid = au.a().a(npcid).c();
            pc.a(new ei(String.valueOf(nameid) + "(ID:" + npcid + ") (" + count + ") \u53ec\u559a\u4e86\u3002"));
        }
        catch (Exception e3) {
            pc.a(new ei("\u8acb\u8f38\u5165" + cmdName + " npcid|name [\u6578\u91cf] \u3002"));
        }
    }
}

