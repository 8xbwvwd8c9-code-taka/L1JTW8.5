package l1r.aq;

import l1r.ao.CastleTable;
import l1r.ao.ClanTable;
import l1r.ao.ExpTable;
import l1r.ao.ItemTable;
import l1r.ao.TownTable;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.as.L1BugBearRace;
import l1r.as.L1Dragon;
import l1r.at.L1GameTimeClock;
import l1r.be.S_Html;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillHaste;
import l1r.be.S_SkillSound;
import l1r.bf.S_008;
import l1r.bh.L1Castle;

public class L1NpcTalkData {
   public static void a(L1NpcInstance var0, L1PcInstance var1) {
      int var2 = var0.U_().b();
      L1PcInstance var3 = (L1PcInstance)var0.M();
      String var4 = null;
      switch (var2) {
         case 70957:
            var4 = "roi2";
            break;
         case 71062:
            var4 = var3.equals(var1) ? "kamit2" : "kamit1";
            break;
         case 71075:
            var4 = var3.equals(var1) ? "llizard2" : "llizard1a";
            break;
         case 71093:
            var4 = var3.equals(var1) ? "searcherk2" : "searcherk4";
            break;
         case 71094:
            var4 = var3.equals(var1) ? "endiaq2" : "endiaq4";
            break;
         case 81350:
            var4 = "dspy3";
      }

      if (var4 != null) {
         var1.a(new S_Html(var0.fr(), var4, ""));
      }
   }

