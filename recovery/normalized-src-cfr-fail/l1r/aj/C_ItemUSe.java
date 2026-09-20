/*
 * Decompiled with CFR 0.152.
 */
package l1r.aj;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.aj.ClientBasePacket;
import l1r.ao.AccountTable;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.ao.HistoryTable;
import l1r.ao.ItemTable;
import l1r.ao.LostPowerItemTable;
import l1r.ao.LuckyDrawTable;
import l1r.ao.NpcTable;
import l1r.ao.PetTable;
import l1r.ao.ResolventTable;
import l1r.ao.SkillsTable;
import l1r.ao.SpawnTable;
import l1r.ao.TreasureBoxTable;
import l1r.ap.L1DotaInstance;
import l1r.ap.L1EffectInstance;
import l1r.ap.L1GuardianInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.aq.L1CastleLocation;
import l1r.aq.L1Character;
import l1r.aq.L1Clan;
import l1r.aq.L1Cooking;
import l1r.aq.L1Getback;
import l1r.aq.L1HouseLocation;
import l1r.aq.L1ItemQuestBuff;
import l1r.aq.L1Location;
import l1r.aq.L1Magic;
import l1r.aq.L1Object;
import l1r.aq.L1PolyMorph;
import l1r.aq.L1SpawnEffect;
import l1r.aq.L1Teleport;
import l1r.aq.L1TownLocation;
import l1r.aq.L1World;
import l1r.as.L1CastleWar;
import l1r.as.L1Dragon;
import l1r.au.L1PcInventory;
import l1r.av.L1ItemDelay;
import l1r.aw.Enchant;
import l1r.aw.FurnitureItem;
import l1r.aw.MagicDoll;
import l1r.aw.Potion;
import l1r.az.L1DamagePoison;
import l1r.bc.FishingTimer;
import l1r.be.S_AddSkill;
import l1r.be.S_AttackPacket;
import l1r.be.S_ChatPacket;
import l1r.be.S_DoActionGFX;
import l1r.be.S_Fishing;
import l1r.be.S_Html;
import l1r.be.S_IdentifyDesc;
import l1r.be.S_ItemName;
import l1r.be.S_Liquor;
import l1r.be.S_Message_YN;
import l1r.be.S_OwnCharAttrDef;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_OwnCharStatus2;
import l1r.be.S_PacketBox;
import l1r.be.S_Paralysis;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_RuneSlot;
import l1r.be.S_SPMR;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.be.S_Sound;
import l1r.be.S_SystemMessage;
import l1r.bf.L1SkillExecutor;
import l1r.bf.S_005;
import l1r.bf.S_061;
import l1r.bf.S_069;
import l1r.bf.S_075;
import l1r.bh.L1Account;
import l1r.bh.L1BookMark;
import l1r.bh.L1Item;
import l1r.bh.L1Npc;
import l1r.bh.L1Pet;
import l1r.bh.L1QuestNew;
import l1r.bh.L1Skills;
import l1r.bi.GeneralThreadPool;
import l1r.bi.LineageUtil;
import l1r.bi.Point;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.bj.ClientThread;
import l1r.l1j.server.DatabaseFactory;

