/*
 * Decompiled with CFR 0.152.
 */
package az;

import ap.u;
import aq.f;
import be.ds;

public abstract class c {
    protected static boolean a(f cha) {
        if (cha == null) {
            return false;
        }
        if (cha.eo() != null) {
            return false;
        }
        if (!(cha instanceof u)) {
            return true;
        }
        u player = (u)cha;
        return !player.dL() && !player.bB(104);
    }

    protected static void a(f cha, int msgId) {
        if (!(cha instanceof u)) {
            return;
        }
        u player = (u)cha;
        player.a(new ds(msgId));
    }

    public abstract int a();

    public abstract void b();
}

