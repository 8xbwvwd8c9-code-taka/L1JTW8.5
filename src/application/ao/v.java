/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.ah;
import ao.ak;
import ao.ao;
import ao.u;
import ap.q;
import ap.t;
import ap.z;
import aq.aq;
import aq.q;
import au.f;
import be.ds;
import be.ei;
import bh.h;
import bi.i;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;
import l1j.server.b;

public class v {
    private static final Logger a = Logger.getLogger(v.class.getName());
    private static v b;
    private final HashMap<Integer, ArrayList<h>> c = this.b();

    public static v a() {
        if (b == null) {
            b = new v();
        }
        return b;
    }

    private v() {
    }

    private HashMap<Integer, ArrayList<h>> b() {
        HashMap<Integer, ArrayList<h>> droplistMap;
        block7: {
            droplistMap = new HashMap<Integer, ArrayList<h>>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("select * from droplist");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int unblesschance;
                        int blesschance;
                        int enchantlvl;
                        int chance;
                        int max;
                        int min;
                        int itemId;
                        int mobId = rs.getInt("mobId");
                        h drop = new h(mobId, itemId = rs.getInt("itemId"), min = rs.getInt("min"), max = rs.getInt("max"), chance = rs.getInt("chance"), enchantlvl = rs.getInt("enchantlvl"), blesschance = rs.getInt("bless_change"), unblesschance = rs.getInt("unbless_change"));
                        ArrayList<h> dropList = droplistMap.get(drop.e());
                        if (dropList == null) {
                            dropList = new ArrayList();
                            droplistMap.put(new Integer(drop.e()), dropList);
                        }
                        dropList.add(drop);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
        return droplistMap;
    }

    public void a(t npc, f inventory) {
        double adenarate;
        u.a().a(npc);
        int mobId = npc.U_().b();
        List dropList = this.c.get(mobId);
        if (dropList == null) {
            return;
        }
        double droprate = l1j.server.a.F;
        if (droprate <= 0.0) {
            droprate = 0.0;
        }
        if ((adenarate = l1j.server.a.E) <= 0.0) {
            adenarate = 0.0;
        }
        if (droprate <= 0.0 && adenarate <= 0.0) {
            return;
        }
        for (h drop : dropList) {
            int itemId = drop.b();
            if (adenarate == 0.0 && itemId == 40308) continue;
            int randomChance = i.a(1000000) + 1;
            double rateOfMapId = ao.a().b(npc.fp());
            ak.a().a(itemId, (double)drop.a() * droprate * rateOfMapId, inventory);
            if (droprate == 0.0 || (double)drop.a() * droprate * rateOfMapId < (double)randomChance) continue;
            int itemCount = drop.d();
            int addCount = drop.c() - drop.d() + 1;
            if (addCount > 1) {
                itemCount += i.a(addCount);
            }
            if (itemId == 40308) {
                itemCount = (int)((double)itemCount * adenarate);
            }
            if (itemCount < 0) {
                itemCount = 0;
            }
            if (itemCount > 2000000000) {
                itemCount = 2000000000;
            }
            int enchantlvl = drop.f();
            q item = ah.a().b(itemId);
            if (item.d()) {
                item.e(itemCount);
                item.a(enchantlvl);
                if (drop.g() > 0 && i.a(100) < drop.g()) {
                    item.f(0);
                } else if (drop.h() > 0 && i.a(100) < drop.h()) {
                    item.f(2);
                }
                inventory.d(item);
                continue;
            }
            int i2 = 0;
            while (i2 < itemCount) {
                q each = ah.a().b(itemId);
                each.a(enchantlvl);
                each.n();
                if (drop.g() > 0 && i.a(100) < drop.g()) {
                    each.f(0);
                } else if (drop.h() > 0 && i.a(100) < drop.h()) {
                    item.f(2);
                }
                inventory.d(each);
                ++i2;
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public void a(t npc, aq.q hateList) {
        mobInventory = npc.y();
        if (mobInventory.c() == 0) {
            return;
        }
        hateDataList = hateList.d();
        totalHate = 0;
        for (q.a data : hateDataList) {
            cha = data.a;
            if (l1j.server.a.P == 2 && (cha instanceof z || cha instanceof ap.v)) {
                hateDataList.remove(data);
                continue;
            }
            if (cha != null && cha.fp() == npc.fp() && cha.fu().c(npc.fu()) <= l1j.server.a.Q) {
                totalHate += data.b;
                continue;
            }
            hateDataList.remove(data);
        }
        targetInventory = null;
        for (q item : mobInventory.d()) {
            block31: {
                block32: {
                    itemId = item.N();
                    if (item.f() && item.a().aP() == 2) {
                        item.c(false);
                    }
                    if (totalHate <= 0 || l1j.server.a.P == 0 && itemId != 40308) break block32;
                    randomInt = i.a(totalHate);
                    chanceHate = 0;
                    for (q.a data : hateDataList) {
                        if ((chanceHate += data.b) <= randomInt) continue;
                        cha = data.a;
                        if (cha == null) {
                            targetInventory = aq.a().a(npc.fs(), npc.ft(), npc.fp());
                            break block31;
                        }
                        targetInventory = cha.y();
                        if (itemId < 40131 || itemId > 40135) ** GOTO lbl-1000
                        if (!(cha instanceof ap.u) || hateDataList.size() > 1) {
                            targetInventory = null;
                        } else {
                            pc = (ap.u)cha;
                            if (pc.bb().a(10) != 1) {
                                targetInventory = null;
                            } else if (targetInventory.a(item, item.E()) != 0) {
                                targetInventory = aq.a().a(cha.fs(), cha.ft(), cha.fp());
                            } else if (cha instanceof ap.u) {
                                pc = (ap.u)cha;
                                adenaCount = pc.j().g(40308);
                                if (adenaCount + (long)item.E() > 2000000000L) {
                                    targetInventory = aq.a().a(pc.fs(), pc.ft(), pc.fp());
                                    pc.a(new ei("\\aG\u6240\u6301\u6709\u7684\u91d1\u5e63\u8d85\u904e\u4e862000000000\u4e0a\u9650"));
                                } else if (pc.q()) {
                                    if (pc.cz() == 1 || pc.cz() == 5) {
                                        partySize = 0;
                                        memberItemCount = 0;
                                        for (ap.u member : pc.aL().c()) {
                                            if (member == null || member.fp() != npc.fp() || member.ea() <= 0 || member.eX()) continue;
                                            ++partySize;
                                        }
                                        if (partySize > 1 && item.E() >= partySize) {
                                            memberItemCount = item.E() / partySize;
                                            for (ap.u member : pc.aL().c()) {
                                                if (member == null || member.fp() != npc.fp() || member.ea() <= 0 || member.eX()) continue;
                                                memberItem = ah.a(member, itemId, memberItemCount, 0, false);
                                                for (ap.u element : pc.aL().c()) {
                                                    element.a(new ds(813, new String[]{npc.et(), memberItem.s(), member.et()}));
                                                }
                                            }
                                            mobInventory.b(item, item.E());
                                        } else {
                                            for (ap.u member : pc.aL().c()) {
                                                member.a(new ds(813, new String[]{npc.et(), item.s(), pc.et()}));
                                            }
                                        }
                                    } else {
                                        for (ap.u element : pc.aL().c()) {
                                            element.a(new ds(813, new String[]{npc.et(), item.s(), pc.et()}));
                                        }
                                    }
                                } else {
                                    pc.a(new ds(143, new String[]{npc.et(), item.s()}));
                                }
                            }
                        }
                        break block31;
                    }
                    break block31;
                }
                x = 0;
                y = 0;
                v0 = new int[8][];
                v1 = new int[2];
                v1[1] = -1;
                v0[0] = v1;
                v0[1] = new int[]{1, -1};
                v2 = new int[2];
                v2[0] = 1;
                v0[2] = v2;
                v0[3] = new int[]{1, 1};
                v3 = new int[2];
                v3[1] = 1;
                v0[4] = v3;
                v0[5] = new int[]{-1, 1};
                v4 = new int[2];
                v4[0] = -1;
                v0[6] = v4;
                v0[7] = new int[]{-1, -1};
                DIR_TABLE = v0;
                k = 0;
                while (k < 8) {
                    dir = i.a(8);
                    if (npc.fq().b(npc.fs(), npc.ft(), dir)) {
                        x = DIR_TABLE[dir][0];
                        y = DIR_TABLE[dir][1];
                        break;
                    }
                    ++k;
                }
                targetInventory = aq.a().a(npc.fs() + x, npc.ft() + y, npc.fp());
            }
            if (targetInventory == null) {
                mobInventory.b(item, item.E());
                continue;
            }
            mobInventory.a(item, item.E(), targetInventory);
        }
        npc.fg();
    }
}

