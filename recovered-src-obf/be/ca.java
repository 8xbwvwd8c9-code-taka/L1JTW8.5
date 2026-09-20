/*
 * Decompiled with CFR 0.152.
 */
package be;

import ai.c;
import be.eu;

public class ca
extends eu {
    public ca(int type, String ... msgs) {
        this.c(168);
        this.b(0);
        this.a(c.e());
        this.b(type);
        String[] stringArray = msgs;
        int n2 = msgs.length;
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
        return "S_Message_YN";
    }
}

