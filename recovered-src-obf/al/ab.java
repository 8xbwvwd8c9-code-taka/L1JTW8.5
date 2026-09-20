/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ao.at;
import ao.au;
import ao.bg;
import ap.u;
import be.ei;
import java.util.StringTokenizer;

public class ab
implements l {
    private ab() {
    }

    public static l a() {
        return new ab();
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void a(u pc, String cmdName, String arg) {
        block12: {
            block11: {
                msg = null;
                tok = new StringTokenizer(arg);
                type = tok.nextToken();
                npcId = Integer.parseInt(tok.nextToken().trim());
                template = au.a().a(npcId);
                if (template != null) break block11;
                msg = "\u627e\u4e0d\u5230\u7b26\u5408\u689d\u4ef6\u7684NPC\u3002";
                if (msg != null) {
                    pc.a(new ei(msg));
                }
                return;
            }
            if (!type.equalsIgnoreCase("mob")) ** GOTO lbl25
            if (template.d().equals("L1Monster")) break block12;
            msg = "\u6307\u5b9a\u7684NPC\u4e0d\u662fL1Monster\u985e\u578b\u3002";
            if (msg != null) {
                pc.a(new ei(msg));
            }
            return;
        }
        try {
            try {
                block14: {
                    bg.a(pc, template);
                    break block14;
lbl25:
                    // 1 sources

                    if (type.equalsIgnoreCase("npc")) {
                        at.a().a(pc, template);
                    }
                }
                bg.a(npcId, pc, 0, 0L);
                msg = template.c() + (" (" + npcId + ") ") + "\u65b0\u589e\u5230\u8cc7\u6599\u5eab\u4e2d\u3002";
            }
            catch (Exception e) {
                msg = "\u8acb\u8f38\u5165 : " + cmdName + " mob|npc NPCID \u3002";
                if (msg == null) ** GOTO lbl43
                pc.a(new ei(msg));
            }
        }
        catch (Throwable var9_10) {
            if (msg != null) {
                pc.a(new ei(msg));
            }
            throw var9_10;
        }
        if (msg != null) {
            pc.a(new ei(msg));
        }
lbl43:
        // 5 sources

    }
}

