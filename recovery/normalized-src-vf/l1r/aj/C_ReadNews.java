package l1r.aj;

import l1r.aq.L1LoginCharList;
import l1r.be.S_News;
import l1r.bj.ClientThread;

public class C_ReadNews extends ClientBasePacket {
   public C_ReadNews(byte[] var1, ClientThread var2) {
      super(var1);
      boolean var3 = this.c() == 1;
      if (var3) {
         var2.i().clear();
         var2.a(new S_News(""));
         L1LoginCharList.a(var2);
      } else {
         String var4 = var2.b();
         if (var4 != null) {
            var2.a(new S_News(var4));
         } else {
            var2.a(new S_News(""));
            L1LoginCharList.a(var2);
         }
      }
   }

   @Override
   public String a() {
      return "C_ReadNews";
   }
}
