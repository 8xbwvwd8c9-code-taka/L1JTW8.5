/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ap.v;
import ap.z;
import aq.aa;
import aq.ap;
import aq.u;
import au.e;
import ax.b;
import be.ei;
import be.eu;
import bi.h;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class aq {
    private final ConcurrentHashMap<String, ap.u> c;
    private final ConcurrentHashMap<Integer, v> d;
    private final ConcurrentHashMap<Integer, z> e;
    private final ConcurrentHashMap<Integer, ap.h> f;
    private final ConcurrentHashMap<Integer, aa> g;
    private final ConcurrentHashMap<Integer, aa>[] h;
    private final CopyOnWriteArrayList<ap> i;
    private int j = 4;
    private boolean k = true;
    private boolean l = false;
    private static final int m = 25599;
    public static final int a = 40308;
    private static aq n;
    private Collection<aa> o;
    private Collection<ap.u> p;
    private Collection<v> q;
    private Collection<z> r;
    private List<ap> s;
    public int[] b = new int[4];

    private aq() {
        this.c = new ConcurrentHashMap();
        this.d = new ConcurrentHashMap();
        this.e = new ConcurrentHashMap();
        this.f = new ConcurrentHashMap();
        this.g = new ConcurrentHashMap();
        this.h = new ConcurrentHashMap[25600];
        this.i = new CopyOnWriteArrayList();
        int i2 = 0;
        while (i2 <= 25599) {
            this.h[i2] = new ConcurrentHashMap();
            ++i2;
        }
    }

    public static aq a() {
        if (n == null) {
            n = new aq();
        }
        return n;
    }

    public void a(aa object) {
        if (object == null) {
            throw new NullPointerException();
        }
        this.g.put(object.fr(), object);
        if (object instanceof ap.u) {
            this.c.put(((ap.u)object).et(), (ap.u)object);
        }
        if (object instanceof v) {
            this.d.put(object.fr(), (v)object);
        }
        if (object instanceof z) {
            this.e.put(object.fr(), (z)object);
        }
        if (object instanceof ap.h) {
            this.f.put(object.fr(), (ap.h)object);
        }
    }

    public void b(aa object) {
        if (object == null) {
            throw new NullPointerException();
        }
        this.g.remove(object.fr());
        if (object instanceof ap.u) {
            this.c.remove(((ap.u)object).et());
        }
        if (object instanceof v) {
            this.d.remove(object.fr());
        }
        if (object instanceof z) {
            this.e.remove(object.fr());
        }
        if (object instanceof ap.h) {
            this.f.remove(object.fr());
        }
    }

    public aa a(int oID) {
        return this.g.get(oID);
    }

    public Collection<aa> b() {
        Collection<aa> vs = this.o;
        return vs != null ? vs : (this.o = Collections.unmodifiableCollection(this.g.values()));
    }

    public e a(int x2, int y2, int i2) {
        int inventoryKey = ((x2 - 30000) * 10000 + (y2 - 30000)) * -1;
        aa object = this.h[i2].get(inventoryKey);
        if (object == null) {
            return new e(inventoryKey, x2, y2, i2);
        }
        return (e)object;
    }

    public e a(u loc) {
        return this.a(loc.f(), loc.g(), loc.a().b());
    }

    public void c(aa object) {
        if (object.fp() <= 25599) {
            this.h[object.fp()].put(object.fr(), object);
        }
    }

    public void d(aa object) {
        if (object.fp() <= 25599) {
            this.h[object.fp()].remove(object.fr());
        }
    }

    public void a(aa object, int newMap) {
        if (object.fp() != newMap) {
            if (object.fp() <= 25599) {
                this.h[object.fp()].remove(object.fr());
            }
            if (newMap <= 25599) {
                this.h[newMap].put(object.fr(), object);
            }
        }
    }

    private Map<Integer, Integer> a(h src, h target) {
        HashMap<Integer, Integer> lineMap = new HashMap<Integer, Integer>();
        int x0 = src.f();
        int y0 = src.g();
        int x1 = target.f();
        int y1 = target.g();
        int sx = x1 > x0 ? 1 : -1;
        int dx2 = x1 > x0 ? x1 - x0 : x0 - x1;
        int sy = y1 > y0 ? 1 : -1;
        int dy2 = y1 > y0 ? y1 - y0 : y0 - y1;
        int x2 = x0;
        int y2 = y0;
        if (dx2 >= dy2) {
            int E = -dx2;
            int i2 = 0;
            while (i2 <= dx2) {
                int key = (x2 << 16) + y2;
                lineMap.put(key, key);
                x2 += sx;
                if ((E += 2 * dy2) >= 0) {
                    y2 += sy;
                    E -= 2 * dx2;
                }
                ++i2;
            }
        } else {
            int E = -dy2;
            int i3 = 0;
            while (i3 <= dy2) {
                int key = (x2 << 16) + y2;
                lineMap.put(key, key);
                y2 += sy;
                if ((E += 2 * dx2) >= 0) {
                    x2 += sx;
                    E -= 2 * dy2;
                }
                ++i3;
            }
        }
        return lineMap;
    }

    public ArrayList<aa> a(aa src, aa target) {
        Map<Integer, Integer> lineMap = this.a((h)src.fu(), target.fu());
        int mapid = target.fp();
        ArrayList<aa> result = new ArrayList<aa>();
        if (mapid <= 25599) {
            for (aa element : this.h[mapid].values()) {
                int key;
                if (element.fr() == src.fr() || !lineMap.containsKey(key = (element.fs() << 16) + element.ft())) continue;
                result.add(element);
            }
        }
        return result;
    }

    public ArrayList<aa> a(u loc, int radius) {
        ArrayList<aa> result = new ArrayList<aa>();
        if (loc.b() <= 25599) {
            for (aa element : this.h[loc.b()].values()) {
                if (element.fp() != loc.b()) continue;
                if (radius == -1) {
                    if (!loc.e(element.fu())) continue;
                    result.add(element);
                    continue;
                }
                if (radius == 0) {
                    if (!loc.f(element.fu())) continue;
                    result.add(element);
                    continue;
                }
                if (loc.c(element.fu()) > radius) continue;
                result.add(element);
            }
        }
        return result;
    }

    public List<aa> e(aa object) {
        return this.b(object, -1);
    }

    public ArrayList<aa> b(aa object, int radius) {
        b map = object.fq();
        u pt = object.fu();
        ArrayList<aa> result = new ArrayList<aa>();
        if (map.b() <= 25599) {
            for (aa element : this.h[map.b()].values()) {
                if (element.fr() == object.fr() || map != element.fq()) continue;
                if (radius == -1) {
                    if (!pt.e(element.fu())) continue;
                    result.add(element);
                    continue;
                }
                if (radius == 0) {
                    if (!pt.f(element.fu())) continue;
                    result.add(element);
                    continue;
                }
                if (pt.c(element.fu()) > radius) continue;
                result.add(element);
            }
        }
        return result;
    }

    public List<ap.u> f(aa object) {
        return this.c(object, -1);
    }

    public List<ap.u> c(aa object, int radius) {
        int map = object.fp();
        u pt = object.fu();
        ArrayList<ap.u> result = new ArrayList<ap.u>();
        for (ap.u element : this.c()) {
            if (element.fr() == object.fr() || map != element.fp()) continue;
            if (radius == -1) {
                if (!pt.e(element.fu())) continue;
                result.add(element);
                continue;
            }
            if (radius == 0) {
                if (!pt.f(element.fu())) continue;
                result.add(element);
                continue;
            }
            if (pt.c(element.fu()) > radius) continue;
            result.add(element);
        }
        return result;
    }

    public List<ap.u> b(aa object, aa target) {
        int map = object.fp();
        u objectPt = object.fu();
        u targetPt = target.fu();
        ArrayList<ap.u> result = new ArrayList<ap.u>();
        for (ap.u element : this.c()) {
            if (element.fr() == object.fr() || map != element.fp() || !objectPt.e(element.fu()) || targetPt.e(element.fu())) continue;
            result.add(element);
        }
        return result;
    }

    public Collection<ap.u> c() {
        Collection<ap.u> vs = this.p;
        return vs != null ? vs : (this.p = Collections.unmodifiableCollection(this.c.values()));
    }

    public ap.u a(String name) {
        if (this.c.containsKey(name)) {
            return this.c.get(name);
        }
        for (ap.u each : this.c()) {
            if (!each.et().equalsIgnoreCase(name)) continue;
            return each;
        }
        return null;
    }

    public Map<Integer, ap.h> d() {
        return this.f;
    }

    public Collection<v> e() {
        Collection<v> vs = this.q;
        return vs != null ? vs : (this.q = Collections.unmodifiableCollection(this.d.values()));
    }

    public Collection<z> f() {
        Collection<z> vs = this.r;
        return vs != null ? vs : (this.r = Collections.unmodifiableCollection(this.e.values()));
    }

    public final Map<Integer, aa> g() {
        return this.g;
    }

    public final Map<Integer, aa>[] h() {
        return this.h;
    }

    public final Map<Integer, aa> b(int mapId) {
        return this.h[mapId];
    }

    public void a(ap war) {
        if (!this.i.contains(war)) {
            this.i.add(war);
        }
    }

    public void b(ap war) {
        if (this.i.contains(war)) {
            this.i.remove(war);
        }
    }

    public List<ap> i() {
        List<ap> vs = this.s;
        return vs != null ? vs : (this.s = Collections.unmodifiableList(this.i));
    }

    public boolean b(String clanName) {
        return this.c(clanName) != null;
    }

    public ap c(String clanName) {
        for (ap war : this.i()) {
            if (!war.b(clanName)) continue;
            return war;
        }
        return null;
    }

    public void c(int weather) {
        this.j = weather;
    }

    public int j() {
        return this.j;
    }

    public void a(boolean flag) {
        this.k = flag;
    }

    public boolean k() {
        return this.k;
    }

    public void b(boolean flag) {
        this.l = flag;
    }

    public boolean l() {
        return this.l;
    }

    public void a(eu packet) {
        for (ap.u pc : this.c()) {
            pc.a(packet);
        }
    }

    public void d(String message) {
        this.a(new ei(message));
    }
}

