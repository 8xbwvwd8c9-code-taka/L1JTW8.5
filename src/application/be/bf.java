/*
 * Decompiled with CFR 0.152.
 */
package be;

import ao.af;
import ap.q;
import be.eu;

public class bf
extends eu {
    public bf(q item) {
        this.a(item);
    }

    private void a(q item) {
        this.c(204);
        this.b(item.a().q());
        StringBuilder name = new StringBuilder();
        if (item.F() == 0) {
            name.append("$227 ");
        } else if (item.F() == 2) {
            name.append("$228 ");
        }
        name.append(item.a().j());
        if (item.N() == 40312 && item.M() != 0) {
            name.append(af.a(item));
        }
        if (item.g()) {
            this.b(134);
            this.c(3);
            this.a(name.toString());
            this.a(String.valueOf(item.a().v()) + "+" + item.G());
            this.a(String.valueOf(item.a().w()) + "+" + item.G());
        } else if (item.h()) {
            if (item.N() == 20383) {
                this.b(137);
                this.c(3);
                this.a(name.toString());
                this.a(String.valueOf(item.I()));
            } else {
                this.b(135);
                this.c(2);
                this.a(name.toString());
                this.a(String.valueOf(Math.abs(item.a().X())) + "+" + item.G());
            }
        } else if (item.f()) {
            if (item.a().aP() == 1) {
                this.b(137);
                this.c(3);
                this.a(name.toString());
                this.a(String.valueOf(item.I()));
            } else if (item.a().aP() == 2) {
                this.b(138);
                this.c(2);
                name.append(": $231 ");
                name.append(String.valueOf(item.M()));
                this.a(name.toString());
            } else if (item.a().aP() == 7) {
                this.b(136);
                this.c(3);
                this.a(name.toString());
                this.a(String.valueOf(item.a().V()));
            } else {
                this.b(138);
                this.c(2);
                this.a(name.toString());
            }
            this.a(String.valueOf(item.p()));
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_IdentifyDesc";
    }
}

