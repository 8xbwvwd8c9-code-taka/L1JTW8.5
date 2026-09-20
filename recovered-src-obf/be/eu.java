/*
 * Decompiled with CFR 0.152.
 */
package be;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;

public abstract class eu {
    private static final Logger a = Logger.getLogger(eu.class.getName());
    protected static final String aV = l1j.server.a.k;
    ByteArrayOutputStream aW = new ByteArrayOutputStream();

    protected eu() {
    }

    protected void a(int value) {
        this.aW.write(value & 0xFF);
        this.aW.write(value >> 8 & 0xFF);
        this.aW.write(value >> 16 & 0xFF);
        this.aW.write(value >> 24 & 0xFF);
    }

    protected void b(int value) {
        this.aW.write(value & 0xFF);
        this.aW.write(value >> 8 & 0xFF);
    }

    protected void c(int value) {
        this.aW.write(value & 0xFF);
    }

    protected void d(int value) {
        this.aW.write(value);
    }

    protected void a(long value) {
        this.aW.write((int)(value & 0xFFL));
    }

    protected void a(double org) {
        long value = Double.doubleToRawLongBits(org);
        this.aW.write((int)(value & 0xFFL));
        this.aW.write((int)(value >> 8 & 0xFFL));
        this.aW.write((int)(value >> 16 & 0xFFL));
        this.aW.write((int)(value >> 24 & 0xFFL));
        this.aW.write((int)(value >> 32 & 0xFFL));
        this.aW.write((int)(value >> 40 & 0xFFL));
        this.aW.write((int)(value >> 48 & 0xFFL));
        this.aW.write((int)(value >> 56 & 0xFFL));
    }

    protected void a(String text) {
        try {
            if (text != null) {
                this.aW.write(text.getBytes(aV));
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
        this.aW.write(0);
    }

    protected void a(byte[] text) {
        try {
            if (text != null && text.length > 0) {
                this.aW.write(text);
            }
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public int c() {
        return this.aW.size() + 2;
    }

    public byte[] d() {
        return this.aW.toByteArray();
    }

    public abstract byte[] a() throws IOException;

    public String b() {
        return this.getClass().getSimpleName();
    }
}

