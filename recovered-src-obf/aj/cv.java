/*
 * Decompiled with CFR 0.152.
 */
package aj;

import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;

public abstract class cv {
    private static final Logger a = Logger.getLogger(cv.class.getName());
    private byte[] b;
    private int c;

    public cv(byte[] abyte0) {
        this.b = abyte0;
        this.c = 1;
    }

    public int b() {
        int i2 = this.b[this.c++] & 0xFF;
        i2 |= this.b[this.c++] << 8 & 0xFF00;
        i2 |= this.b[this.c++] << 16 & 0xFF0000;
        return i2 |= this.b[this.c++] << 24 & 0xFF000000;
    }

    public int c() {
        int i2 = this.b[this.c++] & 0xFF;
        return i2;
    }

    public int d() {
        int i2 = this.b[this.c++] & 0xFF;
        return i2 |= this.b[this.c++] << 8 & 0xFF00;
    }

    public int e() {
        int i2 = this.b[this.c++] & 0xFF;
        i2 |= this.b[this.c++] << 8 & 0xFF00;
        return i2 |= this.b[this.c++] << 16 & 0xFF0000;
    }

    public double f() {
        long l2 = this.b[this.c++] & 0xFF;
        l2 |= (long)(this.b[this.c++] << 8 & 0xFF00);
        l2 |= (long)(this.b[this.c++] << 16 & 0xFF0000);
        l2 |= (long)(this.b[this.c++] << 24 & 0xFF000000);
        l2 |= (long)this.b[this.c++] << 32 & 0xFF00000000L;
        l2 |= (long)this.b[this.c++] << 40 & 0xFF0000000000L;
        l2 |= (long)this.b[this.c++] << 48 & 0xFF000000000000L;
        return Double.longBitsToDouble(l2 |= (long)this.b[this.c++] << 56 & 0xFF00000000000000L);
    }

    public byte[] a(int length) {
        byte[] array = new byte[length];
        int i2 = this.c;
        while (i2 < this.c + length) {
            array[i2 - this.c] = this.b[i2];
            ++i2;
        }
        this.c += length;
        return array;
    }

    public String g() {
        String s2 = null;
        try {
            s2 = new String(this.b, this.c, this.b.length - this.c, l1j.server.a.k);
            s2 = s2.substring(0, s2.indexOf(0));
            this.c += s2.getBytes(l1j.server.a.k).length + 1;
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        return s2;
    }

    public byte[] h() {
        byte[] result = new byte[this.b.length - this.c];
        try {
            System.arraycopy(this.b, this.c, result, 0, this.b.length - this.c);
            this.c = this.b.length;
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        return result;
    }

    public void i() {
        this.b = null;
    }

    public String a() {
        return "[C] " + this.getClass().getSimpleName();
    }
}

