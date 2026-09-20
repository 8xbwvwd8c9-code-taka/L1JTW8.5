package l1r.am;

import a.g;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.an.PBMessageALL;
import l1r.an.PBMessageALL2;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL4;
import l1r.an.PBMessageALL5;
import l1r.an.PBMessageALL6;
import l1r.an.PBMessageALL7;
import l1r.an.PBMessageALL8;
import l1r.ao.ItemTable;
import l1r.bi.LineageUtil;
import l1r.bi.Random;

public class MonsterListReader {
   private static final Logger a = Logger.getLogger(MonsterListReader.class.getName());
   private static MonsterListReader b;
   private static HashMap<Integer, String> c = new HashMap<>();
   private static HashMap<Integer, MonsterListReader.L1R_c> d = new HashMap<>();
   private static HashMap<Integer, MonsterListReader.L1R_d> e = new HashMap<>();
   private static HashMap<Integer, MonsterListReader.L1R_a> f = new HashMap<>();
   private static HashMap<Integer, MonsterListReader.L1R_b> g = new HashMap<>();
   private static HashMap<Integer, int[][]> h = new HashMap<>();
   private int i = 0;
   private final HashMap<Integer, PBMessageALL4.L1R_i> j = new HashMap<>();
   private int[] k;

   public static MonsterListReader a() {
      if (b == null) {
         b = new MonsterListReader();
      }

      return b;
   }

   public int b() {
      return this.i;
   }

