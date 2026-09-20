/*
 * Decompiled with CFR 0.152.
 */
package be;

import ap.u;
import be.eu;

public class do
extends eu {
    public do(u pc) {
        this.c(152);
        if (pc.bB(1004)) {
            this.b(pc.eV() - pc.eW() - 2);
        } else {
            this.b(pc.eV() - pc.eW());
        }
        this.b(pc.W_() - pc.bp());
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_SPMR";
    }
}

