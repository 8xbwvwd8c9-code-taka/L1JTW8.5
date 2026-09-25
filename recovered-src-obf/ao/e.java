/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.au;
import aq.ah;
import bh.l;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class e {
    private static final Logger a = Logger.getLogger(e.class.getName());

    private e() {
    }

    public static void a() {
        block7: {
            int spawnCount = 0;
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM spawnlist_boss");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        ah spawnDat;
                        int npcTemplateId = rs.getInt("npc_id");
                        l template1 = au.a().a(npcTemplateId);
                        if (template1 == null) {
                            System.out.println("mob data for id:" + npcTemplateId + " missing in npc table");
                            spawnDat = null;
                            continue;
                        }
                        spawnDat = new ah(template1);
                        spawnDat.a(rs.getInt("id"));
                        spawnDat.c(npcTemplateId);
                        spawnDat.a(rs.getString("cycle_type"));
                        spawnDat.b(rs.getString("weeks"));
                        spawnDat.a(rs.getTime("time"));
                        spawnDat.b(rs.getInt("count"));
                        spawnDat.d(rs.getInt("group_id"));
                        spawnDat.e(rs.getInt("locx"));
                        spawnDat.f(rs.getInt("locy"));
                        spawnDat.g(rs.getInt("randomx"));
                        spawnDat.h(rs.getInt("randomy"));
                        spawnDat.i(rs.getInt("locx1"));
                        spawnDat.j(rs.getInt("locy1"));
                        spawnDat.k(rs.getInt("locx2"));
                        spawnDat.l(rs.getInt("locy2"));
                        spawnDat.m(rs.getInt("heading"));
                        spawnDat.p(rs.getShort("mapid"));
                        spawnDat.a(rs.getBoolean("respawn_screen"));
                        spawnDat.q(rs.getInt("movement_distance"));
                        spawnDat.b(rs.getBoolean("rest"));
                        spawnDat.r(rs.getInt("spawn_type"));
                        spawnDat.s(rs.getInt("percentage"));
                        spawnDat.a();
                        spawnCount += spawnDat.c();
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
}

