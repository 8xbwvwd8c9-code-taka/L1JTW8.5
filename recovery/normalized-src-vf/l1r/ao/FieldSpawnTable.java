package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1FieldObjectInstance;
import l1r.aq.L1World;
import l1r.bh.L1Npc;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class FieldSpawnTable {
   private static final Logger a = Logger.getLogger(FieldSpawnTable.class.getName());
   private static FieldSpawnTable b;
   private final ArrayList<String> c = new ArrayList<>();

   public static FieldSpawnTable a() {
      if (b == null) {
         b = new FieldSpawnTable();
      }

      return b;
   }

   private FieldSpawnTable() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM spawnlist_field");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("id");
            int var5 = var3.getInt("locx");
            int var6 = var3.getInt("locy");
            int var7 = var3.getInt("mapid");
            int var8 = var3.getInt("action_type");
            int var9 = var3.getInt("gfxid");
            if (this.c.contains(var5 + "," + var6 + "," + var7 + "," + var9)) {
               System.out.println("FieldSpawnTable id: " + var4 + " is repeat");
            } else {
               this.c.add(var5 + "," + var6 + "," + var7 + "," + var9);
               L1Npc var10 = NpcTable.a().a(190000);
               L1FieldObjectInstance var11 = new L1FieldObjectInstance(var10);
               var11.cF(IdFactory.a().c());
               var11.cw(var9);
               if (var9 >= 12901 && var9 <= 12905) {
                  var11.a("$19274");
               } else if (var9 == 14348 || var9 == 14350) {
                  var11.f("$22945");
               } else if (var9 == 14352 || var9 == 14354) {
                  var11.f("$22944");
               }

               var11.cG(var5);
               var11.cH(var6);
               var11.q(var5);
               var11.r(var6);
               var11.cE(var7);
               var11.ct(var3.getInt("heading"));
               var11.cy(var3.getInt("light"));
               if (var8 != 28 && var9 != 1026) {
                  var11.b(var8);
               } else {
                  var11.b(-1);
                  var11.cq(var8);
               }

               L1World.a().a(var11);
               L1World.a().c(var11);
            }
         }
      } catch (Exception var15) {
         a.log(Level.SEVERE, var15.getLocalizedMessage(), var15);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public L1FieldObjectInstance a(int var1, int var2, int var3, int var4) {
      L1Npc var5 = NpcTable.a().a(190000);
      var5.k(var1);
      L1FieldObjectInstance var6 = new L1FieldObjectInstance(var5);
      var6.cF(IdFactory.a().c());
      var6.cG(var2);
      var6.cH(var3);
      var6.cE(var4);
      var6.ct(0);
      var6.cy(5);
      var6.b(-1);
      L1World.a().a(var6);
      L1World.a().c(var6);
      return var6;
   }
}
