package l1r.aj;

import java.util.TimerTask;
import l1r.ao.ClanTable;
import l1r.ao.HouseTable;
import l1r.ap.L1DoorInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.bh.L1House;
import l1r.bi.GeneralThreadPool;
import l1r.bj.ClientThread;

public class C_Door extends ClientBasePacket {
   public C_Door(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         this.d();
         this.d();
         int var4 = this.b();
         L1DoorInstance var5 = (L1DoorInstance)L1World.a().a(var4);
         if (var5 != null) {
            if (var5.i() == 6006) {
               if (var5.o() == 28) {
                  var5.g();
               } else if (var3.j().b(640608, 1) || var3.j().b(40163, 1)) {
                  var5.f();
                  new C_Door.a(var5, null).a();
               }
            } else if (var5.i() == 6007) {
               if (var5.o() == 28) {
                  var5.g();
               } else if (var3.j().b(40313, 1)) {
                  var5.f();
                  new C_Door.a(var5, null).a();
               }
            } else if (!this.a(var3, var5.p())) {
               if (var5.o() == 28) {
                  var5.g();
               } else if (var5.o() == 29) {
                  var5.f();
               }
            }
         }
      }
   }

   private boolean a(L1PcInstance var1, int var2) {
      if (var2 == 0) {
         return false;
      }

      L1Clan var3 = ClanTable.a().a(var1.aF());
      if (var3 != null) {
         int var4 = var3.n();
         if (var4 != 0) {
            L1House var5 = HouseTable.a().a(var4);
            if (var2 == var5.f()) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public String a() {
      return "C_Door";
   }

   private class a extends TimerTask {
      private final L1DoorInstance b;

      private a(L1DoorInstance var2) {
         this.b = var2;
      }

      @Override
      public void run() {
         if (this.b.o() == 28) {
            this.b.g();
         }
      }

      private void a() {
         GeneralThreadPool.a().a(this, 15000L);
      }

      // $VF: synthetic method
      a(L1DoorInstance var2, C_Door.a var3) {
         this(var2);
      }
   }
}
