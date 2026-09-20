package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.as.L1HardinBattle;
import l1r.as.L1OrimBattle;
import l1r.be.S_DoActionGFX;
import l1r.bj.ClientThread;

public class C_ExtraCommand extends ClientBasePacket {
   private static final String a = "[C] C_ExtraCommand";

   public C_ExtraCommand(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         if (!var3.bN()) {
            if (!var3.ff()) {
               if (!var3.aR()) {
                  if (var3.fp() != 9101 || !var3.l() && !var3.am()) {
                     if (var3.fp() != 9000 || !var3.l() && !var3.am()) {
                        if (var3.bB(67)) {
                           int var5 = var3.fe();
                           if (var5 != 6080 && var5 != 6094) {
                              return;
                           }
                        }

                        S_DoActionGFX var6 = new S_DoActionGFX(var3.fr(), var4);
                        var3.b(var6);
                     } else {
                        L1HardinBattle.a().a = var4;
                     }
                  } else {
                     L1OrimBattle.a().b = var4;
                  }
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_ExtraCommand";
   }
}
