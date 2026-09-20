package l1r.be;

import l1r.ap.L1PcInstance;
import l1r.bi.Random;

public class S_ChatPacket extends ServerBasePacket {
   public S_ChatPacket(L1PcInstance var1, String var2, int var3) {
      switch (var3) {
         case 0:
            this.c(45);
            this.c(var3);
            this.a(var1.ff() ? 0 : var1.fr());
            this.a(var1.et() + ": " + var2);
         case 1:
         case 5:
         case 6:
         case 7:
         case 8:
         case 10:
         default:
            break;
         case 2:
            this.c(45);
            this.c(var3);
            this.a(var1.ff() ? 0 : var1.fr());
            this.a("<" + var1.et() + "> " + var2);
            this.b(var1.fs());
            this.b(var1.ft());
            break;
         case 3:
            this.c(107);
            this.c(var3);
            String var4 = var1.l() ? "******" : var1.et();
            this.a("[" + var4 + "] " + var2);
            break;
         case 4:
            this.c(107);
            this.c(var3);
            this.a("{" + var1.et() + "} " + var2);
            break;
         case 9:
            this.c(107);
            this.c(var3);
            this.a("-> (" + var1.et() + ") " + var2);
            break;
         case 11:
            this.c(107);
            this.c(var3);
            this.a("(" + var1.et() + ") " + var2);
            break;
         case 12:
            this.c(107);
            this.c(var3);
            this.a("[" + var1.et() + "] " + var2);
            break;
         case 13:
            this.c(107);
            this.c(var3);
            this.a("{{" + var1.et() + "}} " + var2);
            break;
         case 14:
            this.c(45);
            this.c(var3);
            this.a(var1.ff() ? 0 : var1.fr());
            this.a("(" + var1.et() + ") " + var2);
            break;
         case 15:
            this.c(45);
            this.c(var3);
            this.a(var1.ff() ? 0 : var1.fr());
            this.a("{" + var1.et() + "} " + var2);
            break;
         case 16:
            this.c(119);
            this.a(var1.et());
            this.a(var2);
            break;
         case 17:
            this.c(107);
            this.c(var3);
            this.a("{" + var1.et() + "}" + var2);
            break;
         case 18:
            this.c(107);
            this.c(var3);
            this.a("[" + var1.et() + "]" + var2);
            this.b(Random.a(255));
      }
   }

   public S_ChatPacket(String var1, String var2, int var3) {
      this.c(107);
      this.c(18);
      this.a("[" + var1 + "]" + var2);
      this.b(var3);
   }

   public S_ChatPacket(String var1, String var2) {
      this.c(107);
      this.c(19);
      this.a("[" + var1 + "]" + var2);
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ChatPacket";
   }
}
