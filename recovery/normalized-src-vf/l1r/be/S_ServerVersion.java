package l1r.be;

import l1r.ai.GameServer;
import l1r.an.PBMessageALL9;
import l1r.l1j.server.Config;

public class S_ServerVersion extends ServerBasePacket {
   public S_ServerVersion() {
      this.c(1);
      this.b(821);
      PBMessageALL9.L1R_g.L1R_a var1 = PBMessageALL9.L1R_g.ak();
      var1.a(0);
      var1.b(Config.a);
      var1.c(1710162001);
      var1.d(1710162001);
      var1.e(2015090301);
      var1.f(1710162001);
      var1.g(GameServer.a().a);
      var1.h(0);
      var1.i(3);
      var1.j(this.e());
      var1.k((int)(System.currentTimeMillis() / 1000L));
      var1.l(150316700);
      var1.m(150204901);
      var1.n(151118701);
      var1.o(1710161002);
      var1.p(201523276);
      var1.q(3);
      this.a(var1.M().g());
      this.b(0);
   }

   public S_ServerVersion(int var1) {
      this.c(181);
      this.c(0);
      this.c(Config.a);
      this.a(1701172002);
      this.a(1701172002);
      this.a(2015090301);
      this.a(1701172002);
      this.a(GameServer.a().a);
      this.c(0);
      this.c(0);
      this.c(3);
      this.a(this.e());
      this.a((int)(System.currentTimeMillis() / 1000L));
      this.a(150316700);
      this.a(150204901);
      this.a(151118701);
      this.a(495);
      this.a(160922701);
   }

   private int e() {
      boolean[] var1 = new boolean[]{
         false,
         true,
         false,
         false,
         false,
         false,
         false,
         true,
         true,
         false,
         false,
         true,
         true,
         true,
         true,
         false,
         true,
         true,
         true,
         true,
         true,
         true,
         true,
         true,
         false,
         false,
         true,
         true,
         true,
         true,
         true
      };
      int var2 = 0;

      for (int var3 = 0; var3 < var1.length; var3++) {
         if (var1[var3]) {
            var2 |= 1 << var3;
         }
      }

      return var2;
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ServerVersion";
   }
}
