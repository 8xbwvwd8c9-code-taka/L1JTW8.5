package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.be.S_CharacterConfig;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CharacterConfigTable {
   private static final Logger a = Logger.getLogger(CharacterConfigTable.class.getName());
   private static CharacterConfigTable b;

   public static CharacterConfigTable a() {
      if (b == null) {
         b = new CharacterConfigTable();
      }

      return b;
   }

   public void a(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM character_config WHERE object_id=?");
         var3.setInt(1, var1.fr());
         var4 = var3.executeQuery();
         if (!var4.next()) {
            return;
         }

         byte[] var5 = var4.getBytes("data");
         if (var5 != null) {
            var1.a(new S_CharacterConfig(var5));
            return;
         }
      } catch (Exception var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
         return;
      } finally {
         SQLUtil.a(var4, var3, var2);
      }
   }

   public void a(int var1, byte[] var2) {
      if (this.a(var1) > 0) {
         this.c(var1, var2);
      } else {
         this.b(var1, var2);
      }
   }

   private void b(int var1, byte[] var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("INSERT INTO character_config SET object_id=?, data=?");
         var4.setInt(1, var1);
         var4.setBytes(2, var2);
         var4.execute();
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   private void c(int var1, byte[] var2) {
      Connection var3 = null;
      PreparedStatement var4 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("UPDATE character_config SET data=? WHERE object_id=?");
         var4.setBytes(1, var2);
         var4.setInt(2, var1);
         var4.execute();
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var4);
         SQLUtil.a(var3);
      }
   }

   private int a(int var1) {
      int var2 = 0;
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT count(*) as cnt FROM character_config WHERE object_id=?");
         var4.setInt(1, var1);
         var5 = var4.executeQuery();
         if (var5.next()) {
            var2 = var5.getInt("cnt");
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }

      return var2;
   }
}
