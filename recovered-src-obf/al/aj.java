/*
 * Decompiled with CFR 0.152.
 */
package al;

import al.l;
import ap.u;
import aq.am;
import ax.b;
import ax.d;
import be.ei;
import java.util.StringTokenizer;

public class aj
implements l {
    private aj() {
    }

    public static l a() {
        return new aj();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            StringTokenizer st = new StringTokenizer(arg);
            int mapid = Integer.parseInt(st.nextToken());
            b l1map = d.b().a(mapid);
            if (l1map == null) {
                pc.a(new ei("\u5730\u5716: " + mapid + " \u627e\u4e0d\u5230\u3002"));
                return;
            }
            b map = l1map;
            int x_center = (map.b + map.d) / 2;
            int y_center = (map.c + map.e) / 2;
            if (!map.c(x_center, y_center)) {
                int i2 = map.b;
                while (i2 < map.d - 1) {
                    int j2 = map.c;
                    while (j2 < map.e - 1) {
                        if (map.c(i2, j2)) {
                            am.a(pc, i2, j2, mapid, 5, true);
                            pc.a(new ei("\u5ea7\u6a19 " + i2 + ", " + j2 + ", " + mapid + "\u5df2\u7d93\u5230\u9054\u3002"));
                            return;
                        }
                        j2 += 2;
                    }
                    i2 += 2;
                }
                pc.a(new ei(String.valueOf(cmdName) + " \u627e\u4e0d\u5230\u53ef\u79fb\u52d5\u7684\u5ea7\u6a19\u3002"));
                return;
            }
            am.a(pc, x_center, y_center, mapid, 5, true);
            pc.a(new ei("\u5ea7\u6a19 " + x_center + ", " + y_center + ", " + mapid + "\u5df2\u7d93\u5230\u9054\u3002"));
        }
        catch (Exception e2) {
            pc.a(new ei(String.valueOf(cmdName) + "\u8acb\u8f38\u5165 [\u5730\u5716\u7de8\u865f]\u3002"));
        }
    }
}

