/*
 * Decompiled with CFR 0.152.
 */
package bi;

public class h {
    protected int a = 0;
    protected int b = 0;
    private static final int[] c;
    private static final int[] d;
    private static final int e = 30;

    static {
        int[] nArray = new int[8];
        nArray[1] = 1;
        nArray[2] = 1;
        nArray[3] = 1;
        nArray[5] = -1;
        nArray[6] = -1;
        nArray[7] = -1;
        c = nArray;
        int[] nArray2 = new int[8];
        nArray2[0] = -1;
        nArray2[1] = -1;
        nArray2[3] = 1;
        nArray2[4] = 1;
        nArray2[5] = 1;
        nArray2[7] = -1;
        d = nArray2;
    }

    public h() {
    }

    public h(int x2, int y2) {
        this.a = x2;
        this.b = y2;
    }

    public h(h pt) {
        this.a = pt.a;
        this.b = pt.b;
    }

    public int f() {
        return this.a;
    }

    public void b(int x2) {
        this.a = x2;
    }

    public int g() {
        return this.b;
    }

    public void c(int y2) {
        this.b = y2;
    }

    public void a(h pt) {
        this.a = pt.a;
        this.b = pt.b;
    }

    public void a(int x2, int y2) {
        this.a = x2;
        this.b = y2;
    }

    public void d(int heading) {
        this.a += c[heading];
        this.b += d[heading];
    }

    public void e(int heading) {
        this.a -= c[heading];
        this.b -= d[heading];
    }

    public double b(h pt) {
        long diffX = pt.f() - this.f();
        long diffY = pt.g() - this.g();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public int c(h pt) {
        return Math.max(Math.abs(pt.f() - this.f()), Math.abs(pt.g() - this.g()));
    }

    public int d(h pt) {
        return Math.abs(pt.f() - this.f()) + Math.abs(pt.g() - this.g());
    }

    public boolean e(h pt) {
        return this.d(pt) < 30;
    }

    public boolean f(h pt) {
        return pt.f() == this.f() && pt.g() == this.g();
    }

    public int hashCode() {
        return 7 * this.f() + this.g();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof h)) {
            return false;
        }
        h pt = (h)obj;
        return this.f() == pt.f() && this.g() == pt.g();
    }

    public String toString() {
        return String.format("(%d, %d)", this.a, this.b);
    }
}

