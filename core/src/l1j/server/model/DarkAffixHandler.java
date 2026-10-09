package l1j.server.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.DatabaseFactory;
import l1j.server.datatables.DarkLootTable;
import l1j.server.datatables.DarkLootTable.GemTemplate;
import l1j.server.model.instance.L1ItemInstance;
import l1j.server.model.instance.L1PcInstance;
import l1j.server.serverpackets.S_SkillSound;
import l1j.server.serverpackets.S_SystemMessage;
import l1j.server.utils.Random;
import l1j.server.utils.SQLUtil;

/**
 * 暗黑詞綴打寶、五大元素寶石孔洞與完美無瑕唯一限制核心處理器
 */
public class DarkAffixHandler {
   private static final Logger log = Logger.getLogger(DarkAffixHandler.class.getName());
   private static DarkAffixHandler instance;

   // 記憶體快取: itemObjId -> DarkAffixData
   private final Map<Integer, DarkAffixData> affixCache = new ConcurrentHashMap<>();

   public static class DarkAffixData {
      public int itemObjId;
      public int affixGrade;
      public String affixPrefix = "";
      public String affixSuffix = "";
      public String fullDisplayName = "";
      public boolean isIdentified;
      public String equipSlotType = "";

      public int affixStr, affixDex, affixCon, affixInt, affixWis, affixCha;
      public int affixDmg, affixHit, affixBowDmg, affixBowHit, affixSp, affixAc, affixMr;
      public int affixHp, affixMp, affixHpr, affixMpr, affixDmgReduction;
      public int affixFireRes, affixWaterRes, affixAirRes, affixEarthRes;
      public int affixCritChance;

      public int totalSockets;
      public int[] gemSockets = new int[3];
      public int replaceIndex;
   }

   public static DarkAffixHandler getInstance() {
      if (instance == null) {
         instance = new DarkAffixHandler();
      }
      return instance;
   }

   private DarkAffixHandler() {}

   public boolean isEligibleSocketSlot(L1ItemInstance item) {
      if (item == null || item.a() == null) return false;
      int itemType = item.a().aP(); // 1: weapon, 2: armor
      if (itemType == 1) return true;
      if (itemType == 2) {
         int armorType = item.a().U();
         return armorType == 2 || armorType == 18 || armorType == 19 || armorType == 20 
             || armorType == 21 || armorType == 22 || armorType == 25 || armorType == 5;
      }
      return false;
   }

   public boolean isWeapon(L1ItemInstance item) {
      return item != null && item.a() != null && item.a().aP() == 1;
   }

   public boolean isArmor(L1ItemInstance item) {
      return item != null && item.a() != null && item.a().aP() == 2;
   }

   public void rollDarkLoot(L1ItemInstance item, boolean isEliteMob, boolean isBoss) {
      if (item == null || !isEligibleSocketSlot(item)) return;

      int chance = isBoss ? 100 : (isEliteMob ? 35 : 5);
      if (Random.a(100) >= chance) return;

      DarkAffixData data = new DarkAffixData();
      data.itemObjId = item.fr();

      item.a(0);       // 強化值嚴格維持 +0
      item.c(false);   // 強制未鑑定

      int rollGrade = Random.a(100);
      if (isBoss) {
         data.affixGrade = rollGrade < 30 ? 7 : (rollGrade < 65 ? 6 : 5);
      } else if (isEliteMob) {
         data.affixGrade = rollGrade < 10 ? 6 : (rollGrade < 35 ? 5 : (rollGrade < 65 ? 4 : 3));
      } else {
         data.affixGrade = rollGrade < 5 ? 4 : (rollGrade < 25 ? 3 : (rollGrade < 60 ? 2 : 1));
      }

      if (isWeapon(item)) {
         data.totalSockets = Random.a(4); // 0~3 洞
      } else {
         data.totalSockets = Random.a(2); // 0~1 洞
      }

      int g = data.affixGrade;
      if (isWeapon(item)) {
         data.affixDmg = g * 2 + Random.a(g + 1);
         data.affixHit = g * 2;
         data.affixSp = g;
         data.affixCritChance = g;
         data.affixPrefix = "【暗黑毀滅】";
      } else {
         data.affixAc = -g;
         data.affixMr = g * 2;
         data.affixHp = g * 20;
         data.affixDmgReduction = g;
         data.affixPrefix = "【暗黑守護】";
      }

      data.isIdentified = false;
      data.fullDisplayName = getGradeColor(data.affixGrade) + data.affixPrefix + item.a().c();

      save(data);
      affixCache.put(data.itemObjId, data);
   }

