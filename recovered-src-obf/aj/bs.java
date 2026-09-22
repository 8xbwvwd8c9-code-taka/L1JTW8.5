/*
 * Decompiled with CFR 0.152.
 */
package aj;

import a.g;
import a.s;
import aj.cv;
import aj.z;
import an.a;
import an.b;
import an.c;
import an.d;
import an.e;
import an.f;
import ao.ah;
import ao.al;
import ao.ba;
import ao.j;
import ao.m;
import ao.w;
import ap.q;
import ap.t;
import ap.u;
import aq.aa;
import aq.am;
import aq.aq;
import aq.b;
import aq.f;
import aq.k;
import aq.n;
import be.cm;
import be.dc;
import be.ds;
import be.dt;
import be.ee;
import be.ei;
import bi.c;
import bi.d;
import bi.i;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;

public class bs
extends cv {
    private static final Logger a = Logger.getLogger(bs.class.getName());

    public bs(byte[] decrypt, bj.d client) {
        block139: {
            super(decrypt);
            int type = this.d();
            u pc = client.f();
            try {
                if (type == 115) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    c.c msg = c.c.a(data);
                    String text = new String(msg.r().e(), l1j.server.a.k);
                    String account = text.split("-")[0];
                    String name = text.split("-")[1];
                    client.a(new dc(116));
                    new bc.d(client, account, name).a(3000L);
                    break block139;
                }
                if (type == 117) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    client.d();
                    System.out.println("[Shift Server]:\u5207\u65b7\u539f\u672c\u7684\u9023\u7dda");
                    break block139;
                }
                if (type == 143) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.a msg = a.a.a(data);
                    if (msg.p() == 12) {
                        if (pc.j().b(40308, 1000)) {
                            am.a(pc, 32630, 32776, 4, 5, true);
                        } else {
                            pc.a(new ds(189));
                        }
                    }
                    break block139;
                }
                if (type == 135) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.a msg = a.a.a(data);
                    ba.a().a(pc, msg.p());
                    break block139;
                }
                if (type == 514) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    e.i msg = e.i.a(data);
                    int chatCount = msg.p();
                    int chatType = msg.r();
                    String chatText = new String(msg.t().e(), l1j.server.a.k);
                    String targetName = new String(msg.x().e(), l1j.server.a.k);
                    aq.g.a(pc, chatType, chatText, chatCount, targetName);
                    break block139;
                }
                if (type == 524) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.a msg = a.a.a(data);
                    int questID = msg.p();
                    bh.s qn = pc.dS().get(questID);
                    if (qn != null && !qn.w()) {
                        if (msg.q()) {
                            int idx = msg.r();
                            if (qn.i() == null || qn.j() == null || qn.k() == null || idx < 0 || idx >= qn.i().length || idx >= qn.j().length || idx >= qn.k().length) {
                                pc.a(new ds(79));
                                return;
                            }
                        }
                        int i2 = 0;
                        while (i2 < qn.f().length) {
                            ah.a(pc, qn.f()[i2], qn.g()[i2], qn.h()[i2]);
                            ++i2;
                        }
                        if (msg.q()) {
                            int idx = msg.r();
                            ah.a(pc, qn.i()[idx], qn.j()[idx], qn.k()[idx]);
                        }
                        if (qn.l() > 0) {
                            double exppenalty = w.d(pc.ev());
                            pc.x((int)((double)qn.l() * exppenalty));
                        }
                        qn.a(true);
                        pc.a(new dc(525, questID));
                        if (qn.r().length > 0 && qn.o()) {
                            i = 0;
                            while (i < qn.r().length) {
                                pc.j().b(qn.r()[i], qn.t()[i], qn.s()[i], 3);
                                ++i;
                            }
                        }
                    }
                    break block139;
                }
                if (type == 527) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.a msg = a.a.a(data);
                    int questID = msg.p();
                    bh.s qn = pc.dS().get(questID);
                    if (qn != null && qn.e().length > 0) {
                        am.a(pc, qn.e()[0], qn.e()[1], qn.e()[2], 5, true);
                    }
                    break block139;
                }
                if (type == 543) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.c msg = a.c.a(data);
                    int type1 = msg.p();
                    int type2 = msg.r();
                    int size = msg.t();
                    n exList = pc.cd();
                    if (type1 == 0) {
                        pc.a(new cm(17, exList.a(), 0));
                        pc.a(new cm(17, exList.a(), 1));
                        break block139;
                    }
                    if (type1 == 1) {
                        int i3 = 0;
                        while (i3 < size) {
                            String name = new String(msg.a(i3).e(), l1j.server.a.k);
                            if (exList.b()) {
                                pc.a(new ds(472));
                                break block139;
                            }
                            if (!exList.c(name)) {
                                exList.a(name);
                                pc.a(new cm(18, 0, name));
                                pc.a(new cm(18, 1, name));
                            }
                            ++i3;
                        }
                        break block139;
                    }
                    if (type1 == 2) {
                        int i4 = 0;
                        while (i4 < size) {
                            String name = new String(msg.a(i4).e(), l1j.server.a.k);
                            exList.b(name);
                            pc.a(new cm(19, 0, name));
                            pc.a(new cm(19, 1, name));
                            ++i4;
                        }
                    }
                    break block139;
                }
                if (type == 563) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.a msg = a.a.a(data);
                    int number = msg.p();
                    d.i msg15 = am.d.a().a(number);
                    int achievementIDX = number;
                    if (achievementIDX >= 1700) {
                        achievementIDX -= 30;
                    }
                    if (msg15 == null || pc.dR() == null || achievementIDX <= 0 || achievementIDX > pc.dR().length) {
                        pc.a(new ds(79));
                        return;
                    }
                    for (g bs2 : msg15.o()) {
                        a.a msg1 = a.a.a(bs2);
                        int id = msg1.r();
                        int count = msg1.t();
                        if (id == 0) {
                            double exppenalty = w.d(pc.ev());
                            pc.x((int)((double)count * exppenalty));
                            continue;
                        }
                        if (id == 16667) {
                            if (!pc.bB(4092)) {
                                pc.F(5);
                            }
                            pc.j(4092, 1800000);
                            pc.a(new dc(4092, 1800, 8, 6841, 0, 1426, 0, 0, 1));
                            pc.a(new ee(pc.fr(), 14102));
                            pc.b(new ee(pc.fr(), 14102));
                            continue;
                        }
                        if (id == 15815) {
                            int[] skills;
                            int[] nArray = skills = new int[]{42, 79, 158, 159, 160, 175, 206, 211, 216, 115, 148};
                            int n2 = skills.length;
                            int n3 = 0;
                            while (n3 < n2) {
                                int skillid = nArray[n3];
                                bf.a executor = bi.g.a(skillid);
                                executor.a((f)pc, 0);
                                ++n3;
                            }
                            continue;
                        }
                        int itemid = 0;
                        if (id == 7) {
                            itemid = 40308;
                        } else if (id == 14092) {
                            itemid = 640514;
                        } else if (id == 16764) {
                            itemid = 640819;
                        }
                        ah.a(pc, itemid, count);
                    }
                    pc.dR()[achievementIDX - 1] = 1;
                    pc.a(new dc(564, 0, number));
                    m.a().a(pc);
                    break block139;
                }
                if (type == 565) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.c msg = a.c.a(data);
                    int[] loc = am.d.a().b(msg.p());
                    if (loc == null) {
                        pc.a(new ei("\u932f\u8aa4\u7684\u50b3\u9001\u7de8\u865f:0x" + bi.g.a(msg.p(), 4)));
                        return;
                    }
                    if (!pc.fq().i() || pc.bB(230) || pc.eX()) {
                        pc.a(new ds(276));
                        return;
                    }
                    if (!pc.j().b(140100, 1)) {
                        pc.a(new ds(4692, "$5096"));
                        return;
                    }
                    if (loc[2] >= 100 && loc[2] <= 111) {
                        return;
                    }
                    am.a(pc, loc[0], loc[1], loc[2], 5, true);
                    break block139;
                }
                if (type == 569) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.a msg = a.a.a(data);
                    int itemobjid = msg.p();
                    q selectItem = pc.j().e(itemobjid);
                    if (selectItem == null || selectItem.f() && !selectItem.a().aN()) {
                        pc.a(new ds(79));
                        return;
                    }
                    if (selectItem.F() >= 128) {
                        pc.a(new ds(2124));
                        return;
                    }
                    selectItem.f(selectItem.F() + 128);
                    pc.j().j(selectItem);
                    break block139;
                }
                if (type == 801) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.a msg = a.a.a(data);
                    if (pc.bB(26004)) {
                        pc.a(new dc(800, pc.dV()));
                        return;
                    }
                    pc.j(26004, 2000);
                    if (msg.o()) {
                        if (msg.p() == 0) {
                            pc.dT().clear();
                            for (q item : pc.j().d()) {
                                if (!item.D()) continue;
                                pc.dT().add(item.fr());
                            }
                        } else if (msg.p() == 1) {
                            pc.dU().clear();
                            for (q item : pc.j().d()) {
                                if (!item.D()) continue;
                                pc.dU().add(item.fr());
                            }
                        }
                        pc.bu(msg.p());
                        j.a().a(pc);
                    }
                    if (msg.q()) {
                        pc.j().j();
                        ArrayList<Integer> equipList = msg.r() == 0 ? pc.dT() : pc.dU();
                        for (int id : equipList) {
                            pc.j().k(id);
                        }
                        pc.bu(msg.r());
                        pc.a(new dc(800, msg.r()));
                    }
                    break block139;
                }
                if (type == 811) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    a.a msg = a.a.a(data);
                    int line = msg.p();
                    int choice = msg.r();
                    int[][] weeklyData = pc.dY();
                    long weeklyIndex = (long)line * 3L;
                    if (weeklyData == null || weeklyIndex < 0L || weeklyIndex >= weeklyData.length || weeklyData[(int)weeklyIndex] == null || weeklyData[(int)weeklyIndex].length <= 3 || weeklyData[(int)weeklyIndex][3] == 5) {
                        pc.a(new ds(79));
                        return;
                    }
                    int exp = 0;
                    if (choice == 1) {
                        exp = 721306;
                        ah.a(pc, 640941, 1);
                    } else if (choice == 2 && pc.j().b(640938, 1)) {
                        exp = 7213060;
                        ah.a(pc, 640941, 5);
                    } else if (choice == 3 && pc.j().b(640939, 1)) {
                        exp = 16229385;
                        ah.a(pc, 640769, 1);
                    }
                    if (exp > 0) {
                        double exppenalty = w.d(pc.ev());
                        pc.x((int)((double)exp * exppenalty));
                    }
                    weeklyData[(int)weeklyIndex][3] = 5;
                    pc.a(new dc(814, line, 5));
                } else if (type == 820) {
                    client.a(new dt());
                } else if (type != 1002) {
                    if (type == 54) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        byte[] true_data = this.a(l1j.server.a.aR);
                        if (pc.aK().j() || Arrays.equals(data, true_data)) {
                            pc.a(new dc(55, 3));
                        } else {
                            System.out.println("\u66f4\u65b0\u9053\u5177\u6e05\u55ae\u9a57\u8b49(" + pc.eu() + ")");
                            pc.a(new dc(55, 0));
                            ArrayList<k> list = ao.s.a().b();
                            for (k craft : list) {
                                pc.a(new dc(55, craft, 1));
                            }
                            pc.a(new dc(55, 2));
                            pc.aK().a(true);
                        }
                    } else if (type == 56) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        int objid = 0;
                        a.a msg = a.a.a(data);
                        objid = msg.p();
                        aa obj = aq.a().a(objid);
                        if (obj instanceof t) {
                            t npc = (t)obj;
                            String[] keys = npc.F();
                            if (keys.length == 0) {
                                return;
                            }
                            pc.a(new dc(57, keys));
                        }
                    } else if (type == 92) {
                        pc.a(new dc(93, 33));
                    } else if (type == 58) {
                        int enchant;
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        e.a msg = e.a.a(data);
                        k craft = ao.s.a().a(msg.r());
                        if (craft == null) {
                            a.log(Level.WARNING, "Reject unknown craft id=" + msg.r() + " player=" + pc.et());
                            return;
                        }
                        q addchanceitem = craft.f();
                        int counts = msg.t();
                        ArrayList<q> trueMaterialList = new ArrayList<q>();
                        block13: for (g bs3 : msg.u()) {
                            c.e msg7 = c.e.a(bs3);
                            int systemid = msg7.r();
                            enchant = msg7.v();
                            if (addchanceitem != null && addchanceitem.m() == systemid) continue;
                            block14: for (q item : craft.g().values()) {
                                if (item.m() == systemid && item.G() == enchant) {
                                    trueMaterialList.add(item);
                                    continue block13;
                                }
                                for (q exchange : craft.h().get(item.N())) {
                                    if (exchange.m() != systemid || exchange.G() != enchant) continue;
                                    trueMaterialList.add(exchange);
                                    continue block14;
                                }
                            }
                        }
                        if (trueMaterialList.size() != craft.g().size()) {
                            a.log(Level.SEVERE, "Item Craft has MaterialList Error with [" + pc.et() + "] craft id=" + craft.a());
                            return;
                        }
                        for (q item : trueMaterialList) {
                            if (!pc.j().b(item.N(), item.G(), item.E() * counts, item.F())) {
                                a.log(Level.SEVERE, "Item Craft has Consume Error with [" + pc.et() + "] craft id=" + craft.a());
                                return;
                            }
                            if (item.N() != 40308) continue;
                            int castleId = i.a(4) + 1;
                            ao.g.a().a(castleId, item.E() * counts);
                        }
                        int chance = craft.l();
                        if (addchanceitem != null) {
                            for (g bs4 : msg.u()) {
                                e.a msg2 = e.a.a(bs4);
                                if (msg2.r() != addchanceitem.m()) continue;
                                if (!pc.j().b(addchanceitem.N(), msg2.t() * counts)) break;
                                chance += msg2.t() * counts;
                                break;
                            }
                        }
                        if (i.a(100) < chance) {
                            q craft_item = craft.b();
                            int itemid = craft_item.N();
                            int count = craft_item.E() * counts;
                            enchant = craft_item.G();
                            int bless = craft_item.F();
                            if (craft.c() > 0 && i.a(100) < craft.c()) {
                                bless = 0;
                                pc.a(new ee(pc.fr(), 2047));
                                pc.b(new ee(pc.fr(), 2047));
                                if (craft.c() < 10) {
                                    aq.a().a(new ds(3599, "$227 " + craft_item.a().j(), enchant));
                                }
                            }
                            if (craft.l() <= 5) {
                                pc.a(new ee(pc.fr(), 2047));
                                pc.b(new ee(pc.fr(), 2047));
                                aq.a().a(new ds(3599, craft_item.a().j(), enchant));
                            }
                            ah.a(pc, itemid, count, enchant, bless, true);
                            pc.a(new dc(59, craft, craft_item, 0));
                        } else {
                            q failitem = craft.e();
                            if (failitem != null) {
                                ah.a(pc, failitem.N(), failitem.E() * counts);
                            }
                            pc.a(new dc(59, craft, craft.b(), 1));
                        }
                    } else if (type == 317) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        a.a msg = a.a.a(data);
                        int action = msg.p();
                        if (action == 1) {
                            pc.a(new dc(318, ao.g.a().b()));
                        }
                    } else if (type == 319) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        a.a msg = a.a.a(data);
                        int id2 = msg.r();
                        pc.a(new dc(320, pc.fr(), id2));
                        pc.b(new dc(320, pc.fr(), id2));
                    } else if (type == 332) {
                        if (pc.aF() > 0) {
                            pc.a(new dc(333, pc));
                        }
                    } else if (type == 326) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        d.g msg = d.g.a(data);
                        int joinTypeOpen = msg.p();
                        int joinType = msg.r();
                        byte[] code = msg.t().e();
                        aq.i clan = ao.q.a().a(pc.aF());
                        if (clan != null) {
                            clan.j(joinTypeOpen);
                            clan.k(joinType);
                            clan.a(code);
                        }
                        pc.a(new dc(327, pc));
                    } else if (type == 338) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        a.a msg = a.a.a(data);
                        int objid = msg.p();
                        int markid = msg.r();
                        if (pc.q()) {
                            for (u member : pc.aL().c()) {
                                member.a(new dc(339, objid, markid));
                            }
                        }
                    } else if (type == 100) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        b.c msg = b.c.a(data);
                        ah.a(pc, 640106, msg.r());
                        for (int key : msg.q()) {
                            al.a().a(client.a(), key);
                        }
                        pc.a(new dc(al.a().c(client.a()), 0));
                        pc.a(new ds(3728, msg.r()));
                    } else if (type == 122) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        byte[] true_data = this.a(l1j.server.a.aS);
                        pc.a(new dc(128));
                        if (Arrays.equals(data, true_data)) {
                            pc.a(new dc(123, 3));
                        } else {
                            System.out.println("\u66f4\u65b0\u9b54\u6cd5\u5a03\u5a03\u5408\u6210\u6e05\u55ae\u9a57\u8b49(" + pc.eu() + ")");
                            pc.a(new dc(123, 0));
                            b.a().a(pc);
                            b.a().b(pc);
                            b.a().c(pc);
                            pc.a(new dc(123, 2));
                        }
                    } else if (type == 124) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        ArrayList<q> materialList = new ArrayList<q>();
                        f.g msg = f.g.a(data);
                        int level = msg.p();
                        for (g bs5 : msg.q()) {
                            a.a msg1 = a.a.a(bs5);
                            int index = msg1.p();
                            int systemid = msg1.r();
                            int objid = msg1.t();
                            q finditem = pc.j().e(objid);
                            if (finditem == null) {
                                a.log(Level.SEVERE, "\u9b54\u6cd5\u5a03\u5a03-\u5408\u6210:itemobjid= " + objid + " is Null");
                                return;
                            }
                            materialList.add(finditem);
                        }
                        b.a().a(pc, level, materialList);
                    } else if (type == 460) {
                        pc.a(new dc(461, pc));
                    } else if (type == 484) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        a.a msg = a.a.a(data);
                        int level = msg.p();
                        int classType = msg.r();
                        int mode = msg.t();
                        int uk5 = msg.x();
                        int str = msg.z();
                        int intel = msg.B();
                        int wis = msg.D();
                        int dex = msg.F();
                        int con = msg.H();
                        int cha = msg.L();
                        if (client.h() == null) {
                            client.b(new u());
                        }
                        u dummy = client.h();
                        dummy.ad(classType);
                        dummy.i(z.a[classType]);
                        dummy.o(str - dummy.bf());
                        dummy.q(dex - dummy.bh());
                        dummy.p(con - dummy.bg());
                        dummy.t(wis - dummy.bk());
                        dummy.s(intel - dummy.bj());
                        dummy.r(cha - dummy.bi());
                        if (str > 0) {
                            int calcDmg = d.a(dummy.bf(), dummy.ez());
                            int calcHit = d.b(dummy.bf(), dummy.ez());
                            int calcCritical = d.c(dummy.bf(), dummy.ez());
                            if (mode != 16 || uk5 == 16) {
                                client.a(new dc(mode * 2, "str", calcDmg, calcHit, calcCritical, (int)dummy.K()));
                            }
                        }
                        if (intel > 0) {
                            int calcMagicDmg = d.g(dummy.bj(), dummy.eD());
                            int calcMagicHit = d.h(dummy.bj(), dummy.eD());
                            int calcMagicCritical = d.i(dummy.bj(), dummy.eD());
                            int calcMagicBouns = d.c(dummy.eD());
                            int calcMagicDecrese = d.d(dummy.eD());
                            client.a(new dc(mode * 2, "int", calcMagicDmg, calcMagicHit, calcMagicCritical, calcMagicBouns, calcMagicDecrese));
                        }
                        if (wis > 0) {
                            int mpup = dummy.aC().j(dummy.eE());
                            int rnd = dummy.aC().k(dummy.eE());
                            int calcMpr = d.m(dummy.bk(), dummy.eE());
                            int calcPotionMpr = d.n(dummy.bk(), dummy.eE());
                            int calcMr = d.e(dummy.eE());
                            int calcMp = c.c(dummy) - c.b(dummy);
                            client.a(new dc(mode * 2, "wis", calcMpr, calcPotionMpr, calcMr, mpup, mpup + rnd, calcMp));
                        }
                        if (dex > 0) {
                            int calcAc = d.a(dummy.eB());
                            int calcEr = d.b(dummy.eB());
                            int calcBowDmg = d.d(dummy.bh(), dummy.eB());
                            int calcBowHit = d.e(dummy.bh(), dummy.eB());
                            int calcBowCritical = d.f(dummy.bh(), dummy.eB());
                            client.a(new dc(mode * 2, "dex", calcBowDmg, calcBowHit, calcBowCritical, calcAc, calcEr));
                        }
                        if (con > 0) {
                            int calcHpup = dummy.aC().e() + d.j(dummy.aC().a()[2], dummy.eA());
                            int calcHpr = d.k(dummy.bg(), dummy.eA());
                            int calcPotionHpr = d.l(dummy.bg(), dummy.eA());
                            if (mode != 16 || uk5 == 1) {
                                client.a(new dc(mode * 2, "con", calcHpr, calcPotionHpr, (int)dummy.K(), calcHpup, 0));
                            }
                        }
                        if (cha > 0) {
                            client.a(new dc(mode * 2, "cha", 0, 0, 0, 0));
                        }
                    } else if (type != 1002 && type == 802) {
                        pc.a(new dc(803, pc));
                    }
                }
            }
            catch (s e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            catch (UnsupportedEncodingException e3) {
                a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
            }
        }
    }

    private byte[] a(String tsxt) {
        String[] ss = tsxt.trim().split(" ");
        byte[] data = new byte[ss.length];
        int i2 = 0;
        while (i2 < ss.length) {
            data[i2] = this.b(ss[i2])[0];
            ++i2;
        }
        return data;
    }

    private byte[] b(String hexString) {
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
        return "C_ProtoBuffers";
    }
}

