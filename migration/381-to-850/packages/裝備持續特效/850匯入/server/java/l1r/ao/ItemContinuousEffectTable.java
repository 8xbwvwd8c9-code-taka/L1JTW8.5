package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_SkillSound;
import l1r.bi.GeneralThreadPool;
import l1r.bi.SQLUtil;
import l1r.l1j.server.Config;
import l1r.l1j.server.DatabaseFactory;

/**
 * 裝備持續特效管理表 (ItemContinuousEffectTable)
 * 對應企劃：L4,w_裝備持續特效,N/A
 * 支援穿戴特定神裝、高強化裝備(+8/+9/+10)之定時視野光環特效廣播
 */
public class ItemContinuousEffectTable {
   private static final Logger a = Logger.getLogger(ItemContinuousEffectTable.class.getName());
   private static ItemContinuousEffectTable b;
   private final List<ContinuousEffect> c = new ArrayList<>();
   private boolean timerStarted = false;

   public static class ContinuousEffect {
      public int id;
      public int itemId;        // 0=全域所有裝備門檻
      public int enchantLevel;   // 需求強化等級(>=該等級生效, 0=任意強化)
      public int effectGfx;      // 特效GFX ID
      public int intervalSec;    // 播放間隔(秒)
      public String note;
   }

   public static ItemContinuousEffectTable a() {
      if (b == null) {
         b = new ItemContinuousEffectTable();
      }
      return b;
   }

   private ItemContinuousEffectTable() {
      load();
      startTimer();
   }

   public void load() {
      this.c.clear();
      Connection con = null;
      PreparedStatement pstm = null;
      ResultSet rs = null;

      try {
         con = DatabaseFactory.a().b();
         pstm = con.prepareStatement("SELECT * FROM w_item_continuous_effect");
         rs = pstm.executeQuery();

         while (rs.next()) {
            ContinuousEffect eff = new ContinuousEffect();
            eff.id = rs.getInt("id");
            eff.itemId = rs.getInt("item_id");
            eff.enchantLevel = rs.getInt("enchant_level");
            eff.effectGfx = rs.getInt("effect_gfx");
            eff.intervalSec = rs.getInt("interval_sec");
            eff.note = rs.getString("note");
            this.c.add(eff);
         }
         System.out.println("載入裝備持續特效設定數量: " + this.c.size());
      } catch (SQLException e) {
         a.log(Level.WARNING, "w_item_continuous_effect 讀取失敗，嘗試相容讀取 w_裝備持續特效: " + e.getMessage());
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
         pstm = con.prepareStatement("SELECT * FROM w_裝備持續特效");
         rs = pstm.executeQuery();

         while (rs.next()) {
            String armorIdStr = rs.getString("armor_id");
            int gfxId = rs.getInt("gfxId");
            if (armorIdStr != null && !armorIdStr.isEmpty() && !"0".equals(armorIdStr)) {
               String[] ids = armorIdStr.split(",");
               for (String idPart : ids) {
                  try {
                     ContinuousEffect eff = new ContinuousEffect();
                     eff.itemId = Integer.parseInt(idPart.trim());
                     eff.enchantLevel = 0;
                     eff.effectGfx = gfxId;
                     eff.intervalSec = 15;
                     eff.note = "381相容特效";
                     this.c.add(eff);
                  } catch (NumberFormatException ignored) {}
               }
            } else {
               ContinuousEffect eff = new ContinuousEffect();
               eff.itemId = 0;
               eff.enchantLevel = 0;
               eff.effectGfx = gfxId;
               eff.intervalSec = 15;
               eff.note = "381相容特效";
               this.c.add(eff);
            }
         }
         System.out.println("相容載入 w_裝備持續特效 數量: " + this.c.size());
      } catch (Exception e) {
         a.log(Level.SEVERE, "loadFallback 裝備持續特效失敗: " + e.getMessage(), e);
      } finally {
         SQLUtil.a(rs, pstm, con);
      }
   }

   public synchronized void startTimer() {
      if (this.timerStarted) {
         return;
      }
      if (Config.EquipmentContinuousEffectSwitch != 1) {
         return;
      }
      this.timerStarted = true;
      long intervalMillis = Math.max(1, Config.EquipmentContinuousEffectInterval) * 1000L;
      GeneralThreadPool.a().a(new ContinuousEffectTask(), intervalMillis, intervalMillis);
      System.out.println("裝備持續特效調度計時器啟動，間隔: " + (intervalMillis / 1000L) + " 秒");
   }

   private class ContinuousEffectTask implements Runnable {
      @Override
      public void run() {
         try {
            if (Config.EquipmentContinuousEffectSwitch != 1 || c.isEmpty()) {
               return;
            }

            for (L1PcInstance pc : L1World.a().c()) {
               if (pc == null || pc.j() == null) {
                  continue;
               }

               Set<Integer> playedGfx = new HashSet<>();

               for (L1ItemInstance item : pc.j().d()) {
                  if (item == null || !item.D()) {
                     continue; // 僅檢查裝備中的物品
                  }

                  int itemId = item.N();
                  int enchantLevel = item.G();

                  for (ContinuousEffect eff : c) {
                     if (eff.effectGfx <= 0 || playedGfx.contains(eff.effectGfx)) {
                        continue;
                     }

                     boolean match = false;
                     // 1. 全域強化等級門檻
                     if (eff.itemId == 0 && eff.enchantLevel > 0 && enchantLevel >= eff.enchantLevel) {
                        match = true;
                     }
                     // 2. 指定道具ID + 強化等級門檻
                     else if (eff.itemId == itemId) {
                        if (eff.enchantLevel == 0 || enchantLevel >= eff.enchantLevel) {
                           match = true;
                        }
                     }

                     if (match) {
                        playedGfx.add(eff.effectGfx);
                        pc.a(new S_SkillSound(pc.fr(), eff.effectGfx));
                        pc.b(new S_SkillSound(pc.fr(), eff.effectGfx));
                     }
                  }
               }
            }
         } catch (Exception e) {
            a.log(Level.WARNING, "裝備持續特效定時廣播異常: " + e.getMessage(), e);
         }
      }
   }
}
