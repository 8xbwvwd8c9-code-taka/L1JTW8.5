package l1r.be;

import java.util.List;
import l1r.bh.L1BoardTopic;

public class S_Board extends ServerBasePacket {
   private static final int a = 8;

   public S_Board(int var1) {
      this.a(var1, 0);
   }

   public S_Board(int var1, int var2) {
      this.a(var1, var2);
   }

   private void a(int var1, int var2) {
      List var3 = L1BoardTopic.a(var2, 8);
      this.c(191);
      this.c(0);
      this.a(var1);
      if (var2 == 0) {
         this.a(Integer.MAX_VALUE);
      } else {
         this.a(var2);
      }

      this.c(var3.size());
      if (var2 == 0) {
         this.c(0);
         this.b(300);
      }

      for (L1BoardTopic var4 : var3) {
         this.a(var4.a());
         this.a(var4.b());
         this.a(var4.c());
         this.a(var4.d());
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Board";
   }
}
