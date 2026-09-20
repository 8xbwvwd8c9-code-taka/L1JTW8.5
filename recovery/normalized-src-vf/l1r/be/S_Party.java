package l1r.be;

import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ap.L1PcInstance;

public class S_Party extends ServerBasePacket {
   public S_Party(int var1, L1PcInstance var2) {
      switch (var1) {
         case 104:
            this.a(var2);
            break;
         case 105:
            this.b(var2);
            break;
         case 106:
            this.c(var2);
         case 107:
         case 108:
         case 109:
         default:
            break;
         case 110:
            this.d(var2);
      }
   }

   private void a(L1PcInstance var1) {
      L1PcInstance var2 = var1.aL().a();
      CopyOnWriteArrayList<L1PcInstance> var3 = var1.aL().c();
      this.c(121);
      this.c(104);
      this.c(var3.size() - 1);
      this.a(var2.fr());
      this.a(var2.et());
      this.c(var2.ay());
      this.b(0);
      this.c(var2.ea() * 100 / var2.ew());
      this.c(var2.eb() * 100 / var2.ex());
      this.a(var2.fp());
      this.b(var2.fs());
      this.b(var2.ft());
      this.a(0);
      this.c(1);

      for (int var4 = 0; var4 < var3.size(); var4++) {
         L1PcInstance var5 = var3.get(var4);
         if (var5 != null && var5.fr() != var2.fr()) {
            this.a(var5.fr());
            this.a(var5.et());
            this.c(var5.ay());
            this.b(0);
            this.c(var5.ea() * 100 / var5.ew());
            this.c(var5.eb() * 100 / var5.ex());
            this.a(var5.fp());
            this.b(var5.fs());
            this.b(var5.ft());
            this.a(0);
            this.c(0);
         }
      }
   }

   private void b(L1PcInstance var1) {
      this.c(121);
      this.c(105);
      this.a(var1.fr());
      this.a(var1.et());
      this.c(var1.ay());
      this.b(0);
      this.a(var1.fp());
      this.b(var1.fs());
      this.b(var1.ft());
   }

   private void c(L1PcInstance var1) {
      this.c(121);
      this.c(106);
      this.a(var1.fr());
      this.b(0);
   }

   private void d(L1PcInstance var1) {
      if (var1.aL() != null) {
         CopyOnWriteArrayList<L1PcInstance> var2 = var1.aL().c();
         this.c(121);
         this.c(110);
         this.c(var2.size());

         for (L1PcInstance var3 : var2) {
            this.a(var3.fr());
            this.a(var3.fp());
            this.b(var3.fs());
            this.b(var3.ft());
         }
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Party";
   }
}
