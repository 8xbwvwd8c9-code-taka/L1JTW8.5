/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.u;
import aq.aq;
import be.ab;
import be.ds;
import bj.d;
import l1j.server.a;

public class x
extends cv {
    public x(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        u whisperFrom = client.f();
        if (whisperFrom == null) {
            return;
        }
        String targetName = this.g();
        String text = this.g();
        if (whisperFrom.bB(1005)) {
            whisperFrom.a(new ds(242));
            return;
        }
        if (whisperFrom.ev() < a.O) {
            whisperFrom.a(new ds(404, String.valueOf(a.O)));
            return;
        }
        u whisperTo = aq.a().a(targetName);
        if (whisperTo == null) {
            whisperFrom.a(new ds(73, targetName));
            return;
        }
        if (whisperTo.equals(whisperFrom)) {
            return;
        }
        if (whisperTo.cd().c(whisperFrom.et())) {
            whisperFrom.a(new ds(117, whisperTo.et()));
            return;
        }
        if (!whisperTo.ck()) {
            whisperFrom.a(new ds(205, whisperTo.et()));
            return;
        }
        whisperFrom.a(new ab(whisperTo, text, 9));
        whisperTo.a(new ab(whisperFrom, text, 16));
    }

    @Override
    public String a() {
        return "C_ChatWhisper";
    }
}

