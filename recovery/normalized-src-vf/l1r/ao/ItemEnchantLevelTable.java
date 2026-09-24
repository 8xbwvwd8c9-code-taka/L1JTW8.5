package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_SPMR;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

/**
 * 裝武強化等級能力加成數據管理表 (ItemEnchantLevelTable)
 * 對應企劃：L2,w_裝武強化lv,N/A
 * 支援武器/防具不同強化等級各項屬性加成與穿脫裝結算
 */
public class ItemEnchantLevelTable {
   private static final Logger a = Logger.getLogger(ItemEnchantLevelTable.class.getName());
   private static ItemEnchantLevelTable b;
   private final Map<String, EnchantBonus> c = new HashMap<>();

   public static class EnchantBonus {
      public int id;
      public String note;
      public int type; // 1: weapon, 2: armor
      public int itemid;
      public int level;
      public int addStr;
      public int addDex;
      public int addCon;
      public int addInt;
      public int addWis;
      public int addCha;
      public int addAc;
      public int addMaxHp;
      public int addMaxMp;
      public int addHpr;
      public int addMpr;
      public int addDmg;
      public int addBowDmg;
      public int addHit;
      public int addBowHit;
      public int addDmgReduction;
      public int addMr;
      public int addSp;
      public int pvpDmg;
      public int pvpDmgReduction;
      public int potionHeal;
      public int potionHealling;
      public int addMagicHit;
      public double weaponSkillDmg;
      public int weaponSkillChance;
   }

   public static ItemEnchantLevelTable a() {
      if (b == null) {
         b = new ItemEnchantLevelTable();
      }
      return b;
   }

   private ItemEnchantLevelTable() {
      load();
   }

   public void load() {
      this.c.clear();
      Connection con = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;

      try {
         con = DatabaseFactory.a().b();
         pstm = con.prepareStatement("SELECT * FROM w_item_enchant_level_bonus");
         rs = pstm.executeQuery();

         while (rs.next()) {
            EnchantBonus bonus = new EnchantBonus();
            bonus.id = rs.getInt("id");
            bonus.note = rs.getString("note");
            bonus.type = rs.getInt("type");
            bonus.itemid = rs.getInt("itemid");
            bonus.level = rs.getInt("level");
            bonus.addStr = rs.getInt("addStr");
            bonus.addDex = rs.getInt("addDex");
            bonus.addCon = rs.getInt("addCon");
            bonus.addInt = rs.getInt("addInt");
            bonus.addWis = rs.getInt("addWis");
            bonus.addCha = rs.getInt("addCha");
            bonus.addAc = rs.getInt("addAc");
            bonus.addMaxHp = rs.getInt("addMaxHp");
            bonus.addMaxMp = rs.getInt("addMaxMp");
            bonus.addHpr = rs.getInt("addHpr");
            bonus.addMpr = rs.getInt("addMpr");
            bonus.addDmg = rs.getInt("addDmg");
            bonus.addBowDmg = rs.getInt("addBowDmg");
            bonus.addHit = rs.getInt("addHit");
            bonus.addBowHit = rs.getInt("addBowHit");
            bonus.addDmgReduction = rs.getInt("addDmgReduction");
            bonus.addMr = rs.getInt("addMr");
            bonus.addSp = rs.getInt("addSp");
            bonus.pvpDmg = rs.getInt("PVPdmg");
            bonus.pvpDmgReduction = rs.getInt("PVPdmgReduction");
            bonus.potionHeal = rs.getInt("Potion_Heal");
            bonus.potionHealling = rs.getInt("Potion_Healling");
            bonus.addMagicHit = rs.getInt("add_magic_hit");
            bonus.weaponSkillDmg = rs.getDouble("weaponSkillDmg");
            bonus.weaponSkillChance = rs.getInt("weaponSkillChance");

            String key = makeKey(bonus.itemid, bonus.level, bonus.type);
            this.c.put(key, bonus);
         }
         System.out.println("載入裝武強化等級加成資料數量: " + this.c.size());
      } catch (SQLException e) {
         a.log(Level.WARNING, "w_item_enchant_level_bonus 讀取失敗，嘗試相容讀取 w_裝武強化lv: " + e.getMessage());
         loadFallback();
      } finally {
         SQLUtil.a(rs, pstm, con);
      }
   }

