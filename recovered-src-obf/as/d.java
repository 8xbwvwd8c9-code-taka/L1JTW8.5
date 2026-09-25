/*
 * Decompiled with CFR 0.152.
 */
package as;

import ao.bg;
import ap.q;
import ap.s;
import ap.t;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import au.e;
import be.ds;
import java.util.TimerTask;

public class d {
    private static d a;

    public static d a() {
        if (a == null) {
            a = new d();
        }
        return a;
    }

    public boolean a(int npcid, u pc) {
        if (npcid >= 190331 && npcid <= 190333 && pc.bB(4011)) {
            pc.a(new ds(3418));
            return true;
        }
        if (npcid >= 190334 && npcid <= 190336 && pc.bB(4012)) {
            pc.a(new ds(3418));
            return true;
        }
        if (npcid >= 190337 && npcid <= 190339 && pc.bB(4077)) {
            pc.a(new ds(3418));
            return true;
        }
        if (npcid == 190330) {
            am.a(pc, 32795, 32665, pc.fp(), 4, true);
        } else if (npcid == 190331) {
            am.a(pc, 32670, 32675, 1005, 4, true);
        } else if (npcid == 190332) {
            am.a(pc, 32670, 32675, 1029, 4, true);
        } else if (npcid == 190333) {
            am.a(pc, 32670, 32675, 1053, 4, true);
        } else if (npcid == 190343) {
            am.a(pc, 32988, 32842, pc.fp(), 4, true);
        } else if (npcid == 190334) {
            am.a(pc, 32927, 32667, 1011, 4, true);
        } else if (npcid == 190335) {
            am.a(pc, 32927, 32667, 1035, 4, true);
        } else if (npcid == 190336) {
            am.a(pc, 32927, 32667, 1059, 4, true);
        } else if (npcid == 190344) {
            am.a(pc, 32842, 32887, pc.fp(), 4, true);
        } else if (npcid == 190337) {
            am.a(pc, 32736, 32850, 1017, 4, true);
        } else if (npcid == 190338) {
            am.a(pc, 32736, 32850, 1041, 4, true);
        } else if (npcid == 190339) {
            am.a(pc, 32736, 32850, 1065, 4, true);
        } else {
            return false;
        }
        return true;
    }

    public void a(q item, u pc) {
        if (pc.fp() != 4) {
            pc.a(new ds(1892));
            return;
        }
        int castleId = aq.e.a(pc);
        if (castleId > 0) {
            pc.a(new ds(3274));
            return;
        }
        int spawnid = item.a().V();
        for (aa obj : aq.a().b(4).values()) {
            if (!(obj instanceof t) || ((t)obj).z() != spawnid) continue;
            pc.a(new ds(1537));
            return;
        }
        if (item.N() == 640626) {
            bg.a(97006, 32784, 32691, 1005, 30000L);
        } else if (item.N() == 640627) {
            bg.a(97006, 32784, 32691, 1029, 30000L);
        } else if (item.N() == 640628) {
            bg.a(97006, 32784, 32691, 1053, 30000L);
        } else if (item.N() == 640629) {
            bg.a(97044, 32947, 32843, 1011, 30000L);
        } else if (item.N() == 640630) {
            bg.a(97044, 32947, 32843, 1035, 30000L);
        } else if (item.N() == 640631) {
            bg.a(97044, 32947, 32843, 1059, 30000L);
        } else if (item.N() == 640632) {
            bg.a(97094, 32856, 32867, 1017, 30000L);
        } else if (item.N() == 640633) {
            bg.a(97094, 32856, 32867, 1041, 30000L);
        } else if (item.N() == 640634) {
            bg.a(97094, 32856, 32867, 1065, 30000L);
        }
        bg.a(spawnid, pc, 0, 10800000L);
        pc.j().b(item, 1);
        aq.a().a(new ds(2921));
    }

    public void a(int npcid) {
        int mapid = -1;
        if (npcid == 190331) {
            mapid = 1005;
        } else if (npcid == 190332) {
            mapid = 1029;
        } else if (npcid == 190333) {
            mapid = 1053;
        } else if (npcid == 190334) {
            mapid = 1011;
        } else if (npcid == 190335) {
            mapid = 1035;
        } else if (npcid == 190336) {
            mapid = 1059;
        } else if (npcid == 190337) {
            mapid = 1017;
        } else if (npcid == 190338) {
            mapid = 1041;
        } else if (npcid == 190339) {
            mapid = 1065;
        }
        if (mapid == -1) {
            return;
        }
        for (aa obj : aq.a().b(mapid).values()) {
            if (obj instanceof u) {
                u pc = (u)obj;
                am.a(pc, 33703, 32502, 4, 5, true);
                continue;
            }
            if (obj instanceof s) {
                s mob = (s)obj;
                mob.aa_();
                continue;
            }
            if (!(obj instanceof q)) continue;
            q item = (q)obj;
            e groundInventory = aq.a().a(item.fs(), item.ft(), item.fp());
            groundInventory.f(item);
        }
    }

    public void a(t npc) {
        bi.e.a().a(new a(npc), 180000L);
    }

    private class a
    extends TimerTask {
        private final t b;

        private a(t _npc) {
            this.b = _npc;
        }

        @Override
        public void run() {
            int doorNpcid = -1;
            if (this.b.fp() == 1005) {
                doorNpcid = 190331;
            } else if (this.b.fp() == 1029) {
                doorNpcid = 190332;
            } else if (this.b.fp() == 1053) {
                doorNpcid = 190333;
            } else if (this.b.fp() == 1011) {
                doorNpcid = 190334;
            } else if (this.b.fp() == 1035) {
                doorNpcid = 190335;
            } else if (this.b.fp() == 1059) {
                doorNpcid = 190336;
            } else if (this.b.fp() == 1017) {
                doorNpcid = 190337;
            } else if (this.b.fp() == 1041) {
                doorNpcid = 190338;
            } else if (this.b.fp() == 1065) {
                doorNpcid = 190339;
            }
            for (aa obj : aq.a().b(4).values()) {
                if (!(obj instanceof t) || ((t)obj).z() != doorNpcid) continue;
                ((t)obj).aa_();
            }
        }
    }
}

