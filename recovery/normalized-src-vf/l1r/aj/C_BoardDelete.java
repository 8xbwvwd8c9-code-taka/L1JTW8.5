package l1r.aj;

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
      if (var5 == null) {
         System.out.println("不正確的NPCID : " + var3);
      } else {
         L1BoardTopic var6 = L1BoardTopic.a(var4);
         if (var6 == null) {
            this.b(var4);
         } else {
            String var7 = var2.f().et();
            if (!var7.equals(var6.b())) {
               this.a(var6, var7);
            } else {
               var6.f();
            }
         }
      }
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
