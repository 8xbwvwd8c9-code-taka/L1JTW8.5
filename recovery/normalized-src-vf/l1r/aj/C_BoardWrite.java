package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1BoardTopic;
import l1r.bj.ClientThread;

public class C_BoardWrite extends ClientBasePacket {
   private static final String a = "[C] C_BoardWrite";

   public C_BoardWrite(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.b();
      String var4 = this.g();
      String var5 = this.g();
      if (var4 == null || var5 == null || var4.length() > 16 || var5.length() > 1000) {
         return;
      }
      L1Object var6 = L1World.a().a(var3);
      if (var6 == null) {
         System.out.println("不正確的 NPCID : " + var3);
      } else {
         L1PcInstance var7 = var2.f();
         L1BoardTopic.a(var7.et(), var4, var5);
         var7.j().b(40308, 300);
      }
   }

   @Override
   public String a() {
      return "[C] C_BoardWrite";
   }
}
