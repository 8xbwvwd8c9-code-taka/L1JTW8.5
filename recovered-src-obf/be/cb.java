/*
 * Decompiled with CFR 0.152.
 */
package be;

import aq.f;
import be.eu;

public class cb
extends eu {
    private static final int[] a;
    private static final int[] b;

    static {
        int[] nArray = new int[8];
        nArray[1] = 1;
        nArray[2] = 1;
        nArray[3] = 1;
        nArray[5] = -1;
        nArray[6] = -1;
        nArray[7] = -1;
        a = nArray;
        int[] nArray2 = new int[8];
        nArray2[0] = -1;
        nArray2[1] = -1;
        nArray2[3] = 1;
        nArray2[4] = 1;
        nArray2[5] = 1;
        nArray2[7] = -1;
        b = nArray2;
    }

    public cb(f cha) {
        int heading = cha.fb();
        int x2 = cha.fs() - a[heading];
        int y2 = cha.ft() - b[heading];
        this.c(210);
        this.a(cha.fr());
        this.b(x2);
        this.b(y2);
        this.c(heading);
        this.b(128);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_MoveCharPacket";
    }
}

