/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import be.cm;
import bf.a;
import bh.v;

public class fe
extends a {
    private final int a = 187;
    private final v b = be.a().a(187);

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
        if (_user instanceof u) {
            int[] weakness;
            u pc = (u)_user;
            pc.w(true);
            int i2 = 0;
            while (i2 < 3) {
                target.c(pc);
                ++i2;
            }
            pc.w(false);
            int[] nArray = weakness = new int[]{5001, 5002, 5003};
            int n2 = weakness.length;
            int n3 = 0;
            while (n3 < n2) {
                int buffid = nArray[n3];
                if (pc.bB(buffid)) {
                    pc.bA(buffid);
                    pc.a(new cm(75, 0));
                }
                ++n3;
            }
            pc.a(new be.aq(target.fs(), target.ft(), this.b.u()));
            pc.b(new be.aq(target.fs(), target.ft(), this.b.u()));
            this.a(_user, this.b);
        }
    }

    @Override
    public void a(f cha) {
    }
}

