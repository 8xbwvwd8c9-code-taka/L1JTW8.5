/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import aq.f;
import be.eu;

public class az
extends eu {
    public az(int objId, int hpRatio, int mpRatio) {
        this.a(objId, hpRatio, mpRatio);
    }

    public az(f cha) {
        int mpRatio;
        int objId = cha.fr();
        int hpRatio = cha.ea() <= 0 ? 255 : 100 * cha.ea() / cha.ew();
        int n2 = mpRatio = cha.ex() == 0 ? 255 : 100 * cha.eb() / cha.ex();
        if (!(cha instanceof u)) {
            mpRatio = 255;
        }
        this.a(objId, hpRatio, mpRatio);
    }

    private void a(int objId, int hpRatio, int mpRatio) {
        this.c(79);
        this.a(objId);
        this.c(hpRatio);
        this.c(mpRatio);
        this.b(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_HPMeter";
    }
}

