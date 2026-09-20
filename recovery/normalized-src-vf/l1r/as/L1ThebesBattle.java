package l1r.as;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.ItemTable;
import l1r.ao.SpawnTable;
import l1r.ap.L1FieldObjectInstance;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_SystemMessage;
import l1r.be.ServerBasePacket;
import l1r.bi.GeneralThreadPool;
import l1r.bi.Random;

public class L1ThebesBattle {
   private static final Logger a = Logger.getLogger(L1ThebesBattle.class.getName());
   private static L1ThebesBattle b;
   private int c = 0;
   private int d = 0;
   private int e = 0;
   private int f = 0;
   private int g = 0;
   private int h = 0;
   private int i = 0;
   private int j = 0;
   private int k = 0;
   private int l = 0;

   public static L1ThebesBattle a() {
      if (b == null) {
         b = new L1ThebesBattle();
      }

      return b;
   }

   public void b() {
      L1World.a().a(new S_SystemMessage("\\aE$21837"));
      int[] var1 = new int[]{10500, 10501, 10502};
      int[] var5 = var1;
      int var4 = var1.length;

      for (int var3 = 0; var3 < var4; var3++) {
         int var2 = var5[var3];

         for (L1Object var6 : L1World.a().b(var2).values()) {
            if (var6 instanceof L1ItemInstance) {
               L1ItemInstance var8 = (L1ItemInstance)var6;
               L1Inventory var9 = L1World.a().a(var8.fs(), var8.ft(), var8.fp());
               var9.f(var8);
            } else if (var6 instanceof L1PcInstance) {
               L1PcInstance var10 = (L1PcInstance)var6;
               int var11 = var10.j().g(640820);
               if (var10.dX() == this.l) {
                  if (var11 >= 5000) {
                     ItemTable.a(var10, 640821, 1);
                  }

                  if (this.l == 4) {
                     L1Teleport.a(var10, 32734, 32756, 10500, 5, true);
                  } else if (this.l == 5) {
                     L1Teleport.a(var10, 32663, 32890, 10500, 5, true);
                  } else if (this.l == 6) {
                     L1Teleport.a(var10, 32732, 33040, 10500, 5, true);
                  }
               } else {
                  if (var11 >= 5000) {
                     ItemTable.a(var10, 640822, 1);
                  }

                  var10.a(new S_ProtoBuffers(113, var10));
               }

               var10.j().a(640820);
            }
         }
      }

      this.c = 1800;
      if (this.l == 4) {
         SpawnTable.a(46124, 32771, 32895, 10502, 5, this.c * 1000);
      } else if (this.l == 5) {
         SpawnTable.a(46123, 32771, 32895, 10502, 5, this.c * 1000);
      } else if (this.l == 6) {
         SpawnTable.a(190577, 32771, 32895, 10502, 5, this.c * 1000);
      }

      this.a(new S_ProtoBuffers(540, a().c(), "$21831"));
      GeneralThreadPool.a().a(new L1ThebesBattle.L1R_a(null), 1000L);
   }

   private L1ThebesBattle() {
      int var1 = 21;
      int var2 = Calendar.getInstance().get(11);
      int var3 = Calendar.getInstance().get(12);
      long var4 = 0L;
      if (var2 >= 21) {
         var4 = (1440 + ((21 - var2) * 60 - var3)) * 60 * 1000;
      } else {
         var4 = ((21 - var2) * 60 - var3) * 60 * 1000;
      }

      this.a(var4);
   }

   private void a(long var1) {
      System.out.println("底比斯傳送門:距離再次執行時間還有..." + var1 / 1000L / 60L + "分");
      GeneralThreadPool.a().a(new L1ThebesBattle.L1R_f(null), var1);
   }

   private void a(int var1, String[] var2) {
      GeneralThreadPool.a().a(new L1ThebesBattle.L1R_c(var2), var1 * 1000);
   }

