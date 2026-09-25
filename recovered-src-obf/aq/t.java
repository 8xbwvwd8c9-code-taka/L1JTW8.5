/*
 * Decompiled with CFR 0.152.
 */
package aq;

import bi.f;

public class t {
    private static final int[] a = new int[]{10000, 20000, 100000, 500000, 1500000, 3000000, 5000000, 10000000, 15500000};
    private static f b = new f(-15500000, 15500000);
    private int c = 0;

    public int a() {
        return this.c;
    }

    public void a(int i2) {
        this.c = b.a(i2);
    }

    public void b(int i2) {
        this.a(this.c + i2);
    }

    public int b() {
        boolean isMinus = false;
        int karmaLevel = 0;
        int karma = this.a();
        if (karma < 0) {
            isMinus = true;
            karma *= -1;
        }
        int[] nArray = a;
        int n2 = a.length;
        int n3 = 0;
        while (n3 < n2) {
            int point = nArray[n3];
            if (karma < point || ++karmaLevel >= 8) break;
            ++n3;
        }
        if (isMinus) {
            karmaLevel *= -1;
        }
        return karmaLevel;
    }

    public int c() {
        int karma = this.a();
        int karmaLevel = this.b();
        if (karmaLevel == 0) {
            return 0;
        }
        if (karma < 0) {
            karma *= -1;
            karmaLevel *= -1;
        }
        return 100 * (karma - a[karmaLevel - 1]) / (a[karmaLevel] - a[karmaLevel - 1]);
    }
}

