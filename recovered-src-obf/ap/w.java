/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.au;
import ap.k;
import ap.t;
import ap.u;
import be.be;
import bh.l;

public class w
extends t {
    public w(l template) {
        super(template);
    }

    @Override
    public void Z_() {
        int npcId = this.U_().b();
        if (this.ae()) {
            return;
        }
        if (npcId == 71075 || npcId == 70957 || npcId == 81209) {
            return;
        }
        this.w = false;
        this.q();
    }

    @Override
    public void a(u pc, String action) {
        if (action.equalsIgnoreCase("start")) {
            int npcId = this.U_().b();
            if ((npcId == 71092 || npcId == 71093) && pc.z() && pc.bb().a(3) == 4) {
                l l1npc = au.a().a(71093);
                new k(l1npc, this, pc);
                pc.a(new be(this.fr(), ""));
            } else if (npcId == 71094 && pc.C() && pc.bb().a(4) == 2) {
                l l1npc = au.a().a(71094);
                new k(l1npc, this, pc);
                pc.a(new be(this.fr(), ""));
            } else if (npcId == 70957 || npcId == 81209) {
                l l1npc = au.a().a(70957);
                new k(l1npc, this, pc);
                pc.a(new be(this.fr(), ""));
            } else if (npcId == 81350 && pc.bb().a(4) == 3) {
                l l1npc = au.a().a(81350);
                new k(l1npc, this, pc);
                pc.a(new be(this.fr(), ""));
            }
        }
    }
}

