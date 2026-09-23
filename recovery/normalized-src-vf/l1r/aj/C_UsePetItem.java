package l1r.aj;

import l1r.ao.PetItemTable;
import l1r.ao.PetTypeTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.aq.L1World;
import l1r.be.S_PetEquipment;
import l1r.be.S_ServerMessage;
import l1r.bh.L1PetItem;
import l1r.bh.L1PetType;
import l1r.bj.ClientThread;

public class C_UsePetItem extends ClientBasePacket {
   private static final String a = "[C] C_UsePetItem";

   public C_UsePetItem(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.c();
         int var5 = this.b();
         int var6 = this.c();
         L1PetInstance var7 = (L1PetInstance)L1World.a().a(var5);
         if (var7 != null) {
            if (var6 < 0 || var6 >= var7.y().d().size()) {
               return;
            }
            L1ItemInstance var8 = var7.y().d().get(var6);
            if (var8 != null) {
               if (var8.f() && var8.a().aP() == 11) {
                  L1PetType var9 = PetTypeTable.b().a(var7.U_().b());
                  if (var9 == null || !var9.j()) {
                     var3.a(new S_ServerMessage(74, var8.s()));
                     return;
                  }

                  int var10 = var8.N();
                  L1PetItem var11 = PetItemTable.a().a(var10);
                  if (var11 == null) {
                     var3.a(new S_ServerMessage(74, var8.s()));
                     return;
                  }
                  if (var11.n() == 1) {
                     var7.a(var7, var8);
                     var3.a(new S_PetEquipment(var4, var7, var6));
                  } else if (var11.n() == 0) {
                     var7.b(var7, var8);
                     var3.a(new S_PetEquipment(var4, var7, var6));
                  } else {
                     var3.a(new S_ServerMessage(74, var8.s()));
                  }
               } else {
                  var3.a(new S_ServerMessage(74, var8.s()));
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "[C] C_UsePetItem";
   }
}
