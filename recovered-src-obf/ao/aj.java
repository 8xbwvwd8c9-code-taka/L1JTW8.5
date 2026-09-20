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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class aj {
    private static final Logger a = Logger.getLogger(aj.class.getName());
    private static aj b;

    public static aj a() {
        if (b == null) {
            b = new aj();
        }
        return b;
    }

    private aj() {
        this.b();
    }

    private void b() {
        block6: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM spawnlist_light");
                    rs = pstm.executeQuery();
                    while (rs.next()) {
                        l l1npc = au.a().a(rs.getInt(2));
                        if (l1npc == null) continue;
                        i field = new i(l1npc);
                        field.cF(d.a().c());
                        field.cG(rs.getInt("locx"));
                        field.cH(rs.getInt("locy"));
                        field.cE(rs.getInt("mapid"));
                        field.q(field.fs());
                        field.r(field.ft());
                        field.ct(0);
                        field.s(l1npc.ae());
                        aq.a().a(field);
                        aq.a().c(field);
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
    }
}

