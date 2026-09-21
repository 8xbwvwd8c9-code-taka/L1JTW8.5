/*
 * Decompiled with CFR 0.152.
 */
package bg;

import aq.f;
import bi.e;

public class a {
    private a() {
    }

    public static void a(f cha, int time) {
        cha.W(true);
        e.a().a(new a(cha), time);
    }

    private static class a
    implements Runnable {
        private final f a;

        private a(f cha) {
            this.a = cha;
        }

        @Override
        public void run() {
            this.a.W(false);
        }
    }
}

