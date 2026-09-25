/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ei;
import bf.a;
import bh.v;

public class et
extends a {
    private final int a = 172;
    private final v b = be.a().a(172);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new ei("\u66b4\u98a8\u75be\u8d70 \uff08\u672a\u958b\u653e\uff09"));
        }
        this.a(_user, this.b);
    }

    @Override
    public void a(f cha) {
    }
}

