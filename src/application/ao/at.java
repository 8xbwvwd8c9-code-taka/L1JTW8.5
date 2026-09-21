/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ao.au;
import ap.u;
import aq.ag;
import bh.l;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.a;
import l1j.server.b;

public class at {
    private static final Logger a = Logger.getLogger(at.class.getName());
    private static at b;
    private final HashMap<Integer, ag> c = new HashMap();
    private int d;

    public static at a() {
        if (b == null) {
            b = new at();
        }
        return b;
    }

    private at() {
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
                    pstm = con.prepareStatement("SELECT * FROM spawnlist_npc");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        ag l1spawn;
                        int npcid;
                        if (!l1j.server.a.ac && (npcid = rs.getInt(1)) >= l1j.server.a.ad && npcid <= l1j.server.a.ae) continue;
                        int npcTemplateid = rs.getInt("npc_templateid");
                        l l1npc = au.a().a(npcTemplateid);
                        if (l1npc == null) {
                            System.out.println("mob data for id:" + npcTemplateid + " missing in npc table");
                            l1spawn = null;
                            continue;
                        }
                        if (rs.getInt("count") == 0) continue;
                        l1spawn = new ag(l1npc);
                        l1spawn.a(rs.getInt("id"));
                        l1spawn.b(rs.getInt("count"));
                        l1spawn.e(rs.getInt("locx"));
                        l1spawn.f(rs.getInt("locy"));
                        l1spawn.g(rs.getInt("randomx"));
                        l1spawn.h(rs.getInt("randomy"));
                        l1spawn.i(0);
                        l1spawn.j(0);
                        l1spawn.k(0);
                        l1spawn.l(0);
                        l1spawn.m(rs.getInt("heading"));
                        l1spawn.n(rs.getInt("respawn_delay"));
                        l1spawn.p(rs.getShort("mapid"));
                        l1spawn.q(rs.getInt("movement_distance"));
                        l1spawn.a();
                        this.c.put(new Integer(l1spawn.b()), l1spawn);
                        if (l1spawn.b() <= this.d) continue;
                        this.d = l1spawn.b();
                    }
                }
                catch (Exception e2) {
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

    public void a(u pc, l npc) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    boolean count = true;
                    String note = npc.c();
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO spawnlist_npc SET location=?,count=?,npc_templateid=?,locx=?,locy=?,heading=?,mapid=?");
                    pstm.setString(1, note);
                    pstm.setInt(2, 1);
                    pstm.setInt(3, npc.b());
                    pstm.setInt(4, pc.fs());
                    pstm.setInt(5, pc.ft());
                    pstm.setInt(6, pc.fb());
                    pstm.setInt(7, pc.fp());
                    pstm.execute();
                }
                catch (Exception e2) {
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

    public ag a(int i2) {
        return this.c.get(i2);
    }
}

