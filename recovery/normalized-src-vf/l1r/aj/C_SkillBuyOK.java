package l1r.aj;

import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.be.S_AddSkill;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bh.L1Skills;
import l1r.bj.ClientThread;

public class C_SkillBuyOK extends ClientBasePacket {
   public C_SkillBuyOK(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.d();
         int var5 = 0;
         CopyOnWriteArrayList var6 = new CopyOnWriteArrayList<>();

         for (int var7 = 0; var7 < var4; var7++) {
            int var8 = this.b() + 1;
            L1Skills var9 = SkillsTable.a().a(var8);
            int var10 = var9.c();
            int var11 = var9.c();
            if (var3.U() >= var11) {
               var5 += var10 * var10 * 100;
               var6.add(var8);
            }
         }

         if (!var6.isEmpty()) {
            if (!var3.j().b(40308, var5)) {
               var3.a(new S_ServerMessage(189));
            } else {
               for (int var12 : var6) {
                  L1Skills var14 = SkillsTable.a().a(var12);
                  SkillsTable.a().a(var3.fr(), var12, var14.b(), 0, 0);
               }

               var3.a(new S_SkillSound(var3.fr(), 224));
               var3.b(new S_SkillSound(var3.fr(), 224));
               var3.a(new S_AddSkill(var3, var6));
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_SkillBuyOK";
   }
}
