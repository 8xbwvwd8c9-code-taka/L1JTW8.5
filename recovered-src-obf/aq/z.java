/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.ah;
import ao.bh;
import ao.g;
import ao.q;
import ao.w;
import ap.t;
import ap.u;
import aq.a;
import aq.af;
import aq.f;
import aq.i;
import as.d;
import at.c;
import be.be;
import be.ds;
import be.ea;
import be.ee;
import bf.j;

public class z {
    public static void a(t npc, u pc) {
        int npcid = npc.U_().b();
        u _master = (u)npc.M();
        String htmlid = null;
        switch (npcid) {
            case 71093: {
                htmlid = _master.equals(pc) ? "searcherk2" : "searcherk4";
                break;
            }
            case 71094: {
                htmlid = _master.equals(pc) ? "endiaq2" : "endiaq4";
                break;
            }
            case 71062: {
                htmlid = _master.equals(pc) ? "kamit2" : "kamit1";
                break;
            }
            case 71075: {
                htmlid = _master.equals(pc) ? "llizard2" : "llizard1a";
                break;
            }
            case 70957: {
                htmlid = "roi2";
                break;
            }
            case 81350: {
                htmlid = "dspy3";
            }
        }
        if (htmlid != null) {
            pc.a(new be(npc.fr(), htmlid, ""));
        }
    }

