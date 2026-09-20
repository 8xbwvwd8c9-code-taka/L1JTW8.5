package l1r.be;

import l1r.ap.L1PcInstance;
import l1r.bi.IntRange;

public class S_HPUpdate extends ServerBasePacket {
   private static final IntRange a = new IntRange(1, 32767);

   public S_HPUpdate(int var1, int var2) {
      this.a(var1, var2);
   }

   public S_HPUpdate(L1PcInstance var1) {
      this.a(var1.ea(), var1.ew());
   }

   private void a(int var1, int var2) {
      this.c(9);
      this.b(a.a(var1));
      this.b(a.a(var2));
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_HPUpdate";
   }
}
