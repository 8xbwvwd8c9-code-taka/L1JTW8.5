/*
 * Decompiled with CFR 0.152.
 */
package bi;

import bi.i;

public class f {
    private final int a;
    private final int b;

    public f(int low, int high) {
        this.a = low;
        this.b = high;
    }

    public static boolean a(int i2, int low, int high) {
        return low <= i2 && i2 <= high;
    }

    public int a(int i2) {
        int r2 = i2;
        r2 = this.a <= r2 ? r2 : this.a;
        r2 = r2 <= this.b ? r2 : this.b;
        return r2;
    }

    public static int b(int n2, int low, int high) {
        int r2 = n2;
        r2 = low <= r2 ? r2 : low;
        r2 = r2 <= high ? r2 : high;
        return r2;
    }

    public int a() {
        return i.a(this.d() + 1) + this.a;
    }

    public int b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.b - this.a;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof f)) {
            return false;
        }
        f range = (f)obj;
        return this.a == range.a && this.b == range.b;
    }

    public String toString() {
        return "low=" + this.a + ", high=" + this.b;
    }

    public int hashCode() {
        return super.hashCode();
    }
}

