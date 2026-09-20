package l1r.be;

import l1r.ap.L1PcInstance;

public class S_InitAbility extends ServerBasePacket {
   public S_InitAbility(L1PcInstance var1) {
      int[] var2 = var1.aC().a();
      int var3 = var1.bq() - var2[0];
      int var4 = var1.bs() - var2[1];
      int var5 = var1.br() - var2[2];
      int var6 = var1.bu() - var2[3];
      int var7 = var1.bv() - var2[4];
      int var8 = var1.bt() - var2[5];
      int var9 = var6 * 16 + var3;
      int var10 = var4 * 16 + var7;
      int var11 = var8 * 16 + var5;
      this.c(42);
      this.c(4);
      this.c(var9);
      this.c(var10);
      this.c(var11);
      this.c(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_InitAbility";
   }
}
