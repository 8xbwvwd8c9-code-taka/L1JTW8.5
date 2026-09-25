/*
 * Decompiled with CFR 0.152.
 */
package bj;

public final class c {
    private final byte[] a = new byte[256];
    private final byte[] b = new byte[256];

    public c(int seed) {
        int[] key = new int[]{seed, -1827678238};
        key[0] = Integer.rotateRight(key[0] ^ 0x9C30D539, 13);
        key[1] = key[1] ^ (key[0] ^ 0x7C72E993);
        byte[] keys = new byte[8];
        int i2 = 0;
        while (i2 < 2) {
            int j2 = 0;
            while (j2 < 4) {
                keys[(i2 << 2) + j2] = (byte)(key[i2] >> (j2 << 3) & 0xFF);
                ++j2;
            }
            ++i2;
        }
        i2 = 0;
        while (i2 < 256) {
            this.a[i2] = (byte)i2;
            this.b[i2] = (byte)i2;
            ++i2;
        }
        int j3 = 0;
        int i3 = 0;
        while (i3 < 256) {
            j3 = keys[i3 % 8] + j3 + this.a[i3] & 0xFF;
            this.a(this.a, i3, j3);
            ++i3;
        }
        int k2 = 0;
        int i4 = 0;
        while (i4 < 256) {
            k2 = keys[i4 % 8] + k2 + this.b[i4] & 0xFF;
            this.a(this.b, i4, k2);
            ++i4;
        }
        keys = null;
    }

    public final byte[] a(byte[] data) {
        int j2 = 0;
        int k2 = 0;
        int len = data.length;
        int i2 = 0;
        while (i2 < len) {
            j2 = j2 + 1 & 0xFF;
            k2 = this.b[j2] + k2 & 0xFF;
            this.a(this.b, j2, k2);
            data[i2] = (byte)(this.b[this.b[j2] + this.b[k2] & 0xFF] ^ data[i2]);
            ++i2;
        }
        return data;
    }

    public final byte[] b(byte[] data) {
        int j2 = 0;
        int k2 = 0;
        int len = data.length;
        int i2 = 0;
        while (i2 < len) {
            j2 = j2 + 1 & 0xFF;
            k2 = this.a[j2] + k2 & 0xFF;
            this.a(this.a, j2, k2);
            data[i2] = (byte)(this.a[this.a[j2] + this.a[k2] & 0xFF] ^ data[i2]);
            ++i2;
        }
        return data;
    }

    private final void a(byte[] buf, int j2, int k2) {
        byte temp = buf[j2];
        buf[j2] = buf[k2];
        buf[k2] = temp;
    }
}

