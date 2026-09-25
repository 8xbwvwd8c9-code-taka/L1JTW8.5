/*
 * Decompiled with CFR 0.152.
 */
package as;

import ao.ah;
import ao.bg;
import ap.q;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import be.cm;
import be.dc;
import be.ei;
import be.eu;
import bi.i;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class j {
    private static final Logger a = Logger.getLogger(j.class.getName());
    private static j b;
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

    public static j a() {
        if (b == null) {
            b = new j();
        }
        return b;
    }

    public void b() {
        int[] mapids;
        aq.a().a(new ei("\\aE$21837"));
        int[] nArray = mapids = new int[]{10500, 10501, 10502};
        int n2 = mapids.length;
        int n3 = 0;
        while (n3 < n2) {
            int mapid = nArray[n3];
            for (aa obj : aq.a().b(mapid).values()) {
                if (obj instanceof q) {
                    q item = (q)obj;
                    au.e groundInventory = aq.a().a(item.fs(), item.ft(), item.fp());
                    groundInventory.f(item);
                    continue;
                }
                if (!(obj instanceof u)) continue;
                u pc = (u)obj;
                int count = pc.j().g(640820);
                if (pc.dX() == this.l) {
                    if (count >= 5000) {
                        ah.a(pc, 640821, 1);
                    }
                    if (this.l == 4) {
                        am.a(pc, 32734, 32756, 10500, 5, true);
                    } else if (this.l == 5) {
                        am.a(pc, 32663, 32890, 10500, 5, true);
                    } else if (this.l == 6) {
                        am.a(pc, 32732, 33040, 10500, 5, true);
                    }
                } else {
                    if (count >= 5000) {
                        ah.a(pc, 640822, 1);
                    }
                    pc.a(new dc(113, pc));
                }
                pc.j().a(640820);
            }
            ++n3;
        }
        this.c = 1800;
        if (this.l == 4) {
            bg.a(46124, 32771, 32895, 10502, 5, this.c * 1000);
        } else if (this.l == 5) {
            bg.a(46123, 32771, 32895, 10502, 5, this.c * 1000);
        } else if (this.l == 6) {
            bg.a(190577, 32771, 32895, 10502, 5, this.c * 1000);
        }
        this.a(new dc(540, as.j.a().c(), "$21831"));
        bi.e.a().a(new a(), 1000L);
    }

    private j() {
        int n2 = 21;
        int hour = Calendar.getInstance().get(11);
        int minute = Calendar.getInstance().get(12);
        long timeMill = 0L;
        timeMill = hour >= 21 ? (long)((1440 + ((21 - hour) * 60 - minute)) * 60 * 1000) : (long)(((21 - hour) * 60 - minute) * 60 * 1000);
        this.a(timeMill);
    }

    private void a(long timeMill) {
        System.out.println("\u5e95\u6bd4\u65af\u50b3\u9001\u9580:\u8ddd\u96e2\u518d\u6b21\u57f7\u884c\u6642\u9593\u9084\u6709..." + timeMill / 1000L / 60L + "\u5206");
        bi.e.a().a(new f(), timeMill);
    }

    private void a(int delaySec, String[] message) {
        bi.e.a().a(new c(message), delaySec * 1000);
    }

    private void g() {
        String[] texts;
        ArrayList<g> list_4 = new ArrayList<g>();
        ArrayList<g> list_5 = new ArrayList<g>();
        ArrayList<g> list_6 = new ArrayList<g>();
        int score_4 = 0;
        int score_5 = 0;
        int score_6 = 0;
        for (u pc : aq.a().c()) {
            if (pc.fp() < 10500 || pc.fp() > 10502) continue;
            g tr = new g();
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
        Collections.sort(list_4, new Comparator<g>(){

            public int a(g r1, g r2) {
                return r2.b - r1.b;
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((g)object, (g)object2);
            }
        });
        Collections.sort(list_5, new Comparator<g>(){

            public int a(g r1, g r2) {
                return r2.b - r1.b;
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((g)object, (g)object2);
            }
        });
        Collections.sort(list_6, new Comparator<g>(){

            public int a(g r1, g r2) {
                return r2.b - r1.b;
            }

            @Override
            public /* synthetic */ int compare(Object object, Object object2) {
                return this.a((g)object, (g)object2);
            }
        });
        for (u pc : aq.a().c()) {
            if (pc.fp() < 10500 || pc.fp() > 10502) continue;
            if (pc.dX() == 4) {
                pc.a(new dc(this.e, pc.et(), list_4.toArray(new g[0])));
            } else if (pc.dX() == 5) {
                pc.a(new dc(this.f, pc.et(), list_5.toArray(new g[0])));
            } else if (pc.dX() == 6) {
                pc.a(new dc(this.g, pc.et(), list_6.toArray(new g[0])));
            }
            this.c(pc);
            int[] point = new int[]{this.h, this.i, this.j};
            pc.a(new dc(this.k, point));
        }
    }

    public void a(u pc) {
        ArrayList list = new ArrayList();
        int[] point = new int[]{this.h, this.i, this.j};
        if (pc.dX() == 4) {
            pc.a(new dc(this.e, pc.et(), list.toArray(new g[0])));
        } else if (pc.dX() == 5) {
            pc.a(new dc(this.f, pc.et(), list.toArray(new g[0])));
        } else if (pc.dX() == 6) {
            pc.a(new dc(this.g, pc.et(), list.toArray(new g[0])));
        }
        this.c(pc);
        pc.a(new dc(540, as.j.a().d(), "$21830"));
        pc.a(new dc(this.k, point));
    }

    private void c(u pc) {
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
            pc.a(new dc(5016, -1, 10, 7089, 0, 4542, 0, 0, 1));
            pc.bz(5017);
            pc.a(new dc(110, 5017));
        }
    }

    public void a(int camp) {
        int period = 480;
        int[] point = new int[]{this.h, this.i, this.j};
        this.a(new dc(480, point));
        this.k = 480;
        bi.e.a().a(new b(), 1000L);
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

    private void a(eu serverbasepacket) {
        for (u pc : aq.a().c()) {
            if (pc.fp() < 10500 || pc.fp() > 10502) continue;
            pc.a(serverbasepacket);
        }
    }

    public void b(u pc) {
        if (pc.fp() >= 10500 && pc.fp() <= 10502) {
            if (as.j.a().d() > 0) {
                pc.a(new dc(540, as.j.a().d(), "$21830"));
            } else if (as.j.a().c() > 0) {
                pc.a(new dc(540, as.j.a().c(), "$21831"));
            }
            if (pc.fp() != 10500) {
                if (!pc.bB(5016) && !pc.bB(5017)) {
                    pc.j(5017, 0);
                    pc.a(new dc(5017, -1, 10, 7088, 0, 4541, 0, 0, 1));
                }
            } else if (pc.bB(5017)) {
                pc.bz(5017);
                pc.a(new dc(110, 5017));
            }
        } else if (pc.bB(5017)) {
            pc.bz(5017);
            pc.a(new dc(110, 5017));
        } else if (pc.bB(5016)) {
            pc.bz(5016);
            pc.a(new dc(110, 5016));
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
        for (aa obj : aq.a().b(10502).values()) {
            ap.i field;
            if (!(obj instanceof ap.i) || (field = (ap.i)obj).fe() != oldGfx) continue;
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

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                if (j.this.c > 1) {
                    j j2 = j.this;
                    j2.c = j2.c - 1;
                    bi.e.a().a(new a(), 1000L);
                    return;
                }
                for (u pc : aq.a().c()) {
                    if (pc.fp() < 10500 || pc.fp() > 10502) continue;
                    pc.a(new dc(113, pc));
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class b
    extends TimerTask {
        private b() {
        }

        @Override
        public void run() {
            try {
                j j2 = j.this;
                j2.k = j2.k - 1;
                if (j.this.k > 0) {
                    bi.e.a().a(new b(), 1000L);
                    return;
                }
                int[] counts = new int[3];
                for (u pc : aq.a().c()) {
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
                    j j3 = j.this;
                    j3.h = j3.h + 1;
                    campText = "\\fR[$9676]";
                } else if (counts[1] == max) {
                    j j4 = j.this;
                    j4.i = j4.i + 1;
                    campText = "\\fB[$9677]";
                } else if (counts[2] == max) {
                    j j5 = j.this;
                    j5.j = j5.j + 1;
                    campText = "\\fA[$9675]";
                }
                for (u pc : aq.a().c()) {
                    if (pc.fp() != 10502) continue;
                    if (pc.dX() == 4) {
                        am.a(pc, 32772, 32822, 10502, 5, true);
                        continue;
                    }
                    if (pc.dX() == 5) {
                        am.a(pc, 32698, 32895, 10502, 5, true);
                        continue;
                    }
                    if (pc.dX() != 6) continue;
                    am.a(pc, 32773, 32965, 10502, 5, true);
                }
                int[] point = new int[]{j.this.h, j.this.i, j.this.j};
                j.this.a(new dc(0, point));
                j.this.h();
                String[] texts = new String[]{String.valueOf(campText) + " \\fE$21821"};
                j.this.a(3, texts);
                if (j.this.d > 480) {
                    bg.a(190574 + bi.i.a(3), 32771, 32895, 10502);
                } else {
                    int max_point = Math.max(j.this.h, Math.max(j.this.i, j.this.j));
                    if (j.this.h == max_point && j.this.i == max_point) {
                        j.this.l = j.this.e > j.this.f ? 4 : 5;
                    } else if (j.this.i == max_point && j.this.j == max_point) {
                        j.this.l = j.this.f > j.this.g ? 5 : 6;
                    } else if (j.this.j == max_point && j.this.h == max_point) {
                        j.this.l = j.this.g > j.this.e ? 6 : 4;
                    } else if (j.this.h == max_point) {
                        j.this.l = 4;
                    } else if (j.this.i == max_point) {
                        j.this.l = 5;
                    } else if (j.this.j == max_point) {
                        j.this.l = 6;
                    }
                    String campText2 = "";
                    if (j.this.l == 4) {
                        campText2 = "\\fR[$9676]";
                    } else if (j.this.l == 5) {
                        campText2 = "\\fB[$9677]";
                    } else if (j.this.l == 6) {
                        campText2 = "\\fA[$9675]";
                    }
                    String[] texts2 = new String[]{String.valueOf(campText2) + " \\fE$21823", "\\fE$21825"};
                    j.this.a(3, texts2);
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class c
    extends TimerTask {
        private final String[] b;

        public c(String[] _message) {
            this.b = _message;
        }

        @Override
        public void run() {
            try {
                int i2 = 0;
                while (i2 < this.b.length) {
                    j.this.a(new cm(84, 2, this.b[i2]));
                    Thread.sleep(8000L);
                    ++i2;
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class d
    extends TimerTask {
        private d() {
        }

        @Override
        public void run() {
            try {
                if (j.this.d > 1) {
                    j j2 = j.this;
                    j2.d = j2.d - 1;
                    bi.e.a().a(new d(), 1000L);
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class e
    extends TimerTask {
        private e() {
        }

        @Override
        public void run() {
            try {
                if (j.this.d <= 30) {
                    return;
                }
                j.this.g();
                bi.e.a().a(new e(), 30000L);
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    private class f
    extends TimerTask {
        private f() {
        }

        @Override
        public void run() {
            try {
                j.this.i();
                j.this.l = 0;
                j.this.h = 0;
                j.this.i = 0;
                j.this.j = 0;
                j.this.e = 0;
                j.this.f = 0;
                j.this.g = 0;
                int period = 1800;
                bg.a(190574, 32771, 32895, 10502);
                bg.a(190554, 32620, 33181, 4, 5, 1800000L);
                System.out.println("[\u5e95\u6bd4\u65af\u50b3\u9001\u9580\u51fa\u73fe\u4e86] (32620,33181,4)");
                aq.a().a(new ei("\\aE$21836"));
                j.this.d = 1800;
                bi.e.a().a(new d(), 1000L);
                bi.e.a().a(new e(), 30000L);
                j.this.a(86400000L);
                String[] texts = new String[]{"\\fE$21824", "\\fE$21832", "\\fE$21833", "\\fE$21834"};
                j.this.a(180, texts);
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }

    public class g {
        public String a;
        public int b;
    }
}

