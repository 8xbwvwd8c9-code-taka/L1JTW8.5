/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ao.au;
import ap.l;
import aq.aq;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class y {
    private static final Logger a = Logger.getLogger(y.class.getName());
    private static y b;

    public static y a() {
        if (b == null) {
            b = new y();
        }
        return b;
    }

    private y() {
        this.b();
    }

    private void b() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM spawnlist_furniture");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int npcid = rs.getInt("npcid");
                        bh.l l1npc = au.a().a(npcid);
                        if (l1npc == null) {
                            a.log(Level.SEVERE, "FurnitureSpawnTable npicd:" + npcid + " is Null");
                            continue;
                        }
                        l furniture = new l(l1npc);
                        furniture.cF(d.a().c());
                        furniture.b(rs.getInt("item_obj_id"));
                        furniture.cG(rs.getInt("locx"));
                        furniture.cH(rs.getInt("locy"));
                        furniture.cE(rs.getInt("mapid"));
                        furniture.q(furniture.fs());
                        furniture.r(furniture.ft());
                        furniture.ct(0);
                        aq.a().a(furniture);
                        aq.a().c(furniture);
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

    public void a(l furniture) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO spawnlist_furniture SET item_obj_id=?, npcid=?, locx=?, locy=?, mapid=?");
                    pstm.setInt(1, furniture.f());
                    pstm.setInt(2, furniture.U_().b());
                    pstm.setInt(3, furniture.fs());
                    pstm.setInt(4, furniture.ft());
                    pstm.setInt(5, furniture.fp());
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

    public void b(l furniture) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("DELETE FROM spawnlist_furniture WHERE item_obj_id=?");
                    pstm.setInt(1, furniture.f());
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