   private String getGradeColor(int grade) {
      switch (grade) {
         case 1: return "\\aE";
         case 2: return "\\aG";
         case 3: return "\\aC";
         case 4: return "\\fR";
         case 5: return "\\fO";
         case 6: return "\\fV";
         case 7: return "\\fW";
         default: return "\\fU";
      }
   }

   public void onIdentify(L1PcInstance pc, L1ItemInstance item) {
      if (item == null) return;
      DarkAffixData data = getAffixData(item.fr());
      if (data == null) return;

      data.isIdentified = true;
      save(data);

      StringBuilder sb = new StringBuilder();
      sb.append("\\fU【暗黑屬性明細】");
      if (data.affixDmg > 0) sb.append(" 近攻+").append(data.affixDmg);
      if (data.affixHit > 0) sb.append(" 近命+").append(data.affixHit);
      if (data.affixSp > 0) sb.append(" SP+").append(data.affixSp);
      if (data.affixAc != 0) sb.append(" AC").append(data.affixAc);
      if (data.affixMr > 0) sb.append(" MR+").append(data.affixMr);
      if (data.affixHp > 0) sb.append(" HP+").append(data.affixHp);
      if (data.affixDmgReduction > 0) sb.append(" 減免+").append(data.affixDmgReduction);
      sb.append(" 【孔洞: ").append(data.totalSockets).append("】");

      pc.a(new S_SystemMessage(sb.toString()));

      if (data.affixGrade >= 6) {
         String msg = "\\fV【全服傳奇誕生】恭喜玩家 [" + pc.et() + "] 鑑定了神級裝備 " + data.fullDisplayName + "！";
         for (L1PcInstance p : L1World.a().c()) {
            p.a(new S_SystemMessage(msg));
         }
      }
   }

   public boolean socketGem(L1PcInstance pc, L1ItemInstance gemItem, L1ItemInstance targetItem) {
      if (pc == null || gemItem == null || targetItem == null) return false;

      DarkLootTable lootTable = DarkLootTable.getInstance();
      int gemItemId = gemItem.N();
      GemTemplate gem = lootTable.getGemTemplate(gemItemId);
      if (gem == null) {
         pc.a(new S_SystemMessage("\\fR【系統】該物品不是可鑲嵌的寶石！"));
         return false;
      }

      if (!isEligibleSocketSlot(targetItem)) {
         pc.a(new S_SystemMessage("\\fR【系統】該部位無法進行寶石鑲嵌（僅限武器與 8 大防具）！"));
         return false;
      }

      if (gemItemId == 50060 && !isArmor(targetItem)) {
         pc.a(new S_SystemMessage("\\fR【系統】完美無瑕的寶石只能鑲嵌在防具上！"));
         return false;
      }

      DarkAffixData data = getOrCreateAffixData(targetItem);
      if (data.totalSockets <= 0) {
         pc.a(new S_SystemMessage("\\fR【系統】該裝備沒有孔洞，無法鑲嵌寶石！"));
         return false;
      }

      int targetSocketIndex = -1;
      for (int i = 0; i < data.totalSockets; i++) {
         if (data.gemSockets[i] == 0) {
            targetSocketIndex = i;
            break;
         }
      }

      if (targetSocketIndex == -1) {
         targetSocketIndex = data.replaceIndex;
         data.replaceIndex = (data.replaceIndex + 1) % data.totalSockets;
      }

      int oldGem = data.gemSockets[targetSocketIndex];
      data.gemSockets[targetSocketIndex] = gemItemId;
      save(data);

      pc.j().a(gemItem, 1);
      pc.a(new S_SkillSound(pc.fr(), 4443));
      pc.b(new S_SkillSound(pc.fr(), 4443));

      String replaceNotice = oldGem > 0 ? "（已循環替換第 " + targetSocketIndex + " 孔之舊寶石）" : "";
      pc.a(new S_SystemMessage("\\aG【鑲嵌成功】已將 " + gem.gemName + " 鑲嵌至第 " + targetSocketIndex + " 孔！" + replaceNotice));
      return true;
   }

