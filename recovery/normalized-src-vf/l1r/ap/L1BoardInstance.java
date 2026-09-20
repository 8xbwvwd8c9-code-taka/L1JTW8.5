package l1r.ap;

import l1r.be.S_Board;
import l1r.be.S_BoardRead;
import l1r.bh.L1Npc;

public class L1BoardInstance extends L1NpcInstance {
   public L1BoardInstance(L1Npc var1) {
      super(var1);
   }

   @Override
   public void c(L1PcInstance var1) {
      var1.a(new S_Board(this.fr()));
   }

   @Override
   public void a(L1PcInstance var1, int var2) {
      var1.a(new S_Board(this.fr(), var2));
   }

   public void b(L1PcInstance var1, int var2) {
      var1.a(new S_BoardRead(var2));
   }
}
