package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_Rank;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class RankingTable {
   private static final Logger a = Logger.getLogger(RankingTable.class.getName());
   private final int b = 50;
   private static RankingTable c;
   private final HashMap<Integer, Integer> d = new HashMap<>();
   private final HashMap<Integer, Integer> e = new HashMap<>();
   private final HashMap<Integer, Integer> f = new HashMap<>();
   private final HashMap<Integer, Integer> g = new HashMap<>();
   private final HashMap<Integer, Integer> h = new HashMap<>();
   private final HashMap<Integer, Integer> i = new HashMap<>();
   private final HashMap<Integer, Integer> j = new HashMap<>();
   private final HashMap<Integer, Integer> k = new HashMap<>();
   private final HashMap<Integer, Integer> l = new HashMap<>();
   private final ArrayList<RankingTable.L1R_a> m = new ArrayList<>();
   private final ArrayList<RankingTable.L1R_a> n = new ArrayList<>();
   private final ArrayList<RankingTable.L1R_a> o = new ArrayList<>();
   private final ArrayList<RankingTable.L1R_a> p = new ArrayList<>();
   private final ArrayList<RankingTable.L1R_a> q = new ArrayList<>();
   private final ArrayList<RankingTable.L1R_a> r = new ArrayList<>();
   private final ArrayList<RankingTable.L1R_a> s = new ArrayList<>();
   private final ArrayList<RankingTable.L1R_a> t = new ArrayList<>();
   private final ArrayList<RankingTable.L1R_a> u = new ArrayList<>();

   public static RankingTable a() {
      if (c == null) {
         c = new RankingTable();
      }

      return c;
   }

   public static void b() {
      for (L1PcInstance var0 : L1World.a().c()) {
         var0.I();
      }

      c = new RankingTable();
   }

   private RankingTable() {
      this.c();
      this.d();
   }

   public void a(L1PcInstance var1, int var2) {
      switch (var2) {
         case 0:
            var1.a(new S_Rank(var2, this.n.toArray(new RankingTable.L1R_a[0])));
            break;
         case 1:
            var1.a(new S_Rank(var2, this.o.toArray(new RankingTable.L1R_a[0])));
            break;
         case 2:
            var1.a(new S_Rank(var2, this.p.toArray(new RankingTable.L1R_a[0])));
            break;
         case 3:
            var1.a(new S_Rank(var2, this.q.toArray(new RankingTable.L1R_a[0])));
            break;
         case 4:
            var1.a(new S_Rank(var2, this.r.toArray(new RankingTable.L1R_a[0])));
            break;
         case 5:
            var1.a(new S_Rank(var2, this.s.toArray(new RankingTable.L1R_a[0])));
            break;
         case 6:
            var1.a(new S_Rank(var2, this.t.toArray(new RankingTable.L1R_a[0])));
            break;
         case 7:
            var1.a(new S_Rank(var2, this.u.toArray(new RankingTable.L1R_a[0])));
            break;
         case 8:
            var1.a(new S_Rank(var2, this.m.toArray(new RankingTable.L1R_a[0])));
      }
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM ranking");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("no");
            this.d.put(var3.getInt("level_char_id_all"), var4);
            this.e.put(var3.getInt("level_char_id_p"), var4);
            this.f.put(var3.getInt("level_char_id_k"), var4);
            this.g.put(var3.getInt("level_char_id_e"), var4);
            this.h.put(var3.getInt("level_char_id_w"), var4);
            this.i.put(var3.getInt("level_char_id_d"), var4);
            this.j.put(var3.getInt("level_char_id_r"), var4);
            this.k.put(var3.getInt("level_char_id_i"), var4);
            this.l.put(var3.getInt("level_char_id_o"), var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void d() {
      long var1 = System.currentTimeMillis();
      System.out.print("loading rankLevel...");
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT * FROM characters");
         var5 = var4.executeQuery();

         while (var5.next()) {
            var5.getInt("AccessLevel");
            RankingTable.L1R_a var6 = new RankingTable.L1R_a();
            var6.a = var5.getInt("objid");
            var6.b = var5.getString("char_name");
            var6.c = var5.getInt("level");
            var6.d = var5.getInt("Exp");
            var6.e = var5.getInt("Type");
            if (var6.e == 0) {
               this.n.add(var6);
            } else if (var6.e == 1) {
               this.o.add(var6);
            } else if (var6.e == 2) {
               this.p.add(var6);
            } else if (var6.e == 3) {
               this.q.add(var6);
            } else if (var6.e == 4) {
               this.r.add(var6);
            } else if (var6.e == 5) {
               this.s.add(var6);
            } else if (var6.e == 6) {
               this.t.add(var6);
            } else if (var6.e == 7) {
               this.q.add(var6);
            }

            this.m.add(var6.a());
         }

         this.a(this.m);
         this.a(this.n);
         this.a(this.o);
         this.a(this.p);
         this.a(this.q);
         this.a(this.r);
         this.a(this.s);
         this.a(this.t);
         this.a(this.u);
         this.a(this.m, "all");
         this.a(this.n, "p");
         this.a(this.o, "k");
         this.a(this.p, "e");
         this.a(this.q, "w");
         this.a(this.r, "d");
         this.a(this.s, "r");
         this.a(this.t, "i");
         this.a(this.u, "o");
         System.out.println("OK! " + (System.currentTimeMillis() - var1) + " ms");
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }
   }

   private void a(ArrayList<RankingTable.L1R_a> var1, String var2) {
      HashMap var3 = new HashMap<>();
      if (var2.equals("p")) {
         var3 = this.e;
      } else if (var2.equals("k")) {
         var3 = this.f;
      } else if (var2.equals("e")) {
         var3 = this.g;
      } else if (var2.equals("w")) {
         var3 = this.h;
      } else if (var2.equals("d")) {
         var3 = this.i;
      } else if (var2.equals("r")) {
         var3 = this.j;
      } else if (var2.equals("i")) {
         var3 = this.k;
      } else if (var2.equals("o")) {
         var3 = this.l;
      } else if (var2.equals("all")) {
         var3 = this.d;
      }

      for (int var4 = 0; var4 < var1.size(); var4++) {
         int var5 = var4 + 1;
         int var6 = var1.get(var4).a;
         if (var4 < 50) {
            this.a(var6, var5, "level_char_id_" + var2);
            if (var3.containsKey(var6)) {
               int var7 = var3.get(var6);
               var1.get(var4).f = var7;
            }
         }
      }

      if (var1.size() > 50) {
         new ArrayList<>(var1.subList(0, 50));
      }
   }

   private void a(ArrayList<RankingTable.L1R_a> var1) {
      Collections.sort(var1, new Comparator<RankingTable.L1R_a>() {
         public int a(RankingTable.L1R_a var1, RankingTable.L1R_a var2) {
            return var2.c == var1.c ? var2.d - var1.d : var2.c - var1.c;
         }

         @Override
         public int compare(RankingTable.L1R_a var1, RankingTable.L1R_a var2) {
            return this.a(var1, var2);
         }
      });
   }

   private void a(int var1, int var2, String var3) {
      Connection var4 = null;
      PreparedStatement var5 = null;

      try {
         var4 = DatabaseFactory.a().b();
         var5 = var4.prepareStatement("UPDATE ranking SET " + var3 + "=" + var1 + " WHERE no =" + var2);
         var5.execute();
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5);
         SQLUtil.a(var4);
      }
   }

   public class L1R_a implements Cloneable {
      public int a;
      public String b;
      public int c;
      public int d;
      public int e;
      public int f = 0;

      public RankingTable.L1R_a a() {
         RankingTable.L1R_a var1 = null;

         try {
            var1 = (RankingTable.L1R_a)super.clone();
         } catch (CloneNotSupportedException var3) {
            var3.printStackTrace();
         }

         return var1;
      }

      // $VF: synthetic method
      @Override
      public Object clone() throws CloneNotSupportedException {
         return this.a();
      }
   }
}
