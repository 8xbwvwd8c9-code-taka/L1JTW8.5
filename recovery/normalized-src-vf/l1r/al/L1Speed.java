package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.be.S_Liquor;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillBrave;
import l1r.be.S_SkillHaste;
import l1r.be.S_SkillSound;
import l1r.be.S_SystemMessage;

public class L1Speed implements L1CommandExecutor {
   public static L1CommandExecutor a() {
      return new L1Speed();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         this.b(var1);
         this.c(var1);
         this.d(var1);
      } catch (Exception var5) {
         var1.a(new S_SystemMessage(".speed 指令錯誤"));
      }
   }

   public void a(L1PcInstance var1) {
      this.a(var1, null, null);
   }

   private void b(L1PcInstance var1) {
      int var2 = var1.fr();
      int var3 = 1;
      int var4 = 600;
      int[] var5 = new int[]{29, 76, 152};
      int[] var9 = var5;
      int var8 = var5.length;

      for (int var7 = 0; var7 < var8; var7++) {
         int var6 = var9[var7];
         if (var1.bB(var6)) {
            var1.bA(var6);
         }
      }

      var1.j(1001, var4 * 1000);
      var1.a(new S_SkillSound(var2, 191));
      var1.b(new S_SkillSound(var2, 191));
      var1.a(new S_SkillHaste(var2, var3, var4));
      var1.b(new S_SkillHaste(var2, var3, var4));
      var1.a(new S_ServerMessage(184));
      var1.cu(var3);
   }

   private void c(L1PcInstance var1) {
      int var2 = var1.fr();
      int var3 = 1;
      int var4 = 600;
      var1.j(1000, var4 * 1000);
      var1.a(new S_SkillSound(var2, 751));
      var1.b(new S_SkillSound(var2, 751));
      var1.a(new S_SkillBrave(var2, var3, var4));
      var1.b(new S_SkillBrave(var2, var3, var4));
      var1.cv(var3);
   }

   private void d(L1PcInstance var1) {
      var1.j(1027, 600000);
      var1.a(new S_Liquor(var1.fr(), 8));
      var1.b(new S_Liquor(var1.fr(), 8));
      var1.a(new S_SkillSound(var1.fr(), 7976));
      var1.b(new S_SkillSound(var1.fr(), 7976));
      var1.a(new S_ServerMessage(1065));
   }
}
