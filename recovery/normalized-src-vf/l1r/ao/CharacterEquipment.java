package l1r.ao;

import a.g;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.an.PBMessageALL2;
import l1r.an.PBMessageALL3;
import l1r.ap.L1PcInstance;
import l1r.be.S_ProtoBuffers;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CharacterEquipment {
   private static final Logger a = Logger.getLogger(CharacterEquipment.class.getName());
   private static CharacterEquipment b;

   public static CharacterEquipment a() {
      if (b == null) {
         b = new CharacterEquipment();
      }

      return b;
   }

   public void a(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE character_equip SET data=? WHERE id=?");
         var3.setBytes(1, this.d(var1));
         var3.setInt(2, var1.fr());
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public void b(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;
      ResultSet var4 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("SELECT * FROM character_equip WHERE id=?");
         var3.setInt(1, var1.fr());
         var4 = var3.executeQuery();
         if (!var4.next()) {
            this.c(var1);
         } else {
            byte[] var5 = var4.getBytes("data");
            PBMessageALL3.a var6 = PBMessageALL3.a.a(var5);
            var1.bu(var6.p());
            var1.dT().clear();
            var1.dU().clear();

            for (g var7 : var6.q()) {
               PBMessageALL2.c var9 = PBMessageALL2.c.a(var7);
               ArrayList var10 = var9.p() == 0 ? var1.dT() : var1.dU();

               for (int var11 : var9.q()) {
                  var10.add(var11);
               }
            }
         }

         var1.a(new S_ProtoBuffers(var1.dT(), var1.dU(), var1.dV()));
      } catch (Exception var16) {
         a.log(Level.SEVERE, var16.getLocalizedMessage(), var16);
      } finally {
         SQLUtil.a(var4, var3, var2);
      }
   }

   private void c(L1PcInstance var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("INSERT INTO character_equip SET id=?, data=?");
         var3.setInt(1, var1.fr());
         var3.setBytes(2, this.d(var1));
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private byte[] d(L1PcInstance var1) {
      PBMessageALL3.a.a var2 = PBMessageALL3.a.aa();
      var2.b(var1.dV());
      PBMessageALL2.c.a var3 = PBMessageALL2.c.s();
      PBMessageALL2.c.a var4 = PBMessageALL2.c.s();
      var3.b(0);

      for (int var5 : var1.dT()) {
         var3.c(var5);
      }

      var2.e(var3.t().f());
      var4.b(1);

      for (int var7 : var1.dU()) {
         var4.c(var7);
      }

      var2.e(var4.t().f());
      var2.c(2);
      var2.d(1);
      return var2.M().g();
   }
}
