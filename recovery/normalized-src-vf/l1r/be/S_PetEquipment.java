package l1r.be;

import l1r.ap.L1PetInstance;

public class S_PetEquipment extends ServerBasePacket {
   public S_PetEquipment(int var1, L1PetInstance var2, int var3) {
      this.c(121);
      this.c(37);
      this.c(var1);
      this.a(var2.fr());
      this.c(var3);
      this.c(var2.ey());
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_PetEquipment";
   }
}
