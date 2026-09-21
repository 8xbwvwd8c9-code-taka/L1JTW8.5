/*
 * Decompiled with CFR 0.152.
 */
package as;

import ao.ao;
import ao.bg;
import ap.f;
import ap.q;
import ap.t;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import aq.l;
import au.e;
import ax.b;
import ax.d;
import be.cg;
import be.cm;
import be.ds;
import be.eu;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class k {
    private static final Logger a = Logger.getLogger(k.class.getName());
    private static k b;
    private static final int c = 99;
    private static boolean[] d;

    static {
        d = new boolean[99];
    }

    public static k a() {
        if (b == null) {
            b = new k();
        }
        return b;
    }

    private k() {
        b map = ax.d.b().a(2600);
        b map2 = ax.d.b().a(2699);
        int i2 = 1;
        while (i2 < 99) {
            try {
                b clone = map.s();
                clone.a = 2600 + i2;
                ao.a().a(clone);
                ax.d.b().a().put(clone.a, clone);
                b clone2 = map2.s();
                clone2.a = 2699 + i2;
                ao.a().a(clone2);
                ax.d.b().a().put(clone2.a, clone2);
                aq.u src = new aq.u(32753, 32985, clone.a);
                aq.u src2 = new aq.u(32753, 32986, clone.a);
                aq.u dist = new aq.u(32830, 32758, clone.a);
                l.a().a(src, dist);
                l.a().a(src2, dist);
            }
            catch (CloneNotSupportedException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            ++i2;
        }
    }

    public boolean a(u pc) {
        int baseMapid = 2699;
        int i2 = 0;
        while (i2 < d.length) {
            if (!d[i2]) {
                k.d[i2] = true;
                bi.e.a().b(new a(2699 + i2, pc));
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
            t npc = mobsID.length > 1 ? bg.a(npcid, loc.f(), loc.g(), loc.b(), 5, 10, false) : bg.a(npcid, loc.f(), loc.g(), loc.b(), 5, 0, false);
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
                    t npc1 = (t)k.this.a(new aq.u(32624, 33057, this.c), 46164, 1).get(0);
                    t npc2 = (t)k.this.a(new aq.u(32624, 33057, this.c - 99), 46165, 1).get(0);
                    am.a(this.b, 32608, 33055, this.c, 5, true);
                    this.b.a(new cm(153, 3600));
                    while (this.b.fu().c(npc1.fu()) > 5 && this.b.fp() == this.c && this.b.bE() == 1) {
                        this.a(1000L);
                    }
                    int[] msgid1 = new int[]{18861, 18862, 18863, 18864, 18865, 18866, 18867, 18868};
                    int i2 = 0;
                    while (i2 < msgid1.length) {
                        if (this.b.fp() == this.c - 99) break;
                        if (i2 == 1 || i2 == 3 || i2 == 5) {
                            npc1.b(new cg(this.b, "$" + msgid1[i2]));
                        } else {
                            npc1.b(new cg(npc1, "$" + msgid1[i2]));
                        }
                        this.a(2000L);
                        ++i2;
                    }
                    this.a(120);
                    k.this.a(new aq.u(32613, 33055, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32609, 33045, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32616, 33035, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32623, 33020, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32628, 33007, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32628, 32998, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32666, 33000, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32669, 33014, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32673, 33030, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32681, 33040, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32691, 33041, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32690, 33054, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32706, 33052, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32718, 33042, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32729, 33034, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32735, 33018, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32741, 33018, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32747, 32990, this.c - 99), 46166, 1);
                    k.this.a(new aq.u(32747, 32983, this.c - 99), 46166, 1);
                    int[] msgid2 = new int[]{18644, 18645, 18646, 18647};
                    int i3 = 0;
                    while (i3 < msgid2.length) {
                        npc2.b(new cg(npc2, "$" + msgid2[i3]));
                        this.a(2000L);
                        ++i3;
                    }
                    f door1 = ao.t.b().a(0, bh.f.a(8307), new aq.u(32654, 33002, this.c - 99), 0, 1, false);
                    t mob1 = (t)k.this.a(new aq.u(32640, 32997, this.c - 99), 46174, 1).get(0);
                    mob1.g(32654, 33000);
                    k.this.a(new aq.u(32614, 33035, this.c - 99), 46167, 10);
                    k.this.a(new aq.u(32643, 32997, this.c - 99), 46167, 10);
                    k.this.a(new aq.u(32606, 33052, this.c - 99), 46168, 10);
                    k.this.a(new aq.u(32643, 32997, this.c - 99), 46168, 10);
                    k.this.a(new aq.u(32612, 33006, this.c - 99), 46169, 10);
                    k.this.a(new aq.u(32643, 32997, this.c - 99), 46169, 10);
                    while (!mob1.eX() || mob1.ag()) {
                        this.a(1000L);
                    }
                    door1.f();
                    f door2 = ao.t.b().a(0, bh.f.a(8307), new aq.u(32694, 33054, this.c - 99), 0, 1, false);
                    t mob2 = (t)k.this.a(new aq.u(32684, 33044, this.c - 99), 46175, 1).get(0);
                    mob2.g(32694, 33052);
                    k.this.a(new aq.u(32669, 33024, this.c - 99), 46167, 15);
                    k.this.a(new aq.u(32683, 33056, this.c - 99), 46167, 15);
                    k.this.a(new aq.u(32677, 33000, this.c - 99), 46170, 15);
                    k.this.a(new aq.u(32688, 32986, this.c - 99), 46171, 10);
                    k.this.a(new aq.u(32690, 33029, this.c - 99), 46171, 10);
                    while (!mob2.eX() || mob2.ag()) {
                        this.a(1000L);
                    }
                    door2.f();
                    f door3 = ao.t.b().a(0, bh.f.a(8305), new aq.u(32736, 33008, this.c - 99), 0, 1, false);
                    t mob3 = (t)k.this.a(new aq.u(32735, 33021, this.c - 99), 46176, 1).get(0);
                    mob3.g(32738, 33009);
                    k.this.a(new aq.u(32725, 33018, this.c - 99), 46167, 20);
                    k.this.a(new aq.u(32716, 33047, this.c - 99), 46172, 15);
                    k.this.a(new aq.u(32735, 33037, this.c - 99), 46173, 15);
                    k.this.a(new aq.u(32743, 33018, this.c - 99), 46168, 10);
                    while (!mob3.eX() || mob3.ag()) {
                        this.a(1000L);
                    }
                    door3.f();
                    int[] bossList = new int[]{46177, 46177, 46178, 46178, 46179};
                    t boss = (t)k.this.a(new aq.u(32834, 32770, this.c - 99), bossList[4], 1).get(0);
                    if (boss.z() == 46179) {
                        while (this.b.fu().c(boss.fu()) > 5) {
                            this.a(1000L);
                        }
                        boss.U(true);
                        int[] msgid = new int[]{18869, 18870, 18871, 18872, 18873, 18874};
                        int i4 = 0;
                        while (i4 < msgid.length) {
                            if (i4 == 1 || i4 == 3) {
                                boss.b(new cg(this.b, "$" + msgid[i4]));
                            } else {
                                boss.b(new cg(boss, "$" + msgid[i4]));
                            }
                            this.a(2000L);
                            ++i4;
                        }
                        boss.U(false);
                    }
                    while (!boss.eX()) {
                        this.a(1000L);
                    }
                    this.b();
                }
                catch (Exception exception) {
                    this.a();
                    d[this.c - 2699] = false;
                    System.out.println("[\u526f\u672c\u7d50\u675f]:\u706b\u9f8d\u5de2\u7a74(" + this.c + ")");
                }
            }
            finally {
                this.a();
                d[this.c - 2699] = false;
                System.out.println("[\u526f\u672c\u7d50\u675f]:\u706b\u9f8d\u5de2\u7a74(" + this.c + ")");
            }
        }

        private void a() {
            int i2 = 0;
            while (i2 < 2) {
                for (aa obj : aq.a().b()) {
                    if (obj.fp() != this.c - 99 * i2) continue;
                    if (obj instanceof u) {
                        u pc = (u)obj;
                        pc.j().a(310);
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
                ++i2;
            }
        }

        private void a(int maxTimeSec) throws InterruptedException {
            int count = -1;
            while (count++ < maxTimeSec) {
                if (this.b.fp() == this.c - 99) {
                    return;
                }
                this.a(1000L);
            }
            throw new InterruptedException();
        }

        private void a(long milliseconds) throws InterruptedException {
            Thread.sleep(milliseconds);
            if (this.b.fp() != this.c && this.b.fp() != this.c - 99 || this.b.bE() == 0) {
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
                if (pc.fp() != this.c && pc.fp() != this.c - 99) continue;
                pc.a(serverbasepacket);
            }
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
    }
}