   public static void b(L1NpcInstance var0, L1PcInstance var1) {
      int var2 = var0.fr();
      int var3 = var0.U_().b();
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;
      int var9 = 0;
      L1Quest var10 = var1.bb();
      String var11 = null;
      String[] var12 = null;
      L1Castle var13 = null;
      L1Clan var14 = null;
      String var15 = "安安妳好再見_";
      String var16 = "Srwh";
      if (!L1Dragon.a().a(var3, var1)) {
         if (!L1AdenTelData.a().a(var3, var2, var1)) {
            switch (var3) {
               case 46180:
                  if (var10.a(44) == 255) {
                     var11 = "hamo1";
                  } else {
                     var11 = "hamo2";
                  }
                  break;
               case 46181:
                  if (var1.j().f(640294)) {
                     var11 = "eldnas2";
                  } else {
                     var11 = "eldnas1";
                  }
                  break;
               case 50001:
                  if (var1.A()) {
                     var11 = "barnia3";
                  } else if (var1.z() || var1.x()) {
                     var11 = "barnia2";
                  }
                  break;
               case 50082:
                  if (var1.ev() >= 13) {
                     if (var1.A()) {
                        var11 = "en0222e";
                     } else if (var1.C()) {
                        var11 = "en0222d";
                     } else {
                        var11 = "en0222";
                     }
                  }
                  break;
               case 60028:
                  if (!var1.A()) {
                     var11 = "elCE1";
                  }
                  break;
               case 60514:
                  var15 = var15 + 1;
                  var13 = CastleTable.a().a(1);
                  var14 = ClanTable.a().a(var13.g());
                  if (var14 != null) {
                     var15 = var14.f();
                     var16 = var14.l();
                  }

                  var11 = "ktguard6";
                  var12 = new String[]{var0.et(), var15, var16};
                  break;
               case 60524:
               case 60525:
               case 60529:
                  var15 = var15 + 4;
                  var13 = CastleTable.a().a(4);
                  var14 = ClanTable.a().a(var13.g());
                  if (var14 != null) {
                     var15 = var14.f();
                     var16 = var14.l();
                  }

                  var11 = "grguard6";
                  var12 = new String[]{var0.et(), var15, var16};
                  break;
               case 60530:
               case 60531:
                  var15 = var15 + 6;
                  var13 = CastleTable.a().a(6);
                  var14 = ClanTable.a().a(var13.g());
                  if (var14 != null) {
                     var15 = var14.f();
                     var16 = var14.l();
                  }

                  var11 = "dcguard6";
                  var12 = new String[]{var0.et(), var15, var16};
                  break;
               case 60533:
               case 60534:
                  var15 = var15 + 7;
                  var13 = CastleTable.a().a(7);
                  var14 = ClanTable.a().a(var13.g());
                  if (var14 != null) {
                     var15 = var14.f();
                     var16 = var14.l();
                  }

                  var11 = "adguard6";
                  var12 = new String[]{var0.et(), var15, var16};
                  break;
               case 60552:
                  var15 = var15 + 3;
                  var13 = CastleTable.a().a(3);
                  var14 = ClanTable.a().a(var13.g());
                  if (var14 != null) {
                     var15 = var14.f();
                     var16 = var14.l();
                  }

                  var11 = "wdguard6";
                  var12 = new String[]{var0.et(), var15, var16};
                  break;
               case 60560:
                  var15 = var15 + 2;
                  var13 = CastleTable.a().a(2);
                  var14 = ClanTable.a().a(var13.g());
                  if (var14 != null) {
                     var15 = var14.f();
                     var16 = var14.l();
                  }

                  var11 = "orcguard6";
                  var12 = new String[]{var0.et(), var15, var16};
                  break;
               case 70009:
                  if (var1.x()) {
                     var11 = "gerengp1";
                  } else if (var1.z()) {
                     var11 = "gerengk1";
                  } else if (var1.A()) {
                     var11 = "gerenge1";
                  } else if (var1.B()) {
                     var11 = "gerengTe1";
                  } else if (var1.C()) {
                     var11 = "gerengde1";
                  } else if (var1.D()) {
                     var11 = "gerengdk1";
                  } else if (var1.E()) {
                     var11 = "gerengi1";
                  } else if (var1.F()) {
                     var11 = "gerengwr1";
                  }
                  break;
               case 70011:
                  int var17 = L1GameTimeClock.a().b().c() % 86400;
                  if (var17 < 21600 || var17 > 72000) {
                     var11 = "shipEvI6";
                  }
                  break;
               case 70035:
               case 70041:
               case 70042:
                  if (L1BugBearRace.a().b() == 0) {
                     var11 = "maeno5";
                  } else if (L1BugBearRace.a().b() == 1) {
                     var11 = "maeno1";
                  } else if (L1BugBearRace.a().b() == 2) {
                     var11 = "maeno3";
                  } else {
                     var11 = "maeno5";
                  }
                  break;
               case 70060:
                  if (!var1.A()) {
                     var11 = "elfinM1";
                  }
                  break;
               case 70080:
                  if (var1.z()) {
                     var11 = "horunev2";
                  } else if (!var1.D() && !var1.E() && !var1.F()) {
                     var11 = "horun1";
                  } else {
                     var11 = "horundk1";
                  }
                  break;
               case 70087:
                  if (var1.C()) {
                     var11 = "sedia";
                  }
                  break;
               case 70099:
                  if (!var10.c(11) && var1.ev() > 13) {
                     var11 = "kuper1";
                  }
                  break;
               case 70506:
                  var11 = d(var1);
                  break;
               case 70512:
                  if (var1.ev() >= 25) {
                     var11 = "jpe0102";
                  }
                  break;
               case 70514:
                  if (var1.ev() >= 25) {
                     var11 = "jpe0092";
                  }
                  break;
               case 70528:
                  var11 = b(var1, 1);
                  break;
               case 70534:
                  var11 = a(var1, 1);
                  break;
               case 70538:
               case 70560:
               case 70644:
               case 70667:
               case 70725:
               case 70790:
               case 70884:
                  if (!var1.x()) {
                     var11 = "bpledge1";
                  } else if (var1.aH() == 4) {
                     var11 = "bpledge2";
                  }
                  break;
               case 70546:
                  var11 = b(var1, 6);
                  break;
               case 70549:
               case 70985:
                  if (c(var1, 1)) {
                     var11 = "gateokeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70553:
                  if (c(var1, 1)) {
                     if (a(var1)) {
                        var11 = "ishmael1";
                     } else {
                        var11 = "ishmael6";
                        var12 = new String[]{var1.et()};
                     }
                  }
                  break;
               case 70556:
                  var11 = a(var1, 6);
                  break;
               case 70567:
                  var11 = b(var1, 3);
                  break;
               case 70572:
                  var11 = a(var1, 3);
                  break;
               case 70594:
                  var11 = b(var1, 7);
                  break;
               case 70600:
               case 70986:
                  if (c(var1, 2)) {
                     var11 = "orckeeper";
                  }
                  break;
               case 70623:
                  if (c(var1, 4)) {
                     if (a(var1)) {
                        var11 = "orville1";
                     } else {
                        var11 = "orville6";
                        var12 = new String[]{var1.et()};
                     }
                  }
                  break;
               case 70631:
                  var11 = a(var1, 7);
                  break;
               case 70654:
                  var11 = b(var1, 9);
                  break;
               case 70656:
                  if (c(var1, 1)) {
                     var11 = "gatekeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70663:
                  var11 = a(var1, 9);
                  break;
               case 70665:
                  if (c(var1, 6)) {
                     if (a(var1)) {
                        var11 = "potempin1";
                     } else {
                        var11 = "potempin6";
                        var12 = new String[]{var1.et()};
                     }
                  }
                  break;
               case 70687:
               case 70987:
                  if (c(var1, 3)) {
                     var11 = "gateokeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70721:
                  if (c(var1, 7)) {
                     if (a(var1)) {
                        var11 = "timon1";
                     } else {
                        var11 = "timon6";
                        var12 = new String[]{var1.et()};
                     }
                  }
                  break;
               case 70748:
                  var11 = b(var1, 10);
                  break;
               case 70761:
                  var11 = a(var1, 10);
                  break;
               case 70774:
                  var11 = b(var1, 5);
                  break;
               case 70778:
                  if (c(var1, 3)) {
                     var11 = "gatekeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70784:
                  if (c(var1, 3)) {
                     if (a(var1)) {
                        var11 = "othmond1";
                     } else {
                        var11 = "othmond6";
                        var12 = new String[]{var1.et()};
                     }
                  }
                  break;
               case 70788:
                  var11 = a(var1, 5);
                  break;
               case 70794:
                  if (var1.x()) {
                     var11 = "gerardp1";
                  } else if (var1.z()) {
                     var11 = "gerardkev5";
                  } else if (var1.A()) {
                     var11 = "gerarde1";
                  } else if (var1.B()) {
                     var11 = "gerardw1";
                  } else if (var1.C()) {
                     var11 = "gerardde1";
                  }
                  break;
               case 70796:
                  if (!var10.c(11) && var1.ev() > 13) {
                     var11 = "dunham1";
                  }
                  break;
               case 70799:
                  var11 = b(var1, 2);
                  break;
               case 70800:
               case 70988:
               case 70989:
               case 70990:
               case 70991:
                  if (c(var1, 4)) {
                     var11 = "gateokeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70806:
                  var11 = a(var1, 2);
                  break;
               case 70815:
                  var11 = b(var1, 4);
                  break;
               case 70817:
                  if (c(var1, 4)) {
                     var11 = "gatekeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70822:
                  if (c(var1, 2)) {
                     if (a(var1)) {
                        var11 = "seghem1";
                     } else {
                        var11 = "seghem6";
                        var12 = new String[]{var1.et()};
                     }
                  }
                  break;
               case 70830:
                  var11 = a(var1, 4);
                  break;
               case 70838:
                  if (!var1.x() && !var1.z() && !var1.B() && !var1.D() && !var1.E()) {
                     if (var1.C() && var1.fa() <= -1) {
                        var11 = "nerupaM2";
                     } else if (var1.C()) {
                        var11 = "nerupace1";
                     } else if (var1.A()) {
                        var11 = "nerupae1";
                     }
                  } else {
                     var11 = "nerupam1";
                  }
                  break;
               case 70841:
                  if (var1.A()) {
                     var11 = "luudielE1";
                  } else if (var1.C()) {
                     var11 = "luudielCE1";
                  }
                  break;
               case 70857:
                  var15 = var15 + 5;
                  var13 = CastleTable.a().a(5);
                  var14 = ClanTable.a().a(var13.g());
                  if (var14 != null) {
                     var15 = var14.f();
                     var16 = var14.l();
                  }

                  var11 = "heguard6";
                  var12 = new String[]{var0.et(), var15, var16};
                  break;
               case 70860:
                  var11 = b(var1, 8);
                  break;
               case 70862:
               case 70992:
                  if (c(var1, 5)) {
                     var11 = "gateokeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70863:
                  if (c(var1, 5)) {
                     var11 = "gatekeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70876:
                  var11 = a(var1, 8);
                  break;
               case 70880:
                  if (c(var1, 5)) {
                     if (a(var1)) {
                        var11 = "fisher1";
                     } else {
                        var11 = "fisher6";
                        var12 = new String[]{var1.et()};
                     }
                  }
                  break;
               case 70957:
               case 81209:
                  if (var1.bb().a(38) != 1) {
                     var11 = "roi1";
                  }
                  break;
               case 70993:
               case 70994:
                  if (c(var1, 6)) {
                     var11 = "gateokeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70995:
                  if (c(var1, 6)) {
                     var11 = "gatekeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70996:
                  if (c(var1, 7)) {
                     var11 = "gatekeeper";
                     var12 = new String[]{var1.et()};
                  }
                  break;
               case 70998:
                  var11 = e(var1);
                  break;
               case 71002:
                  var11 = c(var1);
                  break;
               case 71005:
                  var11 = f(var1);
                  break;
               case 71009:
                  if (var1.ev() < 13) {
                     var11 = "jpe0071";
                  }
                  break;
               case 71011:
                  if (var1.ev() < 13) {
                     var11 = "jpe0061";
                  }
                  break;
               case 71013:
                  if (var1.C()) {
                     if (var1.ev() < 14) {
                        var11 = "karen1";
                     } else {
                        var11 = "karen4";
                     }
                  }
                  break;
               case 71014:
                  if (var1.ev() < 13) {
                     var11 = "en0241";
                  }
                  break;
               case 71015:
                  if (var1.ev() < 13) {
                     var11 = "en0261";
                  } else if (var1.ev() >= 13 && var1.ev() < 25) {
                     var11 = "en0262";
                  }
                  break;
               case 71018:
                  if (var1.ev() < 12) {
                     var11 = "jpe0133";
                  } else if (var1.ev() >= 12 && var1.ev() < 25) {
                     if (var1.j().f(41240)) {
                        var11 = "jpe0132";
                     } else {
                        var11 = "jpe0131";
                     }
                  }
                  break;
               case 71019:
                  if (var1.ev() < 12) {
                     var11 = "jpe0114";
                  } else if (var1.ev() >= 12 && var1.ev() < 25) {
                     if (var1.j().f(41239)) {
                        var11 = "jpe0113";
                     } else {
                        var11 = "jpe0111";
                     }
                  }
                  break;
               case 71020:
                  if (var1.ev() < 12) {
                     var11 = "jpe0125";
                  } else if (var1.ev() >= 12 && var1.ev() < 25) {
                     if (var1.j().f(41231)) {
                        var11 = "jpe0123";
                     } else if (var1.j().f(41232)
                        || var1.j().f(41233)
                        || var1.j().f(41234)
                        || var1.j().f(41235)
                        || var1.j().f(41238)
                        || var1.j().f(41239)
                        || var1.j().f(41240)) {
                        var11 = "jpe0126";
                     }
                  }
                  break;
               case 71021:
                  if (var1.ev() >= 12 && var1.ev() < 25) {
                     var11 = "en0191";
                  }
                  break;
               case 71022:
                  if (var1.ev() < 12) {
                     var11 = "jpe0155";
                  } else if (var1.ev() >= 12
                     && var1.ev() < 25
                     && (
                        var1.j().f(41230)
                           || var1.j().f(41231)
                           || var1.j().f(41232)
                           || var1.j().f(41233)
                           || var1.j().f(41235)
                           || var1.j().f(41238)
                           || var1.j().f(41239)
                           || var1.j().f(41240)
                     )) {
                     var11 = "jpe0158";
                  }
                  break;
               case 71023:
                  if (var1.ev() < 12) {
                     var11 = "jpe0145";
                  } else if (var1.ev() >= 12 && var1.ev() < 25) {
                     if (var1.j().f(41233) || var1.j().f(41234)) {
                        var11 = "jpe0143";
                     } else if (var1.j().f(41238) || var1.j().f(41239) || var1.j().f(41240)) {
                        var11 = "jpe0147";
                     } else if (var1.j().f(41235) || var1.j().f(41236) || var1.j().f(41237)) {
                        var11 = "jpe0144";
                     }
                  }
                  break;
               case 71025:
                  if (var1.ev() >= 10 && var1.ev() < 25) {
                     if (var1.j().f(41226)) {
                        var11 = "jpe0084";
                     } else if (var1.j().f(41225)) {
                        var11 = "jpe0083";
                     } else if (var1.j().f(40653) || var1.j().f(40613)) {
                        var11 = "jpe0081";
                     }
                  }
                  break;
               case 71026:
                  if (var1.ev() < 10) {
                     var11 = "en0113";
                  } else if (var1.ev() >= 10 && var1.ev() < 25) {
                     var11 = "en0111";
                  } else if (var1.ev() > 25) {
                     var11 = "en0112";
                  }
                  break;
               case 71027:
                  if (var1.ev() < 10) {
                     var11 = "en0283";
                  } else if (var1.ev() >= 10 && var1.ev() < 25) {
                     var11 = "en0281";
                  } else if (var1.ev() > 25) {
                     var11 = "en0282";
                  }
                  break;
               case 71031:
                  if (var1.ev() < 25) {
                     var11 = "en0081";
                  }
                  break;
               case 71032:
                  if (var1.A()) {
                     var11 = "en0091e";
                  } else if (var1.C()) {
                     var11 = "en0091d";
                  } else if (var1.z()) {
                     var11 = "en0091k";
                  } else if (var1.B()) {
                     var11 = "en0091w";
                  } else if (var1.x()) {
                     var11 = "en0091p";
                  }
                  break;
               case 71033:
                  if (var1.j().f(41228)) {
                     if (var1.A()) {
                        var11 = "en0211e";
                     } else if (var1.C()) {
                        var11 = "en0211d";
                     } else if (var1.z()) {
                        var11 = "en0211k";
                     } else if (var1.B()) {
                        var11 = "en0211w";
                     } else if (var1.x()) {
                        var11 = "en0211p";
                     }
                  }
                  break;
               case 71034:
                  if (var1.j().f(41227)) {
                     if (var1.A()) {
                        var11 = "en0201e";
                     } else if (var1.C()) {
                        var11 = "en0201d";
                     } else if (var1.z()) {
                        var11 = "en0201k";
                     } else if (var1.B()) {
                        var11 = "en0201w";
                     } else if (var1.x()) {
                        var11 = "en0201p";
                     }
                  }
                  break;
               case 71038:
                  if (var1.j().f(41060)) {
                     if (!var1.j().f(41090) && !var1.j().f(41091) && !var1.j().f(41092)) {
                        var11 = "orcfnoname8";
                     } else {
                        var11 = "orcfnoname7";
                     }
                  }
                  break;
               case 71040:
                  if (var1.j().f(41060)) {
                     if (var1.j().f(41065)) {
                        if (!var1.j().f(41086) && !var1.j().f(41087) && !var1.j().f(41088) && !var1.j().f(41089)) {
                           var11 = "orcfnoa5";
                        } else {
                           var11 = "orcfnoa6";
                        }
                     } else {
                        var11 = "orcfnoa2";
                     }
                  }
                  break;
               case 71041:
                  if (var1.j().f(41060)) {
                     if (var1.j().f(41064)) {
                        if (!var1.j().f(41081) && !var1.j().f(41082) && !var1.j().f(41083) && !var1.j().f(41084) && !var1.j().f(41085)) {
                           var11 = "orcfhuwoomo8";
                        } else {
                           var11 = "orcfhuwoomo2";
                        }
                     } else {
                        var11 = "orcfhuwoomo1";
                     }
                  }
                  break;
               case 71042:
                  if (var1.j().f(41060)) {
                     if (var1.j().f(41062)) {
                        if (!var1.j().f(41071) && !var1.j().f(41072) && !var1.j().f(41073) && !var1.j().f(41074) && !var1.j().f(41075)) {
                           var11 = "orcfbakumo8";
                        } else {
                           var11 = "orcfbakumo2";
                        }
                     } else {
                        var11 = "orcfbakumo1";
                     }
                  }
                  break;
               case 71043:
                  if (var1.j().f(41060)) {
                     if (var1.j().f(41063)) {
                        if (!var1.j().f(41076) && !var1.j().f(41077) && !var1.j().f(41078) && !var1.j().f(41079) && !var1.j().f(41080)) {
                           var11 = "orcfbuka8";
                        } else {
                           var11 = "orcfbuka2";
                        }
                     } else {
                        var11 = "orcfbuka1";
                     }
                  }
                  break;
               case 71044:
                  if (var1.j().f(41060)) {
                     if (var1.j().f(41061)) {
                        if (!var1.j().f(41066) && !var1.j().f(41067) && !var1.j().f(41068) && !var1.j().f(41069) && !var1.j().f(41070)) {
                           var11 = "orcfkame8";
                        } else {
                           var11 = "orcfkame2";
                        }
                     } else {
                        var11 = "orcfkame1";
                     }
                  }
                  break;
               case 71092:
               case 71093:
                  if (var1.z() && var1.bb().a(3) == 4) {
                     var11 = "searcherk1";
                  }
                  break;
               case 71094:
                  if (var1.C() && var1.bb().a(4) == 2) {
                     var11 = "endiaq1";
                  }
                  break;
               case 71141:
                  if (var1.fe() == 3887) {
                     var11 = "moumthree1";
                  }
                  break;
               case 71142:
                  if (var1.fe() == 3887) {
                     var11 = "moumtwo1";
                  }
                  break;
               case 71145:
                  if (var1.fe() == 3887) {
                     var11 = "moumone1";
                  }
                  break;
               case 71167:
                  if (var1.fe() == 3887) {
                     var11 = "frim1";
                  }
                  break;
               case 71168:
                  if (var1.j().f(41028)) {
                     var11 = "dantes1";
                  }
                  break;
               case 71181:
                  if (var1.aJ() == 0) {
                     var11 = "my2";
                  }
                  break;
               case 71182:
                  if (var1.aJ() == 1) {
                     var11 = "sm2";
                  }
                  break;
               case 71198:
                  if (var1.bb().a(71198) == 1) {
                     var11 = "tion4";
                  } else if (var1.bb().a(71198) == 2) {
                     var11 = "tion5";
                  } else if (var1.bb().a(71198) == 3) {
                     var11 = "tion6";
                  } else if (var1.bb().a(71198) == 4) {
                     var11 = "tion7";
                  } else if (var1.bb().a(71198) == 5) {
                     var11 = "tion5";
                  } else if (var1.j().g(21059, 1)) {
                     var11 = "tion19";
                  }
                  break;
               case 71199:
                  if (var1.bb().a(71199) == 1) {
                     var11 = "jeron3";
                  } else if (var1.j().g(21059, 1) || var1.bb().a(71199) == 255) {
                     var11 = "jeron7";
                  }
                  break;
               case 71256:
                  if (!var1.A()) {
                     var11 = "robinhood2";
                  } else if (var1.bb().a(40) == 255) {
                     var11 = "robinhood12";
                  } else if (var1.bb().a(40) == 8) {
                     if (var1.j().g(40491, 30)
                        && var1.j().g(40495, 40)
                        && var1.j().g(100, 1)
                        && var1.j().g(40509, 12)
                        && var1.j().g(40052, 1)
                        && var1.j().g(40053, 1)
                        && var1.j().g(40054, 1)
                        && var1.j().g(40055, 1)
                        && var1.j().g(41347, 1)
                        && var1.j().g(41350, 1)) {
                        var11 = "robinhood11";
                     } else if (var1.j().g(40491, 30) && var1.j().g(40495, 40) && var1.j().g(100, 1) && var1.j().g(40509, 12)) {
                        var11 = "robinhood16";
                     } else if (!var1.j().g(40491, 30) || !var1.j().g(40495, 40) || !var1.j().g(100, 1) || !var1.j().g(40509, 12)) {
                        var11 = "robinhood17";
                     }
                  } else if (var1.bb().a(40) == 7) {
                     if (var1.j().g(41352, 4)
                        && var1.j().g(40618, 30)
                        && var1.j().g(40643, 30)
                        && var1.j().g(40645, 30)
                        && var1.j().g(40651, 30)
                        && var1.j().g(40676, 30)
                        && var1.j().g(40514, 20)
                        && var1.j().g(41351, 1)
                        && var1.j().g(41346, 1)) {
                        var11 = "robinhood9";
                     } else if (var1.j().g(41351, 1) && var1.j().g(41352, 4)) {
                        var11 = "robinhood14";
                     } else if (var1.j().g(41351, 1) && !var1.j().g(41352, 4)) {
                        var11 = "robinhood15";
                     } else if (var1.j().f(41351)) {
                        var11 = "robinhood9";
                     } else {
                        var11 = "robinhood18";
                     }
                  } else if (var1.bb().a(40) == 2 || var1.bb().a(40) == 3 || var1.bb().a(40) == 4 || var1.bb().a(40) == 5 || var1.bb().a(40) == 6) {
                     var11 = "robinhood13";
                  } else if (var1.bb().a(40) == 1) {
                     var11 = "robinhood8";
                  } else {
                     var11 = "robinhood1";
                  }
                  break;
               case 71257:
                  if (!var1.A()) {
                     var11 = "zybril16";
                  } else if (var1.bb().a(40) >= 7) {
                     var11 = "zybril19";
                  } else if (var1.j().f(41349) && var1.bb().a(40) == 7) {
                     var11 = "zybril19";
                  } else if (var1.j().f(41349) && var1.bb().a(40) == 6) {
                     var11 = "zybril18";
                  } else if (var1.bb().a(40) == 6 && !var1.j().f(41354)) {
                     var11 = "zybril7";
                  } else if (var1.bb().a(40) == 6 && var1.j().f(41354)) {
                     var11 = "zybril17";
                  } else if (var1.j().f(41353) && var1.j().g(40514, 10) && var1.bb().a(40) == 5) {
                     var11 = "zybril8";
                  } else if (var1.bb().a(40) == 5) {
                     var11 = "zybril13";
                  } else if (var1.bb().a(40) == 4 && var1.j().g(40048, 10) && var1.j().g(40049, 10) && var1.j().g(40050, 10) && var1.j().g(40051, 10)) {
                     var11 = "zybril7";
                  } else if (var1.bb().a(40) == 4) {
                     var11 = "zybril12";
                  } else if (var1.bb().a(40) == 3) {
                     var11 = "zybril3";
                  } else if (var1.A() && (var1.bb().a(40) == 2 || var1.bb().a(40) == 1)) {
                     var11 = "zybril1";
                  }
                  break;
               case 71258:
                  if (var1.fa() <= -501) {
                     var11 = "marba1";
                  } else if (!var1.x() && !var1.C() && !var1.z() && !var1.B() && !var1.D() && !var1.E()) {
                     if (!var1.j().f(40665)
                        || !var1.j().f(40693) && !var1.j().f(40694) && !var1.j().f(40695) && !var1.j().f(40697) && !var1.j().f(40698) && !var1.j().f(40699)) {
                        if (var1.j().f(40665)) {
                           var11 = "marba17";
                        } else if (var1.j().f(40664)) {
                           var11 = "marba19";
                        } else if (var1.j().f(40637)) {
                           var11 = "marba18";
                        }
                     } else {
                        var11 = "marba8";
                     }
                  } else {
                     var11 = "marba2";
                  }
                  break;
               case 71259:
                  if (var1.fa() <= -501) {
                     var11 = "aras12";
                  } else if (!var1.x() && !var1.C() && !var1.z() && !var1.B() && !var1.D() && !var1.E()) {
                     if (!var1.j().f(40665)
                        || !var1.j().f(40679) && !var1.j().f(40680) && !var1.j().f(40681) && !var1.j().f(40682) && !var1.j().f(40683) && !var1.j().f(40684)) {
                        if (var1.j().f(40665)) {
                           var11 = "aras8";
                        } else if (var1.j().f(40679)
                           || var1.j().f(40680)
                           || var1.j().f(40681)
                           || var1.j().f(40682)
                           || var1.j().f(40683)
                           || var1.j().f(40684)
                           || var1.j().f(40693)
                           || var1.j().f(40694)
                           || var1.j().f(40695)
                           || var1.j().f(40697)
                           || var1.j().f(40698)
                           || var1.j().f(40699)) {
                           var11 = "aras3";
                        } else if (var1.j().f(40664)) {
                           var11 = "aras6";
                        } else if (var1.j().f(40637)) {
                           var11 = "aras1";
                        } else {
                           var11 = "aras7";
                        }
                     } else {
                        var11 = "aras3";
                     }
                  } else {
                     var11 = "aras11";
                  }
                  break;
               case 80047:
                  if (var1.Q() <= -3) {
                     var11 = "uhelp2";
                  }
                  break;
               case 80048:
                  if (var1.ev() <= 44) {
                     var11 = "entgate3";
                  } else if (var1.ev() >= 45 && var1.ev() <= 51) {
                     var11 = "entgate2";
                  } else {
                     var11 = "entgate";
                  }
                  break;
               case 80049:
                  if (var1.P() <= -10000000) {
                     var11 = "betray11";
                  }
                  break;
               case 80050:
                  if (var1.Q() > -1) {
                     var11 = "meet103";
                  }
                  break;
               case 80053:
                  var4 = var1.Q();
                  if (var4 == 0) {
                     var11 = "aliceyet";
                  } else if (var4 >= 1) {
                     if (!var1.j().f(196)
                        && !var1.j().f(197)
                        && !var1.j().f(198)
                        && !var1.j().f(199)
                        && !var1.j().f(200)
                        && !var1.j().f(201)
                        && !var1.j().f(202)
                        && !var1.j().f(203)) {
                        var11 = "gd";
                     } else {
                        var11 = "alice_gd";
                     }
                  } else if (var4 <= -1) {
                     if (var1.j().f(40991)) {
                        if (var4 <= -1) {
                           var11 = "Mate_1";
                        }
                     } else if (var1.j().f(196)) {
                        if (var4 <= -2) {
                           var11 = "Mate_2";
                        } else {
                           var11 = "alice_1";
                        }
                     } else if (var1.j().f(197)) {
                        if (var4 <= -3) {
                           var11 = "Mate_3";
                        } else {
                           var11 = "alice_2";
                        }
                     } else if (var1.j().f(198)) {
                        if (var4 <= -4) {
                           var11 = "Mate_4";
                        } else {
                           var11 = "alice_3";
                        }
                     } else if (var1.j().f(199)) {
                        if (var4 <= -5) {
                           var11 = "Mate_5";
                        } else {
                           var11 = "alice_4";
                        }
                     } else if (var1.j().f(200)) {
                        if (var4 <= -6) {
                           var11 = "Mate_6";
                        } else {
                           var11 = "alice_5";
                        }
                     } else if (var1.j().f(201)) {
                        if (var4 <= -7) {
                           var11 = "Mate_7";
                        } else {
                           var11 = "alice_6";
                        }
                     } else if (var1.j().f(202)) {
                        if (var4 <= -8) {
                           var11 = "Mate_8";
                        } else {
                           var11 = "alice_7";
                        }
                     } else if (var1.j().f(203)) {
                        var11 = "alice_8";
                     } else {
                        var11 = "alice_no";
                     }
                  }
                  break;
               case 80055:
                  int var55 = 0;
                  if (var1.j().f(20358)) {
                     var55 = 1;
                  } else if (var1.j().f(20359)) {
                     var55 = 2;
                  } else if (var1.j().f(20360)) {
                     var55 = 3;
                  } else if (var1.j().f(20361)) {
                     var55 = 4;
                  } else if (var1.j().f(20362)) {
                     var55 = 5;
                  } else if (var1.j().f(20363)) {
                     var55 = 6;
                  } else if (var1.j().f(20364)) {
                     var55 = 7;
                  } else if (var1.j().f(20365)) {
                     var55 = 8;
                  }

                  if (var1.Q() == -1) {
                     if (var55 >= 1) {
                        var11 = "uamuletd";
                     } else {
                        var11 = "uamulet1";
                     }
                  } else if (var1.Q() == -2) {
                     if (var55 >= 2) {
                        var11 = "uamuletd";
                     } else {
                        var11 = "uamulet2";
                     }
                  } else if (var1.Q() == -3) {
                     if (var55 >= 3) {
                        var11 = "uamuletd";
                     } else {
                        var11 = "uamulet3";
                     }
                  } else if (var1.Q() == -4) {
                     if (var55 >= 4) {
                        var11 = "uamuletd";
                     } else {
                        var11 = "uamulet4";
                     }
                  } else if (var1.Q() == -5) {
                     if (var55 >= 5) {
                        var11 = "uamuletd";
                     } else {
                        var11 = "uamulet5";
                     }
                  } else if (var1.Q() == -6) {
                     if (var55 >= 6) {
                        var11 = "uamuletd";
                     } else {
                        var11 = "uamulet6";
                     }
                  } else if (var1.Q() == -7) {
                     if (var55 >= 7) {
                        var11 = "uamuletd";
                     } else {
                        var11 = "uamulet7";
                     }
                  } else if (var1.Q() == -8) {
                     if (var55 >= 8) {
                        var11 = "uamuletd";
                     } else {
                        var11 = "uamulet8";
                     }
                  } else {
                     var11 = "uamulet0";
                  }
                  break;
               case 80056:
                  if (var1.P() <= -10000000) {
                     var11 = "infamous11";
                  }
                  break;
               case 80057:
                  var4 = var1.Q();
                  String[] var18 = new String[]{"alfons1", "cbk1", "cbk2", "cbk3", "cbk4", "cbk5", "cbk6", "cbk7", "cbk8"};
                  String[] var19 = new String[]{"cyk1", "cyk2", "cyk3", "cyk4", "cyk5", "cyk6", "cyk7", "cyk8"};
                  if (var4 < 0) {
                     var11 = var19[Math.abs(var4) - 1];
                  } else if (var4 >= 0) {
                     var11 = var18[var4];
                  }
                  break;
               case 80058:
                  int var20 = var1.ev();
                  if (var20 <= 44) {
                     var11 = "cpass03";
                  } else if (var20 <= 51 && 45 <= var20) {
                     var11 = "cpass02";
                  }
                  break;
               case 80059:
                  if (var1.Q() > 0) {
                     var11 = "cpass03";
                  } else if (var1.j().f(40921)) {
                     var11 = "wpass02";
                  } else if (var1.j().f(40917)) {
                     var11 = "wpass14";
                  } else if (var1.j().f(40912) || var1.j().f(40910) || var1.j().f(40911)) {
                     var11 = "wpass04";
                  } else if (var1.j().f(40909)) {
                     int var54 = b(var1);
                     if (var1.j().g(40913, var54)) {
                        a(var1, 1, var54);
                        var11 = "wpass06";
                     } else {
                        var11 = "wpass03";
                     }
                  } else if (var1.j().f(40913)) {
                     var11 = "wpass08";
                  }
                  break;
               case 80060:
                  if (var1.Q() > 0) {
                     var11 = "cpass03";
                  } else if (var1.j().f(40921)) {
                     var11 = "wpass02";
                  } else if (var1.j().f(40920)) {
                     var11 = "wpass13";
                  } else if (var1.j().f(40909) || var1.j().f(40910) || var1.j().f(40911)) {
                     var11 = "wpass04";
                  } else if (var1.j().f(40912)) {
                     int var53 = b(var1);
                     if (var1.j().g(40916, var53)) {
                        a(var1, 8, var53);
                        var11 = "wpass06";
                     } else {
                        var11 = "wpass03";
                     }
                  } else if (var1.j().f(40916)) {
                     var11 = "wpass08";
                  }
                  break;
               case 80061:
                  if (var1.Q() > 0) {
                     var11 = "cpass03";
                  } else if (var1.j().f(40921)) {
                     var11 = "wpass02";
                  } else if (var1.j().f(40918)) {
                     var11 = "wpass11";
                  } else if (var1.j().f(40909) || var1.j().f(40912) || var1.j().f(40911)) {
                     var11 = "wpass04";
                  } else if (var1.j().f(40910)) {
                     int var52 = b(var1);
                     if (var1.j().g(40914, var52)) {
                        a(var1, 4, var52);
                        var11 = "wpass06";
                     } else {
                        var11 = "wpass03";
                     }
                  } else if (var1.j().f(40914)) {
                     var11 = "wpass08";
                  }
                  break;
               case 80062:
                  if (var1.Q() > 0) {
                     var11 = "cpass03";
                  } else if (var1.j().f(40921)) {
                     var11 = "wpass02";
                  } else if (var1.j().f(40919)) {
                     var11 = "wpass12";
                  } else if (var1.j().f(40909) || var1.j().f(40912) || var1.j().f(40910)) {
                     var11 = "wpass04";
                  } else if (var1.j().f(40911)) {
                     int var21 = b(var1);
                     if (var1.j().g(40915, var21)) {
                        a(var1, 2, var21);
                        var11 = "wpass06";
                     } else {
                        var11 = "wpass03";
                     }
                  } else if (var1.j().f(40915)) {
                     var11 = "wpass08";
                  }
                  break;
               case 80064:
                  if (var1.Q() < 1) {
                     var11 = "meet003";
                  }
                  break;
               case 80065:
                  if (var1.Q() >= 3) {
                     var11 = "uturn1";
                  }
                  break;
               case 80066:
                  if (var1.P() < 10000000) {
                     var11 = "betray02";
                  }
                  break;
               case 80067:
                  if (var1.bb().a(36) == 255) {
                     var11 = "minicod10";
                  } else if (var1.Q() >= 1) {
                     var11 = "minicod07";
                  } else if (var1.bb().a(36) == 1 && var1.fe() == 6034) {
                     var11 = "minicod03";
                  } else if (var1.bb().a(36) == 1 && var1.fe() != 6034) {
                     var11 = "minicod05";
                  } else if (var1.bb().a(37) == 255 || var1.j().f(41121) || var1.j().f(41122)) {
                     var11 = "minicod01";
                  } else if (var1.j().f(41130) && var1.j().f(41131)) {
                     var11 = "minicod06";
                  } else if (var1.j().f(41130)) {
                     var11 = "minicod02";
                  }
                  break;
               case 80071:
                  int var22 = 0;
                  if (var1.j().f(21020)) {
                     var22 = 1;
                  } else if (var1.j().f(21021)) {
                     var22 = 2;
                  } else if (var1.j().f(21022)) {
                     var22 = 3;
                  } else if (var1.j().f(21023)) {
                     var22 = 4;
                  } else if (var1.j().f(21024)) {
                     var22 = 5;
                  } else if (var1.j().f(21025)) {
                     var22 = 6;
                  } else if (var1.j().f(21026)) {
                     var22 = 7;
                  } else if (var1.j().f(21027)) {
                     var22 = 8;
                  }

                  if (var1.Q() == 1) {
                     if (var22 >= 1) {
                        var11 = "lringd";
                     } else {
                        var11 = "lring1";
                     }
                  } else if (var1.Q() == 2) {
                     if (var22 >= 2) {
                        var11 = "lringd";
                     } else {
                        var11 = "lring2";
                     }
                  } else if (var1.Q() == 3) {
                     if (var22 >= 3) {
                        var11 = "lringd";
                     } else {
                        var11 = "lring3";
                     }
                  } else if (var1.Q() == 4) {
                     if (var22 >= 4) {
                        var11 = "lringd";
                     } else {
                        var11 = "lring4";
                     }
                  } else if (var1.Q() == 5) {
                     if (var22 >= 5) {
                        var11 = "lringd";
                     } else {
                        var11 = "lring5";
                     }
                  } else if (var1.Q() == 6) {
                     if (var22 >= 6) {
                        var11 = "lringd";
                     } else {
                        var11 = "lring6";
                     }
                  } else if (var1.Q() == 7) {
                     if (var22 >= 7) {
                        var11 = "lringd";
                     } else {
                        var11 = "lring7";
                     }
                  } else if (var1.Q() == 8) {
                     if (var22 >= 8) {
                        var11 = "lringd";
                     } else {
                        var11 = "lring8";
                     }
                  } else {
                     var11 = "lring0";
                  }
                  break;
               case 80072:
                  var4 = var1.Q();
                  String[] var23 = new String[]{"lsmith0", "lsmith1", "lsmith2", "lsmith3", "lsmith4", "lsmith5", "lsmith7", "lsmith8"};
                  if (var4 <= 8) {
                     var11 = var23[var4 - 1];
                  }
                  break;
               case 80074:
                  if (var1.P() >= 10000000) {
                     var11 = "infamous01";
                  }
                  break;
               case 80076:
                  if (var1.j().f(41058)) {
                     var11 = "voyager8";
                  } else if (!var1.j().f(49082) && !var1.j().f(49083)) {
                     if (var1.j().f(49082)
                        || var1.j().f(49083)
                        || var1.j().f(49084)
                        || var1.j().f(49085)
                        || var1.j().f(49086)
                        || var1.j().f(49087)
                        || var1.j().f(49088)
                        || var1.j().f(49089)
                        || var1.j().f(49090)
                        || var1.j().f(49091)) {
                        var11 = "voyager7";
                     }
                  } else if (!var1.j().f(41038)
                     && !var1.j().f(41039)
                     && !var1.j().f(41039)
                     && !var1.j().f(41039)
                     && !var1.j().f(41039)
                     && !var1.j().f(41039)
                     && !var1.j().f(41039)
                     && !var1.j().f(41039)
                     && !var1.j().f(41039)
                     && !var1.j().f(41039)) {
                     var11 = "voyager7";
                  } else {
                     var11 = "voyager9";
                  }
                  break;
               case 80079:
                  if (var1.bb().a(35) == 255 && !var1.j().f(41312)) {
                     var11 = "keplisha6";
                  } else if (var1.j().f(41314)) {
                     var11 = "keplisha3";
                  } else if (var1.j().f(41313)) {
                     var11 = "keplisha2";
                  } else if (var1.j().f(41312)) {
                     var11 = "keplisha4";
                  }
                  break;
               case 80094:
                  if (var1.E()) {
                     var11 = "altar1";
                  } else if (!var1.E()) {
                     var11 = "altar2";
                  }
                  break;
               case 80099:
                  if (var1.bb().a(41) == 1) {
                     if (var1.j().g(41325, 1)) {
                        var11 = "rarson8";
                     } else {
                        var11 = "rarson10";
                     }
                  } else if (var1.bb().a(41) == 2) {
                     if (var1.j().g(41317, 1) && var1.j().g(41315, 1)) {
                        var11 = "rarson13";
                     } else {
                        var11 = "rarson19";
                     }
                  } else if (var1.bb().a(41) == 3) {
                     var11 = "rarson14";
                  } else if (var1.bb().a(41) == 4) {
                     if (!var1.j().g(41326, 1)) {
                        var11 = "rarson18";
                     } else if (var1.j().g(41326, 1)) {
                        var11 = "rarson11";
                     } else {
                        var11 = "rarson17";
                     }
                  } else if (var1.bb().a(41) >= 5) {
                     var11 = "rarson1";
                  }
                  break;
               case 80101:
                  if (var1.bb().a(41) == 4) {
                     if (var1.j().g(41315, 1) && var1.j().g(40494, 30) && var1.j().g(41317, 1)) {
                        var11 = "kuen4";
                     } else if (var1.j().g(41316, 1)) {
                        var11 = "kuen1";
                     } else if (!var1.j().f(41316)) {
                        var1.bb().a(41, 1);
                     }
                  } else if (var1.bb().a(41) == 2 && var1.j().g(41317, 1)) {
                     var11 = "kuen3";
                  } else {
                     var11 = "kuen1";
                  }
                  break;
               case 80102:
                  if (var1.j().f(41329)) {
                     var11 = "fillis3";
                  }
                  break;
               case 80104:
                  if (!var1.x()) {
                     var11 = "horseseller4";
                  }
                  break;
               case 81155:
                  if (c(var1, 8)) {
                     if (a(var1)) {
                        var11 = "olle1";
                     } else {
                        var11 = "olle6";
                        var12 = new String[]{var1.et()};
                     }
                  }
                  break;
               case 81156:
                  var15 = var15 + "_8";
                  var13 = CastleTable.a().a(8);
                  var14 = ClanTable.a().a(var13.g());
                  if (var14 != null) {
                     var15 = var14.f();
                     var16 = var14.l();
                  }

                  var11 = "ktguard6";
                  var12 = new String[]{var0.et(), var15, var16};
                  break;
               case 81202:
                  if (var1.bb().a(37) == 255) {
                     var11 = "minitos10";
                  } else if (var1.Q() <= -1) {
                     var11 = "minitos07";
                  } else if (var1.bb().a(37) == 1 && var1.fe() == 6035) {
                     var11 = "minitos03";
                  } else if (var1.bb().a(37) == 1 && var1.fe() != 6035) {
                     var11 = "minitos05";
                  } else if (var1.bb().a(36) == 255 || var1.j().f(41130) || var1.j().f(41131)) {
                     var11 = "minitos01";
                  } else if (var1.j().f(41121) && var1.j().f(41122)) {
                     var11 = "minitos06";
                  } else if (var1.j().f(41121)) {
                     var11 = "minitos02";
                  }
                  break;
               case 81208:
                  if (!var1.j().f(41129) && !var1.j().f(41138)) {
                     if (var1.j().f(41126) && var1.j().f(41127) && var1.j().f(41128) || var1.j().f(41135) && var1.j().f(41136) && var1.j().f(41137)) {
                        var11 = "minibrob02";
                     }
                  } else {
                     var11 = "minibrob04";
                  }
                  break;
               case 81255:
               case 190371:
                  if (var1.ev() <= 51) {
                     d(var1, 1);
                  }
                  break;
               case 81256:
                  if (var1.ev() < 3) {
                     var1.x(ExpTable.b(var1.ev()));
                  }

                  if (var1.ev() < 5) {
                     var11 = "newadmin1";
                  } else if (var1.ev() >= 5 && var1.ev() <= 51) {
                     var11 = "newadmin2";
                  } else {
                     var11 = "newadmin3";
                  }

                  if (var1.ev() < 13) {
                     d(var1, 2);
                     d(var1, 3);
                  }
                  break;
               case 81258:
                  if (var1.E()) {
                     var11 = "asha1";
                  }
                  break;
               case 81259:
                  if (var1.D()) {
                     var11 = "feaena1";
                  }
                  break;
               case 81260:
                  int var24 = var1.bF();
                  if (var1.ev() > 9 && var24 > 0 && var24 < 11) {
                     var11 = "artisan1";
                  }
                  break;
               case 81261:
                  if (var1.j().g(49031, 1)) {
                     if (var1.j().g(21081, 1)) {
                        var11 = "gemout1";
                     } else if (var1.j().g(21082, 1)) {
                        var11 = "gemout2";
                     } else if (var1.j().g(21083, 1)) {
                        var11 = "gemout3";
                     } else if (var1.j().g(21084, 1)) {
                        var11 = "gemout4";
                     } else if (var1.j().g(21085, 1)) {
                        var11 = "gemout5";
                     } else if (var1.j().g(21086, 1)) {
                        var11 = "gemout6";
                     } else if (var1.j().g(21087, 1)) {
                        var11 = "gemout7";
                     } else if (var1.j().g(21088, 1)) {
                        var11 = "gemout8";
                     } else {
                        var11 = "gemout17";
                     }
                  }
                  break;
               case 81322:
                  if (var1.ev() < 52) {
                     var11 = "adjutant2";
                     if (var1.z()) {
                        var11 = "adjutant4";
                     }
                  }
                  break;
               case 81334:
                  if (var1.x()) {
                     var11 = "rtf01";
                  } else if (var1.z()) {
                     var11 = "rtf02";
                  } else if (var1.A()) {
                     var11 = "rtf03";
                  } else if (var1.B()) {
                     var11 = "rtf04";
                  }
                  break;
               case 81335:
                  if (var1.j().g(49241, 1)) {
                     var11 = "50q_pout1";
                  } else {
                     var11 = "50q_pout";
                  }
                  break;
               case 81350:
                  if (var1.A() && var1.bb().a(4) == 3) {
                     var11 = "dspy2";
                  }
                  break;
               case 81371:
                  if (var1.j().f(41701)) {
                     var11 = "j_html00";
                  }
                  break;
               case 81372:
                  if (var1.bb().a(42) == 1) {
                     var11 = "id1";
                  } else if (var1.bb().a(43) == 1) {
                     var11 = "id0";
                  }
                  break;
               case 81401:
                  if (var1.bb().a(45) == 255) {
                     var11 = "marbinquest9";
                  } else if (var1.j().f(640694) || var1.bb().a(45) == 1) {
                     var11 = "marbinquest3";
                  }
                  break;
               case 81404:
                  if (var1.j().f(640699)) {
                     var11 = "icqwand4";
                  }
                  break;
               case 81431:
                  if (!var1.A()) {
                     var11 = "rinda2";
                  }
                  break;
               case 190081:
                  var11 = var1.ev() < 52 ? "newbiegate1" : "newbiegate2";
                  break;
               case 190096:
                  if (!var1.j().f(640357) && !var1.j().f(640358)) {
                     var11 = "ekins1";
                  } else {
                     var11 = "ekins2";
                  }
                  break;
               case 190138:
                  if (var1.ev() < 2) {
                     var1.x(ExpTable.b(var1.ev()));
                     ItemTable.a(var1, 42099, 5);
                     var11 = "newtutor1";
                  } else if (var1.ev() >= 2 && var1.ev() <= 51) {
                     var11 = "newtutor2";
                  } else {
                     var11 = "newtutor3";
                  }
                  break;
               case 190139:
               case 190143:
                  if (var1.x() && var1.ev() < 10) {
                     var11 = "sirissnt";
                  } else if (var1.z() && var1.ev() < 50) {
                     var11 = "sirissnw";
                  } else if (var1.A() && var1.ev() < 8) {
                     var11 = "sirissnt";
                  } else if (var1.B() && var1.ev() < 4) {
                     var11 = "sirissnt";
                  } else if (var1.C() && var1.ev() < 12) {
                     var11 = "sirissnt";
                  } else if (var1.D()) {
                     var11 = "sirisswr";
                  } else if (var1.E()) {
                     var11 = "sirissdk";
                  } else if (var1.F() && var1.ev() < 50) {
                     var11 = "sirisswr";
                  } else {
                     var11 = "siriss";
                  }
                  break;
               case 190272:
                  if (var1.j().f(21339)) {
                     var11 = "twf_earring5";
                  } else {
                     var11 = "twf_earring";
                  }
                  break;
               case 190274:
                  if (var1.j().f(640560)) {
                     var11 = "oldbook1";
                  } else {
                     var11 = "oldbook2";
                  }
                  break;
               case 190276:
                  var11 = var1.ev() < 70 ? "nerva1" : "nerva2";
                  break;
               case 190277:
                  if (var1.j().f(640562)) {
                     var11 = "riddle1";
                  } else if (var1.j().f(640563)) {
                     var11 = "riddle3";
                  } else {
                     var11 = "riddle4";
                  }
                  break;
               case 190366:
                  var11 = "clgunter2";
                  if (var1.ev() < 8) {
                     d(var1, 2);
                     var11 = "clgunter1";
                  }
                  break;
               case 190367:
                  if (var1.ev() >= 10) {
                     var11 = "cltoti";
                  }
                  break;
               case 190487:
                  if (var1.ev() < 81) {
                     var11 = "chisvall2";
                  }
                  break;
               case 190489:
                  if (var1.ev() > 55) {
                     var11 = "tel_lala2";
                  }
                  break;
               case 190492:
                  if (var1.fp() == 6340) {
                     var11 = "tw_vip1";
                  } else if (var1.fp() == 6341) {
                     var11 = "tw_vip2";
                  } else if (var1.fp() == 6342) {
                     var11 = "tw_vip3";
                  } else if (var1.fp() == 6343) {
                     var11 = "tw_vip4";
                  } else if (var1.fp() == 6344) {
                     var11 = "tw_vip5";
                  } else {
                     var11 = "tw_vip6";
                  }
            }

            if (var11 != null) {
               var1.a(new S_Html(var2, var11, var12));
            } else {
               if (var0.E().length() > 0 && var1.fa() < -1000) {
                  var1.a(new S_Html(var2, var0.E()));
               } else if (var0.D().length() > 0) {
                  var1.a(new S_Html(var2, var0.D()));
               }
            }
         }
      }
   }

   private static String a(L1PcInstance var0, int var1) {
      String var2;
      if (var0.bF() == var1 && TownTable.a().a(var0, var1)) {
         var2 = "secretary1";
      } else {
         var2 = "secretary2";
      }

      return var2;
   }

   private static String b(L1PcInstance var0, int var1) {
      String var2;
      if (var0.bF() == var1) {
         var2 = "hometown";
      } else {
         var2 = "othertown";
      }

      return var2;
   }

   private static boolean c(L1PcInstance var0, int var1) {
      if (var0.aF() != 0) {
         L1Clan var2 = ClanTable.a().a(var0.aF());
         if (var2 != null && var2.m() == var1) {
            return true;
         }
      }

      return false;
   }

   private static boolean a(L1PcInstance var0) {
      if (var0.x()) {
         L1Clan var1 = ClanTable.a().a(var0.aF());
         if (var1 != null && var0.fr() == var1.k()) {
            return true;
         }
      }

      return false;
   }

   private static int b(L1PcInstance var0) {
      int var1 = 0;
      int var2 = 10;
      if (var0.j().f(40917)) {
         var1++;
      }

      if (var0.j().f(40920)) {
         var1++;
      }

      if (var0.j().f(40918)) {
         var1++;
      }

      if (var0.j().f(40919)) {
         var1++;
      }

      if (var1 == 0) {
         var2 = 10;
      } else if (var1 == 1) {
         var2 = 100;
      } else if (var1 == 2) {
         var2 = 200;
      } else if (var1 == 3) {
         var2 = 500;
      }

      return var2;
   }

   private static void a(L1PcInstance var0, int var1, int var2) {
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      if (var1 == 1) {
         var3 = 40917;
         var4 = 40909;
         var5 = 40913;
      } else if (var1 == 2) {
         var3 = 40919;
         var4 = 40911;
         var5 = 40915;
      } else if (var1 == 4) {
         var3 = 40918;
         var4 = 40910;
         var5 = 40914;
      } else if (var1 == 8) {
         var3 = 40920;
         var4 = 40912;
         var5 = 40916;
      }

      var0.j().b(var4, 1);
      var0.j().b(var5, var2);
      ItemTable.a(var0, var3, 1);
   }

   private static String c(L1PcInstance var0) {
      String var1 = "";
      if (var0.ev() < 13) {
         var1 = "jpe0161";
      } else {
         var1 = "jpe0162";
      }

      return var1;
   }

   private static String d(L1PcInstance var0) {
      String var1 = "";
      if (var0.x() || var0.B()) {
         var1 = "en0101";
      } else if (var0.z() || var0.A() || var0.C()) {
         var1 = "en0102";
      }

      return var1;
   }

   private static String e(L1PcInstance var0) {
      String var1 = "";
      if (var0.ev() < 3) {
         var1 = "en0301";
      } else if (var0.ev() >= 3 && var0.ev() < 7) {
         var1 = "en0302";
      } else if (var0.ev() >= 7 && var0.ev() < 9) {
         var1 = "en0303";
      } else if (var0.ev() >= 9 && var0.ev() < 12) {
         var1 = "en0304";
      } else if (var0.ev() >= 12 && var0.ev() < 13) {
         var1 = "en0305";
      } else if (var0.ev() >= 13 && var0.ev() < 25) {
         var1 = "en0306";
      } else {
         var1 = "en0307";
      }

      return var1;
   }

   private static String f(L1PcInstance var0) {
      String var1 = "";
      if (var0.ev() < 25) {
         var1 = "jpe0041";
         if (var0.j().f(41209) || var0.j().f(41210) || var0.j().f(41211) || var0.j().f(41212)) {
            var1 = "jpe0043";
         }

         if (var0.j().f(41213)) {
            var1 = "jpe0044";
         }
      } else {
         var1 = "jpe0045";
      }

      return var1;
   }

   private static void d(L1PcInstance var0, int var1) {
      switch (var1) {
         case 1:
            var0.a(new S_ServerMessage(183));
            var0.a(new S_SkillHaste(var0.fr(), 1, 1600));
            var0.b(new S_SkillHaste(var0.fr(), 1, 0));
            var0.a(new S_SkillSound(var0.fr(), 755));
            var0.b(new S_SkillSound(var0.fr(), 755));
            var0.cu(1);
            var0.j(1001, 1600000);
            var0.a(var0.ew());
            var0.i_(var0.ex());
            var0.a(new S_ServerMessage(77));
            var0.a(new S_SkillSound(var0.fr(), 830));
            break;
         case 2:
            var0.a(new S_ServerMessage(183));
            var0.a(new S_SkillHaste(var0.fr(), 1, 1600));
            var0.b(new S_SkillHaste(var0.fr(), 1, 0));
            var0.a(new S_SkillSound(var0.fr(), 755));
            var0.b(new S_SkillSound(var0.fr(), 755));
            var0.cu(1);
            var0.j(1001, 1600000);
            break;
         case 3:
            new S_008().a(var0, 0);
      }
   }
}
