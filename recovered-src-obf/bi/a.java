/*
 * Decompiled with CFR 0.152.
 */
package bi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class a
extends OutputStream {
    private static final Logger a = Logger.getLogger(a.class.getName());
    private static final String b = l1j.server.a.k;
    private final ByteArrayOutputStream c = new ByteArrayOutputStream();

    @Override
    public void write(int b2) throws IOException {
        this.c.write(b2);
    }

    public void a(int value) {
        this.c.write(value & 0xFF);
        this.c.write(value >> 8 & 0xFF);
        this.c.write(value >> 16 & 0xFF);
        this.c.write(value >> 24 & 0xFF);
    }

    public void b(int value) {
        this.c.write(value & 0xFF);
        this.c.write(value >> 8 & 0xFF);
    }

    public void c(int value) {
        this.c.write(value & 0xFF);
    }

    public void d(int value) {
        this.c.write(value);
    }

    public void a(long value) {
        this.c.write((int)(value & 0xFFL));
    }

    public void a(double org) {
        long value = Double.doubleToRawLongBits(org);
        this.c.write((int)(value & 0xFFL));
        this.c.write((int)(value >> 8 & 0xFFL));
        this.c.write((int)(value >> 16 & 0xFFL));
        this.c.write((int)(value >> 24 & 0xFFL));
        this.c.write((int)(value >> 32 & 0xFFL));
        this.c.write((int)(value >> 40 & 0xFFL));
        this.c.write((int)(value >> 48 & 0xFFL));
        this.c.write((int)(value >> 56 & 0xFFL));
    }

    public void a(String text) {
        try {
            if (text != null) {
                this.c.write(text.getBytes(b));
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        this.c.write(0);
    }

    public void a(byte[] text) {
        try {
            if (text != null) {
                this.c.write(text);
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public int a() {
        return this.c.size() + 2;
    }

    public byte[] b() {
        return this.c.toByteArray();
    }
}

