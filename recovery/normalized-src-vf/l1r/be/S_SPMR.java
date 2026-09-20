package l1r.be;

import l1r.ap.L1PcInstance;

public class S_SPMR extends ServerBasePacket {
   public S_SPMR(L1PcInstance var1) {
      this.c(152);
      if (var1.bB(1004)) {
         this.b(var1.eV() - var1.eW() - 2);
      } else {
         this.b(var1.eV() - var1.eW());
      }

      this.b(var1.W_() - var1.bp());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SPMR";
   }
}
