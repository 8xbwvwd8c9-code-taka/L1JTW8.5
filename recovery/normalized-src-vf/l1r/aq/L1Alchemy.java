package l1r.aq;

import a.g;
import java.util.ArrayList;
import java.util.HashMap;
import l1r.an.PBMessageALL;
import l1r.an.PBMessageALL3;
import l1r.an.PBMessageALL5;
import l1r.an.PBMessageALL6;
import l1r.ao.ItemTable;
import l1r.ao.MagicDollTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bh.L1Item;
import l1r.bi.Random;

public class L1Alchemy {
   private static L1Alchemy a;
   private final ArrayList<L1Item> b = new ArrayList<>();
   private final ArrayList<L1Item> c = new ArrayList<>();
   private final ArrayList<L1Item> d = new ArrayList<>();
   private final ArrayList<L1Item> e = new ArrayList<>();
   private final ArrayList<L1Item> f = new ArrayList<>();

   public static L1Alchemy a() {
      if (a == null) {
         a = new L1Alchemy();
      }

      return a;
   }

   private L1Alchemy() {
   }

   public void a(HashMap<Integer, MagicDollTable.a> var1) {
      for (MagicDollTable.a var2 : var1.values()) {
         if (var2.d == 1) {
            this.b.add(ItemTable.a().a(var2.a));
         } else if (var2.d == 2) {
            this.c.add(ItemTable.a().a(var2.a));
         } else if (var2.d == 3) {
            this.d.add(ItemTable.a().a(var2.a));
         } else if (var2.d == 4) {
            this.e.add(ItemTable.a().a(var2.a));
         } else if (var2.d == 5) {
            this.f.add(ItemTable.a().a(var2.a));
         }
      }
   }

   public void a(L1PcInstance var1) {
      PBMessageALL5.g.a var2 = PBMessageALL5.g.aa();
      var2.a(1);

      for (int var3 = 1; var3 <= 4; var3++) {
         var2.e(this.c(var3));
         var1.a(new S_ProtoBuffers(123, var2.M().g()));
      }
   }

   public void b(L1PcInstance var1) {
      PBMessageALL5.g.a var2 = PBMessageALL5.g.aa();
      var2.a(1);

      for (int var3 = 1; var3 <= 5; var3++) {
         var2.f(this.b(var3));
         var1.a(new S_ProtoBuffers(123, var2.M().g()));
      }
   }

   public void c(L1PcInstance var1) {
      PBMessageALL5.g.a var2 = PBMessageALL5.g.aa();
      var2.a(1);

      for (int var3 = 1; var3 <= 4; var3++) {
         var2.g(this.a(var3));
         var1.a(new S_ProtoBuffers(123, var2.M().g()));
      }
   }

   public g a(int var1) {
      new ArrayList();
      if (var1 == 1) {
         ArrayList var2 = this.b;
      } else if (var1 == 2) {
         ArrayList var11 = this.c;
      } else if (var1 == 3) {
         ArrayList var12 = this.d;
      } else if (var1 == 4) {
         ArrayList var13 = this.e;
      } else if (var1 == 5) {
         ArrayList var14 = this.f;
      }

      PBMessageALL6.g.a var3 = PBMessageALL6.g.aa();
      var3.c(var1);

      for (int var4 = 1; var4 <= 4; var4++) {
         PBMessageALL.a.a var5 = PBMessageALL.a.aa();
         var5.a(var4);
         var5.b(0);
         var5.c(var1);
         var3.e(var5.M().f());
      }

      int[][] var15 = new int[][]{{1, 2}, {1, 3}, {1, 4}, {2, 3}, {2, 4}, {3, 4}};
      int[][] var8 = var15;
      int var7 = var15.length;

      for (int var6 = 0; var6 < var7; var6++) {
         int[] var16 = var8[var6];
         PBMessageALL6.e.a var9 = PBMessageALL6.e.aa();
         var9.b(var16[0]);
         var9.b(var16[1]);
         var9.c(var1 + 1);
         var3.f(var9.M().f());
      }

      int[][] var17 = new int[][]{{1, 2, 3}, {1, 2, 4}, {1, 3, 4}, {2, 3, 4}};
      int[][] var22 = var17;
      int var21 = var17.length;

      for (int var20 = 0; var20 < var21; var20++) {
         int[] var18 = var22[var20];
         PBMessageALL6.e.a var10 = PBMessageALL6.e.aa();
         var10.b(var18[0]);
         var10.b(var18[1]);
         var10.b(var18[2]);
         var10.c(var1 + 1);
         var3.f(var10.M().f());
      }

      PBMessageALL6.e.a var19 = PBMessageALL6.e.aa();
      var19.b(1);
      var19.b(2);
      var19.b(3);
      var19.b(4);
      var19.c(var1 + 1);
      if (var1 <= 3) {
         var19.e(g.a(new byte[]{16, (byte)(var1 + 2)}));
      }

      var3.f(var19.M().f());
      return var3.M().f();
   }

