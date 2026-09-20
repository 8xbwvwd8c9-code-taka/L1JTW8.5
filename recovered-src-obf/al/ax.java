/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.au;
import ao.bg;
import ap.u;
import be.ei;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class ax
implements l {
    private ax() {
    }

    public static l a() {
        return new ax();
    }

    private void a(u pc, String cmdName) {
        String errorMsg = "\u8acb\u8f38\u5165: " + cmdName + " npcid|name [\u6578\u91cf] [\u7bc4\u570d] \u3002";
        pc.a(new ei(errorMsg));
    }

    private int a(String nameId) {
        int npcid = 0;
        try {
            npcid = Integer.parseInt(nameId);
        }
        catch (NumberFormatException e2) {
            npcid = au.a().a(nameId);
        }
        return npcid;
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            String nameId = tok.nextToken();
            int count = 1;
            if (tok.hasMoreTokens()) {
                count = Integer.parseInt(tok.nextToken());
            }
            int randomrange = 0;
            if (tok.hasMoreTokens()) {
                randomrange = Integer.parseInt(tok.nextToken(), 10);
            }
            int npcid = this.a(nameId);
            bh.l npc = au.a().a(npcid);
            if (npc == null) {
                pc.a(new ei("\u627e\u4e0d\u5230\u7b26\u5408\u689d\u4ef6\u7684NPC\u3002"));
                return;
            }
            int i2 = 0;
            while (i2 < count) {
                bg.a(npcid, pc, randomrange, 0L);
                ++i2;
            }
            String msg = String.format("%s(%d) (%d) \u53ec\u559a\u4e86\u3002 (\u7bc4\u570d:%d)", npc.c(), npcid, count, randomrange);
            pc.a(new ei(msg));
        }
        catch (NoSuchElementException e2) {
            this.a(pc, cmdName);
        }
        catch (NumberFormatException e3) {
            this.a(pc, cmdName);
        }
        catch (Exception e4) {
            pc.a(new ei(String.valueOf(cmdName) + " \u5185\u90e8\u932f\u8aa4\u3002"));
        }
    }
}

