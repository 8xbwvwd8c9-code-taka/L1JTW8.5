/*
 * Decompiled with CFR 0.152.
 */
package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.DropMapTable;
import l1r.ao.ItemTable;
import l1r.ao.LostPowerItemTable;
import l1r.ao.MapsTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1HateList;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Drop;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

public class DropTable {
    private static final Logger a = Logger.getLogger(DropTable.class.getName());
    private static DropTable b;
    private final HashMap<Integer, ArrayList<L1Drop>> c = this.b();

    public static DropTable a() {
        if (b == null) {
            b = new DropTable();
        }
        return b;
    }

    private DropTable() {
    }

    private HashMap<Integer, ArrayList<L1Drop>> b() {
        HashMap<Integer, ArrayList<L1Drop>> droplistMap;
        block7: {
            droplistMap = new HashMap<Integer, ArrayList<L1Drop>>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = DatabaseFactory.a().b();
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
                        L1Drop drop = new L1Drop(mobId, itemId = rs.getInt("itemId"), min = rs.getInt("min"), max = rs.getInt("max"), chance = rs.getInt("chance"), enchantlvl = rs.getInt("enchantlvl"), blesschance = rs.getInt("bless_change"), unblesschance = rs.getInt("unbless_change"));
                        ArrayList<L1Drop> dropList = droplistMap.get(drop.e());
                        if (dropList == null) {
                            dropList = new ArrayList();
                            droplistMap.put(new Integer(drop.e()), dropList);
                        }
                        dropList.add(drop);
                    }
                }
                catch (SQLException e) {
                    a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                    SQLUtil.a(rs, pstm, con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                SQLUtil.a(rs, pstm, con);
                throw throwable;
            }
            SQLUtil.a(rs, pstm, con);
        }
        return droplistMap;
    }

    public void a(L1NpcInstance npc, L1Inventory inventory) {
        double adenarate;
        DropMapTable.a().a(npc);
        int mobId = npc.U_().b();
        List dropList = this.c.get(mobId);
        if (dropList == null) {
            return;
        }
        double droprate = Config.F;
        if (droprate <= 0.0) {
            droprate = 0.0;
        }
        if ((adenarate = Config.E) <= 0.0) {
            adenarate = 0.0;
        }
        if (droprate <= 0.0 && adenarate <= 0.0) {
            return;
        }
        for (L1Drop drop : dropList) {
            int itemId = drop.b();
            if (adenarate == 0.0 && itemId == 40308) continue;
            int randomChance = Random.a(1000000) + 1;
            double rateOfMapId = MapsTable.a().b(npc.fp());
            LostPowerItemTable.a().a(itemId, (double)drop.a() * droprate * rateOfMapId, inventory);
            if (droprate == 0.0 || (double)drop.a() * droprate * rateOfMapId < (double)randomChance) continue;
            int itemCount = drop.d();
            int addCount = drop.c() - drop.d() + 1;
            if (addCount > 1) {
                itemCount += Random.a(addCount);
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
            L1ItemInstance item = ItemTable.a().b(itemId);
            if (item.d()) {
                item.e(itemCount);
                item.a(enchantlvl);
                if (drop.g() > 0 && Random.a(100) < drop.g()) {
                    item.f(0);
                } else if (drop.h() > 0 && Random.a(100) < drop.h()) {
                    item.f(2);
                }
                inventory.d(item);
                continue;
            }
            int i = 0;
            while (i < itemCount) {
                L1ItemInstance each = ItemTable.a().b(itemId);
                each.a(enchantlvl);
                each.n();
                if (drop.g() > 0 && Random.a(100) < drop.g()) {
                    each.f(0);
                } else if (drop.h() > 0 && Random.a(100) < drop.h()) {
                    item.f(2);
                }
                inventory.d(each);
                ++i;
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    public void a(L1NpcInstance npc, L1HateList hateList) {
        mobInventory = npc.y();
        if (mobInventory.c() == 0) {
            return;
        }
        hateDataList = hateList.d();
        totalHate = 0;
        for (L1HateList.L1R_a data : hateDataList) {
            cha = data.a;
            if (Config.P == 2 && (cha instanceof L1SummonInstance || cha instanceof L1PetInstance)) {
                hateDataList.remove(data);
                continue;
            }
            if (cha != null && cha.fp() == npc.fp() && cha.fu().c(npc.fu()) <= Config.Q) {
                totalHate += data.b;
                continue;
            }
            hateDataList.remove(data);
        }
        targetInventory = null;
        for (L1ItemInstance item : mobInventory.d()) {
            block31: {
                block32: {
                    itemId = item.N();
                    if (item.f() && item.a().aP() == 2) {
                        item.c(false);
                    }
                    if (totalHate <= 0 || Config.P == 0 && itemId != 40308) break block32;
                    randomInt = Random.a(totalHate);
                    chanceHate = 0;
                    for (L1HateList.L1R_a data : hateDataList) {
                        if ((chanceHate += data.b) <= randomInt) continue;
                        cha = data.a;
                        if (cha == null) {
                            targetInventory = L1World.a().a(npc.fs(), npc.ft(), npc.fp());
                            break block31;
                        }
                        targetInventory = cha.y();
                        if (itemId < 40131 || itemId > 40135) ** GOTO lbl-1000
                        if (!(cha instanceof L1PcInstance) || hateDataList.size() > 1) {
                            targetInventory = null;
                        } else {
                            pc = (L1PcInstance)cha;
                            if (pc.bb().a(10) != 1) {
                                targetInventory = null;
                            } else if (targetInventory.a(item, item.E()) != 0) {
                                targetInventory = L1World.a().a(cha.fs(), cha.ft(), cha.fp());
                            } else if (cha instanceof L1PcInstance) {
                                pc = (L1PcInstance)cha;
                                adenaCount = pc.j().g(40308);
                                if (adenaCount + (long)item.E() > 2000000000L) {
                                    targetInventory = L1World.a().a(pc.fs(), pc.ft(), pc.fp());
                                    pc.a(new S_SystemMessage("\\aG\u6240\u6301\u6709\u7684\u91d1\u5e63\u8d85\u904e\u4e862000000000\u4e0a\u9650"));
                                } else if (pc.q()) {
                                    if (pc.cz() == 1 || pc.cz() == 5) {
                                        partySize = 0;
                                        memberItemCount = 0;
                                        for (L1PcInstance member : pc.aL().c()) {
                                            if (member == null || member.fp() != npc.fp() || member.ea() <= 0 || member.eX()) continue;
                                            ++partySize;
                                        }
                                        if (partySize > 1 && item.E() >= partySize) {
                                            memberItemCount = item.E() / partySize;
                                            for (L1PcInstance member : pc.aL().c()) {
                                                if (member == null || member.fp() != npc.fp() || member.ea() <= 0 || member.eX()) continue;
                                                memberItem = ItemTable.a(member, itemId, memberItemCount, 0, false);
                                                for (L1PcInstance element : pc.aL().c()) {
                                                    element.a(new S_ServerMessage(813, new String[]{npc.et(), memberItem.s(), member.et()}));
                                                }
                                            }
                                            mobInventory.b(item, item.E());
                                        } else {
                                            for (L1PcInstance member : pc.aL().c()) {
                                                member.a(new S_ServerMessage(813, new String[]{npc.et(), item.s(), pc.et()}));
                                            }
                                        }
                                    } else {
                                        for (L1PcInstance element : pc.aL().c()) {
                                            element.a(new S_ServerMessage(813, new String[]{npc.et(), item.s(), pc.et()}));
                                        }
                                    }
                                } else {
                                    pc.a(new S_ServerMessage(143, new String[]{npc.et(), item.s()}));
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
                    dir = Random.a(8);
                    if (npc.fq().b(npc.fs(), npc.ft(), dir)) {
                        x = DIR_TABLE[dir][0];
                        y = DIR_TABLE[dir][1];
                        break;
                    }
                    ++k;
                }
                targetInventory = L1World.a().a(npc.fs() + x, npc.ft() + y, npc.fp());
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
