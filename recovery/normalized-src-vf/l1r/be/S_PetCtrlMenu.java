package l1r.be;

import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1SummonInstance;

public class S_PetCtrlMenu extends ServerBasePacket {
   public S_PetCtrlMenu(L1PcInstance var1, L1NpcInstance var2, boolean var3) {
      this.c(42);
      this.c(12);
      if (var3) {
         this.b(var1.ek().size() * 3);
         this.a(0);
         this.a(var2.fr());
         this.a(var2.fp());
         this.b(var2.fs());
         this.b(var2.ft());
         this.c(var2 instanceof L1SummonInstance ? 0 : 1);
         this.a(var2.T());
      } else {
         this.b(var1.ek().size() * 3 - 3);
         this.a(1);
         this.a(var2.fr());
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_PetCtrlMenu";
   }
}
