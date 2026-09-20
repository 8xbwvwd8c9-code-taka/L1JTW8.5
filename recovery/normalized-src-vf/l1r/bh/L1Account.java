package l1r.bh;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;
import sun.misc.BASE64Encoder;

public class L1Account {
   private static final Logger a = Logger.getLogger(L1Account.class.getName());
   private String b;
   private String c;
   private String d;
   private Timestamp e = new Timestamp(System.currentTimeMillis());
   private int f;
   private String g;
   private boolean h = false;
   private int i;
   private boolean j = false;
   private int k = 0;
   private boolean l = false;
   private boolean m = false;
   private int n = 0;
   private int o = 0;
   private int p = 0;
   private int q = Integer.MAX_VALUE;

   public boolean a() {
      return this.f > 0;
   }

   public int b() {
      int var1 = 0;
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         String var5 = "SELECT count(*) as cnt FROM characters WHERE account_name=?";
         var3 = var2.prepareStatement("SELECT count(*) as cnt FROM characters WHERE account_name=?");
         var3.setString(1, this.b);
         var4 = var3.executeQuery();
         if (var4.next()) {
            var1 = var4.getInt("cnt");
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }

      return var1;
   }

   public boolean a(String var1) {
      try {
         this.j = this.d.equals(b(var1));
         return this.j;
      } catch (Exception var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         return false;
      }
   }

   public static String b(String var0) {
      byte[] var1 = new byte[0];

      try {
         var1 = var0.getBytes("UTF-8");
         var1 = MessageDigest.getInstance("SHA").digest(var1);
      } catch (UnsupportedEncodingException var3) {
         a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
      } catch (NoSuchAlgorithmException var4) {
         a.log(Level.SEVERE, var4.getLocalizedMessage(), var4);
      }

      return new BASE64Encoder().encode(var1);
   }

   public void a(int var1) {
      this.n = Math.min(this.n + var1, Integer.MAX_VALUE);
   }

   public void b(int var1) {
      this.o = Math.min(this.o + var1, Integer.MAX_VALUE);
   }

   public void c() {
      this.p++;
   }

   public void c(int var1) {
      this.q = Math.min(var1, this.q);
   }

   public String d() {
      return this.b;
   }

   public void c(String var1) {
      this.b = var1;
   }

   public String e() {
      return this.c;
   }

   public void d(String var1) {
      this.c = var1;
   }

   public String f() {
      return this.d;
   }

   public void e(String var1) {
      this.d = var1;
   }

   public Timestamp g() {
      return this.e;
   }

   public void a(Timestamp var1) {
      this.e = var1;
   }

   public int h() {
      return this.f;
   }

   public void d(int var1) {
      this.f = var1;
   }

   public String i() {
      return this.g;
   }

   public void f(String var1) {
      this.g = var1;
   }

   public boolean j() {
      return this.h;
   }

   public void a(boolean var1) {
      this.h = var1;
   }

   public int k() {
      return this.i;
   }

   public void e(int var1) {
      this.i = var1;
   }

   public boolean l() {
      return this.j;
   }

   public void b(boolean var1) {
      this.j = var1;
   }

   public int m() {
      return this.k;
   }

   public void f(int var1) {
      this.k = var1;
   }

   public boolean n() {
      return this.l;
   }

   public void c(boolean var1) {
      this.l = var1;
   }

   public boolean o() {
      return this.m;
   }

   public void d(boolean var1) {
      this.m = var1;
   }

   public int p() {
      return this.n;
   }

   public void g(int var1) {
      this.n = var1;
   }

   public int q() {
      return this.o;
   }

   public void h(int var1) {
      this.o = var1;
   }

   public int r() {
      return this.p;
   }

   public int s() {
      return this.q;
   }
}
