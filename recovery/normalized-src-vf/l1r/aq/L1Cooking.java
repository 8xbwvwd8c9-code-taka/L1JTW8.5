package l1r.aq;

import l1r.ap.L1ItemInstance;
import l1r.ap.L1PcInstance;
import l1r.be.S_HPUpdate;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_PacketBox;
import l1r.be.S_SPMR;
import l1r.be.S_ServerMessage;
import l1r.be.S_SkillSound;

public class L1Cooking {
   public static void a(L1PcInstance var0, L1ItemInstance var1) {
      int var2 = var1.N();
      if ((var2 == 41284 || var2 == 41292 || var2 == 49056 || var2 == 49064 || var2 == 49251 || var2 == 49259) && var0.fj() != 225) {
         var0.a(new S_ServerMessage(74, var1.c()));
      } else {
         int var3 = var1.a().V();
         if (var2 == 640712 || var2 == 640713 || var2 == 640714) {
            var0.a(new S_SkillSound(var0.fr(), var3 + 11782));
            var0.b(new S_SkillSound(var0.fr(), var3 + 11782));
         } else if (var2 == 640715) {
            var0.a(new S_SkillSound(var0.fr(), 14847));
            var0.b(new S_SkillSound(var0.fr(), 14847));
         }

         a(var0, var3, 900);
         var0.a(new S_ServerMessage(76, var1.c()));
         var0.j().b(var1, 1);
      }
   }

   public static void a(L1PcInstance var0, int var1, int var2) {
      if (!var0.bB(var1)) {
         if (var1 == 3000 || var1 == 3008) {
            var0.bY(10);
            var0.bZ(10);
            var0.ca(10);
            var0.cb(10);
         } else if (var1 == 3001 || var1 == 3009) {
            var0.bH(30);
            if (var0.q()) {
               var0.aL().f(var0);
            }
         } else if (var1 == 3002 || var1 == 3010) {
            var0.d(3);
         } else if (var1 == 3003 || var1 == 3011) {
            var0.bL(-1);
         } else if (var1 == 3004 || var1 == 3012) {
            var0.bJ(20);
         } else if (var1 == 3005 || var1 == 3013) {
            var0.c(3);
         } else if (var1 != 3006 && var1 != 3014) {
            if (var1 != 3007 && var1 != 3015) {
               if (var1 == 3016 || var1 == 3024) {
                  var0.ck(1);
                  var0.cm(1);
               } else if (var1 == 3017 || var1 == 3025) {
                  var0.bH(30);
                  if (var0.q()) {
                     var0.aL().f(var0);
                  }

                  var0.bJ(30);
               } else if (var1 == 3018 || var1 == 3026) {
                  var0.bL(-2);
               } else if (var1 == 3019 || var1 == 3027) {
                  var0.cl(1);
                  var0.cn(1);
               } else if (var1 == 3020 || var1 == 3028) {
                  var0.c(2);
                  var0.d(2);
               } else if (var1 == 3021 || var1 == 3029) {
                  var0.co(10);
               } else if (var1 != 3022 && var1 != 3030) {
                  if (var1 != 3023 && var1 != 3031) {
                     if (var1 == 3032 || var1 == 3040) {
                        var0.cl(1);
                        var0.cn(2);
                     } else if (var1 == 3033 || var1 == 3041) {
                        var0.bH(50);
                        if (var0.q()) {
                           var0.aL().f(var0);
                        }

                        var0.bJ(50);
                     } else if (var1 == 3034 || var1 == 3042) {
                        var0.ck(1);
                        var0.cm(2);
                     } else if (var1 == 3035 || var1 == 3043) {
                        var0.bL(-3);
                     } else if (var1 == 3036 || var1 == 3044) {
                        var0.co(15);
                        var0.bY(10);
                        var0.bZ(10);
                        var0.ca(10);
                        var0.cb(10);
                     } else if (var1 == 3037 || var1 == 3045) {
                        var0.cp(2);
                        var0.d(2);
                     } else if (var1 == 3038 || var1 == 3046) {
                        var0.bH(30);
                        var0.c(2);
                        var0.a(new S_HPUpdate(var0.ea(), var0.ew()));
                        if (var0.q()) {
                           var0.aL().f(var0);
                        }
                     } else if (var1 != 3039 && var1 != 3047) {
                        if (var1 == 3048) {
                           var0.c(10);
                           var0.d(2);
                        } else if (var1 == 3049) {
                           var2 = 1800;
                           var0.ck(2);
                           var0.cm(1);
                           var0.co(10);
                           var0.bY(10);
                           var0.bZ(10);
                           var0.ca(10);
                           var0.cb(10);
                           var0.c(2);
                           var0.d(2);
                           var0.F(2);
                        } else if (var1 == 3050) {
                           var2 = 1800;
                           var0.cl(2);
                           var0.cn(1);
                           var0.co(10);
                           var0.bY(10);
                           var0.bZ(10);
                           var0.ca(10);
                           var0.cb(10);
                           var0.c(2);
                           var0.d(2);
                           var0.F(2);
                        } else if (var1 == 3051) {
                           var2 = 1800;
                           var0.cp(2);
                           var0.co(10);
                           var0.bY(10);
                           var0.bZ(10);
                           var0.ca(10);
                           var0.cb(10);
                           var0.c(2);
                           var0.d(3);
                           var0.F(2);
                        } else if (var1 == 3052) {
                           var2 = 1800;
                           var0.F(2);
                        } else if (var1 == 3053) {
                           var2 = 1200;
                           var0.ck(3);
                           var0.F(3);
                           var0.co(10);
                        } else if (var1 == 3054) {
                           var2 = 1200;
                           var0.cl(3);
                           var0.F(3);
                           var0.co(10);
                        } else if (var1 == 3055) {
                           var2 = 1200;
                           var0.cp(3);
                           var0.F(3);
                           var0.co(10);
                        } else if (var1 == 3056) {
                           var2 = 1200;
                           var0.F(3);
                        }
                     }
                  }
               } else {
                  var0.cp(1);
               }
            }
         } else {
            var0.co(5);
         }

         var0.a(new S_SPMR(var0));
         var0.a(new S_OwnCharStatus(var0));
      }

      var0.a(new S_PacketBox(53, var0, a(var1), var2));
      var0.j(var1, var2 * 1000);
   }

