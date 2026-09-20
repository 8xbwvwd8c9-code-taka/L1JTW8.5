package l1r.bd;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.be.S_EffectLocation;

public abstract class L1Trap__obf_i {
   private final int a;
   private final int b;
   private final boolean c;

   public L1Trap__obf_i(TrapStorage var1) {
      this.a = var1.b("id");
      this.b = var1.b("gfxId");
      this.c = var1.c("isDetectionable");
   }

   public L1Trap__obf_i(int var1, int var2, boolean var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   protected void a(L1Object var1) {
      if (this.b() != 0) {
         for (L1PcInstance var2 : L1World.a().f(var1)) {
            var2.a(new S_EffectLocation(var1.fu(), this.b()));
         }
      }
   }

   public abstract void a(L1PcInstance var1, L1Object var2);

   public void b(L1Object var1) {
      if (this.c) {
         this.a(var1);
      }
   }

   public static L1Trap__obf_i c() {
      return new L1Trap__obf_e();
   }
}
