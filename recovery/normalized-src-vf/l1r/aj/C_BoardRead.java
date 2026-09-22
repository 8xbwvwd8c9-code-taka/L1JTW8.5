package l1r.aj;

import l1r.ap.L1BoardInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bj.ClientThread;

public class C_BoardRead extends ClientBasePacket {
   private static final String a = "[C] C_BoardRead";

   public C_BoardRead(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.b();
      int var4 = this.b();
      L1Object var5 = L1World.a().a(var3);
      L1PcInstance var6 = var2 == null ? null : var2.f();
      if (!(var5 instanceof L1BoardInstance) || var6 == null || var6.fp() != var5.fp() || var6.f(var5) > 3) {
         return;
      }
      L1BoardInstance var7 = (L1BoardInstance)var5;
      var7.b(var6, var4);
   }

   @Override
   public String a() {
      return "[C] C_BoardRead";
   }
}
