/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ap.t;
import ap.u;
import aq.f;
import be.p;
import bh.l;
import bi.b;

public class x
extends t {
    public x(l template) {
        super(template);
    }

    @Override
    public void b(f attacker, int damage) {
        if (this.ea() > 0 && !this.eX() && damage > 0) {
            if (this.fb() < 7) {
                this.ct(this.fb() + 1);
            } else {
                this.ct(0);
            }
            this.b(new p(this));
            if (attacker instanceof u) {
                u pc = (u)attacker;
                pc.a(this);
                if (pc.ev() < 5) {
                    this.n.a(pc, 1);
                    bi.b.a(pc, this, this.n);
                }
            }
        }
        this.n.a();
    }
}

