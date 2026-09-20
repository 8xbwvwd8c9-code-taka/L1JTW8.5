package l1r.be;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.bh.L1BookMark;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class S_Bookmarks extends ServerBasePacket {
   private static final Logger a = Logger.getLogger(S_Bookmarks.class.getName());

   public S_Bookmarks(String var1, int var2, int var3, int var4, int var5) {
      this.c(2);
      this.a(var1);
      this.b(var2);
      this.b(var4);
      this.b(var5);
      this.a(var3);
      this.c(0);
   }

   public S_Bookmarks(L1PcInstance var1) {
      ArrayList var2 = new ArrayList<>();
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT * FROM character_teleport WHERE char_id=? ORDER BY order_id  ASC");
         var4.setInt(1, var1.fr());
         var5 = var4.executeQuery();

         while (var5.next()) {
            L1BookMark var6 = new L1BookMark();
            var6.a(var5.getInt("id"));
            var6.b(var5.getInt("char_id"));
            var6.a(var5.getString("name"));
            var6.c(var5.getInt("locx"));
            var6.d(var5.getInt("locy"));
            var6.g(var5.getShort("mapid"));
            var6.e(var5.getInt("order_id"));
            var6.f(var5.getInt("order_id_fast"));
            var2.add(var6);
         }

         this.c(42);
         this.c(42);
         this.c(128);
         this.c(0);
         this.c(2);
         byte[] var15 = new byte[127];

         for (int var7 = 0; var7 < var2.size(); var7++) {
            var15[var7] = (byte)var7;
         }

         for (L1BookMark var16 : var2) {
            if (var16.g() >= 0) {
               int var9 = var2.size() + var16.g();
               var15[var9] = (byte)var16.f();
            }
         }

         this.a(var15);
         this.b(var1.cI());
         this.b(var2.size());

         for (L1BookMark var17 : var2) {
            this.a(var17.a());
            this.a(var17.c());
            this.b(var17.h());
            this.b(var17.d());
            this.b(var17.e());
            var1.ba().add(var17);
         }

         this.b(0);
      } catch (SQLException var13) {
         a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Bookmarks";
   }
}
