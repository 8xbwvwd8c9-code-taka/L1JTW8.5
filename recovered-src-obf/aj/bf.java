/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.az;
import ao.be;
import ao.k;
import ao.m;
import ap.q;
import ap.u;
import ap.z;
import aq.ae;
import aq.ap;
import aq.aq;
import aq.e;
import aq.f;
import aq.i;
import aq.j;
import aq.o;
import aq.s;
import aq.x;
import az.d;
import be.ac;
import be.bg;
import be.bh;
import be.bn;
import be.bs;
import be.bt;
import be.bx;
import be.ca;
import be.cj;
import be.ck;
import be.cm;
import be.cy;
import be.dc;
import be.dn;
import be.ds;
import be.dx;
import be.eh;
import be.eq;
import be.n;
import be.v;
import bf.ag;
import bf.av;
import bf.bd;
import bf.bm;
import bf.cf;
import bf.cq;
import bf.cx;
import bf.do;
import bf.ea;
import bf.ef;
import bf.es;
import bf.eu;
import bf.ev;
import bf.ex;
import bf.ey;
import bf.fa;
import bf.ff;
import bf.fi;
import bf.fk;
import bf.fp;
import bf.ft;
import bf.fy;
import bf.gd;
import bf.ge;
import bf.gf;
import bf.p;
import bi.g;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;
import l1j.server.b;

