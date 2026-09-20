/*
 * Decompiled with CFR 0.152.
 */
package as;

import ao.ao;
import ao.bg;
import ap.q;
import ap.t;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import au.e;
import ax.b;
import ax.d;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class f {
    private static final Logger c = Logger.getLogger(f.class.getName());
    public static final int a = 1;
    public static final int b = 2;
    private static f d;
    private final int e = 50;
    private final int f = 2101;
    private final int g = 2151;
    private final boolean[] h = new boolean[50];
    private final boolean[] i = new boolean[50];

    public static f a() {
        if (d == null) {
            d = new f();
        }
        return d;
    }

    public f() {
        b map = ax.d.b().a(2101);
        b map2 = ax.d.b().a(2151);
        try {
            int i2 = 1;
            while (i2 < 50) {
                b clone = map.s();
                clone.a = 2101 + i2;
                ao.a().a(clone);
                ax.d.b().a().put(clone.a, clone);
                b clone2 = map2.s();
                clone2.a = 2151 + i2;
                ao.a().a(clone2);
                ax.d.b().a().put(clone2.a, clone2);
                ap.f[] fArray = ao.t.b().c();
                int n2 = fArray.length;
                int n3 = 0;
                while (n3 < n2) {
                    ap.f create;
                    bh.f gfx;
                    aq.u spwanLoc;
                    ap.f door = fArray[n3];
                    if (door.fp() == 2101) {
                        spwanLoc = new aq.u(door.fs(), door.ft(), clone.a);
                        gfx = bh.f.a(door.fe());
                        create = ao.t.b().a(0, gfx, spwanLoc, 0, 0, false);
                        if (door.i() == 5016) {
                            create.f(5016);
                        }
                    } else if (door.fp() == 2151) {
                        spwanLoc = new aq.u(door.fs(), door.ft(), clone2.a);
                        gfx = bh.f.a(door.fe());
                        create = ao.t.b().a(0, gfx, spwanLoc, 0, 0, false);
                        if (door.i() == 5021) {
                            create.f(5021);
                        }
                    }
                    ++n3;
                }
                ++i2;
            }
        }
        catch (CloneNotSupportedException e2) {
            c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public boolean a(u pc, int type) {
        if (type == 1) {
            int i2 = 0;
            while (i2 < this.h.length) {
                if (!this.h[i2]) {
                    this.h[i2] = true;
                    am.a(pc, 32728, 32819, 2101 + i2, 0, true);
                    bi.e.a().a(new a(type, i2), 30000L);
                    return true;
                }
                ++i2;
            }
        } else {
            int i3 = 0;
            while (i3 < this.i.length) {
                if (!this.i[i3]) {
                    this.i[i3] = true;
                    am.a(pc, 32728, 32819, 2151 + i3, 0, true);
                    bi.e.a().a(new a(type, i3), 30000L);
                    return true;
                }
                ++i3;
            }
        }
        return false;
    }

    private ArrayList<t> a(aq.u loc, int mobid, int amount) {
        int[] mobids = new int[amount];
        int i2 = 0;
        while (i2 < amount) {
            mobids[i2] = mobid;
            ++i2;
        }
        return this.a(loc, mobids);
    }

    private ArrayList<t> a(aq.u loc, int[] mobsID) {
        ArrayList<t> mob_list = new ArrayList<t>();
        int[] nArray = mobsID;
        int n2 = mobsID.length;
        int n3 = 0;
        while (n3 < n2) {
            int npcid = nArray[n3];
            t npc = mobsID.length > 1 ? bg.a(npcid, loc.f(), loc.g(), loc.b(), 5, 15, true) : bg.a(npcid, loc.f(), loc.g(), loc.b(), 5, 0, true);
            mob_list.add(npc);
            ++n3;
        }
        return mob_list;
    }

    private class a
    extends TimerTask {
        private final int b;
        private final int c;
        private int d = 0;
        private final int e;

        private a(int _type, int _idx) {
            this.b = _type;
            this.c = _idx;
            this.e = (this.b == 1 ? 2101 : 2151) + this.c;
            f.this.a(new aq.u(32734, 32802, this.e), 81404, 1);
            f.this.a(new aq.u(32860, 32920, this.e), 190352, 1);
            int[] mobs = new int[]{190351, 190351, 190351, 190351, 190351, 190351, 97080, 97080, 97080, 97080, 97079, 97079, 97079, 97079, 97088, 97088, 97088, 97088, 97088, 97088, 97088, 97088};
            f.this.a(new aq.u(32765, 32818, this.e), mobs);
            int[] mobs2 = new int[]{190351, 190351, 190351, 190351, 190351, 190351, 97080, 97080, 97080, 97080, 97076, 97076, 97076, 97076, 97087, 97087, 97087, 97087, 97088, 97088, 97088, 97088};
            f.this.a(new aq.u(32836, 32805, this.e), mobs2);
            int[] mobs3 = new int[]{190351, 190351, 190351, 190351, 190351, 190351, 97080, 97080, 97080, 97080, 97079, 97079, 97079, 97079, 97087, 97087, 97087, 97087, 97087, 97087, 97087, 97087};
            f.this.a(new aq.u(32848, 32852, this.e), mobs3);
            int[] mobs4 = new int[]{190351, 190351, 190351, 190351, 190351, 190351, 97079, 97079, 97079, 97079, 97079, 97079, 97079, 97079, 97087, 97087, 97087, 97087, 97088, 97088, 97088, 97088};
            f.this.a(new aq.u(32765, 32895, this.e), mobs4);
            int[] mobs5 = new int[]{190351, 190351, 190351, 190351, 190351, 190351, 190351, 190351, 190351, 97087, 97087, 97087, 97087, 97087, 97088, 97088, 97088, 97088, 97088};
            f.this.a(new aq.u(32823, 32921, this.e), mobs5);
            if (this.b == 1) {
                f.this.a(new aq.u(32823, 32921, this.e), 46142, 1);
            } else {
                f.this.a(new aq.u(32823, 32921, this.e), 46141, 1);
            }
        }

        @Override
        public void run() {
            try {
                if (this.b == 1 && !f.this.h[this.c]) {
                    return;
                }
                if (this.b == 2 && !f.this.i[this.c]) {
                    return;
                }
                if (this.d > 60) {
                    throw new InterruptedException();
                }
                for (aa obj : aq.a().b(this.e).values()) {
                    if (!(obj instanceof u)) continue;
                    ++this.d;
                    this.a(30L);
                    return;
                }
            }
            catch (Exception e2) {
                c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            this.a();
            if (this.b == 1) {
                ((f)f.this).h[this.c] = false;
            } else {
                ((f)f.this).i[this.c] = false;
            }
            System.out.println("[\u526f\u672c\u7d50\u675f]:\u51b0\u4e4b\u5973\u738b(" + this.e + ")");
        }

        private void a() {
            for (aa obj : aq.a().b(this.e).values()) {
                if (obj instanceof u) {
                    u pc = (u)obj;
                    am.a(pc, 34062, 32311, 4, 5, true);
                    continue;
                }
                if (obj instanceof ap.f) {
                    ((ap.f)obj).g();
                    continue;
                }
                if (obj instanceof t) {
                    t npc = (t)obj;
                    npc.aa_();
                    continue;
                }
                if (!(obj instanceof q)) continue;
                q item = (q)obj;
                e groundInventory = aq.a().a(item.fs(), item.ft(), item.fp());
                groundInventory.f(item);
            }
        }

        private void a(long sec) {
            bi.e.a().a(this, sec * 1000L);
        }
    }
}

