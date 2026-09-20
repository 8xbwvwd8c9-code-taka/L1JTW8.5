/*
 * Decompiled with CFR 0.152.
 */
package ax;

import ao.t;
import ap.f;
import bi.h;

public class b
implements Cloneable {
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public int d = 0;
    public int e = 0;
    private short[][] u = new short[0][0];
    public double f = 1.0;
    public double g = 1.0;
    public double h = 1.0;
    public boolean i = false;
    public boolean j = false;
    public boolean k = false;
    public boolean l = false;
    public boolean m = false;
    public boolean n = false;
    public boolean o = false;
    public boolean p = false;
    public boolean q = false;
    public boolean r = false;
    public boolean s = false;
    public boolean t = false;
    private static final int v = 2048;
    private static final int w = 4096;
    private static final int x = 8192;
    private final int[][] y;

    public b() {
        int[][] nArrayArray = new int[8][];
        int[] nArray = new int[2];
        nArray[1] = -1;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{1, -1};
        int[] nArray2 = new int[2];
        nArray2[0] = 1;
        nArrayArray[2] = nArray2;
        nArrayArray[3] = new int[]{1, 1};
        int[] nArray3 = new int[2];
        nArray3[1] = 1;
        nArrayArray[4] = nArray3;
        nArrayArray[5] = new int[]{-1, 1};
        int[] nArray4 = new int[2];
        nArray4[0] = -1;
        nArrayArray[6] = nArray4;
        nArrayArray[7] = new int[]{-1, -1};
        this.y = nArrayArray;
    }

    public b(int mapId, short[][] map, int worldTopLeftX, int worldTopLeftY) {
        int[][] nArrayArray = new int[8][];
        int[] nArray = new int[2];
        nArray[1] = -1;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{1, -1};
        int[] nArray2 = new int[2];
        nArray2[0] = 1;
        nArrayArray[2] = nArray2;
        nArrayArray[3] = new int[]{1, 1};
        int[] nArray3 = new int[2];
        nArray3[1] = 1;
        nArrayArray[4] = nArray3;
        nArrayArray[5] = new int[]{-1, 1};
        int[] nArray4 = new int[2];
        nArray4[0] = -1;
        nArrayArray[6] = nArray4;
        nArrayArray[7] = new int[]{-1, -1};
        this.y = nArrayArray;
        this.a = mapId;
        this.u = map;
        this.b = worldTopLeftX;
        this.c = worldTopLeftY;
        this.d = worldTopLeftX + map.length - 1;
        this.e = worldTopLeftY + map[0].length - 1;
    }

    public short[][] a() {
        return this.u;
    }

    public int b() {
        return this.a;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.c;
    }

    public int e() {
        return this.d - this.b + 1;
    }

    public int f() {
        return this.e - this.c + 1;
    }

    public void a(int x2, int y2, int tile) {
        if (this.b(x2, y2)) {
            this.u[x2 - this.b][y2 - this.c] = (short)tile;
        }
    }

    private int h(int x2, int y2) {
        if (!this.b(x2, y2)) {
            return 0;
        }
        return this.u[x2 - this.b][y2 - this.c];
    }

    private int i(int x2, int y2) {
        return this.h(x2, y2) & 0xFFFFF7FF;
    }

    public int a(int x2, int y2) {
        return this.i(x2, y2);
    }

    public boolean a(h pt) {
        return this.b(pt.f(), pt.g());
    }

    public boolean b(int x2, int y2) {
        if (this.a == 4 && (x2 < 32520 || y2 < 32070 || y2 < 32190 && x2 < 33950)) {
            return false;
        }
        return this.b <= x2 && x2 <= this.d && this.c <= y2 && y2 <= this.e;
    }

    public boolean c(int x2, int y2) {
        int tile = this.h(x2, y2);
        return (tile & 0xFF) > 0;
    }

    public boolean b(int x2, int y2, int heading) {
        int tile = this.h(x2, y2);
        int nextX = x2 + this.y[heading][0];
        int nextY = y2 + this.y[heading][1];
        int next_tile = this.h(nextX, nextY);
        if ((tile & 0x1000) == 4096) {
            return heading != 0 && heading != 1 && heading != 7;
        }
        if ((next_tile & 0x1000) == 4096) {
            return heading != 5 && heading != 4 && heading != 3;
        }
        if ((tile & 0x2000) == 8192) {
            return heading != 1 && heading != 2 && heading != 3;
        }
        if ((next_tile & 0x2000) == 8192) {
            return heading != 5 && heading != 6 && heading != 7;
        }
        int[] bits = new int[]{1, 16, 4, 32, 2, 64, 8, 128};
        return (tile & bits[heading]) == bits[heading];
    }

    public boolean c(int x2, int y2, int heading) {
        int tile = this.h(x2, y2);
        int nextX = x2 + this.y[heading][0];
        int nextY = y2 + this.y[heading][1];
        if ((this.h(nextX, nextY) & 0x800) == 2048) {
            return false;
        }
        return this.b(x2, y2, heading);
    }

    public boolean d(int x2, int y2, int heading) {
        int[][] nArrayArray = new int[8][];
        int[] nArray = new int[2];
        nArray[1] = -1;
        nArrayArray[0] = nArray;
        nArrayArray[1] = new int[]{1, -1};
        int[] nArray2 = new int[2];
        nArray2[0] = 1;
        nArrayArray[2] = nArray2;
        nArrayArray[3] = new int[]{1, 1};
        int[] nArray3 = new int[2];
        nArray3[1] = 1;
        nArrayArray[4] = nArray3;
        nArrayArray[5] = new int[]{-1, 1};
        int[] nArray4 = new int[2];
        nArray4[0] = -1;
        nArrayArray[6] = nArray4;
        nArrayArray[7] = new int[]{-1, -1};
        int[][] diff = nArrayArray;
        int targetX = x2 + diff[heading][0];
        int targetY = y2 + diff[heading][1];
        if (this.j(targetX, targetY)) {
            return false;
        }
        return this.c(x2, y2);
    }

    public void a(h pt, boolean isPassable) {
        this.a(pt.f(), pt.g(), isPassable);
    }

    public void a(int x2, int y2, boolean isPassable) {
        if (!this.b(x2, y2)) {
            return;
        }
        if (isPassable) {
            short[] sArray = this.u[x2 - this.b];
            int n2 = y2 - this.c;
            sArray[n2] = (short)(sArray[n2] & 0xFFFFF7FF);
        } else {
            short[] sArray = this.u[x2 - this.b];
            int n3 = y2 - this.c;
            sArray[n3] = (short)(sArray[n3] | 0x800);
        }
    }

    public void a(int x2, int y2, boolean isPassable, int doorDir) {
        int flag;
        if (!this.b(x2, y2)) {
            return;
        }
        int n2 = flag = doorDir == 0 ? 4096 : 8192;
        if (isPassable) {
            short[] sArray = this.u[x2 - this.b];
            int n3 = y2 - this.c;
            sArray[n3] = (short)(sArray[n3] & ~flag);
        } else {
            short[] sArray = this.u[x2 - this.b];
            int n4 = y2 - this.c;
            sArray[n4] = (short)(sArray[n4] | flag);
        }
    }

    public boolean b(h pt) {
        return this.d(pt.f(), pt.g());
    }

    public boolean d(int x2, int y2) {
        int tile = this.i(x2, y2);
        return (tile & 0x200) == 512;
    }

    public boolean c(h pt) {
        return this.e(pt.f(), pt.g());
    }

    public boolean e(int x2, int y2) {
        int tile = this.i(x2, y2);
        return (tile & 0x400) == 1024;
    }

    public boolean d(h pt) {
        return this.f(pt.f(), pt.g());
    }

    public boolean f(int x2, int y2) {
        int tile = this.i(x2, y2);
        return (tile & 0x100) == 256;
    }

    public boolean g(int x2, int y2) {
        return (this.a == 5300 || this.a == 5490) && this.i(x2, y2) == 512;
    }

    public boolean g() {
        return this.i;
    }

    public boolean h() {
        return this.j;
    }

    public boolean i() {
        return this.k;
    }

    public boolean j() {
        return this.l;
    }

    public boolean k() {
        return this.m;
    }

    public boolean l() {
        return this.n;
    }

    public boolean m() {
        return this.o;
    }

    public boolean n() {
        return this.p;
    }

    public boolean o() {
        return this.q;
    }

    public boolean p() {
        return this.r;
    }

    public boolean q() {
        return this.s;
    }

    public boolean r() {
        return this.t;
    }

    private boolean j(int x2, int y2) {
        f[] fArray = ao.t.b().c();
        int n2 = fArray.length;
        int n3 = 0;
        while (n3 < n2) {
            f door = fArray[n3];
            if (this.a == door.fp() && door.o() != 28 && !door.eX()) {
                int leftEdgeLocation = door.ac_();
                int rightEdgeLocation = door.n();
                int size = rightEdgeLocation - leftEdgeLocation;
                if (size == 0) {
                    if (x2 == door.fs() && y2 == door.ft()) {
                        return true;
                    }
                } else if (door.j() == 0) {
                    int doorX = leftEdgeLocation;
                    while (doorX <= rightEdgeLocation) {
                        if (x2 == doorX && y2 == door.ft()) {
                            return true;
                        }
                        ++doorX;
                    }
                } else {
                    int doorY = leftEdgeLocation;
                    while (doorY <= rightEdgeLocation) {
                        if (x2 == door.fs() && y2 == doorY) {
                            return true;
                        }
                        ++doorY;
                    }
                }
            }
            ++n3;
        }
        return false;
    }

    public b s() throws CloneNotSupportedException {
        return (b)super.clone();
    }

    public String e(h pt) {
        return "" + this.a(pt.f(), pt.g());
    }
}

