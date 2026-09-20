package l1r.be;

import java.util.List;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PetInstance;

public class S_PetInventory extends ServerBasePacket {
   public S_PetInventory(L1PetInstance var1) {
      List var2 = var1.y().d();
      this.c(162);
      this.a(var1.fr());
      this.b(var2.size());
      this.c(11);

      for (Object var3 : var2) {
         L1ItemInstance var5 = (L1ItemInstance)var3;
         if (var5 != null) {
            this.a(var5.fr());
            this.c(2);
            this.b(var5.e());
            this.c(var5.F());
            this.a(var5.E());
            if (var5.f() && var5.a().aP() == 11 && var5.D()) {
               this.c(var5.C() ? 3 : 2);
            } else {
               this.c(var5.C() ? 1 : 0);
            }

            this.a(var5.r());
         }
      }

      this.c(var1.ey());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_PetInventory";
   }
}
