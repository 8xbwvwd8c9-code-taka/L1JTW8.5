/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.ab;
import ao.ah;
import ao.au;
import ao.aw;
import ao.o;
import ao.p;
import ap.q;
import ap.u;
import aq.aa;
import aq.ac;
import aq.ae;
import aq.ai;
import aq.am;
import aq.ap;
import aq.aq;
import aq.h;
import aq.x;
import ax.b;
import be.ca;
import be.cl;
import be.cm;
import be.cy;
import be.dc;
import be.di;
import be.ds;
import be.ee;
import be.el;
import be.r;
import be.v;
import be.y;
import be.z;
import bh.c;
import bh.i;
import bh.l;
import bh.n;
import bj.d;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;

public class f
extends cv {
    private static final Logger a = Logger.getLogger(f.class.getName());
    private static final int[] b;
    private static final int[] c;
    private static final int d = 0;
    private static final int e = 1;

    static {
        int[] nArray = new int[8];
        nArray[1] = 1;
        nArray[2] = 1;
        nArray[3] = 1;
        nArray[5] = -1;
        nArray[6] = -1;
        nArray[7] = -1;
        b = nArray;
        int[] nArray2 = new int[8];
        nArray2[0] = -1;
        nArray2[1] = -1;
        nArray2[3] = 1;
        nArray2[4] = 1;
        nArray2[5] = 1;
        nArray2[7] = -1;
        c = nArray2;
    }

    public f(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int attrcode = this.d();
        if (attrcode == 0) {
            int count = this.b();
            attrcode = this.d();
        }
        int c2 = 0;
        switch (attrcode) {
            case 97: {
                c2 = this.d();
                u joinPc = (u)aq.a().a(pc.aQ());
                pc.am(0);
                if (joinPc == null) break;
                if (c2 == 0) {
                    joinPc.a(new ds(96, pc.et()));
                    break;
                }
                if (c2 != 1) break;
                int clan_id = pc.aF();
                aq.i clan = ao.q.a().a(pc.aF());
                if (clan == null) {
                    return;
                }
                int maxMember = pc.eC() * 3 * (2 + pc.ev() / 50);
                if (pc.ev() < 45) {
                    maxMember /= 3;
                }
                if (l1j.server.a.U > 0) {
                    maxMember = l1j.server.a.U;
                }
                if (joinPc.aF() == 0) {
                    if (maxMember <= clan.p().size()) {
                        joinPc.a(new ds(188, pc.et()));
                        return;
                    }
                    if (joinPc.x() && pc.ev() < 45) {
                        pc.a(new ds(1530));
                        return;
                    }
                    for (u clanMembers : clan.b()) {
                        clanMembers.a(new ds(94, joinPc.et()));
                    }
                    joinPc.ah(clan_id);
                    joinPc.c(clan.f());
                    joinPc.ai(7);
                    joinPc.f("");
                    joinPc.I();
                    clan.a(joinPc.eu());
                    p.a().a(joinPc);
                    joinPc.a(new be.ac(joinPc));
                    joinPc.a(new y(joinPc.fr(), ""));
                    joinPc.b(new y(joinPc.fr(), ""));
                    joinPc.a(new be.ac(joinPc, true));
                    joinPc.a(new v(60, joinPc.fr(), clan.e()));
                    joinPc.a(new cy(clan));
                    joinPc.a(new cm(173, clan.j()));
                    joinPc.a(new ds(95, clan.f()));
                    for (u member : clan.b()) {
                        member.a(new v(60, joinPc.fr(), clan.i()));
                        joinPc.a(new v(60, member.fr(), clan.i()));
                        if (clan.b().size() < 3 || member.bB(4084)) continue;
                        member.j(4084, 0);
                        member.a(new cm(180, 450, 3240, 1));
                    }
                    break;
                }
                if (l1j.server.a.V && pc.ev() >= 45) {
                    this.a(pc, joinPc, maxMember);
                    break;
                }
                joinPc.a(new ds(89));
                break;
            }
            case 180: {
                c2 = this.c();
                String polyName = this.g();
                if (!pc.cy()) break;
                ae.a(pc, polyName, 7200);
                pc.t(false);
                break;
            }
            case 217: 
            case 221: 
            case 222: {
                ap clanWar;
                c2 = this.d();
                u enemyLeader = (u)aq.a().a(pc.aQ());
                if (enemyLeader == null || pc.aF() == 0) {
                    return;
                }
                pc.am(0);
                String enemyClanName = enemyLeader.aG();
                if (c2 == 0) {
                    if (attrcode == 217) {
                        enemyLeader.a(new ds(236, pc.aG()));
                        break;
                    }
                    if (attrcode != 221 && attrcode != 222) break;
                    enemyLeader.a(new ds(237, pc.aG()));
                    break;
                }
                if (c2 != 1) break;
                if (attrcode == 217) {
                    ap member = new ap(2, enemyClanName, pc.aG());
                    break;
                }
                if (attrcode != 221 && attrcode != 222 || (clanWar = aq.a().c(pc.aG())) == null) break;
                if (attrcode == 221) {
                    clanWar.a(enemyClanName, pc.aG());
                    break;
                }
                if (attrcode != 222) break;
                clanWar.b(enemyClanName, pc.aG());
                break;
            }
            case 252: {
                c2 = this.d();
                u trading_partner = (u)aq.a().a(pc.aO());
                if (trading_partner == null) break;
                if (c2 == 0) {
                    trading_partner.a(new ds(253, pc.et()));
                    pc.al(0);
                    trading_partner.al(0);
                    break;
                }
                if (c2 != 1) break;
                pc.a(new el(trading_partner.et()));
                trading_partner.a(new el(pc.et()));
                break;
            }
            case 321: {
                c2 = this.d();
                u resusepc1 = (u)aq.a().a(pc.aQ());
                pc.am(0);
                if (resusepc1 == null || c2 != 1) break;
                this.a(pc, resusepc1, (short)(pc.ew() / 2));
                break;
            }
            case 322: {
                c2 = this.d();
                u resusepc2 = (u)aq.a().a(pc.aQ());
                pc.am(0);
                if (resusepc2 != null && c2 == 1) {
                    this.a(pc, resusepc2, pc.ew());
                    if (pc.ca() >= 1 && pc.cb() && pc.cc()) {
                        pc.a(true);
                        pc.aH(0);
                        pc.k(false);
                    }
                }
                pc.bA(165);
                break;
            }
            case 325: {
                c2 = this.c();
                String name = this.g();
                aq.aa petObject = aq.a().a(pc.aQ());
                pc.am(0);
                if (!(petObject instanceof ap.v)) {
                    return;
                }
                ap.v pet = (ap.v)petObject;
                f.a(pet, name);
                break;
            }
            case 479: {
                if (this.c() != 1) break;
                String s2 = this.g();
                if (pc.ev() - 50 <= pc.bA()) {
                    return;
                }
                if (s2.equalsIgnoreCase("str") && pc.bf() < 45) {
                    pc.o(1);
                    pc.ay(pc.bA() + 1);
                    pc.a(new cl(pc));
                    pc.I();
                } else if (s2.equalsIgnoreCase("dex") && pc.bh() < 45) {
                    pc.q(1);
                    pc.W();
                    pc.ay(pc.bA() + 1);
                    pc.a(new cl(pc));
                    pc.I();
                } else if (s2.equalsIgnoreCase("con") && pc.bg() < 45) {
                    pc.p(1);
                    pc.ay(pc.bA() + 1);
                    pc.a(new cl(pc));
                    pc.I();
                } else if (s2.equalsIgnoreCase("int") && pc.bj() < 45) {
                    pc.s(1);
                    pc.ay(pc.bA() + 1);
                    pc.a(new cl(pc));
                    pc.I();
                } else if (s2.equalsIgnoreCase("wis") && pc.bk() < 45) {
                    pc.t(1);
                    pc.Y();
                    pc.ay(pc.bA() + 1);
                    pc.a(new cl(pc));
                    pc.I();
                } else if (s2.equalsIgnoreCase("cha") && pc.bi() < 45) {
                    pc.r(1);
                    pc.ay(pc.bA() + 1);
                    pc.a(new cl(pc));
                    pc.I();
                } else {
                    pc.a(new ds(481));
                }
                pc.a(new dc(490, pc));
                if (pc.ev() - 50 <= pc.bA() || pc.bf() + pc.bh() + pc.bg() + pc.bj() + pc.bk() + pc.bi() >= 270) break;
                int bonus = pc.ev() - 50 - pc.bA();
                pc.a(new ca(479, "" + bonus));
                break;
            }
            case 512: {
                c2 = this.d();
                String name = this.g();
                int houseId = pc.aQ();
                pc.am(0);
                if (name.length() <= 16) {
                    i house = ab.a().a(houseId);
                    if (house == null) {
                        return;
                    }
                    house.a(name);
                    ab.a().a(house);
                    break;
                }
                pc.a(new ds(513));
                break;
            }
            case 630: {
                c2 = this.d();
                u fightPc = (u)aq.a().a(pc.cp());
                if (fightPc == null) {
                    pc.aN(0);
                    return;
                }
                if (c2 == 0) {
                    pc.aN(0);
                    fightPc.aN(0);
                    fightPc.a(new ds(631, pc.et()));
                    break;
                }
                if (c2 != 1) break;
                fightPc.a(new cm(5, fightPc.cp(), fightPc.fr()));
                pc.a(new cm(5, pc.cp(), pc.fr()));
                break;
            }
            case 653: {
                c2 = this.d();
                u target653 = (u)aq.a().a(pc.bD());
                if (c2 == 0) {
                    return;
                }
                if (c2 == 1) {
                    if (target653 != null) {
                        target653.aB(0);
                        target653.I();
                        target653.a(new ds(662));
                    } else {
                        o.a().b(pc.bD());
                    }
                }
                pc.aB(0);
                pc.I();
                pc.a(new ds(662));
                break;
            }
            case 654: {
                c2 = this.d();
                u partner = (u)aq.a().a(pc.aQ());
                pc.am(0);
                if (partner == null) break;
                if (c2 == 0) {
                    partner.a(new ds(656, pc.et()));
                    break;
                }
                if (c2 != 1) break;
                pc.aB(partner.fr());
                pc.I();
                pc.a(new ds(790));
                pc.a(new ds(655, partner.et()));
                partner.aB(pc.fr());
                partner.I();
                partner.a(new ds(790));
                partner.a(new ds(655, pc.et()));
                break;
            }
            case 729: {
                c2 = this.d();
                if (c2 == 0) {
                    pc.a(new ds(79));
                    break;
                }
                if (c2 != 1) break;
                this.a(pc);
                break;
            }
            case 738: {
                c2 = this.d();
                if (c2 != 1 || pc.ca() < 1) break;
                int cost = 0;
                int level = pc.ev();
                int lawful = pc.fa();
                cost = level < 45 ? level * level * 100 : level * level * 200;
                if (lawful >= 0) {
                    cost /= 2;
                }
                if (pc.j().b(40308, cost)) {
                    pc.a(false);
                    pc.aH(0);
                    break;
                }
                pc.a(new ds(189));
                break;
            }
            case 744: {
                c2 = this.d();
                if (c2 == 0) {
                    pc.a(new ds(79));
                    break;
                }
                if (c2 != 1) break;
                int[] loc = pc.cA();
                am.a(pc, loc[0], loc[1], loc[2], 5, true);
                break;
            }
            case 951: {
                c2 = this.d();
                u chatPc = (u)aq.a().a(pc.aN());
                if (chatPc == null) break;
                if (c2 == 0) {
                    chatPc.a(new ds(423, pc.et()));
                    pc.ak(0);
                    break;
                }
                if (c2 != 1) break;
                if (chatPc.r()) {
                    if (chatPc.aM().a() || chatPc.l()) {
                        chatPc.aM().a(pc);
                        break;
                    }
                    chatPc.a(new ds(417));
                    break;
                }
                h chatParty = new h();
                chatParty.a(chatPc);
                chatParty.a(pc);
                chatPc.a(new ds(424, pc.et()));
                break;
            }
            case 953: {
                c2 = this.d();
                u target = (u)aq.a().a(pc.aN());
                if (target == null) break;
                if (c2 == 0) {
                    target.a(new ds(423, pc.et()));
                    pc.ak(0);
                    break;
                }
                if (c2 != 1) break;
                if (target.q()) {
                    if (target.aL().b() < l1j.server.a.W || target.l()) {
                        target.aL().a(pc);
                        x.a().e(pc);
                        break;
                    }
                    target.a(new ds(417));
                    break;
                }
                ac party = new ac(target);
                party.a(pc);
                x.a().d(target);
                x.a().d(pc);
                target.a(new ds(424, pc.et()));
                break;
            }
            case 954: {
                c2 = this.d();
                u target2 = (u)aq.a().a(pc.aN());
                if (target2 == null) break;
                if (c2 == 0) {
                    target2.a(new ds(423, pc.et()));
                    pc.ak(0);
                    break;
                }
                if (c2 != 1) break;
                if (target2.q()) {
                    if (target2.aL().b() < l1j.server.a.W || target2.l()) {
                        target2.aL().a(pc);
                        x.a().e(pc);
                        break;
                    }
                    target2.a(new ds(417));
                    break;
                }
                ac party = new ac(target2);
                party.a(pc);
                x.a().d(target2);
                x.a().d(pc);
                target2.a(new ds(424, pc.et()));
                break;
            }
            case 1322: {
                if (this.c() != 1) break;
                if (pc.j().b(640234, 1)) {
                    ap.v pet2 = (ap.v)aq.a().a(pc.aQ());
                    l l1npc = au.a().a(pet2.z());
                    pet2.e(l1npc.c());
                    pc.a(new r(pet2.fr(), l1npc.c()));
                    pc.b(new r(pet2.fr(), l1npc.c()));
                    pc.a(new ca(325, new String[0]));
                    break;
                }
                pc.am(0);
                pc.a(new ds(337, "$5843"));
                break;
            }
            case 2935: {
                if (this.c() == 1) {
                    q new_item = ah.a(pc, 41762, 1);
                    bh.c.a(pc, new_item);
                    q item = pc.j().e(pc.aQ());
                    if (item == null) {
                        pc.a(new ds(156));
                        return;
                    }
                    pc.j().f(item);
                    break;
                }
                pc.a(new ds(79));
                break;
            }
            case 2936: {
                if (this.c() == 1) {
                    q item = pc.j().e(pc.aQ());
                    if (item == null) {
                        pc.a(new ds(156));
                        return;
                    }
                    HashMap<Object, Object> list = new HashMap();
                    if (item.N() == 41762) {
                        bh.c.a(item, pc);
                        return;
                    }
                    if (item.N() == 41759) {
                        list = bh.c.b;
                    } else if (item.N() == 41760) {
                        list = bh.c.a;
                    }
                    int max_space = pc.cI();
                    if (max_space - pc.ba().size() < list.size()) {
                        int need_space = list.size() - (max_space - pc.ba().size());
                        pc.a(new ds(2961, "" + need_space));
                        break;
                    }
                    bh.c.a(pc, list);
                    pc.j().f(item);
                    break;
                }
                pc.a(new ds(79));
                break;
            }
            case 2967: {
                if (this.c() == 1) {
                    aa obj = aq.a().a(pc.aQ());
                    if (obj instanceof u) {
                        u master = (u)obj;
                        master.a(new ca(2968, pc.et()));
                        master.am(pc.fr());
                    }
                } else {
                    pc.a(new ds(2965));
                }
                pc.am(0);
                break;
            }
            case 2968: {
                aa obj = aq.a().a(pc.aQ());
                if (obj instanceof u) {
                    u disciple = (u)obj;
                    if (this.c() == 1) {
                        disciple.aV(pc.fr());
                        pc.aV(-1);
                        x.a().a(pc.fr(), disciple);
                        pc.a(new ds(2964));
                        disciple.a(new ds(2964));
                    } else {
                        pc.a(new ds(2965));
                        disciple.a(new ds(2965));
                    }
                }
                pc.am(0);
                break;
            }
            case 3348: {
                aq.i clan;
                aq.i targetClan = ao.q.a().a(pc.aQ());
                if (targetClan != null && this.c() == 1 && (clan = ao.q.a().a(pc.aF())) != null) {
                    clan.t().add(targetClan.e());
                    for (u member : clan.b()) {
                        member.a(new ds(3360, targetClan.f()));
                        member.a(new cy(clan));
                    }
                    ao.q.a().b(clan);
                    targetClan.t().add(clan.e());
                    for (u member : targetClan.b()) {
                        member.a(new ds(3360, clan.f()));
                        member.a(new cy(targetClan));
                    }
                    ao.q.a().b(targetClan);
                }
                pc.am(0);
                break;
            }
        }
    }

    private void a(u pc, u resusepc, short resHp) {
        int gfxid = 230;
        if (pc.bB(165)) {
            pc.i_(0);
        }
        pc.a(new ee(pc.fr(), 230));
        pc.b(new ee(pc.fr(), 230));
        pc.j(resHp);
        pc.a(resHp);
        pc.a();
        pc.c();
        pc.t();
        pc.a(new di(pc, resusepc, 0));
        pc.b(new di(pc, resusepc, 0));
        pc.a(new z(pc));
        pc.b(new z(pc));
    }

    private void a(u pc, u joinPc, int maxMember) {
        aq.i clan = ao.q.a().a(pc.aF());
        aq.i oldClan = ao.q.a().a(joinPc.aF());
        if (clan != null && oldClan != null && joinPc.x() && joinPc.fr() == oldClan.k()) {
            if (maxMember < clan.p().size() + oldClan.p().size()) {
                joinPc.a(new ds(188, pc.et()));
                return;
            }
            for (u u2 : clan.b()) {
                u2.a(new ds(94, joinPc.et()));
            }
            pc.ai(4);
            pc.a(new cm(27, 4, pc.et()));
            pc.I();
            for (String string : oldClan.p()) {
                u oldClanMember = aq.a().a(string);
                if (oldClanMember != null) {
                    p.a().a(oldClanMember.fr());
                    oldClanMember.ah(clan.e());
                    oldClanMember.c(clan.f());
                    oldClanMember.ai(2);
                    oldClanMember.I();
                    clan.a(oldClanMember.et());
                    p.a().a(oldClanMember);
                    oldClanMember.a(new cm(27, 7, oldClanMember.et()));
                    oldClanMember.a(new ds(95, clan.f()));
                    oldClanMember.a(new be.ac(oldClanMember, true));
                    oldClanMember.a(new v(60, oldClanMember.fr(), clan.e()));
                    oldClanMember.a(new cm(173, clan.j()));
                    oldClanMember.a(new cy(clan));
                    for (u player : clan.b()) {
                        player.a(new v(60, oldClanMember.fr(), clan.i()));
                        oldClanMember.a(new v(60, player.fr(), clan.i()));
                        if (clan.b().size() < 3 || player.bB(4084)) continue;
                        player.j(4084, 0);
                        player.a(new cm(180, 450, 3240, 1));
                    }
                    continue;
                }
                try {
                    u offClanMember = o.a().a(string);
                    p.a().a(offClanMember.fr());
                    offClanMember.ah(clan.e());
                    offClanMember.c(clan.f());
                    offClanMember.ai(2);
                    offClanMember.I();
                    clan.a(offClanMember.et());
                    p.a().a(offClanMember);
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                }
            }
            String string = String.valueOf(oldClan.i());
            File file = new File("./emblem/" + string);
            file.delete();
            ao.q.a().b(oldClan.f());
        }
    }

    private static void a(ap.v pet, String name) {
        if (pet == null || name == null) {
            throw new NullPointerException();
        }
        int petItemObjId = pet.k();
        n petTemplate = aw.a().b(petItemObjId);
        if (petTemplate == null) {
            throw new NullPointerException();
        }
        u pc = (u)pet.M();
        if (aw.a(name)) {
            pc.a(new ds(327));
            return;
        }
        l l1npc = au.a().a(pet.z());
        if (!pet.et().equalsIgnoreCase(l1npc.c())) {
            pc.a(new ds(326));
            return;
        }
        pet.e(name);
        petTemplate.a(name);
        aw.a().a(petTemplate);
        q item = pc.j().e(pet.k());
        pc.j().b(item);
        pc.a(new r(pet.fr(), name));
        pc.b(new r(pet.fr(), name));
    }

    private void a(u pc) {
        u callClanPc = (u)aq.a().a(pc.aQ());
        pc.am(0);
        if (callClanPc == null) {
            return;
        }
        if (pc.fr() != callClanPc.ct()) {
            return;
        }
        if (!callClanPc.fq().h() || as.b.a().a((aq.f)pc)) {
            pc.a(new ds(3675));
            return;
        }
        b map = callClanPc.fq();
        int locX = callClanPc.fs();
        int locY = callClanPc.ft();
        int heading = callClanPc.cu();
        locX += b[heading];
        locY += c[heading];
        heading = (heading + 4) % 4;
        boolean isExsistCharacter = false;
        for (aa object : aq.a().b((aa)callClanPc, 1)) {
            aq.f cha;
            if (!(object instanceof aq.f) || (cha = (aq.f)object).fs() != locX || cha.ft() != locY || cha.fp() != callClanPc.fp()) continue;
            isExsistCharacter = true;
            break;
        }
        if (locX == 0 && locY == 0 || !map.c(locX, locY) || isExsistCharacter) {
            pc.a(new ds(627));
            return;
        }
        ai.a().a(12938, 5000, pc.fs(), pc.ft(), pc.fp());
        am.a(pc, locX, locY, callClanPc.fp(), heading, true);
    }

    @Override
    public String a() {
        return "C_Attr";
    }
}

