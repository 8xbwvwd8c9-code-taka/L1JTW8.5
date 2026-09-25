/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;
import java.io.IOException;

public class bd
extends eu {
    public bd(int objId, int max, String htmlId) {
        this.c(193);
        this.a(objId);
        this.a(0);
        this.a(0);
        this.a(0);
        this.a(max);
        this.b(2);
        this.a("request");
        this.a(htmlId);
        this.b(0);
    }

    @Override
    public byte[] a() throws IOException {
        return this.d();
    }
}

