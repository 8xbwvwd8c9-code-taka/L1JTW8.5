package l1r.be;

import l1r.ap.L1NpcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;

public class S_PetMenuPacket extends ServerBasePacket {
   public S_PetMenuPacket(L1NpcInstance var1, int var2) {
      this.c(110);
      if (var1 instanceof L1PetInstance) {
         L1PetInstance var3 = (L1PetInstance)var1;
         this.a(var3.fr());
         this.a("anicom");
         this.c(0);
         this.b(11);
         switch (var3.j()) {
            case 1:
               this.a("$469");
               break;
            case 2:
               this.a("$470");
               break;
            case 3:
               this.a("$471");
               break;
            case 4:
            default:
               this.a("$471");
               break;
            case 5:
               this.a("$472");
         }

         this.a(Integer.toString(var3.ea()));
         this.a(Integer.toString(var3.ew()));
         this.a(Integer.toString(var3.eb()));
         this.a(Integer.toString(var3.ex()));
         this.a(Integer.toString(var3.ev()));
         this.a("");
         String var4 = "$610";
         if (var3.fj() > 80) {
            var4 = "$612";
         } else if (var3.fj() > 60) {
            var4 = "$611";
         } else if (var3.fj() > 30) {
            var4 = "$610";
         } else if (var3.fj() > 10) {
            var4 = "$609";
         } else if (var3.fj() >= 0) {
            var4 = "$608";
         }

         this.a(var4);
         this.a(Integer.toString(var2));
         this.a(Integer.toString(var3.fa()));
         this.a(String.valueOf(var3.fj()));
      } else if (var1 instanceof L1SummonInstance) {
         L1SummonInstance var5 = (L1SummonInstance)var1;
         this.a(var5.fr());
         this.a("moncom");
         this.c(0);
         this.b(6);
         switch (var5.i()) {
            case 1:
               this.a("$469");
               break;
            case 2:
               this.a("$470");
               break;
            case 3:
               this.a("$471");
               break;
            case 4:
            default:
               this.a("$471");
               break;
            case 5:
               this.a("$472");
         }

         this.a(Integer.toString(var5.ea()));
         this.a(Integer.toString(var5.ew()));
         this.a(Integer.toString(var5.eb()));
         this.a(Integer.toString(var5.ex()));
         this.a(Integer.toString(var5.ev()));
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_PetMenuPacket";
   }
}
