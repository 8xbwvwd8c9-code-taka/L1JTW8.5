package l1r.aq;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.be.S_OwnCharAttrDef;
import l1r.be.S_PacketBox;
import l1r.be.S_SPMR;
import l1r.be.S_ServerMessage;
import l1r.bi.SQLUtil;
import l1r.l1j.server.DatabaseFactory;

public class L1Master {
   private static final Logger a = Logger.getLogger(L1Master.class.getName());
   private static L1Master b;
   private static HashMap<Integer, CopyOnWriteArrayList<L1PcInstance>> c = new HashMap<>();
   private static HashMap<Integer, String> d = new HashMap<>();

   public static L1Master a() {
      if (b == null) {
         b = new L1Master();
      }

      return b;
   }

   private L1Master() {
      Connection var1 = null;
      PreparedStatement var2 = null;
      ResultSet var3 = null;

      try {
         var1 = DatabaseFactory.a().b();
         var2 = var1.prepareStatement("SELECT * FROM characters ");
         var3 = var2.executeQuery();

         while (var3.next()) {
            int var4 = var3.getInt("objid");
            int var5 = var3.getInt("MasterID");
            String var6 = var3.getString("char_name");
            if (var5 > 0) {
               L1PcInstance var7 = L1PcInstance.b(var6);
               this.a(var5, var7);
            } else if (var5 < 0) {
               d.put(var4, var6);
            }
         }
      } catch (SQLException var11) {
         a.log(Level.SEVERE, var11.getLocalizedMessage(), var11);
      } finally {
         SQLUtil.a(var3, var2, var1);
      }
   }

   private String d(int var1) {
      String var2 = null;
      Connection var3 = null;
      PreparedStatement var4 = null;
      ResultSet var5 = null;

      try {
         var3 = DatabaseFactory.a().b();
         var4 = var3.prepareStatement("SELECT * FROM characters  WHERE objid=?");
         var4.setInt(1, var1);
         var5 = var4.executeQuery();
         if (var5.next()) {
            var2 = var5.getString("char_name");
            d.put(var1, var2);
         }
      } catch (SQLException var10) {
         a.log(Level.SEVERE, var10.getLocalizedMessage(), var10);
      } finally {
         SQLUtil.a(var5, var4, var3);
      }

      return var2;
   }

   private void a(String var1) {
      Connection var2 = null;
      PreparedStatement var3 = null;

      try {
         var2 = DatabaseFactory.a().b();
         var3 = var2.prepareStatement("UPDATE characters SET MasterID = 0 WHERE char_name =?");
         var3.setString(1, var1);
         var3.execute();
         var3.close();
      } catch (SQLException var8) {
         a.log(Level.SEVERE, var8.getLocalizedMessage(), var8);
      } finally {
         SQLUtil.a(var3);
         SQLUtil.a(var2);
      }
   }

   public String a(int var1) {
      return d.containsKey(var1) ? d.get(var1) : this.d(var1);
   }

   public CopyOnWriteArrayList<L1PcInstance> b(int var1) {
      return c.get(var1);
   }

   private ArrayList<L1PcInstance> e(int var1) {
      ArrayList var2 = new ArrayList<>();

      for (L1PcInstance var3 : c.get(var1)) {
         L1Object var5 = L1World.a().a(var3.fr());
         if (var5 instanceof L1PcInstance) {
            var2.add((L1PcInstance)var5);
         }
      }

      return var2;
   }

   public int c(int var1) {
      return c.containsKey(var1) ? c.get(var1).size() : 0;
   }

   public void a(int var1, L1PcInstance var2) {
      if (c.containsKey(var1)) {
         c.get(var1).add(var2);
      } else {
         CopyOnWriteArrayList var3 = new CopyOnWriteArrayList<>();
         var3.add(var2);
         c.put(var1, var3);
      }

      this.b(var2, 4059);
   }

   public void a(int var1, String var2) {
      for (int var3 = 0; var3 < c.get(var1).size(); var3++) {
         L1PcInstance var4 = c.get(var1).get(var3);
         if (var4.et().equalsIgnoreCase(var2)) {
            c.get(var1).remove(var3);
            break;
         }
      }

      L1PcInstance var6 = L1World.a().a(var2);
      if (var6 == null) {
         this.a(var2);
      } else {
         var6.aV(0);
         var6.I();
         var6.a(new S_ServerMessage(2977));
         this.f(var6);
      }

      L1Object var7 = L1World.a().a(var1);
      if (var7 instanceof L1PcInstance) {
         ((L1PcInstance)var7).a(new S_ServerMessage(2977));
      }

      if (a().c(var1) <= 0) {
         if (var7 instanceof L1PcInstance) {
            ((L1PcInstance)var7).aV(0);
            ((L1PcInstance)var7).I();
         } else {
            String var5 = a().a(var1);
            a().a(var5);
         }
      }
   }

   public void a(L1PcInstance var1) {
      if (var1.cE() == -1) {
         for (L1PcInstance var2 : this.e(var1.fr())) {
            this.f(var2);
         }
      }
   }

   public void b(L1PcInstance var1) {
      if (var1.cE() == -1) {
         for (L1PcInstance var2 : this.e(var1.fr())) {
            int var4 = 0;
            if (var2.q()) {
               for (L1PcInstance var5 : var2.aL().c()) {
                  if (var5.cE() == var1.fr()) {
                     var4++;
                  }
               }
            } else {
               var4 = 1;
            }

            int var8 = 4058 + var4;
            this.b(var2, var8);
         }
      } else if (var1.cE() > 0) {
         L1Object var7 = L1World.a().a(var1.cE());
         if (var7 != null) {
            this.b(var1, 4059);
         }
      }
   }

