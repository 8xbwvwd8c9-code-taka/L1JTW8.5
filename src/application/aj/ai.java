/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.ab;
import ao.q;
import ap.f;
import ap.u;
import aq.aq;
import aq.i;
import bi.e;
import bj.d;
import java.util.TimerTask;

public class ai
extends cv {
    public ai(byte[] abyte0, d client) throws Exception {
        super(abyte0);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        this.d();
        this.d();
        int objectId = this.b();
        f door = (f)aq.a().a(objectId);
        if (door == null) {
            return;
        }
        if (door.i() == 6006) {
            if (door.o() == 28) {
                door.g();
            } else if (pc.j().b(640608, 1) || pc.j().b(40163, 1)) {
                door.f();
                new a(door).a();
            }
        } else if (door.i() == 6007) {
            if (door.o() == 28) {
                door.g();
            } else if (pc.j().b(40313, 1)) {
                door.f();
                new a(door).a();
            }
        } else if (!this.a(pc, door.p())) {
            if (door.o() == 28) {
                door.g();
            } else if (door.o() == 29) {
                door.f();
            }
        }
    }

    private boolean a(u pc, int keeperId) {
        bh.i house;
        int houseId;
        if (keeperId == 0) {
            return false;
        }
        i clan = q.a().a(pc.aF());
        return clan == null || (houseId = clan.n()) == 0 || keeperId != (house = ab.a().a(houseId)).f();
    }

    @Override
    public String a() {
        return "C_Door";
    }

    private class a
    extends TimerTask {
        private final f b;

        private a(f door) {
            this.b = door;
        }

        @Override
        public void run() {
            if (this.b.o() == 28) {
                this.b.g();
            }
        }

        private void a() {
            e.a().a(this, 15000L);
        }
    }
}

