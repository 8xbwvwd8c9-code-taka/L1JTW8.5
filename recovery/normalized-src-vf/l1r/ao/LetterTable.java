package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class LetterTable {
   private static final Logger a = Logger.getLogger(LetterTable.class.getName());
   private static LetterTable b;

   public static LetterTable a() {
      if (b == null) {
         b = new LetterTable();
      }

      return b;
   }

   public void a(int var1, int var2, String var3, String var4, String var5, int var6, byte[] var7, byte[] var8) {
      Connection var9 = null;
      PreparedStatement var10 = null;
      ResultSet var11 = null;
      PreparedStatement var12 = null;

      try {
         var9 = DatabaseFactory.a().b();
         var10 = var9.prepareStatement("SELECT * FROM letter ORDER BY item_object_id");
         var11 = var10.executeQuery();
         var12 = var9.prepareStatement("INSERT INTO letter SET item_object_id=?, code=?, sender=?, receiver=?, date=?, template_id=?, subject=?, content=?");
         var12.setInt(1, var1);
         var12.setInt(2, var2);
         var12.setString(3, var3);
         var12.setString(4, var4);
         var12.setString(5, var5);
         var12.setInt(6, var6);
         var12.setBytes(7, var7);
         var12.setBytes(8, var8);
         var12.execute();
      } catch (SQLException var17) {
         a.log(Level.SEVERE, var17.getLocalizedMessage(), var17);
      } finally {
         SQLUtil.a(var12);
         SQLUtil.a(var11, var10, var9);
      }
   }

   public void a(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM letter WHERE item_object_id=?");
         var3.setInt(1, var1);
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }
}