   public static void a(L1PcInstance var0, int var1) {
      if (var1 == 3000 || var1 == 3008) {
         var0.bY(-10);
         var0.bZ(-10);
         var0.ca(-10);
         var0.cb(-10);
      } else if (var1 == 3001 || var1 == 3009) {
         var0.bH(-30);
         if (var0.q()) {
            var0.aL().f(var0);
         }
      } else if (var1 == 3002 || var1 == 3010) {
         var0.d(-3);
      } else if (var1 == 3003 || var1 == 3011) {
         var0.bL(1);
      } else if (var1 == 3004 || var1 == 3012) {
         var0.bJ(-20);
      } else if (var1 == 3005 || var1 == 3013) {
         var0.c(-3);
      } else if (var1 == 3006 || var1 == 3014) {
         var0.co(-5);
      } else if (var1 != 3007 && var1 != 3015) {
         if (var1 == 3016 || var1 == 3024) {
            var0.ck(-1);
            var0.cm(-1);
         } else if (var1 == 3017 || var1 == 3025) {
            var0.bH(-30);
            var0.bJ(-30);
            if (var0.q()) {
               var0.aL().f(var0);
            }
         } else if (var1 == 3018 || var1 == 3026) {
            var0.bL(2);
         } else if (var1 == 3019 || var1 == 3027) {
            var0.cl(-1);
            var0.cn(-1);
         } else if (var1 == 3020 || var1 == 3028) {
            var0.c(-2);
            var0.d(-2);
         } else if (var1 == 3021 || var1 == 3029) {
            var0.co(-10);
         } else if (var1 == 3022 || var1 == 3030) {
            var0.cp(-1);
         } else if (var1 != 3023 && var1 != 3031) {
            if (var1 == 3032 || var1 == 3040) {
               var0.cl(-1);
               var0.cn(-2);
            } else if (var1 == 3033 || var1 == 3041) {
               var0.bH(-50);
               var0.bJ(-50);
               if (var0.q()) {
                  var0.aL().f(var0);
               }
            } else if (var1 == 3034 || var1 == 3042) {
               var0.ck(-1);
               var0.cm(-2);
            } else if (var1 == 3035 || var1 == 3043) {
               var0.bL(3);
            } else if (var1 == 3036 || var1 == 3044) {
               var0.co(-15);
               var0.bY(-10);
               var0.bZ(-10);
               var0.ca(-10);
               var0.cb(-10);
            } else if (var1 == 3037 || var1 == 3045) {
               var0.cp(-2);
               var0.d(-2);
            } else if (var1 == 3038 || var1 == 3046) {
               var0.bH(-30);
               var0.c(-2);
               if (var0.q()) {
                  var0.aL().f(var0);
               }
            } else if (var1 != 3039 && var1 != 3047) {
               if (var1 == 3048) {
                  var0.c(-10);
                  var0.d(-2);
               } else if (var1 == 3049) {
                  var0.ck(-2);
                  var0.cm(-1);
                  var0.co(-10);
                  var0.bY(-10);
                  var0.bZ(-10);
                  var0.ca(-10);
                  var0.cb(-10);
                  var0.c(-2);
                  var0.d(-2);
                  var0.F(-2);
               } else if (var1 == 3050) {
                  var0.cl(-2);
                  var0.cn(-1);
                  var0.co(-10);
                  var0.bY(-10);
                  var0.bZ(-10);
                  var0.ca(-10);
                  var0.cb(-10);
                  var0.c(-2);
                  var0.d(-2);
                  var0.F(-2);
               } else if (var1 == 3051) {
                  var0.cp(-2);
                  var0.co(-10);
                  var0.bY(-10);
                  var0.bZ(-10);
                  var0.ca(-10);
                  var0.cb(-10);
                  var0.c(-2);
                  var0.d(-3);
                  var0.F(-2);
               } else if (var1 == 3052) {
                  var0.F(-2);
               } else if (var1 == 3053) {
                  var0.ck(-3);
                  var0.F(-3);
                  var0.co(-10);
               } else if (var1 == 3054) {
                  var0.cl(-3);
                  var0.F(-3);
                  var0.co(-10);
               } else if (var1 == 3055) {
                  var0.cp(-3);
                  var0.F(-3);
                  var0.co(10);
               } else if (var1 == 3056) {
                  var0.F(-3);
               }
            }
         }
      }

      var0.c_(Math.min(var0.fj() + 20, 225));
      var0.a(new S_PacketBox(11, var0.fj()));
      var0.a(new S_PacketBox(53, var0, a(var1), 0));
      var0.a(new S_SPMR(var0));
   }

   private static int a(int var0) {
      int var1 = 0;
      if (var0 >= 3000 && var0 <= 3031) {
         var1 = var0 - 3000;
      } else if (var0 >= 3032 && var0 <= 3047) {
         var1 = var0 - 2995;
      } else if (var0 == 3048) {
         var1 = 54;
      } else if (var0 >= 3049 && var0 <= 3052) {
         var1 = var0 - 2892;
      } else if (var0 >= 3053 && var0 <= 3056) {
         var1 = var0 - 2849;
      }

      return var1;
   }
}