   public g b(int var1) {
      ArrayList var2 = new ArrayList<>();
      if (var1 == 1) {
         var2 = this.b;
      } else if (var1 == 2) {
         var2 = this.c;
      } else if (var1 == 3) {
         var2 = this.d;
      } else if (var1 == 4) {
         var2 = this.e;
      } else if (var1 == 5) {
         var2 = this.f;
      }

      PBMessageALL3.a.a var3 = PBMessageALL3.a.aa();
      var3.b(var1);

      for (L1Item var4 : var2) {
         PBMessageALL.a.a var6 = PBMessageALL.a.aa();
         var6.a(var4.W());
         var6.b(var4.m());
         var3.e(var6.M().f());
      }

      return var3.M().f();
   }

   public g c(int var1) {
      ArrayList var2 = new ArrayList<>();
      if (var1 == 1) {
         var2 = this.b;
      } else if (var1 == 2) {
         var2 = this.c;
      } else if (var1 == 3) {
         var2 = this.d;
      } else if (var1 == 4) {
         var2 = this.e;
      } else if (var1 == 5) {
         var2 = this.f;
      }

      PBMessageALL3.a.a var3 = PBMessageALL3.a.aa();
      var3.b(var1);

      for (L1Item var4 : var2) {
         PBMessageALL.a.a var6 = PBMessageALL.a.aa();
         var6.a(var4.W());
         var3.e(var6.M().f());
      }

      return var3.M().f();
   }

   public void a(L1PcInstance var1, int var2, ArrayList<L1ItemInstance> var3) {
      for (L1ItemInstance var4 : var3) {
         if (var4.bb() != null || var1.O(var4.fr())) {
            var1.a(new S_ProtoBuffers(125, 8, 0, 0));
            return;
         }
      }

      int[] var12 = new int[]{0, 0, 20, 35, 45};
      boolean var13 = true;

      for (L1ItemInstance var6 : var3) {
         if (var1.j().f(var6) <= 0) {
            var13 = false;
         }
      }

      if (var13) {
         int var14 = var12[var3.size()];
         int var15 = Random.a(100);
         if (var15 < var14) {
            boolean var8 = Random.a(100) < 10;
            ArrayList var9 = new ArrayList<>();
            if (var2 == 1) {
               var9 = var8 ? this.d : this.c;
            } else if (var2 == 2) {
               var9 = var8 ? this.e : this.d;
            } else if (var2 == 3) {
               var9 = var8 ? this.f : this.e;
            } else if (var2 == 4) {
               var9 = this.f;
            }

            L1Item var10 = var9.get(Random.a(var9.size()));
            L1ItemInstance var11 = ItemTable.a(var1, var10.g(), 1, true);
            var1.a(new S_ProtoBuffers(125, var8 ? 10 : 0, var11.fr(), var11.e()));
            if (var9.equals(this.f)) {
               var1.a(new S_SkillSound(var1.fr(), 2047));
               var1.b(new S_SkillSound(var1.fr(), 2047));
               L1World.a().a(new S_ServerMessage(3599, var11.a().j(), 0));
            }
         } else if (var15 > 40 + var14) {
            L1ItemInstance var16 = var3.get(Random.a(var3.size()));
            ItemTable.a(var1, var16.N(), 1, true);
            var1.a(new S_ProtoBuffers(125, 1, var16.fr(), var16.e()));
         } else {
            ArrayList var17 = new ArrayList<>();
            if (var2 == 1) {
               var17 = this.b;
            } else if (var2 == 2) {
               var17 = this.c;
            } else if (var2 == 3) {
               var17 = this.d;
            } else if (var2 == 4) {
               var17 = this.e;
            } else if (var2 == 5) {
               var17 = this.f;
            }

            L1Item var18 = var17.get(Random.a(var17.size()));
            L1ItemInstance var19 = ItemTable.a(var1, var18.g(), 1, true);
            var1.a(new S_ProtoBuffers(125, 0, var19.fr(), var19.e()));
         }
      }
   }
}
