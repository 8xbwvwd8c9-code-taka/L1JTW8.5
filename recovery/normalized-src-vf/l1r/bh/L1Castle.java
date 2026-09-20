package l1r.bh;

import java.util.ArrayList;
import java.util.Calendar;
import l1r.ao.ClanTable;
import l1r.aq.L1Clan;
import l1r.l1j.server.Config;

public class L1Castle {
   private final int b;
   private final String c;
   private Calendar d;
   private int e;
   private int f;
   private int g;
   private boolean h = false;
   private final ArrayList<L1Castle.a> i;
   private final ArrayList<L1Castle.a> j;
   public static final String a = "安安妳好再見_";
   private static final int k = 1;
   private static final int l = 2;
   private static final int m = 3;
   private static final int n = 4;
   private static final int o = 5;
   private static final int p = 6;
   private static final int q = 7;

   public L1Castle(int var1, String var2) {
      this.b = var1;
      this.c = var2;
      this.i = new ArrayList<>();
      this.j = new ArrayList<>();
      switch (this.b) {
         case 1:
            this.i.add(new L1Castle.a(190285, "$4210", null));
            this.i.add(new L1Castle.a(190286, "$4211", null));
            this.i.add(new L1Castle.a(190287, "$4212", null));
            this.i.add(new L1Castle.a(190288, "$4213", null));
            this.j.add(new L1Castle.a(11111, "$240", null));
            break;
         case 2:
            this.i.add(new L1Castle.a(190289, "$4214", null));
            this.i.add(new L1Castle.a(190290, "$4215", null));
            this.i.add(new L1Castle.a(190291, "$4216", null));
            this.i.add(new L1Castle.a(190292, "$4217", null));
            this.j.add(new L1Castle.a(11111, "$511", null));
            break;
         case 3:
            this.i.add(new L1Castle.a(190293, "$4218", null));
            this.i.add(new L1Castle.a(190294, "$4219", null));
            this.i.add(new L1Castle.a(190295, "$4220", null));
            this.i.add(new L1Castle.a(190296, "$4221", null));
            this.j.add(new L1Castle.a(11111, "$240", null));
            break;
         case 4:
            this.i.add(new L1Castle.a(190297, "$4222", null));
            this.i.add(new L1Castle.a(190298, "$4223", null));
            this.i.add(new L1Castle.a(190299, "$4224", null));
            this.i.add(new L1Castle.a(190300, "$4225", null));
            break;
         case 5:
            this.i.add(new L1Castle.a(190301, "$4226", null));
            this.i.add(new L1Castle.a(190302, "$4227", null));
            this.i.add(new L1Castle.a(190303, "$4228", null));
            this.i.add(new L1Castle.a(190304, "$4229", null));
            break;
         case 6:
            this.i.add(new L1Castle.a(190305, "$4230", null));
            this.i.add(new L1Castle.a(190306, "$4231", null));
            this.i.add(new L1Castle.a(190307, "$4232", null));
            this.i.add(new L1Castle.a(190308, "$4233", null));
            break;
         case 7:
            this.i.add(new L1Castle.a(190309, "$4234", null));
            this.i.add(new L1Castle.a(190310, "$4235", null));
            this.i.add(new L1Castle.a(190311, "$4236", null));
            this.i.add(new L1Castle.a(190312, "$4237", null));
      }
   }

   public int a() {
      return this.b;
   }

   public String b() {
      return this.c;
   }

   public Calendar c() {
      return this.d;
   }

   public Calendar d() {
      Calendar var1 = (Calendar)this.d.clone();
      var1.add(Config.ai, Config.ah);
      return var1;
   }

   public void a(Calendar var1) {
      this.d = var1;
   }

   public int e() {
      return this.e;
   }

   public void a(int var1) {
      this.e = var1;
   }

   public int f() {
      return this.f < 0 ? 0 : this.f;
   }

   public void b(int var1) {
      this.f = var1;
   }

   public int g() {
      return this.g;
   }

   public void c(int var1) {
      this.g = var1;
   }

   public int h() {
      if (this.g > 0) {
         L1Clan var1 = ClanTable.a().a(this.g);
         if (var1 != null) {
            return var1.k();
         }
      }

      return 0;
   }

   public int i() {
      int var1 = 0;

      for (L1Castle.a var2 : this.i) {
         var1 += var2.c;
      }

      return var1;
   }

   public boolean j() {
      return this.h;
   }

   public void a(boolean var1) {
      this.h = var1;
   }

   public ArrayList<L1Castle.a> k() {
      return this.i;
   }

   public ArrayList<L1Castle.a> l() {
      return this.j;
   }

   public class a {
      public int a;
      public String b;
      public int c = 0;

      private a(int var2, String var3) {
         this.a = var2;
         this.b = var3;
      }

      // $VF: synthetic method
      a(int var2, String var3, L1Castle.a var4) {
         this(var2, var3);
      }
   }
}
