package l1r.aj;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.CharacterTable;
import l1r.ao.ClanTable;
import l1r.ao.MailTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.be.S_Mail;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;
import l1r.bh.L1Mail;
import l1r.bj.ClientThread;

public class C_Mail extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_Mail.class.getName());

   public C_Mail(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         if (var4 == 0 || var4 == 1 || var4 == 2) {
            var3.a(new S_Mail(var3, var4));
         } else if (var4 == 16 || var4 == 17 || var4 == 18) {
            int var19 = this.b();
            L1Mail var24 = MailTable.a().c(var19);
            if (var24.f() == 0) {
               var24.c(1);
               MailTable.a().a(var19);
            }

            var3.a(new S_Mail(var24, var4));
         } else if (var4 == 32) {
            if (!var3.j().b(40308, 50)) {
               var3.a(new S_ServerMessage(189));
               return;
            }

            this.d();
            String var5 = this.g();
            byte[] var6 = this.h();
            L1PcInstance var7 = L1World.a().a(var5);
            if (var7 != null) {
               if (this.a(var7, S_Mail.a) >= 40) {
                  var3.a(new S_Mail(var4, false));
                  return;
               }

               L1Mail var8 = MailTable.a().a(S_Mail.a, var7, var3, var6, true);
               var3.a(new S_Mail(var3, var8, true));
               L1Mail var9 = MailTable.a().a(S_Mail.a, var7, var3, var6, false);
               var7.a(new S_Mail(var7, var9, false));
               var7.b(new S_SkillSound(var7.fr(), 1091));
            } else {
               try {
                  L1PcInstance var27 = CharacterTable.a().a(var5);
                  if (var27 == null) {
                     var3.a(new S_Mail(var4, false));
                     var3.a(new S_ServerMessage(109, var5));
                     return;
                  }

                  if (this.a(var27, S_Mail.a) >= 40) {
                     var3.a(new S_Mail(var4, false));
                     return;
                  }

                  L1Mail var30 = MailTable.a().a(S_Mail.a, var27, var3, var6, true);
                  var3.a(new S_Mail(var3, var30, true));
                  MailTable.a().a(S_Mail.a, var27, var3, var6, false);
               } catch (Exception var14) {
                  a.log(Level.SEVERE, var14.getLocalizedMessage(), var14);
               }
            }

            var3.a(new S_Mail(var4, true));
         } else if (var4 == 33) {
            if (var3.aF() == 0) {
               var3.a(new S_ServerMessage(1262));
               return;
            }

            if (!var3.j().b(40308, 1000)) {
               var3.a(new S_ServerMessage(189));
               return;
            }

            this.d();
            String var15 = this.g();
            byte[] var20 = this.h();
            L1Clan var25 = ClanTable.a().c(var15);
            if (var25 == null) {
               var3.a(new S_ServerMessage(3982));
               return;
            }

            for (String var28 : var25.p()) {
               L1PcInstance var10 = L1World.a().a(var28);
               if (var10 != null) {
                  int var11 = this.a(var10, S_Mail.b);
                  if (var11 < 80) {
                     L1Mail var12 = MailTable.a().a(S_Mail.b, var10, var3, var20, false);
                     var10.a(new S_Mail(var10, var12, false));
                     var10.b(new S_SkillSound(var10.fr(), 1091));
                  }
               } else {
                  try {
                     L1PcInstance var32 = CharacterTable.a().a(var28);
                     if (var32 != null && this.a(var32, S_Mail.b) < 80) {
                        MailTable.a().a(S_Mail.b, var32, var3, var20, false);
                     }
                  } catch (Exception var13) {
                     a.log(Level.SEVERE, var13.getLocalizedMessage(), var13);
                  }
               }
            }
         } else if (var4 == 64) {
            int var16 = this.b();
            L1Mail var21 = MailTable.a().c(var16);
            var21.b(S_Mail.c);
            var3.a(new S_Mail(var21, var4));
            MailTable.a().a(var21);
         } else if (var4 != 48 && var4 != 49 && var4 != 50) {
            if (var4 == 96 || var4 == 97 || var4 == 98) {
               int var18 = this.b();

               for (int var23 = 0; var23 < var18; var23++) {
                  int var26 = this.b();
                  L1Mail var29 = MailTable.a().c(var26);
                  if (var29 != null) {
                     var3.a(new S_Mail(var29, var29.b() + 48));
                     MailTable.a().b(var26);
                  }
               }
            }
         } else {
            int var17 = this.b();
            L1Mail var22 = MailTable.a().c(var17);
            if (var22 == null) {
               return;
            }

            var3.a(new S_Mail(var22, var4));
            MailTable.a().b(var17);
         }
      }
   }

   private int a(L1PcInstance var1, int var2) {
      return MailTable.a().a(var1.fr(), var2).size();
   }

   @Override
   public String a() {
      return "C_Mail";
   }
}
