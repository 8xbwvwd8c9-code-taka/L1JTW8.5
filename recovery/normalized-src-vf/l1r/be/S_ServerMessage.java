package l1r.be;

import l1r.ap.L1ItemInstance;

public class S_ServerMessage extends ServerBasePacket {
   public S_ServerMessage(String var1, L1ItemInstance var2) {
      this.c(115);
      this.b(3736);
      this.a(var2.s());
      System.out.println(var2.s());
      this.a(var2.e());
      this.a(var1);
      this.b(0);
   }

   public S_ServerMessage(int var1, int var2) {
      this.c(115);
      this.b(var1);
      this.a(var2);
   }

   public S_ServerMessage(int var1, String var2, int var3) {
      this.c(115);
      this.b(var1);
      this.a(var2);
      this.a(var3);
      this.b(0);
   }

   public S_ServerMessage(int var1) {
      this.c(115);
      this.b(var1);
      this.c(0);
      if (var1 == 418) {
         this.b(2560);
      }
   }

   public S_ServerMessage(int var1, String... var2) {
      this.c(115);
      this.b(var1);
      this.c(var2.length);
      String[] var6 = var2;
      int var5 = var2.length;

      for (int var4 = 0; var4 < var5; var4++) {
         String var3 = var6[var4];
         this.a(var3);
      }

      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ServerMessage";
   }
}
