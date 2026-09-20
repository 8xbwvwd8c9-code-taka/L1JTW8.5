package l1r.be;

import java.util.ArrayList;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;

public class S_PetList extends ServerBasePacket {
   public S_PetList(int var1, L1PcInstance var2) {
      this.a(var1, var2);
   }

   private void a(int var1, L1PcInstance var2) {
      ArrayList var3 = new ArrayList<>();

      for (L1ItemInstance var4 : var2.j().d()) {
         if ((var4.N() == 40314 || var4.N() == 40316) && !this.a(var2, var4)) {
            var3.add(var4);
         }
      }

      if (!var3.isEmpty()) {
         this.c(162);
         this.a(var1);
         this.b(var3.size());
         this.c(12);

         for (L1ItemInstance var6 : var3) {
            this.a(var6.fr());
            this.c(0);
            this.b(var6.e());
            this.c(var6.F());
            this.a(var6.E());
            this.c(var6.C() ? 1 : 0);
            this.a(var6.r());
         }

         this.a(115);
      }
   }

   private boolean a(L1PcInstance var1, L1ItemInstance var2) {
      for (L1NpcInstance var3 : var1.ek().values()) {
         if (var3 instanceof L1PetInstance) {
            L1PetInstance var5 = (L1PetInstance)var3;
            if (var2.fr() == var5.k()) {
               return true;
            }
         }
      }

      return false;
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_PetList";
   }
}
