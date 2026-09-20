package l1r.aj;

import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.be.S_ServerMessage;
import l1r.bi.Point;
import l1r.bj.ClientThread;

public class C_DropItem extends ClientBasePacket {
   private final int[][] a = new int[][]{{0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}};

   public C_DropItem(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null && !var3.bN()) {
         int var4 = this.b();

         for (int var5 = 0; var5 < var4; var5++) {
            int var6 = this.d();
            int var7 = this.d();
            int var8 = this.b();
            int var9 = this.b();
            L1ItemInstance var10 = var3.j().e(var8);
            if (var10 != null) {
               if (!var10.a().s() || var10.F() >= 128) {
                  var3.a(new S_ServerMessage(210, var10.a().h()));
               } else if (var3.N(var10.fr())) {
                  var3.a(new S_ServerMessage(1187));
               } else if (var3.O(var10.fr())) {
                  var3.a(new S_ServerMessage(1181));
               } else if (var10.D()) {
                  var3.a(new S_ServerMessage(125));
               } else {
                  if (var3.fu().b(new Point(var6, var7)) > 1.0) {
                     int var11 = var3.h(var6, var7);
                     var6 = var3.fs() + this.a[var11][0];
                     var7 = var3.ft() + this.a[var11][1];
                  }

                  var3.j().a(var10, var9, L1World.a().a(var6, var7, var3.fp()));
               }
            }
         }

         var3.fg();
      }
   }

   @Override
   public String a() {
      return "C_DropItem";
   }
}