   private MonsterListReader() {
      try {
         byte[] var1 = Files.readAllBytes(Paths.get("./data/contents/npc-common.bin"));
         byte[] var2 = Files.readAllBytes(Paths.get("./data/contents/criteria-common.bin"));
         byte[] var3 = Files.readAllBytes(Paths.get("./data/contents/item-common.bin"));
         byte[] var4 = Files.readAllBytes(Paths.get("./data/contents/quest-common.bin"));
         byte[] var5 = Files.readAllBytes(Paths.get("./data/contents/achievement-common.bin"));
         PBMessageALL3.L1R_a var6 = PBMessageALL3.L1R_a.a(var3);

         for (g var7 : var6.q()) {
            PBMessageALL5.L1R_g var9 = PBMessageALL5.L1R_g.a(var7);
            int var10 = var9.p();
            PBMessageALL2.L1R_g var11 = PBMessageALL2.L1R_g.a(var9.r());
            MonsterListReader.L1R_a var12 = new MonsterListReader.L1R_a();
            var12.a = var11.p();
            var12.b = var11.r();
            var12.c = var11.t();
            var12.d = new String(var11.v().e(), "BIG5");
            var12.e = new String(var11.x().e(), "BIG5");
            var12.f = var11.B();
            String var13 = "A";
            if (var11.H() > 0) {
               String[] var14 = new String[]{"P", "K", "E", "W", "D", "R", "I", "O"};
               var13 = "";

               for (int var15 : var11.G()) {
                  var13 = var13 + var14[var15];
               }
            }

            var12.g = var13;
            var12.h = -var11.an();
            var12.i = var11.aH();
            var12.j = var11.aJ();
            f.put(var10, var12);
         }

         PBMessageALL6.L1R_g var31 = PBMessageALL6.L1R_g.a(var1);

         for (g var32 : var31.q()) {
            PBMessageALL3.L1R_c var37 = PBMessageALL3.L1R_c.a(var32);
            PBMessageALL2.L1R_i var41 = PBMessageALL2.L1R_i.a(var37.r());
            int var45 = var41.p();
            String var49 = new String(var41.t().e(), "BIG5");
            c.put(var45, var49);
            MonsterListReader.L1R_c var53 = new MonsterListReader.L1R_c();
            var53.a = LineageUtil.d(var49);
            var53.b = var41.r();
            var53.c = var41.v();
            var53.d = var41.x();
            var53.e = var41.z();
            var53.f = var41.B();
            var53.g = var41.X();
            var53.h = var41.E_();

            for (g var57 : var41.H_()) {
               PBMessageALL.L1R_a var17 = PBMessageALL.L1R_a.a(var57);
               int var18 = var17.p();
               MonsterListReader.L1R_a var19 = f.get(var18);
               if (var19 != null && !var53.i.contains(LineageUtil.d(var19.e))) {
                  var53.i.add(LineageUtil.d(var19.e));
               }
            }

            d.put(var45, var53);
         }

         PBMessageALL6.L1R_g var33 = PBMessageALL6.L1R_g.a(var2);

         for (g var35 : var33.q()) {
            PBMessageALL3.L1R_c var42 = PBMessageALL3.L1R_c.a(var35);
            int var46 = var42.p();
            PBMessageALL7.L1R_a var50 = PBMessageALL7.L1R_a.a(var42.r());
            int var54 = var50.a(0);
            if (c.containsKey(var54)) {
               String var58 = c.get(var54);
               MonsterListReader.L1R_b var62 = new MonsterListReader.L1R_b(var46, var58.replace(" ", ""), LineageUtil.d(var58).replace(" ", ""));
               g.put(var46, var62);
            }

            if (var46 > this.i) {
               this.i = var46;
            }
         }

         PBMessageALL3.L1R_a var36 = PBMessageALL3.L1R_a.a(var4);

         for (g var39 : var36.q()) {
            PBMessageALL5.L1R_g var47 = PBMessageALL5.L1R_g.a(var39);
            int var51 = var47.p();
            PBMessageALL8.L1R_g var55 = PBMessageALL8.L1R_g.a(var47.r());
            int var59 = var55.p();
            MonsterListReader.L1R_d var63 = new MonsterListReader.L1R_d();
            MonsterListReader.e.put(var59, var63);
            var63.a = var59;
            if (!var55.B().d()) {
               PBMessageALL3.L1R_g var65 = PBMessageALL3.L1R_g.a(var55.B());

               for (g var69 : var65.o()) {
                  PBMessageALL.L1R_a var20 = PBMessageALL.L1R_a.a(var69);
                  int var21 = var20.p();
                  int var22 = var20.r();
                  int var23 = var20.t();
                  if (var21 == 2) {
                     var63.c.add("rewardExp =" + var23 + ";");
                  } else {
                     String var24 = "" + var22;
                     if (f.containsKey(var22)) {
                        var24 = LineageUtil.d(f.get(var22).e);
                     }

                     var63.c.add(var24 + ":" + var23);
                  }
               }
            }

            if (!var55.D().d()) {
               PBMessageALL3.L1R_a var66 = PBMessageALL3.L1R_a.a(var55.D());
               int var70 = var66.p();

               for (g var74 : var66.q()) {
                  PBMessageALL.L1R_a var80 = PBMessageALL.L1R_a.a(var74);
                  int var82 = var80.p();
                  int var84 = var80.r();
                  int var86 = var80.t();
                  if (var82 == 2) {
                     var63.d.add("rewardExp =" + var86 + ";");
                  } else {
                     String var25 = "" + var84;
                     if (f.containsKey(var84)) {
                        var25 = LineageUtil.d(f.get(var84).e);
                     }

                     var63.d.add(var25 + ":" + var86);
                  }
               }
            }

            if (!var55.F().d()) {
               PBMessageALL.L1R_a var67 = PBMessageALL.L1R_a.a(var55.F());
               int var71 = var67.r();
               int var75 = var67.t();
               int var78 = var67.v();
               var63.b = var75 + "," + var78 + "," + var71;
            }
         }

         this.k = new int[this.b() * 3 + 30];
         PBMessageALL3.L1R_a var40 = PBMessageALL3.L1R_a.a(var5);

         for (g var44 : var40.q()) {
            PBMessageALL3.L1R_a var52 = PBMessageALL3.L1R_a.a(var44);

            for (g var56 : var52.q()) {
               PBMessageALL8.L1R_c var64 = PBMessageALL8.L1R_c.a(var56);
               int var68 = var64.p();
               PBMessageALL.L1R_g var72 = PBMessageALL.L1R_g.a(var64.t());
               PBMessageALL.L1R_a var76 = PBMessageALL.L1R_a.a(var72.p());
               int var79 = var76.p();
               int var81 = var76.r();
               this.k[var68 - 1] = var81;
               PBMessageALL4.L1R_i var83 = PBMessageALL4.L1R_i.a(var64.x());
               this.j.put(var68, var83);
               PBMessageALL.L1R_a var85 = PBMessageALL.L1R_a.a(var64.B());
               int var87 = var85.v();
               int var88 = var85.x();
               int var26 = var85.z();
               int var27 = var85.B();
               int var28 = var85.D();
               int[][] var29 = new int[2][3];
               var29[0] = new int[]{var88, var26, var87};
               var29[1] = new int[]{var27, var28, var87};
               h.put(var68, var29);
            }
         }
      } catch (Exception var30) {
         a.log(Level.SEVERE, var30.getLocalizedMessage(), var30);
      }
   }

   public PBMessageALL4.L1R_i a(int var1) {
      return this.j.get(var1);
   }

