/*
 * Decompiled with CFR 0.152.
 */
package ax;

import ax.b;
import ax.e;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class d {
    private static final Logger a = Logger.getLogger(d.class.getName());
    private static d b;
    private Map<Integer, b> c;

    public Map<Integer, b> a() {
        return this.c;
    }

    public static d b() {
        if (b == null) {
            b = new d();
        }
        return b;
    }

    private d() {
        long begin = System.currentTimeMillis();
        System.out.print("loading map...");
        try {
            this.c = e.b().a();
            if (this.c == null) {
                throw new RuntimeException("\u5730\u5716\u6a94\u6848\u8b80\u53d6\u5931\u6557...");
            }
        }
        catch (FileNotFoundException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        catch (Exception e3) {
            a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
            System.exit(0);
        }
        System.out.println("OK! " + (System.currentTimeMillis() - begin) + "ms");
    }

    public b a(int mapId) {
        b map = this.c.get(mapId);
        if (map == null) {
            System.out.println("[Error] get null map:" + mapId);
            map = new b();
        }
        return map;
    }
}

