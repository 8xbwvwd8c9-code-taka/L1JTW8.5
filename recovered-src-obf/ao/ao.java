/*
 * Decompiled with CFR 0.152.
 */
package ao;

import ax.b;
import ax.d;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ao {
    private static final Logger a = Logger.getLogger(ao.class.getName());
    private static ao b;
    private final HashMap<Integer, b> c = new HashMap();

    public void a(b clone) {
        this.c.put(clone.a, clone);
    }

    private ao() {
        this.b();
        for (int key : d.b().a().keySet()) {
            this.c.containsKey(key);
        }
    }

    private void b() {
        block7: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM mapids");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int mapId = rs.getInt("mapid");
                        b l1map = d.b().a(mapId);
                        if (l1map != null) {
                            b data = l1map;
                            data.f = rs.getDouble("monster_amount");
                            data.g = rs.getDouble("drop_rate");
                            data.h = rs.getDouble("exp_rate");
                            data.i = rs.getBoolean("underwater");
                            data.j = rs.getBoolean("markable");
                            data.k = rs.getBoolean("teleportable");
                            data.l = rs.getBoolean("escapable");
                            data.m = rs.getBoolean("resurrection");
                            data.n = rs.getBoolean("painwand");
                            data.o = rs.getBoolean("penalty");
                            data.p = rs.getBoolean("take_pets");
                            data.q = rs.getBoolean("recall_pets");
                            data.r = rs.getBoolean("usable_item");
                            data.s = rs.getBoolean("usable_skill");
                            data.t = rs.getBoolean("polyable");
                            this.c.put(new Integer(mapId), data);
                            continue;
                        }
                        System.out.println("DB->mapids \u5730\u5716\u7de8\u865f: " + mapId + " \u6c92\u6709\u5730\u5716\u6a94(.txt)");
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

    public static ao a() {
        if (b == null) {
            b = new ao();
        }
        return b;
    }

    public double a(int mapId) {
        b map = this.c.get(mapId);
        if (map == null) {
            return 0.0;
        }
        return map.f;
    }

    public double b(int mapId) {
        b map = this.c.get(mapId);
        if (map == null) {
            return 0.0;
        }
        return map.g;
    }
}

