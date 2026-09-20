package l1r.be;

import l1r.ao.InnTable;
import l1r.ap.L1ItemInstance;

public class S_IdentifyDesc extends ServerBasePacket {
   public S_IdentifyDesc(L1ItemInstance var1) {
      this.a(var1);
   }

   private void a(L1ItemInstance var1) {
      this.c(204);
      this.b(var1.a().q());
      StringBuilder var2 = new StringBuilder();
      if (var1.F() == 0) {
         var2.append("$227 ");
      } else if (var1.F() == 2) {
         var2.append("$228 ");
      }

      var2.append(var1.a().j());
      if (var1.N() == 40312 && var1.M() != 0) {
         var2.append(InnTable.a(var1));
      }

      if (var1.g()) {
         this.b(134);
         this.c(3);
         this.a(var2.toString());
         this.a(var1.a().v() + "+" + var1.G());
         this.a(var1.a().w() + "+" + var1.G());
      } else if (var1.h()) {
         if (var1.N() == 20383) {
            this.b(137);
            this.c(3);
            this.a(var2.toString());
            this.a(String.valueOf(var1.I()));
         } else {
            this.b(135);
            this.c(2);
            this.a(var2.toString());
            this.a(Math.abs(var1.a().X()) + "+" + var1.G());
         }
      } else if (var1.f()) {
         if (var1.a().aP() == 1) {
            this.b(137);
            this.c(3);
            this.a(var2.toString());
            this.a(String.valueOf(var1.I()));
         } else if (var1.a().aP() == 2) {
            this.b(138);
            this.c(2);
            var2.append(": $231 ");
            var2.append(String.valueOf(var1.M()));
            this.a(var2.toString());
         } else if (var1.a().aP() == 7) {
            this.b(136);
            this.c(3);
            this.a(var2.toString());
            this.a(String.valueOf(var1.a().V()));
         } else {
            this.b(138);
            this.c(2);
            this.a(var2.toString());
         }

         this.a(String.valueOf(var1.p()));
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_IdentifyDesc";
   }
}
