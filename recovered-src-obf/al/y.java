/*
 * Decompiled with CFR 0.152.
 */
package al;

import ai.d;
import al.l;
import am.c;
import ao.au;
import ap.m;
import ap.t;
import ap.u;
import aq.aa;
import aq.ak;
import aq.am;
import aq.aq;
import be.ei;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class y
implements l {
    private static final Logger a = Logger.getLogger(y.class.getName());

    private y() {
    }

    public static l a() {
        return new y();
    }

    @Override
    public void a(u pc, String cmdName, String arg) {
        try {
            int i2;
            String type = arg;
            int mapid = 4;
            int begin_x = 32000;
            int begin_y = 33553;
            int range = 6;
            for (aa obj : aq.a().b(4).values()) {
                t npc;
                if (!(obj instanceof t) || (npc = (t)obj).z() != 45001) continue;
                aq.a().d(npc);
            }
            ArrayList<int[]> locList = new ArrayList<int[]>();
            int j2 = 33553;
            while (j2 < 34053) {
                int i3 = 32000;
                while (i3 < 32500) {
                    locList.add(new int[]{i3, j2});
                    i3 += 6;
                }
                j2 += 6;
            }
            int count = 0;
            ArrayList<Object> findList = new ArrayList();
            if (type.equals("effect")) {
                findList = c.a().a(true);
            } else if (type.equals("effect2")) {
                findList = c.a().a(false);
            } else if (type.equals("mob")) {
                findList = c.a().b();
            } else if (type.equals("poly")) {
                int[] nArray = ak.e;
                int n2 = ak.e.length;
                int n3 = 0;
                while (n3 < n2) {
                    i2 = nArray[n3];
                    findList.add(i2);
                    ++n3;
                }
            } else {
                findList = c.a().e(Integer.parseInt(type));
            }
            Collections.sort(findList);
            Iterator<Object> iterator = findList.iterator();
            while (iterator.hasNext()) {
                i2 = (Integer)iterator.next();
                if (count >= locList.size()) continue;
                int[] loc = (int[])locList.get(count);
                int x2 = loc[0];
                int y2 = loc[1];
                bh.l l1npc = au.a().a(45001);
                if (l1npc != null) {
                    t temp = new t(l1npc);
                    temp.cw(i2);
                    if (type.equals("effect") || type.equals("effect2")) {
                        temp = new m(l1npc);
                        temp.cw(l1npc.z());
                        ((m)temp).b(i2);
                    }
                    temp.cF(d.a().c());
                    temp.a("\u7de8\u865f: " + i2);
                    temp.cE(4);
                    temp.ct(5);
                    temp.cG(x2);
                    temp.cH(y2);
                    aq.a().a(temp);
                    aq.a().c(temp);
                    ++count;
                    continue;
                }
                pc.a(new ei("\u5730\u5716\u6c92\u7a7a\u4f4d\u653e\u4e86!!"));
            }
            am.a(pc, 32000, 33553, 4, 5, true);
        }
        catch (Exception e2) {
            pc.a(new ei(String.valueOf(cmdName) + " + \u8981\u5c0b\u627e\u7684type\u3002"));
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public void a(int x2, int y2) {
        bh.l l1npc = au.a().a(45060);
        if (l1npc != null) {
            t temp = new t(l1npc);
            temp.cF(d.a().c());
            temp.a(" ");
            temp.cE(4);
            temp.ct(5);
            temp.cG(x2);
            temp.cH(y2);
            aq.a().a(temp);
            aq.a().c(temp);
        }
    }
}