   private void g() {
      ArrayList var1 = new ArrayList<>();
      ArrayList var2 = new ArrayList<>();
      ArrayList var3 = new ArrayList<>();
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;

      for (L1PcInstance var7 : L1World.a().c()) {
         if (var7.fp() >= 10500 && var7.fp() <= 10502) {
            L1ThebesBattle.L1R_g var9 = new L1ThebesBattle.L1R_g();
            if (var7.dX() == 4) {
               var9.a = var7.et();
               var9.b = var7.j().g(640820);
               var4 += var9.b;
               var1.add(var9);
            } else if (var7.dX() == 5) {
               var9.a = var7.et();
               var9.b = var7.j().g(640820);
               var5 += var9.b;
               var2.add(var9);
            } else if (var7.dX() == 6) {
               var9.a = var7.et();
               var9.b = var7.j().g(640820);
               var6 += var9.b;
               var3.add(var9);
            }
         }
      }

      if (var4 > this.e) {
         if (var4 >= 600000 && this.e < 600000) {
            String[] var10 = new String[]{"\\fR[$9676] \\fE$21812"};
            this.a(1, var10);
         }

         this.e = var4;
      }

      if (var5 > this.f) {
         if (var5 >= 600000 && this.f < 600000) {
            String[] var11 = new String[]{"\\fB[$9677] \\fE$21812"};
            this.a(1, var11);
         }

         this.f = var5;
      }

      if (var6 > this.g) {
         if (var6 >= 600000 && this.g < 600000) {
            String[] var12 = new String[]{"\\fA[$9675] \\fE$21812"};
            this.a(1, var12);
         }

         this.g = var6;
      }

      Collections.sort(var1, new Comparator<L1ThebesBattle.L1R_g>() {
         public int a(L1ThebesBattle.L1R_g var1, L1ThebesBattle.L1R_g var2) {
            return var2.b - var1.b;
         }

         // $VF: synthetic method
         @Override
         public int compare(Object var1, Object var2) {
            return this.a((L1ThebesBattle.L1R_g)var1, (L1ThebesBattle.L1R_g)var2);
         }
      });
      Collections.sort(var2, new Comparator<L1ThebesBattle.L1R_g>() {
         public int a(L1ThebesBattle.L1R_g var1, L1ThebesBattle.L1R_g var2) {
            return var2.b - var1.b;
         }

         // $VF: synthetic method
         @Override
         public int compare(Object var1, Object var2) {
            return this.a((L1ThebesBattle.L1R_g)var1, (L1ThebesBattle.L1R_g)var2);
         }
      });
      Collections.sort(var3, new Comparator<L1ThebesBattle.L1R_g>() {
         public int a(L1ThebesBattle.L1R_g var1, L1ThebesBattle.L1R_g var2) {
            return var2.b - var1.b;
         }

         // $VF: synthetic method
         @Override
         public int compare(Object var1, Object var2) {
            return this.a((L1ThebesBattle.L1R_g)var1, (L1ThebesBattle.L1R_g)var2);
         }
      });

      for (L1PcInstance var13 : L1World.a().c()) {
         if (var13.fp() >= 10500 && var13.fp() <= 10502) {
            if (var13.dX() == 4) {
               var13.a(new S_ProtoBuffers(this.e, var13.et(), var1.toArray(new L1ThebesBattle.L1R_g[0])));
            } else if (var13.dX() == 5) {
               var13.a(new S_ProtoBuffers(this.f, var13.et(), var2.toArray(new L1ThebesBattle.L1R_g[0])));
            } else if (var13.dX() == 6) {
               var13.a(new S_ProtoBuffers(this.g, var13.et(), var3.toArray(new L1ThebesBattle.L1R_g[0])));
            }

            this.c(var13);
            int[] var15 = new int[]{this.h, this.i, this.j};
            var13.a(new S_ProtoBuffers(this.k, var15));
         }
      }
   }

   public void a(L1PcInstance var1) {
      ArrayList var2 = new ArrayList<>();
      int[] var3 = new int[]{this.h, this.i, this.j};
      if (var1.dX() == 4) {
         var1.a(new S_ProtoBuffers(this.e, var1.et(), var2.toArray(new L1ThebesBattle.L1R_g[0])));
      } else if (var1.dX() == 5) {
         var1.a(new S_ProtoBuffers(this.f, var1.et(), var2.toArray(new L1ThebesBattle.L1R_g[0])));
      } else if (var1.dX() == 6) {
         var1.a(new S_ProtoBuffers(this.g, var1.et(), var2.toArray(new L1ThebesBattle.L1R_g[0])));
      }

      this.c(var1);
      var1.a(new S_ProtoBuffers(540, a().d(), "$21830"));
      var1.a(new S_ProtoBuffers(this.k, var3));
   }

