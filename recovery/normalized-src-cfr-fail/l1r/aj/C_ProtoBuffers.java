/*
 * Decompiled with CFR 0.152.
 */
package l1r.aj;

import a.g;
import a.s;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aj.C_CreateChar;
import l1r.aj.ClientBasePacket;
import l1r.am.MonsterListReader;
import l1r.an.PBMessageALL;
import l1r.an.PBMessageALL2;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL4;
import l1r.an.PBMessageALL5;
import l1r.an.PBMessageALL6;
import l1r.ao.CastleTable;
import l1r.ao.CharacterEquipment;
import l1r.ao.CharacterMobsTable;
import l1r.ao.ClanTable;
import l1r.ao.CraftListTable;
import l1r.ao.ExpTable;
import l1r.ao.ItemTable;
import l1r.ao.LuckyDrawTable;
import l1r.ao.RankingTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Alchemy;
import l1r.aq.L1Character;
import l1r.aq.L1Chat;
import l1r.aq.L1Clan;
import l1r.aq.L1Craft;
import l1r.aq.L1ExcludingList;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.bc.ShiftLoginTimer;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_ServerVersion;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;
import l1r.bf.L1SkillExecutor;
import l1r.bh.L1QuestNew;
import l1r.bi.CalcInitHpMp;
import l1r.bi.CalcStat;
import l1r.bi.LineageUtil;
import l1r.bi.Random;
import l1r.bj.ClientThread;
import l1r.l1j.server.Config;

