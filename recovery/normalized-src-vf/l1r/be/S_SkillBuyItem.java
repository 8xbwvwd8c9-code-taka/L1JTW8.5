package l1r.be;

import java.util.ArrayList;
import l1r.ap.L1PcInstance;

public class S_SkillBuyItem extends ServerBasePacket {
   public S_SkillBuyItem(int var1) {
      this.c(65);
      this.a(var1 - 1);
   }

   public S_SkillBuyItem(L1PcInstance var1) {
      ArrayList var2 = S_SkillBuy.a(var1);
      this.c(98);
      this.b(var2.size());

      for (int var3 : var2) {
         this.a(var3);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_SkillBuyItem";
   }
}
