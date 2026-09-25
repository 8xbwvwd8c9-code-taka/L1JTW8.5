/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.c;
import ap.u;
import ao.ah;
import be.ds;
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
        if (title == null || content == null || title.length() > 16 || content.length() > 1000) {
            return;
        }
        aa tg = aq.a().a(id);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        if (!(tg instanceof c)) {
            return;
        }
        if (tg.fu().c(pc.fu()) > 11) {
            return;
        }
        if (title == null || title.length() > 16) {
            pc.a(new ds(166, "標題過長"));
            return;
        }
        if (content == null || content.length() > 1000) {
            pc.a(new ds(166, "內容過長"));
            return;
        }
        if (!pc.j().b(40308, 300)) {
            return;
        }
        if (b.a(pc.et(), title, content) == null) {
            ah.a(pc, 40308, 300, 0, false);
        }
    }

    @Override
    public String a() {
        return a;
    }
}

