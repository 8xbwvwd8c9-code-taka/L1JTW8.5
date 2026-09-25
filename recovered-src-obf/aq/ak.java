/*
 * Decompiled with CFR 0.152.
 */
package aq;

import am.c;
import ap.u;
import be.ei;
import be.k;
import java.util.EnumMap;

public class ak {
    private final u f;
    private int g;
    private int h;
    private final EnumMap<a, Long> i = new EnumMap(a.class);
    public static final double a = 0.75;
    public static final double b = 0.87;
    public static final double c = 0.375;
    private static final int j = 0;
    private static final int k = 1;
    public static final int d = 2;
    public static int[] e = new int[]{6142, 6145, 8806, 11328, 11329, 11330, 11331, 11332, 11333, 11334, 11335, 11336, 11337, 11338, 11339, 11340, 11341, 11342, 11343, 11344, 11345, 11346, 11347, 11348, 11349, 11350, 11351, 11352, 11353, 11354, 11355, 11356, 11357, 11358, 11359, 11360, 11361, 11362, 11363, 11364, 11365, 11366, 11367, 11368, 11369, 11370, 11371, 11372, 11373, 11374, 11375, 11377, 11378, 11379, 11380, 11381, 11382, 11383, 11384, 11385, 11386, 11387, 11388, 11389, 11390, 11391, 11392, 11393, 11394, 11395, 11396, 11397, 11398, 11399, 11400, 11401, 11402, 11403, 11404, 11405, 11406, 11407, 11408, 11409, 11410, 11411, 11412, 11413, 11414, 11415, 11416, 11417, 11418, 11419, 11420, 11421, 11446, 11447, 12225, 12226, 12227, 12237, 12240, 12541, 12542, 12681, 12702, 13152, 13153, 13216, 13217, 13218, 13219, 13220, 13388, 13389, 13450, 11635, 11650, 13346, 13631, 13635, 13715, 13717, 13719, 13721, 13723, 13725, 13727, 13729, 13731, 13733, 13735, 13737, 13739, 13741, 13743, 13745, 14461, 14462, 14478, 14927, 14928, 15115, 15539, 15537, 15534, 15814, 15550, 15548, 15545, 15526, 15528, 15531, 15532, 15534, 15537, 15539, 15545, 15548, 15550, 15814, 15830, 15831, 15832, 15833, 15834, 15846, 15847, 15848, 15849, 15850, 15599, 15868, 15866, 15865};
    private static /* synthetic */ int[] l;

    public ak(u _pc) {
        this.f = _pc;
        this.g = 0;
        this.h = 0;
        long now = System.currentTimeMillis();
        a[] aArray = aq.ak$a.values();
        int n2 = aArray.length;
        int n3 = 0;
        while (n3 < n2) {
            a each = aArray[n3];
            this.i.put(each, now);
            ++n3;
        }
    }

    public int a(a type) {
        int result = 0;
        long now = System.currentTimeMillis();
        long interval = now - this.i.get((Object)type);
        double asobi = (double)(l1j.server.a.r - 5) / 100.0;
        interval = (long)((double)interval * asobi);
        int rightInterval = this.b(type);
        if (0L < interval && interval < (long)rightInterval) {
            ++this.g;
            this.h = 0;
            if (this.g >= l1j.server.a.p) {
                this.b();
                return 2;
            }
            result = 1;
        } else if (interval >= (long)rightInterval) {
            ++this.h;
            if (this.h >= l1j.server.a.q) {
                this.g = 0;
                this.h = 0;
            }
        }
        this.i.put(type, now);
        return result;
    }

    private void b() {
        this.f.a(new ei("\u904a\u6232\u7ba1\u7406\u54e1\u5728\u904a\u6232\u4e2d\u4f7f\u7528\u52a0\u901f\u5668\u6aa2\u6e2c\u4e2d\u3002"));
        this.f.a(new k());
        this.h = 3;
    }

