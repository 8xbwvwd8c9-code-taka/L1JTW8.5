/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aq;
import aq.f;
import be.bi;
import be.ch;
import be.dh;
import be.ep;
import bf.a;
import bh.v;

public class cj
extends a {
    private final int a = 97;
    private final v b = be.a().a(97);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        _user.j(97, this.b.v() * 1000);
        if (_user instanceof u) {
            u pc = (u)_user;
            pc.a(new bi(pc.fr(), 1));
            for (u other : aq.a().f(pc)) {
                if (other.bB(26003)) {
                    other.a(new ep(pc.fr(), true));
                    continue;
                }
                other.a(new dh(pc));
            }
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new bi(pc.fr(), 0));
            for (u other : aq.a().f(pc)) {
                if (other.bB(26003)) {
                    other.a(new ep(pc.fr(), false));
                    continue;
                }
                other.a(new ch(pc));
            }
        }
    }
}

