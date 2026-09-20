package l1r.bh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1DoorSpawn {
   private static final Logger a = Logger.getLogger(L1DoorSpawn.class.getName());
   private final int b;
   private final L1DoorGfx c;
   private final int d;
   private final int e;
   private final int f;
   private final int g;
   private final int h;
   private final boolean i;

   private L1DoorSpawn(int var1, L1DoorGfx var2, int var3, int var4, int var5, int var6, int var7, boolean var8) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = var4;
      this.f = var5;
      this.g = var6;
      this.h = var7;
      this.i = var8;
   }

   public int a() {
      return this.b;
   }

   public L1DoorGfx b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public int d() {
      return this.e;
   }

   public int e() {
      return this.f;
   }

   public int f() {
      return this.g;
   }

   public int g() {
      return this.h;
   }

   public boolean h() {
      return this.i;
   }

   public static List<L1DoorSpawn> i() {
      ArrayList var0 = new ArrayList<>();
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM spawnlist_door");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("id");
            int var5 = var3.getInt("gfxid");
            int var6 = var3.getInt("locx");
            int var7 = var3.getInt("locy");
            int var8 = var3.getInt("mapid");
            int var9 = var3.getInt("hp");
            int var10 = var3.getInt("keeper");
            boolean var11 = var3.getBoolean("isOpening");
            L1DoorGfx var12 = L1DoorGfx.a(var5);
            L1DoorSpawn var13 = new L1DoorSpawn(var4, var12, var6, var7, var8, var9, var10, var11);
            var0.add(var13);
         }
      } catch (SQLException var17) {
         a.log(Level.SEVERE, var17.getLocalizedMessage(), var17);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }

      return var0;
   }
}
