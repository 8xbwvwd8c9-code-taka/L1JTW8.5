package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.be.S_ProtoBuffers;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class SoulTowerTable {
   private static final Logger a = Logger.getLogger(SoulTowerTable.class.getName());
   private static SoulTowerTable b;
   private final ArrayList<SoulTowerTable.L1R_a> c = new ArrayList<>();

   public static SoulTowerTable a() {
      if (b == null) {
         b = new SoulTowerTable();
      }

      return b;
   }

   private SoulTowerTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT *FROM soul_tower");
         var3 = var2.executeQuery();

         while (var3.next()) {
            SoulTowerTable.L1R_a var4 = new SoulTowerTable.L1R_a();
            var4.a = var3.getString("name");
            var4.b = var3.getInt("class");
            var4.c = var3.getInt("time");
            var4.d = var3.getDate("date").getTime();
            this.c.add(var4);
         }
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(L1PcInstance var1) {
      var1.a(new S_ProtoBuffers(this.c.toArray(new SoulTowerTable.L1R_a[0])));
   }

   public void a(L1PcInstance var1, int var2) {
      boolean var3 = false;

      for (SoulTowerTable.L1R_a var4 : this.c) {
         if (var2 < var4.c || this.c.size() < 10) {
            var3 = true;
            break;
         }
      }

      if (var3) {
         SoulTowerTable.L1R_a var17 = new SoulTowerTable.L1R_a();
         var17.a = var1.et();
         var17.b = var1.ay();
         var17.c = var2;
         Date var18 = new Date();
         java.sql.Date var6 = new java.sql.Date(var18.getTime());
         var17.d = var6.getTime();
         this.c.add(var17);
         Collections.sort(this.c, new Comparator<SoulTowerTable.L1R_a>() {
            public int a(SoulTowerTable.L1R_a var1, SoulTowerTable.L1R_a var2x) {
               return var1.c - var2x.c;
            }

            @Override
            public int compare(SoulTowerTable.L1R_a var1, SoulTowerTable.L1R_a var2) {
               return this.a(var1, var2);
            }
         });
         Connection var7 = null;
         PreparedStatement var8 = null;

         try {
            var7 = DatabaseFactory.a().b();
            var8 = var7.prepareStatement("DELETE FROM soul_tower WHERE rank >0");
            var8.execute();

            for (int var9 = 0; var9 < this.c.size() && var9 < 10; var9++) {
               SoulTowerTable.L1R_a var10 = this.c.get(var9);
               var8 = var7.prepareStatement("INSERT INTO soul_tower  SET rank=?,name=?,class=?,time=?,date=?");
               var8.setInt(1, var9 + 1);
               var8.setString(2, var10.a);
               var8.setInt(3, var10.b);
               var8.setInt(4, var10.c);
               Date var11 = new Date();
               var11.setTime(var10.d);
               var8.setDate(5, new java.sql.Date(var11.getTime()));
               var8.execute();
            }
         } catch (SQLException var15) {
            a.log(Level.SEVERE, var15.getLocalizedMessage(), var15);
         } finally {
            SQLUtil.a(var8);
            SQLUtil.a(var7);
         }
      }
   }

   public class L1R_a {
      public String a;
      public int b;
      public int c;
      public long d;
   }
}
