package be;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class dc extends eu {
   private static final Logger ao = Logger.getLogger(dc.class.getName());
   public static final int a = 55;
   public static final int b = 57;
   public static final int c = 59;
   public static final int d = 63;
   public static final int e = 65;
   public static final int f = 68;
   public static final int g = 76;
   public static final int h = 93;
   public static final int i = 101;
   public static final int j = 102;
   public static final int k = 103;
   public static final int l = 104;
   public static final int m = 110;
   public static final int n = 113;
   public static final int o = 116;
   public static final int p = 118;
   public static final int q = 119;
   public static final int r = 120;
   public static final int s = 123;
   public static final int t = 125;
   public static final int u = 126;
   public static final int v = 128;
   public static final int w = 133;
   public static final int x = 136;
   public static final int y = 141;
   public static final int z = 145;
   public static final int A = 318;
   public static final int B = 320;
   public static final int C = 325;
   public static final int D = 327;
   public static final int E = 333;
   public static final int F = 335;
   public static final int G = 337;
   public static final int H = 339;
   public static final int I = 401;
   public static final int J = 402;
   public static final int K = 450;
   public static final int L = 461;
   public static final int M = 463;
   public static final int N = 483;
   public static final int O = 485;
   public static final int P = 487;
   public static final int Q = 489;
   public static final int R = 490;
   public static final int S = 515;
   public static final int T = 516;
   public static final int U = 518;
   public static final int V = 519;
   public static final int W = 521;
   public static final int X = 525;
   public static final int Y = 537;
   public static final int Z = 539;
   public static final int aa = 540;
   public static final int ab = 559;
   public static final int ac = 560;
   public static final int ad = 564;
   public static final int ae = 567;
   public static final int af = 568;
   public static final int ag = 579;
   public static final int ah = 588;
   public static final int ai = 800;
   public static final int aj = 803;
   public static final int ak = 810;
   public static final int al = 813;
   public static final int am = 814;
   public static final int an = 821;

   public dc(int var1) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 116:
            an.a.a.a var3 = an.a.a.aa();
            var3.a(1);
            this.a(var3.M().g());
            break;
         case 126:
            an.a.a.a var2 = an.a.a.aa();
            var2.a(0);
            var2.b(1);
            this.a(var2.M().g());
            break;
         case 141:
            an.a.c.a var4 = an.a.c.aa();
            var4.b(1);
            var4.c(1);
            String[] var5 = new String[]{"", "$1506", "$11993", "$16298"};

            for (int var6 = 1; var6 <= 3; var6++) {
               an.h.e.a var7 = an.h.e.aa();
               var7.a(var6);
               String var8 = "http://g.lineage.power.plaync.com/wiki/%EC%97%90%EB%A5%B4%EC%9E%90%EB%B2%A0";
               var7.e(bi.g.a("http://g.lineage.power.plaync.com/wiki/%EC%97%90%EB%A5%B4%EC%9E%90%EB%B2%A0"));
               var7.f(bi.g.a(var5[var6]));
               var7.c(aq.aq.a().b[var6]);
               var7.d(aq.aq.a().b[var6] + (var6 == 3 ? 2400 : 600));
               an.e.c.a var9 = an.e.c.aa();
               var9.e(bi.g.a("4654"));
               var9.a(1000);
               var7.g(var9.M().f());
               var4.e(var7.M().f());
            }

            int var14 = 22;
            int var15 = Calendar.getInstance().get(11);
            int var16 = Calendar.getInstance().get(12);
            long var17 = 0L;
            if (var15 > 22) {
               var17 = (1440 + ((22 - var15) * 60 - var16)) * 60 * 1000;
            } else {
               var17 = ((22 - var15) * 60 - var16) * 60 * 1000;
            }

            int var11 = (int)((new Date().getTime() + var17) / 1000L);
            an.h.e.a var12 = an.h.e.aa();
            var12.a(12);
            var12.e(bi.g.a(""));
            var12.f(bi.g.a("$25034"));
            var12.c(var11);
            var12.d(var11 + 3600);
            an.e.c.a var13 = an.e.c.aa();
            var13.e(bi.g.a("4654"));
            var13.a(1000);
            var12.g(var13.M().f());
            var4.e(var12.M().f());
            this.a(var4.M().g());
      }

      this.b(0);
   }

   public dc(int var1, int[][] var2) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 810:
            an.a.g.a var3 = an.a.g.aa();
            an.g.i.a var4 = an.g.i.aa();
            an.g.g.a var5 = an.g.g.aa();
            an.e.e.a var6 = an.e.e.aa();
            an.a.a.a var7 = an.a.a.aa();
            var6.a(1);
            var6.b(721306);
            var6.c(0);
            var7.a(17368);
            var7.b(1);
            var6.e(var7.M().f());
            var5.e(var6.M().f());
            var6.a(2);
            var6.b(7213060);
            var6.c(15391);
            var7.a(17368);
            var7.b(5);
            var6.e(var7.M().f());
            var5.e(var6.M().f());
            var6.a(3);
            var6.b(16229385);
            var6.c(17038);
            var7.a(1695);
            var7.b(1);
            var6.e(var7.M().f());
            var5.e(var6.M().f());
            an.a.a.a var8 = an.a.a.aa();
            var8.a(1);
            var8.b(7213060);
            var8.c(17345);
            var5.f(var8.M().f());
            var8.a(2);
            var8.b(14426037);
            var8.c(17344);
            var5.f(var8.M().f());
            var5.d(55);
            var4.e(var5.M().f());

            for (int var9 = 0; var9 < 3; var9++) {
               an.e.a.a var10 = an.e.a.aa();
               var10.b(var9);
               var10.d(var2[var9 * 3][3]);

               for (int var11 = 0; var11 < 3; var11++) {
                  int var12 = var2[var9 * 3 + var11][0];
                  int var13 = var2[var9 * 3 + var11][1];
                  int var14 = var2[var9 * 3 + var11][2];
                  an.a.a.a var15 = an.a.a.aa();
                  var15.a(var11);
                  var15.b(var13);
                  var15.c(var12 * 3 - 2);
                  var15.d(var12);
                  var15.e(var14);
                  var10.e(var15.M().f());
               }

               var4.f(var10.M().f());
            }

            var3.e(var4.M().f());
            this.a(var3.M().g());
         default:
            this.b(0);
      }
   }

   public dc(int var1, int var2) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 55:
            this.c(8);
            this.c(var2);
            this.b(0);
            break;
         case 76:
            an.d.g.a var17 = an.d.g.aa();
            var17.a(1);
            var17.b(0);
            var17.e(bi.g.a(""));
            this.a(var17.M().g());
            this.b(0);
            break;
         case 93:
            an.a.a.a var18 = an.a.a.aa();
            var18.a(0);
            var18.b(var2);
            this.a(var18.M().g());
            this.b(0);
            break;
         case 103:
            an.d.g.a var16 = an.d.g.aa();
            var16.a(14144);
            var16.b(var2 * 2);
            var16.e(a.g.a(new byte[]{-30, 112, -1}));
            var16.c(20);
            this.a(var16.M().g());
            this.b(0);
            break;
         case 110:
            an.a.a.a var20 = an.a.a.aa();
            var20.a(3);
            var20.b(var2);
            var20.c(0);
            var20.d(0);
            var20.e(0);
            var20.f(0);
            var20.g(1);
            var20.h(0);
            var20.i(0);
            var20.j(0);
            var20.k(0);
            this.a(var20.M().g());
            this.b(0);
            break;
         case 123:
            an.a.a.a var14 = an.a.a.aa();
            var14.a(var2);
            this.a(var14.M().g());
            this.b(0);
            break;
         case 402:
            this.c(8);
            this.c(var2);
            this.b(0);
            break;
         case 450:
            an.a.a.a var15 = an.a.a.aa();
            var15.a(var2);
            this.a(var15.M().g());
            this.b(0);
            break;
         case 463:
            an.a.a.a var19 = an.a.a.aa();
            var19.a(var2);
            var19.b(0);
            var19.c(0);
            this.a(var19.M().g());
            this.b(0);
            break;
         case 487:
            boolean var5 = var2 == 3;
            int[] var6 = new int[]{0, 25, 35, 45};
            an.f.c.a var7 = an.f.c.aa();
            an.a.a.a var8 = an.a.a.aa();
            var8.a(var6[var2]);
            var8.b(var5 ? 3 : 1);
            var8.c(var5 ? 3 : 1);
            if (var5) {
               var8.d(1);
            }

            var7.e(var8.M().f());
            an.a.a.a var9 = an.a.a.aa();
            var9.a(var6[var2]);
            var9.b(var5 ? 3 : 1);
            var9.c(var5 ? 3 : 1);
            if (var5) {
               var9.d(1);
            }

            var7.f(var9.M().f());
            an.a.a.a var10 = an.a.a.aa();
            var10.a(var6[var2]);
            var10.b(var5 ? 3 : 1);
            var10.c(var5 ? 3 : 1);
            var10.g(50 * var2);
            var7.g(var10.M().f());
            an.a.a.a var11 = an.a.a.aa();
            var11.a(var6[var2]);
            var11.b(var5 ? 3 : 1);
            var11.c(var5 ? 3 : 1);
            if (var5) {
               var11.d(1);
            }

            var7.h(var11.M().f());
            an.a.a.a var12 = an.a.a.aa();
            var12.a(var6[var2]);
            var12.b(var5 ? 3 : 1);
            if (var5) {
               var12.c(2);
            }

            var12.f(50 * var2);
            var7.i(var12.M().f());
            an.a.a.a var13 = an.a.a.aa();
            var13.a(-1);
            var7.j(var13.M().f());
            this.a(var7.M().g());
            this.b(0);
            break;
         case 525:
            an.a.a.a var4 = an.a.a.aa();
            var4.a(0);
            var4.b(var2);
            this.a(var4.M().g());
            this.b(0);
            break;
         case 800:
            an.a.a.a var3 = an.a.a.aa();
            var3.a(var2);
            this.a(var3.M().g());
            this.b(0);
      }
   }

   public dc(int var1, byte[] var2) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 123:
            this.a(var2);
            this.b(0);
      }
   }

   public dc(int var1, ap.u var2) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 76:
            aq.i var20 = ao.q.a().a(var2.aF());
            aq.ap var21 = aq.aq.a().c(var2.aG());
            an.d.g.a var22 = an.d.g.aa();
            var22.a(var20.a() ? 2 : 1);
            if (var21 != null) {
               String var47 = var21.c(var2.aG()).get(0).f();
               var22.b(var21.a() * 2);
               var22.e(bi.g.a(var47));
               this.a(var22.M().g());
            }

            this.b(0);
            break;
         case 113:
            an.e.e.a var5 = an.e.e.aa();
            String var6 = l1j.server.a.f;
            String[] var7 = var6.split("\\.");
            int var8 = Integer.parseInt(var7[0]) & 0xFF;
            var8 |= Integer.parseInt(var7[1]) << 8 & 0xFF00;
            var8 |= Integer.parseInt(var7[2]) << 16 & 0xFF0000;
            var8 |= Integer.parseInt(var7[3]) << 24 & 0xFF000000;
            var5.a(var8);
            var5.b(l1j.server.a.g);
            var5.c(1);
            String var9 = var2.bc() + "-" + var2.eu();
            var5.e(bi.g.a(var9));
            var5.d(3);
            this.a(var5.M().g());
            this.b(0);
            break;
         case 325:
            an.e.e.a var16 = an.e.e.aa();
            var16.a(2);
            this.a(var16.M().g());
            this.b(0);
            break;
         case 327:
            an.e.e.a var17 = an.e.e.aa();
            var17.a(0);
            this.a(var17.M().g());
            this.b(0);
            break;
         case 333:
            aq.i var18 = ao.q.a().a(var2.aF());
            an.e.e.a var19 = an.e.e.aa();
            var19.a(1);
            var19.b(var18.q());
            var19.c(var18.r());
            var19.e(a.g.a(var18.s()));
            this.a(var19.M().g());
            this.b(0);
            break;
         case 461:
            an.c.g.a var24 = an.c.g.aa();
            Connection var25 = null;
            PreparedStatement var26 = null;
            ResultSet var27 = null;

            try {
               var25 = l1j.server.b.a().b();
               var26 = var25.prepareStatement("SELECT * FROM characters WHERE account_name=? ORDER BY objid");
               var26.setString(1, var2.bc());
               var27 = var26.executeQuery();

               while (var27.next()) {
                  an.d.e.a var28 = an.d.e.aa();
                  var28.a(l1j.server.a.a);
                  var28.b(var27.getInt("objid"));
                  Timestamp var29 = var27.getTimestamp("TamUseTime");
                  if (var29 == null) {
                     var28.c(0);
                  } else {
                     long var30 = var29.getTime() - System.currentTimeMillis();
                     var28.c(var30 < 0L ? 0 : (int)(var30 / 1000L));
                  }

                  var28.d(0);
                  var28.e(a.g.a(var27.getString("char_name").getBytes(l1j.server.a.k)));
                  var28.e(var27.getInt("level"));
                  var28.f(var27.getInt("Type"));
                  var28.g(var27.getInt("Sex"));
                  var24.e(var28.M().f());
               }
            } catch (Exception var35) {
               ao.log(Level.SEVERE, var35.getLocalizedMessage(), var35);
            } finally {
               bi.j.a(var27, var26, var25);
            }

            var24.b(3);
            var24.c(0);
            var24.d(0);
            this.a(var24.M().g());
            this.b(0);
            break;
         case 485:
            an.a.a.a var46 = an.a.a.aa();
            var46.a(var2.j().h());
            var46.b(var2.j().e());
            var46.c((int)var2.K());
            this.a(var46.M().g());
            this.b(0);
            break;
         case 489:
            an.a.a.a var43 = an.a.a.aa();
            var43.a(var2.bB());
            this.a(var43.M().g());
            this.b(0);
            break;
         case 490:
            an.a.a.a var45 = an.a.a.aa();
            var45.a(var2.bf());
            var45.b(var2.bj());
            var45.c(var2.bk());
            var45.d(var2.bh());
            var45.e(var2.bg());
            var45.f(var2.bi());
            this.a(var45.M().g());
            this.b(0);
            break;
         case 539:
            an.c.c.a var23 = an.c.c.aa();
            var23.a(2);
            var23.e(bi.g.a(var2.et()));
            this.a(var23.M().g());
            this.b(0);
            break;
         case 559:
            an.a.c.a var10 = an.a.c.aa();
            var10.b(0);
            var10.c(0);

            for (int var40 = 0; var40 < var2.dQ().length; var40++) {
               int var41 = am.d.a().a(var40, var2.dQ()[var40]);
               if (var41 > 0) {
                  for (int var42 = var40 * 3; var42 <= var40 * 3 + var41 - 1; var42++) {
                     an.a.a.a builderxx = an.a.a.aa();
                     int var15 = var42 + 1;
                     if (var15 >= 1670) {
                        var15 += 30;
                     }

                     builderxx.a(var15);
                     builderxx.b(var2.fr());
                     builderxx.c(var2.dR()[var42]);
                     var10.e(builderxx.M().f());
                  }
               }
            }

            this.a(var10.M().g());
            this.b(0);
            break;
         case 560:
            an.a.c.a var11 = an.a.c.aa();
            var11.b(0);
            var11.c(0);
            int[] var12 = var2.dQ();

            for (int var13 = 0; var13 < var12.length; var13++) {
               if (var12[var13] > 0) {
                  an.a.a.a var14 = an.a.a.aa();
                  var14.a(var13 + 1);
                  var14.b(var12[var13]);
                  var11.e(var14.M().f());
               }
            }

            this.a(var11.M().g());
            this.b(0);
            break;
         case 803:
            an.c.g.a var3 = an.c.g.aa();
            an.c.a.a var4 = an.c.a.aa();
            var4.b(1);
            var4.e(bi.g.a("古魯丁地監"));
            var4.c(var2.cS());
            var4.d(21600);
            var3.e(var4.M().f());
            var4.b(2);
            var4.e(bi.g.a("新夢幻之島"));
            var4.c(var2.cW());
            var4.d(7200);
            var3.e(var4.M().f());
            this.a(var3.M().g());
            this.b(0);
      }
   }

   public dc(int var1, int var2, int var3, int var4) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 125:
            an.c.c.a var6 = an.c.c.aa();
            var6.a(var2);
            an.a.a.a var7 = an.a.a.aa();
            var7.a(var3);
            var7.b(var4);
            var6.e(var7.M().f());
            this.a(var6.M().g());
            break;
         case 813:
            an.a.a.a var5 = an.a.a.aa();
            var5.a(var2);
            var5.b(var3);
            var5.c(var4);
            this.a(var5.M().g());
      }

      this.b(0);
   }

   public dc(int var1, int var2, int var3) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 63:
            an.a.a.a var8 = an.a.a.aa();
            var8.a(1);
            var8.b(var2);
            var8.c(var3);
            this.a(var8.M().g());
            this.b(0);
            break;
         case 65:
            an.a.a.a var5 = an.a.a.aa();
            var5.a(var2);
            var5.b(var3);
            this.a(var5.M().g());
            this.b(0);
            break;
         case 320:
            an.a.a.a var6 = an.a.a.aa();
            var6.a(var2);
            var6.b(2);
            var6.c(var3);
            this.a(var6.M().g());
            this.b(0);
            break;
         case 339:
            an.a.a.a var7 = an.a.a.aa();
            var7.a(var2);
            var7.b(var3);
            this.a(var7.M().g());
            this.b(0);
            break;
         case 564:
         case 567:
         case 568:
         case 814:
            an.a.a.a var4 = an.a.a.aa();
            var4.a(var2);
            var4.b(var3);
            this.a(var4.M().g());
            this.b(0);
      }
   }

   public dc(int var1, aq.k var2, ap.q var3, int var4) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 59:
            an.d.c.a var5 = an.d.c.aa();
            var5.a(var4);
            var5.e(var2.a(var3, false));
            var5.f(var2.a(0L, 0L));
            this.a(var5.M().g());
            this.b(0);
      }
   }

   public dc(int var1, aq.k var2, int var3) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 55:
            this.c(8);
            this.c(var3);
            an.c.a.a var4 = an.c.a.aa();
            var4.e(var2.i());
            this.a(var4.M().g());
            this.b(0);
      }
   }

   public dc(int var1, String... var2) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 57:
            an.c.a.a var3 = an.c.a.aa();
            var3.b(0);
            var3.c(0);
            String[] var14 = var2;
            int var13 = var2.length;

            for (int var12 = 0; var12 < var13; var12++) {
               String var11 = var14[var12];
               if (var11.trim().length() != 0) {
                  an.a.a.a var15 = an.a.a.aa();
                  var15.a(Integer.parseInt(var11));
                  var15.b(0);
                  var15.c(0);
                  var3.e(var15.M().f());
               }
            }

            this.a(var3.M().g());
            this.b(0);
            break;
         case 337:
            an.c.a.a var4 = an.c.a.aa();
            var4.b(1);
            String[] var8 = var2;
            int var7 = var2.length;

            for (int var6 = 0; var6 < var7; var6++) {
               String var5 = var8[var6];
               if (var5.trim().length() != 0) {
                  an.e.c.a var9 = an.e.c.aa();
                  var9.e(bi.g.a(var5));
                  int var10 = aq.aq.a().a(var5) == null ? 0 : 1;
                  var9.a(var10);
                  var9.f(bi.g.a(""));
                  var4.e(var9.M().f());
               }
            }

            this.a(var4.M().g());
            this.b(0);
      }
   }

   public dc(ao.bf.a... var1) {
      this.c(1);
      this.b(335);
      an.d.i.a var2 = an.d.i.aa();
      ao.bf.a[] var6 = var1;
      int var5 = var1.length;

      for (int var4 = 0; var4 < var5; var4++) {
         ao.bf.a var3 = var6[var4];
         an.h.i.a var7 = an.h.i.aa();
         var7.e(bi.g.a(var3.a));
         var7.a(var3.b);
         var7.b(var3.c);
         var7.c((int)(var3.d / 1000L));
         var7.d(1);
         if (var2.p() < 3) {
            var2.e(var7.M().f());
         }

         if (var2.r() < 10) {
            var2.f(var7.M().f());
         }
      }

      this.a(var2.M().g());
      this.b(0);
   }

   public dc(int var1, bh.d... var2) {
      this.c(1);
      this.b(var1);
      an.c.g.a var3 = an.c.g.aa();
      bh.d[] var7 = var2;
      int var6 = var2.length;

      for (int var5 = 0; var5 < var6; var5++) {
         bh.d var4 = var7[var5];
         if (var4.a() != 3) {
            String var8 = "安安妳好再見_" + var4.a();
            String var9 = "Srwh";
            aq.i var10 = ao.q.a().a(var4.g());
            if (var10 != null) {
               var8 = var10.f();
               var9 = var10.l();
            }

            int var11 = var4.f();
            an.d.c.a var12 = an.d.c.aa();
            var12.a(var4.a());
            var12.e(bi.g.a(var8));
            var12.f(bi.g.a(var9));
            var12.b(var4.a());
            var12.a((long)var4.c().getFirstDayOfWeek());
            var12.b((long)(var11 / 2 * 2 + var11 * 2));
            var12.c((long)(var11 * 2));
            var3.e(var12.M().f());
         }
      }

      this.a(var3.M().g());
      this.b(0);
   }

   public dc(int var1, int var2, String var3) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 102:
            an.d.g.a var5 = an.d.g.aa();
            var5.a(6298);
            var5.b(3755);
            var5.e(a.g.a(new byte[]{-1, 0, -1}));
            var5.c(10);
            var5.d(var2 * 2);
            var5.f(bi.g.a(var3));
            this.a(var5.M().g());
            this.b(0);
            break;
         case 540:
            an.c.c.a var4 = an.c.c.aa();
            var4.a(var2);
            var4.e(bi.g.a(var3));
            this.a(var4.M().g());
            this.b(0);
      }
   }

   public dc(int var1, ArrayList<Integer> var2) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 401:
            an.c.g.a var3 = an.c.g.aa();

            for (int var4 : var2) {
               an.a.a.a var6 = an.a.a.aa();
               var6.a(var4);
               if (var4 == 5) {
                  var6.b(10);
               }

               var3.e(var6.M().f());
            }

            this.a(var3.M().g());
            this.b(0);
      }
   }

   public dc(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9) {
      this.c(1);
      this.b(110);
      an.a.a.a var10 = an.a.a.aa();
      var10.a(var2 == 0 ? 3 : 1);
      var10.b(var1);
      var10.c(var2);
      var10.d(var3);
      var10.e(var4);
      var10.f(var5);
      var10.g(var9);
      var10.h(var6);
      var10.i(var7);
      var10.j(var8);
      var10.k(1);
      this.a(var10.M().g());
      this.b(0);
   }

   public dc(HashMap<Integer, ap.q> var1, int var2) {
      this.c(1);
      this.b(101);
      an.a.c.a var3 = an.a.c.aa();
      var3.b(99);
      var3.c(var2);

      for (int var4 : var1.keySet()) {
         ap.q var6 = var1.get(var4);
         an.b.a.a var7 = an.b.a.y();
         var7.a(var6.N());
         var7.b(var6.E());
         var7.c(var6.e());
         var7.d(var4);
         var7.e(bi.g.a(var6.b()));
         var3.e(var7.z().f());
      }

      this.a(var3.M().g());
      this.b(0);
   }

   public dc(int var1, String var2, int... var3) {
      this.c(1);
      this.b(483);
      an.e.g.a var4 = an.e.g.aa();
      var4.a(var1);
      an.a.a.a var5 = an.a.a.aa();
      var5.a(var3[0]);
      var5.b(var3[1]);
      var5.c(var3[2]);
      var5.d(var3[3]);
      if (var3.length >= 5) {
         var5.e(var3[4]);
      }

      if (var3.length >= 6) {
         var5.f(var3[5]);
      }

      if (var2 == "str") {
         var4.e(var5.M().f());
      } else if (var2 == "int") {
         var4.f(var5.M().f());
      } else if (var2 == "wis") {
         var4.g(var5.M().f());
      } else if (var2 == "dex") {
         var4.h(var5.M().f());
      } else if (var2 == "con") {
         var4.i(var5.M().f());
      } else if (var2 == "cha") {
         var4.j(var5.M().f());
      }

      this.a(var4.M().g());
      this.b(0);
   }

   public dc(ap.u var1, String var2, int var3) {
      this.c(1);
      this.b(515);
      an.f.a.a var4 = an.f.a.aa();
      var4.a(var3);
      var4.b(1);
      var4.e(bi.g.a(var2));
      var4.f(bi.g.a(var1.et()));
      var4.c(l1j.server.a.a);
      var4.d(0);
      this.a(var4.M().g());
      this.b(0);
   }

   public dc(int var1, int var2, String[] var3, int[] var4) {
      this.c(1);
      this.b(var1);
      switch (var1) {
         case 579:
            an.c.a.a var5 = an.c.a.aa();
            var5.b(var2);

            for (int var6 = 0; var6 < var4.length; var6++) {
               an.a.g.a var7 = an.a.g.aa();
               var7.e(bi.g.a(var3[var6]));
               if (var4[var6] > 0) {
                  an.a.a.a var8 = an.a.a.aa();
                  var8.a(7);
                  var8.b(var4[var6]);
                  var8.c(1);
                  var7.f(var8.M().f());
               }

               var5.e(var7.M().f());
            }

            this.a(var5.M().g());
            this.b(0);
      }
   }

   public dc(ap.u var1, String var2, int var3, int var4) {
      this.c(1);
      this.b(516);
      an.e.i.a var5 = an.e.i.aa();
      var5.a(var4);
      var5.b(var3);
      var5.e(bi.g.a(var2));
      var5.f(bi.g.a(var1.et()));
      var5.d(l1j.server.a.a);
      if (var3 == 0 || var3 == 2) {
         var5.e(var1.fr());
         var5.f(var1.fs());
         var5.g(var1.ft());
      }

      this.a(var5.M().g());
      this.b(0);
   }

   public dc(ArrayList<Integer> var1, ArrayList<Integer> var2, int var3) {
      this.c(1);
      this.b(800);
      Collections.sort(var1);
      Collections.sort(var2);
      an.c.a.a var4 = an.c.a.aa();
      var4.b(var3);
      an.b.c.a var5 = an.b.c.s();
      an.b.c.a var6 = an.b.c.s();
      var5.b(0);

      for (int var7 : var1) {
         var5.c(var7);
      }

      var4.e(var5.t().f());
      var6.b(1);

      for (int var9 : var1) {
         var6.c(var9);
      }

      var4.e(var6.t().f());
      var4.c(2);
      var4.d(1);
      this.a(var4.M().g());
      this.b(0);
   }

   public dc(int var1, bh.s var2) {
      this.c(1);
      this.b(var1);
      an.e.a.a var3 = an.e.a.aa();
      var3.b(var2.a());
      var3.c(var2.y().fr());
      an.a.a.a var4 = an.a.a.aa();
      int var5 = 1;
      if (var2.n() > 0) {
         var4.a(var5++);
         var4.b(var2.z());
         var4.c(var2.n());
         var3.e(var4.M().f());
      }

      if (var2.p().length > 0) {
         for (int var6 = 0; var6 < var2.p().length; var6++) {
            var4.a(var5++);
            var4.b(var2.A()[var6]);
            var4.c(var2.q()[var6]);
            var3.e(var4.M().f());
         }
      }

      if (var2.r().length > 0) {
         for (int var7 = 0; var7 < var2.r().length; var7++) {
            var4.a(var5++);
            var4.b(var2.B()[var7]);
            var4.c(var2.s()[var7]);
            var3.e(var4.M().f());
         }
      }

      if (var2.u().length > 0) {
         for (int var8 = 0; var8 < var2.u().length; var8++) {
            var4.a(var5++);
            var4.b(var2.C()[var8]);
            var4.c(var2.v()[var8]);
            var3.e(var4.M().f());
         }
      }

      var3.e(1);
      an.e.c.a var9 = an.e.c.aa();
      var9.e(var3.M().f());
      this.a(var9.M().g());
      this.b(0);
   }

   public dc(int var1, String var2, as.j.g... var3) {
      this.c(1);
      this.b(133);
      an.d.i.a var4 = an.d.i.aa();

      for (int var5 = 0; var5 < var3.length && var5 < 10; var5++) {
         an.c.c.a var6 = an.c.c.aa();
         as.j.g var7 = var3[var5];
         var6.a(var5 + 1);
         var6.e(bi.g.a(var7.a));
         var6.b(var7.b);
         var4.e(var6.M().f());
      }

      boolean var9 = false;

      for (int var10 = 0; var10 < var3.length; var10++) {
         as.j.g var12 = var3[var10];
         if (var12.a.equalsIgnoreCase(var2)) {
            an.c.c.a var8 = an.c.c.aa();
            var8.a(var10 + 1);
            var8.e(bi.g.a(var12.a));
            var8.b(var12.b);
            var4.f(var8.M().f());
            var9 = true;
            break;
         }
      }

      if (!var9) {
         an.c.c.a var11 = an.c.c.aa();
         var11.a(0);
         var11.e(bi.g.a(var2));
         var11.b(0);
         var4.f(var11.M().f());
      }

      var4.c(var1);
      this.a(var4.M().g());
      this.b(0);
   }

   public dc(int var1, int[] var2) {
      this.c(1);
      this.b(134);
      an.c.g.a var3 = an.c.g.aa();
      an.a.a.a var4 = an.a.a.aa();
      var4.a(4);
      var4.b(var2[0]);
      var4.c(l1j.server.a.a);
      var3.e(var4.M().f());
      var4.a(5);
      var4.b(var2[1]);
      var4.c(l1j.server.a.a);
      var3.e(var4.M().f());
      var4.a(6);
      var4.b(var2[2]);
      var4.c(l1j.server.a.a);
      var3.e(var4.M().f());
      var3.b(var1);
      this.a(var3.M().g());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ProtoBuffers";
   }
}
