package l1r.be;

import java.util.Map;

public class S_EquipmentSlot extends ServerBasePacket {
   public S_EquipmentSlot(int var1, int var2, boolean var3) {
      this.c(42);
      this.c(66);
      this.a(var1);
      this.c(var2);
      this.c(var3 ? 1 : 0);
      this.b(0);
   }

   public S_EquipmentSlot(Map<Integer, Integer> var1) {
      this.c(42);
      this.c(65);
      this.c(var1.size());

      for (int var2 : var1.keySet()) {
         this.a(var2);
         this.a(var1.get(var2));
      }

      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_EquipmentSlot";
   }
}
