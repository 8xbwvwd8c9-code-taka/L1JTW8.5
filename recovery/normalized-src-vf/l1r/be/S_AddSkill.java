package l1r.be;

import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.bh.L1Skills;

public class S_AddSkill extends ServerBasePacket {
   private void a(int var1, int... var2) {
      byte[] var3 = new byte[32];
      int[] var7 = var2;
      int var6 = var2.length;

      for (int var5 = 0; var5 < var6; var5++) {
         int var4 = var7[var5];
         L1Skills var8 = SkillsTable.a().a(var4);
         int var10001 = var8.c() - 1;
         var3[var10001] = (byte)(var3[var10001] | var8.r());
      }

      this.c(103);
      this.c(32);
      this.a(var3);
      int var9 = 0;
      if (var1 == 1) {
         var9 = 4;
      } else if (var1 == 2) {
         var9 = 1;
      } else if (var1 == 4) {
         var9 = 2;
      } else if (var1 == 8) {
         var9 = 3;
      }

      this.c(var9);
   }

   public S_AddSkill(L1PcInstance var1, int... var2) {
      this.a(var1.bC(), var2);
   }

   public S_AddSkill(L1PcInstance var1, CopyOnWriteArrayList<Integer> var2) {
      int[] var3 = new int[var2.size()];

      for (int var4 = 0; var4 < var2.size(); var4++) {
         var3[var4] = var2.get(var4);
      }

      this.a(var1.bC(), var3);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_AddSkill";
   }
}
