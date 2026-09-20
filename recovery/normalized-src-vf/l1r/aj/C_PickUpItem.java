package l1r.aj;

import l1r.ao.HistoryTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Object;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_DoActionGFX;
import l1r.be.S_ServerMessage;
import l1r.be.S_SystemMessage;
import l1r.bj.ClientThread;

public class C_PickUpItem extends ClientBasePacket {
   public C_PickUpItem(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.eX() && !var3.bN()) {
         if (!var3.ff() && !var3.N()) {
            int var4 = this.d();
            int var5 = this.d();
            int var6 = this.b();
            int var7 = this.b();
            if (var6 != var3.fr()) {
               L1Inventory var8 = L1World.a().a(var4, var5, var3.fp());
               L1Object var9 = var8.e(var6);
               if (var9 != null) {
                  L1ItemInstance var10 = (L1ItemInstance)var9;
                  if (var10.S() != 0 && var3.fr() != var10.S()) {
                     var3.a(new S_ServerMessage(623));
                  } else if (var3.fu().c(var10.fu()) <= 3) {
                     if (var10.N() == 40308) {
                        L1ItemInstance var11 = var3.j().b(40308);
                        int var12 = 0;
                        if (var11 != null) {
                           var12 = var11.E();
                        }

                        if ((long)var12 + var7 > 2000000000L) {
                           var3.a(new S_SystemMessage("你身上的金幣已經超過2000000000了，所以不能撿取金幣。"));
                           return;
                        }
                     }

                     if (var3.j().a(var10, var7) == 0 && var10.fs() != 0 && var10.ft() != 0) {
                        var8.a(var10, var7, var3.j());
                        var3.fg();
                        var3.a(new S_DoActionGFX(var3.fr(), 15));
                        if (!var3.aA()) {
                           var3.b(new S_DoActionGFX(var3.fr(), 15));
                        }

                        HistoryTable.a().c(var3, "撿起", var10, var7);
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_PickUpItem";
   }
}
