package l1r.ba;

import java.util.Calendar;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.GameServer;
import l1r.ao.CharacterMobsWeekTable;
import l1r.ao.CharacterTable;
import l1r.ao.MobQuestWeekTable;
import l1r.ao.RankingTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_ProtoBuffers;
import l1r.bi.GeneralThreadPool;
import l1r.l1j.server.Config;

public class CurrentTimeReseter {
   private static final Logger a = Logger.getLogger(CurrentTimeReseter.class.getName());
   private static CurrentTimeReseter b;

   public static CurrentTimeReseter a() {
      if (b == null) {
         b = new CurrentTimeReseter();
      }

      return b;
   }

   public CurrentTimeReseter() {
      this.d();
      this.e();
      if (Config.y) {
         this.c();
      }
   }

   private void c() {
      int var1 = Config.z;
      int var2 = Calendar.getInstance().get(11);
      int var3 = Calendar.getInstance().get(12);
      long var4 = 0L;
      if (var2 >= var1) {
         var4 = (1440 + ((var1 - var2) * 60 - var3)) * 60 * 1000;
      } else {
         var4 = ((var1 - var2) * 60 - var3) * 60 * 1000;
      }

      System.out.println("伺服器重置:距離執行時間還有..." + var4 / 1000L / 60L + "分");
      GeneralThreadPool.a().a(new CurrentTimeReseter.L1R_b(null), var4);
   }

   private void d() {
      int var1 = 9;
      int var2 = Calendar.getInstance().get(11);
      int var3 = Calendar.getInstance().get(12);
      long var4 = 0L;
      if (var2 >= 9) {
         var4 = (1440 + ((9 - var2) * 60 - var3)) * 60 * 1000;
      } else {
         var4 = ((9 - var2) * 60 - var3) * 60 * 1000;
      }

      System.out.println("計時地圖/排行榜重置:距離執行時間還有..." + var4 / 1000L / 60L + "分");
      GeneralThreadPool.a().a(new CurrentTimeReseter.L1R_a(null), var4);
   }

   private void e() {
      int var1 = 23;
      int var2 = Calendar.getInstance().get(11);
      int var3 = Calendar.getInstance().get(12);
      int var4 = 0;
      int var5 = Calendar.getInstance().get(7);
      if (Calendar.getInstance().getFirstDayOfWeek() == 1) {
         var5--;
      }

      long var6 = 0L;
      if (var5 > 0) {
         var6 = (7 - var5 - 1) * 24 * 60 * 60 * 1000;
      }

      if (var2 >= 23) {
         var6 += (1440 + ((23 - var2) * 60 - var3)) * 60 * 1000;
      } else {
         var6 += ((23 - var2) * 60 - var3) * 60 * 1000;
      }

      System.out.println("每週任務重置:距離執行時間還有..." + var6 / 1000L / 60L + "分");
      GeneralThreadPool.a().a(new CurrentTimeReseter.L1R_c(null), var6);
   }

   private class L1R_a extends TimerTask {
      private L1R_a() {
      }

      @Override
      public void run() {
         try {
            for (L1PcInstance var1 : L1World.a().c()) {
               var1.ap();
               var1.y(false);
            }

            CharacterTable.a().b();
            System.out.println("[計時地圖重置了]");
            RankingTable.b();
            System.out.println("[排行榜排名更新了]");
         } catch (Exception var3) {
            CurrentTimeReseter.a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         } finally {
            System.out.println("計時地圖/排行榜重置:距離執行時間還有...1440分");
            GeneralThreadPool.a().a(CurrentTimeReseter.this.new L1R_a(), 86400000L);
         }
      }

      // $VF: synthetic method
      L1R_a(CurrentTimeReseter.L1R_a var2) {
         this();
      }
   }

   private class L1R_b extends TimerTask {
      private L1R_b() {
      }

      @Override
      public void run() {
         try {
            GameServer.a().a(60, true);
         } catch (Exception var2) {
            CurrentTimeReseter.a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      // $VF: synthetic method
      L1R_b(CurrentTimeReseter.L1R_b var2) {
         this();
      }
   }

   private class L1R_c extends TimerTask {
      private L1R_c() {
      }

      @Override
      public void run() {
         try {
            CharacterMobsWeekTable.a().b();

            for (L1PcInstance var1 : L1World.a().c()) {
               var1.a(MobQuestWeekTable.a().b());
               CharacterMobsWeekTable.a().b(var1);
               var1.a(new S_ProtoBuffers(810, var1.dY()));
            }
         } catch (Exception var3) {
            CurrentTimeReseter.a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         } finally {
            System.out.println("每週任務重置:距離執行時間還有...10080分");
            GeneralThreadPool.a().a(CurrentTimeReseter.this.new L1R_c(), 604800000L);
         }
      }

      // $VF: synthetic method
      L1R_c(CurrentTimeReseter.L1R_c var2) {
         this();
      }
   }
}
