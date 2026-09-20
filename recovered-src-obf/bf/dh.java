/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ap.u;
import aq.f;
import be.cm;
import be.do;
import bf.a;

public class dh
extends a {
    private final int a = 121;

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == 0) {
            u tpc;
            _target.bz(121);
            if (_target instanceof u && (tpc = (u)_target).q()) {
                int count = tpc.aL().b();
                if (count >= 1 && count <= 4) {
                    tpc.co(8);
                    tpc.bt(1);
                    tpc.a(new cm(180, 477, 3430, 1));
                } else if (count >= 5 && count <= 6) {
                    tpc.co(9);
                    tpc.cd(2);
                    tpc.bt(2);
                    tpc.a(new cm(180, 478, 3431, 1));
                } else if (count >= 7) {
                    tpc.co(10);
                    tpc.cd(2);
                    tpc.ch(2);
                    tpc.bt(3);
                    tpc.a(new cm(180, 479, 3432, 1));
                }
                tpc.a(new do(tpc));
                _target.j(121, 86400000);
            }
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            if (pc.dP() == 1) {
                pc.co(-8);
                pc.a(new cm(180, 477, 3430, 0));
            } else if (pc.dP() == 2) {
                pc.co(-9);
                pc.cd(-2);
                pc.a(new cm(180, 478, 3431, 0));
            } else if (pc.dP() == 3) {
                pc.co(-10);
                pc.cd(-2);
                pc.ch(-2);
                pc.a(new cm(180, 479, 3432, 0));
            }
            pc.bt(0);
            pc.a(new do(pc));
        }
    }
}

