/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ap.c;
import ap.u;
import aq.aa;
import aq.aq;
import bh.b;
import bj.d;

public class l
extends cv {
    private static final String a = "[C] C_BoardDelete";

    public l(byte[] decrypt, d client) {
        super(decrypt);
        int objId = this.b();
        int topicId = this.b();
        aa obj = aq.a().a(objId);
        u pc = client.f();
        if (pc == null || !(obj instanceof c)) {
            return;
        }
        if (obj.fu().c(pc.fu()) > 11) {
            return;
        }
        b topic = b.a(topicId);
        if (topic == null) {
            this.b(topicId);
            return;
        }
        String name = pc.et();
        if (!name.equals(topic.b())) {
            this.a(topic, name);
            return;
        }
        topic.f();
    }

    private void b(int topicId) {
        System.out.println(String.format("Illegal board deletion request: Topic id <%d> does not exist.", topicId));
    }

    private void a(b topic, String name) {
        System.out.println(String.format("Illegal board deletion request: Name <%s> expected but was <%s>.", topic.b(), name));
    }

    @Override
    public String a() {
        return a;
    }
}

