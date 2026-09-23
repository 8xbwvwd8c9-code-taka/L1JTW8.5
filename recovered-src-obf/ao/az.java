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
import bh.s;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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

    public HashMap<Integer, s> b() {
        return this.c;
    }
}

