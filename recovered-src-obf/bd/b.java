/*
 * Decompiled with CFR 0.152.
 */
package bd;

import ap.u;
import aq.aa;
import aq.f;
import bd.a;
import bd.i;
import bd.j;

public class b
extends i {
    private final a a;
    private final int b;
    private final int c;

    public b(j storage) {
        super(storage);
        this.a = new a(storage.b("dice"));
        this.b = storage.b("base");
        this.c = storage.b("diceCount");
    }

    @Override
    public void a(u trodFrom, aa trapObj) {
        this.a(trapObj);
        int dmg = this.a.a(this.c) + this.b;
        trodFrom.a((f)trodFrom, (double)dmg, false);
    }
}

