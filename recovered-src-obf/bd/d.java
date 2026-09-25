/*
 * Decompiled with CFR 0.152.
 */
package bd;

import ao.bg;
import ap.u;
import aq.aa;
import ax.b;
import bd.i;
import bd.j;
import bi.h;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class d
extends i {
    private static final Logger a = Logger.getLogger(d.class.getName());
    private final int b;
    private final int c;

    public d(j storage) {
        super(storage);
        this.b = storage.b("monsterNpcId");
        this.c = storage.b("monsterCount");
    }

    private void a(List<h> list, b map, h pt) {
        if (map.c(pt.f(), pt.g())) {
            list.add(pt);
        }
    }

    private List<h> a(aq.u loc, int d2) {
        ArrayList<h> result = new ArrayList<h>();
        b m2 = loc.a();
        int x2 = loc.f();
        int y2 = loc.g();
        int i2 = 0;
        while (i2 < d2) {
            this.a(result, m2, new h(d2 - i2 + x2, i2 + y2));
            this.a(result, m2, new h(-(d2 - i2) + x2, -i2 + y2));
            this.a(result, m2, new h(-i2 + x2, d2 - i2 + y2));
            this.a(result, m2, new h(i2 + x2, -(d2 - i2) + y2));
            ++i2;
        }
        return result;
    }

    @Override
    public void a(u trodFrom, aa trapObj) {
        this.a(trapObj);
        List<h> points = this.a(trapObj.fu(), 5);
        if (points.isEmpty()) {
            return;
        }
        try {
            int cnt = 0;
            block2: while (true) {
                Iterator<h> iterator = points.iterator();
                do {
                    if (!iterator.hasNext()) continue block2;
                    h pt = iterator.next();
                    bg.a(this.b, pt.f(), pt.g(), trapObj.fp());
                } while (this.c > ++cnt);
                break;
            }
            return;
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return;
        }
    }
}