   private void loadFallback() {
      Connection con = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;

      try {
         con = DatabaseFactory.a().b();
         pstm = con.prepareStatement("SELECT * FROM w_裝武強化lv");
         rs = pstm.executeQuery();

         while (rs.next()) {
            EnchantBonus bonus = new EnchantBonus();
            bonus.type = rs.getInt("type");
            bonus.itemid = rs.getInt("itemid");
            bonus.level = rs.getInt("level");
            bonus.addStr = rs.getInt("addStr");
            bonus.addDex = rs.getInt("addDex");
            bonus.addCon = rs.getInt("addCon");
            bonus.addInt = rs.getInt("addInt");
            bonus.addWis = rs.getInt("addWis");
            bonus.addCha = rs.getInt("addCha");
            bonus.addAc = rs.getInt("addAc");
            bonus.addMaxHp = rs.getInt("addMaxHp");
            bonus.addMaxMp = rs.getInt("addMaxMp");
            bonus.addHpr = rs.getInt("addHpr");
            bonus.addMpr = rs.getInt("addMpr");
            bonus.addDmg = rs.getInt("addDmg");
            bonus.addBowDmg = rs.getInt("addBowDmg");
            bonus.addHit = rs.getInt("addHit");
            bonus.addBowHit = rs.getInt("addBowHit");
            bonus.addDmgReduction = rs.getInt("addDmgReduction");
            bonus.addMr = rs.getInt("addMr");
            bonus.addSp = rs.getInt("addSp");
            bonus.pvpDmg = rs.getInt("PVPdmg");
            bonus.pvpDmgReduction = rs.getInt("PVPdmgReduction");
            bonus.potionHeal = rs.getInt("Potion_Heal");
            bonus.potionHealling = rs.getInt("Potion_Healling");
            bonus.addMagicHit = rs.getInt("add_magic_hit");
            bonus.weaponSkillDmg = rs.getDouble("weaponSkillDmg");
            bonus.weaponSkillChance = rs.getInt("weaponSkillChance");

            String key = makeKey(bonus.itemid, bonus.level, bonus.type);
            this.c.put(key, bonus);
         }
         System.out.println("相容載入 w_裝武強化lv 資料數量: " + this.c.size());
      } catch (Exception e) {
         a.log(Level.SEVERE, "loadFallback 失敗: " + e.getMessage(), e);
      } finally {
         SQLUtil.a(rs, pstm, con);
      }
   }

   public static String makeKey(int itemId, int enchantLevel, int type) {
      return itemId + "_" + enchantLevel + "_" + type;
   }

   public EnchantBonus getBonus(int itemId, int enchantLevel, int type) {
      return this.c.get(makeKey(itemId, enchantLevel, type));
   }

   public void onEquip(L1PcInstance pc, L1ItemInstance item) {
      if (Config.ItemEnchantLevelBonusSwitch != 1 || pc == null || item == null) {
         return;
      }
      int type = item.g() ? 1 : 2;
      EnchantBonus bonus = getBonus(item.N(), item.G(), type);
      if (bonus == null) {
         return;
      }

      applyBonusStats(pc, bonus, 1);
      pc.a(new S_SPMR(pc));
   }

   public void onUnequip(L1PcInstance pc, L1ItemInstance item) {
      if (Config.ItemEnchantLevelBonusSwitch != 1 || pc == null || item == null) {
         return;
      }
      int type = item.g() ? 1 : 2;
      EnchantBonus bonus = getBonus(item.N(), item.G(), type);
      if (bonus == null) {
         return;
      }

      applyBonusStats(pc, bonus, -1);
      pc.a(new S_SPMR(pc));
   }

   private void applyBonusStats(L1PcInstance pc, EnchantBonus b, int sign) {
      if (b.addStr != 0) {
         pc.bN(b.addStr * sign);
      }
      if (b.addDex != 0) {
         pc.bR(b.addDex * sign);
      }
      if (b.addCon != 0) {
         pc.bP(b.addCon * sign);
      }
      if (b.addInt != 0) {
         pc.bV(b.addInt * sign);
      }
      if (b.addWis != 0) {
         pc.bX(b.addWis * sign);
      }
      if (b.addCha != 0) {
         pc.bT(b.addCha * sign);
      }

      // AC：防禦力增加（越小越硬），穿上時 -addAc，脫下時 +addAc
      if (b.addAc != 0) {
         pc.bL(-b.addAc * sign);
      }

      if (b.addMaxHp != 0) {
         pc.F(b.addMaxHp * sign);
      }
      if (b.addMaxMp != 0) {
         pc.D(b.addMaxMp * sign);
      }
      if (b.addHpr != 0) {
         pc.G(b.addHpr * sign);
      }
      if (b.addMpr != 0) {
         pc.H(b.addMpr * sign);
      }

      if (b.addHit != 0) {
         pc.co(b.addHit * sign);
      }
      if (b.addDmg != 0) {
         pc.cp(b.addDmg * sign);
      }
      if (b.addBowHit != 0) {
         pc.ab(b.addBowHit * sign);
      }
      if (b.addBowDmg != 0) {
         pc.aa(b.addBowDmg * sign);
      }

      if (b.addDmgReduction != 0) {
         pc.X(b.addDmgReduction * sign);
      }
      if (b.addMr != 0) {
         pc.U(b.addMr * sign);
      }
      if (b.addSp != 0) {
         pc.Q(b.addSp * sign);
      }
   }
}
