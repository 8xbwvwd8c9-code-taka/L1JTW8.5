/*
 * Decompiled with CFR 0.152.
 */
package be;

import aj.bp;
import ao.q;
import aq.aq;
import aq.i;
import be.eu;
import bi.g;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class cw
extends eu {
    private static final Logger a = Logger.getLogger(cw.class.getName());

    public cw(int ClanId) {
        i clan = q.a().a(ClanId);
        this.c(121);
        this.c(167);
        this.a(clan.f());
        this.a(clan.l());
        this.a(clan.i());
        this.c(clan.n() > 0 ? 1 : 0);
        this.c(clan.m() > 0 ? 1 : 0);
        this.c(aq.a().b(clan.f()) ? 1 : 0);
        this.a((int)(clan.g().getTime() / 1000L));
        try {
            byte[] clanText = clan.h().getBytes(aV);
            byte[] text = Arrays.copyOf(clanText, 478);
            this.a(text);
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    public cw(ArrayList<bp.a> list) {
        this.c(121);
        this.c(170);
        this.b(1);
        this.c(list.size());
        for (bp.a data : list) {
            this.a(data.a);
            this.c(data.b);
            this.c(data.c);
            byte[] text = Arrays.copyOf(g.b(data.g), 62);
            this.a(text);
            this.a(data.d);
            this.c(data.e);
            this.a(data.f);
        }
    }

    public cw(String name, String notes) {
        this.c(121);
        this.c(169);
        this.a(name);
        try {
            byte[] text = Arrays.copyOf(notes.getBytes(aV), 62);
            this.a(text);
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
        }
    }

    @Override
    public byte[] a() {
        return this.d();
    }

    @Override
    public String b() {
        return "S_Pledge";
    }
}

