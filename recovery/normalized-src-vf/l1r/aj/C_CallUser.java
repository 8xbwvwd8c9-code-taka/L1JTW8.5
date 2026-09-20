package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;
import l1r.bj.ClientThread;

public class C_CallUser extends ClientBasePacket {
   public C_CallUser(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && var3.l()) {
         String var4 = this.g();
         if (!var4.isEmpty()) {
            L1PcInstance var5 = L1World.a().a(var4);
            if (var5 == null) {
               var3.a(new S_SystemMessage(var4 + "已不在線上。"));
            } else {
               L1Teleport.a(var5, var3, 2);
               var3.a(new S_SystemMessage(var4 + "成功被您召喚回來。"));
               var5.a(new S_SystemMessage("您被召喚到GM身邊。"));
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_CallUser";
   }
}
