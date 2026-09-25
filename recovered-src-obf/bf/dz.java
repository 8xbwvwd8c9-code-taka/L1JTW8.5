/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.aa;
import aq.aq;
import aq.f;
import aq.w;
import be.ea;
import bf.a;
import bh.v;

public class dz
extends a {
    private final int a = 152;
    private final v b = be.a().a(152);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            _target.j(152, this.b.v() * 1000);
            switch (_target.fc()) {
                case 1: {
                    int[] status;
                    int[] nArray = status = new int[]{43, 54, 1001};
                    int n2 = status.length;
                    int n3 = 0;
                    while (n3 < n2) {
                        int id = nArray[n3];
                        if (_target.bB(id)) {
                            _target.bz(id);
                            _target.cu(0);
                        }
                        ++n3;
                    }
                    break;
                }
                case 0: 
                case 2: {
                    int type = 2;
                    int time = this.b.v();
                    if (_target instanceof u) {
                        u pc = (u)_target;
                        pc.a(new ea(pc.fr(), 2, time));
                    }
                    _target.b(new ea(_target.fr(), 2, time));
                    _target.cu(2);
                }
            }
            this.a(_target, this.b);
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        if (target instanceof u && ((u)target).bU() > 0) {
            this.b(_user, 280);
            return;
        }
        w _magic = new w(_user, target);
        boolean isSuccess = _magic.a(152);
        if (isSuccess) {
            target.j(152, this.b.v() * 1000);
            switch (target.fc()) {
                case 1: {
                    int[] status;
                    int[] nArray = status = new int[]{43, 54, 1001};
                    int n2 = status.length;
                    int n3 = 0;
                    while (n3 < n2) {
                        int id = nArray[n3];
                        if (target.bB(id)) {
                            target.bz(id);
                            target.cu(0);
                        }
                        ++n3;
                    }
                    break;
                }
                case 0: 
                case 2: {
                    int type = 2;
                    int time = this.b.v();
                    if (target instanceof u) {
                        u pc = (u)target;
                        pc.a(new ea(pc.fr(), 2, time));
                    }
                    target.b(new ea(target.fr(), 2, time));
                    target.cu(2);
                }
            }
            this.a(target, this.b);
        } else {
            this.b(_user, 280);
        }
    }

    @Override
    public void a(f cha) {
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ea(pc.fr(), 0, 0));
            pc.b(new ea(pc.fr(), 0, 0));
        }
        cha.cu(0);
    }
}

