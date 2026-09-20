package l1r.aj;

import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_SkillBuyItem;
import l1r.bj.ClientThread;

public class C_SkillBuyItem extends ClientBasePacket {
   public C_SkillBuyItem(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.b();
         L1Object var5 = L1World.a().a(var4);
         if (var5 instanceof L1NpcInstance) {
            L1NpcInstance var6 = (L1NpcInstance)var5;
            if (var6.z() == 70080) {
               var3.a(new S_SkillBuyItem(var3));
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_SkillBuyItem";
   }
}
