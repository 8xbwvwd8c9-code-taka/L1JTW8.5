/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.am;
import be.ei;
import java.util.StringTokenizer;

public class ak
implements l {
    private ak() {
    }

    public static l a() {
        return new ak();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int locx = Integer.parseInt(st.nextToken());
            int locy = Integer.parseInt(st.nextToken());
            int mapid = st.hasMoreTokens() ? (int)Short.parseShort(st.nextToken()) : pc.fp();
            am.a(pc, locx, locy, mapid, 5, true);
            pc.a(new ei("\u5ea7\u6a19 " + locx + ", " + locy + ", " + mapid + "\u5df2\u7d93\u5230\u9054\u3002"));
        }
        catch (Exception e2) {
            pc.a(new ei(String.valueOf(cmdName) + "\u8acb\u8f38\u5165 X\u5ea7\u6a19 Y\u5ea7\u6a19 [\u5730\u5716\u7de8\u865f]\u3002"));
        }
    }
}

