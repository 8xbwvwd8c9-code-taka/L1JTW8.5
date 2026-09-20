package l1r.aq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ItemTable;
import l1r.ap.L1DollInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_ChangeName;
import l1r.be.S_CharTitle;
import l1r.be.S_HPUpdate;
import l1r.be.S_MPUpdate;
import l1r.be.S_PacketBox;
import l1r.be.S_SPMR;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1GuardianSoul {
   private static final Logger b = Logger.getLogger(L1GuardianSoul.class.getName());
   private static L1GuardianSoul c;
   private final long d = 86400000L;
   public boolean a = false;

   public static L1GuardianSoul a() {
      if (c == null) {
         c = new L1GuardianSoul();
      }

      return c;
   }

   private L1GuardianSoul() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM character_items WHERE item_id=?");
         var2.setInt(1, 640102);
         var3 = var2.executeQuery();

         while (var3.next() && !this.a) {
            int var4 = var3.getInt("char_id");
            Timestamp var5 = var3.getTimestamp("last_used");
            long var6 = System.currentTimeMillis() - var5.getTime();
            if (var6 > 86400000L) {
               this.a(640102);
            } else {
               this.a(var4, var6);
            }
         }
      } catch (SQLException var11) {
         b.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(int var1, long var2) {
      this.a = true;
      long var4 = 86400000L - var2;
      if (var2 == 0L) {
         L1World.a().a(new S_PacketBox(84, 2, "\\f=有人獲得了守護者的靈魂。"));
      }

      GeneralThreadPool.a().a(new L1GuardianSoul.L1R_a(var1), var4);
      System.out.println("任務:【守護之魂】剩餘時間 " + var4 / 1000L + " 秒");
   }

   private void a(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM character_items WHERE item_id =" + var1);
         var3.execute();
         System.out.println("道具:【守護者的靈魂】的持有紀錄從資料庫移除了");
      } catch (SQLException var8) {
         b.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void a(L1MonsterInstance var1) {
      if (!this.a) {
         int var2 = 1 + var1.ev() / 2;
         int var3 = Random.a(100000);
         if (var3 < var2) {
            L1ItemInstance var4 = ItemTable.a().b(640102);
            var1.y().d(var4);
         }
      }
   }

   private class L1R_a extends TimerTask {
      private final int b;

      public L1R_a(int var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         try {
            L1GuardianSoul.this.a = false;
            L1World.a().a(new S_PacketBox(84, 2, "\\f=守護者的靈魂消逝了。"));
            L1Object var1 = L1World.a().a(this.b);
            if (var1 instanceof L1PcInstance) {
               L1PcInstance var2 = (L1PcInstance)var1;
               L1ItemInstance var3 = var2.j().b(640102);
               if (var3 != null) {
                  var2.j().b(var3, var3.E());
               }

               var2.bz(4058);
               var2.cp(-30);
               var2.a(new S_SPMR(var2));
               var2.bH(-400);
               var2.bJ(-200);
               var2.a(new S_MPUpdate(var2.eb(), var2.ex()));
               var2.a(new S_HPUpdate(var2.ea(), var2.ew()));
               if (var2.q()) {
                  var2.aL().f(var2);
               }

               var2.a(new S_PacketBox(144, 0));
               var2.a(new S_ChangeName(var2.fr(), var2.et()));
               var2.b(new S_ChangeName(var2.fr(), var2.et()));

               for (L1NpcInstance var4 : var2.ek().values()) {
                  var4.b(new S_CharTitle(var4.fr(), var2.et() + "的"));
               }

               for (L1DollInstance var7 : var2.el().values()) {
                  var7.b(new S_CharTitle(var7.fr(), var2.et() + "的"));
               }
            } else {
               L1GuardianSoul.this.a(640102);
            }
         } catch (Throwable var6) {
            L1GuardianSoul.b.log(Level.SEVERE, var6.getLocalizedMessage(), var6);
         }
      }
   }
}
