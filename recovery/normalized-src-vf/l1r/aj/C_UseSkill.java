package l1r.aj;

import l1r.ao.SkillsTable;
import l1r.ap.L1PcInstance;
import l1r.aq.L1SpeedChecker;
import l1r.be.S_ServerMessage;
import l1r.bf.L1SkillExecutor;
import l1r.bh.L1BookMark;
import l1r.bi.LineageUtil;
import l1r.bj.ClientThread;

public class C_UseSkill extends ClientBasePacket {
   public C_UseSkill(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.aR() && !var3.eX()) {
         int var4 = this.c();
         int var5 = this.c();
         int var6 = var4 * 8 + var5 + 1;
         String var7 = null;
         int var8 = 0;
         int var9 = 0;
         int var10 = 0;
         if (!var3.fq().q()) {
            var3.a(new S_ServerMessage(563));
         } else if (var3.h(var6)) {
            int var11;
            if (SkillsTable.a().a(var6).s() == 18) {
               var11 = var3.ce().a(L1SpeedChecker.L1R_a.c);
            } else {
               var11 = var3.ce().a(L1SpeedChecker.L1R_a.d);
            }

            if (var11 != 2) {
               if (var3.bB(78)) {
                  var3.bz(78);
               }

               if (var3.bB(32)) {
                  var3.bz(32);
               }

               try {
                  if (var6 == 116 || var6 == 118) {
                     var7 = this.g();
                  } else if (var6 == 5 || var6 == 69) {
                     int var12 = this.d();
                     int var13 = this.d();
                     int var14 = this.d();
                     L1BookMark var15 = var3.a(var13, var14);
                     if (var15 != null) {
                        var8 = var15.a();
                     }
                  } else if (var6 != 58 && var6 != 63) {
                     var8 = this.b();
                     var9 = this.d();
                     var10 = this.d();
                  } else {
                     var9 = this.d();
                     var10 = this.d();
                  }
               } catch (ArrayIndexOutOfBoundsException var16) {
               }

               L1SkillExecutor var17 = LineageUtil.a(var6);
               var17.a(var3, var8, var6, var9, var10, var7);
            }
         }
      }
   }
}
