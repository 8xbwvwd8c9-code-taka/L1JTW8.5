package l1r.aq;

import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ap.L1PcInstance;
import l1r.be.S_HPMeter;
import l1r.be.S_PacketBox;
import l1r.be.S_Party;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;
import l1r.bf.S_121;
import l1r.bi.GeneralThreadPool;

public class L1Party {
   private static final Logger a = Logger.getLogger(L1Party.class.getName());
   private final CopyOnWriteArrayList<L1PcInstance> b = new CopyOnWriteArrayList<>();
   private L1PcInstance c = null;

   public L1Party(L1PcInstance var1) {
      this.c = var1;
      this.b.add(var1);
      this.h(var1);
      GeneralThreadPool.a().b(new L1Party.a(null), 3000L);
   }

   public void a(L1PcInstance var1) {
      this.b.add(var1);
      this.h(var1);
   }

   private void h(L1PcInstance var1) {
      var1.a(this);
      this.k(var1);
      if (var1.x() && var1.h(121) && !this.i(var1)) {
         for (L1PcInstance var4 : this.b) {
            new S_121().a(var4, 0);
         }
      } else if (this.i(var1)) {
         for (L1PcInstance var2 : this.b) {
            new S_121().a(var2, 0);
         }
      }
   }

   private boolean i(L1PcInstance var1) {
      for (L1PcInstance var2 : this.b) {
         if (var2.fr() != var1.fr() && var2.x() && var2.h(121)) {
            return true;
         }
      }

      return false;
   }

   public void b(L1PcInstance var1) {
      if (!this.e(var1) && this.b.size() > 2) {
         this.j(var1);

         for (L1PcInstance var2 : this.c()) {
            var2.a(new S_ServerMessage(420, var1.et()));
         }

         var1.a(new S_ServerMessage(420, var1.et()));
      } else {
         this.e();
      }
   }

   public void c(L1PcInstance var1) {
      if (this.b.size() <= 2) {
         this.e();
      } else {
         this.j(var1);

         for (L1PcInstance var2 : this.c()) {
            var2.a(new S_ServerMessage(420, var1.et()));
         }

         var1.a(new S_ServerMessage(419));
      }
   }

   private void e() {
      for (L1PcInstance var1 : this.b) {
         if (!this.e(var1)) {
            this.c.a(new S_ServerMessage(420, var1.et()));
            this.j(var1);
            var1.a(new S_ServerMessage(418));
         } else {
            var1.a(new S_ServerMessage(418));
            this.j(var1);
         }
      }
   }

   private void j(L1PcInstance var1) {
      if (this.b.contains(var1)) {
         L1Master.a().c(var1);
         this.b.remove(var1);
         var1.a((L1Party)null);

         for (L1PcInstance var2 : this.b) {
            var2.a(new S_HPMeter(var1.fr(), 255, 255));
            var1.a(new S_HPMeter(var2.fr(), 255, 255));
         }

         var1.a(new S_HPMeter(var1.fr(), 255, 255));
         if (var1.bB(121)) {
            var1.bz(121);
            if (this.b.size() <= 1) {
               return;
            }
         }

         if (var1.x() && var1.h(121) && !this.i(var1)) {
            for (L1PcInstance var5 : this.b) {
               var5.bz(121);
            }
         } else {
            if (this.i(var1)) {
               for (L1PcInstance var4 : this.b) {
                  new S_121().a(var4, 0);
               }
            }
         }
      }
   }

   private void k(L1PcInstance var1) {
      for (L1PcInstance var2 : this.b) {
         if (var1.fr() != this.c.fr() || this.b.size() != 1) {
            if (var2.fr() != var1.fr()) {
               var2.a(new S_Party(105, var1));
               var2.a(new S_ProtoBuffers(539, var1));
               var2.a(new S_HPMeter(var1));
            } else {
               for (L1PcInstance var4 : this.c()) {
                  var1.a(new S_HPMeter(var4));
               }

               var1.a(new S_Party(104, var1));
            }

            var2.a(new S_PacketBox(178));
         }
      }
   }

   public boolean d(L1PcInstance var1) {
      return this.b.contains(var1);
   }

   public L1PcInstance a() {
      return this.c;
   }

   public boolean e(L1PcInstance var1) {
      return var1.fr() == this.c.fr();
   }

   public void f(L1PcInstance var1) {
      for (L1PcInstance var2 : this.b) {
         var2.a(new S_HPMeter(var1));
      }
   }

   public void g(L1PcInstance var1) {
      this.c = var1;

      for (L1PcInstance var2 : this.c()) {
         var2.a(new S_Party(106, var1));
      }
   }

   public int b() {
      return this.b.size();
   }

   public CopyOnWriteArrayList<L1PcInstance> c() {
      return this.b;
   }

   private class a extends TimerTask {
      private a() {
      }

      @Override
      public void run() {
         try {
            if (L1Party.this.b.isEmpty()) {
               return;
            }

            for (L1PcInstance var1 : L1Party.this.b) {
               var1.a(new S_Party(110, var1));
            }

            this.a(3000);
         } catch (Exception var3) {
            L1Party.a.log(Level.SEVERE, var3.getLocalizedMessage(), var3);
         }
      }

      private void a(int var1) {
         GeneralThreadPool.a().b(this, var1);
      }

      // $VF: synthetic method
      a(L1Party.a var2) {
         this();
      }
   }
}
