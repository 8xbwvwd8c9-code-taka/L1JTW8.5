/*
 * Decompiled with CFR 0.152.
 */
package bj;

public class a {
    private static final int a = -1674521287;
    private static final int b = -1827678238;
    private static final int c = 2087905683;
    private static final int d = 679411651;
    private final byte[] e = new byte[8];
    private final byte[] f = new byte[8];
    private final byte[] g = new byte[256];
    private final byte[] h = new byte[256];

    public a(int key) {
        byte t2 = 0;
        int temp = 0;
        int[] keys = new int[]{key ^ 0x9C30D539, -1827678238};
        keys[0] = Integer.rotateLeft(keys[0], 19);
        keys[1] = keys[1] ^ (keys[0] ^ 0x7C72E993);
        int i2 = 0;
        while (i2 < 2) {
            int j2 = 0;
            while (j2 < 4) {
                byte data;
                this.f[i2 * 4 + j2] = data = (byte)(keys[i2] >> j2 * 8 & 0xFF);
                this.e[i2 * 4 + j2] = data;
                ++j2;
            }
            ++i2;
        }
        i2 = 0;
        while (i2 < 256) {
            this.g[i2] = (byte)i2;
            ++i2;
        }
        int j3 = 0;
        while (j3 < 256) {
            temp = this.g[j3] + temp + this.e[j3 % 8] & 0xFF;
            t2 = this.g[temp];
            this.g[temp] = this.g[j3];
            this.g[j3] = t2;
            ++j3;
        }
        System.arraycopy(this.g, 0, this.h, 0, 256);
    }

    public void a(byte[] data) {
        int length = data.length + 1;
        int b2 = 0;
        int c2 = 0;
        byte d2 = 0;
        int a2 = 1;
        while (a2 < length) {
            b2 += this.g[a2 & 0xFF];
            c2 = a2 & 0xFF;
            d2 = this.g[c2];
            this.g[c2] = this.g[b2 &= 0xFF];
            this.g[b2] = d2;
            int n2 = a2 - 1;
            data[n2] = (byte)(data[n2] ^ this.g[this.g[b2] + this.g[c2] & 0xFF]);
            ++a2;
        }
    }

    public byte[] b(byte[] data) {
        data[0] = (byte)(data[0] ^ (this.f[5] ^ data[1]));
        data[1] = (byte)(data[1] ^ (this.f[4] ^ data[2]));
        data[2] = (byte)(data[2] ^ (this.f[3] ^ data[3]));
        data[3] = (byte)(data[3] ^ this.f[2]);
        int length = data.length;
        int i2 = length - 1;
        while (i2 >= 1) {
            data[i2] = (byte)(data[i2] ^ (data[i2 - 1] ^ this.f[i2 & 7]));
            --i2;
        }
        data[0] = (byte)(data[0] ^ this.f[0]);
        byte[] temp = new byte[length -= 4];
        System.arraycopy(data, 4, temp, 0, length);
        this.a(this.f, temp);
        return temp;
    }

    public byte[] c(byte[] data) {
        byte[] nd = new byte[data.length + 4];
        System.arraycopy(data, 0, nd, 4, data.length);
        nd[0] = (byte)(nd[0] ^ this.e[0]);
        int i2 = 1;
        while (i2 < nd.length) {
            int n2 = i2;
            nd[n2] = (byte)(nd[n2] ^ (nd[i2 - 1] ^ this.e[i2 & 7]));
            ++i2;
        }
        nd[3] = (byte)(nd[3] ^ this.e[2]);
        nd[2] = (byte)(nd[2] ^ (this.e[3] ^ nd[3]));
        nd[1] = (byte)(nd[1] ^ (this.e[4] ^ nd[2]));
        nd[0] = (byte)(nd[0] ^ (this.e[5] ^ nd[1]));
        this.a(this.e, data);
        return nd;
    }

    public byte[] d(byte[] data) {
        int length = data.length + 1;
        int b2 = 0;
        int c2 = 0;
        byte d2 = 0;
        int a2 = 1;
        while (a2 < length) {
            b2 = a2 & 0xFF;
            c2 += this.h[b2];
            d2 = this.h[b2];
            this.h[b2] = this.h[c2 &= 0xFF];
            this.h[c2] = (byte)(d2 & 0xFF);
            int i2 = a2 - 1;
            data[i2] = (byte)(data[i2] ^ this.h[this.h[b2] + this.h[c2] & 0xFF]);
            ++a2;
        }
        return data;
    }

    private void a(byte[] data, byte[] ref) {
        int i2 = 0;
        while (i2 < 4) {
            int n2 = i2;
            data[n2] = (byte)(data[n2] ^ ref[i2]);
            ++i2;
        }
        int int32 = ((data[7] & 0xFF) << 24 | (data[6] & 0xFF) << 16 | (data[5] & 0xFF) << 8 | data[4] & 0xFF) + 679411651;
        int i3 = 0;
        while (i3 < 4) {
            data[i3 + 4] = (byte)(int32 >> i3 * 8 & 0xFF);
            ++i3;
        }
    }
}

