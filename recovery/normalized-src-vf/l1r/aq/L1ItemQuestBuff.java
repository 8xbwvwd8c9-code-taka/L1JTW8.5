package l1r.aq;

import java.sql.Timestamp;
import l1r.ap.L1PcInstance;
import l1r.be.S_HPUpdate;
import l1r.be.S_MPUpdate;
import l1r.be.S_OwnCharAttrDef;
import l1r.be.S_OwnCharStatus;
import l1r.be.S_OwnCharStatus2;
import l1r.be.S_PacketBox;
import l1r.be.S_ProtoBuffers;
import l1r.be.S_SPMR;

public class L1ItemQuestBuff {
   public static void a(L1PcInstance var0, int var1) {
      if (!var0.bB(var1)) {
         switch (var1) {
            case 4072:
               var0.bH(50);
               var0.ck(2);
               var0.c(3);
               var0.bN(1);
               break;
            case 4073:
               var0.bH(25);
               var0.cl(2);
               var0.bJ(25);
               var0.c(1);
               var0.d(1);
               var0.bR(1);
               break;
            case 4074:
               var0.bJ(50);
               var0.d(3);
               var0.bV(1);
               var0.cp(2);
               var0.a(new S_SPMR(var0));
               break;
            case 4075:
               var0.bH(30);
               var0.bJ(30);
               var0.bL(-5);
               var0.co(10);
               var0.F(1);
               var0.a(new S_SPMR(var0));
               var0.a(new S_OwnCharAttrDef(var0));
         }

         if (var0.q()) {
            var0.aL().f(var0);
         }

         var0.a(new S_OwnCharStatus(var0));
      }

      var0.j(var1, 600000);
   }

   public static void a(L1PcInstance var0, int var1, int var2) {
      int var3 = var1 - 3929;
      if (!var0.bB(var1)) {
         switch (var1) {
            case 4013:
               var0.bH(10);
               break;
            case 4014:
               var0.bH(20);
               break;
            case 4015:
               var0.bH(30);
               break;
            case 4016:
               var0.bH(40);
               break;
            case 4017:
               var0.bH(50);
               var0.c(1);
               break;
            case 4018:
               var0.bH(60);
               var0.c(2);
               break;
            case 4019:
               var0.bH(70);
               var0.c(3);
               break;
            case 4020:
               var0.bH(80);
               var0.c(4);
               var0.cm(1);
               break;
            case 4021:
               var0.bH(100);
               var0.c(5);
               var0.cm(2);
               var0.ck(2);
               var0.bN(1);
               var0.a(new S_OwnCharStatus2(var0));
               break;
            case 4022:
               var0.bH(5);
               var0.bJ(3);
               break;
            case 4023:
               var0.bH(10);
               var0.bJ(6);
               break;
            case 4024:
               var0.bH(15);
               var0.bJ(10);
               break;
            case 4025:
               var0.bH(20);
               var0.bJ(15);
               break;
            case 4026:
               var0.bH(25);
               var0.bJ(20);
               break;
            case 4027:
               var0.bH(30);
               var0.bJ(20);
               var0.c(1);
               break;
            case 4028:
               var0.bH(35);
               var0.bJ(20);
               var0.c(1);
               var0.d(1);
               break;
            case 4029:
               var0.bH(40);
               var0.bJ(25);
               var0.c(2);
               var0.d(1);
               break;
            case 4030:
               var0.bH(50);
               var0.bJ(30);
               var0.c(2);
               var0.d(2);
               var0.cl(2);
               var0.cn(2);
               var0.bR(1);
               var0.a(new S_OwnCharStatus2(var0));
               break;
            case 4031:
               var0.bJ(5);
               break;
            case 4032:
               var0.bJ(10);
               break;
            case 4033:
               var0.bJ(15);
               break;
            case 4034:
               var0.bJ(20);
               break;
            case 4035:
               var0.bJ(25);
               var0.d(1);
               break;
            case 4036:
               var0.bJ(30);
               var0.d(2);
               break;
            case 4037:
               var0.bJ(35);
               var0.d(3);
               break;
            case 4038:
               var0.bJ(40);
               var0.d(4);
               break;
            case 4039:
               var0.bJ(50);
               var0.d(5);
               var0.bV(1);
               var0.cp(1);
               var0.a(new S_SPMR(var0));
               var0.a(new S_OwnCharStatus2(var0));
               break;
            case 4040:
               var0.co(2);
               break;
            case 4041:
               var0.co(4);
               break;
            case 4042:
               var0.co(6);
               break;
            case 4043:
               var0.co(8);
               break;
            case 4044:
               var0.co(10);
               var0.bL(-1);
               break;
            case 4045:
               var0.co(10);
               var0.bL(-2);
               break;
            case 4046:
               var0.co(10);
               var0.bL(-3);
               break;
            case 4047:
               var0.co(15);
               var0.bL(-4);
               var0.F(1);
               break;
            case 4048:
               var0.co(20);
               var0.bL(-5);
               var0.bP(1);
               var0.F(3);
         }

         if (var3 >= 84 && var3 <= 92) {
            var0.a(new S_HPUpdate(var0.ea(), var0.ew()));
            if (var0.q()) {
               var0.aL().f(var0);
            }
         } else if (var3 >= 93 && var3 <= 101) {
            var0.a(new S_HPUpdate(var0.ea(), var0.ew()));
            var0.a(new S_MPUpdate(var0.eb(), var0.ex()));
            if (var0.q()) {
               var0.aL().f(var0);
            }
         } else if (var3 >= 102 && var3 <= 110) {
            var0.a(new S_MPUpdate(var0.eb(), var0.ex()));
         } else if (var3 >= 111 && var3 <= 119) {
            var0.a(new S_SPMR(var0));
            var0.a(new S_OwnCharAttrDef(var0));
            var0.a(new S_OwnCharStatus2(var0));
         }
      }

      var0.j(var1, var2 * 1000);
   }

