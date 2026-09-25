/*
 * Decompiled with CFR 0.152.
 */
package bh;

import java.sql.Timestamp;

public class i {
    private int a;
    private String b;
    private int c;
    private String d;
    private int e;
    private boolean f;
    private boolean g;
    private Timestamp h;
    private Timestamp i;
    private int j;
    private String k;
    private int l;
    private String m;
    private int n;

    public void a() {
        String initialName = "$1194;";
        if (this.a >= 65537 && this.a <= 65542) {
            initialName = String.valueOf(initialName) + (this.a - 65536);
        } else if (this.a >= 262145 && this.a <= 262189) {
            initialName = String.valueOf(initialName) + (this.a - 262144);
        } else if (this.a >= 327681 && this.a <= 327691) {
            initialName = String.valueOf(initialName) + (this.a - 327680);
        } else if (this.a >= 458753 && this.a <= 458819) {
            initialName = String.valueOf(initialName) + (this.a - 458752);
        }
        this.b = initialName = String.valueOf(initialName) + "$1195";
    }

    public int b() {
        return this.a;
    }

    public void a(int houseId) {
        this.a = houseId;
    }

    public String c() {
        return this.b;
    }

    public void a(String houseName) {
        this.b = houseName;
    }

    public int d() {
        return this.c;
    }

    public void b(int houseArea) {
        this.c = houseArea;
    }

    public String e() {
        return this.d;
    }

    public void b(String location) {
        this.d = location;
    }

    public int f() {
        return this.e;
    }

    public void c(int keeperId) {
        this.e = keeperId;
    }

    public boolean g() {
        return this.f;
    }

    public void a(boolean isOnSale) {
        this.f = isOnSale;
    }

    public boolean h() {
        return this.g;
    }

    public void b(boolean isPurchaseBasement) {
        this.g = isPurchaseBasement;
    }

    public Timestamp i() {
        return this.h;
    }

    public void a(Timestamp taxDeadline) {
        this.h = taxDeadline;
    }

    public Timestamp j() {
        return this.i;
    }

    public void b(Timestamp deadline) {
        this.i = deadline;
    }

    public int k() {
        return this.j;
    }

    public void d(int price) {
        this.j = price;
    }

    public String l() {
        return this.k;
    }

    public void c(String oldOwnerName) {
        this.k = oldOwnerName;
    }

    public int m() {
        return this.l;
    }

    public void e(int oldOwnerId) {
        this.l = oldOwnerId;
    }

    public String n() {
        return this.m;
    }

    public void d(String bidderName) {
        this.m = bidderName;
    }

    public int o() {
        return this.n;
    }

    public void f(int bidderId) {
        this.n = bidderId;
    }
}