    public int b(a type) {
        int interval;
        double[] frameRate = new double[]{18.0, 19.0, 20.0, 21.2, 22.5, 24.0, 25.7, 25.7, 25.7, 25.7, 27.7, 30.0, 30.6, 31.1};
        switch (ak.a()[type.ordinal()]) {
            case 2: {
                int act;
                interval = am.c.a().a(this.f.fe(), this.f.k() + 1);
                if (interval == 0 || interval == 640) {
                    interval = am.c.a().a(this.f.fe(), 1);
                }
                if ((act = this.f.k() + 1) == 1 || act == 47 || act == 30 || act == 72 || act == 78) {
                    frameRate = new double[]{17.7, 18.7, 19.8, 21.0, 22.4, 24.0, 25.9, 25.9, 25.9, 25.9, 28.0, 30.6, 31.1, 31.6};
                    break;
                }
                if (act == 25 || act == 84 || act == 76 || act == 87) {
                    frameRate = new double[]{18.3, 19.2, 20.2, 21.4, 22.6, 24.0, 25.6, 25.6, 25.6, 25.6, 27.5, 29.6, 30.2, 30.8};
                    break;
                }
                if (act == 12 || act == 51 || act == 74 || act == 79 || act == 94) {
                    frameRate = new double[]{18.5, 19.5, 20.4, 21.5, 22.7, 24.0, 25.5, 25.5, 25.5, 25.5, 27.2, 29.2, 29.8, 30.4};
                    break;
                }
                if (act != 21 && act != 63 && act != 75 && act != 82) break;
                frameRate = new double[]{19.0, 19.9, 20.8, 21.8, 22.8, 24.0, 25.4, 25.4, 25.4, 25.4, 26.9, 28.5, 29.1, 29.7};
                break;
            }
            case 1: {
                interval = am.c.a().a(this.f.fe(), this.f.k());
                if (interval == 0 || interval == 640) {
                    interval = am.c.a().a(this.f.fe(), 0);
                }
                frameRate = new double[]{24.0, 24.4, 24.9, 25.5, 26.2, 27.0, 27.9, 28.9, 30.0, 31.2, 31.2, 31.2, 31.2, 31.2};
                break;
            }
            case 3: {
                interval = am.c.a().a(this.f.fe(), 18);
                frameRate = new double[]{19.4, 20.4, 21.4, 22.6, 24.0, 24.0, 24.0, 24.0, 24.0, 25.5, 25.5, 25.5, 25.5, 25.5};
                break;
            }
            case 4: {
                interval = am.c.a().a(this.f.fe(), 19);
                frameRate = new double[]{19.8, 20.7, 21.7, 22.8, 24.0, 24.0, 24.0, 24.0, 24.0, 25.3, 25.3, 25.3, 25.3, 25.3};
                break;
            }
            default: {
                return 0;
            }
        }
        int[] nArray = e;
        int n2 = e.length;
        int n3 = 0;
        while (n3 < n2) {
            int i2 = nArray[n3];
            if (this.f.fe() == i2) {
                interval = (int)((double)interval * (24.0 / frameRate[this.f.aq()]));
                break;
            }
            ++n3;
        }
        switch (this.f.fc()) {
            case 1: {
                interval = (int)((double)interval * 0.75);
                break;
            }
            case 2: {
                interval = (int)((double)interval / 0.75);
            }
        }
        switch (this.f.fd()) {
            case 1: {
                interval = (int)((double)interval * 0.75);
                break;
            }
            case 3: {
                if (type.equals((Object)aq.ak$a.a)) {
                    interval = (int)((double)interval * 0.75);
                    break;
                }
                interval = (int)((double)interval * 0.87);
                break;
            }
            case 4: {
                if (!type.equals((Object)aq.ak$a.a)) break;
                interval = (int)((double)interval * 0.75);
                break;
            }
            case 5: {
                interval = (int)((double)interval * 0.375);
            }
        }
        if (this.f.L()) {
            interval = (int)((double)interval * 0.87);
        }
        if (this.f.M() && !type.equals((Object)aq.ak$a.a)) {
            interval /= 2;
        }
        if (this.f.fp() == 5143) {
            interval = (int)((double)interval * 0.1);
        }
        return interval;
    }

    static /* synthetic */ int[] a() {
        if (l != null) {
            return l;
        }
        int[] nArray = new int[aq.ak$a.values().length];
        try {
            nArray[aq.ak$a.b.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[aq.ak$a.a.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[aq.ak$a.c.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            nArray[aq.ak$a.d.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        l = nArray;
        return nArray;
    }

    public static enum a {
        a,
        b,
        c,
        d;

    }
}

