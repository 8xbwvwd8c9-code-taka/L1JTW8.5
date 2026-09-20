package l1r.be;

import l1r.ap.L1PcInstance;

public class S_WhoCharinfo extends ServerBasePacket {
   public S_WhoCharinfo(L1PcInstance var1) {
      String var2 = "";
      int var3 = var1.fa();
      if (var3 < 0) {
         var2 = "(Chaotic)";
      } else if (var3 >= 0 && var3 < 500) {
         var2 = "(Neutral)";
      } else if (var3 >= 500) {
         var2 = "(Lawful)";
      }

      this.c(107);
      this.c(8);
      String var4 = "";
      String var5 = "";
      if (!var1.eZ().equalsIgnoreCase("")) {
         var4 = var1.eZ() + " ";
      }

      if (var1.aF() > 0) {
         var5 = "[" + var1.aG() + "]";
      }

      this.a(var4 + var1.et() + " " + var2 + " " + var5);
      this.a(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_WhoCharinfo";
   }
}
