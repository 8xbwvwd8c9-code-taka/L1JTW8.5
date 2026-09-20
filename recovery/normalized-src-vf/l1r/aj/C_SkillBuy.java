package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.be.S_SkillBuy;
import l1r.bj.ClientThread;

public class C_SkillBuy extends ClientBasePacket {
   private static final String a = "[C] C_SkillBuy";

   public C_SkillBuy(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.b();
         var3.a(new S_SkillBuy(var3));
      }
   }

   @Override
   public String a() {
      return "[C] C_SkillBuy";
   }
}