   private void c(L1PcInstance var1) {
      int var2 = 0;
      if (var1.dX() == 4) {
         var2 = this.e;
      } else if (var1.dX() == 5) {
         var2 = this.f;
      } else if (var1.dX() == 6) {
         var2 = this.g;
      }

      if (var2 >= 600000 && !var1.bB(5016)) {
         var1.j(5016, 0);
         var1.a(new S_ProtoBuffers(5016, -1, 10, 7089, 0, 4542, 0, 0, 1));
         var1.bz(5017);
         var1.a(new S_ProtoBuffers(110, 5017));
      }
   }

   public void a(int var1) {
      int var2 = 480;
      int[] var3 = new int[]{this.h, this.i, this.j};
      this.a(new S_ProtoBuffers(480, var3));
      this.k = 480;
      GeneralThreadPool.a().a(new L1ThebesBattle.L1R_b(null), 1000L);
      String var4 = "";
      if (var1 == 4) {
         var4 = "\\fR[$9676]";
      } else if (var1 == 5) {
         var4 = "\\fB[$9677]";
      } else if (var1 == 6) {
         var4 = "\\fA[$9675]";
      }

      String[] var5 = new String[]{var4 + " \\fE$21815", "\\fE$21816", "\\fE$21817", "\\fE$21818"};
      this.a(1, var5);
      String[] var6 = new String[]{"\\fE$21819"};
      this.a(125, var6);
   }

   private void a(ServerBasePacket var1) {
      for (L1PcInstance var2 : L1World.a().c()) {
         if (var2.fp() >= 10500 && var2.fp() <= 10502) {
            var2.a(var1);
         }
      }
   }

   public void b(L1PcInstance var1) {
      if (var1.fp() >= 10500 && var1.fp() <= 10502) {
         if (a().d() > 0) {
            var1.a(new S_ProtoBuffers(540, a().d(), "$21830"));
         } else if (a().c() > 0) {
            var1.a(new S_ProtoBuffers(540, a().c(), "$21831"));
         }

         if (var1.fp() != 10500) {
            if (!var1.bB(5016) && !var1.bB(5017)) {
               var1.j(5017, 0);
               var1.a(new S_ProtoBuffers(5017, -1, 10, 7088, 0, 4541, 0, 0, 1));
            }
         } else if (var1.bB(5017)) {
            var1.bz(5017);
            var1.a(new S_ProtoBuffers(110, 5017));
         }
      } else if (var1.bB(5017)) {
         var1.bz(5017);
         var1.a(new S_ProtoBuffers(110, 5017));
      } else if (var1.bB(5016)) {
         var1.bz(5016);
         var1.a(new S_ProtoBuffers(110, 5016));
      }
   }

   private void h() {
      if (this.h == 1) {
         this.a(13803, 13812);
      }

      if (this.h == 2) {
         this.a(13806, 13815);
      }

      if (this.h == 3) {
         this.a(13809, 13818);
      }

      if (this.i == 1) {
         this.a(13785, 13794);
      }

      if (this.i == 2) {
         this.a(13788, 13797);
      }

      if (this.i == 3) {
         this.a(13791, 13800);
      }

      if (this.j == 1) {
         this.a(13761, 13773);
      }

      if (this.j == 2) {
         this.a(13765, 13777);
      }

      if (this.j == 3) {
         this.a(13769, 13781);
      }
   }

   private void i() {
      this.a(13812, 13803);
      this.a(13815, 13806);
      this.a(13818, 13809);
      this.a(13773, 13761);
      this.a(13777, 13765);
      this.a(13781, 13769);
      this.a(13794, 13785);
      this.a(13797, 13788);
      this.a(13800, 13791);
   }

   private void a(int var1, int var2) {
      for (L1Object var3 : L1World.a().b(10502).values()) {
         if (var3 instanceof L1FieldObjectInstance) {
            L1FieldObjectInstance var5 = (L1FieldObjectInstance)var3;
            if (var5.fe() == var1) {
               var5.cw(var2);
               break;
            }
         }
      }
   }

