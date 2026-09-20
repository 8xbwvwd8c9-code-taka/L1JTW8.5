/*
 * Decompiled with CFR 0.152.
 */
package as;

import ao.ah;
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
import be.be;
import be.cm;
import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class h {
    private static final Logger a = Logger.getLogger(h.class.getName());
    private static h b;
    private static final int c = 1400;
    private static final int d = 99;
    private static final int e = 14400000;
    private final ConcurrentHashMap<Integer, a> f = new ConcurrentHashMap();

    public static h a() {
        if (b == null) {
            b = new h();
        }
        return b;
    }

    private h() {
        b map = ax.d.b().a(1400);
        int i2 = 1;
        while (i2 < 99) {
            try {
                b clone = map.s();
                clone.a = 1400 + i2;
                ao.a().a(clone);
                ax.d.b().a().put(clone.a, clone);
            }
            catch (CloneNotSupportedException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            ++i2;
        }
    }

    public int b() {
        int i2 = 1400;
        while (i2 < 1499) {
            if (!this.f.containsKey(i2)) {
                return i2;
            }
            ++i2;
        }
        return -1;
    }

    public void a(u pc, t npc) {
        boolean isValid = false;
        for (q item : pc.j().d()) {
            if (item.N() != 640615) continue;
            if (item.bb() == null || System.currentTimeMillis() > item.bb().getTime()) {
                pc.j().f(item);
                continue;
            }
            isValid = true;
        }
        if (isValid) {
            pc.a(new be(npc.fr(), "bosskey6"));
        } else {
            pc.a(new be(npc.fr(), "bosskey4"));
        }
    }

    public void b(u pc, t npc) {
        q key = pc.j().b(640615);
        if (key == null || key.bb() == null) {
            pc.a(new be(npc.fr(), "bosskey2"));
            return;
        }
        if (System.currentTimeMillis() > key.bb().getTime()) {
            pc.a(new be(npc.fr(), "bosskey2"));
            return;
        }
        int roomid = key.M();
        if (roomid == 0) {
            pc.a(new be(npc.fr(), "bosskey2"));
            return;
        }
        am.a(pc, 32901, 32814, roomid, 5, true);
    }

    public void a(u pc, int count, t npc) {
        if (pc.j().f(640615)) {
            pc.a(new be(npc.fr(), "bosskey6"));
            return;
        }
        if (!pc.j().b(40308, 200 * count)) {
            pc.a(new be(npc.fr(), "bosskey5"));
            return;
        }
        int room = this.b();
        if (room < 0) {
            pc.a(new be(npc.fr(), "bosskey3"));
            return;
        }
        bg.a(190328, 32902, 32818, room);
        a rut = new a(room);
        rut.a();
        this.f.put(room, rut);
        int i2 = 0;
        while (i2 < count) {
            q keys = ah.a().b(640615);
            keys.b(new Timestamp(System.currentTimeMillis() + 14400000L));
            keys.j(room);
            if (pc.j().a(keys, count) == 0) {
                pc.j().d(keys);
            } else {
                aq.a().a(pc.fs(), pc.ft(), pc.fp()).d(keys);
            }
            ++i2;
        }
        pc.a(new be(npc.fr(), "bosskey7"));
    }

    public void a(u pc, String s2, t npc) {
        int stoneID = 0;
        int npcid = 0;
        String msg = "";
        if (s2.equals("A")) {
            stoneID = 640520;
            npcid = 190839;
            msg = "$8473";
        } else if (s2.equals("B")) {
            stoneID = 640521;
            npcid = 190833;
            msg = "$8474";
        } else if (s2.equals("C")) {
            stoneID = 640522;
            npcid = 45649;
            msg = "$8475";
        } else if (s2.equals("D")) {
            stoneID = 640523;
            npcid = 45685;
            msg = "$8476";
        } else if (s2.equals("d")) {
            stoneID = 640616;
            npcid = 45600;
            msg = "$18977";
        } else if (s2.equals("E")) {
            stoneID = 640524;
            npcid = 97374;
            msg = "$9267";
        } else if (s2.equals("F")) {
            stoneID = 640252;
            npcid = 97373;
            msg = "$9268";
        } else if (s2.equals("G")) {
            stoneID = 640526;
            npcid = 97376;
            msg = "$9269";
        } else if (s2.equals("H")) {
            stoneID = 640527;
            npcid = 97371;
            msg = "$9270";
        } else if (s2.equals("I")) {
            stoneID = 640528;
            npcid = 97375;
            msg = "$9271";
        } else if (s2.equals("J")) {
            stoneID = 640529;
            npcid = 97377;
            msg = "$9272";
        } else if (s2.equals("K")) {
            stoneID = 640530;
            npcid = 97372;
            msg = "$9273";
        } else if (s2.equals("L")) {
            stoneID = 640531;
            npcid = 97370;
            msg = "$9274";
        } else if (s2.equals("M")) {
            stoneID = 640532;
            npcid = 97367;
            msg = "$9275";
        } else if (s2.equals("N")) {
            stoneID = 640533;
            npcid = 97358;
            msg = "$9276";
        } else if (s2.equals("O")) {
            stoneID = 640534;
            npcid = 97359;
            msg = "$9277";
        } else if (s2.equals("P")) {
            stoneID = 640535;
            npcid = 97360;
            msg = "$9278";
        } else if (s2.equals("Q")) {
            stoneID = 640332;
            npcid = 190800;
            msg = "$15707";
        } else if (s2.equals("S")) {
            stoneID = 640333;
            npcid = 45601;
            msg = "$15708";
        } else {
            s2.equals("e");
        }
        if (stoneID * npcid == 0) {
            return;
        }
        if (pc.j().b(stoneID, 1)) {
            bg.a(npcid, 32878, 32816, npc.fp());
            pc.a(new be(npc.fr(), ""));
            for (aa obj : aq.a().b(npc.fp()).values()) {
                if (!(obj instanceof u)) continue;
                u each = (u)obj;
                each.a(new cm(84, 2, msg));
            }
        } else {
            pc.a(new be(npc.fr(), "bosskey10"));
        }
    }

    private class a
    extends TimerTask {
        private int b = 0;

        private a(int _roomid) {
            this.b = _roomid;
        }

        private void a() {
            bi.e.a().a(this, 14400000L);
        }

        @Override
        public void run() {
            try {
                for (aa obj : aq.a().b()) {
                    if (obj.fp() != this.b) continue;
                    if (obj instanceof u) {
                        u pc = (u)obj;
                        am.a(pc, 33486, 32765, 4, 5, true);
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
                if (h.this.f.containsKey(this.b)) {
                    h.this.f.remove(this.b);
                    System.out.println("room=" + this.b + " is released");
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

