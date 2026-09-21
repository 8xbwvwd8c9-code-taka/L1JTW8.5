/*
 * Decompiled with CFR 0.152.
 */
package as;

import ai.d;
import ao.g;
import ao.q;
import ap.aa;
import ap.r;
import ap.t;
import ap.u;
import aq.aj;
import aq.am;
import aq.ap;
import aq.aq;
import aq.e;
import aq.f;
import aq.i;
import be.cm;
import be.dc;
import be.ei;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class b {
    private static final Logger a = Logger.getLogger(b.class.getName());
    private static b b;

    private b() {
        int minSpawnDate = 0;
        for (bh.d castle : g.a().c().values()) {
            if (castle.g() == 0) continue;
            int date = (int)(castle.c().getTimeInMillis() / 1000L);
            if (minSpawnDate != 0 && date >= minSpawnDate) continue;
            minSpawnDate = date;
        }
        aq.a().b[3] = minSpawnDate;
        bi.e.a().a(new a(), 1000L, 1000L);
    }

    public static b a() {
        if (b == null) {
            b = new b();
        }
        return b;
    }

    public boolean a(int castle_id) {
        bh.d castle = g.a().a(castle_id);
        if (castle == null) {
            return false;
        }
        return castle.j();
    }

    public boolean a(f cha) {
        int castleId = e.a(cha);
        return castleId != 0 && this.a(castleId);
    }

    public void a(u pc) {
        ArrayList<String> list = new ArrayList<String>();
        for (bh.d castle : g.a().c().values()) {
            if (!castle.j()) continue;
            i clan = q.a().c("\u5b89\u5b89\u59b3\u597d\u518d\u898b_" + castle.a());
            int clanid = castle.g();
            if (clanid > 0) {
                clan = q.a().a(clanid);
            }
            list.add(clan.f());
        }
        if (!list.isEmpty()) {
            pc.a(new cm(80, list.toArray()));
        }
    }

    private void b(bh.d castle) {
        Object defClanWar;
        i npcClan;
        castle.a(true);
        aj.a().c(castle.a());
        if (castle.g() == 0) {
            npcClan = new i();
            npcClan.c(d.a().c());
            npcClan.e("\u5b89\u5b89\u59b3\u597d\u518d\u898b_" + castle.a());
            npcClan.g(castle.a());
            q.a().a(npcClan);
            aj.a().d(castle.a());
        } else {
            npcClan = new i();
            npcClan.c(d.a().c());
            npcClan.e("\u5b89\u5b89\u59b3\u597d\u518d\u898b_" + castle.a());
            q.a().a(npcClan);
            aj.a().e(castle.a());
            for (i defClan : q.a().b().values()) {
                if (defClan.m() != castle.a()) continue;
                defClanWar = aq.a().c(defClan.f());
                if (defClanWar == null) {
                    new ap(1, npcClan.f(), defClan.f());
                    break;
                }
                ((ap)defClanWar).a(npcClan);
                break;
            }
            aq.a().a(new dc(102, 16700 + castle.a(), "$16304"));
        }
        defClanWar = ao.t.b().c();
        int n2 = ((ap.f[])defClanWar).length;
        int n3 = 0;
        while (n3 < n2) {
            ap.f door = defClanWar[n3];
            if (e.a(castle.a(), door)) {
                door.h();
            }
            ++n3;
        }
        aq.a().a(new ei("\\aL" + castle.b() + "\u7684\u653b\u57ce\u6230\u958b\u59cb\u3002"));
        for (u pc : aq.a().c()) {
            i clan;
            if (pc.l() || !e.a(castle.a(), pc) || (clan = q.a().a(pc.aF())) != null && clan.m() == castle.a()) continue;
            int[] loc = e.e(castle.a());
            am.a(pc, loc[0], loc[1], loc[2], 5, true);
        }
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("[\u653b\u57ce\u6230\u958b\u59cb]" + castle.b() + "..(\u7d50\u675f\u6642\u9593:" + df2.format(castle.d().getTime()) + ")");
    }

    public void a(bh.d castle) {
        if (castle.j()) {
            castle.a(false);
            aq.a().a(new ei("\\aL" + castle.b() + "\u7684\u653b\u57ce\u6230\u7d50\u675f\u3002"));
            this.c(castle);
            for (aq.aa l1object : aq.a().b()) {
                t flag;
                if (l1object instanceof t && (flag = (t)l1object).z() == 81122 && e.a(castle.a(), flag)) {
                    flag.aa_();
                }
                if (l1object instanceof ap.d) {
                    ap.d crown = (ap.d)l1object;
                    if (e.a(castle.a(), crown)) {
                        crown.aa_();
                    }
                }
                if (l1object instanceof aa) {
                    aa tower = (aa)l1object;
                    if (e.a(castle.a(), tower)) {
                        tower.aa_();
                    }
                }
                if (!(l1object instanceof r)) continue;
                r keeper = (r)l1object;
                if (!e.a(castle.a(), keeper)) continue;
                keeper.aa_();
            }
            aj.a().a(castle.a());
            ap.f[] fArray = ao.t.b().c();
            int n2 = fArray.length;
            int n3 = 0;
            while (n3 < n2) {
                ap.f door = fArray[n3];
                if (e.a(castle.a(), door)) {
                    door.h();
                }
                ++n3;
            }
            q.a().a("\u5b89\u5b89\u59b3\u597d\u518d\u898b_" + castle.a());
            System.out.println("[\u653b\u57ce\u6230\u7d50\u675f]" + castle.b());
        } else {
            this.c(castle);
        }
    }

    private void c(bh.d castle) {
        castle.c().add(l1j.server.a.ak, l1j.server.a.aj);
        castle.a(10);
        g.a().a(castle);
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                Calendar now = Calendar.getInstance();
                for (bh.d castle : g.a().c().values()) {
                    if (!castle.j() && now.after(castle.c()) && now.before(castle.d())) {
                        b.this.b(castle);
                        continue;
                    }
                    if (!now.after(castle.d())) continue;
                    b.this.a(castle);
                }
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