   public static void b(L1PcInstance var0, int var1, int var2) {
      if (!var0.bB(var1)) {
         switch (var1) {
            case 4006:
               var0.cm(3);
               var0.ck(3);
               var0.d(2);
               break;
            case 4008:
               var0.bH(50);
               var0.c(4);
               var0.a(new S_HPUpdate(var0.ea(), var0.ew()));
               if (var0.q()) {
                  var0.aL().f(var0);
               }
               break;
            case 4009:
               var0.bJ(40);
               var0.d(4);
               var0.a(new S_MPUpdate(var0.eb(), var0.ex()));
               break;
            case 4010:
               var0.cm(3);
               var0.ck(3);
               var0.cn(3);
               var0.cl(3);
               var0.cp(3);
               break;
            case 4049:
               var0.ce(3);
               var0.cA(1);
               var0.a(new S_PacketBox(88, var0.fk()));
               break;
            case 4050:
               var0.cg(3);
               break;
            case 4051:
               var0.cf(3);
               break;
            case 4052:
               var0.cd(3);
               var0.ck(2);
               break;
            case 4053:
               var0.ci(3);
               var0.cA(1);
               var0.a(new S_PacketBox(88, var0.fk()));
               break;
            case 4054:
               var0.ch(3);
               var0.cA(1);
               var0.a(new S_PacketBox(88, var0.fk()));
               break;
            case 4055:
               var0.ck(2);
               var0.cA(1);
               var0.a(new S_PacketBox(88, var0.fk()));
               break;
            case 4067:
               var0.cm(30);
               var0.ck(30);
               var0.cn(30);
               var0.cl(30);
               var0.cp(30);
               var0.a(new S_ProtoBuffers(var1, var2, 0, 5985, 0, 4067, 4067, 4068, 5));
               break;
            case 4068:
               var0.bL(-50);
               var0.a(new S_ProtoBuffers(var1, var2, 0, 5984, 0, 4080, 4080, 4081, 5));
               break;
            case 4081:
               var0.bL(-2);
               var0.ck(4);
               var0.F(1);
               break;
            case 4082:
               var0.bL(-2);
               var0.cl(4);
               var0.F(1);
               break;
            case 4083:
               var0.bL(-2);
               var0.cp(3);
               var0.F(1);
               break;
            case 4086:
               var0.bR(1);
               var0.cn(5);
               var0.cl(3);
               break;
            case 4087:
               var0.bR(1);
               var0.cn(5);
               var0.cl(3);
               break;
            case 4088:
               var0.bN(1);
               var0.cm(5);
               var0.ck(3);
               break;
            case 4089:
               var0.bN(1);
               var0.cm(5);
               var0.ck(3);
               break;
            case 4090:
               var0.bV(1);
               var0.bJ(50);
               var0.d(5);
               break;
            case 4091:
               var0.bV(1);
               var0.bJ(50);
               var0.d(5);
         }

         var0.a(new S_OwnCharStatus(var0));
         var0.a(new S_SPMR(var0));
      }

      var0.j(var1, var2 * 1000);
   }

   public static void a(L1PcInstance var0, int var1, int var2, Timestamp var3) {
      if (var0.bB(var1)) {
         var0.bz(var1);
      }

      switch (var1) {
         case 4011:
            var0.bL(-2);
            var0.bZ(50);
            var0.a(new S_PacketBox(100, 82, var2));
            break;
         case 4012:
            var0.c(3);
            var0.d(1);
            var0.bY(50);
            var0.a(new S_PacketBox(100, 85, var2));
            break;
         case 4056:
            var0.bH(100);
            var0.bJ(50);
            var0.c(3);
            var0.d(3);
            var0.cb(30);
            var0.ck(1);
            var0.cm(5);
            var0.C(40);
            break;
         case 4057:
            var0.bH(80);
            var0.bJ(10);
            var0.bZ(30);
            var0.bL(-8);
            break;
         case 4077:
            var0.cp(1);
            var0.co(1);
            var0.ca(50);
            var0.a(new S_PacketBox(100, 88, var2));
            break;
         case 4079:
            var0.bH(100);
            var0.bJ(50);
            var0.c(3);
            var0.d(3);
            var0.bY(30);
            var0.ck(1);
            var0.cm(5);
            var0.C(40);
      }

      var0.a(new S_HPUpdate(var0.ea(), var0.ew()));
      if (var0.q()) {
         var0.aL().f(var0);
      }

      var0.a(new S_MPUpdate(var0.eb(), var0.ex()));
      var0.a(new S_OwnCharStatus2(var0));
      var0.a(new S_OwnCharAttrDef(var0));
      if (var3 != null) {
         var0.a(var1, var2 * 1000, var3);
      } else {
         var0.j(var1, var2 * 1000);
      }
   }
}
