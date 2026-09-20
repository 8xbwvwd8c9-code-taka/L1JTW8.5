package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_Message_YN;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;

public class L1Ress implements L1CommandExecutor {
   private L1Ress() {
   }

   public static L1CommandExecutor a() {
      return new L1Ress();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         int var4 = var1.fr();
         var1.a(new S_SkillSound(var4, 759));
         var1.b(new S_SkillSound(var4, 759));
         var1.a(var1.ew());
         var1.i_(var1.ex());

         for (L1PcInstance var5 : L1World.a().f(var1)) {
            if (var5.ea() == 0 && var5.eX()) {
               var5.a(new S_SystemMessage("GM給予了重生。"));
               var5.b(new S_SkillSound(var5.fr(), 3944));
               var5.a(new S_SkillSound(var5.fr(), 3944));
               var5.am(var4);
               var5.a(new S_Message_YN(322));
            } else {
               var5.a(new S_SystemMessage("GM給予了治療。"));
               var5.b(new S_SkillSound(var5.fr(), 832));
               var5.a(new S_SkillSound(var5.fr(), 832));
               var5.a(var5.ew());
               var5.i_(var5.ex());
            }
         }
      } catch (Exception var7) {
         var1.a(new S_SystemMessage(var2 + " 指令錯誤"));
      }
   }
}
