/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.t;
import ap.u;
import aq.aa;
import aq.ai;
import aq.aq;
import aq.f;
import aq.w;
import be.cn;
import bf.a;
import bh.v;
import bi.i;

public class fj
extends a {
    private final int a = 192;
    private final v b = be.a().a(192);

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
        w magic = new w(_user, target);
        int dmg = magic.b(192);
        magic.a(dmg, 0);
        boolean isSuccess = magic.a(192);
        if (isSuccess && !target.bB(192) && !target.bB(1028)) {
            int[] times = new int[]{1000, 2000, 3000, 4000};
            int stopnDuration = times[i.a(times.length)];
            if (target instanceof u) {
                u tpc = (u)target;
                tpc.a(new cn(6, true));
            } else if (target instanceof t) {
                t npc = (t)target;
                npc.n(true);
            }
            target.cC(stopnDuration);
            target.j(192, 500);
        }
        this.b(_user, target, this.b, dmg);
    }

    @Override
    public void a(f cha) {
        int time = cha.fm();
        cha.j(1028, time);
        ai.a().a(4184, time, cha.fs(), cha.ft(), cha.fp());
    }
}