   public int c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public int e() {
      return this.k;
   }

   private class L1R_a extends TimerTask {
      private L1R_a() {
      }

      @Override
      public void run() {
         try {
            if (L1ThebesBattle.this.c > 1) {
               L1ThebesBattle.this.c = L1ThebesBattle.this.c - 1;
               GeneralThreadPool.a().a(L1ThebesBattle.this.new L1R_a(), 1000L);
               return;
            }

            for (L1PcInstance var1 : L1World.a().c()) {
               if (var1.fp() >= 10500 && var1.fp() <= 10502) {
                  var1.a(new S_ProtoBuffers(113, var1));
               }
            }
         } catch (Exception var3) {
            L1ThebesBattle.a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         }
      }

      // $VF: synthetic method
      L1R_a(L1ThebesBattle.L1R_a var2) {
         this();
      }
   }

   private class L1R_b extends TimerTask {
      private L1R_b() {
      }

      @Override
      public void run() {
         try {
            L1ThebesBattle.this.k = L1ThebesBattle.this.k - 1;
            if (L1ThebesBattle.this.k > 0) {
               GeneralThreadPool.a().a(L1ThebesBattle.this.new L1R_b(), 1000L);
               return;
            }

            int[] var1 = new int[3];

            for (L1PcInstance var2 : L1World.a().c()) {
               if (var2.fp() == 10502 && var2.fs() >= 32765 && var2.fs() <= 32777 && var2.ft() >= 32889 && var2.ft() <= 32901) {
                  if (var2.dX() == 4) {
                     var1[0]++;
                  } else if (var2.dX() == 5) {
                     var1[1]++;
                  } else if (var2.dX() == 6) {
                     var1[2]++;
                  }
               }
            }

            String var10 = "";
            int var11 = Math.max(var1[0], Math.max(var1[1], var1[2]));
            if (var1[0] == var11) {
               L1ThebesBattle.this.h = L1ThebesBattle.this.h + 1;
               var10 = "\\fR[$9676]";
            } else if (var1[1] == var11) {
               L1ThebesBattle.this.i = L1ThebesBattle.this.i + 1;
               var10 = "\\fB[$9677]";
            } else if (var1[2] == var11) {
               L1ThebesBattle.this.j = L1ThebesBattle.this.j + 1;
               var10 = "\\fA[$9675]";
            }

            for (L1PcInstance var4 : L1World.a().c()) {
               if (var4.fp() == 10502) {
                  if (var4.dX() == 4) {
                     L1Teleport.a(var4, 32772, 32822, 10502, 5, true);
                  } else if (var4.dX() == 5) {
                     L1Teleport.a(var4, 32698, 32895, 10502, 5, true);
                  } else if (var4.dX() == 6) {
                     L1Teleport.a(var4, 32773, 32965, 10502, 5, true);
                  }
               }
            }

            int[] var12 = new int[]{L1ThebesBattle.this.h, L1ThebesBattle.this.i, L1ThebesBattle.this.j};
            L1ThebesBattle.this.a(new S_ProtoBuffers(0, var12));
            L1ThebesBattle.this.h();
            String[] var13 = new String[]{var10 + " \\fE$21821"};
            L1ThebesBattle.this.a(3, var13);
            if (L1ThebesBattle.this.d > 480) {
               SpawnTable.a(190574 + Random.a(3), 32771, 32895, 10502);
            } else {
               int var6 = Math.max(L1ThebesBattle.this.h, Math.max(L1ThebesBattle.this.i, L1ThebesBattle.this.j));
               if (L1ThebesBattle.this.h == var6 && L1ThebesBattle.this.i == var6) {
                  L1ThebesBattle.this.l = L1ThebesBattle.this.e > L1ThebesBattle.this.f ? 4 : 5;
               } else if (L1ThebesBattle.this.i == var6 && L1ThebesBattle.this.j == var6) {
                  L1ThebesBattle.this.l = L1ThebesBattle.this.f > L1ThebesBattle.this.g ? 5 : 6;
               } else if (L1ThebesBattle.this.j == var6 && L1ThebesBattle.this.h == var6) {
                  L1ThebesBattle.this.l = L1ThebesBattle.this.g > L1ThebesBattle.this.e ? 6 : 4;
               } else if (L1ThebesBattle.this.h == var6) {
                  L1ThebesBattle.this.l = 4;
               } else if (L1ThebesBattle.this.i == var6) {
                  L1ThebesBattle.this.l = 5;
               } else if (L1ThebesBattle.this.j == var6) {
                  L1ThebesBattle.this.l = 6;
               }

               String var7 = "";
               if (L1ThebesBattle.this.l == 4) {
                  var7 = "\\fR[$9676]";
               } else if (L1ThebesBattle.this.l == 5) {
                  var7 = "\\fB[$9677]";
               } else if (L1ThebesBattle.this.l == 6) {
                  var7 = "\\fA[$9675]";
               }

               String[] var8 = new String[]{var7 + " \\fE$21823", "\\fE$21825"};
               L1ThebesBattle.this.a(3, var8);
            }
         } catch (Exception var9) {
            L1ThebesBattle.a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         }
      }

