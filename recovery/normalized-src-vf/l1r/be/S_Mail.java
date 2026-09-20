package l1r.be;

import java.util.ArrayList;
import l1r.ao.MailTable;
import l1r.ap.L1PcInstance;
import l1r.bh.L1Mail;

public class S_Mail extends ServerBasePacket {
   public static int a = 0;
   public static int b = 1;
   public static int c = 2;

   public S_Mail(L1PcInstance var1, int var2) {
      ArrayList var3 = MailTable.a().a(var1.fr(), var2);
      this.c(205);
      this.c(var2);
      this.b(var3.size());

      for (L1Mail var4 : var3) {
         this.a(var4.a());
         this.c(var4.f());
         this.a((int)(var4.e().getTime() / 1000L));
         this.c(var4.c().equalsIgnoreCase(var1.et()) ? 1 : 0);
         this.a(var4.c().equalsIgnoreCase(var1.et()) ? var4.d() : var4.c());
         this.a(var4.g());
      }
   }

   public S_Mail(L1PcInstance var1, L1Mail var2, boolean var3) {
      this.c(205);
      this.c(var2.b() == 0 ? 80 : 81);
      this.a(var2.a());
      this.c(var3 ? 1 : 0);
      this.a(var1.et());
      this.a(var2.g());
   }

   public S_Mail(int var1, boolean var2) {
      this.c(205);
      this.c(var1);
      this.c(var2 ? 1 : 0);
   }

   public S_Mail(L1Mail var1, int var2) {
      if (var2 != 48 && var2 != 49 && var2 != 50 && var2 != 64) {
         this.c(205);
         this.c(var2);
         this.a(var1.a());
         this.a(var1.h());
      } else {
         this.c(205);
         this.c(var2);
         this.a(var1.a());
         this.c(1);
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_Mail";
   }
}
