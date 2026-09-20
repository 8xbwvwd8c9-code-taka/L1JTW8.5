/*
 * Decompiled with CFR 0.152.
 */
package l1r.aj;

import java.util.concurrent.ConcurrentHashMap;
import l1r.aj.ClientBasePacket;
import l1r.ao.AccountTable;
import l1r.ao.ClanMembersTable;
import l1r.ao.ClanTable;
import l1r.ao.HistoryTable;
import l1r.ao.ItemTable;
import l1r.ao.LuckyDrawTable;
import l1r.ao.ResolventTable;
import l1r.ao.ShopWorldTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.be.S_CharEvent;
import l1r.be.S_Pledge;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_RetrieveList;
import l1r.be.S_ServerMessage;
import l1r.bh.L1Account;
import l1r.bj.ClientThread;

public class C_ShopWorld
extends ClientBasePacket {
    public C_ShopWorld(byte[] decrypt, ClientThread client) {
        super(decrypt);
        L1PcInstance pc = client.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int action = this.c();
        block0 : switch (action) {
            case 10: {
                int size = this.b();
                int i = 0;
                while (i < size) {
                    int index = this.b();
                    int itemCount = this.b();
                    ConcurrentHashMap<Integer, L1ItemInstance> whList = ShopWorldTable.a().a(client.a());
                    L1ItemInstance item = whList.get(index);
                    if (pc.j().a(item, itemCount) != 0) break block0;
                    pc.j().d(item);
                    pc.a(new S_ServerMessage(403, item.s()));
                    ShopWorldTable.a().a(client.a(), index);
                    HistoryTable.a().h(pc, "\u9818\u53d6", item, itemCount);
                    ++i;
                }
                break;
            }
            case 11: {
                if (pc.cN()) break;
                pc.a(new S_CharEvent(ShopWorldTable.a().b()));
                pc.a(new S_ProtoBuffers(LuckyDrawTable.a().c(client.a()), 0));
                pc.x(true);
                break;
            }
            case 13: {
                int npcobjid = this.b();
                int itemobjid = this.b();
                int itemobjid2 = this.b();
                L1NpcInstance npc = (L1NpcInstance)L1World.a().a(npcobjid);
                L1ItemInstance resolvent = pc.j().e(itemobjid);
                if (resolvent == null) {
                    return;
                }
                int crystalCount = ResolventTable.a().a(resolvent);
                L1ItemInstance subitem = pc.j().e(itemobjid2);
                if (subitem != null && subitem.N() == 640343) {
                    crystalCount = (int)((double)crystalCount * 1.5);
                    pc.j().b(subitem, 1);
                }
                ItemTable.a(pc, 640341, crystalCount, npc.T());
                pc.j().b(resolvent, 1);
                break;
            }
            case 9: {
                ConcurrentHashMap<Integer, L1ItemInstance> whList = ShopWorldTable.a().a(client.a());
                if (whList.isEmpty()) {
                    pc.a(new S_ServerMessage(2746));
                    return;
                }
                pc.a(new S_RetrieveList(whList));
                break;
            }
            case 8: {
                int unknow = this.b();
                int itemid = this.b();
                int sysID = this.b();
                int count = this.b();
                int price = this.b();
                int unknow2 = this.b();
                int type = this.c();
                if (type == 0) {
                    ShopWorldTable.L1R_b shopData = ShopWorldTable.a().b().get(itemid);
                    L1ItemInstance item = shopData.a;
                    L1Account account = client.e();
                    if (price != shopData.b * count) {
                        pc.a(new S_ServerMessage(156));
                        return;
                    }
                    if (item == null || item.m() != sysID) {
                        pc.a(new S_ServerMessage(156));
                        return;
                    }
                    int adena = account.p();
                    if (adena < price) {
                        pc.a(new S_ServerMessage(2742));
                        return;
                    }
                    if (pc.aK().e().h() < shopData.d) {
                        pc.a(new S_ServerMessage(2743));
                        return;
                    }
                    account.g(adena - price);
                    AccountTable.a().a(account);
                    pc.a(new S_ServerMessage(2745));
                    pc.a(new S_CharEvent(37, account.p()));
                    pc.bd(pc.cQ() + price);
                    ShopWorldTable.a().a(account.d(), item.N(), count);
                    HistoryTable.a().h(pc, "\u8cfc\u8cb7", item, count);
                    break;
                }
                if (type == 1) {
                    String shopData = this.g();
                    break;
                }
                if (type != 1) break;
                String shopData = this.g();
                break;
            }
            case 15: {
                String announce = this.g();
                L1Clan clan = ClanTable.a().a(pc.aF());
                clan.f(announce);
                ClanTable.a().b(clan);
                break;
            }
            case 16: {
                String notes = this.g();
                if (notes == null) {
                    notes = "";
                }
                ClanMembersTable.a().a(pc, notes);
                pc.a(new S_Pledge(pc.et(), notes));
            }
        }
    }

    @Override
    public String a() {
        return "C_ShopWorld";
    }
}
