package l1r.be;

import l1r.ai.GameServer;

public class S_Message_YN extends ServerBasePacket {
   public S_Message_YN(int var1, String... var2) {
      this.c(168);
      this.b(0);
      this.a(GameServer.e());
      this.b(var1);
      String[] var6 = var2;
      int var5 = var2.length;

      for (int var4 = 0; var4 < var5; var4++) {
         String var3 = var6[var4];
         this.a(var3);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Message_YN";
   }
}