public class bf
extends cv {
    private static final Logger a = Logger.getLogger(bf.class.getName());
    private static final String b = "[C] C_LoginToServer";

    public bf(byte[] abyte0, bj.d client) throws Exception {
        super(abyte0);
        u partner;
        int castle_id;
        String login = client.a();
        String charName = this.g();
        if (client.f() != null) {
            a.log(Level.SEVERE, "\u540c\u4e00\u500b\u89d2\u8272\u91cd\u8907\u767b\u5165\uff0c\u5f37\u5236\u5207\u65b7 " + client.g() + ") \u7684\u9023\u7d50");
            client.c();
            return;
        }
        u pc = u.b(charName);
        if (pc == null) {
            return;
        }
        bh.a account = client.e();
        if (account == null) {
            a.log(Level.SEVERE, "\u7121\u6548\u7684\u89d2\u8272\u540d\u7a31: char=" + charName + " account=" + login + " host=" + client.g());
            client.c();
            return;
        }
        if (account.o()) {
            a.log(Level.SEVERE, "\u540c\u4e00\u500b\u5e33\u865f\u96d9\u91cd\u89d2\u8272\u767b\u5165\uff0c\u5f37\u5236\u5207\u65b7 " + client.g() + ") \u7684\u9023\u7d50");
            client.c();
            return;
        }
        if (!login.equals(pc.bc())) {
            a.log(Level.SEVERE, "\u7121\u6548\u7684\u89d2\u8272\u540d\u7a31: char=" + charName + " account=" + login + " host=" + client.g());
            client.c();
            return;
        }
        if (pc.ay() < 0 || pc.ay() > 7) {
            a.log(Level.WARNING, "Reject non-playable character type during login: char=" + charName + " type=" + pc.ay());
            client.c();
            return;
        }
        System.out.println("\u89d2\u8272\u767b\u5165\u5230\u4f3a\u670d\u5668\u4e2d: char=" + charName + " account=" + login + " host=" + client.g());
        int currentHpAtLoad = pc.ea();
        int currentMpAtLoad = pc.eb();
        pc.aC(1);
        ao.o.a().c(pc);
        aq.a().a(pc);
        pc.a(client);
        client.a(pc);
        pc.a(new bt());
        ao.a.a().b(account, true);
        if (pc.fp() >= 4000 && pc.fp() <= 4050) {
            pc.cG(Short.MAX_VALUE);
            pc.cH(32831);
            pc.cE(2400);
        } else if (pc.fp() >= 2600 && pc.fp() <= 2797) {
            pc.cG(33703);
            pc.cH(32502);
            pc.cE(4);
        }
        ao.z.a().a(pc);
        if (l1j.server.a.Z) {
            int[] loc = o.a(pc);
            pc.cG(loc[0]);
            pc.cH(loc[1]);
            pc.cE(loc[2]);
        }
        if ((castle_id = e.a(pc)) > 0 && as.b.a().a(castle_id)) {
            int[] loc;
            i clan = ao.q.a().a(pc.aF());
            if (clan != null) {
                if (clan.m() != castle_id) {
                    loc = new int[3];
                    loc = e.e(castle_id);
                    pc.cG(loc[0]);
                    pc.cH(loc[1]);
                    pc.cE(loc[2]);
                }
            } else {
                loc = new int[3];
                loc = e.e(castle_id);
                pc.cG(loc[0]);
                pc.cH(loc[1]);
                pc.cE(loc[2]);
            }
        }
        aq.a().c(pc);
        ao.i.a().a(pc);
        this.e(pc);
        bf.a(pc);
        pc.a(new dn(68));
        pc.a(new dn(67, pc.cP()));
        pc.ad();
        pc.a(new n(pc));
        pc.a(new ck(pc));
        pc.a(new bx(pc.fp(), pc.fq().g()));
        pc.a(new cj(pc));
        pc.a(new dc(485, pc));
        bh.d[] dArray = ao.g.a().b();
        int n2 = dArray.length;
        int loc = 0;
        while (loc < n2) {
            bh.d ca2 = dArray[loc];
            pc.a(new be.o(ca2.a(), ca2.h() > 0 ? ca2.h() : 0));
            ++loc;
        }
        pc.i();
        pc.a(new bg(pc));
        pc.a(new dc(487, 1));
        pc.a(new dc(487, 2));
        pc.a(new dc(487, 3));
        pc.a(new dc(490, pc));
        pc.a(new dc(489, pc));
        pc.a(new be.es(aq.a().j()));
        bf.b(pc);
        bf.d(pc);
        m.a().b(pc);
        pc.fg();
        pc.a(new be.do(pc));
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
            i clan = ao.q.a().a(pc.aF());
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
                    for (i enemy_clan : currentWar.c(clan.f())) {
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
        if (pc.bI() > 0) {
            ba.g.a().a(pc, false);
        }
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
        ao.n.a().a(pc);
        if (pc.dY() == null) {
            pc.a(ao.aq.a().b());
            ao.n.a().b(pc);
        }
        pc.a(new dc(810, pc.dY()));
        pc.a(new dc(126));
        pc.a(new cm(189));
        pc.a(new dc(103, 4126));
        k.a().a(pc);
        ao.j.a().b(pc);
        az.a().b(pc);
        pc.a(new be.a(3, 1));
        if (pc.l()) {
            pc.j(26003, 0);
        }
        pc.a(new dc(141));
    }

    public static void a(u pc) {
        pc.j().a();
        pc.au().a();
        pc.av().a();
        pc.aw().a();
        if (pc.v() == null || pc.v().a().aO() == 0) {
            pc.a(new cm(160, 1, 0));
        }
        pc.a(new bh(pc.j().d()));
    }

    public static void b(u pc) {
        block9: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_skills WHERE char_obj_id=?");
                    pstm.setInt(1, pc.fr());
                    rs = pstm.executeQuery();
                    CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<Integer>();
                    ArrayList<Integer> passive_list = new ArrayList<Integer>();
                    while (rs.next()) {
                        int skillId = rs.getInt("skill_id");
                        bh.v l1skills = be.a().a(skillId);
                        pc.f(skillId);
                        if (skillId < 600) {
                            list.add(skillId);
                            continue;
                        }
                        passive_list.add(skillId - 600);
                        if (skillId == 609) continue;
                        pc.j(skillId, 0);
                    }
                    if (!list.isEmpty()) {
                        pc.a(new be.d(pc, list));
                    }
                    if (!passive_list.isEmpty()) {
                        pc.a(new dc(401, passive_list));
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(rs, pstm, con);
                    break block9;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(rs, pstm, con);
                throw throwable;
            }
            bi.j.a(rs, pstm, con);
        }
    }

    public static void c(u pc) {
        for (z summon : aq.a().f()) {
            if (summon.M().fr() != pc.fr()) continue;
            summon.e(pc);
            pc.e(summon);
            for (u visiblePc : aq.a().f(summon)) {
                visiblePc.a(new eh(summon, visiblePc));
            }
        }
    }

    public static void d(u pc) {
        block101: {
            int base_con;
            if (pc.fp() == 10500) {
                if (!pc.bB(5018)) {
                    pc.ck(10);
                    pc.cl(10);
                    pc.cm(10);
                    pc.cn(10);
                    pc.F(5);
                    pc.cp(5);
                }
                pc.j(5018, 3600000);
                pc.a(new dc(5018, 3600, 8, 4470, 0, 4452, 0, 0, 3));
            }
            if (pc.aK() != null && pc.aK().e().a()) {
                int vipLv = Math.min(pc.aK().e().h(), 5);
                pc.a(new v(72, vipLv));
                int[] nArray = new int[6];
                nArray[1] = 10;
                nArray[2] = 20;
                nArray[3] = 30;
                nArray[4] = 40;
                nArray[5] = 50;
                int[] hpmp = nArray;
                int[] nArray2 = new int[6];
                nArray2[2] = 1;
                nArray2[3] = 3;
                nArray2[4] = 5;
                nArray2[5] = 7;
                int[] dmghit = nArray2;
                int[] nArray3 = new int[6];
                nArray3[3] = 1;
                nArray3[4] = 2;
                nArray3[5] = 3;
                int[] sp = nArray3;
                pc.bH(hpmp[vipLv]);
                pc.bJ(hpmp[vipLv]);
                pc.ck(dmghit[vipLv]);
                pc.cm(dmghit[vipLv]);
                pc.cl(dmghit[vipLv]);
                pc.cn(dmghit[vipLv]);
                pc.cp(sp[vipLv]);
            }
            if ((base_con = pc.bg()) >= 25 && base_con <= 34) {
                pc.bH(50);
            } else if (base_con >= 35 && base_con <= 44) {
                pc.bH(150);
            } else if (base_con >= 45) {
                pc.bH(300);
            }
            int base_wis = pc.bk();
            if (base_wis >= 25 && base_wis <= 34) {
                pc.bJ(50);
            } else if (base_wis >= 35 && base_wis <= 44) {
                pc.bJ(150);
            } else if (base_wis >= 45) {
                pc.bJ(300);
            }
            int tamCharCount = pc.aK().e().r();
            if (tamCharCount > 0) {
                int time = pc.aK().e().s();
                int[] stringc = new int[]{4181, 4182, 4183, 4183, 4183, 4183, 4183, 4183};
                if (!pc.bB(4071)) {
                    pc.bL(-tamCharCount);
                }
                pc.j(4071, 0);
                pc.a(new dc(4071, time, 8, 6100, 0, stringc[tamCharCount - 1], stringc[tamCharCount - 1], 0, 3));
            }
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                HashMap<Integer, Integer> iconTimeList = new HashMap<Integer, Integer>();
                con = l1j.server.b.a().b();
                pstm = con.prepareStatement("SELECT * FROM character_buff WHERE char_obj_id=?");
                pstm.setInt(1, pc.fr());
                rs = pstm.executeQuery();
                block62: while (rs.next()) {
                    int skillid = rs.getInt("skill_id");
                    int remaining_time = rs.getInt("remaining_time");
                    Timestamp limitTime = rs.getTimestamp("limit_time");
                    if (limitTime != null) {
                        Timestamp current = new Timestamp(System.currentTimeMillis());
                        remaining_time = limitTime.before(current) ? 10 : (int)((limitTime.getTime() - current.getTime()) / 1000L);
                    }
                    switch (skillid) {
                        case 1000: {
                            pc.a(new dx(pc.fr(), 1, remaining_time));
                            pc.b(new dx(pc.fr(), 1, 0));
                            pc.cv(1);
                            pc.j(skillid, remaining_time * 1000);
                            break;
                        }
                        case 1016: {
                            pc.a(new dx(pc.fr(), 3, remaining_time));
                            pc.b(new dx(pc.fr(), 3, 0));
                            pc.cv(3);
                            pc.j(skillid, remaining_time * 1000);
                            break;
                        }
                        case 1017: {
                            pc.a(new dx(pc.fr(), 4, remaining_time));
                            pc.b(new dx(pc.fr(), 4, 0));
                            pc.cv(4);
                            pc.j(skillid, remaining_time * 1000);
                            break;
                        }
                        case 1026: {
                            pc.a(new dx(pc.fr(), 5, remaining_time));
                            pc.b(new dx(pc.fr(), 5, 0));
                            pc.cv(5);
                            pc.j(skillid, remaining_time * 1000);
                            break;
                        }
                        case 1001: {
                            pc.a(new be.ea(pc.fr(), 1, remaining_time));
                            pc.b(new be.ea(pc.fr(), 1, 0));
                            pc.cu(1);
                            pc.j(skillid, remaining_time * 1000);
                            break;
                        }
                        case 1002: {
                            pc.a(new cm(34, remaining_time));
                            pc.j(skillid, remaining_time * 1000);
                            break;
                        }
                        case 1005: {
                            pc.a(new cm(36, remaining_time));
                            pc.j(skillid, remaining_time * 1000);
                            break;
                        }
                        case 1007: {
                            d.b(pc, remaining_time);
                            break;
                        }
                        case 1027: {
                            pc.a(new bs(pc.fr(), 8));
                            pc.b(new bs(pc.fr(), 8));
                            pc.a(new cm(60, remaining_time));
                            pc.j(skillid, remaining_time * 1000);
                            break;
                        }
                        case 67: {
                            int poly_id = rs.getInt("poly_id");
                            ae.a(pc, poly_id, remaining_time, 0);
                            break;
                        }
                        case 1006: {
                            ae.a(pc, 13450, remaining_time, 0);
                            if (!pc.bB(skillid)) {
                                pc.bH(100);
                                pc.bJ(100);
                                pc.cm(10);
                                pc.ck(5);
                                pc.cn(10);
                                pc.cl(5);
                                pc.cp(5);
                                pc.bN(1);
                                pc.bR(1);
                                pc.bV(1);
                                pc.a(new ck(pc));
                            }
                            pc.j(1006, remaining_time * 1000);
                            break;
                        }
                        case 1037: {
                            ae.a(pc, 12854, remaining_time, 0);
                            if (!pc.bB(skillid)) {
                                pc.bH(120);
                                pc.bJ(100);
                                pc.F(15);
                                pc.co(30);
                                pc.cm(10);
                                pc.cn(10);
                                pc.U(3);
                                pc.bN(3);
                                pc.bR(3);
                                pc.bV(3);
                                pc.a(new ck(pc));
                            }
                            pc.j(1037, remaining_time * 1000);
                            for (q item : pc.j().d()) {
                                if (!item.a().S() || !item.D()) continue;
                                pc.j().a(item, false);
                            }
                            continue block62;
                        }
                        case 1031: {
                            ae.a(pc, 14491, remaining_time, 0);
                            if (!pc.bB(skillid)) {
                                pc.bH(120);
                                pc.bJ(100);
                                pc.cm(10);
                                pc.ck(7);
                                pc.cn(10);
                                pc.cl(7);
                                pc.cp(5);
                                pc.bN(1);
                                pc.bR(1);
                                pc.bV(1);
                                pc.a(new ck(pc));
                            }
                            pc.j(1031, remaining_time * 1000);
                            break;
                        }
                        case 1038: {
                            pc.a(new dc(1038, remaining_time, 0, 6546, 0, 3823, 1971, 1972, 1));
                            if (!pc.bB(skillid)) {
                                pc.bH(100);
                                pc.bJ(100);
                                pc.ck(5);
                                pc.cm(10);
                                pc.cl(5);
                                pc.cn(10);
                                pc.cp(5);
                                pc.bL(-10);
                                pc.co(10);
                                pc.a(new ck(pc));
                                pc.a(new be.do(pc));
                            }
                            pc.a(new bs(pc.fr(), 8));
                            pc.b(new bs(pc.fr(), 8));
                            pc.j(1038, remaining_time * 1000);
                            break;
                        }
                        case 4092: {
                            if (!pc.bB(skillid)) {
                                pc.F(5);
                            }
                            pc.j(4092, remaining_time * 1000);
                            pc.a(new dc(4092, remaining_time, 8, 6841, 0, 1426, 0, 0, 1));
                            break;
                        }
                        case 4080: {
                            if (!pc.bB(skillid)) {
                                pc.bH(25);
                                pc.bJ(20);
                            }
                            pc.a(new ck(pc));
                            pc.j(4080, remaining_time * 1000);
                            pc.a(new dc(4080, remaining_time, 0, 4910, 0, 4415, 0, 0, 1));
                            break;
                        }
                        case 4011: 
                        case 4012: 
                        case 4077: {
                            if (remaining_time == 0) continue block62;
                            s.a(pc, skillid, remaining_time, limitTime);
                            break;
                        }
                        case 4078: {
                            pc.j(skillid, remaining_time * 1000);
                            pc.a(new cm(86, 62, 1, remaining_time));
                            break;
                        }
                        case 4001: 
                        case 4002: 
                        case 4003: 
                        case 4004: 
                        case 4005: 
                        case 4007: 
                        case 4070: {
                            remaining_time = (remaining_time / 8 + 1) / 2;
                            iconTimeList.put(skillid, remaining_time);
                            pc.j(skillid, (remaining_time * 2 - 1) * 8 * 1000);
                            break;
                        }
                        case 4006: 
                        case 4008: 
                        case 4009: 
                        case 4010: 
                        case 4086: 
                        case 4087: 
                        case 4088: 
                        case 4089: 
                        case 4090: 
                        case 4091: {
                            remaining_time = (remaining_time / 8 + 1) / 2;
                            iconTimeList.put(skillid, remaining_time);
                            s.b(pc, skillid, (remaining_time * 2 - 1) * 8);
                            break;
                        }
                        case 4049: 
                        case 4050: 
                        case 4051: 
                        case 4052: 
                        case 4053: 
                        case 4054: 
                        case 4055: {
                            remaining_time = (remaining_time / 16 + 1) / 2;
                            iconTimeList.put(skillid, remaining_time);
                            s.b(pc, skillid, (remaining_time * 2 - 1) * 16);
                            break;
                        }
                        case 4056: 
                        case 4057: 
                        case 4079: {
                            remaining_time = (remaining_time / 16 + 1) / 2;
                            iconTimeList.put(skillid, remaining_time);
                            s.a(pc, skillid, (remaining_time * 2 - 1) * 16, limitTime);
                            break;
                        }
                        case 4076: {
                            remaining_time = (remaining_time / 8 + 1) / 2;
                            pc.a(new cm(86, 173, 1, remaining_time));
                            pc.j(skillid, (remaining_time * 2 - 1) * 8 * 1000);
                            break;
                        }
                        case 188: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new ff().a((f)pc, remaining_time);
                            break;
                        }
                        case 111: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new cx().a((f)pc, remaining_time);
                            break;
                        }
                        case 202: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new fp().a((f)pc, remaining_time);
                            break;
                        }
                        case 64: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new bm().a((f)pc, remaining_time);
                            break;
                        }
                        case 32: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new ag().a((f)pc, remaining_time);
                            break;
                        }
                        case 14: {
                            new p().a((f)pc, remaining_time);
                            break;
                        }
                        case 71: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new bf.bt().a((f)pc, remaining_time);
                            break;
                        }
                        case 78: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new bf.ca().a((f)pc, remaining_time);
                            break;
                        }
                        case 104: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new cq().a((f)pc, remaining_time);
                            break;
                        }
                        case 47: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new av().a((f)pc, remaining_time);
                            break;
                        }
                        case 56: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new bf.be().a((f)pc, remaining_time);
                            break;
                        }
                        case 55: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new bd().a((f)pc, remaining_time);
                            break;
                        }
                        case 158: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new ef().a((f)pc, remaining_time);
                            break;
                        }
                        case 153: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new ea().a((f)pc, remaining_time);
                            break;
                        }
                        case 134: {
                            new do().a((f)pc, remaining_time);
                            break;
                        }
                        case 176: {
                            iconTimeList.put(skillid, (remaining_time / 8 + 1) / 2);
                            new ex().a((f)pc, remaining_time);
                            break;
                        }
                        case 171: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new es().a((f)pc, remaining_time);
                            break;
                        }
                        case 174: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new ev().a((f)pc, remaining_time);
                            break;
                        }
                        case 173: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new eu().a((f)pc, remaining_time);
                            break;
                        }
                        case 206: {
                            iconTimeList.put(skillid, (remaining_time / 8 + 1) / 2);
                            new ft().a((f)pc, remaining_time);
                            break;
                        }
                        case 216: {
                            iconTimeList.put(skillid, (remaining_time / 8 + 1) / 2);
                            new gd().a((f)pc, remaining_time);
                            break;
                        }
                        case 217: {
                            iconTimeList.put(skillid, (remaining_time / 8 + 1) / 2);
                            new ge().a((f)pc, remaining_time);
                            break;
                        }
                        case 191: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new fi().a((f)pc, remaining_time);
                            break;
                        }
                        case 193: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new fk().a((f)pc, remaining_time);
                            break;
                        }
                        case 211: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new fy().a((f)pc, remaining_time);
                            break;
                        }
                        case 218: {
                            new gf().a((f)pc, remaining_time);
                            break;
                        }
                        case 183: {
                            iconTimeList.put(skillid, (remaining_time / 2 + 1) / 2);
                            new fa().a((f)pc, remaining_time);
                            break;
                        }
                        case 181: {
                            iconTimeList.put(skillid, (remaining_time / 8 + 1) / 2);
                            new ey().a((f)pc, remaining_time);
                            break;
                        }
                        case 89: {
                            iconTimeList.put(skillid, (remaining_time / 8 + 1) / 2);
                            new cf().a((f)pc, remaining_time);
                            break;
                        }
                        case 25009: 
                        case 25010: 
                        case 25011: {
                            int giftTime = 180;
                            if (skillid == 25010) {
                                giftTime = 900;
                            } else if (skillid == 25011) {
                                giftTime = 3600;
                            }
                            pc.j(skillid, giftTime * 1000);
                            pc.a(new cm(150, skillid - 25008, giftTime));
                            break;
                        }
                        case 25012: {
                            pc.j(skillid, remaining_time * 1000);
                            break;
                        }
                        default: {
                            if (skillid >= 4013 && skillid <= 4048) {
                                remaining_time = (remaining_time / 16 + 1) / 2;
                                iconTimeList.put(skillid, remaining_time);
                                s.a(pc, skillid, (remaining_time * 2 - 1) * 16);
                                break;
                            }
                            if (skillid >= 1 && skillid <= 609) {
                                bf.a executor = g.a(skillid);
                                executor.a((f)pc, remaining_time);
                                break;
                            }
                            if (skillid >= 3000 && skillid <= 3056) {
                                j.a(pc, skillid, remaining_time);
                                break;
                            }
                            System.out.println("login buff " + skillid + " has some error");
                        }
                    }
                }
                if (!iconTimeList.isEmpty()) {
                    pc.a(new be.b(iconTimeList));
                }
                if (pc.bB(4056)) {
                    pc.a(new cm(86, 76, 0, 45));
                } else if (pc.bB(4057)) {
                    pc.a(new cm(86, 76, 0, 60));
                } else if (pc.bB(4079)) {
                    pc.a(new cm(86, 76, 0, 74));
                }
            }
            catch (SQLException e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                bi.j.a(rs, pstm, con);
                break block101;
            }
            catch (Exception e3) {
                try {
                    a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                }
                catch (Throwable throwable) {
                    bi.j.a(rs, pstm, con);
                    throw throwable;
                }
                bi.j.a(rs, pstm, con);
                break block101;
            }
            bi.j.a(rs, pstm, con);
        }
        if (!pc.bB(25012)) {
            pc.j(25012, 1800000);
        }
        if (pc.ev() >= 49) {
            Timestamp logoutTime = pc.cB();
            if (logoutTime == null) {
                logoutTime = new Timestamp(System.currentTimeMillis());
            }
            int result = (int)((System.currentTimeMillis() - logoutTime.getTime()) / 900000L);
            int bless = pc.cC() + result * 7700;
            pc.K(bless);
        }
    }

    private void e(u pc) {
        String[] sss;
        String[] stringArray = sss = new String[]{"79 c0 00 00 00 00 00 00 00 00", "79 b8 00 00"};
        int n2 = sss.length;
        int n3 = 0;
        while (n3 < n2) {
            String s2 = stringArray[n3];
            String[] ss = s2.trim().split(" ");
            byte[] data = new byte[ss.length];
            int i2 = 0;
            while (i2 < ss.length) {
                data[i2] = this.a(ss[i2])[0];
                ++i2;
            }
            pc.a(new cm(data));
            ++n3;
        }
    }

    private byte[] a(String hexString) {
        char[] hex = hexString.toCharArray();
        int length = hex.length / 2;
        byte[] rawData = new byte[length];
        int i2 = 0;
        while (i2 < length) {
            int low;
            int high = Character.digit(hex[i2 * 2], 16);
            int value = high << 4 | (low = Character.digit(hex[i2 * 2 + 1], 16));
            if (value > 127) {
                value -= 256;
            }
            rawData[i2] = (byte)value;
            ++i2;
        }
        return rawData;
    }

    @Override
    public String a() {
        return b;
    }
}

