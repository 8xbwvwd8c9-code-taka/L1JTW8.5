package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Location;
import l1r.aq.L1Teleport;
import l1r.aq.L1World;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;
import l1r.bj.ClientThread;

public class C_TeleportUser extends ClientBasePacket {
   public C_TeleportUser(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && var3.l()) {
         String var4 = this.g();
         if (!var4.isEmpty()) {
            L1PcInstance var5 = L1World.a().a(var4);
            if (var5 == null) {
               var3.a(new S_SystemMessage(var4 + "已不在線上。"));
            } else {
               L1Location var6 = L1Location.a(var5.fu(), 1, 2, false);
               L1Teleport.a(var3, var6.f(), var6.g(), var5.fp(), var3.fb(), true);
               var3.a(new S_SkillSound(var3.fr(), 12446));
               var3.b(new S_SkillSound(var3.fr(), 12446));
               var3.a(new S_SystemMessage("移動到玩家[" + var4 + "]身邊。"));
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_TeleportUser";
   }
}
