/*
 * Decompiled with CFR 0.152.
 */
package l1r.as;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ItemTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1FieldObjectInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.au.L1GroundInventory;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_SystemMessage;
import l1r.be.ServerBasePacket;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1ThebesBattle {
    private static final Logger a = Logger.getLogger(L1ThebesBattle.class.getName());
    private static L1ThebesBattle b;
    private int c = 0;
    private int d = 0;
    private int e = 0;
    private int f = 0;
    private int g = 0;
    private int h = 0;
    private int i = 0;
    private int j = 0;
    private int k = 0;
    private int l = 0;

    public static L1ThebesBattle a() {
        if (b == null) {
            b = new L1ThebesBattle();
        }
        return b;
    }

    public void b() {
        int[] mapids;
        L1World.a().a(new S_SystemMessage("\\aE$21837"));
        int[] nArray = mapids = new int[]{10500, 10501, 10502};
        int n = mapids.length;
        int n2 = 0;
        while (n2 < n) {
            int mapid = nArray[n2];
            for (L1Object obj : L1World.a().b(mapid).values()) {
                if (obj instanceof L1ItemInstance) {
                    L1ItemInstance item = (L1ItemInstance)obj;
                    L1GroundInventory groundInventory = L1World.a().a(item.fs(), item.ft(), item.fp());
                    groundInventory.f(item);
                    continue;
                }
                if (!(obj instanceof L1PcInstance)) continue;
                L1PcInstance pc = (L1PcInstance)obj;
                int count = pc.j().g(640820);
                if (pc.dX() == this.l) {
                    if (count >= 5000) {
                        ItemTable.a(pc, 640821, 1);
                    }
                    if (this.l == 4) {
                        L1Teleport.a(pc, 32734, 32756, 10500, 5, true);
                    } else if (this.l == 5) {
                        L1Teleport.a(pc, 32663, 32890, 10500, 5, true);
                    } else if (this.l == 6) {
                        L1Teleport.a(pc, 32732, 33040, 10500, 5, true);
                    }
                } else {
                    if (count >= 5000) {
                        ItemTable.a(pc, 640822, 1);
                    }
                    pc.a(new S_ProtoBuffers(113, pc));
                }
                pc.j().a(640820);
            }
            ++n2;
        }
        this.c = 1800;
        if (this.l == 4) {
            SpawnTable.a(46124, 32771, 32895, 10502, 5, this.c * 1000);
        } else if (this.l == 5) {
            SpawnTable.a(46123, 32771, 32895, 10502, 5, this.c * 1000);
        } else if (this.l == 6) {
            SpawnTable.a(190577, 32771, 32895, 10502, 5, this.c * 1000);
        }
        this.a(new S_ProtoBuffers(540, L1ThebesBattle.a().c(), "$21831"));
        GeneralThreadPool.a().a(new L1R_a(), 1000L);
    }

    private L1ThebesBattle() {
        int n = 21;
        int hour = Calendar.getInstance().get(11);
        int minute = Calendar.getInstance().get(12);
        long timeMill = 0L;
        timeMill = hour >= 21 ? (long)((1440 + ((21 - hour) * 60 - minute)) * 60 * 1000) : (long)(((21 - hour) * 60 - minute) * 60 * 1000);
        this.a(timeMill);
    }

    private void a(long timeMill) {
        System.out.println("\u5e95\u6bd4\u65af\u50b3\u9001\u9580:\u8ddd\u96e2\u518d\u6b21\u57f7\u884c\u6642\u9593\u9084\u6709..." + timeMill / 1000L / 60L + "\u5206");
        GeneralThreadPool.a().a(new L1R_f(), timeMill);
    }

    private void a(int delaySec, String[] message) {
        GeneralThreadPool.a().a(new L1R_c(message), delaySec * 1000);
    }

    private void g() {
        String[] texts;
        ArrayList<L1R_g> list_4 = new ArrayList<L1R_g>();
        ArrayList<L1R_g> list_5 = new ArrayList<L1R_g>();
        ArrayList<L1R_g> list_6 = new ArrayList<L1R_g>();
        int score_4 = 0;
        int score_5 = 0;
        int score_6 = 0;
        for (L1PcInstance pc : L1World.a().c()) {
            if (pc.fp() < 10500 || pc.fp() > 10502) continue;
            L1R_g tr = new L1R_g();
            if (pc.dX() == 4) {
                tr.a = pc.et();
                tr.b = pc.j().g(640820);
                score_4 += tr.b;
                list_4.add(tr);
                continue;
            }
            if (pc.dX() == 5) {
                tr.a = pc.et();
                tr.b = pc.j().g(640820);
                score_5 += tr.b;
                list_5.add(tr);
                continue;
            }
            if (pc.dX() != 6) continue;
            tr.a = pc.et();
            tr.b = pc.j().g(640820);
            score_6 += tr.b;
            list_6.add(tr);
        }
        if (score_4 > this.e) {
            if (score_4 >= 600000 && this.e < 600000) {
                texts = new String[]{"\\fR[$9676] \\fE$21812"};
                this.a(1, texts);
            }
            this.e = score_4;
        }
        if (score_5 > this.f) {
            if (score_5 >= 600000 && this.f < 600000) {
                texts = new String[]{"\\fB[$9677] \\fE$21812"};
                this.a(1, texts);
            }
            this.f = score_5;
        }
        if (score_6 > this.g) {
            if (score_6 >= 600000 && this.g < 600000) {
                texts = new String[]{"\\fA[$9675] \\fE$21812"};
                this.a(1, texts);
            }
            this.g = score_6;
        }
        Collections.sort(list_4, new Comparator<L1R_g>(){

            public int a(L1R_g r1, L1R_g r2) {
                return r2.b - r1.b;
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((L1R_g)object, (L1R_g)object2);
            }
        });
        Collections.sort(list_5, new Comparator<L1R_g>(){

            public int a(L1R_g r1, L1R_g r2) {
                return r2.b - r1.b;
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((L1R_g)object, (L1R_g)object2);
            }
        });
        Collections.sort(list_6, new Comparator<L1R_g>(){

            public int a(L1R_g r1, L1R_g r2) {
                return r2.b - r1.b;
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((L1R_g)object, (L1R_g)object2);
            }
        });
        for (L1PcInstance pc : L1World.a().c()) {
            if (pc.fp() < 10500 || pc.fp() > 10502) continue;
            if (pc.dX() == 4) {
                pc.a(new S_ProtoBuffers(this.e, pc.et(), list_4.toArray(new L1R_g[0])));
            } else if (pc.dX() == 5) {
                pc.a(new S_ProtoBuffers(this.f, pc.et(), list_5.toArray(new L1R_g[0])));
            } else if (pc.dX() == 6) {
                pc.a(new S_ProtoBuffers(this.g, pc.et(), list_6.toArray(new L1R_g[0])));
            }
            this.c(pc);
            int[] point = new int[]{this.h, this.i, this.j};
            pc.a(new S_ProtoBuffers(this.k, point));
        }
    }

    public void a(L1PcInstance pc) {
        ArrayList list = new ArrayList();
        int[] point = new int[]{this.h, this.i, this.j};
        if (pc.dX() == 4) {
            pc.a(new S_ProtoBuffers(this.e, pc.et(), list.toArray(new L1R_g[0])));
        } else if (pc.dX() == 5) {
            pc.a(new S_ProtoBuffers(this.f, pc.et(), list.toArray(new L1R_g[0])));
        } else if (pc.dX() == 6) {
            pc.a(new S_ProtoBuffers(this.g, pc.et(), list.toArray(new L1R_g[0])));
        }
        this.c(pc);
        pc.a(new S_ProtoBuffers(540, L1ThebesBattle.a().d(), "$21830"));
        pc.a(new S_ProtoBuffers(this.k, point));
    }

    private void c(L1PcInstance pc) {
        int score = 0;
        if (pc.dX() == 4) {
            score = this.e;
        } else if (pc.dX() == 5) {
            score = this.f;
        } else if (pc.dX() == 6) {
            score = this.g;
        }
        if (score >= 600000 && !pc.bB(5016)) {
            pc.j(5016, 0);
            pc.a(new S_ProtoBuffers(5016, -1, 10, 7089, 0, 4542, 0, 0, 1));
            pc.bz(5017);
            pc.a(new S_ProtoBuffers(110, 5017));
        }
    }

    public void a(int camp) {
        int period = 480;
        int[] point = new int[]{this.h, this.i, this.j};
        this.a(new S_ProtoBuffers(480, point));
        this.k = 480;
        GeneralThreadPool.a().a(new L1R_b(), 1000L);
        String campText2 = "";
        if (camp == 4) {
            campText2 = "\\fR[$9676]";
        } else if (camp == 5) {
            campText2 = "\\fB[$9677]";
        } else if (camp == 6) {
            campText2 = "\\fA[$9675]";
        }
        String[] texts = new String[]{String.valueOf(campText2) + " \\fE$21815", "\\fE$21816", "\\fE$21817", "\\fE$21818"};
        this.a(1, texts);
        String[] texts2 = new String[]{"\\fE$21819"};
        this.a(125, texts2);
    }

    private void a(ServerBasePacket serverbasepacket) {
        for (L1PcInstance pc : L1World.a().c()) {
            if (pc.fp() < 10500 || pc.fp() > 10502) continue;
            pc.a(serverbasepacket);
        }
    }

    public void b(L1PcInstance pc) {
        if (pc.fp() >= 10500 && pc.fp() <= 10502) {
            if (L1ThebesBattle.a().d() > 0) {
                pc.a(new S_ProtoBuffers(540, L1ThebesBattle.a().d(), "$21830"));
            } else if (L1ThebesBattle.a().c() > 0) {
                pc.a(new S_ProtoBuffers(540, L1ThebesBattle.a().c(), "$21831"));
            }
            if (pc.fp() != 10500) {
                if (!pc.bB(5016) && !pc.bB(5017)) {
                    pc.j(5017, 0);
                    pc.a(new S_ProtoBuffers(5017, -1, 10, 7088, 0, 4541, 0, 0, 1));
                }
            } else if (pc.bB(5017)) {
                pc.bz(5017);
                pc.a(new S_ProtoBuffers(110, 5017));
            }
        } else if (pc.bB(5017)) {
            pc.bz(5017);
            pc.a(new S_ProtoBuffers(110, 5017));
        } else if (pc.bB(5016)) {
            pc.bz(5016);
            pc.a(new S_ProtoBuffers(110, 5016));
        }
    }

    private void h() {
        if (this.h == 1) {
            this.a(13803, 13812);
        }
        if (this.h == 2) {
            this.a(13806, 13815);
        }
        if (this.h == 3) {
            this.a(13809, 13818);
        }
        if (this.i == 1) {
            this.a(13785, 13794);
        }
        if (this.i == 2) {
            this.a(13788, 13797);
        }
        if (this.i == 3) {
            this.a(13791, 13800);
        }
        if (this.j == 1) {
            this.a(13761, 13773);
        }
        if (this.j == 2) {
            this.a(13765, 13777);
        }
        if (this.j == 3) {
            this.a(13769, 13781);
        }
    }

    private void i() {
        this.a(13812, 13803);
        this.a(13815, 13806);
        this.a(13818, 13809);
        this.a(13773, 13761);
        this.a(13777, 13765);
        this.a(13781, 13769);
        this.a(13794, 13785);
        this.a(13797, 13788);
        this.a(13800, 13791);
    }

    private void a(int oldGfx, int newGfx) {
        for (L1Object obj : L1World.a().b(10502).values()) {
            L1FieldObjectInstance field;
            if (!(obj instanceof L1FieldObjectInstance) || (field = (L1FieldObjectInstance)obj).fe() != oldGfx) continue;
            field.cw(newGfx);
            break;
        }
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public int e() {
        return this.k;
    }

    private class L1R_a
    extends TimerTask {
        private L1R_a() {
        }

        @Override
        public void run() {
            try {
                if (L1ThebesBattle.this.c > 1) {
                    L1ThebesBattle l1ThebesBattle = L1ThebesBattle.this;
                    l1ThebesBattle.c = l1ThebesBattle.c - 1;
                    GeneralThreadPool.a().a(new L1R_a(), 1000L);
                    return;
                }
                for (L1PcInstance pc : L1World.a().c()) {
                    if (pc.fp() < 10500 || pc.fp() > 10502) continue;
                    pc.a(new S_ProtoBuffers(113, pc));
                }
            }
            catch (Exception e) {
                a.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }

    private class L1R_b
    extends TimerTask {
        private L1R_b() {
        }

        @Override
        public void run() {
            try {
                L1ThebesBattle l1ThebesBattle = L1ThebesBattle.this;
                l1ThebesBattle.k = l1ThebesBattle.k - 1;
                if (L1ThebesBattle.this.k > 0) {
                    GeneralThreadPool.a().a(new L1R_b(), 1000L);
                    return;
                }
                int[] counts = new int[3];
                for (L1PcInstance pc : L1World.a().c()) {
                    if (pc.fp() != 10502 || pc.fs() < 32765 || pc.fs() > 32777 || pc.ft() < 32889 || pc.ft() > 32901) continue;
                    if (pc.dX() == 4) {
                        counts[0] = counts[0] + 1;
                        continue;
                    }
                    if (pc.dX() == 5) {
                        counts[1] = counts[1] + 1;
                        continue;
                    }
                    if (pc.dX() != 6) continue;
                    counts[2] = counts[2] + 1;
                }
                String campText = "";
                int max = Math.max(counts[0], Math.max(counts[1], counts[2]));
                if (counts[0] == max) {
                    L1ThebesBattle l1ThebesBattle2 = L1ThebesBattle.this;
                    l1ThebesBattle2.h = l1ThebesBattle2.h + 1;
                    campText = "\\fR[$9676]";
                } else if (counts[1] == max) {
                    L1ThebesBattle l1ThebesBattle3 = L1ThebesBattle.this;
                    l1ThebesBattle3.i = l1ThebesBattle3.i + 1;
                    campText = "\\fB[$9677]";
                } else if (counts[2] == max) {
                    L1ThebesBattle l1ThebesBattle4 = L1ThebesBattle.this;
                    l1ThebesBattle4.j = l1ThebesBattle4.j + 1;
                    campText = "\\fA[$9675]";
                }
                for (L1PcInstance pc : L1World.a().c()) {
                    if (pc.fp() != 10502) continue;
                    if (pc.dX() == 4) {
                        L1Teleport.a(pc, 32772, 32822, 10502, 5, true);
                        continue;
                    }
                    if (pc.dX() == 5) {
                        L1Teleport.a(pc, 32698, 32895, 10502, 5, true);
                        continue;
                    }
                    if (pc.dX() != 6) continue;
                    L1Teleport.a(pc, 32773, 32965, 10502, 5, true);
                }
                int[] point = new int[]{L1ThebesBattle.this.h, L1ThebesBattle.this.i, L1ThebesBattle.this.j};
                L1ThebesBattle.this.a(new S_ProtoBuffers(0, point));
                L1ThebesBattle.this.h();
                String[] texts = new String[]{String.valueOf(campText) + " \\fE$21821"};
                L1ThebesBattle.this.a(3, texts);
                if (L1ThebesBattle.this.d > 480) {
                    SpawnTable.a(190574 + Random.a(3), 32771, 32895, 10502);
                } else {
                    int max_point = Math.max(L1ThebesBattle.this.h, Math.max(L1ThebesBattle.this.i, L1ThebesBattle.this.j));
                    if (L1ThebesBattle.this.h == max_point && L1ThebesBattle.this.i == max_point) {
                        L1ThebesBattle.this.l = L1ThebesBattle.this.e > L1ThebesBattle.this.f ? 4 : 5;
                    } else if (L1ThebesBattle.this.i == max_point && L1ThebesBattle.this.j == max_point) {
                        L1ThebesBattle.this.l = L1ThebesBattle.this.f > L1ThebesBattle.this.g ? 5 : 6;
                    } else if (L1ThebesBattle.this.j == max_point && L1ThebesBattle.this.h == max_point) {
                        L1ThebesBattle.this.l = L1ThebesBattle.this.g > L1ThebesBattle.this.e ? 6 : 4;
                    } else if (L1ThebesBattle.this.h == max_point) {
                        L1ThebesBattle.this.l = 4;
                    } else if (L1ThebesBattle.this.i == max_point) {
                        L1ThebesBattle.this.l = 5;
                    } else if (L1ThebesBattle.this.j == max_point) {
                        L1ThebesBattle.this.l = 6;
                    }
                    String campText2 = "";
                    if (L1ThebesBattle.this.l == 4) {
                        campText2 = "\\fR[$9676]";
                    } else if (L1ThebesBattle.this.l == 5) {
                        campText2 = "\\fB[$9677]";
                    } else if (L1ThebesBattle.this.l == 6) {
                        campText2 = "\\fA[$9675]";
                    }
                    String[] texts2 = new String[]{String.valueOf(campText2) + " \\fE$21823", "\\fE$21825"};
                    L1ThebesBattle.this.a(3, texts2);
                }
            }
            catch (Exception e) {
                a.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }

    private class L1R_c
    extends TimerTask {
        private final String[] b;

        public L1R_c(String[] _message) {
            this.b = _message;
        }

        @Override
        public void run() {
            try {
                int i = 0;
                while (i < this.b.length) {
                    L1ThebesBattle.this.a(new S_PacketBox(84, 2, this.b[i]));
                    Thread.sleep(8000L);
                    ++i;
                }
            }
            catch (Exception e) {
                a.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }

    private class L1R_d
    extends TimerTask {
        private L1R_d() {
        }

        @Override
        public void run() {
            try {
                if (L1ThebesBattle.this.d > 1) {
                    L1ThebesBattle l1ThebesBattle = L1ThebesBattle.this;
                    l1ThebesBattle.d = l1ThebesBattle.d - 1;
                    GeneralThreadPool.a().a(new L1R_d(), 1000L);
                }
            }
            catch (Exception e) {
                a.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }

    private class L1R_e
    extends TimerTask {
        private L1R_e() {
        }

        @Override
        public void run() {
            try {
                if (L1ThebesBattle.this.d <= 30) {
                    return;
                }
                L1ThebesBattle.this.g();
                GeneralThreadPool.a().a(new L1R_e(), 30000L);
            }
            catch (Exception e) {
                a.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }

    private class L1R_f
    extends TimerTask {
        private L1R_f() {
        }

        @Override
        public void run() {
            try {
                L1ThebesBattle.this.i();
                L1ThebesBattle.this.l = 0;
                L1ThebesBattle.this.h = 0;
                L1ThebesBattle.this.i = 0;
                L1ThebesBattle.this.j = 0;
                L1ThebesBattle.this.e = 0;
                L1ThebesBattle.this.f = 0;
                L1ThebesBattle.this.g = 0;
                int period = 1800;
                SpawnTable.a(190574, 32771, 32895, 10502);
                SpawnTable.a(190554, 32620, 33181, 4, 5, 1800000L);
                System.out.println("[\u5e95\u6bd4\u65af\u50b3\u9001\u9580\u51fa\u73fe\u4e86] (32620,33181,4)");
                L1World.a().a(new S_SystemMessage("\\aE$21836"));
                L1ThebesBattle.this.d = 1800;
                GeneralThreadPool.a().a(new L1R_d(), 1000L);
                GeneralThreadPool.a().a(new L1R_e(), 30000L);
                L1ThebesBattle.this.a(86400000L);
                String[] texts = new String[]{"\\fE$21824", "\\fE$21832", "\\fE$21833", "\\fE$21834"};
                L1ThebesBattle.this.a(180, texts);
            }
            catch (Exception e) {
                a.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }

    public class L1R_g {
        public String a;
        public int b;
    }
}
