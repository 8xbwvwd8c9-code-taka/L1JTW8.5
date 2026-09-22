package l1r.aj;

import l1r.ao.ItemTable;
import l1r.ap.L1BoardInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bh.L1BoardTopic;
import l1r.bj.ClientThread;

public class C_BoardWrite extends ClientBasePacket {
   private static final String a = "[C] C_BoardWrite";

   public C_BoardWrite(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.b();
      String var4 = this.g();
      String var5 = this.g();
      L1Object var6 = L1World.a().a(var3);
      L1PcInstance var7 = var2.f();
      if (var7 == null) {
         return;
      }

      if (!(var6 instanceof L1BoardInstance)) {
         return;
      }

      if (var6.fu().c(var7.fu()) > 11) {
         return;
      }

      if (var4 == null || var4.length() > 16) {
         var7.a(new S_ServerMessage(166, "標題過長"));
         return;
      }

      if (var5 == null || var5.length() > 1000) {
         var7.a(new S_ServerMessage(166, "內容過長"));
         return;
      }

      if (!var7.j().b(40308, 300)) {
         return;
      }

      if (L1BoardTopic.a(var7.et(), var4, var5) == null) {
         ItemTable.a(var7, 40308, 300, 0, false);
      }
   }

   @Override
   public String a() {
      return "[C] C_BoardWrite";
   }
}
