package l1r.be;

import java.util.ArrayList;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;

public class S_FixWeaponList extends ServerBasePacket {
   public S_FixWeaponList(L1PcInstance var1) {
      this.c(215);
      this.a(200);
      ArrayList var2 = new ArrayList<>();

      for (L1ItemInstance var4 : var1.j().d()) {
         if (var4.g() && var4.H() > 0) {
            var2.add(var4);
         }
      }

      this.b(var2.size());

      for (L1ItemInstance var6 : var2) {
         this.a(var6.fr());
         this.c(var6.H());
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_FixWeaponList";
   }
}