   public void onEquip(L1PcInstance pc, L1ItemInstance item) {
      if (pc == null || item == null) return;
      DarkAffixData data = getAffixData(item.fr());
      if (data == null) return;

      boolean isWeapon = isWeapon(item);

      // 1. 暗黑詞綴屬性增加
      applyStats(pc, data.affixStr, data.affixDex, data.affixCon, data.affixInt, data.affixWis, data.affixCha,
                 data.affixDmg, data.affixHit, data.affixSp, data.affixAc, data.affixMr, data.affixHp, data.affixMp, data.affixDmgReduction);

      // 2. 寶石加成
      DarkLootTable lootTable = DarkLootTable.getInstance();
      for (int i = 0; i < data.totalSockets; i++) {
         int gemId = data.gemSockets[i];
         if (gemId == 0) continue;

         if (gemId == 50060) {
            int count = countEquippedFlawlessGems(pc);
            if (count > 1) {
               pc.a(new S_SystemMessage("\\fR【系統】同類型的完美無瑕寶石能力全身僅能生效一件！"));
               continue;
            }
         }

         GemTemplate g = lootTable.getGemTemplate(gemId);
         if (g != null) {
            if (isWeapon) {
               applyStats(pc, g.wStr, g.wDex, g.wCon, g.wInt, 0, 0, g.wDmg, g.wHit, g.wSp, 0, 0, 0, 0, 0);
            } else {
               int allStat = g.aAllStat > 0 ? 1 : 0;
               applyStats(pc, g.aStr + allStat, g.aDex + allStat, g.aCon + allStat, g.aInt + allStat, allStat, allStat,
                          g.aDmg, g.aHit, g.aSp, g.aAc, g.aMr, g.aHp, g.aMp, g.aDmgReduction);
            }
         }
      }
   }

   public void onRemoveEquip(L1PcInstance pc, L1ItemInstance item) {
      if (pc == null || item == null) return;
      DarkAffixData data = getAffixData(item.fr());
      if (data == null) return;

      boolean isWeapon = isWeapon(item);

      // 1. 暗黑詞綴屬性扣除
      applyStats(pc, -data.affixStr, -data.affixDex, -data.affixCon, -data.affixInt, -data.affixWis, -data.affixCha,
                 -data.affixDmg, -data.affixHit, -data.affixSp, -data.affixAc, -data.affixMr, -data.affixHp, -data.affixMp, -data.affixDmgReduction);

      // 2. 寶石加成扣除
      DarkLootTable lootTable = DarkLootTable.getInstance();
      for (int i = 0; i < data.totalSockets; i++) {
         int gemId = data.gemSockets[i];
         if (gemId == 0) continue;

         GemTemplate g = lootTable.getGemTemplate(gemId);
         if (g != null) {
            if (isWeapon) {
               applyStats(pc, -g.wStr, -g.wDex, -g.wCon, -g.wInt, 0, 0, -g.wDmg, -g.wHit, -g.wSp, 0, 0, 0, 0, 0);
            } else {
               int allStat = g.aAllStat > 0 ? 1 : 0;
               applyStats(pc, -(g.aStr + allStat), -(g.aDex + allStat), -(g.aCon + allStat), -(g.aInt + allStat), -allStat, -allStat,
                          -g.aDmg, -g.aHit, -g.aSp, -g.aAc, -g.aMr, -g.aHp, -g.aMp, -g.aDmgReduction);
            }
         }
      }
   }

