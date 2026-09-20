package l1r.aq;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.ap.L1KeeperInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.be.S_War;
import l1r.bi.GeneralThreadPool;

public class L1War {
   private static final Logger c = Logger.getLogger(L1War.class.getName());
   public static final int a = 1;
   public static final int b = 2;
   private final L1Clan d;
   private int e = 0;
   private boolean f = false;
   private final ConcurrentHashMap<String, L1Clan> g = new ConcurrentHashMap<>();
   private Calendar h;
   private int i = 0;

   public int a() {
      Calendar var1 = Calendar.getInstance();
      return (int)(this.h.getTimeInMillis() / 1000L - var1.getTimeInMillis() / 1000L);
   }

   public L1War(int var1, String var2, String var3) {
      L1Clan var4 = ClanTable.a().c(var3);
      L1Clan var5 = ClanTable.a().c(var2);
      this.e = var1;
      this.d = var4;
      this.g.put(var2, var5);
      if (var1 == 1) {
         this.i = this.d.m();
         this.h = CastleTable.a().a(this.i).d();
         GeneralThreadPool.a().a(new L1War.L1R_a(null), 1000L);
      } else if (var1 == 2) {
         GeneralThreadPool.a().a(new L1War.L1R_b(null), 60000L);
      }

      L1World.a().a(this);
      this.b(var5);
   }

   private void b(L1Clan var1) {
      for (L1PcInstance var2 : var1.b()) {
         if (this.e == 1) {
            var2.a(new S_ProtoBuffers(76, var2));
         }

         var2.a(new S_War(1, var1.f(), this.d.f()));
      }

      for (L1PcInstance var4 : this.d.b()) {
         if (this.e == 1) {
            var4.a(new S_ProtoBuffers(76, var4));
         }

         var4.a(new S_War(1, var1.f(), this.d.f()));
      }
   }

   public void a(String var1, String var2) {
      L1Clan var3 = ClanTable.a().c(var1);

      for (L1PcInstance var4 : var3.b()) {
         var4.a(new S_ProtoBuffers(76, 0));
         var4.a(new S_War(2, var1, var2));
      }

      L1Clan var7 = ClanTable.a().c(var2);

      for (L1PcInstance var8 : var7.b()) {
         var8.a(new S_War(4, var2, var1));
      }

      if (this.e == 1) {
         this.g.remove(var1);
      }

      if (this.e == 2 || this.g.isEmpty()) {
         this.f = true;
         L1World.a().b(this);
      }

      L1World.a().a(new S_ServerMessage(231, var2, var1));
   }

   public void b(String var1, String var2) {
      L1Clan var3 = ClanTable.a().c(var1);

      for (L1PcInstance var4 : var3.b()) {
         var4.a(new S_ProtoBuffers(76, 0));
         var4.a(new S_War(3, var1, var2));
      }

      L1Clan var7 = ClanTable.a().c(var2);

      for (L1PcInstance var8 : var7.b()) {
         var8.a(new S_War(3, var1, var2));
      }

      if (this.e == 1) {
         this.g.remove(var1);
      }

      if (this.e == 2 || this.g.isEmpty()) {
         this.f = true;
         L1World.a().b(this);
      }

      L1World.a().a(new S_ServerMessage(227, var1, var2));
   }

   public void a(String var1) {
      L1World.a().a(new S_ServerMessage(231, var1, this.d.f()));

      for (L1PcInstance var2 : this.d.b()) {
         for (String var4 : this.g.keySet()) {
            var2.a(new S_ProtoBuffers(76, 0));
            var2.a(new S_War(3, this.d.f(), var4));
         }
      }

      for (L1Clan var6 : this.g.values()) {
         L1World.a().a(new S_ServerMessage(227, this.d.f(), var6.f()));

         for (L1PcInstance var10 : var6.b()) {
            var10.a(new S_ProtoBuffers(76, 0));
            var10.a(new S_War(3, var6.f(), this.d.f()));
         }
      }

      if (this.d.f().contains("安安妳好再見_")) {
         for (L1Object var7 : L1World.a().g().values()) {
            if (var7 instanceof L1KeeperInstance) {
               L1KeeperInstance var11 = (L1KeeperInstance)var7;
               if (L1CastleLocation.a(this.i, var11)) {
                  var11.aa_();
               }
            }
         }
      }

      this.f = true;
      L1World.a().b(this);
   }

   private void f() {
      for (L1Clan var1 : this.g.values()) {
         L1World.a().a(new S_ServerMessage(231, this.d.f(), var1.f()));
         L1World.a().a(new S_ServerMessage(227, this.d.f(), var1.f()));

         for (L1PcInstance var3 : var1.b()) {
            var3.a(new S_ProtoBuffers(76, 0));
            var3.a(new S_War(3, var1.f(), this.d.f()));
         }
      }

      for (L1PcInstance var5 : this.d.b()) {
         for (String var7 : this.g.keySet()) {
            var5.a(new S_ProtoBuffers(76, 0));
            var5.a(new S_War(4, this.d.f(), var7));
         }
      }

      this.f = true;
      L1World.a().b(this);
   }

   public boolean b(String var1) {
      return this.g.containsKey(var1) || this.d.f().equalsIgnoreCase(var1);
   }

   public boolean c(String var1, String var2) {
      return this.b(var1) && this.b(var2);
   }

   public ArrayList<L1Clan> c(String var1) {
      ArrayList var2 = new ArrayList<>();
      if (this.d.f().equalsIgnoreCase(var1)) {
         var2.addAll(this.g.values());
      } else {
         var2.add(this.d);
      }

      return var2;
   }

   public void a(L1Clan var1) {
      if (!this.g.containsKey(var1.f())) {
         this.g.put(var1.f(), var1);
      }

      this.b(var1);
   }

   public L1Clan b() {
      return this.d;
   }

   public int c() {
      return this.e;
   }

   public int d() {
      return this.i;
   }

   private class L1R_a extends TimerTask {
      private L1R_a() {
      }

      @Override
      public void run() {
         try {
            if (L1War.this.f) {
               return;
            }

            Calendar var1 = Calendar.getInstance();
            if (var1.after(L1War.this.h)) {
               L1War.this.f();
               return;
            }

            this.a(1000L);
         } catch (Exception var2) {
            L1War.c.log(Level.SEVERE, var2.getLocalizedMessage(), var2);
         }
      }

      private void a(long var1) {
         GeneralThreadPool.a().a(this, var1);
      }

      // $VF: synthetic method
      L1R_a(L1War.L1R_a var2) {
         this();
      }
   }

   private class L1R_b extends TimerTask {
      private int b = 0;

      private L1R_b() {
      }

      @Override
      public void run() {
         try {
            if (L1War.this.f) {
               return;
            }

            if (++this.b > 240) {
               for (L1Clan var1 : L1War.this.g.values()) {
                  L1War.this.b(var1.f(), L1War.this.d.f());
               }

               return;
            }

            this.a(60000L);
         } catch (Exception var3) {
            L1War.c.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         }
      }

      private void a(long var1) {
         GeneralThreadPool.a().a(this, var1);
      }

      // $VF: synthetic method
      L1R_b(L1War.L1R_b var2) {
         this();
      }
   }
}
