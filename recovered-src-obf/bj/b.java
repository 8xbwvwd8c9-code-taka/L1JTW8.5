/*
 * Decompiled with CFR 0.152.
 */
package bj;

public final class b {
    private final byte[] a = new byte[8];
    private final byte[] b = new byte[8];
    private final byte[] c = new byte[4];

    public b(int seed) {
        int[] key = new int[]{seed, -1827678238};
        key[0] = Integer.rotateRight(key[0] ^ 0x9C30D539, 13);
        key[1] = key[1] ^ (key[0] ^ 0x7C72E993);
        int i2 = 0;
        while (i2 < 2) {
            int j2 = 0;
            while (j2 < 4) {
                this.a[(i2 << 2) + j2] = (byte)(key[i2] >> (j2 << 3) & 0xFF);
                ++j2;
            }
            ++i2;
        }
    }

    public byte[] a(byte[] data) {
        int i2 = 0;
        while (i2 < this.c.length) {
            this.c[i2] = data[i2 + 4];
            ++i2;
        }
        data[0] = (byte)(data[0] ^ this.b[0]);
        i2 = 1;
        while (i2 < data.length) {
            int n2 = i2;
            data[n2] = (byte)(data[n2] ^ (data[i2 - 1] ^ this.b[i2 & 7]));
            ++i2;
        }
        data[3] = (byte)(data[3] ^ this.b[2]);
        data[2] = (byte)(data[2] ^ (this.b[3] ^ data[3]));
        data[1] = (byte)(data[1] ^ (this.b[4] ^ data[2]));
        data[0] = (byte)(data[0] ^ (this.b[5] ^ data[1]));
        this.a(this.b, this.c);
        return data;
    }

    public final byte[] b(byte[] data) {
        data[0] = (byte)(data[0] ^ (this.a[5] ^ data[1]));
        data[1] = (byte)(data[1] ^ (this.a[4] ^ data[2]));
        data[2] = (byte)(data[2] ^ (this.a[3] ^ data[3]));
        data[3] = (byte)(data[3] ^ this.a[2]);
        int i2 = data.length - 1;
        while (i2 >= 1) {
            int n2 = i2;
            data[n2] = (byte)(data[n2] ^ (data[i2 - 1] ^ this.a[i2 & 7]));
            --i2;
        }
        data[0] = (byte)(data[0] ^ this.a[0]);
        i2 = 0;
        while (i2 < 4) {
            this.c[i2] = data[i2 + 4];
            ++i2;
        }
        this.a(this.a, this.c);
        return data;
    }

    private final void a(byte[] key, byte[] ref) {
        int i2 = 0;
        while (i2 < 4) {
            int n2 = i2;
            key[n2] = (byte)(key[n2] ^ ref[i2]);
            ++i2;
        }
        int mask = ((key[7] & 0xFF) << 24 | (key[6] & 0xFF) << 16 | (key[5] & 0xFF) << 8 | key[4] & 0xFF) + 679411651;
        int i3 = 0;
        while (i3 < 4) {
            key[i3 + 4] = (byte)(mask >> (i3 << 3) & 0xFF);
            ++i3;
        }
    }
}

