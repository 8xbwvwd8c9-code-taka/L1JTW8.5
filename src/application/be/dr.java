/*
 * Decompiled with CFR 0.152.
 */
package be;

import be.eu;

public class dr
extends eu {
    public dr(String senderName, int mapId, int x2, int y2, int msgId) {
        this.c(121);
        this.c(111);
        this.a(senderName);
        this.b(mapId);
        this.b(x2);
        this.b(y2);
        this.c(msgId);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_SendLocation";
    }
}

