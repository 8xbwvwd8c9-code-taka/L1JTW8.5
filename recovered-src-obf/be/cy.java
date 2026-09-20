/*
 * Decompiled with CFR 0.152.
 */
package be;

import ao.q;
import aq.i;
import be.eu;

public class cy
extends eu {
    public cy(i clan) {
        this.c(44);
        this.b(2);
        this.a(clan.t().size());
        for (int clanid : clan.t()) {
            i watch_clan = q.a().a(clanid);
            if (watch_clan == null) {
                this.a("null");
                continue;
            }
            this.a(watch_clan.f());
        }
    }

    public cy() {
        this.c(44);
        this.b(2);
        this.a(0);
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_ClanAttention";
    }
}

