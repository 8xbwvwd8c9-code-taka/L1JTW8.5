package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Getback;
import l1r.aq.L1World;
import l1r.ba.HellTimer;
import l1r.be.S_CharVisualUpdate;
import l1r.be.S_MapID;
import l1r.be.S_OtherCharPacks;
import l1r.be.S_OwnCharPack;
import l1r.be.S_RemoveObject;
import l1r.be.S_ServerMessage;
import l1r.be.S_Weather;
import l1r.bj.ClientThread;

public class C_RestartDead extends ClientBasePacket {
   public C_RestartDead(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         var3.t();
         var3.es();
         var3.b(new S_RemoveObject(var3));
         var3.a(var3.ev());
         var3.c_(40);
         var3.X(false);
         var3.cq(0);
         int[] var4 = new int[3];
         if (var3.bI() > 0) {
            var4[0] = 32701;
            var4[1] = 32777;
            var4[2] = 666;
         } else if (var3.fp() >= 10500 && var3.fp() <= 10502) {
            if (var3.dX() == 4) {
               var4[0] = 32734;
               var4[1] = 32756;
            } else if (var3.dX() == 5) {
               var4[0] = 32663;
               var4[1] = 32890;
            } else {
               var4[0] = 32732;
               var4[1] = 33040;
            }

            var4[2] = 10500;
            var3.a(var3.ew());
         } else {
            var4 = L1Getback.a(var3);
         }

         L1World.a().a(var3, var4[2]);
         var3.cG(var4[0]);
         var3.cH(var4[1]);
         var3.cE(var4[2]);
         var3.a(new S_MapID(var3.fp(), var3.fq().g()));
         var3.b(new S_OtherCharPacks(var3));
         var3.a(new S_OwnCharPack(var3));
         var3.a(new S_CharVisualUpdate(var3));
         var3.a();
         var3.c();
         var3.a(new S_Weather(L1World.a().j()));
         if (var3.bI() > 0) {
            HellTimer.a().a(var3, false);
         }

         var3.ae();
         if ((var3.fp() != 1700 || var3.fp() != 1703) && var3.j().m(21397)) {
            var3.a(new S_ServerMessage(123, "\\aG$22171"));
         }
      }
   }

   @Override
   public String a() {
      return "C_RestartDead";
   }
}
