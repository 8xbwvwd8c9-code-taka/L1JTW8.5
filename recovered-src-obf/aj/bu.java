/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import aq.v;
import be.ce;
import bj.d;

public class bu
extends cv {
    public bu(byte[] decrypt, d client) {
        super(decrypt);
        boolean isPass;
        boolean bl2 = isPass = this.c() == 1;
        if (isPass) {
            client.i().clear();
            client.a(new ce(""));
            v.a(client);
            return;
        }
        String nextNews = client.b();
        if (nextNews != null) {
            client.a(new ce(nextNews));
        } else {
            client.a(new ce(""));
            v.a(client);
        }
    }

    @Override
    public String a() {
        return "C_ReadNews";
    }
}

