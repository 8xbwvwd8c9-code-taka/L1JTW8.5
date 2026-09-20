package l1r.al;

import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_PacketBox;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bi.GeneralThreadPool;

public class L1Who implements L1CommandExecutor {
   private L1Who() {
   }

   public static L1CommandExecutor a() {
      return new L1Who();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         var1.a(new S_PacketBox(45));

         for (L1PcInstance var4 : L1World.a().c()) {
            if (var4.aK() != null && var4.aK().e() != null) {
               var1.a(new S_SystemMessage(var4.et() + "\t-" + var4.aK().e().i()));
            } else {
               var1.a(new S_SystemMessage(var4.et() + "\t<-"));
            }
         }

         var1.a(new S_SystemMessage(GeneralThreadPool.a().b()));
         var1.a(new S_ServerMessage(81, "" + L1World.a().c().size()));
      } catch (Exception var6) {
         var1.a(new S_SystemMessage("請輸入: .who 。"));
      }
   }
}
