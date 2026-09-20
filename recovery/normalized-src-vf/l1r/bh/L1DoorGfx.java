package l1r.bh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1DoorGfx {
   private static final Logger a = Logger.getLogger(L1DoorGfx.class.getName());
   private final int b;
   private final int c;
   private final int d;
   private final int e;
   private final int f;
   private final int g;

   private L1DoorGfx(int var1, int var2, int var3, int var4, int var5, int var6) {
      this.b = var1;
      this.c = var2;
      this.f = var5;
      this.g = var6;
      this.d = var3;
      this.e = var4;
   }

   public int a() {
      return this.b;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.f;
   }

   public int d() {
      return this.g;
   }

   public int e() {
      return this.d;
   }

   public int f() {
      return this.e;
   }

   public static L1DoorGfx a(int var0) {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM door_gfxs WHERE gfxid = ?");
         var2.setInt(1, var0);
         var3 = var2.executeQuery();
         if (var3.next()) {
            int var4 = var3.getInt("gfxid");
            int var5 = var3.getInt("direction");
            int var6 = var3.getInt("right_edge_offset");
            int var7 = var3.getInt("left_edge_offset");
            int var8 = var3.getInt("enterX_offset");
            int var9 = var3.getInt("enterY_offset");
            return new L1DoorGfx(var4, var5, var8, var9, var6, var7);
         }

         System.out.println("DoorGfx: " + var0 + "is not found");
      } catch (SQLException var14) {
         a.log(Level.SEVERE, var14.getLocalizedMessage(), var14);
         return null;
      } finally {
         SQLUtil.a(var3, var2, var1);
      }

      return null;
   }
}