    public static void b(t npc, u pc) {
        int objid = npc.fr();
        int npcid = npc.U_().b();
        int karmaLevel = 0;
        boolean pcLv = false;
        boolean lv15_step = false;
        boolean lv30_step = false;
        boolean lv45_step = false;
        boolean lv50_step = false;
        af quest = pc.bb();
        String htmlid = null;
        String[] htmldata = null;
        bh.d l1castle = null;
        i clan = null;
        String clan_name = "\u5b89\u5b89\u59b3\u597d\u518d\u898b_";
        String pri_name = "Srwh";
        if (d.a().a(npcid, pc)) {
            return;
        }
        if (a.a().a(npcid, objid, pc)) {
            return;
        }
        switch (npcid) {
            case 70538: 
            case 70560: 
            case 70644: 
            case 70667: 
            case 70725: 
            case 70790: 
            case 70884: {
                if (!pc.x()) {
                    htmlid = "bpledge1";
                    break;
                }
                if (pc.aH() != 4) break;
                htmlid = "bpledge2";
                break;
            }
            case 70060: {
                if (pc.A()) break;
                htmlid = "elfinM1";
                break;
            }
            case 81431: {
                if (pc.A()) break;
                htmlid = "rinda2";
                break;
            }
            case 190492: {
                if (pc.fp() == 6340) {
                    htmlid = "tw_vip1";
                    break;
                }
                if (pc.fp() == 6341) {
                    htmlid = "tw_vip2";
                    break;
                }
                if (pc.fp() == 6342) {
                    htmlid = "tw_vip3";
                    break;
                }
                if (pc.fp() == 6343) {
                    htmlid = "tw_vip4";
                    break;
                }
                if (pc.fp() == 6344) {
                    htmlid = "tw_vip5";
                    break;
                }
                htmlid = "tw_vip6";
                break;
            }
            case 190489: {
                if (pc.ev() <= 55) break;
                htmlid = "tel_lala2";
                break;
            }
            case 190367: {
                if (pc.ev() < 10) break;
                htmlid = "cltoti";
                break;
            }
            case 190487: {
                if (pc.ev() >= 81) break;
                htmlid = "chisvall2";
                break;
            }
            case 190366: {
                htmlid = "clgunter2";
                if (pc.ev() >= 8) break;
                z.d(pc, 2);
                htmlid = "clgunter1";
                break;
            }
            case 81404: {
                if (!pc.j().f(640699)) break;
                htmlid = "icqwand4";
                break;
            }
            case 81401: {
                if (pc.bb().a(45) == 255) {
                    htmlid = "marbinquest9";
                    break;
                }
                if (!pc.j().f(640694) && pc.bb().a(45) != 1) break;
                htmlid = "marbinquest3";
                break;
            }
            case 70080: {
                if (pc.z()) {
                    htmlid = "horunev2";
                    break;
                }
                if (pc.D() || pc.E() || pc.F()) {
                    htmlid = "horundk1";
                    break;
                }
                htmlid = "horun1";
                break;
            }
            case 190276: {
                htmlid = pc.ev() < 70 ? "nerva1" : "nerva2";
                break;
            }
            case 190277: {
                if (pc.j().f(640562)) {
                    htmlid = "riddle1";
                    break;
                }
                if (pc.j().f(640563)) {
                    htmlid = "riddle3";
                    break;
                }
                htmlid = "riddle4";
                break;
            }
            case 190274: {
                if (pc.j().f(640560)) {
                    htmlid = "oldbook1";
                    break;
                }
                htmlid = "oldbook2";
                break;
            }
            case 190272: {
                if (pc.j().f(21339)) {
                    htmlid = "twf_earring5";
                    break;
                }
                htmlid = "twf_earring";
                break;
            }
            case 190096: {
                if (pc.j().f(640357) || pc.j().f(640358)) {
                    htmlid = "ekins2";
                    break;
                }
                htmlid = "ekins1";
                break;
            }
            case 190081: {
                htmlid = pc.ev() < 52 ? "newbiegate1" : "newbiegate2";
                break;
            }
            case 46180: {
                if (quest.a(44) == 255) {
                    htmlid = "hamo1";
                    break;
                }
                htmlid = "hamo2";
                break;
            }
            case 46181: {
                if (pc.j().f(640294)) {
                    htmlid = "eldnas2";
                    break;
                }
                htmlid = "eldnas1";
                break;
            }
            case 70841: {
                if (pc.A()) {
                    htmlid = "luudielE1";
                    break;
                }
                if (!pc.C()) break;
                htmlid = "luudielCE1";
                break;
            }
            case 70794: {
                if (pc.x()) {
                    htmlid = "gerardp1";
                    break;
                }
                if (pc.z()) {
                    htmlid = "gerardkev5";
                    break;
                }
                if (pc.A()) {
                    htmlid = "gerarde1";
                    break;
                }
                if (pc.B()) {
                    htmlid = "gerardw1";
                    break;
                }
                if (!pc.C()) break;
                htmlid = "gerardde1";
                break;
            }
            case 190139: 
            case 190143: {
                if (pc.x() && pc.ev() < 10) {
                    htmlid = "sirissnt";
                    break;
                }
                if (pc.z() && pc.ev() < 50) {
                    htmlid = "sirissnw";
                    break;
                }
                if (pc.A() && pc.ev() < 8) {
                    htmlid = "sirissnt";
                    break;
                }
                if (pc.B() && pc.ev() < 4) {
                    htmlid = "sirissnt";
                    break;
                }
                if (pc.C() && pc.ev() < 12) {
                    htmlid = "sirissnt";
                    break;
                }
                if (pc.D()) {
                    htmlid = "sirisswr";
                    break;
                }
                if (pc.E()) {
                    htmlid = "sirissdk";
                    break;
                }
                if (pc.F() && pc.ev() < 50) {
                    htmlid = "sirisswr";
                    break;
                }
                htmlid = "siriss";
                break;
            }
            case 70009: {
                if (pc.x()) {
                    htmlid = "gerengp1";
                    break;
                }
                if (pc.z()) {
                    htmlid = "gerengk1";
                    break;
                }
                if (pc.A()) {
                    htmlid = "gerenge1";
                    break;
                }
                if (pc.B()) {
                    htmlid = "gerengTe1";
                    break;
                }
                if (pc.C()) {
                    htmlid = "gerengde1";
                    break;
                }
                if (pc.D()) {
                    htmlid = "gerengdk1";
                    break;
                }
                if (pc.E()) {
                    htmlid = "gerengi1";
                    break;
                }
                if (!pc.F()) break;
                htmlid = "gerengwr1";
                break;
            }
            case 81334: {
                if (pc.x()) {
                    htmlid = "rtf01";
                    break;
                }
                if (pc.z()) {
                    htmlid = "rtf02";
                    break;
                }
                if (pc.A()) {
                    htmlid = "rtf03";
                    break;
                }
                if (!pc.B()) break;
                htmlid = "rtf04";
                break;
            }
            case 81335: {
                if (pc.j().g(49241, 1)) {
                    htmlid = "50q_pout1";
                    break;
                }
                htmlid = "50q_pout";
                break;
            }
            case 70087: {
                if (!pc.C()) break;
                htmlid = "sedia";
                break;
            }
            case 70099: {
                if (quest.c(11) || pc.ev() <= 13) break;
                htmlid = "kuper1";
                break;
            }
            case 70796: {
                if (quest.c(11) || pc.ev() <= 13) break;
                htmlid = "dunham1";
                break;
            }
            case 70011: {
                int time = c.a().b().c() % 86400;
                if (time >= 21600 && time <= 72000) break;
                htmlid = "shipEvI6";
                break;
            }
            case 70553: {
                if (!z.c(pc, 1)) break;
                if (z.a(pc)) {
                    htmlid = "ishmael1";
                    break;
                }
                htmlid = "ishmael6";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70822: {
                if (!z.c(pc, 2)) break;
                if (z.a(pc)) {
                    htmlid = "seghem1";
                    break;
                }
                htmlid = "seghem6";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70784: {
                if (!z.c(pc, 3)) break;
                if (z.a(pc)) {
                    htmlid = "othmond1";
                    break;
                }
                htmlid = "othmond6";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70623: {
                if (!z.c(pc, 4)) break;
                if (z.a(pc)) {
                    htmlid = "orville1";
                    break;
                }
                htmlid = "orville6";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70880: {
                if (!z.c(pc, 5)) break;
                if (z.a(pc)) {
                    htmlid = "fisher1";
                    break;
                }
                htmlid = "fisher6";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70665: {
                if (!z.c(pc, 6)) break;
                if (z.a(pc)) {
                    htmlid = "potempin1";
                    break;
                }
                htmlid = "potempin6";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70721: {
                if (!z.c(pc, 7)) break;
                if (z.a(pc)) {
                    htmlid = "timon1";
                    break;
                }
                htmlid = "timon6";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 81155: {
                if (!z.c(pc, 8)) break;
                if (z.a(pc)) {
                    htmlid = "olle1";
                    break;
                }
                htmlid = "olle6";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 80057: {
                karmaLevel = pc.Q();
                String[] html1 = new String[]{"alfons1", "cbk1", "cbk2", "cbk3", "cbk4", "cbk5", "cbk6", "cbk7", "cbk8"};
                String[] html2 = new String[]{"cyk1", "cyk2", "cyk3", "cyk4", "cyk5", "cyk6", "cyk7", "cyk8"};
                if (karmaLevel < 0) {
                    htmlid = html2[Math.abs(karmaLevel) - 1];
                    break;
                }
                if (karmaLevel < 0) break;
                htmlid = html1[karmaLevel];
                break;
            }
            case 80058: {
                int level = pc.ev();
                if (level <= 44) {
                    htmlid = "cpass03";
                    break;
                }
                if (level > 51 || 45 > level) break;
                htmlid = "cpass02";
                break;
            }
            case 80059: {
                if (pc.Q() > 0) {
                    htmlid = "cpass03";
                    break;
                }
                if (pc.j().f(40921)) {
                    htmlid = "wpass02";
                    break;
                }
                if (pc.j().f(40917)) {
                    htmlid = "wpass14";
                    break;
                }
                if (pc.j().f(40912) || pc.j().f(40910) || pc.j().f(40911)) {
                    htmlid = "wpass04";
                    break;
                }
                if (pc.j().f(40909)) {
                    int count = z.b(pc);
                    if (pc.j().g(40913, count)) {
                        z.a(pc, 1, count);
                        htmlid = "wpass06";
                        break;
                    }
                    htmlid = "wpass03";
                    break;
                }
                if (!pc.j().f(40913)) break;
                htmlid = "wpass08";
                break;
            }
            case 80060: {
                if (pc.Q() > 0) {
                    htmlid = "cpass03";
                    break;
                }
                if (pc.j().f(40921)) {
                    htmlid = "wpass02";
                    break;
                }
                if (pc.j().f(40920)) {
                    htmlid = "wpass13";
                    break;
                }
                if (pc.j().f(40909) || pc.j().f(40910) || pc.j().f(40911)) {
                    htmlid = "wpass04";
                    break;
                }
                if (pc.j().f(40912)) {
                    int count = z.b(pc);
                    if (pc.j().g(40916, count)) {
                        z.a(pc, 8, count);
                        htmlid = "wpass06";
                        break;
                    }
                    htmlid = "wpass03";
                    break;
                }
                if (!pc.j().f(40916)) break;
                htmlid = "wpass08";
                break;
            }
            case 80061: {
                if (pc.Q() > 0) {
                    htmlid = "cpass03";
                    break;
                }
                if (pc.j().f(40921)) {
                    htmlid = "wpass02";
                    break;
                }
                if (pc.j().f(40918)) {
                    htmlid = "wpass11";
                    break;
                }
                if (pc.j().f(40909) || pc.j().f(40912) || pc.j().f(40911)) {
                    htmlid = "wpass04";
                    break;
                }
                if (pc.j().f(40910)) {
                    int count = z.b(pc);
                    if (pc.j().g(40914, count)) {
                        z.a(pc, 4, count);
                        htmlid = "wpass06";
                        break;
                    }
                    htmlid = "wpass03";
                    break;
                }
                if (!pc.j().f(40914)) break;
                htmlid = "wpass08";
                break;
            }
            case 80062: {
                if (pc.Q() > 0) {
                    htmlid = "cpass03";
                    break;
                }
                if (pc.j().f(40921)) {
                    htmlid = "wpass02";
                    break;
                }
                if (pc.j().f(40919)) {
                    htmlid = "wpass12";
                    break;
                }
                if (pc.j().f(40909) || pc.j().f(40912) || pc.j().f(40910)) {
                    htmlid = "wpass04";
                    break;
                }
                if (pc.j().f(40911)) {
                    int count = z.b(pc);
                    if (pc.j().g(40915, count)) {
                        z.a(pc, 2, count);
                        htmlid = "wpass06";
                        break;
                    }
                    htmlid = "wpass03";
                    break;
                }
                if (!pc.j().f(40915)) break;
                htmlid = "wpass08";
                break;
            }
            case 80065: {
                if (pc.Q() < 3) break;
                htmlid = "uturn1";
                break;
            }
            case 80047: {
                if (pc.Q() > -3) break;
                htmlid = "uhelp2";
                break;
            }
            case 80049: {
                if (pc.P() > -10000000) break;
                htmlid = "betray11";
                break;
            }
            case 80050: {
                if (pc.Q() <= -1) break;
                htmlid = "meet103";
                break;
            }
            case 80053: {
                karmaLevel = pc.Q();
                if (karmaLevel == 0) {
                    htmlid = "aliceyet";
                    break;
                }
                if (karmaLevel >= 1) {
                    if (pc.j().f(196) || pc.j().f(197) || pc.j().f(198) || pc.j().f(199) || pc.j().f(200) || pc.j().f(201) || pc.j().f(202) || pc.j().f(203)) {
                        htmlid = "alice_gd";
                        break;
                    }
                    htmlid = "gd";
                    break;
                }
                if (karmaLevel > -1) break;
                if (pc.j().f(40991)) {
                    if (karmaLevel > -1) break;
                    htmlid = "Mate_1";
                    break;
                }
                if (pc.j().f(196)) {
                    if (karmaLevel <= -2) {
                        htmlid = "Mate_2";
                        break;
                    }
                    htmlid = "alice_1";
                    break;
                }
                if (pc.j().f(197)) {
                    if (karmaLevel <= -3) {
                        htmlid = "Mate_3";
                        break;
                    }
                    htmlid = "alice_2";
                    break;
                }
                if (pc.j().f(198)) {
                    if (karmaLevel <= -4) {
                        htmlid = "Mate_4";
                        break;
                    }
                    htmlid = "alice_3";
                    break;
                }
                if (pc.j().f(199)) {
                    if (karmaLevel <= -5) {
                        htmlid = "Mate_5";
                        break;
                    }
                    htmlid = "alice_4";
                    break;
                }
                if (pc.j().f(200)) {
                    if (karmaLevel <= -6) {
                        htmlid = "Mate_6";
                        break;
                    }
                    htmlid = "alice_5";
                    break;
                }
                if (pc.j().f(201)) {
                    if (karmaLevel <= -7) {
                        htmlid = "Mate_7";
                        break;
                    }
                    htmlid = "alice_6";
                    break;
                }
                if (pc.j().f(202)) {
                    if (karmaLevel <= -8) {
                        htmlid = "Mate_8";
                        break;
                    }
                    htmlid = "alice_7";
                    break;
                }
                if (pc.j().f(203)) {
                    htmlid = "alice_8";
                    break;
                }
                htmlid = "alice_no";
                break;
            }
            case 80055: {
                int amuletLevel = 0;
                if (pc.j().f(20358)) {
                    amuletLevel = 1;
                } else if (pc.j().f(20359)) {
                    amuletLevel = 2;
                } else if (pc.j().f(20360)) {
                    amuletLevel = 3;
                } else if (pc.j().f(20361)) {
                    amuletLevel = 4;
                } else if (pc.j().f(20362)) {
                    amuletLevel = 5;
                } else if (pc.j().f(20363)) {
                    amuletLevel = 6;
                } else if (pc.j().f(20364)) {
                    amuletLevel = 7;
                } else if (pc.j().f(20365)) {
                    amuletLevel = 8;
                }
                if (pc.Q() == -1) {
                    if (amuletLevel >= 1) {
                        htmlid = "uamuletd";
                        break;
                    }
                    htmlid = "uamulet1";
                    break;
                }
                if (pc.Q() == -2) {
                    if (amuletLevel >= 2) {
                        htmlid = "uamuletd";
                        break;
                    }
                    htmlid = "uamulet2";
                    break;
                }
                if (pc.Q() == -3) {
                    if (amuletLevel >= 3) {
                        htmlid = "uamuletd";
                        break;
                    }
                    htmlid = "uamulet3";
                    break;
                }
                if (pc.Q() == -4) {
                    if (amuletLevel >= 4) {
                        htmlid = "uamuletd";
                        break;
                    }
                    htmlid = "uamulet4";
                    break;
                }
                if (pc.Q() == -5) {
                    if (amuletLevel >= 5) {
                        htmlid = "uamuletd";
                        break;
                    }
                    htmlid = "uamulet5";
                    break;
                }
                if (pc.Q() == -6) {
                    if (amuletLevel >= 6) {
                        htmlid = "uamuletd";
                        break;
                    }
                    htmlid = "uamulet6";
                    break;
                }
                if (pc.Q() == -7) {
                    if (amuletLevel >= 7) {
                        htmlid = "uamuletd";
                        break;
                    }
                    htmlid = "uamulet7";
                    break;
                }
                if (pc.Q() == -8) {
                    if (amuletLevel >= 8) {
                        htmlid = "uamuletd";
                        break;
                    }
                    htmlid = "uamulet8";
                    break;
                }
                htmlid = "uamulet0";
                break;
            }
            case 80056: {
                if (pc.P() > -10000000) break;
                htmlid = "infamous11";
                break;
            }
            case 80064: {
                if (pc.Q() >= 1) break;
                htmlid = "meet003";
                break;
            }
            case 80066: {
                if (pc.P() >= 10000000) break;
                htmlid = "betray02";
                break;
            }
            case 80071: {
                int earringLevel = 0;
                if (pc.j().f(21020)) {
                    earringLevel = 1;
                } else if (pc.j().f(21021)) {
                    earringLevel = 2;
                } else if (pc.j().f(21022)) {
                    earringLevel = 3;
                } else if (pc.j().f(21023)) {
                    earringLevel = 4;
                } else if (pc.j().f(21024)) {
                    earringLevel = 5;
                } else if (pc.j().f(21025)) {
                    earringLevel = 6;
                } else if (pc.j().f(21026)) {
                    earringLevel = 7;
                } else if (pc.j().f(21027)) {
                    earringLevel = 8;
                }
                if (pc.Q() == 1) {
                    if (earringLevel >= 1) {
                        htmlid = "lringd";
                        break;
                    }
                    htmlid = "lring1";
                    break;
                }
                if (pc.Q() == 2) {
                    if (earringLevel >= 2) {
                        htmlid = "lringd";
                        break;
                    }
                    htmlid = "lring2";
                    break;
                }
                if (pc.Q() == 3) {
                    if (earringLevel >= 3) {
                        htmlid = "lringd";
                        break;
                    }
                    htmlid = "lring3";
                    break;
                }
                if (pc.Q() == 4) {
                    if (earringLevel >= 4) {
                        htmlid = "lringd";
                        break;
                    }
                    htmlid = "lring4";
                    break;
                }
                if (pc.Q() == 5) {
                    if (earringLevel >= 5) {
                        htmlid = "lringd";
                        break;
                    }
                    htmlid = "lring5";
                    break;
                }
                if (pc.Q() == 6) {
                    if (earringLevel >= 6) {
                        htmlid = "lringd";
                        break;
                    }
                    htmlid = "lring6";
                    break;
                }
                if (pc.Q() == 7) {
                    if (earringLevel >= 7) {
                        htmlid = "lringd";
                        break;
                    }
                    htmlid = "lring7";
                    break;
                }
                if (pc.Q() == 8) {
                    if (earringLevel >= 8) {
                        htmlid = "lringd";
                        break;
                    }
                    htmlid = "lring8";
                    break;
                }
                htmlid = "lring0";
                break;
            }
            case 80072: {
                karmaLevel = pc.Q();
                String[] html = new String[]{"lsmith0", "lsmith1", "lsmith2", "lsmith3", "lsmith4", "lsmith5", "lsmith7", "lsmith8"};
                if (karmaLevel > 8) break;
                htmlid = html[karmaLevel - 1];
                break;
            }
            case 80074: {
                if (pc.P() < 10000000) break;
                htmlid = "infamous01";
                break;
            }
            case 80104: {
                if (pc.x()) break;
                htmlid = "horseseller4";
                break;
            }
            case 70528: {
                htmlid = z.b(pc, 1);
                break;
            }
            case 70546: {
                htmlid = z.b(pc, 6);
                break;
            }
            case 70567: {
                htmlid = z.b(pc, 3);
                break;
            }
            case 70815: {
                htmlid = z.b(pc, 4);
                break;
            }
            case 70774: {
                htmlid = z.b(pc, 5);
                break;
            }
            case 70799: {
                htmlid = z.b(pc, 2);
                break;
            }
            case 70594: {
                htmlid = z.b(pc, 7);
                break;
            }
            case 70860: {
                htmlid = z.b(pc, 8);
                break;
            }
            case 70654: {
                htmlid = z.b(pc, 9);
                break;
            }
            case 70748: {
                htmlid = z.b(pc, 10);
                break;
            }
            case 70534: {
                htmlid = z.a(pc, 1);
                break;
            }
            case 70556: {
                htmlid = z.a(pc, 6);
                break;
            }
            case 70572: {
                htmlid = z.a(pc, 3);
                break;
            }
            case 70830: {
                htmlid = z.a(pc, 4);
                break;
            }
            case 70788: {
                htmlid = z.a(pc, 5);
                break;
            }
            case 70806: {
                htmlid = z.a(pc, 2);
                break;
            }
            case 70631: {
                htmlid = z.a(pc, 7);
                break;
            }
            case 70876: {
                htmlid = z.a(pc, 8);
                break;
            }
            case 70663: {
                htmlid = z.a(pc, 9);
                break;
            }
            case 70761: {
                htmlid = z.a(pc, 10);
                break;
            }
            case 70998: {
                htmlid = z.e(pc);
                break;
            }
            case 71002: {
                htmlid = z.c(pc);
                break;
            }
            case 70506: {
                htmlid = z.d(pc);
                break;
            }
            case 71005: {
                htmlid = z.f(pc);
                break;
            }
            case 71009: {
                if (pc.ev() >= 13) break;
                htmlid = "jpe0071";
                break;
            }
            case 71011: {
                if (pc.ev() >= 13) break;
                htmlid = "jpe0061";
                break;
            }
            case 71014: {
                if (pc.ev() >= 13) break;
                htmlid = "en0241";
                break;
            }
            case 71015: {
                if (pc.ev() < 13) {
                    htmlid = "en0261";
                    break;
                }
                if (pc.ev() < 13 || pc.ev() >= 25) break;
                htmlid = "en0262";
                break;
            }
            case 71031: {
                if (pc.ev() >= 25) break;
                htmlid = "en0081";
                break;
            }
            case 71032: {
                if (pc.A()) {
                    htmlid = "en0091e";
                    break;
                }
                if (pc.C()) {
                    htmlid = "en0091d";
                    break;
                }
                if (pc.z()) {
                    htmlid = "en0091k";
                    break;
                }
                if (pc.B()) {
                    htmlid = "en0091w";
                    break;
                }
                if (!pc.x()) break;
                htmlid = "en0091p";
                break;
            }
            case 71034: {
                if (!pc.j().f(41227)) break;
                if (pc.A()) {
                    htmlid = "en0201e";
                    break;
                }
                if (pc.C()) {
                    htmlid = "en0201d";
                    break;
                }
                if (pc.z()) {
                    htmlid = "en0201k";
                    break;
                }
                if (pc.B()) {
                    htmlid = "en0201w";
                    break;
                }
                if (!pc.x()) break;
                htmlid = "en0201p";
                break;
            }
            case 71033: {
                if (!pc.j().f(41228)) break;
                if (pc.A()) {
                    htmlid = "en0211e";
                    break;
                }
                if (pc.C()) {
                    htmlid = "en0211d";
                    break;
                }
                if (pc.z()) {
                    htmlid = "en0211k";
                    break;
                }
                if (pc.B()) {
                    htmlid = "en0211w";
                    break;
                }
                if (!pc.x()) break;
                htmlid = "en0211p";
                break;
            }
            case 71026: {
                if (pc.ev() < 10) {
                    htmlid = "en0113";
                    break;
                }
                if (pc.ev() >= 10 && pc.ev() < 25) {
                    htmlid = "en0111";
                    break;
                }
                if (pc.ev() <= 25) break;
                htmlid = "en0112";
                break;
            }
            case 71027: {
                if (pc.ev() < 10) {
                    htmlid = "en0283";
                    break;
                }
                if (pc.ev() >= 10 && pc.ev() < 25) {
                    htmlid = "en0281";
                    break;
                }
                if (pc.ev() <= 25) break;
                htmlid = "en0282";
                break;
            }
            case 71021: {
                if (pc.ev() < 12 || pc.ev() >= 25) break;
                htmlid = "en0191";
                break;
            }
            case 71022: {
                if (pc.ev() < 12) {
                    htmlid = "jpe0155";
                    break;
                }
                if (pc.ev() < 12 || pc.ev() >= 25 || !pc.j().f(41230) && !pc.j().f(41231) && !pc.j().f(41232) && !pc.j().f(41233) && !pc.j().f(41235) && !pc.j().f(41238) && !pc.j().f(41239) && !pc.j().f(41240)) break;
                htmlid = "jpe0158";
                break;
            }
            case 71023: {
                if (pc.ev() < 12) {
                    htmlid = "jpe0145";
                    break;
                }
                if (pc.ev() < 12 || pc.ev() >= 25) break;
                if (pc.j().f(41233) || pc.j().f(41234)) {
                    htmlid = "jpe0143";
                    break;
                }
                if (pc.j().f(41238) || pc.j().f(41239) || pc.j().f(41240)) {
                    htmlid = "jpe0147";
                    break;
                }
                if (!pc.j().f(41235) && !pc.j().f(41236) && !pc.j().f(41237)) break;
                htmlid = "jpe0144";
                break;
            }
            case 71020: {
                if (pc.ev() < 12) {
                    htmlid = "jpe0125";
                    break;
                }
                if (pc.ev() < 12 || pc.ev() >= 25) break;
                if (pc.j().f(41231)) {
                    htmlid = "jpe0123";
                    break;
                }
                if (!pc.j().f(41232) && !pc.j().f(41233) && !pc.j().f(41234) && !pc.j().f(41235) && !pc.j().f(41238) && !pc.j().f(41239) && !pc.j().f(41240)) break;
                htmlid = "jpe0126";
                break;
            }
            case 71019: {
                if (pc.ev() < 12) {
                    htmlid = "jpe0114";
                    break;
                }
                if (pc.ev() < 12 || pc.ev() >= 25) break;
                if (pc.j().f(41239)) {
                    htmlid = "jpe0113";
                    break;
                }
                htmlid = "jpe0111";
                break;
            }
            case 71018: {
                if (pc.ev() < 12) {
                    htmlid = "jpe0133";
                    break;
                }
                if (pc.ev() < 12 || pc.ev() >= 25) break;
                if (pc.j().f(41240)) {
                    htmlid = "jpe0132";
                    break;
                }
                htmlid = "jpe0131";
                break;
            }
            case 71025: {
                if (pc.ev() < 10 || pc.ev() >= 25) break;
                if (pc.j().f(41226)) {
                    htmlid = "jpe0084";
                    break;
                }
                if (pc.j().f(41225)) {
                    htmlid = "jpe0083";
                    break;
                }
                if (!pc.j().f(40653) && !pc.j().f(40613)) break;
                htmlid = "jpe0081";
                break;
            }
            case 70512: {
                if (pc.ev() < 25) break;
                htmlid = "jpe0102";
                break;
            }
            case 70514: {
                if (pc.ev() < 25) break;
                htmlid = "jpe0092";
                break;
            }
            case 71038: {
                if (!pc.j().f(41060)) break;
                if (pc.j().f(41090) || pc.j().f(41091) || pc.j().f(41092)) {
                    htmlid = "orcfnoname7";
                    break;
                }
                htmlid = "orcfnoname8";
                break;
            }
            case 71040: {
                if (!pc.j().f(41060)) break;
                if (pc.j().f(41065)) {
                    if (pc.j().f(41086) || pc.j().f(41087) || pc.j().f(41088) || pc.j().f(41089)) {
                        htmlid = "orcfnoa6";
                        break;
                    }
                    htmlid = "orcfnoa5";
                    break;
                }
                htmlid = "orcfnoa2";
                break;
            }
            case 71041: {
                if (!pc.j().f(41060)) break;
                if (pc.j().f(41064)) {
                    if (pc.j().f(41081) || pc.j().f(41082) || pc.j().f(41083) || pc.j().f(41084) || pc.j().f(41085)) {
                        htmlid = "orcfhuwoomo2";
                        break;
                    }
                    htmlid = "orcfhuwoomo8";
                    break;
                }
                htmlid = "orcfhuwoomo1";
                break;
            }
            case 71042: {
                if (!pc.j().f(41060)) break;
                if (pc.j().f(41062)) {
                    if (pc.j().f(41071) || pc.j().f(41072) || pc.j().f(41073) || pc.j().f(41074) || pc.j().f(41075)) {
                        htmlid = "orcfbakumo2";
                        break;
                    }
                    htmlid = "orcfbakumo8";
                    break;
                }
                htmlid = "orcfbakumo1";
                break;
            }
            case 71043: {
                if (!pc.j().f(41060)) break;
                if (pc.j().f(41063)) {
                    if (pc.j().f(41076) || pc.j().f(41077) || pc.j().f(41078) || pc.j().f(41079) || pc.j().f(41080)) {
                        htmlid = "orcfbuka2";
                        break;
                    }
                    htmlid = "orcfbuka8";
                    break;
                }
                htmlid = "orcfbuka1";
                break;
            }
            case 71044: {
                if (!pc.j().f(41060)) break;
                if (pc.j().f(41061)) {
                    if (pc.j().f(41066) || pc.j().f(41067) || pc.j().f(41068) || pc.j().f(41069) || pc.j().f(41070)) {
                        htmlid = "orcfkame2";
                        break;
                    }
                    htmlid = "orcfkame8";
                    break;
                }
                htmlid = "orcfkame1";
                break;
            }
            case 81371: {
                if (!pc.j().f(41701)) break;
                htmlid = "j_html00";
                break;
            }
            case 81372: {
                if (pc.bb().a(42) == 1) {
                    htmlid = "id1";
                    break;
                }
                if (pc.bb().a(43) != 1) break;
                htmlid = "id0";
                break;
            }
            case 80079: {
                if (pc.bb().a(35) == 255 && !pc.j().f(41312)) {
                    htmlid = "keplisha6";
                    break;
                }
                if (pc.j().f(41314)) {
                    htmlid = "keplisha3";
                    break;
                }
                if (pc.j().f(41313)) {
                    htmlid = "keplisha2";
                    break;
                }
                if (!pc.j().f(41312)) break;
                htmlid = "keplisha4";
                break;
            }
            case 80102: {
                if (!pc.j().f(41329)) break;
                htmlid = "fillis3";
                break;
            }
            case 71167: {
                if (pc.fe() != 3887) break;
                htmlid = "frim1";
                break;
            }
            case 71141: {
                if (pc.fe() != 3887) break;
                htmlid = "moumthree1";
                break;
            }
            case 71142: {
                if (pc.fe() != 3887) break;
                htmlid = "moumtwo1";
                break;
            }
            case 71145: {
                if (pc.fe() != 3887) break;
                htmlid = "moumone1";
                break;
            }
            case 71198: {
                if (pc.bb().a(71198) == 1) {
                    htmlid = "tion4";
                    break;
                }
                if (pc.bb().a(71198) == 2) {
                    htmlid = "tion5";
                    break;
                }
                if (pc.bb().a(71198) == 3) {
                    htmlid = "tion6";
                    break;
                }
                if (pc.bb().a(71198) == 4) {
                    htmlid = "tion7";
                    break;
                }
                if (pc.bb().a(71198) == 5) {
                    htmlid = "tion5";
                    break;
                }
                if (!pc.j().g(21059, 1)) break;
                htmlid = "tion19";
                break;
            }
            case 71199: {
                if (pc.bb().a(71199) == 1) {
                    htmlid = "jeron3";
                    break;
                }
                if (!pc.j().g(21059, 1) && pc.bb().a(71199) != 255) break;
                htmlid = "jeron7";
                break;
            }
            case 80076: {
                if (pc.j().f(41058)) {
                    htmlid = "voyager8";
                    break;
                }
                if (pc.j().f(49082) || pc.j().f(49083)) {
                    if (pc.j().f(41038) || pc.j().f(41039) || pc.j().f(41039) || pc.j().f(41039) || pc.j().f(41039) || pc.j().f(41039) || pc.j().f(41039) || pc.j().f(41039) || pc.j().f(41039) || pc.j().f(41039)) {
                        htmlid = "voyager9";
                        break;
                    }
                    htmlid = "voyager7";
                    break;
                }
                if (!pc.j().f(49082) && !pc.j().f(49083) && !pc.j().f(49084) && !pc.j().f(49085) && !pc.j().f(49086) && !pc.j().f(49087) && !pc.j().f(49088) && !pc.j().f(49089) && !pc.j().f(49090) && !pc.j().f(49091)) break;
                htmlid = "voyager7";
                break;
            }
            case 80048: {
                if (pc.ev() <= 44) {
                    htmlid = "entgate3";
                    break;
                }
                if (pc.ev() >= 45 && pc.ev() <= 51) {
                    htmlid = "entgate2";
                    break;
                }
                htmlid = "entgate";
                break;
            }
            case 71168: {
                if (!pc.j().f(41028)) break;
                htmlid = "dantes1";
                break;
            }
            case 80067: {
                if (pc.bb().a(36) == 255) {
                    htmlid = "minicod10";
                    break;
                }
                if (pc.Q() >= 1) {
                    htmlid = "minicod07";
                    break;
                }
                if (pc.bb().a(36) == 1 && pc.fe() == 6034) {
                    htmlid = "minicod03";
                    break;
                }
                if (pc.bb().a(36) == 1 && pc.fe() != 6034) {
                    htmlid = "minicod05";
                    break;
                }
                if (pc.bb().a(37) == 255 || pc.j().f(41121) || pc.j().f(41122)) {
                    htmlid = "minicod01";
                    break;
                }
                if (pc.j().f(41130) && pc.j().f(41131)) {
                    htmlid = "minicod06";
                    break;
                }
                if (!pc.j().f(41130)) break;
                htmlid = "minicod02";
                break;
            }
            case 81202: {
                if (pc.bb().a(37) == 255) {
                    htmlid = "minitos10";
                    break;
                }
                if (pc.Q() <= -1) {
                    htmlid = "minitos07";
                    break;
                }
                if (pc.bb().a(37) == 1 && pc.fe() == 6035) {
                    htmlid = "minitos03";
                    break;
                }
                if (pc.bb().a(37) == 1 && pc.fe() != 6035) {
                    htmlid = "minitos05";
                    break;
                }
                if (pc.bb().a(36) == 255 || pc.j().f(41130) || pc.j().f(41131)) {
                    htmlid = "minitos01";
                    break;
                }
                if (pc.j().f(41121) && pc.j().f(41122)) {
                    htmlid = "minitos06";
                    break;
                }
                if (!pc.j().f(41121)) break;
                htmlid = "minitos02";
                break;
            }
            case 81208: {
                if (pc.j().f(41129) || pc.j().f(41138)) {
                    htmlid = "minibrob04";
                    break;
                }
                if ((!pc.j().f(41126) || !pc.j().f(41127) || !pc.j().f(41128)) && (!pc.j().f(41135) || !pc.j().f(41136) || !pc.j().f(41137))) break;
                htmlid = "minibrob02";
                break;
            }
            case 71256: {
                if (!pc.A()) {
                    htmlid = "robinhood2";
                    break;
                }
                if (pc.bb().a(40) == 255) {
                    htmlid = "robinhood12";
                    break;
                }
                if (pc.bb().a(40) == 8) {
                    if (pc.j().g(40491, 30) && pc.j().g(40495, 40) && pc.j().g(100, 1) && pc.j().g(40509, 12) && pc.j().g(40052, 1) && pc.j().g(40053, 1) && pc.j().g(40054, 1) && pc.j().g(40055, 1) && pc.j().g(41347, 1) && pc.j().g(41350, 1)) {
                        htmlid = "robinhood11";
                        break;
                    }
                    if (pc.j().g(40491, 30) && pc.j().g(40495, 40) && pc.j().g(100, 1) && pc.j().g(40509, 12)) {
                        htmlid = "robinhood16";
                        break;
                    }
                    if (pc.j().g(40491, 30) && pc.j().g(40495, 40) && pc.j().g(100, 1) && pc.j().g(40509, 12)) break;
                    htmlid = "robinhood17";
                    break;
                }
                if (pc.bb().a(40) == 7) {
                    if (pc.j().g(41352, 4) && pc.j().g(40618, 30) && pc.j().g(40643, 30) && pc.j().g(40645, 30) && pc.j().g(40651, 30) && pc.j().g(40676, 30) && pc.j().g(40514, 20) && pc.j().g(41351, 1) && pc.j().g(41346, 1)) {
                        htmlid = "robinhood9";
                        break;
                    }
                    if (pc.j().g(41351, 1) && pc.j().g(41352, 4)) {
                        htmlid = "robinhood14";
                        break;
                    }
                    if (pc.j().g(41351, 1) && !pc.j().g(41352, 4)) {
                        htmlid = "robinhood15";
                        break;
                    }
                    if (pc.j().f(41351)) {
                        htmlid = "robinhood9";
                        break;
                    }
                    htmlid = "robinhood18";
                    break;
                }
                if (pc.bb().a(40) == 2 || pc.bb().a(40) == 3 || pc.bb().a(40) == 4 || pc.bb().a(40) == 5 || pc.bb().a(40) == 6) {
                    htmlid = "robinhood13";
                    break;
                }
                if (pc.bb().a(40) == 1) {
                    htmlid = "robinhood8";
                    break;
                }
                htmlid = "robinhood1";
                break;
            }
            case 71257: {
                if (!pc.A()) {
                    htmlid = "zybril16";
                    break;
                }
                if (pc.bb().a(40) >= 7) {
                    htmlid = "zybril19";
                    break;
                }
                if (pc.j().f(41349) && pc.bb().a(40) == 7) {
                    htmlid = "zybril19";
                    break;
                }
                if (pc.j().f(41349) && pc.bb().a(40) == 6) {
                    htmlid = "zybril18";
                    break;
                }
                if (pc.bb().a(40) == 6 && !pc.j().f(41354)) {
                    htmlid = "zybril7";
                    break;
                }
                if (pc.bb().a(40) == 6 && pc.j().f(41354)) {
                    htmlid = "zybril17";
                    break;
                }
                if (pc.j().f(41353) && pc.j().g(40514, 10) && pc.bb().a(40) == 5) {
                    htmlid = "zybril8";
                    break;
                }
                if (pc.bb().a(40) == 5) {
                    htmlid = "zybril13";
                    break;
                }
                if (pc.bb().a(40) == 4 && pc.j().g(40048, 10) && pc.j().g(40049, 10) && pc.j().g(40050, 10) && pc.j().g(40051, 10)) {
                    htmlid = "zybril7";
                    break;
                }
                if (pc.bb().a(40) == 4) {
                    htmlid = "zybril12";
                    break;
                }
                if (pc.bb().a(40) == 3) {
                    htmlid = "zybril3";
                    break;
                }
                if (!pc.A() || pc.bb().a(40) != 2 && pc.bb().a(40) != 1) break;
                htmlid = "zybril1";
                break;
            }
            case 71258: {
                if (pc.fa() <= -501) {
                    htmlid = "marba1";
                    break;
                }
                if (pc.x() || pc.C() || pc.z() || pc.B() || pc.D() || pc.E()) {
                    htmlid = "marba2";
                    break;
                }
                if (pc.j().f(40665) && (pc.j().f(40693) || pc.j().f(40694) || pc.j().f(40695) || pc.j().f(40697) || pc.j().f(40698) || pc.j().f(40699))) {
                    htmlid = "marba8";
                    break;
                }
                if (pc.j().f(40665)) {
                    htmlid = "marba17";
                    break;
                }
                if (pc.j().f(40664)) {
                    htmlid = "marba19";
                    break;
                }
                if (!pc.j().f(40637)) break;
                htmlid = "marba18";
                break;
            }
            case 71259: {
                if (pc.fa() <= -501) {
                    htmlid = "aras12";
                    break;
                }
                if (pc.x() || pc.C() || pc.z() || pc.B() || pc.D() || pc.E()) {
                    htmlid = "aras11";
                    break;
                }
                if (pc.j().f(40665) && (pc.j().f(40679) || pc.j().f(40680) || pc.j().f(40681) || pc.j().f(40682) || pc.j().f(40683) || pc.j().f(40684))) {
                    htmlid = "aras3";
                    break;
                }
                if (pc.j().f(40665)) {
                    htmlid = "aras8";
                    break;
                }
                if (pc.j().f(40679) || pc.j().f(40680) || pc.j().f(40681) || pc.j().f(40682) || pc.j().f(40683) || pc.j().f(40684) || pc.j().f(40693) || pc.j().f(40694) || pc.j().f(40695) || pc.j().f(40697) || pc.j().f(40698) || pc.j().f(40699)) {
                    htmlid = "aras3";
                    break;
                }
                if (pc.j().f(40664)) {
                    htmlid = "aras6";
                    break;
                }
                if (pc.j().f(40637)) {
                    htmlid = "aras1";
                    break;
                }
                htmlid = "aras7";
                break;
            }
            case 70838: {
                if (pc.x() || pc.z() || pc.B() || pc.D() || pc.E()) {
                    htmlid = "nerupam1";
                    break;
                }
                if (pc.C() && pc.fa() <= -1) {
                    htmlid = "nerupaM2";
                    break;
                }
                if (pc.C()) {
                    htmlid = "nerupace1";
                    break;
                }
                if (!pc.A()) break;
                htmlid = "nerupae1";
                break;
            }
            case 80094: {
                if (pc.E()) {
                    htmlid = "altar1";
                    break;
                }
                if (pc.E()) break;
                htmlid = "altar2";
                break;
            }
            case 80099: {
                if (pc.bb().a(41) == 1) {
                    if (pc.j().g(41325, 1)) {
                        htmlid = "rarson8";
                        break;
                    }
                    htmlid = "rarson10";
                    break;
                }
                if (pc.bb().a(41) == 2) {
                    if (pc.j().g(41317, 1) && pc.j().g(41315, 1)) {
                        htmlid = "rarson13";
                        break;
                    }
                    htmlid = "rarson19";
                    break;
                }
                if (pc.bb().a(41) == 3) {
                    htmlid = "rarson14";
                    break;
                }
                if (pc.bb().a(41) == 4) {
                    if (!pc.j().g(41326, 1)) {
                        htmlid = "rarson18";
                        break;
                    }
                    if (pc.j().g(41326, 1)) {
                        htmlid = "rarson11";
                        break;
                    }
                    htmlid = "rarson17";
                    break;
                }
                if (pc.bb().a(41) < 5) break;
                htmlid = "rarson1";
                break;
            }
            case 80101: {
                if (pc.bb().a(41) == 4) {
                    if (pc.j().g(41315, 1) && pc.j().g(40494, 30) && pc.j().g(41317, 1)) {
                        htmlid = "kuen4";
                        break;
                    }
                    if (pc.j().g(41316, 1)) {
                        htmlid = "kuen1";
                        break;
                    }
                    if (pc.j().f(41316)) break;
                    pc.bb().a(41, 1);
                    break;
                }
                if (pc.bb().a(41) == 2 && pc.j().g(41317, 1)) {
                    htmlid = "kuen3";
                    break;
                }
                htmlid = "kuen1";
                break;
            }
            case 70035: 
            case 70041: 
            case 70042: {
                if (as.a.a().b() == 0) {
                    htmlid = "maeno5";
                    break;
                }
                if (as.a.a().b() == 1) {
                    htmlid = "maeno1";
                    break;
                }
                if (as.a.a().b() == 2) {
                    htmlid = "maeno3";
                    break;
                }
                htmlid = "maeno5";
                break;
            }
            case 190138: {
                if (pc.ev() < 2) {
                    pc.x(w.b(pc.ev()));
                    ah.a(pc, 42099, 5);
                    htmlid = "newtutor1";
                    break;
                }
                if (pc.ev() >= 2 && pc.ev() <= 51) {
                    htmlid = "newtutor2";
                    break;
                }
                htmlid = "newtutor3";
                break;
            }
            case 81255: 
            case 190371: {
                if (pc.ev() > 51) break;
                z.d(pc, 1);
                break;
            }
            case 81256: {
                if (pc.ev() < 3) {
                    pc.x(w.b(pc.ev()));
                }
                htmlid = pc.ev() < 5 ? "newadmin1" : (pc.ev() >= 5 && pc.ev() <= 51 ? "newadmin2" : "newadmin3");
                if (pc.ev() >= 13) break;
                z.d(pc, 2);
                z.d(pc, 3);
                break;
            }
            case 81260: {
                int townid = pc.bF();
                if (pc.ev() <= 9 || townid <= 0 || townid >= 11) break;
                htmlid = "artisan1";
                break;
            }
            case 81261: {
                if (!pc.j().g(49031, 1)) break;
                if (pc.j().g(21081, 1)) {
                    htmlid = "gemout1";
                    break;
                }
                if (pc.j().g(21082, 1)) {
                    htmlid = "gemout2";
                    break;
                }
                if (pc.j().g(21083, 1)) {
                    htmlid = "gemout3";
                    break;
                }
                if (pc.j().g(21084, 1)) {
                    htmlid = "gemout4";
                    break;
                }
                if (pc.j().g(21085, 1)) {
                    htmlid = "gemout5";
                    break;
                }
                if (pc.j().g(21086, 1)) {
                    htmlid = "gemout6";
                    break;
                }
                if (pc.j().g(21087, 1)) {
                    htmlid = "gemout7";
                    break;
                }
                if (pc.j().g(21088, 1)) {
                    htmlid = "gemout8";
                    break;
                }
                htmlid = "gemout17";
                break;
            }
            case 71181: {
                if (pc.aJ() != 0) break;
                htmlid = "my2";
                break;
            }
            case 71182: {
                if (pc.aJ() != 1) break;
                htmlid = "sm2";
                break;
            }
            case 81322: {
                if (pc.ev() >= 52) break;
                htmlid = "adjutant2";
                if (!pc.z()) break;
                htmlid = "adjutant4";
                break;
            }
            case 71092: 
            case 71093: {
                if (!pc.z() || pc.bb().a(3) != 4) break;
                htmlid = "searcherk1";
                break;
            }
            case 71094: {
                if (!pc.C() || pc.bb().a(4) != 2) break;
                htmlid = "endiaq1";
                break;
            }
            case 70957: 
            case 81209: {
                if (pc.bb().a(38) == 1) break;
                htmlid = "roi1";
                break;
            }
            case 81350: {
                if (!pc.A() || pc.bb().a(4) != 3) break;
                htmlid = "dspy2";
                break;
            }
            case 60028: {
                if (pc.A()) break;
                htmlid = "elCE1";
                break;
            }
            case 70549: 
            case 70985: {
                if (!z.c(pc, 1)) break;
                htmlid = "gateokeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70656: {
                if (!z.c(pc, 1)) break;
                htmlid = "gatekeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70600: 
            case 70986: {
                if (!z.c(pc, 2)) break;
                htmlid = "orckeeper";
                break;
            }
            case 70687: 
            case 70987: {
                if (!z.c(pc, 3)) break;
                htmlid = "gateokeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70778: {
                if (!z.c(pc, 3)) break;
                htmlid = "gatekeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70800: 
            case 70988: 
            case 70989: 
            case 70990: 
            case 70991: {
                if (!z.c(pc, 4)) break;
                htmlid = "gateokeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70817: {
                if (!z.c(pc, 4)) break;
                htmlid = "gatekeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70862: 
            case 70992: {
                if (!z.c(pc, 5)) break;
                htmlid = "gateokeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70863: {
                if (!z.c(pc, 5)) break;
                htmlid = "gatekeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70993: 
            case 70994: {
                if (!z.c(pc, 6)) break;
                htmlid = "gateokeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70995: {
                if (!z.c(pc, 6)) break;
                htmlid = "gatekeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 70996: {
                if (!z.c(pc, 7)) break;
                htmlid = "gatekeeper";
                htmldata = new String[]{pc.et()};
                break;
            }
            case 60514: {
                clan_name = String.valueOf(clan_name) + 1;
                l1castle = g.a().a(1);
                clan = q.a().a(l1castle.g());
                if (clan != null) {
                    clan_name = clan.f();
                    pri_name = clan.l();
                }
                htmlid = "ktguard6";
                htmldata = new String[]{npc.et(), clan_name, pri_name};
                break;
            }
            case 60560: {
                clan_name = String.valueOf(clan_name) + 2;
                l1castle = g.a().a(2);
                clan = q.a().a(l1castle.g());
                if (clan != null) {
                    clan_name = clan.f();
                    pri_name = clan.l();
                }
                htmlid = "orcguard6";
                htmldata = new String[]{npc.et(), clan_name, pri_name};
                break;
            }
            case 60552: {
                clan_name = String.valueOf(clan_name) + 3;
                l1castle = g.a().a(3);
                clan = q.a().a(l1castle.g());
                if (clan != null) {
                    clan_name = clan.f();
                    pri_name = clan.l();
                }
                htmlid = "wdguard6";
                htmldata = new String[]{npc.et(), clan_name, pri_name};
                break;
            }
            case 60524: 
            case 60525: 
            case 60529: {
                clan_name = String.valueOf(clan_name) + 4;
                l1castle = g.a().a(4);
                clan = q.a().a(l1castle.g());
                if (clan != null) {
                    clan_name = clan.f();
                    pri_name = clan.l();
                }
                htmlid = "grguard6";
                htmldata = new String[]{npc.et(), clan_name, pri_name};
                break;
            }
            case 70857: {
                clan_name = String.valueOf(clan_name) + 5;
                l1castle = g.a().a(5);
                clan = q.a().a(l1castle.g());
                if (clan != null) {
                    clan_name = clan.f();
                    pri_name = clan.l();
                }
                htmlid = "heguard6";
                htmldata = new String[]{npc.et(), clan_name, pri_name};
                break;
            }
            case 60530: 
            case 60531: {
                clan_name = String.valueOf(clan_name) + 6;
                l1castle = g.a().a(6);
                clan = q.a().a(l1castle.g());
                if (clan != null) {
                    clan_name = clan.f();
                    pri_name = clan.l();
                }
                htmlid = "dcguard6";
                htmldata = new String[]{npc.et(), clan_name, pri_name};
                break;
            }
            case 60533: 
            case 60534: {
                clan_name = String.valueOf(clan_name) + 7;
                l1castle = g.a().a(7);
                clan = q.a().a(l1castle.g());
                if (clan != null) {
                    clan_name = clan.f();
                    pri_name = clan.l();
                }
                htmlid = "adguard6";
                htmldata = new String[]{npc.et(), clan_name, pri_name};
                break;
            }
            case 81156: {
                clan_name = String.valueOf(clan_name) + "_8";
                l1castle = g.a().a(8);
                clan = q.a().a(l1castle.g());
                if (clan != null) {
                    clan_name = clan.f();
                    pri_name = clan.l();
                }
                htmlid = "ktguard6";
                htmldata = new String[]{npc.et(), clan_name, pri_name};
                break;
            }
            case 50082: {
                if (pc.ev() < 13) break;
                if (pc.A()) {
                    htmlid = "en0222e";
                    break;
                }
                if (pc.C()) {
                    htmlid = "en0222d";
                    break;
                }
                htmlid = "en0222";
                break;
            }
            case 50001: {
                if (pc.A()) {
                    htmlid = "barnia3";
                    break;
                }
                if (!pc.z() && !pc.x()) break;
                htmlid = "barnia2";
                break;
            }
            case 81258: {
                if (!pc.E()) break;
                htmlid = "asha1";
                break;
            }
            case 81259: {
                if (!pc.D()) break;
                htmlid = "feaena1";
                break;
            }
            case 71013: {
                if (!pc.C()) break;
                htmlid = pc.ev() < 14 ? "karen1" : "karen4";
            }
        }
        if (htmlid != null) {
            pc.a(new be(objid, htmlid, htmldata));
            return;
        }
        if (npc.E().length() > 0 && pc.fa() < -1000) {
            pc.a(new be(objid, npc.E()));
        } else if (npc.D().length() > 0) {
            pc.a(new be(objid, npc.D()));
        }
    }

    private static String a(u pc, int town_id) {
        String htmlid = pc.bF() == town_id && bh.a().a(pc, town_id) ? "secretary1" : "secretary2";
        return htmlid;
    }

    private static String b(u pc, int town_id) {
        String htmlid = pc.bF() == town_id ? "hometown" : "othertown";
        return htmlid;
    }

    private static boolean c(u pc, int castle_id) {
        i clan;
        return pc.aF() != 0 && (clan = q.a().a(pc.aF())) != null && clan.m() == castle_id;
    }

    private static boolean a(u pc) {
        i clan;
        return pc.x() && (clan = q.a().a(pc.aF())) != null && pc.fr() == clan.k();
    }

    private static int b(u pc) {
        int rulerCount = 0;
        int necessarySealCount = 10;
        if (pc.j().f(40917)) {
            ++rulerCount;
        }
        if (pc.j().f(40920)) {
            ++rulerCount;
        }
        if (pc.j().f(40918)) {
            ++rulerCount;
        }
        if (pc.j().f(40919)) {
            ++rulerCount;
        }
        if (rulerCount == 0) {
            necessarySealCount = 10;
        } else if (rulerCount == 1) {
            necessarySealCount = 100;
        } else if (rulerCount == 2) {
            necessarySealCount = 200;
        } else if (rulerCount == 3) {
            necessarySealCount = 500;
        }
        return necessarySealCount;
    }

    private static void a(u pc, int attr, int sealCount) {
        int rulerId = 0;
        int protectionId = 0;
        int sealId = 0;
        if (attr == 1) {
            rulerId = 40917;
            protectionId = 40909;
            sealId = 40913;
        } else if (attr == 2) {
            rulerId = 40919;
            protectionId = 40911;
            sealId = 40915;
        } else if (attr == 4) {
            rulerId = 40918;
            protectionId = 40910;
            sealId = 40914;
        } else if (attr == 8) {
            rulerId = 40920;
            protectionId = 40912;
            sealId = 40916;
        }
        pc.j().b(protectionId, 1);
        pc.j().b(sealId, sealCount);
        ah.a(pc, rulerId, 1);
    }

    private static String c(u pc) {
        String htmlid = "";
        htmlid = pc.ev() < 13 ? "jpe0161" : "jpe0162";
        return htmlid;
    }

    private static String d(u pc) {
        String htmlid = "";
        if (pc.x() || pc.B()) {
            htmlid = "en0101";
        } else if (pc.z() || pc.A() || pc.C()) {
            htmlid = "en0102";
        }
        return htmlid;
    }

    private static String e(u pc) {
        String htmlid = "";
        htmlid = pc.ev() < 3 ? "en0301" : (pc.ev() >= 3 && pc.ev() < 7 ? "en0302" : (pc.ev() >= 7 && pc.ev() < 9 ? "en0303" : (pc.ev() >= 9 && pc.ev() < 12 ? "en0304" : (pc.ev() >= 12 && pc.ev() < 13 ? "en0305" : (pc.ev() >= 13 && pc.ev() < 25 ? "en0306" : "en0307")))));
        return htmlid;
    }

    private static String f(u pc) {
        String htmlid = "";
        if (pc.ev() < 25) {
            htmlid = "jpe0041";
            if (pc.j().f(41209) || pc.j().f(41210) || pc.j().f(41211) || pc.j().f(41212)) {
                htmlid = "jpe0043";
            }
            if (pc.j().f(41213)) {
                htmlid = "jpe0044";
            }
        } else {
            htmlid = "jpe0045";
        }
        return htmlid;
    }

    private static void d(u pc, int helpNo) {
        switch (helpNo) {
            case 1: {
                pc.a(new ds(183));
                pc.a(new ea(pc.fr(), 1, 1600));
                pc.b(new ea(pc.fr(), 1, 0));
                pc.a(new ee(pc.fr(), 755));
                pc.b(new ee(pc.fr(), 755));
                pc.cu(1);
                pc.j(1001, 1600000);
                pc.a(pc.ew());
                pc.i_(pc.ex());
                pc.a(new ds(77));
                pc.a(new ee(pc.fr(), 830));
                break;
            }
            case 2: {
                pc.a(new ds(183));
                pc.a(new ea(pc.fr(), 1, 1600));
                pc.b(new ea(pc.fr(), 1, 0));
                pc.a(new ee(pc.fr(), 755));
                pc.b(new ee(pc.fr(), 755));
                pc.cu(1);
                pc.j(1001, 1600000);
                break;
            }
            case 3: {
                new j().a((f)pc, 0);
                break;
            }
        }
    }
}

