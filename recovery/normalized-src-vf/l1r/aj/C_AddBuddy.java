package l1r.aj;

import l1r.ao.BuddyTable;
import l1r.ao.CharacterTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Buddy;
import l1r.be.S_ServerMessage;
import l1r.bj.ClientThread;

public class C_AddBuddy extends ClientBasePacket {
   public C_AddBuddy(byte[] var1, ClientThread var2) {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         L1Buddy var4 = BuddyTable.a().a(var3.fr());
         String var5 = this.g();
         if (!var5.equalsIgnoreCase(var3.et())) {
            if (var4.b(var5)) {
               var3.a(new S_ServerMessage(1052, var5));
            } else {
               CharacterTable.a[] var9;
               int var8 = (var9 = CharacterTable.a().c()).length;

               for (int var7 = 0; var7 < var8; var7++) {
                  CharacterTable.a var6 = var9[var7];
                  if (var5.equalsIgnoreCase(var6.b)) {
                     var4.a(var6.a, var6.b);
                     BuddyTable.a().a(var3.fr(), var6.a, var6.b);
                     return;
                  }
               }

               var3.a(new S_ServerMessage(109, var5));
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_AddBuddy";
   }
}
