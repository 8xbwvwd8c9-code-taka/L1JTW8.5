/*
 * Decompiled with CFR 0.152.
 */
package ap;

import ao.g;
import ao.q;
import ap.aa;
import ap.f;
import ap.t;
import ap.u;
import aq.aj;
import aq.am;
import aq.ap;
import aq.aq;
import aq.e;
import aq.i;
import be.ds;
import be.o;
import bh.l;

public class d
extends t {
    public d(l template) {
        super(template);
    }

    @Override
    public void c(u picker) {
        if (picker.aF() == 0) {
            return;
        }
        i clan = ao.q.a().a(picker.aF());
        if (clan == null) {
            return;
        }
        if (!picker.x()) {
            return;
        }
        if (picker.fe() != 0 && picker.fe() != 1) {
            return;
        }
        if (picker.fr() != clan.k()) {
            return;
        }
        if (!this.d(picker)) {
            return;
        }
        if (clan.m() != 0) {
            picker.a(new ds(474));
            return;
        }
        int castle_id = aq.e.a(this.fs(), this.ft(), this.fp());
        boolean existDefenseClan = false;
        i defence_clan = null;
        for (i defClan : ao.q.a().b().values()) {
            if (castle_id != defClan.m()) continue;
            defence_clan = ao.q.a().c(defClan.f());
            existDefenseClan = true;
            break;
        }
        boolean in_war = aq.a().b(clan.f());
        if (existDefenseClan && !in_war) {
            return;
        }
        if (existDefenseClan && defence_clan != null) {
            defence_clan.g(0);
            ao.q.a().b(defence_clan);
        }
        clan.g(castle_id);
        ao.q.a().b(clan);
        bh.d l1castale = ao.g.a().a(castle_id);
        l1castale.c(clan.e());
        aq.a().a(new o(castle_id, picker.fr()));
        int[] loc = new int[3];
        for (u pc : aq.a().c()) {
            if (pc.aF() == picker.aF() || !aq.e.a(castle_id, pc)) continue;
            loc = aq.e.e(castle_id);
            int locx = loc[0];
            int locy = loc[1];
            short mapid = (short)loc[2];
            am.a(pc, locx, locy, mapid, 5, true);
        }
        ap war = aq.a().c(clan.f());
        if (war != null && existDefenseClan) {
            war.a(clan.f());
        }
        if (!clan.b().isEmpty()) {
            ds s_serverMessage = new ds(643);
            for (u pc : clan.b()) {
                pc.a(s_serverMessage);
            }
        }
        this.aa_();
        for (aq.aa l1object : aq.a().b()) {
            aa tower;
            if (!(l1object instanceof aa) || !aq.e.a(castle_id, tower = (aa)l1object)) continue;
            tower.aa_();
        }
        aj.a().a(castle_id);
        f[] fArray = ao.t.b().c();
        int n2 = fArray.length;
        int n3 = 0;
        while (n3 < n2) {
            f door = fArray[n3];
            if (aq.e.a(castle_id, door)) {
                door.h();
            }
            ++n3;
        }
    }

    private boolean d(u pc) {
        return this.fs() - 1 <= pc.fs() && pc.fs() <= this.fs() + 1 && this.ft() - 1 <= pc.ft() && pc.ft() <= this.ft() + 1;
    }
}

