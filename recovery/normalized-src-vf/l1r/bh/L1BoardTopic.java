package l1r.bh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1BoardTopic {
   private static final Logger a = Logger.getLogger(L1BoardTopic.class.getName());
   private final int b;
   private final String c;
   private final String d;
   private final String e;
   private final String f;

   public int a() {
      return this.b;
   }

   public String b() {
      return this.c;
   }

   public String c() {
      return this.d;
   }

   public String d() {
      return this.e;
   }

   public String e() {
      return this.f;
   }

   private L1BoardTopic(int var1, String var2, String var3, String var4) {
      this.b = var1;
      this.c = var2;
      SimpleDateFormat var5 = new SimpleDateFormat("yy/MM/dd");
      this.d = var5.format(new Date());
      this.e = var3;
      this.f = var4;
   }

   private L1BoardTopic(ResultSet var1) throws SQLException {
      this.b = var1.getInt("id");
      this.c = var1.getString("name");
      this.d = var1.getString("date");
      this.e = var1.getString("title");
      this.f = var1.getString("content");
   }

   public static synchronized L1BoardTopic a(String var0, String var1, String var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;
      PreparedStatement var6 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT max(id) + 1 as newid FROM board");
         var5 = var4.executeQuery();
         var5.next();
         int var7 = var5.getInt("newid");
         L1BoardTopic var8 = new L1BoardTopic(var7, var0, var1, var2);
         var6 = var3.prepareStatement("INSERT INTO board SET id=?, name=?, date=?, title=?, content=?");
         var6.setInt(1, var8.a());
         var6.setString(2, var8.b());
         var6.setString(3, var8.c());
         var6.setString(4, var8.d());
         var6.setString(5, var8.e());
         var6.execute();
         return var8;
      } catch (SQLException var13) {
         a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      } finally {
         SQLUtil.a(var6);
         SQLUtil.a(var5, var4, var3);
      }

      return null;
   }

   public void f() {
      Connection var1 = null;
      PreparedStatement var2 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("DELETE FROM board WHERE id=?");
         var2.setInt(1, this.a());
         var2.execute();
      } catch (SQLException var7) {
         a.log(Level.SEVERE, var7.getLocalizedMessage(), var7);
      } finally {
         SQLUtil.a(var2);
         SQLUtil.a(var1);
      }
   }

   public static L1BoardTopic a(int var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM board WHERE id=?");
         var2.setInt(1, var0);
         var3 = var2.executeQuery();
         if (var3.next()) {
            return new L1BoardTopic(var3);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }

      return null;
   }

   private static PreparedStatement a(Connection var0, int var1, int var2) throws SQLException {
      PreparedStatement var3 = null;
      int var4 = 1;
      if (var1 == 0) {
         var3 = var0.prepareStatement("SELECT * FROM board ORDER BY id DESC LIMIT ?");
      } else {
         var3 = var0.prepareStatement("SELECT * FROM board WHERE id < ? ORDER BY id DESC LIMIT ?");
         var3.setInt(1, var1);
         var4++;
      }

      var3.setInt(var4, var2);
      return var3;
   }

   public static List<L1BoardTopic> a(int var0, int var1) {
      List var2 = new ArrayList<>();
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = a(var3, var0, var1);
         var5 = var4.executeQuery();

         while (var5.next()) {
            var2.add(new L1BoardTopic(var5));
         }

         return var2;
      } catch (SQLException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }

      return null;
   }
}
