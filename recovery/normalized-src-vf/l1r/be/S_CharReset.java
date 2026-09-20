package l1r.be;

import l1r.ap.L1PcInstance;
import l1r.bi.CalcInitHpMp;

public class S_CharReset extends ServerBasePacket {
   public S_CharReset(L1PcInstance var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      this.c(42);
      this.c(2);
      this.c(var2);
      this.c(var1.cx());
      this.b(var3);
      this.b(var4);
      this.b(var5);
      this.c(var6);
      this.c(var7);
      this.c(var8);
      this.c(var9);
      this.c(var10);
      this.c(var11);
   }

   public S_CharReset(int var1) {
      this.c(42);
      this.c(3);
      this.c(var1);
   }

   public S_CharReset(L1PcInstance var1) {
      this.c(42);
      this.c(1);
      this.b(CalcInitHpMp.a(var1));
      this.b(CalcInitHpMp.c(var1));
      this.c(10);
      this.c(var1.cx());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_CharReset";
   }
}
