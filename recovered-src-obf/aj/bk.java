/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.ab;
import ao.ac;
import ao.ad;
import ao.af;
import ao.aw;
import ao.bf;
import ao.bh;
import ap.p;
import ap.q;
import ap.t;
import ap.u;
import ap.z;
import aq.aa;
import aq.ae;
import aq.am;
import aq.an;
import aq.aq;
import aq.e;
import aq.f;
import aq.i;
import aq.r;
import aq.s;
import as.b;
import as.c;
import as.k;
import ba.h;
import be.ah;
import be.ao;
import be.bb;
import be.bc;
import be.be;
import be.bm;
import be.by;
import be.bz;
import be.ca;
import be.cm;
import be.cp;
import be.cs;
import be.cx;
import be.dc;
import be.dj;
import be.dk;
import be.dl;
import be.dm;
import be.dn;
import be.dp;
import be.dq;
import be.ds;
import be.du;
import be.dv;
import be.ea;
import be.eb;
import be.ee;
import be.ei;
import be.ej;
import be.j;
import be.v;
import be.x;
import bf.as;
import bf.n;
import bf.w;
import bi.g;
import bj.d;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import l1j.server.a;

public class bk
extends cv {
    /*
     * Opcode count of 15061 triggered aggressive code reduction.  Override with --aggressivesizethreshold.
     */
    public bk(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int objid = this.b();
        String s2 = this.g();
        int[] materials = null;
        int[] counts = null;
        int[] createitem = null;
        int[] createcount = null;
        String htmlid = null;
        String success_htmlid = null;
        String failure_htmlid = null;
        String[] htmldata = null;
        int contribution = 0;
        aa obj = aq.a().a(objid);
        if (obj == null) {
            System.out.println("object not found: " + s2);
            return;
        }
        if (obj instanceof u) {
            u target = (u)obj;
            if (target.cy()) {
                ae.a(pc, s2, 7200);
                target.t(false);
            }
            return;
        }
        if (!(obj instanceof t)) {
            return;
        }
        t npc = (t)obj;
        int npcid = npc.z();
        if ((obj instanceof ap.v || obj instanceof z) && npc.M() != pc) {
            return;
        }
        if (!(obj instanceof ap.v) && !(obj instanceof z) && npc.fu().c(pc.fu()) > 11) {
            return;
        }
        npc.a(pc, s2);
        if (ad.a().a(s2, pc, npc)) {
            return;
        }
        if (ao.ae.a().a(s2, pc, npc)) {
            return;
        }
        if (ac.a().a(s2, pc, npc)) {
            return;
        }
        if (s2.equalsIgnoreCase("buy")) {
            if (npc.z() == 70027 || npc.z() == 70023) {
                return;
            }
            if (npcid == 190005 || npcid == 190045 || npcid == 190095 || npcid == 190353) {
                pc.a(new du(npc, du.b));
                return;
            }
            if (npcid == 190142 || npcid == 190488) {
                pc.a(new du(npc, pc, du.d));
                return;
            }
            if (npcid == 70035 || npcid == 70041 || npcid == 70042) {
                pc.a(new du(npc, du.c));
                return;
            }
            pc.a(new du(npc, du.a));
        } else if (s2.equalsIgnoreCase("sell")) {
            if (obj instanceof p) {
                htmlid = this.a(pc, objid, npcid);
            } else {
                pc.a(new dv(npc, pc));
            }
        } else if (s2.equalsIgnoreCase("retrieve")) {
            if (pc.ev() >= 5) {
                if (client.e().m() > 0) {
                    pc.a(new ds(834));
                } else {
                    pc.a(new dj(objid, pc));
                }
            }
        } else if (s2.equalsIgnoreCase("retrieve-elven")) {
            if (pc.ev() >= 5 && pc.A() && pc.A() && pc.ev() > 4) {
                if (client.e().m() > 0) {
                    pc.a(new ds(834));
                } else {
                    pc.a(new dm(objid, pc));
                }
            }
        } else if (s2.equalsIgnoreCase("retrieve-char")) {
            if (pc.ev() >= 5) {
                if (client.e().m() > 0) {
                    pc.a(new ds(834));
                } else {
                    pc.a(new dk(objid, pc));
                }
            }
        } else if (s2.equalsIgnoreCase("retrieve-pledge")) {
            if (pc.ev() >= 5) {
                if (pc.aF() == 0) {
                    pc.a(new ds(208));
                    return;
                }
                int rank = pc.aH();
                if (!i.a(rank)) {
                    pc.a(new ds(728));
                    return;
                }
                if (client.e().m() > 0) {
                    pc.a(new ds(834));
                } else {
                    pc.a(new dl(objid, pc));
                }
            }
        } else if (s2.equalsIgnoreCase("history")) {
            pc.a(new cx(pc.aF()));
        } else if (s2.equalsIgnoreCase("get")) {
            if (npcid == 70099 || npcid == 70796) {
                ao.ah.a(pc, 20081, 1, npc.T());
                pc.bb().b(11);
                htmlid = "";
            } else if (npcid == 70528 || npcid == 70546 || npcid == 70567 || npcid == 70594 || npcid == 70654 || npcid == 70748 || npcid == 70774 || npcid == 70799 || npcid == 70815 || npcid == 70860) {
                int townId = pc.bF();
                int pay = pc.bH();
                int cb2 = pc.bG();
                htmlid = "";
                if (pay < 1) {
                    pc.a(new ds(767));
                } else if (pay > 0 && cb2 < 500) {
                    pc.a(new ds(766));
                } else if (townId > 0) {
                    long adenaCount;
                    double payBonus = 1.0;
                    boolean isLeader = bh.a().a(pc, townId);
                    if (cb2 > 999 && cb2 < 1500) {
                        payBonus = 1.5;
                    } else if (cb2 > 1499 && cb2 < 2000) {
                        payBonus = 2.0;
                    } else if (cb2 > 1999 && cb2 < 2500) {
                        payBonus = 2.5;
                    } else if (cb2 > 2499 && cb2 < 3000) {
                        payBonus = 3.0;
                    } else if (cb2 > 2999) {
                        payBonus = 4.0;
                    }
                    if (isLeader) {
                        payBonus += 1.0;
                    }
                    if ((double)(adenaCount = (long)pc.j().g(40308)) + (double)pay * payBonus > 2.0E9) {
                        pc.a(new ei("\\aG\u6240\u6301\u6709\u7684\u91d1\u5e63\u5c07\u6703\u8d85\u904e2000000000\u4e0a\u9650"));
                        htmlid = "";
                    } else {
                        pay = (int)((double)h.a(pc.fr()) * payBonus);
                        ao.ah.a(pc, 40308, pay, 0, false);
                        pc.a(new ds(761, "" + pay));
                        pc.aF(0);
                    }
                }
            }
        } else if (s2.equalsIgnoreCase("townscore")) {
            if ((npcid == 70528 || npcid == 70546 || npcid == 70567 || npcid == 70594 || npcid == 70654 || npcid == 70748 || npcid == 70774 || npcid == 70799 || npcid == 70815 || npcid == 70860) && pc.bF() > 0) {
                pc.a(new ds(1569, String.valueOf(pc.bG())));
            }
        } else if (!s2.equalsIgnoreCase("fix")) {
            if (s2.equalsIgnoreCase("openigate")) {
                this.a(pc, npcid, true);
                htmlid = "";
            } else if (s2.equalsIgnoreCase("closeigate")) {
                this.a(pc, npcid, false);
                htmlid = "";
            } else if (s2.equalsIgnoreCase("askwartime")) {
                if (npcid == 60514) {
                    htmldata = this.d(1);
                    htmlid = "ktguard7";
                } else if (npcid == 60560) {
                    htmldata = this.d(2);
                    htmlid = "orcguard7";
                } else if (npcid == 60552) {
                    htmldata = this.d(3);
                    htmlid = "wdguard7";
                } else if (npcid == 60524 || npcid == 60525 || npcid == 60529) {
                    htmldata = this.d(4);
                    htmlid = "grguard7";
                } else if (npcid == 70857) {
                    htmldata = this.d(5);
                    htmlid = "heguard7";
                } else if (npcid == 60530 || npcid == 60531) {
                    htmldata = this.d(6);
                    htmlid = "dcguard7";
                } else if (npcid == 60533 || npcid == 60534) {
                    htmldata = this.d(7);
                    htmlid = "adguard7";
                } else if (npcid == 81156) {
                    htmldata = this.d(8);
                    htmlid = "dfguard3";
                }
            } else if (s2.equalsIgnoreCase("inex")) {
                int castle_id;
                i clan = ao.q.a().a(pc.aF());
                if (clan != null && (castle_id = clan.m()) != 0) {
                    bh.d l1castle = ao.g.a().a(castle_id);
                    pc.a(new ds(309, l1castle.b(), String.valueOf(l1castle.f())));
                    htmlid = "";
                }
            } else if (s2.equalsIgnoreCase("tax")) {
                pc.a(new ej(pc.fr()));
            } else if (s2.equalsIgnoreCase("withdrawal")) {
                int castle_id;
                i clan = ao.q.a().a(pc.aF());
                if (clan != null && (castle_id = clan.m()) != 0) {
                    bh.d l1castle = ao.g.a().a(castle_id);
                    pc.a(new ao(pc.fr(), l1castle.f()));
                }
            } else if (s2.equalsIgnoreCase("cdeposit")) {
                pc.a(new ah(pc.fr()));
            } else if (s2.equalsIgnoreCase("employ")) {
                i clan = ao.q.a().a(pc.aF());
                if (clan == null) {
                    return;
                }
                int castle_id = clan.m();
                if (castle_id > 0) {
                    bh.d l1castle = ao.g.a().a(castle_id);
                    if (pc.fr() == l1castle.h()) {
                        pc.a(new bz(l1castle));
                    }
                }
            } else if (s2.equalsIgnoreCase("arrange")) {
                i clan = ao.q.a().a(pc.aF());
                if (clan == null) {
                    return;
                }
                int castle_id = clan.m();
                if (castle_id == 0) {
                    return;
                }
                bh.d l1castle = ao.g.a().a(castle_id);
                if (l1castle.i() > 0) {
                    htmldata = new String[]{clan.f(), "" + l1castle.i()};
                    htmlid = "fisher15";
                } else {
                    htmlid = "fisher9";
                }
            } else if (s2.equalsIgnoreCase("archer")) {
                i clan = ao.q.a().a(pc.aF());
                if (clan == null) {
                    return;
                }
                int castle_id = clan.m();
                if (castle_id > 0) {
                    bh.d l1castle = ao.g.a().a(castle_id);
                    if (pc.fr() == l1castle.h()) {
                        pc.a(new be.f(l1castle));
                    }
                }
            } else if (s2.equalsIgnoreCase("castlegate")) {
                this.a(pc);
                htmlid = "";
            } else if (s2.equalsIgnoreCase("demand")) {
                i clan = ao.q.a().a(pc.aF());
                if (clan == null) {
                    return;
                }
                int castle_id = clan.m();
                if (castle_id > 0) {
                    bh.d l1castle = ao.g.a().a(castle_id);
                    pc.a(new by(pc, l1castle));
                }
            } else if (s2.equalsIgnoreCase("encw")) {
                new n().a((f)pc, 0);
                htmlid = "";
            } else if (s2.equalsIgnoreCase("enca")) {
                new w().a((f)pc, 0);
                htmlid = "";
            } else if (s2.equalsIgnoreCase("depositnpc")) {
                for (t petNpc : pc.ek().values()) {
                    if (!(petNpc instanceof ap.v)) continue;
                    ap.v pet = (ap.v)petNpc;
                    pc.a(new cp(pc, petNpc, false));
                    pet.ax();
                    pet.b(true);
                    pc.ek().remove(pet.fr());
                    pet.aa_();
                }
                htmlid = "";
            } else if (s2.equalsIgnoreCase("withdrawnpc")) {
                pc.a(new cs(objid, pc));
            } else if (s2.equalsIgnoreCase("aggressive")) {
                if (obj instanceof ap.v) {
                    ap.v pet = (ap.v)obj;
                    pet.e(1);
                }
            } else if (s2.equalsIgnoreCase("defensive")) {
                if (obj instanceof ap.v) {
                    ap.v pet = (ap.v)obj;
                    pet.e(2);
                }
            } else if (s2.equalsIgnoreCase("stay")) {
                if (obj instanceof ap.v) {
                    ap.v pet = (ap.v)obj;
                    pet.e(3);
                }
            } else if (s2.equalsIgnoreCase("extend")) {
                if (obj instanceof ap.v) {
                    ap.v pet = (ap.v)obj;
                    pet.e(4);
                }
            } else if (s2.equalsIgnoreCase("alert")) {
                if (obj instanceof ap.v) {
                    ap.v pet = (ap.v)obj;
                    pet.e(5);
                }
            } else if (s2.equalsIgnoreCase("dismiss")) {
                if (obj instanceof ap.v) {
                    ap.v pet = (ap.v)obj;
                    pet.e(6);
                }
            } else if (s2.equalsIgnoreCase("changename")) {
                pc.am(objid);
                pc.a(new ca(325, new String[0]));
            } else if (s2.equalsIgnoreCase("attackchr")) {
                pc.a(new dp(npc.fr()));
            } else if (s2.equalsIgnoreCase("select")) {
                String s22 = this.g();
                pc.a(new j(objid, s22));
            } else if (s2.equalsIgnoreCase("map")) {
                String s23 = this.g();
                pc.a(new bb(objid, s23));
            } else if (s2.equalsIgnoreCase("apply")) {
                String s24 = this.g();
                i clan = ao.q.a().a(pc.aF());
                if (clan != null) {
                    if (pc.x() && pc.fr() == clan.k()) {
                        if (pc.ev() >= 15) {
                            if (clan.n() == 0) {
                                pc.setL1rAmountContext(objid, 1);
                                pc.a(new be.e(objid, s24));
                            } else {
                                pc.a(new ds(521));
                                htmlid = "";
                            }
                        } else {
                            pc.a(new ds(519));
                            htmlid = "";
                        }
                    } else {
                        pc.a(new ds(518));
                        htmlid = "";
                    }
                } else {
                    pc.a(new ds(518));
                    htmlid = "";
                }
            } else if (s2.equalsIgnoreCase("open") || s2.equalsIgnoreCase("close")) {
                this.a(pc, npc, s2);
                htmlid = "";
            } else if (s2.equalsIgnoreCase("expel")) {
                this.a(pc, npcid);
                htmlid = "";
            } else if (s2.equalsIgnoreCase("pay")) {
                htmldata = this.b(pc, npc);
                htmlid = "agpay";
            } else if (s2.equalsIgnoreCase("payfee")) {
                htmldata = new String[]{npc.U_().c(), "2000"};
                htmlid = "";
                if (this.a(pc, npc)) {
                    htmlid = "agpayfee";
                }
            } else if (s2.equalsIgnoreCase("name")) {
                bh.i house;
                int keeperId;
                int houseId;
                i clan = ao.q.a().a(pc.aF());
                if (clan != null && (houseId = clan.n()) != 0 && npcid == (keeperId = (house = ab.a().a(houseId)).f())) {
                    pc.am(houseId);
                    pc.a(new ca(512, ""));
                }
                htmlid = "";
            } else if (!s2.equalsIgnoreCase("rem")) {
                int i2;
                if (s2.equalsIgnoreCase("tel0") || s2.equalsIgnoreCase("tel1") || s2.equalsIgnoreCase("tel2") || s2.equalsIgnoreCase("tel3")) {
                    bh.i house;
                    int keeperId;
                    int houseId;
                    i clan = ao.q.a().a(pc.aF());
                    if (clan != null && (houseId = clan.n()) != 0 && npcid == (keeperId = (house = ab.a().a(houseId)).f())) {
                        int[] loc = new int[3];
                        if (s2.equalsIgnoreCase("tel0")) {
                            loc = r.a(houseId, 0);
                        } else if (s2.equalsIgnoreCase("tel1")) {
                            loc = r.a(houseId, 1);
                        } else if (s2.equalsIgnoreCase("tel2")) {
                            loc = r.a(houseId, 2);
                        } else if (s2.equalsIgnoreCase("tel3")) {
                            loc = r.a(houseId, 3);
                        }
                        am.a(pc, loc[0], loc[1], loc[2], 5, true);
                    }
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("upgrade")) {
                    bh.i house;
                    int keeperId;
                    int houseId;
                    i clan = ao.q.a().a(pc.aF());
                    if (clan != null && (houseId = clan.n()) != 0 && npcid == (keeperId = (house = ab.a().a(houseId)).f())) {
                        if (pc.x() && pc.fr() == clan.k()) {
                            if (house.h()) {
                                pc.a(new ds(1135));
                            } else if (pc.j().b(40308, 5000000)) {
                                house.b(true);
                                ab.a().a(house);
                                pc.a(new ds(1099));
                            } else {
                                pc.a(new ds(189));
                            }
                        } else {
                            pc.a(new ds(518));
                        }
                    }
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("hall") && obj instanceof p) {
                    bh.i house;
                    int keeperId;
                    int houseId;
                    i clan = ao.q.a().a(pc.aF());
                    if (clan != null && (houseId = clan.n()) != 0 && npcid == (keeperId = (house = ab.a().a(houseId)).f())) {
                        if (house.h()) {
                            int[] loc = r.b(houseId);
                            am.a(pc, loc[0], loc[1], loc[2], 5, true);
                        } else {
                            pc.a(new ds(1098));
                        }
                    }
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("fire")) {
                    if (pc.A()) {
                        if (pc.bC() != 0) {
                            pc.a(new ds(684));
                            return;
                        }
                        pc.aA(2);
                        pc.I();
                        pc.a(new cm(15, 1));
                        pc.a(new be.d(pc, pc.at()));
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("water")) {
                    if (pc.A()) {
                        if (pc.bC() != 0) {
                            pc.a(new ds(684));
                            return;
                        }
                        pc.aA(4);
                        pc.I();
                        pc.a(new cm(15, 2));
                        pc.a(new be.d(pc, pc.at()));
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("air")) {
                    if (pc.A()) {
                        if (pc.bC() != 0) {
                            pc.a(new ds(684));
                            return;
                        }
                        pc.aA(8);
                        pc.I();
                        pc.a(new cm(15, 3));
                        pc.a(new be.d(pc, pc.at()));
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("earth")) {
                    if (pc.A()) {
                        if (pc.bC() != 0) {
                            pc.a(new ds(684));
                            return;
                        }
                        pc.aA(1);
                        pc.I();
                        pc.a(new cm(15, 4));
                        pc.a(new be.d(pc, pc.at()));
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("init")) {
                    if (pc.A()) {
                        if (pc.bC() == 0) {
                            pc.a(new ds(79));
                            return;
                        }
                        if (pc.bB(147)) {
                            pc.bz(147);
                        }
                        pc.aA(0);
                        pc.I();
                        pc.a(new ds(678));
                        pc.a(new be.d(pc, pc.at()));
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("exp")) {
                    if (pc.ca() >= 1) {
                        int cost = 0;
                        int level = pc.ev();
                        int lawful = pc.fa();
                        cost = level < 45 ? level * level * 100 : level * level * 200;
                        if (lawful >= 0) {
                            cost /= 2;
                        }
                        pc.a(new ca(738, String.valueOf(cost)));
                    } else {
                        pc.a(new ds(739));
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("pk")) {
                    if (pc.fa() < 30000) {
                        pc.a(new ds(559));
                    } else if (pc.aD() < 5) {
                        pc.a(new ds(560));
                    } else if (pc.j().b(40308, 700000)) {
                        pc.af(pc.aD() - 5);
                        pc.a(new ds(561, String.valueOf(pc.aD())));
                    } else {
                        pc.a(new ds(189));
                    }
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("ent")) {
                    if (npcid == 71251) {
                        if (!pc.j().f(49142)) {
                            pc.a(new ds(1290));
                            return;
                        }
                        new as().a((f)pc, 1);
                        pc.j().j();
                        am.a(pc, 32737, 32789, 997, 4, true);
                        int initStatusPoint = 75 + pc.bB();
                        int pcStatusPoint = pc.bf() + pc.bj() + pc.bk() + pc.bh() + pc.bg() + pc.bi();
                        if (pc.ev() > 50) {
                            pcStatusPoint += pc.ev() - 50 - pc.bA();
                        }
                        int diff = pcStatusPoint - initStatusPoint;
                        int maxLevel = 1;
                        maxLevel = diff > 0 ? Math.min(50 + diff, 99) : pc.ev();
                        pc.aT(maxLevel);
                        pc.aS(1);
                        pc.s(true);
                        pc.a(new x(pc));
                    }
                } else if (s2.equalsIgnoreCase("haste")) {
                    if (npcid == 70514) {
                        pc.a(new ds(183));
                        pc.a(new ea(pc.fr(), 1, 1600));
                        pc.b(new ea(pc.fr(), 1, 0));
                        pc.a(new ee(pc.fr(), 755));
                        pc.b(new ee(pc.fr(), 755));
                        pc.cu(1);
                        pc.j(1001, 1600000);
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("skeleton nbmorph")) {
                    this.a(client, 2374);
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("lycanthrope nbmorph")) {
                    this.a(client, 3874);
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("shelob nbmorph")) {
                    this.a(client, 95);
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("ghoul nbmorph")) {
                    this.a(client, 3873);
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("ghast nbmorph")) {
                    this.a(client, 3875);
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("atuba orc nbmorph")) {
                    this.a(client, 3868);
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("skeleton axeman nbmorph")) {
                    this.a(client, 2376);
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("troll nbmorph")) {
                    this.a(client, 3878);
                    htmlid = "";
                } else if (s2.equalsIgnoreCase("teleport mutant-dungen")) {
                    for (u otherPc : aq.a().c(pc, 3)) {
                        if (otherPc.aF() != pc.aF() || otherPc.fr() == pc.fr()) continue;
                        am.a(otherPc, 32740, 32800, 217, 5, true);
                    }
                    am.a(pc, 32740, 32800, 217, 5, true);
                } else if (s2.equalsIgnoreCase("teleport mage-quest-dungen")) {
                    am.a(pc, 32791, 32788, 201, 5, true);
                } else if (s2.equalsIgnoreCase("request blood of evil")) {
                    if (pc.aF() == 0) {
                        pc.a(new ds(2498));
                        return;
                    }
                    i clan = ao.q.a().a(pc.aF());
                    if (clan == null || pc.fr() != clan.k()) {
                        pc.a(new ds(2498));
                        return;
                    }
                    if (pc.aH() == 4) {
                        pc.a(new ds(3258));
                        return;
                    }
                    if (pc.ev() < 45) {
                        pc.a(new ds(2738));
                        return;
                    }
                    if (!pc.j().b(40308, 50000)) {
                        pc.a(new ds(189));
                        return;
                    }
                    pc.ai(4);
                    pc.a(new cm(27, 4, pc.et()));
                    pc.I();
                } else if (npcid == 81279) {
                    if (s2.equalsIgnoreCase("a")) {
                        s.a(pc, 4056, 2400, null);
                        pc.a(new ee(pc.fr(), 7681));
                        pc.b(new ee(pc.fr(), 7681));
                        pc.a(new ee(pc.fr(), 7783));
                        pc.b(new ee(pc.fr(), 7783));
                        htmlid = "grayknight2";
                    }
                } else if (npcid == 81292) {
                    if (s2.equalsIgnoreCase("a")) {
                        s.a(pc, 4057, 2400, null);
                        pc.a(new ee(pc.fr(), 7680));
                        pc.b(new ee(pc.fr(), 7680));
                        pc.a(new ee(pc.fr(), 7852));
                        pc.b(new ee(pc.fr(), 7852));
                        htmlid = "";
                    }
                } else if (npcid == 81407) {
                    if (s2.equalsIgnoreCase("a")) {
                        s.a(pc, 4079, 2400, null);
                        pc.a(new ee(pc.fr(), 7683));
                        pc.b(new ee(pc.fr(), 7683));
                        pc.a(new ee(pc.fr(), 7853));
                        pc.b(new ee(pc.fr(), 7853));
                        htmlid = "";
                    }
                } else if (npcid == 71038) {
                    if (s2.equalsIgnoreCase("A")) {
                        ao.ah.a(pc, 41060, 1, 0, npc.T());
                        htmlid = "orcfnoname9";
                    }
                } else if (npcid == 71040) {
                    if (s2.equalsIgnoreCase("A")) {
                        ao.ah.a(pc, 41065, 1, 0, npc.T());
                        htmlid = "orcfnoa4";
                    }
                } else if (npcid == 71041) {
                    if (s2.equalsIgnoreCase("A")) {
                        ao.ah.a(pc, 41064, 1, 0, npc.T());
                        htmlid = "orcfhuwoomo4";
                    }
                } else if (npcid == 71042) {
                    if (s2.equalsIgnoreCase("A")) {
                        ao.ah.a(pc, 41062, 1, 0, npc.T());
                        htmlid = "orcfbakumo4";
                    }
                } else if (npcid == 71043) {
                    if (s2.equalsIgnoreCase("A")) {
                        ao.ah.a(pc, 41063, 1, 0, npc.T());
                        htmlid = "orcfbuka4";
                    }
                } else if (npcid == 71044) {
                    if (s2.equalsIgnoreCase("A")) {
                        ao.ah.a(pc, 41061, 1, 0, npc.T());
                        htmlid = "orcfkame4";
                    }
                } else if (npcid == 80049) {
                    if (s2.equalsIgnoreCase("1") && pc.P() <= -10000000) {
                        pc.A(1000000);
                        pc.a(new ds(1078));
                        htmlid = "betray13";
                    }
                } else if (npcid == 80050) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.j().b(40718, 1)) {
                            pc.B((int)(-100.0 * a.D));
                            pc.a(new ds(1079));
                            htmlid = "meet107";
                        } else {
                            htmlid = "meet104";
                        }
                    } else if (s2.equalsIgnoreCase("b")) {
                        if (pc.j().b(40718, 10)) {
                            pc.B((int)(-1000.0 * a.D));
                            pc.a(new ds(1079));
                            htmlid = "meet108";
                        } else {
                            htmlid = "meet104";
                        }
                    } else if (s2.equalsIgnoreCase("c")) {
                        if (pc.j().b(40718, 100)) {
                            pc.B((int)(-10000.0 * a.D));
                            pc.a(new ds(1079));
                            htmlid = "meet109";
                        } else {
                            htmlid = "meet104";
                        }
                    } else if (s2.equalsIgnoreCase("d")) {
                        if (pc.j().f(40615) || pc.j().f(40616)) {
                            htmlid = "";
                        } else {
                            am.a(pc, 32683, 32895, 608, 5, true);
                        }
                    }
                } else if (npcid == 80052) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.bB(5015)) {
                            pc.bA(5015);
                        }
                        pc.a(new ee(pc.fr(), 750));
                        pc.b(new ee(pc.fr(), 750));
                        pc.a(new eb(221, 1020, 2));
                        pc.j(5015, 1020000);
                        pc.a(new ds(1127));
                        htmlid = "";
                    }
                } else if (npcid == 80053) {
                    if (s2.equalsIgnoreCase("a")) {
                        int aliceMaterialId = 0;
                        int karmaLevel = 0;
                        int[] material = null;
                        int[] count = null;
                        int createItem = 0;
                        String successHtmlId = null;
                        String htmlId = null;
                        int[] aliceMaterialIdList = new int[]{40991, 196, 197, 198, 199, 200, 201, 202};
                        int[] karmaLevelList = new int[]{-1, -2, -3, -4, -5, -6, -7, -8};
                        int[][] materialsList = new int[][]{{40995, 40718, 40991}, {40997, 40718, 196}, {40990, 40718, 197}, {40994, 40718, 198}, {40993, 40718, 199}, {40998, 40718, 200}, {40996, 40718, 201}, {40992, 40718, 202}};
                        int[][] countList = new int[][]{{100, 100, 1}, {100, 100, 1}, {100, 100, 1}, {50, 100, 1}, {50, 100, 1}, {50, 100, 1}, {10, 100, 1}, {10, 100, 1}};
                        int[] createItemList = new int[]{196, 197, 198, 199, 200, 201, 202, 203};
                        String[] successHtmlIdList = new String[]{"alice_1", "alice_2", "alice_3", "alice_4", "alice_5", "alice_6", "alice_7", "alice_8"};
                        String[] htmlIdList = new String[]{"aliceyet", "alice_1", "alice_2", "alice_3", "alice_4", "alice_5", "alice_5", "alice_7"};
                        i2 = 0;
                        while (i2 < aliceMaterialIdList.length) {
                            if (pc.j().f(aliceMaterialIdList[i2])) {
                                aliceMaterialId = aliceMaterialIdList[i2];
                                karmaLevel = karmaLevelList[i2];
                                material = materialsList[i2];
                                count = countList[i2];
                                createItem = createItemList[i2];
                                successHtmlId = successHtmlIdList[i2];
                                htmlId = htmlIdList[i2];
                                break;
                            }
                            ++i2;
                        }
                        if (aliceMaterialId == 0) {
                            htmlid = "alice_no";
                        } else if (aliceMaterialId == 203) {
                            htmlid = "alice_8";
                        } else if (pc.Q() <= karmaLevel) {
                            materials = material;
                            counts = count;
                            createitem = new int[]{createItem};
                            createcount = new int[]{1};
                            success_htmlid = successHtmlId;
                            failure_htmlid = "alice_no";
                        } else {
                            htmlid = htmlId;
                        }
                    }
                } else if (npcid == 80055) {
                    this.b(pc, npc, s2);
                    htmlid = "";
                } else if (npcid == 80056) {
                    if (pc.P() <= -10000000) {
                        this.e(pc, npc, s2);
                    }
                    htmlid = "";
                } else if (npcid == 80064) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.j().b(40678, 1)) {
                            pc.B((int)(100.0 * a.D));
                            pc.a(new ds(1078));
                            htmlid = "meet007";
                        } else {
                            htmlid = "meet004";
                        }
                    } else if (s2.equalsIgnoreCase("b")) {
                        if (pc.j().b(40678, 10)) {
                            pc.B((int)(1000.0 * a.D));
                            pc.a(new ds(1078));
                            htmlid = "meet008";
                        } else {
                            htmlid = "meet004";
                        }
                    } else if (s2.equalsIgnoreCase("c")) {
                        if (pc.j().b(40678, 100)) {
                            pc.B((int)(10000.0 * a.D));
                            pc.a(new ds(1078));
                            htmlid = "meet009";
                        } else {
                            htmlid = "meet004";
                        }
                    } else if (s2.equalsIgnoreCase("d")) {
                        if (pc.j().f(40909) || pc.j().f(40910) || pc.j().f(40911) || pc.j().f(40912) || pc.j().f(40913) || pc.j().f(40914) || pc.j().f(40915) || pc.j().f(40916) || pc.j().f(40917) || pc.j().f(40918) || pc.j().f(40919) || pc.j().f(40920) || pc.j().f(40921)) {
                            htmlid = "";
                        } else {
                            am.a(pc, 32674, 32832, 602, 2, true);
                        }
                    }
                } else if (npcid == 80066) {
                    if (s2.equalsIgnoreCase("1") && pc.P() >= 10000000) {
                        pc.A(-1000000);
                        pc.a(new ds(1079));
                        htmlid = "betray03";
                    }
                } else if (npcid == 80071) {
                    this.c(pc, npc, s2);
                    htmlid = "";
                } else if (npcid == 80073) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.bB(5014)) {
                            pc.bA(5014);
                        }
                        pc.a(new ee(pc.fr(), 750));
                        pc.b(new ee(pc.fr(), 750));
                        pc.a(new eb(221, 1020, 1));
                        pc.j(5014, 1020000);
                        pc.a(new ds(1127));
                        htmlid = "";
                    }
                } else if (npcid == 80072) {
                    String sEquals = null;
                    int karmaLevel = 0;
                    int[] material = null;
                    int[] count = null;
                    int createItem = 0;
                    String failureHtmlId = null;
                    String htmlId = null;
                    String[] sEqualsList = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "a", "b", "c", "d", "e", "f", "g", "h"};
                    String[] htmlIdList = new String[]{"lsmitha", "lsmithb", "lsmithc", "lsmithd", "lsmithe", "", "lsmithf", "lsmithg", "lsmithh"};
                    int[] karmaLevelList = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
                    int[][] materialsList = new int[][]{{20158, 40669, 40678}, {20144, 40672, 40678}, {20075, 40671, 40678}, {20183, 40674, 40678}, {20190, 40674, 40678}, {20078, 40674, 40678}, {20078, 40670, 40678}, {40719, 40673, 40678}};
                    int[][] countList = new int[][]{{1, 50, 100}, {1, 50, 100}, {1, 50, 100}, {1, 20, 100}, {1, 40, 100}, {1, 5, 100}, {1, 1, 100}, {1, 1, 100}};
                    int[] createItemList = new int[]{20083, 20131, 20069, 20179, 20209, 20290, 20261, 20031};
                    String[] failureHtmlIdList = new String[]{"lsmithaa", "lsmithbb", "lsmithcc", "lsmithdd", "lsmithee", "lsmithff", "lsmithgg", "lsmithhh"};
                    i2 = 0;
                    while (i2 < sEqualsList.length) {
                        if (s2.equalsIgnoreCase(sEqualsList[i2])) {
                            sEquals = sEqualsList[i2];
                            if (i2 <= 8) {
                                htmlId = htmlIdList[i2];
                                break;
                            }
                            if (i2 <= 8) break;
                            karmaLevel = karmaLevelList[i2 - 9];
                            material = materialsList[i2 - 9];
                            count = countList[i2 - 9];
                            createItem = createItemList[i2 - 9];
                            failureHtmlId = failureHtmlIdList[i2 - 9];
                            break;
                        }
                        ++i2;
                    }
                    if (s2.equalsIgnoreCase(sEquals)) {
                        if (karmaLevel != 0 && pc.Q() >= karmaLevel) {
                            materials = material;
                            counts = count;
                            createitem = new int[]{createItem};
                            createcount = new int[]{1};
                            success_htmlid = "";
                            failure_htmlid = failureHtmlId;
                        } else {
                            htmlid = htmlId;
                        }
                    }
                } else if (npcid == 80074) {
                    if (pc.P() >= 10000000) {
                        this.f(pc, npc, s2);
                    }
                    htmlid = "";
                } else if (npcid == 80057) {
                    htmlid = this.b(pc.Q());
                    htmldata = new String[]{String.valueOf(pc.R())};
                } else if (npcid == 80059 || npcid == 80060 || npcid == 80061 || npcid == 80062) {
                    htmlid = this.d(pc, (t)obj, s2);
                } else if (npcid == 81124) {
                    if (s2.equalsIgnoreCase("1")) {
                        this.a(client, 4002);
                        htmlid = "";
                    } else if (s2.equalsIgnoreCase("2")) {
                        this.a(client, 4004);
                        htmlid = "";
                    } else if (s2.equalsIgnoreCase("3")) {
                        this.a(client, 4950);
                        htmlid = "";
                    }
                } else if (npcid == 70811) {
                    if (s2.equalsIgnoreCase("contract1")) {
                        pc.bb().a(10, 1);
                        htmlid = "lyraev2";
                    }
                } else if (s2.equalsIgnoreCase("pandora6") || s2.equalsIgnoreCase("cold6") || s2.equalsIgnoreCase("balsim3") || s2.equalsIgnoreCase("arieh6") || s2.equalsIgnoreCase("andyn3") || s2.equalsIgnoreCase("ysorya3") || s2.equalsIgnoreCase("luth3") || s2.equalsIgnoreCase("catty3") || s2.equalsIgnoreCase("mayer3") || s2.equalsIgnoreCase("vergil3") || s2.equalsIgnoreCase("stella6") || s2.equalsIgnoreCase("ralf6") || s2.equalsIgnoreCase("berry6") || s2.equalsIgnoreCase("jin6") || s2.equalsIgnoreCase("defman3") || s2.equalsIgnoreCase("mellisa3") || s2.equalsIgnoreCase("mandra3") || s2.equalsIgnoreCase("bius3") || s2.equalsIgnoreCase("momo6") || s2.equalsIgnoreCase("ashurEv7") || s2.equalsIgnoreCase("elmina3") || s2.equalsIgnoreCase("glen3") || s2.equalsIgnoreCase("mellin3") || s2.equalsIgnoreCase("orcm6") || s2.equalsIgnoreCase("jackson3") || s2.equalsIgnoreCase("britt3") || s2.equalsIgnoreCase("old6") || s2.equalsIgnoreCase("shivan3")) {
                    htmlid = s2;
                    int taxRatesCastle = e.b(npc);
                    htmldata = new String[]{String.valueOf(taxRatesCastle)};
                } else if (s2.equalsIgnoreCase("set")) {
                    int town_id = an.a(npc.fs(), npc.ft(), npc.fp());
                    if (town_id >= 1 && town_id <= 10) {
                        if (pc.bF() == -1) {
                            pc.a(new ds(759));
                        } else if (pc.bF() > 0) {
                            bh.w town;
                            if (pc.bF() != town_id && (town = bh.a().a(pc.bF())) != null) {
                                pc.a(new ds(758, town.b()));
                            }
                        } else if (pc.bF() == 0) {
                            if (pc.ev() < 10) {
                                pc.a(new ds(757));
                            } else {
                                int level = pc.ev();
                                int cost = level * level * 10;
                                if (pc.j().b(40308, cost)) {
                                    pc.aD(town_id);
                                    pc.aE(0);
                                    pc.I();
                                } else {
                                    pc.a(new ds(337, "$4"));
                                }
                            }
                        }
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("clear")) {
                    int town_id = an.a((f)npc);
                    if (town_id > 0) {
                        if (pc.bF() > 0) {
                            if (pc.bF() == town_id) {
                                pc.aD(-1);
                                pc.aE(0);
                                pc.I();
                            } else {
                                pc.a(new ds(756));
                            }
                        }
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("ask")) {
                    int town_id = an.a((f)npc);
                    if (town_id >= 1 && town_id <= 10) {
                        bh.w town = bh.a().a(town_id);
                        String leader = town.d();
                        if (leader != null && leader.length() != 0) {
                            htmlid = "owner";
                            htmldata = new String[]{leader};
                        } else {
                            htmlid = "noowner";
                        }
                    }
                } else if (npcid == 71198) {
                    if (s2.equalsIgnoreCase("A")) {
                        if (pc.bb().a(71198) != 0 || pc.j().g(21059, 1)) {
                            return;
                        }
                        if (pc.j().b(41339, 5)) {
                            ao.ah.a(pc, 41340, 1, 0, npc.T());
                            pc.bb().a(71198, 1);
                            htmlid = "tion4";
                        } else {
                            htmlid = "tion9";
                        }
                    } else if (s2.equalsIgnoreCase("B")) {
                        if (pc.bb().a(71198) != 1 || pc.j().g(21059, 1)) {
                            return;
                        }
                        if (pc.j().b(41341, 1)) {
                            pc.bb().a(71198, 2);
                            htmlid = "tion5";
                        } else {
                            htmlid = "tion10";
                        }
                    } else if (s2.equalsIgnoreCase("C")) {
                        if (pc.bb().a(71198) != 2 || pc.j().g(21059, 1)) {
                            return;
                        }
                        if (pc.j().b(41343, 1)) {
                            ao.ah.a(pc, 21057, 1, 0, npc.T());
                            pc.bb().a(71198, 3);
                            htmlid = "tion6";
                        } else {
                            htmlid = "tion12";
                        }
                    } else if (s2.equalsIgnoreCase("D")) {
                        if (pc.bb().a(71198) != 3 || pc.j().g(21059, 1)) {
                            return;
                        }
                        if (pc.j().b(41344, 1)) {
                            ao.ah.a(pc, 21058, 1, 0, npc.T());
                            pc.j().b(21057, 1);
                            pc.bb().a(71198, 4);
                            htmlid = "tion7";
                        } else {
                            htmlid = "tion13";
                        }
                    } else if (s2.equalsIgnoreCase("E")) {
                        if (pc.bb().a(71198) != 4 || pc.j().g(21059, 1)) {
                            return;
                        }
                        if (pc.j().b(41345, 1)) {
                            ao.ah.a(pc, 21059, 1, 0, npc.T());
                            pc.j().b(21058, 1);
                            pc.bb().a(71198, 0);
                            pc.bb().a(71199, 0);
                            htmlid = "tion8";
                        } else {
                            htmlid = "tion15";
                        }
                    }
                } else if (npcid == 71199) {
                    if (s2.equalsIgnoreCase("A")) {
                        if (pc.bb().a(71199) != 0 || pc.j().g(21059, 1)) {
                            return;
                        }
                        if (pc.j().g(41340, 1)) {
                            pc.bb().a(71199, 1);
                            htmlid = "jeron2";
                        } else {
                            htmlid = "jeron10";
                        }
                    } else if (s2.equalsIgnoreCase("B")) {
                        if (pc.bb().a(71199) != 1 || pc.j().g(21059, 1)) {
                            return;
                        }
                        if (pc.j().b(40308, 1000000)) {
                            ao.ah.a(pc, 41341, 1, 0, npc.T());
                            pc.j().b(41340, 1);
                            pc.bb().a(71199, 255);
                            htmlid = "jeron6";
                        } else {
                            htmlid = "jeron8";
                        }
                    } else if (s2.equalsIgnoreCase("C")) {
                        if (pc.bb().a(71199) != 1 || pc.j().g(21059, 1)) {
                            return;
                        }
                        if (pc.j().b(41342, 1)) {
                            ao.ah.a(pc, 41341, 1, 0, npc.T());
                            pc.j().b(41340, 1);
                            pc.bb().a(71199, 255);
                            htmlid = "jeron5";
                        } else {
                            htmlid = "jeron9";
                        }
                    }
                } else if (npcid == 80079) {
                    if (s2.equalsIgnoreCase("0")) {
                        if (!pc.j().f(41312)) {
                            ao.ah.a(pc, 41312, 1, 0, npc.T());
                            pc.bb().a(35, 255);
                            htmlid = "keplisha7";
                        }
                    } else if (s2.equalsIgnoreCase("1")) {
                        if (!pc.j().f(41314)) {
                            if (pc.j().g(40308, 1000)) {
                                materials = new int[]{40308, 41313};
                                counts = new int[]{1000, 1};
                                createitem = new int[]{41314};
                                createcount = new int[]{1};
                                int htmlA = bi.i.a(3) + 1;
                                int htmlB = bi.i.a(100) + 1;
                                switch (htmlA) {
                                    case 1: {
                                        htmlid = "horosa" + htmlB;
                                        break;
                                    }
                                    case 2: {
                                        htmlid = "horosb" + htmlB;
                                        break;
                                    }
                                    case 3: {
                                        htmlid = "horosc" + htmlB;
                                    }
                                }
                            } else {
                                htmlid = "keplisha8";
                            }
                        }
                    } else if (s2.equalsIgnoreCase("2")) {
                        if (pc.fe() != pc.aB()) {
                            htmlid = "keplisha9";
                        } else if (pc.j().f(41314)) {
                            pc.j().b(41314, 1);
                            int html = bi.i.a(9) + 1;
                            int PolyId = 6180 + bi.i.a(64);
                            this.b(client, PolyId);
                            switch (html) {
                                case 1: {
                                    htmlid = "horomon11";
                                    break;
                                }
                                case 2: {
                                    htmlid = "horomon12";
                                    break;
                                }
                                case 3: {
                                    htmlid = "horomon13";
                                    break;
                                }
                                case 4: {
                                    htmlid = "horomon21";
                                    break;
                                }
                                case 5: {
                                    htmlid = "horomon22";
                                    break;
                                }
                                case 6: {
                                    htmlid = "horomon23";
                                    break;
                                }
                                case 7: {
                                    htmlid = "horomon31";
                                    break;
                                }
                                case 8: {
                                    htmlid = "horomon32";
                                    break;
                                }
                                case 9: {
                                    htmlid = "horomon33";
                                }
                                default: {
                                    break;
                                }
                            }
                        }
                    } else if (s2.equalsIgnoreCase("3")) {
                        pc.j().b(41312, 1);
                        pc.j().b(41313, 1);
                        pc.j().b(41314, 1);
                        htmlid = "";
                    }
                } else if (npcid == 80082) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.ev() < 15) {
                            htmlid = "fk_in_lv";
                        } else if (pc.j().b(40308, 1000)) {
                            ae.b(pc);
                            am.a(pc, 32742, 32799, 5490, 4, true);
                        } else {
                            htmlid = "fk_in_0";
                        }
                    }
                } else if (npcid == 80084) {
                    if (s2.equalsIgnoreCase("q")) {
                        if (pc.j().g(41356, 1)) {
                            htmlid = "rparum4";
                        } else {
                            ao.ah.a(pc, 41356, 1, 0, npc.T());
                            htmlid = "rparum3";
                        }
                    }
                } else if (npcid == 80105) {
                    if (s2.equalsIgnoreCase("c") && pc.x() && pc.j().g(20383, 1)) {
                        if (pc.j().g(40308, 100000)) {
                            q item = pc.j().b(20383);
                            if (item != null && item.I() != 50) {
                                item.g(50);
                                pc.j().b(item);
                                pc.j().b(40308, 100000);
                                htmlid = "";
                            }
                        } else {
                            pc.a(new ds(337, "$4"));
                        }
                    }
                } else if (npcid == 71126) {
                    if (s2.equalsIgnoreCase("B")) {
                        if (pc.j().g(41007, 1)) {
                            htmlid = "eris10";
                        } else {
                            ao.ah.a(pc, 41007, 1, 0, npc.T());
                            htmlid = "eris6";
                        }
                    } else if (s2.equalsIgnoreCase("C")) {
                        if (pc.j().g(41009, 1)) {
                            htmlid = "eris10";
                        } else {
                            ao.ah.a(pc, 41009, 1, 0, npc.T());
                            htmlid = "eris8";
                        }
                    } else if (s2.equalsIgnoreCase("A")) {
                        if (pc.j().g(41007, 1)) {
                            if (pc.j().g(40969, 20)) {
                                htmlid = "eris18";
                                materials = new int[]{40969, 41007};
                                counts = new int[]{20, 1};
                                createitem = new int[]{41008};
                                createcount = new int[]{1};
                            } else {
                                htmlid = "eris5";
                            }
                        } else {
                            htmlid = "eris2";
                        }
                    } else if (s2.equalsIgnoreCase("E")) {
                        htmlid = pc.j().g(41010, 1) ? "eris19" : "eris7";
                    } else if (s2.equalsIgnoreCase("D")) {
                        if (pc.j().g(41010, 1)) {
                            htmlid = "eris19";
                        } else if (pc.j().g(41009, 1)) {
                            if (pc.j().g(40959, 1)) {
                                htmlid = "eris17";
                                materials = new int[]{40959, 41009};
                                counts = new int[]{1, 1};
                                createitem = new int[]{41010};
                                createcount = new int[]{1};
                            } else if (pc.j().g(40960, 1)) {
                                htmlid = "eris16";
                                materials = new int[]{40960, 41009};
                                counts = new int[]{1, 1};
                                createitem = new int[]{41010};
                                createcount = new int[]{1};
                            } else if (pc.j().g(40961, 1)) {
                                htmlid = "eris15";
                                materials = new int[]{40961, 41009};
                                counts = new int[]{1, 1};
                                createitem = new int[]{41010};
                                createcount = new int[]{1};
                            } else if (pc.j().g(40962, 1)) {
                                htmlid = "eris14";
                                materials = new int[]{40962, 41009};
                                counts = new int[]{1, 1};
                                createitem = new int[]{41010};
                                createcount = new int[]{1};
                            } else if (pc.j().g(40635, 10)) {
                                htmlid = "eris12";
                                materials = new int[]{40635, 41009};
                                counts = new int[]{10, 1};
                                createitem = new int[]{41010};
                                createcount = new int[]{1};
                            } else if (pc.j().g(40638, 10)) {
                                htmlid = "eris11";
                                materials = new int[]{40638, 41009};
                                counts = new int[]{10, 1};
                                createitem = new int[]{41010};
                                createcount = new int[]{1};
                            } else if (pc.j().g(40642, 10)) {
                                htmlid = "eris13";
                                materials = new int[]{40642, 41009};
                                counts = new int[]{10, 1};
                                createitem = new int[]{41010};
                                createcount = new int[]{1};
                            } else if (pc.j().g(40667, 10)) {
                                htmlid = "eris13";
                                materials = new int[]{40667, 41009};
                                counts = new int[]{10, 1};
                                createitem = new int[]{41010};
                                createcount = new int[]{1};
                            } else {
                                htmlid = "eris8";
                            }
                        } else {
                            htmlid = "eris7";
                        }
                    }
                } else if (npcid == 80076) {
                    if (s2.equalsIgnoreCase("A")) {
                        int[] diaryno = new int[]{49082, 49083};
                        int pid = bi.i.a(diaryno.length);
                        int di2 = diaryno[pid];
                        if (di2 == 49082) {
                            htmlid = "voyager6a";
                        } else if (di2 == 49083) {
                            htmlid = "voyager6b";
                        }
                        ao.ah.a(pc, di2, 1, 0, npc.T());
                    }
                } else if (npcid == 71128) {
                    if (s2.equals("A")) {
                        htmlid = pc.j().g(41010, 1) ? "perita2" : "perita3";
                    } else if (s2.equals("p")) {
                        htmlid = pc.j().g(40987, 1) && pc.j().g(40988, 1) && pc.j().g(40989, 1) ? "perita43" : (pc.j().g(40987, 1) && pc.j().g(40989, 1) ? "perita44" : (pc.j().g(40987, 1) && pc.j().g(40988, 1) ? "perita45" : (pc.j().g(40988, 1) && pc.j().g(40989, 1) ? "perita47" : (pc.j().g(40987, 1) ? "perita46" : (pc.j().g(40988, 1) ? "perita49" : (pc.j().g(40987, 1) ? "perita48" : "perita50"))))));
                    } else if (s2.equals("q")) {
                        htmlid = pc.j().g(41173, 1) && pc.j().g(41174, 1) && pc.j().g(41175, 1) ? "perita54" : (pc.j().g(41173, 1) && pc.j().g(41175, 1) ? "perita55" : (pc.j().g(41173, 1) && pc.j().g(41174, 1) ? "perita56" : (pc.j().g(41174, 1) && pc.j().g(41175, 1) ? "perita58" : (pc.j().g(41174, 1) ? "perita57" : (pc.j().g(41175, 1) ? "perita60" : (pc.j().g(41176, 1) ? "perita59" : "perita61"))))));
                    } else if (s2.equals("s")) {
                        htmlid = pc.j().g(41161, 1) && pc.j().g(41162, 1) && pc.j().g(41163, 1) ? "perita62" : (pc.j().g(41161, 1) && pc.j().g(41163, 1) ? "perita63" : (pc.j().g(41161, 1) && pc.j().g(41162, 1) ? "perita64" : (pc.j().g(41162, 1) && pc.j().g(41163, 1) ? "perita66" : (pc.j().g(41161, 1) ? "perita65" : (pc.j().g(41162, 1) ? "perita68" : (pc.j().g(41163, 1) ? "perita67" : "perita69"))))));
                    } else if (s2.equals("B")) {
                        if (pc.j().g(40651, 10) && pc.j().g(40643, 10) && pc.j().g(40618, 10) && pc.j().g(40645, 10) && pc.j().g(40676, 10) && pc.j().g(40442, 5) && pc.j().g(40051, 1)) {
                            htmlid = "perita7";
                            materials = new int[]{40651, 40643, 40618, 40645, 40676, 40442, 40051};
                            counts = new int[]{10, 10, 10, 10, 20, 5, 1};
                            createitem = new int[]{40925};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita8";
                        }
                    } else if (s2.equals("G") || s2.equals("h") || s2.equals("i")) {
                        if (pc.j().g(40651, 5) && pc.j().g(40643, 5) && pc.j().g(40618, 5) && pc.j().g(40645, 5) && pc.j().g(40676, 5) && pc.j().g(40675, 5) && pc.j().g(40049, 3) && pc.j().g(40051, 1)) {
                            htmlid = "perita27";
                            materials = new int[]{40651, 40643, 40618, 40645, 40676, 40675, 40049, 40051};
                            counts = new int[]{5, 5, 5, 5, 10, 10, 3, 1};
                            createitem = new int[]{40926};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita28";
                        }
                    } else if (s2.equals("H") || s2.equals("j") || s2.equals("k")) {
                        if (pc.j().g(40651, 10) && pc.j().g(40643, 10) && pc.j().g(40618, 10) && pc.j().g(40645, 10) && pc.j().g(40676, 20) && pc.j().g(40675, 10) && pc.j().g(40048, 3) && pc.j().g(40051, 1)) {
                            htmlid = "perita29";
                            materials = new int[]{40651, 40643, 40618, 40645, 40676, 40675, 40048, 40051};
                            counts = new int[]{10, 10, 10, 10, 20, 10, 3, 1};
                            createitem = new int[]{40927};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita30";
                        }
                    } else if (s2.equals("I") || s2.equals("l") || s2.equals("m")) {
                        if (pc.j().g(40651, 20) && pc.j().g(40643, 20) && pc.j().g(40618, 20) && pc.j().g(40645, 20) && pc.j().g(40676, 30) && pc.j().g(40675, 10) && pc.j().g(40050, 3) && pc.j().g(40051, 1)) {
                            htmlid = "perita31";
                            materials = new int[]{40651, 40643, 40618, 40645, 40676, 40675, 40050, 40051};
                            counts = new int[]{20, 20, 20, 20, 30, 10, 3, 1};
                            createitem = new int[]{40928};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita32";
                        }
                    } else if (s2.equals("J") || s2.equals("n") || s2.equals("o")) {
                        if (pc.j().g(40651, 30) && pc.j().g(40643, 30) && pc.j().g(40618, 30) && pc.j().g(40645, 30) && pc.j().g(40676, 30) && pc.j().g(40675, 20) && pc.j().g(40052, 1) && pc.j().g(40051, 1)) {
                            htmlid = "perita33";
                            materials = new int[]{40651, 40643, 40618, 40645, 40676, 40675, 40052, 40051};
                            counts = new int[]{30, 30, 30, 30, 30, 20, 1, 1};
                            createitem = new int[]{40928};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita34";
                        }
                    } else if (s2.equals("K")) {
                        int earinga = 0;
                        int earingb = 0;
                        if (pc.j().h(21014) || pc.j().h(21006) || pc.j().h(21007)) {
                            htmlid = "perita36";
                        } else if (pc.j().g(21014, 1)) {
                            earinga = 21014;
                            earingb = 41176;
                        } else if (pc.j().g(21006, 1)) {
                            earinga = 21006;
                            earingb = 41177;
                        } else if (pc.j().g(21007, 1)) {
                            earinga = 21007;
                            earingb = 41178;
                        } else {
                            htmlid = "perita36";
                        }
                        if (earinga > 0) {
                            materials = new int[]{earinga};
                            counts = new int[]{1};
                            createitem = new int[]{earingb};
                            createcount = new int[]{1};
                        }
                    } else if (s2.equals("L")) {
                        if (pc.j().h(21015)) {
                            htmlid = "perita22";
                        } else if (pc.j().g(21015, 1)) {
                            materials = new int[]{21015};
                            counts = new int[]{1};
                            createitem = new int[]{41179};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita22";
                        }
                    } else if (s2.equals("M")) {
                        if (pc.j().h(21016)) {
                            htmlid = "perita26";
                        } else if (pc.j().g(21016, 1)) {
                            materials = new int[]{21016};
                            counts = new int[]{1};
                            createitem = new int[]{41182};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita26";
                        }
                    } else if (s2.equals("b")) {
                        if (pc.j().h(21009)) {
                            htmlid = "perita39";
                        } else if (pc.j().g(21009, 1)) {
                            materials = new int[]{21009};
                            counts = new int[]{1};
                            createitem = new int[]{41180};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita39";
                        }
                    } else if (s2.equals("d")) {
                        if (pc.j().h(21012)) {
                            htmlid = "perita41";
                        } else if (pc.j().g(21012, 1)) {
                            materials = new int[]{21012};
                            counts = new int[]{1};
                            createitem = new int[]{41183};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita41";
                        }
                    } else if (s2.equals("a")) {
                        if (pc.j().h(21008)) {
                            htmlid = "perita38";
                        } else if (pc.j().g(21008, 1)) {
                            materials = new int[]{21008};
                            counts = new int[]{1};
                            createitem = new int[]{41181};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita38";
                        }
                    } else if (s2.equals("c")) {
                        if (pc.j().h(21010)) {
                            htmlid = "perita40";
                        } else if (pc.j().g(21010, 1)) {
                            materials = new int[]{21010};
                            counts = new int[]{1};
                            createitem = new int[]{41184};
                            createcount = new int[]{1};
                        } else {
                            htmlid = "perita40";
                        }
                    }
                } else if (npcid == 71129) {
                    if (s2.equals("Z")) {
                        htmlid = "rumtis2";
                    } else if (s2.equals("Y")) {
                        htmlid = pc.j().g(41010, 1) ? "rumtis3" : "rumtis4";
                    } else if (s2.equals("q")) {
                        htmlid = "rumtis92";
                    } else if (s2.equals("A")) {
                        htmlid = pc.j().g(41161, 1) ? "rumtis6" : "rumtis101";
                    } else if (s2.equals("B")) {
                        htmlid = pc.j().g(41164, 1) ? "rumtis7" : "rumtis101";
                    } else if (s2.equals("C")) {
                        htmlid = pc.j().g(41167, 1) ? "rumtis8" : "rumtis101";
                    } else if (s2.equals("T")) {
                        htmlid = pc.j().g(41167, 1) ? "rumtis9" : "rumtis101";
                    } else if (s2.equals("w")) {
                        htmlid = pc.j().g(41162, 1) ? "rumtis14" : "rumtis101";
                    } else if (s2.equals("x")) {
                        htmlid = pc.j().g(41165, 1) ? "rumtis15" : "rumtis101";
                    } else if (s2.equals("y")) {
                        htmlid = pc.j().g(41168, 1) ? "rumtis16" : "rumtis101";
                    } else if (s2.equals("z")) {
                        htmlid = pc.j().g(41171, 1) ? "rumtis17" : "rumtis101";
                    } else if (s2.equals("U")) {
                        htmlid = pc.j().g(41163, 1) ? "rumtis10" : "rumtis101";
                    } else if (s2.equals("V")) {
                        htmlid = pc.j().g(41166, 1) ? "rumtis11" : "rumtis101";
                    } else if (s2.equals("W")) {
                        htmlid = pc.j().g(41169, 1) ? "rumtis12" : "rumtis101";
                    } else if (s2.equals("X")) {
                        htmlid = pc.j().g(41172, 1) ? "rumtis13" : "rumtis101";
                    } else if (s2.equals("D") || s2.equals("E") || s2.equals("F") || s2.equals("G")) {
                        boolean insn = false;
                        boolean bacn = false;
                        int me = 0;
                        int mr = 0;
                        int mj = 0;
                        int an2 = 0;
                        int men = 0;
                        int mrn = 0;
                        int mjn = 0;
                        int ann = 0;
                        if (pc.j().g(40959, 1) && pc.j().g(40960, 1) && pc.j().g(40961, 1) && pc.j().g(40962, 1)) {
                            insn = true;
                            me = 40959;
                            mr = 40960;
                            mj = 40961;
                            an2 = 40962;
                            men = 1;
                            mrn = 1;
                            mjn = 1;
                            ann = 1;
                        } else if (pc.j().g(40642, 10) && pc.j().g(40635, 10) && pc.j().g(40638, 10) && pc.j().g(40667, 10)) {
                            bacn = true;
                            me = 40642;
                            mr = 40635;
                            mj = 40638;
                            an2 = 40667;
                            men = 10;
                            mrn = 10;
                            mjn = 10;
                            ann = 10;
                        }
                        if (pc.j().g(40046, 1) && pc.j().g(40618, 5) && pc.j().g(40643, 5) && pc.j().g(40645, 5) && pc.j().g(40651, 5) && pc.j().g(40676, 5)) {
                            if (insn || bacn) {
                                htmlid = "rumtis60";
                                materials = new int[]{me, mr, mj, an2, 40046, 40618, 40643, 40651, 40676};
                                counts = new int[]{men, mrn, mjn, ann, 1, 5, 5, 5, 5, 5};
                                createitem = new int[]{40926};
                                createcount = new int[]{1};
                            } else {
                                htmlid = "rumtis18";
                            }
                        }
                    }
                } else if (npcid == 190554) {
                    if (s2.equalsIgnoreCase("a")) {
                        pc.a(new dc(113, pc));
                    }
                } else if (npcid >= 190571 && npcid <= 190573) {
                    if (s2.equalsIgnoreCase("a")) {
                        pc.a(new dc(113, pc));
                    }
                } else if (npcid == 81208) {
                    if (s2.equalsIgnoreCase("k")) {
                        if (pc.j().f(41135) || pc.j().f(41136) || pc.j().f(41137)) {
                            materials = new int[]{41135, 41136, 41137};
                            counts = new int[]{1, 1, 1};
                            createitem = new int[]{41138};
                            createcount = new int[]{1};
                            htmlid = "minibrob03";
                        } else if (pc.j().f(41126) || pc.j().f(41127) || pc.j().f(41128)) {
                            materials = new int[]{41126, 41127, 41128};
                            counts = new int[]{1, 1, 1};
                            createitem = new int[]{41129};
                            createcount = new int[]{1};
                            htmlid = "minibrob03";
                        }
                    }
                } else if (npcid == 80067) {
                    if (s2.equalsIgnoreCase("n")) {
                        htmlid = "";
                        if (pc.j().b(41131, 1)) {
                            this.a(client, 6034);
                            int[] itemids = new int[]{41132, 41133, 41134};
                            int i3 = 0;
                            while (i3 < itemids.length) {
                                ao.ah.a(pc, itemids[i3], 1, npc.T());
                                ++i3;
                            }
                            pc.bb().a(36, 1);
                        }
                    } else if (s2.equalsIgnoreCase("d")) {
                        htmlid = "minicod09";
                        pc.j().b(41130, 1);
                        pc.j().b(41131, 1);
                    } else if (s2.equalsIgnoreCase("k")) {
                        htmlid = "";
                        pc.j().b(41132, 1);
                        pc.j().b(41133, 1);
                        pc.j().b(41134, 1);
                        pc.j().b(41135, 1);
                        pc.j().b(41136, 1);
                        pc.j().b(41137, 1);
                        pc.j().b(41138, 1);
                        pc.bb().a(36, 0);
                    } else if (s2.equalsIgnoreCase("e")) {
                        if (pc.bb().a(36) == 255 || pc.Q() >= 1) {
                            htmlid = "";
                        } else if (pc.j().f(41138)) {
                            htmlid = "";
                            pc.B((int)(1600.0 * a.D));
                            pc.j().b(41130, 1);
                            pc.j().b(41131, 1);
                            pc.j().b(41138, 1);
                            pc.bb().a(36, 255);
                        } else {
                            htmlid = "minicod04";
                        }
                    } else if (s2.equalsIgnoreCase("g")) {
                        ao.ah.a(pc, 41130, 1, 0, npc.T());
                        htmlid = "";
                    }
                } else if (npcid == 81202) {
                    if (s2.equalsIgnoreCase("n")) {
                        htmlid = "";
                        if (pc.j().b(41122, 1)) {
                            this.a(client, 6035);
                            int[] itemids = new int[]{41123, 41124, 41125};
                            int i4 = 0;
                            while (i4 < itemids.length) {
                                ao.ah.a(pc, itemids[i4], 1, npc.T());
                                ++i4;
                            }
                            pc.bb().a(37, 1);
                        }
                    } else if (s2.equalsIgnoreCase("d")) {
                        htmlid = "minitos09";
                        pc.j().b(41121, 1);
                        pc.j().b(41122, 1);
                    } else if (s2.equalsIgnoreCase("k")) {
                        htmlid = "";
                        pc.j().b(41123, 1);
                        pc.j().b(41124, 1);
                        pc.j().b(41125, 1);
                        pc.j().b(41126, 1);
                        pc.j().b(41127, 1);
                        pc.j().b(41128, 1);
                        pc.j().b(41129, 1);
                        pc.bb().a(37, 0);
                    } else if (s2.equalsIgnoreCase("e")) {
                        if (pc.bb().a(37) == 255 || pc.Q() >= 1) {
                            htmlid = "";
                        } else if (pc.j().f(41129)) {
                            htmlid = "";
                            pc.B((int)(-1600.0 * a.D));
                            pc.j().b(41121, 1);
                            pc.j().b(41122, 1);
                            pc.j().b(41129, 1);
                            pc.bb().a(37, 255);
                        } else {
                            htmlid = "minitos04";
                        }
                    } else if (s2.equalsIgnoreCase("g")) {
                        ao.ah.a(pc, 41121, 1, 0, npc.T());
                        htmlid = "";
                    }
                } else if (npcid == 71253) {
                    if (s2.equalsIgnoreCase("A")) {
                        if (pc.j().g(49101, 100)) {
                            materials = new int[]{49101};
                            counts = new int[]{100};
                            createitem = new int[]{49092};
                            createcount = new int[]{1};
                            htmlid = "joegolem18";
                        } else {
                            htmlid = "joegolem19";
                        }
                    }
                } else if (npcid == 71256) {
                    if (s2.equalsIgnoreCase("E")) {
                        if (pc.bb().a(40) == 8 && pc.j().g(40491, 30) && pc.j().g(40495, 40) && pc.j().g(100, 1) && pc.j().g(40509, 12) && pc.j().g(40052, 1) && pc.j().g(40053, 1) && pc.j().g(40054, 1) && pc.j().g(40055, 1) && pc.j().g(41347, 1) && pc.j().g(41350, 1)) {
                            pc.j().b(40491, 30);
                            pc.j().b(40495, 40);
                            pc.j().b(100, 1);
                            pc.j().b(40509, 12);
                            pc.j().b(40052, 1);
                            pc.j().b(40053, 1);
                            pc.j().b(40054, 1);
                            pc.j().b(40055, 1);
                            pc.j().b(41347, 1);
                            pc.j().b(41350, 1);
                            htmlid = "robinhood12";
                            ao.ah.a(pc, 205, 1, 0, npc.T());
                            pc.bb().a(40, 255);
                        }
                    } else if (s2.equalsIgnoreCase("C")) {
                        if (pc.bb().a(40) == 7 && pc.j().g(41352, 4) && pc.j().g(40618, 30) && pc.j().g(40643, 30) && pc.j().g(40645, 30) && pc.j().g(40651, 30) && pc.j().g(40676, 30) && pc.j().g(40514, 20) && pc.j().g(41351, 1) && pc.j().g(41346, 1)) {
                            pc.j().b(41352, 4);
                            pc.j().b(40618, 30);
                            pc.j().b(40643, 30);
                            pc.j().b(40645, 30);
                            pc.j().b(40651, 30);
                            pc.j().b(40676, 30);
                            pc.j().b(40514, 20);
                            pc.j().b(41351, 1);
                            pc.j().b(41346, 1);
                            ao.ah.a(pc, 41347, 1, 0, npc.T());
                            ao.ah.a(pc, 41350, 1, 0, npc.T());
                            htmlid = "robinhood10";
                            pc.bb().a(40, 8);
                        }
                    } else if (s2.equalsIgnoreCase("B")) {
                        if (pc.j().f(41348) && pc.j().f(41346)) {
                            htmlid = "robinhood13";
                        } else {
                            ao.ah.a(pc, 41348, 1, 0, npc.T());
                            ao.ah.a(pc, 41346, 1, 0, npc.T());
                            htmlid = "robinhood13";
                            pc.bb().a(40, 2);
                        }
                    } else if (s2.equalsIgnoreCase("A")) {
                        if (pc.j().f(40028)) {
                            pc.j().b(40028, 1);
                            htmlid = "robinhood4";
                            pc.bb().a(40, 1);
                        } else {
                            htmlid = "robinhood19";
                        }
                    }
                } else if (npcid == 71257) {
                    if (s2.equalsIgnoreCase("D")) {
                        if (pc.j().f(41349)) {
                            htmlid = "zybril10";
                            pc.j().b(41349, 1);
                            ao.ah.a(pc, 41351, 1, 0, npc.T());
                            pc.bb().a(40, 7);
                        } else {
                            htmlid = "zybril14";
                        }
                    } else if (s2.equalsIgnoreCase("C")) {
                        if (pc.j().g(40514, 10) && pc.j().f(41353)) {
                            pc.j().b(40514, 10);
                            pc.j().b(41353, 1);
                            ao.ah.a(pc, 41354, 1, 0, npc.T());
                            htmlid = "zybril9";
                            pc.bb().a(40, 6);
                        }
                    } else if (pc.j().f(41353) && pc.j().g(40514, 10)) {
                        htmlid = "zybril8";
                    } else if (s2.equalsIgnoreCase("B")) {
                        if (pc.j().g(40048, 10) && pc.j().g(40049, 10) && pc.j().g(40050, 10) && pc.j().g(40051, 10)) {
                            pc.j().b(40048, 10);
                            pc.j().b(40049, 10);
                            pc.j().b(40050, 10);
                            pc.j().b(40051, 10);
                            ao.ah.a(pc, 41353, 1, 0, npc.T());
                            htmlid = "zybril15";
                            pc.bb().a(40, 5);
                        } else {
                            htmlid = "zybril12";
                            pc.bb().a(40, 4);
                        }
                    } else if (s2.equalsIgnoreCase("A")) {
                        if (pc.j().f(41348) && pc.j().f(41346)) {
                            htmlid = "zybril3";
                            pc.bb().a(40, 3);
                        } else {
                            htmlid = "zybril11";
                        }
                    }
                } else if (npcid == 71258) {
                    if (pc.j().f(40665)) {
                        htmlid = "marba17";
                        if (s2.equalsIgnoreCase("B")) {
                            htmlid = "marba7";
                            if (pc.j().f(214) && pc.j().f(20389) && pc.j().f(20393) && pc.j().f(20401) && pc.j().f(20406) && pc.j().f(20409)) {
                                htmlid = "marba15";
                            }
                        }
                    } else if (s2.equalsIgnoreCase("A")) {
                        if (pc.j().f(40637)) {
                            htmlid = "marba20";
                        } else {
                            ao.ah.a(pc, 40637, 1, 0, npc.T());
                            htmlid = "marba6";
                        }
                    }
                } else if (npcid == 71259) {
                    if (pc.j().f(40665)) {
                        htmlid = "aras8";
                    } else if (pc.j().f(40637)) {
                        htmlid = "aras1";
                        if (s2.equalsIgnoreCase("A")) {
                            if (pc.j().f(40664)) {
                                htmlid = "aras6";
                                htmlid = pc.j().f(40679) || pc.j().f(40680) || pc.j().f(40681) || pc.j().f(40682) || pc.j().f(40683) || pc.j().f(40684) || pc.j().f(40693) || pc.j().f(40694) || pc.j().f(40695) || pc.j().f(40697) || pc.j().f(40698) || pc.j().f(40699) ? "aras3" : "aras6";
                            } else {
                                ao.ah.a(pc, 40664, 1, 0, npc.T());
                                htmlid = "aras6";
                            }
                        } else if (s2.equalsIgnoreCase("B")) {
                            if (pc.j().f(40664)) {
                                pc.j().b(40664, 1);
                                ao.ah.a(pc, 40665, 1, 0, npc.T());
                                htmlid = "aras13";
                            } else {
                                htmlid = "aras14";
                                ao.ah.a(pc, 40665, 1, 0, npc.T());
                            }
                        } else if (s2.equalsIgnoreCase("7")) {
                            htmlid = pc.j().f(40693) && pc.j().f(40694) && pc.j().f(40695) && pc.j().f(40697) && pc.j().f(40698) && pc.j().f(40699) ? "aras10" : "aras9";
                        }
                    } else {
                        htmlid = "aras7";
                    }
                } else if (npcid == 80099) {
                    if (s2.equalsIgnoreCase("A")) {
                        if (pc.j().g(40308, 300)) {
                            pc.j().b(40308, 300);
                            ao.ah.a(pc, 41315, 1, 0, npc.T());
                            pc.bb().a(41, 1);
                            htmlid = "rarson16";
                        } else if (!pc.j().g(40308, 300)) {
                            htmlid = "rarson7";
                        }
                    } else if (s2.equalsIgnoreCase("B")) {
                        if (pc.bb().a(41) == 1 && pc.j().g(41325, 1)) {
                            pc.j().b(41325, 1);
                            ao.ah.a(pc, 40308, 2000, 0, npc.T());
                            ao.ah.a(pc, 41317, 1, 0, npc.T());
                            pc.bb().a(41, 2);
                            htmlid = "rarson9";
                        } else {
                            htmlid = "rarson10";
                        }
                    } else if (s2.equalsIgnoreCase("C")) {
                        if (pc.bb().a(41) == 4 && pc.j().g(41326, 1)) {
                            ao.ah.a(pc, 40308, 30000, 0, npc.T());
                            pc.j().b(41326, 1);
                            htmlid = "rarson12";
                            pc.bb().a(41, 5);
                        } else {
                            htmlid = "rarson17";
                        }
                    } else if (s2.equalsIgnoreCase("D")) {
                        if (pc.bb().a(41) <= 1 || pc.bb().a(41) == 5) {
                            if (pc.j().g(40308, 300)) {
                                pc.j().b(40308, 300);
                                ao.ah.a(pc, 41315, 1, 0, npc.T());
                                pc.bb().a(41, 1);
                                htmlid = "rarson16";
                            } else if (!pc.j().g(40308, 300)) {
                                htmlid = "rarson7";
                            }
                        } else if (pc.bb().a(41) >= 2 && pc.bb().a(41) <= 4) {
                            if (pc.j().g(40308, 300)) {
                                pc.j().b(40308, 300);
                                ao.ah.a(pc, 41315, 1, 0, npc.T());
                                htmlid = "rarson16";
                            } else if (!pc.j().g(40308, 300)) {
                                htmlid = "rarson7";
                            }
                        }
                    }
                } else if (npcid == 80101) {
                    if (s2.equalsIgnoreCase("request letter of kuen")) {
                        if (pc.bb().a(41) == 2 && pc.j().g(41317, 1)) {
                            pc.j().b(41317, 1);
                            ao.ah.a(pc, 41318, 1, 0, npc.T());
                            pc.bb().a(41, 3);
                            htmlid = "";
                        } else {
                            htmlid = "";
                        }
                    } else if (s2.equalsIgnoreCase("request holy mithril dust")) {
                        if (pc.bb().a(41) == 3 && pc.j().g(41315, 1) && pc.j().g(40494, 30) && pc.j().g(41318, 1)) {
                            pc.j().b(41315, 1);
                            pc.j().b(41318, 1);
                            pc.j().b(40494, 30);
                            ao.ah.a(pc, 41316, 1, 0, npc.T());
                            pc.bb().a(41, 4);
                            htmlid = "";
                        } else {
                            htmlid = "";
                        }
                    }
                } else if (npcid == 80135) {
                    if (pc.D() && s2.equalsIgnoreCase("a")) {
                        if (pc.j().g(49220, 1)) {
                            htmlid = "elas5";
                        } else {
                            ao.ah.a(pc, 49220, 1, 0, npc.T());
                            htmlid = "elas4";
                        }
                    }
                } else if (npcid == 81245) {
                    if (pc.D() && s2.equalsIgnoreCase("request flute of spy")) {
                        if (pc.j().g(49223, 1)) {
                            pc.j().b(49223, 1);
                            ao.ah.a(pc, 49222, 1, 0, npc.T());
                            htmlid = "";
                        } else {
                            htmlid = "";
                        }
                    }
                } else if (npcid == 81246) {
                    if (s2.getBytes()[0] >= "0".getBytes()[0] && s2.getBytes()[0] <= "0".getBytes()[0] + 19) {
                        int count = s2.getBytes()[0] - "0".getBytes()[0] + 1;
                        materials = new int[]{40308};
                        counts = new int[]{2500 * count};
                        if (pc.ev() < 30) {
                            htmlid = "sharna4";
                        } else if (pc.ev() >= 30 && pc.ev() <= 39) {
                            createitem = new int[]{49149};
                            createcount = new int[]{count};
                        } else if (pc.ev() >= 40 && pc.ev() <= 51) {
                            createitem = new int[]{49150};
                            createcount = new int[]{count};
                        } else if (pc.ev() >= 52 && pc.ev() <= 54) {
                            createitem = new int[]{49151};
                            createcount = new int[]{count};
                        } else if (pc.ev() >= 55 && pc.ev() <= 59) {
                            createitem = new int[]{49152};
                            createcount = new int[]{count};
                        } else if (pc.ev() >= 60 && pc.ev() <= 64) {
                            createitem = new int[]{49153};
                            createcount = new int[]{count};
                        } else if (pc.ev() >= 65 && pc.ev() <= 69) {
                            createitem = new int[]{49154};
                            createcount = new int[]{count};
                        } else if (pc.ev() >= 70) {
                            createitem = new int[]{49155};
                            createcount = new int[]{count};
                        }
                        success_htmlid = "sharna3";
                        failure_htmlid = "sharna5";
                    }
                } else if (npcid == 70035 || npcid == 70041 || npcid == 70042) {
                    if (s2.equalsIgnoreCase("status")) {
                        htmldata = as.a.a().c();
                        htmlid = "maeno4";
                    }
                } else if (npcid == 81334) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.j().g(49239, 1)) {
                            htmlid = "rtf06";
                        } else {
                            int[] item_ids = new int[]{49239};
                            int[] item_amounts = new int[]{1};
                            int i5 = 0;
                            while (i5 < item_ids.length) {
                                ao.ah.a(pc, item_ids[i5], item_amounts[i5], npc.T());
                                ++i5;
                            }
                        }
                    }
                } else if (npcid == 190346) {
                    if (s2.equalsIgnoreCase("a")) {
                        int[] skills = new int[]{42, 26, 48};
                        if (!pc.j().b(640621, 10)) {
                            pc.a(new be(objid, "pbs_03"));
                            return;
                        }
                        int[] mj = skills;
                        int mr = skills.length;
                        int i5 = 0;
                        while (i5 < mr) {
                            int skillid = mj[i5];
                            bf.a executor = g.a(skillid);
                            executor.a((f)pc, 0);
                            ++i5;
                        }
                        htmlid = "pbs_02";
                    }
                } else if (npcid >= 81353 && npcid <= 81363 || npcid == 190493 || npcid == 190578) {
                    if (s2.equals("a") || s2.equals("b")) {
                        int[] skills = s2.equals("b") ? new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115, 149} : new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115, 148};
                        if (!pc.j().b(40308, 3000)) {
                            pc.a(new be(objid, "bs_adena"));
                            return;
                        }
                        int[] mj = skills;
                        int mr = skills.length;
                        int i5 = 0;
                        while (i5 < mr) {
                            int skillid = mj[i5];
                            bf.a executor = g.a(skillid);
                            executor.a((f)pc, 0);
                            ++i5;
                        }
                        htmlid = "bs_done";
                    } else if (s2.equalsIgnoreCase("0")) {
                        htmlid = "bs_01";
                    } else if (s2.equalsIgnoreCase("1")) {
                        pc.bs(1);
                        htmlid = "bs_m4";
                        htmldata = new String[]{"500", "1000", "1000", "2000", "2000", "1"};
                    } else if (s2.equalsIgnoreCase("2")) {
                        pc.bs(5);
                        htmlid = "bs_m4";
                        htmldata = new String[]{"2500", "5000", "5000", "10000", "10000", "5"};
                    } else if (s2.equalsIgnoreCase("3")) {
                        pc.bs(10);
                        htmlid = "bs_m4";
                        htmldata = new String[]{"5000", "10000", "10000", "20000", "20000", "10"};
                    } else if (s2.equalsIgnoreCase("4")) {
                        pc.bs(100);
                        htmlid = "bs_m4";
                        htmldata = new String[]{"50000", "100000", "100000", "200000", "200000", "100"};
                    } else if (s2.equalsIgnoreCase("5")) {
                        pc.bs(500);
                        htmlid = "bs_m4";
                        htmldata = new String[]{"250000", "500000", "500000", "1000000", "1000000", "500"};
                    } else if (s2.getBytes()[0] >= "A".getBytes()[0] && s2.getBytes()[0] <= "Y".getBytes()[0] + 19) {
                        int[] skillids = new int[]{2, 3, 4, 8, 1, 14, 13, 12, 9, 15, 17, 21, 19, 22, 18, 32, 25, 26, 31, 29, 35, 37, 39, 38, 34};
                        int blanksc_skillid = skillids[s2.getBytes()[0] - 65];
                        bh.v l1skills = ao.be.a().a(blanksc_skillid);
                        if (l1skills.f() != 0 && !pc.j().g(l1skills.f(), l1skills.g() * pc.dO())) {
                            pc.a(new be(objid, "bs_m6"));
                            return;
                        }
                        if (!pc.j().g(40089 + l1skills.c(), pc.dO())) {
                            pc.a(new be(objid, "bs_m6"));
                            return;
                        }
                        int[] nArray = new int[6];
                        nArray[1] = 500;
                        nArray[2] = 1000;
                        nArray[3] = 1000;
                        nArray[4] = 2000;
                        nArray[5] = 2000;
                        int[] prices = nArray;
                        if (!pc.j().g(40308, prices[l1skills.c()] * pc.dO())) {
                            pc.a(new be(objid, "bs_m6"));
                            return;
                        }
                        ao.ah.a(pc, 40858 + blanksc_skillid, pc.dO());
                        pc.j().b(40308, prices[l1skills.c()] * pc.dO());
                        pc.j().b(40089 + l1skills.c(), pc.dO());
                        if (l1skills.f() != 0) {
                            pc.j().b(l1skills.f(), l1skills.g() * pc.dO());
                        }
                        htmlid = "bs_m1";
                    }
                } else if (npcid == 81296) {
                    int count = 0;
                    if (s2.equalsIgnoreCase("0")) {
                        count = 1;
                    } else if (s2.equalsIgnoreCase("1")) {
                        count = 3;
                    } else if (s2.equalsIgnoreCase("2")) {
                        count = 5;
                    } else if (s2.equalsIgnoreCase("3")) {
                        count = 10;
                    }
                    if (count > 0 && pc.j().b(640438, count)) {
                        pc.cs(3000 * count);
                        pc.a(new ee(pc.fr(), 7353));
                        pc.b(new ee(pc.fr(), 7353));
                        htmlid = "yuris2";
                    } else {
                        htmlid = "yuris3";
                    }
                } else if (npcid == 70701) {
                    if (s2.equalsIgnoreCase("material")) {
                        if (pc.ca() >= 1) {
                            if (pc.j().b(640381, 1) || pc.j().b(640225, 1)) {
                                pc.a(true);
                                pc.aH(0);
                                pc.a(new ee(pc.fr(), 10418));
                                pc.b(new ee(pc.fr(), 10418));
                            } else {
                                pc.a(new ds(739));
                            }
                        } else {
                            pc.a(new ds(2985));
                            htmlid = "";
                        }
                    }
                } else if (npcid == 81260) {
                    int townid = pc.bF();
                    char s1 = s2.charAt(0);
                    if (pc.ev() > 9 && townid > 0 && townid < 11) {
                        block16 : switch (s1) {
                            case '0': {
                                createitem = new int[]{49305};
                                createcount = new int[]{1};
                                materials = new int[]{40308, 40014};
                                counts = new int[]{1000, 3};
                                contribution = 2;
                                htmlid = "";
                                break;
                            }
                            case '1': {
                                createitem = new int[]{49304};
                                createcount = new int[]{1};
                                materials = new int[]{40308, 40068};
                                counts = new int[]{1000, 3};
                                contribution = 4;
                                htmlid = "";
                                break;
                            }
                            case '2': {
                                createitem = new int[]{49307};
                                createcount = new int[]{1};
                                materials = new int[]{40308, 40016};
                                counts = new int[]{500, 3};
                                contribution = 2;
                                htmlid = "";
                                break;
                            }
                            case '3': {
                                createitem = new int[]{49306};
                                createcount = new int[]{1};
                                materials = new int[]{40308, 40015};
                                counts = new int[]{1000, 3};
                                contribution = 2;
                                htmlid = "";
                                break;
                            }
                            case '4': {
                                createitem = new int[]{49302};
                                createcount = new int[]{1};
                                materials = new int[]{40308, 40013};
                                counts = new int[]{500, 3};
                                contribution = 1;
                                htmlid = "";
                                break;
                            }
                            case '5': {
                                createitem = new int[]{49303};
                                createcount = new int[]{1};
                                materials = new int[]{40308, 40032};
                                counts = new int[]{500, 3};
                                contribution = 1;
                                htmlid = "";
                                break;
                            }
                            case '6': {
                                createitem = new int[]{49308};
                                createcount = new int[]{1};
                                materials = new int[]{40308, 40088};
                                counts = new int[]{1000, 3};
                                contribution = 3;
                                htmlid = "";
                                break;
                            }
                            case 'A': 
                            case 'a': {
                                switch (townid) {
                                    case 1: {
                                        createitem = new int[]{49292};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                    case 2: {
                                        createitem = new int[]{49297};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                    case 3: {
                                        createitem = new int[]{49293};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                    case 4: {
                                        createitem = new int[]{49296};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                    case 5: {
                                        createitem = new int[]{49295};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                    case 6: {
                                        createitem = new int[]{49294};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                    case 7: {
                                        createitem = new int[]{49298};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                    case 8: {
                                        createitem = new int[]{49299};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                    case 9: {
                                        createitem = new int[]{49301};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                    case 10: {
                                        createitem = new int[]{49300};
                                        createcount = new int[]{1};
                                        materials = new int[]{40308};
                                        counts = new int[]{400};
                                        htmlid = "";
                                        break block16;
                                    }
                                }
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                    }
                } else if (npcid == 81278) {
                    if (s2.equalsIgnoreCase("0")) {
                        if (pc.j().g(46000, 1)) {
                            htmlid = "veil3";
                        } else if (pc.j().g(40308, 1000000)) {
                            pc.j().b(40308, 1000000);
                            ao.ah.a(pc, 46000, 1, npc.T());
                            htmlid = "veil7";
                        } else if (!pc.j().g(40308, 1000000)) {
                            htmlid = "veil4";
                        }
                    } else if (s2.equalsIgnoreCase("1")) {
                        htmlid = "veil9";
                    }
                } else if (npcid == 81277) {
                    int level = pc.ev();
                    char s1 = s2.charAt(0);
                    if (s2.equalsIgnoreCase("0")) {
                        if (level >= 30 && level <= 51) {
                            am.a(pc, 32820, 32904, 1002, 5, true);
                            htmlid = "";
                        } else {
                            htmlid = "dsecret3";
                        }
                    } else if (level >= 52) {
                        switch (s1) {
                            case '1': {
                                am.a(pc, 32904, 32627, 1002, 5, true);
                                break;
                            }
                            case '2': {
                                am.a(pc, 32793, 32593, 1002, 5, true);
                                break;
                            }
                            case '3': {
                                am.a(pc, 32874, 32785, 1002, 5, true);
                                break;
                            }
                            case '4': {
                                am.a(pc, 32993, 32716, 1002, 4, true);
                                break;
                            }
                            case '5': {
                                am.a(pc, 32698, 32664, 1002, 6, true);
                                break;
                            }
                            case '6': {
                                am.a(pc, 32710, 32759, 1002, 6, true);
                                break;
                            }
                            case '7': {
                                am.a(pc, 32986, 32630, 1002, 4, true);
                            }
                        }
                        htmlid = "";
                    } else {
                        htmlid = "dsecret3";
                    }
                } else if (npcid == 46164) {
                    if (s2.equalsIgnoreCase("enter")) {
                        q item = ao.ah.a().b(310);
                        if (pc.j().a(item, 1) != 0) {
                            return;
                        }
                        item.f(2);
                        pc.j().d(item);
                        pc.a(new ds(403, item.b()));
                        am.a(pc, 32624, 33057, pc.fp() - 99, 5, true);
                    }
                } else if (npcid == 46181) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.j().b(640294, 1)) {
                            if (!k.a().a(pc)) {
                                ao.ah.a(pc, 640294, 1, 0, npc.T());
                                pc.a(new ds(3903));
                            }
                        } else {
                            pc.a(new ds(337, "$18617"));
                        }
                    }
                } else if (npcid == 46180) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.bb().a(44) == 255) {
                            return;
                        }
                        if (pc.ev() >= 60) {
                            createitem = new int[]{640293};
                            createcount = new int[]{1};
                            htmlid = "";
                            pc.bb().b(44);
                        } else {
                            pc.a(new ds(2738));
                        }
                    }
                } else if (npcid == 190022) {
                    int changeCount;
                    q item;
                    if (s2.equalsIgnoreCase("a")) {
                        item = pc.j().b(640299);
                        changeCount = item == null ? 1 : item.E();
                        createitem = new int[]{40308};
                        createcount = new int[]{1000 * changeCount};
                        materials = new int[]{640299};
                        counts = new int[]{changeCount};
                    } else if (s2.equalsIgnoreCase("b")) {
                        item = pc.j().b(640300);
                        changeCount = item == null ? 1 : item.E();
                        createitem = new int[]{40308};
                        createcount = new int[]{10000 * changeCount};
                        materials = new int[]{640300};
                        counts = new int[]{changeCount};
                    } else if (s2.equalsIgnoreCase("c")) {
                        item = pc.j().b(640301);
                        changeCount = item == null ? 1 : item.E();
                        createitem = new int[]{40308};
                        createcount = new int[]{150000 * changeCount};
                        materials = new int[]{640301};
                        counts = new int[]{changeCount};
                    }
                    htmlid = "";
                } else if (npcid == 190019) {
                    if (s2.equalsIgnoreCase("a")) {
                        int changeCount;
                        q item = pc.j().b(640295);
                        int n2 = changeCount = item == null ? 0 : item.E() / 108;
                        if (changeCount == 0) {
                            changeCount = 1;
                        }
                        createitem = new int[]{640303};
                        createcount = new int[]{1 * changeCount};
                        materials = new int[]{640295};
                        counts = new int[]{108 * changeCount};
                        htmlid = "";
                    } else if (s2.equalsIgnoreCase("e")) {
                        createitem = new int[]{640306};
                        createcount = new int[]{1};
                        materials = new int[]{640295};
                        counts = new int[]{108};
                    }
                } else if (npcid == 190018) {
                    if (s2.equalsIgnoreCase("teleport jp yamato p1")) {
                        am.a(pc, 32817, 32798, 8000, 2, true);
                    }
                } else if (npcid == 190023) {
                    if (s2.equalsIgnoreCase("a")) {
                        am.a(pc, 32931, 32867, 8001, 2, true);
                    } else if (s2.equalsIgnoreCase("b")) {
                        am.a(pc, 32931, 32867, 8002, 2, true);
                    } else if (s2.equalsIgnoreCase("c")) {
                        am.a(pc, 32931, 32867, 8003, 2, true);
                    }
                } else if (npcid == 190024) {
                    if (s2.equalsIgnoreCase("a")) {
                        createitem = new int[]{640297};
                        createcount = new int[]{1};
                        materials = new int[]{640296};
                        counts = new int[]{108};
                    } else if (s2.equalsIgnoreCase("b")) {
                        createitem = new int[]{640298};
                        createcount = new int[]{1};
                        materials = new int[]{40308};
                        counts = new int[]{500000};
                    } else if (s2.equalsIgnoreCase("c")) {
                        am.a(pc, 32935, 32867, 8000, 6, true);
                    } else if (s2.equalsIgnoreCase("D")) {
                        if ((pc.cP() & dn.f) == dn.f) {
                            htmlid = "tw_sanojo3";
                        } else if (pc.j().b(40308, 20000000)) {
                            pc.bc(pc.cP() | dn.f);
                            pc.a(new dn(67, pc.cP()));
                            htmlid = "tw_sanojo4";
                        } else {
                            htmlid = "tw_sanojo3";
                        }
                    }
                } else if (npcid == 190020) {
                    if (s2.equalsIgnoreCase("a")) {
                        am.a(pc, 33416, 32826, 4, 5, true);
                    }
                } else if (npcid == 190027) {
                    if (s2.equalsIgnoreCase("r")) {
                        bf.a().a(pc);
                    }
                } else if (npcid == 190028) {
                    if (s2.equalsIgnoreCase("enter") && !as.i.a().a(pc)) {
                        pc.a(new ds(3903));
                    }
                } else if (npcid == 190046) {
                    int level = 0;
                    int slotBit = 0;
                    int adenaCount = 0;
                    if (s2.equalsIgnoreCase("C")) {
                        level = 59;
                        slotBit = dn.e;
                        adenaCount = 20000000;
                    } else if (s2.equalsIgnoreCase("A")) {
                        level = 76;
                        slotBit = dn.c;
                        adenaCount = 10000000;
                    } else if (s2.equalsIgnoreCase("B")) {
                        level = 81;
                        slotBit = dn.d;
                        adenaCount = 30000000;
                    } else if (s2.equalsIgnoreCase("E")) {
                        level = 83;
                        slotBit = dn.g;
                        adenaCount = 30000000;
                    } else if (s2.equalsIgnoreCase("F")) {
                        level = 70;
                        slotBit = dn.h;
                        adenaCount = 2000000;
                    }
                    if ((pc.cP() & slotBit) == slotBit) {
                        htmlid = "slot5";
                    } else if (pc.ev() < level) {
                        htmlid = "slot3";
                    } else if (pc.j().b(40308, adenaCount)) {
                        if (slotBit == dn.g) {
                            ao.ah.a(pc, 21542, 1, npc.et());
                        }
                        pc.bc(pc.cP() | slotBit);
                        pc.a(new dn(67, pc.cP()));
                        htmlid = "slot9";
                    } else {
                        htmlid = "slot6";
                    }
                } else if (npcid == 190081) {
                    if (s2.equals("A") || s2.equals("B") || s2.equals("C") || s2.equals("D")) {
                        if (pc.ev() < 10 || pc.ev() > 44) {
                            htmlid = "newbiegate3";
                        } else {
                            int mapid = 25 + pc.ev() / 10 - 1;
                            am.a(pc, 32799, 32799, mapid, 5, true);
                        }
                    }
                } else if (npcid == 190080) {
                    if (s2.equalsIgnoreCase("B")) {
                        if (pc.j().f(640804)) {
                            if (!pc.bB(4085)) {
                                pc.bH(100);
                                pc.bJ(100);
                                pc.c(5);
                                pc.d(5);
                                pc.bN(1);
                                pc.bR(1);
                                pc.bP(1);
                                pc.bX(1);
                                pc.bV(1);
                                pc.bT(1);
                                pc.cA(5);
                                pc.ck(5);
                                pc.cl(5);
                                pc.cp(3);
                                pc.F(5);
                                pc.bL(-5);
                                pc.a(new cm(132, pc.u()));
                            }
                            pc.j(4085, 1800000);
                            pc.a(new dc(4085, 1800, 8, 4470, 0, 4385, 0, 0, 1));
                            pc.a(new ee(pc.fr(), 14102));
                            pc.b(new ee(pc.fr(), 14102));
                        } else {
                            if (!pc.bB(4069)) {
                                pc.bH(10);
                                pc.bJ(10);
                                pc.c(1);
                                pc.d(1);
                                pc.bL(-3);
                            }
                            pc.j(4069, 1800000);
                            pc.a(new dc(4069, 1800, 8, 4470, 0, 4387, 0, 0, 1));
                            pc.a(new ee(pc.fr(), 10299));
                            pc.b(new ee(pc.fr(), 10299));
                        }
                        htmlid = "tw_tnbuff1";
                    }
                } else if (npcid == 190084) {
                    if (s2.equalsIgnoreCase("itemresolve")) {
                        pc.a(new v(48, obj.fr()));
                    }
                } else if (npcid == 190096) {
                    if (pc.ev() < 52) {
                        htmlid = "ekins3";
                    } else if (s2.equalsIgnoreCase("a")) {
                        createitem = new int[]{640373};
                        createcount = new int[]{3};
                        materials = new int[]{640368, 640357};
                        counts = new int[]{5, 1};
                        success_htmlid = "ekins4";
                        failure_htmlid = "ekins5";
                        if (pc.j().g(640368, 5) && pc.j().g(640357, 1)) {
                            int exp = (int)((double)ao.w.b(64) * 0.02);
                            pc.x(exp);
                            pc.a(new ee(pc.fr(), 10418));
                            pc.b(new ee(pc.fr(), 10418));
                        }
                    } else if (s2.equalsIgnoreCase("b")) {
                        createitem = new int[]{640373};
                        createcount = new int[]{3};
                        materials = new int[]{640368, 640358};
                        counts = new int[]{5, 1};
                        success_htmlid = "ekins4";
                        failure_htmlid = "ekins5";
                        if (pc.j().g(640368, 5) && pc.j().g(640358, 1)) {
                            int exp = (int)((double)ao.w.b(64) * 0.06);
                            pc.x(exp);
                            pc.a(new ee(pc.fr(), 10418));
                            pc.b(new ee(pc.fr(), 10418));
                        }
                    }
                } else if (npcid == 190093) {
                    boolean check = false;
                    q item = pc.j().b(640368);
                    if (item != null && item.E() >= 5) {
                        int need_count = 0;
                        for (q needItem : pc.j().d()) {
                            if (needItem.N() != 640357 && needItem.N() != 640358) continue;
                            need_count += needItem.E();
                        }
                        if (item.E() / 5 >= need_count) {
                            check = true;
                        }
                    }
                    if (pc.ev() < 52) {
                        htmlid = "edlen5";
                    } else if (!pc.j().f(640357) && !pc.j().f(640358)) {
                        htmlid = "edlen3";
                    } else if (check) {
                        htmlid = "edlen2";
                    } else if (s2.equalsIgnoreCase("a")) {
                        am.a(pc, 32835, 32771, 1931, 5, true);
                    } else if (s2.equalsIgnoreCase("b")) {
                        am.a(pc, 32703, 32645, 1931, 5, true);
                    } else if (s2.equalsIgnoreCase("c")) {
                        am.a(pc, 32759, 32714, 1931, 5, true);
                    } else if (s2.equalsIgnoreCase("d")) {
                        am.a(pc, 32644, 32833, 1931, 5, true);
                    }
                } else if (npcid == 190113) {
                    if (s2.equalsIgnoreCase("enter") && !c.a().a(pc)) {
                        pc.a(new ds(3903));
                    }
                } else if (npcid == 81402) {
                    if (s2.equalsIgnoreCase("enter") && !as.f.a().a(pc, 1)) {
                        pc.a(new ds(3903));
                    }
                } else if (npcid == 81403) {
                    if (s2.equalsIgnoreCase("enter") && !as.f.a().a(pc, 2)) {
                        pc.a(new ds(3903));
                    }
                } else if (npcid == 81404) {
                    if (s2.equalsIgnoreCase("a")) {
                        pc.j().a(640700);
                        ao.ah.a(pc, 640700, 1, true);
                        htmlid = "icqwand2";
                    } else if (s2.equalsIgnoreCase("b")) {
                        pc.j().a(640699);
                        ao.ah.a(pc, 640699, 100, true);
                        htmlid = "icqwand3";
                    }
                } else if (npcid == 190352) {
                    if (s2.equalsIgnoreCase("a")) {
                        ao.ah.a(pc, 49031, 1);
                        ao.ah.a(pc, 21081, 1);
                        am.a(pc, 34062, 32311, 4, 5, true);
                    }
                } else if (npcid == 190166) {
                    if (s2.equalsIgnoreCase("C")) {
                        pc.b(640385, 30, 0);
                        htmlid = "twf_oldwishe";
                        htmldata = new String[]{"$19543"};
                    } else if (s2.equalsIgnoreCase("D")) {
                        pc.b(21372, 10, 9);
                        htmlid = "twf_oldwishe";
                        htmldata = new String[]{"+9 $19665"};
                    } else if (s2.equalsIgnoreCase("E")) {
                        pc.b(21373, 10, 9);
                        htmlid = "twf_oldwishe";
                        htmldata = new String[]{"+9 $19666"};
                    } else if (s2.equalsIgnoreCase("F")) {
                        pc.b(41682, 10, 0);
                        htmlid = "twf_oldwishe";
                        htmldata = new String[]{"$17824"};
                    } else if (s2.equalsIgnoreCase("G")) {
                        pc.b(21226, 10, 0);
                        htmlid = "twf_oldwishe";
                        htmldata = new String[]{"$16747"};
                    } else if (s2.equalsIgnoreCase("H")) {
                        pc.b(21227, 10, 0);
                        htmlid = "twf_oldwishe";
                        htmldata = new String[]{"$15611"};
                    } else if (s2.equalsIgnoreCase("I")) {
                        pc.b(21228, 10, 0);
                        htmlid = "twf_oldwishe";
                        htmldata = new String[]{"$15621"};
                    } else if (s2.equalsIgnoreCase("J")) {
                        pc.b(21229, 10, 0);
                        htmlid = "twf_oldwishe";
                        htmldata = new String[]{"$15631"};
                    } else if (s2.equalsIgnoreCase("Z") && pc.dy() != null) {
                        if (pc.j().b(640384, pc.dy()[1])) {
                            ao.ah.a(pc, pc.dy()[0], 1, pc.dy()[2], npc.U_().A());
                        } else {
                            htmlid = "twf_oldwish4";
                            pc.a(new ds(337, "$19085 (" + pc.dy()[1] + ")"));
                        }
                    }
                } else if (npcid == 190272) {
                    if (s2.equalsIgnoreCase("A")) {
                        if (pc.j().f(640556) || pc.j().f(21339)) {
                            htmlid = "twf_earring3";
                        } else {
                            ao.ah.a(pc, 640556, 1, npc.T());
                            ao.ah.a(pc, 21330, 1, npc.T());
                            htmlid = "twf_earring4";
                        }
                    } else if (s2.equalsIgnoreCase("B")) {
                        q ring = null;
                        int itemid = 21330;
                        while (itemid <= 21339) {
                            ring = pc.j().b(itemid);
                            if (ring != null) break;
                            ++itemid;
                        }
                        if (ring == null) {
                            htmlid = "twf_earring2";
                        } else if (ring.D()) {
                            htmlid = "twf_earring7";
                        } else {
                            htmlid = "twf_earring1";
                            htmldata = new String[]{"" + (ring.N() - 21330)};
                        }
                    } else if (s2.equalsIgnoreCase("C")) {
                        q ring = null;
                        int itemid = 21330;
                        while (itemid <= 21339) {
                            ring = pc.j().b(itemid);
                            if (ring != null) break;
                            ++itemid;
                        }
                        if (ring == null) {
                            htmlid = "twf_earring2";
                        } else if (ring.D()) {
                            htmlid = "twf_earring7";
                        } else if (ring.N() == 21339) {
                            htmlid = "twf_earring5";
                        } else {
                            itemid = ring.N();
                            switch (itemid) {
                                case 21330: {
                                    materials = new int[]{itemid, 640266};
                                    counts = new int[]{1, 5};
                                    break;
                                }
                                case 21331: {
                                    materials = new int[]{itemid, 640266};
                                    counts = new int[]{1, 10};
                                    break;
                                }
                                case 21332: {
                                    materials = new int[]{itemid, 640266};
                                    counts = new int[]{1, 15};
                                    break;
                                }
                                case 21333: {
                                    materials = new int[]{itemid, 640266};
                                    counts = new int[]{1, 15};
                                    break;
                                }
                                case 21334: {
                                    materials = new int[]{itemid, 640266};
                                    counts = new int[]{1, 15};
                                    break;
                                }
                                case 21335: {
                                    materials = new int[]{itemid, 640266};
                                    counts = new int[]{1, 30};
                                    break;
                                }
                                case 21336: {
                                    materials = new int[]{itemid, 640266, 40308};
                                    counts = new int[]{1, 30, 500000};
                                    break;
                                }
                                case 21337: {
                                    materials = new int[]{itemid, 640266, 40308};
                                    counts = new int[]{1, 30, 750000};
                                    break;
                                }
                                case 21338: {
                                    materials = new int[]{itemid, 640266, 40308, 640556};
                                    counts = new int[]{1, 30, 1000000, 1};
                                }
                            }
                            createitem = new int[]{itemid + 1};
                            createcount = new int[]{1};
                            htmlid = "";
                        }
                    }
                } else if (npcid == 190168) {
                    int[] skills = new int[]{};
                    if (s2.equalsIgnoreCase("4")) {
                        skills = new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115, 149};
                    } else if (s2.equalsIgnoreCase("5")) {
                        skills = new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115, 148};
                    } else if (s2.equalsIgnoreCase("6")) {
                        skills = new int[]{43, 79, 151, 158, 160, 206, 211, 216, 115};
                    }
                    int[] mj = skills;
                    int needItem = skills.length;
                    int need_count = 0;
                    while (need_count < needItem) {
                        int skillid = mj[need_count];
                        bf.a executor = g.a(skillid);
                        executor.a((f)pc, 0);
                        ++need_count;
                    }
                    htmlid = "";
                } else if (npcid == 190274) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.j().f(640557) || pc.j().f(640558) || pc.j().f(640559) || pc.j().f(640564) || pc.j().f(640565) || pc.j().f(640566) || pc.j().f(640567) || pc.j().f(640568) || pc.j().f(640569) || pc.j().f(640570) || pc.j().f(640571) || pc.j().f(640572) || pc.j().f(640573)) {
                            htmlid = "oldbook3";
                        } else {
                            ao.ah.a(pc, 640557, 1);
                            htmlid = "oldbook2";
                        }
                    }
                } else if (npcid == 190328) {
                    as.h.a().a(pc, s2, (t)obj);
                } else if (npcid == 190327) {
                    if (s2.equalsIgnoreCase("1")) {
                        as.h.a().a(pc, (t)obj);
                    } else if (s2.equalsIgnoreCase("2")) {
                        as.h.a().a(pc, 4, (t)obj);
                    } else if (s2.equalsIgnoreCase("3")) {
                        as.h.a().a(pc, 8, (t)obj);
                    } else if (s2.equalsIgnoreCase("4")) {
                        as.h.a().a(pc, 16, (t)obj);
                    } else if (s2.equalsIgnoreCase("6")) {
                        as.h.a().b(pc, (t)obj);
                    }
                } else if (npcid == 81401) {
                    if (s2.equalsIgnoreCase("b")) {
                        pc.bb().a(45, 1);
                        if (!pc.j().f(640694)) {
                            createitem = new int[]{640694};
                            createcount = new int[]{100};
                            htmlid = "marbinquest2";
                        } else {
                            htmlid = "marbinquest3";
                        }
                    } else if (s2.equalsIgnoreCase("c")) {
                        q item = pc.j().b(640694);
                        if (item == null && pc.bb().a(45) > 0) {
                            pc.bb().b(45);
                            htmlid = "marbinquest6";
                        } else {
                            htmlid = "marbinquest7";
                        }
                    }
                } else if (npcid == 190492) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.j().f(640786)) {
                            if (!pc.bB(1032)) {
                                pc.bH(15);
                                pc.bJ(15);
                                pc.F(1);
                            }
                            pc.j(1032, 0x6DDD00);
                            pc.a(new ee(pc.fr(), 14483));
                            pc.b(new ee(pc.fr(), 14483));
                            pc.a(new dc(1032, 7200, 8, 7225, 0, 4621, 0, 0, 3));
                        } else {
                            htmlid = "tw_vip6";
                        }
                    } else if (s2.equalsIgnoreCase("b")) {
                        if (pc.j().f(640787)) {
                            if (!pc.bB(1033)) {
                                pc.bH(30);
                                pc.bJ(30);
                                pc.F(2);
                            }
                            pc.j(1033, 0x6DDD00);
                            pc.a(new ee(pc.fr(), 14483));
                            pc.b(new ee(pc.fr(), 14483));
                            pc.a(new dc(1033, 7200, 8, 7899, 0, 5063, 0, 0, 3));
                        } else {
                            htmlid = "tw_vip6";
                        }
                    } else if (s2.equalsIgnoreCase("c")) {
                        if (pc.j().f(640788)) {
                            if (!pc.bB(1034)) {
                                pc.bH(45);
                                pc.bJ(45);
                                pc.F(3);
                            }
                            pc.j(1034, 0x6DDD00);
                            pc.a(new ee(pc.fr(), 14483));
                            pc.b(new ee(pc.fr(), 14483));
                            pc.a(new dc(1034, 7200, 8, 7904, 0, 5064, 0, 0, 3));
                        } else {
                            htmlid = "tw_vip6";
                        }
                    } else if (s2.equalsIgnoreCase("d")) {
                        if (pc.j().f(640789)) {
                            if (!pc.bB(1035)) {
                                pc.bH(65);
                                pc.bJ(65);
                                pc.F(4);
                            }
                            pc.j(1035, 0x6DDD00);
                            pc.a(new ee(pc.fr(), 14483));
                            pc.b(new ee(pc.fr(), 14483));
                            pc.a(new dc(1035, 7200, 8, 7901, 0, 5065, 0, 0, 3));
                        } else {
                            htmlid = "tw_vip6";
                        }
                    } else if (s2.equalsIgnoreCase("e")) {
                        if (pc.j().f(640790)) {
                            if (!pc.bB(1036)) {
                                pc.bH(100);
                                pc.bJ(75);
                                pc.F(5);
                            }
                            pc.j(1036, 0x6DDD00);
                            pc.a(new ee(pc.fr(), 14483));
                            pc.b(new ee(pc.fr(), 14483));
                            pc.a(new dc(1036, 7200, 8, 7903, 0, 5066, 0, 0, 3));
                        } else {
                            htmlid = "tw_vip6";
                        }
                    }
                } else if (npcid == 81290) {
                    if (s2.equalsIgnoreCase("buy 7")) {
                        if (pc.j().b(47011, 1)) {
                            q petamu = ao.ah.a(pc, 40314, 1, 0, true);
                            if (petamu != null) {
                                aw.a().a(97023, petamu.fr());
                                pc.a(new bm(petamu));
                            }
                        } else {
                            pc.a(new ds(337, "$7779(1)"));
                        }
                    } else if (s2.equalsIgnoreCase("buy 8")) {
                        if (pc.j().b(47012, 1)) {
                            q petamu = ao.ah.a(pc, 40314, 1, 0, true);
                            if (petamu != null) {
                                aw.a().a(97022, petamu.fr());
                                pc.a(new bm(petamu));
                            }
                        } else {
                            pc.a(new ds(337, "$7780(1)"));
                        }
                    }
                } else if (npcid == 190886) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (pc.j().f(413)) {
                            ae.b(pc);
                            am.a(pc, 32873, 32799, 6311, 5, true);
                        } else {
                            htmlid = "tw_170308b1";
                        }
                    }
                } else if (npcid == 190926) {
                    if (s2.equalsIgnoreCase("a")) {
                        if (!pc.j().f(40029)) {
                            ao.ah.a(pc, 40029, 200, npc.T());
                            htmlid = "clgunter41";
                        } else {
                            htmlid = "clgunter42";
                        }
                    }
                } else if (npcid == 190927) {
                    if (s2.equalsIgnoreCase("a")) {
                        pc.a(pc.ew());
                        pc.i_(pc.ex());
                        pc.a(new ds(77));
                        pc.a(new ee(pc.fr(), 830));
                        new bf.bc().a((f)pc, 0);
                        htmlid = "";
                    }
                } else if (npcid == 81371) {
                    if (s2.equalsIgnoreCase("c")) {
                        if (!pc.j().f(41701)) {
                            createitem = new int[]{41701};
                            createcount = new int[]{1};
                            htmlid = "j_html00";
                        } else {
                            htmlid = "j_html03";
                        }
                    } else if (s2.equalsIgnoreCase("a")) {
                        if (pc.j().f(41703) && pc.j().g(40308, 10000)) {
                            pc.j().b(40308, 10000);
                            q item = pc.j().b(41703);
                            if (item.E() > 3) {
                                pc.j().b(41703, item.E() - 3);
                            } else {
                                pc.j().b(41703, 1);
                            }
                            pc.bb().a(43, 1);
                            am.a(pc, 32735, 32865, 9100, 5, true);
                        } else {
                            htmlid = "j_html02";
                        }
                    } else if (s2.equalsIgnoreCase("b")) {
                        if (pc.j().f(41702) && pc.j().g(40308, 10000)) {
                            pc.j().b(40308, 10000);
                            q item = pc.j().b(41702);
                            if (item.E() > 3) {
                                pc.j().b(41702, item.E() - 3);
                            } else {
                                pc.j().b(41702, 1);
                            }
                            pc.bb().a(42, 1);
                            am.a(pc, 32735, 32865, 9100, 5, true);
                        } else {
                            htmlid = "j_html02";
                        }
                    }
                } else if (npcid == 81372) {
                    if (s2.equalsIgnoreCase("enter")) {
                        if (pc.l()) {
                            int leader_questid = 43;
                            for (u party_pc : pc.aL().c()) {
                                am.a(party_pc, 32727, 32724, 9000, 5, true);
                            }
                            as.e.a().a(10);
                            return;
                        }
                        if (pc.am()) {
                            int leader_questid = 43;
                            if (pc.bb().a(42) > 0) {
                                leader_questid = 42;
                            }
                            if (leader_questid == 42 && as.g.a().a) {
                                boolean check = true;
                                for (u party_pc : pc.aL().c()) {
                                    if (party_pc.fp() == 9100 && party_pc.bb().a(leader_questid) != 0) continue;
                                    check = false;
                                    break;
                                }
                                if (check && pc.aL().b() >= 3) {
                                    for (u party_pc : pc.aL().c()) {
                                        am.a(party_pc, 32799, 32803, 9101, 5, true);
                                    }
                                    as.g.a().a(10);
                                } else {
                                    htmlid = "id1_1";
                                }
                            } else if (leader_questid == 43 && as.e.a().b) {
                                boolean check = true;
                                for (u party_pc : pc.aL().c()) {
                                    if (party_pc.fp() == 9100 && party_pc.bb().a(leader_questid) != 0) continue;
                                    check = false;
                                    break;
                                }
                                if (check && pc.aL().b() >= 5) {
                                    for (u party_pc : pc.aL().c()) {
                                        am.a(party_pc, 32727, 32724, 9000, 5, true);
                                    }
                                    as.e.a().a(10);
                                } else {
                                    htmlid = "id0_1";
                                }
                            } else {
                                htmlid = "id14";
                            }
                        } else {
                            htmlid = "id1_2";
                        }
                    }
                } else if (s2.equalsIgnoreCase("room")) {
                    int status = af.a().a(pc, npcid, false);
                    if (status == -3) {
                        htmlid = "inn6";
                    } else if (status == -1) {
                        htmlid = "inn5";
                    } else if (status == -2) {
                        htmlid = "inn15";
                    } else {
                        pc.br(status);
                        pc.a(new bc(npc, 300, 1, 8, "inn2"));
                    }
                } else if (s2.equalsIgnoreCase("hall")) {
                    if (pc.x()) {
                        int status = af.a().a(pc, npcid, true);
                        if (status == -3) {
                            htmlid = "inn6";
                        } else if (status == -1) {
                            htmlid = "inn5";
                        } else if (status == -2) {
                            htmlid = "inn15";
                        } else {
                            pc.br(status);
                            pc.a(new bc(npc, 1760, 10, 40, "inn12"));
                        }
                    } else {
                        htmlid = "inn10";
                    }
                } else if (s2.equalsIgnoreCase("return")) {
                    int price = af.a().b(pc);
                    if (price > 0) {
                        htmldata = new String[]{npc.et(), String.valueOf(price)};
                        htmlid = "inn20";
                        ao.ah.a(pc, 40308, price, npc.T());
                    } else {
                        htmlid = "";
                    }
                } else if (s2.equalsIgnoreCase("enter")) {
                    af.a().a(pc);
                }
            }
        }
        if (createitem != null) {
            boolean isCreate = true;
            if (materials != null && counts != null) {
                int i6 = 0;
                while (i6 < materials.length) {
                    if (!pc.j().h((int)materials[i6], (int)counts[i6])) {
                        bh.j temp = ao.ah.a().a((int)materials[i6]);
                        pc.a(new ds(337, String.valueOf(temp.h()) + "(" + (int)counts[i6] + ")"));
                        isCreate = false;
                    }
                    ++i6;
                }
            }
            if (isCreate && createcount != null) {
                int create_count = 0;
                int create_weight = 0;
                int i7 = 0;
                while (i7 < createitem.length) {
                    bh.j temp;
                    if (createitem[i7] > 0 && createcount[i7] > 0 && (temp = ao.ah.a().a((int)createitem[i7])) != null) {
                        if (temp.aF()) {
                            if (!pc.j().f((int)createitem[i7])) {
                                ++create_count;
                            }
                        } else {
                            create_count += createcount[i7];
                        }
                        create_weight += temp.l() * createcount[i7] / 1000;
                    }
                    ++i7;
                }
                if (pc.j().c() + create_count > 180) {
                    pc.a(new ds(263));
                    return;
                }
                if (pc.K() < (double)(pc.j().e() + create_weight)) {
                    pc.a(new ds(82));
                    return;
                }
                if (materials != null && counts != null) {
                    int j2 = 0;
                    while (j2 < materials.length) {
                        pc.j().b((int)materials[j2], (int)counts[j2]);
                        ++j2;
                    }
                }
                int k2 = 0;
                while (k2 < createitem.length) {
                    if (createitem[k2] > 0 && createcount[k2] > 0) {
                        ao.ah.a(pc, (int)createitem[k2], (int)createcount[k2], npc.T());
                    }
                    ++k2;
                }
                if (success_htmlid != null) {
                    pc.a(new be(objid, success_htmlid, htmldata));
                }
                if (contribution > 0) {
                    pc.u(contribution);
                }
            } else if (failure_htmlid != null) {
                pc.a(new be(objid, failure_htmlid, htmldata));
            }
        }
        if (htmlid != null) {
            pc.a(new be(objid, htmlid, htmldata));
        }
    }

    private String b(int level) {
        if (level == 0 || level < -7 || 7 < level) {
            return "";
        }
        String htmlid = "";
        if (level > 0) {
            htmlid = "vbk" + level;
        } else if (level < 0) {
            htmlid = "vyk" + Math.abs(level);
        }
        return htmlid;
    }

    private void a(d clientthread, int polyId) {
        u pc = clientthread.f();
        if (pc.j().g(40308, 100)) {
            pc.j().b(40308, 100);
            ae.a(pc, polyId, 1800, 4);
        } else {
            pc.a(new ds(337, "$4"));
        }
    }

    private void b(d clientthread, int polyId) {
        u pc = clientthread.f();
        if (pc.j().g(40308, 100)) {
            pc.j().b(40308, 100);
            ae.a(pc, polyId, 1800, 8);
        } else {
            pc.a(new ds(337, "$4"));
        }
    }

    private String a(u pc, int objectId, int npcid) {
        i clan = ao.q.a().a(pc.aF());
        if (clan == null) {
            return "";
        }
        int houseId = clan.n();
        if (houseId == 0) {
            return "";
        }
        bh.i house = ab.a().a(houseId);
        int keeperId = house.f();
        if (npcid != keeperId) {
            return "";
        }
        if (!pc.x()) {
            pc.a(new ds(518));
            return "";
        }
        if (pc.fr() != clan.k()) {
            pc.a(new ds(518));
            return "";
        }
        if (house.g()) {
            return "agonsale";
        }
        pc.setL1rAmountContext(objectId, 2);
        pc.a(new dq(objectId, String.valueOf(houseId)));
        return null;
    }

    private void a(u pc, t npc, String s2) {
        int houseId;
        i clan = ao.q.a().a(pc.aF());
        if (clan != null && (houseId = clan.n()) != 0) {
            bh.i house = ab.a().a(houseId);
            int keeperId = house.f();
            if (npc.z() == keeperId) {
                ap.f door1 = null;
                ap.f door2 = null;
                ap.f door3 = null;
                ap.f door4 = null;
                ap.f[] fArray = ao.t.b().c();
                int n2 = fArray.length;
                int n3 = 0;
                while (n3 < n2) {
                    ap.f door = fArray[n3];
                    if (door.p() == keeperId) {
                        if (door1 == null) {
                            door1 = door;
                        } else if (door2 == null) {
                            door2 = door;
                        } else if (door3 == null) {
                            door3 = door;
                        } else {
                            door4 = door;
                            break;
                        }
                    }
                    ++n3;
                }
                if (door1 != null) {
                    if (s2.equalsIgnoreCase("open")) {
                        door1.f();
                    } else if (s2.equalsIgnoreCase("close")) {
                        door1.g();
                    }
                }
                if (door2 != null) {
                    if (s2.equalsIgnoreCase("open")) {
                        door2.f();
                    } else if (s2.equalsIgnoreCase("close")) {
                        door2.g();
                    }
                }
                if (door3 != null) {
                    if (s2.equalsIgnoreCase("open")) {
                        door3.f();
                    } else if (s2.equalsIgnoreCase("close")) {
                        door3.g();
                    }
                }
                if (door4 != null) {
                    if (s2.equalsIgnoreCase("open")) {
                        door4.f();
                    } else if (s2.equalsIgnoreCase("close")) {
                        door4.g();
                    }
                }
            }
        }
    }

    private void a(u pc, int keeperId, boolean isOpen) {
        i clan;
        boolean isNowWar = false;
        int pcCastleId = 0;
        if (pc.aF() != 0 && (clan = ao.q.a().a(pc.aF())) != null) {
            pcCastleId = clan.m();
        }
        if (keeperId == 70656 || keeperId == 70549 || keeperId == 70985) {
            if (this.c(1) && pcCastleId != 1) {
                return;
            }
            isNowWar = b.a().a(1);
        } else if (keeperId == 70600) {
            if (this.c(2) && pcCastleId != 2) {
                return;
            }
            isNowWar = b.a().a(2);
        } else if (keeperId == 70778 || keeperId == 70987 || keeperId == 70687) {
            if (this.c(3) && pcCastleId != 3) {
                return;
            }
            isNowWar = b.a().a(3);
        } else if (keeperId == 70817 || keeperId == 70800 || keeperId == 70988 || keeperId == 70990 || keeperId == 70989 || keeperId == 70991) {
            if (this.c(4) && pcCastleId != 4) {
                return;
            }
            isNowWar = b.a().a(4);
        } else if (keeperId == 70863 || keeperId == 70992 || keeperId == 70862) {
            if (this.c(5) && pcCastleId != 5) {
                return;
            }
            isNowWar = b.a().a(5);
        } else if (keeperId == 70995 || keeperId == 70994 || keeperId == 70993) {
            if (this.c(6) && pcCastleId != 6) {
                return;
            }
            isNowWar = b.a().a(6);
        } else if (keeperId == 70996) {
            if (this.c(7) && pcCastleId != 7) {
                return;
            }
            isNowWar = b.a().a(7);
        }
        ap.f[] fArray = ao.t.b().c();
        int n2 = fArray.length;
        int n3 = 0;
        while (n3 < n2) {
            ap.f door = fArray[n3];
            if (!(door.p() != keeperId || isNowWar && door.ew() > 1)) {
                if (isOpen) {
                    door.f();
                } else {
                    door.g();
                }
            }
            ++n3;
        }
    }

    private boolean c(int castleId) {
        boolean isExistDefenseClan = false;
        for (i clan : ao.q.a().b().values()) {
            if (castleId != clan.m()) continue;
            isExistDefenseClan = true;
            break;
        }
        return isExistDefenseClan;
    }

    private void a(u clanPc, int keeperId) {
        int houseId = 0;
        for (bh.i house : ab.a().c().values()) {
            if (house.f() != keeperId) continue;
            houseId = house.b();
        }
        if (houseId == 0) {
            return;
        }
        int[] loc = new int[3];
        for (aa object : aq.a().b()) {
            u pc;
            if (!(object instanceof u) || !r.a(houseId, (pc = (u)object).fs(), pc.ft(), pc.fp()) || clanPc.aF() == pc.aF()) continue;
            loc = r.a(houseId, 0);
            am.a(pc, loc[0], loc[1], loc[2], 5, true);
        }
    }

    private void a(u pc) {
        int castleId;
        i clan = ao.q.a().a(pc.aF());
        if (clan != null && (castleId = clan.m()) != 0) {
            if (!b.a().a(castleId)) {
                ap.f[] fArray = ao.t.b().c();
                int n2 = fArray.length;
                int n3 = 0;
                while (n3 < n2) {
                    ap.f door = fArray[n3];
                    if (e.a(castleId, door)) {
                        door.h();
                    }
                    ++n3;
                }
                pc.a(new ds(990));
            } else {
                pc.a(new ds(991));
            }
        }
    }

    private boolean a(u pc, t npc) {
        int houseId;
        i clan = ao.q.a().a(pc.aF());
        if (clan != null && (houseId = clan.n()) != 0) {
            bh.i house = ab.a().a(houseId);
            int keeperId = house.f();
            if (npc.z() == keeperId) {
                int remainingTime = (int)((house.i().getTime() - System.currentTimeMillis()) / 86400000L);
                if (remainingTime >= a.an / 2) {
                    pc.a(new ds(1729));
                } else {
                    if (pc.j().g(40308, 2000)) {
                        pc.j().b(40308, 2000);
                        Timestamp ts = new Timestamp(System.currentTimeMillis() + (long)(a.an * 24 * 60 * 60) * 1000L);
                        house.a(ts);
                        ab.a().a(house);
                        return true;
                    }
                    pc.a(new ds(189));
                }
            }
        }
        return false;
    }

    private String[] b(u pc, t npc) {
        int houseId;
        String name = npc.U_().c();
        String[] result = new String[]{name, "2000", "1", "1", "00"};
        i clan = ao.q.a().a(pc.aF());
        if (clan != null && (houseId = clan.n()) != 0) {
            bh.i house = ab.a().a(houseId);
            int keeperId = house.f();
            if (npc.z() == keeperId) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date(house.i().getTime()));
                int month = cal.get(2) + 1;
                int day = cal.get(5);
                int hour = cal.get(11);
                result = new String[]{name, "2000", String.valueOf(month), String.valueOf(day), String.valueOf(hour)};
            }
        }
        return result;
    }

    private String[] d(int castleId) {
        bh.d castle = ao.g.a().a(castleId);
        if (castle == null) {
            return null;
        }
        Calendar warTime = castle.c();
        int year = warTime.get(1);
        int month = warTime.get(2) + 1;
        int day = warTime.get(5);
        int hour = warTime.get(11);
        int minute = warTime.get(12);
        String[] result = castleId == 2 ? new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(hour), String.valueOf(minute)} : new String[]{"", String.valueOf(year), String.valueOf(month), String.valueOf(day), String.valueOf(hour), String.valueOf(minute)};
        return result;
    }

    private void b(u pc, t npc, String s2) {
        int[] amuletIdList = new int[]{20358, 20359, 20360, 20361, 20362, 20363, 20364, 20365};
        int amuletId = 0;
        if (s2.equalsIgnoreCase("1")) {
            amuletId = amuletIdList[0];
        } else if (s2.equalsIgnoreCase("2")) {
            amuletId = amuletIdList[1];
        } else if (s2.equalsIgnoreCase("3")) {
            amuletId = amuletIdList[2];
        } else if (s2.equalsIgnoreCase("4")) {
            amuletId = amuletIdList[3];
        } else if (s2.equalsIgnoreCase("5")) {
            amuletId = amuletIdList[4];
        } else if (s2.equalsIgnoreCase("6")) {
            amuletId = amuletIdList[5];
        } else if (s2.equalsIgnoreCase("7")) {
            amuletId = amuletIdList[6];
        } else if (s2.equalsIgnoreCase("8")) {
            amuletId = amuletIdList[7];
        }
        if (amuletId != 0) {
            ao.ah.a(pc, amuletId, 1, npc.T());
            int[] nArray = amuletIdList;
            int n2 = amuletIdList.length;
            int n3 = 0;
            while (n3 < n2) {
                int id = nArray[n3];
                if (id != amuletId && pc.j().f(id)) {
                    pc.j().b(id, 1);
                }
                ++n3;
            }
        }
    }

    private void c(u pc, t npc, String s2) {
        int[] earringIdList = new int[]{21020, 21021, 21022, 21023, 21024, 21025, 21026, 21027};
        int earringId = 0;
        if (s2.equalsIgnoreCase("1")) {
            earringId = earringIdList[0];
        } else if (s2.equalsIgnoreCase("2")) {
            earringId = earringIdList[1];
        } else if (s2.equalsIgnoreCase("3")) {
            earringId = earringIdList[2];
        } else if (s2.equalsIgnoreCase("4")) {
            earringId = earringIdList[3];
        } else if (s2.equalsIgnoreCase("5")) {
            earringId = earringIdList[4];
        } else if (s2.equalsIgnoreCase("6")) {
            earringId = earringIdList[5];
        } else if (s2.equalsIgnoreCase("7")) {
            earringId = earringIdList[6];
        } else if (s2.equalsIgnoreCase("8")) {
            earringId = earringIdList[7];
        }
        if (earringId != 0) {
            ao.ah.a(pc, earringId, 1, npc.T());
            int[] nArray = earringIdList;
            int n2 = earringIdList.length;
            int n3 = 0;
            while (n3 < n2) {
                int id = nArray[n3];
                if (id != earringId && pc.j().f(id)) {
                    pc.j().b(id, 1);
                }
                ++n3;
            }
        }
    }

    private String d(u pc, t npc, String s2) {
        String htmlid = "";
        int protectionId = 0;
        int sealId = 0;
        int locX = 0;
        int locY = 0;
        int mapId = 0;
        if (npc.z() == 80059) {
            protectionId = 40909;
            sealId = 40913;
            locX = 32773;
            locY = 32835;
            mapId = 607;
        } else if (npc.z() == 80060) {
            protectionId = 40912;
            sealId = 40916;
            locX = 32757;
            locY = 32842;
            mapId = 606;
        } else if (npc.z() == 80061) {
            protectionId = 40910;
            sealId = 40914;
            locX = 32830;
            locY = 32822;
            mapId = 604;
        } else if (npc.z() == 80062) {
            protectionId = 40911;
            sealId = 40915;
            locX = 32835;
            locY = 32822;
            mapId = 605;
        }
        if (s2.equalsIgnoreCase("a")) {
            am.a(pc, locX, locY, mapId, 5, true);
            htmlid = "";
        } else if (s2.equalsIgnoreCase("b")) {
            ao.ah.a(pc, protectionId, 1, npc.T());
            htmlid = "";
        } else if (s2.equalsIgnoreCase("c")) {
            htmlid = "wpass07";
        } else if (s2.equalsIgnoreCase("d")) {
            if (pc.j().f(sealId)) {
                q item = pc.j().b(sealId);
                pc.j().b(sealId, item.E());
            }
        } else if (s2.equalsIgnoreCase("e")) {
            htmlid = "";
        } else if (s2.equalsIgnoreCase("f")) {
            if (pc.j().f(protectionId)) {
                pc.j().b(protectionId, 1);
            }
            if (pc.j().f(sealId)) {
                q item = pc.j().b(sealId);
                pc.j().b(sealId, item.E());
            }
            htmlid = "";
        }
        return htmlid;
    }

    private void e(u pc, t npc, String s2) {
        if (s2.equalsIgnoreCase("1")) {
            pc.B((int)(500.0 * a.D));
            ao.ah.a(pc, 40718, 1, npc.T());
            pc.a(new ds(1081));
        } else if (s2.equalsIgnoreCase("2")) {
            pc.B((int)(5000.0 * a.D));
            ao.ah.a(pc, 40718, 10, npc.T());
            pc.a(new ds(1081));
        } else if (s2.equalsIgnoreCase("3")) {
            pc.B((int)(50000.0 * a.D));
            ao.ah.a(pc, 40718, 100, npc.T());
            pc.a(new ds(1081));
        }
    }

    private void f(u pc, t npc, String s2) {
        if (s2.equalsIgnoreCase("1")) {
            pc.B((int)(-500.0 * a.D));
            ao.ah.a(pc, 40678, 1, npc.T());
            pc.a(new ds(1080));
        } else if (s2.equalsIgnoreCase("2")) {
            pc.B((int)(-5000.0 * a.D));
            ao.ah.a(pc, 40678, 10, npc.T());
            pc.a(new ds(1080));
        } else if (s2.equalsIgnoreCase("3")) {
            pc.B((int)(-50000.0 * a.D));
            ao.ah.a(pc, 40678, 100, npc.T());
            pc.a(new ds(1080));
        }
    }

    @Override
    public String a() {
        return "C_NpcAction";
    }
}

