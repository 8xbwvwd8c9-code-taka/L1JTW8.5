package l1r.aj;

import l1r.ap.L1BoardInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.bh.L1BoardTopic;
import l1r.bj.ClientThread;

public class C_BoardDelete extends ClientBasePacket {
   private static final String a = "[C] C_BoardDelete";

   public C_BoardDelete(byte[] var1, ClientThread var2) {
      super(var1);
      int var3 = this.b();
      int var4 = this.b();
      L1Object var5 = L1World.a().a(var3);
      L1PcInstance var6 = var2 == null ? null : var2.f();
      if (!(var5 instanceof L1BoardInstance) || var6 == null || var6.fp() != var5.fp() || var6.f(var5) > 3) {
         return;
      }
      L1BoardTopic var7 = L1BoardTopic.a(var4);
      if (var7 == null) {
         this.b(var4);
         return;
      }
      String var8 = var6.et();
      if (!var8.equals(var7.b())) {
         this.a(var7, var8);
         return;
      }
      var7.f();
   }

   private void b(int var1) {
      System.out.println(String.format("Illegal board deletion request: Topic id <%d> does not exist.", var1));
   }

   private void a(L1BoardTopic var1, String var2) {
      System.out.println(String.format("Illegal board deletion request: Name <%s> expected but was <%s>.", var1.b(), var2));
   }

   @Override
   public String a() {
      return "[C] C_BoardDelete";
   }
}
