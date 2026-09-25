/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.au;
import ao.g;
import ao.q;
import ap.t;
import ap.u;
import ap.z;
import bh.d;
import aq.i;
import bh.l;
import bj.d;
import java.util.ArrayList;

public class bh
extends cv {
    public bh(byte[] abyte0, d clientthread) throws Exception {
        super(abyte0);
        u pc = clientthread.f();
        if (pc == null) {
            return;
        }
        int objid = this.b();
        int size = this.d();
        int castaleID = this.d();
        int targetid = this.b();
        int count = this.d();
        bh.d l1castle = g.a().a(castaleID);
        if (l1castle == null) {
            return;
        }
        i clan = q.a().a(pc.aF());
        if (clan == null || clan.m() != castaleID || clan.k() != pc.fr() || l1castle.h() != pc.fr()) {
            return;
        }
        int petcost = 0;
        for (t petNpc : pc.ek().values()) {
            petcost += petNpc.Q();
        }
        int charisma = pc.eC() + 6 - petcost;
        int maxCount = Math.min(5, Math.min(l1castle.i(), Math.max(charisma / 6, 0)));
        if (count <= 0 || count > maxCount) {
            return;
        }

        ArrayList<d.a> used = new ArrayList<d.a>();
        ArrayList<l> templates = new ArrayList<l>();
        int i2 = 0;
        while (i2 < count) {
            d.a selected = null;
            for (d.a m2 : l1castle.k()) {
                if (m2.c <= 0) continue;
                selected = m2;
                break;
            }
            if (selected == null) {
                for (d.a slot : used) {
                    ++slot.c;
                }
                return;
            }
            l npcTemp = au.a().a(selected.a);
            if (npcTemp == null) {
                for (d.a slot : used) {
                    ++slot.c;
                }
                return;
            }
            --selected.c;
            used.add(selected);
            templates.add(npcTemp);
            ++i2;
        }

        if (!g.a().a(l1castle)) {
            for (d.a slot : used) {
                ++slot.c;
            }
            return;
        }

        for (l npcTemp : templates) {
            z summon = new z(npcTemp, pc);
            summon.o(6);
        }
    }

    @Override
    public String a() {
        return "C_MercenaryArrange";
    }
}

