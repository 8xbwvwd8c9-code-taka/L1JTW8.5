package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.be.S_PacketBox;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class CharacterGiftTable {
   private static final Logger a = Logger.getLogger(CharacterGiftTable.class.getName());
   private static CharacterGiftTable b;
   private final HashMap<Integer, CharacterGiftTable.L1R_a> c = new HashMap<>();
   private final HashMap<String, CharacterGiftTable.L1R_b> d = new HashMap<>();

   public static CharacterGiftTable a() {
      if (b == null) {
         b = new CharacterGiftTable();
      }

      return b;
   }

   public CharacterGiftTable() {
      this.b();
      this.c();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM gift");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("index");
            int var5 = var3.getInt("itemid");
            String var6 = var4 + "-" + var5;
            if (!this.d.containsKey(var6)) {
               CharacterGiftTable.L1R_b var7 = new CharacterGiftTable.L1R_b(null);
               var7.a = var4;
               var7.b = var5;
               var7.c = var3.getInt("count");
               var7.d = var3.getInt("enchant");
               var7.e = var3.getString("activate");
               this.d.put(var6, var7);
            }
         }
      } catch (Exception var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM character_gift");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("objid");
            byte[] var5 = var3.getBytes("data");
            if (!this.c.containsKey(var4)) {
               CharacterGiftTable.L1R_a var6 = new CharacterGiftTable.L1R_a(var4, null);
               var6.b = var5;
               this.c.put(var4, var6);
            }
         }
      } catch (Exception var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void b(L1PcInstance var1, int var2) {
      String var3 = var1.aC().h();

      for (CharacterGiftTable.L1R_b var4 : this.d.values()) {
         if (var4.a == var2 && (var4.e.contains(var3) || var4.e.contains("A"))) {
            ItemTable.a(var1, var4.b, var4.c, var4.d);
         }
      }
   }

   public void a(L1PcInstance var1) {
      CharacterGiftTable.L1R_a var2;
      if (this.c.containsKey(var1.fr())) {
         var2 = this.c.get(var1.fr());
      } else {
         var2 = new CharacterGiftTable.L1R_a(var1.fr(), null);
         this.a(var2);
      }

      var1.a(new S_PacketBox(188, var2.b));
   }

   public void a(L1PcInstance var1, int var2) {
      if (!this.c.containsKey(var1.fr())) {
         System.out.println("CharacterGiftTable has some error , ID=" + var1.fr());
      } else {
         CharacterGiftTable.L1R_a var3 = this.c.get(var1.fr());
         if (var2 < var3.b.length && var3.b[var2] == 0) {
            var3.b[var2] = 1;
            this.b(var3);
            this.b(var1, var2);
         }
      }
   }

   private void a(CharacterGiftTable.L1R_a var1) {
      if (!this.c.containsKey(var1.a)) {
         Connection var2 = null;
         PreparedStatement var3 = null;

         try {
            var2 = DatabaseFactory.a().b();
            var3 = var2.prepareStatement("INSERT INTO character_gift SET objid=?, data=?");
            var3.setInt(1, var1.a);
            var3.setBytes(2, var1.b);
            var3.execute();
            this.c.put(var1.a, var1);
         } catch (SQLException var8) {
            a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
         } finally {
            SQLUtil.a(var3);
            SQLUtil.a(var2);
         }
      }
   }

   private void b(CharacterGiftTable.L1R_a var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE character_gift SET  data=? WHERE objid=" + var1.a);
         var3.setBytes(1, var1.b);
         var3.execute();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   private class L1R_a {
      public int a = 0;
      public byte[] b = new byte[512];

      private L1R_a(int var2) {
         this.a = var2;
      }

      // $VF: synthetic method
      L1R_a(int var2, CharacterGiftTable.L1R_a var3) {
         this(var2);
      }
   }

   private class L1R_b {
      public int a;
      public int b;
      public int c;
      public int d;
      public String e;

      private L1R_b() {
      }

      // $VF: synthetic method
      L1R_b(CharacterGiftTable.L1R_b var2) {
         this();
      }
   }
}
