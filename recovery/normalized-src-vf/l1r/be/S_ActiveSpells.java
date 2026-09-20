package l1r.be;

import java.util.Map;

public class S_ActiveSpells extends ServerBasePacket {
   private final int a = 215;

   public S_ActiveSpells(Map<Integer, Integer> var1) {
      this.c(121);
      this.c(20);
      this.a(215);
      byte[] var2 = this.a(var1);
      this.a(var2);
   }

   private byte[] a(Map<Integer, Integer> var1) {
      byte[] var2 = new byte[215];
      int var3 = (int)(System.currentTimeMillis() / 1000L);
      var2[72] = (byte)(var3 & 0xFF);
      var2[73] = (byte)(var3 >> 8 & 0xFF);
      var2[74] = (byte)(var3 >> 16 & 0xFF);
      var2[75] = (byte)(var3 >> 24 & 0xFF);

      for (int var4 : var1.keySet()) {
         switch (var4) {
            case 14:
               var2[3] = var1.get(var4).byteValue();
               break;
            case 31:
               var2[1] = var1.get(var4).byteValue();
               break;
            case 32:
               var2[0] = var1.get(var4).byteValue();
               break;
            case 47:
               var2[8] = var1.get(var4).byteValue();
               break;
            case 55:
               var2[18] = var1.get(var4).byteValue();
               break;
            case 56:
               var2[9] = var1.get(var4).byteValue();
               break;
            case 64:
            case 202:
               var2[6] = var1.get(var4).byteValue();
               break;
            case 71:
               var2[4] = var1.get(var4).byteValue();
               break;
            case 78:
               var2[5] = var1.get(var4).byteValue();
               break;
            case 89:
               var2[158] = var1.get(var4).byteValue();
               var2[160] = -70;
               break;
            case 104:
               var2[7] = var1.get(var4).byteValue();
               break;
            case 111:
               var2[17] = var1.get(var4).byteValue();
               break;
            case 133:
               var2[24] = var1.get(var4).byteValue();
               var2[25] = 3;
               break;
            case 134:
               var2[22] = var1.get(var4).byteValue();
               break;
            case 153:
               var2[21] = var1.get(var4).byteValue();
               break;
            case 158:
               var2[19] = var1.get(var4).byteValue();
               break;
            case 167:
               var2[20] = var1.get(var4).byteValue();
               break;
            case 171:
               var2[26] = var1.get(var4).byteValue();
               break;
            case 173:
               var2[32] = var1.get(var4).byteValue();
               break;
            case 174:
               var2[30] = var1.get(var4).byteValue();
               break;
            case 175:
               var2[31] = var1.get(var4).byteValue();
               break;
            case 176:
               var2[23] = var1.get(var4).byteValue();
               break;
            case 181:
               var2[60] = var1.get(var4).byteValue();
               break;
            case 183:
               var2[59] = var1.get(var4).byteValue();
               break;
            case 188:
               var2[57] = var1.get(var4).byteValue();
               break;
            case 191:
               var2[55] = var1.get(var4).byteValue();
               break;
            case 193:
               var2[56] = var1.get(var4).byteValue();
               break;
            case 206:
               var2[52] = var1.get(var4).byteValue();
               break;
            case 211:
               var2[58] = var1.get(var4).byteValue();
               break;
            case 216:
               var2[53] = var1.get(var4).byteValue();
               break;
            case 217:
               var2[54] = var1.get(var4).byteValue();
               break;
            case 1017:
               var2[61] = var1.get(var4).byteValue();
               break;
            case 3048:
               var2[42] = var1.get(var4).byteValue();
               var2[43] = 54;
               break;
            case 4001:
            case 4002:
            case 4003:
            case 4004:
            case 4005:
               var2[45] = var1.get(var4).byteValue();
               var2[62] = 50;
               break;
            case 4006:
               var2[48] = var1.get(var4).byteValue();
               var2[49] = 44;
               break;
            case 4007:
               var2[45] = var1.get(var4).byteValue();
               var2[62] = 20;
               break;
            case 4008:
            case 4009:
            case 4010:
               var2[46] = var1.get(var4).byteValue();
               var2[47] = (byte)(var4 - 4008);
               break;
            case 4049:
            case 4050:
            case 4051:
            case 4052:
            case 4053:
            case 4054:
            case 4055:
               var2[78] = var1.get(var4).byteValue();
               var2[79] = (byte)(46 + (var4 - 4049));
               break;
            case 4056:
               var2[76] = var1.get(var4).byteValue();
               var2[77] = 45;
               break;
            case 4057:
               var2[76] = var1.get(var4).byteValue();
               var2[77] = 60;
               break;
            case 4070:
               System.out.println(var1.get(var4));
               var2[45] = var1.get(var4).byteValue();
               var2[106] = 1;
               break;
            case 4079:
               var2[76] = var1.get(var4).byteValue();
               var2[77] = 74;
               break;
            case 4086:
               var2[50] = var1.get(var4).byteValue();
               var2[51] = 64;
               break;
            case 4087:
               var2[50] = var1.get(var4).byteValue();
               var2[51] = 65;
               break;
            case 4088:
               var2[50] = var1.get(var4).byteValue();
               var2[51] = 66;
               break;
            case 4089:
               var2[50] = var1.get(var4).byteValue();
               var2[51] = 67;
               break;
            case 4090:
               var2[50] = var1.get(var4).byteValue();
               var2[51] = 68;
               break;
            case 4091:
               var2[50] = var1.get(var4).byteValue();
               var2[51] = 69;
               break;
            default:
               if (var4 >= 4013 && var4 <= 4048) {
                  var2[102] = var1.get(var4).byteValue();
                  var2[103] = (byte)(var4 - 3929);
               }
         }
      }

      return var2;
   }

   @Override
   public byte[] a() {
      return this.d();
   }

   @Override
   public String b() {
      return "S_ActiveSpells";
   }
}
