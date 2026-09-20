package l1r.be;

import java.io.IOException;

public class S_HowManyMake extends ServerBasePacket {
   public S_HowManyMake(int var1, int var2, String var3) {
      this.c(193);
      this.a(var1);
      this.a(0);
      this.a(0);
      this.a(0);
      this.a(var2);
      this.b(2);
      this.a("request");
      this.a(var3);
      this.b(0);
   }

   @Override
   public byte[] a() throws IOException {
      return this.d();
   }
}