public class C_ItemUSe
extends ClientBasePacket {
    private static final Logger a = Logger.getLogger(C_ItemUSe.class.getName());
    private static final String b = "[C] C_ItemUSe";

    public C_ItemUSe(byte[] abyte0, ClientThread client) throws Exception {
        super(abyte0);
        L1PcInstance pc = client.f();
        if (pc == null || pc.bN() || pc.eX()) {
            return;
        }
        int itemObjid = this.b();
        L1ItemInstance l1iteminstance = pc.j().e(itemObjid);
        if (l1iteminstance == null || pc.aR() || pc.ea() <= 0) {
            return;
        }
        if (!pc.fq().p()) {
            pc.a(new S_ServerMessage(563));
            return;
        }
        if ((l1iteminstance.a().aP() == 6 || l1iteminstance.a().aP() >= 23 && l1iteminstance.a().aP() <= 27) && pc.bB(71)) {
            pc.a(new S_ServerMessage(698));
            return;
        }
        int select_itemid = 0;
        String s = "";
        int h = 0;
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
                s = this.g();
                h = this.d();
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
                if (LostPowerItemTable.a().b().containsKey(itemId)) {
                    pc.a(new S_ServerMessage(4963));
                } else {
                    pc.a(new S_ServerMessage(74, l1iteminstance.s()));
                }
                return;
            }
            case 16: 
            case 61: {
                s = this.g();
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
                int x = this.d();
                int y = this.d();
                L1BookMark book = pc.a(x, y);
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
                catch (ArrayIndexOutOfBoundsException a) {
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
                pc.a(new S_ServerMessage(3898, "" + l1iteminstance.a().aK() / 1000));
                if (use_type == 9 || use_type == 6 || use_type == 29) {
                    pc.a(new S_Paralysis(7, false));
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
                    pc.a(new S_ServerMessage(3898, "" + (delayEffect - diff)));
                    if (use_type == 9 || use_type == 6 || use_type == 29) {
                        pc.a(new S_Paralysis(7, false));
                    }
                    return;
                }
            }
        }
        L1ItemInstance selectItem = pc.j().e(select_itemid);
        int item_minlvl = l1iteminstance.a().o();
        int item_maxlvl = l1iteminstance.a().p();
        if (item_minlvl != 0 && item_minlvl > pc.ev()) {
            pc.a(new S_ServerMessage(318, String.valueOf(String.valueOf(item_minlvl))));
            if (use_type == 9 || use_type == 6 || use_type == 29) {
                pc.a(new S_Paralysis(7, false));
            }
            return;
        }
        if (item_maxlvl != 0 && item_maxlvl < pc.ev()) {
            pc.a(new S_PacketBox(12, item_maxlvl));
            if (use_type == 9 || use_type == 6 || use_type == 29) {
                pc.a(new S_Paralysis(7, false));
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
                pc.a(new S_ServerMessage(264));
            }
        } else if (l1iteminstance.f()) {
            int[] loc;
            int probability;
            boolean isPoly;
            int rnd;
            L1Object obj3;
            int gfxid;
            boolean isEquipped;
            int i;
            boolean isSuccess;
            L1Object target;
            L1SkillExecutor executor;
            if (l1iteminstance.a().aP() == 0 || l1iteminstance.a().aP() == 15) {
                pc.j().k(l1iteminstance);
                pc.a(new S_ServerMessage(452, l1iteminstance.s()));
            } else if (l1iteminstance.a().aP() == 16) {
                if (itemId == 40576 && !pc.A() || itemId == 40577 && !pc.B() || itemId == 40578 && !pc.z()) {
                    pc.a(new S_ServerMessage(264));
                    return;
                }
                if (TreasureBoxTable.a().a(itemId, pc)) {
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
                pc.a(new S_ItemName(l1iteminstance));
            } else if (l1iteminstance.a().aP() == 5) {
                int soundid = l1iteminstance.a().V();
                pc.a(new S_SkillSound(pc.fr(), soundid));
                pc.b(new S_SkillSound(pc.fr(), soundid));
                pc.j().b(l1iteminstance, 1);
            } else if (l1iteminstance.a().aP() == 7) {
                int foodvolume = l1iteminstance.a().V() / 10;
                pc.c_(Math.min(pc.fj() + foodvolume, 225));
                pc.a(new S_PacketBox(11, pc.fj()));
                pc.a(new S_ServerMessage(76, l1iteminstance.b()));
                if (itemId == 40057) {
                    pc.j(1012, 0);
                    pc.a(new S_ServerMessage(152));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (l1iteminstance.a().aP() == 17 || l1iteminstance.a().aP() == 22) {
                MagicDoll.a(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 18) {
                if (itemId == 41401) {
                    FurnitureItem.a(pc, spellsc_objid, l1iteminstance);
                } else {
                    FurnitureItem.a(pc, l1iteminstance);
                }
            } else if (l1iteminstance.a().aP() >= 23 && l1iteminstance.a().aP() <= 25) {
                Potion.a(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 26) {
                Potion.c(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 27) {
                Potion.b(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 28) {
                int skillid = l1iteminstance.a().V();
                L1SkillExecutor executor2 = LineageUtil.a(skillid);
                if (executor2.a((L1Character)pc, spellsc_objid, skillid)) {
                    executor2.a((L1Character)pc, spellsc_objid, spellsc_x, spellsc_y, null);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(281));
                }
            } else if (l1iteminstance.a().aP() == 29) {
                L1Cooking.a(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 30) {
                this.f(pc, l1iteminstance);
            } else if (l1iteminstance.a().aP() == 31) {
                if (itemId >= 41429 && itemId <= 41432 || itemId >= 640150 && itemId <= 640153) {
                    Enchant.e(pc, l1iteminstance, selectItem);
                } else if (itemId >= 640157 && itemId <= 640160) {
                    Enchant.d(pc, l1iteminstance, selectItem);
                } else {
                    Enchant.b(pc, l1iteminstance, selectItem);
                }
            } else if (l1iteminstance.a().aP() == 32) {
                Enchant.c(pc, l1iteminstance, selectItem);
            } else if (l1iteminstance.a().U() == 8) {
                executor = l1iteminstance.F() == 0 ? new S_075() : new S_061();
                executor.a((L1Character)pc, spellsc_objid, 0, 0, "res");
                pc.j().b(l1iteminstance, 1);
            } else if (l1iteminstance.a().U() == 16 || l1iteminstance.a().U() == 61) {
                if (L1PolyMorph.a(pc, s, l1iteminstance.a().V())) {
                    if (itemId == 640804) {
                        return;
                    }
                    pc.j().b(l1iteminstance, 1);
                }
            } else if (l1iteminstance.a().U() == 28) {
                this.a(pc, l1iteminstance, blanksc_skillid);
            } else if (l1iteminstance.a().U() == 6 || l1iteminstance.a().U() == 29) {
                executor = itemId == 40086 ? new S_069() : new S_005();
                executor.a((L1Character)pc, btele, 0, 0, null);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640105 || itemId == 640106) {
                int consumeCount = count * (itemId == 640105 ? 1 : 3);
                if (pc.j().b(itemId, consumeCount)) {
                    HashMap<Integer, L1ItemInstance> list = new HashMap<Integer, L1ItemInstance>();
                    int nextIndex = LuckyDrawTable.a().b(client.a());
                    int i2 = 0;
                    while (i2 < count) {
                        list.put(nextIndex + i2, LuckyDrawTable.a().a(pc.et()));
                        ++i2;
                    }
                    pc.a(new S_ProtoBuffers(list, 1));
                    LuckyDrawTable.a().a(client.a(), list);
                }
            } else if (itemId == 640382) {
                target = L1World.a().a(spellsc_objid);
                if (target instanceof L1NpcInstance) {
                    L1NpcInstance npc;
                    block767: {
                        npc = (L1NpcInstance)target;
                        Connection con = null;
                        PreparedStatement pstm = null;
                        try {
                            try {
                                con = DatabaseFactory.a().b();
                                pstm = con.prepareStatement("DELETE FROM spawnlist_npc WHERE npc_templateid=? AND locx=? AND locy=? AND mapid=?");
                                if (target instanceof L1MonsterInstance) {
                                    pstm = con.prepareStatement("DELETE FROM spawnlist WHERE npc_templateid=? AND locx=? AND locy=? AND mapid=?");
                                }
                                pstm.setInt(1, npc.z());
                                pstm.setInt(2, npc.fs());
                                pstm.setInt(3, npc.ft());
                                pstm.setInt(4, npc.fp());
                                pstm.execute();
                            }
                            catch (SQLException e) {
                                a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                                SQLUtil.a(pstm);
                                SQLUtil.a(con);
                                break block767;
                            }
                        }
                        catch (Throwable throwable) {
                            SQLUtil.a(pstm);
                            SQLUtil.a(con);
                            throw throwable;
                        }
                        SQLUtil.a(pstm);
                        SQLUtil.a(con);
                    }
                    npc.a(0, 0, 0);
                    pc.a(new S_SystemMessage("npcid:" + npc.z() + " = " + npc.U_().c()));
                }
            } else if (itemId == 640104) {
                target = L1World.a().a(spellsc_objid);
                if (target instanceof L1NpcInstance) {
                    L1NpcInstance npc;
                    block769: {
                        npc = (L1NpcInstance)target;
                        Connection con = null;
                        PreparedStatement pstm = null;
                        try {
                            try {
                                con = DatabaseFactory.a().b();
                                pstm = con.prepareStatement("UPDATE spawnlist_npc SET locx=?,locy=?,heading=? WHERE npc_templateid=? AND locx=? AND locy=?");
                                pstm.setInt(1, pc.fs());
                                pstm.setInt(2, pc.ft());
                                pstm.setInt(3, pc.fb());
                                pstm.setInt(4, npc.z());
                                pstm.setInt(5, npc.fs());
                                pstm.setInt(6, npc.ft());
                                pstm.execute();
                            }
                            catch (Exception e) {
                                a.log(Level.SEVERE, e.getLocalizedMessage(), e);
                                SQLUtil.a(pstm);
                                SQLUtil.a(con);
                                break block769;
                            }
                        }
                        catch (Throwable throwable) {
                            SQLUtil.a(pstm);
                            SQLUtil.a(con);
                            throw throwable;
                        }
                        SQLUtil.a(pstm);
                        SQLUtil.a(con);
                    }
                    npc.a(pc.fs(), pc.ft(), pc.fb());
                    pc.a(new S_SystemMessage("npcid:" + npc.z() + " = " + npc.U_().c()));
                }
            } else if (itemId == 640234) {
                if (selectItem == null) {
                    pc.a(new S_ServerMessage(156));
                    return;
                }
                if (selectItem.N() != 40314 && selectItem.N() != 40316) {
                    pc.a(new S_ServerMessage(1164));
                    return;
                }
                String name = null;
                for (L1NpcInstance petNpc : pc.ek().values()) {
                    if (!(petNpc instanceof L1PetInstance)) continue;
                    L1PetInstance pet = (L1PetInstance)petNpc;
                    if (selectItem.fr() != pet.k()) continue;
                    name = pet.et();
                    pc.am(pet.fr());
                    break;
                }
                if (name == null) {
                    pc.a(new S_ServerMessage(1301));
                    return;
                }
                pc.a(new S_Message_YN(1322, String.valueOf(name) + " "));
            } else if (itemId == 640835) {
                L1World.a().a(new S_ChatPacket(pc.et(), s));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640233) {
                L1World.a().a(new S_ChatPacket(pc.et(), s, h));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640334) {
                if (pc.cJ() >= 100) {
                    pc.a(new S_ServerMessage(1622));
                    return;
                }
                pc.ao();
                pc.a(new S_ServerMessage(1624, "" + pc.cJ()));
                pc.I();
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640144) {
                int castleId = L1CastleLocation.a(pc);
                if (castleId > 0) {
                    pc.a(new S_ServerMessage(3274));
                    return;
                }
                int spawnid = l1iteminstance.a().V();
                SpawnTable.a(spawnid, pc, 0, 300000L);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640626 && itemId <= 640637) {
                L1Dragon.a().a(l1iteminstance, pc);
            } else if (itemId == 640561) {
                if (selectItem == null) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                if (selectItem.N() >= 21340 && selectItem.N() <= 21344) {
                    pc.j().f(selectItem);
                    ItemTable.a(pc, 640562, 1);
                } else if (selectItem.N() >= 21345 && selectItem.N() <= 21349) {
                    pc.j().f(selectItem);
                    ItemTable.a(pc, 640563, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640841) {
                if (selectItem == null) {
                    pc.a(new S_ServerMessage(156));
                    return;
                }
                if (selectItem.N() < 21509 || selectItem.N() > 21511) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                if (selectItem.G() >= 9) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                Enchant.a(pc, selectItem, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640677) {
                if (selectItem == null) {
                    pc.a(new S_ServerMessage(156));
                    return;
                }
                if (selectItem.N() < 393 || selectItem.N() > 400) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                if (selectItem.G() >= 15) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                Enchant.a(pc, selectItem, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640370 && itemId <= 640372) {
                if (selectItem == null) {
                    pc.a(new S_ServerMessage(156));
                    return;
                }
                if (!selectItem.g() || selectItem.G() != l1iteminstance.a().V()) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                Enchant.b(pc, l1iteminstance, selectItem);
            } else if (itemId == 640833) {
                if (selectItem == null || selectItem.N() < 21495 || selectItem.N() > 21499) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                int success_rate = 35;
                boolean bl = isSuccess = Random.a(100) + 1 < 35;
                if (isSuccess) {
                    pc.a(new S_ServerMessage(161, selectItem.s(), "$252", "$247"));
                    L1Item next = ItemTable.a().a(selectItem.N() + 1);
                    boolean isEquipped2 = selectItem.D();
                    pc.j().a(selectItem, false);
                    selectItem.a(next);
                    pc.j().a(selectItem, isEquipped2);
                    pc.j().j(selectItem);
                } else {
                    pc.a(new S_ServerMessage(164, selectItem.s(), "$245"));
                    pc.j().f(selectItem);
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640946) {
                if (selectItem == null || !selectItem.j()) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                int success_rate = 35;
                boolean bl = isSuccess = Random.a(100) + 1 < 35;
                if (isSuccess) {
                    Enchant.a(pc, selectItem, 1);
                } else {
                    pc.a(new S_ServerMessage(164, selectItem.s(), "$245"));
                    pc.j().f(selectItem);
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640440) {
                boolean isSuccess2;
                int[] highest;
                if (selectItem == null || selectItem.N() < 21261 || selectItem.N() > 21300) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                int[] e = highest = new int[]{21270, 21280, 21290, 21300};
                int isEquipped2 = highest.length;
                int next = 0;
                while (next < isEquipped2) {
                    i = e[next];
                    if (selectItem.N() == i) {
                        pc.a(new S_ServerMessage(1453));
                        return;
                    }
                    ++next;
                }
                int success_rate = 35;
                boolean bl = isSuccess2 = Random.a(100) + 1 < 35;
                if (isSuccess2) {
                    pc.a(new S_ServerMessage(161, selectItem.s(), "$252", "$247"));
                    L1Item next2 = ItemTable.a().a(selectItem.N() + 1);
                    boolean isEquipped3 = selectItem.D();
                    pc.j().a(selectItem, false);
                    selectItem.a(next2);
                    pc.j().a(selectItem, isEquipped3);
                    pc.j().j(selectItem);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640346) {
                if (selectItem == null || selectItem.N() < 21199 || selectItem.N() > 21203) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                int success_rate = 60 - (selectItem.N() - 21198) * 11;
                boolean bl = isSuccess = Random.a(100) + 1 < success_rate;
                if (isSuccess) {
                    pc.a(new S_ServerMessage(161, selectItem.s(), "$252", "$247"));
                    L1Item next = ItemTable.a().a(selectItem.N() + 1);
                    boolean isEquipped4 = selectItem.D();
                    pc.j().a(selectItem, false);
                    selectItem.a(next);
                    pc.j().a(selectItem, isEquipped4);
                    pc.j().j(selectItem);
                } else {
                    pc.a(new S_ServerMessage(160, selectItem.s(), "$252", "$248"));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 49142) {
                pc.a(new S_SystemMessage("\\aH\u8acb\u81f3\u53e4\u9b6f\u4e01\u6d3d\u8a62\u76f8\u95dcNPC\u3002"));
            } else if (itemId == 640439) {
                if (selectItem == null || selectItem.N() < 21261 || selectItem.N() > 21300) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                Timestamp limit = new Timestamp(System.currentTimeMillis() + 86400000L);
                selectItem.b(limit);
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640386 && itemId <= 640391) {
                if (selectItem == null || selectItem.N() != 20084 && selectItem.N() != 20085 && selectItem.N() != 120085) {
                    pc.a(new S_ServerMessage(1453));
                    return;
                }
                if (selectItem.D()) {
                    pc.a(new S_ServerMessage(4357));
                    return;
                }
                int value = l1iteminstance.a().V() + (selectItem.N() == 20084 ? 6 : 0);
                pc.j().f(selectItem);
                L1ItemInstance createitem = ItemTable.a(pc, value, 1, selectItem.G(), selectItem.F(), selectItem.C(), 31);
                pc.a(new S_ServerMessage(3299));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640392 && itemId <= 640400) {
                if (selectItem == null || selectItem.N() < 21246 || selectItem.N() > 21257) {
                    pc.a(new S_ServerMessage(1453));
                    return;
                }
                int value = l1iteminstance.a().V();
                isEquipped = selectItem.D();
                pc.j().a(selectItem, false);
                selectItem.l(value);
                selectItem.w();
                pc.j().a(selectItem, isEquipped);
                pc.j().j(selectItem);
                pc.a(new S_ServerMessage(3299));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640374 && itemId <= 640379) {
                if (selectItem == null || selectItem.N() < 21152 || selectItem.N() > 21155) {
                    pc.a(new S_ServerMessage(1453));
                    return;
                }
                int value = l1iteminstance.a().V();
                isEquipped = selectItem.D();
                pc.j().a(selectItem, false);
                selectItem.l(value);
                selectItem.z();
                pc.j().a(selectItem, isEquipped);
                pc.j().j(selectItem);
                pc.a(new S_ServerMessage(3299));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640844) {
                if (selectItem == null || !selectItem.f()) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                int changeid = LostPowerItemTable.a().a(selectItem.N());
                if (changeid <= 0) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                if (Random.a(100) < 10) {
                    ItemTable.a(pc, changeid, 1, 0, false);
                    pc.a(new S_ServerMessage(4964, selectItem.b()));
                } else {
                    pc.a(new S_ServerMessage(4965, selectItem.b()));
                }
                pc.j().b(selectItem, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640345) {
                if (selectItem == null || selectItem.N() != 21204) {
                    pc.a(new S_ServerMessage(3440));
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
                pc.a(new S_ServerMessage(3439));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640348 && itemId <= 640350) {
                if (selectItem == null || selectItem.N() != 21204) {
                    pc.a(new S_ServerMessage(3440));
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
                    int rnd2 = Random.a(bits.length);
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
                pc.a(new S_ServerMessage(3439));
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640337) {
                if (!pc.j().b(41246, 20)) {
                    pc.a(new S_ServerMessage(337, "$5240"));
                    return;
                }
                target = L1World.a().a(spellsc_objid);
                if (target instanceof L1Character) {
                    L1Character cha = (L1Character)target;
                    int damage = 150 + pc.ev();
                    int gfxid2 = 13987;
                    if (Random.a(100) < 25) {
                        damage = 200 + pc.ev() + Random.a(300);
                        gfxid2 = 13989;
                    }
                    if (pc.a(pc, cha, false)) {
                        damage = 0;
                    }
                    if (!pc.i(cha.fs(), cha.ft())) {
                        damage = 0;
                    }
                    L1Magic _magic = new L1Magic(pc, cha);
                    _magic.a(damage, 0);
                    if (cha instanceof L1PcInstance) {
                        ((L1PcInstance)cha).a(new S_DoActionGFX(cha.fr(), 2));
                    }
                    cha.b(new S_DoActionGFX(cha.fr(), 2));
                    pc.a(new S_SkillSound(cha.fr(), gfxid2));
                    pc.b(new S_SkillSound(cha.fr(), gfxid2));
                }
            } else if (itemId == 640339) {
                if (!pc.j().b(41246, 20)) {
                    pc.a(new S_ServerMessage(337, "$5240"));
                    return;
                }
                target = L1World.a().a(spellsc_objid);
                if (target instanceof L1Character) {
                    L1Character cha = (L1Character)target;
                    int damage = 250 + pc.ev();
                    int gfxid3 = 13991;
                    if (Random.a(100) < 25) {
                        damage = 400 + pc.ev() + Random.a(400);
                        gfxid3 = 13993;
                    }
                    if (pc.a(pc, cha, false)) {
                        damage = 0;
                    }
                    if (!pc.i(cha.fs(), cha.ft())) {
                        damage = 0;
                    }
                    L1Magic _magic = new L1Magic(pc, cha);
                    _magic.a(damage, 0);
                    if (cha instanceof L1PcInstance) {
                        ((L1PcInstance)cha).a(new S_DoActionGFX(cha.fr(), 2));
                    }
                    cha.b(new S_DoActionGFX(cha.fr(), 2));
                    pc.a(new S_SkillSound(cha.fr(), gfxid3));
                    pc.b(new S_SkillSound(cha.fr(), gfxid3));
                }
            } else if (itemId == 640338) {
                if (!pc.j().b(41246, 20)) {
                    pc.a(new S_ServerMessage(337, "$5240"));
                    return;
                }
                int damage = 80 + pc.ev();
                gfxid = 13995;
                if (Random.a(100) < 25) {
                    damage = 160 + pc.ev() + Random.a(300);
                    gfxid = 13997;
                }
                for (L1Object object : L1World.a().b((L1Object)pc, 4)) {
                    if (!(object instanceof L1MonsterInstance) || !pc.i(object.fs(), object.ft())) continue;
                    L1MonsterInstance mob = (L1MonsterInstance)object;
                    L1Magic _magic = new L1Magic(pc, mob);
                    _magic.a(damage, 0);
                    mob.b(new S_DoActionGFX(mob.fr(), 2));
                }
                pc.a(new S_SkillSound(pc.fr(), gfxid));
                pc.b(new S_SkillSound(pc.fr(), gfxid));
            } else if (itemId == 640356) {
                if (select_charid == pc.fr()) {
                    pc.e(new Timestamp(System.currentTimeMillis() + 2592000000L));
                }
                CharacterTable.a().a(select_charid, 2592000000L);
                pc.a(new S_ProtoBuffers(461, pc));
                pc.a(new S_SkillSound(pc.fr(), 2028));
                pc.b(new S_SkillSound(pc.fr(), 2028));
                pc.a(new S_ServerMessage(3916));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640700) {
                obj3 = L1World.a().a(spellsc_objid);
                L1Character target2 = new L1Character();
                if (obj3 == null) {
                    target2.cF(spellsc_objid);
                    target2.cG(spellsc_x);
                    target2.cH(spellsc_y);
                } else {
                    target2.cF(obj3.fr());
                    target2.cG(obj3.fs());
                    target2.cH(obj3.ft());
                }
                int dmg = 200 + Random.a(150);
                L1Location loc2 = new L1Location(target2.fs(), target2.ft(), pc.fp());
                for (L1Object object : L1World.a().a(loc2, 3)) {
                    if (!(object instanceof L1MonsterInstance)) continue;
                    L1MonsterInstance mob = (L1MonsterInstance)object;
                    L1Magic _magic = new L1Magic(pc, mob);
                    dmg = (int)L1Magic.a(pc, mob, dmg, 0);
                    _magic.a(dmg, 0);
                    mob.b(new S_DoActionGFX(mob.fr(), 2));
                }
                pc.ct(pc.a((L1Object)target2));
                pc.a(new S_AttackPacket(pc, target2, 17, 762, dmg, 8, 0));
                pc.b(new S_AttackPacket(pc, target2, 17, 762, dmg, 8, 0));
                this.e(pc, l1iteminstance);
            } else if (itemId == 640354) {
                for (L1Object object : L1World.a().b((L1Object)pc, 5)) {
                    if (!(object instanceof L1DotaInstance)) continue;
                    L1DotaInstance mob = (L1DotaInstance)object;
                    mob.b(new S_DoActionGFX(mob.fr(), 2));
                    mob.b(pc, 250);
                }
                pc.a(new S_SkillSound(pc.fr(), 1819));
                pc.b(new S_SkillSound(pc.fr(), 1819));
                this.e(pc, l1iteminstance);
            } else if (itemId == 640355) {
                for (L1Object object : L1World.a().b((L1Object)pc, 3934)) {
                    if (!(object instanceof L1DotaInstance)) continue;
                    L1DotaInstance mob = (L1DotaInstance)object;
                    mob.b(new S_DoActionGFX(mob.fr(), 2));
                    mob.b(pc, 700);
                }
                pc.a(new S_SkillSound(pc.fr(), 3934));
                pc.b(new S_SkillSound(pc.fr(), 3934));
                this.e(pc, l1iteminstance);
            } else if (itemId == 640319 || itemId == 640320) {
                int gfxid4 = l1iteminstance.a().V();
                for (L1Object object : L1World.a().b((L1Object)pc, 7)) {
                    if (!(object instanceof L1MonsterInstance)) continue;
                    L1MonsterInstance mob = (L1MonsterInstance)object;
                    mob.b(new S_DoActionGFX(mob.fr(), 2));
                    mob.b(pc, 100);
                }
                pc.a(new S_SkillSound(pc.fr(), gfxid4));
                pc.b(new S_SkillSound(pc.fr(), gfxid4));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640321 || itemId == 640322) {
                int skillid = l1iteminstance.a().V();
                L1ItemQuestBuff.b(pc, skillid, 120);
                pc.a(new S_SkillSound(pc.fr(), 11101));
                pc.b(new S_SkillSound(pc.fr(), 11101));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640709 || itemId == 640710 || itemId == 640711) {
                int skillid = l1iteminstance.a().V();
                L1ItemQuestBuff.b(pc, skillid, 1200);
                pc.a(new S_SkillSound(pc.fr(), skillid + 10751));
                pc.b(new S_SkillSound(pc.fr(), skillid + 10751));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640810 && itemId <= 640818) {
                int skillid = l1iteminstance.a().V();
                int time = 900;
                if (itemId == 640810 || itemId == 640812 || itemId == 640814) {
                    time = 300;
                }
                L1ItemQuestBuff.b(pc, skillid, time);
                pc.a(new S_SkillSound(pc.fr(), skillid + 3865));
                pc.b(new S_SkillSound(pc.fr(), skillid + 3865));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640103) {
                Enchant.a(pc, l1iteminstance, selectItem);
            } else if (itemId == 640110) {
                if (selectItem.a().aP() != 17 && selectItem.a().aP() != 22) {
                    pc.a(new S_ServerMessage(2477));
                    return;
                }
                if (selectItem.N() != 640478 && selectItem.N() != 640483) {
                    pc.a(new S_ServerMessage(3658));
                    return;
                }
                if (pc.O(selectItem.fr())) {
                    pc.a(new S_ServerMessage(1181));
                    return;
                }
                int[] ability = new int[]{16384, 65536, 32768, 2, 262144};
                selectItem.n(ability[Random.a(ability.length)]);
                pc.a(new S_ServerMessage(3657));
                selectItem.x();
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640109) {
                if (selectItem.a().aP() != 17 && selectItem.a().aP() != 22) {
                    pc.a(new S_ServerMessage(2477));
                    return;
                }
                if (selectItem.N() != 640476 && selectItem.N() != 640477 && selectItem.N() != 640481 && selectItem.N() != 640482) {
                    pc.a(new S_ServerMessage(3658));
                    return;
                }
                if (pc.O(selectItem.fr())) {
                    pc.a(new S_ServerMessage(1181));
                    return;
                }
                int field = Random.a(3) + 1;
                int[] ability = new int[]{16384, 65536, 32768, 2, 262144};
                if (!(field != 1 && field != 2 || selectItem.N() != 640476 && selectItem.N() != 640481)) {
                    pc.j().f(selectItem);
                    ItemTable.a(pc, selectItem.N() + 1, 1, selectItem.C());
                } else if (field == 3) {
                    pc.j().f(selectItem);
                    ItemTable.a(pc, selectItem.a().V(), 1, selectItem.C());
                } else {
                    selectItem.l(0);
                    selectItem.m(0);
                    selectItem.n(0);
                }
                if (field == 1) {
                    selectItem.l(ability[Random.a(ability.length)]);
                } else if (field == 2) {
                    selectItem.m(ability[Random.a(ability.length)]);
                } else if (field == 3) {
                    selectItem.n(ability[Random.a(ability.length)]);
                }
                pc.a(new S_ServerMessage(3657));
                selectItem.x();
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640574 || itemId == 640575) {
                if (pc.fp() == 4 && pc.fu().e(new Point(33333, 32444))) {
                    int npcid = l1iteminstance.a().V();
                    SpawnTable.a(npcid, pc, 7, 0L);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 640547) {
                int counts = 1;
                rnd = Random.a(100) + 1;
                if (rnd <= 40) {
                    counts = 17 + Random.a(60);
                } else if (rnd >= 41 && rnd <= 70) {
                    counts = 177 + Random.a(600);
                } else if (rnd >= 71 && rnd <= 90) {
                    counts = 1777 + Random.a(6000);
                } else if (rnd >= 91 && rnd <= 100) {
                    counts = 17777 + Random.a(60000);
                }
                ItemTable.a(pc, 40308, counts);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640622 && itemId <= 640625) {
                int rnd3 = Random.a(100) + 1;
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
                ItemTable.a(pc, 640621, counts);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 47103) {
                pc.a(new S_ServerMessage(452, l1iteminstance.s()));
            } else if (itemId == 40003) {
                for (L1ItemInstance lightItem : pc.j().d()) {
                    if (lightItem.N() != 40002) continue;
                    lightItem.j(l1iteminstance.a().d());
                    pc.a(new S_ItemName(lightItem));
                    pc.a(new S_ServerMessage(230));
                    break;
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 43000) {
                pc.k(1);
                pc.Z();
                pc.ay(0);
                pc.a(new S_SkillSound(pc.fr(), 191));
                pc.b(new S_SkillSound(pc.fr(), 191));
                pc.a(new S_OwnCharStatus(pc));
                pc.a(new S_ServerMessage(822));
                pc.I();
                pc.ac();
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 40033 && itemId <= 40038) {
                if (pc.bB() >= 10) {
                    pc.a(new S_ServerMessage(939));
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
                    pc.a(new S_ServerMessage(481));
                    return;
                }
                pc.az(pc.bB() + 1);
                pc.a(new S_OwnCharStatus2(pc));
                pc.I();
                pc.a(new S_ProtoBuffers(489, pc));
                pc.a(new S_ProtoBuffers(490, pc));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40858) {
                pc.e(true);
                pc.a(new S_Liquor(pc.fr(), 1));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40017 || itemId == 40507 || itemId == 640496) {
                pc.a(new S_SkillSound(pc.fr(), 192));
                pc.b(new S_SkillSound(pc.fr(), 192));
                pc.en();
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40014 || itemId == 140014 || itemId == 41415 || itemId == 49305 || itemId == 640363 || itemId == 640729) {
                Potion.a(pc, 1000, l1iteminstance, pc.z() || pc.F());
            } else if (itemId == 40031 || itemId == 640365 || itemId == 640732) {
                Potion.a(pc, 1000, l1iteminstance, pc.x());
            } else if (itemId == 40733) {
                Potion.a(pc, 1000, l1iteminstance, !pc.D() && !pc.E());
            } else if (itemId == 40068 || itemId == 140068 || itemId == 49304 || itemId == 640364 || itemId == 640730) {
                Potion.a(pc, 1016, l1iteminstance, pc.A());
            } else if (itemId == 49158 || itemId == 640731) {
                Potion.a(pc, 1017, l1iteminstance, pc.D() || pc.E());
            } else if (itemId == 49138 || itemId == 640190) {
                Potion.a(pc, l1iteminstance, 600);
            } else if (itemId == 47005) {
                pc.a(new S_SkillSound(pc.fr(), 7321));
                pc.b(new S_SkillSound(pc.fr(), 7321));
                L1ItemQuestBuff.b(pc, 4006, 2400);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640664) {
                if (pc.cC() > 0) {
                    pc.a(new S_ServerMessage(4384));
                    return;
                }
                pc.K(3850000);
                pc.a(new S_SkillSound(pc.fr(), 7467));
                pc.b(new S_SkillSound(pc.fr(), 7467));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640665) {
                pc.a(new S_SkillSound(pc.fr(), 13249));
                pc.b(new S_SkillSound(pc.fr(), 13249));
                pc.j(4078, 1200000);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640336) {
                pc.a(new S_SkillSound(pc.fr(), 7892));
                pc.b(new S_SkillSound(pc.fr(), 7892));
                L1ItemQuestBuff.b(pc, 4070, 1800);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 47006) {
                pc.a(new S_SkillSound(pc.fr(), 7013));
                pc.b(new S_SkillSound(pc.fr(), 7013));
                L1ItemQuestBuff.b(pc, 4007, 3600);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 47000 && itemId <= 47004) {
                int skillId = itemId - 42999;
                gfxid = itemId - 39699;
                L1ItemQuestBuff.b(pc, skillId, 900);
                pc.a(new S_SkillSound(pc.fr(), gfxid));
                pc.b(new S_SkillSound(pc.fr(), gfxid));
                pc.a(new S_ServerMessage(1292));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 47007 && itemId <= 47009) {
                int skillId = itemId - 42999;
                gfxid = itemId - 40014;
                L1ItemQuestBuff.b(pc, skillId, 3600);
                pc.a(new S_SkillSound(pc.fr(), gfxid));
                pc.b(new S_SkillSound(pc.fr(), gfxid));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40032 || itemId == 40041 || itemId == 41344 || itemId == 49303) {
                Potion.f(pc, l1iteminstance);
            } else if (itemId == 40015 || itemId == 140015 || itemId == 40736 || itemId == 49306 || itemId == 640495) {
                Potion.d(pc, l1iteminstance);
            } else if (itemId == 40016 || itemId == 140016 || itemId == 49307 || itemId == 640733) {
                Potion.e(pc, l1iteminstance);
            } else if (itemId == 40025) {
                Potion.g(pc, l1iteminstance);
            } else if (itemId == 640831) {
                pc.a(new S_ProtoBuffers(1038, 600, 0, 6546, 0, 3823, 1971, 1972, 1));
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
                    pc.a(new S_OwnCharStatus(pc));
                    pc.a(new S_SPMR(pc));
                }
                pc.a(new S_Liquor(pc.fr(), 8));
                pc.b(new S_Liquor(pc.fr(), 8));
                pc.a(new S_SkillSound(pc.fr(), 12214));
                pc.b(new S_SkillSound(pc.fr(), 12214));
                pc.j(1038, 600000);
            } else if (itemId == 640702) {
                pc.a(new S_ProtoBuffers(4080, 1800, 0, 4910, 0, 4415, 0, 0, 1));
                if (!pc.bB(4080)) {
                    pc.bH(25);
                    pc.bJ(20);
                    pc.a(new S_OwnCharStatus(pc));
                }
                pc.a(new S_SkillSound(pc.fr(), 13391));
                pc.b(new S_SkillSound(pc.fr(), 13391));
                pc.j(4080, 1800000);
            } else if (itemId == 640583) {
                int time = l1iteminstance.a().V();
                isPoly = L1PolyMorph.a(pc, 13450, time, 1);
                if (!isPoly) {
                    l1iteminstance.a((Timestamp)null);
                    pc.a(new S_ServerMessage(79));
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
                    pc.a(new S_OwnCharStatus(pc));
                }
                pc.j(1006, time * 1000);
            } else if (itemId == 640827) {
                int time = l1iteminstance.a().V();
                isPoly = L1PolyMorph.a(pc, 12854, time, 1);
                if (!isPoly) {
                    l1iteminstance.a((Timestamp)null);
                    pc.a(new S_ServerMessage(79));
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
                    pc.a(new S_OwnCharStatus(pc));
                }
                pc.j(1037, time * 1000);
                for (L1ItemInstance item : pc.j().d()) {
                    if (!item.a().S() || !item.D()) continue;
                    pc.j().a(item, false);
                }
            } else if (itemId == 640785) {
                int time = l1iteminstance.a().V();
                isPoly = L1PolyMorph.a(pc, 14491, time, 1);
                if (!isPoly) {
                    l1iteminstance.a((Timestamp)null);
                    pc.a(new S_ServerMessage(79));
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
                    pc.a(new S_OwnCharStatus(pc));
                }
                pc.j(1031, time * 1000);
            } else if (itemId >= 640581 && itemId <= 640582) {
                int polyid = l1iteminstance.a().V();
                L1PolyMorph.a(pc, polyid, 5400, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640739) {
                int[] polys = new int[]{12283, 12283, 12314, 12295, 12280, 12283, 12286, 12283};
                L1PolyMorph.a(pc, polys[pc.ay()], 1800, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 640307 && itemId <= 640311) {
                int polyid = l1iteminstance.a().V();
                L1PolyMorph.a(pc, polyid, 3600, 1);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 49220 || itemId == 49139 || itemId >= 41154 && itemId <= 41157 || itemId >= 41143 && itemId <= 41145 || itemId >= 640416 && itemId <= 640423 || itemId == 640836) {
                int time;
                int n = time = itemId == 49220 ? 1200 : 900;
                if (itemId >= 640416 && itemId <= 640423) {
                    time = 3600;
                } else if (itemId == 640836) {
                    time = 600;
                }
                int polyid = l1iteminstance.a().V();
                L1PolyMorph.a(pc, polyid, time, 1);
                if (itemId != 640836) {
                    pc.j().b(l1iteminstance, 1);
                }
            } else if (itemId >= 49149 && itemId <= 49155) {
                if (pc.F()) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                this.a(pc, l1iteminstance);
            } else if (itemId == 40317 || itemId == 640498) {
                int msgid = 79;
                if (!selectItem.f() && selectItem.H() > 0) {
                    pc.j().h(selectItem);
                    msgid = selectItem.H() == 0 ? 464 : 463;
                }
                pc.a(new S_ServerMessage(msgid, selectItem.s()));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 47017 && itemId <= 47023) {
                int gfxid5 = l1iteminstance.a().V();
                pc.a(new S_SkillSound(pc.fr(), gfxid5));
                pc.b(new S_SkillSound(pc.fr(), gfxid5));
                L1ItemQuestBuff.b(pc, itemId - 42968, 600);
            } else if (itemId >= 47041 && itemId <= 47046) {
                int createID = l1iteminstance.a().V();
                if (selectItem.a().V() == createID) {
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                    ItemTable.a(pc, createID, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId >= 47049 && itemId <= 47052) {
                if (selectItem.N() >= 47053 && selectItem.N() <= 47062) {
                    if (Random.a(100) + 1 > 50) {
                        int newItem = selectItem.N() + (itemId - 47048) * 10;
                        ItemTable.a(pc, newItem, 1);
                    } else {
                        pc.a(new S_ServerMessage(1411, selectItem.b()));
                    }
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 47048) {
                if (selectItem.N() >= 47053 && selectItem.N() <= 47102) {
                    int createID = selectItem.a().V();
                    if (createID == 0) {
                        pc.a(new S_ServerMessage(79));
                        return;
                    }
                    if (Random.a(100) + 1 > 50) {
                        pc.a(new S_ServerMessage(1410, selectItem.b()));
                        ItemTable.a(pc, createID, 1);
                    } else {
                        pc.a(new S_ServerMessage(1411, selectItem.b()));
                    }
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId >= 640404 && itemId <= 640415) {
                if (pc.j().b(41246, 100)) {
                    int skillid = l1iteminstance.a().V();
                    L1ItemQuestBuff.a(pc, skillid);
                    int[] gfxids = new int[]{13160, 13161, 13162, 13163};
                    pc.a(new S_SkillSound(pc.fr(), gfxids[skillid - 4072]));
                    pc.b(new S_SkillSound(pc.fr(), gfxids[skillid - 4072]));
                } else {
                    pc.a(new S_ServerMessage(337, "$5240"));
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
                    pc.a(new S_SkillSound(pc.fr(), gfxid6));
                    pc.b(new S_SkillSound(pc.fr(), gfxid6));
                    L1ItemQuestBuff.a(pc, skillId, 600);
                } else {
                    isDelayEffect = false;
                    pc.a(new S_ServerMessage(337, "$5240"));
                }
            } else if (itemId == 40097 || itemId == 40119 || itemId == 140119 || itemId == 40329) {
                for (L1ItemInstance eachItem : pc.j().d()) {
                    if (eachItem.F() != 2 && eachItem.F() != 130 || (itemId == 40119 || itemId == 40097) && l1iteminstance.F() != 0 && !eachItem.D()) continue;
                    if (eachItem.F() == 130) {
                        eachItem.f(129);
                    } else {
                        eachItem.f(1);
                    }
                    if (eachItem.d() && pc.j().d(eachItem.N(), 1) != null) {
                        pc.j().b(eachItem, eachItem.E());
                        ItemTable.a(pc, eachItem.N(), eachItem.E(), eachItem.G(), eachItem.F(), eachItem.C());
                        continue;
                    }
                    pc.j().j(eachItem);
                }
                pc.a(new S_ServerMessage(155));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40126 || itemId == 40098) {
                if (selectItem == null) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                if (!selectItem.C()) {
                    selectItem.a(true);
                    pc.j().b(selectItem);
                }
                pc.a(new S_IdentifyDesc(selectItem));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640758) {
                int createID = selectItem.a().V();
                if (selectItem.N() >= 640753 && selectItem.N() <= 640757 && createID > 0) {
                    if (Random.a(100) < 20) {
                        ItemTable.a(pc, createID, 1);
                    } else {
                        pc.a(new S_ServerMessage(165, selectItem.b()));
                    }
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 41036) {
                int createID = selectItem.N() + 10;
                probability = 67;
                this.a(pc, l1iteminstance, selectItem, createID, 67, 0, 158);
            } else if (itemId >= 41048 && itemId <= 41057) {
                if (selectItem.N() == itemId + 8034) {
                    ItemTable.a(pc, selectItem.a().V(), 1);
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId >= 41738 && itemId <= 41753) {
                ItemTable.a(pc, 41719 + Random.a(18), 1);
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
                pc.a(new S_Sound(437));
                pc.b(new S_Sound(437));
                for (L1NpcInstance petNpc : pc.ek().values()) {
                    if (!(petNpc instanceof L1PetInstance)) continue;
                    ((L1PetInstance)petNpc).i();
                }
            } else if (itemId == 640472) {
                switch (telNo) {
                    case 0: {
                        L1Teleport.a(pc, 32732, 32798, 101, 5, true);
                        break;
                    }
                    case 1: {
                        L1Teleport.a(pc, 32799, 32799, 102, 5, true);
                        break;
                    }
                    case 2: {
                        L1Teleport.a(pc, 32799, 32799, 103, 5, true);
                        break;
                    }
                    case 3: {
                        L1Teleport.a(pc, 32669, 32863, 104, 5, true);
                        break;
                    }
                    case 4: {
                        L1Teleport.a(pc, 32671, 32863, 105, 5, true);
                        break;
                    }
                    case 5: {
                        L1Teleport.a(pc, 32719, 32870, 106, 5, true);
                        break;
                    }
                    case 6: {
                        L1Teleport.a(pc, 32670, 32863, 107, 5, true);
                        break;
                    }
                    case 7: {
                        L1Teleport.a(pc, 32671, 32863, 108, 5, true);
                        break;
                    }
                    case 8: {
                        L1Teleport.a(pc, 32671, 32863, 109, 5, true);
                        break;
                    }
                    case 9: {
                        L1Teleport.a(pc, 32799, 32799, 110, 5, true);
                        break;
                    }
                    case 10: {
                        L1Teleport.a(pc, 32622, 32799, 111, 5, true);
                        break;
                    }
                    case 11: {
                        L1Teleport.a(pc, 32693, 32903, 111, 5, true);
                    }
                }
            } else if (itemId == 640437) {
                this.e(pc, l1iteminstance);
                if (l1iteminstance.I() <= 0) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                switch (telNo) {
                    case 0: {
                        L1Teleport.a(pc, 32732, 32798, 101, 5, true);
                        break;
                    }
                    case 1: {
                        L1Teleport.a(pc, 32761, 32833, 77, 5, true);
                        break;
                    }
                    case 2: {
                        L1Teleport.a(pc, 32712, 32791, 59, 5, true);
                        break;
                    }
                    case 3: {
                        L1Teleport.a(pc, 32802, 32734, 43, 5, true);
                        break;
                    }
                    case 4: {
                        L1Teleport.a(pc, 32928, 32799, 430, 5, true);
                        break;
                    }
                    case 5: {
                        L1Teleport.a(pc, 32925, 32995, 410, 5, true);
                        break;
                    }
                    case 6: {
                        L1Teleport.a(pc, 32969, 32959, 521, 5, true);
                        break;
                    }
                    case 7: {
                        L1Teleport.a(pc, 32789, 32799, 600, 5, true);
                        break;
                    }
                    case 8: {
                        L1Teleport.a(pc, 32753, 32830, 309, 5, true);
                        break;
                    }
                    case 9: {
                        L1Teleport.a(pc, 32429, 33015, 550, 5, true);
                    }
                }
            } else if (itemId == 640668 || itemId == 640669) {
                Timestamp current = new Timestamp(System.currentTimeMillis());
                if (l1iteminstance.bb() != null && l1iteminstance.bb().before(current)) {
                    pc.a(new S_ServerMessage(3081));
                    pc.j().b(l1iteminstance, 1);
                    return;
                }
                L1Teleport.a(pc, 32780, 32833, 622, 5, true);
            } else if (itemId == 40493) {
                pc.a(new S_Sound(165));
                pc.b(new S_Sound(165));
                for (L1Object visible : pc.eq()) {
                    L1GuardianInstance guardian;
                    if (!(visible instanceof L1GuardianInstance) || (guardian = (L1GuardianInstance)visible).U_().b() != 70850 || ItemTable.a(pc, 88, 1) == null) continue;
                    pc.j().b(l1iteminstance, 1);
                }
            } else if (itemId >= 40325 && itemId <= 40328) {
                if (pc.j().b(40318, 1)) {
                    int dice = itemId == 40328 ? 6 : itemId - 40323;
                    gfxid = l1iteminstance.a().V() + Random.a(dice);
                    pc.a(new S_SkillSound(pc.fr(), gfxid));
                    pc.b(new S_SkillSound(pc.fr(), gfxid));
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId >= 41440 && itemId <= 41672) {
                this.d(pc, l1iteminstance);
            } else if (itemId == 41689) {
                if (!pc.C()) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                int skillid = l1iteminstance.a().V();
                L1Skills l1skills = SkillsTable.a().a(skillid);
                pc.a(new S_ProtoBuffers(402, skillid - 600));
                pc.a(new S_SkillSound(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
                pc.b(new S_SkillSound(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
                SkillsTable.a().a(pc.fr(), l1skills.a(), l1skills.b(), 0, 0);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId >= 41681 && itemId <= 41688) {
                if (!pc.F()) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                int skillid = l1iteminstance.a().V();
                L1Skills l1skills = SkillsTable.a().a(skillid);
                pc.a(new S_ProtoBuffers(402, skillid - 600));
                pc.a(new S_SkillSound(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
                pc.b(new S_SkillSound(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
                SkillsTable.a().a(pc.fr(), l1skills.a(), l1skills.b(), 0, 0);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40079 || itemId == 40095 || itemId == 40521 || itemId == 40124 || itemId == 640497) {
                if (!pc.fq().j() && !pc.l()) {
                    pc.a(new S_ServerMessage(647));
                    pc.a(new S_Paralysis(7, false));
                    return;
                }
                loc = L1Getback.a(pc);
                if (itemId == 40124 || itemId == 640497) {
                    if (pc.bF() > 0) {
                        loc = L1TownLocation.a(pc.bF());
                    }
                    if (pc.aF() > 0) {
                        L1Clan clan = ClanTable.a().a(pc.aF());
                        if (clan.m() > 0) {
                            loc = L1CastleLocation.d(clan.m());
                        } else if (clan.n() > 0) {
                            loc = L1HouseLocation.a(clan.n());
                        }
                    }
                }
                L1Teleport.a(pc, loc[0], loc[1], loc[2], 5, true);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640501) {
                if (L1CastleWar.a().a((L1Character)pc)) {
                    pc.a(new S_ServerMessage(2139));
                    return;
                }
                if (pc.fu().c(new Point(fishX, fishY)) <= 5 && pc.i(fishX, fishY)) {
                    L1Teleport.a(pc, fishX, fishY, pc.fp(), pc.fb(), true);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(626));
                }
            } else if (itemId >= 40901 && itemId <= 40908) {
                if (pc.bD() == 0) {
                    pc.a(new S_ServerMessage(662));
                    return;
                }
                obj3 = L1World.a().a(pc.bD());
                if (obj3 instanceof L1PcInstance) {
                    L1PcInstance partner = (L1PcInstance)obj3;
                    boolean isCastleAarea = L1CastleLocation.a(partner.fu());
                    if (partner.fq().h() && !isCastleAarea) {
                        L1Teleport.a(pc, partner.fs(), partner.ft(), partner.fp(), 5, true);
                    } else {
                        pc.a(new S_ServerMessage(547));
                    }
                } else {
                    pc.a(new S_ServerMessage(546));
                }
            } else if (itemId == 40555) {
                if (pc.z() && pc.fu().e(new Point(32821, 32800))) {
                    L1Teleport.a(pc, 32815, 32810, 13, 5, true);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 40417) {
                if (pc.fp() == 440 && pc.fu().e(new Point(32670, 32980))) {
                    L1Teleport.a(pc, 32922, 32812, 430, 5, true);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 40700) {
                pc.a(new S_Sound(10));
                pc.b(new S_Sound(10));
                if (pc.fs() >= 32619 && pc.fs() <= 32623 && pc.ft() >= 33120 && pc.ft() <= 33124 && pc.fp() == 440) {
                    this.a(pc, 45875);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 40566) {
                if (pc.A() && pc.fp() == 4 && !pc.j().f(40548) && pc.fs() >= 33971 && pc.fs() <= 33975 && pc.ft() >= 32324 && pc.ft() <= 32328) {
                    this.a(pc, 45300);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 49222) {
                if (pc.D() && pc.fp() == 61) {
                    this.a(pc, 46161);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 49189) {
                if (pc.E() && pc.fp() == 4) {
                    this.a(pc, 46163);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 49201) {
                if (pc.E() && pc.fp() == 4) {
                    this.a(pc, 81254);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 49208 || itemId == 49227) {
                if (pc.ay() >= 5 && pc.fp() == 2004) {
                    this.a(pc, 81307 + pc.ay());
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 49167) {
                if (pc.ay() <= 3 && pc.fp() == 2000 + pc.ay() && pc.fs() == 32807 && pc.ft() == 32773) {
                    this.a(pc, 81323 + pc.ay());
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId >= 40557 && itemId <= 40563) {
                int[][] data = new int[][]{{32620, 32641}, {33513, 32890}, {34215, 33195}, {32580, 33260}, {33046, 32806}, {33447, 33476}, {32730, 32426}};
                i = itemId - 40557;
                int npcid = l1iteminstance.a().V();
                if (pc.fs() == data[i][0] && pc.ft() == data[i][1] && pc.fp() == 4) {
                    for (L1Object obj2 : L1World.a().b()) {
                        if (!(obj2 instanceof L1NpcInstance) || ((L1NpcInstance)obj2).z() != npcid) continue;
                        pc.a(new S_ServerMessage(79));
                        return;
                    }
                    SpawnTable.a(npcid, pc, 0, 300000L);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 40572) {
                if (pc.fs() == 32778 && pc.ft() == 32738 && pc.fp() == 21) {
                    L1Teleport.a(pc, 32781, 32728, 21, 5, true);
                } else if (pc.fs() == 32781 && pc.ft() == 32728 && pc.fp() == 21) {
                    L1Teleport.a(pc, 32778, 32738, 21, 5, true);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 40412) {
                pc.a(new S_DoActionGFX(pc.fr(), 17));
                pc.b(new S_DoActionGFX(pc.fr(), 17));
                if (!pc.fq().l()) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                int[] mobArray = new int[]{45008, 45140, 45016, 45021, 45025, 45033, 45099, 45147, 45123, 45130, 45046, 45092, 45138, 45098, 45127, 45143, 45149, 45171, 45040, 45155, 45192, 45173, 45213, 45079, 45144};
                rnd = Random.a(mobArray.length);
                SpawnTable.a(mobArray[rnd], pc, 0, 300000L);
                this.e(pc, l1iteminstance);
            } else if (itemId == 40007 || itemId == 40006) {
                obj3 = L1World.a().a(spellsc_objid);
                L1Character target3 = new L1Character();
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
                pc.ct(pc.a((L1Object)target3));
                pc.a(new S_AttackPacket(pc, target3, 17, gfxid7, dmg, 6, 0));
                pc.b(new S_AttackPacket(pc, target3, 17, gfxid7, dmg, 6, 0));
                this.e(pc, l1iteminstance);
            } else if (itemId == 40008 || itemId == 40410 || itemId == 140008) {
                if (pc.fq().g()) {
                    pc.a(new S_ServerMessage(563));
                    return;
                }
                pc.a(new S_DoActionGFX(pc.fr(), 17));
                pc.b(new S_DoActionGFX(pc.fr(), 17));
                target = L1World.a().a(spellsc_objid);
                if (target instanceof L1Character) {
                    this.a(pc, (L1Character)target);
                    this.e(pc, l1iteminstance);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 41121 || itemId == 41130) {
                ItemTable.a(pc, itemId + 1, 1);
            } else if (itemId == 42501) {
                if (pc.eb() < 10) {
                    pc.a(new S_ServerMessage(278));
                } else {
                    pc.i_(pc.eb() - 10);
                    L1Teleport.a(pc, spellsc_x, spellsc_y, pc.fp(), pc.fb(), true);
                }
            } else if (itemId == 41759 || itemId == 41760 || itemId == 41762) {
                pc.a(new S_Message_YN(2936, new String[0]));
                pc.am(l1iteminstance.fr());
            } else if (itemId == 41763) {
                if (pc.cI() > 110) {
                    pc.a(new S_ServerMessage(2962));
                } else {
                    L1BookMark.a(pc);
                    pc.j().b(l1iteminstance, 1);
                }
            } else if (itemId == 41293 || itemId == 640269 || itemId == 640282) {
                this.a(pc, fishX, fishY, l1iteminstance);
            } else if (itemId == 640272) {
                if (selectItem == null) {
                    pc.a(new S_ServerMessage(156));
                    return;
                }
                if (selectItem.N() == 640269) {
                    pc.j().b(l1iteminstance, 1);
                    pc.j().b(selectItem, 1);
                    L1ItemInstance create = ItemTable.a(pc, 640282, 1);
                    create.a(true);
                    create.g(100);
                    pc.j().b(create);
                } else if (selectItem.N() == 640282 && selectItem.I() <= 300) {
                    pc.j().b(l1iteminstance, 1);
                    selectItem.g(selectItem.I() + 100);
                    pc.j().b(selectItem);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 41245) {
                if (selectItem == null) {
                    pc.a(new S_ServerMessage(156));
                    return;
                }
                this.a(pc, selectItem, l1iteminstance);
            } else if (itemId >= 41255 && itemId <= 41259) {
                if (cookStatus == 0) {
                    pc.a(new S_PacketBox(52, itemId - 41255));
                } else {
                    this.b(pc, cookNo);
                }
            } else if (itemId == 41260) {
                for (L1Object obj3 : pc.eq()) {
                    if (!(obj3 instanceof L1EffectInstance) || obj3.f(pc) > 3 || ((L1EffectInstance)obj3).fe() != 5943) continue;
                    pc.a(new S_ServerMessage(1162));
                    return;
                }
                loc = pc.eg();
                L1SpawnEffect.a().a(5943, 600000, loc[0], loc[1], pc.fp());
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 41345) {
                L1DamagePoison.a(pc, pc, 3000, 5, 30);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 41315 || itemId == 41316 || itemId == 41354 || itemId == 49168) {
                int skillid = l1iteminstance.a().V();
                pc.j(skillid, 900000);
                pc.a(new S_SkillSound(pc.fr(), 190));
                pc.b(new S_SkillSound(pc.fr(), 190));
                if (itemId != 49168) {
                    pc.a(new S_ServerMessage(skillid + 127));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640297 || itemId == 640298) {
                int timesec = l1iteminstance.a().V();
                pc.j(1030, timesec * 1000);
                pc.a(new S_SkillSound(pc.fr(), timesec == 300 ? 20 : 21));
                pc.b(new S_SkillSound(pc.fr(), timesec == 300 ? 20 : 21));
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 49092 || itemId == 49094 || itemId == 49098 || itemId == 49317 || itemId == 49321 || itemId == 49198 || itemId == 49199 || itemId == 49188) {
                if (selectItem.a().V() == itemId) {
                    int i3 = l1iteminstance.a().V();
                    ItemTable.a(pc, selectItem.N() + i3, 1);
                    pc.j().b(selectItem, 1);
                    pc.j().b(l1iteminstance, 1);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 49148 || itemId == 640145 || itemId == 640727) {
                Enchant.a(pc, l1iteminstance, selectItem, false);
            } else if (itemId == 640614) {
                if (selectItem.N() >= 21366 && selectItem.N() <= 21371) {
                    Enchant.a(pc, l1iteminstance, selectItem, true);
                } else {
                    pc.a(new S_ServerMessage(79));
                }
            } else if (itemId == 41426) {
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
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 41427) {
                if (selectItem.F() < 128 || selectItem.f() && !selectItem.a().aN()) {
                    pc.a(new S_ServerMessage(79));
                    return;
                }
                selectItem.f(selectItem.F() - 128);
                pc.j().j(selectItem);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 41428) {
                L1Account account = pc.aK().e();
                int solt = Math.min(account.k() + 1, 8);
                account.e(solt);
                AccountTable.a().c(account);
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 40075) {
                if (selectItem.h()) {
                    int[] msg = new int[]{167, 171, 169, 170, 168, 172, 173, 174};
                    if (selectItem.a().aP() <= 7) {
                        pc.a(new S_ServerMessage(msg[selectItem.a().aP()]));
                    } else {
                        pc.a(new S_ServerMessage(msg[0]));
                    }
                    pc.j().b(selectItem, 1);
                } else {
                    pc.a(new S_ServerMessage(154));
                }
                pc.j().b(l1iteminstance, 1);
            } else if (itemId == 640941) {
                pc.a(new S_Html(pc.fr(), "gunterseal"));
            } else if (itemId == 40630) {
                pc.a(new S_Html(pc.fr(), "diegodiary"));
            } else if (itemId == 40641) {
                pc.a(new S_Html(pc.fr(), "tscrolla"));
            } else if (itemId == 40663) {
                pc.a(new S_Html(pc.fr(), "sonsletter"));
            } else if (itemId == 41007) {
                pc.a(new S_Html(pc.fr(), "erisscroll"));
            } else if (itemId == 41009) {
                pc.a(new S_Html(pc.fr(), "erisscroll2"));
            } else if (itemId == 41019) {
                pc.a(new S_Html(pc.fr(), "lashistory1"));
            } else if (itemId == 41020) {
                pc.a(new S_Html(pc.fr(), "lashistory2"));
            } else if (itemId == 41021) {
                pc.a(new S_Html(pc.fr(), "lashistory3"));
            } else if (itemId == 41022) {
                pc.a(new S_Html(pc.fr(), "lashistory4"));
            } else if (itemId == 41023) {
                pc.a(new S_Html(pc.fr(), "lashistory5"));
            } else if (itemId == 41024) {
                pc.a(new S_Html(pc.fr(), "lashistory6"));
            } else if (itemId == 41025) {
                pc.a(new S_Html(pc.fr(), "lashistory7"));
            } else if (itemId == 41026) {
                pc.a(new S_Html(pc.fr(), "lashistory8"));
            } else if (itemId == 41060) {
                pc.a(new S_Html(pc.fr(), "nonames"));
            } else if (itemId == 41061) {
                pc.a(new S_Html(pc.fr(), "kames"));
            } else if (itemId == 41062) {
                pc.a(new S_Html(pc.fr(), "bakumos"));
            } else if (itemId == 41063) {
                pc.a(new S_Html(pc.fr(), "bukas"));
            } else if (itemId == 41064) {
                pc.a(new S_Html(pc.fr(), "huwoomos"));
            } else if (itemId == 41065) {
                pc.a(new S_Html(pc.fr(), "noas"));
            } else if (itemId == 41356) {
                pc.a(new S_Html(pc.fr(), "rparum3"));
            } else if (itemId == 41340) {
                pc.a(new S_Html(pc.fr(), "tion"));
            } else if (itemId == 41317) {
                pc.a(new S_Html(pc.fr(), "rarson"));
            } else if (itemId == 41318) {
                pc.a(new S_Html(pc.fr(), "kuen"));
            } else if (itemId == 41329) {
                pc.a(new S_Html(pc.fr(), "anirequest"));
            } else if (itemId == 41346) {
                pc.a(new S_Html(pc.fr(), "robinscroll"));
            } else if (itemId == 41347) {
                pc.a(new S_Html(pc.fr(), "robinscroll2"));
            } else if (itemId == 41348) {
                pc.a(new S_Html(pc.fr(), "robinhood"));
            } else if (itemId == 49172) {
                pc.a(new S_Html(pc.fr(), "silrein1lt"));
            } else if (itemId == 49173) {
                pc.a(new S_Html(pc.fr(), "silrein2lt"));
            } else if (itemId == 49174) {
                pc.a(new S_Html(pc.fr(), "silrein3lt"));
            } else if (itemId == 49175) {
                pc.a(new S_Html(pc.fr(), "silrein4lt"));
            } else if (itemId == 49176) {
                pc.a(new S_Html(pc.fr(), "silrein5lt"));
            } else if (itemId == 49177) {
                pc.a(new S_Html(pc.fr(), "silrein6lt"));
            } else if (itemId == 49202) {
                pc.a(new S_Html(pc.fr(), "cot_ep1st"));
            } else if (itemId == 49206) {
                pc.a(new S_Html(pc.fr(), "bluesoul_p"));
            } else if (itemId == 49210) {
                pc.a(new S_Html(pc.fr(), "first_p"));
            } else if (itemId == 49211) {
                pc.a(new S_Html(pc.fr(), "second_p"));
            } else if (itemId == 49212) {
                pc.a(new S_Html(pc.fr(), "third_p"));
            } else if (itemId == 49221) {
                pc.a(new S_Html(pc.fr(), "spy_letter"));
            } else if (itemId == 49231) {
                pc.a(new S_Html(pc.fr(), "redsoul_p"));
            } else if (itemId == 49287) {
                pc.a(new S_Html(pc.fr(), "fourth_p"));
            } else if (itemId == 49288) {
                pc.a(new S_Html(pc.fr(), "fifth_p"));
            } else {
                int locX = l1iteminstance.a().aG();
                int locY = l1iteminstance.a().aH();
                short mapId = l1iteminstance.a().aI();
                if (locX != 0 && locY != 0) {
                    if (pc.fq().j() || pc.l()) {
                        if (itemId >= 40103 && itemId <= 40112 && pc.fp() == mapId) {
                            L1Teleport.a(pc, 200);
                        } else {
                            L1Teleport.a(pc, locX, locY, mapId, pc.fb(), true);
                        }
                        pc.j().b(l1iteminstance, 1);
                    } else {
                        pc.a(new S_ServerMessage(647));
                        pc.a(new S_Paralysis(7, false));
                    }
                } else if (l1iteminstance.E() < 1) {
                    pc.a(new S_ServerMessage(329, l1iteminstance.s()));
                } else {
                    pc.a(new S_ServerMessage(74, l1iteminstance.s()));
                }
            }
        }
        if (isDelayEffect) {
            Timestamp ts = new Timestamp(System.currentTimeMillis());
            l1iteminstance.a(ts);
            pc.j().j(l1iteminstance);
        }
        L1ItemDelay.a(client, l1iteminstance);
        for (L1QuestNew qn : pc.dS().values()) {
            int i = 0;
            while (i < qn.u().length) {
                if (qn.u()[i] == itemId) {
                    qn.c(i);
                }
                ++i;
            }
        }
    }

    private void a(L1PcInstance pc, L1ItemInstance item) {
        int basePolyID = item.a().V();
        if (pc.D() || pc.E()) {
            basePolyID = 7129 + (item.N() - 49149) * 4;
        }
        int polyid = basePolyID + pc.ay() * 2 + pc.aJ();
        L1PolyMorph.a(pc, polyid, 1800, 1);
        pc.j().b(item, 1);
    }

    private void a(L1PcInstance pc, L1ItemInstance l1iteminstance, L1ItemInstance selectItem, int createID, int probability, int successMsg, int failureMsg) {
        int itemId = l1iteminstance.N();
        if (selectItem.a().V() == itemId) {
            if (Random.a(100) < probability) {
                pc.j().b(selectItem, 1);
                ItemTable.a(pc, createID, 1);
                if (successMsg > 0) {
                    pc.a(new S_ServerMessage(successMsg, selectItem.b()));
                }
            } else {
                pc.a(new S_ServerMessage(failureMsg, selectItem.b()));
                if (failureMsg == 158) {
                    pc.j().b(selectItem, 1);
                }
            }
            pc.j().b(l1iteminstance, 1);
        } else {
            pc.a(new S_ServerMessage(79));
        }
    }

    private void a(L1PcInstance pc, L1ItemInstance item, int blanksc_skillid) {
        if (!pc.B()) {
            pc.a(new S_ServerMessage(264));
            return;
        }
        if (blanksc_skillid <= item.a().V()) {
            L1ItemInstance spellsc = ItemTable.a().b(40858 + blanksc_skillid);
            if (spellsc != null && pc.j().a(spellsc, 1) == 0) {
                L1Skills l1skills = SkillsTable.a().a(blanksc_skillid);
                if (pc.ea() + 1 < l1skills.e() + 1) {
                    pc.a(new S_ServerMessage(279));
                    return;
                }
                if (pc.eb() < l1skills.d()) {
                    pc.a(new S_ServerMessage(278));
                    return;
                }
                if (l1skills.f() != 0 && !pc.j().g(l1skills.f(), l1skills.g())) {
                    pc.a(new S_ServerMessage(299));
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
            pc.a(new S_ServerMessage(591));
        }
    }

    private void b(L1PcInstance pc, L1ItemInstance armor) {
        int type = armor.a().aP();
        L1PcInventory pcInventory = pc.j();
        boolean hasEquipeSpace = false;
        int maxEquipeCount = 1;
        if (type == 9) {
            maxEquipeCount = 2;
            if ((pc.cP() & S_RuneSlot.c) == S_RuneSlot.c) {
                ++maxEquipeCount;
            }
            if ((pc.cP() & S_RuneSlot.d) == S_RuneSlot.d) {
                ++maxEquipeCount;
            }
        } else if (type == 13) {
            if ((pc.cP() & S_RuneSlot.e) == S_RuneSlot.e) {
                ++maxEquipeCount;
            }
        } else if (type == 23 && (pc.cP() & S_RuneSlot.f) == S_RuneSlot.f) {
            ++maxEquipeCount;
        }
        boolean bl = hasEquipeSpace = pcInventory.i(type) <= maxEquipeCount - 1;
        if (hasEquipeSpace && !armor.D()) {
            int polyid = pc.fe();
            if (!L1PolyMorph.b(polyid, type)) {
                pc.a(new S_ServerMessage(2055, armor.s()));
                return;
            }
            if (type == 29 && (pc.cP() & S_RuneSlot.g) != S_RuneSlot.g) {
                pc.a(new S_ServerMessage(333));
                return;
            }
            if (type == 30 && (pc.cP() & S_RuneSlot.h) != S_RuneSlot.h) {
                pc.a(new S_ServerMessage(333));
                return;
            }
            if (type == 10 && pcInventory.i(8) >= 1 || type == 8 && pcInventory.i(10) >= 1) {
                pc.a(new S_ServerMessage(124));
                return;
            }
            if ((type == 10 || type == 8) && pc.w() == 2) {
                pc.a(new S_ServerMessage(124));
                return;
            }
            if (type == 8 && pc.v() != null && pc.v().a().e() && !pc.F()) {
                pc.a(new S_ServerMessage(129));
                return;
            }
            if (type == 3 && pcInventory.i(4) >= 1) {
                pc.a(new S_ServerMessage(126, "$224", "$225"));
                return;
            }
            if (type == 3 && pcInventory.i(2) >= 1) {
                pc.a(new S_ServerMessage(126, "$224", "$226"));
                return;
            }
            if (type == 2 && pcInventory.i(4) >= 1) {
                pc.a(new S_ServerMessage(126, "$226", "$225"));
                return;
            }
            if (type == 23 && armor.bb() == null) {
                if (armor.N() >= 21261 && armor.N() <= 21300) {
                    pc.a(new S_ServerMessage(1891));
                    return;
                }
                if (armor.N() == 21397 && pc.fp() != 1700 && pc.fp() != 1703) {
                    pc.a(new S_ServerMessage(333));
                    return;
                }
            }
            pcInventory.a(armor, true);
        } else if (armor.D()) {
            if (armor.F() == 2) {
                pc.a(new S_ServerMessage(150));
                return;
            }
            if (type == 3 && pcInventory.i(2) >= 1) {
                pc.a(new S_ServerMessage(127));
                return;
            }
            if ((type == 2 || type == 3) && pcInventory.i(4) >= 1) {
                pc.a(new S_ServerMessage(127));
                return;
            }
            if (type == 23 && armor.bb() == null && armor.N() >= 21261 && armor.N() <= 21300) {
                pc.a(new S_ServerMessage(1891));
                return;
            }
            if (type == 8 && pc.bB(90)) {
                pc.bz(90);
            }
            pcInventory.a(armor, false);
        } else {
            pc.a(new S_ServerMessage(124));
        }
        pc.a(pc.ea());
        pc.i_(pc.eb());
        pc.a(new S_OwnCharAttrDef(pc));
        pc.a(new S_OwnCharStatus(pc));
        pc.a(new S_SPMR(pc));
    }

    private void c(L1PcInstance pc, L1ItemInstance weapon) {
        L1PcInventory pcInventory = pc.j();
        if (pc.v() == null || !pc.v().equals(weapon)) {
            int weapon_type = weapon.a().aP();
            int polyid = pc.fe();
            if (!L1PolyMorph.a(polyid, weapon_type)) {
                pc.a(new S_ServerMessage(2055, weapon.s()));
                return;
            }
            if (weapon.a().e() && pcInventory.i(8) >= 1 && !pc.bB(603)) {
                pc.a(new S_ServerMessage(128));
                return;
            }
            if (weapon.N() == 413 && pc.fp() != 6311) {
                pc.a(new S_ServerMessage(333));
                return;
            }
        }
        if (pc.v() != null) {
            if (pc.a(weapon)) {
                if (weapon.F() == 2) {
                    pc.a(new S_ServerMessage(150));
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
            pc.a(new S_ServerMessage(149, weapon.s()));
        }
        pcInventory.a(weapon, true);
    }

    private void d(L1PcInstance pc, L1ItemInstance item) {
        int learnLevel;
        int skillid = item.a().V();
        L1Skills l1skills = SkillsTable.a().a(skillid);
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
                pc.a(new S_ServerMessage(79));
                return;
            }
            if (pc.ev() / baseLevel < skillLevel) {
                pc.a(new S_ServerMessage(312));
                return;
            }
        }
        if (skillid >= 87 && skillid <= 92) {
            if (!pc.z()) {
                pc.a(new S_ServerMessage(79));
                return;
            }
            int n = learnLevel = skillid == 89 ? 60 : 50;
            if (skillid == 92) {
                learnLevel = 80;
            }
            if (pc.ev() < learnLevel) {
                pc.a(new S_ServerMessage(312));
                return;
            }
        }
        if (skillid >= 97 && skillid <= 112 || skillid == 233) {
            if (!pc.C()) {
                pc.a(new S_ServerMessage(79));
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
                pc.a(new S_ServerMessage(312));
                return;
            }
        }
        if (skillid >= 113 && skillid <= 122) {
            if (!pc.x()) {
                pc.a(new S_ServerMessage(79));
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
                pc.a(new S_ServerMessage(312));
                return;
            }
        }
        if (skillid >= 129 && skillid <= 176) {
            if (!pc.A()) {
                pc.a(new S_ServerMessage(79));
                return;
            }
            learnLevel = 50;
            learnLevel = skillid >= 129 && skillid <= 131 ? 10 : (skillid >= 137 && skillid <= 138 ? 20 : (skillid >= 145 && skillid <= 152 || skillid == 132 || skillid == 170 ? 30 : (skillid >= 153 && skillid <= 160 || skillid == 133 ? 40 : (skillid == 135 ? 80 : 50))));
            if (pc.ev() < learnLevel) {
                pc.a(new S_ServerMessage(312));
                return;
            }
        }
        if (skillid >= 181 && skillid <= 196) {
            if (!pc.D()) {
                pc.a(new S_ServerMessage(79));
                return;
            }
            learnLevel = ((skillid - 181) / 5 + 1) * 15;
            if (skillid == 196) {
                learnLevel = 80;
            }
            if (pc.ev() < learnLevel) {
                pc.a(new S_ServerMessage(312));
                return;
            }
        }
        if (skillid >= 201 && skillid <= 222) {
            if (!pc.E()) {
                pc.a(new S_ServerMessage(79));
                return;
            }
            learnLevel = ((skillid - 201) / 5 + 1) * 10;
            if (skillid == 222) {
                learnLevel = 80;
            }
            if (pc.ev() < learnLevel) {
                pc.a(new S_ServerMessage(312));
                return;
            }
        }
        if (skillid >= 225 && skillid <= 231 && !pc.F()) {
            pc.a(new S_ServerMessage(79));
            return;
        }
        pc.a(new S_AddSkill(pc, skillid));
        pc.a(new S_SkillSound(pc.fr(), l1skills.o() >= 0 ? 224 : 231));
        pc.b(new S_SkillSound(pc.fr(), 224));
        SkillsTable.a().a(pc.fr(), l1skills.a(), l1skills.b(), 0, 0);
        pc.j().b(item, 1);
    }

    private void e(L1PcInstance pc, L1ItemInstance l1iteminstance) {
        if (l1iteminstance.I() > 1) {
            l1iteminstance.g(l1iteminstance.I() - 1);
            pc.j().b(l1iteminstance);
        } else {
            pc.j().b(l1iteminstance, 1);
        }
    }

    private int a(L1PcInstance pc, L1Object target) {
        if (target == null || !pc.i(target.fs(), target.ft())) {
            return 0;
        }
        int dmg = pc.ez() * 3 + Random.a(pc.ez());
        if (target instanceof L1PcInstance) {
            L1PcInstance tpc = (L1PcInstance)target;
            if (pc.fr() == tpc.fr() || pc.a(pc, tpc, false)) {
                return 0;
            }
            if (L1Magic.a(tpc)) {
                return 0;
            }
            int newHp = tpc.ea() - dmg;
            if (newHp > 0) {
                tpc.a(newHp);
            } else if (newHp <= 0 && tpc.l()) {
                tpc.a(tpc.ew());
            } else {
                tpc.b((L1Character)pc);
            }
            return dmg;
        }
        if (target instanceof L1MonsterInstance) {
            L1MonsterInstance mob = (L1MonsterInstance)target;
            mob.b(pc, dmg);
            return dmg;
        }
        return 0;
    }

    private void a(L1PcInstance pc, L1Character traget) {
        L1MonsterInstance mob;
        boolean isTargetMe = pc.fr() == traget.fr();
        int pid = Random.a(L1PolyMorph.f.length);
        int polyId = L1PolyMorph.f[pid];
        int probability = 3 * (pc.ev() - traget.ev()) + 100 - traget.W_();
        if (traget instanceof L1PcInstance) {
            L1PcInstance tpc = (L1PcInstance)traget;
            if (isTargetMe || tpc.aF() != 0 && tpc.aF() == pc.aF()) {
                probability = 100;
            }
            if (probability <= Random.a(100)) {
                pc.a(new S_ServerMessage(79));
                return;
            }
            if (tpc.j().h(20281)) {
                tpc.a(new S_Message_YN(180, ""));
                tpc.t(true);
            } else {
                L1PolyMorph.a(tpc, polyId, 1800, 1);
            }
            if (!isTargetMe) {
                tpc.a(new S_ServerMessage(241, pc.et()));
            }
        } else if (traget instanceof L1MonsterInstance && (mob = (L1MonsterInstance)traget).ev() < 50) {
            int[] lowLevelBossID;
            int[] nArray = lowLevelBossID = new int[]{45338, 45370, 45456, 45464, 45473, 45488, 45497, 45516, 45529, 45458};
            int n = lowLevelBossID.length;
            int n2 = 0;
            while (n2 < n) {
                int npcid = nArray[n2];
                if (mob.z() == npcid) {
                    return;
                }
                ++n2;
            }
            L1PolyMorph.a(mob, polyId, 1800, 1);
        }
    }

    private void f(L1PcInstance pc, L1ItemInstance item) {
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
            L1Teleport.a(pc, item.a().aG(), item.a().aH(), item.a().aI(), 5, true);
            return;
        }
        if (pc.fp() != item.a().V()) {
            isTeleport = false;
        }
        if (isTeleport) {
            L1Teleport.a(pc, item.a().aG(), item.a().aH(), item.a().aI(), 5, true);
        } else {
            pc.a(new S_ServerMessage(79));
            pc.a(new S_Paralysis(7, false));
        }
    }

    private void a(L1PcInstance pc, int mobid) {
        for (L1Object obj : L1World.a().b()) {
            if (!(obj instanceof L1MonsterInstance) || ((L1MonsterInstance)obj).z() != mobid) continue;
            pc.a(new S_ServerMessage(79));
            return;
        }
        SpawnTable.a(mobid, pc, 0, 0L);
    }

    private void g(L1PcInstance pc, L1ItemInstance item) {
        int itemObjectId = item.fr();
        if (!pc.fq().n()) {
            pc.a(new S_ServerMessage(563));
            return;
        }
        int petCost = 0;
        for (L1NpcInstance petNpc : pc.ek().values()) {
            if (petNpc instanceof L1PetInstance && ((L1PetInstance)petNpc).k() == itemObjectId) {
                return;
            }
            petCost += petNpc.Q();
        }
        int charisma = pc.eC() + (pc.A() ? 12 : 6) - petCost;
        int petCount = charisma / 6;
        if (petCount <= 0) {
            pc.a(new S_ServerMessage(489));
            return;
        }
        if (!pc.j().b(41160, 1)) {
            pc.a(new S_ServerMessage(79));
            return;
        }
        L1Pet l1pet = PetTable.a().b(itemObjectId);
        if (l1pet != null) {
            L1Npc npcTemp = NpcTable.a().a(l1pet.c());
            L1PetInstance pet = new L1PetInstance(npcTemp, pc, l1pet);
            pet.o(6);
        }
    }

    private void a(L1PcInstance pc, int fishX, int fishY, L1ItemInstance item) {
        if (pc.fp() != 5300 && pc.fp() != 5301 && pc.fp() != 5490) {
            pc.a(new S_ServerMessage(1138));
            return;
        }
        if (pc.fe() != pc.aB()) {
            pc.a(new S_ServerMessage(1170));
            return;
        }
        if (!L1World.a().c(pc, 0).isEmpty()) {
            pc.a(new S_SystemMessage("\u9019\u500b\u4f4d\u7f6e\u5df2\u88ab\u4f54\u64da\u3002"));
            return;
        }
        if (pc.ff() && !pc.l()) {
            pc.a(new S_SystemMessage("\u96b1\u8eab\u91e3\u9b5a\u662f\u4e0d\u79d1\u5b78\u7684\u3002"));
            return;
        }
        int rodLength = 6;
        if (!pc.fq().g(fishX, fishY)) {
            pc.a(new S_ServerMessage(1138));
            return;
        }
        if (!(pc.fq().g(fishX + 1, fishY) && pc.fq().g(fishX - 1, fishY) && pc.fq().g(fishX, fishY + 1) && pc.fq().g(fishX, fishY - 1))) {
            pc.a(new S_ServerMessage(1138));
            return;
        }
        if (fishX > pc.fs() + 6 || fishX < pc.fs() - 6) {
            pc.a(new S_ServerMessage(1138));
        } else if (fishY > pc.ft() + 6 || fishY < pc.ft() - 6) {
            pc.a(new S_ServerMessage(1138));
        } else if (pc.j().b(640270, 1)) {
            pc.aO(fishX);
            pc.aP(fishY);
            pc.a(new S_Fishing(pc.fr(), 71, fishX, fishY));
            pc.b(new S_Fishing(pc.fr(), 71, fishX, fishY));
            pc.r(true);
            GeneralThreadPool.a().b(new FishingTimer(pc, item));
        } else {
            pc.a(new S_ServerMessage(1137));
        }
    }

    private void a(L1PcInstance pc, L1ItemInstance item, L1ItemInstance resolvent) {
        if ((item.g() || item.h()) && (item.D() || item.G() != 0)) {
            pc.a(new S_ServerMessage(1161));
            return;
        }
        int crystalCount = ResolventTable.a().a(item.N());
        if (crystalCount == 0) {
            pc.a(new S_ServerMessage(1161));
            return;
        }
        int rnd = Random.a(100);
        if (rnd < 50) {
            crystalCount = 0;
            pc.a(new S_ServerMessage(158, item.b()));
        } else if (rnd >= 90) {
            crystalCount = (int)((double)crystalCount * 1.5);
        }
        if (crystalCount > 0) {
            ItemTable.a(pc, 41246, crystalCount);
        }
        HistoryTable.a().b(pc, "\u7372\u5f97\u7d50\u6676" + crystalCount + "\u500b\uff0c\u878d\u6389\u4e86", item);
        pc.j().b(item, 1);
        pc.j().b(resolvent, 1);
    }

    private void b(L1PcInstance pc, int cookNo) {
        boolean isNearFire = false;
        for (L1Object obj : pc.eq()) {
            if (!(obj instanceof L1EffectInstance) || obj.f(pc) > 3 || ((L1EffectInstance)obj).fe() != 5943) continue;
            isNearFire = true;
            break;
        }
        if (!isNearFire) {
            pc.a(new S_ServerMessage(1160));
            return;
        }
        if (pc.K() <= (double)pc.j().e()) {
            pc.a(new S_ServerMessage(1103));
            return;
        }
        if (pc.bB(2999)) {
            return;
        }
        pc.j(2999, 3000);
        int chance = Random.a(100) + 1;
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
            int n = consumeID.length;
            int n2 = 0;
            while (n2 < n) {
                int itemid = nArray[n2];
                pc.j().b(itemid, 1);
                ++n2;
            }
            int gfxid = 6394;
            if (chance <= 90) {
                ItemTable.a(pc, createdID[0], 1);
                gfxid = 6392;
            } else if (chance > 95) {
                ItemTable.a(pc, createdID[1], 1);
                gfxid = 6390;
            } else {
                pc.a(new S_ServerMessage(1101));
            }
            pc.a(new S_SkillSound(pc.fr(), gfxid));
            pc.b(new S_SkillSound(pc.fr(), gfxid));
        } else {
            pc.a(new S_ServerMessage(1102));
        }
    }

    @Override
    public String a() {
        return b;
    }
}
