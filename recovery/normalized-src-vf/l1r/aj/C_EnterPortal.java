package l1r.aj;

import l1r.ap.L1PcInstance;
import l1r.aq.L1Dungeon;
import l1r.ax.L1WorldMap;
import l1r.be.S_SystemMessage;
import l1r.bj.ClientThread;

public class C_EnterPortal extends ClientBasePacket {
   public C_EnterPortal(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.d();
         int var5 = this.d();
         if (!var3.aR()) {
            L1Dungeon.a().a(var4, var5, var3.fq().b(), var3);
            if (var3.l()) {
               int var6 = L1WorldMap.b().a(var3.fp()).a(var4, var5);
               String var7 = String.format("座標 (%d, %d, %d) %d", var4, var5, var3.fp(), var6);
               var3.a(new S_SystemMessage(var7));
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_EnterPortal";
   }
}
