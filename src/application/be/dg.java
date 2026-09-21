/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class dg
extends eu {
    public dg(int type, String ... messages) {
        this.c(6);
        this.b(type);
        this.c(messages.length);
        String[] stringArray = messages;
        int n2 = messages.length;
        int n3 = 0;
        while (n3 < n2) {
            String msg = stringArray[n3];
            this.a(msg);
            ++n3;
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_RedMessage";
    }
}

