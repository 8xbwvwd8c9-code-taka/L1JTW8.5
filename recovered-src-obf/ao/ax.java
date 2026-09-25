/*
 * Decompiled with CFR 0.152.
 */
package ao;

import bh.p;
import bi.f;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class ax {
    private static final Logger a = Logger.getLogger(ax.class.getName());
    private static ax b;
    private final HashMap<Integer, p> c = new HashMap();
    private final Set<String> d = new HashSet<String>();

    public static void a() {
        b = new ax();
    }

    public static ax b() {
        return b;
    }

    private ax() {
        this.c();
    }

    private void c() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM pettypes");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int baseNpcId = rs.getInt("BaseNpcId");
                        String name = rs.getString("Name");
                        int itemIdForTaming = rs.getInt("ItemIdForTaming");
                        int hpUpMin = rs.getInt("HpUpMin");
                        int hpUpMax = rs.getInt("HpUpMax");
                        int mpUpMin = rs.getInt("MpUpMin");
                        int mpUpMax = rs.getInt("MpUpMax");
                        int evolvItemId = rs.getInt("EvolvItemId");
                        int npcIdForEvolving = rs.getInt("NpcIdForEvolving");
                        int[] msgIds = new int[5];
                        int i2 = 0;
                        while (i2 < 5) {
                            msgIds[i2] = rs.getInt("MessageId" + (i2 + 1));
                            ++i2;
                        }
                        int defyMsgId = rs.getInt("DefyMessageId");
                        boolean canUseEquipment = rs.getBoolean("canUseEquipment");
                        f hpUpRange = new f(hpUpMin, hpUpMax);
                        f mpUpRange = new f(mpUpMin, mpUpMax);
                        this.c.put(baseNpcId, new p(baseNpcId, name, itemIdForTaming, hpUpRange, mpUpRange, evolvItemId, npcIdForEvolving, msgIds, defyMsgId, canUseEquipment));
                        this.d.add(name.toLowerCase());
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block7;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
    }

    public p a(int baseNpcId) {
        return this.c.get(baseNpcId);
    }

    public boolean a(String name) {
        return this.d.contains(name.toLowerCase());
    }
}

