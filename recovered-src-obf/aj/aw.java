/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.aa;
import ao.ah;
import ao.av;
import ao.ax;
import ap.e;
import ap.q;
import ap.t;
import ap.u;
import ap.v;
import ap.z;
import aq.aq;
import au.f;
import au.g;
import be.bm;
import be.ds;
import bh.l;
import bh.o;
import bh.p;
import bi.i;
import bj.d;

public class aw
extends cv {
    private static final String a = "[C] C_GiveItem";
    private static final String[] b = new String[]{"L1Npc", "L1Monster", "L1Guardian", "L1Guard"};

    public aw(byte[] decrypt, d client) {
        super(decrypt);
        u pc = client.f();
        if (pc == null || pc.bN()) {
            return;
        }
        int targetId = this.b();
        this.d();
        this.d();
        int itemId = this.b();
        int count = this.b();
        aq.aa object = aq.a().a(targetId);
        if (object == null || !(object instanceof t)) {
            return;
        }
        t target = (t)object;
        if (!this.a(target.U_())) {
            return;
        }
        f targetInv = target.y();
        g inv = pc.j();
        q item = inv.e(itemId);
        if (item == null) {
            return;
        }
        if (item.D()) {
            pc.a(new ds(141));
            return;
        }
        if (!item.a().s()) {
            pc.a(new ds(210, item.a().h()));
            return;
        }
        if (item.F() >= 128) {
            pc.a(new ds(210, item.a().h()));
            return;
        }
        for (t petNpc : pc.ek().values()) {
            if (!(petNpc instanceof v)) continue;
            v pet = (v)petNpc;
            if (item.fr() != pet.k()) continue;
            pc.a(new ds(1187));
            return;
        }
        for (e doll : pc.el().values()) {
            if (doll.f() != item.fr()) continue;
            pc.a(new ds(1181));
            return;
        }
        if (targetInv.a(item, count) != 0) {
            pc.a(new ds(942));
            return;
        }
        item = inv.a(item, count, targetInv);
        target.a(item);
        target.fg();
        pc.fg();
        aa.a().a(pc, "\u7d66\u4e88(" + target.et() + ")", item, count);
        p petType = ax.b().a(target.U_().b());
        if (petType == null || target.eX()) {
            return;
        }
        if (item.N() == petType.d()) {
            this.a(pc, target);
        } else if (item.N() == petType.i()) {
            this.a(pc, target, item.N());
        }
        if (item.f()) {
            if (item.a().aP() == 7) {
                this.a(target, item);
            } else if (item.a().aP() == 11 && petType.j()) {
                this.b(target, item);
            }
        }
    }

    /*
     * Unable to fully structure code
     */
    private void a(t target, q item) {
        if (!(target instanceof v) || item.a().V() == 0) {
            return;
        }
        pet = (v)target;
        if (pet.fj() < 100) ** GOTO lbl9
        return;
        while (pet.y().b(item.N(), 1)) {
            value = pet.fj() + item.a().V() / 10;
            pet.c_(value > 100 ? 100 : value);
lbl9:
            // 2 sources

            if (pet.fj() < 100) continue;
        }
        ao.aw.a().a(pet);
    }

    private void b(t target, q item) {
        if (!(target instanceof v)) {
            return;
        }
        v pet = (v)target;
        o petItem = av.a().a(item.N());
        if (petItem.n() == 1) {
            pet.a(pet, item);
        } else if (petItem.n() == 0) {
            pet.b(pet, item);
        }
    }

    private boolean a(l npc) {
        String[] stringArray = b;
        int n2 = b.length;
        int n3 = 0;
        while (n3 < n2) {
            String impl = stringArray[n3];
            if (npc.d().equals(impl)) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    private void a(u pc, t target) {
        if (target instanceof v || target instanceof z) {
            return;
        }
        int petcost = 0;
        for (t petNpc : pc.ek().values()) {
            petcost += petNpc.Q();
        }
        int charisma = pc.eC();
        if (pc.x()) {
            charisma += 6;
        } else if (pc.A()) {
            charisma += 12;
        } else if (pc.B()) {
            charisma += 6;
        } else if (pc.C()) {
            charisma += 6;
        } else if (pc.D()) {
            charisma += 6;
        } else if (pc.E()) {
            charisma += 6;
        }
        g inv = pc.j();
        if ((charisma -= petcost) >= 6 && inv.c() < 180) {
            if (this.b(pc, target)) {
                q petamu = ah.a(pc, 40314, 1, 0, false);
                if (petamu != null) {
                    new v(target, pc, petamu.fr());
                    pc.a(new bm(petamu));
                }
            } else {
                pc.a(new ds(324));
            }
        }
    }

    private void a(u pc, t target, int itemId) {
        q highpetamu;
        v pet;
        if (!(target instanceof v)) {
            return;
        }
        g inv = pc.j();
        q petamu = inv.e((pet = (v)target).k());
        if (petamu == null) {
            return;
        }
        if ((pet.ev() >= 30 || itemId == 41310) && pc == pet.M() && (highpetamu = ah.a(pc, 40316, 1, 0, false)) != null) {
            pet.d(highpetamu.fr());
            pc.a(new bm(highpetamu));
            inv.b(petamu, 1);
        }
    }

    private boolean b(u pc, t npc) {
        if (pc.l()) {
            return true;
        }
        boolean isSuccess = false;
        int npcId = npc.U_().b();
        if (npcId == 45313) {
            if (npc.ew() / 3 > npc.ea() && i.a(16) == 15) {
                isSuccess = true;
            }
        } else if (npc.ew() / 3 > npc.ea()) {
            isSuccess = true;
        }
        return isSuccess;
    }

    @Override
    public String a() {
        return a;
    }
}

