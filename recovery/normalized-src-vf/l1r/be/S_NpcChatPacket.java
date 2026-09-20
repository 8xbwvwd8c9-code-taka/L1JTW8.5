package l1r.be;

import l1r.ap.L1NpcInstance;
import l1r.aq.L1Character;

public class S_NpcChatPacket extends ServerBasePacket {
   public S_NpcChatPacket(L1Character var1, String var2) {
      this.c(27);
      this.c(21);
      this.a(var1.fr());
      this.a(var2);
   }

   public S_NpcChatPacket(L1NpcInstance var1, String var2, int var3) {
      this.a(var1, var2, var3);
   }

   private void a(L1NpcInstance var1, String var2, int var3) {
      switch (var3) {
         case 0:
            this.c(27);
            this.c(var3);
            this.a(var1.fr());
            this.a(var1.et() + ": " + var2);
            break;
         case 2:
            this.c(27);
            this.c(var3);
            this.a(var1.fr());
            this.a("<" + var1.et() + "> " + var2);
            break;
         case 3:
            this.c(27);
            this.c(var3);
            this.a(var1.fr());
            this.a("[" + var1.et() + "] " + var2);
            break;
         case 21:
            this.c(27);
            this.c(var3);
            this.a(var1.fr());
            this.a(var2);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_NpcChatPacket";
   }
}
