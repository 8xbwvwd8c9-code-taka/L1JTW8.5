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
import bf.a;
import bh.v;

public class gn
extends a {
    private final int a = 230;
    private final v b = be.a().a(230);

    @Override
    public void a(f _target, int timeSecs) {
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        this.b(_user, this.b);
        this.a(target, this.b);
        w magic = new w(_user, target);
        boolean isSuccess = magic.a(230);
        if (isSuccess && !target.bB(230)) {
            target.j(230, this.b.v() * 1000);
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new cn(9, true));
            } else if (target instanceof t) {
                t npc = (t)target;
                npc.V(true);
            }
            ai.a().a(this.b.u(), 6000, target.fs(), target.ft(), target.fp());
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new cn(9, false));
        } else if (cha instanceof s || cha instanceof z || cha instanceof ap.v) {
            t npc = (t)cha;
            npc.V(false);
        }
    }
}

