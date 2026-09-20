/*
 * Decompiled with CFR 0.152.
 */
package l1r.bd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.SpawnTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Object;
import l1r.ax.L1Map;
import l1r.bd.L1Trap__obf_i;
import l1r.bd.TrapStorage;
import l1r.bi.Point;

public class L1MonsterTrap
extends L1Trap__obf_i {
    private static final Logger a = Logger.getLogger(L1MonsterTrap.class.getName());
    private final int b;
    private final int c;

    public L1MonsterTrap(TrapStorage storage) {
        super(storage);
        this.b = storage.b("monsterNpcId");
        this.c = storage.b("monsterCount");
    }

    private void a(List<Point> list, L1Map map, Point pt) {
        if (map.c(pt.f(), pt.g())) {
            list.add(pt);
        }
    }

    private List<Point> a(L1Location loc, int d) {
        ArrayList<Point> result = new ArrayList<Point>();
        L1Map m = loc.a();
        int x = loc.f();
        int y = loc.g();
        int i = 0;
        while (i < d) {
            this.a(result, m, new Point(d - i + x, i + y));
            this.a(result, m, new Point(-(d - i) + x, -i + y));
            this.a(result, m, new Point(-i + x, d - i + y));
            this.a(result, m, new Point(i + x, -(d - i) + y));
            ++i;
        }
        return result;
    }

    @Override
    public void a(L1PcInstance trodFrom, L1Object trapObj) {
        this.a(trapObj);
        List<Point> points = this.a(trapObj.fu(), 5);
        if (points.isEmpty()) {
            return;
        }
        try {
            int cnt = 0;
            block2: while (true) {
                Iterator<Point> iterator = points.iterator();
                do {
                    if (!iterator.hasNext()) continue block2;
                    Point pt = iterator.next();
                    SpawnTable.a(this.b, pt.f(), pt.g(), trapObj.fp());
                } while (this.c > ++cnt);
                break;
            }
            return;
        }
        catch (Exception e) {
            a.log(Level.SEVERE, e.getLocalizedMessage(), e);
            return;
        }
    }
}
