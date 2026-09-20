/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.aa;
import aq.aq;
import bh.b;
import bj.d;

public class o
extends cv {
    private static final String a = "[C] C_BoardWrite";

    public o(byte[] decrypt, d client) {
        super(decrypt);
        int id = this.b();
        String title = this.g();
        String content = this.g();
        aa tg = aq.a().a(id);
        if (tg == null) {
            System.out.println("\u4e0d\u6b63\u78ba\u7684 NPCID : " + id);
            return;
        }
        u pc = client.f();
        b.a(pc.et(), title, content);
        pc.j().b(40308, 300);
    }

    @Override
    public String a() {
        return a;
    }
}

