/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import java.util.Map;

public class b
extends eu {
    private final int a = 215;

    public b(Map<Integer, Integer> buffList) {
        this.c(121);
        this.c(20);
        this.a(215);
        byte[] data = this.a(buffList);
        this.a(data);
    }

    private byte[] a(Map<Integer, Integer> iconList) {
        byte[] data = new byte[215];
        int time = (int)(System.currentTimeMillis() / 1000L);
        data[72] = (byte)(time & 0xFF);
        data[73] = (byte)(time >> 8 & 0xFF);
        data[74] = (byte)(time >> 16 & 0xFF);
        data[75] = (byte)(time >> 24 & 0xFF);
        block50: for (int skillid : iconList.keySet()) {
            switch (skillid) {
                case 32: {
                    data[0] = iconList.get(skillid).byteValue();
                    break;
                }
                case 31: {
                    data[1] = iconList.get(skillid).byteValue();
                    break;
                }
                case 14: {
                    data[3] = iconList.get(skillid).byteValue();
                    break;
                }
                case 71: {
                    data[4] = iconList.get(skillid).byteValue();
                    break;
                }
                case 78: {
                    data[5] = iconList.get(skillid).byteValue();
                    break;
                }
                case 64: 
                case 202: {
                    data[6] = iconList.get(skillid).byteValue();
                    break;
                }
                case 104: {
                    data[7] = iconList.get(skillid).byteValue();
                    break;
                }
                case 47: {
                    data[8] = iconList.get(skillid).byteValue();
                    break;
                }
                case 56: {
                    data[9] = iconList.get(skillid).byteValue();
                    break;
                }
                case 111: {
                    data[17] = iconList.get(skillid).byteValue();
                    break;
                }
                case 55: {
                    data[18] = iconList.get(skillid).byteValue();
                    break;
                }
                case 158: {
                    data[19] = iconList.get(skillid).byteValue();
                    break;
                }
                case 167: {
                    data[20] = iconList.get(skillid).byteValue();
                    break;
                }
                case 153: {
                    data[21] = iconList.get(skillid).byteValue();
                    break;
                }
                case 134: {
                    data[22] = iconList.get(skillid).byteValue();
                    break;
                }
                case 176: {
                    data[23] = iconList.get(skillid).byteValue();
                    break;
                }
                case 133: {
                    data[24] = iconList.get(skillid).byteValue();
                    data[25] = 3;
                    break;
                }
                case 171: {
                    data[26] = iconList.get(skillid).byteValue();
                    break;
                }
                case 174: {
                    data[30] = iconList.get(skillid).byteValue();
                    break;
                }
                case 175: {
                    data[31] = iconList.get(skillid).byteValue();
                    break;
                }
                case 173: {
                    data[32] = iconList.get(skillid).byteValue();
                    break;
                }
                case 4086: {
                    data[50] = iconList.get(skillid).byteValue();
                    data[51] = 64;
                    break;
                }
                case 4087: {
                    data[50] = iconList.get(skillid).byteValue();
                    data[51] = 65;
                    break;
                }
                case 4088: {
                    data[50] = iconList.get(skillid).byteValue();
                    data[51] = 66;
                    break;
                }
                case 4089: {
                    data[50] = iconList.get(skillid).byteValue();
                    data[51] = 67;
                    break;
                }
                case 4090: {
                    data[50] = iconList.get(skillid).byteValue();
                    data[51] = 68;
                    break;
                }
                case 4091: {
                    data[50] = iconList.get(skillid).byteValue();
                    data[51] = 69;
                    break;
                }
                case 206: {
                    data[52] = iconList.get(skillid).byteValue();
                    break;
                }
                case 216: {
                    data[53] = iconList.get(skillid).byteValue();
                    break;
                }
                case 217: {
                    data[54] = iconList.get(skillid).byteValue();
                    break;
                }
                case 191: {
                    data[55] = iconList.get(skillid).byteValue();
                    break;
                }
                case 193: {
                    data[56] = iconList.get(skillid).byteValue();
                    break;
                }
                case 188: {
                    data[57] = iconList.get(skillid).byteValue();
                    break;
                }
                case 211: {
                    data[58] = iconList.get(skillid).byteValue();
                    break;
                }
                case 183: {
                    data[59] = iconList.get(skillid).byteValue();
                    break;
                }
                case 181: {
                    data[60] = iconList.get(skillid).byteValue();
                    break;
                }
                case 1017: {
                    data[61] = iconList.get(skillid).byteValue();
                    break;
                }
                case 3048: {
                    data[42] = iconList.get(skillid).byteValue();
                    data[43] = 54;
                    break;
                }
                case 4070: {
                    System.out.println(iconList.get(skillid));
                    data[45] = iconList.get(skillid).byteValue();
                    data[106] = 1;
                    break;
                }
                case 4007: {
                    data[45] = iconList.get(skillid).byteValue();
                    data[62] = 20;
                    break;
                }
                case 4001: 
                case 4002: 
                case 4003: 
                case 4004: 
                case 4005: {
                    data[45] = iconList.get(skillid).byteValue();
                    data[62] = 50;
                    break;
                }
                case 4006: {
                    data[48] = iconList.get(skillid).byteValue();
                    data[49] = 44;
                    break;
                }
                case 4008: 
                case 4009: 
                case 4010: {
                    data[46] = iconList.get(skillid).byteValue();
                    data[47] = (byte)(skillid - 4008);
                    break;
                }
                case 4049: 
                case 4050: 
                case 4051: 
                case 4052: 
                case 4053: 
                case 4054: 
                case 4055: {
                    data[78] = iconList.get(skillid).byteValue();
                    data[79] = (byte)(46 + (skillid - 4049));
                    break;
                }
                case 4056: {
                    data[76] = iconList.get(skillid).byteValue();
                    data[77] = 45;
                    break;
                }
                case 4057: {
                    data[76] = iconList.get(skillid).byteValue();
                    data[77] = 60;
                    break;
                }
                case 4079: {
                    data[76] = iconList.get(skillid).byteValue();
                    data[77] = 74;
                    break;
                }
                case 89: {
                    data[158] = iconList.get(skillid).byteValue();
                    data[160] = -70;
                    break;
                }
                default: {
                    if (skillid < 4013 || skillid > 4048) continue block50;
                    data[102] = iconList.get(skillid).byteValue();
                    data[103] = (byte)(skillid - 3929);
                }
            }
        }
        return data;
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

