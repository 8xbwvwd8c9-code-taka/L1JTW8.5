package l1r.au;

import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_DropItemPack;
import l1r.be.S_RemoveObject;

public class L1GroundInventory extends L1Inventory {
   public L1GroundInventory(int var1, int var2, int var3, int var4) {
      this.cF(var1);
      this.cG(var2);
      this.cH(var3);
      this.cE(var4);
      L1World.a().c(this);
   }

   @Override
   public void b(L1PcInstance var1) {
      for (L1ItemInstance var2 : this.d()) {
         if (!var1.b(var2)) {
            var1.c(var2);
            var1.a(new S_DropItemPack(var2));
         }
      }
   }

   @Override
   public void a(L1ItemInstance var1) {
      for (L1PcInstance var2 : L1World.a().f(var1)) {
         var2.a(new S_DropItemPack(var1));
         var2.c(var1);
      }
   }

   @Override
   public void b(L1ItemInstance var1) {
      for (L1PcInstance var2 : L1World.a().f(var1)) {
         var2.a(new S_DropItemPack(var1));
      }
   }

   @Override
   public void c(L1ItemInstance var1) {
      for (L1PcInstance var2 : L1World.a().f(var1)) {
         var2.a(new S_RemoveObject(var1));
         var2.d(var1);
      }

      this.a.remove(var1);
      if (this.a.isEmpty()) {
         L1World.a().d(this);
      }
   }
}
