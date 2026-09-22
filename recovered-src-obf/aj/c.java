/*
 * Decompiled with CFR 0.152.
 */
package aj;

import aj.cv;
import ao.ab;
import ao.ac;
import ao.af;
import ao.ah;
import ao.l;
import ap.q;
import ap.t;
import ap.u;
import aq.aq;
import au.f;
import be.be;
import be.ds;
import bh.i;
import bj.d;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class c
extends cv {
    public c(byte[] decrypt, d client) throws Exception {
        super(decrypt);
        u pc = client.f();
        if (pc == null) {
            return;
        }
        int objectId = this.b();
        int amount = this.b();
        int type = this.c();
        String s2 = this.g();

        aq.aa object = aq.a().a(objectId);
        if (!(object instanceof t)) {
            return;
        }
        t npc = (t)object;
        if (npc.fu().c(pc.fu()) > 11) {
            return;
        }
        if (amount <= 0) {
            return;
        }
        if (ac.a().a(s2, pc, npc, amount)) {
            return;
        }

        String s1 = "";
        String s22 = "";
        try {
            StringTokenizer stringtokenizer = new StringTokenizer(s2);
            s1 = stringtokenizer.nextToken();
            s22 = stringtokenizer.nextToken();
        }
        catch (NoSuchElementException e2) {
            s1 = "";
            s22 = "";
        }

        if (s1.equalsIgnoreCase("agapply")) {
            if (!(npc instanceof ap.b)) {
                return;
            }
            int houseId;
            try {
                houseId = Integer.parseInt(s22);
            }
            catch (NumberFormatException e3) {
                return;
            }
            i house = ab.a().a(houseId);
            if (house == null || !house.g() || house.j() == null || !house.j().after(new Timestamp(System.currentTimeMillis()))) {
                return;
            }
            aq.i clan = ao.q.a().a(pc.aF());
            if (clan == null || !pc.x() || pc.fr() != clan.k() || pc.ev() < 15 || clan.n() != 0) {
                return;
            }
            if (amount <= house.k()) {
                return;
            }
            for (i other : ab.a().c().values()) {
                if (pc.fr() == other.o()) {
                    pc.a(new ds(523));
                    return;
                }
            }
            if (!pc.j().g(40308, amount)) {
                pc.a(new ds(189));
                return;
            }
            if (!this.commitAuctionBidAtomic(pc, house, amount)) {
                return;
            }
            return;
        }

        if (s1.equalsIgnoreCase("agsell")) {
            if (!(npc instanceof ap.p)) {
                return;
            }
            if (amount < 100000 || amount > 2000000000) {
                return;
            }
            int houseId;
            try {
                houseId = Integer.parseInt(s22);
            }
            catch (NumberFormatException e4) {
                return;
            }
            i house = ab.a().a(houseId);
            aq.i clan = ao.q.a().a(pc.aF());
            if (house == null || clan == null || !pc.x() || pc.fr() != clan.k() || clan.n() != houseId || npc.z() != house.f() || house.g()) {
                return;
            }
            if (amount < 100000 || amount > 2000000000) {
                return;
            }

            Timestamp oldDeadline = house.j();
            int oldPrice = house.k();
            String oldOwner = house.l();
            int oldOwnerId = house.m();
            String oldBidder = house.n();
            int oldBidderId = house.o();
            boolean oldSale = house.g();
            boolean oldBasement = house.h();

            Timestamp ts = new Timestamp(System.currentTimeMillis() + 432000000L);
            house.b(ts);
            house.d(amount);
            house.c(pc.et());
            house.e(pc.fr());
            house.d("");
            house.f(0);
            house.a(true);
            house.b(false);
            if (!ab.a().a(house)) {
                house.b(oldDeadline);
                house.d(oldPrice);
                house.c(oldOwner);
                house.e(oldOwnerId);
                house.d(oldBidder);
                house.f(oldBidderId);
                house.a(oldSale);
                house.b(oldBasement);
            }
            return;
        }

        int npcId = npc.z();
        if (npcId == 70070 || npcId == 70019 || npcId == 70075 || npcId == 70012 || npcId == 70031 || npcId == 70084 || npcId == 70065 || npcId == 70054 || npcId == 70096) {
            long charge = 300L * (long)amount;
            if (charge <= 0L || charge > 2000000000L) {
                return;
            }
            int chargeInt = (int)charge;
            if (!pc.j().g(40308, chargeInt)) {
                pc.a(new be(npcId, "inn3", npc.et()));
                return;
            }
            if (!af.a().a(pc.dM())) {
                pc.a(new be(npcId, ""));
                return;
            }
            q item = ah.a().b(40312);
            if (item == null) {
                return;
            }
            item.e(amount);
            item.j(item.fr());
            if (pc.j().a(item, amount) != 0) {
                return;
            }
            if (!pc.j().b(40308, chargeInt)) {
                return;
            }
            pc.j().d(item);
            if (!af.a().a(item.M(), amount, pc.dM())) {
                pc.j().f(item);
                ao.ah.a(pc, 40308, chargeInt, 0, false);
                return;
            }
            pc.a(new ds(143, npc.et(), item.s()));
            pc.a(new be(npcId, "inn4", npc.et()));
        }
    }

    private boolean commitAuctionBidAtomic(u pc, bh.i house, int amount) {
        synchronized (house) {
            ap.q bidderAdena = pc.j().b(40308);
            if (bidderAdena == null || bidderAdena.E() < amount) {
                return false;
            }

            int bidderOldCount = bidderAdena.E();
            int bidderNewCount = bidderOldCount - amount;
            int oldPrice = house.k();
            int oldBidderId = house.o();
            Timestamp oldDeadline = house.j();
            u oldPc = oldBidderId == 0 ? null : (u)aq.a().a(oldBidderId);
            ap.q oldAdena = null;
            ap.q refundInsert = null;
            int oldAdenaOldCount = 0;
            int oldAdenaNewCount = 0;

            Connection con = null;
            boolean oldAutoCommit = true;
            boolean committed = false;

            try {
                con = l1j.server.b.a().b();
                this.requireAuctionBidInnoDb(con);
                oldAutoCommit = con.getAutoCommit();
                con.setAutoCommit(false);

                try (PreparedStatement pstm = con.prepareStatement(
                        "UPDATE house SET price=?, bidder=?, bidder_id=? WHERE house_id=? AND is_on_sale=1 AND price=? AND bidder_id=? AND deadline=?")) {
                    pstm.setInt(1, amount);
                    pstm.setString(2, pc.et());
                    pstm.setInt(3, pc.fr());
                    pstm.setInt(4, house.b());
                    pstm.setInt(5, oldPrice);
                    pstm.setInt(6, oldBidderId);
                    pstm.setTimestamp(7, oldDeadline);
                    if (pstm.executeUpdate() != 1) {
                        throw new SQLException("BUG-850-142 house bid CAS failed");
                    }
                }

                l items = l.a();
                if (bidderNewCount == 0) {
                    items.deleteQuestRewardItem(con, pc.fr(), bidderAdena, bidderOldCount);
                } else {
                    items.updateQuestRewardCount(con, pc.fr(), bidderAdena, bidderOldCount, bidderNewCount);
                }

                if (oldBidderId != 0 && oldPrice > 0) {
                    if (oldPc != null) {
                        oldAdena = oldPc.j().b(40308);
                        if (oldAdena != null) {
                            oldAdenaOldCount = oldAdena.E();
                            long newCountLong = (long)oldAdenaOldCount + (long)oldPrice;
                            if (newCountLong > Integer.MAX_VALUE) {
                                throw new SQLException("BUG-850-142 old bidder Adena overflow");
                            }
                            oldAdenaNewCount = (int)newCountLong;
                            items.updateQuestRewardCount(con, oldBidderId, oldAdena, oldAdenaOldCount, oldAdenaNewCount);
                        } else {
                            refundInsert = ah.a().b(40308);
                            if (refundInsert == null) {
                                throw new SQLException("BUG-850-142 refund item template unavailable");
                            }
                            refundInsert.e(oldPrice);
                            items.insertQuestReward(con, oldBidderId, refundInsert);
                        }
                    } else {
                        try (PreparedStatement pstm = con.prepareStatement(
                                "SELECT id,count FROM character_items WHERE char_id=? AND item_id=40308 ORDER BY id LIMIT 1 FOR UPDATE")) {
                            pstm.setInt(1, oldBidderId);
                            try (ResultSet rs = pstm.executeQuery()) {
                                if (rs.next()) {
                                    int itemId = rs.getInt("id");
                                    int count = rs.getInt("count");
                                    long newCountLong = (long)count + (long)oldPrice;
                                    if (newCountLong > Integer.MAX_VALUE) {
                                        throw new SQLException("BUG-850-142 offline old bidder Adena overflow");
                                    }
                                    try (PreparedStatement update = con.prepareStatement(
                                            "UPDATE character_items SET count=? WHERE id=? AND char_id=? AND count=?")) {
                                        update.setInt(1, (int)newCountLong);
                                        update.setInt(2, itemId);
                                        update.setInt(3, oldBidderId);
                                        update.setInt(4, count);
                                        if (update.executeUpdate() != 1) {
                                            throw new SQLException("BUG-850-142 offline refund CAS failed");
                                        }
                                    }
                                } else {
                                    refundInsert = ah.a().b(40308);
                                    if (refundInsert == null) {
                                        throw new SQLException("BUG-850-142 offline refund item template unavailable");
                                    }
                                    refundInsert.e(oldPrice);
                                    items.insertQuestReward(con, oldBidderId, refundInsert);
                                }
                            }
                        }
                    }
                }

                con.commit();
                committed = true;
            }
            catch (Exception e2) {
                if (con != null) {
                    try {
                        con.rollback();
                    }
                    catch (SQLException ignored) {
                    }
                }
            }
            finally {
                if (con != null) {
                    try {
                        con.setAutoCommit(oldAutoCommit);
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

            if (!committed) {
                return false;
            }

            house.d(amount);
            house.d(pc.et());
            house.f(pc.fr());

            if (bidderNewCount == 0) {
                pc.j().publishCommittedQuestDelete(bidderAdena);
            } else {
                pc.j().publishCommittedQuestUpdate(bidderAdena, bidderNewCount);
            }

            if (oldPc != null && oldBidderId != 0 && oldPrice > 0) {
                if (oldAdena != null) {
                    oldPc.j().publishCommittedQuestUpdate(oldAdena, oldAdenaNewCount);
                } else if (refundInsert != null) {
                    oldPc.j().publishCommittedQuestInsert(refundInsert);
                }
                oldPc.a(new ds(525, String.valueOf(oldPrice)));
            }

            return true;
        }
    }

    private void requireAuctionBidInnoDb(Connection con) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement(
                "SELECT TABLE_NAME,ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME IN ('house','character_items')");
             ResultSet rs = pstm.executeQuery()) {
            int count = 0;
            while (rs.next()) {
                if (!"InnoDB".equalsIgnoreCase(rs.getString("ENGINE"))) {
                    throw new SQLException("BUG-850-142 requires InnoDB auction tables");
                }
                ++count;
            }
            if (count != 2) {
                throw new SQLException("BUG-850-142 missing auction transaction table");
            }
        }
    }

    @Override
    public String a() {
        return "C_Amount";
    }
}