   public int a(int var1, int var2) {
      int var3;
      for (var3 = 0; var3 < 3; var3++) {
         int var4 = var1 * 3 + var3;
         if (var4 >= 1670) {
            var4 += 30;
         }

         int var5 = this.k[var4];
         if (var2 < var5) {
            break;
         }
      }

      return var3;
   }

   public int[] b(int var1) {
      return h.containsKey(var1) ? h.get(var1)[Random.a(2)] : null;
   }

   public void c() throws Exception {
      File var1 = new File("QuestInfo.txt");
      BufferedWriter var2 = new BufferedWriter(new FileWriter(var1, false));

      for (MonsterListReader.L1R_d var3 : e.values()) {
         var2.write("任務編號:" + var3.a + "\r\n");
         var2.write("\t固定獎勵:\r\n");

         for (String var5 : var3.c) {
            var2.write("\t\t" + var5 + "\r\n");
         }

         String var12 = "";
         String var13 = "";
         String var7 = "";
         String var8 = "//";

         for (String var9 : var3.c) {
            if (!var9.contains("rewardExp")) {
               String[] var11 = var9.split(":");
               var12 = var12 + ItemTable.a().b(var11[0]) + ",";
               var13 = var13 + var11[1] + ",";
               var7 = var7 + "0,";
               var8 = var8 + var9 + "/";
            }
         }

         var2.write("\t\trewardItemid=new int[]{" + var12 + "};" + var8 + "\r\n");
         var2.write("\t\trewardItemCount=new int[]{" + var13 + "};" + "\r\n");
         var2.write("\t\trewardItemEnchant=new int[]{" + var7 + "};" + "\r\n");
         var2.write("\t選擇獎勵:\r\n");

         for (String var14 : var3.d) {
            var2.write("\t\t" + var14 + "\r\n");
         }

         var2.write("\t移動座標:\r\n\t\tteleportLoc = new int[]{" + var3.b + "};\r\n");
         var2.newLine();
      }

      var2.close();
      System.out.println("任務資料(" + e.size() + ")相關資訊已匯出至 QuestInfo.txt");
   }

   public void d() throws Exception {
      File var1 = new File("NpcInfo.txt");
      BufferedWriter var2 = new BufferedWriter(new FileWriter(var1, false));

      for (MonsterListReader.L1R_c var3 : d.values()) {
         if (var3.a.trim().length() != 0) {
            String var5 = "";

            for (String var6 : var3.i) {
               var5 = var5 + var6 + "||";
            }

            var2.write(var3.a + "\t" + var3.c + "\t" + var3.b + "\t" + var3.e + "\t" + var3.f + "\t" + var3.d + "\t" + var3.h + "\t" + var3.g + "\t" + var5);
            var2.newLine();
         }
      }

      var2.close();
      System.out.println("NPC資料(" + d.size() + ")相關資訊已匯出至 NpcInfo.txt");
   }

   public void e() throws Exception {
      File var1 = new File("ItemInfo.txt");
      BufferedWriter var2 = new BufferedWriter(new FileWriter(var1, false));

      for (MonsterListReader.L1R_a var3 : f.values()) {
         var2.write(var3.a + "\t" + LineageUtil.d(var3.e) + "\t" + var3.e + "\t" + var3.b + "\t" + var3.c + "\t" + var3.f);
         var2.newLine();
      }

      var2.close();
      System.out.println("道具資料(" + f.size() + ")相關資訊已匯出至 ItemInfo.txt");
   }

   public void f() throws Exception {
      File var1 = new File("MonsterListInfo.txt");
      BufferedWriter var2 = new BufferedWriter(new FileWriter(var1, true));

      for (MonsterListReader.L1R_b var3 : g.values()) {
         var2.write(var3.a + "\t" + var3.b + "\t" + var3.c);
         var2.newLine();
      }

      var2.close();
      System.out.println("怪物圖鑑(" + g.size() + ")相關資訊已匯出至 MonsterListInfo.txt");
   }

   class L1R_a {
      public int a;
      public int b;
      public int c;
      public String d;
      public String e;
      public int f;
      public String g = "A";
      public int h;
      public int i;
      public int j;
   }

   class L1R_b {
      public int a;
      public String b;
      public String c;

      public L1R_b(int var2, String var3, String var4) {
         this.a = var2;
         this.b = var3;
         this.c = var4;
      }
   }

   class L1R_c {
      public String a;
      public int b;
      public int c;
      public int d;
      public int e;
      public int f;
      public int g;
      public int h;
      public ArrayList<String> i = new ArrayList<>();
   }

   class L1R_d {
      public int a;
      public String b = "";
      public ArrayList<String> c = new ArrayList<>();
      public ArrayList<String> d = new ArrayList<>();
   }
}
