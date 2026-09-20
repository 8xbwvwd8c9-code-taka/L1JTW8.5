package l1r.be;

import l1r.ao.SkillsTable;
import l1r.bh.L1Skills;

public class S_DelSkill extends ServerBasePacket {
   public S_DelSkill(int... var1) {
      byte[] var2 = new byte[29];
      int[] var6 = var1;
      int var5 = var1.length;

      for (int var4 = 0; var4 < var5; var4++) {
         int var3 = var6[var4];
         L1Skills var7 = SkillsTable.a().a(var3);
         int var10001 = var7.c();
         var2[var10001] = (byte)(var2[var10001] | var7.r());
      }

      boolean var8 = var2[5] + var2[6] + var2[7] + var2[8] > 0;
      boolean var9 = var2[9] + var2[10] > 0;
      if (var8 && !var9) {
         var2[0] = 50;
      } else if (var9) {
         var2[0] = 100;
      } else {
         var2[0] = 32;
      }

      this.c(127);
      this.a(var2);
      this.a(0);
      this.a(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_DelSkill";
   }
}