      // $VF: synthetic method
      L1R_b(L1ThebesBattle.L1R_b var2) {
         this();
      }
   }

   private class L1R_c extends TimerTask {
      private final String[] b;

      public L1R_c(String[] var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         try {
            for (int var1 = 0; var1 < this.b.length; var1++) {
               L1ThebesBattle.this.a(new S_PacketBox(84, 2, this.b[var1]));
               Thread.sleep(8000L);
            }
         } catch (Exception var2) {
            L1ThebesBattle.a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }
   }

   private class L1R_d extends TimerTask {
      private L1R_d() {
      }

      @Override
      public void run() {
         try {
            if (L1ThebesBattle.this.d > 1) {
               L1ThebesBattle.this.d = L1ThebesBattle.this.d - 1;
               GeneralThreadPool.a().a(L1ThebesBattle.this.new L1R_d(), 1000L);
            }
         } catch (Exception var2) {
            L1ThebesBattle.a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      // $VF: synthetic method
      L1R_d(L1ThebesBattle.L1R_d var2) {
         this();
      }
   }

   private class L1R_e extends TimerTask {
      private L1R_e() {
      }

      @Override
      public void run() {
         try {
            if (L1ThebesBattle.this.d <= 30) {
               return;
            }

            L1ThebesBattle.this.g();
            GeneralThreadPool.a().a(L1ThebesBattle.this.new L1R_e(), 30000L);
         } catch (Exception var2) {
            L1ThebesBattle.a.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      // $VF: synthetic method
      L1R_e(L1ThebesBattle.L1R_e var2) {
         this();
      }
   }

   private class L1R_f extends TimerTask {
      private L1R_f() {
      }

      @Override
      public void run() {
         try {
            L1ThebesBattle.this.i();
            L1ThebesBattle.this.l = 0;
            L1ThebesBattle.this.h = 0;
            L1ThebesBattle.this.i = 0;
            L1ThebesBattle.this.j = 0;
            L1ThebesBattle.this.e = 0;
            L1ThebesBattle.this.f = 0;
            L1ThebesBattle.this.g = 0;
            int var1 = 1800;
            SpawnTable.a(190574, 32771, 32895, 10502);
            SpawnTable.a(190554, 32620, 33181, 4, 5, 1800000L);
            System.out.println("[底比斯傳送門出現了] (32620,33181,4)");
            L1World.a().a(new S_SystemMessage("\\aE$21836"));
            L1ThebesBattle.this.d = 1800;
            GeneralThreadPool.a().a(L1ThebesBattle.this.new L1R_d(null), 1000L);
            GeneralThreadPool.a().a(L1ThebesBattle.this.new L1R_e(null), 30000L);
            L1ThebesBattle.this.a(86400000L);
            String[] var2 = new String[]{"\\fE$21824", "\\fE$21832", "\\fE$21833", "\\fE$21834"};
            L1ThebesBattle.this.a(180, var2);
         } catch (Exception var3) {
            L1ThebesBattle.a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         }
      }

      // $VF: synthetic method
      L1R_f(L1ThebesBattle.L1R_f var2) {
         this();
      }
   }

   public class L1R_g {
      public String a;
      public int b;
   }
}
