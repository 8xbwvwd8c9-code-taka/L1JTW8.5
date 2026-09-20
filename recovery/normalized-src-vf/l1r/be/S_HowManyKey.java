package l1r.be;

import java.io.IOException;
import l1r.ap.L1NpcInstance;

public class S_HowManyKey extends ServerBasePacket {
   public S_HowManyKey(L1NpcInstance var1, int var2, int var3, int var4, String var5) {
      this.c(193);
      this.a(var1.fr());
      this.a(var2);
      this.a(var3);
      this.a(var3);
      this.a(var4);
      this.b(0);
      this.a(var5);
      this.c(0);
      this.b(2);
      this.a(var1.et());
      this.a(String.valueOf(var2));
      this.b(0);
   }

   @Override
   public byte[] a() throws IOException {
      return this.d();
   }
}
