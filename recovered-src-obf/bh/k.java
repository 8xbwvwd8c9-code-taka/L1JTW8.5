/*
 * Decompiled with CFR 0.152.
 */
package bh;

import java.sql.Timestamp;

public class k {
    private int a;
    private int b;
    private String c;
    private String d;
    private Timestamp e = null;
    private int f = 0;
    private byte[] g;
    private byte[] h;
    private int i = 0;

    public int a() {
        return this.a;
    }

    public void a(int id) {
        this.a = id;
    }

    public int b() {
        return this.b;
    }

    public void b(int type) {
        this.b = type;
    }

    public String c() {
        return this.c;
    }

    public void a(String senderName) {
        this.c = senderName;
    }

    public String d() {
        return this.d;
    }

    public void b(String receiverName) {
        this.d = receiverName;
    }

    public Timestamp e() {
        return this.e;
    }

    public void a(Timestamp date) {
        this.e = date;
    }

    public int f() {
        return this.f;
    }

    public void c(int readStatus) {
        this.f = readStatus;
    }

    public byte[] g() {
        return this.g;
    }

    public void a(byte[] subject) {
        this.g = subject;
    }

    public byte[] h() {
        return this.h;
    }

    public void b(byte[] content) {
        this.h = content;
    }

    public int i() {
        return this.i;
    }

    public void d(int inBoxId) {
        this.i = inBoxId;
    }
}

