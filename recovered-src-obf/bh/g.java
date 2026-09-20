/*
 * Decompiled with CFR 0.152.
 */
package bh;

import bh.f;
import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class g {
    private static final Logger a = Logger.getLogger(g.class.getName());
    private final int b;
    private final f c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;
    private final int h;
    private final boolean i;

    private g(int id, f gfx, int x2, int y2, int mapId, int hp, int keeper, boolean isOpening) {
        this.b = id;
        this.c = gfx;
        this.d = x2;
        this.e = y2;
        this.f = mapId;
        this.g = hp;
        this.h = keeper;
        this.i = isOpening;
    }

    public int a() {
        return this.b;
    }

    public f b() {
        return this.c;
    }

    public int c() {
        return this.d;
    }

    public int d() {
        return this.e;
    }

    public int e() {
        return this.f;
    }

    public int f() {
        return this.g;
    }

    public int g() {
        return this.h;
    }

    public boolean h() {
        return this.i;
    }

    public static List<g> i() {
        ArrayList<g> result;
        block6: {
            result = new ArrayList<g>();
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM spawnlist_door");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        int gfxId = rs.getInt("gfxid");
                        int x2 = rs.getInt("locx");
                        int y2 = rs.getInt("locy");
                        int mapId = rs.getInt("mapid");
                        int hp = rs.getInt("hp");
                        int keeper = rs.getInt("keeper");
                        boolean isOpening = rs.getBoolean("isOpening");
                        f gfx = bh.f.a(gfxId);
                        g spawn = new g(id, gfx, x2, y2, mapId, hp, keeper, isOpening);
                        result.add(spawn);
                    }
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    j.a(rs, pstm, con);
                    break block6;
                }
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
        }
        return result;
    }
}