   private void applyStats(L1PcInstance pc, int str, int dex, int con, int intel, int wis, int cha,
                           int dmg, int hit, int sp, int ac, int mr, int hp, int mp, int dr) {
      if (str != 0) pc.cd(str);
      if (dex != 0) pc.ce(dex);
      if (con != 0) pc.cf(con);
      if (intel != 0) pc.ch(intel);
      if (wis != 0) pc.ci(wis);
      if (cha != 0) pc.cj(cha);
      if (dmg != 0) pc.bY(dmg);
      if (hit != 0) pc.bZ(hit);
      if (sp != 0) pc.cb(sp);
      if (ac != 0) pc.bL(ac);
      if (mr != 0) pc.F(mr);
      if (hp != 0) pc.C(hp);
      if (mp != 0) pc.D(mp);
      if (dr != 0) pc.ca(dr);
   }

   private int countEquippedFlawlessGems(L1PcInstance pc) {
      int count = 0;
      for (L1ItemInstance eq : pc.j().d()) {
         if (eq.D()) {
            DarkAffixData d = getAffixData(eq.fr());
            if (d != null) {
               for (int g : d.gemSockets) {
                  if (g == 50060) count++;
               }
            }
         }
      }
      return count;
   }

   public DarkAffixData getAffixData(int itemObjId) {
      if (affixCache.containsKey(itemObjId)) {
         return affixCache.get(itemObjId);
      }
      DarkAffixData data = load(itemObjId);
      if (data != null) {
         affixCache.put(itemObjId, data);
      }
      return data;
   }

   public DarkAffixData getOrCreateAffixData(L1ItemInstance item) {
      DarkAffixData data = getAffixData(item.fr());
      if (data == null) {
         data = new DarkAffixData();
         data.itemObjId = item.fr();
         if (isWeapon(item)) {
            data.totalSockets = Random.a(4);
         } else if (isArmor(item)) {
            data.totalSockets = Random.a(2);
         }
         save(data);
         affixCache.put(data.itemObjId, data);
      }
      return data;
   }

   private DarkAffixData load(int itemObjId) {
      Connection con = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;

      try {
         con = DatabaseFactory.a().b();
         pstm = con.prepareStatement("SELECT * FROM character_items_dark_affix WHERE item_obj_id = ?");
         pstm.setInt(1, itemObjId);
         rs = pstm.executeQuery();

         if (rs.next()) {
            DarkAffixData d = new DarkAffixData();
            d.itemObjId = rs.getInt("item_obj_id");
            d.affixGrade = rs.getInt("affix_grade");
            d.affixPrefix = rs.getString("affix_prefix");
            d.affixSuffix = rs.getString("affix_suffix");
            d.fullDisplayName = rs.getString("full_display_name");
            d.isIdentified = rs.getInt("is_identified") == 1;
            d.equipSlotType = rs.getString("equip_slot_type");

            d.affixStr = rs.getInt("affix_str");
            d.affixDex = rs.getInt("affix_dex");
            d.affixCon = rs.getInt("affix_con");
            d.affixInt = rs.getInt("affix_int");
            d.affixWis = rs.getInt("affix_wis");
            d.affixCha = rs.getInt("affix_cha");
            d.affixDmg = rs.getInt("affix_dmg");
            d.affixHit = rs.getInt("affix_hit");
            d.affixBowDmg = rs.getInt("affix_bow_dmg");
            d.affixBowHit = rs.getInt("affix_bow_hit");
            d.affixSp = rs.getInt("affix_sp");
            d.affixAc = rs.getInt("affix_ac");
            d.affixMr = rs.getInt("affix_mr");
            d.affixHp = rs.getInt("affix_hp");
            d.affixMp = rs.getInt("affix_mp");
            d.affixHpr = rs.getInt("affix_hpr");
            d.affixMpr = rs.getInt("affix_mpr");
            d.affixDmgReduction = rs.getInt("affix_dmg_reduction");
            d.affixFireRes = rs.getInt("affix_fire_res");
            d.affixWaterRes = rs.getInt("affix_water_res");
            d.affixAirRes = rs.getInt("affix_air_res");
            d.affixEarthRes = rs.getInt("affix_earth_res");
            d.affixCritChance = rs.getInt("affix_crit_chance");

            d.totalSockets = rs.getInt("total_sockets");
            d.gemSockets[0] = rs.getInt("gem_socket_0");
            d.gemSockets[1] = rs.getInt("gem_socket_1");
            d.gemSockets[2] = rs.getInt("gem_socket_2");
            d.replaceIndex = rs.getInt("replace_index");
            return d;
         }
      } catch (SQLException e) {
         log.log(Level.SEVERE, "Failed loading character_items_dark_affix: " + e.getMessage(), e);
      } finally {
         SQLUtil.a(rs, pstm, con);
      }
      return null;
   }

