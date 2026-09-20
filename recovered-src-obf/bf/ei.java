/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import bf.a;
import bh.v;
import java.util.ArrayList;

public class ei
extends a {
    private final int a = 161;
    private final v b = be.a().a(161);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        this.a(_user, this.b);
        ArrayList<aa> objects = aq.a().b((aa)_user, this.b.q());
        for (aa obj : objects) {
            w magic;
            boolean isSeccess;
            f target;
            if (!(obj instanceof f) || (target = (f)obj).eX() || target.ff() || !(isSeccess = (magic = new w(_user, target)).a(161))) continue;
            target.j(161, this.b.v() * 1000);
            this.c(target, this.b.u());
            this.b(_user, 715);
        }
    }

    @Override
    public void a(f cha) {
    }
}

