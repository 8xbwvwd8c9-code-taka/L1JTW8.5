package l1r.be;

import l1r.ap.L1PcInstance;

public class S_SkillIconAura extends ServerBasePacket {
   public static final int a = 113;
   public static final int b = 114;
   public static final int c = 116;
   public static final int d = 147;
   public static final int e = 148;
   public static final int f = 154;
   public static final int g = 155;
   public static final int h = 162;
   public static final int i = 165;
   public static final int j = 221;

   public S_SkillIconAura(int var1, int var2, L1PcInstance var3) {
      this.c(121);
      this.c(22);
      this.c(var1);
      this.b(var2);
      this.a(var3.ey());
   }

   public S_SkillIconAura(int var1, int var2, int var3) {
      this.c(121);
      this.c(22);
      this.c(var1);
      this.b(var2);
      this.c(var3);
   }

   @Override
   public byte[] a() {
      return this.d();
   }
}
