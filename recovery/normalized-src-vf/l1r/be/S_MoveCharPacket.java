package l1r.be;

import l1r.aq.L1Character;

public class S_MoveCharPacket extends ServerBasePacket {
   private static final int[] a = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
   private static final int[] b = new int[]{-1, -1, 0, 1, 1, 1, 0, -1};

   public S_MoveCharPacket(L1Character var1) {
      int var2 = var1.fb();
      int var3 = var1.fs() - a[var2];
      int var4 = var1.ft() - b[var2];
      this.c(210);
      this.a(var1.fr());
      this.b(var3);
      this.b(var4);
      this.c(var2);
      this.b(128);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_MoveCharPacket";
   }
}
