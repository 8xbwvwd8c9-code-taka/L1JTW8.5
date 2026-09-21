/*
 * Decompiled with CFR 0.152.
 */
package bd;

import ap.u;
import aq.aa;
import az.a;
import az.b;
import az.d;
import bd.i;
import bd.j;

public class f
extends i {
    private final String a;
    private final int b;
    private final int c;
    private final int d;

    public f(j storage) {
        super(storage);
        this.a = storage.a("poisonType");
        this.b = storage.b("poisonDelay");
        this.c = storage.b("poisonTime");
        this.d = storage.b("poisonDamage");
    }

    @Override
    public void a(u trodFrom, aa trapObj) {
        this.a(trapObj);
        if (this.a.equals("d")) {
            az.a.a(trodFrom, trodFrom, this.c, this.d, 30);
        } else if (this.a.equals("s")) {
            az.d.b(trodFrom, 120);
        } else if (this.a.equals("p")) {
            az.b.a(trodFrom, this.b, this.c);
        }
    }
}

