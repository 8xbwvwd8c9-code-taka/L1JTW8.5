package l1r.aj;

import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import l1r.ao.CharacterItemTable;
import l1r.ao.HouseTable;
import l1r.ao.HtmlCraftTable;
import l1r.ao.InnTable;
import l1r.ao.ItemTable;
import l1r.ap.L1ItemInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.aq.L1World;
import l1r.au.L1Inventory;
import l1r.be.S_Html;
import l1r.be.S_ServerMessage;
import l1r.bh.L1House;
import l1r.bj.ClientThread;

public class C_Amount extends ClientBasePacket {
   public C_Amount(byte[] var1, ClientThread var2) throws Exception {
      super(var1);
      L1PcInstance var3 = var2.f();
      if (var3 != null) {
         int var4 = this.b();
         int var5 = this.b();
         int var6 = this.c();
         String var7 = this.g();
         L1NpcInstance var8 = (L1NpcInstance)L1World.a().a(var4);
         if (var8 != null) {
            if (!HtmlCraftTable.a().a(var7, var3, var8, var5)) {
               String var9 = "";
               String var10 = "";

               try {
                  StringTokenizer var11 = new StringTokenizer(var7);
                  var9 = var11.nextToken();
                  var10 = var11.nextToken();
               } catch (NoSuchElementException var17) {
                  var9 = "";
                  var10 = "";
               }

               if (var9.equalsIgnoreCase("agapply")) {
                  for (L1House var20 : HouseTable.a().c().values()) {
                     if (var3.et().equalsIgnoreCase(var20.n())) {
                        var3.a(new S_ServerMessage(523));
                        return;
                     }
                  }

                  int var21 = Integer.valueOf(var10);
                  L1House var24 = HouseTable.a().a(var21);
                  if (!var3.j().b(40308, var5)) {
                     var3.a(new S_ServerMessage(189));
                     return;
                  }

                  int var13 = var24.k();
                  int var14 = var24.o();
                  var24.d(var5);
                  var24.d(var3.et());
                  var24.f(var3.fr());
                  HouseTable.a().a(var24);
                  if (var14 != 0) {
                     L1PcInstance var15 = (L1PcInstance)L1World.a().a(var14);
                     if (var15 != null) {
                        ItemTable.a(var15, 40308, var13, 0, false);
                        var15.a(new S_ServerMessage(525, String.valueOf(var13)));
                     } else {
                        L1ItemInstance var16 = ItemTable.a().b(40308);
                        var16.e(var13);
                        CharacterItemTable.a().a(var14, var16);
                     }
                  }
               } else if (var9.equalsIgnoreCase("agsell")) {
                  int var22 = Integer.valueOf(var10);
                  L1House var25 = HouseTable.a().a(var22);
                  Timestamp var27 = new Timestamp(System.currentTimeMillis() + 432000000L);
                  var25.b(var27);
                  var25.d(var5);
                  var25.c(var3.et());
                  var25.e(var3.fr());
                  var25.d("");
                  var25.f(0);
                  var25.a(true);
                  var25.b(false);
                  HouseTable.a().a(var25);
               } else {
                  int var23 = var8.z();
                  if (var23 == 70070
                     || var23 == 70019
                     || var23 == 70075
                     || var23 == 70012
                     || var23 == 70031
                     || var23 == 70084
                     || var23 == 70065
                     || var23 == 70054
                     || var23 == 70096) {
                     if (!var3.j().g(40308, 300 * var5)) {
                        var3.a(new S_Html(var23, "inn3", var8.et()));
                        return;
                     }

                     if (!InnTable.a().a(var3.dM())) {
                        var3.a(new S_Html(var23, ""));
                        return;
                     }

                     L1ItemInstance var26 = ItemTable.a().b(40312);
                     var26.e(var5);
                     var26.j(var26.fr());
                     var3.j().b(40308, 300 * var5);
                     L1Inventory var28;
                     if (var3.j().a(var26, var5) == 0) {
                        var28 = var3.j();
                     } else {
                        var28 = L1World.a().a(var3.fu());
                     }

                     var28.d(var26);
                     InnTable.a().a(var26.M(), var5, var3.dM());
                     var3.a(new S_ServerMessage(143, var8.et(), var26.s()));
                     var3.a(new S_Html(var23, "inn4", var8.et()));
                  }
               }
            }
         }
      }
   }

   @Override
   public String a() {
      return "C_Amount";
   }
}
