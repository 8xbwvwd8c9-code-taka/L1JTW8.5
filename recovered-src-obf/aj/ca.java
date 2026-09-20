/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import be.dt;
import bj.d;

public class ca
extends cv {
    private static final String a = "[C] C_ServerVersion";

    public ca(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        this.d();
        this.c();
        int clientLanguage = this.b();
        int unknownVer1 = this.d();
        int unknownVer2 = this.d();
        int clientVersion = this.b();
        client.a(new dt());
    }

    @Override
    public String a() {
        return a;
    }
}

