/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import bj.d;

public class v
extends cv {
    public v(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int type = this.c();
        int result = this.c();
        switch (type) {
            case 0: {
                pc.q(result == 1);
                break;
            }
            case 2: {
                pc.m(result == 1);
                break;
            }
            case 6: {
                pc.n(result == 1);
                break;
            }
            case 9: {
                pc.o(result == 0);
                break;
            }
            case 10: {
                pc.p(result == 1);
            }
        }
    }

    @Override
    public String a() {
        return "C_ChatOnOff";
    }
}

