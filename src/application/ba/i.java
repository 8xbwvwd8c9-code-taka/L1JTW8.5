/*
 * Decompiled with CFR 0.152.
 */
package ba;

import ao.ab;
import ao.ah;
import ao.l;
import ap.q;
import ap.u;
import aq.aq;
import be.ds;
import bi.e;
import java.sql.Timestamp;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class i {
    private static final Logger a = Logger.getLogger(i.class.getName());
    private static i b;

    public static i a() {
        if (b == null) {
            b = new i();
        }
        return b;
    }

    public i() {
        e.a().a(new a(), 100L, 600000L);
    }

    private void c() throws Exception {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        for (bh.i house : ab.a().c().values()) {
            if (!house.g() || !house.j().before(current)) continue;
            this.b(house);
        }
    }

    private void b(bh.i house) throws Exception {
        int houseId = house.b();
        int price = house.k();
        int oldOwnerId = house.m();
        String bidder = house.n();
        int bidderId = house.o();
        if (oldOwnerId != 0 && bidderId != 0) {
            u oldOwnerPc = (u)aq.a().a(oldOwnerId);
            int payPrice = (int)((double)price * 0.9);
            if (oldOwnerPc != null) {
                ah.a(oldOwnerPc, 40308, payPrice);
                oldOwnerPc.a(new ds(527, String.valueOf(payPrice)));
            } else {
                q item = ah.a().b(40308);
                item.e(payPrice);
                l.a().a(oldOwnerId, item);
            }
            u bidderPc = (u)aq.a().a(bidderId);
            if (bidderPc != null) {
                bidderPc.a(new ds(524, String.valueOf(price), bidder));
            }
            this.a(houseId);
            this.a(houseId, bidderId);
            this.b(houseId);
        } else if (oldOwnerId == 0 && bidderId != 0) {
            u bidderPc = (u)aq.a().a(bidderId);
            if (bidderPc != null) {
                bidderPc.a(new ds(524, String.valueOf(price), bidder));
            }
            this.a(houseId, bidderId);
            this.b(houseId);
        } else if (oldOwnerId != 0 && bidderId == 0) {
            u oldOwnerPc = (u)aq.a().a(oldOwnerId);
            if (oldOwnerPc != null) {
                oldOwnerPc.a(new ds(528));
            }
            this.b(houseId);
        } else if (oldOwnerId == 0 && bidderId == 0) {
            Timestamp ts = new Timestamp(System.currentTimeMillis() + 432000000L);
            house.b(ts);
            house.d(100000);
            ab.a().a(house);
        }
    }

    private void a(int houseId) {
        for (aq.i clan : ao.q.a().b().values()) {
            if (clan.n() != houseId) continue;
            clan.h(0);
            ao.q.a().b(clan);
        }
    }

    private void a(int houseId, int bidderId) {
        for (aq.i clan : ao.q.a().b().values()) {
            if (clan.k() != bidderId) continue;
            clan.h(houseId);
            ao.q.a().b(clan);
            break;
        }
    }

    private void b(int houseId) {
        bh.i house = ab.a().a(houseId);
        house.a(false);
        Timestamp ts = new Timestamp(System.currentTimeMillis() + (long)(l1j.server.a.an * 24 * 60 * 60) * 1000L);
        house.a(ts);
        ab.a().a(house);
    }

    private void d() {
        Timestamp current = new Timestamp(System.currentTimeMillis());
        for (bh.i house : ab.a().c().values()) {
            if (house.g() || !house.i().before(current) || house.o() <= 0) continue;
            this.a(house);
        }
    }

    public void a(bh.i house) {
        int houseId = house.b();
        for (aq.i clan : ao.q.a().b().values()) {
            if (clan.n() != houseId) continue;
            clan.h(0);
            ao.q.a().b(clan);
        }
        Timestamp ts = new Timestamp(System.currentTimeMillis() + 432000000L);
        house.b(ts);
        house.d(100000);
        house.c("");
        house.e(0);
        house.d("");
        house.f(0);
        house.a(true);
        house.b(false);
        Timestamp ts2 = new Timestamp(System.currentTimeMillis() + (long)(l1j.server.a.an * 24 * 60 * 60) * 1000L);
        house.a(ts2);
        house.a();
        ab.a().a(house);
    }

    private class a
    extends TimerTask {
        private a() {
        }

        @Override
        public void run() {
            try {
                i.this.c();
                i.this.d();
            }
            catch (Exception e2) {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
        }
    }
}

