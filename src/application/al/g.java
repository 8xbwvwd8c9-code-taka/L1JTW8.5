/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.aq;
import aq.f;
import be.ei;
import bf.a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

public class g
implements l {
    private g() {
    }

    public static l a() {
        return new g();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer tok = new StringTokenizer(arg);
            Collection<Object> players = null;
            String s2 = tok.nextToken();
            if (s2.equalsIgnoreCase("me")) {
                players = new ArrayList<u>();
                players.add(pc);
                s2 = tok.nextToken();
            } else if (s2.equalsIgnoreCase("all")) {
                players = aq.a().c();
                s2 = tok.nextToken();
            } else {
                players = aq.a().f(pc);
            }
            int skillId = Integer.parseInt(s2);
            int time = 0;
            if (tok.hasMoreTokens()) {
                time = Integer.parseInt(tok.nextToken());
            }
            a executor = bi.g.a(skillId);
            for (u u2 : players) {
                executor.a((f)u2, time);
            }
        }
        catch (Exception e2) {
            pc.a(new ei("\u8acb\u8f38\u5165 " + cmdName + " [all|me] skillId time\u3002"));
        }
    }
}

