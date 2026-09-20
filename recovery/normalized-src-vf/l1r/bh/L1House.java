package l1r.bh;

import java.sql.Timestamp;

public class L1House {
   private int a;
   private String b;
   private int c;
   private String d;
   private int e;
   private boolean f;
   private boolean g;
   private Timestamp h;
   private Timestamp i;
   private int j;
   private String k;
   private int l;
   private String m;
   private int n;

   public void a() {
      String var1 = "$1194;";
      if (this.a >= 65537 && this.a <= 65542) {
         var1 = var1 + (this.a - 65536);
      } else if (this.a >= 262145 && this.a <= 262189) {
         var1 = var1 + (this.a - 262144);
      } else if (this.a >= 327681 && this.a <= 327691) {
         var1 = var1 + (this.a - 327680);
      } else if (this.a >= 458753 && this.a <= 458819) {
         var1 = var1 + (this.a - 458752);
      }

      var1 = var1 + "$1195";
      this.b = var1;
   }

   public int b() {
      return this.a;
   }

   public void a(int var1) {
      this.a = var1;
   }

   public String c() {
      return this.b;
   }

   public void a(String var1) {
      this.b = var1;
   }

   public int d() {
      return this.c;
   }

   public void b(int var1) {
      this.c = var1;
   }

   public String e() {
      return this.d;
   }

   public void b(String var1) {
      this.d = var1;
   }

   public int f() {
      return this.e;
   }

   public void c(int var1) {
      this.e = var1;
   }

   public boolean g() {
      return this.f;
   }

   public void a(boolean var1) {
      this.f = var1;
   }

   public boolean h() {
      return this.g;
   }

   public void b(boolean var1) {
      this.g = var1;
   }

   public Timestamp i() {
      return this.h;
   }

   public void a(Timestamp var1) {
      this.h = var1;
   }

   public Timestamp j() {
      return this.i;
   }

   public void b(Timestamp var1) {
      this.i = var1;
   }

   public int k() {
      return this.j;
   }

   public void d(int var1) {
      this.j = var1;
   }

   public String l() {
      return this.k;
   }

   public void c(String var1) {
      this.k = var1;
   }

   public int m() {
      return this.l;
   }

   public void e(int var1) {
      this.l = var1;
   }

   public String n() {
      return this.m;
   }

   public void d(String var1) {
      this.m = var1;
   }

   public int o() {
      return this.n;
   }

   public void f(int var1) {
      this.n = var1;
   }
}
