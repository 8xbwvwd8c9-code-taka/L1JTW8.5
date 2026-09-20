/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class cn
extends eu {
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 3;
    public static final int d = 4;
    public static final int e = 5;
    public static final int f = 6;
    public static final int g = 7;
    public static final int h = 8;
    public static final int i = 9;

    public cn(int type, boolean flag) {
        this.c(156);
        int value = 0;
        switch (type) {
            case 1: {
                value = flag ? 2 : 3;
                break;
            }
            case 2: {
                value = flag ? 4 : 5;
                break;
            }
            case 7: {
                value = flag ? 7 : 7;
                break;
            }
            case 3: {
                value = flag ? 10 : 11;
                break;
            }
            case 4: {
                value = flag ? 12 : 13;
                break;
            }
            case 5: {
                value = flag ? 22 : 23;
                break;
            }
            case 6: {
                value = flag ? 24 : 25;
                break;
            }
            case 8: {
                value = flag ? 26 : 27;
                break;
            }
            case 9: {
                value = flag ? 30 : 31;
            }
        }
        this.c(value);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Paralysis";
    }
}

