/*
 * Decompiled with CFR 0.152.
 */
package bh;

import ap.u;
import be.dc;

public class s {
    private int a = 0;
    private int b = 1;
    private int c = 55;
    private int d = 7783;
    private int[] e = new int[0];
    private int[] f = new int[0];
    private int[] g = new int[0];
    private int[] h = new int[0];
    private int[] i = new int[0];
    private int[] j = new int[0];
    private int[] k = new int[0];
    private int l = 0;
    private String m = "A";
    private int n = 0;
    private boolean o = false;
    private int[] p = new int[0];
    private int[] q = new int[0];
    private int[] r = new int[0];
    private int[] s = new int[0];
    private int[] t = new int[0];
    private int[] u = new int[0];
    private int[] v = new int[0];
    private boolean w = false;
    private boolean x = false;
    private u y = null;
    private int z = 1;
    private int[] A = new int[0];
    private int[] B = new int[0];
    private int[] C = new int[0];

    public void a(int level) {
        if (!this.x) {
            this.z = level > this.n ? this.n : level;
            this.D();
        }
    }

    public void b(int idx) {
        if (!this.x && this.A[idx] < this.q[idx]) {
            int n2 = idx;
            this.A[n2] = this.A[n2] + 1;
            this.D();
        }
    }

    public void a(int idx, int count) {
        if (!this.x) {
            this.B[idx] = count > this.s[idx] ? this.s[idx] : count;
            this.D();
        }
    }

    public void c(int idx) {
        if (!this.x && this.C[idx] < this.v[idx]) {
            int n2 = idx;
            this.C[n2] = this.C[n2] + 1;
            this.D();
        }
    }

    private void D() {
        int i2;
        if (this.y != null) {
            this.y.a(new dc(519, this));
        }
        if (this.n > 0 && this.z < this.n) {
            return;
        }
        if (this.p.length > 0) {
            i2 = 0;
            while (i2 < this.p.length) {
                if (this.A[i2] < this.p[i2]) {
                    return;
                }
                ++i2;
            }
        }
        if (this.r.length > 0) {
            i2 = 0;
            while (i2 < this.r.length) {
                if (this.B[i2] < this.s[i2]) {
                    return;
                }
                ++i2;
            }
        }
        if (this.u.length > 0) {
            i2 = 0;
            while (i2 < this.u.length) {
                if (this.C[i2] < this.v[i2]) {
                    return;
                }
                ++i2;
            }
        }
        this.x = true;
    }

