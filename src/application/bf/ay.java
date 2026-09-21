/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.s;
import ap.t;
import ap.u;
import ap.z;
import aq.aa;
import aq.ai;
import aq.aq;
import aq.f;
import aq.w;
import be.cn;
import be.cz;
import bf.a;
import bh.v;

public class ay
extends a {
    private final int a = 50;
    private final v b = be.a().a(50);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1 && !_target.bB(50)) {
            int time = this.b.v() * 1000;
            int type = 2;
            _target.j(50, time);
            _target.V(true);
            if (_target instanceof u) {
                u pc = (u)_target;
                pc.a(new cz(pc.fr(), 2));
                pc.b(new cz(pc.fr(), 2));
                pc.a(new cn(4, true));
            } else if (_target instanceof t) {
                t npc = (t)_target;
                npc.b(new cz(npc.fr(), 2));
            }
            this.a(_target, this.b);
            ai.a().a(this.b.u(), time, _target.fs(), _target.ft(), _target.fp());
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        w magic = new w(_user, target);
        int dmg = magic.b(50);
        magic.a(dmg, 0);
        this.b(_user, target, this.b, dmg);
        boolean isSuccess = magic.a(50);
        if (isSuccess && !target.bB(50)) {
            int time = this.b.v() * 1000;
            int type = 2;
            target.j(50, time);
            target.V(true);
            if (target instanceof u) {
                u pc = (u)target;
                pc.a(new cz(pc.fr(), 2));
                pc.b(new cz(pc.fr(), 2));
                pc.a(new cn(4, true));
            } else if (target instanceof t) {
                t npc = (t)target;
                npc.b(new cz(npc.fr(), 2));
            }
            this.a(target, this.b);
            ai.a().a(this.b.u(), time, target.fs(), target.ft(), target.fp());
        }
    }

    @Override
    public void a(f cha) {
        cha.V(false);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cz(pc.fr(), 0));
            pc.b(new cz(pc.fr(), 0));
            pc.a(new cn(4, false));
        } else if (cha instanceof s || cha instanceof z || cha instanceof ap.v) {
            t npc = (t)cha;
            npc.b(new cz(npc.fr(), 0));
        }
    }
}

