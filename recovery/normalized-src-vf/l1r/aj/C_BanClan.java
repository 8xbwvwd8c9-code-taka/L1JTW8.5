package l1r.aj;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1r.ao.CharacterTable;
import l1r.ao.ClanMembersTable;
import l1r.ao.ClanTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Clan;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_BanClan extends ClientBasePacket {
   private static final Logger a = Logger.getLogger(C_BanClan.class.getName());
   private static final String b = "[C] C_BanClan";

   public C_BanClan(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         String var4 = this.g();
         L1Clan var5 = ClanTable.a().a(var3.aF());
         if (var5 != null) {
            if (var3.x() && var3.fr() == var5.k()) {
               for (int var6 = 0; var6 < var5.p().size(); var6++) {
                  if (var3.et().toLowerCase().equals(var4.toLowerCase())) {
                     return;
                  }
               }

               L1PcInstance var7 = L1World.a().a(var4);
               if (var7 != null) {
                  if (var7.aF() == var3.aF()) {
                     if (!ClanMembersTable.a().kickClanMemberAtomic(var7, var3.aF())) {
                        return;
                     }
                     var7.ah(0);
                     var7.c("");
                     var7.ai(0);
                     var7.a(new S_ServerMessage(238, var5.f()));
                     var3.a(new S_ServerMessage(240, var7.et()));
                     var5.b(var7.et());
                  } else {
                     var3.a(new S_ServerMessage(109, var4));
                  }
               } else {
                  try {
                     L1PcInstance var8 = CharacterTable.a().a(var4);
                     if (var8 != null && var8.aF() == var3.aF()) {
                        if (!ClanMembersTable.a().kickClanMemberAtomic(var8, var3.aF())) {
                           return;
                        }
                        var8.ah(0);
                        var8.c("");
                        var8.ai(0);
                        var5.b(var8.et());
                        var3.a(new S_ServerMessage(240, var8.et()));
                     } else {
                        var3.a(new S_ServerMessage(109, var4));
                     }
                  } catch (Exception var9) {
                     a.log(Level.SEVERE, var9.getLocalizedMessage(), var9);
                  }
               }
            } else {
               var3.a(new S_ServerMessage(518));
            }
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_BanClan";
   }
}
