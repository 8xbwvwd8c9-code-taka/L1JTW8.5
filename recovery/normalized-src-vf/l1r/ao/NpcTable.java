package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1NpcInstance;
import l1r.bh.L1Npc;
import l1r.bi.LineageUtil;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class NpcTable {
   private static final Logger a = Logger.getLogger(NpcTable.class.getName());
   private final boolean b;
   private static NpcTable c;
   private final HashMap<Integer, L1Npc> d = new HashMap<>();
   private static final Map<String, Integer> e = d();

   public static NpcTable a() {
      if (c == null) {
         c = new NpcTable();
      }

      return c;
   }

   public boolean b() {
      return this.b;
   }

   private NpcTable() {
      this.c();
      this.b = true;
   }

   private void c() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM npc");
         var3 = var2.executeQuery();

         while (var3.next()) {
            L1Npc var4 = new L1Npc();
            int var5 = var3.getInt("npcid");
            var4.a(var5);
            var4.a(var3.getString("name"));
            var4.d(var3.getString("nameid"));
            var4.b(var3.getString("impl"));
            var4.k(var3.getInt("gfxid"));
            var4.b(var3.getInt("lvl"));
            var4.c(var3.getInt("hp"));
            var4.d(var3.getInt("mp"));
            var4.e(var3.getInt("ac"));
            var4.a(var3.getByte("str"));
            var4.b(var3.getByte("con"));
            var4.c(var3.getByte("dex"));
            var4.d(var3.getByte("wis"));
            var4.e(var3.getByte("intel"));
            var4.f(var3.getInt("mr"));
            var4.g(var3.getInt("exp"));
            var4.h(var3.getInt("lawful"));
            var4.c(var3.getString("size"));
            var4.i(var3.getInt("weakAttr"));
            var4.j(var3.getInt("ranged"));
            var4.c(var3.getBoolean("tamable"));
            var4.d(var3.getInt("can_move") > 0);
            var4.e(var3.getInt("can_attack") > 0);
            var4.m(var3.getBoolean("isDwarf"));
            var4.I(var3.getInt("base_damage"));
            var4.J(var3.getInt("random_damage"));
            var4.l(var3.getInt("undead"));
            var4.m(var3.getInt("poison_atk"));
            var4.f(var3.getBoolean("agro"));
            var4.a(var3.getBoolean("agrososc"));
            var4.b(var3.getBoolean("agrocoi"));
            Integer var6 = NpcTable.e.get(var3.getString("family"));
            if (var6 == null) {
               var4.n(0);
            } else {
               var4.n(var6);
            }

            int var7 = var3.getInt("agrofamily");
            if (var4.D() == 0 && var7 == 1) {
               var4.o(0);
            } else {
               var4.o(var7);
            }

            var4.p(var3.getInt("agrogfxid1"));
            var4.q(var3.getInt("agrogfxid2"));
            var4.g(var3.getBoolean("picupitem"));
            var4.r(var3.getInt("digestitem"));
            var4.h(var3.getBoolean("bravespeed"));
            var4.s(var3.getInt("hprinterval"));
            var4.t(var3.getInt("hpr"));
            var4.u(var3.getInt("mprinterval"));
            var4.v(var3.getInt("mpr"));
            var4.i(var3.getBoolean("teleport"));
            var4.w(var3.getInt("randomlevel"));
            var4.x(var3.getInt("randomhp"));
            var4.y(var3.getInt("randommp"));
            var4.z(var3.getInt("randomac"));
            var4.A(var3.getInt("randomexp"));
            var4.B(var3.getInt("randomlawful"));
            var4.C(var3.getInt("damage_reduction"));
            var4.j(var3.getBoolean("hard"));
            var4.k(var3.getBoolean("doppel"));
            var4.l(var3.getBoolean("IsErase"));
            var4.D(var3.getInt("bowActId"));
            var4.E(var3.getInt("karma"));
            var4.F(var3.getInt("transform_id"));
            var4.G(var3.getInt("transform_gfxid"));
            var4.H(var3.getInt("light_size"));
            var4.n(var3.getBoolean("amount_fixed"));
            var4.o(var3.getBoolean("change_head"));
            var4.p(var3.getBoolean("cant_resurrect"));
            var4.e(var3.getString("normal_action"));
            var4.f(var3.getString("caotic_action"));
            var4.g(var3.getString("craft_list"));
            var4.K(var3.getInt("mobList_number"));
            this.d.put(var5, var4);
         }
      } catch (SQLException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public L1Npc a(int var1) {
      return this.d.get(var1);
   }

   public L1NpcInstance b(int var1) {
      L1Npc var2 = this.a(var1);
      if (var2 == null) {
         throw new IllegalArgumentException(String.format("NpcTemplate: %d not found", var1));
      } else {
         return LineageUtil.a(var2);
      }
   }

   private static Map<String, Integer> d() {
      HashMap var0 = new HashMap<>();
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("select distinct(family) as family from npc WHERE NOT trim(family) =''");
         var3 = var2.executeQuery();
         int var4 = 1;

         while (var3.next()) {
            String var5 = var3.getString("family");
            var0.put(var5, var4++);
         }
      } catch (SQLException var9) {
         a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }

      return var0;
   }

   public int a(String var1) {
      for (L1Npc var2 : this.d.values()) {
         if (var2.c().replace(" ", "").equals(var1)) {
            return var2.b();
         }
      }

      return 0;
   }
}
