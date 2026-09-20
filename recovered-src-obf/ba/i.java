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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

        aq.i oldClan = this.e(houseId);
        aq.i bidderClan = bidderId == 0 ? null : this.f(bidderId);

        if (bidderId != 0 && bidderClan == null) {
            if (price > 0 && !this.a(bidderId, price)) {
                return;
            }
            String oldBidder = house.n();
            int oldBidderId = house.o();
            house.d("");
            house.f(0);
            if (!ab.a().a(house)) {
                house.d(oldBidder);
                house.f(oldBidderId);
                if (price > 0) {
                    this.b(bidderId, price);
                }
            }
            return;
        }

        if (oldOwnerId != 0 && bidderId != 0) {
            if (price <= 0) {
                return;
            }
            int payPrice = (int)((double)price * 0.9);
            if (payPrice <= 0 || !this.a(oldOwnerId, payPrice)) {
                return;
            }

            int oldClanHouse = oldClan == null ? 0 : oldClan.n();
            int bidderClanHouse = bidderClan.n();
            boolean oldSale = house.g();
            Timestamp oldTax = house.i();

            if (oldClan != null) {
                oldClan.h(0);
            }
            bidderClan.h(houseId);
            house.a(false);
            house.a(new Timestamp(System.currentTimeMillis() + (long)(l1j.server.a.an * 24 * 60 * 60) * 1000L));

            if (!this.a(oldClan, bidderClan, house)) {
                if (oldClan != null) {
                    oldClan.h(oldClanHouse);
                }
                bidderClan.h(bidderClanHouse);
                house.a(oldSale);
                house.a(oldTax);
                this.b(oldOwnerId, payPrice);
                return;
            }

            u oldOwnerPc = (u)aq.a().a(oldOwnerId);
            if (oldOwnerPc != null) {
                oldOwnerPc.a(new ds(527, String.valueOf(payPrice)));
            }
            u bidderPc = (u)aq.a().a(bidderId);
            if (bidderPc != null) {
                bidderPc.a(new ds(524, String.valueOf(price), bidder));
            }
            return;
        }

        if (oldOwnerId == 0 && bidderId != 0) {
            int bidderClanHouse = bidderClan.n();
            boolean oldSale = house.g();
            Timestamp oldTax = house.i();
            bidderClan.h(houseId);
            house.a(false);
            house.a(new Timestamp(System.currentTimeMillis() + (long)(l1j.server.a.an * 24 * 60 * 60) * 1000L));
            if (!this.a(null, bidderClan, house)) {
                bidderClan.h(bidderClanHouse);
                house.a(oldSale);
                house.a(oldTax);
                return;
            }
            u bidderPc = (u)aq.a().a(bidderId);
            if (bidderPc != null) {
                bidderPc.a(new ds(524, String.valueOf(price), bidder));
            }
            return;
        }

        if (oldOwnerId != 0) {
            boolean oldSale = house.g();
            Timestamp oldTax = house.i();
            house.a(false);
            house.a(new Timestamp(System.currentTimeMillis() + (long)(l1j.server.a.an * 24 * 60 * 60) * 1000L));
            if (!this.a(null, null, house)) {
                house.a(oldSale);
                house.a(oldTax);
                return;
            }
            u oldOwnerPc = (u)aq.a().a(oldOwnerId);
            if (oldOwnerPc != null) {
                oldOwnerPc.a(new ds(528));
            }
            return;
        }

        Timestamp oldDeadline = house.j();
        int oldPrice = house.k();
        house.b(new Timestamp(System.currentTimeMillis() + 432000000L));
        house.d(100000);
        if (!ab.a().a(house)) {
            house.b(oldDeadline);
            house.d(oldPrice);
        }
    }

    private aq.i e(int houseId) {
        for (aq.i clan : ao.q.a().b().values()) {
            if (clan.n() == houseId) {
                return clan;
            }
        }
        return null;
    }

    private aq.i f(int bidderId) {
        for (aq.i clan : ao.q.a().b().values()) {
            if (clan.k() == bidderId && clan.n() == 0) {
                return clan;
            }
        }
        return null;
    }

    private boolean a(aq.i oldClan, aq.i newClan, bh.i house) {
        Connection con = null;
        PreparedStatement oldStmt = null;
        PreparedStatement newStmt = null;
        PreparedStatement houseStmt = null;
        try {
            con = l1j.server.b.a().b();
            con.setAutoCommit(false);
            if (oldClan != null) {
                oldStmt = con.prepareStatement("UPDATE clan_data SET hashouse=? WHERE clan_name=?");
                oldStmt.setInt(1, oldClan.n());
                oldStmt.setString(2, oldClan.f());
                oldStmt.executeUpdate();
            }
            if (newClan != null) {
                newStmt = con.prepareStatement("UPDATE clan_data SET hashouse=? WHERE clan_name=?");
                newStmt.setInt(1, newClan.n());
                newStmt.setString(2, newClan.f());
                newStmt.executeUpdate();
            }
            houseStmt = con.prepareStatement("UPDATE house SET house_name=?, house_area=?, location=?, keeper_id=?, is_on_sale=?, is_purchase_basement=?, tax_deadline=?, deadline=?, price=?, old_owner=?, old_owner_id=?, bidder=?, bidder_id=? WHERE house_id=?");
            houseStmt.setString(1, house.c());
            houseStmt.setInt(2, house.d());
            houseStmt.setString(3, house.e());
            houseStmt.setInt(4, house.f());
            houseStmt.setBoolean(5, house.g());
            houseStmt.setBoolean(6, house.h());
            houseStmt.setTimestamp(7, house.i());
            houseStmt.setTimestamp(8, house.j());
            houseStmt.setInt(9, house.k());
            houseStmt.setString(10, house.l());
            houseStmt.setInt(11, house.m());
            houseStmt.setString(12, house.n());
            houseStmt.setInt(13, house.o());
            houseStmt.setInt(14, house.b());
            houseStmt.executeUpdate();
            con.commit();
            return true;
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            if (con != null) {
                try {
                    con.rollback();
                }
                catch (SQLException ignored) {
                }
            }
            return false;
        }
        finally {
            try {
                if (houseStmt != null) houseStmt.close();
            }
            catch (SQLException ignored) {
            }
            try {
                if (newStmt != null) newStmt.close();
            }
            catch (SQLException ignored) {
            }
            try {
                if (oldStmt != null) oldStmt.close();
            }
            catch (SQLException ignored) {
            }
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                }
                catch (SQLException ignored) {
                }
                try {
                    con.close();
                }
                catch (SQLException ignored) {
                }
            }
        }
    }

    private boolean a(int charId, int amount) {
        if (amount <= 0) {
            return false;
        }
        u pc = (u)aq.a().a(charId);
        if (pc != null) {
            return ah.a(pc, 40308, amount) != null;
        }
        q item = ah.a().b(40308);
        if (item == null) {
            return false;
        }
        item.e(amount);
        try {
            l.a().a(charId, item);
            return true;
        }
        catch (Exception e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
    }

    private boolean b(int charId, int amount) {
        if (amount <= 0) {
            return true;
        }
        u pc = (u)aq.a().a(charId);
        if (pc != null) {
            return pc.j().b(40308, amount);
        }
        Connection con = null;
        PreparedStatement pstm = null;
        try {
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT id,count FROM character_items WHERE char_id=? AND item_id=40308 ORDER BY id DESC LIMIT 1");
            pstm.setInt(1, charId);
            java.sql.ResultSet rs = pstm.executeQuery();
            if (!rs.next()) {
                rs.close();
                return false;
            }
            int id = rs.getInt("id");
            int count = rs.getInt("count");
            rs.close();
            if (count < amount) {
                return false;
            }
            pstm.close();
            if (count == amount) {
                pstm = con.prepareStatement("DELETE FROM character_items WHERE id=?");
                pstm.setInt(1, id);
            } else {
                pstm = con.prepareStatement("UPDATE character_items SET count=? WHERE id=?");
                pstm.setInt(1, count - amount);
                pstm.setInt(2, id);
            }
            pstm.executeUpdate();
            return true;
        }
        catch (SQLException e2) {
            a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            return false;
        }
        finally {
            try {
                if (pstm != null) pstm.close();
            }
            catch (SQLException ignored) {
            }
            try {
                if (con != null) con.close();
            }
            catch (SQLException ignored) {
            }
        }
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
        aq.i ownerClan = this.e(houseId);

        Timestamp oldDeadline = house.j();
        int oldPrice = house.k();
        String oldOwner = house.l();
        int oldOwnerId = house.m();
        String oldBidder = house.n();
        int oldBidderId = house.o();
        boolean oldSale = house.g();
        boolean oldBasement = house.h();
        Timestamp oldTax = house.i();
        int oldClanHouse = ownerClan == null ? 0 : ownerClan.n();

        if (ownerClan != null) {
            ownerClan.h(0);
        }
        house.b(new Timestamp(System.currentTimeMillis() + 432000000L));
        house.d(100000);
        house.c("");
        house.e(0);
        house.d("");
        house.f(0);
        house.a(true);
        house.b(false);
        house.a(new Timestamp(System.currentTimeMillis() + (long)(l1j.server.a.an * 24 * 60 * 60) * 1000L));
        house.a();

        if (!this.a(ownerClan, null, house)) {
            if (ownerClan != null) {
                ownerClan.h(oldClanHouse);
            }
            house.b(oldDeadline);
            house.d(oldPrice);
            house.c(oldOwner);
            house.e(oldOwnerId);
            house.d(oldBidder);
            house.f(oldBidderId);
            house.a(oldSale);
            house.b(oldBasement);
            house.a(oldTax);
        }
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

