/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ai.d;
import ao.au;
import ap.i;
import aq.aq;
import bh.l;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class x {
    private static final Logger a = Logger.getLogger(x.class.getName());
    private static x b;
    private final ArrayList<String> c;

    public static x a() {
        if (b == null) {
            b = new x();
        }
        return b;
    }

    private x() {
        block14: {
            this.c = new ArrayList();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM spawnlist_field");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        int x2 = rs.getInt("locx");
                        int y2 = rs.getInt("locy");
                        int mapid = rs.getInt("mapid");
                        int action = rs.getInt("action_type");
                        int gfxid = rs.getInt("gfxid");
                        if (this.c.contains(String.valueOf(x2) + "," + y2 + "," + mapid + "," + gfxid)) {
                            System.out.println("FieldSpawnTable id: " + id + " is repeat");
                            continue;
                        }
                        this.c.add(String.valueOf(x2) + "," + y2 + "," + mapid + "," + gfxid);
                        l l1npc = au.a().a(190000);
                        i field = new i(l1npc);
                        field.cF(d.a().c());
                        field.cw(gfxid);
                        if (gfxid >= 12901 && gfxid <= 12905) {
                            field.a("$19274");
                        } else if (gfxid == 14348 || gfxid == 14350) {
                            field.f("$22945");
                        } else if (gfxid == 14352 || gfxid == 14354) {
                            field.f("$22944");
                        }
                        field.cG(x2);
                        field.cH(y2);
                        field.q(x2);
                        field.r(y2);
                        field.cE(mapid);
                        field.ct(rs.getInt("heading"));
                        field.cy(rs.getInt("light"));
                        if (action == 28 || gfxid == 1026) {
                            field.b(-1);
                            field.cq(action);
                        } else {
                            field.b(action);
                        }
                        aq.a().a(field);
                        aq.a().c(field);
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

    public i a(int gfxid, int x2, int y2, int mapid) {
        l l1npc = au.a().a(190000);
        l1npc.k(gfxid);
        i field = new i(l1npc);
        field.cF(d.a().c());
        field.cG(x2);
        field.cH(y2);
        field.cE(mapid);
        field.ct(0);
        field.cy(5);
        field.b(-1);
        aq.a().a(field);
        aq.a().c(field);
        return field;
    }
}

