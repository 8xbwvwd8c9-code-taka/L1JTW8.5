package l1r.ay;

import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Teleport;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_ServerMessage;

public class L1PcEinMonitor extends L1PcMonitor {
   private boolean a = false;
   private boolean b = false;
   private final int c = 1;

   public L1PcEinMonitor(int var1) {
      super(var1);
   }

   @Override
   public void a(L1PcInstance var1) {
      if (var1.fu().c()) {
         if (!this.a) {
            this.a = true;
            this.b = true;
         } else {
            this.b = false;
         }

         if (var1.ev() >= 49 && var1.cC() < 1540000) {
            int var2 = var1.cC() + 11;
            var1.K(var2);
         }
      } else if (this.a) {
         this.a = false;
         this.b = true;
      } else {
         this.b = false;
      }

      if (this.b) {
         var1.a(new S_ProtoBuffers(463, this.a ? 128 : 0));
         if (var1.bB(4078)) {
            var1.a(4078, this.a);
            var1.a(new S_PacketBox(86, 62, this.a ? 2 : 1, var1.bC(4078)));
         }
      }

      if ((var1.fp() < 807 || var1.fp() > 813) && (var1.fp() < 53 || var1.fp() > 56)) {
         if (var1.fp() >= 280 && var1.fp() <= 284) {
            if (var1.cT() <= 0) {
               L1Teleport.a(var1, 33703, 32502, 4, 5, true);
            } else {
               var1.bf(var1.cT() - 1);
               if (!var1.cR()) {
                  var1.y(true);
                  var1.a(new S_ProtoBuffers(540, var1.cT(), "\\fZ$20823 "));
                  var1.a(new S_ServerMessage(1527, "" + var1.cT() / 60));
               }
            }
         } else if (var1.fp() != 814 && (var1.fp() < 30 || var1.fp() > 37)) {
            if (var1.fp() >= 285 && var1.fp() <= 289) {
               if (var1.cV() <= 0) {
                  L1Teleport.a(var1, 33703, 32502, 4, 5, true);
               } else {
                  var1.bh(var1.cV() - 1);
                  if (!var1.cR()) {
                     var1.y(true);
                     var1.a(new S_ProtoBuffers(540, var1.cV(), "\\fZ$20823 "));
                     var1.a(new S_ServerMessage(1527, "" + var1.cV() / 60));
                  }
               }
            } else if (var1.fp() == 1931) {
               if (var1.cW() <= 0) {
                  L1Teleport.a(var1, 33703, 32502, 4, 5, true);
               } else {
                  var1.bi(var1.cW() - 1);
                  if (!var1.cR()) {
                     var1.y(true);
                     var1.a(new S_ProtoBuffers(540, var1.cW(), "\\fZ$20823 "));
                     var1.a(new S_ServerMessage(1527, "" + var1.cW() / 60));
                  }
               }

               L1ItemInstance var6 = var1.j().b(640368);
               if (var6 != null && var6.E() >= 5) {
                  int var3 = 0;

                  for (L1ItemInstance var4 : var1.j().d()) {
                     if (var4.N() == 640357 || var4.N() == 640358) {
                        var3 += var4.E();
                     }
                  }

                  if (var6.E() / 5 >= var3) {
                     L1Teleport.a(var1, 33703, 32502, 4, 5, true);
                  }
               }
            } else if (var1.fp() < 121 || var1.fp() > 130) {
               if (var1.fp() >= 451 && var1.fp() <= 479 || var1.fp() >= 490 && var1.fp() <= 496) {
                  if (var1.cY() <= 0) {
                     L1Teleport.a(var1, 33703, 32502, 4, 5, true);
                  } else {
                     var1.bk(var1.cY() - 1);
                     if (!var1.cR()) {
                        var1.y(true);
                        var1.a(new S_ProtoBuffers(540, var1.cY(), "\\fZ$20823 "));
                        var1.a(new S_ServerMessage(1527, "" + var1.cY() / 60));
                     }
                  }
               } else if (var1.cR()) {
                  var1.y(false);
               }
            } else if (var1.cX() <= 0) {
               L1Teleport.a(var1, 33440, 32822, 4, 5, true);
            } else {
               var1.bj(var1.cX() - 1);
               if (!var1.cR()) {
                  var1.y(true);
                  var1.a(new S_ProtoBuffers(540, var1.cX(), "\\fZ$20823 "));
                  var1.a(new S_ServerMessage(1527, "" + var1.cX() / 60));
               }
            }
         } else if (var1.cU() <= 0) {
            L1Teleport.a(var1, 33703, 32502, 4, 5, true);
         } else {
            var1.bg(var1.cU() - 1);
            if (!var1.cR()) {
               var1.y(true);
               var1.a(new S_ProtoBuffers(540, var1.cU(), "\\fZ$20823 "));
               var1.a(new S_ServerMessage(1527, "" + var1.cU() / 60));
            }
         }
      } else if (var1.cS() <= 0) {
         L1Teleport.a(var1, 33703, 32502, 4, 5, true);
      } else {
         var1.be(var1.cS() - 1);
         if (!var1.cR()) {
            var1.y(true);
            var1.a(new S_ProtoBuffers(540, var1.cS(), "\\fZ$20823 "));
            var1.a(new S_ServerMessage(1527, "" + var1.cS() / 60));
         }
      }

      if (var1.fp() != 2005 && var1.fp() != 86) {
         if (var1.fp() != 7783 && (var1.fp() < 12146 || var1.fp() > 12149)) {
            if (var1.fp() == 777) {
               if (var1.ev() >= 80) {
                  L1Teleport.a(var1, 34043, 32184, 4, 5, true);
               }
            } else if ((var1.fp() == 778 || var1.fp() == 779) && var1.ev() >= 80) {
               L1Teleport.a(var1, 32608, 33178, 4, 5, true);
            }
         } else if (var1.ev() >= 55 && !var1.l()) {
            L1Teleport.a(var1, 32583, 32931, 0, 5, true);
         }
      } else if (var1.ev() >= 52 && !var1.l()) {
         L1Teleport.a(var1, 33084, 33391, 4, 5, true);
      }
   }
}
