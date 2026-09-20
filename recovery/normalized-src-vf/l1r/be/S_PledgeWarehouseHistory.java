package l1r.be;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class S_PledgeWarehouseHistory extends ServerBasePacket {
   private static final Logger a = Logger.getLogger(S_PledgeWarehouseHistory.class.getName());

   public S_PledgeWarehouseHistory(int var1) {
      this.c(121);
      this.c(117);
      ArrayList var2 = this.f(var1);
      this.a(var2.size());

      for (S_PledgeWarehouseHistory.L1R_a var3 : var2) {
         this.a(var3.a);
         this.c(var3.b);
         this.a(var3.c);
         this.a(var3.d);
         this.a((int)((System.currentTimeMillis() - var3.e.getTime()) / 60000L));
      }
   }

   private void e(int var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("DELETE FROM clan_warehouse_history WHERE clan_id=? AND record_time < ?");
         var3.setInt(1, var1);
         var3.setTimestamp(2, new Timestamp(System.currentTimeMillis() - 259200000L));
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private ArrayList<S_PledgeWarehouseHistory.L1R_a> f(int var1) {
      this.e(var1);
      ArrayList var2 = new ArrayList<>();
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT * FROM clan_warehouse_history WHERE clan_id=? ORDER BY id DESC");
         var4.setInt(1, var1);
         var5 = var4.executeQuery();

         while (var5.next()) {
            S_PledgeWarehouseHistory.L1R_a var6 = new S_PledgeWarehouseHistory.L1R_a(null);
            var6.a = var5.getString("char_name");
            var6.b = var5.getInt("type");
            var6.c = var5.getString("item_name");
            var6.d = var5.getInt("item_count");
            var6.e = var5.getTimestamp("record_time");
            var2.add(var6);
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }

      return var2;
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_PledgeWarehouseHistory";
   }

   private class L1R_a {
      public String a;
      public int b;
      public String c;
      public int d;
      public Timestamp e;

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(S_PledgeWarehouseHistory.L1R_a var2) {
         this();
      }
   }
}
