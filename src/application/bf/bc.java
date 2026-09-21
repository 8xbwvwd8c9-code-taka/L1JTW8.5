/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.u;
import aq.f;
import be.ea;
import bf.a;
import bh.v;

public class bc
extends a {
    private final int a = 54;
    private final v b = be.a().a(54);

    @Override
    public void a(f _target, int timeSecs) {
        if (_target instanceof u && ((u)_target).bU() > 0) {
            this.a(_target, this.b);
            return;
        }
        switch (_target.fc()) {
            case 0: 
            case 1: {
                if (timeSecs == 0) {
                    timeSecs = this.b.v();
                    this.a(_target, this.b);
                }
                _target.j(54, timeSecs * 1000);
                int msg = _target.fc() == 1 ? 183 : 184;
                this.b(_target, msg);
                boolean type = true;
                if (_target instanceof u) {
                    u pc = (u)_target;
                    pc.e(false);
                    pc.a(new ea(pc.fr(), 1, timeSecs));
                }
                _target.b(new ea(_target.fr(), 1, timeSecs));
                _target.cu(1);
                break;
            }
            case 2: {
                int[] status;
                int[] nArray = status = new int[]{29, 76, 152};
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
                this.a(_target, this.b);
            }
        }
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        if (_user instanceof u && ((u)_user).bU() > 0) {
            this.a(_user, this.b);
            return;
        }
        switch (_user.fc()) {
            case 0: 
            case 1: {
                int msg = _user.fc() == 1 ? 183 : 184;
                this.b(_user, msg);
                boolean type = true;
                if (_user instanceof u) {
                    u pc = (u)_user;
                    if (pc.bU() > 0) {
                        return;
                    }
                    pc.e(false);
                    pc.a(new ea(pc.fr(), 1, this.b.v()));
                }
                _user.b(new ea(_user.fr(), 1, this.b.v()));
                _user.cu(1);
                break;
            }
            case 2: {
                int[] status;
                int[] nArray = status = new int[]{29, 76, 152};
                int n2 = status.length;
                int n3 = 0;
                while (n3 < n2) {
                    int id = nArray[n3];
                    if (_user.bB(id)) {
                        _user.bz(id);
                        _user.cu(0);
                    }
                    ++n3;
                }
                break;
            }
        }
        this.a(_user, this.b);
    }

    @Override
    public void a(f cha) {
        cha.cu(0);
        if (cha instanceof u) {
            u pc = (u)cha;
            pc.a(new ea(pc.fr(), 0, 0));
            pc.b(new ea(pc.fr(), 0, 0));
        }
    }
}

