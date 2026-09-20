package l1r.ap;

import l1r.bh.L1Npc;

public class L1FurnitureInstance extends L1NpcInstance {
   private int y;

   public L1FurnitureInstance(L1Npc var1) {
      super(var1);
   }

   public int f() {
      return this.y;
   }

   public void b(int var1) {
      this.y = var1;
   }
}
