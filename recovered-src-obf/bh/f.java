/*
 * Decompiled with CFR 0.152.
 */
package bh;

import bi.j;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class f {
    private static final Logger a = Logger.getLogger(f.class.getName());
    private final int b;
    private final int c;
    private final int d;
    private final int e;
    private final int f;
    private final int g;

    private f(int gfxId, int direction, int entxOffset, int entyOffset, int rightEdgeOffset, int leftEdgeOffset) {
        this.b = gfxId;
        this.c = direction;
        this.f = rightEdgeOffset;
        this.g = leftEdgeOffset;
        this.d = entxOffset;
        this.e = entyOffset;
    }

    public int a() {
        return this.b;
    }

    public int b() {
        return this.c;
    }

    public int c() {
        return this.f;
    }

    public int d() {
        return this.g;
    }

    public int e() {
        return this.d;
    }

    public int f() {
        return this.e;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static f a(int gfxId) {
        f f2;
        ResultSet rs;
        PreparedStatement pstm;
        Connection con;
        block5: {
            con = null;
            pstm = null;
            rs = null;
            con = l1j.server.b.a().b();
            pstm = con.prepareStatement("SELECT * FROM door_gfxs WHERE gfxid = ?");
            pstm.setInt(1, gfxId);
            rs = pstm.executeQuery();
            if (rs.next()) break block5;
            System.out.println("DoorGfx: " + gfxId + "is not found");
            j.a(rs, pstm, con);
            return null;
        }
        try {
            int id = rs.getInt("gfxid");
            int dir = rs.getInt("direction");
            int rEdge = rs.getInt("right_edge_offset");
            int lEdge = rs.getInt("left_edge_offset");
            int entx = rs.getInt("enterX_offset");
            int enty = rs.getInt("enterY_offset");
            f2 = new f(id, dir, entx, enty, rEdge, lEdge);
        }
        catch (SQLException e2) {
            try {
                a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
            }
            catch (Throwable throwable) {
                j.a(rs, pstm, con);
                throw throwable;
            }
            j.a(rs, pstm, con);
            return null;
        }
        j.a(rs, pstm, con);
        return f2;
    }
}

