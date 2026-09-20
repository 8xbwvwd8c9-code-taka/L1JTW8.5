package l1r.aj;

import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bj.ClientThread;

public class C_FixWeapon extends ClientBasePacket {
   public C_FixWeapon(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.b();
         L1Object var6 = L1World.a().a(var5);
         if (var6 != null) {
            if (var6.fu().c(var3.fu()) <= 11) {
               L1ItemInstance var7 = var3.j().e(var4);
               if (var7 != null) {
                  int var8 = var7.H() * 200;
                  if (var3.j().b(40308, var8)) {
                     var7.b(0);
                     var3.j().b(var7);
                  }
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_FixWeapon";
   }
}