   public void c(L1PcInstance var1) {
      if (var1.cE() < 0) {
         for (L1PcInstance var2 : this.e(var1.fr())) {
            if (var1.aL().d(var2)) {
               this.a(var2, -4);
            }
         }
      } else if (var1.cE() > 0) {
         for (L1PcInstance var4 : this.e(var1.cE())) {
            if (var4.fr() == var1.fr()) {
               this.a(var4, -8);
            } else if (var1.aL().d(var4)) {
               if (var1.aL().e(var1)) {
                  this.a(var4, -8);
               } else {
                  this.a(var4, -1);
               }
            }
         }
      }
   }

   public void d(L1PcInstance var1) {
      if (var1.cE() == -1) {
         for (L1PcInstance var2 : this.e(var1.fr())) {
            if (var1.aL().d(var2)) {
               this.a(var2, 4);
            }
         }
      } else if (var1.cE() > 0) {
         for (L1PcInstance var4 : this.e(var1.cE())) {
            if (var1.fr() != var4.fr() && var1.aL().d(var4)) {
               this.a(var4, 1);
            }
         }
      }
   }

   public void e(L1PcInstance var1) {
      if (var1.cE() == -1) {
         for (L1PcInstance var2 : this.e(var1.fr())) {
            if (var1.aL().d(var2)) {
               this.a(var2, 4);
            }
         }
      } else if (var1.cE() > 0) {
         int var5 = 0;

         for (L1PcInstance var6 : this.e(var1.cE())) {
            if (var1.fr() != var6.fr() && var1.aL().d(var6)) {
               var5 = this.a(var6, 1);
            }
         }

         if (var5 != 0) {
            this.c(var1, 4059);
            this.b(var1, var5);
         }
      }
   }

   private int a(L1PcInstance var1, int var2) {
      int var3 = 0;

      for (int var4 = 4059; var4 <= 4066; var4++) {
         if (var1.bB(var4)) {
            var3 = Math.min(Math.max(4059, var4 + var2), 4066);
            this.c(var1, var4);
            this.b(var1, var3);
            break;
         }
      }

      return var3;
   }

   private void b(L1PcInstance var1, int var2) {
      var1.j(var2, 0);
      var1.a(new S_PacketBox(147, var2 - 4059, 1));
      if (var2 == 4062 || var2 == 4066) {
         var1.a(new S_PacketBox(132, var1.u()));
      }

      if (var2 >= 4061 && var2 <= 4062) {
         var1.bY(2);
         var1.bZ(2);
         var1.ca(2);
         var1.cb(2);
      }

      if (var2 >= 4060 && var2 <= 4062) {
         var1.co(1);
         var1.a(new S_SPMR(var1));
      }

      if (var2 >= 4059 && var2 <= 4062) {
         var1.bL(-1);
         var1.a(new S_OwnCharAttrDef(var1));
      }

      if (var2 >= 4065 && var2 <= 4066) {
         var1.bY(6);
         var1.bZ(6);
         var1.ca(6);
         var1.cb(6);
      }

      if (var2 >= 4064 && var2 <= 4066) {
         var1.co(3);
         var1.a(new S_SPMR(var1));
      }

      if (var2 >= 4063 && var2 <= 4066) {
         var1.bL(-3);
         var1.a(new S_OwnCharAttrDef(var1));
      }
   }

   private void c(L1PcInstance var1, int var2) {
      var1.bz(var2);
      var1.a(new S_PacketBox(147, var2 - 4059, 0));
      if (var2 == 4062 || var2 == 4066) {
         var1.a(new S_PacketBox(132, var1.u()));
      }

      if (var2 >= 4061 && var2 <= 4062) {
         var1.bY(-2);
         var1.bZ(-2);
         var1.ca(-2);
         var1.cb(-2);
      }

      if (var2 >= 4060 && var2 <= 4062) {
         var1.co(-1);
         var1.a(new S_SPMR(var1));
      }

      if (var2 >= 4059 && var2 <= 4062) {
         var1.bL(1);
         var1.a(new S_OwnCharAttrDef(var1));
      }

      if (var2 >= 4065 && var2 <= 4066) {
         var1.bY(-6);
         var1.bZ(-6);
         var1.ca(-6);
         var1.cb(-6);
      }

      if (var2 >= 4064 && var2 <= 4066) {
         var1.co(-3);
         var1.a(new S_SPMR(var1));
      }

      if (var2 >= 4063 && var2 <= 4066) {
         var1.bL(3);
         var1.a(new S_OwnCharAttrDef(var1));
      }
   }

   public void removeDeletedMaster(int var1) {
      CopyOnWriteArrayList<L1PcInstance> var2 = c.remove(var1);
      d.remove(var1);
      if (var2 == null) return;
      for (L1PcInstance var3 : var2) {
         L1Object var4 = L1World.a().a(var3.fr());
         if (var4 instanceof L1PcInstance) {
            L1PcInstance var5 = (L1PcInstance)var4;
            if (var5.cE() == var1) {
               var5.aV(0);
               var5.I();
               this.f(var5);
            }
         }
      }
   }

   private void f(L1PcInstance var1) {
      for (int var2 = 4059; var2 <= 4066; var2++) {
         if (var1.bB(var2)) {
            this.c(var1, var2);
         }
      }
   }
}
