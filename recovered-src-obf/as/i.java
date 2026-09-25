/*
 * Decompiled with CFR 0.152.
 */
package as;

import ao.ao;
import ao.bf;
import ao.bg;
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
import be.eu;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class i {
    private static final Logger a = Logger.getLogger(i.class.getName());
    private static i b;
    private static final int c = 4001;
    private static final int d = 50;
    private static boolean[] e;

    static {
        e = new boolean[50];
    }

    public static i a() {
        if (b == null) {
            b = new i();
        }
        return b;
    }

    private i() {
        b map = ax.d.b().a(4001);
        int i2 = 1;
        while (i2 < 50) {
            try {
                b clone = map.s();
                clone.a = 4001 + i2;
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
                i.e[i2] = true;
                bi.e.a().b(new a(4001 + i2, pc));
                return true;
            }
            ++i2;
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
            t npc = mobsID.length > 1 ? bg.a(npcid, loc.f(), loc.g(), loc.b(), 5, 5, true) : bg.a(npcid, loc.f(), loc.g(), loc.b(), 5, 0, true);
            mob_list.add(npc);
            ++n3;
        }
        return mob_list;
    }

    private class a
    extends Thread {
        private final u b;
        private final int c;

        private a(int _mapid, u _pc) {
            this.b = _pc;
            this.c = _mapid;
        }

        @Override
        public void run() {
            try {
                try {
                    long begin = System.currentTimeMillis();
                    am.a(this.b, 32869, 32923, this.c, 2, true);
                    this.b.a(new cm(195, 1800));
                    i.this.a(new aq.u(32801, 32812, this.c), 190045, 1);
                    i.this.a(new aq.u(32756, 32872, this.c), 190045, 1);
                    f door1 = ao.t.b().a(0, bh.f.a(12632), new aq.u(32843, 32878, this.c), 0, 1, false);
                    i.this.a(new aq.u(32849, 32923, this.c), 190031, 10);
                    i.this.a(new aq.u(32844, 32905, this.c), 190029, 6);
                    i.this.a(new aq.u(32844, 32905, this.c), 190030, 6);
                    this.a(10000L);
                    this.a("\\f=$18344");
                    this.a(3000L);
                    this.a("\\f=$18327");
                    this.a(3000L);
                    this.a("\\f=$18328");
                    ArrayList list = i.this.a(new aq.u(32843, 32894, this.c), 190034, 2);
                    this.a(list);
                    this.a("\\f=$18329");
                    i.this.a(new aq.u(32843, 32886, this.c), 190034, 2);
                    i.this.a(new aq.u(32843, 32886, this.c), 190029, 6);
                    t keeper1 = (t)i.this.a(new aq.u(32843, 32885, this.c), 190032, 1).get(0);
                    while (!keeper1.eX()) {
                        this.a(1000L);
                    }
                    door1.f();
                    this.a("\\f=$18338");
                    f door2 = ao.t.b().a(0, bh.f.a(6336), new aq.u(32842, 32848, this.c), 0, 1, false);
                    this.a(3000L);
                    this.a("\\f=$18347");
                    ArrayList<t> list2 = new ArrayList<t>();
                    list2.addAll(i.this.a(new aq.u(32844, 32862, this.c), 190034, 4));
                    list2.addAll(i.this.a(new aq.u(32844, 32862, this.c), 190035, 4));
                    list2.addAll(i.this.a(new aq.u(32844, 32862, this.c), 190036, 4));
                    i.this.a(new aq.u(32844, 32862, this.c), 190029, 6);
                    this.a(list2);
                    this.a("\\f=$18330");
                    i.this.a(new aq.u(32859, 32858, this.c), 190034, 2);
                    t keeper2 = (t)i.this.a(new aq.u(32859, 32858, this.c), 190037, 1).get(0);
                    while (!keeper2.eX()) {
                        this.a(1000L);
                    }
                    this.a("\\f=$18333");
                    door2.f();
                    f door3 = ao.t.b().a(0, bh.f.a(12754), new aq.u(32820, 32812, this.c), 0, 1, false);
                    f door4 = ao.t.b().a(0, bh.f.a(12754), new aq.u(32820, 32813, this.c), 0, 1, false);
                    f door5 = ao.t.b().a(0, bh.f.a(12754), new aq.u(32820, 32814, this.c), 0, 1, false);
                    this.a(5000L);
                    this.a("\\f=$18331");
                    i.this.a(new aq.u(32846, 32814, this.c), 190035, 2);
                    i.this.a(new aq.u(32846, 32814, this.c), 190029, 3);
                    i.this.a(new aq.u(32846, 32814, this.c), 190034, 2);
                    i.this.a(new aq.u(32864, 32804, this.c), 190035, 2);
                    i.this.a(new aq.u(32864, 32804, this.c), 190030, 3);
                    i.this.a(new aq.u(32864, 32804, this.c), 190034, 2);
                    i.this.a(new aq.u(32850, 32801, this.c), 190035, 2);
                    i.this.a(new aq.u(32850, 32801, this.c), 190029, 3);
                    i.this.a(new aq.u(32850, 32801, this.c), 190034, 2);
                    i.this.a(new aq.u(32831, 32799, this.c), 190035, 2);
                    i.this.a(new aq.u(32831, 32799, this.c), 190030, 3);
                    i.this.a(new aq.u(32831, 32799, this.c), 190034, 2);
                    t keeper3 = (t)i.this.a(new aq.u(32833, 32809, this.c), 190034, 1).get(0);
                    while (!keeper3.eX()) {
                        this.a(1000L);
                    }
                    door3.f();
                    door4.f();
                    door5.f();
                    this.a("\\f=$18348");
                    f door6 = ao.t.b().a(0, bh.f.a(12711), new aq.u(32790, 32815, this.c), 0, 1, false);
                    i.this.a(new aq.u(32800, 32816, this.c), 190036, 3);
                    i.this.a(new aq.u(32800, 32816, this.c), 190034, 2);
                    t keeper4 = (t)i.this.a(new aq.u(32800, 32816, this.c), 190038, 1).get(0);
                    while (!keeper4.eX()) {
                        this.a(1000L);
                    }
                    door6.f();
                    this.a("\\f=$18340");
                    ArrayList<f> door_list = new ArrayList<f>();
                    int i2 = 32769;
                    while (i2 <= 32777) {
                        f door = ao.t.b().a(0, bh.f.a(12754), new aq.u(i2, 32829, this.c), 0, 1, false);
                        door.c(0);
                        door_list.add(door);
                        ++i2;
                    }
                    f door0 = ao.t.b().a(0, bh.f.a(12711), new aq.u(32760, 32819, this.c), 0, 1, false);
                    this.a(3000L);
                    this.a("\\f=$18349");
                    i.this.a(new aq.u(32776, 32818, this.c), 190036, 4);
                    i.this.a(new aq.u(32776, 32818, this.c), 190030, 4);
                    i.this.a(new aq.u(32776, 32818, this.c), 190034, 4);
                    i.this.a(new aq.u(32776, 32818, this.c), 190035, 4);
                    ArrayList list3 = i.this.a(new aq.u(32776, 32818, this.c), 190029, 4);
                    this.a(list3);
                    for (f door : door_list) {
                        door.f();
                    }
                    ArrayList<f> door_list2 = new ArrayList<f>();
                    int i3 = 32763;
                    while (i3 <= 32776) {
                        f door = ao.t.b().a(0, bh.f.a(12754), new aq.u(i3, 32843, this.c), 0, 1, false);
                        door.c(0);
                        door_list2.add(door);
                        ++i3;
                    }
                    this.a(5000L);
                    this.a("\\f=$18332");
                    i.this.a(new aq.u(32772, 32835, this.c), 190036, 4);
                    i.this.a(new aq.u(32772, 32835, this.c), 190030, 4);
                    i.this.a(new aq.u(32772, 32835, this.c), 190034, 4);
                    i.this.a(new aq.u(32772, 32835, this.c), 190035, 4);
                    i.this.a(new aq.u(32772, 32835, this.c), 190029, 4);
                    this.a(15000L);
                    for (f door : door_list2) {
                        door.f();
                    }
                    this.a("\\f=$18333");
                    ArrayList<f> door_list3 = new ArrayList<f>();
                    int i4 = 32749;
                    while (i4 <= 32751) {
                        f door = ao.t.b().a(0, bh.f.a(12754), new aq.u(i4, 32881, this.c), 0, 1, false);
                        door.c(0);
                        door_list3.add(door);
                        ++i4;
                    }
                    i.this.a(new aq.u(32769, 32854, this.c), 190036, 2);
                    i.this.a(new aq.u(32769, 32854, this.c), 190030, 2);
                    i.this.a(new aq.u(32769, 32854, this.c), 190034, 2);
                    i.this.a(new aq.u(32769, 32854, this.c), 190035, 2);
                    i.this.a(new aq.u(32769, 32854, this.c), 190029, 2);
                    t keeper7 = (t)i.this.a(new aq.u(32769, 32854, this.c), 190039, 1).get(0);
                    while (!keeper7.eX()) {
                        this.a(1000L);
                    }
                    this.a("\\f=$18341");
                    for (f door : door_list3) {
                        door.f();
                    }
                    f door8 = ao.t.b().a(0, bh.f.a(12711), new aq.u(32769, 32905, this.c), 0, 1, false);
                    this.a(10000L);
                    this.a("\\f=$18334");
                    i.this.a(new aq.u(32753, 32898, this.c), 190036, 6);
                    i.this.a(new aq.u(32753, 32898, this.c), 190030, 6);
                    i.this.a(new aq.u(32753, 32898, this.c), 190034, 6);
                    i.this.a(new aq.u(32753, 32898, this.c), 190035, 6);
                    i.this.a(new aq.u(32753, 32898, this.c), 190029, 10);
                    this.a(15000L);
                    this.a("\\f=$18335");
                    t keeper8 = (t)i.this.a(new aq.u(32765, 32906, this.c), 190033, 1).get(0);
                    while (!keeper8.eX()) {
                        this.a(1000L);
                    }
                    door8.f();
                    this.a("\\f=$18342");
                    this.a(3000L);
                    this.a("\\f=$18336");
                    i.this.a(new aq.u(32786, 32906, this.c), 190036, 4);
                    i.this.a(new aq.u(32786, 32906, this.c), 190030, 4);
                    i.this.a(new aq.u(32786, 32906, this.c), 190034, 4);
                    i.this.a(new aq.u(32786, 32906, this.c), 190035, 4);
                    i.this.a(new aq.u(32786, 32906, this.c), 190029, 4);
                    this.a(10000L);
                    this.a("\\f=$18337");
                    t boss = (t)i.this.a(new aq.u(32786, 32906, this.c), 190041, 1).get(0);
                    while (!boss.eX()) {
                        this.a(1000L);
                    }
                    int usetime = (int)((System.currentTimeMillis() - begin) / 1000L);
                    this.b.a(new cm(196, usetime));
                    bf.a().a(this.b, usetime);
                    this.a("\\f=$18343");
                    this.a(2000L);
                    this.a("\\f=$18574");
                    this.a(2000L);
                    this.a("\\f=$18575");
                    this.a(2000L);
                    int i5 = 0;
                    while (i5 < 10) {
                        this.a("$" + (18576 + i5));
                        this.a(1000L);
                        ++i5;
                    }
                }
                catch (Exception exception) {
                    this.a();
                    e[this.c - 4001] = false;
                    System.out.println("[\u526f\u672c\u7d50\u675f]:\u5c4d\u9b42\u5854(" + this.c + ")");
                }
            }
            finally {
                this.a();
                e[this.c - 4001] = false;
                System.out.println("[\u526f\u672c\u7d50\u675f]:\u5c4d\u9b42\u5854(" + this.c + ")");
            }
        }

        private void a() {
            for (aa obj : aq.a().b()) {
                if (obj.fp() != this.c) continue;
                if (obj instanceof u) {
                    u pc = (u)obj;
                    am.a(pc, 33703, 32502, 4, 5, true);
                    continue;
                }
                if (obj instanceof f) {
                    ao.t.b().a(obj.fu());
                    continue;
                }
                if (obj instanceof t) {
                    t npc = (t)obj;
                    if (npc instanceof ap.i) continue;
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
            this.a(new cm(84, 2, msg));
        }

        private int a(ArrayList<t> list) throws InterruptedException {
            int count = -1;
            while (count++ < 900) {
                boolean isAllDeath = false;
                for (t mob : list) {
                    if (!mob.eX()) {
                        isAllDeath = false;
                        break;
                    }
                    isAllDeath = mob.eX();
                }
                if (isAllDeath) {
                    return count;
                }
                this.a(1000L);
            }
            throw new InterruptedException();
        }
    }
}

