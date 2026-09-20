/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import aq.aq;
import aq.x;
import be.eu;
import bh.a;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

public class cm
extends eu {
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

    public cm(byte[] b2) {
        this.a(b2);
    }

    public cm(u pc, int val) {
        this.c(121);
        this.c(val);
        this.b(1800);
        this.b(3600);
        this.a(0);
    }

    public cm(int subCode, int type, int type2, int time) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 180: {
                this.c(time);
                this.a(type);
                this.a(type2);
                this.b(0);
                break;
            }
            case 86: {
                if (type == 173) {
                    this.c(type);
                    this.c(type2);
                    this.a(time);
                    return;
                }
                if (type == 62) {
                    this.c(type);
                    this.c(type2);
                    this.b(time);
                    this.b(34334);
                    return;
                }
                if (type == 76) {
                    this.c(type);
                    this.c(type2);
                    this.c(time);
                    return;
                }
                if (type == 3) {
                    this.c(type);
                    this.c(time);
                    this.c(type2);
                    return;
                }
                this.c(type);
                this.c(1);
                this.c(type2);
                this.b(time);
                this.b(0);
            }
        }
    }

    public cm(int subCode) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 3: 
            case 4: 
            case 9: 
            case 31: 
            case 42: 
            case 43: 
            case 125: 
            case 127: {
                break;
            }
            case 45: {
                this.e();
                break;
            }
            case 59: {
                this.b(0);
                break;
            }
            case 178: {
                this.c(1);
                this.a(12);
                this.a(-1);
                this.a(-1);
                this.a(-1);
                this.b(0);
                break;
            }
        }
    }

    public cm(int subCode, byte[] b2) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 188: {
                this.a(b2);
            }
        }
    }

    public cm(int subCode, u pc, int type, int time) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 53: {
                this.c(pc.ez());
                this.c(pc.eD());
                this.c(pc.eE());
                this.c(pc.eB());
                this.c(pc.eA());
                this.c(pc.eC());
                this.b(pc.fj() * 9);
                this.c(type);
                this.c(36);
                this.b(time);
                this.c(pc.j().h());
            }
        }
    }

    public cm(int subCode, u pc) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 204: {
                this.b(pc.dW());
                break;
            }
            case 176: {
                this.c(1);
                this.b(pc.fs());
                this.b(pc.ft());
            }
        }
    }

    public cm(int subCode, int value) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 195: {
                this.a(value);
                this.a((int)(new Date().getTime() / 1000L));
                break;
            }
            case 196: {
                this.a(0);
                this.b(value);
                this.b(0);
                break;
            }
            case 151: {
                this.c(value);
                this.a(0);
                break;
            }
            case 153: {
                this.b(value);
                this.b(0);
                break;
            }
            case 55: {
                this.a(0);
                this.b(value);
                this.b(0);
                break;
            }
            case 60: {
                this.c(value / 4);
                this.c(8);
                break;
            }
            case 57: {
                this.c(44);
                this.b(value);
                break;
            }
            case 35: {
                this.b(value);
                this.c(0);
                break;
            }
            case 34: {
                this.b(value);
                this.c(1);
                break;
            }
            case 36: 
            case 40: 
            case 56: 
            case 75: 
            case 132: {
                this.b(value);
                break;
            }
            case 0: 
            case 1: 
            case 2: {
                this.c(value);
                this.b(0);
                break;
            }
            case 6: 
            case 10: 
            case 11: 
            case 74: 
            case 141: 
            case 144: {
                this.c(value);
                break;
            }
            case 15: 
            case 49: {
                this.c(value);
                this.c(0);
                break;
            }
            case 12: {
                this.c(0);
                this.c(value);
                break;
            }
            case 52: {
                this.c(219);
                this.c(49);
                this.c(223);
                this.c(2);
                this.c(1);
                this.c(value);
                break;
            }
            case 88: {
                this.c(value);
                this.c(0);
                break;
            }
            case 101: {
                this.c(value);
                break;
            }
            case 21: {
                this.c(0);
                this.c(0);
                this.c(0);
                this.c(value);
                break;
            }
            case 173: {
                this.c(1);
                this.c(value);
                break;
            }
            case 83: {
                this.a(value);
                break;
            }
            case 146: {
                CopyOnWriteArrayList<u> list = aq.x.a().b(value);
                if (list != null) {
                    this.c(list.size());
                    for (u disciple : list) {
                        this.a(disciple.et());
                        this.c(disciple.ev());
                        this.c(disciple.ay());
                    }
                }
                this.a(aq.x.a().a(value));
                break;
            }
            case 185: {
                this.a(value);
                this.b(4);
                this.a("gold deathknight");
                this.a("lightning deathknight");
                this.a("fire deathknight");
                this.a("dark deathknight");
                this.a(0);
                this.b(0);
                this.c(0);
                break;
            }
        }
    }

    public cm(int subCode, int type, int time) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 154: {
                this.b(time);
                this.b(type);
                this.a(0);
                break;
            }
            case 86: {
                if (type == 92) {
                    this.c(type);
                    this.c(time);
                    this.b(0);
                    return;
                }
                this.c(type);
                this.c(1);
                this.c(time);
                this.b(0);
                break;
            }
            case 161: {
                this.c(type);
                if (type == 2) {
                    this.b(0);
                    this.c(time);
                    break;
                }
                this.b(time);
                this.c(time > 0 ? 1 : 0);
                break;
            }
            case 156: {
                this.a(type);
                this.a(time);
                this.b(0);
                break;
            }
            case 150: {
                this.c(1);
                this.c(type);
                this.a(time);
                this.b(0);
                break;
            }
            case 5: {
                this.a(type);
                this.a(time);
                break;
            }
            case 56: {
                if (type == 32) {
                    this.b(time);
                    this.c(type);
                    this.c(12);
                    break;
                }
                this.b(time);
                this.c(0);
                this.c(0);
                break;
            }
            case 44: {
                this.a(type);
                this.b(time / 4);
                break;
            }
            case 160: {
                this.c(type);
                this.b(time);
                break;
            }
            case 100: {
                this.c(type);
                this.a(time / 60);
                break;
            }
            case 147: {
                this.c(time);
                this.c(type);
                break;
            }
            case 82: {
                this.a(type);
                this.a(time);
                this.a(0);
                break;
            }
        }
    }

    public cm(int subCode, String name) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 23: 
            case 168: {
                this.a(name);
                break;
            }
        }
    }

    public cm(int subCode, int rank, String name) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 18: 
            case 19: {
                this.a(name);
                this.c(rank);
                break;
            }
            case 27: {
                this.c(rank);
                this.a(name);
                break;
            }
            case 84: {
                this.c(rank);
                this.a(name);
            }
        }
    }

    public cm(int subCode, String[] names, int type) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 17: {
                this.c(type);
                this.c(type);
                this.c(names.length);
                String[] stringArray = names;
                int n2 = names.length;
                int n3 = 0;
                while (n3 < n2) {
                    String name = stringArray[n3];
                    this.a(name);
                    ++n3;
                }
                this.b(0);
            }
        }
    }

    public cm(int subCode, Object[] names) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 79: {
                this.c(names.length);
                Object[] objectArray = names;
                int n2 = names.length;
                int n3 = 0;
                while (n3 < n2) {
                    Object name = objectArray[n3];
                    this.a(name.toString());
                    ++n3;
                }
                break;
            }
            case 80: {
                this.c(names.length);
                Object[] objectArray = names;
                int n4 = names.length;
                int n5 = 0;
                while (n5 < n4) {
                    Object name = objectArray[n5];
                    this.a(name.toString());
                    ++n5;
                }
                break;
            }
            case 171: {
                this.b(names.length);
                Object[] objectArray = names;
                int n6 = names.length;
                int n7 = 0;
                while (n7 < n6) {
                    Object name = objectArray[n7];
                    u pc = (u)name;
                    this.a(pc.et());
                    this.c(1);
                    ++n7;
                }
                break;
            }
        }
    }

    public cm(int subCode, String[] names, int[] value) {
        this.c(121);
        this.c(subCode);
        switch (subCode) {
            case 159: {
                this.a(names.length);
                int i2 = 0;
                while (i2 < names.length) {
                    int index = i2 + 1;
                    this.a(index);
                    this.a(names[i2]);
                    this.a(value[i2] / 60);
                    ++i2;
                }
                this.b(0);
            }
        }
    }

    private void e() {
        this.c(aq.aq.a().c().size());
        for (u pc : aq.aq.a().c()) {
            if (pc.aK() == null) {
                this.a(0);
                this.a(pc.et());
                this.a("socket null");
                continue;
            }
            a acc = pc.aK().e();
            if (acc == null) {
                this.a(0);
                this.a(pc.et());
                this.a("account null");
                continue;
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(acc.g());
            this.a((int)(cal.getTimeInMillis() / 1000L));
            this.a(pc.et());
            String text = "\\aA\u5b89\u5168\u5340";
            if (pc.fp() == 5300 || pc.fp() == 5301 || pc.fp() == 5490) {
                text = "\\aJ\u91e3\u9b5a\u6c60";
            } else if (pc.fu().e()) {
                text = "\\aL\u4e00\u822c\u5340";
            } else if (pc.fu().d()) {
                text = "\\aH\u6230\u9b25\u5340";
            }
            this.a(String.valueOf(pc.ev()) + "-" + text);
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

