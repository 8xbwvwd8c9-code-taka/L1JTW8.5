/*
 * Decompiled with CFR 0.152.
 */
package bc;

import aj.bf;
import ao.a;
import ao.az;
import ao.g;
import ao.i;
import ao.k;
import ao.m;
import ao.q;
import ap.u;
import aq.ap;
import aq.aq;
import aq.x;
import as.b;
import as.j;
import be.ac;
import be.bg;
import be.bn;
import be.bt;
import be.bu;
import be.bx;
import be.ca;
import be.cj;
import be.ck;
import be.cm;
import be.cy;
import be.dc;
import be.dn;
import be.do;
import be.ds;
import be.eq;
import be.es;
import be.n;
import be.o;
import be.v;
import bi.e;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class d
extends TimerTask {
    private static final Logger a = Logger.getLogger(d.class.getName());
    private final bj.d b;
    private final String c;
    private final String d;

    public d(bj.d _client, String _accountName, String _charName) {
        this.b = _client;
        this.c = _accountName;
        this.d = _charName;
    }

    public void a(long timeMill) {
        System.out.println("[Shift Server]:" + this.d + "\u8f49\u63db\u4f3a\u670d\u5668\u4e2d...");
        e.a().a(this, timeMill);
    }

    @Override
    public void run() {
        try {
            if (aq.a().a(this.d) != null) {
                e.a().a(this, 3000L);
                return;
            }
            System.out.println("[Shift Server]:" + this.d + "\u8f49\u63db\u4f3a\u670d\u5668...OK");
            this.a(this.b, this.c, this.d);
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    private void a(bj.d client, String accountName, String charName) {
        bh.a account = ao.a.a().c(accountName);
        try {
            u partner;
            client.a(account);
            client.a(new bu(0));
            u pc = u.b(charName);
            if (pc == null) {
                return;
            }
            int currentHpAtLoad = pc.ea();
            int currentMpAtLoad = pc.eb();
            pc.aC(1);
            ao.o.a().c(pc);
            aq.a().a(pc);
            pc.a(client);
            client.a(pc);
            pc.a(new bt());
            ao.a.a().b(account, true);
            if (pc.fp() == 4) {
                int val = (pc.fr() + Calendar.getInstance().get(5)) % 3;
                pc.bw(4 + val);
                if (pc.dX() == 4) {
                    pc.cG(32734);
                    pc.cH(32756);
                } else if (pc.dX() == 5) {
                    pc.cG(32663);
                    pc.cH(32890);
                } else {
                    pc.cG(32732);
                    pc.cH(33040);
                }
                pc.cE(10500);
            } else if (pc.fp() >= 10500 && pc.fp() <= 10502) {
                pc.cG(32612);
                pc.cH(33186);
                pc.cE(4);
                pc.bw(-1);
            }
            aq.a().c(pc);
            i.a().a(pc);
            bf.a(pc);
            pc.a(new dn(68));
            pc.a(new dn(67, pc.cP()));
            pc.ad();
            pc.a(new n(pc));
            pc.a(new ck(pc));
            pc.a(new bx(pc.fp(), pc.fq().g()));
            pc.a(new cj(pc));
            pc.a(new dc(485, pc));
            bh.d[] dArray = g.a().b();
            int n2 = dArray.length;
            int n3 = 0;
            while (n3 < n2) {
                bh.d ca2 = dArray[n3];
                pc.a(new o(ca2.a(), ca2.h() > 0 ? ca2.h() : 0));
                ++n3;
            }
            pc.i();
            pc.a(new bg(pc));
            pc.a(new dc(487, 1));
            pc.a(new dc(487, 2));
            pc.a(new dc(487, 3));
            pc.a(new dc(490, pc));
            pc.a(new dc(489, pc));
            pc.a(new es(aq.a().j()));
            bf.b(pc);
            bf.d(pc);
            m.a().b(pc);
            pc.fg();
            pc.a(new do(pc));
            pc.a(new bn(pc));
            pc.a(new cm(132, pc.u()));
            if (pc.ea() > 0) {
                pc.X(false);
                pc.cq(0);
            } else {
                pc.X(true);
                pc.cq(8);
            }
            if (pc.ev() >= 51 && pc.ev() - 50 > pc.bA() && pc.bf() + pc.bh() + pc.bg() + pc.bj() + pc.bk() + pc.bi() < 270) {
                int bonus = pc.ev() - 50 - pc.bA();
                pc.a(new ca(479, "" + bonus));
            }
            bf.c(pc);
            as.b.a().a(pc);
            if (pc.aF() != 0) {
                aq.i clan = q.a().a(pc.aF());
                if (clan != null) {
                    pc.a(new ac(pc));
                    pc.a(new cm(173, clan.j()));
                    pc.a(new cy(clan));
                    for (u clanMember : clan.b()) {
                        if (clanMember.fr() != pc.fr()) {
                            clanMember.a(new ds(843, pc.et()));
                        }
                        if (clan.b().size() < 3 || clanMember.bB(4084)) continue;
                        clanMember.j(4084, 0);
                        clanMember.a(new cm(180, 450, 3240, 1));
                    }
                    ap currentWar = aq.a().c(clan.f());
                    if (currentWar != null) {
                        for (aq.i enemy_clan : currentWar.c(clan.f())) {
                            if (!enemy_clan.f().contains("\u5b89\u5b89\u59b3\u597d\u518d\u898b_")) {
                                pc.a(new eq(8, clan.f(), enemy_clan.f()));
                            } else {
                                pc.a(new ds(235, enemy_clan.f()));
                            }
                            if (currentWar.c() != 1) continue;
                            pc.a(new dc(76, pc));
                        }
                    }
                } else {
                    pc.ah(0);
                    pc.c("");
                    pc.ai(0);
                    pc.I();
                }
            }
            if (pc.bD() != 0 && (partner = (u)aq.a().a(pc.bD())) != null && partner.bD() != 0 && pc.bD() == partner.fr() && partner.bD() == pc.fr()) {
                pc.a(new ds(548));
                partner.a(new ds(549));
            }
            if (currentHpAtLoad > pc.ea()) {
                pc.a(currentHpAtLoad);
            }
            if (currentMpAtLoad > pc.eb()) {
                pc.i_(currentMpAtLoad);
            }
            pc.a();
            pc.c();
            pc.e();
            pc.G();
            pc.f();
            pc.I();
            pc.ac();
            pc.ae();
            if (!(pc.bB(25009) || pc.bB(25010) || pc.bB(25011))) {
                pc.j(25009, 180000);
                pc.a(new cm(150, 1, 180));
            }
            x.a().b(pc);
            pc.a(new v(37, client.e().p()));
            pc.a(new dc(559, pc));
            pc.a(new dc(560, pc));
            pc.a(new cm(189));
            pc.a(new dc(103, 4126));
            k.a().a(pc);
            ao.j.a().b(pc);
            az.a().b(pc);
            if (pc.l()) {
                pc.j(26003, 0);
            }
            pc.a(new dc(141));
            if (pc.fp() == 10500) {
                j.a().a(pc);
            }
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}

