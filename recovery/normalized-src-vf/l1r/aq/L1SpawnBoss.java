package l1r.aq;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import l1r.bh.L1Npc;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;
import l1r.l1j.server.Config;

public class L1SpawnBoss extends L1Spawn {
   private int b;
   private String c;
   private int d;
   private String e = "";
   private Time f;

   public L1SpawnBoss(L1Npc var1) {
      super(var1);
   }

   @Override
   public void a(int var1, int var2) {
      if (this.v() == 0) {
         this.a(this.c(this.c), var2);
      }
   }

   private synchronized int v() {
      return --this.b;
   }

   public void a(String var1) {
      this.c = var1;
   }

   @Override
   public void s(int var1) {
      this.d = var1;
   }

   private long c(String var1) {
      long var2 = 0L;
      var1 = var1 == null ? "" : var1;

      try {
         Calendar var4 = Calendar.getInstance();
         if (this.e != null && this.e.length() > 0 && !this.e.contains("" + (var4.get(7) - 1))) {
            String[] var5 = this.e.split(",");
            long var6 = 604800000L;
            String[] var11 = var5;
            int var10 = var5.length;

            for (int var9 = 0; var9 < var10; var9++) {
               String var8 = var11[var9];
               if (var8.trim().length() != 0) {
                  int var12 = Integer.parseInt(var8);
                  int var13 = var4.get(7) - 1;
                  int var14 = var12 - var13;
                  if (var14 < 0) {
                     var14 += 7;
                  }

                  long var15 = var14 * 24 * 60 * 60 * 1000;
                  if (var15 < var6) {
                     var6 = var15;
                  }
               }
            }

            var2 = var6;
         }

         if (var1.contains("d")) {
            var1 = var1.replace("d", "");
            var2 += Long.parseLong(var1) * 24L * 60L * 60L * 1000L;
         } else if (var1.contains("h")) {
            var1 = var1.replace("h", "");
            var2 += Long.parseLong(var1) * 60L * 60L * 1000L;
         } else if (var1.contains("m")) {
            var1 = var1.replace("m", "");
            var2 += Long.parseLong(var1) * 60L * 1000L;
         } else if (this.f != null) {
            Calendar var22 = Calendar.getInstance();
            var22.setTime(new Date(this.f.getTime()));
            int var23 = var4.get(11);
            int var7 = var4.get(12);
            int var24 = var22.get(11);
            int var25 = var22.get(12);
            if (var23 > var24) {
               var2 += (1440 + (var24 - var23) * 60 + (var25 - var7)) * 60 * 1000;
            } else if (var23 < var24) {
               var2 += ((var24 - var23) * 60 + (var25 - var7)) * 60 * 1000;
            } else if (var23 == var24) {
               if (var7 >= var25) {
                  var2 += (1440 + (var24 - var23) * 60 + (var25 - var7)) * 60 * 1000;
               } else if (var7 < var25) {
                  var2 += var25 - var7;
               }
            }
         }
      } catch (NumberFormatException var17) {
         System.out.println("DB:spawnlist_boss 不正確的日期格式");
         var2 = 3600000L;
      }

      return var2;
   }

   @Override
   public void a() {
      if (this.d > 0) {
         long var1 = 0L;
         if (!Config.al && Random.a(100) < this.d || this.r() == 2 || this.e != null) {
            var1 = this.c(this.c);
         }

         this.a(var1, 0);
      }
   }

   private void a(long var1, int var3) {
      if (var1 >= 0L) {
         int var4 = this.b;
         this.b = this.c();

         while (var4 < this.c()) {
            var4++;
            GeneralThreadPool.a().a(new L1SpawnBoss.L1R_a(0, var3, null), var1);
         }

         if (this.d() == 97258) {
            L1World.a().b[1] = (int)((new java.util.Date().getTime() + var1) / 1000L);
         } else if (this.d() == 190086) {
            L1World.a().b[2] = (int)((new java.util.Date().getTime() + var1) / 1000L);
         }
      }
   }

   @Override
   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[MOB]npcid:" + this.d());
      var1.append("[現在の周期]");
      var1.append(" - ");
      var1.append("[出現時間]");
      return var1.toString();
   }

   public String t() {
      return this.e;
   }

   public void b(String var1) {
      this.e = var1;
   }

   public Time u() {
      return this.f;
   }

   public void a(Time var1) {
      this.f = var1;
   }

   private class L1R_a implements Runnable {
      private final int b;
      private final int c;

      private L1R_a(int var2, int var3) {
         this.b = var2;
         this.c = var3;
      }

      @Override
      public void run() {
         L1SpawnBoss.this.b(this.b, this.c);
      }

      // $VF: synthetic method
      L1R_a(int var2, int var3, L1SpawnBoss.L1R_a var4) {
         this(var2, var3);
      }
   }
}
