/*
 * Decompiled with CFR 0.152.
 */
package ao;

import a.g;
import an.c;
import an.g;
import ap.q;
import ap.u;
import be.dc;
import be.ds;
import bh.s;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class az {
    private static final Logger a = Logger.getLogger(az.class.getName());
    private static az b;
    private final HashMap<Integer, s> c = new HashMap();

    public static az a() {
        if (b == null) {
            b = new az();
        }
        return b;
    }

    private az() {
        int[] quests;
        int[] nArray = quests = new int[]{256, 257, 258, 259, 260, 261, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 293, 294, 299, 306, 314, 317, 318, 319, 320, 321, 322, 323, 324, 325, 326, 328, 334, 336, 338, 340, 341, 342, 343, 346, 349};
        int n2 = quests.length;
        int n3 = 0;
        while (n3 < n2) {
            int i2 = nArray[n3];
            s qn = new s(i2);
            this.c.put(i2, qn);
            ++n3;
        }
    }

    public void a(u pc) {
        for (int i2 : this.c.keySet()) {
            if (pc.dS().containsKey(i2)) continue;
            s qn = this.c.get(i2);
            if (pc.ev() < qn.b() || pc.ev() > qn.c() || pc.fp() != qn.d() || !qn.m().equalsIgnoreCase("A") && !qn.m().equalsIgnoreCase(pc.aC().h())) continue;
            s quest = new s(i2);
            quest.a(pc);
            if (quest.n() > 0) {
                quest.a(pc.ev());
            }
            pc.dS().put(i2, quest);
            pc.a(new dc(518, quest));
            int objectiveIndex = 0;
            while (objectiveIndex < quest.r().length) {
                long inventoryCount = 0L;
                for (q inventoryItem : pc.j().d()) {
                    if (quest.r()[objectiveIndex] == inventoryItem.N()
                    && quest.t()[objectiveIndex] <= inventoryItem.G()
                    && inventoryItem.E() > 0) {
                        inventoryCount += (long)inventoryItem.E();
                        if (inventoryCount >= (long)quest.s()[objectiveIndex]) {
                            inventoryCount = quest.s()[objectiveIndex];
                            break;
                        }
                    }
                }
                quest.a(objectiveIndex, (int)inventoryCount);
                ++objectiveIndex;
            }
        }
    }

    public void b(u pc) {
        this.d(pc);
        this.a(pc);
    }

    private void d(u pc) {
        block14: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_quests_new WHERE objid=?");
                    pstm.setInt(1, pc.fr());
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        byte[] data = rs.getBytes("data");
                        c.g msg = c.g.a(data);
                        for (g bs2 : msg.o()) {
                            int[] itemUseCount;
                            int[] itemCount;
                            g.e msg2 = g.e.a(bs2);
                            s qn = new s(msg2.p());
                            qn.a(pc);
                            qn.a(msg2.r() == 1);
                            qn.b(msg2.t() == 1);
                            qn.d(msg2.v());
                            int[] npcCount = new int[msg2.x()];
                            if (npcCount.length > 0) {
                                int i2 = 0;
                                while (i2 < npcCount.length) {
                                    npcCount[i2] = msg2.a(i2);
                                    ++i2;
                                }
                                qn.a(npcCount);
                            }
                            if ((itemCount = new int[msg2.z()]).length > 0) {
                                int i3 = 0;
                                while (i3 < itemCount.length) {
                                    itemCount[i3] = msg2.b(i3);
                                    ++i3;
                                }
                                qn.b(itemCount);
                            }
                            if ((itemUseCount = new int[msg2.B()]).length > 0) {
                                int i4 = 0;
                                while (i4 < itemUseCount.length) {
                                    itemUseCount[i4] = msg2.c(i4);
                                    ++i4;
                                }
                                qn.c(itemUseCount);
                            }
                            pc.dS().put(qn.a(), qn);
                            if (qn.w() || pc.fp() != qn.d()) continue;
                            pc.a(new dc(518, qn));
                        }
                    } else {
                        this.e(pc);
                    }
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block14;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    private void e(u pc) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_quests_new SET objid=?, data=?");
                    pstm.setInt(1, pc.fr());
                    pstm.setBytes(2, this.f(pc));
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(pstm);
                    j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                j.a(pstm);
                j.a(con);
                throw throwable;
            }
            j.a(pstm);
            j.a(con);
        }
    }

    private byte[] f(u pc) {
        c.g.a build = c.g.aa();
        for (s qn : pc.dS().values()) {
            int i2;
            g.e.a builder = g.e.aa();
            builder.f(qn.a());
            builder.g(qn.w() ? 1 : 0);
            builder.h(qn.x() ? 1 : 0);
            builder.i(qn.z());
            int[] nArray = qn.A();
            int n2 = nArray.length;
            int n3 = 0;
            while (n3 < n2) {
                i2 = nArray[n3];
                builder.j(i2);
                ++n3;
            }
            nArray = qn.B();
            n2 = nArray.length;
            n3 = 0;
            while (n3 < n2) {
                i2 = nArray[n3];
                builder.k(i2);
                ++n3;
            }
            nArray = qn.C();
            n2 = nArray.length;
            n3 = 0;
            while (n3 < n2) {
                i2 = nArray[n3];
                builder.l(i2);
                ++n3;
            }
            build.e(builder.M().f());
        }
        return build.M().g();
    }

    public void c(u pc) {
        synchronized (pc.dS()) {
            block5: {
                Connection con = null;
                PreparedStatement pstm = null;
                try {
                    try {
                        con = l1j.server.b.a().b();
                        pstm = con.prepareStatement("INSERT INTO character_quests_new (objid,data) VALUES (?,?) ON DUPLICATE KEY UPDATE data=VALUES(data)");
                        pstm.setBytes(2, this.f(pc));
                        pstm.setInt(1, pc.fr());
                        pstm.execute();
                    }
                    catch (SQLException e2) {
                        a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                        j.a(pstm);
                        j.a(con);
                        break block5;
                    }
                }
                catch (Throwable throwable) {
                    j.a(pstm);
                    j.a(con);
                    throw throwable;
                }
                j.a(pstm);
                j.a(con);
            }
        }
    }

    public boolean claimReward(u pc, s qn, int selectedIndex) {
        if (pc == null || qn == null) {
            return false;
        }
        synchronized (qn) {
            synchronized (pc) {
                synchronized (pc.dS()) {
                    synchronized (pc.j()) {
                    if (qn.w() || !qn.x()) {
                        return false;
                    }
                    QuestRewardPlan plan = this.buildQuestRewardPlan(pc, qn, selectedIndex);
                    if (plan == null) {
                        return false;
                    }
                    int expGain = 0;
                    if (qn.l() > 0) {
                        double expPenalty = w.d(pc.ev());
                        expGain = (int)((double)qn.l() * expPenalty);
                        if (expGain < 0) {
                            return false;
                        }
                    }
                    int oldExp = pc.m();
                    int newExp = (int)Math.min(1859065562L, (long)oldExp + (long)expGain);
                    byte[] claimedData;
                    qn.a(true);
                    try {
                        claimedData = this.f(pc);
                    }
                    catch (RuntimeException e2) {
                        qn.a(false);
                        throw e2;
                    }

                    Connection con = null;
                    boolean oldAutoCommit = true;
                    boolean committed = false;
                    try {
                        con = l1j.server.b.a().b();
                        oldAutoCommit = con.getAutoCommit();
                        this.requireInnoDb(con, "character_items");
                        this.requireInnoDb(con, "character_quests_new");
                        this.requireInnoDb(con, "characters");
                        con.setAutoCommit(false);
                        l storage = l.a();

                        for (QuestItemMutation mutation : plan.existing.values()) {
                            if (mutation.newCount == mutation.oldCount) {
                                continue;
                            }
                            if (mutation.newCount < 0) {
                                throw new SQLException("BUG-850-275 negative item count");
                            }
                            if (mutation.newCount == 0) {
                                storage.deleteQuestRewardItem(con, pc.fr(), mutation.item, mutation.oldCount);
                            } else {
                                storage.updateQuestRewardCount(con, pc.fr(), mutation.item, mutation.oldCount, mutation.newCount);
                            }
                        }

                        for (q item : plan.inserts) {
                            storage.insertQuestReward(con, pc.fr(), item);
                        }

                        try (PreparedStatement pstm = con.prepareStatement(
                                "UPDATE character_quests_new SET data=? WHERE objid=?")) {
                            pstm.setBytes(1, claimedData);
                            pstm.setInt(2, pc.fr());
                            if (pstm.executeUpdate() != 1) {
                                throw new SQLException("BUG-850-275 quest claimed persistence failed");
                            }
                        }

                        if (expGain > 0) {
                            try (PreparedStatement pstm = con.prepareStatement(
                                    "UPDATE characters SET Exp=? WHERE objid=?")) {
                                pstm.setInt(1, newExp);
                                pstm.setInt(2, pc.fr());
                                if (pstm.executeUpdate() != 1) {
                                    throw new SQLException("BUG-850-275 EXP persistence failed");
                                }
                            }
                        }

                        con.commit();
                        committed = true;
                    }
                    catch (Exception e3) {
                        if (con != null) {
                            try {
                                con.rollback();
                            }
                            catch (SQLException rollbackError) {
                                a.log(Level.SEVERE, rollbackError.getLocalizedMessage(), rollbackError);
                            }
                        }
                        a.log(Level.SEVERE, "BUG-850-275 quest reward transaction failed", e3);
                    }
                    finally {
                        if (con != null) {
                            try {
                                con.setAutoCommit(oldAutoCommit);
                            }
                            catch (SQLException autoCommitError) {
                                a.log(Level.SEVERE, autoCommitError.getLocalizedMessage(), autoCommitError);
                            }
                        }
                        j.a(con);
                    }

                    if (!committed) {
                        qn.a(false);
                        return false;
                    }

                    try {
                        for (QuestItemMutation mutation : plan.existing.values()) {
                            if (mutation.newCount == mutation.oldCount) {
                                continue;
                            }
                            if (mutation.newCount == 0) {
                                pc.j().publishCommittedQuestDelete(mutation.item);
                            } else {
                                pc.j().publishCommittedQuestUpdate(mutation.item, mutation.newCount);
                            }
                        }

                        for (q item : plan.inserts) {
                            pc.j().publishCommittedQuestInsert(item);
                        }

                        if (expGain > 0) {
                            pc.k(newExp);
                        }

                        for (String notice : plan.notices) {
                            pc.a(new ds(403, notice));
                        }

                        pc.a(new dc(525, qn.a()));
                    }
                    catch (RuntimeException publishError) {
                        a.log(Level.SEVERE, "BUG-850-275 committed reward live publication failed; relog restores durable state", publishError);
                    }

                    return true;
                    }
                }
            }
        }
    }

    private QuestRewardPlan buildQuestRewardPlan(u pc, s qn, int selectedIndex) {
        QuestRewardPlan plan = new QuestRewardPlan(pc.j().e());
        if (selectedIndex < -1
                || qn.f() == null
                || qn.g() == null
                || qn.h() == null
                || qn.f().length != qn.g().length
                || qn.f().length != qn.h().length) {
            return null;
        }
        int i2 = 0;
        while (i2 < qn.f().length) {
            if (!this.addQuestReward(pc, plan, qn.f()[i2], qn.g()[i2], qn.h()[i2])) {
                return null;
            }
            ++i2;
        }

        if (selectedIndex >= 0) {
            if (qn.i() == null || qn.j() == null || qn.k() == null
                    || selectedIndex >= qn.i().length
                    || selectedIndex >= qn.j().length
                    || selectedIndex >= qn.k().length
                    || !this.addQuestReward(pc, plan, qn.i()[selectedIndex], qn.j()[selectedIndex], qn.k()[selectedIndex])) {
                return null;
            }
        }

        if (pc.j().c() + plan.newSlots > 180 || (double)plan.projectedWeight >= pc.K()) {
            return null;
        }

        if (qn.r().length > 0 && qn.o()) {
            if (qn.r().length != qn.s().length || qn.r().length != qn.t().length) {
                return null;
            }
            int i3 = 0;
            while (i3 < qn.r().length) {
                if (!this.consumeQuestRequirement(pc, plan, qn.r()[i3], qn.t()[i3], qn.s()[i3])) {
                    return null;
                }
                ++i3;
            }
        }

        return plan;
    }

    private boolean addQuestReward(u pc, QuestRewardPlan plan, int itemId, int count, int enchant) {
        if (count <= 0 || itemId == 40312 || itemId == 413 || itemId == 21446) {
            return false;
        }
        bh.j itemTemplate = ah.a().a(itemId);
        if (itemTemplate == null) {
            return false;
        }
        q prototype = new q(itemTemplate, count);
        prototype.a(enchant);
        plan.notices.add(prototype.s());
        plan.projectedWeight += (long)itemTemplate.l() * (long)count / 1000L + 1L;

        if (itemTemplate.aF()) {
            q existing = pc.j().d(itemId, prototype.F());
            if (existing != null) {
                QuestItemMutation mutation = plan.existing.get(existing.fr());
                if (mutation == null) {
                    mutation = new QuestItemMutation(existing);
                    plan.existing.put(existing.fr(), mutation);
                }
                long newCount = (long)mutation.newCount + (long)count;
                if (newCount > 2000000000L) {
                    return false;
                }
                mutation.newCount = (int)newCount;
                return true;
            }

            String stackKey = itemId + ":" + prototype.F();
            q plannedStack = plan.newStacks.get(stackKey);
            if (plannedStack != null) {
                long newCount = (long)plannedStack.E() + (long)count;
                if (newCount > 2000000000L) {
                    return false;
                }
                plannedStack.e((int)newCount);
                return true;
            }
            prototype.cF(ai.d.a().d());
            plan.inserts.add(prototype);
            plan.newStacks.put(stackKey, prototype);
            ++plan.newSlots;
            return true;
        }

        int i2 = 0;
        while (i2 < count) {
            q item = new q(itemTemplate, 1);
            item.cF(ai.d.a().d());
            item.a(enchant);
            item.n();
            plan.inserts.add(item);
            ++plan.newSlots;
            ++i2;
        }
        return true;
    }

    private boolean consumeQuestRequirement(u pc, QuestRewardPlan plan, int itemId, int enchant, int count) {
        if (count <= 0) {
            return false;
        }
        int remaining = count;
        for (q item : pc.j().d()) {
            if (item.D() || item.N() != itemId || item.G() != enchant) {
                continue;
            }
            QuestItemMutation mutation = plan.existing.get(item.fr());
            if (mutation == null) {
                mutation = new QuestItemMutation(item);
                plan.existing.put(item.fr(), mutation);
            }
            int available = Math.max(0, mutation.oldCount - mutation.consumed);
            if (available <= 0) {
                continue;
            }
            int take = item.d() ? Math.min(remaining, available) : 1;
            mutation.consumed += take;
            mutation.newCount -= take;
            remaining -= take;
            if (remaining == 0) {
                return true;
            }
        }
        return false;
    }

    private void requireInnoDb(Connection con, String table) throws SQLException {
        try (PreparedStatement pstm = con.prepareStatement(
                "SELECT ENGINE FROM information_schema.TABLES WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME=?")) {
            pstm.setString(1, table);
            try (ResultSet rs = pstm.executeQuery()) {
                if (!rs.next() || !"InnoDB".equalsIgnoreCase(rs.getString("ENGINE"))) {
                    throw new SQLException("BUG-850-275 requires InnoDB table: " + table);
                }
            }
        }
    }

    private static final class QuestRewardPlan {
        private final LinkedHashMap<Integer, QuestItemMutation> existing = new LinkedHashMap<Integer, QuestItemMutation>();
        private final List<q> inserts = new ArrayList<q>();
        private final LinkedHashMap<String, q> newStacks = new LinkedHashMap<String, q>();
        private final List<String> notices = new ArrayList<String>();
        private int newSlots = 0;
        private long projectedWeight;

        private QuestRewardPlan(long weight) {
            this.projectedWeight = weight;
        }
    }

    private static final class QuestItemMutation {
        private final q item;
        private final int oldCount;
        private int newCount;
        private int consumed = 0;

        private QuestItemMutation(q item) {
            this.item = item;
            this.oldCount = item.E();
            this.newCount = this.oldCount;
        }
    }

    public HashMap<Integer, s> b() {
        return this.c;
    }
}