public class C_ProtoBuffers
extends ClientBasePacket {
    private static final Logger a = Logger.getLogger(C_ProtoBuffers.class.getName());

    public C_ProtoBuffers(byte[] decrypt, ClientThread client) {
        block139: {
            super(decrypt);
            int type = this.d();
            L1PcInstance pc = client.f();
            try {
                if (type == 115) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL3.L1R_c msg = PBMessageALL3.L1R_c.a(data);
                    String text = new String(msg.r().e(), Config.k);
                    String account = text.split("-")[0];
                    String name = text.split("-")[1];
                    client.a(new S_ProtoBuffers(116));
                    new ShiftLoginTimer(client, account, name).a(3000L);
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
                    PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                    if (msg.p() == 12) {
                        if (pc.j().b(40308, 1000)) {
                            L1Teleport.a(pc, 32630, 32776, 4, 5, true);
                        } else {
                            pc.a(new S_ServerMessage(189));
                        }
                    }
                    break block139;
                }
                if (type == 135) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                    RankingTable.a().a(pc, msg.p());
                    break block139;
                }
                if (type == 514) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL5.L1R_i msg = PBMessageALL5.L1R_i.a(data);
                    int chatCount = msg.p();
                    int chatType = msg.r();
                    String chatText = new String(msg.t().e(), Config.k);
                    String targetName = new String(msg.x().e(), Config.k);
                    L1Chat.a(pc, chatType, chatText, chatCount, targetName);
                    break block139;
                }
                if (type == 524) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                    int questID = msg.p();
                    L1QuestNew qn = pc.dS().get(questID);
                    if (qn != null && !qn.w()) {
                        int i2 = 0;
                        while (i2 < qn.f().length) {
                            ItemTable.a(pc, qn.f()[i2], qn.g()[i2], qn.h()[i2]);
                            ++i2;
                        }
                        if (msg.q()) {
                            int idx = msg.r();
                            ItemTable.a(pc, qn.i()[idx], qn.j()[idx], qn.k()[idx]);
                        }
                        if (qn.l() > 0) {
                            double exppenalty = ExpTable.d(pc.ev());
                            pc.x((int)((double)qn.l() * exppenalty));
                        }
                        qn.a(true);
                        pc.a(new S_ProtoBuffers(525, questID));
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
                    PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                    int questID = msg.p();
                    L1QuestNew qn = pc.dS().get(questID);
                    if (qn != null && qn.e().length > 0) {
                        L1Teleport.a(pc, qn.e()[0], qn.e()[1], qn.e()[2], 5, true);
                    }
                    break block139;
                }
                if (type == 543) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL.L1R_c msg = PBMessageALL.L1R_c.a(data);
                    int type1 = msg.p();
                    int type2 = msg.r();
                    int size = msg.t();
                    L1ExcludingList exList = pc.cd();
                    if (type1 == 0) {
                        pc.a(new S_PacketBox(17, exList.a(), 0));
                        pc.a(new S_PacketBox(17, exList.a(), 1));
                        break block139;
                    }
                    if (type1 == 1) {
                        int i3 = 0;
                        while (i3 < size) {
                            String name = new String(msg.a(i3).e(), Config.k);
                            if (exList.b()) {
                                pc.a(new S_ServerMessage(472));
                                break block139;
                            }
                            if (!exList.c(name)) {
                                exList.a(name);
                                pc.a(new S_PacketBox(18, 0, name));
                                pc.a(new S_PacketBox(18, 1, name));
                            }
                            ++i3;
                        }
                        break block139;
                    }
                    if (type1 == 2) {
                        int i4 = 0;
                        while (i4 < size) {
                            String name = new String(msg.a(i4).e(), Config.k);
                            exList.b(name);
                            pc.a(new S_PacketBox(19, 0, name));
                            pc.a(new S_PacketBox(19, 1, name));
                            ++i4;
                        }
                    }
                    break block139;
                }
                if (type == 563) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                    int number = msg.p();
                    PBMessageALL4.L1R_i msg15 = MonsterListReader.a().a(number);
                    for (g bs : msg15.o()) {
                        PBMessageALL.L1R_a msg1 = PBMessageALL.L1R_a.a(bs);
                        int id = msg1.r();
                        int count = msg1.t();
                        if (id == 0) {
                            double exppenalty = ExpTable.d(pc.ev());
                            pc.x((int)((double)count * exppenalty));
                            continue;
                        }
                        if (id == 16667) {
                            if (!pc.bB(4092)) {
                                pc.F(5);
                            }
                            pc.j(4092, 1800000);
                            pc.a(new S_ProtoBuffers(4092, 1800, 8, 6841, 0, 1426, 0, 0, 1));
                            pc.a(new S_SkillSound(pc.fr(), 14102));
                            pc.b(new S_SkillSound(pc.fr(), 14102));
                            continue;
                        }
                        if (id == 15815) {
                            int[] skills;
                            int[] nArray = skills = new int[]{42, 79, 158, 159, 160, 175, 206, 211, 216, 115, 148};
                            int n2 = skills.length;
                            int n3 = 0;
                            while (n3 < n2) {
                                int skillid = nArray[n3];
                                L1SkillExecutor executor = LineageUtil.a(skillid);
                                executor.a((L1Character)pc, 0);
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
                        ItemTable.a(pc, itemid, count);
                    }
                    int achievementIDX = number;
                    if (achievementIDX >= 1700) {
                        achievementIDX -= 30;
                    }
                    pc.dR()[achievementIDX - 1] = 1;
                    pc.a(new S_ProtoBuffers(564, 0, number));
                    CharacterMobsTable.a().a(pc);
                    break block139;
                }
                if (type == 565) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL.L1R_c msg = PBMessageALL.L1R_c.a(data);
                    int[] loc = MonsterListReader.a().b(msg.p());
                    if (loc == null) {
                        pc.a(new S_SystemMessage("\u932f\u8aa4\u7684\u50b3\u9001\u7de8\u865f:0x" + LineageUtil.a(msg.p(), 4)));
                        return;
                    }
                    if (!pc.fq().i() || pc.bB(230) || pc.eX()) {
                        pc.a(new S_ServerMessage(276));
                        return;
                    }
                    if (!pc.j().b(140100, 1)) {
                        pc.a(new S_ServerMessage(4692, "$5096"));
                        return;
                    }
                    if (loc[2] >= 100 && loc[2] <= 111) {
                        return;
                    }
                    L1Teleport.a(pc, loc[0], loc[1], loc[2], 5, true);
                    break block139;
                }
                if (type == 569) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                    int itemobjid = msg.p();
                    L1ItemInstance selectItem = pc.j().e(itemobjid);
                    if (selectItem == null || selectItem.f() && !selectItem.a().aN()) {
                        pc.a(new S_ServerMessage(79));
                        return;
                    }
                    if (selectItem.F() >= 128) {
                        pc.a(new S_ServerMessage(2124));
                        return;
                    }
                    selectItem.f(selectItem.F() + 128);
                    pc.j().j(selectItem);
                    break block139;
                }
                if (type == 801) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                    if (pc.bB(26004)) {
                        pc.a(new S_ProtoBuffers(800, pc.dV()));
                        return;
                    }
                    pc.j(26004, 2000);
                    if (msg.o()) {
                        if (msg.p() == 0) {
                            pc.dT().clear();
                            for (L1ItemInstance item : pc.j().d()) {
                                if (!item.D()) continue;
                                pc.dT().add(item.fr());
                            }
                        } else if (msg.p() == 1) {
                            pc.dU().clear();
                            for (L1ItemInstance item : pc.j().d()) {
                                if (!item.D()) continue;
                                pc.dU().add(item.fr());
                            }
                        }
                        pc.bu(msg.p());
                        CharacterEquipment.a().a(pc);
                    }
                    if (msg.q()) {
                        pc.j().j();
                        ArrayList<Integer> equipList = msg.r() == 0 ? pc.dT() : pc.dU();
                        for (int id : equipList) {
                            pc.j().k(id);
                        }
                        pc.bu(msg.r());
                        pc.a(new S_ProtoBuffers(800, msg.r()));
                    }
                    break block139;
                }
                if (type == 811) {
                    int dataLength = this.d();
                    byte[] data = this.a(dataLength);
                    PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                    int line = msg.p();
                    int choice = msg.r();
                    if (pc.dY() == null || pc.dY()[line * 3][3] == 5) {
                        pc.a(new S_ServerMessage(79));
                        return;
                    }
                    int exp = 0;
                    if (choice == 1) {
                        exp = 721306;
                        ItemTable.a(pc, 640941, 1);
                    } else if (choice == 2 && pc.j().b(640938, 1)) {
                        exp = 7213060;
                        ItemTable.a(pc, 640941, 5);
                    } else if (choice == 3 && pc.j().b(640939, 1)) {
                        exp = 16229385;
                        ItemTable.a(pc, 640769, 1);
                    }
                    if (exp > 0) {
                        double exppenalty = ExpTable.d(pc.ev());
                        pc.x((int)((double)exp * exppenalty));
                    }
                    pc.dY()[line * 3][3] = 5;
                    pc.a(new S_ProtoBuffers(814, line, 5));
                } else if (type == 820) {
                    client.a(new S_ServerVersion());
                } else if (type != 1002) {
                    if (type == 54) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        byte[] true_data = this.a(Config.aR);
                        if (pc.aK().j() || Arrays.equals(data, true_data)) {
                            pc.a(new S_ProtoBuffers(55, 3));
                        } else {
                            System.out.println("\u66f4\u65b0\u9053\u5177\u6e05\u55ae\u9a57\u8b49(" + pc.eu() + ")");
                            pc.a(new S_ProtoBuffers(55, 0));
                            ArrayList<L1Craft> list = CraftListTable.a().b();
                            for (L1Craft craft : list) {
                                pc.a(new S_ProtoBuffers(55, craft, 1));
                            }
                            pc.a(new S_ProtoBuffers(55, 2));
                            pc.aK().a(true);
                        }
                    } else if (type == 56) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        int objid = 0;
                        PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                        objid = msg.p();
                        L1Object obj = L1World.a().a(objid);
                        if (obj instanceof L1NpcInstance) {
                            L1NpcInstance npc = (L1NpcInstance)obj;
                            String[] keys = npc.F();
                            if (keys.length == 0) {
                                return;
                            }
                            pc.a(new S_ProtoBuffers(57, keys));
                        }
                    } else if (type == 92) {
                        pc.a(new S_ProtoBuffers(93, 33));
                    } else if (type == 58) {
                        int enchant;
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        PBMessageALL5.L1R_a msg = PBMessageALL5.L1R_a.a(data);
                        L1Craft craft = CraftListTable.a().a(msg.r());
                        L1ItemInstance addchanceitem = craft.f();
                        int counts = msg.t();
                        ArrayList<L1ItemInstance> trueMaterialList = new ArrayList<L1ItemInstance>();
                        block13: for (g bs : msg.u()) {
                            PBMessageALL3.L1R_e msg7 = PBMessageALL3.L1R_e.a(bs);
                            int systemid = msg7.r();
                            enchant = msg7.v();
                            if (addchanceitem != null && addchanceitem.m() == systemid) continue;
                            block14: for (L1ItemInstance item : craft.g().values()) {
                                if (item.m() == systemid && item.G() == enchant) {
                                    trueMaterialList.add(item);
                                    continue block13;
                                }
                                for (L1ItemInstance exchange : craft.h().get(item.N())) {
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
                        for (L1ItemInstance item : trueMaterialList) {
                            if (!pc.j().b(item.N(), item.G(), item.E() * counts, item.F())) {
                                a.log(Level.SEVERE, "Item Craft has Consume Error with [" + pc.et() + "] craft id=" + craft.a());
                                return;
                            }
                            if (item.N() != 40308) continue;
                            int castleId = Random.a(4) + 1;
                            CastleTable.a().a(castleId, item.E() * counts);
                        }
                        int chance = craft.l();
                        if (addchanceitem != null) {
                            for (g bs : msg.u()) {
                                PBMessageALL5.L1R_a msg2 = PBMessageALL5.L1R_a.a(bs);
                                if (msg2.r() != addchanceitem.m()) continue;
                                if (!pc.j().b(addchanceitem.N(), msg2.t() * counts)) break;
                                chance += msg2.t() * counts;
                                break;
                            }
                        }
                        if (Random.a(100) < chance) {
                            L1ItemInstance craft_item = craft.b();
                            int itemid = craft_item.N();
                            int count = craft_item.E() * counts;
                            enchant = craft_item.G();
                            int bless = craft_item.F();
                            if (craft.c() > 0 && Random.a(100) < craft.c()) {
                                bless = 0;
                                pc.a(new S_SkillSound(pc.fr(), 2047));
                                pc.b(new S_SkillSound(pc.fr(), 2047));
                                if (craft.c() < 10) {
                                    L1World.a().a(new S_ServerMessage(3599, "$227 " + craft_item.a().j(), enchant));
                                }
                            }
                            if (craft.l() <= 5) {
                                pc.a(new S_SkillSound(pc.fr(), 2047));
                                pc.b(new S_SkillSound(pc.fr(), 2047));
                                L1World.a().a(new S_ServerMessage(3599, craft_item.a().j(), enchant));
                            }
                            ItemTable.a(pc, itemid, count, enchant, bless, true);
                            pc.a(new S_ProtoBuffers(59, craft, craft_item, 0));
                        } else {
                            L1ItemInstance failitem = craft.e();
                            if (failitem != null) {
                                ItemTable.a(pc, failitem.N(), failitem.E() * counts);
                            }
                            pc.a(new S_ProtoBuffers(59, craft, craft.b(), 1));
                        }
                    } else if (type == 317) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                        int action = msg.p();
                        if (action == 1) {
                            pc.a(new S_ProtoBuffers(318, CastleTable.a().b()));
                        }
                    } else if (type == 319) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                        int id2 = msg.r();
                        pc.a(new S_ProtoBuffers(320, pc.fr(), id2));
                        pc.b(new S_ProtoBuffers(320, pc.fr(), id2));
                    } else if (type == 332) {
                        if (pc.aF() > 0) {
                            pc.a(new S_ProtoBuffers(333, pc));
                        }
                    } else if (type == 326) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        PBMessageALL4.L1R_g msg = PBMessageALL4.L1R_g.a(data);
                        int joinTypeOpen = msg.p();
                        int joinType = msg.r();
                        byte[] code = msg.t().e();
                        L1Clan clan = ClanTable.a().a(pc.aF());
                        if (clan != null) {
                            clan.j(joinTypeOpen);
                            clan.k(joinType);
                            clan.a(code);
                        }
                        pc.a(new S_ProtoBuffers(327, pc));
                    } else if (type == 338) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
                        int objid = msg.p();
                        int markid = msg.r();
                        if (pc.q()) {
                            for (L1PcInstance member : pc.aL().c()) {
                                member.a(new S_ProtoBuffers(339, objid, markid));
                            }
                        }
                    } else if (type == 100) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        PBMessageALL2.L1R_c msg = PBMessageALL2.L1R_c.a(data);
                        ItemTable.a(pc, 640106, msg.r());
                        for (int key : msg.q()) {
                            LuckyDrawTable.a().a(client.a(), key);
                        }
                        pc.a(new S_ProtoBuffers(LuckyDrawTable.a().c(client.a()), 0));
                        pc.a(new S_ServerMessage(3728, msg.r()));
                    } else if (type == 122) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        byte[] true_data = this.a(Config.aS);
                        pc.a(new S_ProtoBuffers(128));
                        if (Arrays.equals(data, true_data)) {
                            pc.a(new S_ProtoBuffers(123, 3));
                        } else {
                            System.out.println("\u66f4\u65b0\u9b54\u6cd5\u5a03\u5a03\u5408\u6210\u6e05\u55ae\u9a57\u8b49(" + pc.eu() + ")");
                            pc.a(new S_ProtoBuffers(123, 0));
                            L1Alchemy.a().a(pc);
                            L1Alchemy.a().b(pc);
                            L1Alchemy.a().c(pc);
                            pc.a(new S_ProtoBuffers(123, 2));
                        }
                    } else if (type == 124) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        ArrayList<L1ItemInstance> materialList = new ArrayList<L1ItemInstance>();
                        PBMessageALL6.L1R_g msg = PBMessageALL6.L1R_g.a(data);
                        int level = msg.p();
                        for (g bs : msg.q()) {
                            PBMessageALL.L1R_a msg1 = PBMessageALL.L1R_a.a(bs);
                            int index = msg1.p();
                            int systemid = msg1.r();
                            int objid = msg1.t();
                            L1ItemInstance finditem = pc.j().e(objid);
                            if (finditem == null) {
                                a.log(Level.SEVERE, "\u9b54\u6cd5\u5a03\u5a03-\u5408\u6210:itemobjid= " + objid + " is Null");
                                return;
                            }
                            materialList.add(finditem);
                        }
                        L1Alchemy.a().a(pc, level, materialList);
                    } else if (type == 460) {
                        pc.a(new S_ProtoBuffers(461, pc));
                    } else if (type == 484) {
                        int dataLength = this.d();
                        byte[] data = this.a(dataLength);
                        PBMessageALL.L1R_a msg = PBMessageALL.L1R_a.a(data);
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
                            client.b(new L1PcInstance());
                        }
                        L1PcInstance dummy = client.h();
                        dummy.ad(classType);
                        dummy.i(C_CreateChar.a[classType]);
                        dummy.o(str - dummy.bf());
                        dummy.q(dex - dummy.bh());
                        dummy.p(con - dummy.bg());
                        dummy.t(wis - dummy.bk());
                        dummy.s(intel - dummy.bj());
                        dummy.r(cha - dummy.bi());
                        if (str > 0) {
                            int calcDmg = CalcStat.a(dummy.bf(), dummy.ez());
                            int calcHit = CalcStat.b(dummy.bf(), dummy.ez());
                            int calcCritical = CalcStat.c(dummy.bf(), dummy.ez());
                            if (mode != 16 || uk5 == 16) {
                                client.a(new S_ProtoBuffers(mode * 2, "str", calcDmg, calcHit, calcCritical, (int)dummy.K()));
                            }
                        }
                        if (intel > 0) {
                            int calcMagicDmg = CalcStat.g(dummy.bj(), dummy.eD());
                            int calcMagicHit = CalcStat.h(dummy.bj(), dummy.eD());
                            int calcMagicCritical = CalcStat.i(dummy.bj(), dummy.eD());
                            int calcMagicBouns = CalcStat.c(dummy.eD());
                            int calcMagicDecrese = CalcStat.d(dummy.eD());
                            client.a(new S_ProtoBuffers(mode * 2, "int", calcMagicDmg, calcMagicHit, calcMagicCritical, calcMagicBouns, calcMagicDecrese));
                        }
                        if (wis > 0) {
                            int mpup = dummy.aC().j(dummy.eE());
                            int rnd = dummy.aC().k(dummy.eE());
                            int calcMpr = CalcStat.m(dummy.bk(), dummy.eE());
                            int calcPotionMpr = CalcStat.n(dummy.bk(), dummy.eE());
                            int calcMr = CalcStat.e(dummy.eE());
                            int calcMp = CalcInitHpMp.c(dummy) - CalcInitHpMp.b(dummy);
                            client.a(new S_ProtoBuffers(mode * 2, "wis", calcMpr, calcPotionMpr, calcMr, mpup, mpup + rnd, calcMp));
                        }
                        if (dex > 0) {
                            int calcAc = CalcStat.a(dummy.eB());
                            int calcEr = CalcStat.b(dummy.eB());
                            int calcBowDmg = CalcStat.d(dummy.bh(), dummy.eB());
                            int calcBowHit = CalcStat.e(dummy.bh(), dummy.eB());
                            int calcBowCritical = CalcStat.f(dummy.bh(), dummy.eB());
                            client.a(new S_ProtoBuffers(mode * 2, "dex", calcBowDmg, calcBowHit, calcBowCritical, calcAc, calcEr));
                        }
                        if (con > 0) {
                            int calcHpup = dummy.aC().e() + CalcStat.j(dummy.aC().a()[2], dummy.eA());
                            int calcHpr = CalcStat.k(dummy.bg(), dummy.eA());
                            int calcPotionHpr = CalcStat.l(dummy.bg(), dummy.eA());
                            if (mode != 16 || uk5 == 1) {
                                client.a(new S_ProtoBuffers(mode * 2, "con", calcHpr, calcPotionHpr, (int)dummy.K(), calcHpup, 0));
                            }
                        }
                        if (cha > 0) {
                            client.a(new S_ProtoBuffers(mode * 2, "cha", 0, 0, 0, 0));
                        }
                    } else if (type != 1002 && type == 802) {
                        pc.a(new S_ProtoBuffers(803, pc));
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