   public void save(DarkAffixData d) {
      Connection con = null;
      PreparedStatement pstm = null;

      try {
         con = DatabaseFactory.a().b();
         String sql = "INSERT INTO character_items_dark_affix ("
             + "item_obj_id, affix_grade, affix_prefix, affix_suffix, full_display_name, is_identified, equip_slot_type, "
             + "affix_str, affix_dex, affix_con, affix_int, affix_wis, affix_cha, affix_dmg, affix_hit, affix_bow_dmg, affix_bow_hit, "
             + "affix_sp, affix_ac, affix_mr, affix_hp, affix_mp, affix_hpr, affix_mpr, affix_dmg_reduction, affix_fire_res, "
             + "affix_water_res, affix_air_res, affix_earth_res, affix_crit_chance, total_sockets, gem_socket_0, gem_socket_1, gem_socket_2, replace_index) "
             + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
             + "ON DUPLICATE KEY UPDATE "
             + "affix_grade=VALUES(affix_grade), full_display_name=VALUES(full_display_name), is_identified=VALUES(is_identified), "
             + "total_sockets=VALUES(total_sockets), gem_socket_0=VALUES(gem_socket_0), gem_socket_1=VALUES(gem_socket_1), "
             + "gem_socket_2=VALUES(gem_socket_2), replace_index=VALUES(replace_index)";

         pstm = con.prepareStatement(sql);
         pstm.setInt(1, d.itemObjId);
         pstm.setInt(2, d.affixGrade);
         pstm.setString(3, d.affixPrefix);
         pstm.setString(4, d.affixSuffix);
         pstm.setString(5, d.fullDisplayName);
         pstm.setInt(6, d.isIdentified ? 1 : 0);
         pstm.setString(7, d.equipSlotType);

         pstm.setInt(8, d.affixStr);
         pstm.setInt(9, d.affixDex);
         pstm.setInt(10, d.affixCon);
         pstm.setInt(11, d.affixInt);
         pstm.setInt(12, d.affixWis);
         pstm.setInt(13, d.affixCha);
         pstm.setInt(14, d.affixDmg);
         pstm.setInt(15, d.affixHit);
         pstm.setInt(16, d.affixBowDmg);
         pstm.setInt(17, d.affixBowHit);
         pstm.setInt(18, d.affixSp);
         pstm.setInt(19, d.affixAc);
         pstm.setInt(20, d.affixMr);
         pstm.setInt(21, d.affixHp);
         pstm.setInt(22, d.affixMp);
         pstm.setInt(23, d.affixHpr);
         pstm.setInt(24, d.affixMpr);
         pstm.setInt(25, d.affixDmgReduction);
         pstm.setInt(26, d.affixFireRes);
         pstm.setInt(27, d.affixWaterRes);
         pstm.setInt(28, d.affixAirRes);
         pstm.setInt(29, d.affixEarthRes);
         pstm.setInt(30, d.affixCritChance);

         pstm.setInt(31, d.totalSockets);
         pstm.setInt(32, d.gemSockets[0]);
         pstm.setInt(33, d.gemSockets[1]);
         pstm.setInt(34, d.gemSockets[2]);
         pstm.setInt(35, d.replaceIndex);

         pstm.executeUpdate();
      } catch (SQLException e) {
         log.log(Level.SEVERE, "Failed saving character_items_dark_affix: " + e.getMessage(), e);
      } finally {
         SQLUtil.a(null, pstm, con);
      }
   }
}
