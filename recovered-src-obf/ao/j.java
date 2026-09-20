/*
 * Decompiled with CFR 0.152.
 */
package ao;

import a.g;
import an.b;
import an.c;
import ap.u;
import be.dc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import l1j.server.b;

public class j {
    private static final Logger a = Logger.getLogger(j.class.getName());
    private static j b;

    public static j a() {
        if (b == null) {
            b = new j();
        }
        return b;
    }

    public void a(u pc) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("UPDATE character_equip SET data=? WHERE id=?");
                    pstm.setBytes(1, this.d(pc));
                    pstm.setInt(2, pc.fr());
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(pstm);
                    bi.j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(pstm);
                bi.j.a(con);
                throw throwable;
            }
            bi.j.a(pstm);
            bi.j.a(con);
        }
    }

    public void b(u pc) {
        block9: {
            Connection con = null;
            PreparedStatement pstm = null;
            ResultSet rs = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("SELECT * FROM character_equip WHERE id=?");
                    pstm.setInt(1, pc.fr());
                    rs = pstm.executeQuery();
                    if (rs.next()) {
                        byte[] data = rs.getBytes("data");
                        c.a msg = c.a.a(data);
                        pc.bu(msg.p());
                        pc.dT().clear();
                        pc.dU().clear();
                        for (g bs2 : msg.q()) {
                            b.c msg2 = b.c.a(bs2);
                            ArrayList<Integer> equipList = msg2.p() == 0 ? pc.dT() : pc.dU();
                            for (int i2 : msg2.q()) {
                                equipList.add(i2);
                            }
                        }
                    } else {
                        this.c(pc);
                    }
                    pc.a(new dc(pc.dT(), pc.dU(), pc.dV()));
                }
                catch (Exception e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(rs, pstm, con);
                    break block9;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(rs, pstm, con);
                throw throwable;
            }
            bi.j.a(rs, pstm, con);
        }
    }

    private void c(u pc) {
        block5: {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                try {
                    con = l1j.server.b.a().b();
                    pstm = con.prepareStatement("INSERT INTO character_equip SET id=?, data=?");
                    pstm.setInt(1, pc.fr());
                    pstm.setBytes(2, this.d(pc));
                    pstm.execute();
                }
                catch (SQLException e2) {
                    a.log(Level.SEVERE, e2.getLocalizedMessage(), e2);
                    bi.j.a(pstm);
                    bi.j.a(con);
                    break block5;
                }
            }
            catch (Throwable throwable) {
                bi.j.a(pstm);
                bi.j.a(con);
                throw throwable;
            }
            bi.j.a(pstm);
            bi.j.a(con);
        }
    }

    private byte[] d(u pc) {
        c.a.a builder10 = c.a.aa();
        builder10.b(pc.dV());
        b.c.a builder = b.c.s();
        b.c.a builder2 = b.c.s();
        builder.b(0);
        for (int i2 : pc.dT()) {
            builder.c(i2);
        }
        builder10.e(builder.t().f());
        builder2.b(1);
        for (int i2 : pc.dU()) {
            builder2.c(i2);
        }
        builder10.e(builder2.t().f());
        builder10.c(2);
        builder10.d(1);
        return builder10.M().g();
    }
}

