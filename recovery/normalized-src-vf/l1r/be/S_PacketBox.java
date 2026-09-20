package l1r.be;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;
import l1r.ap.L1PcInstance;
import l1r.aq.L1Master;
import l1r.aq.L1World;
import l1r.bh.L1Account;

public class S_PacketBox extends ServerBasePacket {
   public static final int a = 0;
   public static final int b = 1;
   public static final int c = 2;
   public static final int d = 3;
   public static final int e = 4;
   public static final int f = 5;
   public static final int g = 6;
   public static final int h = 9;
   public static final int i = 10;
   public static final int j = 11;
   public static final int k = 12;
   public static final int l = 14;
   public static final int m = 15;
   public static final int n = 17;
   public static final int o = 18;
   public static final int p = 19;
   public static final int q = 20;
   public static final int r = 21;
   public static final int s = 22;
   public static final int t = 23;
   public static final int u = 25;
   public static final int v = 27;
   public static final int w = 30;
   public static final int x = 31;
   public static final int y = 33;
   public static final int z = 34;
   public static final int A = 35;
   public static final int B = 36;
   public static final int C = 37;
   public static final int D = 38;
   public static final int E = 40;
   public static final int F = 41;
   public static final int G = 42;
   public static final int H = 43;
   public static final int I = 44;
   public static final int J = 45;
   public static final int K = 49;
   public static final int L = 51;
   public static final int M = 52;
   public static final int N = 53;
   public static final int O = 55;
   public static final int P = 56;
   public static final int Q = 57;
   public static final int R = 59;
   public static final int S = 60;
   public static final int T = 74;
   public static final int U = 75;
   public static final int V = 79;
   public static final int W = 80;
   public static final int X = 82;
   public static final int Y = 83;
   public static final int Z = 84;
   public static final int aa = 86;
   public static final int ab = 87;
   public static final int ac = 88;
   public static final int ad = 100;
   public static final int ae = 101;
   public static final int af = 102;
   public static final int ag = 104;
   public static final int ah = 105;
   public static final int ai = 106;
   public static final int aj = 110;
   public static final int ak = 111;
   public static final int al = 114;
   public static final int am = 117;
   public static final int an = 125;
   public static final int ao = 127;
   public static final int ap = 132;
   public static final int aq = 141;
   public static final int ar = 144;
   public static final int as = 146;
   public static final int at = 147;
   public static final int au = 149;
   public static final int av = 150;
   public static final int aw = 151;
   public static final int ax = 153;
   public static final int ay = 154;
   public static final int az = 156;
   public static final int aA = 159;
   public static final int aB = 160;
   public static final int aC = 161;
   public static final int aD = 166;
   public static final int aE = 167;
   public static final int aF = 168;
   public static final int aG = 169;
   public static final int aH = 170;
   public static final int aI = 171;
   public static final int aJ = 172;
   public static final int aK = 173;
   public static final int aL = 176;
   public static final int aM = 178;
   public static final int aN = 180;
   public static final int aO = 185;
   public static final int aP = 188;
   public static final int aQ = 189;
   public static final int aR = 194;
   public static final int aS = 195;
   public static final int aT = 196;
   public static final int aU = 204;

   public S_PacketBox(byte[] var1) {
      this.a(var1);
   }

   public S_PacketBox(L1PcInstance var1, int var2) {
      this.c(121);
      this.c(var2);
      this.b(1800);
      this.b(3600);
      this.a(0);
   }

   public S_PacketBox(int var1, int var2, int var3, int var4) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 86:
            if (var2 == 173) {
               this.c(var2);
               this.c(var3);
               this.a(var4);
               return;
            }

            if (var2 == 62) {
               this.c(var2);
               this.c(var3);
               this.b(var4);
               this.b(34334);
               return;
            }

            if (var2 == 76) {
               this.c(var2);
               this.c(var3);
               this.c(var4);
               return;
            }

            if (var2 == 3) {
               this.c(var2);
               this.c(var4);
               this.c(var3);
               return;
            }

