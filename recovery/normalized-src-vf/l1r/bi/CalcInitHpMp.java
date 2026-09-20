package l1r.bi;

import l1r.ap.L1PcInstance;

public class CalcInitHpMp {
   private CalcInitHpMp() {
   }

   public static int a(L1PcInstance var0) {
      int var1 = 1;
      if (var0.x()) {
         var1 = 14;
      } else if (var0.z()) {
         var1 = 16;
      } else if (var0.A()) {
         var1 = 15;
      } else if (var0.B()) {
         var1 = 12;
      } else if (var0.C()) {
         var1 = 12;
      } else if (var0.D()) {
         var1 = 16;
      } else if (var0.E()) {
         var1 = 14;
      } else if (var0.F()) {
         var1 = 16;
      }

      return var1;
   }

   public static int b(L1PcInstance var0) {
      int var1 = 1;
      if (var0.x()) {
         var1 = 2;
      } else if (var0.z()) {
         var1 = 1;
      } else if (var0.A()) {
         var1 = 4;
      } else if (var0.B()) {
         var1 = 6;
      } else if (var0.C()) {
         var1 = 3;
      } else if (var0.D()) {
         var1 = 2;
      } else if (var0.E()) {
         var1 = 5;
      } else if (var0.F()) {
         var1 = 1;
      }

      return var1;
   }

   public static int c(L1PcInstance var0) {
      int var1 = 1;
      if (var0.x()) {
         switch (var0.eE()) {
            case 11:
               var1 = 2;
               break;
            case 12:
            case 13:
            case 14:
            case 15:
               var1 = 3;
               break;
            case 16:
            case 17:
            case 18:
               var1 = 4;
               break;
            default:
               var1 = 2;
         }
      } else if (var0.z() || var0.F()) {
         switch (var0.eE()) {
            case 9:
            case 10:
            case 11:
               var1 = 1;
               break;
            case 12:
            case 13:
               var1 = 2;
               break;
            default:
               var1 = 1;
         }
      } else if (var0.A()) {
         switch (var0.eE()) {
            case 12:
            case 13:
            case 14:
            case 15:
               var1 = 4;
               break;
            case 16:
            case 17:
            case 18:
               var1 = 6;
               break;
            default:
               var1 = 4;
         }
      } else if (var0.B()) {
         switch (var0.eE()) {
            case 12:
            case 13:
            case 14:
            case 15:
               var1 = 6;
               break;
            case 16:
            case 17:
            case 18:
               var1 = 8;
               break;
            default:
               var1 = 6;
         }
      } else if (var0.C()) {
         switch (var0.eE()) {
            case 10:
            case 11:
               var1 = 3;
               break;
            case 12:
            case 13:
            case 14:
            case 15:
               var1 = 4;
               break;
            case 16:
            case 17:
            case 18:
               var1 = 6;
               break;
            default:
               var1 = 3;
         }
      } else if (var0.D()) {
         var1 = 2;
      } else if (var0.E()) {
         switch (var0.eE()) {
            case 12:
            case 13:
            case 14:
            case 15:
               var1 = 5;
               break;
            case 16:
            case 17:
            case 18:
               var1 = 6;
               break;
            default:
               var1 = 5;
         }
      }

      return var1;
   }
}
