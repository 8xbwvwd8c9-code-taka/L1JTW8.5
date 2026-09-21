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
import java.util.ArrayList;

public class by
extends a {
    private final int a = 76;
    private final v b = be.a().a(76);

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
        int area = this.b.q();
        ArrayList<f> list = this.a(_user, target, area);
        block4: for (f other : list) {
            w _magic;
            boolean isSuccess;
            if (other instanceof u && ((u)other).bU() > 0 || !(isSuccess = (_magic = new w(_user, other)).a(76))) continue;
            other.j(76, this.b.v() * 1000);
            this.b(other, 277);
            switch (other.fc()) {
                case 1: {
                    int[] status;
                    int[] nArray = status = new int[]{43, 54, 1001};
                    int n2 = status.length;
                    int n3 = 0;
                    while (n3 < n2) {
                        int id = nArray[n3];
                        if (other.bB(id)) {
                            other.bz(id);
                            other.cu(0);
                        }
                        ++n3;
                    }
                    continue block4;
                }
                case 0: 
                case 2: {
                    int type = 2;
                    int time = this.b.v();
                    if (other instanceof u) {
                        u pc = (u)other;
                        pc.a(new ea(pc.fr(), 2, time));
                    }
                    other.b(new ea(other.fr(), 2, time));
                    other.cu(2);
                }
            }
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

