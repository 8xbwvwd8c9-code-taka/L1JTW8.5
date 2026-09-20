package l1r.aj;

import l1r.ap.L1MonsterInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_SelectTarget extends ClientBasePacket {
   public C_SelectTarget(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.c();
         int var6 = this.b();
         L1Object var7 = L1World.a().a(var4);
         L1Object var8 = L1World.a().a(var6);
         if (var7 instanceof L1PetInstance && var8 instanceof L1Character) {
            L1PetInstance var9 = (L1PetInstance)var7;
            L1Character var10 = (L1Character)var8;
            if (var10 instanceof L1PcInstance) {
               L1PcInstance var11 = (L1PcInstance)var10;
               if (var11.ep() == 1 || var9.ep() == 1 || var11.a(var11, var9, false)) {
                  var3.a(new S_ServerMessage(328));
                  return;
               }
            } else if (var10 instanceof L1PetInstance) {
               L1PetInstance var12 = (L1PetInstance)var10;
               if (var12.ep() == 1 || var9.ep() == 1) {
                  var3.a(new S_ServerMessage(328));
                  return;
               }
            } else if (var10 instanceof L1SummonInstance) {
               L1SummonInstance var13 = (L1SummonInstance)var10;
               if (var13.ep() == 1 || var9.ep() == 1) {
                  var3.a(new S_ServerMessage(328));
                  return;
               }
            } else if (var10 instanceof L1MonsterInstance) {
               L1MonsterInstance var14 = (L1MonsterInstance)var10;
               if (var9.M().d(var9.M(), var14.z())) {
                  var3.a(new S_ServerMessage(328));
                  return;
               }
            }

            var9.g(var10);
         } else {
            var3.a(new S_ServerMessage(328));
         }
      }
   }

   @Override
   public String a() {
      return "C_SelectTarget";
   }
}