    public s(int questid) {
        this.a = questid;
        switch (questid) {
            case 256: {
                this.c = 5;
                this.n = 5;
                this.l = 3346;
                this.f = new int[]{640728, 640739};
                this.g = new int[]{1, 1};
                this.h = new int[2];
                this.e = new int[]{32780, 32825, 7783};
                break;
            }
            case 257: {
                this.b = 8;
                this.c = 10;
                this.u = new int[]{640728, 640739};
                this.v = new int[]{1, 1};
                this.C = new int[this.u.length];
                this.l = 5904;
                this.e = new int[]{32780, 32825, 7783};
                break;
            }
            case 258: {
                this.b = 10;
                this.c = 15;
                this.p = new int[]{190389, 190435};
                this.q = new int[]{5, 6};
                this.A = new int[this.p.length];
                this.l = 28416;
                this.f = new int[]{40029};
                this.g = new int[]{15};
                this.h = new int[1];
                this.e = new int[]{32659, 32769, 7783};
                break;
            }
            case 259: {
                this.b = 17;
                this.c = 21;
                this.r = new int[]{640741};
                this.s = new int[]{15};
                this.t = new int[1];
                this.B = new int[this.r.length];
                this.o = true;
                this.l = 78512;
                this.f = new int[]{20028};
                this.g = new int[]{1};
                this.h = new int[1];
                this.e = new int[]{32765, 32836, 12146};
                break;
            }
            case 260: {
                this.b = 21;
                this.c = 24;
                this.p = new int[]{190410, 190431};
                this.q = new int[]{8, 8};
                this.A = new int[this.p.length];
                this.l = 89931;
                this.f = new int[]{20206, 640728};
                this.g = new int[]{1, 3};
                this.h = new int[2];
                this.e = new int[]{32728, 32761, 7783};
                break;
            }
            case 261: {
                this.b = 24;
                this.c = 25;
                this.p = new int[]{190433};
                this.q = new int[]{5};
                this.A = new int[this.p.length];
                this.l = 95156;
                this.f = new int[]{40308};
                this.g = new int[]{200};
                this.h = new int[1];
                this.e = new int[]{32766, 32755, 7783};
                break;
            }
            case 271: {
                this.b = 20;
                this.m = "P";
                this.n = 30;
                this.f = new int[]{640732, 640739};
                this.g = new int[]{3, 3};
                this.h = new int[2];
                this.e = new int[]{32597, 32865, 7783};
                break;
            }
            case 272: {
                this.b = 20;
                this.m = "K";
                this.n = 30;
                this.f = new int[]{640729, 314, 640739};
                this.g = new int[]{5, 1, 3};
                this.h = new int[3];
                this.e = new int[]{32597, 32865, 7783};
                break;
            }
            case 273: {
                this.b = 20;
                this.m = "E";
                this.n = 30;
                this.f = new int[]{316, 41586, 640730, 640739};
                this.g = new int[]{1, 1, 3, 3};
                this.h = new int[4];
                this.e = new int[]{32597, 32865, 7783};
                break;
            }
            case 274: {
                this.b = 20;
                this.m = "W";
                this.n = 30;
                this.f = new int[]{41482, 640733, 640739};
                this.g = new int[]{1, 2, 3};
                this.h = new int[3];
                this.e = new int[]{32597, 32865, 7783};
                break;
            }
            case 275: {
                this.b = 20;
                this.m = "D";
                this.n = 30;
                this.f = new int[]{41541, 640737, 640739};
                this.g = new int[]{1, 3, 3};
                this.h = new int[3];
                this.e = new int[]{32597, 32865, 7783};
                break;
            }
            case 276: {
                this.b = 20;
                this.m = "R";
                this.n = 30;
                this.f = new int[]{41627, 41625, 640734, 640739};
                this.g = new int[]{1, 1, 3, 3};
                this.h = new int[4];
                this.e = new int[]{32597, 32865, 7783};
                break;
            }
            case 277: {
                this.b = 20;
                this.m = "I";
                this.n = 30;
                this.f = new int[]{41654, 640731, 640739};
                this.g = new int[]{1, 3, 3};
                this.h = new int[3];
                this.e = new int[]{32597, 32865, 7783};
                break;
            }
            case 278: {
                this.b = 20;
                this.m = "O";
                this.n = 30;
                this.f = new int[]{41664, 640729, 640739, 41246};
                this.g = new int[]{1, 5, 3, 100};
                this.h = new int[4];
                this.e = new int[]{32597, 32865, 7783};
                break;
            }
            case 279: {
                this.b = 30;
                this.m = "P";
                this.n = 45;
                this.f = new int[]{640732, 40029, 640728, 640739};
                this.g = new int[]{5, 30, 3, 3};
                this.h = new int[4];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 280: {
                this.b = 30;
                this.m = "K";
                this.n = 45;
                this.f = new int[]{640729, 40029, 640728, 640739};
                this.g = new int[]{5, 30, 3, 3};
                this.h = new int[4];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 281: {
                this.b = 30;
                this.m = "E";
                this.n = 45;
                this.f = new int[]{41593, 640730, 640739, 640736};
                this.g = new int[]{1, 3, 3, 10};
                this.h = new int[4];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 282: {
                this.b = 30;
                this.m = "W";
                this.n = 45;
                this.f = new int[]{40029, 640728, 640733, 640739};
                this.g = new int[]{30, 3, 5, 3};
                this.h = new int[4];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 283: {
                this.b = 30;
                this.m = "D";
                this.n = 45;
                this.f = new int[]{41547, 640737, 640739};
                this.g = new int[]{1, 3, 3};
                this.h = new int[3];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 284: {
                this.b = 30;
                this.m = "R";
                this.n = 45;
                this.f = new int[]{40029, 640728, 640734, 640739};
                this.g = new int[]{30, 3, 5, 3};
                this.h = new int[4];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 285: {
                this.b = 30;
                this.m = "I";
                this.n = 45;
                this.f = new int[]{40029, 640728, 640731, 640739};
                this.g = new int[]{30, 3, 5, 3};
                this.h = new int[4];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 286: {
                this.b = 30;
                this.m = "O";
                this.n = 45;
                this.f = new int[]{41681, 640729, 640739};
                this.g = new int[]{1, 3, 3};
                this.h = new int[3];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 287: {
                this.b = 45;
                this.m = "P";
                this.n = 55;
                this.f = new int[]{51, 20051};
                this.g = new int[]{1, 1};
                this.h = new int[2];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 288: {
                this.b = 45;
                this.m = "K";
                this.n = 55;
                this.f = new int[]{56, 20318};
                this.g = new int[]{1, 1};
                this.h = new int[2];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 289: {
                this.b = 45;
                this.m = "E";
                this.n = 55;
                this.f = new int[]{50, 184};
                this.g = new int[]{1, 1};
                this.h = new int[2];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 290: {
                this.b = 45;
                this.m = "W";
                this.n = 55;
                this.f = new int[]{20225, 20055};
                this.g = new int[]{1, 1};
                this.h = new int[2];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 291: {
                this.b = 45;
                this.m = "D";
                this.n = 55;
                this.f = new int[]{13, 20195};
                this.g = new int[]{1, 1};
                this.h = new int[2];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 292: {
                this.b = 45;
                this.m = "R";
                this.n = 55;
                this.f = new int[]{272, 21103};
                this.g = new int[]{1, 1};
                this.h = new int[2];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 293: {
                this.b = 45;
                this.m = "I";
                this.n = 55;
                this.f = new int[]{270, 21101};
                this.g = new int[]{1, 1};
                this.h = new int[2];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 294: {
                this.b = 45;
                this.m = "O";
                this.n = 55;
                this.f = new int[]{21198, 323};
                this.g = new int[]{1, 1};
                this.h = new int[2];
                this.e = new int[]{32647, 32908, 7783};
                break;
            }
            case 295: {
                break;
            }
            case 299: {
                this.b = 20;
                this.r = new int[]{20126};
                this.s = new int[]{1};
                this.t = new int[]{1};
                this.B = new int[this.r.length];
                this.l = 4481;
                this.e = new int[]{32659, 32769, 7783};
                break;
            }
            case 303: {
                break;
            }
            case 306: {
                this.d = 0;
                this.u = new int[]{640740};
                this.v = new int[]{1};
                this.C = new int[this.u.length];
                this.l = 38963;
                this.e = new int[]{32641, 32818, 7783};
                break;
            }
            case 314: {
                this.b = 26;
                this.c = 29;
                this.p = new int[]{190400, 190401};
                this.q = new int[]{15, 15};
                this.A = new int[this.p.length];
                this.l = 84064;
                this.f = new int[]{49312, 40029, 640728};
                this.g = new int[]{1, 30, 3};
                this.h = new int[3];
                this.e = new int[]{32581, 32830, 7783};
                break;
            }
            case 315: {
                break;
            }
            case 317: {
                this.b = 29;
                this.c = 31;
                this.p = new int[]{190396, 190388, 190409, 190407};
                this.q = new int[]{15, 15, 3, 3};
                this.A = new int[this.p.length];
                this.l = 84064;
                this.f = new int[]{49312, 640728, 40308};
                this.g = new int[]{1, 5, 250};
                this.h = new int[3];
                this.e = new int[]{32512, 32823, 7783};
                break;
            }
            case 318: {
                this.b = 15;
                this.c = 17;
                this.p = new int[]{190395, 190387};
                this.q = new int[]{8, 3};
                this.A = new int[this.p.length];
                this.l = 21868;
                this.f = new int[]{40029};
                this.g = new int[]{30};
                this.h = new int[1];
                this.e = new int[]{32622, 32757, 7783};
                break;
            }
            case 319: {
                this.b = 31;
                this.c = 33;
                this.r = new int[]{640744, 640745};
                this.s = new int[]{15, 10};
                this.t = new int[2];
                this.B = new int[this.r.length];
                this.o = true;
                this.l = 117690;
                this.f = new int[]{20173, 40029, 640496};
                this.g = new int[]{1, 50, 10};
                this.h = new int[3];
                this.e = new int[]{32518, 32909, 7783};
                break;
            }
            case 320: {
                this.b = 31;
                this.c = 33;
                this.p = new int[]{190444};
                this.q = new int[]{1};
                this.A = new int[this.p.length];
                this.l = 100877;
                this.f = new int[]{49312, 49311, 40029};
                this.g = new int[]{1, 1, 100};
                this.h = new int[3];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32489, 32899, 7783};
                break;
            }
            case 321: {
                this.b = 33;
                this.c = 35;
                this.p = new int[]{190438, 190398, 190437};
                this.q = new int[]{15, 15, 15};
                this.A = new int[this.p.length];
                this.l = 134503;
                this.f = new int[]{49312, 40029};
                this.g = new int[]{1, 30};
                this.h = new int[2];
                this.e = new int[]{32566, 32919, 7783};
                break;
            }
            case 322: {
                this.b = 35;
                this.c = 36;
                this.p = new int[]{190442};
                this.q = new int[]{1};
                this.A = new int[this.p.length];
                this.l = 151316;
                this.f = new int[]{49312, 49311, 40308};
                this.g = new int[]{1, 1, 300};
                this.h = new int[3];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32552, 32952, 7783};
                break;
            }
            case 323: {
                this.b = 36;
                this.c = 37;
                this.r = new int[]{640742};
                this.s = new int[]{40};
                this.t = new int[1];
                this.B = new int[this.r.length];
                this.o = true;
                this.l = 168129;
                this.f = new int[]{21196, 40029, 40308};
                this.g = new int[]{1, 40, 350};
                this.h = new int[3];
                this.e = new int[]{32859, 32925, 12149};
                break;
            }
            case 324: {
                this.b = 37;
                this.c = 38;
                this.p = new int[]{190440};
                this.q = new int[]{1};
                this.A = new int[this.p.length];
                this.l = 184942;
                this.f = new int[]{49312, 49311, 40308};
                this.g = new int[]{1, 1, 400};
                this.h = new int[3];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32802, 32869, 12149};
                break;
            }
            case 325: {
                this.b = 39;
                this.c = 40;
                this.r = new int[]{640743};
                this.s = new int[]{20};
                this.t = new int[1];
                this.B = new int[this.r.length];
                this.o = true;
                this.l = 201755;
                this.f = new int[]{21138, 40029, 640728};
                this.g = new int[]{1, 30, 5};
                this.h = new int[3];
                this.e = new int[]{32524, 32782, 7783};
                break;
            }
            case 326: {
                this.b = 41;
                this.c = 42;
                this.r = new int[]{640750};
                this.s = new int[]{100};
                this.t = new int[1];
                this.B = new int[this.r.length];
                this.o = true;
                this.l = 235381;
                this.f = new int[]{20282, 40029, 40308};
                this.g = new int[]{1, 40, 700};
                this.h = new int[3];
                this.e = new int[]{32720, 33113, 12147};
                break;
            }
            case 328: {
                this.b = 42;
                this.c = 44;
                this.n = 44;
                this.p = new int[]{190383, 190418};
                this.q = new int[]{20, 15};
                this.A = new int[this.p.length];
                this.l = 243120;
                this.f = new int[]{49312, 40029, 40308};
                this.g = new int[]{1, 50, 1000};
                this.h = new int[3];
                this.e = new int[]{32808, 32801, 12148};
                break;
            }
            case 329: {
                break;
            }
            case 333: {
                break;
            }
            case 334: {
                this.b = 24;
                this.r = new int[]{640494};
                this.s = new int[]{1};
                this.t = new int[1];
                this.B = new int[this.r.length];
                this.f = new int[]{640494};
                this.g = new int[]{5};
                this.h = new int[1];
                this.e = new int[]{32633, 32854, 7783};
                break;
            }
            case 336: {
                this.b = 45;
                this.c = 46;
                this.p = new int[]{190424, 190417, 190406, 190412};
                this.q = new int[]{15, 10, 10, 10};
                this.A = new int[this.p.length];
                this.l = 14435077;
                this.f = new int[]{49312, 40029, 40308};
                this.g = new int[]{1, 50, 2000};
                this.h = new int[3];
                this.e = new int[]{32628, 32941, 7783};
                break;
            }
            case 338: {
                this.b = 46;
                this.c = 47;
                this.p = new int[]{190393, 190390, 190443};
                this.q = new int[]{15, 10, 1};
                this.A = new int[this.p.length];
                this.l = 16490773;
                this.f = new int[]{49312, 40029, 21139};
                this.g = new int[]{2, 50, 1};
                this.h = new int[3];
                this.e = new int[]{32615, 32989, 7783};
                break;
            }
            case 340: {
                this.b = 47;
                this.c = 48;
                this.r = new int[]{640749, 640746};
                this.s = new int[]{20, 15};
                this.t = new int[2];
                this.B = new int[this.r.length];
                this.o = true;
                this.l = 15462925;
                this.f = new int[]{49312, 40029, 40308};
                this.g = new int[]{1, 50, 3000};
                this.h = new int[3];
                this.e = new int[]{32665, 32935, 7783};
                break;
            }
            case 341: {
                this.b = 48;
                this.c = 49;
                this.p = new int[]{190439};
                this.q = new int[]{1};
                this.A = new int[this.p.length];
                this.l = 20602167;
                this.f = new int[]{49312, 21197, 40029};
                this.g = new int[]{2, 1, 50};
                this.h = new int[3];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32674, 32995, 7783};
                break;
            }
            case 342: {
                this.b = 49;
                this.c = 50;
                this.p = new int[]{190392, 190419};
                this.q = new int[]{15, 15};
                this.A = new int[this.p.length];
                this.l = 17518622;
                this.f = new int[]{49312, 40029, 40308};
                this.g = new int[]{1, 50, 3500};
                this.h = new int[3];
                this.e = new int[]{32703, 32909, 7783};
                break;
            }
            case 343: {
                this.b = 50;
                this.c = 51;
                this.r = new int[]{640747, 640748};
                this.s = new int[]{15, 15};
                this.t = new int[2];
                this.B = new int[this.r.length];
                this.o = true;
                this.l = 18546470;
                this.f = new int[]{49312, 40029, 40308};
                this.g = new int[]{1, 50, 4000};
                this.h = new int[3];
                this.e = new int[]{32730, 32866, 7783};
                break;
            }
            case 346: {
                this.b = 51;
                this.c = 52;
                this.p = new int[]{190441};
                this.q = new int[]{1};
                this.A = new int[this.p.length];
                this.l = 19574319;
                this.f = new int[]{49312, 20282, 40308};
                this.g = new int[]{2, 1, 6000};
                this.h = new int[3];
                this.i = new int[]{640507, 640508, 640509, 640510};
                this.j = new int[]{1, 1, 1, 1};
                this.k = new int[4];
                this.e = new int[]{32720, 32893, 7783};
                break;
            }
            case 349: {
                this.b = 52;
                this.c = 53;
                this.p = new int[]{190386, 190385, 190413, 190384};
                this.q = new int[]{15, 15, 15, 15};
                this.A = new int[this.p.length];
                this.l = 35343790;
                this.f = new int[]{49312, 40029, 640728};
                this.g = new int[]{2, 100, 5};
                this.h = new int[3];
                this.e = new int[]{32741, 32959, 7783};
                break;
            }
            case 350: {
                break;
            }
        }
    }

    public int a() {
        return this.a;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public int d() {
        return this.d;
    }

    public int[] e() {
        return this.e;
    }

    public int[] f() {
        return this.f;
    }

    public int[] g() {
        return this.g;
    }

    public int[] h() {
        return this.h;
    }

    public int[] i() {
        return this.i;
    }

    public int[] j() {
        return this.j;
    }

    public int[] k() {
        return this.k;
    }

    public int l() {
        return this.l;
    }

    public String m() {
        return this.m;
    }

    public int n() {
        return this.n;
    }

    public boolean o() {
        return this.o;
    }

    public int[] p() {
        return this.p;
    }

    public int[] q() {
        return this.q;
    }

    public int[] r() {
        return this.r;
    }

    public int[] s() {
        return this.s;
    }

    public int[] t() {
        return this.t;
    }

    public int[] u() {
        return this.u;
    }

    public int[] v() {
        return this.v;
    }

    public boolean w() {
        return this.w;
    }

    public void a(boolean isQuestEnd) {
        this.w = isQuestEnd;
    }

    public boolean x() {
        return this.x;
    }

    public void b(boolean isQuestComplete) {
        this.x = isQuestComplete;
    }

    public u y() {
        return this.y;
    }

    public void a(u owner) {
        this.y = owner;
    }

    public int z() {
        return this.z;
    }

    public void d(int currentLevel) {
        this.z = currentLevel;
    }

    public int[] A() {
        return this.A;
    }

    public void a(int[] currentNpcCount) {
        this.A = currentNpcCount;
    }

    public int[] B() {
        return this.B;
    }

    public void b(int[] currentItemCount) {
        this.B = currentItemCount;
    }

    public int[] C() {
        return this.C;
    }

    public void c(int[] currentUseItemCount) {
        this.C = currentUseItemCount;
    }
}

