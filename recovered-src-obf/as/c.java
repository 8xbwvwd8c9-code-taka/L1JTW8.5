/*
 * Decompiled with CFR 0.152.
 */
package as;

import ao.ah;
import ao.ao;
import ao.bg;
import ao.x;
import ap.f;
import ap.q;
import ap.t;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import au.e;
import ax.b;
import ax.d;
import be.cg;
import be.cm;
import be.ds;
import be.ee;
import be.eu;
import bi.i;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class c {
    private static final Logger a = Logger.getLogger(c.class.getName());
    private static c b;
    private static final int c = 1936;
    private static final int d = 50;
    private static boolean[] e;

    static {
        e = new boolean[50];
    }

    public static c a() {
        if (b == null) {
            b = new c();
        }
        return b;
    }

    private c() {
        int y2;
        b map = ax.d.b().a(1936);
        int x2 = 32799;
        while (x2 <= 32800) {
            y2 = 32843;
            while (y2 <= 32851) {
                map.a(x2, y2, 511);
                ++y2;
            }
            ++x2;
        }
        x2 = 32811;
        while (x2 <= 32817) {
            y2 = 32861;
            while (y2 <= 32862) {
                map.a(x2, y2, 511);
                ++y2;
            }
            ++x2;
        }
        x2 = 32800;
        while (x2 <= 32801) {
            y2 = 32874;
            while (y2 <= 32879) {
                map.a(x2, y2, 511);
                ++y2;
            }
            ++x2;
        }
        x2 = 32784;
        while (x2 <= 32788) {
            y2 = 32860;
            while (y2 <= 32861) {
                map.a(x2, y2, 511);
                ++y2;
            }
            ++x2;
        }
        int i2 = 1;
        while (i2 < 50) {
            try {
                b clone = map.s();
                clone.a = 1936 + i2;
                ao.a().a(clone);
                ax.d.b().a().put(clone.a, clone);
            }
            catch (CloneNotSupportedException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            ++i2;
        }
    }

    public boolean a(u pc) {
        int i2 = 0;
        while (i2 < e.length) {
            if (!e[i2]) {
                as.c.e[i2] = true;
                bi.e.a().b(new a(1936 + i2, pc));
                return true;
            }
            ++i2;
        }
        return false;
    }

    private class a
    extends Thread {
        private final u b;
        private final int c;
        private t d;
        private int e = -1;

        private a(int _mapid, u _pc) {
            this.b = _pc;
            this.c = _mapid;
        }

        @Override
        public void run() {
            try {
                try {
                    am.a(this.b, 32795, 32867, this.c, 1, true);
                    this.d = this.a(new aq.u(32801, 32862, this.c), 190114, 1, 4).get(0);
                    ap.i field = x.a().a(7572, 32801, 32862, this.c);
                    ArrayList<t> list = new ArrayList<t>();
                    list.addAll(this.a(new aq.u(32798, 32862, this.c), 190110, 1, 2));
                    list.addAll(this.a(new aq.u(32801, 32865, this.c), 190110, 1, 0));
                    list.addAll(this.a(new aq.u(32804, 32859, this.c), 190110, 1, 5));
                    list.addAll(this.a(new aq.u(32798, 32861, this.c), 190111, 1, 2));
                    list.addAll(this.a(new aq.u(32802, 32866, this.c), 190111, 1, 0));
                    this.a(list);
                    ah.a(this.b, 640354, 1, true);
                    field.aa_();
                    this.b.a(new cm(156, 1, 3));
                    this.a("$17947");
                    ArrayList<t> list2 = new ArrayList<t>();
                    list2.addAll(this.a(new aq.u(32800, 32845, this.c), 190098, 15, 4));
                    list2.addAll(this.a(new aq.u(32800, 32845, this.c), 190099, 15, 4));
                    list2.addAll(this.a(new aq.u(32817, 32862, this.c), 190101, 15, 7));
                    list2.addAll(this.a(new aq.u(32817, 32862, this.c), 190100, 15, 7));
                    list2.addAll(this.a(new aq.u(32801, 32878, this.c), 190102, 15, 0));
                    list2.addAll(this.a(new aq.u(32801, 32878, this.c), 190103, 15, 0));
                    list2.addAll(this.a(new aq.u(32785, 32861, this.c), 190104, 15, 2));
                    list2.addAll(this.a(new aq.u(32785, 32861, this.c), 190105, 15, 2));
                    this.a(3000L);
                    this.a("$17701");
                    this.a(list2);
                    this.b.a(new cm(156, 2, 3));
                    ArrayList<t> list3 = new ArrayList<t>();
                    this.a("$17969");
                    this.e = 0;
                    int small_boss = 190106 + i.a(4);
                    if (small_boss == 190106) {
                        this.a("$17941");
                        list3.addAll(this.a(new aq.u(32800, 32845, this.c), small_boss, 1, 4));
                    } else if (small_boss == 190107) {
                        this.a("$17944");
                        list3.addAll(this.a(new aq.u(32817, 32862, this.c), small_boss, 1, 7));
                    } else if (small_boss == 190108) {
                        this.a("$17942");
                        list3.addAll(this.a(new aq.u(32801, 32878, this.c), small_boss, 1, 0));
                    } else if (small_boss == 190109) {
                        this.a("$17943");
                        list3.addAll(this.a(new aq.u(32785, 32861, this.c), small_boss, 1, 2));
                    }
                    list3.addAll(this.a(new aq.u(32800, 32845, this.c), 190098, 15, 4));
                    list3.addAll(this.a(new aq.u(32800, 32845, this.c), 190099, 15, 4));
                    list3.addAll(this.a(new aq.u(32817, 32862, this.c), 190101, 15, 7));
                    list3.addAll(this.a(new aq.u(32817, 32862, this.c), 190100, 15, 7));
                    list3.addAll(this.a(new aq.u(32801, 32878, this.c), 190102, 15, 0));
                    list3.addAll(this.a(new aq.u(32801, 32878, this.c), 190103, 15, 0));
                    list3.addAll(this.a(new aq.u(32785, 32861, this.c), 190104, 15, 2));
                    list3.addAll(this.a(new aq.u(32785, 32861, this.c), 190105, 15, 2));
                    this.a(3000L);
                    this.a("$17703");
                    this.a(list3);
                    this.b.a(new cm(156, 3, 3));
                    this.e = 0;
                    ArrayList<t> list4 = new ArrayList<t>();
                    int small_boss2 = 190106 + i.a(4);
                    if (small_boss2 == 190106) {
                        this.a("$17941");
                        list4.addAll(this.a(new aq.u(32800, 32845, this.c), small_boss2, 1, 4));
                    } else if (small_boss2 == 190107) {
                        this.a("$17944");
                        list4.addAll(this.a(new aq.u(32817, 32862, this.c), small_boss2, 1, 7));
                    } else if (small_boss2 == 190108) {
                        this.a("$17942");
                        list4.addAll(this.a(new aq.u(32801, 32878, this.c), small_boss2, 1, 0));
                    } else if (small_boss2 == 190109) {
                        this.a("$17943");
                        list4.addAll(this.a(new aq.u(32785, 32861, this.c), small_boss2, 1, 2));
                    }
                    this.a("$17969");
                    list4.addAll(this.a(new aq.u(32800, 32845, this.c), 190098, 10, 4));
                    list4.addAll(this.a(new aq.u(32800, 32845, this.c), 190099, 10, 4));
                    list4.addAll(this.a(new aq.u(32817, 32862, this.c), 190101, 10, 7));
                    list4.addAll(this.a(new aq.u(32817, 32862, this.c), 190100, 10, 7));
                    list4.addAll(this.a(new aq.u(32801, 32878, this.c), 190102, 10, 0));
                    list4.addAll(this.a(new aq.u(32801, 32878, this.c), 190103, 10, 0));
                    list4.addAll(this.a(new aq.u(32785, 32861, this.c), 190104, 10, 2));
                    list4.addAll(this.a(new aq.u(32785, 32861, this.c), 190105, 10, 2));
                    this.a(3000L);
                    this.a("$17703");
                    this.a(list4);
                    ArrayList<t> list5 = new ArrayList<t>();
                    int rnd = i.a(4);
                    if (rnd == 0) {
                        list5.addAll(this.a(new aq.u(32800, 32845, this.c), 190112, 1, 4));
                    } else if (rnd == 1) {
                        list5.addAll(this.a(new aq.u(32817, 32862, this.c), 190112, 1, 7));
                    } else if (rnd == 2) {
                        list5.addAll(this.a(new aq.u(32801, 32878, this.c), 190112, 1, 0));
                    } else if (rnd == 3) {
                        list5.addAll(this.a(new aq.u(32785, 32861, this.c), 190112, 1, 2));
                    }
                    this.a("$17995:$17713");
                    list5.addAll(this.a(new aq.u(32800, 32845, this.c), 190098, 10, 4));
                    list5.addAll(this.a(new aq.u(32800, 32845, this.c), 190099, 10, 4));
                    list5.addAll(this.a(new aq.u(32817, 32862, this.c), 190101, 10, 7));
                    list5.addAll(this.a(new aq.u(32817, 32862, this.c), 190100, 10, 7));
                    list5.addAll(this.a(new aq.u(32801, 32878, this.c), 190102, 10, 0));
                    list5.addAll(this.a(new aq.u(32801, 32878, this.c), 190103, 10, 0));
                    list5.addAll(this.a(new aq.u(32785, 32861, this.c), 190104, 10, 2));
                    list5.addAll(this.a(new aq.u(32785, 32861, this.c), 190105, 10, 2));
                    this.a(list5);
                    this.a("$17707");
                    this.d.g_(190115);
                    this.a(3000L);
                    this.a("$17708");
                    this.a(3000L);
                    this.a("$17709");
                    this.a(3000L);
                    this.a("$17710");
                    this.a(3000L);
                    this.a("$17712");
                    this.a(3000L);
                    this.d.b(new ee(this.d.fr(), 169));
                    this.d.aa_();
                    this.c();
                    this.a("$17962");
                    this.a(10000L);
                    this.b();
                }
                catch (Exception exception) {
                    this.a();
                    e[this.c - 1936] = false;
                    System.out.println("[\u526f\u672c\u7d50\u675f]:\u4e2d\u592e\u5bfa\u9662(" + this.c + ")");
                }
            }
            finally {
                this.a();
                e[this.c - 1936] = false;
                System.out.println("[\u526f\u672c\u7d50\u675f]:\u4e2d\u592e\u5bfa\u9662(" + this.c + ")");
            }
        }

        private void a() {
            for (aa obj : aq.a().b()) {
                if (obj.fp() != this.c) continue;
                if (obj instanceof u) {
                    u pc = (u)obj;
                    pc.j().a(640354);
                    pc.j().a(640355);
                    am.a(pc, 33703, 32502, 4, 5, true);
                    continue;
                }
                if (obj instanceof f) {
                    ao.t.b().a(obj.fu());
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

        private void a(long milliseconds) throws InterruptedException {
            Thread.sleep(milliseconds);
            if (this.b.fp() != this.c || this.b.bE() == 0) {
                throw new InterruptedException();
            }
            if (this.b.eX()) {
                this.b.a(new cg(this.b, "$18636"));
                Thread.sleep(3000L);
                this.b.a(new cg(this.b, "$18637"));
                Thread.sleep(3000L);
            }
        }

        private void a(eu serverbasepacket) {
            for (u pc : aq.a().c()) {
                if (pc.fp() != this.c) continue;
                pc.a(serverbasepacket);
            }
        }

        private void a(String msg) {
            this.a(new cm(84, 2, "\\f=" + msg));
        }

        private void b() throws InterruptedException {
            this.a(new ds(1476));
            this.a(10000L);
            this.a(new ds(1477));
            this.a(10000L);
            this.a(new ds(1478));
            this.a(5000L);
            this.a(new ds(1480));
            this.a(1000L);
            this.a(new ds(1481));
            this.a(1000L);
            this.a(new ds(1482));
            this.a(1000L);
            this.a(new ds(1483));
            this.a(1000L);
            this.a(new ds(1484));
            this.a(1000L);
        }

        private void c() {
            int[] drop1 = new int[]{40087, 40074, 40087, 40074, 40087, 40074};
            int[] drop2 = new int[]{264, 262, 260, 263, 261, 326, 337, 336, 328, 329, 21152, 21154, 21153, 21155};
            if (i.a(100) > 10) {
                ah.a(this.d, drop1[i.a(drop1.length)], 1);
            } else {
                ah.a(this.d, drop2[i.a(drop2.length)], 1);
            }
            ah.a(this.d, 640353, 1);
        }

        private ArrayList<t> a(aq.u loc, int mobid, int amount, int heading) {
            int[] mobids = new int[amount];
            int i2 = 0;
            while (i2 < amount) {
                mobids[i2] = mobid;
                ++i2;
            }
            return this.a(loc, mobids, heading);
        }

        private ArrayList<t> a(aq.u loc, int[] mobsID, int heading) {
            ArrayList<t> mob_list = new ArrayList<t>();
            int[] nArray = mobsID;
            int n2 = mobsID.length;
            int n3 = 0;
            while (n3 < n2) {
                int npcid = nArray[n3];
                t npc = bg.a(npcid, loc.f(), loc.g(), loc.b(), heading, true);
                mob_list.add(npc);
                ++n3;
            }
            return mob_list;
        }

        private int a(ArrayList<t> list) throws InterruptedException {
            int count = -1;
            while (count++ < 300) {
                boolean isAllDeath = false;
                for (t mob : list) {
                    if (!mob.eX()) {
                        isAllDeath = false;
                        break;
                    }
                    if (this.e == 0 && mob.z() >= 190106 && mob.z() <= 190109) {
                        this.a("$17968");
                        ah.a(this.b, 640355, 1, true);
                        this.e = 1;
                    }
                    isAllDeath = mob.eX();
                }
                if (isAllDeath) {
                    return count;
                }
                this.a(1000L);
            }
            this.a("$17714");
            this.a(3000L);
            this.a("$17715");
            this.d.aa_();
            this.b();
            this.a();
            return -1;
        }
    }
}

