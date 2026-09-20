package l1r.be;

import l1r.ap.L1PcInstance;
import l1r.at.L1GameTimeClock;

public class S_OwnCharStatus extends ServerBasePacket {
   public S_OwnCharStatus(L1PcInstance var1) {
      int var2 = L1GameTimeClock.a().b().c();
      var2 -= var2 % 300;
      this.c(132);
      this.a(var1.fr());
      this.c(var1.ev());
      this.a(var1.m());
      this.b(var1.ez());
      this.b(var1.eD());
      this.b(var1.eE());
      this.b(var1.eB());
      this.b(var1.eA());
      this.b(var1.eC());
      this.b(var1.ea());
      this.b(var1.ew());
      this.b(var1.eb());
      this.b(var1.ex());
      this.a(var1.ey());
      this.a(var2);
      this.c(var1.fj());
      this.c(var1.j().h());
      this.b(var1.fa());
      this.b(var1.eH());
      this.b(var1.eG());
      this.b(var1.eF());
      this.b(var1.eI());
      this.a(var1.bM());
      this.b(0);
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_OwnCharStatus";
   }
}
