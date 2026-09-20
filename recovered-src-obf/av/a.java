/*
 * Decompiled with CFR 0.152.
 */
package av;

import ap.q;
import ap.u;
import aq.f;
import bi.e;
import bj.d;

public class a {
    private a() {
    }

    public static void a(d client, q item) {
        int delayId = 0;
        int delayTime = 0;
        u pc = client.f();
        if (item.f()) {
            delayId = item.a().aJ();
            delayTime = item.a().aK();
        } else {
            if (item.g()) {
                return;
            }
            if (item.h()) {
                if (item.N() == 20077 || item.N() == 20062 || item.N() == 120077) {
                    if (item.D() && !pc.ff()) {
                        pc.O();
                    }
                } else {
                    return;
                }
            }
        }
        a timer = new a(pc, delayId);
        pc.a(delayId, timer);
        e.a().a(timer, delayTime);
    }

    public static class a
    implements Runnable {
        private final int a;
        private final f b;

        private a(f cha, int id) {
            this.b = cha;
            this.a = id;
        }

        @Override
        public void run() {
            this.a(this.a);
        }

        private void a(int delayId) {
            this.b.bE(delayId);
        }
    }
}

