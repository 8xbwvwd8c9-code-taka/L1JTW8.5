package l1r.ap;

import l1r.be.S_SignboardPack;
import l1r.bh.L1Npc;

public class L1SignboardInstance extends L1NpcInstance {
   public L1SignboardInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void c(L1PcInstance var1) {
   }

   @Override
   public void b(L1PcInstance var1) {
      var1.c(this);
      var1.a(new S_SignboardPack(this));
   }
}
