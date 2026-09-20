package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ai.IdFactory;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.aq.L1MobGroupInfo;
import l1r.aq.L1World;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class MobGroupTable {
   private static final Logger a = Logger.getLogger(MobGroupTable.class.getName());
   private static MobGroupTable b;
   private final HashMap<Integer, MobGroupTable.L1R_b> c = new HashMap<>();
   private boolean d;
   private boolean e;

   public static MobGroupTable a() {
      if (b == null) {
         b = new MobGroupTable();
      }

      return b;
   }

   private MobGroupTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM mobgroup");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("id");
            MobGroupTable.L1R_b var5 = new MobGroupTable.L1R_b(null);
            var5.c = var3.getBoolean("remove_group_if_leader_die");

            for (int var6 = 1; var6 <= 7; var6++) {
               int var7 = var3.getInt("minion" + var6 + "_id");
               int var8 = var3.getInt("minion" + var6 + "_count");
               var5.b.add(new MobGroupTable.L1R_a(var7, var8, null));
            }

            this.c.put(var4, var5);
         }
      } catch (SQLException var12) {
         a.log(Level.SEVERE, var12.getLocalizedMessage(), var12);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   public void a(L1NpcInstance var1, int var2, boolean var3, boolean var4) {
      MobGroupTable.L1R_b var5 = this.c.get(var2);
      if (var5 != null) {
         this.d = var3;
         this.e = var4;
         L1MobGroupInfo var6 = new L1MobGroupInfo();
         var6.a(var5.c);
         var6.c(var1);

         for (MobGroupTable.L1R_a var7 : var5.b) {
            if (!var7.a()) {
               for (int var9 = 0; var9 < var7.c; var9++) {
                  L1NpcInstance var10 = this.a(var1, var7.b);
                  if (var10 != null) {
                     var6.c(var10);
                  }
               }
            }
         }
      }
   }

   private L1NpcInstance a(L1NpcInstance var1, int var2) {
      L1NpcInstance var3 = null;

      try {
         var3 = NpcTable.a().b(var2);
         var3.cF(IdFactory.a().c());
         var3.ct(var1.fb());
         var3.cE(var1.fp());
         var3.u(var1.ad());
         var3.l(var1.ai());
         var3.cG(var1.fs() + Random.a(5) - 2);
         var3.cH(var1.ft() + Random.a(5) - 2);
         if (!this.a(var3)) {
            var3.cG(var1.fs());
            var3.cH(var1.ft());
         }

         var3.q(var3.fs());
         var3.r(var3.ft());
         if (var3 instanceof L1MonsterInstance) {
            ((L1MonsterInstance)var3).a(var1);
         }

         var3.a(var1.R());
         var3.g(var1.Z());
         var3.p(var1.S());
         if (var3 instanceof L1MonsterInstance && var3.fp() == 666) {
            ((L1MonsterInstance)var3).c(true);
         }

         L1World.a().a(var3);
         L1World.a().c(var3);
         if (var3 instanceof L1MonsterInstance && !this.e && var3.ac() == 0) {
            var3.Z_();
         }

         var3.fg();
         var3.a_(0);
      } catch (Exception var5) {
         a.log(Level.SEVERE, var5.getLocalizedMessage(), var5);
      }

      return var3;
   }

   private boolean a(L1NpcInstance var1) {
      if (var1.fq().a(var1.fu()) && var1.fq().c(var1.fs(), var1.ft())) {
         if (this.d) {
            return true;
         }

         if (L1World.a().f(var1).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private class L1R_a {
      private final int b;
      private final int c;

      private L1R_a(int var2, int var3) {
         this.b = var2;
         this.c = var3;
      }

      private boolean a() {
         return this.b == 0 && this.c == 0;
      }

      // $VF: synthetic method
      L1R_a(int var2, int var3, MobGroupTable.L1R_a var4) {
         this(var2, var3);
      }
   }

   private class L1R_b {
      private final ArrayList<MobGroupTable.L1R_a> b = new ArrayList<>();
      private boolean c;

      private L1R_b() {
      }

      // $VF: synthetic method
      L1R_b(MobGroupTable.L1R_b var2) {
         this();
      }
   }
}
