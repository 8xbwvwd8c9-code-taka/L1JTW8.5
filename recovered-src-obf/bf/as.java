/*
 * Decompiled with CFR 0.152.
 */
package bf;

import ao.be;
import ap.t;
import ap.u;
import aq.aa;
import aq.ae;
import aq.aq;
import aq.f;
import aq.w;
import be.al;
import be.cf;
import be.dx;
import be.ea;
import be.r;
import bf.a;
import bg.d;
import bh.v;
import java.util.HashMap;

public class as
extends a {
    private final int a = 44;
    private final v b = be.a().a(44);

    @Override
    public void a(f _target, int timeSecs) {
        if (timeSecs == -1) {
            this.a(_target, this.b);
        } else if (timeSecs == 0) {
            return;
        }
        if (_target instanceof u) {
            u pc = (u)_target;
            ae.b(pc);
            if (pc.bU() > 0) {
                pc.cu(0);
                pc.a(new ea(pc.fr(), 0, 0));
                pc.b(new ea(pc.fr(), 0, 0));
            }
            if (pc.aX()) {
                pc.a(new al(pc.fr(), 70, pc.aW()));
                pc.b(new al(pc.fr(), 70, pc.aW()));
            }
        }
        HashMap<Integer, d> tempList = new HashMap<Integer, d>(_target.eh());
        for (int id : tempList.keySet()) {
            if (id >= 3999 || as.a(id)) continue;
            _target.bz(id);
        }
        _target.en();
        _target.ef();
    }

    @Override
    public void a(f _user, int _targetId, int x2, int y2, String message) {
        this.b(_user, this.b);
        aa obj = aq.a().a(_targetId);
        if (!(obj instanceof f)) {
            return;
        }
        f target = (f)obj;
        w _magic = new w(_user, target);
        if (_magic.a(44)) {
            if (target instanceof u) {
                u pc = (u)target;
                if (pc.bU() > 0) {
                    pc.cu(0);
                    pc.a(new ea(pc.fr(), 0, 0));
                    pc.b(new ea(pc.fr(), 0, 0));
                }
                if (pc.aX()) {
                    pc.a(new al(pc.fr(), 70, pc.aW()));
                    pc.b(new al(pc.fr(), 70, pc.aW()));
                }
            } else if (target instanceof t) {
                t npc = (t)target;
                this.a(npc);
                npc.cu(0);
                npc.cv(0);
                npc.b(new ea(target.fr(), 0, 0));
                npc.b(new dx(target.fr(), 0, 0));
                npc.h(false);
                npc.V(false);
                npc.l(0);
            }
            HashMap<Integer, d> tempList = new HashMap<Integer, d>(target.eh());
            for (int id : tempList.keySet()) {
                if (id >= 3999 || as.a(id)) continue;
                target.bz(id);
            }
            target.en();
            target.ef();
            this.a(target, this.b);
        } else {
            this.b(_user, 280);
        }
    }

    private void a(t npc) {
        int npcId = npc.U_().b();
        if (npcId == 71092 && npc.G() == npc.fe()) {
            npc.cw(1314);
            npc.b(new cf(npc.fr(), 1314, npc.fa(), npc.eY()));
        }
        if (npcId == 45640) {
            if (npc.G() == npc.fe()) {
                npc.a(npc.ew());
                npc.cw(2332);
                npc.b(new cf(npc.fr(), 2332, npc.fa(), npc.eY()));
                npc.e("$2103");
                npc.a("$2103");
                npc.b(new r(npc.fr(), "$2103"));
            } else if (npc.fe() == 2332) {
                npc.a(npc.ew());
                npc.cw(2755);
                npc.b(new cf(npc.fr(), 2755, npc.fa(), npc.eY()));
                npc.e("$2488");
                npc.a("$2488");
                npc.b(new r(npc.fr(), "$2488"));
            }
        }
        if (npcId == 81209 && npc.G() == npc.fe()) {
            npc.cw(4310);
            npc.b(new cf(npc.fr(), 4310, npc.fa(), npc.eY()));
        }
        if (npcId == 81352 && npc.G() == npc.fe()) {
            npc.cw(148);
            npc.b(new cf(npc.fr(), 148, npc.fa(), npc.eY()));
            npc.e("$6068");
            npc.a("$6068");
            npc.b(new r(npc.fr(), "$6068"));
        }
    }

    private static boolean a(int skillNum) {
        int[] cantCancelableSkill = new int[]{121, 12, 21, 8, 26, 42, 78, 79, 107, 99, 106, 111, 87, 88, 89, 90, 91, 185, 190, 195, 204, 209, 214, 219, 226, 1005};
        if (skillNum > 600 && skillNum < 620) {
            return true;
        }
        int[] nArray = cantCancelableSkill;
        int n2 = cantCancelableSkill.length;
        int n3 = 0;
        while (n3 < n2) {
            int id = nArray[n3];
            if (skillNum == id) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    @Override
    public void a(f cha) {
    }
}

