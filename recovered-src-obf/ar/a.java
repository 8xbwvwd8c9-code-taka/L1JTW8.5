/*
 * Decompiled with CFR 0.152.
 */
package ar;

import ar.b;
import ar.c;
import ar.d;
import ar.e;
import ar.f;
import ar.g;
import ar.h;
import ar.i;

public abstract class a {
    public static a a(int classId) {
        if (classId == 0 || classId == 1) {
            return new g();
        }
        if (classId == 138 || classId == 37) {
            return new d();
        }
        if (classId == 61 || classId == 48) {
            return new f();
        }
        if (classId == 734 || classId == 1186) {
            return new i();
        }
        if (classId == 2786 || classId == 2796) {
            return new b();
        }
        if (classId == 6658 || classId == 6661) {
            return new c();
        }
        if (classId == 6671 || classId == 6650) {
            return new e();
        }
        if (classId == 12490 || classId == 12494) {
            return new h();
        }
        throw new IllegalArgumentException();
    }

    public abstract int b(int var1);

    public abstract int c(int var1);

    public abstract int[] a();

    public abstract int b();

    public abstract int c();

    public abstract int d();

    public abstract int d(int var1);

    public abstract int e(int var1);

    public abstract int f(int var1);

    public abstract int g(int var1);

    public abstract int h(int var1);

    public abstract int i(int var1);

    public abstract int e();

    public abstract int f();

    public abstract int g();

    public abstract int j(int var1);

    public abstract int k(int var1);

    public abstract String h();
}

