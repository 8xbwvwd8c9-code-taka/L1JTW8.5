package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_PetInventory;
import l1r.bj.ClientThread;

public class C_PetMenu extends ClientBasePacket {
   private static final String a = "[C] C_PetMenu";

   public C_PetMenu(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         L1Object var5 = L1World.a().a(var4);
         if (var5 instanceof L1PetInstance) {
            L1PetInstance var6 = (L1PetInstance)var5;
            var3.a(new S_PetInventory(var6));
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_PetMenu";
   }
}
