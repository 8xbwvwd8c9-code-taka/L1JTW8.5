package l1r.ao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.am.ListSprReader__obf_c;
import l1r.ap.L1NpcInstance;
import l1r.aq.L1Character;
import l1r.bf.S_000;
import l1r.bg.L1SkillDelay;
import l1r.bi.Random;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class MobSkillsTable {
   private static final Logger a = Logger.getLogger(MobSkillsTable.class.getName());
   private static MobSkillsTable b;
   private final HashMap<Integer, MobSkillsTable.L1R_a> c = new HashMap<>();

   public static MobSkillsTable a() {
      if (b == null) {
         b = new MobSkillsTable();
      }

      return b;
   }

   private MobSkillsTable() {
      this.b();
   }

   private void b() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM mobskills");
         var3 = var2.executeQuery();
         this.a(var3);
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private void a(ResultSet var1) throws SQLException {
      while (var1.next()) {
         int var2 = var1.getInt("npcid");
         MobSkillsTable.L1R_a var3;
         if (this.c.containsKey(var2)) {
            var3 = this.c.get(var2);
         } else {
            var3 = new MobSkillsTable.L1R_a(null);
            this.c.put(var2, var3);
         }

         MobSkillsTable.L1R_b var4 = new MobSkillsTable.L1R_b();
         var4.a = var1.getInt("probability");
         var4.b = var1.getInt("base_damage");
         var4.c = var1.getInt("random_damage");
         var4.d = var1.getInt("range");
         var4.e = var1.getInt("area");
         var4.f = var1.getInt("effect_id");
         var4.g = var1.getBoolean("is_target_effect");
         var4.h = var1.getBoolean("is_arrow");
         var4.i = var1.getInt("action");
         var4.j = var1.getInt("mp_consume");
         var4.k = var1.getInt("spell_count");
         var4.l = var1.getInt("spell_hp_percent");
         var4.m = var1.getInt("attr");
         var4.n = var1.getInt("summonid");
         var4.o = var1.getInt("polyid");
         var4.p = var1.getInt("skill_effect");
         var4.q = var1.getBoolean("is_skill_bind");
         var4.r = var1.getBoolean("is_skill_recall");
         var4.s = var1.getBoolean("is_skill_smoke");
         var4.t = var1.getInt("delay");
         var3.b.add(var4);
      }
   }

   public int a(L1NpcInstance var1, L1Character var2) {
      if (var1.ej()) {
         return 0;
      }

      if (!this.c.containsKey(var1.z())) {
         return 0;
      }

      if (var1.bB(64)) {
         return 0;
      }

      MobSkillsTable.L1R_a var3 = this.c.get(var1.z());
      MobSkillsTable.L1R_b var4 = null;

      for (MobSkillsTable.L1R_b var5 : var3.b) {
         if ((var5.k <= 0 || var1.aq() < var5.k) && var1.ea() * 100 / var1.ew() <= var5.l && Random.a(100) < var5.a) {
            var4 = var5;
            break;
         }
      }

      if (var4 == null) {
         return 0;
      }

      S_000 var7 = new S_000();
      if (var4.d < 0) {
         if (!var1.fu().e(var2.fu())) {
            return 0;
         }
      } else if (var1.fu().c(var2.fu()) > var4.d || !var1.i(var2.fs(), var2.ft())) {
         return 0;
      }

      if (var1.eb() < var4.j) {
         return 0;
      }

      int var8 = var1.eb() - var4.j;
      var1.i_(var8);
      var7.a(var1, var2, var4);
      if (var1.z() == 190280) {
         var1.aa_();
         return 0;
      }

      if (var4.t > 0) {
         L1SkillDelay.a(var1, var4.t);
      }

      if (var4.k > 0) {
         var1.I();
      }

      return ListSprReader__obf_c.a().a(var1.fe(), var4.i) + 100;
   }

   private class L1R_a {
      private final ArrayList<MobSkillsTable.L1R_b> b = new ArrayList<>();

      private L1R_a() {
      }

      // $VF: synthetic method
      L1R_a(MobSkillsTable.L1R_a var2) {
         this();
      }
   }

   public class L1R_b {
      public int a;
      public int b;
      public int c;
      public int d;
      public int e;
      public int f;
      public boolean g;
      public boolean h;
      public int i;
      public int j;
      public int k;
      public int l;
      public int m;
      public int n;
      public int o;
      public int p;
      public boolean q;
      public boolean r;
      public boolean s;
      public int t;
   }
}
