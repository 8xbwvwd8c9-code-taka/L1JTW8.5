package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bh.L1PetType;
import l1r.bi.IntRange;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class PetTypeTable {
   private static final Logger a = Logger.getLogger(PetTypeTable.class.getName());
   private static PetTypeTable b;
   private final HashMap<Integer, L1PetType> c = new HashMap<>();
   private final Set<String> d = new HashSet<>();

   public static void a() {
      b = new PetTypeTable();
   }

   public static PetTypeTable b() {
      return b;
   }

   private PetTypeTable() {
      this.c();
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM pettypes");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("BaseNpcId");
            String var5 = var3.getString("Name");
            int var6 = var3.getInt("ItemIdForTaming");
            int var7 = var3.getInt("HpUpMin");
            int var8 = var3.getInt("HpUpMax");
            int var9 = var3.getInt("MpUpMin");
            int var10 = var3.getInt("MpUpMax");
            int var11 = var3.getInt("EvolvItemId");
            int var12 = var3.getInt("NpcIdForEvolving");
            int[] var13 = new int[5];

            for (int var14 = 0; var14 < 5; var14++) {
               var13[var14] = var3.getInt("MessageId" + (var14 + 1));
            }

            int var23 = var3.getInt("DefyMessageId");
            boolean var15 = var3.getBoolean("canUseEquipment");
            IntRange var16 = new IntRange(var7, var8);
            IntRange var17 = new IntRange(var9, var10);
            this.c.put(var4, new L1PetType(var4, var5, var6, var16, var17, var11, var12, var13, var23, var15));
            this.d.add(var5.toLowerCase());
         }
      } catch (SQLException var21) {
         a.log(Level.SEVERE, var21.getLocalizedMessage(), var21);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public L1PetType a(int var1) {
      return this.c.get(var1);
   }

   public boolean a(String var1) {
      return this.d.contains(var1.toLowerCase());
   }
}
