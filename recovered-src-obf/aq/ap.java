/*
 * Decompiled with CFR 0.152.
 */
package aq;

import ao.g;
import ao.q;
import ap.r;
import ap.u;
import aq.aa;
import aq.aq;
import aq.e;
import aq.i;
import be.dc;
import be.ds;
import be.eq;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ap {
    private static final Logger c = Logger.getLogger(ap.class.getName());
    public static final int a = 1;
    public static final int b = 2;
    private final i d;
    private int e = 0;
    private boolean f = false;
    private final ConcurrentHashMap<String, i> g = new ConcurrentHashMap();
    private Calendar h;
    private int i = 0;

    public int a() {
        Calendar now = Calendar.getInstance();
        return (int)(this.h.getTimeInMillis() / 1000L - now.getTimeInMillis() / 1000L);
    }

    public ap(int war_type, String attack_clan_name, String defence_clan_name) {
        i defence_clan = q.a().c(defence_clan_name);
        i attack_clan = q.a().c(attack_clan_name);
        this.e = war_type;
        this.d = defence_clan;
        this.g.put(attack_clan_name, attack_clan);
        if (war_type == 1) {
            this.i = this.d.m();
            this.h = ao.g.a().a(this.i).d();
            bi.e.a().a(new a(), 1000L);
        } else if (war_type == 2) {
            bi.e.a().a(new b(), 60000L);
        }
        aq.a().a(this);
        this.b(attack_clan);
    }

    private void b(i attackClan) {
        for (u pc : attackClan.b()) {
            if (this.e == 1) {
                pc.a(new dc(76, pc));
            }
            pc.a(new eq(1, attackClan.f(), this.d.f()));
        }
        for (u pc : this.d.b()) {
            if (this.e == 1) {
                pc.a(new dc(76, pc));
            }
            pc.a(new eq(1, attackClan.f(), this.d.f()));
        }
    }

    public void a(String clan1_name, String clan2_name) {
        i clan1 = q.a().c(clan1_name);
        for (u pc : clan1.b()) {
            pc.a(new dc(76, 0));
            pc.a(new eq(2, clan1_name, clan2_name));
        }
        i clan2 = q.a().c(clan2_name);
        for (u pc : clan2.b()) {
            pc.a(new eq(4, clan2_name, clan1_name));
        }
        if (this.e == 1) {
            this.g.remove(clan1_name);
        }
        if (this.e == 2 || this.g.isEmpty()) {
            this.f = true;
            aq.a().b(this);
        }
        aq.a().a(new ds(231, clan2_name, clan1_name));
    }

    public void b(String clan1_name, String clan2_name) {
        i clan1 = q.a().c(clan1_name);
        for (u pc : clan1.b()) {
            pc.a(new dc(76, 0));
            pc.a(new eq(3, clan1_name, clan2_name));
        }
        i clan2 = q.a().c(clan2_name);
        for (u pc : clan2.b()) {
            pc.a(new eq(3, clan1_name, clan2_name));
        }
        if (this.e == 1) {
            this.g.remove(clan1_name);
        }
        if (this.e == 2 || this.g.isEmpty()) {
            this.f = true;
            aq.a().b(this);
        }
        aq.a().a(new ds(227, clan1_name, clan2_name));
    }

    public void a(String clan_name) {
        aq.a().a(new ds(231, clan_name, this.d.f()));
        for (u element : this.d.b()) {
            for (String name : this.g.keySet()) {
                element.a(new dc(76, 0));
                element.a(new eq(3, this.d.f(), name));
            }
        }
        for (i attackClan : this.g.values()) {
            aq.a().a(new ds(227, this.d.f(), attackClan.f()));
            for (u element2 : attackClan.b()) {
                element2.a(new dc(76, 0));
                element2.a(new eq(3, attackClan.f(), this.d.f()));
            }
        }
        if (this.d.f().contains("\u5b89\u5b89\u59b3\u597d\u518d\u898b_")) {
            for (aa obj : aq.a().g().values()) {
                r keeper;
                if (!(obj instanceof r) || !aq.e.a(this.i, keeper = (r)obj)) continue;
                keeper.aa_();
            }
        }
        this.f = true;
        aq.a().b(this);
    }

    private void f() {
        for (i attackClan : this.g.values()) {
            aq.a().a(new ds(231, this.d.f(), attackClan.f()));
            aq.a().a(new ds(227, this.d.f(), attackClan.f()));
            for (u member : attackClan.b()) {
                member.a(new dc(76, 0));
                member.a(new eq(3, attackClan.f(), this.d.f()));
            }
        }
        for (u member : this.d.b()) {
            for (String name : this.g.keySet()) {
                member.a(new dc(76, 0));
                member.a(new eq(4, this.d.f(), name));
            }
        }
        this.f = true;
        aq.a().b(this);
    }

    public boolean b(String clanName) {
        return this.g.containsKey(clanName) || this.d.f().equalsIgnoreCase(clanName);
    }

    public boolean c(String player_clan_name, String target_clan_name) {
        return this.b(player_clan_name) && this.b(target_clan_name);
    }

    public ArrayList<i> c(String clanName) {
        ArrayList<i> list = new ArrayList<i>();
        if (this.d.f().equalsIgnoreCase(clanName)) {
            list.addAll(this.g.values());
        } else {
            list.add(this.d);
        }
        return list;
    }

    public void a(i attack_clan) {
        if (!this.g.containsKey(attack_clan.f())) {
            this.g.put(attack_clan.f(), attack_clan);
        }
        this.b(attack_clan);
    }

    public i b() {
        return this.d;
    }

    public int c() {
        return this.e;
    }

    public int d() {
        return this.i;
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                if (ap.this.f) {
                    return;
                }
                Calendar now = Calendar.getInstance();
                if (now.after(ap.this.h)) {
                    ap.this.f();
                    return;
                }
                this.a(1000L);
            }
            catch (Exception e2) {
                c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }

        private void a(long delay) {
            bi.e.a().a(this, delay);
        }
    }

    private class b
    extends TimerTask {
        private int b = 0;

        private b() {
        }

        @Override
        public void run() {
            try {
                if (ap.this.f) {
                    return;
                }
                if (++this.b > 240) {
                    for (i attackClan : ap.this.g.values()) {
                        ap.this.b(attackClan.f(), ap.this.d.f());
                    }
                    return;
                }
                this.a(60000L);
            }
            catch (Exception e2) {
                c.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }

        private void a(long delay) {
            bi.e.a().a(this, delay);
        }
    }
}

