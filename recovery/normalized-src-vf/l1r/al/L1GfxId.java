package l1r.al;

import java.util.StringTokenizer;
import l1r.ai.IdFactory;
import l1r.ao.NpcTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_SystemMessage;
import l1r.bh.L1Npc;
import l1r.bi.LineageUtil;

public class L1GfxId implements L1CommandExecutor {
   private L1GfxId() {
   }

   public static L1CommandExecutor a() {
      return new L1GfxId();
   }

   @Override
   public void a(L1PcInstance var1, String var2, String var3) {
      try {
         StringTokenizer var4 = new StringTokenizer(var3);
         int var5 = Integer.parseInt(var4.nextToken(), 10);
         int var6 = Integer.parseInt(var4.nextToken(), 10);

         for (int var7 = 0; var7 < var6; var7++) {
            L1Npc var8 = NpcTable.a().a(45001);
            if (var8 != null) {
               L1NpcInstance var9 = LineageUtil.a(var8);
               var9.cF(IdFactory.a().c());
               var9.cw(var5 + var7);
               var9.a("" + (var5 + var7));
               var9.cE(var1.fp());
               var9.cG(var1.fs() + var7);
               var9.cH(var1.ft() + var7);
               var9.q(var9.fs());
               var9.r(var9.ft());
               var9.ct(4);
               L1World.a().a(var9);
               L1World.a().c(var9);
            }
         }
      } catch (Exception var10) {
         var1.a(new S_SystemMessage(var2 + " 請輸入  動畫編號  動畫數量  人物ID。"));
      }
   }
}
