package l1r.bg;

import l1r.ao.AccountTable;
import l1r.ap.L1MonsterInstance;
import l1r.ap.L1NpcInstance;
import l1r.ap.L1PcInstance;
import l1r.ap.L1PetInstance;
import l1r.ap.L1SummonInstance;
import l1r.aq.L1Character;
import l1r.aq.L1Cooking;
import l1r.az.L1Poison;
import l1r.be.S_CharEvent;
import l1r.be.S_DoActionGFX;
import l1r.be.S_HPUpdate;
import l1r.be.S_Liquor;
import l1r.be.S_MPUpdate;
import l1r.be.S_OwnCharAttrDef;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_PacketBox;
import l1r.be.S_Paralysis;
import l1r.be.S_Poison;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_SPMR;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillBrave;
import l1r.be.S_SkillHaste;
import l1r.be.S_SkillIconAura;
import l1r.be.S_SkillIconBlessOfEva;
import l1r.bf.L1SkillExecutor;
import l1r.bh.L1Account;
import l1r.bi.LineageUtil;

class L1SkillTimer__obf_c {
   public static void a(L1Character var0, int var1) {
      if (var1 < 600) {
         L1SkillExecutor var106 = LineageUtil.a(var1);
         var106.a(var0);
      } else {
         if (var1 == 15003 || var1 == 15004) {
            var0.V(false);
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var103 = (L1PcInstance)var0;
               var103.a(new S_Poison(var103.fr(), 0));
               var103.b(new S_Poison(var103.fr(), 0));
               var103.a(new S_Paralysis(4, false));
            } else if (var0 instanceof L1MonsterInstance || var0 instanceof L1SummonInstance || var0 instanceof L1PetInstance) {
               L1NpcInstance var104 = (L1NpcInstance)var0;
               var104.b(new S_Poison(var104.fr(), 0));
            }
         } else if (var1 == 1028) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var2 = (L1PcInstance)var0;
               var2.a(new S_Paralysis(6, false));
            } else if (var0 instanceof L1NpcInstance) {
               L1NpcInstance var6 = (L1NpcInstance)var0;
               var6.n(false);
            }
         } else if (var1 == 1018) {
            var0.ca(-30);
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var7 = (L1PcInstance)var0;
               var7.a(new S_OwnCharAttrDef(var7));
            }
         } else if (var1 == 1020) {
            var0.cb(-30);
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var8 = (L1PcInstance)var0;
               var8.a(new S_OwnCharAttrDef(var8));
            }
         } else if (var1 == 1022) {
            var0.bY(-30);
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var9 = (L1PcInstance)var0;
               var9.a(new S_OwnCharAttrDef(var9));
            }
         } else if (var1 == 1000 || var1 == 1016 || var1 == 1026) {
            var0.cv(0);
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var102 = (L1PcInstance)var0;
               var102.a(new S_SkillBrave(var102.fr(), 0, 0));
               var102.b(new S_SkillBrave(var102.fr(), 0, 0));
            }
         } else if (var1 == 1027) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var10 = (L1PcInstance)var0;
               var10.a(new S_Liquor(var10.fr(), 0));
               var10.b(new S_Liquor(var10.fr(), 0));
            }
         } else if (var1 == 1017) {
            var0.cv(0);
         } else if (var1 == 1001) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var11 = (L1PcInstance)var0;
               var11.a(new S_SkillHaste(var11.fr(), 0, 0));
               var11.b(new S_SkillHaste(var11.fr(), 0, 0));
            }

            var0.cu(0);
         } else if (var1 == 1003) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var12 = (L1PcInstance)var0;
               var12.a(new S_SkillIconBlessOfEva(var12.fr(), 0));
            }
         } else if (var1 == 1004) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var13 = (L1PcInstance)var0;
               var0.cp(-2);
               var13.a(new S_PacketBox(57, 0));
            }
         } else if (var1 == 1005) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var14 = (L1PcInstance)var0;
               var14.a(new S_ServerMessage(288));
            }
         } else if (var1 >= 3000 && var1 <= 3056) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var101 = (L1PcInstance)var0;
               L1Cooking.a(var101, var1);
            }
         } else if (var1 == 4006) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var15 = (L1PcInstance)var0;
               var15.cm(-3);
               var15.ck(-3);
               var15.d(-2);
            }
         } else if (var1 == 4008) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var16 = (L1PcInstance)var0;
               var16.bH(-50);
               var16.c(-4);
               var16.a(new S_HPUpdate(var16.ea(), var16.ew()));
               if (var16.q()) {
                  var16.aL().f(var16);
               }
            }
         } else if (var1 == 4009) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var17 = (L1PcInstance)var0;
               var17.bJ(-40);
               var17.d(-4);
               var17.a(new S_MPUpdate(var17.eb(), var17.ex()));
            }
         } else if (var1 == 4010) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var18 = (L1PcInstance)var0;
               var18.cm(-3);
               var18.ck(-3);
               var18.cn(-3);
               var18.cl(-3);
               var18.cp(-3);
            }
         } else if (var1 == 4011) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var19 = (L1PcInstance)var0;
               var19.bL(2);
               var19.bZ(-50);
               var19.a(new S_PacketBox(100, 82, 0));
               var19.a(new S_ServerMessage(3419));
            }
         } else if (var1 == 4012) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var20 = (L1PcInstance)var0;
               var20.c(-3);
               var20.d(-1);
               var20.bY(-50);
               var20.a(new S_PacketBox(100, 85, 0));
               var20.a(new S_ServerMessage(3419));
            }
         } else if (var1 == 4077) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var21 = (L1PcInstance)var0;
               var21.cp(-1);
               var21.co(-1);
               var21.ca(-50);
               var21.a(new S_PacketBox(100, 88, 0));
               var21.a(new S_ServerMessage(3419));
            }
         } else if (var1 == 4013) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var22 = (L1PcInstance)var0;
               var22.bH(-10);
               var22.a(new S_HPUpdate(var22.ea(), var22.ew()));
               if (var22.q()) {
                  var22.aL().f(var22);
               }
            }
         } else if (var1 == 4014) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var23 = (L1PcInstance)var0;
               var23.bH(-20);
               var23.a(new S_HPUpdate(var23.ea(), var23.ew()));
               if (var23.q()) {
                  var23.aL().f(var23);
               }
            }
         } else if (var1 == 4015) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var24 = (L1PcInstance)var0;
               var24.bH(-30);
               var24.a(new S_HPUpdate(var24.ea(), var24.ew()));
               if (var24.q()) {
                  var24.aL().f(var24);
               }
            }
         } else if (var1 == 4016) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var25 = (L1PcInstance)var0;
               var25.bH(-40);
               var25.a(new S_HPUpdate(var25.ea(), var25.ew()));
               if (var25.q()) {
                  var25.aL().f(var25);
               }
            }
         } else if (var1 == 4017) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var26 = (L1PcInstance)var0;
               var26.bH(-50);
               var26.c(-1);
               var26.a(new S_HPUpdate(var26.ea(), var26.ew()));
               if (var26.q()) {
                  var26.aL().f(var26);
               }
            }
         } else if (var1 == 4018) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var27 = (L1PcInstance)var0;
               var27.bH(-60);
               var27.c(-2);
               var27.a(new S_HPUpdate(var27.ea(), var27.ew()));
               if (var27.q()) {
                  var27.aL().f(var27);
               }
            }
         } else if (var1 == 4019) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var28 = (L1PcInstance)var0;
               var28.bH(-70);
               var28.c(-3);
               var28.a(new S_HPUpdate(var28.ea(), var28.ew()));
               if (var28.q()) {
                  var28.aL().f(var28);
               }
            }
         } else if (var1 == 4020) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var29 = (L1PcInstance)var0;
               var29.bH(-80);
               var29.c(-4);
               var29.cm(-1);
               var29.a(new S_HPUpdate(var29.ea(), var29.ew()));
               if (var29.q()) {
                  var29.aL().f(var29);
               }
            }
         } else if (var1 == 4021) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var30 = (L1PcInstance)var0;
               var30.bH(-100);
               var30.c(-5);
               var30.cm(-2);
               var30.ck(-2);
               var30.bN(-1);
               var30.a(new S_HPUpdate(var30.ea(), var30.ew()));
               if (var30.q()) {
                  var30.aL().f(var30);
               }
            }
         } else if (var1 == 4022) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var31 = (L1PcInstance)var0;
               var31.bH(-5);
               var31.bJ(-3);
               var31.a(new S_HPUpdate(var31.ea(), var31.ew()));
               var31.a(new S_MPUpdate(var31.eb(), var31.ex()));
               if (var31.q()) {
                  var31.aL().f(var31);
               }
            }
         } else if (var1 == 4023) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var32 = (L1PcInstance)var0;
               var32.bH(-10);
               var32.bJ(-6);
               var32.a(new S_HPUpdate(var32.ea(), var32.ew()));
               var32.a(new S_MPUpdate(var32.eb(), var32.ex()));
               if (var32.q()) {
                  var32.aL().f(var32);
               }
            }
         } else if (var1 == 4024) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var33 = (L1PcInstance)var0;
               var33.bH(-15);
               var33.bJ(-10);
               var33.a(new S_HPUpdate(var33.ea(), var33.ew()));
               var33.a(new S_MPUpdate(var33.eb(), var33.ex()));
               if (var33.q()) {
                  var33.aL().f(var33);
               }
            }
         } else if (var1 == 4025) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var34 = (L1PcInstance)var0;
               var34.bH(-20);
               var34.bJ(-15);
               var34.a(new S_HPUpdate(var34.ea(), var34.ew()));
               var34.a(new S_MPUpdate(var34.eb(), var34.ex()));
               if (var34.q()) {
                  var34.aL().f(var34);
               }
            }
         } else if (var1 == 4026) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var35 = (L1PcInstance)var0;
               var35.bH(-25);
               var35.bJ(-20);
               var35.a(new S_HPUpdate(var35.ea(), var35.ew()));
               var35.a(new S_MPUpdate(var35.eb(), var35.ex()));
               if (var35.q()) {
                  var35.aL().f(var35);
               }
            }
         } else if (var1 == 4027) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var36 = (L1PcInstance)var0;
               var36.bH(-30);
               var36.bJ(-20);
               var36.c(-1);
               var36.a(new S_HPUpdate(var36.ea(), var36.ew()));
               var36.a(new S_MPUpdate(var36.eb(), var36.ex()));
               if (var36.q()) {
                  var36.aL().f(var36);
               }
            }
         } else if (var1 == 4028) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var37 = (L1PcInstance)var0;
               var37.bH(-35);
               var37.bJ(-20);
               var37.c(-1);
               var37.d(-1);
               var37.a(new S_HPUpdate(var37.ea(), var37.ew()));
               var37.a(new S_MPUpdate(var37.eb(), var37.ex()));
               if (var37.q()) {
                  var37.aL().f(var37);
               }
            }
         } else if (var1 == 4029) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var38 = (L1PcInstance)var0;
               var38.bH(-40);
               var38.bJ(-25);
               var38.c(-2);
               var38.d(-1);
               var38.a(new S_HPUpdate(var38.ea(), var38.ew()));
               var38.a(new S_MPUpdate(var38.eb(), var38.ex()));
               if (var38.q()) {
                  var38.aL().f(var38);
               }
            }
         } else if (var1 == 4030) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var39 = (L1PcInstance)var0;
               var39.bH(-50);
               var39.bJ(-30);
               var39.c(-2);
               var39.d(-2);
               var39.cl(-2);
               var39.cn(-2);
               var39.bR(-1);
               var39.a(new S_HPUpdate(var39.ea(), var39.ew()));
               var39.a(new S_MPUpdate(var39.eb(), var39.ex()));
               if (var39.q()) {
                  var39.aL().f(var39);
               }
            }
         } else if (var1 == 4031) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var40 = (L1PcInstance)var0;
               var40.bJ(-5);
               var40.a(new S_MPUpdate(var40.eb(), var40.ex()));
            }
         } else if (var1 == 4032) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var41 = (L1PcInstance)var0;
               var41.bJ(-10);
               var41.a(new S_MPUpdate(var41.eb(), var41.ex()));
            }
         } else if (var1 == 4033) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var42 = (L1PcInstance)var0;
               var42.bJ(-15);
               var42.a(new S_MPUpdate(var42.eb(), var42.ex()));
            }
         } else if (var1 == 4034) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var43 = (L1PcInstance)var0;
               var43.bJ(-20);
               var43.a(new S_MPUpdate(var43.eb(), var43.ex()));
            }
         } else if (var1 == 4035) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var44 = (L1PcInstance)var0;
               var44.bJ(-25);
               var44.d(-1);
               var44.a(new S_MPUpdate(var44.eb(), var44.ex()));
            }
         } else if (var1 == 4036) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var45 = (L1PcInstance)var0;
               var45.bJ(-30);
               var45.d(-2);
               var45.a(new S_MPUpdate(var45.eb(), var45.ex()));
            }
         } else if (var1 == 4037) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var46 = (L1PcInstance)var0;
               var46.bJ(-35);
               var46.d(-3);
               var46.a(new S_MPUpdate(var46.eb(), var46.ex()));
            }
         } else if (var1 == 4038) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var47 = (L1PcInstance)var0;
               var47.bJ(-40);
               var47.d(-4);
               var47.a(new S_MPUpdate(var47.eb(), var47.ex()));
            }
         } else if (var1 == 4039) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var48 = (L1PcInstance)var0;
               var48.bJ(-50);
               var48.d(-5);
               var48.bV(-1);
               var48.cp(-1);
               var48.a(new S_MPUpdate(var48.eb(), var48.ex()));
            }
         } else if (var1 == 4040) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var49 = (L1PcInstance)var0;
               var49.co(-2);
            }
         } else if (var1 == 4041) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var50 = (L1PcInstance)var0;
               var50.co(-4);
            }
         } else if (var1 == 4042) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var51 = (L1PcInstance)var0;
               var51.co(-6);
            }
         } else if (var1 == 4043) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var52 = (L1PcInstance)var0;
               var52.co(-8);
            }
         } else if (var1 == 4044) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var53 = (L1PcInstance)var0;
               var53.co(-10);
               var53.bL(1);
            }
         } else if (var1 == 4045) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var54 = (L1PcInstance)var0;
               var54.co(-10);
               var54.bL(2);
            }
         } else if (var1 == 4046) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var55 = (L1PcInstance)var0;
               var55.co(-10);
               var55.bL(3);
            }
         } else if (var1 == 4047) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var56 = (L1PcInstance)var0;
               var56.co(-15);
               var56.bL(4);
               var56.F(-1);
            }
         } else if (var1 == 4048) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var57 = (L1PcInstance)var0;
               var57.co(-20);
               var57.bL(5);
               var57.bP(-1);
               var57.F(-3);
            }
         } else if (var1 == 4049) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var58 = (L1PcInstance)var0;
               var58.ce(-3);
               var58.cA(-1);
               var58.a(new S_PacketBox(88, var58.fk()));
            }
         } else if (var1 == 4050) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var59 = (L1PcInstance)var0;
               var59.cg(-3);
            }
         } else if (var1 == 4051) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var60 = (L1PcInstance)var0;
               var60.cf(-3);
            }
         } else if (var1 == 4052) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var61 = (L1PcInstance)var0;
               var61.cd(-3);
               var61.ck(-2);
            }
         } else if (var1 == 4053) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var62 = (L1PcInstance)var0;
               var62.ci(-3);
               var62.cA(-1);
               var62.a(new S_PacketBox(88, var62.fk()));
            }
         } else if (var1 == 4054) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var63 = (L1PcInstance)var0;
               var63.ch(-3);
               var63.cA(-1);
               var63.a(new S_PacketBox(88, var63.fk()));
            }
         } else if (var1 == 4055) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var64 = (L1PcInstance)var0;
               var64.ck(2);
               var64.cA(-1);
               var64.a(new S_PacketBox(88, var64.fk()));
            }
         } else if (var1 == 4056) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var65 = (L1PcInstance)var0;
               var65.bH(-100);
               var65.bJ(-50);
               var65.c(-3);
               var65.d(-3);
               var65.cb(-30);
               var65.ck(-1);
               var65.cm(-5);
               var65.C(-40);
               var65.a(new S_HPUpdate(var65.ea(), var65.ew()));
               if (var65.q()) {
                  var65.aL().f(var65);
               }

               var65.a(new S_MPUpdate(var65.eb(), var65.ex()));
            }
         } else if (var1 == 4057) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var66 = (L1PcInstance)var0;
               var66.bH(-80);
               var66.bJ(-10);
               var66.bZ(-30);
               var66.bL(8);
               var66.a(new S_HPUpdate(var66.ea(), var66.ew()));
               if (var66.q()) {
                  var66.aL().f(var66);
               }

               var66.a(new S_MPUpdate(var66.eb(), var66.ex()));
            }
         } else if (var1 == 4079) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var67 = (L1PcInstance)var0;
               var67.bH(-100);
               var67.bJ(-50);
               var67.c(-3);
               var67.d(-3);
               var67.bY(-30);
               var67.ck(-1);
               var67.cm(-5);
               var67.C(-40);
               var67.a(new S_HPUpdate(var67.ea(), var67.ew()));
               if (var67.q()) {
                  var67.aL().f(var67);
               }

               var67.a(new S_MPUpdate(var67.eb(), var67.ex()));
            }
         } else if (var1 == 4067) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var68 = (L1PcInstance)var0;
               var68.cm(-30);
               var68.ck(-30);
               var68.cn(-30);
               var68.cl(-30);
               var68.cp(-30);
               var68.a(new S_ProtoBuffers(110, var1));
            }
         } else if (var1 == 4068) {
            if (var0 instanceof L1PcInstance) {
               L1PcInstance var69 = (L1PcInstance)var0;
               var69.bL(50);
               var69.a(new S_OwnCharAttrDef(var69));
               var69.a(new S_ProtoBuffers(110, var1));
            }
         } else if (var1 != 4086 && var1 != 4087) {
            if (var1 != 4088 && var1 != 4089) {
               if (var1 != 4090 && var1 != 4091) {
                  if (var1 == 4092) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var73 = (L1PcInstance)var0;
                        var73.F(-5);
                        var73.a(new S_ProtoBuffers(110, var1));
                     }
                  } else if (var1 == 5018) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var74 = (L1PcInstance)var0;
                        var74.ck(-10);
                        var74.cl(-10);
                        var74.cm(-10);
                        var74.cn(-10);
                        var74.F(-5);
                        var74.cp(-5);
                        var74.a(new S_ProtoBuffers(110, var1));
                     }
                  } else if (var1 == 5014) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var75 = (L1PcInstance)var0;
                        var75.a(new S_SkillIconAura(221, 0, 1));
                     }
                  } else if (var1 == 5015) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var76 = (L1PcInstance)var0;
                        var76.a(new S_SkillIconAura(221, 0, 2));
                     }
                  } else if (var1 == 4069) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var77 = (L1PcInstance)var0;
                        var77.bH(-10);
                        var77.bJ(-10);
                        var77.c(-1);
                        var77.d(-1);
                        var77.bL(3);
                        var77.a(new S_ProtoBuffers(4069, 0, 8, 0, 0, 0, 0, 0, 3));
                     }
                  } else if (var1 == 4085) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var78 = (L1PcInstance)var0;
                        var78.bH(-100);
                        var78.bJ(-100);
                        var78.c(-5);
                        var78.d(-5);
                        var78.bN(-1);
                        var78.bR(-1);
                        var78.bP(-1);
                        var78.bX(-1);
                        var78.bV(-1);
                        var78.bT(-1);
                        var78.cA(-5);
                        var78.ck(-5);
                        var78.cl(-5);
                        var78.cp(-3);
                        var78.F(-5);
                        var78.bL(5);
                        var78.a(new S_PacketBox(132, var78.u()));
                        var78.a(new S_ProtoBuffers(4085, 0, 8, 0, 0, 0, 0, 0, 3));
                     }
                  } else if (var1 == 1032) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var79 = (L1PcInstance)var0;
                        var79.bH(-15);
                        var79.bJ(-15);
                        var79.F(-1);
                        var79.a(new S_ProtoBuffers(1032, 0, 8, 0, 0, 0, 0, 0, 3));
                     }
                  } else if (var1 == 1033) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var80 = (L1PcInstance)var0;
                        var80.bH(-30);
                        var80.bJ(-30);
                        var80.F(-2);
                        var80.a(new S_ProtoBuffers(1033, 0, 8, 0, 0, 0, 0, 0, 3));
                     }
                  } else if (var1 == 1034) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var81 = (L1PcInstance)var0;
                        var81.bH(-45);
                        var81.bJ(-45);
                        var81.F(-3);
                        var81.a(new S_ProtoBuffers(1034, 0, 8, 0, 0, 0, 0, 0, 3));
                     }
                  } else if (var1 == 1035) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var82 = (L1PcInstance)var0;
                        var82.bH(-65);
                        var82.bJ(-65);
                        var82.F(-4);
                        var82.a(new S_ProtoBuffers(1035, 0, 8, 0, 0, 0, 0, 0, 3));
                     }
                  } else if (var1 == 1036) {
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var83 = (L1PcInstance)var0;
                        var83.bH(-100);
                        var83.bJ(-75);
                        var83.F(-5);
                        var83.a(new S_ProtoBuffers(1036, 0, 8, 0, 0, 0, 0, 0, 3));
                     }
                  } else if (var1 == 1007) {
                     var0.y(0);
                     var0.a((L1Poison)null);
                     if (var0 instanceof L1PcInstance) {
                        L1PcInstance var84 = (L1PcInstance)var0;
                        var84.a(new S_PacketBox(161, 0, 0));
                        var84.a(new S_ServerMessage(311));
                     }
                  } else if (var1 != 25009 && var1 != 25010 && var1 != 25011) {
                     if (var1 == 4076) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var86 = (L1PcInstance)var0;
                           var86.a(new S_PacketBox(86, 173, 0, 0));
                        }
                     } else if (var1 == 25012) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var87 = (L1PcInstance)var0;
                           if (var87.aK() != null) {
                              L1Account var3 = var87.aK().e();
                              int var4 = var87.ep() == 0 ? 4 : 2;
                              var3.a(var4);
                              int var5 = var3.p() + var87.cQ();
                              if (var3.h() == 0 && var5 >= 1000) {
                                 var3.d(1);
                                 var87.a(new S_CharEvent(72, var3.h()));
                              } else if (var3.h() == 1 && var5 >= 2000) {
                                 var3.d(2);
                                 var87.a(new S_CharEvent(72, var3.h()));
                              } else if (var3.h() == 2 && var5 >= 4000) {
                                 var3.d(3);
                                 var87.a(new S_CharEvent(72, var3.h()));
                              } else if (var3.h() == 3 && var5 >= 10000) {
                                 var3.d(4);
                                 var87.a(new S_CharEvent(72, var3.h()));
                              } else if (var3.h() == 4 && var5 >= 30000) {
                                 var3.d(5);
                                 var87.a(new S_CharEvent(72, var3.h()));
                              }

                              AccountTable.a().a(var3);
                              var87.a(new S_CharEvent(37, var3.p()));
                              var87.j(25012, 1800000);
                           }
                        }
                     } else if (var1 == 4072) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var88 = (L1PcInstance)var0;
                           var88.bH(-50);
                           var88.ck(-2);
                           var88.c(-3);
                           var88.bN(-1);
                           if (var88.q()) {
                              var88.aL().f(var88);
                           }
                        }
                     } else if (var1 == 4073) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var89 = (L1PcInstance)var0;
                           var89.bH(-25);
                           var89.cl(-2);
                           var89.bJ(-25);
                           var89.c(-1);
                           var89.d(-1);
                           var89.bR(-1);
                           if (var89.q()) {
                              var89.aL().f(var89);
                           }
                        }
                     } else if (var1 == 4074) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var90 = (L1PcInstance)var0;
                           var90.bJ(-50);
                           var90.d(-3);
                           var90.bV(-1);
                           var90.cp(-2);
                        }
                     } else if (var1 == 4075) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var91 = (L1PcInstance)var0;
                           var91.bH(-30);
                           var91.bJ(-30);
                           var91.bL(5);
                           var91.co(-10);
                           var91.F(-1);
                           var91.a(new S_OwnCharAttrDef(var91));
                        }
                     } else if (var1 == 1006) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var92 = (L1PcInstance)var0;
                           var92.bH(-100);
                           var92.bJ(-100);
                           var92.cm(-10);
                           var92.ck(-5);
                           var92.cn(-10);
                           var92.cl(-5);
                           var92.cp(-5);
                           var92.bN(-1);
                           var92.bR(-1);
                           var92.bV(-1);
                           var92.a(new S_DoActionGFX(var92.fr(), 3));
                           var92.b(new S_DoActionGFX(var92.fr(), 3));
                        }
                     } else if (var1 == 1037) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var93 = (L1PcInstance)var0;
                           var93.bH(-120);
                           var93.bJ(-100);
                           var93.F(-15);
                           var93.co(-30);
                           var93.cm(-10);
                           var93.cn(-10);
                           var93.U(-3);
                           var93.bN(-3);
                           var93.bR(-3);
                           var93.bV(-3);
                           var93.a(new S_DoActionGFX(var93.fr(), 3));
                           var93.b(new S_DoActionGFX(var93.fr(), 3));
                           var93.bz(67);
                        }
                     } else if (var1 == 1031) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var94 = (L1PcInstance)var0;
                           var94.bH(-120);
                           var94.bJ(-100);
                           var94.cm(-10);
                           var94.ck(-7);
                           var94.cn(-10);
                           var94.cl(-7);
                           var94.cp(-5);
                           var94.bN(-1);
                           var94.bR(-1);
                           var94.bV(-1);
                           var94.a(new S_DoActionGFX(var94.fr(), 3));
                           var94.b(new S_DoActionGFX(var94.fr(), 3));
                        }
                     } else if (var1 == 1038) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var95 = (L1PcInstance)var0;
                           var95.bH(-100);
                           var95.bJ(-100);
                           var95.ck(-5);
                           var95.cm(-10);
                           var95.cl(-5);
                           var95.cn(-10);
                           var95.cp(-5);
                           var95.bL(10);
                           var95.co(-10);
                           var95.a(new S_ProtoBuffers(110, var1));
                        }
                     } else if (var1 == 5006) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var96 = (L1PcInstance)var0;
                           var96.bv(0);
                           var96.a(new S_PacketBox(204, var96));
                        }
                     } else if (var1 == 4080) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var97 = (L1PcInstance)var0;
                           var97.bH(-25);
                           var97.bJ(-20);
                        }
                     } else if (var1 == 4081) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var98 = (L1PcInstance)var0;
                           var98.bL(2);
                           var98.ck(-4);
                           var98.F(-1);
                        }
                     } else if (var1 == 4082) {
                        if (var0 instanceof L1PcInstance) {
                           L1PcInstance var99 = (L1PcInstance)var0;
                           var99.bL(2);
                           var99.cl(-4);
                           var99.F(-1);
                        }
                     } else if (var1 == 4083 && var0 instanceof L1PcInstance) {
                        L1PcInstance var100 = (L1PcInstance)var0;
                        var100.bL(2);
                        var100.cp(-3);
                        var100.F(-1);
                     }
                  } else if (var0 instanceof L1PcInstance) {
                     L1PcInstance var85 = (L1PcInstance)var0;
                     var85.bl(var1);
                     var85.a(new S_PacketBox(151, var1 - 25008));
                  }
               } else if (var0 instanceof L1PcInstance) {
                  L1PcInstance var72 = (L1PcInstance)var0;
                  var72.bV(-1);
                  var72.bJ(-50);
                  var72.d(-5);
               }
            } else if (var0 instanceof L1PcInstance) {
               L1PcInstance var71 = (L1PcInstance)var0;
               var71.bN(-1);
               var71.cm(-5);
               var71.ck(-3);
            }
         } else if (var0 instanceof L1PcInstance) {
            L1PcInstance var70 = (L1PcInstance)var0;
            var70.bR(-1);
            var70.cn(-5);
            var70.cl(-3);
         }

         if (var0 instanceof L1PcInstance) {
            L1PcInstance var105 = (L1PcInstance)var0;
            var105.a(new S_OwnCharStatus(var105));
            var105.a(new S_ProtoBuffers(485, var105));
            var105.a(new S_SPMR(var105));
         }
      }
   }
}
