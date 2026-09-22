/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.ah;
import ao.al;
import ao.au;
import ao.aw;
import ao.bb;
import ao.bg;
import ao.bk;
import ap.q;
import ap.t;
import ap.v;
import aq.aa;
import aq.ae;
import aq.ai;
import aq.am;
import aq.an;
import aq.aq;
import aq.e;
import aq.f;
import aq.i;
import aq.j;
import aq.o;
import aq.r;
import aq.s;
import aq.u;
import aq.w;
import au.g;
import aw.c;
import az.a;
import be.ab;
import be.ak;
import be.av;
import be.be;
import be.bf;
import be.bm;
import be.bs;
import be.ca;
import be.ci;
import be.ck;
import be.cl;
import be.cm;
import be.cn;
import be.dc;
import be.dn;
import be.do;
import be.ds;
import be.ee;
import be.ef;
import be.ei;
import bf.bj;
import bf.br;
import bf.bx;
import bh.l;
import bh.n;
import bi.h;
import bj.d;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class az
extends cv {
    private static final Logger a = Logger.getLogger(az.class.getName());
    private static final String b = "[C] C_ItemUSe";

    public az(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        ap.u pc = client.f();
        if (pc == null || pc.bN() || pc.eX()) {
            return;
        }
        int itemObjid = this.b();
        q l1iteminstance = pc.j().e(itemObjid);
        if (l1iteminstance == null || pc.aR() || pc.ea() <= 0) {
            return;
        }
        if (!pc.fq().p()) {
            pc.a(new ds(563));
            return;
        }
        if ((l1iteminstance.a().aP() == 6 || l1iteminstance.a().aP() >= 23 && l1iteminstance.a().aP() <= 27) && pc.bB(71)) {
            pc.a(new ds(698));
            return;
        }
        int select_itemid = 0;
        String s2 = "";
        int h2 = 0;
        int bmapid = 0;
        int btele = 0;
        int telNo = 0;
        int blanksc_skillid = 0;
        int spellsc_objid = 0;
        int spellsc_x = 0;
        int spellsc_y = 0;
        int cookStatus = 0;
        int cookNo = 0;
        int fishX = 0;
        int fishY = 0;
        int itemId = l1iteminstance.N();
        int use_type = l1iteminstance.a().U();
        int count = 0;
        int select_charid = 0;
        switch (use_type) {
            case 59: 
            case 60: {
                s2 = this.g();
                h2 = this.d();
                break;
            }
            case 56: {
                telNo = this.c();
                break;
            }
            case 68: {
                select_charid = this.b();
                break;
            }
            case 0: {
                if (ao.ak.a().b().containsKey(itemId)) {
                    pc.a(new ds(4963));
                } else {
                    pc.a(new ds(74, l1iteminstance.s()));
                }
                return;
            }
            case 16: 
            case 61: {
                s2 = this.g();
                break;
            }
            case 7: 
            case 14: 
            case 26: 
            case 27: 
            case 46: 
            case 55: {
                select_itemid = this.b();
                break;
            }
            case 6: 
            case 29: {
                bmapid = this.d();
                int x2 = this.d();
                int y2 = this.d();
                bh.c book = pc.a(x2, y2);
                if (book == null) break;
                btele = book.a();
                break;
            }
            case 28: {
                blanksc_skillid = this.c() + 1;
                break;
            }
            case 42: 
            case 50: {
                fishX = this.d();
                fishY = this.d();
                break;
            }
            case 8: 
            case 30: {
                try {
                    spellsc_objid = this.b();
                }
                catch (ArrayIndexOutOfBoundsException a2) {
                    spellsc_objid = 0;
                }
                break;
            }
            case 5: 
            case 17: {
                spellsc_objid = this.b();
                spellsc_x = this.d();
                spellsc_y = this.d();
                break;
            }
            case 52: {
                cookStatus = this.c();
                cookNo = this.c();
                break;
            }
            case 62: 
            case 65: {
                count = this.b();
                break;
            }
        }
        boolean isDelayEffect = false;
        if (l1iteminstance.f()) {
            int delay_id = l1iteminstance.a().aJ();
            if (delay_id != 0 && pc.bF(delay_id)) {
                pc.a(new ds(3898, "" + l1iteminstance.a().aK() / 1000));
                if (use_type == 9 || use_type == 6 || use_type == 29) {
                    pc.a(new cn(7, false));
                }
                return;
            }
            int delayEffect = l1iteminstance.a().aL();
            if (delayEffect > 0) {
                Calendar now;
                int diff;
                isDelayEffect = true;
                Timestamp lastUsed = l1iteminstance.J();
                if (lastUsed != null && (diff = (int)(((now = Calendar.getInstance()).getTimeInMillis() - lastUsed.getTime()) / 1000L)) <= delayEffect) {
                    pc.a(new ds(3898, "" + (delayEffect - diff)));
                    if (use_type == 9 || use_type == 6 || use_type == 29) {
                        pc.a(new cn(7, false));
                    }
                    return;
                }
            }
        }
        q selectItem = pc.j().e(select_itemid);
        int item_minlvl = l1iteminstance.a().o();
        int item_maxlvl = l1iteminstance.a().p();
        if (item_minlvl != 0 && item_minlvl > pc.ev()) {
            pc.a(new ds(318, String.valueOf(String.valueOf(item_minlvl))));
            if (use_type == 9 || use_type == 6 || use_type == 29) {
                pc.a(new cn(7, false));
            }
            return;
        }
        if (item_maxlvl != 0 && item_maxlvl < pc.ev()) {
            pc.a(new cm(12, item_maxlvl));
            if (use_type == 9 || use_type == 6 || use_type == 29) {
                pc.a(new cn(7, false));
            }
            return;
        }
        if (l1iteminstance.g() || l1iteminstance.h()) {
            if (l1iteminstance.a().y() && pc.x() || l1iteminstance.a().z() && pc.z() || l1iteminstance.a().A() && pc.A() || l1iteminstance.a().B() && pc.B() || l1iteminstance.a().C() && pc.C() || l1iteminstance.a().D() && pc.D() || l1iteminstance.a().E() && pc.E() || l1iteminstance.a().F() && pc.F()) {
                if (l1iteminstance.g()) {
                    this.c(pc, l1iteminstance);
                } else {
                    this.b(pc, l1iteminstance);
                }
            } else {
                pc.a(new ds(264));
            }
        } else if (l1iteminstance.f()) {
            int[] loc;
            int probability;
            boolean isPoly;
            int rnd;
            aa obj3;
            int gfxid;
            boolean isEquipped;
            int i2;
            boolean isSuccess;
            aa target;
            bf.a executor;
            if (l1iteminstance.a().aP() == 0 || l1iteminstance.a().aP() == 15) {
                pc.j().k(l1iteminstance);
                pc.a(new ds(452, l1iteminstance.s()));
            } else if (l1iteminstance.a().aP() == 16) {
                if (itemId == 40576 && !pc.A() || itemId == 40577 && !pc.B() || itemId == 40578 && !pc.z()) {
                    pc.a(new ds(264));
                    return;
                }
                if (bk.a().a(itemId, pc)) {
                    if (l1iteminstance.a().aL() > 0) {
                        if (l1iteminstance.d()) {
                            if (l1iteminstance.E() > 1) {
                                isDelayEffect = true;
                            }
                            pc.j().c(l1iteminstance.fr(), 1);
                        } else {
                            isDelayEffect = true;
                        }
                    } else {
                        pc.j().c(l1iteminstance.fr(), 1);
                    }
                }
            } else if (l1iteminstance.a().aP() == 2) {
                if (l1iteminstance.M() <= 0 && itemId != 40004) {
                    return;
                }
                if (l1iteminstance.T()) {
                    l1iteminstance.c(false);
                    pc.fg();
                } else {
                    l1iteminstance.c(true);
                    pc.fg();
                }
                pc.a(new bm(l1iteminstance));
            } else if (l1iteminstance.a().aP() == 5) {
                int soundid = l1iteminstance.a().V();
                pc.a(new ee(pc.fr(), soundid));
                pc.b(new ee(pc.fr(), soundid));
                pc.j().b(l1iteminstance, 1);
            } else if (l1iteminstance.a().aP() == 7) {
                int foodvolume = l1iteminstance.a().V() / 10;
                pc.c_(Math.min(pc.fj() + foodvolume, 225));
                pc.a(new cm(11, pc.fj()));
                pc.a(new ds(76, l1iteminstance.b()));
                if (itemId == 40057) {
                    pc.j(1012, 0);
                    pc.a(new ds(152));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (l1iteminstance.a().aP() == 17 || l1iteminstance.a().aP() == 22) {
                c.a(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 18) {
                if (itemId == 41401) {
                    aw.b.a(pc, spellsc_objid, l1iteminstance);
                } else {
                    aw.b.a(pc, l1iteminstance);
                }
            } else if (l1iteminstance.a().aP() >= 23 && l1iteminstance.a().aP() <= 25) {
                aw.d.a(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 26) {
                aw.d.c(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 27) {
                aw.d.b(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 28) {
                int skillid = l1iteminstance.a().V();
                bf.a executor2 = bi.g.a(skillid);
                if (executor2.a((f)pc, spellsc_objid, skillid)) {
                    executor2.a((f)pc, spellsc_objid, spellsc_x, spellsc_y, null);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(281));
                }
            } else if (l1iteminstance.a().aP() == 29) {
                j.a(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 30) {
                this.f(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 31) {
                if (itemId >= 41429 && itemId <= 41432 || itemId >= 640150 && itemId <= 640153) {
                    aw.a.e(pc, l1iteminstance, selectItem);
                } else if (itemId >= 640157 && itemId <= 640160) {
                    aw.a.d(pc, l1iteminstance, selectItem);
                } else {
                    aw.a.b(pc, l1iteminstance, selectItem);
                }
            } else if (l1iteminstance.a().aP() == 32) {
                aw.a.c(pc, l1iteminstance, selectItem);
            } else if (l1iteminstance.a().U() == 8) {
                executor = l1iteminstance.F() == 0 ? new bx() : new bj();
                executor.a((f)pc, spellsc_objid, 0, 0, "res");
                pc.j().b(l1iteminstance, 1);
            } else if (l1iteminstance.a().U() == 16 || l1iteminstance.a().U() == 61) {
                if (ae.a(pc, s2, l1iteminstance.a().V())) {
                    if (itemId == 640804) {
                        return;
                    }
                    pc.j().b(l1iteminstance, 1);
                }
            } else if (l1iteminstance.a().U() == 28) {
                this.a(pc, l1iteminstance, blanksc_skillid);
            } else if (l1iteminstance.a().U() == 6 || l1iteminstance.a().U() == 29) {
                executor = itemId == 40086 ? new br() : new bf.g();
                executor.a((f)pc, btele, 0, 0, null);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640105 || itemId == 640106) {
                int consumeCount = count * (itemId == 640105 ? 1 : 3);
                if (pc.j().b(itemId, consumeCount)) {
                    HashMap<Integer, q> list = new HashMap<Integer, q>();
                    int nextIndex = al.a().b(client.a());
                    int i3 = 0;
                    while (i3 < count) {
                        list.put(nextIndex + i3, al.a().a(pc.et()));
                        ++i3;
                    }
                    pc.a(new dc(list, 1));
                    al.a().a(client.a(), list);
                }
            } else if (itemId == 640382) {
                target = aq.a().a(spellsc_objid);
                if (target instanceof t) {
                    t npc;
                    block767: {
                        npc = (t)target;
                        Connection con = null;
                        PreparedStatement pstm = null;
                        try {
                            try {
                                con = l1j.server.b.a().b();
                                pstm = con.prepareStatement("DELETE FROM spawnlist_npc WHERE npc_templateid=? AND locx=? AND locy=? AND mapid=?");
                                if (target instanceof ap.s) {
                                    pstm = con.prepareStatement("DELETE FROM spawnlist WHERE npc_templateid=? AND locx=? AND locy=? AND mapid=?");
                                }
                                pstm.setInt(1, npc.z());
                                pstm.setInt(2, npc.fs());
                                pstm.setInt(3, npc.ft());
                                pstm.setInt(4, npc.fp());
                                pstm.execute();
                            }
                            catch (SQLException e2) {
                                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                                bi.j.a(pstm);
                                bi.j.a(con);
                                break block767;
                            }
                        }
                        catch (Throwable throwable) {
                            bi.j.a(pstm);
                            bi.j.a(con);
                            throw throwable;
                        }
                        bi.j.a(pstm);
                        bi.j.a(con);
                    }
                    npc.a(0, 0, 0);
                    pc.a(new ei("npcid:" + npc.z() + " = " + npc.U_().c()));
                }
            } else if (itemId == 640104) {
                target = aq.a().a(spellsc_objid);
                if (target instanceof t) {
                    t npc;
                    block769: {
                        npc = (t)target;
                        Connection con = null;
                        PreparedStatement pstm = null;
                        try {
                            try {
                                con = l1j.server.b.a().b();
                                pstm = con.prepareStatement("UPDATE spawnlist_npc SET locx=?,locy=?,heading=? WHERE npc_templateid=? AND locx=? AND locy=?");
                                pstm.setInt(1, pc.fs());
                                pstm.setInt(2, pc.ft());
                                pstm.setInt(3, pc.fb());
                                pstm.setInt(4, npc.z());
                                pstm.setInt(5, npc.fs());
                                pstm.setInt(6, npc.ft());
                                pstm.execute();
                            }
                            catch (Exception e3) {
                                a.log(Level.SEVERE, e3.getLocalizedMessage(), e3);
                                bi.j.a(pstm);
                                bi.j.a(con);
                                break block769;
                            }
                        }
                        catch (Throwable throwable) {
                            bi.j.a(pstm);
                            bi.j.a(con);
                            throw throwable;
                        }
                        bi.j.a(pstm);
                        bi.j.a(con);
                    }
                    npc.a(pc.fs(), pc.ft(), pc.fb());
                    pc.a(new ei("npcid:" + npc.z() + " = " + npc.U_().c()));
                }
            } else if (itemId == 640234) {
                if (selectItem == null) {
                    pc.a(new ds(156));
                    return;
                }
                if (selectItem.N() != 40314 && selectItem.N() != 40316) {
                    pc.a(new ds(1164));
                    return;
                }
                String name = null;
                for (t petNpc : pc.ek().values()) {
                    if (!(petNpc instanceof v)) continue;
                    v pet = (v)petNpc;
                    if (selectItem.fr() != pet.k()) continue;
                    name = pet.et();
                    pc.am(pet.fr());
                    break;
                }
                if (name == null) {
                    pc.a(new ds(1301));
                    return;
                }
                pc.a(new ca(1322, String.valueOf(name) + " "));
            } else if (itemId == 640835) {
                aq.a().a(new ab(pc.et(), s2));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640233) {
                aq.a().a(new ab(pc.et(), s2, h2));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640334) {
                if (pc.cJ() >= 100) {
                    pc.a(new ds(1622));
                    return;
                }
                pc.ao();
                pc.a(new ds(1624, "" + pc.cJ()));
                pc.I();
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640144) {
                int castleId = e.a(pc);
                if (castleId > 0) {
                    pc.a(new ds(3274));
                    return;
                }
                int spawnid = l1iteminstance.a().V();
                bg.a(spawnid, pc, 0, 300000L);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640626 && itemId <= 640637) {
                as.d.a().a(l1iteminstance, pc);
            } else if (itemId == 640561) {
                if (selectItem == null) {
                    pc.a(new ds(79));
                    return;
                }
                if (selectItem.N() >= 21340 && selectItem.N() <= 21344) {
                    pc.j().f(selectItem);
                    ah.a(pc, 640562, 1);
                } else if (selectItem.N() >= 21345 && selectItem.N() <= 21349) {
                    pc.j().f(selectItem);
                    ah.a(pc, 640563, 1);
                } else {
                    pc.a(new ds(79));
                    return;
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640841) {
                if (selectItem == null) {
                    pc.a(new ds(156));
                    return;
                }
                if (selectItem.N() < 21509 || selectItem.N() > 21511) {
                    pc.a(new ds(79));
                    return;
                }
                if (selectItem.G() >= 9) {
                    pc.a(new ds(79));
                    return;
                }
                aw.a.a(pc, selectItem, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640677) {
                if (selectItem == null) {
                    pc.a(new ds(156));
                    return;
                }
                if (selectItem.N() < 393 || selectItem.N() > 400) {
                    pc.a(new ds(79));
                    return;
                }
                if (selectItem.G() >= 15) {
                    pc.a(new ds(79));
                    return;
                }
                aw.a.a(pc, selectItem, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640370 && itemId <= 640372) {
                if (selectItem == null) {
                    pc.a(new ds(156));
                    return;
                }
                if (!selectItem.g() || selectItem.G() != l1iteminstance.a().V()) {
                    pc.a(new ds(79));
                    return;
                }
                aw.a.b(pc, l1iteminstance, selectItem);
            } else if (itemId == 640833) {
                if (selectItem == null || selectItem.N() < 21495 || selectItem.N() > 21499) {
                    pc.a(new ds(79));
                    return;
                }
                int success_rate = 35;
                boolean bl2 = isSuccess = bi.i.a(100) + 1 < 35;
                if (isSuccess) {
                    pc.a(new ds(161, selectItem.s(), "$252", "$247"));
                    bh.j next = ah.a().a(selectItem.N() + 1);
                    boolean isEquipped2 = selectItem.D();
                    pc.j().a(selectItem, false);
                    selectItem.a(next);
                    pc.j().a(selectItem, isEquipped2);
                    pc.j().j(selectItem);
                } else {
                    pc.a(new ds(164, selectItem.s(), "$245"));
                    pc.j().f(selectItem);
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640946) {
                if (selectItem == null || !selectItem.j()) {
                    pc.a(new ds(79));
                    return;
                }
                int success_rate = 35;
                boolean bl3 = isSuccess = bi.i.a(100) + 1 < 35;
                if (isSuccess) {
                    aw.a.a(pc, selectItem, 1);
                } else {
                    pc.a(new ds(164, selectItem.s(), "$245"));
                    pc.j().f(selectItem);
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640440) {
                boolean isSuccess2;
                int[] highest;
                if (selectItem == null || selectItem.N() < 21261 || selectItem.N() > 21300) {
                    pc.a(new ds(79));
                    return;
                }
                int[] e3 = highest = new int[]{21270, 21280, 21290, 21300};
                int isEquipped2 = highest.length;
                int next = 0;
                while (next < isEquipped2) {
                    i2 = e3[next];
                    if (selectItem.N() == i2) {
                        pc.a(new ds(1453));
                        return;
                    }
                    ++next;
                }
                int success_rate = 35;
                boolean bl4 = isSuccess2 = bi.i.a(100) + 1 < 35;
                if (isSuccess2) {
                    pc.a(new ds(161, selectItem.s(), "$252", "$247"));
                    bh.j next2 = ah.a().a(selectItem.N() + 1);
                    boolean isEquipped3 = selectItem.D();
                    pc.j().a(selectItem, false);
                    selectItem.a(next2);
                    pc.j().a(selectItem, isEquipped3);
                    pc.j().j(selectItem);
                } else {
                    pc.a(new ds(79));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640346) {
                if (selectItem == null || selectItem.N() < 21199 || selectItem.N() > 21203) {
                    pc.a(new ds(79));
                    return;
                }
                int success_rate = 60 - (selectItem.N() - 21198) * 11;
                boolean bl5 = isSuccess = bi.i.a(100) + 1 < success_rate;
                if (isSuccess) {
                    pc.a(new ds(161, selectItem.s(), "$252", "$247"));
                    bh.j next = ah.a().a(selectItem.N() + 1);
                    boolean isEquipped4 = selectItem.D();
                    pc.j().a(selectItem, false);
                    selectItem.a(next);
                    pc.j().a(selectItem, isEquipped4);
                    pc.j().j(selectItem);
                } else {
                    pc.a(new ds(160, selectItem.s(), "$252", "$248"));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 49142) {
                pc.a(new ei("\\aH\u8acb\u81f3\u53e4\u9b6f\u4e01\u6d3d\u8a62\u76f8\u95dcNPC\u3002"));
            } else if (itemId == 640439) {
                if (selectItem == null || selectItem.N() < 21261 || selectItem.N() > 21300) {
                    pc.a(new ds(79));
                    return;
                }
                Timestamp limit = new Timestamp(System.currentTimeMillis() + 86400000L);
                selectItem.b(limit);
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640386 && itemId <= 640391) {
                if (selectItem == null || selectItem.N() != 20084 && selectItem.N() != 20085 && selectItem.N() != 120085) {
                    pc.a(new ds(1453));
                    return;
                }
                if (selectItem.D()) {
                    pc.a(new ds(4357));
                    return;
                }
                int value = l1iteminstance.a().V() + (selectItem.N() == 20084 ? 6 : 0);
                pc.j().f(selectItem);
                q createitem = ah.a(pc, value, 1, selectItem.G(), selectItem.F(), selectItem.C(), 31);
                pc.a(new ds(3299));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640392 && itemId <= 640400) {
                if (selectItem == null || selectItem.N() < 21246 || selectItem.N() > 21257) {
                    pc.a(new ds(1453));
                    return;
                }
                int value = l1iteminstance.a().V();
                isEquipped = selectItem.D();
                pc.j().a(selectItem, false);
                selectItem.l(value);
                selectItem.w();
                pc.j().a(selectItem, isEquipped);
                pc.j().j(selectItem);
                pc.a(new ds(3299));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640374 && itemId <= 640379) {
                if (selectItem == null || selectItem.N() < 21152 || selectItem.N() > 21155) {
                    pc.a(new ds(1453));
                    return;
                }
                int value = l1iteminstance.a().V();
                isEquipped = selectItem.D();
                pc.j().a(selectItem, false);
                selectItem.l(value);
                selectItem.z();
                pc.j().a(selectItem, isEquipped);
                pc.j().j(selectItem);
                pc.a(new ds(3299));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640844) {
                if (selectItem == null || !selectItem.f()) {
                    pc.a(new ds(79));
                    return;
                }
                int changeid = ao.ak.a().a(selectItem.N());
                if (changeid <= 0) {
                    pc.a(new ds(79));
                    return;
                }
                if (bi.i.a(100) < 10) {
                    ah.a(pc, changeid, 1, 0, false);
                    pc.a(new ds(4964, selectItem.b()));
                } else {
                    pc.a(new ds(4965, selectItem.b()));
                }
                pc.j().b(selectItem, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640345) {
                if (selectItem == null || selectItem.N() != 21204) {
                    pc.a(new ds(3440));
                    return;
                }
                if (selectItem.aa() == 0) {
                    boolean isEquipped5 = selectItem.D();
                    pc.j().a(selectItem, false);
                    selectItem.o(8192);
                    selectItem.A();
                    pc.j().a(selectItem, isEquipped5);
                    pc.j().j(selectItem);
                }
                pc.a(new ds(3439));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640348 && itemId <= 640350) {
                if (selectItem == null || selectItem.N() != 21204) {
                    pc.a(new ds(3440));
                    return;
                }
                int currentStatus = 0;
                currentStatus |= selectItem.X();
                currentStatus |= selectItem.Y();
                currentStatus |= selectItem.Z();
                int[] nArray = new int[14];
                nArray[1] = 1;
                nArray[2] = 2;
                nArray[3] = 4;
                nArray[4] = 8;
                nArray[5] = 16;
                nArray[6] = 32;
                nArray[7] = 64;
                nArray[8] = 128;
                nArray[9] = 256;
                nArray[10] = 512;
                nArray[11] = 1024;
                nArray[12] = 2048;
                nArray[13] = 4096;
                int[] bits = nArray;
                int newSuperEnchant = 0;
                while (newSuperEnchant == 0) {
                    int rnd2 = bi.i.a(bits.length);
                    if ((currentStatus & bits[rnd2]) == bits[rnd2]) continue;
                    newSuperEnchant = bits[rnd2];
                }
                boolean isEquipped6 = selectItem.D();
                pc.j().a(selectItem, false);
                if (itemId == 640348) {
                    selectItem.l(newSuperEnchant);
                } else if (itemId == 640349) {
                    selectItem.m(newSuperEnchant);
                } else if (itemId == 640350) {
                    selectItem.n(newSuperEnchant);
                }
                selectItem.A();
                pc.j().a(selectItem, isEquipped6);
                pc.a(new ds(3439));
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640337) {
                if (!pc.j().b(41246, 20)) {
                    pc.a(new ds(337, "$5240"));
                    return;
                }
                target = aq.a().a(spellsc_objid);
                if (target instanceof f) {
                    f cha = (f)target;
                    int damage = 150 + pc.ev();
                    int gfxid2 = 13987;
                    if (bi.i.a(100) < 25) {
                        damage = 200 + pc.ev() + bi.i.a(300);
                        gfxid2 = 13989;
                    }
                    if (pc.a(pc, cha, false)) {
                        damage = 0;
                    }
                    if (!pc.i(cha.fs(), cha.ft())) {
                        damage = 0;
                    }
                    w _magic = new w(pc, cha);
                    _magic.a(damage, 0);
                    if (cha instanceof ap.u) {
                        ((ap.u)cha).a(new ak(cha.fr(), 2));
                    }
                    cha.b(new ak(cha.fr(), 2));
                    pc.a(new ee(cha.fr(), gfxid2));
                    pc.b(new ee(cha.fr(), gfxid2));
                }
            } else if (itemId == 640339) {
                if (!pc.j().b(41246, 20)) {
                    pc.a(new ds(337, "$5240"));
                    return;
                }
                target = aq.a().a(spellsc_objid);
                if (target instanceof f) {
                    f cha = (f)target;
                    int damage = 250 + pc.ev();
                    int gfxid3 = 13991;
                    if (bi.i.a(100) < 25) {
                        damage = 400 + pc.ev() + bi.i.a(400);
                        gfxid3 = 13993;
                    }
                    if (pc.a(pc, cha, false)) {
                        damage = 0;
                    }
                    if (!pc.i(cha.fs(), cha.ft())) {
                        damage = 0;
                    }
                    w _magic = new w(pc, cha);
                    _magic.a(damage, 0);
                    if (cha instanceof ap.u) {
                        ((ap.u)cha).a(new ak(cha.fr(), 2));
                    }
                    cha.b(new ak(cha.fr(), 2));
                    pc.a(new ee(cha.fr(), gfxid3));
                    pc.b(new ee(cha.fr(), gfxid3));
                }
            } else if (itemId == 640338) {
                if (!pc.j().b(41246, 20)) {
                    pc.a(new ds(337, "$5240"));
                    return;
                }
                int damage = 80 + pc.ev();
                gfxid = 13995;
                if (bi.i.a(100) < 25) {
                    damage = 160 + pc.ev() + bi.i.a(300);
                    gfxid = 13997;
                }
                for (aa object : aq.a().b((aa)pc, 4)) {
                    if (!(object instanceof ap.s) || !pc.i(object.fs(), object.ft())) continue;
                    ap.s mob = (ap.s)object;
                    w _magic = new w(pc, mob);
                    _magic.a(damage, 0);
                    mob.b(new ak(mob.fr(), 2));
                }
                pc.a(new ee(pc.fr(), gfxid));
                pc.b(new ee(pc.fr(), gfxid));
            } else if (itemId == 640356) {
                if (select_charid == pc.fr()) {
                    pc.e(new Timestamp(System.currentTimeMillis() + 2592000000L));
                }
                ao.o.a().a(select_charid, 2592000000L);
                pc.a(new dc(461, pc));
                pc.a(new ee(pc.fr(), 2028));
                pc.b(new ee(pc.fr(), 2028));
                pc.a(new ds(3916));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640700) {
                obj3 = aq.a().a(spellsc_objid);
                f target2 = new f();
                if (obj3 == null) {
                    target2.cF(spellsc_objid);
                    target2.cG(spellsc_x);
                    target2.cH(spellsc_y);
                } else {
                    target2.cF(obj3.fr());
                    target2.cG(obj3.fs());
                    target2.cH(obj3.ft());
                }
                int dmg = 200 + bi.i.a(150);
                u loc2 = new u(target2.fs(), target2.ft(), pc.fp());
                for (aa object : aq.a().a(loc2, 3)) {
                    if (!(object instanceof ap.s)) continue;
                    ap.s mob = (ap.s)object;
                    w _magic = new w(pc, mob);
                    dmg = (int)w.a(pc, mob, dmg, 0);
                    _magic.a(dmg, 0);
                    mob.b(new ak(mob.fr(), 2));
                }
                pc.ct(pc.a((aa)target2));
                pc.a(new be.g(pc, target2, 17, 762, dmg, 8, 0));
                pc.b(new be.g(pc, target2, 17, 762, dmg, 8, 0));
                this.e(pc, l1iteminstance);
            } else if (itemId == 640354) {
                for (aa object : aq.a().b((aa)pc, 5)) {
                    if (!(object instanceof ap.g)) continue;
                    ap.g mob = (ap.g)object;
                    mob.b(new ak(mob.fr(), 2));
                    mob.b(pc, 250);
                }
                pc.a(new ee(pc.fr(), 1819));
                pc.b(new ee(pc.fr(), 1819));
                this.e(pc, l1iteminstance);
            } else if (itemId == 640355) {
                for (aa object : aq.a().b((aa)pc, 3934)) {
                    if (!(object instanceof ap.g)) continue;
                    ap.g mob = (ap.g)object;
                    mob.b(new ak(mob.fr(), 2));
                    mob.b(pc, 700);
                }
                pc.a(new ee(pc.fr(), 3934));
                pc.b(new ee(pc.fr(), 3934));
                this.e(pc, l1iteminstance);
            } else if (itemId == 640319 || itemId == 640320) {
                int gfxid4 = l1iteminstance.a().V();
                for (aa object : aq.a().b((aa)pc, 7)) {
                    if (!(object instanceof ap.s)) continue;
                    ap.s mob = (ap.s)object;
                    mob.b(new ak(mob.fr(), 2));
                    mob.b(pc, 100);
                }
                pc.a(new ee(pc.fr(), gfxid4));
                pc.b(new ee(pc.fr(), gfxid4));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640321 || itemId == 640322) {
                int skillid = l1iteminstance.a().V();
                s.b(pc, skillid, 120);
                pc.a(new ee(pc.fr(), 11101));
                pc.b(new ee(pc.fr(), 11101));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640709 || itemId == 640710 || itemId == 640711) {
                int skillid = l1iteminstance.a().V();
                s.b(pc, skillid, 1200);
                pc.a(new ee(pc.fr(), skillid + 10751));
                pc.b(new ee(pc.fr(), skillid + 10751));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640810 && itemId <= 640818) {
                int skillid = l1iteminstance.a().V();
                int time = 900;
                if (itemId == 640810 || itemId == 640812 || itemId == 640814) {
                    time = 300;
                }
                s.b(pc, skillid, time);
                pc.a(new ee(pc.fr(), skillid + 3865));
                pc.b(new ee(pc.fr(), skillid + 3865));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640103) {
                aw.a.a(pc, l1iteminstance, selectItem);
            } else if (itemId == 640110) {
                if (selectItem.a().aP() != 17 && selectItem.a().aP() != 22) {
                    pc.a(new ds(2477));
                    return;
                }
                if (selectItem.N() != 640478 && selectItem.N() != 640483) {
                    pc.a(new ds(3658));
                    return;
                }
                if (pc.O(selectItem.fr())) {
                    pc.a(new ds(1181));
                    return;
                }
                int[] ability = new int[]{16384, 65536, 32768, 2, 262144};
                selectItem.n(ability[bi.i.a(ability.length)]);
                pc.a(new ds(3657));
                selectItem.x();
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640109) {
                if (selectItem.a().aP() != 17 && selectItem.a().aP() != 22) {
                    pc.a(new ds(2477));
                    return;
                }
                if (selectItem.N() != 640476 && selectItem.N() != 640477 && selectItem.N() != 640481 && selectItem.N() != 640482) {
                    pc.a(new ds(3658));
                    return;
                }
                if (pc.O(selectItem.fr())) {
                    pc.a(new ds(1181));
                    return;
                }
                int field = bi.i.a(3) + 1;
                int[] ability = new int[]{16384, 65536, 32768, 2, 262144};
                if (!(field != 1 && field != 2 || selectItem.N() != 640476 && selectItem.N() != 640481)) {
                    pc.j().f(selectItem);
                    ah.a(pc, selectItem.N() + 1, 1, selectItem.C());
                } else if (field == 3) {
                    pc.j().f(selectItem);
                    ah.a(pc, selectItem.a().V(), 1, selectItem.C());
                } else {
                    selectItem.l(0);
                    selectItem.m(0);
                    selectItem.n(0);
                }
                if (field == 1) {
                    selectItem.l(ability[bi.i.a(ability.length)]);
                } else if (field == 2) {
                    selectItem.m(ability[bi.i.a(ability.length)]);
                } else if (field == 3) {
                    selectItem.n(ability[bi.i.a(ability.length)]);
                }
                pc.a(new ds(3657));
                selectItem.x();
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640574 || itemId == 640575) {
                if (pc.fp() == 4 && pc.fu().e(new h(33333, 32444))) {
                    int npcid = l1iteminstance.a().V();
                    bg.a(npcid, pc, 7, 0L);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 640547) {
                int counts = 1;
                rnd = bi.i.a(100) + 1;
                if (rnd <= 40) {
                    counts = 17 + bi.i.a(60);
                } else if (rnd >= 41 && rnd <= 70) {
                    counts = 177 + bi.i.a(600);
                } else if (rnd >= 71 && rnd <= 90) {
                    counts = 1777 + bi.i.a(6000);
                } else if (rnd >= 91 && rnd <= 100) {
                    counts = 17777 + bi.i.a(60000);
                }
                ah.a(pc, 40308, counts);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640622 && itemId <= 640625) {
                int rnd3 = bi.i.a(100) + 1;
                int counts = 0;
                if (itemId == 640622) {
                    counts = rnd3 * 5;
                } else if (itemId == 640623) {
                    counts = rnd3 * 10;
                } else if (itemId == 640624) {
                    counts = rnd3 * 20;
                } else if (itemId == 640625) {
                    counts = 50000;
                }
                ah.a(pc, 640621, counts);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 47103) {
                pc.a(new ds(452, l1iteminstance.s()));
            } else if (itemId == 40003) {
                for (q lightItem : pc.j().d()) {
                    if (lightItem.N() != 40002) continue;
                    lightItem.j(l1iteminstance.a().d());
                    pc.a(new bm(lightItem));
                    pc.a(new ds(230));
                    break;
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 43000) {
                pc.k(1);
                pc.Z();
                pc.ay(0);
                pc.a(new ee(pc.fr(), 191));
                pc.b(new ee(pc.fr(), 191));
                pc.a(new ck(pc));
                pc.a(new ds(822));
                pc.I();
                pc.ac();
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 40033 && itemId <= 40038) {
                if (pc.bB() >= 10) {
                    pc.a(new ds(939));
                    return;
                }
                if (itemId == 40033 && pc.bf() < 45) {
                    pc.o(1);
                } else if (itemId == 40034 && pc.bg() < 45) {
                    pc.p(1);
                } else if (itemId == 40035 && pc.bh() < 45) {
                    pc.q(1);
                    pc.W();
                } else if (itemId == 40036 && pc.bj() < 45) {
                    pc.s(1);
                } else if (itemId == 40037 && pc.bk() < 45) {
                    pc.t(1);
                    pc.Y();
                } else if (itemId == 40038 && pc.bi() < 45) {
                    pc.r(1);
                } else {
                    pc.a(new ds(481));
                    return;
                }
                pc.az(pc.bB() + 1);
                pc.a(new cl(pc));
                pc.I();
                pc.a(new dc(489, pc));
                pc.a(new dc(490, pc));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40858) {
                pc.e(true);
                pc.a(new bs(pc.fr(), 1));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40017 || itemId == 40507 || itemId == 640496) {
                pc.a(new ee(pc.fr(), 192));
                pc.b(new ee(pc.fr(), 192));
                pc.en();
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40014 || itemId == 140014 || itemId == 41415 || itemId == 49305 || itemId == 640363 || itemId == 640729) {
                aw.d.a(pc, 1000, l1iteminstance, pc.z() || pc.F());
            } else if (itemId == 40031 || itemId == 640365 || itemId == 640732) {
                aw.d.a(pc, 1000, l1iteminstance, pc.x());
            } else if (itemId == 40733) {
                aw.d.a(pc, 1000, l1iteminstance, !pc.D() && !pc.E());
            } else if (itemId == 40068 || itemId == 140068 || itemId == 49304 || itemId == 640364 || itemId == 640730) {
                aw.d.a(pc, 1016, l1iteminstance, pc.A());
            } else if (itemId == 49158 || itemId == 640731) {
                aw.d.a(pc, 1017, l1iteminstance, pc.D() || pc.E());
            } else if (itemId == 49138 || itemId == 640190) {
                aw.d.a(pc, l1iteminstance, 600);
            } else if (itemId == 47005) {
                pc.a(new ee(pc.fr(), 7321));
                pc.b(new ee(pc.fr(), 7321));
                s.b(pc, 4006, 2400);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640664) {
                if (pc.cC() > 0) {
                    pc.a(new ds(4384));
                    return;
                }
                pc.K(3850000);
                pc.a(new ee(pc.fr(), 7467));
                pc.b(new ee(pc.fr(), 7467));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640665) {
                pc.a(new ee(pc.fr(), 13249));
                pc.b(new ee(pc.fr(), 13249));
                pc.j(4078, 1200000);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640336) {
                pc.a(new ee(pc.fr(), 7892));
                pc.b(new ee(pc.fr(), 7892));
                s.b(pc, 4070, 1800);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 47006) {
                pc.a(new ee(pc.fr(), 7013));
                pc.b(new ee(pc.fr(), 7013));
                s.b(pc, 4007, 3600);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 47000 && itemId <= 47004) {
                int skillId = itemId - 42999;
                gfxid = itemId - 39699;
                s.b(pc, skillId, 900);
                pc.a(new ee(pc.fr(), gfxid));
                pc.b(new ee(pc.fr(), gfxid));
                pc.a(new ds(1292));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 47007 && itemId <= 47009) {
                int skillId = itemId - 42999;
                gfxid = itemId - 40014;
                s.b(pc, skillId, 3600);
                pc.a(new ee(pc.fr(), gfxid));
                pc.b(new ee(pc.fr(), gfxid));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40032 || itemId == 40041 || itemId == 41344 || itemId == 49303) {
                aw.d.f(pc, l1iteminstance);
            } else if (itemId == 40015 || itemId == 140015 || itemId == 40736 || itemId == 49306 || itemId == 640495) {
                aw.d.d(pc, l1iteminstance);
            } else if (itemId == 40016 || itemId == 140016 || itemId == 49307 || itemId == 640733) {
                aw.d.e(pc, l1iteminstance);
            } else if (itemId == 40025) {
                aw.d.g(pc, l1iteminstance);
            } else if (itemId == 640831) {
                pc.a(new dc(1038, 600, 0, 6546, 0, 3823, 1971, 1972, 1));
                if (!pc.bB(1038)) {
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
                    pc.a(new do(pc));
                }
                pc.a(new bs(pc.fr(), 8));
                pc.b(new bs(pc.fr(), 8));
                pc.a(new ee(pc.fr(), 12214));
                pc.b(new ee(pc.fr(), 12214));
                pc.j(1038, 600000);
            } else if (itemId == 640702) {
                pc.a(new dc(4080, 1800, 0, 4910, 0, 4415, 0, 0, 1));
                if (!pc.bB(4080)) {
                    pc.bH(25);
                    pc.bJ(20);
                    pc.a(new ck(pc));
                }
                pc.a(new ee(pc.fr(), 13391));
                pc.b(new ee(pc.fr(), 13391));
                pc.j(4080, 1800000);
            } else if (itemId == 640583) {
                int time = l1iteminstance.a().V();
                isPoly = ae.a(pc, 13450, time, 1);
                if (!isPoly) {
                    l1iteminstance.a((Timestamp)null);
                    pc.a(new ds(79));
                    return;
                }
                if (!pc.bB(1006)) {
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
                pc.j(1006, time * 1000);
            } else if (itemId == 640827) {
                int time = l1iteminstance.a().V();
                isPoly = ae.a(pc, 12854, time, 1);
                if (!isPoly) {
                    l1iteminstance.a((Timestamp)null);
                    pc.a(new ds(79));
                    return;
                }
                if (!pc.bB(1037)) {
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
                pc.j(1037, time * 1000);
                for (q item : pc.j().d()) {
                    if (!item.a().S() || !item.D()) continue;
                    pc.j().a(item, false);
                }
            } else if (itemId == 640785) {
                int time = l1iteminstance.a().V();
                isPoly = ae.a(pc, 14491, time, 1);
                if (!isPoly) {
                    l1iteminstance.a((Timestamp)null);
                    pc.a(new ds(79));
                    return;
                }
                if (!pc.bB(1031)) {
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
                pc.j(1031, time * 1000);
            } else if (itemId >= 640581 && itemId <= 640582) {
                int polyid = l1iteminstance.a().V();
                ae.a(pc, polyid, 5400, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640739) {
                int[] polys = new int[]{12283, 12283, 12314, 12295, 12280, 12283, 12286, 12283};
                ae.a(pc, polys[pc.ay()], 1800, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640307 && itemId <= 640311) {
                int polyid = l1iteminstance.a().V();
                ae.a(pc, polyid, 3600, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 49220 || itemId == 49139 || itemId >= 41154 && itemId <= 41157 || itemId >= 41143 && itemId <= 41145 || itemId >= 640416 && itemId <= 640423 || itemId == 640836) {
                int time;
                int n2 = time = itemId == 49220 ? 1200 : 900;
                if (itemId >= 640416 && itemId <= 640423) {
                    time = 3600;
                } else if (itemId == 640836) {
                    time = 600;
                }
                int polyid = l1iteminstance.a().V();
                ae.a(pc, polyid, time, 1);
                if (itemId != 640836) {
                    pc.j().b(l1iteminstance, 1);
                }
            } else if (itemId >= 49149 && itemId <= 49155) {
                if (pc.F()) {
                    pc.a(new ds(79));
                    return;
                }
                this.a(pc, l1iteminstance);
            } else if (itemId == 40317 || itemId == 640498) {
                int msgid = 79;
                if (!selectItem.f() && selectItem.H() > 0) {
                    pc.j().h(selectItem);
                    msgid = selectItem.H() == 0 ? 464 : 463;
                }
                pc.a(new ds(msgid, selectItem.s()));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 47017 && itemId <= 47023) {
                int gfxid5 = l1iteminstance.a().V();
                pc.a(new ee(pc.fr(), gfxid5));
                pc.b(new ee(pc.fr(), gfxid5));
                s.b(pc, itemId - 42968, 600);
            } else if (itemId >= 47041 && itemId <= 47046) {
                int createID = l1iteminstance.a().V();
                if (selectItem.a().V() == createID) {
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                    ah.a(pc, createID, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId >= 47049 && itemId <= 47052) {
                if (selectItem.N() >= 47053 && selectItem.N() <= 47062) {
                    if (bi.i.a(100) + 1 > 50) {
                        int newItem = selectItem.N() + (itemId - 47048) * 10;
                        ah.a(pc, newItem, 1);
                    } else {
                        pc.a(new ds(1411, selectItem.b()));
                    }
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 47048) {
                if (selectItem.N() >= 47053 && selectItem.N() <= 47102) {
                    int createID = selectItem.a().V();
                    if (createID == 0) {
                        pc.a(new ds(79));
                        return;
                    }
                    if (bi.i.a(100) + 1 > 50) {
                        pc.a(new ds(1410, selectItem.b()));
                        ah.a(pc, createID, 1);
                    } else {
                        pc.a(new ds(1411, selectItem.b()));
                    }
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId >= 640404 && itemId <= 640415) {
                if (pc.j().b(41246, 100)) {
                    int skillid = l1iteminstance.a().V();
                    s.a(pc, skillid);
                    int[] gfxids = new int[]{13160, 13161, 13162, 13163};
                    pc.a(new ee(pc.fr(), gfxids[skillid - 4072]));
                    pc.b(new ee(pc.fr(), gfxids[skillid - 4072]));
                } else {
                    pc.a(new ds(337, "$5240"));
                }
            } else if (itemId >= 47064 && itemId <= 47102) {
                if (pc.j().b(41246, 250)) {
                    int offset = 0;
                    if (itemId >= 47064 && itemId <= 47072) {
                        offset = 0;
                    } else if (itemId >= 47074 && itemId <= 47082) {
                        offset = 1;
                    } else if (itemId >= 47084 && itemId <= 47092) {
                        offset = 2;
                    } else if (itemId >= 47094 && itemId <= 47102) {
                        offset = 3;
                    }
                    int skillId = itemId - (43051 + offset);
                    int gfxid6 = itemId - (38125 + offset);
                    pc.a(new ee(pc.fr(), gfxid6));
                    pc.b(new ee(pc.fr(), gfxid6));
                    s.a(pc, skillId, 600);
                } else {
                    isDelayEffect = false;
                    pc.a(new ds(337, "$5240"));
                }
            } else if (itemId == 40097 || itemId == 40119 || itemId == 140119 || itemId == 40329) {
                for (q eachItem : pc.j().d()) {
                    if (eachItem.F() != 2 && eachItem.F() != 130 || (itemId == 40119 || itemId == 40097) && l1iteminstance.F() != 0 && !eachItem.D()) continue;
                    if (eachItem.F() == 130) {
                        eachItem.f(129);
                    } else {
                        eachItem.f(1);
                    }
                    if (eachItem.d() && pc.j().d(eachItem.N(), 1) != null) {
                        pc.j().b(eachItem, eachItem.E());
                        ah.a(pc, eachItem.N(), eachItem.E(), eachItem.G(), eachItem.F(), eachItem.C());
                        continue;
                    }
                    pc.j().j(eachItem);
                }
                pc.a(new ds(155));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40126 || itemId == 40098) {
                if (selectItem == null) {
                    pc.a(new ds(79));
                    return;
                }
                if (!selectItem.C()) {
                    selectItem.a(true);
                    pc.j().b(selectItem);
                }
                pc.a(new bf(selectItem));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640758) {
                int createID = selectItem.a().V();
                if (selectItem.N() >= 640753 && selectItem.N() <= 640757 && createID > 0) {
                    if (bi.i.a(100) < 20) {
                        ah.a(pc, createID, 1);
                    } else {
                        pc.a(new ds(165, selectItem.b()));
                    }
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 41036) {
                int createID = selectItem.N() + 10;
                probability = 67;
                this.a(pc, l1iteminstance, selectItem, createID, 67, 0, 158);
            } else if (itemId >= 41048 && itemId <= 41057) {
                if (selectItem.N() == itemId + 8034) {
                    ah.a(pc, selectItem.a().V(), 1);
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId >= 41738 && itemId <= 41753) {
                ah.a(pc, 41719 + bi.i.a(18), 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40925) {
                int createID = selectItem.N() + 174;
                probability = 90;
                this.a(pc, l1iteminstance, selectItem, createID, 90, 0, 158);
            } else if (itemId >= 40931 && itemId <= 40942) {
                int createID = l1iteminstance.a().V();
                probability = 90;
                this.a(pc, l1iteminstance, selectItem, createID, 90, 0, 160);
            } else if (itemId >= 40926 && itemId <= 40942 && itemId != 40930) {
                int createID = selectItem.N() + (itemId == 40926 ? 3 : 1);
                probability = 90;
                this.a(pc, l1iteminstance, selectItem, createID, 90, 0, 160);
            } else if (itemId >= 40943 && itemId <= 40958) {
                int createID = 20435 + (selectItem.N() - 41185);
                probability = 80;
                int successMsg = l1iteminstance.a().V() - 4;
                int failureMsg = l1iteminstance.a().V();
                this.a(pc, l1iteminstance, selectItem, createID, 80, successMsg, failureMsg);
            } else if (itemId == 41029) {
                int createID = selectItem.N() + 1;
                probability = 50;
                this.a(pc, l1iteminstance, selectItem, createID, 50, 0, 158);
            } else if (itemId == 40964) {
                int createID = selectItem.N() + 8;
                probability = 50;
                this.a(pc, l1iteminstance, selectItem, createID, 50, 0, 158);
            } else if (itemId == 40314 || itemId == 40316) {
                this.g(pc, l1iteminstance);
            } else if (itemId == 40315) {
                pc.a(new ef(437));
                pc.b(new ef(437));
                for (t petNpc : pc.ek().values()) {
                    if (!(petNpc instanceof v)) continue;
                    ((v)petNpc).i();
                }
            } else if (itemId == 640472) {
                switch (telNo) {
                    case 0: {
                        am.a(pc, 32732, 32798, 101, 5, true);
                        break;
                    }
                    case 1: {
                        am.a(pc, 32799, 32799, 102, 5, true);
                        break;
                    }
                    case 2: {
                        am.a(pc, 32799, 32799, 103, 5, true);
                        break;
                    }
                    case 3: {
                        am.a(pc, 32669, 32863, 104, 5, true);
                        break;
                    }
                    case 4: {
                        am.a(pc, 32671, 32863, 105, 5, true);
                        break;
                    }
                    case 5: {
                        am.a(pc, 32719, 32870, 106, 5, true);
                        break;
                    }
                    case 6: {
                        am.a(pc, 32670, 32863, 107, 5, true);
                        break;
                    }
                    case 7: {
                        am.a(pc, 32671, 32863, 108, 5, true);
                        break;
                    }
                    case 8: {
                        am.a(pc, 32671, 32863, 109, 5, true);
                        break;
                    }
                    case 9: {
                        am.a(pc, 32799, 32799, 110, 5, true);
                        break;
                    }
                    case 10: {
                        am.a(pc, 32622, 32799, 111, 5, true);
                        break;
                    }
                    case 11: {
                        am.a(pc, 32693, 32903, 111, 5, true);
                    }
                }
            } else if (itemId == 640437) {
                this.e(pc, l1iteminstance);
                if (l1iteminstance.I() <= 0) {
                    pc.a(new ds(79));
                    return;
                }
                switch (telNo) {
                    case 0: {
                        am.a(pc, 32732, 32798, 101, 5, true);
                        break;
                    }
                    case 1: {
                        am.a(pc, 32761, 32833, 77, 5, true);
                        break;
                    }
                    case 2: {
                        am.a(pc, 32712, 32791, 59, 5, true);
                        break;
                    }
                    case 3: {
                        am.a(pc, 32802, 32734, 43, 5, true);
                        break;
                    }
                    case 4: {
                        am.a(pc, 32928, 32799, 430, 5, true);
                        break;
                    }
                    case 5: {
                        am.a(pc, 32925, 32995, 410, 5, true);
                        break;
                    }
                    case 6: {
                        am.a(pc, 32969, 32959, 521, 5, true);
                        break;
                    }
                    case 7: {
                        am.a(pc, 32789, 32799, 600, 5, true);
                        break;
                    }
                    case 8: {
                        am.a(pc, 32753, 32830, 309, 5, true);
                        break;
                    }
                    case 9: {
                        am.a(pc, 32429, 33015, 550, 5, true);
                    }
                }
            } else if (itemId == 640668 || itemId == 640669) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                if (l1iteminstance.bb() != null && l1iteminstance.bb().before(current)) {
                    pc.a(new ds(3081));
                    pc.j().b(l1iteminstance, 1);
                    return;
                }
                am.a(pc, 32780, 32833, 622, 5, true);
            } else if (itemId == 40493) {
                pc.a(new ef(165));
                pc.b(new ef(165));
                for (aa visible : pc.eq()) {
                    ap.o guardian;
                    if (!(visible instanceof ap.o) || (guardian = (ap.o)visible).U_().b() != 70850 || ah.a(pc, 88, 1) == null) continue;
                    pc.j().b(l1iteminstance, 1);
                }
            } else if (itemId >= 40325 && itemId <= 40328) {
                if (pc.j().b(40318, 1)) {
                    int dice = itemId == 40328 ? 6 : itemId - 40323;
                    gfxid = l1iteminstance.a().V() + bi.i.a(dice);
                    pc.a(new ee(pc.fr(), gfxid));
                    pc.b(new ee(pc.fr(), gfxid));
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId >= 41440 && itemId <= 41672) {
                this.d(pc, l1iteminstance);
            } else if (itemId == 41689) {
                if (!pc.C()) {
                    pc.a(new ds(79));
                    return;
                }
                int skillid = l1iteminstance.a().V();
                bh.v l1skills = ao.be.a().a(skillid);
                pc.a(new dc(402, skillid - 600));
                pc.a(new ee(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
                pc.b(new ee(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
                ao.be.a().a(pc.fr(), l1skills.a(), l1skills.b(), 0, 0);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 41681 && itemId <= 41688) {
                if (!pc.F()) {
                    pc.a(new ds(79));
                    return;
                }
                int skillid = l1iteminstance.a().V();
                bh.v l1skills = ao.be.a().a(skillid);
                pc.a(new dc(402, skillid - 600));
                pc.a(new ee(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
                pc.b(new ee(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
                ao.be.a().a(pc.fr(), l1skills.a(), l1skills.b(), 0, 0);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40079 || itemId == 40095 || itemId == 40521 || itemId == 40124 || itemId == 640497) {
                if (!pc.fq().j() && !pc.l()) {
                    pc.a(new ds(647));
                    pc.a(new cn(7, false));
                    return;
                }
                loc = o.a(pc);
                if (itemId == 40124 || itemId == 640497) {
                    if (pc.bF() > 0) {
                        loc = an.a(pc.bF());
                    }
                    if (pc.aF() > 0) {
                        i clan = ao.q.a().a(pc.aF());
                        if (clan.m() > 0) {
                            loc = e.d(clan.m());
                        } else if (clan.n() > 0) {
                            loc = r.a(clan.n());
                        }
                    }
                }
                am.a(pc, loc[0], loc[1], loc[2], 5, true);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640501) {
                if (as.b.a().a((f)pc)) {
                    pc.a(new ds(2139));
                    return;
                }
                if (pc.fu().c(new h(fishX, fishY)) <= 5 && pc.i(fishX, fishY)) {
                    am.a(pc, fishX, fishY, pc.fp(), pc.fb(), true);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(626));
                }
            } else if (itemId >= 40901 && itemId <= 40908) {
                if (pc.bD() == 0) {
                    pc.a(new ds(662));
                    return;
                }
                obj3 = aq.a().a(pc.bD());
                if (obj3 instanceof ap.u) {
                    ap.u partner = (ap.u)obj3;
                    boolean isCastleAarea = e.a(partner.fu());
                    if (partner.fq().h() && !isCastleAarea) {
                        am.a(pc, partner.fs(), partner.ft(), partner.fp(), 5, true);
                    } else {
                        pc.a(new ds(547));
                    }
                } else {
                    pc.a(new ds(546));
                }
            } else if (itemId == 40555) {
                if (pc.z() && pc.fu().e(new h(32821, 32800))) {
                    am.a(pc, 32815, 32810, 13, 5, true);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 40417) {
                if (pc.fp() == 440 && pc.fu().e(new h(32670, 32980))) {
                    am.a(pc, 32922, 32812, 430, 5, true);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 40700) {
                pc.a(new ef(10));
                pc.b(new ef(10));
                if (pc.fs() >= 32619 && pc.fs() <= 32623 && pc.ft() >= 33120 && pc.ft() <= 33124 && pc.fp() == 440) {
                    this.a(pc, 45875);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 40566) {
                if (pc.A() && pc.fp() == 4 && !pc.j().f(40548) && pc.fs() >= 33971 && pc.fs() <= 33975 && pc.ft() >= 32324 && pc.ft() <= 32328) {
                    this.a(pc, 45300);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 49222) {
                if (pc.D() && pc.fp() == 61) {
                    this.a(pc, 46161);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 49189) {
                if (pc.E() && pc.fp() == 4) {
                    this.a(pc, 46163);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 49201) {
                if (pc.E() && pc.fp() == 4) {
                    this.a(pc, 81254);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 49208 || itemId == 49227) {
                if (pc.ay() >= 5 && pc.fp() == 2004) {
                    this.a(pc, 81307 + pc.ay());
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 49167) {
                if (pc.ay() <= 3 && pc.fp() == 2000 + pc.ay() && pc.fs() == 32807 && pc.ft() == 32773) {
                    this.a(pc, 81323 + pc.ay());
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId >= 40557 && itemId <= 40563) {
                int[][] data = new int[][]{{32620, 32641}, {33513, 32890}, {34215, 33195}, {32580, 33260}, {33046, 32806}, {33447, 33476}, {32730, 32426}};
                i2 = itemId - 40557;
                int npcid = l1iteminstance.a().V();
                if (pc.fs() == data[i2][0] && pc.ft() == data[i2][1] && pc.fp() == 4) {
                    for (aa obj2 : aq.a().b()) {
                        if (!(obj2 instanceof t) || ((t)obj2).z() != npcid) continue;
                        pc.a(new ds(79));
                        return;
                    }
                    bg.a(npcid, pc, 0, 300000L);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 40572) {
                if (pc.fs() == 32778 && pc.ft() == 32738 && pc.fp() == 21) {
                    am.a(pc, 32781, 32728, 21, 5, true);
                } else if (pc.fs() == 32781 && pc.ft() == 32728 && pc.fp() == 21) {
                    am.a(pc, 32778, 32738, 21, 5, true);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 40412) {
                pc.a(new ak(pc.fr(), 17));
                pc.b(new ak(pc.fr(), 17));
                if (!pc.fq().l()) {
                    pc.a(new ds(79));
                    return;
                }
                int[] mobArray = new int[]{45008, 45140, 45016, 45021, 45025, 45033, 45099, 45147, 45123, 45130, 45046, 45092, 45138, 45098, 45127, 45143, 45149, 45171, 45040, 45155, 45192, 45173, 45213, 45079, 45144};
                rnd = bi.i.a(mobArray.length);
                bg.a(mobArray[rnd], pc, 0, 300000L);
                this.e(pc, l1iteminstance);
            } else if (itemId == 40007 || itemId == 40006) {
                obj3 = aq.a().a(spellsc_objid);
                f target3 = new f();
                if (obj3 == null) {
                    target3.cF(spellsc_objid);
                    target3.cG(spellsc_x);
                    target3.cH(spellsc_y);
                } else {
                    target3.cF(obj3.fr());
                    target3.cG(obj3.fs());
                    target3.cH(obj3.ft());
                }
                int dmg = this.a(pc, obj3);
                if (itemId == 40006) {
                    dmg *= 2;
                }
                if (l1iteminstance.F() == 0) {
                    dmg *= 2;
                }
                int gfxid7 = itemId == 40006 ? 11737 : 10;
                pc.ct(pc.a((aa)target3));
                pc.a(new be.g(pc, target3, 17, gfxid7, dmg, 6, 0));
                pc.b(new be.g(pc, target3, 17, gfxid7, dmg, 6, 0));
                this.e(pc, l1iteminstance);
            } else if (itemId == 40008 || itemId == 40410 || itemId == 140008) {
                if (pc.fq().g()) {
                    pc.a(new ds(563));
                    return;
                }
                pc.a(new ak(pc.fr(), 17));
                pc.b(new ak(pc.fr(), 17));
                target = aq.a().a(spellsc_objid);
                if (target instanceof f) {
                    this.a(pc, (f)target);
                    this.e(pc, l1iteminstance);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 41121 || itemId == 41130) {
                ah.a(pc, itemId + 1, 1);
            } else if (itemId == 42501) {
                if (pc.eb() < 10) {
                    pc.a(new ds(278));
                } else {
                    pc.i_(pc.eb() - 10);
                    am.a(pc, spellsc_x, spellsc_y, pc.fp(), pc.fb(), true);
                }
            } else if (itemId == 41759 || itemId == 41760 || itemId == 41762) {
                pc.a(new ca(2936, new String[0]));
                pc.am(l1iteminstance.fr());
            } else if (itemId == 41763) {
                if (pc.cI() > 110) {
                    pc.a(new ds(2962));
                } else {
                    bh.c.a(pc);
                    pc.j().b(l1iteminstance, 1);
                }
            } else if (itemId == 41293 || itemId == 640269 || itemId == 640282) {
                this.a(pc, fishX, fishY, l1iteminstance);
            } else if (itemId == 640272) {
                if (selectItem == null) {
                    pc.a(new ds(156));
                    return;
                }
                if (selectItem.N() == 640269) {
                    pc.j().b(l1iteminstance, 1);
                    pc.j().b(selectItem, 1);
                    q create = ah.a(pc, 640282, 1);
                    create.a(true);
                    create.g(100);
                    pc.j().b(create);
                } else if (selectItem.N() == 640282 && selectItem.I() <= 300) {
                    pc.j().b(l1iteminstance, 1);
                    selectItem.g(selectItem.I() + 100);
                    pc.j().b(selectItem);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 41245) {
                if (selectItem == null) {
                    pc.a(new ds(156));
                    return;
                }
                this.a(pc, selectItem, l1iteminstance);
            } else if (itemId >= 41255 && itemId <= 41259) {
                if (cookStatus == 0) {
                    pc.a(new cm(52, itemId - 41255));
                } else {
                    this.b(pc, cookNo);
                }
            } else if (itemId == 41260) {
                for (aa obj3 : pc.eq()) {
                    if (!(obj3 instanceof ap.h) || obj3.f(pc) > 3 || ((ap.h)obj3).fe() != 5943) continue;
                    pc.a(new ds(1162));
                    return;
                }
                loc = pc.eg();
                ai.a().a(5943, 600000, loc[0], loc[1], pc.fp());
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 41345) {
                az.a.a(pc, pc, 3000, 5, 30);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 41315 || itemId == 41316 || itemId == 41354 || itemId == 49168) {
                int skillid = l1iteminstance.a().V();
                pc.j(skillid, 900000);
                pc.a(new ee(pc.fr(), 190));
                pc.b(new ee(pc.fr(), 190));
                if (itemId != 49168) {
                    pc.a(new ds(skillid + 127));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640297 || itemId == 640298) {
                int timesec = l1iteminstance.a().V();
                pc.j(1030, timesec * 1000);
                pc.a(new ee(pc.fr(), timesec == 300 ? 20 : 21));
                pc.b(new ee(pc.fr(), timesec == 300 ? 20 : 21));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 49092 || itemId == 49094 || itemId == 49098 || itemId == 49317 || itemId == 49321 || itemId == 49198 || itemId == 49199 || itemId == 49188) {
                if (selectItem.a().V() == itemId) {
                    int i4 = l1iteminstance.a().V();
                    ah.a(pc, selectItem.N() + i4, 1);
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 49148 || itemId == 640145 || itemId == 640727) {
                aw.a.a(pc, l1iteminstance, selectItem, false);
            } else if (itemId == 640614) {
                if (selectItem.N() >= 21366 && selectItem.N() <= 21371) {
                    aw.a.a(pc, l1iteminstance, selectItem, true);
                } else {
                    pc.a(new ds(79));
                }
            } else if (itemId == 41426) {
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
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 41427) {
                if (selectItem.F() < 128 || selectItem.f() && !selectItem.a().aN()) {
                    pc.a(new ds(79));
                    return;
                }
                selectItem.f(selectItem.F() - 128);
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 41428) {
                bh.a account = pc.aK().e();
                int solt = Math.min(account.k() + 1, 8);
                account.e(solt);
                ao.a.a().c(account);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40075) {
                if (selectItem.h()) {
                    int[] msg = new int[]{167, 171, 169, 170, 168, 172, 173, 174};
                    if (selectItem.a().aP() <= 7) {
                        pc.a(new ds(msg[selectItem.a().aP()]));
                    } else {
                        pc.a(new ds(msg[0]));
                    }
                    pc.j().b(selectItem, 1);
                } else {
                    pc.a(new ds(154));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640941) {
                pc.a(new be(pc.fr(), "gunterseal"));
            } else if (itemId == 40630) {
                pc.a(new be(pc.fr(), "diegodiary"));
            } else if (itemId == 40641) {
                pc.a(new be(pc.fr(), "tscrolla"));
            } else if (itemId == 40663) {
                pc.a(new be(pc.fr(), "sonsletter"));
            } else if (itemId == 41007) {
                pc.a(new be(pc.fr(), "erisscroll"));
            } else if (itemId == 41009) {
                pc.a(new be(pc.fr(), "erisscroll2"));
            } else if (itemId == 41019) {
                pc.a(new be(pc.fr(), "lashistory1"));
            } else if (itemId == 41020) {
                pc.a(new be(pc.fr(), "lashistory2"));
            } else if (itemId == 41021) {
                pc.a(new be(pc.fr(), "lashistory3"));
            } else if (itemId == 41022) {
                pc.a(new be(pc.fr(), "lashistory4"));
            } else if (itemId == 41023) {
                pc.a(new be(pc.fr(), "lashistory5"));
            } else if (itemId == 41024) {
                pc.a(new be(pc.fr(), "lashistory6"));
            } else if (itemId == 41025) {
                pc.a(new be(pc.fr(), "lashistory7"));
            } else if (itemId == 41026) {
                pc.a(new be(pc.fr(), "lashistory8"));
            } else if (itemId == 41060) {
                pc.a(new be(pc.fr(), "nonames"));
            } else if (itemId == 41061) {
                pc.a(new be(pc.fr(), "kames"));
            } else if (itemId == 41062) {
                pc.a(new be(pc.fr(), "bakumos"));
            } else if (itemId == 41063) {
                pc.a(new be(pc.fr(), "bukas"));
            } else if (itemId == 41064) {
                pc.a(new be(pc.fr(), "huwoomos"));
            } else if (itemId == 41065) {
                pc.a(new be(pc.fr(), "noas"));
            } else if (itemId == 41356) {
                pc.a(new be(pc.fr(), "rparum3"));
            } else if (itemId == 41340) {
                pc.a(new be(pc.fr(), "tion"));
            } else if (itemId == 41317) {
                pc.a(new be(pc.fr(), "rarson"));
            } else if (itemId == 41318) {
                pc.a(new be(pc.fr(), "kuen"));
            } else if (itemId == 41329) {
                pc.a(new be(pc.fr(), "anirequest"));
            } else if (itemId == 41346) {
                pc.a(new be(pc.fr(), "robinscroll"));
            } else if (itemId == 41347) {
                pc.a(new be(pc.fr(), "robinscroll2"));
            } else if (itemId == 41348) {
                pc.a(new be(pc.fr(), "robinhood"));
            } else if (itemId == 49172) {
                pc.a(new be(pc.fr(), "silrein1lt"));
            } else if (itemId == 49173) {
                pc.a(new be(pc.fr(), "silrein2lt"));
            } else if (itemId == 49174) {
                pc.a(new be(pc.fr(), "silrein3lt"));
            } else if (itemId == 49175) {
                pc.a(new be(pc.fr(), "silrein4lt"));
            } else if (itemId == 49176) {
                pc.a(new be(pc.fr(), "silrein5lt"));
            } else if (itemId == 49177) {
                pc.a(new be(pc.fr(), "silrein6lt"));
            } else if (itemId == 49202) {
                pc.a(new be(pc.fr(), "cot_ep1st"));
            } else if (itemId == 49206) {
                pc.a(new be(pc.fr(), "bluesoul_p"));
            } else if (itemId == 49210) {
                pc.a(new be(pc.fr(), "first_p"));
            } else if (itemId == 49211) {
                pc.a(new be(pc.fr(), "second_p"));
            } else if (itemId == 49212) {
                pc.a(new be(pc.fr(), "third_p"));
            } else if (itemId == 49221) {
                pc.a(new be(pc.fr(), "spy_letter"));
            } else if (itemId == 49231) {
                pc.a(new be(pc.fr(), "redsoul_p"));
            } else if (itemId == 49287) {
                pc.a(new be(pc.fr(), "fourth_p"));
            } else if (itemId == 49288) {
                pc.a(new be(pc.fr(), "fifth_p"));
            } else {
                int locX = l1iteminstance.a().aG();
                int locY = l1iteminstance.a().aH();
                short mapId = l1iteminstance.a().aI();
                if (locX != 0 && locY != 0) {
                    if (pc.fq().j() || pc.l()) {
                        if (itemId >= 40103 && itemId <= 40112 && pc.fp() == mapId) {
                            am.a(pc, 200);
                        } else {
                            am.a(pc, locX, locY, mapId, pc.fb(), true);
                        }
                        pc.j().b(l1iteminstance, 1);
                    } else {
                        pc.a(new ds(647));
                        pc.a(new cn(7, false));
                    }
                } else if (l1iteminstance.E() < 1) {
                    pc.a(new ds(329, l1iteminstance.s()));
                } else {
                    pc.a(new ds(74, l1iteminstance.s()));
                }
            }
        }
        if (isDelayEffect) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            l1iteminstance.a(ts);
            pc.j().j(l1iteminstance);
        }
        av.a.a(client, l1iteminstance);
        for (bh.s qn : pc.dS().values()) {
            int i5 = 0;
            while (i5 < qn.u().length) {
                if (qn.u()[i5] == itemId) {
                    qn.c(i5);
                }
                ++i5;
            }
        }
    }

    private void a(ap.u pc, q item) {
        int basePolyID = item.a().V();
        if (pc.D() || pc.E()) {
            basePolyID = 7129 + (item.N() - 49149) * 4;
        }
        int polyid = basePolyID + pc.ay() * 2 + pc.aJ();
        ae.a(pc, polyid, 1800, 1);
        pc.j().b(item, 1);
    }

    private void a(ap.u pc, q l1iteminstance, q selectItem, int createID, int probability, int successMsg, int failureMsg) {
        int itemId = l1iteminstance.N();
        if (selectItem.a().V() == itemId) {
            if (bi.i.a(100) < probability) {
                pc.j().b(selectItem, 1);
                ah.a(pc, createID, 1);
                if (successMsg > 0) {
                    pc.a(new ds(successMsg, selectItem.b()));
                }
            } else {
                pc.a(new ds(failureMsg, selectItem.b()));
                if (failureMsg == 158) {
                    pc.j().b(selectItem, 1);
                }
            }
            pc.j().b(l1iteminstance, 1);
        } else {
            pc.a(new ds(79));
        }
    }

    private void a(ap.u pc, q item, int blanksc_skillid) {
        if (!pc.B()) {
            pc.a(new ds(264));
            return;
        }
        if (blanksc_skillid <= item.a().V()) {
            q spellsc = ah.a().b(40858 + blanksc_skillid);
            if (spellsc != null && pc.j().a(spellsc, 1) == 0) {
                bh.v l1skills = ao.be.a().a(blanksc_skillid);
                if (pc.ea() + 1 < l1skills.e() + 1) {
                    pc.a(new ds(279));
                    return;
                }
                if (pc.eb() < l1skills.d()) {
                    pc.a(new ds(278));
                    return;
                }
                if (l1skills.f() != 0 && !pc.j().g(l1skills.f(), l1skills.g())) {
                    pc.a(new ds(299));
                    return;
                }
                pc.a(pc.ea() - l1skills.e());
                pc.i_(pc.eb() - l1skills.d());
                int lawful = pc.fa() + l1skills.o();
                lawful = Math.min(Math.max(-32767, lawful), Short.MAX_VALUE);
                pc.cr(lawful);
                if (l1skills.f() != 0) {
                    pc.j().b(l1skills.f(), l1skills.g());
                }
                pc.j().b(item, 1);
                pc.j().d(spellsc);
            }
        } else {
            pc.a(new ds(591));
        }
    }

    private void b(ap.u pc, q armor) {
        int type = armor.a().aP();
        g pcInventory = pc.j();
        boolean hasEquipeSpace = false;
        int maxEquipeCount = 1;
        if (type == 9) {
            maxEquipeCount = 2;
            if ((pc.cP() & dn.c) == dn.c) {
                ++maxEquipeCount;
            }
            if ((pc.cP() & dn.d) == dn.d) {
                ++maxEquipeCount;
            }
        } else if (type == 13) {
            if ((pc.cP() & dn.e) == dn.e) {
                ++maxEquipeCount;
            }
        } else if (type == 23 && (pc.cP() & dn.f) == dn.f) {
            ++maxEquipeCount;
        }
        boolean bl2 = hasEquipeSpace = pcInventory.i(type) <= maxEquipeCount - 1;
        if (hasEquipeSpace && !armor.D()) {
            int polyid = pc.fe();
            if (!ae.b(pc, type)) {
                pc.a(new ds(2055, armor.s()));
                return;
            }
            if (type == 29 && (pc.cP() & dn.g) != dn.g) {
                pc.a(new ds(333));
                return;
            }
            if (type == 30 && (pc.cP() & dn.h) != dn.h) {
                pc.a(new ds(333));
                return;
            }
            if (type == 10 && pcInventory.i(8) >= 1 || type == 8 && pcInventory.i(10) >= 1) {
                pc.a(new ds(124));
                return;
            }
            if ((type == 10 || type == 8) && pc.w() == 2) {
                pc.a(new ds(124));
                return;
            }
            if (type == 8 && pc.v() != null && pc.v().a().e() && !pc.F()) {
                pc.a(new ds(129));
                return;
            }
            if (type == 3 && pcInventory.i(4) >= 1) {
                pc.a(new ds(126, "$224", "$225"));
                return;
            }
            if (type == 3 && pcInventory.i(2) >= 1) {
                pc.a(new ds(126, "$224", "$226"));
                return;
            }
            if (type == 2 && pcInventory.i(4) >= 1) {
                pc.a(new ds(126, "$226", "$225"));
                return;
            }
            if (type == 23 && armor.bb() == null) {
                if (armor.N() >= 21261 && armor.N() <= 21300) {
                    pc.a(new ds(1891));
                    return;
                }
                if (armor.N() == 21397 && pc.fp() != 1700 && pc.fp() != 1703) {
                    pc.a(new ds(333));
                    return;
                }
            }
            pcInventory.a(armor, true);
        } else if (armor.D()) {
            if (armor.F() == 2) {
                pc.a(new ds(150));
                return;
            }
            if (type == 3 && pcInventory.i(2) >= 1) {
                pc.a(new ds(127));
                return;
            }
            if ((type == 2 || type == 3) && pcInventory.i(4) >= 1) {
                pc.a(new ds(127));
                return;
            }
            if (type == 23 && armor.bb() == null && armor.N() >= 21261 && armor.N() <= 21300) {
                pc.a(new ds(1891));
                return;
            }
            if (type == 8 && pc.bB(90)) {
                pc.bz(90);
            }
            pcInventory.a(armor, false);
        } else {
            pc.a(new ds(124));
        }
        pc.a(pc.ea());
        pc.i_(pc.eb());
        pc.a(new ci(pc));
        pc.a(new ck(pc));
        pc.a(new do(pc));
    }

    private void c(ap.u pc, q weapon) {
        g pcInventory = pc.j();
        if (pc.v() == null || !pc.v().equals(weapon)) {
            int weapon_type = weapon.a().aP();
            int polyid = pc.fe();
            if (!ae.a(pc, weapon_type)) {
                pc.a(new ds(2055, weapon.s()));
                return;
            }
            if (weapon.a().e() && pcInventory.i(8) >= 1 && !pc.bB(603)) {
                pc.a(new ds(128));
                return;
            }
            if (weapon.N() == 413 && pc.fp() != 6311) {
                pc.a(new ds(333));
                return;
            }
        }
        if (pc.v() != null) {
            if (pc.a(weapon)) {
                if (weapon.F() == 2) {
                    pc.a(new ds(150));
                    return;
                }
                pcInventory.a(weapon, false);
                return;
            }
            if (!pc.bB(603) || pcInventory.i(8) >= 1 || pcInventory.i(10) >= 1 || pc.w() == 2) {
                pcInventory.a(pc.v(), false);
            }
        }
        if (weapon.N() == 200002) {
            pc.a(new ds(149, weapon.s()));
        }
        pcInventory.a(weapon, true);
    }

    private void d(ap.u pc, q item) {
        int learnLevel;
        int skillid = item.a().V();
        bh.v l1skills = ao.be.a().a(skillid);
        int skillLevel = l1skills.c();
        boolean isLawful = true;
        if (skillid >= 1 && skillid <= 80) {
            int baseLevel = 0;
            if ((pc.x() || pc.C()) && skillLevel <= 2) {
                baseLevel = 10;
            } else if (pc.A() && skillLevel <= 6) {
                baseLevel = 8;
            } else if (pc.B() && skillLevel <= 10) {
                baseLevel = 4;
            } else if (pc.z() && skillLevel <= 1) {
                baseLevel = 50;
            } else if (pc.F() && skillLevel <= 1) {
                baseLevel = 50;
            }
            if (baseLevel == 0) {
                pc.a(new ds(79));
                return;
            }
            if (pc.ev() / baseLevel < skillLevel) {
                pc.a(new ds(312));
                return;
            }
        }
        if (skillid >= 87 && skillid <= 92) {
            if (!pc.z()) {
                pc.a(new ds(79));
                return;
            }
            int n2 = learnLevel = skillid == 89 ? 60 : 50;
            if (skillid == 92) {
                learnLevel = 80;
            }
            if (pc.ev() < learnLevel) {
                pc.a(new ds(312));
                return;
            }
        }
        if (skillid >= 97 && skillid <= 112 || skillid == 233) {
            if (!pc.C()) {
                pc.a(new ds(79));
                return;
            }
            learnLevel = 60;
            if (skillid >= 97 && skillid <= 100 || skillid == 109) {
                learnLevel = 15;
            } else if (skillid >= 101 && skillid <= 104 || skillid == 110) {
                learnLevel = 30;
            } else if (skillid >= 105 && skillid <= 108 || skillid == 111) {
                learnLevel = 45;
            } else if (skillid == 112) {
                learnLevel = 60;
            } else if (skillid == 233) {
                learnLevel = 80;
            }
            if (pc.ev() < learnLevel) {
                pc.a(new ds(312));
                return;
            }
        }
        if (skillid >= 113 && skillid <= 122) {
            if (!pc.x()) {
                pc.a(new ds(79));
                return;
            }
            learnLevel = 60;
            if (skillid == 113) {
                learnLevel = 15;
            } else if (skillid == 116) {
                learnLevel = 30;
            } else if (skillid == 114) {
                learnLevel = 40;
            } else if (skillid == 118) {
                learnLevel = 45;
            } else if (skillid == 117) {
                learnLevel = 50;
            } else if (skillid == 115) {
                learnLevel = 50;
            } else if (skillid >= 119) {
                learnLevel = 60;
            } else if (skillid >= 122) {
                learnLevel = 80;
            }
            if (pc.ev() < learnLevel) {
                pc.a(new ds(312));
                return;
            }
        }
        if (skillid >= 129 && skillid <= 176) {
            if (!pc.A()) {
                pc.a(new ds(79));
                return;
            }
            learnLevel = 50;
            learnLevel = skillid >= 129 && skillid <= 131 ? 10 : (skillid >= 137 && skillid <= 138 ? 20 : (skillid >= 145 && skillid <= 152 || skillid == 132 || skillid == 170 ? 30 : (skillid >= 153 && skillid <= 160 || skillid == 133 ? 40 : (skillid == 135 ? 80 : 50))));
            if (pc.ev() < learnLevel) {
                pc.a(new ds(312));
                return;
            }
        }
        if (skillid >= 181 && skillid <= 196) {
            if (!pc.D()) {
                pc.a(new ds(79));
                return;
            }
            learnLevel = ((skillid - 181) / 5 + 1) * 15;
            if (skillid == 196) {
                learnLevel = 80;
            }
            if (pc.ev() < learnLevel) {
                pc.a(new ds(312));
                return;
            }
        }
        if (skillid >= 201 && skillid <= 222) {
            if (!pc.E()) {
                pc.a(new ds(79));
                return;
            }
            learnLevel = ((skillid - 201) / 5 + 1) * 10;
            if (skillid == 222) {
                learnLevel = 80;
            }
            if (pc.ev() < learnLevel) {
                pc.a(new ds(312));
                return;
            }
        }
        if (skillid >= 225 && skillid <= 231 && !pc.F()) {
            pc.a(new ds(79));
            return;
        }
        pc.a(new be.d(pc, skillid));
        pc.a(new ee(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
        pc.b(new ee(pc.fr(), 224));
        ao.be.a().a(pc.fr(), l1skills.a(), l1skills.b(), 0, 0);
        pc.j().b(item, 1);
    }

    private void e(ap.u pc, q l1iteminstance) {
        if (l1iteminstance.I() > 1) {
            l1iteminstance.g(l1iteminstance.I() - 1);
            pc.j().b(l1iteminstance);
        } else {
            pc.j().b(l1iteminstance, 1);
        }
    }

    private int a(ap.u pc, aa target) {
        if (target == null || !pc.i(target.fs(), target.ft())) {
            return 0;
        }
        int dmg = pc.ez() * 3 + bi.i.a(pc.ez());
        if (target instanceof ap.u) {
            ap.u tpc = (ap.u)target;
            if (pc.fr() == tpc.fr() || pc.a(pc, tpc, false)) {
                return 0;
            }
            if (w.a(tpc)) {
                return 0;
            }
            int newHp = tpc.ea() - dmg;
            if (newHp > 0) {
                tpc.a(newHp);
            } else if (newHp <= 0 && tpc.l()) {
                tpc.a(tpc.ew());
            } else {
                tpc.b((f)pc);
            }
            return dmg;
        }
        if (target instanceof ap.s) {
            ap.s mob = (ap.s)target;
            mob.b(pc, dmg);
            return dmg;
        }
        return 0;
    }

    private void a(ap.u pc, f traget) {
        ap.s mob;
        boolean isTargetMe = pc.fr() == traget.fr();
        int pid = bi.i.a(ae.f.length);
        int polyId = ae.f[pid];
        int probability = 3 * (pc.ev() - traget.ev()) + 100 - traget.W_();
        if (traget instanceof ap.u) {
            ap.u tpc = (ap.u)traget;
            if (isTargetMe || tpc.aF() != 0 && tpc.aF() == pc.aF()) {
                probability = 100;
            }
            if (probability <= bi.i.a(100)) {
                pc.a(new ds(79));
                return;
            }
            if (tpc.j().h(20281)) {
                tpc.a(new ca(180, ""));
                tpc.t(true);
            } else {
                ae.a(tpc, polyId, 1800, 1);
            }
            if (!isTargetMe) {
                tpc.a(new ds(241, pc.et()));
            }
        } else if (traget instanceof ap.s && (mob = (ap.s)traget).ev() < 50) {
            int[] lowLevelBossID;
            int[] nArray = lowLevelBossID = new int[]{45338, 45370, 45456, 45464, 45473, 45488, 45497, 45516, 45529, 45458};
            int n2 = lowLevelBossID.length;
            int n3 = 0;
            while (n3 < n2) {
                int npcid = nArray[n3];
                if (mob.z() == npcid) {
                    return;
                }
                ++n3;
            }
            ae.a(mob, polyId, 1800, 1);
        }
    }

    private void f(ap.u pc, q item) {
        boolean isTeleport = false;
        if (item.N() >= 40288 && item.N() <= 40297) {
            if (pc.fs() >= 33924 && pc.fs() <= 33930 && pc.ft() >= 33341 && pc.ft() <= 33349) {
                isTeleport = true;
            }
        } else if (item.N() >= 640462 && item.N() <= 640471) {
            if (pc.fs() >= 33924 && pc.fs() <= 33930 && pc.ft() >= 33341 && pc.ft() <= 33349) {
                isTeleport = true;
            }
        } else if (item.N() == 40615) {
            if (pc.fs() >= 32701 && pc.fs() <= 32705 && pc.ft() >= 32894 && pc.ft() <= 32898) {
                isTeleport = true;
            }
        } else if (item.N() == 40616 || item.N() == 40782 || item.N() == 40783) {
            if (pc.fs() >= 32698 && pc.fs() <= 32702 && pc.ft() >= 32894 && pc.ft() <= 32898) {
                isTeleport = true;
            }
        } else if (item.N() == 40692 && pc.j().f(40621)) {
            if (pc.fs() >= 32856 && pc.fs() <= 32858 && pc.ft() >= 32857 && pc.ft() <= 32858) {
                isTeleport = true;
            }
        } else if (item.N() == 41208) {
            if (pc.fs() >= 32844 && pc.fs() <= 32845 && pc.ft() >= 32693 && pc.ft() <= 32694) {
                isTeleport = true;
            }
        } else {
            am.a(pc, item.a().aG(), item.a().aH(), item.a().aI(), 5, true);
            return;
        }
        if (pc.fp() != item.a().V()) {
            isTeleport = false;
        }
        if (isTeleport) {
            am.a(pc, item.a().aG(), item.a().aH(), item.a().aI(), 5, true);
        } else {
            pc.a(new ds(79));
            pc.a(new cn(7, false));
        }
    }

    private void a(ap.u pc, int mobid) {
        for (aa obj : aq.a().b()) {
            if (!(obj instanceof ap.s) || ((ap.s)obj).z() != mobid) continue;
            pc.a(new ds(79));
            return;
        }
        bg.a(mobid, pc, 0, 0L);
    }

    private void g(ap.u pc, q item) {
        int itemObjectId = item.fr();
        if (!pc.fq().n()) {
            pc.a(new ds(563));
            return;
        }
        int petCost = 0;
        for (t petNpc : pc.ek().values()) {
            if (petNpc instanceof v && ((v)petNpc).k() == itemObjectId) {
                return;
            }
            petCost += petNpc.Q();
        }
        int charisma = pc.eC() + (pc.A() ? 12 : 6) - petCost;
        int petCount = charisma / 6;
        if (petCount <= 0) {
            pc.a(new ds(489));
            return;
        }
        if (!pc.j().b(41160, 1)) {
            pc.a(new ds(79));
            return;
        }
        n l1pet = aw.a().b(itemObjectId);
        if (l1pet != null) {
            l npcTemp = au.a().a(l1pet.c());
            v pet = new v(npcTemp, pc, l1pet);
            pet.o(6);
        }
    }

    private void a(ap.u pc, int fishX, int fishY, q item) {
        if (pc.fp() != 5300 && pc.fp() != 5301 && pc.fp() != 5490) {
            pc.a(new ds(1138));
            return;
        }
        if (pc.fe() != pc.aB()) {
            pc.a(new ds(1170));
            return;
        }
        if (!aq.a().c(pc, 0).isEmpty()) {
            pc.a(new ei("\u9019\u500b\u4f4d\u7f6e\u5df2\u88ab\u4f54\u64da\u3002"));
            return;
        }
        if (pc.ff() && !pc.l()) {
            pc.a(new ei("\u96b1\u8eab\u91e3\u9b5a\u662f\u4e0d\u79d1\u5b78\u7684\u3002"));
            return;
        }
        int rodLength = 6;
        if (!pc.fq().g(fishX, fishY)) {
            pc.a(new ds(1138));
            return;
        }
        if (!(pc.fq().g(fishX + 1, fishY) && pc.fq().g(fishX - 1, fishY) && pc.fq().g(fishX, fishY + 1) && pc.fq().g(fishX, fishY - 1))) {
            pc.a(new ds(1138));
            return;
        }
        if (fishX > pc.fs() + 6 || fishX < pc.fs() - 6) {
            pc.a(new ds(1138));
        } else if (fishY > pc.ft() + 6 || fishY < pc.ft() - 6) {
            pc.a(new ds(1138));
        } else if (pc.j().b(640270, 1)) {
            pc.aO(fishX);
            pc.aP(fishY);
            pc.a(new av(pc.fr(), 71, fishX, fishY));
            pc.b(new av(pc.fr(), 71, fishX, fishY));
            pc.r(true);
            bi.e.a().b(new bc.a(pc, item));
        } else {
            pc.a(new ds(1137));
        }
    }

    private void a(ap.u pc, q item, q resolvent) {
        if ((item.g() || item.h()) && (item.D() || item.G() != 0)) {
            pc.a(new ds(1161));
            return;
        }
        int crystalCount = bb.a().a(item.N());
        if (crystalCount == 0) {
            pc.a(new ds(1161));
            return;
        }
        int rnd = bi.i.a(100);
        if (rnd < 50) {
            crystalCount = 0;
            pc.a(new ds(158, item.b()));
        } else if (rnd >= 90) {
            crystalCount = (int)((double)crystalCount * 1.5);
        }
        if (crystalCount > 0) {
            ah.a(pc, 41246, crystalCount);
        }
        ao.aa.a().b(pc, "\u7372\u5f97\u7d50\u6676" + crystalCount + "\u500b\uff0c\u878d\u6389\u4e86", item);
        pc.j().b(item, 1);
        pc.j().b(resolvent, 1);
    }

    private void b(ap.u pc, int cookNo) {
        boolean isNearFire = false;
        for (aa obj : pc.eq()) {
            if (!(obj instanceof ap.h) || obj.f(pc) > 3 || ((ap.h)obj).fe() != 5943) continue;
            isNearFire = true;
            break;
        }
        if (!isNearFire) {
            pc.a(new ds(1160));
            return;
        }
        if (pc.K() <= (double)pc.j().e()) {
            pc.a(new ds(1103));
            return;
        }
        if (pc.bB(2999)) {
            return;
        }
        pc.j(2999, 3000);
        int chance = bi.i.a(100) + 1;
        int[] consumeID = new int[]{};
        int[] createdID = new int[]{};
        switch (cookNo) {
            case 0: {
                consumeID = new int[]{40057};
                createdID = new int[]{41277, 41285};
                break;
            }
            case 1: {
                consumeID = new int[]{41275};
                createdID = new int[]{41278, 41286};
                break;
            }
            case 2: {
                consumeID = new int[]{41263, 41265};
                createdID = new int[]{41279, 41287};
                break;
            }
            case 3: {
                consumeID = new int[]{41274, 41267};
                createdID = new int[]{41280, 41288};
                break;
            }
            case 4: {
                consumeID = new int[]{40062, 40069, 40064};
                createdID = new int[]{41281, 41289};
                break;
            }
            case 5: {
                consumeID = new int[]{40056, 40060, 40061};
                createdID = new int[]{41282, 41290};
                break;
            }
            case 6: {
                consumeID = new int[]{41276};
                createdID = new int[]{41283, 41291};
                break;
            }
            case 7: {
                consumeID = new int[]{40499, 40060};
                createdID = new int[]{41284, 41292};
                break;
            }
            case 8: {
                consumeID = new int[]{49040, 49048};
                createdID = new int[]{49049, 49057};
                break;
            }
            case 9: {
                consumeID = new int[]{49041, 49048};
                createdID = new int[]{49050, 49058};
                break;
            }
            case 10: {
                consumeID = new int[]{49042, 41265, 49048};
                createdID = new int[]{49051, 49059};
                break;
            }
            case 11: {
                consumeID = new int[]{49043, 49048};
                createdID = new int[]{49052, 49060};
                break;
            }
            case 12: {
                consumeID = new int[]{49044, 49048};
                createdID = new int[]{49053, 49061};
                break;
            }
            case 13: {
                consumeID = new int[]{49045, 49048};
                createdID = new int[]{49054, 49062};
                break;
            }
            case 14: {
                consumeID = new int[]{49046, 49048};
                createdID = new int[]{49055, 49063};
                break;
            }
            case 15: {
                consumeID = new int[]{49047, 40499, 49048};
                createdID = new int[]{49056, 49064};
                break;
            }
            case 16: {
                consumeID = new int[]{49048, 49243, 49260};
                createdID = new int[]{49244, 49252};
                break;
            }
            case 17: {
                consumeID = new int[]{49048, 49243, 49261};
                createdID = new int[]{49245, 49253};
                break;
            }
            case 18: {
                consumeID = new int[]{49048, 49243, 49262};
                createdID = new int[]{49246, 49254};
                break;
            }
            case 19: {
                consumeID = new int[]{49048, 49243, 49263};
                createdID = new int[]{49247, 49255};
                break;
            }
            case 20: {
                consumeID = new int[]{49048, 49243, 49264};
                createdID = new int[]{49248, 49256};
                break;
            }
            case 21: {
                consumeID = new int[]{49048, 49243, 49265};
                createdID = new int[]{49249, 49257};
                break;
            }
            case 22: {
                consumeID = new int[]{49048, 49243, 49266};
                createdID = new int[]{49250, 49258};
                break;
            }
            case 23: {
                consumeID = new int[]{49048, 49243, 49267, 40499};
                createdID = new int[]{49251, 49259};
            }
        }
        if (pc.j().a(consumeID)) {
            int[] nArray = consumeID;
            int n2 = consumeID.length;
            int n3 = 0;
            while (n3 < n2) {
                int itemid = nArray[n3];
                pc.j().b(itemid, 1);
                ++n3;
            }
            int gfxid = 6394;
            if (chance <= 90) {
                ah.a(pc, createdID[0], 1);
                gfxid = 6392;
            } else if (chance > 95) {
                ah.a(pc, createdID[1], 1);
                gfxid = 6390;
            } else {
                pc.a(new ds(1101));
            }
            pc.a(new ee(pc.fr(), gfxid));
            pc.b(new ee(pc.fr(), gfxid));
        } else {
            pc.a(new ds(1102));
        }
    }

    @Override
    public String a() {
        return b;
    }
}

