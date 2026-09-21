/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ap.t;
import ap.u;
import be.m;
import bh.l;

public class c
extends t {
    public c(l template) {
        super(template);
    }

    @Override
    public void c(u player) {
        player.a(new be.l(this.fr()));
    }

    @Override
    public void a(u player, int number) {
        player.a(new be.l(this.fr(), number));
    }

    public void b(u player, int number) {
        player.a(new m(number));
    }
}