            this.c(var2);
            this.c(1);
            this.c(var3);
            this.b(var4);
            this.b(0);
            break;
         case 180:
            this.c(var4);
            this.a(var2);
            this.a(var3);
            this.b(0);
      }
   }

   public S_PacketBox(int var1) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 3:
         case 4:
         case 9:
         case 31:
         case 42:
         case 43:
         case 125:
         case 127:
         default:
            break;
         case 45:
            this.e();
            break;
         case 59:
            this.b(0);
            break;
         case 178:
            this.c(1);
            this.a(12);
            this.a(-1);
            this.a(-1);
            this.a(-1);
            this.b(0);
      }
   }

   public S_PacketBox(int var1, byte[] var2) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 188:
            this.a(var2);
      }
   }

   public S_PacketBox(int var1, L1PcInstance var2, int var3, int var4) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 53:
            this.c(var2.ez());
            this.c(var2.eD());
            this.c(var2.eE());
            this.c(var2.eB());
            this.c(var2.eA());
            this.c(var2.eC());
            this.b(var2.fj() * 9);
            this.c(var3);
            this.c(36);
            this.b(var4);
            this.c(var2.j().h());
      }
   }

   public S_PacketBox(int var1, L1PcInstance var2) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 176:
            this.c(1);
            this.b(var2.fs());
            this.b(var2.ft());
            break;
         case 204:
            this.b(var2.dW());
      }
   }

   public S_PacketBox(int var1, int var2) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 0:
         case 1:
         case 2:
            this.c(var2);
            this.b(0);
            break;
         case 6:
         case 10:
         case 11:
         case 74:
         case 141:
         case 144:
            this.c(var2);
            break;
         case 12:
            this.c(0);
            this.c(var2);
            break;
         case 15:
         case 49:
            this.c(var2);
            this.c(0);
            break;
         case 21:
            this.c(0);
            this.c(0);
            this.c(0);
            this.c(var2);
            break;
         case 34:
            this.b(var2);
            this.c(1);
            break;
         case 35:
            this.b(var2);
            this.c(0);
            break;
         case 36:
         case 40:
         case 56:
         case 75:
         case 132:
            this.b(var2);
            break;
         case 52:
            this.c(219);
            this.c(49);
            this.c(223);
            this.c(2);
            this.c(1);
            this.c(var2);
            break;
         case 55:
            this.a(0);
            this.b(var2);
            this.b(0);
            break;
         case 57:
            this.c(44);
            this.b(var2);
            break;
         case 60:
            this.c(var2 / 4);
            this.c(8);
            break;
         case 83:
            this.a(var2);
            break;
         case 88:
            this.c(var2);
            this.c(0);
            break;
         case 101:
            this.c(var2);
            break;
         case 146:
            CopyOnWriteArrayList var3 = L1Master.a().b(var2);
            if (var3 != null) {
               this.c(var3.size());

               for (L1PcInstance var4 : var3) {
                  this.a(var4.et());
                  this.c(var4.ev());
                  this.c(var4.ay());
               }
            }

            this.a(L1Master.a().a(var2));
            break;
         case 151:
            this.c(var2);
            this.a(0);
            break;
         case 153:
            this.b(var2);
            this.b(0);
            break;
         case 173:
            this.c(1);
            this.c(var2);
            break;
         case 185:
            this.a(var2);
            this.b(4);
            this.a("gold deathknight");
            this.a("lightning deathknight");
            this.a("fire deathknight");
            this.a("dark deathknight");
            this.a(0);
            this.b(0);
            this.c(0);
            break;
         case 195:
            this.a(var2);
            this.a((int)(new Date().getTime() / 1000L));
            break;
         case 196:
            this.a(0);
            this.b(var2);
            this.b(0);
      }
   }

   public S_PacketBox(int var1, int var2, int var3) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 5:
            this.a(var2);
            this.a(var3);
            break;
         case 44:
            this.a(var2);
            this.b(var3 / 4);
            break;
         case 56:
            if (var2 == 32) {
               this.b(var3);
               this.c(var2);
               this.c(12);
            } else {
               this.b(var3);
               this.c(0);
               this.c(0);
            }
            break;
         case 82:
            this.a(var2);
            this.a(var3);
            this.a(0);
            break;
         case 86:
            if (var2 == 92) {
               this.c(var2);
               this.c(var3);
               this.b(0);
               return;
            }

            this.c(var2);
            this.c(1);
            this.c(var3);
            this.b(0);
            break;
         case 100:
            this.c(var2);
            this.a(var3 / 60);
            break;
         case 147:
            this.c(var3);
            this.c(var2);
            break;
         case 150:
            this.c(1);
            this.c(var2);
            this.a(var3);
            this.b(0);
            break;
         case 154:
            this.b(var3);
            this.b(var2);
            this.a(0);
            break;
         case 156:
            this.a(var2);
            this.a(var3);
            this.b(0);
            break;
         case 160:
            this.c(var2);
            this.b(var3);
            break;
         case 161:
            this.c(var2);
            if (var2 == 2) {
               this.b(0);
               this.c(var3);
            } else {
               this.b(var3);
               this.c(var3 > 0 ? 1 : 0);
            }
      }
   }

   public S_PacketBox(int var1, String var2) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 23:
         case 168:
            this.a(var2);
      }
   }

   public S_PacketBox(int var1, int var2, String var3) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 18:
         case 19:
            this.a(var3);
            this.c(var2);
            break;
         case 27:
            this.c(var2);
            this.a(var3);
            break;
         case 84:
            this.c(var2);
            this.a(var3);
      }
   }

   public S_PacketBox(int var1, String[] var2, int var3) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 17:
            this.c(var3);
            this.c(var3);
            this.c(var2.length);
            String[] var7 = var2;
            int var6 = var2.length;

            for (int var5 = 0; var5 < var6; var5++) {
               String var4 = var7[var5];
               this.a(var4);
            }

            this.b(0);
      }
   }

   public S_PacketBox(int var1, Object[] var2) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 79:
            this.c(var2.length);
            Object[] var15 = var2;
            int var13 = var2.length;

            for (int var11 = 0; var11 < var13; var11++) {
               Object var9 = var15[var11];
               this.a(var9.toString());
            }
            break;
         case 80:
            this.c(var2.length);
            Object[] var14 = var2;
            int var12 = var2.length;

            for (int var10 = 0; var10 < var12; var10++) {
               Object var8 = var14[var10];
               this.a(var8.toString());
            }
            break;
         case 171:
            this.b(var2.length);
            Object[] var6 = var2;
            int var5 = var2.length;

            for (int var4 = 0; var4 < var5; var4++) {
               Object var3 = var6[var4];
               L1PcInstance var7 = (L1PcInstance)var3;
               this.a(var7.et());
               this.c(1);
            }
      }
   }

   public S_PacketBox(int var1, String[] var2, int[] var3) {
      this.c(121);
      this.c(var1);
      switch (var1) {
         case 159:
            this.a(var2.length);

            for (int var4 = 0; var4 < var2.length; var4++) {
               int var5 = var4 + 1;
               this.a(var5);
               this.a(var2[var4]);
               this.a(var3[var4] / 60);
            }

            this.b(0);
      }
   }

   private void e() {
      this.c(L1World.a().c().size());

      for (L1PcInstance var1 : L1World.a().c()) {
         if (var1.aK() == null) {
            this.a(0);
            this.a(var1.et());
            this.a("socket null");
         } else {
            L1Account var3 = var1.aK().e();
            if (var3 == null) {
               this.a(0);
               this.a(var1.et());
               this.a("account null");
            } else {
               Calendar var4 = Calendar.getInstance();
               var4.setTime(var3.g());
               this.a((int)(var4.getTimeInMillis() / 1000L));
               this.a(var1.et());
               String var5 = "\\aA安全區";
               if (var1.fp() == 5300 || var1.fp() == 5301 || var1.fp() == 5490) {
                  var5 = "\\aJ釣魚池";
               } else if (var1.fu().e()) {
                  var5 = "\\aL一般區";
               } else if (var1.fu().d()) {
                  var5 = "\\aH戰鬥區";
               }

               this.a(var1.ev() + "-" + var5);
            }
         }
      }
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_PacketBox";
   }
}
